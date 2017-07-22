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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.Exam;
import com.virtusa.akura.api.dto.ExamMark;
import com.virtusa.akura.api.dto.ExamSubject;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.SchoolClass;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.dao.ClassGradeDao;
import com.virtusa.akura.common.dao.ExamDao;
import com.virtusa.akura.common.dao.ExamSubjectDao;
import com.virtusa.akura.common.dao.GradeDao;
import com.virtusa.akura.common.dao.SchoolClassDao;
import com.virtusa.akura.common.dao.SubjectDao;
import com.virtusa.akura.student.BaseStudentTest;
import com.virtusa.akura.util.DateUtil;

/**
 * This test class, tests all the persistence related functionality for the Exam marks domain object.
 *
 * @author Virtusa Corporation
 */
public class ExamMarkDaoImplTest extends BaseStudentTest {

    /** This instance will be dependency injected by type. */
    @Autowired
    private ExamMarksDao examMarksDao;

    /** This instance will be dependency injected by type. */
    @Autowired
    private SubjectDao subjectDao;

    /** This instance will be dependency injected by type. */
    @Autowired
    private ExamSubjectDao examSubjectDao;

    /** This instance will be dependency injected by type. */
    @Autowired
    private ExamDao examDao;

    /** This instance will be dependency injected by type. */
    @Autowired
    private GradeDao gradeDao;

    /** This instance will be dependency injected by type. */
    @Autowired
    private StudentDao studentDao;

    /** Represents an instance of ClassGradeDao.  */
    @Autowired
    private ClassGradeDao classGradeDao;

    /** Represents an instance of SchoolClassDao. */
    @Autowired
    private SchoolClassDao schoolClassDao;

    /** Represents an instance of Student. */
    private Student student;

    /** Represents an instance of Exam. */
    private Exam exam;

    /** Represents an instance of SchoolClass.  */
    private SchoolClass classInstace;

    /** Represents an instance of ClassGrade. */
    private ClassGrade classGrade;

    /** Represents an instance of Grade. */
    private Grade grade;

    /** Represents an instance of Subject. */
    private Subject subject;

    /** Represents an instance of Subject. */
    private ExamMark examMark;

    /** Represents an instance of ExamSubject. */
    private ExamSubject examSubject;

    /** Represents the assertion for the subject save. */
    private static final String SUBJECT_SAVE_ASSERT = "Subject type should not be null ";

    /** Represents the description of the Subject. */
    private static final String SUBJECT_DESCRIPTION = "Subject Des";

    /** Represents the assertion for the exam save. */
    private static final String SAVE_ASSERT = "Exam type should not be null ";

    /** Represents the description of the Exam. */
    private static final String NEW_EXAM = "New 7test exam";

    /** Represents the description of the grade. */
    private static final String GRADE_DESCRIPTION = "Grade Des";

    /** Represents the marks for the exam. */
    private static final float MARKS = 46.0f;

    /** Represents the assertion for the student save. */
    private static final String STUDENT_SAVE_ASSERT = "Student should not be null.";

    /**
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @Before
    public void setUp() throws AkuraAppException {

     // instantiates SchoolClass object.
        classInstace = new SchoolClass();
        classInstace.setDescription("Test Class");
        classInstace.setModifiedTime(new Date());
        SchoolClass newClassInstace = schoolClassDao.save(classInstace);
        assertNotNull(newClassInstace);

        grade = new Grade();
        grade.setDescription(GRADE_DESCRIPTION);
        Grade newGrade = gradeDao.save(grade);
        assertNotNull(newGrade);

        classGrade = new ClassGrade();
        classGrade.setSchoolClass(newClassInstace);
        classGrade.setGrade(grade);
        classGrade.setDescription(grade.getDescription() + classInstace.getDescription());
        ClassGrade newClassGrade = classGradeDao.save(classGrade);
        assertNotNull(newClassGrade);

        exam = new Exam();
        exam.setDescription(NEW_EXAM);
        exam.setModifiedTime(new Date());
        exam.setGradeId(grade.getGradeId());
        exam = examDao.save(exam);
        assertNotNull(SAVE_ASSERT + exam);

        subject = new Subject();
        subject.setDescription(SUBJECT_DESCRIPTION);
        subject.setModifiedTime(new Date());

        subject = subjectDao.save(subject);
        assertNotNull(SUBJECT_SAVE_ASSERT + subject);

        examSubject = new ExamSubject();
        examSubject.setModifiedTime(new Date());
        examSubject.setExam(exam);
        examSubject.setSubject(subject);

        examSubject = examSubjectDao.save(examSubject);
        assertNotNull(SUBJECT_SAVE_ASSERT + examSubject);

        student = new Student();
        student.setAdmissionNo("014252a33");
        student.setNameWtInitials("Test Name Lolratne");
        student.setLastName("Lolratne ");
        student.setFullName("Lolratne ");
        student.setAddress("Gampaha");
        student.setModifiedTime(new Date());
        student.setDateOfBirth(new Date());
        student.setAdmissionDate(new Date());
        student.setFirstSchoolDay(new Date());
        student.setGender('M');
        student.setIsOldBoy(false);
        Student newStudent = studentDao.save(student);
        assertNotNull(STUDENT_SAVE_ASSERT + newStudent);

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);

        examMark = new ExamMark();
        examMark.setExamSubject(examSubject.getExamSubjectId());
        examMark.setMarks(MARKS);
        examMark.setYear(DateUtil.getDate(year));
        examMark.setStudent(student.getStudentId());
    }

    /**
     * Test method for {@link com.virtusa.akura.student.dao.ExamMarksDaoImpl#getAllExamMarksList(int, int)}.
     *
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @Test
    public void testGetAllExamMarksList() throws AkuraAppException {

        examMark = examMarksDao.save(examMark);
        assertNotNull(SAVE_ASSERT + examMark);
        List<ExamMark> examMarkList = examMarksDao.findAll(ExamMark.class);
        assertTrue(examMarkList.size() > 0);
    }

    /**
     * Test method for
     * {@link com.virtusa.akura.api.dao.BaseDaoImpl#save(com.virtusa.akura.api.dto.BaseDomain)}.
     *
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @Test
    public void testSave() throws AkuraAppException {

        examMark = examMarksDao.save(examMark);
        assertNotNull(SAVE_ASSERT + examMark);
    }

    /**
     * Deletes the saved objects.
     *
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @After
    public void tearDown() throws AkuraAppException {

        examMarksDao.delete(examMark);
        studentDao.delete(student);
        examSubjectDao.delete(examSubject);
        subjectDao.delete(subject);
        examDao.delete(exam);
        classGradeDao.delete(classGrade);
        gradeDao.delete(grade);
        schoolClassDao.delete(classInstace);
    }
}
