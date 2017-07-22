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

import java.lang.reflect.Array;
import java.util.Calendar;
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
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.api.exception.NotCoverMinimumAttendanceException;
import com.virtusa.akura.reporting.dao.StudentAttendanceDao;
import com.virtusa.akura.reporting.dao.TeacherAttendanceDao;
import com.virtusa.akura.util.HolidayUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.StaticDataUtil;

/**
 * AttendanceReportingServiceImpl for Reporting services related to teacher and student attendance.
 *
 * @author Virtusa Corporation
 */

public class AttendanceReportingServiceImpl implements AttendanceReportingService {

    /** TeacherAttendanceDao attribute for holding TeacherAttendanceDao. */
    private TeacherAttendanceDao teacherAttendanceDao;

    /** StudentAttendanceDao attribute for holding StudentAttendanceDao. */
    private StudentAttendanceDao studentAttendanceDao;

    /** Constant for index value. */
    private static final int INDEX_FIVE = 5;

    /** Constant for index value. */
    private static final int INDEX_THREE = 3;

    /** attribute for holding percentage value. */
    private static final double SIXTY_PRESENT = 0.6;

    /** key to hold String "AkuraErrorMsg". */
    private static final String AKURA_ERROR_MSG = "AkuraErrorMsg";

    /** string for holding no minimum attendance error message. */
    private static final String NOT_MINIMUM_ATTENDANCE_ERROR = "BESTSTUDENT.REPORT.MINIMUM.ATTENDANCE.ERROR";

    /**
     * Adds a public setter to create an instance of type TeacherAttendanceDao.
     *
     * @param setTeacherAttendanceDao {@link TeacherAttendanceDao}.
     */
    public void setTeacherAttendanceDao(TeacherAttendanceDao setTeacherAttendanceDao) {

        this.teacherAttendanceDao = setTeacherAttendanceDao;
    }

    /**
     * Adds a public setter to create an instance of type StudentAttendanceDao.
     *
     * @param setStudentAttendanceDao {@link StudentAttendanceDao}.
     */
    public void setStudentAttendanceDao(StudentAttendanceDao setStudentAttendanceDao) {

        this.studentAttendanceDao = setStudentAttendanceDao;
    }

    /**
     * Generate data list for Teacher wise attendance summary.
     *
     * @param teacherWiseAttendanceTemplate of type TeacherWiseAttendanceTemplate to get inputs from JSP
     * @return JRBeanCollectionDataSource
     * @throws AkuraAppException - The exception details that occurred when processing
     */

    public JRBeanCollectionDataSource getTeacherWiseAttendanceSummary(
            TeacherWiseAttendanceTemplate teacherWiseAttendanceTemplate) throws AkuraAppException {

        return this.teacherAttendanceDao.getTeacherWiseAttendanceSummary(teacherWiseAttendanceTemplate);
    }

    /**
     * Generate data list for Teacher late attendees in a specific time range and specific time range .
     *
     * @param teacherLateAttendiesTemplate of type TeacherLateAttendiesTemplate to get inputs from JSP
     * @return JRBeanCollectionDataSource
     * @throws AkuraAppException - The exception details that occurred when processing
     */

    public JRBeanCollectionDataSource getTeacherLateAttendies(TeacherLateAttendiesTemplate teacherLateAttendiesTemplate)
            throws AkuraAppException {

        return this.teacherAttendanceDao.getTeacherLateAttendies(teacherLateAttendiesTemplate);
    }

    /**
     * Generate data list for Teacher early out breakdown in several time ranges .
     *
     * @param earlyComersTemplate of type earlyComersTemplate to get inputs from JSP
     * @return JRBeanCollectionDataSource
     * @throws AkuraAppException - The exception details that occurred when processing
     */

    public JRBeanCollectionDataSource getEarlyComersTeacherSummary(TeacherEarlyComersTemplate earlyComersTemplate)
            throws AkuraAppException {

        return this.teacherAttendanceDao.getEarlyComersTeacherSummary(earlyComersTemplate);
    }

    /**
     * {@inheritDoc}
     */
    public JRBeanCollectionDataSource getStudentWiseSwipInOutSummary(
            StudentWiseSwipInOutTemplate studentWiseSwipInOutTemplate, String addmissionNo) throws AkuraAppException {

        return this.studentAttendanceDao.getStudentWiseSwipInOutSummary(studentWiseSwipInOutTemplate, addmissionNo);
    }

    /**
     * {@inheritDoc}
     */
    public JRBeanCollectionDataSource getStudentWiseAttendanceSummary(
            StudentWiseAttendanceTemplate studentWiseAttendanceTemplate) throws AkuraAppException {

        return this.studentAttendanceDao.getStudentWiseAttendanceSummary(studentWiseAttendanceTemplate);
    }

    /**
     * {@inheritDoc}
     */
    public JRBeanCollectionDataSource getStudentLateAttendeesSummary(
            TeacherLateAttendiesTemplate teacherLateAttendiesTemplate) throws AkuraAppException {

        return this.studentAttendanceDao.getStudentLateAttendiesSummary(teacherLateAttendiesTemplate);
    }

    /**
     * {@inheritDoc}
     */
    public JRBeanCollectionDataSource getEarlyLeaversStudentSummary(LateLeaversTemplate earlyLateLeaversTemplate)
            throws AkuraAppException {

        return this.studentAttendanceDao.getEarlyLeaversStudentSummary(earlyLateLeaversTemplate);

    }

