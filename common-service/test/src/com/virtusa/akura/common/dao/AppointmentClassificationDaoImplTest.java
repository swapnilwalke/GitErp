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

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.common.BaseCommonTest;
import com.virtusa.akura.api.dto.AppointmentClassification;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * AppointmentClassificationDaoImplTest is to test all the persistent related functionalities for the *
 * appointmentClassification object.
 * 
 * @author Virtusa Corporation
 */
public class AppointmentClassificationDaoImplTest extends BaseCommonTest {
    
    /**
     * Holds appointmentClassificationDAO instance of {@link AppointmentClassificationDao}.
     */
    @Autowired
    private AppointmentClassificationDao appointmentClassificationDao;
    
    /**
     * Holds appointmentClassification instance of {@link AppointmentClassification}.
     */
    private AppointmentClassification appointmentClassification;
    
    /**
     * Instantiate the set up method.
     * 
     * @throws java.lang.Exception {@link Exception}
     */
    @Before
    public final void setUp() throws Exception {

        appointmentClassification = new AppointmentClassification();
        appointmentClassification.setDescription("Mathermatics");
    }
    
    /**
     * Test method for save AppointmentClassification.
     */
    @Test
    public final void testAddAppointmentClassification() {

        try {
            AppointmentClassification addAppointmentClassification =
                    this.appointmentClassificationDao.save(appointmentClassification);
            assertNotNull(" Added AppointmentClassification object can not be null ", addAppointmentClassification);
        } catch (AkuraAppException e) {
            e.getStackTrace();
        }
    }
    
    /**
     * Test method for update AppointmentClassification. This test method covers test for get
     * AppointmentClassification by id too.
     */
    @Test
    public final void testModifyAppointmentClassification() {

        try {
            AppointmentClassification updateAppointmentClassification =
                    this.appointmentClassificationDao.save(appointmentClassification);
            assertNotNull(" Added AppointmentClassification object can not be null ", updateAppointmentClassification);
            
            updateAppointmentClassification.setDescription("Accounting");
            this.appointmentClassificationDao.update(updateAppointmentClassification);
            
            AppointmentClassification findAppointmentClassification =
                    (AppointmentClassification) appointmentClassificationDao.findById(AppointmentClassification.class,
                            updateAppointmentClassification.getClassificationId());
            assertEquals("AppointmentClassification object has been modified successfully",
                    updateAppointmentClassification.getDescription(), findAppointmentClassification.getDescription());
        } catch (AkuraAppException e) {
            e.getStackTrace();
        }
    }
    
    /**
     * Test method for get all the available AppointmentClassification.
     */
    @Test
    public final void testGetAppointmentClassification() {

        try {
            AppointmentClassification addAppointmentClassification =
                    this.appointmentClassificationDao.save(appointmentClassification);
            assertNotNull(" Added AppointmentClassification object can not be null ", addAppointmentClassification);
            
            List<AppointmentClassification> appointmentClassifications =
                    this.appointmentClassificationDao.findAll(AppointmentClassification.class);
            assertTrue("Received AppointmentClassification object list size cannot be zero",
                    appointmentClassifications.size() > 0);
        } catch (AkuraAppException e) {
            e.getStackTrace();
        }
    }
    
    /**
     * Tear down method for each test case. This handles delete AppointmentClassification.
     */
    @After
    public final void teardown() {

        try {
            this.appointmentClassificationDao.delete(appointmentClassification);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
}
