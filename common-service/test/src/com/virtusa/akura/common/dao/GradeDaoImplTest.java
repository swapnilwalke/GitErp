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

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.common.BaseCommonTest;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.exception.AkuraAppException;


/**
 * This test class, tests all the persistence related functionality for the
 * Grade domain object.
 *
 * @author Virtusa Corporation
 */
public class GradeDaoImplTest extends BaseCommonTest {
    /**
     * Represents an instance of Grade.
     */
    private Grade grade;

    /**
     * Represents an instance of GradeDao.
     */
    @Autowired
    private GradeDao gradeDao;

    /**
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public void setUp() throws Exception {
        grade = new Grade();
        grade.setDescription("Grade-15");
    }

    /**
     * Test method for {@link com.virtusa.akura.common.dao.GradeDaoImpl#findGradeByGradeName(java.lang.String)}.
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindGradeByGradeName() throws AkuraAppException {
        Grade newGrade = gradeDao.save(grade);
        assertNotNull(newGrade);
        List<Grade> gradeList = gradeDao.findGradeByGradeName(newGrade.getDescription());
        assertTrue(gradeList.size() > 0);
    }

    /**
     * Test method for {@link com.virtusa.sms.api.dao.BaseDaoImpl#save(com.virtusa.akura.api.dto.BaseDomain)}.
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testSave() throws AkuraAppException {
        Grade newGrade = gradeDao.save(grade);
        assertNotNull(newGrade);
    }

    /**
     * Test method for {@link com.virtusa.sms.api.dao.BaseDaoImpl#update(com.virtusa.akura.api.dto.BaseDomain)}.
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testUpdate() throws AkuraAppException {
        Grade newGrade = gradeDao.save(grade);
        assertNotNull(newGrade);
        newGrade.setDescription("newClassIns");
        gradeDao.update(newGrade);
        Grade findGrade = (Grade) gradeDao.findById(Grade.class, newGrade.getGradeId());
        assertNotNull(findGrade);
    }

    /**
     * Test method for {@link com.virtusa.sms.api.dao.BaseDaoImpl#findAll(java.lang.Class)}.
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindAll() throws AkuraAppException {
        Grade newGrade = gradeDao.save(grade);
        assertNotNull(newGrade);
        List<Grade> gradeList = gradeDao.findAll(Grade.class);
        assertTrue(gradeList.size() > 0);
    }

    /**
     * Tear down method for each test case.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @After
    public void tearDown() throws AkuraAppException {
        gradeDao.delete(grade);
    }
}
