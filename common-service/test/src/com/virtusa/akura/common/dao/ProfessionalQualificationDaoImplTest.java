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
import com.virtusa.akura.api.dto.ProfessionalQualification;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * ProfessionalQualificationDaoImplTest is to test all the persistent related functionalities for the
 * ProfessionalQualification object.
 * 
 * @author Virtusa Corporation
 */
public class ProfessionalQualificationDaoImplTest extends BaseCommonTest {
    
    /**
     * Holds professionalQualificationDAO instance of {@link ProfessionalQualificationDao}.
     */
    @Autowired
    private ProfessionalQualificationDao professionalQualificationDAO;
    
    /**
     * Holds professionalQualification instance of {@link ProfessionalQualification}.
     */
    private ProfessionalQualification professionalQualification;
    
    /**
     * Instantiate the set up method.
     * 
     * @throws java.lang.Exception {@link Exception}
     */
    @Before
    public final void setUp() throws Exception {

        professionalQualification = new ProfessionalQualification();
        professionalQualification.setDescription("Software Engineer");
    }
    
    /**
     * Test method for save ProfessionalQualification.
     */
    @Test
    public final void testAddProfessionalQualification() {

        try {
            ProfessionalQualification addProfessionalQualification =
                    this.professionalQualificationDAO.save(professionalQualification);
            assertNotNull(" Added ProfessionalQualification object can not be null ", addProfessionalQualification);
        } catch (AkuraAppException e) {
            e.getStackTrace();
        }
    }
    
    /**
     * Test method for update ProfessionalQualification. This test method covers test for get
     * ProfessionalQualification by id too.
     */
    @Test
    public final void testModifyProfessionalQualification() {

        try {
            ProfessionalQualification updateProfessionalQualification =
                    this.professionalQualificationDAO.save(professionalQualification);
            assertNotNull(" Added ProfessionalQualification object can not be null ", updateProfessionalQualification);
            
            updateProfessionalQualification.setDescription("Senior Software Engineer");
            this.professionalQualificationDAO.update(updateProfessionalQualification);
            
            ProfessionalQualification findProfessionalQualification =
                    (ProfessionalQualification) professionalQualificationDAO.findById(ProfessionalQualification.class,
                            updateProfessionalQualification.getProfessionalQualificationId());
            assertEquals("ProfessionalQualification object has been modified successfully",
                    updateProfessionalQualification.getDescription(), findProfessionalQualification.getDescription());
        } catch (AkuraAppException e) {
            e.getStackTrace();
        }
    }
    
    /**
     * Test method for get all the available ProfessionalQualifications.
     */
    @Test
    public final void testGetProfessionalQualifications() {

        try {
            ProfessionalQualification addProfessionalQualification =
                    this.professionalQualificationDAO.save(professionalQualification);
            assertNotNull(" Added ProfessionalQualification object can not be null ", addProfessionalQualification);
            
            List<ProfessionalQualification> professionalQualifications =
                    this.professionalQualificationDAO.findAll(ProfessionalQualification.class);
            assertTrue("Received ProfessionalQualification object list size cannot be zero", professionalQualifications
                    .size() > 0);
        } catch (AkuraAppException e) {
            e.getStackTrace();
        }
    }
    
    /**
     * Tear down method for each test case. This handles delete ProfessionalQualification.
     */
    @After
    public final void teardown() {

        try {
            this.professionalQualificationDAO.delete(professionalQualification);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
}
