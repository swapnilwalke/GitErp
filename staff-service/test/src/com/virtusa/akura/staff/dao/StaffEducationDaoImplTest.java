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
package com.virtusa.akura.staff.dao;

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
import com.virtusa.akura.api.dto.EducationalQualification;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.StaffCategory;
import com.virtusa.akura.api.dto.StaffEducation;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.dao.CityDao;
import com.virtusa.akura.common.dao.DistrictDao;
import com.virtusa.akura.common.dao.EducationalQualificationDao;
import com.virtusa.akura.common.dao.ProvinceDao;
import com.virtusa.akura.common.dao.StaffCategoryDao;

/**
 * This test class, tests all the functionalities for the SectionHeadDaoImpl.
 * 
 * @author Virtusa Corporation
 */
public class StaffEducationDaoImplTest extends BaseStaffTest {
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private StaffEducationDao staffEducationDao;
    
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
    
    /** Holds EducationalQualificationDao instance. */
    @Autowired
    private EducationalQualificationDao educationalQualificationDao;
    
    /** Holds StaffEducation instance. */
    private StaffEducation staffEducation;
    
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
    
    /** Holds EducationalQualification instance. */
    private EducationalQualification educationalQualification;
    
    /**
     * Setting up resources.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Before
    public final void setUp() throws AkuraAppException {
    
        // Instantiate a Province object
        Province newProvince = new Province();
        newProvince.setDescription("StaffEducationDaoImplTest Description*---*");
        province = provinceDao.save(newProvince);
        assertNotNull(" Province saved to db " , province);
        
        // Instantiate a District object
        District newDistrict = new District();
        newDistrict.setDescription("StaffEducationDaoImplTest Descrition*---*");
        newDistrict.setProvince(province);
        district = districtDao.save(newDistrict);
        assertNotNull(" District saved to db " , district);
        
        // Instantiate a city
        City newCity = new City();
        newCity.setDescription("StaffEducationDaoImplTest Description*---*");
        newCity.setDistrict(district);
        city = cityDao.save(newCity);
        assertNotNull(" City saved to db " , city);
        
        // Instantiate a staffCategory object.
        StaffCategory newStaffCategory = new StaffCategory();
        newStaffCategory.setAcademic(true);
        newStaffCategory.setDescription("StaffEducationDaoImplTest Description*---*");
        staffCategory = staffCategoryDao.save(newStaffCategory);
        assertNotNull(" StaffCategory saved to db " , staffCategory);
        
        // Instantiate a staff object.
        Staff newStaff = new Staff();
        newStaff.setStaffCategory(staffCategory);
        newStaff.setRegistrationNo("StaffEducationDaoImplTest RegNo*---*");
        newStaff.setDateOfHire(new Date());
        newStaff.setFullName("StaffEducationDaoImplTest FullName");
        newStaff.setLastName("StaffEducationDaoImplTest Name");
        newStaff.setNationalID("StaffEducationDaoImplTest ID*---*");
        newStaff.setDateOfBirth(new Date());
        newStaff.setAddress("StaffEducationDaoImplTest Address");
        newStaff.setGender('M');
        newStaff.setCityId(city.getCityId());
        staff = staffDao.save(newStaff);
        assertNotNull(" Staff saved to db " , staff);
        
        // Instantiate a EducationalQualification object.
        EducationalQualification newEducationalQualification = new EducationalQualification();
        newEducationalQualification.setDescription("StaffEducationDaoImplTest Description*---*");
        educationalQualification = educationalQualificationDao.save(newEducationalQualification);
        assertNotNull(" EducationalQualification saved to db " + educationalQualification);
        
        // Instantiate a SubjectTeacher
        staffEducation = new StaffEducation();
        staffEducation.setStaff(staff);
        staffEducation.setEducationalQualification(educationalQualification);
        staffEducation.setDescription("Descriptioin");
    }
    
    /**
     * Test method to check if a StaffEducation object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testSaveStaffEducation() throws AkuraAppException {
    
        try {
            
            StaffEducation newStaffEducation = staffEducationDao.save(staffEducation);
            assertNotNull("New StaffEducation should not be null. " , newStaffEducation);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a StaffEducation object was successfully updated to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testUpdateStaffEducation() throws AkuraAppException {
    
        StaffEducation newStaffEducation = null;
        try {
            newStaffEducation = staffEducationDao.save(staffEducation);
            assertNotNull("New StaffEducation should not be null " , newStaffEducation);
            
            String updatedDescription = "Updated Description";
            
            newStaffEducation.setDescription(updatedDescription);
            staffEducationDao.update(newStaffEducation);
            StaffEducation updatedStaffEducation =
                    (StaffEducation) staffEducationDao.findById(StaffEducation.class,
                            newStaffEducation.getStaffEducationId());
            assertEquals(updatedDescription, updatedStaffEducation.getDescription());
            
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
            
            List<StaffEducation> staffEducationList = new ArrayList<StaffEducation>();
            staffEducationList.add(staffEducation);
            
            staffEducationDao.saveOrUpdateAll(staffEducationList);
            List<StaffEducation> newStaffEducationList = staffEducationDao.findAll(StaffEducation.class);
            assertNotNull("StaffEducation list should not be null ", newStaffEducationList);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check find the StaffEducation records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindStaffEducationById() throws AkuraAppException {
    
        try {
            
            StaffEducation newStaffEducation = staffEducationDao.save(staffEducation);
            assertNotNull("New StaffEducation should not be null " + newStaffEducation);
            
            StaffEducation findStaffEducation =
                    (StaffEducation) staffEducationDao.findById(StaffEducation.class,
                            newStaffEducation.getStaffEducationId());
            assertNotNull("find StaffEducation should not be null ", findStaffEducation);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check load all the StaffEducation records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindAllStaffEducation() throws AkuraAppException {
    
        try {
            
            StaffEducation newStaffEducation = staffEducationDao.save(staffEducation);
            assertNotNull("New StaffEducation should not be null " , newStaffEducation);
            
            List<StaffEducation> staffEducationList = staffEducationDao.findAll(StaffEducation.class);
            assertNotNull("StaffEducation list should not be null ", staffEducationList);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * test case to test getStaffEducationListForStaff.
     * 
     * @throws AkuraAppException - AkuraAppException.
     */
    @Test
    public void testGetStaffEducationListForStaff() throws AkuraAppException {
    
        StaffEducation newStaffEducation = staffEducationDao.save(staffEducation);
        assertNotNull("New StaffEducation should not be null " , newStaffEducation);
        
        List<StaffEducation> staffEducationList =
                staffEducationDao.getStaffEducationListForStaff(newStaffEducation.getStaff().getStaffId());
        
        assertTrue(staffEducationList.size() > 0);
        
    }
    
    /**
     * Tear down method for each test case.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @After
    public void tearDown() throws AkuraAppException {
    
        try {
            staffEducationDao.delete(staffEducation);
            
            staffDao.delete(staff);
            staffCategoryDao.delete(staffCategory);
            
            cityDao.delete(city);
            districtDao.delete(district);
            provinceDao.delete(province);
            
            educationalQualificationDao.delete(educationalQualification);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
        
    }
}
