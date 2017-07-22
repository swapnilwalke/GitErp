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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.virtusa.akura.api.dto.Holiday;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.StaffCategory;
import com.virtusa.akura.api.dto.StaffLeave;
import com.virtusa.akura.api.dto.TeacherAttendance;
import com.virtusa.akura.api.dto.TeacherWisePresentAbsentTemplate;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.service.StaffCommonService;
import com.virtusa.akura.reporting.service.AttendanceReportingService;
import com.virtusa.akura.reporting.validator.TeacherWisePresentAndAbsentDaysValidator;
import com.virtusa.akura.staff.service.StaffService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * @author Virtusa Corporation
 */
@Controller
@SessionAttributes("teacherWisePresentAbsentTemplate")
public class GenarateTeacherWisePresentAndAbsentDaysReportController {

    /** attribute for holding message. */
    public static final String MESSAGE = "message";

    /** error message for mandatory field required. */
    private static final String ERROR_MSG_MANDATORY_FIELD_REQUIRED = "REF.UI.MANDATORY.FIELD.REQUIRED";

    /** The Constant SELECTED_ADDMISSION. */
    private static final String SELECTED_ADDMISSION = "selectedAddmission";

    /** The Constant POPULATE_REGISTRATION_NO_BY_STAFF_CATEGORY_HTM. */
    private static final String POPULATE_REGISTRATION_NO_BY_STAFF_CATEGORY_HTM =
            "/populateRegistrationNosByStaffCategory1.htm";

    /** variable for year-month-day. */
    private static final String YEAR_MONTH_DAY = "yyyy-MM-dd";

    /** The Constant STAFF_CATEGORY_ID. */
    private static final String STAFF_CATEGORY_ID = "catogaryID";

    /** The constant for String "staffCategoryList". */
    private static final String STAFF_CATEGORY_LIST = "staffCategoryList";

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

    /** attribute for holding ABSENT_KEY. */
    private static final String ABSENT_KEY = "StaffPresentAndAbsentDays_Report_Absent";

    /** attribute for holding PRESENT_KEY. */
    private static final String PRESENT_KEY = "StaffPresentAndAbsentDays_Report_Present";

    /** attribute for holding WORKING_DAYS_KEY. */
    private static final String WORKING_DAYS_KEY = "StaffPresentAndAbsentDays_Report_WorkingDays";

    /** attribute for holding DATE_TO_KEY. */
    private static final String DATE_TO_KEY = "StaffPresentAndAbsentDays_Report_DateTo";

    /** attribute for holding DATE_FROM_KEY. */
    private static final String DATE_FROM_KEY = "StaffPresentAndAbsentDays_Report_DateFrom";

    /** attribute for holding STAFF_NAME_KEY. */
    private static final String STAFF_NAME_KEY = "StaffPresentAndAbsentDays_Report_StaffName";

    /** attribute for holding REG_NO_KEY. */
    private static final String REG_NO_KEY = "StaffPresentAndAbsentDays_Report_RegNo";

    /** attribute for holding STAFF_ABSENT_LABEL. */
    private static final String STAFF_ABSENT_LABEL = "staffAbsent";

    /** attribute for holding STAFF_PRESENT_LABEL. */
    private static final String STAFF_PRESENT_LABEL = "staffPresent";

    /** attribute for holding STAFF_WORKING_LABEL. */
    private static final String STAFF_WORKING_LABEL = "staffWorking";

    /** attribute for holding STAFF_DATE_TO_LABEL. */
    private static final String STAFF_DATE_TO_LABEL = "staffDateTo";

    /** attribute for holding STAFF_DATE_FROM_LABEL. */
    private static final String STAFF_DATE_FROM_LABEL = "staffDateFrom";

    /** attribute for holding STAFF_NAME_LABEL. */
    private static final String STAFF_NAME_LABEL = "staffName";

    /** attribute for holding REG_NO_LABEL. */
    private static final String REG_NO_LABEL = "staffRegNo";

