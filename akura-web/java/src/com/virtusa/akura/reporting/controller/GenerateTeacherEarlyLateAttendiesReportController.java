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

package com.virtusa.akura.reporting.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.virtusa.akura.api.dto.AttendeesWrapperTemplate;
import com.virtusa.akura.api.dto.StaffLeave;
import com.virtusa.akura.api.dto.TeacherAttendance;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.reporting.service.AttendanceReportingService;
import com.virtusa.akura.reporting.validator.LateComersValidator;
import com.virtusa.akura.staff.service.StaffService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * Controller class to generate reports for both teacher late attendees and teacher early comers.
 */
@Controller
@SessionAttributes("attendeesWrapperTemplate")
public class GenerateTeacherEarlyLateAttendiesReportController {

    /** report key for day type. */
    private static final String REPORT_KEY_DATE_TYPE = "dateType";

    /** attribute for holding style path. */
    private static final String STYLE_PATH = "style.path";

    /** attribute for holding app home. */
    private static final String APPSERVER_HOME = "appserver.home";

    /** attribute for holding system config. */
    private static final String SYSTEM_CONFIG = "systemConfig";

    /** attribute for holding style template. */
    private static final String STYLE_TEMPLATE = "styleTemplate";

    /** variable for holding string "generatedDate". */
    private static final String GENERATED_DATE = "generatedDate";

    /** model attribute for report generated date. */
    private static final String REPORT_GENERATED_DATE_LOCALE = "REPORT.GENERATED.DATE.LOCALE";

    /** The Constant REPORT_PAGE. */
    private static final String REPORT_PAGE = "Report_page";

    /** The Constant PAGE. */
    private static final String PAGE = "page";

    /** The Constant GPL. */
    private static final String GPL = "GPLComment";

    /** The Constant REPORT_GENERATED_ON. */
    private static final String REPORT_GENERATED_ON = "reportGeneratedOn";

    /** The Constant REPORT_GENERATED_ON_TEXT. */
    private static final String REPORT_GENERATED_ON_TEXT = "Report_reportGeneratedOn";

    /** The Constant REPORT_DATE_TYPE_ON_TEXT. */
    private static final String REPORT_DATE_TYPE_ON_TEXT = "Report_dateType";

    /** The Constant STAFF_WISE_ATTENDEES_DATE_TO. */
    private static final String STAFF_WISE_ATTENDEES_DATE_TO = "StaffWiseAttendees_dateTo";

    /** The Constant STAFF_WISE_ATTENDEES_DATE_FROM. */
    private static final String STAFF_WISE_ATTENDEES_DATE_FROM = "StaffWiseAttendees_dateFrom";

    /** The Constant STAFF_WISE_ATTENDEES_DATE_TO. */
    private static final String STAFF_WISE_ATTENDEES_TIME_IN_FROM = "StaffLateAttendees_timeInFrom";

    /** The Constant STAFF_WISE_ATTENDEES_DATE_FROM. */
    private static final String STAFF_WISE_ATTENDEES_TIME_IN_TO = "StaffLateAttendees_timeInTo";

    /** The Constant DATE_TO. */
    private static final String DATE_TO = "dateTo1";

    /** The Constant DATE_FROM. */
    private static final String DATE_FROM = "dateFrom1";

    /** The Constant Time In From. */
    private static final String TIME_IN_FROM = "timeInFrom1";

    /** The Constant Time In To. */
    private static final String TIME_IN_TO = "timeInTo1";

    /** attribute for holding string. */
    private static final String STRING_EMPTY = "";

    /** attribute for holding error message key. */
    public static final String NO_DATA_ERROR_MESSAGE = "REF.UI.NO.DATA.FOUND";

    /** attribute for holding message. */
    public static final String MESSAGE = "message";

    /** variable for hours. */
    private static final String VARIABLE_HOURS_HR = " Hr";

    /** report value for time out. */
    private static final String REPORT_VALUE_TIME_OUT = "StuLateAttendees_swipeOutText";

    /** report value for time in. */
    private static final String REPORT_VALUE_TIME_IN = "StuLateAttendees_swipeInText";

    /** report value for consider date. */
    private static final String REPORT_VALUE_DATE_CONSIDERED = "StuLateAttendees_dateConsideredText";

    /** report value for teacher name. */
    private static final String REPORT_VALUE_TEACHER_NAME = "StaffLateAttendees_staffNameText";

    /** report value for teacher id. */
    private static final String REPORT_VALUE_TEACHER_ID = "StaffLateAttendees_registrationNumberText";

    /** report value for title. */
    private static final String REPORT_VALUE_TEACHER_LATE_ATTEND_REPORT = "StaffLateAttendees_titleText";

    /** report parameter for logo. */
    private static final String REPORT_PARA_LOGO_PATH = "logoPath";

    /** report parameter for time in to. */
    private static final String REPORT_PARA_TIMEINTO = "timeinto";

