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

import com.virtusa.akura.api.dto.AnnualStudentAttendanceTemplate;
import com.virtusa.akura.api.dto.BestStudentAttendanceTemplate;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.EarlyLeaversTemplate;
import com.virtusa.akura.api.dto.Holiday;
import com.virtusa.akura.api.dto.LateLeaversTemplate;
import com.virtusa.akura.api.dto.StudentAttendance;
import com.virtusa.akura.api.dto.StudentWiseAttendanceTemplate;
import com.virtusa.akura.api.dto.StudentWiseSwipInOutTemplate;
import com.virtusa.akura.api.dto.TeacherAttendance;
import com.virtusa.akura.api.dto.TeacherEarlyComersTemplate;
import com.virtusa.akura.api.dto.TeacherLateAttendiesTemplate;
import com.virtusa.akura.api.dto.TeacherWiseAttendanceTemplate;
import com.virtusa.akura.api.dto.TeacherWisePresentAbsentTemplate;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.NotCoverMinimumAttendanceException;

/**
 * Interface for services provided for teacher and student attendance reporting.
 *
 * @author Virtusa Corporation
 */

public interface AttendanceReportingService {

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
     * Generate data list for Teacher wise attendance summary.
     *
     * @param teacherWiseAttendanceTemplate of type TeacherWiseAttendanceTemplate to get inputs from JSP
     * @return JRBeanCollectionDataSource
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    JRBeanCollectionDataSource getTeacherWiseAttendanceSummary(
            TeacherWiseAttendanceTemplate teacherWiseAttendanceTemplate) throws AkuraAppException;

    /**
     * Generate data list for Teacher wise attendance summary.
     *
     * @param studentWiseSwipInOutTemplate of type studentWiseSwipInOutTemplate to get inputs from JSP
     * @param addmissionNo admission number.
     * @return JRBeanCollectionDataSource
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    JRBeanCollectionDataSource getStudentWiseSwipInOutSummary(
            StudentWiseSwipInOutTemplate studentWiseSwipInOutTemplate, String addmissionNo) throws AkuraAppException;

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
     * Generate Report for Student Wise Attendance in school.
     *
     * @param studentWiseAttendanceTemplate of type StudentWiseAttendanceTemplate
     * @return JRBeanCollectionDataSource
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    JRBeanCollectionDataSource getStudentWiseAttendanceSummary(
            StudentWiseAttendanceTemplate studentWiseAttendanceTemplate) throws AkuraAppException;

    /**
     * Generate Report for Student Wise Attendance in school.
     *
     * @param teacherLateAttendiesTemplate of type TeacherLateAttendiesTemplate
     * @return JRBeanCollectionDataSource
     * @throws AkuraAppException - The exception details that occurred when processing
     */

    JRBeanCollectionDataSource getStudentLateAttendeesSummary(TeacherLateAttendiesTemplate teacherLateAttendiesTemplate)
            throws AkuraAppException;

    /**
     * Generate Data list for student early leavers .
     *
     * @param earlyLateLeaversTemplate of type LateLeaversTemplate
     * @return JRBeanCollectionDataSource
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    JRBeanCollectionDataSource getEarlyLeaversStudentSummary(LateLeaversTemplate earlyLateLeaversTemplate)
            throws AkuraAppException;

    /**
     * Generate Data list for student early leavers .
     *
     * @param earlyLateLeaversTemplate of type EarlyLeaversTemplate
     * @return JRBeanCollectionDataSource
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    JRBeanCollectionDataSource getEarlyLeaversStudentSummary(EarlyLeaversTemplate earlyLateLeaversTemplate)
            throws AkuraAppException;

    /**
     * Generate Data list of students.
     *
     * @param selectedDate of type string
     * @return list of type StudentAttendance
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    List<StudentAttendance> getStudentAttandanceList(String selectedDate) throws AkuraAppException;

    /**
     * Generate data list for Teacher wise attendance summary.
     *
     * @param teacherWisePresentAbsentTemplate of type TeacherWiseAttendanceTemplate to get inputs from JSP
     * @return List of TeacherAttendance type.
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    List<TeacherAttendance> teacherWisePresentAndAbsentDays(
            TeacherWisePresentAbsentTemplate teacherWisePresentAbsentTemplate) throws AkuraAppException;

    /**
     * Generate Data list of Annual student attendance report.
     *
     * @param classId of type int.
     * @param startDate of type Date.
     * @param holidayList of type List.
     * @return List of AnnualStudentAttendanceTemplate type.
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    List<AnnualStudentAttendanceTemplate> getAnnualStudentAttendancePercentage(int classId, Date startDate,
            List<Holiday> holidayList) throws AkuraAppException;

    /**
     * Generate Report for Grade Wise Best Student Attendance in school.
     *
     * @param bestStudentAttendanceTemplate of type BestStudentAttendanceTemplate
     * @return JRBeanCollectionDataSource
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    JRBeanCollectionDataSource getBestStudentAttendance(BestStudentAttendanceTemplate bestStudentAttendanceTemplate)
            throws AkuraAppException;

    /**
     * Method is to set data for report template by checking the condition, presentDayCount >= workingDays *
     * (60/100).
     *
     * @param gradeDescription - gradeDescription.
     * @param workingDays - workingDays .
     * @param classGradeObj - classGradeObj.
     * @param array - object array.
     * @param reportTempList - reportTemplateList.
     * @throws AkuraAppException - AkuraAppException.
     * @throws NotCoverMinimumAttendanceException - NotCoverMinimumAttendanceException.
     */
    void setDataToReportTemplate(String gradeDescription, ClassGrade classGradeObj, int workingDays,
            List<Object> array, List<BestStudentAttendanceTemplate> reportTempList) throws AkuraAppException,
            NotCoverMinimumAttendanceException;
}
