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

import com.virtusa.akura.api.dto.Donation;
import com.virtusa.akura.api.dto.DonationType;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.dao.DonationTypeDao;
import com.virtusa.akura.student.BaseStudentTest;

/**
 * This test class, tests all the persistence related functionality for the donation domain object.
 * 
 * @author Virtusa Corporation
 */
public class DonationDaoImplTest extends BaseStudentTest {
    
    /** Holds DonationTypeDao instance. */
    @Autowired
    private DonationTypeDao donationTypeDao;
    
    /** Holds DonationDao instance. */
    @Autowired
    private DonationDao donationDao;
    
    /** Instance of a donation entity. */
    private Donation donation;
    
    /** Instance of a donation type entity. */
    private DonationType donationType;
    
    /**
     * Instantiate the setup method.
     * 
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public void setUp() throws Exception {
    
        // Instantiates a donation type object.
        DonationType newType = new DonationType();
        newType.setDescription("Description");
        newType.setModifiedTime(new Date());
        
        donationType = donationTypeDao.save(newType);
        assertNotNull("New Donation Type should not be null. " + donationType);
        
        // Instantiates a donation type object.
        donation = new Donation();
        donation.setPurpose("test");
        donation.setAmount("");
        donation.setDate(new Date());
        donation.setDonationType(donationType);
        donation.setModifiedTime(new Date());
    }
    
    /**
     * Test method to check if a donation object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testSaveDonation() throws AkuraAppException {
    
        try {
            
            Donation newDonation = donationDao.save(donation);
            assertNotNull("donation should not be null " + newDonation);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        } 
    }
    
    /**
     * Test method to check if a donation object was successfully updated to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testUpdateDonation() throws AkuraAppException {
    
        try {
            
            String updatedPurpose = "donation";
            Donation newDonation = donationDao.save(donation);
            assertNotNull("new donation should not be null " + newDonation);
            
            newDonation.setPurpose(updatedPurpose);
            donationDao.update(newDonation);
            Donation updatedDonation = (Donation) donationDao.findById(Donation.class, newDonation.getDonationId());
            assertEquals(updatedPurpose, updatedDonation.getPurpose());
            
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
    public void testSaveOrUpdateAllDonations() throws AkuraAppException {
    
        try {
            List<Donation> donationList = new ArrayList<Donation>();
            
            donationList.add(donation);
            
            donationDao.saveOrUpdateAll(donationList);
            List<Donation> newDonationList = donationDao.findAll(Donation.class);
            assertTrue("new donation list should not be null ", newDonationList.size() > 0);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check find the donation records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindById() throws AkuraAppException {
    
        try {
            
            Donation newDonation = donationDao.save(donation);
            assertNotNull("new donation should not be null " + newDonation);
            
            Donation findDonation = (Donation) donationDao.findById(Donation.class, donation.getDonationId());
            assertNotNull("find donation should not be null ", findDonation);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check load all the donation records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindAll() throws AkuraAppException {
    
        try {
            
            Donation newDonation = donationDao.save(donation);
            assertNotNull("new donation should not be null " + newDonation);
            
            List<Donation> donationList = donationDao.findAll(Donation.class);
            assertNotNull(donationList.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Tear down method for each test class.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @After
    public void tearDown() throws AkuraAppException {
    
        try {
            donationDao.delete(donation);
            donationTypeDao.delete(donationType);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
        
    }
    
}
