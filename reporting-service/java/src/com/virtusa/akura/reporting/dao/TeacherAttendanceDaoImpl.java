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

import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.BaseDomain;
import com.virtusa.akura.api.dto.TeacherAttendance;
import com.virtusa.akura.api.dto.TeacherEarlyComersTemplate;
import com.virtusa.akura.api.dto.TeacherLateAttendiesTemplate;
import com.virtusa.akura.api.dto.TeacherWiseAttendanceTemplate;
import com.virtusa.akura.api.dto.TeacherWisePresentAbsentTemplate;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * Class to implement DAO methods specified in TeacherAttendanceDao.
 */
@SuppressWarnings("unchecked")
public class TeacherAttendanceDaoImpl extends BaseDaoImpl<BaseDomain> implements TeacherAttendanceDao {

    /** parameter name for employee id. */
    private static final String PARA_NAME_TIME_IN_TO = "timeInTo";

    /** parameter name for time in from. */
    private static final String PARA_NAME_TIME_IN_FROM = "timeInFrom";

    /** parameter name for date to. */
    private static final String PARA_NAME_DATE_TO = "dateTo";

    /** parameter name for date from. */
    private static final String PARA_NAME_DATE_FROM = "dateFrom";

    /** parameter name for employee id. */
    private static final String PARA_NAME_EMPLOYEE_ID = "employeeID";

    /**
     * Generate data list for Teacher wise attendance summary.
     *
     * @param teacherWiseAttendanceTemplate of type TeacherWiseAttendanceTemplate to get inputs from JSP
     * @return JRBeanCollectionDataSource
     * @throws AkuraAppException - The exception details that occurred when processing
     */

    public JRBeanCollectionDataSource getTeacherWiseAttendanceSummary(
            TeacherWiseAttendanceTemplate teacherWiseAttendanceTemplate) throws AkuraAppException {

        String qryString =
                "from TeacherAttendance o Where o.employeeID=:employeeID and o.date>=:dateFrom and"
                        + " o.date<=:dateTo";
        String[] paramNames = new String[AkuraConstant.PARAM_INDEX_THREE];
        paramNames[0] = PARA_NAME_EMPLOYEE_ID;
        paramNames[1] = PARA_NAME_DATE_FROM;
        paramNames[2] = PARA_NAME_DATE_TO;

        Object[] paramValues = new Object[AkuraConstant.PARAM_INDEX_THREE];
        paramValues[0] = teacherWiseAttendanceTemplate.getTeacherNo();
        paramValues[1] = teacherWiseAttendanceTemplate.getDateFrom();
        paramValues[2] = teacherWiseAttendanceTemplate.getDateTo();

        try {
            return new JRBeanCollectionDataSource(getHibernateTemplate().findByNamedParam(qryString, paramNames,
                    paramValues));
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }

    }

    /**
     * Generate data list for Teacher late attendees in a specific time range and specific time range .
     *
     * @param teacherLateAttendiesTemplate of type TeacherLateAttendiesTemplate to get inputs from JSP
     * @return JRBeanCollectionDataSource
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    public JRBeanCollectionDataSource getTeacherLateAttendies(TeacherLateAttendiesTemplate
            teacherLateAttendiesTemplate)throws AkuraAppException {

        String qryString =
                "from TeacherAttendance o Where o.date>=:dateFrom and o.date<=:dateTo and"
                        + " o.timeIn>=:timeInFrom and o.timeIn<=:timeInTo";

        String[] paramNames = new String[AkuraConstant.PARAM_INDEX_FOUR];
        paramNames[0] = PARA_NAME_DATE_FROM;
        paramNames[1] = PARA_NAME_DATE_TO;
        paramNames[2] = PARA_NAME_TIME_IN_FROM;
        paramNames[AkuraConstant.PARAM_INDEX_THREE] = PARA_NAME_TIME_IN_TO;

        Object[] paramValues = new Object[AkuraConstant.PARAM_INDEX_FOUR];
        paramValues[AkuraConstant.PARAM_INDEX_ZERO] = teacherLateAttendiesTemplate.getDateFrom();
        paramValues[AkuraConstant.PARAM_INDEX_ONE] = teacherLateAttendiesTemplate.getDateTo();
        paramValues[AkuraConstant.PARAM_INDEX_TWO] = teacherLateAttendiesTemplate.getTimeInFrom();
        paramValues[AkuraConstant.PARAM_INDEX_THREE] = teacherLateAttendiesTemplate.getTimeInTo();

        try {
            return new JRBeanCollectionDataSource(getHibernateTemplate().findByNamedParam(qryString, paramNames,
                    paramValues));
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
    }

    /**
     * Generate data list for Teacher early out breakdown in several time ranges .
     *
     * @param teacherEarlyComersTemplate of type TeacherEarlyComersTemplate to get inputs from JSP
     * @return JRBeanCollectionDataSource
     * @throws AkuraAppException AkuraAppException
     */

