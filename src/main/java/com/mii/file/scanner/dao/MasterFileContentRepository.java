package com.mii.file.scanner.dao;

import com.mii.file.scanner.domain.MasterFileContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author erwinsn
 */
@Repository
public interface MasterFileContentRepository extends JpaRepository<MasterFileContent, Long> {
    
}
