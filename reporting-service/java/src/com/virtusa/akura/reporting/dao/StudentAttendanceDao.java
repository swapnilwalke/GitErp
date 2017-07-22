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
import com.virtusa.akura.api.dto.AnnualStudentAttendanceTemplate;
import com.virtusa.akura.api.dto.BaseDomain;
import com.virtusa.akura.api.dto.BestStudentAttendanceTemplate;
import com.virtusa.akura.api.dto.EarlyLeaversTemplate;
import com.virtusa.akura.api.dto.LateLeaversTemplate;
import com.virtusa.akura.api.dto.StudentAttendance;
import com.virtusa.akura.api.dto.StudentWiseAttendanceTemplate;
import com.virtusa.akura.api.dto.StudentWiseSwipInOutTemplate;
import com.virtusa.akura.api.dto.TeacherLateAttendiesTemplate;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * Interface to define DAO methods related to student attendance.
 */

public interface StudentAttendanceDao extends BaseDao<BaseDomain> {

	/**
	 * Generate Data list for Student Wise Attendance in school.
	 *
	 * @param studentWiseAttendanceTemplate
	 *            of type StudentWiseAttendanceTemplate
	 * @return JRBeanCollectionDataSource
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing
	 */
	JRBeanCollectionDataSource getStudentWiseAttendanceSummary(
			StudentWiseAttendanceTemplate studentWiseAttendanceTemplate)
			throws AkuraAppException;

	/**
	 * Generate Data list for Student Wise SwipIn/SwipOut Attendance in school.
	 *
	 * @param studentWiseSwipInOutTemplate
	 *            of type StudentWiseSwipInSwipOutAttendanceTemplate
	 * @param addmissinNo
	 *            - addmissinNo
	 * @return JRBeanCollectionDataSource
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing
	 */
	JRBeanCollectionDataSource getStudentWiseSwipInOutSummary(
			StudentWiseSwipInOutTemplate studentWiseSwipInOutTemplate,
			String addmissinNo) throws AkuraAppException;

	/**
	 * Generate Data list for Student Wise Attendance in school.
	 *
	 * @param teacherLateAttendiesTemplate
	 *            of type TeacherLateAttendiesTemplate
	 * @return JRBeanCollectionDataSource
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing
	 */
	JRBeanCollectionDataSource getStudentLateAttendiesSummary(
			TeacherLateAttendiesTemplate teacherLateAttendiesTemplate)
			throws AkuraAppException;

	/**
	 * Generate Data list for student early leavers .
	 *
	 * @param earlyLateLeaversTemplate
	 *            of type EarlyLateLeaversTemplate
	 * @return JRBeanCollectionDataSource
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing
	 */
	JRBeanCollectionDataSource getEarlyLeaversStudentSummary(
			LateLeaversTemplate earlyLateLeaversTemplate)
			throws AkuraAppException;

	/**
	 * Generate Data list for student early leavers .
	 *
	 * @param earlyLateLeaversTemplate
	 *            of type EarlyLeaversTemplate
	 * @return JRBeanCollectionDataSource
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing
	 */
	JRBeanCollectionDataSource getEarlyLeaversStudentSummary(
			EarlyLeaversTemplate earlyLateLeaversTemplate)
			throws AkuraAppException;

	 /**
     * Generate Data list of students.
     *
     * @param selectedDate of type string
     * @return list of type StudentAttendance
     * @throws AkuraAppException
     *             - The exception details that occurred when processing
     */
   List<StudentAttendance> getStudentAttandanceList(String selectedDate)
            throws AkuraAppException;

	/**
	 * Generate Data list of annual students attendance.
	 *
	 * @param classGradeId of type int.
	 * @return list of type AnnualStudentAttendanceTemplate
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing
	 */
	List<AnnualStudentAttendanceTemplate> getAnnualStudentAttandanceList(
			int classGradeId) throws AkuraAppException;

	/**
     * Generate Data list for best student attendance .
     *
     * @param bestStudentAttendanceTemplate
     *            of type BestStudentAttendanceTemplate
     * @return JRBeanCollectionDataSource
     * @throws AkuraAppException
     *             - The exception details that occurred when processing
     */
    JRBeanCollectionDataSource getBestStudentAttendance(
            BestStudentAttendanceTemplate bestStudentAttendanceTemplate)
            throws AkuraAppException;


}
