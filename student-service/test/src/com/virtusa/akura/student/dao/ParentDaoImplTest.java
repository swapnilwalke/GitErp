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

package com.virtusa.akura.student.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.Parent;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.Religion;
import com.virtusa.akura.api.dto.WorkingSegment;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.dao.CityDao;
import com.virtusa.akura.common.dao.DistrictDao;
import com.virtusa.akura.common.dao.ProvinceDao;
import com.virtusa.akura.common.dao.ReligionDao;
import com.virtusa.akura.common.dao.WorkingSegmentDao;
import com.virtusa.akura.student.BaseStudentTest;

/**
 * This test class, tests all the persistence related functionality for the parent domain object.
 * 
 * @author Virtusa Corporation
 */
public class ParentDaoImplTest extends BaseStudentTest {
    
    /** Holds ParentDao instance. */
    @Autowired
    private ParentDao parentDao;
    
    /** Holds Province instance. */
    @Autowired
    private ProvinceDao provinceDao;
    
    /** Holds District instance. */
    @Autowired
    private DistrictDao districtDao;
    
    /** Holds City instance. */
    @Autowired
    private CityDao cityDao;
    
    /** Holds WorkingSegmentDao instance. */
    @Autowired
    private WorkingSegmentDao workingSegmentDao;
    
    /** Holds Religion instance. */
    @Autowired
    private ReligionDao religionDao;
    
    /** Holds Parent instance. */
    private Parent parent;
    
    /** Holds workingSegment instance. */
    private WorkingSegment workingSegment;
    
    /** Holds Religion instance. */
    private Religion religion;
    
    /** Holds province instance. */
    private Province province;
    
    /** Holds city instance. */
    private City city;
    
    /** Holds district instance. */
    private District district;
    
    /**
     * Instantiate the setup method.
     * 
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public void setUp() throws Exception {

        String randomString=String.valueOf(Math.round(Math.random()*1000));
        
        // Instantiate a Province object.
        Province newProvince = new Province();
        newProvince.setDescription("Western11111"+randomString);
        newProvince.setModifiedTime(new Date());
        
        // Instantiates a District object.
        District newDistrict = new District();
        newDistrict.setDescription("Gampaha1111"+randomString);
        newDistrict.setModifiedTime(new Date());
        
        // Instantiates a City object.
        City newCity = new City();
        newCity.setDescription("Dematagoda1111"+randomString);
        newCity.setModifiedTime(new Date());
        
        // Instantiates a Religion object.
        Religion newReligion = new Religion();
        newReligion.setDescription("Religion1111"+randomString);
        newReligion.setModifiedTime(new Date());
        
        // Instantiates a newWorkingSegment object.
        WorkingSegment newWorkingSegment = new WorkingSegment();
        newWorkingSegment.setDescription("Engineer1111"+randomString);
        newWorkingSegment.setModifiedTime(new Date());
        
        province = provinceDao.save(newProvince);
        assertNotNull(" province saved to db " + province);
        
        newDistrict.setProvince(newProvince);
        district = districtDao.save(newDistrict);
        assertNotNull(" district saved to db " + district);
        
        newCity.setDistrict(newDistrict);
        city = cityDao.save(newCity);
        assertNotNull(" district saved to db " + city);
        
        workingSegment = workingSegmentDao.save(newWorkingSegment);
        assertNotNull(" working segment saved to db " + workingSegment);
        
        religion = religionDao.save(newReligion);
        assertNotNull(" religion saved to db " + religion);
        
        // Instantiates a parent object.
        parent = new Parent();
        parent.setDesignation("designation");
        parent.setTeacher(false);
        parent.setPastPupil(false);
        parent.setFullName("fullName");
        parent.setNameWithInitials("nameWithInitials");
        parent.setLastName("lastName");
        parent.setNationalIdNo(randomString+"111050V");
        parent.setAddress("address");
        parent.setMobileNo("0123456789");
        parent.setResidenceNo("0123456789");
        parent.setOfficeNo("0123456789");
        parent.setRelationship("Mother");
        parent.setEmail("emal");
        parent.setModifiedTime(new Date());
        parent.setCityId(city.getCityId());
        parent.setTempCityId(city.getCityId());
        parent.setOfficeCityId(city.getCityId());
        parent.setWorkingSegmentId(workingSegment.getWorkingSegmentId());
        parent.setReligionId(religion.getReligionId());
        
    }
    
    /**
     * Test method to check if a parent object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testSaveParent() throws AkuraAppException {

        try {
            
            Parent newParent = parentDao.save(parent);
            assertNotNull("New Parent should not be null. " + newParent);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        } finally {
            parentDao.delete(parent);
        }
    }
    
    /**
     * Test method to check if a parent object was successfully updated to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testUpdateParent() throws AkuraAppException {

        try {
            
            String updatedName = "updatedName";
            Parent newParent = parentDao.save(parent);
            assertNotNull("New Parent should not be null " + newParent);
            
            newParent.setFullName(updatedName);
            parentDao.update(newParent);
            Parent updatedParent = (Parent) parentDao.findById(Parent.class, newParent.getParentId());
            assertEquals(updatedName, updatedParent.getFullName());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        } finally {
            parentDao.delete(parent);
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
            
            List<Parent> parentList = new ArrayList<Parent>();
            parentList.add(parent);
            
            parentDao.saveOrUpdateAll(parentList);
            List<Parent> newparentList = parentDao.findAll(Parent.class);
            assertNotNull("Parent list should not be null ", newparentList);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        } finally {
            parentDao.delete(parent);
        }
    }
    
    /**
     * Test method to check find the parent records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindParentById() throws AkuraAppException {

        try {
            
            Parent newParent = parentDao.save(parent);
            assertNotNull("New parent should not be null " + newParent);
            
            Parent findParent = (Parent) parentDao.findById(Parent.class, newParent.getParentId());
            assertNotNull("find parent should not be null ", findParent);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        } finally {
            parentDao.delete(parent);
        }
    }
    
    /**
     * Test method to check load all the parent records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindAllParents() throws AkuraAppException {

        try {
            
            Parent newParent = parentDao.save(parent);
            assertNotNull("New Parent should not be null " + newParent);
            
            List<Parent> parentList = parentDao.findAll(Parent.class);
            assertNotNull("Parent list should not be null ", parentList);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        } finally {
            parentDao.delete(parent);
        }
    }
    
    /**
     * Tear down method for each test case.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @After
    public void tearDown() throws AkuraAppException {

        try {                       
            cityDao.delete(city);
            districtDao.delete(district);
            provinceDao.delete(province);
            religionDao.delete(religion);
            workingSegmentDao.delete(workingSegment);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
        
    }
    
}
