/*
 * © 2011 Virtusa(Pvt)Ltd. All rights reserved. CONFIDENTIAL AND

 * PROPRIETARY INFORMATION The information contained herein (the

 * 'Proprietary Information') is highly confidential and proprietary to and

 * constitutes trade secrets of Virtusa(Pvt)Ltd. The Proprietary Information

 * for Virtusa(Pvt)Ltd internal use only and shall not be published,

 * communicated, disclosed or divulged to any person, firm, corporation or

 * other legal entity, directly or indirectly, without the prior written

 * consent of Virtusa(Pvt)Ltd Information Management.
 */

package com.virtusa.akura.reporting.service;

import java.util.Date;
import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.api.dto.BestStudentAttendanceTemplate;
import com.virtusa.akura.api.dto.EarlyLateLeaversTemplate;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentAttendance;
import com.virtusa.akura.api.dto.StudentWiseAttendanceTemplate;
import com.virtusa.akura.api.dto.TeacherAttendance;
import com.virtusa.akura.api.dto.TeacherEarlyComersTemplate;
import com.virtusa.akura.api.dto.TeacherLateAttendiesTemplate;
import com.virtusa.akura.api.dto.TeacherWiseAttendanceTemplate;
import com.virtusa.akura.api.dto.TeacherWisePresentAbsentTemplate;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.reporting.BaseReportingTest;

/**
 * This test class, tests all the persistence related functionality for the AttendanceReportingServiceImpl
 * domain object.
 *
 * @author Virtusa Corporation
 */

public class AttendanceReportingServiceImplTest extends BaseReportingTest {

    /**
     * This static instance for year of selected.
     */
    private static final int YEAR = 2013;

    /**
     * This static instance for Number of Hours.
     */
    private static final int NUMBER_OF_HOURS = 20;

    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private AttendanceReportingService attendanceReportingService;

    /**
     * Represents an instance of StudentAttendance.
     */
    private StudentAttendance studentAttendance;

    /**
     * Represents an instance of Student.
     */
    private Student student;

    /**
     * Represents an instance of TeacherWiseAttendanceTemplate.
     */
    private TeacherWiseAttendanceTemplate teacherWiseAttendanceTemplate;

    /**
     * Represents an instance of TeacherLateAttendiesTemplate.
     */
    private TeacherLateAttendiesTemplate teacherLateAttendiesTemplate;

    /**
     * Represents an instance of TeacherEarlyComersTemplate.
     */
    private TeacherEarlyComersTemplate teacherEarlyComersTemplate;

    /**
     * Represents an instance of EarlyLateLeaversTemplate.
     */
    private EarlyLateLeaversTemplate earlyLateLeaversTemplate;

    /**
     * Represents an instance of StudentWiseAttendanceTemplate.
     */
    private StudentWiseAttendanceTemplate studentWiseAttendanceTemplate;

    /**
     * Represents an instance of TeacherWiseAttendanceTemplate.
     */
    private TeacherWisePresentAbsentTemplate presentAbsentTemplate;

    /**
     * Represents an instance of BestStudentAttendanceTemplate.
     */
    private BestStudentAttendanceTemplate bestAttendanceTemplate;

