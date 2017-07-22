
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

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.Exam;
import com.virtusa.akura.api.dto.ExamAdmission;
import com.virtusa.akura.api.dto.ExamMark;
import com.virtusa.akura.api.dto.ExamResults;
import com.virtusa.akura.api.dto.ExamSubject;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.GradeSubject;
import com.virtusa.akura.api.dto.SchoolClass;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.dao.ClassGradeDao;
import com.virtusa.akura.common.dao.ExamDao;
import com.virtusa.akura.common.dao.ExamSubjectDao;
import com.virtusa.akura.common.dao.GradeDao;
import com.virtusa.akura.common.dao.GradeSubjectDao;
import com.virtusa.akura.common.dao.SchoolClassDao;
import com.virtusa.akura.common.dao.SubjectDao;
import com.virtusa.akura.student.BaseStudentTest;
import com.virtusa.akura.util.DateUtil;


/**
 * The class ExamResultsDaoImplTest.
 * 
 * @author Virtusa Corporation
 *
 */

public class ExamResultsDaoImplTest extends BaseStudentTest {
    
    /** The now. */
    private Date now = new Date();
    
    /** The grade. */
    private Grade grade;
    
    /** The grade dao. */
    @Autowired
    private GradeDao gradeDao;
    
    /** The subject. */
    private Subject subject;
    
    /** The subject dao. */
    @Autowired
    private SubjectDao subjectDao;
    
    /** The grade subject. */
    private GradeSubject gradeSubject;
    
    /** The grade subject dao. */
    @Autowired
    private GradeSubjectDao gradeSubjectDao;
    
    /** The Student. */
    private Student student;
    
    /** The Student Dao. */
    @Autowired
    private StudentDao studentDao;
    
    /** The school class. */
    private SchoolClass schoolClass;
    
    /** The school class dao. */
    @Autowired
    private SchoolClassDao schoolClassDao;
    
    /** The class grade. */
    private ClassGrade classGrade;
    
    /** The class grade dao. */
    @Autowired
    private ClassGradeDao classGradeDao;
    
    /** The student class info. */
    private StudentClassInfo studentClassInfo;
    
    /** The student class info dao. */
    @Autowired
    private StudentClassInfoDao studentClassInfoDao;

    /** The Exam. */
    private Exam exam;
    
    /** The Exam Dao. */
    @Autowired
    private ExamDao examDao;
    
    /** The Exam Subject. */
    private ExamSubject examSubject;
    
    /** The Exam Subject Dao. */
    @Autowired
    private ExamSubjectDao examSubjectDao;
    
    /** The Exam Admission. */
    private ExamAdmission examAdmission;
    
    /** The Exam Admission Dao. */
    @Autowired
    private ExamAdmissionDao examAdmissionDao;
    
    /** The Exam Mark. */
    private ExamMark examMark;
    
    /** The Exam Mark Dao. */
    @Autowired
    private ExamMarksDao examMarksDao;

    /** The Exam Results Dao. */
    @Autowired
    private ExamResultsDao examResultsDao;
    
    /**
     * Instantiate the setup method.
     * 
     * @throws AkuraAppException AkuraAppException
     */
    @Before
    public void setUp() throws AkuraAppException { 
        
        grade = new Grade();
        grade.setDescription("Test Grade");
        gradeDao.save(grade);
        assertNotNull("Grade is saved to DB", grade);
        
        schoolClass = new SchoolClass();
        schoolClass.setDescription("Test Class");
        schoolClassDao.save(schoolClass);
        assertNotNull("SchoolClass is saved to DB", schoolClass);
        
        subject = new Subject();
        subject.setDescription("Test Subject");
        subjectDao.save(subject);
        assertNotNull("Subject is saved to DB", subject);
        
        gradeSubject = new GradeSubject();
        gradeSubject.setGrade(grade);
        gradeSubject.setSubject(subject);
        gradeSubject.setSubjectCode("Test Grade Test Subject");
        gradeSubjectDao.save(gradeSubject);
        assertNotNull("GradeSubject is saved to DB", gradeSubject);
        
        classGrade = new ClassGrade();
        classGrade.setGrade(grade);
        classGrade.setSchoolClass(schoolClass);
        classGrade.setDescription("Test Grade Test Class");
        classGradeDao.save(classGrade);
        assertNotNull("ClassGrade is saved to DB", classGrade);
        
        student = new Student();
        student.setFullName("Test Full Name");
        student.setLastName("Test Last Name");
        student.setAdmissionNo("Test Admission");
        student.setNameWtInitials("Test Name With Initials");
        student.setFirstSchoolDay(now);
        studentDao.save(student);
        assertNotNull("Student is saved to DB", student);
           
        studentClassInfo = new StudentClassInfo();
        studentClassInfo.setStudent(student);
        studentClassInfo.setClassGrade(classGrade);
        studentClassInfo.setYear(DateUtil.currentYear());
        studentClassInfo.setCheckMonitor(false);
        studentClassInfoDao.save(studentClassInfo);
        assertNotNull("StudentClassInfo is saved to DB", studentClassInfo);
        
        exam = new Exam();
        exam.setDescription("Test Exam");
        exam.setGradeId(grade.getGradeId());
        exam.setMark(true);
        exam = examDao.save(exam);
        assertNotNull("Exam is saved to DB", exam);
        
        examAdmission = new ExamAdmission();
        examAdmission.setAdmissionNo("123");
        examAdmission.setExamId(exam.getExamId());
        examAdmission.setStudentId(student.getStudentId());
        examAdmission.setYear(now);
        examAdmission = examAdmissionDao.save(examAdmission);
        assertNotNull("Exam Admission is saved to DB", examAdmission);
        
        examSubject = new ExamSubject();
        examSubject.setExam(exam);
        examSubject.setSubject(subject);
        examSubject.setOptionalSubject(false);
        examSubject = examSubjectDao.save(examSubject);
        assertNotNull("Exam Subject is saved to DB", examSubject);
        
        examMark = new ExamMark();
        examMark.setExamSubject(examSubject.getExamSubjectId());
        examMark.setMarks(new Float(50));
        examMark.setStudent(student.getStudentId());
        examMark.setYear(now);
        examMark.setStatus(0);
        examMark = examMarksDao.save(examMark);
        assertNotNull("Exam mark is saved to DB", examMark);
        
        
    }
    
    /**
     * Test method to check if a donation object was successfully added to the db.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public void testGetExamResults() throws AkuraAppException {
        try {
            List<ExamResults> list = examResultsDao.getExamResultsByExamAdmissionNo(examAdmission.getAdmissionNo(), 
                    exam.getExamId());
            assertTrue("Exam Results list should not be null ", list.size() > 0);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Tear down.
     * 
     * @throws Exception the exception
     * @see junit.framework.TestCase#tearDown()
     */
    @After
    public void tearDown() throws Exception {

        try {
            examMarksDao.delete(examMark);
            examSubjectDao.delete(examSubject);
            examAdmissionDao.delete(examAdmission);
            examDao.delete(exam);
            
            studentClassInfoDao.delete(studentClassInfo);
            studentDao.delete(student);
            classGradeDao.delete(classGrade);
            gradeSubjectDao.delete(gradeSubject);
            subjectDao.delete(subject);
            gradeDao.delete(grade);
            schoolClassDao.delete(schoolClass);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
}
