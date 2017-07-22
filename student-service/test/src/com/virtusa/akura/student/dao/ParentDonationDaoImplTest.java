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
import com.virtusa.akura.api.dto.Donation;
import com.virtusa.akura.api.dto.DonationType;
import com.virtusa.akura.api.dto.Parent;
import com.virtusa.akura.api.dto.ParentDonation;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.Religion;
import com.virtusa.akura.api.dto.WorkingSegment;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.dao.CityDao;
import com.virtusa.akura.common.dao.DistrictDao;
import com.virtusa.akura.common.dao.DonationTypeDao;
import com.virtusa.akura.common.dao.ProvinceDao;
import com.virtusa.akura.common.dao.ReligionDao;
import com.virtusa.akura.common.dao.WorkingSegmentDao;
import com.virtusa.akura.student.BaseStudentTest;

/**
 * This test class, tests all the persistence related functionality for the newParent donation domain object.
 * 
 * @author Virtusa Corporation
 */
public class ParentDonationDaoImplTest extends BaseStudentTest {
    
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
    
    /** Holds DonationType instance. */
    @Autowired
    private DonationTypeDao donationTypeDao;
    
    /** This instance will be dependency injected by type. */
    @Autowired
    private DonationDao donationDao;
    
    /** Holds ParentDonation instance. */
    @Autowired
    private ParentDonationDao parentDonationDao;
    
    /** Holds Parent instance. */
    private Parent parent;
    
    /** Holds ParentDonation instance. */
    private ParentDonation parentDonation;
    
    /** Holds donation instance. */
    private Donation donation;
    
    /** Holds donationType instance. */
    private DonationType donationType;
    
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
        newProvince.setDescription("Western1111"+randomString);
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
        newWorkingSegment.setDescription("Engineer111"+randomString);
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
        
        // Instantiates a newParent object.
        Parent newParent = new Parent();
        newParent.setDesignation("designation");
        newParent.setTeacher(false);
        newParent.setPastPupil(false);
        newParent.setFullName("fullName");
        newParent.setNameWithInitials("nameWithInitials");
        newParent.setLastName("lastName");
        newParent.setNationalIdNo(randomString+"111050V");
        newParent.setAddress("address");
        newParent.setMobileNo("0123456789");
        newParent.setResidenceNo("0123456789");
        newParent.setOfficeNo("0123456789");
        newParent.setRelationship("Mother");
        newParent.setEmail("emal");
        newParent.setModifiedTime(new Date());
        newParent.setCityId(city.getCityId());
        newParent.setTempCityId(city.getCityId());
        newParent.setOfficeCityId(city.getCityId());
        newParent.setWorkingSegmentId(workingSegment.getWorkingSegmentId());
        newParent.setReligionId(religion.getReligionId());
        
        parent = parentDao.save(newParent);
        assertNotNull("New Parent should not be null. " + parent);
        
        // Instantiates a donation type object.
        DonationType newType = new DonationType();
        newType.setDescription("Description");
        newType.setModifiedTime(new Date());
        
        donationType = donationTypeDao.save(newType);
        assertNotNull("New Donation Type should not be null. " + donationType);
        
        // Instantiates a donation type object.
        Donation newDonation = new Donation();
        newDonation.setPurpose("test");
        newDonation.setAmount("");
        newDonation.setDate(new Date());
        newDonation.setDonationType(donationType);
        newDonation.setModifiedTime(new Date());
        
        donation = donationDao.save(newDonation);
        assertNotNull("New Donation should not be null. " + donation);
        
        // Instantiates a parentDonation object.
        parentDonation = new ParentDonation();
        parentDonation.setParent(newParent);
        parentDonation.setDonation(donation);
        parentDonation.setModifiedTime(new Date());
        
    }
    
    /**
     * Test method to check if a newParent object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testSaveParentDonation() throws AkuraAppException {

        try {
            
            ParentDonation newParentDonation = parentDonationDao.save(parentDonation);
            assertNotNull("New Parent daontion should not be null " + newParentDonation);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        } 
    }
    
    /**
     * Test method to check if a newParent donation object was successfully updated to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testUpdateParentDonation() throws AkuraAppException {

        try {
                       
            ParentDonation newParentDonation = parentDonationDao.save(parentDonation);
            assertNotNull("New Parent Donation should not be null " + newParentDonation);            
            newParentDonation.setDonation(donation);
            
            parentDonationDao.update(newParentDonation);
            ParentDonation updatedParentDonation =
                    (ParentDonation) parentDonationDao.findById(ParentDonation.class, newParentDonation
                            .getParentDonationId());
            assertEquals(donation.getDonationId(), updatedParentDonation.getDonation().getDonationId());
            
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
    public void testSaveOrUpdateParentDonations() throws AkuraAppException {

        try {
            
            List<ParentDonation> donationTypeList = new ArrayList<ParentDonation>();
            donationTypeList.add(parentDonation);
            
            parentDonationDao.saveOrUpdateAll(donationTypeList);
            List<ParentDonation> newDonationTypeList = parentDonationDao.findAll(ParentDonation.class);
            assertTrue(newDonationTypeList.size() > 0);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        } 
    }
    
    /**
     * Test method to check find the newParent donation records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindParentDonationById() throws AkuraAppException {

        try {
            
            ParentDonation newParentDonationType = parentDonationDao.save(parentDonation);
            assertNotNull("New newParent daontino should not be null " + newParentDonationType);
            
            ParentDonation findParentDonation =
                    (ParentDonation) parentDonationDao.findById(ParentDonation.class, newParentDonationType
                            .getParentDonationId());
            assertNotNull("find newParent daontion should not be null ", findParentDonation);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        } 
    }
    
    /**
     * Test method to check load all the newParent donation records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindAllParentDonation() throws AkuraAppException {

        try {
            
            ParentDonation newParentDonation = parentDonationDao.save(parentDonation);
            assertNotNull("New Parent Donation should not be null " + newParentDonation);
            
            List<ParentDonation> donationList = parentDonationDao.findAll(ParentDonation.class);
            assertNotNull(donationList.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
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
            
            parentDonationDao.delete(parentDonation);
            
            parentDao.delete(parent);
            cityDao.delete(city);
            districtDao.delete(district);
            provinceDao.delete(province);
            religionDao.delete(religion);
            workingSegmentDao.delete(workingSegment);
            
            donationDao.delete(donation);
            donationTypeDao.delete(donationType);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
        
    }
    
}
