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

import com.virtusa.akura.api.dto.Assignment;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.GradeSubject;
import com.virtusa.akura.api.dto.SchoolClass;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentAssignmentMark;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.dao.AssignmentDao;
import com.virtusa.akura.common.dao.ClassGradeDao;
import com.virtusa.akura.common.dao.GradeDao;
import com.virtusa.akura.common.dao.GradeSubjectDao;
import com.virtusa.akura.common.dao.SchoolClassDao;
import com.virtusa.akura.common.dao.SubjectDao;
import com.virtusa.akura.student.BaseStudentTest;
import com.virtusa.akura.util.DateUtil;

/**
 * The Class StudentAssignmentMarkDaoImplTest.
 */
public class StudentAssignmentMarkDaoImplTest extends BaseStudentTest {
    
    /** The now. */
    private Date now = new Date();
    
    /** The assignment. */
    private Assignment assignment;
    
    /** The assignment dao. */
    @Autowired
    private AssignmentDao assignmentDao;
    
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
    
    /** The student assignment mark. */
    private StudentAssignmentMark studentAssignmentMark;
    
    /** The student assignment mark dao. */
    @Autowired
    private StudentAssignmentMarkDao studentAssignmentMarkDao;
    
    /**
     * Set up.
     * 
     * @throws Exception the exception
     * @see junit.framework.TestCase#setUp()
     */
    @Before
    public void setUp() throws Exception {

        grade = new Grade();
        grade.setDescription("Test Grade");
        grade.setModifiedTime(now);
        gradeDao.save(grade);
        assertNotNull("Grade is saved to DB", grade);
        
        subject = new Subject();
        subject.setDescription("Test Subject");
        subject.setModifiedTime(now);
        subjectDao.save(subject);
        assertNotNull("Subject is saved to DB", subject);
        
        gradeSubject = new GradeSubject();
        gradeSubject.setGrade(grade);
        gradeSubject.setSubject(subject);
        gradeSubject.setSubjectCode("Test Grade Test Subject");
        gradeSubject.setModifiedTime(now);
        gradeSubjectDao.save(gradeSubject);
        assertNotNull("GradeSubject is saved to DB", gradeSubject);
        
        assignment = new Assignment();
        assignment.setGradeSubject(gradeSubject);
        assignment.setName("Test Assignment");
        assignment.setDate(now);
        assignment.setModifiedTime(now);
        assignment.setIsMarks(false);
        assignment.setDescription("Test Desc");
        assignmentDao.save(assignment);
        assertNotNull("Assignment is saved to DB", assignment);
        
        student = new Student();
        student.setFullName("Test Full Name");
        student.setFirstSchoolDay(now);
        student.setLastName("Test Last Name");
        student.setAdmissionNo("Test Admission");
        student.setNameWtInitials("Test Name With Initials");
        student.setModifiedTime(now);
        studentDao.save(student);
        assertNotNull("Student is saved to DB", student);
        
        schoolClass = new SchoolClass();
        schoolClass.setDescription("Test Class");
        schoolClass.setModifiedTime(now);
        schoolClassDao.save(schoolClass);
        assertNotNull("SchoolClass is saved to DB", schoolClass);
        
        classGrade = new ClassGrade();
        classGrade.setGrade(grade);
        classGrade.setSchoolClass(schoolClass);
        classGrade.setDescription("Test Grade Test Class");
        classGrade.setModifiedTime(now);
        classGradeDao.save(classGrade);
        assertNotNull("ClassGrade is saved to DB", classGrade);
        
        studentClassInfo = new StudentClassInfo();
        studentClassInfo.setStudent(student);
        studentClassInfo.setClassGrade(classGrade);
        studentClassInfo.setYear(DateUtil.currentYear());
        studentClassInfo.setCheckMonitor(false);
        studentClassInfo.setModifiedTime(now);
        studentClassInfoDao.save(studentClassInfo);
        assertNotNull("StudentClassInfo is saved to DB", studentClassInfo);
        
        studentAssignmentMark = new StudentAssignmentMark();
        studentAssignmentMark.setStudentClassInfoId(studentClassInfo.getStudentClassInfoId());
        studentAssignmentMark.setGradeSubjectId(gradeSubject.getGradeSubjectId());
        studentAssignmentMark.setAssignmentId(assignment.getAssignmentId());
        studentAssignmentMark.setYear(DateUtil.currentYear());
        studentAssignmentMark.setModifiedTime(now);
        studentAssignmentMarkDao.save(studentAssignmentMark);
        assertNotNull("StudentAssignmentMark is saved to DB", studentAssignmentMark);
    }
    
    /**
     * Test get student assignment marks by student id.
     * 
     * @throws AkuraAppException the akura app exception
     */
    @Test
    public void testGetStudentAssignmentMarksByStudentId() throws AkuraAppException {

        try {
            List<Object[]> list = studentAssignmentMarkDao.getStudentAssignmentMarksByStudentId(student.getStudentId());
            assertTrue(list.size() > 0);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test get student assignment marks list.
     * 
     * @throws AkuraAppException the akura app exception
     */
    @Test
    public void testGetStudentAssignmentMarksList() throws AkuraAppException {

        try {
            
            List<Object[]> list =
                    studentAssignmentMarkDao.getStudentAssignmentMarksList(gradeSubject.getGradeSubjectId(),
                            assignment.getAssignmentId(), DateUtil.currentYearOnly(), classGrade.getClassGradeId());
            assertTrue(list.size() > 0);
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
            studentAssignmentMarkDao.delete(studentAssignmentMark);
            studentClassInfoDao.delete(studentClassInfo);
            studentDao.delete(student);
            classGradeDao.delete(classGrade);
            assignmentDao.delete(assignment);
            gradeSubjectDao.delete(gradeSubject);
            subjectDao.delete(subject);
            gradeDao.delete(grade);
            schoolClassDao.delete(schoolClass);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
}
