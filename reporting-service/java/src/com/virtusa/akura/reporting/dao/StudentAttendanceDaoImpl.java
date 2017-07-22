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

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
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
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * Class to implement DAO methods specified in StudentAttendanceDao Interface.
 */
@SuppressWarnings("unchecked")
public class StudentAttendanceDaoImpl extends BaseDaoImpl<BaseDomain> implements StudentAttendanceDao {

    /** string attribute for report data - total number of students. */
    private static final String TAOTAL_NUMBER_OF_STUDENTS = "REPORT.TOTAL.NUMBER.OF.STUDENTS";

    /** ho;ding string of sp call name. */
    private static final String CALL_STUDENT_GAMIFICATION_SEARCH = "callStudentGamificationSearch";

    /** parameter name attribute for date. */
    private static final String PARA_NAME_ATT_DATE = "date";

    /** parameter name attribute for time out to. */
    private static final String PARA_NAME_ATT_TIME_OUT_TO = "timeOutTo";

    /** parameter name attribute for time out from. */
    private static final String PARA_NAME_TIME_OUT_FROM = "timeOutFrom";

    /** parameter name attribute for time in to. */
    private static final String PARA_NAME_ATT_TIME_IN_TO = "timeInTo";

    /** parameter name attribute for time in from. */
    private static final String PARA_NAME_ATT_TIME_IN_FROM = "timeInFrom";

    /** parameter name attribute for to date. */
    private static final String PARA_NAME_ATT_DATE_TO = "dateTo";

    /** parameter name attribute for from date. */
    private static final String PARA_NAME_ATT_DATE_FROM = "dateFrom";

    /** parameter name attribute for student id. */
    private static final String PARA_NAME_ATT_STUDENT_ID = "studentID";

    /** parameter name attribute for class grade id. */
    private static final String PARA_NAME_ATT_CLASS_GRADE_ID = "classGradeId";

    /**
     * {@inheritDoc}
     */
    public JRBeanCollectionDataSource getStudentWiseAttendanceSummary(
            StudentWiseAttendanceTemplate studentWiseAttendanceTemplate) throws AkuraAppException {

        String qryString =
                "from StudentAttendance o Where o.studentID=:studentID and o.date>=:dateFrom and " + "o.date<=:dateTo";

        String[] paramNames = new String[AkuraConstant.PARAM_INDEX_THREE];
        paramNames[0] = PARA_NAME_ATT_STUDENT_ID;
        paramNames[1] = PARA_NAME_ATT_DATE_FROM;
        paramNames[2] = PARA_NAME_ATT_DATE_TO;

        Object[] paramValues = new Object[AkuraConstant.PARAM_INDEX_THREE];
        paramValues[0] = studentWiseAttendanceTemplate.getStudentID();
        paramValues[1] = studentWiseAttendanceTemplate.getDateFrom();
        paramValues[2] = studentWiseAttendanceTemplate.getDateTo();

        try {
            return new JRBeanCollectionDataSource(getHibernateTemplate().findByNamedParam(qryString, paramNames,
                    paramValues));
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }

    }

    /**
     * {@inheritDoc}
     */
    public JRBeanCollectionDataSource getStudentLateAttendiesSummary(
            TeacherLateAttendiesTemplate studentAttendanceWrapperTemplate) throws AkuraAppException {

        String qryString =
                "from StudentAttendance o Where o.date>=:dateFrom and o.date<=:dateTo and"
                        + " o.timeIn>=:timeInFrom and o.timeIn<=:timeInTo";

        String[] paramNames = new String[AkuraConstant.PARAM_INDEX_FOUR];
        paramNames[0] = PARA_NAME_ATT_DATE_FROM;
        paramNames[1] = PARA_NAME_ATT_DATE_TO;
        paramNames[2] = PARA_NAME_ATT_TIME_IN_FROM;
        paramNames[AkuraConstant.PARAM_INDEX_THREE] = PARA_NAME_ATT_TIME_IN_TO;

        Object[] paramValues = new Object[AkuraConstant.PARAM_INDEX_FOUR];
        paramValues[0] = studentAttendanceWrapperTemplate.getDateFrom();
        paramValues[1] = studentAttendanceWrapperTemplate.getDateTo();
        paramValues[2] = studentAttendanceWrapperTemplate.getTimeInFrom();
        paramValues[AkuraConstant.PARAM_INDEX_THREE] = studentAttendanceWrapperTemplate.getTimeInTo();

        try {
            return new JRBeanCollectionDataSource(getHibernateTemplate().findByNamedParam(qryString, paramNames,
                    paramValues));
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }

    }

    /**
     * {@inheritDoc}
     */
    public JRBeanCollectionDataSource getEarlyLeaversStudentSummary(LateLeaversTemplate earlyLateLeaversTemplate)
            throws AkuraAppException {

        String qryString =
                "from StudentAttendance o Where o.date>=:dateFrom and o.date<=:dateTo and "
                        + "o.timeOut>=:timeOutFrom and o.timeOut<=:timeOutTo";

        String[] paramNames = new String[AkuraConstant.PARAM_INDEX_FOUR];
        paramNames[0] = PARA_NAME_ATT_DATE_FROM;
        paramNames[1] = PARA_NAME_ATT_DATE_TO;
        paramNames[2] = PARA_NAME_TIME_OUT_FROM;
        paramNames[AkuraConstant.PARAM_INDEX_THREE] = PARA_NAME_ATT_TIME_OUT_TO;

        Object[] paramValues = new Object[AkuraConstant.PARAM_INDEX_FOUR];
        paramValues[0] = earlyLateLeaversTemplate.getDateFrom();
        paramValues[1] = earlyLateLeaversTemplate.getDateTo();
        paramValues[2] = earlyLateLeaversTemplate.getTimeOutFrom();
        paramValues[AkuraConstant.PARAM_INDEX_THREE] = earlyLateLeaversTemplate.getTimeOutTo();

        try {
            return new JRBeanCollectionDataSource(getHibernateTemplate().findByNamedParam(qryString, paramNames,
                    paramValues));
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }

    }

