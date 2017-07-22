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

package com.virtusa.akura.auditing.service;

import java.util.ArrayList;
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
import com.virtusa.akura.auditing.dao.BusinessAuditDao;
import com.virtusa.akura.auditing.dao.BusinessAuditDeatilsDao;
import com.virtusa.akura.common.dao.UserLoginDao;

/**
 * This test class tests all the functionalities of the BusinessAuditDetailsDaoImpl class.
 * 
 * @author Virtusa Corporation
 */

public class AuditManagerServiceImplTest extends BaseAuditingTest {
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private AuditManagerService auditManagerService;
    
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
        
        AuditEventType auditEventType = new AuditEventType();
        auditEventType.setAuditEventTypeId(1);
        
        businessAudit = new BusinessAudit();
        businessAudit.setBusinessFunction("businessFunctionValue");
        businessAudit.setDate(new Date());
        businessAudit.setEventType(auditEventType);
        businessAudit.setModule("moduleValue");
        businessAudit.setUserLogin(userLogin);
        businessAudit.setModifiedTime(new Date());
        
    }
    
    /**
     * Test method to check if a Add BusinessAudit object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testCreateAudit() throws AkuraAppException {
    
        try {
            
            BusinessAudit newBusinessAudit = auditManagerService.createAudit(businessAudit);
            assertNotNull("New BusinessAudit object can not be null" + newBusinessAudit);
        } catch (AkuraAppException e) {
            
            e.printStackTrace();
        }
        
    }
    
    /**
     * Test method to check if a Add BusinessAuditDetails object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testCreateAuditDetail() throws AkuraAppException {
    
        try {
            
            auditManagerService.createAudit(businessAudit);
            assertNotNull(businessAudit);
            businessAuditDetails = new BusinessAuditDetails();
            businessAuditDetails.setAttributeName("attributeNameValue");
            businessAuditDetails.setAuditDescription("auditDescriptionRef");
            businessAuditDetails.setBusinessAudit(businessAudit);
            businessAuditDetails.setModifiedTime(new Date());
            
            BusinessAuditDetails newBusinessAuditDetails = auditManagerService.createAuditDetail(businessAuditDetails);
            assertNotNull("New newBusinessAuditDetails object can not be null" + newBusinessAuditDetails);
        } catch (AkuraAppException e) {
            
            e.printStackTrace();
        }
        
    }
    
    /**
     * Test methods for  saveAllAuditDetail and getAuditDetailsList.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testSaveAllAuditDetail() throws AkuraAppException {
    
        try {
            
            auditManagerService.createAudit(businessAudit);
            assertNotNull(businessAudit);
            businessAuditDetails = new BusinessAuditDetails();
            businessAuditDetails.setAttributeName("attributeNameValue");
            businessAuditDetails.setAuditDescription("auditDescriptionRef");
            businessAuditDetails.setBusinessAudit(businessAudit);
            businessAuditDetails.setModifiedTime(new Date());
            
            List<BusinessAuditDetails> businessAuditDetailList = new ArrayList<BusinessAuditDetails>();
            businessAuditDetailList.add(businessAuditDetails);
            auditManagerService.saveAllAuditDetail(businessAuditDetailList);
            
            List<BusinessAuditDetails> list =
                    auditManagerService.getAuditDetailsList(businessAudit.getBusinessAuditId());
            assertTrue(list.size() > 0);
            
        } catch (AkuraAppException e) {
            
            e.printStackTrace();
        }
        
    }
    
    /**
     * Tear down method for each test case.
     * 
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @After
    public final void teardown() throws AkuraAppException {
    
        try {
            if (businessAuditDetails != null) {
                businessAuditDeatilsDao.delete(businessAuditDetails);
            }
            businessAuditDao.delete(businessAudit);
            userLoginDao.delete(userLogin);
            
        } catch (AkuraAppException e) {
            
            e.printStackTrace();
        }
    }
    
}