    /** attribute for holding style path. */
    private static final String STYLE_PATH = "style.path";

    /** attribute for holding app home. */
    private static final String APPSERVER_HOME = "appserver.home";

    /** attribute for holding system config. */
    private static final String SYSTEM_CONFIG = "systemConfig";

    /** attribute for holding style template. */
    private static final String STYLE_TEMPLATE = "styleTemplate";

    /** A String variable to represent date departure field. */
    private static final String DATE_DEPARTURE_FIELD = "dateDeparture";

    /** attribute for holding DATE_DEPARTURE_FIELD_KEY. */
    private static final String DATE_DEPARTURE_FIELD_KEY = "StaffPresentAndAbsentDays_Report_DateTodeparture";

    /** attribute for holding DATE_HIRED_FIELD_KEY. */
    private static final String DATE_HIRED_FIELD_KEY = "StaffPresentAndAbsentDays_Report_DateToHire";

    /** The Constant model attribute. */
    private static final String MODEL_ATT_SELECTED_CLASS_GRADE_ID = "selectedClassGradeId";

    /**
     * attendanceReportingService of type AttendanceReportingService to use methods related to attendance
     * Reporting .
     */
    @Autowired
    private AttendanceReportingService attendanceReportingService;

    /**
     * CommonService attribute for holding commonService.
     */
    private CommonService commonService;

    /**
     * CommonService attribute for holding commonService.
     */
    private StaffCommonService staffCommonService;

    /**
     * StaffService attribute for holding staffService.
     */
    private StaffService staffService;

    /**
     * teacherWisePresentAbsentValidator of type TeacherWisePresentAbsentValidator to use methods related to
     * attendance validation .
     */
    private TeacherWisePresentAndAbsentDaysValidator presentAndAbsentDaysValidator;

    /** A String variable to represent absent without leave. */
    private static final String ABSENT_WITHOUT_LEAVE = "StaffPresentAndAbsentDays_Report_Reason_Label";

    /** A String variable to represent reason name. */
    private static final String REASON_KEY = "StaffPresentAndAbsentDays_Report_Reason";

    /** A String variable to represent reason field. */
    private static final String REASON_FIELD = "reason";

    /** A String variable to represent working field. */
    private static final String WORKING_FIELD = "working";

    /** A String variable to represent model name. */
    private static final String MODELANDVIEW = "reporting/teacherWisePresentAndAbsentDays";

    /** A String variable to represent template name. */
    private static final String TEMPLATENAME = "teacherWisePresentAbsentTemplate";

    /** A String variable to represent field name. */
    private static final String FIELD_NAME = "teacherRegNo";

    /** A String variable to represent title field. */
    private static final String TITLE_FIELD = "title";

    /** A String variable to represent title name. */
    private static final String TITLE_NAME_KEY = "StaffPresentAndAbsentDays_Report_Title";

    /** A String variable to represent teacher id. */
    private static final String TEACHERID_FIELD = "teacherid";

    /** A String variable to represent teacher name field. */
    private static final String TEACHER_NAME_FIELD = "teachername";

    /** A String variable to represent date from field. */
    private static final String DATE_FROM_FIELD = "datefrom";

    /** A String variable to represent date to field. */
    private static final String DATE_TO_FIELD = "dateto";

    /** A String variable to represent present field. */
    private static final String PRESENT_FIELD = "present";

    /** A String variable to represent absent field. */
    private static final String ABSENT_FIELD = "absent";

    /** A String variable to represent absent list field. */
    private static final String ABSENTLIST_FIELD = "absentList";

    /** A String variable to represent absent days field. */
    private static final String ABSENT_DAYS_FIELD = "absentDays";

    /** A String variable to represent absent days. */
    private static final String ABSENT_DAYS_KEY = "StaffPresentAndAbsentDays_Report_Absent_Label";

    /** A String variable to represent leave status field. */
    private static final String LEAVE_STATUS_FIELD = "leaveStatus";

