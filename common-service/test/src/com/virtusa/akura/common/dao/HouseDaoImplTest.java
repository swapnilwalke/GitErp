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
import com.virtusa.akura.api.dto.House;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * HouseDaoImplTest is to test all the persistent related functionalities for the House object.
 * 
 * @author Virtusa Corporation
 */
public class HouseDaoImplTest extends BaseCommonTest {
    
    /**
     * Holds houseDAO instance of {@link HouseDao}.
     */
    @Autowired
    private HouseDao houseDAO;
    
    /**
     * Holds house instance of {@link House}.
     */
    private House house;
    
    /**
     * Instantiate the set up method.
     * 
     * @throws java.lang.Exception {@link Exception}
     */
    @Before
    public final void setUp() throws Exception {

        house = new House();
        house.setName("Auntum");
    }
    
    /**
     * Test method for save House.
     */
    @Test
    public final void testAddHouse() {

        try {
            House addHouse = this.houseDAO.save(house);
            assertNotNull("Added House object can not be null ", addHouse);
        } catch (AkuraAppException e) {
            e.getStackTrace();
        }
    }
    
    /**
     * Test method for update House. This test method covers test for get House by id too.
     */
    @Test
    public final void testModifyHouse() {

        try {
            House updateHouse = this.houseDAO.save(house);
            assertNotNull("Added House object can not be null ",updateHouse);
            
            updateHouse.setName("Auatumn");
            this.houseDAO.update(updateHouse);
            
            House findHouse = (House) houseDAO.findById(House.class, updateHouse.getHouseId());
            assertEquals("House object has been modified successfully", updateHouse.getName(), findHouse.getName());
        } catch (AkuraAppException e) {
            e.getStackTrace();
        }
    }
    
    /**
     * Test method for get all the available Houses.
     */
    @Test
    public final void testGetHouses() {

        try {
            House addHouse = this.houseDAO.save(house);
            assertNotNull(addHouse);
            
            List<House> houses = this.houseDAO.findAll(House.class);
            assertTrue("Received House object list size cannot be zero",houses.size() > 0);
        } catch (AkuraAppException e) {
            e.getStackTrace();
        }
    }
    
    /**
     * Tear down method for each test case. This handles delete House.
     */
    @After
    public final void teardown() {

        try {
            this.houseDAO.delete(house);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
}
