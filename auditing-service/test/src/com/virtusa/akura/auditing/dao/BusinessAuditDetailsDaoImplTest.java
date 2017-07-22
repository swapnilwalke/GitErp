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

package com.virtusa.akura.auditing.dao;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.api.dto.AuditEventType;
import com.virtusa.akura.api.dto.BusinessAudit;
import com.virtusa.akura.api.dto.BusinessAuditDetails;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.auditing.BaseAuditingTest;
import com.virtusa.akura.common.dao.UserLoginDao;

/**
 * This test class tests all the functionalities of the BusinessAuditDetailsDaoImpl class.
 * 
 * @author Virtusa Corporation
 */

public class BusinessAuditDetailsDaoImplTest extends BaseAuditingTest {
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private BusinessAuditDeatilsDao businessAuditDeatilsDao;
    
    /** Holds businessAuditDetails instance. */
    private BusinessAuditDetails businessAuditDetails;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private BusinessAuditDao businessAuditDao;
    
    /** Holds businessAudit instance. */
    private BusinessAudit businessAudit;
    
    /**
     * Represents an instance of UserLoginDao.
     */
    @Autowired
    private UserLoginDao userLoginDao;
   
    /**
     * Represents an instance of UserLogin.
     */
    private UserLogin userLogin;
    
   
    
    /**
     * Instantiate the setup method.
     * 
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public void setUp() throws Exception {
        
        AuditEventType auditEventType = new AuditEventType();
        auditEventType.setAuditEventTypeId(1);
    
        userLogin = new UserLogin();
        userLogin.setUserRoleId(1);
        userLogin.setFirstName("firstName");
        userLogin.setLastName("lastName");
        userLogin.setLoginAttempts(0);
        userLogin.setEmail("userLogin@email.com");
        userLogin.setUserIdentificationNo("A00006");
        userLogin.setUsername("newUserName");
        userLogin.setPassword("password");
        userLogin.setUserLevel("2");
        userLogin.setStatus(true);
        userLogin.setModifiedTime(new Date());
        userLoginDao.save(userLogin);
        assertNotNull(userLogin);
        
        businessAudit = new BusinessAudit();
        businessAudit.setBusinessFunction("businessFunctionValue");
        businessAudit.setDate(new Date());
        businessAudit.setEventType(auditEventType);
        businessAudit.setModule("moduleValue");
        businessAudit.setUserLogin(userLogin);
        businessAudit.setModifiedTime(new Date());
        businessAuditDao.save(businessAudit);
        assertNotNull(businessAudit);
        
        businessAuditDetails =new BusinessAuditDetails();
        businessAuditDetails.setAttributeName("attributeNameValue");
        businessAuditDetails.setAuditDescription("auditDescriptionRef");
        businessAuditDetails.setBusinessAudit(businessAudit);
        businessAuditDetails.setModifiedTime(new Date());
        
    }
    
    /**
     * Test method to check if a Add BusinessAuditDetails object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testAddBusinessAuditDetails() throws AkuraAppException {
    
        try {
            
            BusinessAuditDetails newBusinessAuditDetails =
                    this.businessAuditDeatilsDao.save(businessAuditDetails);
            assertNotNull("New BusinessAuditDetails object can not be null" + newBusinessAuditDetails);
        } catch (AkuraAppException e) {
            
            e.printStackTrace();
        }
        
    }
    
    /**
     * Test method to check update and find by id BusinessAuditDetails object.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testUpdateBusinessAuditDetails() throws AkuraAppException {
    
        try {
            
            BusinessAuditDetails newBusinessAuditDetails =
                    this.businessAuditDeatilsDao.save(businessAuditDetails);
            assertNotNull("New BusinessAuditDetails object can not be null" + newBusinessAuditDetails);
            newBusinessAuditDetails.setAttributeName("aaaaa");
            businessAuditDeatilsDao.update(newBusinessAuditDetails);
            BusinessAuditDetails updatedBusinessAuditDetails =
                    (BusinessAuditDetails) this.businessAuditDeatilsDao.findById(BusinessAuditDetails.class,
                            newBusinessAuditDetails.getBusinessAuditDetailsId());
            assertEquals(updatedBusinessAuditDetails.getAttributeName(), newBusinessAuditDetails.getAttributeName());
            
        } catch (AkuraAppException e) {
            
            e.printStackTrace();
        }
        
    }
    
    /**
     * Test method to check load all the BusinessAuditDetails records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testViewAllStudentPrefect() throws AkuraAppException {
    
        try {
            BusinessAuditDetails newBusinessAuditDetails =
                    this.businessAuditDeatilsDao.save(businessAuditDetails);
            assertNotNull("New BusinessAuditDetails object can not be null" + newBusinessAuditDetails);
            
            List<BusinessAuditDetails> list = this.businessAuditDeatilsDao.findAll(BusinessAuditDetails.class);
            assertTrue(list.size()>0);
            
        } catch (AkuraAppException e) {
            
            e.printStackTrace();
        }
        
    }
    
    /**
     * Test method for getAuditDetailsList.
     * 
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @Test
    public final void testGetAuditDetailsList() throws AkuraAppException {
    
        BusinessAuditDetails newBusinessAuditDetails =
                this.businessAuditDeatilsDao.save(businessAuditDetails);
        assertNotNull("New BusinessAuditDetails object can not be null" + newBusinessAuditDetails);
        
        List<BusinessAuditDetails> list = businessAuditDeatilsDao.
                getAuditDetailsList(newBusinessAuditDetails.getBusinessAudit().getBusinessAuditId());
        assertTrue(list.size()>0);
    }
    
    
    /**
     * Tear down method for each test case.
     * 
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @After
    public final void teardown() throws AkuraAppException {
    
        try {
            businessAuditDeatilsDao.delete(businessAuditDetails);
            businessAuditDao.delete(businessAudit);
            userLoginDao.delete(userLogin);
            
        } catch (AkuraAppException e) {
            
            e.printStackTrace();
        }
    }
    
}