    /** A String variable to represent leave status. */
    private static final String LEAVE_STATUS_KEY = "StaffPresentAndAbsentDays_Report_Leave_Status_Label";

    /** A String variable to represent approved/rejected by field. */
    private static final String APPROVED_OR_REJECTED_BY_FIELD = "approvedOrRejectedBy";

    /** A String variable to represent approved or rejected by. */
    private static final String APPROVED_OR_REJECTED_BY_KEY =
            "StaffPresentAndAbsentDays_Report_Approved_Or_Rejected_By_Label";

    /** A String variable to represent login path field. */
    private static final String LOGO_PATH_FIELD = "logoPath";

    /** A String variable to represent Staff id error message. */
    private static final String STAFFID_ERROR_MSG = "REF.UI.PARENT.STAFFID.NOTEXIST";

    /** A String variable to represent JRXML name. */
    private static final String JRXML_NAME = "TeacherPresentAndAbsent";

    /** The Constant DEPARTURE_DATE_VALUE. */
    private static final String DEPARTURE_DATE_VALUE = "departureDate";

    /** The Constant SELECTED_CLASS. */
    private static final String SELECTED_CLASS = "selectedClass";

    /** The String of Model attribute. */
    private static final String MODEL_ATT_SELECTED_ADDMISSION_NO = "selectedAddmissionNo";

    /** A String variable to represent absent without leave. */
    private static final String ABSENT_WITH_NO_LEAVE = "StaffPresentAndAbsentDays_Report_Status_Label";

    /**
     * Set TeacherWisePresentAndAbsentDaysValidator.
     *
     * @param presentAndAbsentDaysValidatorRef of type TeacherWiseAttendanceValidator
     */
    public void setPresentAndAbsentDaysValidator(
            TeacherWisePresentAndAbsentDaysValidator presentAndAbsentDaysValidatorRef) {

        this.presentAndAbsentDaysValidator = presentAndAbsentDaysValidatorRef;
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
     * Set CommonService.
     *
     * @param commonServiceRef of type CommonService
     */
    public void setCommonService(CommonService commonServiceRef) {

        this.commonService = commonServiceRef;
    }

    /**
     * Set StaffCommonService.
     *
     * @param staffCommonServiceRef of type StaffCommonService
     */
    public void setStaffCommonService(StaffCommonService staffCommonServiceRef) {

        this.staffCommonService = staffCommonServiceRef;
    }

    /**
     * Set StaffService.
     *
     * @param staffServiceRef of type StaffService
     */
    public void setStaffService(StaffService staffServiceRef) {

        this.staffService = staffServiceRef;
    }

    /**
     * Display Form View for of the controller and binding it to TeacherWisePresentAbsentTemplate.
     *
     * @param modelMap of type ModelMap
     * @return java.lang.String
     */

    @RequestMapping(method = RequestMethod.GET)
    public String showReportForm(ModelMap modelMap) {

        TeacherWisePresentAbsentTemplate teacherWisePresentAbsentTemplate = new TeacherWisePresentAbsentTemplate();
        modelMap.addAttribute(TEMPLATENAME, teacherWisePresentAbsentTemplate);

        return MODELANDVIEW;
    }

    /**
     * Perform the logic of the controller to generate Teacher Wise Attendance Report .
     *
     * @param teacherWisePresentAbsentTemplate of type TeacherWisePresentAbsentTemplate
     * @param request of type HttpServletRequest
     * @param response of type HttpServletResponse
     * @param errors of type BindingResult
     * @return java.lang.String
     * @param map of type ModelMap
     * @throws AkuraAppException AkuraAppException
     * @throws ParseException ParseException
     */
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute(TEMPLATENAME) TeacherWisePresentAbsentTemplate teacherWisePresentAbsentTemplate,
            BindingResult errors, ModelMap map) throws AkuraAppException, ParseException {

        String selectedClass = request.getParameter(SELECTED_CLASS);
        String selectedAddmissionWithName = request.getParameter(SELECTED_ADDMISSION);
        ModelAndView modelAndView = new ModelAndView();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put(DATE_DEPARTURE_FIELD, AkuraWebConstant.EMPTY_STRING);
        params.put(DATE_TO_FIELD, teacherWisePresentAbsentTemplate.getDateTo());
        String selectedAddmission =null;
        if(selectedAddmissionWithName!=null){
        String [] selectedAdmissionArray = selectedAddmissionWithName.split("-");
        selectedAddmission = selectedAdmissionArray[0].trim();
        }
        if(selectedAddmission!=null){
        teacherWisePresentAbsentTemplate.setTeacherRegNo(selectedAddmission);
        }
        if (selectedAddmission == null) {
            String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_MANDATORY_FIELD_REQUIRED);
            map.addAttribute(MESSAGE, message);

            StaffCategory staffCategory = staffCommonService.getStaffCategory(Integer.parseInt(selectedClass));

            if (staffCategory != null) {
                map.addAttribute(MODEL_ATT_SELECTED_CLASS_GRADE_ID, staffCategory.getCatogaryID());
            }

            modelAndView.setViewName(MODELANDVIEW);
            return modelAndView;
        }