    /** report parameter for time in from. */
    private static final String REPORT_PARA_TIMEINFROM = "timeinfrom";

    /** report parameter for date to. */
    private static final String REPORT_PARA_DATETO = "dateto";

    /** report parameter for date from. */
    private static final String REPORT_PARA_DATEFROM = "datefrom";

    /** report parameter for time out. */
    private static final String REPORT_PARA_TIMEOUT = "timeout";

    /** report parameter for time in. */
    private static final String REPORT_PARA_TIMEIN = "timein";

    /** report parameter for date. */
    private static final String REPORT_PARA_DATE = "date";

    /** report parameter for teacher name. */
    private static final String REPORT_PARA_TEACHERNAME = "teachername";

    /** report parameter for teacher id. */
    private static final String REPORT_PARA_TEACHERID = "teacherid";

    /** report parameter for title. */
    private static final String REPORT_PARA_TITLE = "title";

    /** model attribute for student template. */
    private static final String MODEL_ATT_ATTENDANCE_WRAPPER_TEMPLATE = "attendeesWrapperTemplate";

    /**
     * attendanceReportingService of type AttendanceReportingService to use methods related to attendance
     * Reporting .
     */

    /** request parameter for teacher late report. */
    private static final String REQUEST_PARA_TEACHER_LATE = "/teacherLateReport.htm";

    /** report parameter for title. */
    @Autowired
    private AttendanceReportingService attendanceReportingService;

    /** VIEW_PAGE for holding return jsp page. */
    private static final String VIEW_PAGE = "reporting/teacherEarlyLateAttendees";

    /** report page for holding return jrxml page. */
    private static final String REPORT_NAME = "TeacherEarlyComers";

    /**
     * lateComersValidator of type LateComersValidator .
     */

    private LateComersValidator lateComersValidator;

    /**
     * staffService To invoke service methods.
     */
    private StaffService staffService;

    /**
     * setter to inject StaffService object.
     *
     * @param staffServiceRef the staffService to set
     */

    public void setStaffService(StaffService staffServiceRef) {

        this.staffService = staffServiceRef;
    }

    /**
     * Set AttendanceReportingService.
     *
     * @param attendanceReportingServiceRef of type AttendanceReportingService
     */

    public void setAttendanceReportingService(AttendanceReportingService attendanceReportingServiceRef) {

        this.attendanceReportingService = attendanceReportingServiceRef;
    }

    /**
     * Set AttendanceReportingService.
     *
     * @param lateComersValidatorRef of type LateComersValidator
     */

    public void setLateComersValidator(LateComersValidator lateComersValidatorRef) {

        this.lateComersValidator = lateComersValidatorRef;
    }

    /**
     * Display Form View for of the controller and binding it to TeacherLateAttendiesTemplate.
     *
     * @param modelMap of type ModelMap
     * @return java.lang.String
     */

    @RequestMapping(method = RequestMethod.GET)
    public String showReportForm(ModelMap modelMap) {

        AttendeesWrapperTemplate attendeesWrapperTemplate = new AttendeesWrapperTemplate();
        modelMap.addAttribute(attendeesWrapperTemplate);

        return VIEW_PAGE;
    }

    /**
     * Perform the logic of the controller to generate Teacher Wise Attendance Report .
     *
     * @param attendeesWrapperTemplate of type AttendeesWrapperTemplate
     * @param request of type HttpServletRequest
     * @param response of type HttpServletResponse
     * @param errors of type BindingResult
     * @return java.lang.String
     * @param map of type ModelMap
     * @throws AkuraAppException AkuraAppException
     */

