/*
 * < �KURA, This application manages the daily activities of a Teacher and a Student of a School>
 *
 * Copyright (C) 2012 Virtusa Corporation.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package com.virtusa.akura.common.dao;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.api.dto.EmploymentStatus;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * EmploymentStatusDaoImplTest is to test all the persistent related functionalities for the EmploymentStatus
 * object.
 * 
 * @author Virtusa Corporation
 */
public class EmploymentStatusDaoImplTest {
    
    /**
     * Instance of a EmploymentStatus entity.
     */
    @Autowired
    private EmploymentStatusDao employmentStatusDaoTarget;
    
    /**
     * Instance of EmploymentStatus.
     */
    private EmploymentStatus employmentStatus;
    
    /**
     * Instantiate the setup method.
     * 
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws Exception {

        // Instantiates a EmploymentStatus object.
        employmentStatus = new EmploymentStatus();
        employmentStatus.setDescription("NewEmployemntStatus");
        employmentStatus.setModifiedTime(new Date());
    }
    
    /**
     * Test method to check if a StaffServiceCategory object was successfully added to the database.
     * 
     * @throws AkuraAppException - Detailed Exception.
     */
    @Test
    public void testSaveEmploymentStatus() throws AkuraAppException {

        try {
            
            EmploymentStatus newEmploymentStatus = employmentStatusDaoTarget.save(employmentStatus);
            assertNotNull("New EmploymentStatus Should not null" + newEmploymentStatus);
            
        } catch (AkuraAppException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a EmploymentStatus object was successfully updated in the database. Also
     * covering test for find EmploymentStatus object by id too.
     * 
     * @throws AkuraAppException - Detailed Exception.
     */
    @Test
    public void testUpdateEmploymentStatus() throws AkuraAppException {

        try {
            
            String newDescription = "NewDescription";
            EmploymentStatus newEmploymentStatus = employmentStatusDaoTarget.save(employmentStatus);
            assertNotNull("New Employment Status Should not be null" + newEmploymentStatus);
            
            newEmploymentStatus.setDescription(newDescription);
            employmentStatusDaoTarget.update(newEmploymentStatus);
            
            EmploymentStatus updatedEmploymentStatus =
                    (EmploymentStatus) employmentStatusDaoTarget.findById(EmploymentStatus.class,
                            newEmploymentStatus.getEmploymentStatusId());
            
            assertEquals(newDescription, updatedEmploymentStatus.getDescription());
            
        } catch (AkuraAppException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a collection of StaffServiceCategory objects was successfully loaded from the
     * database.
     * 
     * @throws AkuraAppException - Detailed exception.
     */
    @Test
    public final void testFindEmploymentStatus() throws AkuraAppException {

        try {
            EmploymentStatus newEmploymentStatus = employmentStatusDaoTarget.save(employmentStatus);
            assertNotNull("New Employment Status Should not be null" + newEmploymentStatus);
            
            List<EmploymentStatus> employmentStatusList =
                    employmentStatusDaoTarget.findAll(EmploymentStatus.class);
            assertNotNull(employmentStatusList.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Tear down method invoked at the end of each test case automatically by Junit.
     */
    @After
    public final void teardown() {

        try {
            employmentStatusDaoTarget.delete(employmentStatus);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
}

