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

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.api.dto.TeacherLateAttendiesTemplate;
import com.virtusa.akura.api.dto.TeacherWiseAttendanceTemplate;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraException;
import com.virtusa.akura.reporting.BaseReportingTest;

/**
 * This test class, tests all the persistence related functionality for the TeacherAttendanceDaoImpl domain
 * object.
 *
 * @author Virtusa Corporation
 */

public class TeacherAttendanceDaoImplTest extends BaseReportingTest {

    /** registration no constant used to test update on staff- teacher. */
    private static final String REGISTRATION_NO = "010118";

    /**
     * Represents an instance of TeacherWiseAttendanceTemplate.
     */
    private TeacherWiseAttendanceTemplate teacherWiseAttendanceTemplate;

    /**
     * Represents an instance of TeacherLateAttendiesTemplate.
     */
    private TeacherLateAttendiesTemplate teacherLateAttendiesTemplate;

    /**
     * Represents an instance of teacherAttendanceDao.
     */
    @Autowired
    private TeacherAttendanceDao teacherAttendanceDao;

    /**
     * @throws AkuraException - the Exception that occurred.
     */
    @Before
    public void setUp() throws AkuraException {

        teacherLateAttendiesTemplate = new TeacherLateAttendiesTemplate();
        teacherLateAttendiesTemplate.setDateFrom("2003-01-01");
        teacherLateAttendiesTemplate.setDateTo("2004-01-01");
        teacherLateAttendiesTemplate.setTimeInFrom("07:45");
        teacherLateAttendiesTemplate.setTimeInTo("10.45");

        teacherWiseAttendanceTemplate = new TeacherWiseAttendanceTemplate();
        teacherWiseAttendanceTemplate.setTeacherNo(REGISTRATION_NO);
        teacherWiseAttendanceTemplate.setDateFrom("2012-01-02");
        teacherWiseAttendanceTemplate.setDateTo("2012-03-05");
    }

    /**
     * Test method to check if, TeacherWiseAttendanceTemplate object was successfully reterieved from the db.
     * and , then passed into JRBeanCollectionDataSource object and return that object.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void listTeacherWiseAttendanceSummaryTest() throws AkuraAppException {

        final JRBeanCollectionDataSource dataSource =
                teacherAttendanceDao.getTeacherWiseAttendanceSummary(teacherWiseAttendanceTemplate);

        assertNotNull("Object " + dataSource);
    }

    /**
     * Test method to check if, TeacherLateAttendiesTemplate object was successfully reterieved from the db.
     * and , then passed into JRBeanCollectionDataSource object and return that object.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void listTeacherLateAttendiesTest() throws AkuraAppException {

        final JRBeanCollectionDataSource dataSource =
                teacherAttendanceDao.getTeacherLateAttendies(teacherLateAttendiesTemplate);

        assertNotNull("Object " + dataSource);
    }

}
