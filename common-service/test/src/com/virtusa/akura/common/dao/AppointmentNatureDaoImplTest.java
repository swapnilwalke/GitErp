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

import com.virtusa.akura.api.dto.AppointmentNature;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseCommonTest;

/**
 * AppointmentNatureDaoImplTest is to test all the persistent related functionalities for the appointmentNature object.
 * 
 * @author Virtusa Corporation
 */
public class AppointmentNatureDaoImplTest extends BaseCommonTest {
    
    /**
     * Holds appointmentNatureDao instance of {@link AppointmentNatureDao}.
     */
    @Autowired
    private AppointmentNatureDao appointmentNatureDao;
    
    /**
     * Holds appointmentNature instance of {@link AppointmentNature}.
     */
    private AppointmentNature appointmentNature;
    
    /**
     * Instantiate the set up method.
     * 
     * @throws java.lang.Exception {@link Exception}
     */
    @Before
    public final void setUp() throws Exception {

        appointmentNature = new AppointmentNature();
        appointmentNature.setDescription("Degree");
        
    }
    
    /**
     * Test method for Add AppointmentNature.
     */
    @Test
    public final void testAddAppointmentNature() {

        try {
            AppointmentNature addAppointmentNature = this.appointmentNatureDao.save(appointmentNature);
            assertNotNull("Added Appointment Nature Cannot Be NULL", addAppointmentNature);
        } catch (AkuraAppException ak) {
            ak.printStackTrace();
        }
        
    }
    
    /**
     * Test method for Edit AppointmentNature. This test method covers test for get AppointmentNature by id too.
     */
    @Test
    public final void testEditAppointmentNature() {

        try {
            
            AppointmentNature editAppointmentNature = this.appointmentNatureDao.save(appointmentNature);
            assertNotNull("Added Appointment Nature Cannot Be NULL", editAppointmentNature);
            
            editAppointmentNature.setDescription("Political Appointment");
            this.appointmentNatureDao.update(editAppointmentNature);
            
            AppointmentNature findAppointmentNature =
                    (AppointmentNature) appointmentNatureDao.findById(AppointmentNature.class,
                            appointmentNature.getNatureId());
            assertEquals("Appointment Nature Object Has Been Edited Successfully",
                    editAppointmentNature.getDescription(), findAppointmentNature.getDescription());
            
        } catch (AkuraAppException ak) {
            ak.printStackTrace();
        }
    }
    
    /**
     * Test method for get all the available Appointment Natures.
     */
    @Test
    public final void testGetAppointmentNature() {

        try {
            
            AppointmentNature getAppointmentNature = this.appointmentNatureDao.save(appointmentNature);
            assertNotNull("Added appointment object cannot be null", getAppointmentNature);
            
            List<AppointmentNature> appointmentNatures = this.appointmentNatureDao.findAll(AppointmentNature.class);
            assertTrue("Received appointment nature size cannot be zero", appointmentNatures.size() > 0);
            
        } catch (AkuraAppException ak) {
            ak.printStackTrace();
        }
        
    }
    
    /**
     * Tear down method for each test case. This handles delete Appointment Nature.
     */
    @After
    public final void tearDown() {

        try {
            this.appointmentNatureDao.delete(appointmentNature);
            
        } catch (AkuraAppException ak) {
            ak.printStackTrace();
        }
    }
    
}