    /**
     * {@inheritDoc}
     */
    public JRBeanCollectionDataSource getEarlyLeaversStudentSummary(EarlyLeaversTemplate earlyLateLeaversTemplate)
            throws AkuraAppException {

        return this.studentAttendanceDao.getEarlyLeaversStudentSummary(earlyLateLeaversTemplate);

    }

    /**
     * Generate Data list of students.
     *
     * @param selectedDate of type string
     * @return list of type StudentAttendance
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    public List<StudentAttendance> getStudentAttandanceList(String selectedDate) throws AkuraAppException {

        return this.studentAttendanceDao.getStudentAttandanceList(selectedDate);
    }

    /**
     * Generate Data list of Teacher Attendance.
     *
     * @param teacherWisePresentAbsentTemplate of type TeacherWisePresentAbsentTemplate
     * @return List of TeacherAttendance type.
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    public List<TeacherAttendance> teacherWisePresentAndAbsentDays(
            TeacherWisePresentAbsentTemplate teacherWisePresentAbsentTemplate) throws AkuraAppException {

        return teacherAttendanceDao.teacherWisePresentAndAbsentDays(teacherWisePresentAbsentTemplate);
    }

    /**
     * Generate Data list of Annual student attendance report.
     *
     * @param classId of type int.
     * @param startDate of type Date.
     * @param holidayList of type List.
     * @return List of AnnualStudentAttendanceTemplate type.
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    public List<AnnualStudentAttendanceTemplate> getAnnualStudentAttendancePercentage(int classId, Date startDate,
            List<Holiday> holidayList) throws AkuraAppException {

        // get annual student attendance list by class.
        List<AnnualStudentAttendanceTemplate> annualStudentAttendanceList =
                studentAttendanceDao.getAnnualStudentAttandanceList(classId);
        if (!annualStudentAttendanceList.isEmpty()) {

            Calendar currentDate = Calendar.getInstance();

            Calendar startDateCal = Calendar.getInstance();
            startDateCal.setTime(startDate);

            // get the working days till current date.
            int workingDays = HolidayUtil.countWorkingDays(startDateCal, currentDate, holidayList);

            for (AnnualStudentAttendanceTemplate annualStudentAttendance : annualStudentAttendanceList) {

                if (workingDays != 0) {
                    // calculate the percentage and set percentage value.
                    annualStudentAttendance.setAttendancePercentage(calculateStudentAttendancePercentage(
                            (double) annualStudentAttendance.getPresentCount(), workingDays));
                } else {
                    annualStudentAttendance.setPresentCount(0);
                }

            }

        }

        return annualStudentAttendanceList;

    }

    /**
     * Calculate attendance percentage for Annual student attendance report.
     *
     * @param presentCount of type double.
     * @param workingDays of type int.
     * @return String value.
     */
    private String calculateStudentAttendancePercentage(double presentCount, int workingDays) {

        // calculate the attendance percentage.
        double attendance = presentCount / workingDays * AkuraConstant.HUNDRED;

        // set percentage to two floating point and convert to string.
        String attendancePercentage = StaticDataUtil.setNoOfDigitsInNumber(AkuraConstant.FLOAT_POINT_TWO, (attendance));

        // adding percentage mark.
        return attendancePercentage.concat(AkuraConstant.PERCENTAGE_MARK);
    }

    /**
     * Generate Report for Grade Wise Best Student Attendance in school.
     *
     * @param bestAttendanceTemplate of type BestStudentAttendanceTemplate
     * @return JRBeanCollectionDataSource
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    public JRBeanCollectionDataSource getBestStudentAttendance(BestStudentAttendanceTemplate bestAttendanceTemplate)
            throws AkuraAppException {

        return this.studentAttendanceDao.getBestStudentAttendance(bestAttendanceTemplate);

    }

    /** {@inheritDoc} */
    public void setDataToReportTemplate(String gradeDescription, ClassGrade classGradeObj, int workingDays,
            List<Object> array, List<BestStudentAttendanceTemplate> reportTempList) throws AkuraAppException,
            NotCoverMinimumAttendanceException {

        BestStudentAttendanceTemplate reportDataTemp;

        // if array list is not empty
        if (!array.isEmpty()) {
            for (Object arr : array) {

                // get present day count from a particular student object
                int presentDayCount = Integer.parseInt((String.valueOf(Array.get(arr, 0))));

                // check the condition if that present day count > = working days * (60/100)
                if (presentDayCount >= Math.round(workingDays * SIXTY_PRESENT)) {

                    // create a new BestStudentAttendanceTemplate object
                    reportDataTemp = new BestStudentAttendanceTemplate();

                    // set data for report data template
                    reportDataTemp.setGradeDescription(gradeDescription);
                    reportDataTemp.setPresentDays(presentDayCount);
                    reportDataTemp.setStudentID(String.valueOf(Array.get(arr, 1)));
                    reportDataTemp.setStudentName(String.valueOf(Array.get(arr, INDEX_FIVE)));
                    reportDataTemp.setClassDescription(String.valueOf(Array.get(arr, INDEX_THREE)));

                    if (classGradeObj != null) {
                        reportDataTemp.setClassDescription(classGradeObj.getDescription());
                    }

                    // add report data template to the report template list
                    reportTempList.add(reportDataTemp);
                }
            }

            // pass that report data template to the JRBeanCollectionDataSource
            JRBeanCollectionDataSource jRBeanDataSource = new JRBeanCollectionDataSource(reportTempList);

            if (jRBeanDataSource.getRecordCount() == 0) {
                throw new NotCoverMinimumAttendanceException(PropertyReader.getPropertyValue(AKURA_ERROR_MSG,
                        NOT_MINIMUM_ATTENDANCE_ERROR));
            }
        }
    }
}