        presentAndAbsentDaysValidator.validate(teacherWisePresentAbsentTemplate, errors);
        if (errors.hasErrors()) {

            return setPreviousDataWhileError(map, selectedClass, selectedAddmission, modelAndView);
        }

        if (selectedClass.equals(AkuraWebConstant.STRING_ZERO)) {

            String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_MANDATORY_FIELD_REQUIRED);
            map.addAttribute(MESSAGE, message);
            return setPreviousDataWhileError(map, selectedClass, selectedAddmission, modelAndView);
        }

        String teacherRegNo = teacherWisePresentAbsentTemplate.getTeacherRegNo();
        int staffId = staffService.findStaffIdForRegistrationNo(teacherRegNo);

        if (staffId == 0) {

            errors.rejectValue(FIELD_NAME, STAFFID_ERROR_MSG);
            modelAndView.setViewName(MODELANDVIEW);
            return modelAndView;
        }

        Staff staff = staffService.findStaff(staffId);

        String year = AkuraWebConstant.EMPTY_STRING;
        if (staff.getDateOfDeparture() != null) {
            year = DateUtil.getStringYear(staff.getDateOfDeparture());
        }
        TeacherAttendance tempTAa = new TeacherAttendance();
        List<TeacherAttendance> absentList = new ArrayList<TeacherAttendance>();
        if (year != AkuraWebConstant.EMPTY_STRING) {
            Date departureDateObj = staff.getDateOfDeparture();
            tempTAa.setDateOfDeparture(DateUtil.getFormatDate(departureDateObj));
            teacherWisePresentAbsentTemplate.setDateTo(DateUtil.getFormatDate(departureDateObj));

            params.put(DATE_DEPARTURE_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    DATE_DEPARTURE_FIELD_KEY));
            params.put(DEPARTURE_DATE_VALUE, DateUtil.getFormatDate(departureDateObj));
        } else {
            params.put(DEPARTURE_DATE_VALUE, AkuraWebConstant.EMPTY_STRING);
        }

        absentList.add(tempTAa);

        String startDate = teacherWisePresentAbsentTemplate.getDateFrom();

        SimpleDateFormat sdf = new SimpleDateFormat(YEAR_MONTH_DAY);
        Date dateFrom = sdf.parse(teacherWisePresentAbsentTemplate.getDateFrom());

        if (staff.getDateOfHire() != null) {

            setHireDate(teacherWisePresentAbsentTemplate, params, staff, dateFrom);
        }

        String endDate = teacherWisePresentAbsentTemplate.getDateTo();
        Date startDateObj = DateUtil.getParseDate(teacherWisePresentAbsentTemplate.getDateFrom());
        Date endDateObj = DateUtil.getParseDate(endDate);

        List<TeacherAttendance> attendanceList =
                attendanceReportingService.teacherWisePresentAndAbsentDays(teacherWisePresentAbsentTemplate);
        List<StaffLeave> staffLeaveList =
                staffService.getStaffLeaveListByDatePeriodAndStaffId(startDateObj, endDateObj, staffId);

        Calendar start = Calendar.getInstance();
        start.setTime(startDateObj);
        Calendar end = Calendar.getInstance();
        end.setTime(endDateObj);
        List<String> presentList = new ArrayList<String>();
        List<Holiday> holidayList = getHolidayList(startDateObj, endDateObj);

        boolean flag = false;
        boolean isHoliday = false;
        boolean isReason = false;
        String temp = null;
        int count = 0;

        while (!start.after(end)) {

            for (TeacherAttendance ta : attendanceList) {
                if (DateUtil.getFormatDate(start.getTime()).equals(ta.getDate())) {
                    temp = ta.getDate();
                    flag = true;
                    break;
                }
            }
            isReason = false;
            isHoliday = isHoliday(holidayList, start.getTime(), start);

            if (flag) {
                presentList.add(temp);
                flag = false;
                temp = null;
                count++;
            } else {
                if (!isHoliday) {
                    TeacherAttendance tempTA = new TeacherAttendance();
                    tempTA.setDate(DateUtil.getFormatDate(start.getTime()).toString());

                    for (StaffLeave staffLeave : staffLeaveList) {
                        if (DateUtil.isDateBetween(staffLeave.getFromDate(), staffLeave.getToDate(), start.getTime())) {
                            isReason = setReasonAndApprovalBy(tempTA, staffLeave);

                            break;
                        }
                    }
                    if (!isReason) {
                        setReportParametersWhenNotReason(tempTA);
                    }
                    absentList.add(tempTA);
                    count++;
                }
            }
            start.add(Calendar.DATE, 1);
        }
        JRBeanCollectionDataSource jrBeanDataSource = new JRBeanCollectionDataSource(absentList);
        setReportParameters(params, teacherRegNo, staff, absentList, startDate, presentList, count);

        ReportUtil.displayReportInPdfForm(response, jrBeanDataSource, params, JRXML_NAME);
        return null;
    }

    /**
     * set reason and approval by user.
     *
     * @param tempTA - TeacherAttendance object.
     * @param staffLeave - staffLeave object.
     * @return isReason is exists true of false.
     */
    private boolean setReasonAndApprovalBy(TeacherAttendance tempTA, StaffLeave staffLeave) {

        boolean isReason;
        tempTA.setReason(staffLeave.getReason());
        isReason = true;

        tempTA.setLeaveStatus(staffLeave.getStaffLeaveStatusId());

        if (staffLeave.getUserLogin() != null) {
            tempTA.setLeaveApprovedBy(staffLeave.getUserLogin());
        } else {
            setLeaveApprovalToReportTemplate(tempTA);
        }
        return isReason;
    }

    /**
     * set report parameters when not having reason.
     *
     * @param tempTA - TeacherAttendance object.
     */
    private void setReportParametersWhenNotReason(TeacherAttendance tempTA) {

        tempTA.setReason(PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, ABSENT_WITHOUT_LEAVE));
        tempTA.setLeaveStatusWhenNoApply(PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                ABSENT_WITH_NO_LEAVE));
        setLeaveApprovalToReportTemplate(tempTA);
    }

    /**
     * set data while page has error.
     *
     * @param map - map object.
     * @param selectedClass - selectedClass.
     * @param selectedAddmission - selectedAddmission.
     * @param modelAndView - modelAndView for page.
     * @return modelAndView - view.
     * @throws AkuraAppException - AkuraAppException.
     */
    private ModelAndView setPreviousDataWhileError(ModelMap map, String selectedClass, String selectedAddmission,
            ModelAndView modelAndView) throws AkuraAppException {

        StaffCategory staffCategory = staffCommonService.getStaffCategory(Integer.parseInt(selectedClass));

        if (staffCategory != null) {
            map.addAttribute(MODEL_ATT_SELECTED_CLASS_GRADE_ID, staffCategory.getCatogaryID());
        }
        map.addAttribute(MODEL_ATT_SELECTED_ADDMISSION_NO, selectedAddmission);

        return new ModelAndView(MODELANDVIEW, map);
    }

    /**
     * set report parameters with attributes.
     *
     * @param teacherWisePresentAbsentTemplate - teacherWisePresentAbsentTemplate object.
     * @param params - params of the report.
     * @param staff - staff object.
     * @param dateFrom - dateFrom.
     * @throws AkuraAppException - AkuraAppException.
     */
    private void setHireDate(TeacherWisePresentAbsentTemplate teacherWisePresentAbsentTemplate,
            Map<String, Object> params, Staff staff, Date dateFrom) throws AkuraAppException {

        if (staff.getDateOfHire().after(dateFrom)) {

            teacherWisePresentAbsentTemplate.setDateFrom(DateUtil.getFormatDate(staff.getDateOfHire()));
            params.put(DATE_DEPARTURE_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    DATE_HIRED_FIELD_KEY));
            params.put(DEPARTURE_DATE_VALUE, DateUtil.getFormatDate(staff.getDateOfHire()));

        }
    }

    /**
     * set report parameters with attributes.
     *
     * @param tempTA - TeacherAttendance object.
     */
    private void setLeaveApprovalToReportTemplate(TeacherAttendance tempTA) {

        tempTA.setLeaveApprovalWhenNoApply(PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                ABSENT_WITH_NO_LEAVE));
    }

    /**
     * set report parameters with attributes.
     *
     * @param params - params of report.
     * @param teacherRegNo - teacherRegNo.
     * @param staff - staff object.
     * @param absentList - absentList.
     * @param startDate - startDate.
     * @param presentList - presentList.
     * @param count - count.
     */
    private void setReportParameters(Map<String, Object> params, String teacherRegNo, Staff staff,
            List<TeacherAttendance> absentList, String startDate, List<String> presentList, int count) {

        params.put(TITLE_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, TITLE_NAME_KEY));
        params.put(REG_NO_LABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REG_NO_KEY));
        params.put(STAFF_NAME_LABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STAFF_NAME_KEY));
        params.put(STAFF_DATE_FROM_LABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, DATE_FROM_KEY));
        params.put(STAFF_DATE_TO_LABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, DATE_TO_KEY));
        params.put(STAFF_WORKING_LABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, WORKING_DAYS_KEY));
        params.put(STAFF_PRESENT_LABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, PRESENT_KEY));
        params.put(STAFF_ABSENT_LABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, ABSENT_KEY));

        params.put(TEACHERID_FIELD, teacherRegNo);
        params.put(TEACHER_NAME_FIELD, staff.getNameWithIntials());
        params.put(DATE_FROM_FIELD, startDate);
        params.put(WORKING_FIELD, String.valueOf(count));
        params.put(PRESENT_FIELD, String.valueOf(presentList.size()));
        params.put(ABSENT_FIELD, String.valueOf(absentList.size() - 1));
        params.put(ABSENTLIST_FIELD, absentList);
        if (!absentList.isEmpty()) {
            params.put(REASON_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REASON_KEY));
            params.put(ABSENT_DAYS_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, ABSENT_DAYS_KEY));

            params.put(LEAVE_STATUS_FIELD, PropertyReader
                    .getPropertyValue(ReportUtil.REPORT_TEMPLATE, LEAVE_STATUS_KEY));
            params.put(APPROVED_OR_REJECTED_BY_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    APPROVED_OR_REJECTED_BY_KEY));

        }
        params.put(LOGO_PATH_FIELD, ReportUtil.getReportLogo());
        params.put(STYLE_TEMPLATE, PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                + PropertyReader.getPropertyValue(SYSTEM_CONFIG, STYLE_PATH));
        params.put(REPORT_GENERATED_ON, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_GENERATED_ON_TEXT));
        params.put(GENERATED_DATE, DateUtil.getReportGeneratedDate(PropertyReader.getPropertyValue(
                ReportUtil.REPORT_TEMPLATE, REPORT_GENERATED_DATE_LOCALE)));
        params.put(PAGE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_PAGE));
        params.put(GPL, AkuraWebConstant.REPORT_GPL);
    }

    /**
     * Check the given date is a holiday or not.
     *
     * @param holidayList - list consits of Holidays for the given time period.
     * @param currentDate - currentDate
     * @param start - Calender object
     * @return boolean
     */
    public boolean isHoliday(List<Holiday> holidayList, Date currentDate, Calendar start) {

        boolean flag = false;
        int dayOfWeek = start.get(Calendar.DAY_OF_WEEK);

        for (Holiday tempHoliday : holidayList) {
            if ((currentDate.after(tempHoliday.getStartDate()) && currentDate.before(tempHoliday.getEndDate()))
                    || currentDate.equals(tempHoliday.getStartDate()) || currentDate.equals(tempHoliday.getEndDate())
                    || Calendar.SATURDAY == dayOfWeek || Calendar.SUNDAY == dayOfWeek) {

                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * Get the Holiday List for the given time period.
     *
     * @param startDate - start date of the report.
     * @param endDate - end date of the report.
     * @return ListHoliday - a list containing holiday objects.
     * @throws AkuraAppException - AkuraAppException
     */
    public List<Holiday> getHolidayList(Date startDate, Date endDate) throws AkuraAppException {

        String strYr = DateUtil.getStringYear(startDate);

        String strStartDate = strYr + "-01-01";
        String strEndDate = strYr + "-12-31";

        Date startDateToSearch = DateUtil.getParseDate(strStartDate);
        Date endDateToSearch = DateUtil.getParseDate(strEndDate);

        return commonService.findHolidayByYear(startDateToSearch, endDateToSearch);
    }

    /**
     * Populate registration no by staff category.
     *
     * @param request the request
     * @return the list
     * @throws AkuraAppException the akura app exception
     */
    @RequestMapping(value = POPULATE_REGISTRATION_NO_BY_STAFF_CATEGORY_HTM)
    @ResponseBody
    public String populateRegistrationNoByCategory(HttpServletRequest request) throws AkuraAppException {

        int staffCategoryId = Integer.parseInt(request.getParameter(STAFF_CATEGORY_ID));

        // get staff list by staff category id ,from selected staff type
        List<Staff> staffLists = staffService.getAllStaffListByCategory(staffCategoryId);

        SortUtil.sortStaffNoList(staffLists);

        List<String> staffList = new ArrayList<String>();

        if (!staffLists.isEmpty()) {

            for (Staff s : staffLists) {
                // get departure year
                int year = DateUtil.getYearFromDate(s.getDateOfDepature());

                // get current year
                int currentYear = DateUtil.getYearFromDate(DateUtil.currentYear());
                String nameWithInitials = s.getNameWithIntials();
                if (s.getDateOfDeparture() == null) {
                    
                    staffList.add(s.getRegistrationNo()+" "+"-"+" "+nameWithInitials);
                } else
                // check if the staff is departed on current year.
                if (year == currentYear) {
                    staffList.add(s.getRegistrationNo()+" "+"-"+" "+nameWithInitials+" ");
                }
            }
        }

        StringBuilder addmissionBuilder = new StringBuilder();

        if (!staffList.isEmpty()) {

            for (String admission : staffList) {
                addmissionBuilder.append(admission);
                addmissionBuilder.append(AkuraWebConstant.STRING_COMMA);
            }
        }

        return addmissionBuilder.toString();
    }

    /**
     * Load the list of Staff categories to load on report.
     *
     * @return a list of available staff categories.
     * @throws AkuraAppException - throws when fails.
     */
    @ModelAttribute(STAFF_CATEGORY_LIST)
    public List<StaffCategory> populateListOfStaffCategories() throws AkuraAppException {

        return SortUtil.sortStaffCategoryList(staffCommonService.getAllStaffCategories());

    }
}
