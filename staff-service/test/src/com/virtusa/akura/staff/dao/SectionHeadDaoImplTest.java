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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.staff.BaseStaffTest;
import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.SectionHead;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.StaffCategory;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.dao.CityDao;
import com.virtusa.akura.common.dao.DistrictDao;
import com.virtusa.akura.common.dao.ProvinceDao;
import com.virtusa.akura.common.dao.StaffCategoryDao;

/**
 * This test class, tests all the functionalities for the SectionHeadDaoImpl.
 * 
 * @author Virtusa Corporation
 */
public class SectionHeadDaoImplTest extends BaseStaffTest {
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private SectionHeadDao sectionHeadDao;
    
    /** Holds StaffDao instance. */
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
    
    /** Holds SectionHead instance. */
    private SectionHead sectionHead;
    
    /** Holds staff instance. */
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
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Before
    public final void setUp() throws AkuraAppException {
    
        // Instantiate a Province object
        Province newProvince = new Province();
        newProvince.setDescription("SectionHeadDaoImplTest Description*--");
        province = provinceDao.save(newProvince);
        assertNotNull(" Province saved to db " + province);
        
        // Instantiate a District object
        District newDistrict = new District();
        newDistrict.setDescription("SectionHeadDaoImplTest Descrition*--");
        newDistrict.setProvince(province);
        district = districtDao.save(newDistrict);
        assertNotNull(" District saved to db " + district);
        
        // Instantiate a city
        City newCity = new City();
        newCity.setDescription("SectionHeadDaoImplTest Description*--");
        newCity.setDistrict(district);
        city = cityDao.save(newCity);
        assertNotNull(" City saved to db " + city);
        
        // Instantiate a staffCategory object.
        StaffCategory newStaffCategory = new StaffCategory();
        newStaffCategory.setAcademic(true);
        newStaffCategory.setDescription("SectionHeadDaoImplTest Description*--");
        staffCategory = staffCategoryDao.save(newStaffCategory);
        assertNotNull(" StaffCategory saved to db " + staffCategory);
        
        // Instantiate a staff object.
        Staff newStaff = new Staff();
        newStaff.setStaffCategory(staffCategory);
        newStaff.setRegistrationNo("SectionHeadDaoImplTest RegNo*--");
        newStaff.setDateOfHire(new Date());
        newStaff.setFullName("SectionHeadDaoImplTest FullName");
        newStaff.setLastName("SectionHeadDaoImplTest Name");
        newStaff.setNationalID("SectionHeadDaoImplTest ID*--");
        newStaff.setDateOfBirth(new Date());
        newStaff.setAddress("SectionHeadDaoImplTest Address");
        newStaff.setGender('M');
        newStaff.setCityId(city.getCityId());
        staff = staffDao.save(newStaff);
        assertNotNull(" Staff saved to db " + staff);
        
        sectionHead = new SectionHead();
        sectionHead.setStaff(staff);
        sectionHead.setSection("Section");
        sectionHead.setStartDate(new Date());
        sectionHead.setEndDate(new Date());
        
    }
    
    /**
     * Test method to check if a ClassTeacher object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testSaveSectionHead() throws AkuraAppException {
    
        try {
            
            SectionHead newSectionHead = sectionHeadDao.save(sectionHead);
            assertNotNull("New SectionHead should not be null. " + newSectionHead);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a SectionHead object was successfully updated to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testUpdateSectionHead() throws AkuraAppException {
    
        SectionHead newSectionHead = null;
        try {
            newSectionHead = sectionHeadDao.save(sectionHead);
            assertNotNull("New SectionHead should not be null " + newSectionHead);
            
            String updateSection = "updates Section";
            
            newSectionHead.setSection(updateSection);
            sectionHeadDao.update(newSectionHead);
            SectionHead updatedSectionHead =
                    (SectionHead) sectionHeadDao.findById(SectionHead.class, newSectionHead.getSectionHeadId());
            assertEquals(updateSection, updatedSectionHead.getSection());
            
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
            
            List<SectionHead> sectionHeadList = new ArrayList<SectionHead>();
            sectionHeadList.add(sectionHead);
            
            sectionHeadDao.saveOrUpdateAll(sectionHeadList);
            List<SectionHead> newSectionHeadList = sectionHeadDao.findAll(SectionHead.class);
            assertNotNull("SectionHead list should not be null ", newSectionHeadList);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check find the SectionHead records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindSectionHeadById() throws AkuraAppException {
    
        try {
            
            SectionHead newSectionHead = sectionHeadDao.save(sectionHead);
            assertNotNull("New SectionHead should not be null " + newSectionHead);
            
            SectionHead findSectionHead =
                    (SectionHead) sectionHeadDao.findById(SectionHead.class, newSectionHead.getSectionHeadId());
            assertNotNull("find SectionHead should not be null ", findSectionHead);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check load all the SectionHead records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindAllSectionHead() throws AkuraAppException {
    
        try {
            
            SectionHead newSectionHead = sectionHeadDao.save(sectionHead);
            assertNotNull("New SectionHead should not be null " + newSectionHead);
            
            List<SectionHead> sectionHeadList = sectionHeadDao.findAll(SectionHead.class);
            assertNotNull("SectionHead list should not be null ", sectionHeadList);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * test case to test ClassTeacher Search.
     * 
     * @throws AkuraAppException - AkuraAppException.
     */
    @Test
    public void testSectionHeadSearch() throws AkuraAppException {
    
        SectionHead newSectionHead = sectionHeadDao.save(sectionHead);
        assertNotNull("New SectionHead should not be null " + newSectionHead);
        
        SimpleDateFormat fomatter = new SimpleDateFormat("yyyy");
        
        List<SectionHead> classTeacherList =
                sectionHeadDao.sectionHeadSearch(newSectionHead.getStaff(),
                        fomatter.format(newSectionHead.getEndDate()));
        assertTrue(classTeacherList.size() > 0);
        
    }
    
    /**
     * Tear down method for each test case.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @After
    public void tearDown() throws AkuraAppException {
    
        try {
            sectionHeadDao.delete(sectionHead);
            
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
