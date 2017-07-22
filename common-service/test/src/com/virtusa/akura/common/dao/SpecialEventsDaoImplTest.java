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
import com.virtusa.akura.api.dto.SpecialEvents;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseCommonTest;

/**
 * This test class, tests all the persistence related functionality for the SpecialEvents domain object.
 * 
 * @author adesaram
 */
public class SpecialEventsDaoImplTest extends BaseCommonTest {
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private ParticipantCategoryDao participantCategoryDao;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private SpecialEventsDao specialEventsDao;
    
    /**
     * Represents an instance of participantCategory.
     */
    private ParticipantCategory participantCategory;
    
    /**
     * Represents an instance of SpecialEvents.
     */
    private SpecialEvents specialEvents;
    
    /**
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws Exception {
    
        participantCategory = new ParticipantCategory();
        participantCategory.setDescription("descriptionRef");
        participantCategory.setModifiedTime(new Date());
        ParticipantCategory newParticipantCategory = participantCategoryDao.save(participantCategory);
        assertNotNull(newParticipantCategory);
        
        specialEvents = new SpecialEvents();
        specialEvents.setDate(new Date());
        specialEvents.setDescription("descriptionRef");
        specialEvents.setModifiedTime(new Date());
        specialEvents.setName("nameRef");
        specialEvents.setParticipantCategory(newParticipantCategory);
    }
    
    /**
     * Test method for save SpecialEvents domain object.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testSave() throws AkuraAppException {
    
        try {
            SpecialEvents newSpecialEvents = specialEventsDao.save(specialEvents);
            assertNotNull("SpecialEvents Type object should not null ", newSpecialEvents);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for update and find by ID SpecialEvents domain object.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testUpdate() throws AkuraAppException {
    
        try {
            SpecialEvents newSpecialEvents = specialEventsDao.save(specialEvents);
            assertNotNull("SpecialEvents Type object should not null ", newSpecialEvents);
            newSpecialEvents.setDescription("KadawathaDDD");
            specialEventsDao.update(newSpecialEvents);
            SpecialEvents updatedSpecialEvents =
                    (SpecialEvents) specialEventsDao.findById(SpecialEvents.class,
                            newSpecialEvents.getSpecialEventsId());
            assertEquals(newSpecialEvents.getDescription(), updatedSpecialEvents.getDescription());
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for find all SpecialEvents domain object.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testFindAll() throws AkuraAppException {
    
        try {
            SpecialEvents newSpecialEvents = specialEventsDao.save(specialEvents);
            assertNotNull("SpecialEvents Type object should not null ", newSpecialEvents);
            List<SpecialEvents> specialEventsList = specialEventsDao.findAll(SpecialEvents.class);
            assertTrue(specialEventsList.size() > 0);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for findById method in SpecialEventDaoImpl.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindById() throws AkuraAppException {
    
        SpecialEvents newSpecialEvents = specialEventsDao.save(specialEvents);
        
        SpecialEvents parsistedObj =
                (SpecialEvents) specialEventsDao.findById(SpecialEvents.class, newSpecialEvents.getSpecialEventsId());
        
        assertNotNull(parsistedObj);
    }
    
    /**
     * Tear down method for each test case.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @After
    public final void teardown() throws AkuraAppException {
    
        try {
            specialEventsDao.delete(specialEvents);
            participantCategoryDao.delete(participantCategory);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
}
