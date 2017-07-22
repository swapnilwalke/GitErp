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
import com.virtusa.akura.api.dto.Exam;
import com.virtusa.akura.api.dto.ExamSubject;
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseCommonTest;
import com.virtusa.akura.common.service.CommonService;

/**
 * This test class, tests all the persistence related functionality for the Exam Subject domain object.
 *
 * @author Virtusa Corporation
 */
public class ExamSubjectDaoImplTest extends BaseCommonTest {

    /** Represents the assertion for the subject save. */
    private static final String SUBJECT_SAVE_ASSERT = "Subject type should not be null ";

    /** Represents the description of the Subject. */
    private static final String SUBJECT_DESCRIPTION = "SubjectDescription";

    /** Represents the assertion for the exam save. */
    private static final String SAVE_ASSERT = "Exam type should not be null ";

    /** Represents the description of the Exam. */
    private static final String NEW_EXAM = "New exam";

    /** Represents an instance of the ExamSubject. */
    private ExamSubject examSubject;

    /** Represents an instance of the commonService. */
    @Autowired
    private ExamSubjectDao examSubjectDao;

    /** Represents an instance of the commonService. */
    @Autowired
    private CommonService commonService;

    /** Represents an instance of the Exam. */
    private Exam exam;

    /** Represents an instance of the Subject. */
    private Subject subject;

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws AkuraAppException {

        exam = new Exam();
        exam.setDescription(NEW_EXAM);
        exam.setModifiedTime(new Date());

        exam = commonService.addExam(exam);
        assertNotNull(SAVE_ASSERT + exam);

        subject = new Subject();
        subject.setDescription(SUBJECT_DESCRIPTION);
        subject.setModifiedTime(new Date());

        subject = commonService.addSubject(subject);
        assertNotNull(SUBJECT_SAVE_ASSERT + subject);

        examSubject = new ExamSubject();
        examSubject.setModifiedTime(new Date());
        examSubject.setExam(exam);
        examSubject.setSubject(subject);
    }



    /**
     * Test method for
     * {@link com.virtusa.akura.api.dao.BaseDaoImpl#save(com.virtusa.akura.api.dto.BaseDomain)}.
     *
     * @throws AkuraAppException - The exception details that occurred when saving.
     */
    @Test
    public void testSave() throws AkuraAppException {

        ExamSubject newExamSubject = examSubjectDao.save(examSubject);
        assertNotNull(newExamSubject);
    }

    /**
     * Test method for {@link com.virtusa.akura.api.dao.BaseDaoImpl#findById(java.lang.Class, int)}.
     *
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     */
    @Test
    public void testFindById() throws AkuraAppException {

        ExamSubject newExamSubject = examSubjectDao.save(examSubject);
        assertNotNull(newExamSubject);
        ExamSubject findExamSubject =
                (ExamSubject) examSubjectDao.findById(ExamSubject.class, examSubject.getExamSubjectId());
        assertNotNull(findExamSubject);
    }

    /**
     * Test method for {@link com.virtusa.akura.api.dao.BaseDaoImpl#findAll(java.lang.Class)}.
     *
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     */
    @Test
    public void testFindAll() throws AkuraAppException {

        ExamSubject newExamSubject = examSubjectDao.save(examSubject);
        assertNotNull(newExamSubject);
        List<ExamSubject> examSubjectList = examSubjectDao.findAll(ExamSubject.class);
        assertTrue(examSubjectList.size() > 0);
    }

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @After
    public void tearDown() throws AkuraAppException {
        examSubjectDao.delete(examSubject);
        commonService.deleteExam(exam);
        commonService.deleteSubject(subject);
    }
}
