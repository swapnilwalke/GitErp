/*
 * © 2011 Virtusa(Pvt)Ltd. All rights reserved. CONFIDENTIAL AND 

 * PROPRIETARY INFORMATION The information contained herein (the 

 * 'Proprietary Information') is highly confidential and proprietary to and 

 * constitutes trade secrets of Virtusa(Pvt)Ltd. The Proprietary Information

 * for Virtusa(Pvt)Ltd internal use only and shall not be published, 

 * communicated, disclosed or divulged to any person, firm, corporation or 

 * other legal entity, directly or indirectly, without the prior written 

 * consent of Virtusa(Pvt)Ltd Information Management.
 */

package com.virtusa.akura.student.dao;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.api.dto.WarningLevel;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.dao.WarningLevelDao;
import com.virtusa.akura.student.BaseStudentTest;

/**
 * This test class tests all the functionalities of the WarningLevelDaoImpl class.
 * 
 * @author Virtusa Corporation
 */

public class WarningLevelDaoImplTest extends BaseStudentTest {
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private WarningLevelDao warningLevelDaoTarget;
    
    /** Defines the WarningLevel type object. */
    private WarningLevel warnLevel = new WarningLevel();
    
    /** Defines a Date type object and initialized into current date. */
    private java.sql.Date dt = new java.sql.Date(System.currentTimeMillis());
    
    /**
     * Instantiate the setup method.
     * 
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public void setUp() throws Exception {
        
        // warnLevel.setWarningLevelId(3);
        warnLevel.setDescription("Bad");
        warnLevel.setColor("Red");
        warnLevel.setModifiedTime(dt);
    }
    
    /**
     * Test method to check if a warning level object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testAddWarningLevel() throws AkuraAppException {
        
        try {
            WarningLevel newWarnLevel = this.warningLevelDaoTarget.save(warnLevel);
            assertNotNull("New warning level object can not be null" + newWarnLevel);
        } catch (AkuraAppException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    /**
     * Test method to check if a warning level object was successfully updated to the db. Also this method
     * checks the warning level information is successfully retrieve by the id.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testEditWarningLevel() throws AkuraAppException {
        
        try {
            WarningLevel newWarnLevel = this.warningLevelDaoTarget.save(warnLevel);
            assertNotNull("New warning level object can not be null" + newWarnLevel);
            warnLevel.setDescription("Good");
            this.warningLevelDaoTarget.update(newWarnLevel);
            WarningLevel newWarnL =
                (WarningLevel) warningLevelDaoTarget.findById(WarningLevel.class, newWarnLevel.getWarningLevelId());
            assertEquals("Good", newWarnL.getDescription());
        } catch (AkuraAppException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check load all the warning level records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testViewAllWarningLevel() throws AkuraAppException {
        
        try {
            WarningLevel newWarnLevel = warningLevelDaoTarget.save(warnLevel);
            assertNotNull("New warning level object can not be null" + newWarnLevel);
            
            List<WarningLevel> warnLevelList = warningLevelDaoTarget.findAll(WarningLevel.class);
            assertNotNull(warnLevelList.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Tear down method for each test case.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @After
    public final void teardown() throws AkuraAppException {
        
        try {
            warningLevelDaoTarget.delete(warnLevel);
        } catch (AkuraAppException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