    @SuppressWarnings("unchecked")
    @RequestMapping(value = REQUEST_PARA_TEACHER_LATE, method = RequestMethod.POST)
    public String onSubmit(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute(MODEL_ATT_ATTENDANCE_WRAPPER_TEMPLATE) AttendeesWrapperTemplate attendeesWrapperTemplate,
            BindingResult errors, ModelMap map) throws AkuraAppException {

        String returnString = STRING_EMPTY;

        lateComersValidator.validate(attendeesWrapperTemplate, errors);

        if (errors.hasErrors()) {

            return VIEW_PAGE;
        }

        Map<String, Object> params = new HashMap<String, Object>();

        params.put(REPORT_PARA_TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_VALUE_TEACHER_LATE_ATTEND_REPORT));
        params.put(REPORT_PARA_TEACHERID, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_VALUE_TEACHER_ID));
        params.put(REPORT_PARA_TEACHERNAME, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_VALUE_TEACHER_NAME));
        params.put(REPORT_PARA_DATE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_VALUE_DATE_CONSIDERED));
        params.put(REPORT_PARA_TIMEIN, PropertyReader
                .getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_VALUE_TIME_IN));
        params.put(REPORT_PARA_TIMEOUT, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_VALUE_TIME_OUT));
        params.put(REPORT_PARA_DATEFROM, attendeesWrapperTemplate.getTeacherLateAttendiesTemplate().getDateFrom());
        params.put(REPORT_PARA_DATETO, attendeesWrapperTemplate.getTeacherLateAttendiesTemplate().getDateTo());
        params.put(REPORT_PARA_TIMEINFROM, attendeesWrapperTemplate.getTeacherLateAttendiesTemplate().getTimeInFrom()
                + VARIABLE_HOURS_HR);
        params.put(REPORT_PARA_TIMEINTO, attendeesWrapperTemplate.getTeacherLateAttendiesTemplate().getTimeInTo()
                + VARIABLE_HOURS_HR);
        params.put(REPORT_PARA_LOGO_PATH, ReportUtil.getReportLogo());
        params.put(STYLE_TEMPLATE, PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                + PropertyReader.getPropertyValue(SYSTEM_CONFIG, STYLE_PATH));
        params.put(DATE_FROM, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                STAFF_WISE_ATTENDEES_DATE_FROM));
        params.put(DATE_TO, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STAFF_WISE_ATTENDEES_DATE_TO));
        params.put(TIME_IN_FROM, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                STAFF_WISE_ATTENDEES_TIME_IN_FROM));
        params.put(TIME_IN_TO, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                STAFF_WISE_ATTENDEES_TIME_IN_TO));
        params.put(REPORT_GENERATED_ON, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_GENERATED_ON_TEXT));
        params.put(REPORT_KEY_DATE_TYPE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_DATE_TYPE_ON_TEXT));
        params.put(GENERATED_DATE, DateUtil.getReportGeneratedDate(PropertyReader.getPropertyValue(
                ReportUtil.REPORT_TEMPLATE, REPORT_GENERATED_DATE_LOCALE)));
        params.put(PAGE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_PAGE));
        params.put(GPL, AkuraWebConstant.REPORT_GPL);

        JRBeanCollectionDataSource jrBeanDataSource =
                attendanceReportingService.getTeacherLateAttendies(attendeesWrapperTemplate
                        .getTeacherLateAttendiesTemplate());

        List<TeacherAttendance> objList = null;

        if (jrBeanDataSource.getRecordCount() != 0) {
            objList = (List<TeacherAttendance>) jrBeanDataSource.getData();
        }

        Date fromDate = DateUtil.getParseDate(attendeesWrapperTemplate.getTeacherLateAttendiesTemplate().getDateFrom());
        Date toDate = DateUtil.getParseDate(attendeesWrapperTemplate.getTeacherLateAttendiesTemplate().getDateTo());

        if (objList != null) {
            for (TeacherAttendance obj : objList) {
                int staffId = obj.getStaffId();
                Date attDate = DateUtil.getParseDate(obj.getDate());

                List<StaffLeave> staffLeaveList =
                        staffService.getStaffLeaveListByDatePeriodAndStaffId(fromDate, toDate, staffId);

                if (!staffLeaveList.isEmpty()) {

                    for (StaffLeave sl : staffLeaveList) {

                        Date getFromDate = DateUtil.getParseDate(sl.getFromDate().toString());
                        Date getToDate = DateUtil.getParseDate(sl.getToDate().toString());

                        if ((sl.getStaffLeaveStatusId() == 1)
                                && (getFromDate.before(attDate) && getToDate.after(attDate))) {

                            setDayType(obj, sl);
                        } else if ((sl.getStaffLeaveStatusId() == 1)
                                && (getFromDate.equals(attDate) || getToDate.equals(attDate))) {
                            setDayType(obj, sl);
                        }
                    }
                }
            }
        }

        if (jrBeanDataSource.getRecordCount() != 0) {
            displayReportForm(response, jrBeanDataSource, params, REPORT_NAME);
        } else {
            returnString = VIEW_PAGE;
            setErrorMessage(map);
        }

        return returnString;

    }

    /**
     * set the day type.
     *
     * @param obj - TeacherAttendance object.
     * @param staffLeave - StaffLeave object.
     */
    private void setDayType(TeacherAttendance obj, StaffLeave staffLeave) {

        String dayType = staffLeave.getDateType();
        obj.setDateType(dayType);
    }

    /**
     * set the error messages when returning to the jsp.
     *
     * @param map - ModelMap
     */
    private void setErrorMessage(ModelMap map) {

        String message = new ErrorMsgLoader().getErrorMessage(NO_DATA_ERROR_MESSAGE);
        map.addAttribute(MESSAGE, message);
    }

    /**
     * Method to generate reports for both teacher late attendees & teacher early attendees.
     *
     * @param response of type HttpServletResponse
     * @param jrBeanDataSource of type JRBeanCollectionDataSource
     * @param jrxml of type java.lang.String
     * @param params of type HashMap
     * @throws AkuraAppException AkuraAppException
     */
    private void displayReportForm(HttpServletResponse response, JRBeanCollectionDataSource jrBeanDataSource,
            Map<String, Object> params, String jrxml) throws AkuraAppException {

        ReportUtil.displayReportInPdfForm(response, jrBeanDataSource, params, jrxml);

    }

}
