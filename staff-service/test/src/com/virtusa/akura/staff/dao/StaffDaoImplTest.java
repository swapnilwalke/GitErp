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
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.StaffCategory;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.dao.CityDao;
import com.virtusa.akura.common.dao.DistrictDao;
import com.virtusa.akura.common.dao.ProvinceDao;
import com.virtusa.akura.common.dao.StaffCategoryDao;
import com.virtusa.akura.staff.BaseStaffTest;

/**
 * This test class, tests all the functionalities for the StaffDaoImpl.
 * 
 * @author Virtusa Corporation
 */
public class StaffDaoImplTest extends BaseStaffTest {
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private StaffDao staffDao;
    
    /** Holds StaffCategoryDao instance. */
    @Autowired
    private StaffCategoryDao staffCategoryDao;
    
    /** Holds CityDao instance. */
    @Autowired
    private CityDao cityDao;
    
    /** Holds DistrictDao instance. */
    @Autowired
    private DistrictDao districtDao;
    
    /** Holds ProvinceDao instance. */
    @Autowired
    private ProvinceDao provinceDao;
    
    /**
     * Represents an instance of Staff.
     */
    private Staff staff;
    
    /** Holds StaffCategory instance. */
    private StaffCategory staffCategory;
    
    /** Holds City instance. */
    private City city;
    
    /** Holds District instance. */
    private District district;
    
    /** Holds District instance. */
    private Province province;
    
    /**
     * Setting up resources.
     * @throws AkuraAppException - the exception that occurred.
     */
    @Before
    public final void setUp() throws AkuraAppException{
    
        // Instantiate a Province object
        Province newProvince = new Province();
        newProvince.setDescription("StaffDaoImplTest Description*--*");
        province = provinceDao.save(newProvince);
        assertNotNull(" Province saved to db " + province);
        
        // Instantiate a District object
        District newDistrict = new District();
        newDistrict.setDescription("StaffDaoImplTest Descrition*--*");
        newDistrict.setProvince(province);
        district = districtDao.save(newDistrict);
        assertNotNull(" District saved to db " + district);
        
        // Instantiate a city
        City newCity = new City();
        newCity.setDescription("StaffDaoImplTest Description*--*");
        newCity.setDistrict(district);
        city = cityDao.save(newCity);
        assertNotNull(" City saved to db " + city);
        
        // Instantiate a staffCategory object.
        StaffCategory newStaffCategory = new StaffCategory();
        newStaffCategory.setAcademic(true);
        newStaffCategory.setDescription("StaffDaoImplTest Description*--*");
        staffCategory = staffCategoryDao.save(newStaffCategory);
        assertNotNull(" StaffCategory saved to db " + staffCategory);
        
        // Instantiate a staff object.
        staff = new Staff();
        staff.setStaffCategory(staffCategory);
        staff.setRegistrationNo("StaffDaoImplTest RegNo*--*");
        staff.setDateOfHire(new Date());
        staff.setFullName("StaffDaoImplTest FullName");
        staff.setLastName("StaffDaoImplTest LastName");
        staff.setNationalID("StaffDaoImplTest ID*--*");
        staff.setDateOfBirth(new Date());
        staff.setAddress("StaffDaoImplTest Address");
        staff.setGender('M');
        staff.setCityId(city.getCityId());
        
    }
    
    /**
     * Test method to check if a Staff object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testSaveStaff() throws AkuraAppException {
    
        try {
            
            Staff newStaff = staffDao.save(staff);
            assertNotNull("New Staff should not be null. " + newStaff);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a Staff object was successfully updated to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testUpdateStaff() throws AkuraAppException {
    
        Staff newStaff = null;
        String updatesName="Staff Updated FullName";
        
        try {
            newStaff = staffDao.save(staff);
            assertNotNull("New Staff should not be null " + newStaff);
            
            newStaff.setFullName(updatesName);
            staffDao.update(newStaff);
            Staff updatedStaff =
                    (Staff) staffDao.findById(Staff.class,
                            newStaff.getStaffId());
            assertEquals(updatesName, updatedStaff.getFullName());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a list of objects are saved / updated to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testSaveOrUpdateAll() throws AkuraAppException {
    
        try {
            
            List<Staff> staffList = new ArrayList<Staff>();
            staffList.add(staff);
            
            staffDao.saveOrUpdateAll(staffList);
            List<Staff> newStaffList = staffDao.findAll(Staff.class);
            assertNotNull("Staff list should not be null ", newStaffList);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check find the Staff records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindStaffById() throws AkuraAppException {
    
        try {
            
            Staff newStaff = staffDao.save(staff);
            assertNotNull("New Staff should not be null " + newStaff);
            
            Staff findStaff =
                    (Staff) staffDao.findById(Staff.class,
                            newStaff.getStaffId());
            assertNotNull("find Staff should not be null ", findStaff);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check load all the Staff records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindAllStaff() throws AkuraAppException {
    
        try {
            
            Staff newStaff = staffDao.save(staff);
            assertNotNull("New Staff should not be null " + newStaff);
            
            List<Staff> staffList = staffDao.findAll(Staff.class);
            assertNotNull("Staff list should not be null ", staffList);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method staffSearch.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testStaffSearch() throws AkuraAppException {
    
        try {
            
            Staff newStaff = staffDao.save(staff);
            assertNotNull("New Staff should not be null " + newStaff);
            
            List<Staff> staffList = staffDao.staffSearch(newStaff);
            assertTrue(staffList.size() > 0);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method findStaffIdForRegistrationNo.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindStaffIdForRegistrationNo() throws AkuraAppException {
    
        try {
            
            Staff newStaff = staffDao.save(staff);
            assertNotNull("New Staff should not be null " + newStaff);
            
            int staffID = staffDao.findStaffIdForRegistrationNo(newStaff.getRegistrationNo());
            assertEquals(staffID,newStaff.getStaffId());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method getacademicStaff.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testGetacademicStaff() throws AkuraAppException {
    
        try {
            
            Staff newStaff = staffDao.save(staff);
            assertNotNull("New Staff should not be null " + newStaff);
            
            List<Staff> staffAcademicList = staffDao.getacademicStaff();
            assertTrue(staffAcademicList.size() > 0);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /*
    *//**
     * Test case to test Staff Search.
     * 
     * @throws AkuraAppException SMSExceptions
     *//*
    @Test
    public void testStaffSearch() throws AkuraAppException {
    
        List<Staff> staffList = staffDao.staffSearch(staff);
        assertEquals(1, staffList.size());
    }
    
    *//**
     * test student save update retrive and delete dao functionalities as a flow.
     * 
     * @throws AkuraAppException - AkuraAppException.
     *//*
    @Test
    public void testCheckAdmissionNoIsExist() throws AkuraAppException {
    
        try {
            Staff retrievedStaff = (Staff) staffDao.findById(Staff.class, 1);
            
            assertEquals("check Registration No failed", retrievedStaff.getStaffId(),
                    staffDao.findStaffIdForRegistrationNo("1"));
            
        } catch (AkuraAppException ex) {
            assertFalse("exception occured in testCheckResgistrationNo", true);
        }
        
    }*/
    
    /**
     * Tear down method for each test case.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @After
    public void tearDown() throws AkuraAppException {
    
        try {
            staffDao.delete(staff);
            staffCategoryDao.delete(staffCategory);
            
            cityDao.delete(city);
            districtDao.delete(district);
            provinceDao.delete(province);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
        
    }
    
}