    /**
     * {@inheritDoc}
     */
    public JRBeanCollectionDataSource getEarlyLeaversStudentSummary(EarlyLeaversTemplate earlyLateLeaversTemplate)
            throws AkuraAppException {

        String qryString =
                "from StudentAttendance o Where o.date>=:dateFrom and o.date<=:dateTo and "
                        + "o.timeOut>=:timeOutFrom and o.timeOut<=:timeOutTo";

        String[] paramNames = new String[AkuraConstant.PARAM_INDEX_FOUR];
        paramNames[0] = PARA_NAME_ATT_DATE_FROM;
        paramNames[1] = PARA_NAME_ATT_DATE_TO;
        paramNames[2] = PARA_NAME_TIME_OUT_FROM;
        paramNames[AkuraConstant.PARAM_INDEX_THREE] = PARA_NAME_ATT_TIME_OUT_TO;

        Object[] paramValues = new Object[AkuraConstant.PARAM_INDEX_FOUR];
        paramValues[0] = earlyLateLeaversTemplate.getDateFrom();
        paramValues[1] = earlyLateLeaversTemplate.getDateTo();
        paramValues[2] = earlyLateLeaversTemplate.getTimeOutFrom();
        paramValues[AkuraConstant.PARAM_INDEX_THREE] = earlyLateLeaversTemplate.getTimeOutTo();

        try {
            return new JRBeanCollectionDataSource(getHibernateTemplate().findByNamedParam(qryString, paramNames,
                    paramValues));
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }

    }

    /**
     * {@inheritDoc}
     */
    public List<StudentAttendance> getStudentAttandanceList(String date) throws AkuraAppException {

        String qryString = "from StudentAttendance o where o.date =:date ";
        String[] paramNames = new String[1];
        paramNames[0] = PARA_NAME_ATT_DATE;

        Object[] paramValues = new Object[1];
        paramValues[0] = date;

        try {
            return getHibernateTemplate().findByNamedParam(qryString, paramNames, paramValues);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }

    }

    /**
     * {@inheritDoc}
     */
    public JRBeanCollectionDataSource getStudentWiseSwipInOutSummary(
            StudentWiseSwipInOutTemplate studentWiseSwipInOutTemplate, String addmissionNo) throws AkuraAppException {

        String qryString =
                "from StudentAttendance o Where o.studentID=:studentID and o.date>=:dateFrom and " + "o.date<=:dateTo";

        String[] paramNames = new String[AkuraConstant.PARAM_INDEX_THREE];

        paramNames[0] = PARA_NAME_ATT_STUDENT_ID;
        paramNames[1] = PARA_NAME_ATT_DATE_FROM;
        paramNames[2] = PARA_NAME_ATT_DATE_TO;

        Object[] paramValues = new Object[AkuraConstant.PARAM_INDEX_THREE];

        paramValues[0] = addmissionNo;
        paramValues[1] = studentWiseSwipInOutTemplate.getDateFrom();
        paramValues[2] = studentWiseSwipInOutTemplate.getDateTo();

        try {
            return new JRBeanCollectionDataSource(getHibernateTemplate().findByNamedParam(qryString, paramNames,
                    paramValues));
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
    }

    /**
     * Generate Data list of annual students attendance.
     *
     * @param classGradeId of type int.
     * @return list of type AnnualStudentAttendanceTemplate
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    public List<AnnualStudentAttendanceTemplate> getAnnualStudentAttandanceList(int classGradeId)
            throws AkuraAppException {

        String qryString = "from AnnualStudentAttendanceTemplate o where o.classGradeId =:classGradeId";
        String[] paramNames = new String[AkuraConstant.PARAM_INDEX_ONE];

        paramNames[0] = PARA_NAME_ATT_CLASS_GRADE_ID;

        Object[] paramValues = new Object[AkuraConstant.PARAM_INDEX_ONE];
        paramValues[0] = classGradeId;

        try {
            return getHibernateTemplate().findByNamedParam(qryString, paramNames, paramValues);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
    }

    /**
     * Generate Data list for best student attendance .
     *
     * @param bestStudentAttendanceTemplate of type BestStudentAttendanceTemplate
     * @return JRBeanCollectionDataSource
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    public JRBeanCollectionDataSource getBestStudentAttendance(
            BestStudentAttendanceTemplate bestStudentAttendanceTemplate) throws AkuraAppException {

        int gradeId = bestStudentAttendanceTemplate.getGradeId();
        int classId = bestStudentAttendanceTemplate.getClassId();
        int year = bestStudentAttendanceTemplate.getYear();
        int monthId = bestStudentAttendanceTemplate.getMonthId();

        try {

            List<Object> arrayList = new ArrayList<Object>();

            arrayList.addAll(getHibernateTemplate().findByNamedQuery(CALL_STUDENT_GAMIFICATION_SEARCH, 0,
                    PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, TAOTAL_NUMBER_OF_STUDENTS), gradeId,
                    classId, year, monthId));

            return new JRBeanCollectionDataSource(arrayList);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
    }
}
