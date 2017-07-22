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

import com.virtusa.akura.api.dto.StaffServiceCategory;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * StaffServiceCategoryDaoImplTest is to test all the persistent related functionalities for
 * the StaffServiceCategory object.
 * 
 * @author Virtusa Corporation
 * 
 */
public class StaffServiceCategoryDaoImplTest {
    
    /**
     * This instance will be dependency injected by type.
     *
     */
    @Autowired
    private StaffServiceCategoryDao staffServiceCategoryDaoTearget;
    
    /**
     * Instance of a StaffServiceCategory entity.
     */
    private StaffServiceCategory staffServiceCategory;
    
    /**
     * Instantiate the setup method.
     *
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws Exception {
        //Instantiates a StaffServiceCategory object.
        staffServiceCategory = new StaffServiceCategory(); 
        staffServiceCategory.setDescription("NewStaffService");
        staffServiceCategory.setModifiedTime(new Date());
    }
    
    
    /**
     * Test method to check if a StaffServiceCategory object was successfully added 
     * to the database.
     * 
     * @throws AkuraAppException - Detailed Exception.
     */
    @Test
    public void testAddStaffServiceCategory() throws AkuraAppException {

        try{
           StaffServiceCategory newStaffServiceCategory = staffServiceCategoryDaoTearget.save(staffServiceCategory);
           assertNotNull("New Staff Service Category Should not null" + newStaffServiceCategory);
            
        }catch (AkuraAppException e) {
           e.printStackTrace();
        }
    }
    
    
    /**
     * Test method to check if a StaffServiceCategory object was successfully updated in the database.
     * Also covering test for find StaffServiceCategory object by id too.
     * 
     * @throws AkuraAppException - Detailed Exception.
     */
    @Test
    public void testUpdateStaffServiceCategory() throws AkuraAppException {

        try {
            
            String newDescription = "NewDescription"; 
            
            StaffServiceCategory newStaffServiceCategory = staffServiceCategoryDaoTearget.save(staffServiceCategory);
            assertNotNull("New Staff Service Category Should not null" + newStaffServiceCategory);
            
            newStaffServiceCategory.setDescription(newDescription);
            staffServiceCategoryDaoTearget.update(newStaffServiceCategory);
            
            StaffServiceCategory updatedStaffServiceCategory = (StaffServiceCategory) 
                    staffServiceCategoryDaoTearget.findById(StaffServiceCategory.class,
                    newStaffServiceCategory.getServiceId());
            
            assertEquals(newDescription, updatedStaffServiceCategory.getDescription());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * Test method to check if a collection of StaffServiceCategory objects was successfully
     * loaded from the database.
     * 
     *  @throws AkuraAppException - Detailed Exception.
     */
    @Test
    public final void testFIndAllStaffServiceCategories() throws AkuraAppException{
        
        try {
            StaffServiceCategory newStaffServiceCategory = staffServiceCategoryDaoTearget.save(staffServiceCategory);
            assertNotNull("new StaffServiceCategory should not be null " + newStaffServiceCategory);
            
            List<StaffServiceCategory> staffServiceCategoryList = staffServiceCategoryDaoTearget.
                                                  findAll(StaffServiceCategory.class);    
            assertNotNull(staffServiceCategoryList.size());
            
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
            staffServiceCategoryDaoTearget.delete(staffServiceCategory);
        } catch (AkuraAppException e) {
    
            e.printStackTrace();
        }
    }
    
}
