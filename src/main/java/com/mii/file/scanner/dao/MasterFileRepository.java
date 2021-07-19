package com.mii.file.scanner.dao;

import com.mii.file.scanner.domain.MasterFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author vinch
 */
@Repository
public interface MasterFileRepository extends JpaRepository<MasterFile, Long> {
    
}
