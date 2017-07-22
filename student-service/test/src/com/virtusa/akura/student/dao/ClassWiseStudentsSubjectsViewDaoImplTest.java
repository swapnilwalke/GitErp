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
import com.virtusa.akura.api.dto.ClassWiseStudentsSubjectsDTO;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.GradeSubject;
import com.virtusa.akura.api.dto.SchoolClass;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.dto.StudentTermMark;
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.dto.Term;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.dao.ClassGradeDao;
import com.virtusa.akura.common.dao.GradeDao;
import com.virtusa.akura.common.dao.GradeSubjectDao;
import com.virtusa.akura.common.dao.SchoolClassDao;
import com.virtusa.akura.common.dao.SubjectDao;
import com.virtusa.akura.common.dao.TermDao;
import com.virtusa.akura.student.BaseStudentTest;
import com.virtusa.akura.util.DateUtil;

/**
 * The Class ClassWiseStudentsSubjectsViewDaoImplTest.
 */
public class ClassWiseStudentsSubjectsViewDaoImplTest extends BaseStudentTest {
    
    /** The current date. */
    private Date now = new Date();
    
    /** The school class. */
    private SchoolClass schoolClass;
    
    /** The school class dao. */
    @Autowired
    private SchoolClassDao schoolClassDao;
    
    /** The grade. */
    private Grade grade;
    
    /** The grade dao. */
    @Autowired
    private GradeDao gradeDao;
    
    /** The class grade. */
    private ClassGrade classGrade;
    
    /** The class grade dao. */
    @Autowired
    private ClassGradeDao classGradeDao;
    
    /** The grade subject. */
    private GradeSubject gradeSubject;
    
    /** The grade subject dao. */
    @Autowired
    private GradeSubjectDao gradeSubjectDao;
    
    /** The subject. */
    private Subject subject;
    
    /** The subject dao. */
    @Autowired
    private SubjectDao subjectDao;
    
    /** The student. */
    private Student student;
    
    /** The student dao. */
    @Autowired
    private StudentDao studentDao;
    
    /** The student class info. */
    private StudentClassInfo studentClassInfo;
    
    /** The student class info dao. */
    @Autowired
    private StudentClassInfoDao studentClassInfoDao;
    
    /** The class wise students subjects view dao. */
    @Autowired
    private ClassWiseStudentsSubjectsViewDao classWiseStudentsSubjectsViewDao;
    
    /** The student term mark. */
    private StudentTermMark studentTermMark;
    
    /** The student term mark dao. */
    @Autowired
    private StudentTermMarkDao studentTermMarkDao;
    
    /** The term. */
    private Term term;
    
    /** The term dao. */
    @Autowired
    private TermDao termDao;
    
    /**
     * Sets up.
     * 
     * @throws AkuraAppException the akura app exception
     * @see junit.framework.TestCase#setUp()
     */
    @Before
    public void setUp() throws AkuraAppException {

        schoolClass = new SchoolClass();
        schoolClass.setDescription("Test Class");
        schoolClass.setModifiedTime(now);
        schoolClassDao.save(schoolClass);
        assertNotNull("Class save to DB", schoolClass);
        
        grade = new Grade();
        grade.setDescription("Test Grade");
        grade.setModifiedTime(now);
        gradeDao.save(grade);
        assertNotNull("Grade save to DB", grade);
        
        classGrade = new ClassGrade();
        classGrade.setGrade(grade);
        classGrade.setSchoolClass(schoolClass);
        classGrade.setDescription("Test Grade Test Class");
        classGrade.setModifiedTime(now);
        classGradeDao.save(classGrade);
        assertNotNull("ClassGrade save to DB", classGrade);
        
        subject = new Subject();
        subject.setDescription("Test Subject");
        subject.setModifiedTime(now);
        subjectDao.save(subject);
        assertNotNull("Subject save to DB", subject);
        
        gradeSubject = new GradeSubject();
        gradeSubject.setGrade(grade);
        gradeSubject.setSubject(subject);
        gradeSubject.setSubjectCode("Test Grade - Test Subject");
        gradeSubject.setModifiedTime(now);
        gradeSubjectDao.save(gradeSubject);
        assertNotNull("GradeSubject save to DB", gradeSubject);
        
        student = new Student();
        student.setAdmissionNo("Test Admission");
        student.setFullName("Test Full Name");
        student.setNameWtInitials("Test Name With Initials");
        student.setFirstSchoolDay(now);
        student.setLastName("Test Last Name");
        studentDao.save(student);
        assertNotNull("Student save to DB", student);
        
        studentClassInfo = new StudentClassInfo();
        studentClassInfo.setClassGrade(classGrade);
        studentClassInfo.setStudent(student);
        studentClassInfo.setYear(now);
        studentClassInfo.setCheckMonitor(false);
        studentClassInfo.setModifiedTime(DateUtil.currentYear());
        studentClassInfoDao.save(studentClassInfo);
        assertNotNull("StudentClassInfo save to DB", studentClassInfo);
        
        term = new Term();
        term.setDescription("Test Term");
        term.setFromMonth(now);
        term.setToMonth(now);
        term.setModifiedTime(now);
        termDao.save(term);
        assertNotNull("Term save to DB", term);
        
        studentTermMark = new StudentTermMark();
        studentTermMark.setStudentClassInfoId(studentClassInfo.getStudentClassInfoId());
        studentTermMark.setGradeSubjectId(gradeSubject.getGradeSubjectId());
        studentTermMark.setTermId(term.getTermId());
        studentTermMark.setModifiedTime(now);
        studentTermMarkDao.save(studentTermMark);
        assertNotNull("StudentTermMark save to DB", studentTermMark);
        
    }
    
    /**
     * Test get class wise student list by subject.
     * 
     * @throws AkuraAppException the akura app exception
     */
    @Test
    public void testGetClassWiseStudentListBySubject() throws AkuraAppException {

        try {
            
            List<ClassWiseStudentsSubjectsDTO> classWiseStudentsSubjectsDTOs =
                    classWiseStudentsSubjectsViewDao.getClassWiseStudentListBySubject(classGrade.getClassGradeId(),
                            gradeSubject.getGradeSubjectId(), studentClassInfo.getYear());
            assertTrue(classWiseStudentsSubjectsDTOs.size() > 0);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Tear down.
     * 
     * @throws AkuraAppException the akura app exception
     * @see junit.framework.TestCase#tearDown()
     */
    @After
    public void tearDown() throws AkuraAppException {

        try {
            studentTermMarkDao.delete(studentTermMark);
            termDao.delete(term);
            studentClassInfoDao.delete(studentClassInfo);
            studentDao.delete(student);
            classGradeDao.delete(classGrade);
            schoolClassDao.delete(schoolClass);
            gradeSubjectDao.delete(gradeSubject);
            gradeDao.delete(grade);
            subjectDao.delete(subject);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
}
