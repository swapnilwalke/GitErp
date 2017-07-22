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
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.auditing.BaseAuditingTest;
import com.virtusa.akura.common.dao.UserLoginDao;

/**
 * This test class tests all the functionalities of the BusinessAuditDaoImpl class.
 * 
 * @author Virtusa Corporation
 */

public class BusinessAuditDaoImplTest extends BaseAuditingTest {
    
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
        businessAudit.setFromDate("");
        businessAudit.setToDate("");
        
    }
    
    /**
     * Test method to check if a Add BusinessAudit object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testAddBusinessAudit() throws AkuraAppException {
    
        try {
            
            BusinessAudit newBusinessAudit =
                    this.businessAuditDao.save(businessAudit);
            assertNotNull("New BusinessAudit object can not be null" + newBusinessAudit);
        } catch (AkuraAppException e) {
            
            e.printStackTrace();
        }
        
    }
    
    /**
     * Test method to check update and find by id BusinessAudit object.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testUpdateBusinessAudit() throws AkuraAppException {
    
        try {
            
            BusinessAudit newBusinessAudit =
                    this.businessAuditDao.save(businessAudit);
            assertNotNull("New BusinessAudit object can not be null" + newBusinessAudit);
            newBusinessAudit.setBusinessFunction("newbusinessFunctionValue");
            businessAuditDao.update(newBusinessAudit);
            BusinessAudit updatedBusinessAudit =
                    (BusinessAudit) this.businessAuditDao.findById(BusinessAudit.class,
                            newBusinessAudit.getBusinessAuditId());
            assertEquals(updatedBusinessAudit.getBusinessFunction(), newBusinessAudit.getBusinessFunction());
            
        } catch (AkuraAppException e) {
            
            e.printStackTrace();
        }
        
    }
    
    /**
     * Test method to check load all the BusinessAudit records from the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testViewAllStudentPrefect() throws AkuraAppException {
    
        try {
            BusinessAudit newBusinessAudit =
                    this.businessAuditDao.save(businessAudit);
            assertNotNull("New BusinessAudit object can not be null" + newBusinessAudit);
            
            List<BusinessAudit> list = this.businessAuditDao.findAll(BusinessAudit.class);
            assertTrue(list.size()>0);
            
        } catch (AkuraAppException e) {
            
            e.printStackTrace();
        }
        
    }
    
    /**
     * Test method for searchAudit.
     * 
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @Test
    public final void testSearchAudit() throws AkuraAppException {
    
        BusinessAudit newBusinessAudit =
                this.businessAuditDao.save(businessAudit);
        assertNotNull("New BusinessAudit object can not be null" + newBusinessAudit);
        
        List<BusinessAudit> list = businessAuditDao.searchAudit(newBusinessAudit);
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
            businessAuditDao.delete(businessAudit);
            userLoginDao.delete(userLogin);
            
        } catch (AkuraAppException e) {
            
            e.printStackTrace();
        }
    }
    
}