    public JRBeanCollectionDataSource getEarlyComersTeacherSummary(
            TeacherEarlyComersTemplate teacherEarlyComersTemplate) throws AkuraAppException {

        String qryString =
                "from TeacherAttendance o Where o.date>=:dateFrom and "
                        + " o.date<=:dateTo and o.timeIn>=:timeInFrom and o.timeIn<=:timeInTo";

        String[] paramNames = new String[AkuraConstant.PARAM_INDEX_FOUR];
        paramNames[0] = PARA_NAME_DATE_FROM;
        paramNames[1] = PARA_NAME_DATE_TO;
        paramNames[2] = PARA_NAME_TIME_IN_FROM;
        paramNames[AkuraConstant.PARAM_INDEX_THREE] = PARA_NAME_TIME_IN_TO;

        Object[] paramValues = new Object[AkuraConstant.PARAM_INDEX_FOUR];
        paramValues[0] = teacherEarlyComersTemplate.getDateFrom();
        paramValues[1] = teacherEarlyComersTemplate.getDateTo();
        paramValues[2] = teacherEarlyComersTemplate.getTimeInFrom();
        paramValues[AkuraConstant.PARAM_INDEX_THREE] = teacherEarlyComersTemplate.getTimeInTo();

        try {
            return new JRBeanCollectionDataSource(getHibernateTemplate().findByNamedParam(qryString, paramNames,
                    paramValues));
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
    }

    /**
     * Generate data list for Teacher wise attendance summary.
     *
     * @param teacherWisePresentAbsentTemplate of type TeacherWisePresentAbsentTemplate to get inputs from JSP
     * @return List type of TeacherAttendance.
     * @throws AkuraAppException - The exception details that occurred when processing
     */

    public List<TeacherAttendance> teacherWisePresentAndAbsentDays(
            TeacherWisePresentAbsentTemplate teacherWisePresentAbsentTemplate) throws AkuraAppException {

        String qryString =
                "from TeacherAttendance o Where o.employeeID=:employeeID and o.date>=:dateFrom and"
                        + " o.date<=:dateTo";
        String[] paramNames = new String[AkuraConstant.PARAM_INDEX_THREE];
        paramNames[0] = PARA_NAME_EMPLOYEE_ID;
        paramNames[1] = PARA_NAME_DATE_FROM;
        paramNames[2] = PARA_NAME_DATE_TO;

        Object[] paramValues = new Object[AkuraConstant.PARAM_INDEX_THREE];
        paramValues[0] = teacherWisePresentAbsentTemplate.getTeacherRegNo();
        paramValues[1] = teacherWisePresentAbsentTemplate.getDateFrom();
        paramValues[2] = teacherWisePresentAbsentTemplate.getDateTo();

        try {
            return getHibernateTemplate().findByNamedParam(qryString, paramNames,
                    paramValues);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }

    }
}
