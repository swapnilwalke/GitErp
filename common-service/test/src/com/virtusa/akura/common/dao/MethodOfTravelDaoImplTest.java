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
import com.virtusa.akura.api.dto.MethodOfTravel;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * MethodOfTravelDaoImplTest is to test all the persistent related functionalities for the MethodOfTravel
 * object.
 * 
 * @author Virtusa Corporation
 */
public class MethodOfTravelDaoImplTest extends BaseCommonTest {
    
    /**
     * Holds methodOfTravelDAO instance of {@link MethodOfTravelDao}.
     */
    @Autowired
    private MethodOfTravelDao methodOfTravelDAO;
    
    /**
     * Holds methodOfTravel instance of {@link MethodOfTravel}.
     */
    private MethodOfTravel methodOfTravel;
    
    /**
     * Instantiate the set up method.
     * 
     * @throws java.lang.Exception {@link Exception}
     */
    @Before
    public final void setUp() throws Exception {

        methodOfTravel = new MethodOfTravel();
        methodOfTravel.setTravelMethod("Bus");
    }
    
    /**
     * Test method for save MethodOfTravel.
     */
    @Test
    public final void testAddMethodOfTravel() {

        try {
            MethodOfTravel addMethodOfTravel = this.methodOfTravelDAO.save(methodOfTravel);
            assertNotNull("Added MethodOfTravel object can not be null", addMethodOfTravel);
        } catch (AkuraAppException e) {
            e.getStackTrace();
        }
    }
    
    /**
     * Test method for update MethodOfTravel. This test method covers test for get MethodOfTravel by id too.
     */
    @Test
    public final void testModifyMethodOfTravel() {

        try {
            MethodOfTravel updateMethodOfTravel = this.methodOfTravelDAO.save(methodOfTravel);
            assertNotNull("Added MethodOfTravel object can not be null",updateMethodOfTravel);
            
            updateMethodOfTravel.setTravelMethod("Car");
            this.methodOfTravelDAO.update(updateMethodOfTravel);
            
            MethodOfTravel findMethodOfTravel =
                    (MethodOfTravel) methodOfTravelDAO.findById(MethodOfTravel.class, updateMethodOfTravel
                            .getTravelId());
            assertEquals("MethodOfTravel object has been modified successfully",
                    updateMethodOfTravel.getTravelMethod(), findMethodOfTravel.getTravelMethod());
        } catch (AkuraAppException e) {
            e.getStackTrace();
        }
    }
    
    /**
     * Test method for get all the available MethodOfTravels.
     */
    @Test
    public final void testGetMethodOfTravels() {

        try {
            MethodOfTravel addMethodOfTravel = this.methodOfTravelDAO.save(methodOfTravel);
            assertNotNull(addMethodOfTravel);
            
            List<MethodOfTravel> methodOfTravels = this.methodOfTravelDAO.findAll(MethodOfTravel.class);
            assertTrue("Received MethodOfTravel object list size cannot be zero", methodOfTravels.size() > 0);
        } catch (AkuraAppException e) {
            e.getStackTrace();
        }
    }
    
    /**
     * Tear down method for each test case. This handles delete MethodOfTravel.
     */
    @After
    public final void teardown() {

        try {
            this.methodOfTravelDAO.delete(methodOfTravel);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
}