    /**
     * @throws Exception - the exception that occurred.
     */
    @Before
    public final void setUp() throws Exception {

        student = new Student();
        student.setAdmissionNo("1234567");
        student.setAddress("No 2A");

        studentAttendance = new StudentAttendance();
        studentAttendance.setDate("2011-09-09");
        studentAttendance.setDayname("Monday123");
        studentAttendance.setNumOfHours(NUMBER_OF_HOURS);
        studentAttendance.setStudentID("109873");
        studentAttendance.setStudentName("Sanduni");
        studentAttendance.setTimeIn("7.30");
        studentAttendance.setTimeOut("8.45");

        teacherWiseAttendanceTemplate = new TeacherWiseAttendanceTemplate();
        teacherWiseAttendanceTemplate.setDateFrom("2011-06-08");
        teacherWiseAttendanceTemplate.setDateTo("2011-06-08");
        // teacherWiseAttendanceTemplate.setTeacherName("Gayan");

        teacherLateAttendiesTemplate = new TeacherLateAttendiesTemplate();
        teacherLateAttendiesTemplate.setDateFrom("2011-06-08");
        teacherLateAttendiesTemplate.setDateTo("2011-06-08");
        teacherLateAttendiesTemplate.setTimeInFrom("2011-06-08");
        teacherLateAttendiesTemplate.setTimeInTo("2011-06-08");

        teacherEarlyComersTemplate = new TeacherEarlyComersTemplate();
        teacherEarlyComersTemplate.setDateFrom("2011-06-08");
        teacherEarlyComersTemplate.setDateTo("2011-06-08");
        teacherEarlyComersTemplate.setTimeInFrom("2011-06-08");
        teacherEarlyComersTemplate.setTimeInTo("2011-06-08");

        earlyLateLeaversTemplate = new EarlyLateLeaversTemplate();
        earlyLateLeaversTemplate.setDateFrom("2011-06-08");
        earlyLateLeaversTemplate.setDateTo("2011-06-08");
        earlyLateLeaversTemplate.setTimeOutFrom("2011-06-08");
        earlyLateLeaversTemplate.setTimeOutTo("2011-06-08");

        studentWiseAttendanceTemplate = new StudentWiseAttendanceTemplate();
        studentWiseAttendanceTemplate.setDateFrom("2011-06-08");
        studentWiseAttendanceTemplate.setDateTo("2011-06-08");
        studentWiseAttendanceTemplate.setStudentID("2011-06-08");

        presentAbsentTemplate = new TeacherWisePresentAbsentTemplate();
        presentAbsentTemplate.setTeacherRegNo("010118");
        presentAbsentTemplate.setDateFrom("2011-06-01");
        presentAbsentTemplate.setDateTo("2011-06-30");

        bestAttendanceTemplate = new BestStudentAttendanceTemplate();
        bestAttendanceTemplate.setGradeId(1);
        bestAttendanceTemplate.setClassId(1);
        bestAttendanceTemplate.setYear(YEAR);
        bestAttendanceTemplate.setMonthId(1);
    }

    /**
     * Test method to check if, teacherWiseAttendanceTemplate object was successfully reterieved from the db.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testListTeacherWiseAttendanceSummary() throws AkuraAppException {

        JRBeanCollectionDataSource rd =
                attendanceReportingService.getTeacherWiseAttendanceSummary(teacherWiseAttendanceTemplate);
        assertNotNull("Object :" + rd);
    }

    /**
     * Test method to check if, teacherLateAttendiesTemplate object was successfully reterieved from the db.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testListTeacherLateAttendies() throws AkuraAppException {

        JRBeanCollectionDataSource rd =
                attendanceReportingService.getTeacherLateAttendies(teacherLateAttendiesTemplate);
        assertNotNull("Object :" + rd);
    }

    /**
     * Test method to check if, teacherEarlyComersTemplate object was successfully reterieved from the db.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testListEarlyComersTeacherSummary() throws AkuraAppException {

        JRBeanCollectionDataSource rd =
                attendanceReportingService.getEarlyComersTeacherSummary(teacherEarlyComersTemplate);
        assertNotNull("Object :" + rd);
    }

    /**
     * Test method to check if, studentWiseAttendanceTemplate object was successfully reterieved from the db.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testListStudentWiseAttendanceSummary() throws AkuraAppException {

        JRBeanCollectionDataSource rd =
                attendanceReportingService.getStudentWiseAttendanceSummary(studentWiseAttendanceTemplate);
        assertNotNull("Object :" + rd);
    }

    /**
     * Test method to check if, studentAttendance List object was successfully reterieved from the db.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetStudentAttandanceList() throws AkuraAppException {

        List<StudentAttendance> listStudent = attendanceReportingService.getStudentAttandanceList("" + new Date());
        assertNotNull(listStudent.size() > 0);
    }

    /**
     * Test method for TeacherWisePresentAndAbsentDays.
     *
     * @throws AkuraAppException - The exception that can occurred.
     */
    @Test
    public final void testTeacherWisePresentAndAbsentDays() throws AkuraAppException {

        List<TeacherAttendance> attendanceList =
                attendanceReportingService.teacherWisePresentAndAbsentDays(presentAbsentTemplate);
        assertNotNull(attendanceList.size() > 0);
    }

    /**
     * Test method for GradeWiseBestStudentAttendance.
     *
     * @throws AkuraAppException - The exception that can occurred.
     */
    @Test
    public final void testGradeWiseBestStudentAttendance() throws AkuraAppException {

        JRBeanCollectionDataSource gradeWiseBestStudentAttendanceList =
                attendanceReportingService.getBestStudentAttendance(bestAttendanceTemplate);

        assertNotNull(gradeWiseBestStudentAttendanceList.getRecordCount() > 0);
    }

}
