package com.mii.file.scanner.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author erwinsn
 */
@Table
@Entity
public class MasterFile extends BaseEntity {
    
    @Column
    private String fileName;
    
    @Column
    private String absolutePath;
    
    @Column
    private int totalRecord;
    
    private LocalDateTime fileCreatedDate;
    
    private LocalDateTime fileLastModified;
    
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "masterFile",
            fetch = FetchType.EAGER)
    private List<MasterFileContent> masterFileContents;
    
    public MasterFile() {
        super();
        totalRecord = 0;
        masterFileContents = new ArrayList<>();
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the absolutePath
     */
    public String getAbsolutePath() {
        return absolutePath;
    }

    /**
     * @param absolutePath the absolutePath to set
     */
    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    /**
     * @return the fileCreatedDate
     */
    public LocalDateTime getFileCreatedDate() {
        return fileCreatedDate;
    }

    /**
     * @param fileCreatedDate the fileCreatedDate to set
     */
    public void setFileCreatedDate(LocalDateTime fileCreatedDate) {
        this.fileCreatedDate = fileCreatedDate;
    }

    /**
     * @return the fileLastModified
     */
    public LocalDateTime getFileLastModified() {
        return fileLastModified;
    }

    /**
     * @param fileLastModified the fileLastModified to set
     */
    public void setFileLastModified(LocalDateTime fileLastModified) {
        this.fileLastModified = fileLastModified;
    }

    /**
     * @return the masterFileContents
     */
    public List<MasterFileContent> getMasterFileContents() {
        return masterFileContents;
    }

    /**
     * @param masterFileContents the masterFileContents to set
     */
    public void setMasterFileContents(List<MasterFileContent> masterFileContents) {
        this.masterFileContents = masterFileContents;
    }

    /**
     * @return the totalRecord
     */
    public int getTotalRecord() {
        return totalRecord;
    }

    /**
     * @param totalRecord the totalRecord to set
     */
    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }
    
}
