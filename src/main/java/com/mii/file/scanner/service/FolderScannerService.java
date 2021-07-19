package com.mii.file.scanner.service;

import com.mii.file.scanner.dao.MasterFileRepository;
import com.mii.file.scanner.domain.MasterFile;
import com.mii.file.scanner.domain.MasterFileContent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author vinch
 */
@Service
public class FolderScannerService implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${path.folder.scan}")
    private String folderPath;

    @Autowired
    private MasterFileRepository masterFileRepository;

    @Override
    public void run() {
        try {
            scanFolder();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FolderScannerService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            java.util.logging.Logger.getLogger(FolderScannerService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void scanFolder() throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();

        Map<WatchKey, Path> keys = new HashMap<>();
        String[] arrayPathFolder = folderPath.split(",");
        if (arrayPathFolder.length > 0) {
            for (String pathFolder : arrayPathFolder) {
                Path registerPath = Paths.get(pathFolder);
                WatchKey key = registerPath.register(
                        watchService,
                        StandardWatchEventKinds.ENTRY_CREATE,
                        StandardWatchEventKinds.ENTRY_MODIFY
                );
                keys.put(key, registerPath);
            }

            WatchKey key;
            while ((key = watchService.take()) != null) {
                Path path = keys.get(key);
                Thread.sleep(100);

                for (WatchEvent<?> event : key.pollEvents()) {

                    logger.info(
                            "Event kind:"
                            + event.kind()
                            + ". File affected: "
                            + event.context()
                            + "."
                    );

                    if (event.kind().equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
                        Path pathFile = Paths.get(path.toString() + "\\" + event.context());

                        MasterFile masterFile = new MasterFile();
                        masterFile.setFileName(event.context().toString());
                        masterFile.setAbsolutePath(pathFile.toString());

                        // Extract file attributes
                        BasicFileAttributes attr = Files.readAttributes(pathFile,
                                BasicFileAttributes.class
                        );
                        long fileDateCreated = attr.creationTime().toMillis();
                        long fileDateModified = attr.lastModifiedTime().toMillis();

                        masterFile.setFileCreatedDate(new Timestamp(fileDateCreated).toLocalDateTime());
                        masterFile.setFileLastModified(new Timestamp(fileDateModified).toLocalDateTime());

                        File f = new File(pathFile.toAbsolutePath().toString());
                        InputStream inputStream = null;
                        int totalRecord = 0;
                        try {
                            inputStream = new FileInputStream(f);
                            StringBuilder resultStringBuilder = new StringBuilder();
                            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                                String line;
                                while ((line = br.readLine()) != null) {
                                    String[] splitted = line.split(",", -1);
                                    MasterFileContent content = new MasterFileContent();
                                    content.setField1(splitted[0]);
                                    try {
                                        content.setField2(splitted[1]);
                                        content.setField3(splitted[2]);
                                        content.setField4(splitted[3]);
                                        content.setField5(splitted[4]);
                                        content.setField6(splitted[5]);
                                    } catch(ArrayIndexOutOfBoundsException ex) {
                                        content.setField6(splitted[splitted.length - 1]);
                                        break;
                                    }
                                    content.setRowData(line);
                                    content.setMasterFile(masterFile);
                                    masterFile.getMasterFileContents().add(content);
                                    totalRecord++;
                                }
                            }
                        } finally {
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            masterFile.setTotalRecord(totalRecord);
                            masterFileRepository.save(masterFile);
                        }

                    }

                }
            }

        }

    }

}
