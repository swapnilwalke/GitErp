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

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.BaseDomain;
import com.virtusa.akura.api.dto.TeacherAttendance;
import com.virtusa.akura.api.dto.TeacherEarlyComersTemplate;
import com.virtusa.akura.api.dto.TeacherLateAttendiesTemplate;
import com.virtusa.akura.api.dto.TeacherWiseAttendanceTemplate;
import com.virtusa.akura.api.dto.TeacherWisePresentAbsentTemplate;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * Interface to define DAO methods related to teacher attendance.
 */

public interface TeacherAttendanceDao extends BaseDao<BaseDomain> {

    /**
     * Generate data list for Teacher wise attendance summary.
     *
     * @param teacherWiseAttendanceTemplate of type TeacherWiseAttendanceTemplate to get inputs from JSP
     * @return JRBeanCollectionDataSource
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    JRBeanCollectionDataSource getTeacherWiseAttendanceSummary(
            TeacherWiseAttendanceTemplate teacherWiseAttendanceTemplate) throws AkuraAppException;

    /**
     * Generate data list for Teacher late attendees in a specific time range and specific time range .
     *
     * @param teacherLateAttendiesTemplate of type TeacherLateAttendiesTemplate to get inputs from JSP
     * @return JRBeanCollectionDataSource
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    JRBeanCollectionDataSource getTeacherLateAttendies(TeacherLateAttendiesTemplate teacherLateAttendiesTemplate)
            throws AkuraAppException;

    /**
     * Generate data list for Teacher early out breakdown in several time ranges .
     *
     * @param teacherEarlyComersTemplate of type TeacherEarlyComersTemplate to get inputs from JSP
     * @return JRBeanCollectionDataSource
     * @throws AkuraAppException - The exception details that occurred when processing
     */

    JRBeanCollectionDataSource getEarlyComersTeacherSummary(TeacherEarlyComersTemplate teacherEarlyComersTemplate)
            throws AkuraAppException;

	/**
     * Generate data list for Teacher wise attendance summary.
     *
     * @param teacherWisePresentAbsentTemplate
     *            of type TTeacherWisePresentAbsentTemplate to get inputs from JSP
     * @return List type of TeacherAttendance.
     * @throws AkuraAppException
     *             - The exception details that occurred when processing
     */
	List<TeacherAttendance> teacherWisePresentAndAbsentDays(
            TeacherWisePresentAbsentTemplate teacherWisePresentAbsentTemplate)
            throws AkuraAppException;

}
