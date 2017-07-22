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

package com.virtusa.akura.staff.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.staff.BaseStaffTest;
import com.virtusa.akura.api.dto.StaffCategory;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.dao.StaffCategoryDao;

/**
 * This test class, tests all the functionalities for the SectionHeadDaoImpl.
 * 
 * @author Virtusa Corporation
 */
public class StaffCategoryDaoImplTest extends BaseStaffTest {
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private StaffCategoryDao staffCategoryDao;
    
    /** Holds StaffCategory instance. */
    private StaffCategory staffCategory;
    
    /**
     * Setting up resources.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Before
    public final void setUp() throws AkuraAppException {
    
        staffCategory = new StaffCategory();
        staffCategory.setAcademic(true);
        staffCategory.setDescription("StaffCategoryDaoImplTest Description*---");
        
    }
    
    /**
     * Test method to check if a StaffCategory object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testSaveStaffCategory() throws AkuraAppException {
    
        try {
            
            StaffCategory newStaffCategory = staffCategoryDao.save(staffCategory);
            assertNotNull("New StaffCategory should not be null. " + newStaffCategory);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a StaffCategory object was successfully updated to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testUpdateStaffCategory() throws AkuraAppException {
    
        StaffCategory newStaffCategory = null;
        try {
            newStaffCategory = staffCategoryDao.save(staffCategory);
            assertNotNull("New StaffCategory should not be null " + newStaffCategory);
            
            String updateDescription = "updates Description";
            
            newStaffCategory.setDescription(updateDescription);
            staffCategoryDao.update(newStaffCategory);
            StaffCategory updatedStaffCategory =
                    (StaffCategory) staffCategoryDao.findById(StaffCategory.class,
                            newStaffCategory.getCatogaryID());
            assertEquals(updateDescription, updatedStaffCategory.getDescription());
            
        } catch (AkuraAppException e) {
        	e.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a list of StaffCategory objects are saved / updated to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testSaveOrUpdateAll() throws AkuraAppException {
    
        try {
            
            List<StaffCategory> staffCategoryList = new ArrayList<StaffCategory>();
            staffCategoryList.add(staffCategory);
            
            staffCategoryDao.saveOrUpdateAll(staffCategoryList);
            List<StaffCategory> newStaffCategoryList = staffCategoryDao.findAll(StaffCategory.class);
            assertNotNull("StaffCategory list should not be null ", newStaffCategoryList);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check find the StaffCategory records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindStaffCategoryById() throws AkuraAppException {
    
        try {
            
            StaffCategory newStaffCategory = staffCategoryDao.save(staffCategory);
            assertNotNull("New StaffCategory should not be null " + newStaffCategory);
            
            StaffCategory findStaffCategory =
                    (StaffCategory) staffCategoryDao.findById(StaffCategory.class, newStaffCategory.getCatogaryID());
            assertNotNull("find StaffCategory should not be null ", findStaffCategory);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check load all the StaffCategory records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindAllStaffCategory() throws AkuraAppException {
    
        try {
            
            StaffCategory newStaffCategory = staffCategoryDao.save(staffCategory);
            assertNotNull("New StaffCategory should not be null " + newStaffCategory);
            
            List<StaffCategory> staffCategoryList = staffCategoryDao.findAll(StaffCategory.class);
            assertNotNull("StaffCategory list should not be null ", staffCategoryList);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method isAcademicStaffCategory in StaffCategoryDaoImpl.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testIsAcademicStaffCategory() throws AkuraAppException {
        
        StaffCategory newStaffCategory = staffCategoryDao.save(staffCategory);
        assertNotNull("New StaffCategory should not be null. " + newStaffCategory);
        
        boolean isAcademic = staffCategoryDao.isAcademicStaffCategory(staffCategory.getCatogaryID());
        
        assertEquals(true, isAcademic);
    }
    
        
    /**
     * Tear down method for each test case.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @After
    public void tearDown() throws AkuraAppException {
    
        try {
            staffCategoryDao.delete(staffCategory);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
        
    }
}
