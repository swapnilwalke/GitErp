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

import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.ParticipantCategory;
import com.virtusa.akura.api.dto.SchoolClass;
import com.virtusa.akura.api.dto.SpecialEvents;
import com.virtusa.akura.api.dto.SpecialEventsParticipation;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseCommonTest;

/**
 * This test class, tests all the persistence related functionality for the SpecialEvents domain object.
 * 
 * @author adesaram
 */
public class SpecialEventsParticipationDaoImplTest extends BaseCommonTest {
    
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
     * This instance will be dependency injected by type.
     */
    @Autowired
    private SpecialEventsParticipationDao specialEventsParticipationDao;
    
    /**
     * Represents an instance of participantCategory.
     */
    private ParticipantCategory participantCategory;
    
    /**
     * Represents an instance of SpecialEvents.
     */
    private SpecialEvents specialEvents;
    
    /**
     * Represents an instance of SpecialEvents.
     */
    private SpecialEvents newSpecialEvents;
    
    /**
     * Represents an instance of SpecialEventsParticipation.
     */
    private SpecialEventsParticipation specialEventsParticipation;
    
    /**
     * Represents an instance of SchoolClass.
     */
    private SchoolClass classInstace;
    
    /**
     * Represents an instance of Grade.
     */
    private Grade grade;
    
    /**
     * Represents an instance of ClassGrade.
     */
    private ClassGrade classGrade;
    
    /**
     * Represents an instance of SchoolClassDao.
     */
    @Autowired
    private SchoolClassDao schoolClassDao;
    
    /**
     * Represents an instance of GradeDao.
     */
    @Autowired
    private GradeDao gradeDao;
    
    /**
     * Represents an instance of ClassGradeDao.
     */
    @Autowired
    private ClassGradeDao classGradeDao;
    
    /**
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws Exception {
    
        classInstace = new SchoolClass();
        classInstace.setDescription("NewClass");
        classInstace.setModifiedTime(new Date());
        SchoolClass newClassInstace = schoolClassDao.save(classInstace);
        assertNotNull(newClassInstace);
        
        grade = new Grade();
        grade.setDescription("newGrade");
        Grade newGrade = gradeDao.save(grade);
        assertNotNull(newGrade);
        
        classGrade = new ClassGrade();
        classGrade.setSchoolClass(newClassInstace);
        classGrade.setGrade(newGrade);
        classGrade.setDescription(grade.getDescription() + classInstace.getDescription());
        ClassGrade newClassGrade = classGradeDao.save(classGrade);
        assertNotNull(newClassGrade);
        
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
        newSpecialEvents = specialEventsDao.save(specialEvents);
        assertNotNull(newSpecialEvents);
        
        specialEventsParticipation = new SpecialEventsParticipation();
        specialEventsParticipation.setClassGrade(newClassGrade);
        specialEventsParticipation.setModifiedTime(new Date());
        specialEventsParticipation.setSpecialEvents(newSpecialEvents);
        
    }
    
    /**
     * Test method for save SpecialEventsParticipation domain object.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testSave() throws AkuraAppException {
    
        try {
            SpecialEventsParticipation newSpecialEventsParticipation =
                    specialEventsParticipationDao.save(specialEventsParticipation);
            assertNotNull("SpecialEventsParticipation Type object should not null ", newSpecialEventsParticipation);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for find by ID SpecialEventsParticipation domain object.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testFindById() throws AkuraAppException {
    
        try {
            SpecialEventsParticipation newSpecialEventsParticipation =
                    specialEventsParticipationDao.save(specialEventsParticipation);
            assertNotNull("SpecialEventsParticipation Type object should not null ", newSpecialEventsParticipation);
            
            SpecialEventsParticipation findSpecialEventsParticipation =
                    (SpecialEventsParticipation) specialEventsParticipationDao.findById(
                            SpecialEventsParticipation.class,
                            newSpecialEventsParticipation.getSpecialEventsParticipationId());
            assertNotNull("SpecialEventsParticipation Type object should not null ", findSpecialEventsParticipation);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for find all SpecialEventsParticipation domain object.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testFindAll() throws AkuraAppException {
    
        try {
            SpecialEventsParticipation newSpecialEventsParticipation =
                    specialEventsParticipationDao.save(specialEventsParticipation);
            assertNotNull("SpecialEventsParticipation Type object should not null ", newSpecialEventsParticipation);
            List<SpecialEventsParticipation> specialEventsParticipationList =
                    specialEventsParticipationDao.findAll(SpecialEventsParticipation.class);
            assertTrue(specialEventsParticipationList.size() > 0);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for getSpecialEventParticipationListById SpecialEventsParticipation domain object.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetSpecialEventParticipationListById() throws AkuraAppException {
    
        try {
            SpecialEventsParticipation newSpecialEventsParticipation =
                    specialEventsParticipationDao.save(specialEventsParticipation);
            assertNotNull("SpecialEventsParticipation Type object should not null ", newSpecialEventsParticipation);
            List<SpecialEventsParticipation> specialEventsParticipationList =
                    specialEventsParticipationDao.getSpecialEventParticipationListById(newSpecialEvents
                            .getSpecialEventsId());
            assertTrue(specialEventsParticipationList.size() > 0);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for GetParticipantListBySpecialEvent method in SpecialEventsParticipationDaoImpl.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testGetParticipantListBySpecialEvent() throws AkuraAppException {
    
        SpecialEventsParticipation newSpecialEventsParticipation =
                specialEventsParticipationDao.save(specialEventsParticipation);
        
        List<SpecialEventsParticipation> list = specialEventsParticipationDao
                .getParticipantListBySpecialEvent(newSpecialEventsParticipation.getSpecialEvents());
        
        assertTrue(list.size() > 0);
    }
    
    /**
     * Tear down method for each test case.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @After
    public final void teardown() throws AkuraAppException {
    
        try {
            specialEventsParticipationDao.delete(specialEventsParticipation);
            specialEventsDao.delete(specialEvents);
            participantCategoryDao.delete(participantCategory);
            classGradeDao.delete(classGrade);
            schoolClassDao.delete(classInstace);
            gradeDao.delete(grade);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
}
