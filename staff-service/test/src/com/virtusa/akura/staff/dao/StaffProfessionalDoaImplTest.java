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

import com.virtusa.akura.staff.BaseStaffTest;
import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.ProfessionalQualification;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.StaffCategory;
import com.virtusa.akura.api.dto.StaffProfessional;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.dao.CityDao;
import com.virtusa.akura.common.dao.DistrictDao;
import com.virtusa.akura.common.dao.ProfessionalQualificationDao;
import com.virtusa.akura.common.dao.ProvinceDao;
import com.virtusa.akura.common.dao.StaffCategoryDao;

/**
 * This test class, tests all the functionalities for the SectionHeadDaoImpl.
 * 
 * @author Virtusa Corporation
 */
public class StaffProfessionalDoaImplTest extends BaseStaffTest{
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private StaffProfessionalDoa staffProfessionalDoa;
    
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
    
    /** Holds ProfessionalQualificationDao instance. */
    @Autowired
    private ProfessionalQualificationDao professionalQualificationDao;
    
    /** Holds StaffProfessional instance. */
    private StaffProfessional staffProfessional;
    
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
    
    /** Holds ProfessionalQualification instance. */
    private ProfessionalQualification professionalQualification;
    
    /**
     * Setting up resources.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Before
    public final void setUp() throws AkuraAppException {
    
        // Instantiate a Province object
        Province newProvince = new Province();
        newProvince.setDescription("StaffProfessionalDoaImplTest Description-*-");
        province = provinceDao.save(newProvince);
        assertNotNull(" Province saved to db " + province);
        
        // Instantiate a District object
        District newDistrict = new District();
        newDistrict.setDescription("StaffProfessionalDoaImplTest Descrition-*-");
        newDistrict.setProvince(province);
        district = districtDao.save(newDistrict);
        assertNotNull(" District saved to db " + district);
        
        // Instantiate a city
        City newCity = new City();
        newCity.setDescription("StaffProfessionalDoaImplTest Description-*-");
        newCity.setDistrict(district);
        city = cityDao.save(newCity);
        assertNotNull(" City saved to db " + city);
        
        // Instantiate a staffCategory object.
        StaffCategory newStaffCategory = new StaffCategory();
        newStaffCategory.setAcademic(true);
        newStaffCategory.setDescription("StaffProfessionalDoaImplTest Description-*-");
        staffCategory = staffCategoryDao.save(newStaffCategory);
        assertNotNull(" StaffCategory saved to db " + staffCategory);
        
        // Instantiate a staff object.
        Staff newStaff = new Staff();
        newStaff.setStaffCategory(staffCategory);
        newStaff.setRegistrationNo("StaffProfessionalDoaImplTest ID-*-");
        newStaff.setDateOfHire(new Date());
        newStaff.setFullName("StaffProfessionalDoaImplTest FullName");
        newStaff.setLastName("StaffProfessionalDoaImplTest Name");
        newStaff.setNationalID("StaffProfessionalDoaImplTest ID-*-");
        newStaff.setDateOfBirth(new Date());
        newStaff.setAddress("StaffProfessionalDoaImplTest Address");
        newStaff.setGender('M');
        newStaff.setCityId(city.getCityId());
        staff = staffDao.save(newStaff);
        assertNotNull(" Staff saved to db " + staff);
        
        // Instantiate a ProfessionalQualification object.
        ProfessionalQualification newProfessionalQualification = new ProfessionalQualification();
        newProfessionalQualification.setDescription("StaffProfessionalDoaImplTest Description-*-");
        professionalQualification = professionalQualificationDao.save(newProfessionalQualification);
        assertNotNull(" ProfessionalQualification saved to db " + professionalQualification);
        
        // Instantiate a SubjectTeacher
        staffProfessional = new StaffProfessional();
        staffProfessional.setStaff(staff);
        staffProfessional.setProfessionalQualification(professionalQualification);
        staffProfessional.setDescription("StaffProfessionalDoaImplTest Descriptioin-*-");
    }
    
    /**
     * Test method to check if a StaffProfessional object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testSaveStaffProfessional() throws AkuraAppException {
    
        try {
            
            StaffProfessional newStaffProfessional = staffProfessionalDoa.save(staffProfessional);
            assertNotNull("New StaffProfessional should not be null. " + newStaffProfessional);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a StaffProfessional object was successfully updated to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testUpdateStaffProfessional() throws AkuraAppException {
    
        StaffProfessional newStaffProfessional = null;
        try {
            newStaffProfessional = staffProfessionalDoa.save(staffProfessional);
            assertNotNull("New StaffProfessional should not be null " + newStaffProfessional);
            
            String updatedDescription = "Updated Description";
            
            newStaffProfessional.setDescription(updatedDescription);
            staffProfessionalDoa.update(newStaffProfessional);
            StaffProfessional updatedStaffProfessional =
                    (StaffProfessional) staffProfessionalDoa.findById(StaffProfessional.class,
                            newStaffProfessional.getStaffProfessionalId());
            assertEquals(updatedDescription, updatedStaffProfessional.getDescription());
            
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
            
            List<StaffProfessional> staffProfessionalList = new ArrayList<StaffProfessional>();
            staffProfessionalList.add(staffProfessional);
            
            staffProfessionalDoa.saveOrUpdateAll(staffProfessionalList);
            List<StaffProfessional> newStaffProfessionalList = staffProfessionalDoa.findAll(StaffProfessional.class);
            assertNotNull("StaffProfessional list should not be null ", newStaffProfessionalList);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check find the StaffProfessional records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindStaffProfessionalById() throws AkuraAppException {
    
        try {
            
            StaffProfessional newStaffProfessional = staffProfessionalDoa.save(staffProfessional);
            assertNotNull("New StaffProfessional should not be null " + newStaffProfessional);
            
            StaffProfessional findStaffProfessional =
                    (StaffProfessional) staffProfessionalDoa.findById(StaffProfessional.class,
                            newStaffProfessional.getStaffProfessionalId());
            assertNotNull("find StaffProfessional should not be null ", findStaffProfessional);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check load all the StaffProfessional records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindAllStaffProfessional() throws AkuraAppException {
    
        try {
            
            StaffProfessional newStaffProfessional = staffProfessionalDoa.save(staffProfessional);
            assertNotNull("New StaffProfessional should not be null " + newStaffProfessional);
            
            List<StaffProfessional> staffProfessionalList = staffProfessionalDoa.findAll(StaffProfessional.class);
            assertNotNull("StaffProfessional list should not be null ", staffProfessionalList);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * test case to test getStaffProfessionalListForStaff.
     * 
     * @throws AkuraAppException - AkuraAppException.
     */
    @Test
    public void testGetStaffProfessionalListForStaff() throws AkuraAppException {
    
        StaffProfessional newStaffProfessional = staffProfessionalDoa.save(staffProfessional);
        assertNotNull("New StaffProfessional should not be null " + newStaffProfessional);
        
        List<StaffProfessional> staffProfessionalList =
                staffProfessionalDoa.getStaffProfessionalListForStaff(newStaffProfessional.getStaff().getStaffId());
        
        assertTrue(staffProfessionalList.size() > 0);
        
    }
    
    /**
     * Tear down method for each test case.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @After
    public void tearDown() throws AkuraAppException {
    
        try {
            staffProfessionalDoa.delete(staffProfessional);
            
            staffDao.delete(staff);
            staffCategoryDao.delete(staffCategory);
            
            cityDao.delete(city);
            districtDao.delete(district);
            provinceDao.delete(province);
            
            professionalQualificationDao.delete(professionalQualification);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
        
    }
}
