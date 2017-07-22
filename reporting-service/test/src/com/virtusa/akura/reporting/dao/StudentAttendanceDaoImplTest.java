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

package com.virtusa.akura.reporting.dao;

import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.api.dto.BestStudentAttendanceTemplate;
import com.virtusa.akura.api.dto.EarlyLeaversTemplate;
import com.virtusa.akura.api.dto.LateLeaversTemplate;
import com.virtusa.akura.api.dto.StudentAttendance;
import com.virtusa.akura.api.dto.StudentWiseAttendanceTemplate;
import com.virtusa.akura.api.dto.StudentWiseSwipInOutTemplate;
import com.virtusa.akura.api.dto.TeacherLateAttendiesTemplate;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.reporting.BaseReportingTest;

/**
 * This test class, tests all the persistence related functionality for the StudentAttendanceDaoImpl domain
 * object.
 *
 * @author Virtusa Corporation
 */

public class StudentAttendanceDaoImplTest extends BaseReportingTest {

    /**
     * This static instance for year of selected.
     */
    private static final int YEAR = 2013;

    /** admission no constant used to test update on student. */
    private static final String ADMISSION_NO = "013110";

    /**
     * Represents an instance of EarlyLeaversTemplate.
     */
    private EarlyLeaversTemplate earlyLeaversTemplate;

    /**
     * Represents an instance of LateLeaversTemplate.
     */
    private LateLeaversTemplate lateLeaversTemplate;

    /**
     * Represents an instance of StudentWiseAttendanceTemplate.
     */
    private StudentWiseAttendanceTemplate studentWiseAttendanceTemplate;

    /**
     * Represents an instance of StudentWiseSwipInOutTemplate.
     */
    private StudentWiseSwipInOutTemplate studentWiseSwipInOutTemplate;

    /**
     * Represents an instance of TeacherLateAttendiesTemplate for student late attendees.
     */
    private TeacherLateAttendiesTemplate studentAttendanceWrapperTemplate;

    /**
     * Represents an instance of studentAttendanceDao.
     */
    @Autowired
    private StudentAttendanceDao studentAttendanceDao;

    /**
     * Represents an instance of BestStudentAttendanceTemplate.
     */
    private BestStudentAttendanceTemplate bestAttendanceTemplate;

    /**
     * @throws AkuraAppException - the Exception that occurred.
     */
    @Before
    public void setUp() throws AkuraAppException {

        lateLeaversTemplate = new LateLeaversTemplate();
        lateLeaversTemplate.setDateFrom("2012-03-03");
        lateLeaversTemplate.setDateTo("2013-03-03");
        lateLeaversTemplate.setTimeOutFrom("07:30");
        lateLeaversTemplate.setTimeOutTo("13.30");

        earlyLeaversTemplate = new EarlyLeaversTemplate();
        earlyLeaversTemplate.setDateFrom("2002-01-01");
        earlyLeaversTemplate.setDateTo("2002-01-10");
        earlyLeaversTemplate.setTimeOutFrom("8.30");
        earlyLeaversTemplate.setTimeOutTo("11.30");

        studentWiseAttendanceTemplate = new StudentWiseAttendanceTemplate();
        studentWiseAttendanceTemplate.setStudentID(ADMISSION_NO);

        studentWiseSwipInOutTemplate = new StudentWiseSwipInOutTemplate();
        studentWiseSwipInOutTemplate.setDateFrom("2012-03-03");
        studentWiseSwipInOutTemplate.setDateTo("2012-03-23");

        studentAttendanceWrapperTemplate = new TeacherLateAttendiesTemplate();
        studentAttendanceWrapperTemplate.setDateFrom("2002-01-01");
        studentAttendanceWrapperTemplate.setDateTo("2013-03-03");
        studentAttendanceWrapperTemplate.setTimeInFrom("8.30");
        studentAttendanceWrapperTemplate.setTimeInTo("11.30");

        bestAttendanceTemplate = new BestStudentAttendanceTemplate();
        bestAttendanceTemplate.setGradeId(1);
        bestAttendanceTemplate.setClassId(1);
        bestAttendanceTemplate.setYear(YEAR);
        bestAttendanceTemplate.setMonthId(1);
    }

    /**
     * Test method to check if, StudentWiseAttendanceTemplate object was successfully reterieved from the db.
     * and , then passed into JRBeanCollectionDataSource object and return that object.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void listStudentWiseAttendanceSummaryTest() throws AkuraAppException {

        final JRBeanCollectionDataSource dataSource =
                studentAttendanceDao.getStudentWiseAttendanceSummary(studentWiseAttendanceTemplate);

        assertNotNull("Object " + dataSource);
    }

    /**
     * Test method to check if, EarlyLateLeaversTemplate object was successfully reterieved from the db. and ,
     * then passed into JRBeanCollectionDataSource object and return that object.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void listEarlyLeaversStudentSummaryTest() throws AkuraAppException {

        final JRBeanCollectionDataSource dataSource =
                studentAttendanceDao.getEarlyLeaversStudentSummary(lateLeaversTemplate);

        assertNotNull("Object " + dataSource);
    }

    /**
     * Test method to check if, studentWiseSwipInOutTemplate object was successfully retrieved from the db.
     * and , then passed into JRBeanCollectionDataSource object and return that object.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void listStudentWiseSwipInOutSummaryTest() throws AkuraAppException {

        final JRBeanCollectionDataSource dataSource =
                studentAttendanceDao.getStudentWiseSwipInOutSummary(studentWiseSwipInOutTemplate, ADMISSION_NO);

        assertNotNull("Object " + dataSource);
    }

    /**
     * Test method to check getStudentAttandanceList.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void getStudentAttandanceListTest() throws AkuraAppException {

        final List<StudentAttendance> stdentlist = studentAttendanceDao.getStudentAttandanceList("2011-06-14");
        assertNotNull(stdentlist.size() > 0);
    }

    /**
     * Test method to check if, studentAttendanceWrapperTemplate object was successfully retrieved from the
     * db. and , then passed into JRBeanCollectionDataSource object and return that object.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void listStudentLateAttendiesSummaryTest() throws AkuraAppException {

        final JRBeanCollectionDataSource dataSource =
                studentAttendanceDao.getStudentLateAttendiesSummary(studentAttendanceWrapperTemplate);

        assertNotNull("Object " + dataSource);
    }

    /**
     * Test method for GradeWiseBestStudentAttendance.
     *
     * @throws AkuraAppException - The exception that can occurred.
     */
    @Test
    public final void testGradeWiseBestStudentAttendance() throws AkuraAppException {

        final JRBeanCollectionDataSource gradeWiseBestStudentAttendanceList =
                studentAttendanceDao.getBestStudentAttendance(bestAttendanceTemplate);

        assertNotNull("JR Collection Data Source " + gradeWiseBestStudentAttendanceList);
    }

}
