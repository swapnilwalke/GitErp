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
import com.virtusa.akura.api.dto.Grading;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * GradingDaoImplTest is to test all the persistent related functionalities for the Grading object.
 * 
 * @author Virtusa Corporation
 */

public class GradingDaoImplTest extends BaseCommonTest {
    
    /**
     * Holds gradingDao instance of {@link GradingDao}.
     */
    @Autowired
    private GradingDao gradingDao;
    
    /**
     * Holds grading instance of {@link Grading}.
     */
    private Grading grading;
    
    /**
     * Instantiate the setup method.
     * 
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws Exception {

        grading = new Grading();
        grading.setDescription("Very Good");
        grading.setGradeAcronym("A");
    }
    
    /**
     * Test method for save Grading.
     */
    @Test
    public final void testAddGrading() {

        try {
            Grading gradingObj = this.gradingDao.save(grading);
            assertNotNull("Added Grading object can not be null ", gradingObj);
        } catch (AkuraAppException e) {
            e.getStackTrace();
        }
    }
    
    /**
     * Test method for update Grading. This test method covers test for get Grading by id too.
     */
    @Test
    public final void testModifyGrading() {

        try {
            Grading updateGrading = this.gradingDao.save(grading);
            assertNotNull("Updated Grading object can not be null ", updateGrading);
            
            updateGrading.setDescription("Average");
            this.gradingDao.update(updateGrading);
            
            Grading findGrading = (Grading) gradingDao.findById(Grading.class, updateGrading.getGradingId());
            assertEquals("Grading object has been modified successfully", updateGrading.getDescription(), findGrading
                    .getDescription());
        } catch (AkuraAppException e) {
            e.getStackTrace();
        }
    }
    
    /**
     * Test method for get all the available Gradings.
     */
    @Test
    public final void testGetGrading() {

        try {
            Grading addGrading = this.gradingDao.save(grading);
            assertNotNull("Added Grading object can not be null ", addGrading);
            
            List<Grading> gradings = this.gradingDao.findAll(Grading.class);
            assertTrue("Received Grading object list size cannot be zero",gradings.size() > 0);
        } catch (AkuraAppException e) {
            e.getStackTrace();
        }
    }
    
    /**
     * teardown method for each test case.
     * 
     * @throws Exception - the exception.
     */
    @After
    public final void teardown() throws Exception {

        try {
            this.gradingDao.delete(grading);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
}
