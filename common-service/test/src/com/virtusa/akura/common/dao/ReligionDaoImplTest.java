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
import com.virtusa.akura.api.dto.Religion;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * ReligionDaoImplTest is to test all the persistent related functionalities for the religion object.
 * 
 * @author Virtusa Corporation
 */
public class ReligionDaoImplTest extends BaseCommonTest {
    
    /**
     * Holds religionDAO instance of {@link ReligionDao}.
     */
    @Autowired
    private ReligionDao religionDAO;
    
    /**
     * Holds religion instance of {@link Religion}.
     */
    private Religion religion;
    
    /**
     * Instantiate the set up method.
     * 
     * @throws java.lang.Exception {@link Exception}
     */
    @Before
    public final void setUp() throws Exception {

        religion = new Religion();
        religion.setDescription("Budhism");
    }
    
    /**
     * Test method for save Religion.
     */
    @Test
    public final void testAddReligion() {

        try {
            Religion addReligion = this.religionDAO.save(religion);
            assertNotNull(" Added Religion object can not be null ", addReligion);
        } catch (AkuraAppException e) {
            e.getStackTrace();
        }
    }
    
    /**
     * Test method for update Religion. This test method covers test for get Religion by id too.
     */
    @Test
    public final void testModifyReligion() {

        try {
            Religion updateReligion = this.religionDAO.save(religion);
            assertNotNull(" Added Religion object can not be null ", updateReligion);
            
            updateReligion.setDescription("Islam");
            this.religionDAO.update(updateReligion);
            
            Religion findReligion = (Religion) religionDAO.findById(Religion.class, updateReligion.getReligionId());
            assertEquals("Religion object has been modified successfully", updateReligion.getDescription(),
                    findReligion.getDescription());
        } catch (AkuraAppException e) {
            e.getStackTrace();
        }
    }
    
    /**
     * Test method for get all the available Religions.
     */
    @Test
    public final void testGetReligions() {

        try {
            Religion addReligion = this.religionDAO.save(religion);
            assertNotNull(" Added Religion object can not be null ", addReligion);
            
            List<Religion> religions = this.religionDAO.findAll(Religion.class);
            assertTrue("Received Religion object list size cannot be zero", religions.size() > 0);
        } catch (AkuraAppException e) {
            e.getStackTrace();
        }
    }
    
    /**
     * Tear down method for each test case. This handles delete Religion.
     */
    @After
    public final void teardown() {

        try {
            this.religionDAO.delete(religion);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
}
