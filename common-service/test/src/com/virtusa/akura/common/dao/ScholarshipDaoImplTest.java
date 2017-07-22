/*
 * < ÀKURA, This application manages the daily activities of a Teacher and a Student of a School>
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

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.common.BaseCommonTest;
import com.virtusa.akura.api.dto.Scholarship;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * ScholarshipDaoImplTest is to test all the persistent related functionalities for the Scholarship object.
 * 
 * @author Virtusa Corporation
 */
public class ScholarshipDaoImplTest extends BaseCommonTest {
    
    /**
     * Holds scholarshipDAO instance of {@link ScholarshipDao}.
     */
    @Autowired
    private ScholarshipDao scholarshipDAO;
    
    /**
     * Holds scholarship instance of {@link Scholarship}.
     */
    private Scholarship scholarship;
    
    /**
     * Instantiate the set up method.
     * 
     * @throws java.lang.Exception {@link Exception}
     */
    @Before
    public final void setUp() throws Exception {

        scholarship = new Scholarship();
        scholarship.setName("Grade 5 Scholarship Best Marks");
    }
    
    /**
     * Test method for save Scholarship.
     */
    @Test
    public final void testAddScholarship() {

        try {
            Scholarship addScholarship = this.scholarshipDAO.save(scholarship);
            assertNotNull(" Added Scholarship object can not be null ", addScholarship);
        } catch (AkuraAppException e) {
            e.getStackTrace();
        }
    }
    
    /**
     * Test method for update Scholarship. This test method covers test for get Scholarship by id too.
     */
    @Test
    public final void testModifyScholarship() {

        try {
            Scholarship updateScholarship = this.scholarshipDAO.save(scholarship);
            assertNotNull(" Added Scholarship object can not be null ", updateScholarship);
            
            updateScholarship.setName("O/L Top Result");
            this.scholarshipDAO.update(updateScholarship);
            
            Scholarship findScholarship =
                    (Scholarship) scholarshipDAO.findById(Scholarship.class, updateScholarship.getScholarshipId());
            assertEquals("Scholarship object has been modified successfully", updateScholarship.getName(),
                    findScholarship.getName());
        } catch (AkuraAppException e) {
            e.getStackTrace();
        }
    }
    
    /**
     * Test method for get all the available Scholarships.
     */
    @Test
    public final void testGetScholarships() {

        try {
            Scholarship addScholarship = this.scholarshipDAO.save(scholarship);
            assertNotNull(" Added Scholarship object can not be null ", addScholarship);
            
            List<Scholarship> scholarships = this.scholarshipDAO.findAll(Scholarship.class);
            assertTrue("Received Scholarship object list size cannot be zero", scholarships.size() > 0);
        } catch (AkuraAppException e) {
            e.getStackTrace();
        }
    }
    
    /**
     * Tear down method for each test case. This handles delete Scholarship.
     */
    @After
    public final void teardown() {

        try {
            this.scholarshipDAO.delete(scholarship);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
}
