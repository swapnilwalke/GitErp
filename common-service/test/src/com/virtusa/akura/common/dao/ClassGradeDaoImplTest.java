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

import com.virtusa.akura.common.BaseCommonTest;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.SchoolClass;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This test class, tests all the persistence related functionality for the ClassGrade domain object.
 *
 * @author Virtusa Corporation
 */
public class ClassGradeDaoImplTest extends BaseCommonTest {

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
    public void setUp() throws Exception {

        // instantiates SchoolClass object.
        classInstace = new SchoolClass();
        classInstace.setDescription("NewClass");
        classInstace.setModifiedTime(new Date());
        SchoolClass newClassInstace = schoolClassDao.save(classInstace);
        assertNotNull(newClassInstace);

        grade = new Grade();
        grade.setDescription("Grade-15");
        Grade newGrade = gradeDao.save(grade);
        assertNotNull(newGrade);

        classGrade = new ClassGrade();
        classGrade.setSchoolClass(newClassInstace);
        classGrade.setGrade(newGrade);
        classGrade.setDescription(grade.getDescription() + classInstace.getDescription());
    }

    /**
     * Test method for
     * {@link com.virtusa.sms.school.dao.ClassGradeDaoImpl#findClassGradeListByGrade(Grade grade)}.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindClassGradeListByGrade() throws AkuraAppException {

        ClassGrade newClassGrade = classGradeDao.save(classGrade);
        assertNotNull(newClassGrade);
        List<ClassGrade> classGradeList = classGradeDao.findClassGradeListByGrade(grade);
        assertTrue(classGradeList.size() > 0);
    }

    /**
     * Test method for
     * {@link com.virtusa.akura.common.dao.GradeSubjectDaoImpl#
     * findClassGradeByGradeIdAndClassId(int gradeId, int classId)}
     * .
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testFindClassGradeByGradeIdAndClassId() throws AkuraAppException {

        ClassGrade newClassGrade = classGradeDao.save(classGrade);
        assertNotNull(newClassGrade);
        List<ClassGrade> classGradeList =
                classGradeDao.findClassGradeByGradeIdAndClassId(grade.getGradeId(), classInstace.getClassId());
        assertTrue(classGradeList.size() > 0);
    }

    /**
     * Test method for {@link com.virtusa.sms.api.dao.BaseDaoImpl#save(com.virtusa.akura.api.dto.BaseDomain)}.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testSave() throws AkuraAppException {

        ClassGrade newClassGrade = classGradeDao.save(classGrade);
        assertNotNull(newClassGrade);
    }

    /**
     * Test method for {@link com.virtusa.sms.api.dao.BaseDaoImpl#update(com.virtusa.akura.api.dto.BaseDomain)}.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testUpdate() throws AkuraAppException {

        ClassGrade newClassGrade = classGradeDao.save(classGrade);
        assertNotNull(newClassGrade);
        newClassGrade.setDescription("ClassGrade - 16");
        classGradeDao.update(newClassGrade);
        ClassGrade findClassGrade =
                (ClassGrade) classGradeDao.findById(ClassGrade.class, newClassGrade.getClassGradeId());
        assertEquals(newClassGrade.getDescription(), findClassGrade.getDescription());
    }

    /**
     * @throws AkuraAppException - the exception that occurred.
     */
    @After
    public void tearDown() throws AkuraAppException {

        classGradeDao.delete(classGrade);
        schoolClassDao.delete(classInstace);
        gradeDao.delete(grade);
    }
}
