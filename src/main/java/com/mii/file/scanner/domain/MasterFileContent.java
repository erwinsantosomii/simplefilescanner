package com.mii.file.scanner.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author erwinsn
 */
@Table
@Entity
public class MasterFileContent extends BaseEntity {
    
    @Column
    private String field1;
    
    @Column
    private String field2;
    
    @Column
    private String field3;
    
    @Column
    private String field4;
    
    @Column
    private String field5;
    
    @Column
    private String field6;
    
    @Lob
    @Column
    private String rowData;
    
    @ManyToOne
    @JoinColumn(name = "master_file")
    private MasterFile masterFile;
    
    public MasterFileContent() {
        super();
    }

    /**
     * @return the rowData
     */
    public String getRowData() {
        return rowData;
    }

    /**
     * @param rowData the rowData to set
     */
    public void setRowData(String rowData) {
        this.rowData = rowData;
    }

    /**
     * @return the field1
     */
    public String getField1() {
        return field1;
    }

    /**
     * @param field1 the field1 to set
     */
    public void setField1(String field1) {
        this.field1 = field1;
    }

    /**
     * @return the field2
     */
    public String getField2() {
        return field2;
    }

    /**
     * @param field2 the field2 to set
     */
    public void setField2(String field2) {
        this.field2 = field2;
    }

    /**
     * @return the field3
     */
    public String getField3() {
        return field3;
    }

    /**
     * @param field3 the field3 to set
     */
    public void setField3(String field3) {
        this.field3 = field3;
    }

    /**
     * @return the field4
     */
    public String getField4() {
        return field4;
    }

    /**
     * @param field4 the field4 to set
     */
    public void setField4(String field4) {
        this.field4 = field4;
    }

    /**
     * @return the field5
     */
    public String getField5() {
        return field5;
    }

    /**
     * @param field5 the field5 to set
     */
    public void setField5(String field5) {
        this.field5 = field5;
    }

    /**
     * @return the field6
     */
    public String getField6() {
        return field6;
    }

    /**
     * @param field6 the field6 to set
     */
    public void setField6(String field6) {
        this.field6 = field6;
    }

    /**
     * @return the masterFile
     */
    public MasterFile getMasterFile() {
        return masterFile;
    }

    /**
     * @param masterFile the masterFile to set
     */
    public void setMasterFile(MasterFile masterFile) {
        this.masterFile = masterFile;
    }

}
