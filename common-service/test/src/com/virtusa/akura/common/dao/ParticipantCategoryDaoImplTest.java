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

package com.virtusa.akura.common.dao;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.api.dto.ParticipantCategory;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseCommonTest;

/**
 * This test class, tests all the persistence related functionality for the ParticipantCategory domain object.
 * 
 * @author adesaram
 */
public class ParticipantCategoryDaoImplTest extends BaseCommonTest {
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private ParticipantCategoryDao participantCategoryDao;
    
    /**
     * Represents an instance of participantCategory.
     */
    private ParticipantCategory participantCategory;
    
    /**
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws Exception {
    
        participantCategory = new ParticipantCategory();
        participantCategory.setDescription("descriptionRef");
        participantCategory.setModifiedTime(new Date());
        
    }
    
    /**
     * Test method for save ParticipantCategory domain object.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testSave() throws AkuraAppException {
    
        try {
            ParticipantCategory newParticipantCategory = participantCategoryDao.save(participantCategory);
            assertNotNull("ParticipantCategory Type object should not null ", newParticipantCategory);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for update and find by ID ParticipantCategory domain object.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testUpdate() throws AkuraAppException {
    
        try {
            ParticipantCategory newParticipantCategory = participantCategoryDao.save(participantCategory);
            assertNotNull(newParticipantCategory);
            newParticipantCategory.setDescription("KadawathaDDD");
            participantCategoryDao.update(newParticipantCategory);
            ParticipantCategory updatedParticipantCategory =
                    (ParticipantCategory) participantCategoryDao.findById(ParticipantCategory.class,
                            newParticipantCategory.getParticipantCategoryId());
            assertEquals(newParticipantCategory.getDescription(), updatedParticipantCategory.getDescription());
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for find all ParticipantCategory domain object.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testFindAll() throws AkuraAppException {
    
        try {
            ParticipantCategory newParticipantCategory = participantCategoryDao.save(participantCategory);
            assertNotNull(newParticipantCategory);
            List<ParticipantCategory> participantCategoryList =
                    participantCategoryDao.findAll(ParticipantCategory.class);
            assertTrue(participantCategoryList.size() > 0);
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
    public final void teardown() throws AkuraAppException {
    
        try {
            participantCategoryDao.delete(participantCategory);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
}
