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

import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.StaffCategory;
import com.virtusa.akura.api.dto.TeacherWiseAttendanceTemplate;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.StaffCommonService;
import com.virtusa.akura.reporting.service.AttendanceReportingService;
import com.virtusa.akura.reporting.validator.TeacherWiseAttendanceValidator;
import com.virtusa.akura.staff.service.StaffService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * Controller class to generate a report of teacher wise attendance.
 */
@Controller
@SessionAttributes("teacherWiseAttendanceTemplate")
public class GenerateTeacherWiseAttendanceReportController {
    
    /** error message for mandatory field required. */
    private static final String ERROR_MSG_MANDATORY_FIELD_REQUIRED = "REF.UI.MANDATORY.FIELD.REQUIRED";
    
    /** The Constant SELECTED_ADDMISSION. */
    private static final String SELECTED_ADDMISSION = "selectedAddmission";
    
    /** The Constant POPULATE_REGISTRATION_NO_BY_STAFF_CATEGORY_HTM. */
    private static final String POPULATE_REGISTRATION_NO_BY_STAFF_CATEGORY_HTM =
            "/populateRegistrationNosByStaffCategory.htm";
    
    /** The Constant STAFF_CATEGORY_ID. */
    private static final String STAFF_CATEGORY_ID = "catogaryID";
    
    /** The constant for String "staffCategoryList". */
    private static final String STAFF_CATEGORY_LIST = "staffCategoryList";
    
    /** The Constant STAFF_WISE_ATTENDEES_PAGE. */
    private static final String STAFF_WISE_ATTENDEES_PAGE = "StaffWiseAttendees_page";
    
    /** The Constant STAFF_WISE_ATTENDEES_REPORT_GENERATED_ON. */
    private static final String STAFF_WISE_ATTENDEES_REPORT_GENERATED_ON = "StaffWiseAttendees_reportGeneratedOn";
    
    /** The Constant STAFF_WISE_ATTENDEES_DATE_TO. */
    private static final String STAFF_WISE_ATTENDEES_DATE_TO = "StaffWiseAttendees_dateTo";
    
    /** The Constant STAFF_WISE_ATTENDEES_DATE_FROM. */
    private static final String STAFF_WISE_ATTENDEES_DATE_FROM = "StaffWiseAttendees_dateFrom";
    
    /** variable for holding string "generatedDate". */
    private static final String GENERATED_DATE = "generatedDate";
    
    /** model attribute for report generated date. */
    private static final String REPORT_GENERATED_DATE_LOCALE = "REPORT.GENERATED.DATE.LOCALE";
    
    /** The Constant GPL. */
    private static final String GPL = "GPLComment";
    
    /** The Constant PAGE. */
    private static final String PAGE = "page";
    
    /** The Constant REPORT_GENERATED_ON. */
    private static final String REPORT_GENERATED_ON = "reportGeneratedOn";
    
    /** The Constant DATE_TO. */
    private static final String DATE_TO = "dateTo";
    
    /** The Constant DATE_FROM. */
    private static final String DATE_FROM = "dateFrom";
    
    /** attribute for holding style path. */
    private static final String STYLE_PATH = "style.path";
    
    /** attribute for holding app home. */
    private static final String APPSERVER_HOME = "appserver.home";
    
    /** attribute for holding system config. */
    private static final String SYSTEM_CONFIG = "systemConfig";
    
    /** attribute for holding style template. */
    private static final String STYLE_TEMPLATE = "styleTemplate";
    
    /** attribute for holding string. */
    private static final String STRING_EMPTY = "";
    
    /** attribute for holding error message key. */
    public static final String NO_DATA_ERROR_MESSAGE = "REF.UI.NO.DATA.TEACHER";
    
    /** attribute for holding message. */
    public static final String MESSAGE = "message";
    
    /** report name for jrxml file. */
    private static final String REPORT_NAME_TEACHER_WISE_ATTENDANCE = "TeacherWiseAttendance";
    
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
    
    /** report value for report title. */
    private static final String REPORT_VALUE_TEACHER_WISE_ATTENDANCE_REPORT = "StaffWiseAttendees_titleText";
    
    /** report parameter for logo path. */
    private static final String REPORT_PARA_LOGO_PATH = "logoPath";
    
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
    
    /** report parameter for report title. */
    private static final String REPORT_PARA_TITLE = "title";
    
    /** request mapping value for teacher wise. */
    private static final String REQ_MAP_VALUE_TEACHER_WISE = "/teacherWiseReport.htm";
    
    /** model attribute for teacher wise attendance template. */
    private static final String MODEL_ATT_TEACHER_WISE_ATTENDANCE_TEMPLATE = "teacherWiseAttendanceTemplate";
    
    /** The Constant DEPARTURE_DATE_KEY. */
    private static final String DEPARTURE_DATE_KEY = "dateDeparture";
    
    /** attribute for holding DATE_DEPARTURE_FIELD_KEY. */
    private static final String DATE_DEPARTURE_FIELD_KEY = "StaffPresentAndAbsentDays_Report_DateTodeparture";
    
    /** The Constant DEPARTURE_DATE_VALUE. */
    private static final String DEPARTURE_DATE_VALUE = "departureDate";
    
    /** variable for year-month-day. */
    private static final String YEAR_MONTH_DAY = "yyyy-MM-dd";
    
    /** A String variable to represent date departure field. */
    private static final String DATE_DEPARTURE_FIELD = "dateDeparture";
    
    /** attribute for holding DATE_HIRED_FIELD_KEY. */
    private static final String DATE_HIRED_FIELD_KEY = "StaffPresentAndAbsentDays_Report_DateToHire";
    
    /** The String of Model attribute. */
    private static final String MODEL_ATT_SELECTED_ADDMISSION_NO = "selectedAddmissionNo";
    
    /** The Constant model attribute. */
    private static final String MODEL_ATT_SELECTED_CLASS_GRADE_ID = "selectedClassGradeId";
    
    /** The Constant SELECTED_CLASS. */
    private static final String SELECTED_CLASS = "selectedClass";
    
    /**
     * attendanceReportingService of type AttendanceReportingService to use methods related to attendance
     * Reporting .
     */
    @Autowired
    private AttendanceReportingService attendanceReportingService;
    
    /** VIEW_PAGE for holding return jsp page. */
    private static final String VIEW_PAGE = "reporting/teacherWiseAttendance";
    
    /**
     * staffService To invoke service methods.
     */
    private StaffService staffService;
    
    /**
     * setter to inject staffServiceRef object.
     * 
     * @param staffServiceRef the StaffService to set
     */
    public void setStaffService(StaffService staffServiceRef) {
    
        this.staffService = staffServiceRef;
    }
    
    /**
     * staffCommonService To invoke service methods.
     */
    private StaffCommonService staffCommonService;
    
    /**
     * Set StaffCommonService.
     * 
     * @param staffCommonServiceRef of type StaffCommonService
     */
    public void setStaffCommonService(StaffCommonService staffCommonServiceRef) {
    
        this.staffCommonService = staffCommonServiceRef;
    }
    
    /**
     * teacherWiseAttendanceValidator of type TeacherWiseAttendanceValidator to use methods related to
     * attendance validation .
     */
    
    private TeacherWiseAttendanceValidator teacherWiseAttendanceValidator;
    
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
     * @param teacherWiseAttendanceValidatorRef of type TeacherWiseAttendanceValidator
     */
    
    public void setTeacherWiseAttendanceValidator(TeacherWiseAttendanceValidator teacherWiseAttendanceValidatorRef) {
    
        this.teacherWiseAttendanceValidator = teacherWiseAttendanceValidatorRef;
    }
    
    /**
     * Display Form View for of the controller and binding it to TeacherWiseAttendanceTemplate.
     * 
     * @param modelMap of type ModelMap
     * @return java.lang.String
     */
    
    @RequestMapping(method = RequestMethod.GET)
    public String showReportForm(ModelMap modelMap) {
    
        TeacherWiseAttendanceTemplate teacherWiseAttendanceTemplate = new TeacherWiseAttendanceTemplate();
        modelMap.addAttribute(MODEL_ATT_TEACHER_WISE_ATTENDANCE_TEMPLATE, teacherWiseAttendanceTemplate);
        
        return VIEW_PAGE;
    }
    
    /**
     * Perform the logic of the controller to generate Teacher Wise Attendance Report .
     * 
     * @param teacherTemplate of type TeacherWiseAttendanceTemplate
     * @param request of type HttpServletRequest
     * @param response of type HttpServletResponse
     * @param errors of type BindingResult
     * @return java.lang.String
     * @param map of type ModelMap
     * @throws AkuraAppException AkuraAppException.
     * @throws ParseException - ParseException.
     */
    
    @RequestMapping(value = REQ_MAP_VALUE_TEACHER_WISE, method = RequestMethod.POST)
    public String onSubmit(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute(MODEL_ATT_TEACHER_WISE_ATTENDANCE_TEMPLATE) TeacherWiseAttendanceTemplate teacherTemplate,
            BindingResult errors, ModelMap map) throws AkuraAppException, ParseException {
    
        String returnString = STRING_EMPTY;
        
        String selectedClass = request.getParameter(SELECTED_CLASS);
        String addmissionNo = request.getParameter(SELECTED_ADDMISSION);
        String selectedAddmission=null;
        if(addmissionNo!=null){
        String[] noParts=addmissionNo.split("-");
        selectedAddmission = noParts[0].trim();
        }
        if(selectedAddmission!=null){
            teacherTemplate.setTeacherNo(selectedAddmission);
        }

        if (selectedAddmission == null) {
            String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_MANDATORY_FIELD_REQUIRED);
            map.addAttribute(MESSAGE, message);
            
            StaffCategory staffCategory = staffCommonService.getStaffCategory(Integer.parseInt(selectedClass));
            if (staffCategory != null) {
                map.addAttribute(MODEL_ATT_SELECTED_CLASS_GRADE_ID, staffCategory.getCatogaryID());
            }
            
            return VIEW_PAGE;
        }
        
        teacherWiseAttendanceValidator.validate(teacherTemplate, errors);
        if (errors.hasErrors()) {
            
            StaffCategory staffCategory = staffCommonService.getStaffCategory(Integer.parseInt(selectedClass));
            
            if (staffCategory != null) {
                map.addAttribute(MODEL_ATT_SELECTED_CLASS_GRADE_ID, staffCategory.getCatogaryID());
            }
            map.addAttribute(MODEL_ATT_SELECTED_ADDMISSION_NO, selectedAddmission);
            
            return VIEW_PAGE;
        }
        
        if (selectedClass.equals(AkuraWebConstant.STRING_ZERO)
                || selectedAddmission.equals(AkuraWebConstant.STRING_ZERO)) {
            
            String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_MANDATORY_FIELD_REQUIRED);
            map.addAttribute(MESSAGE, message);
            StaffCategory staffCategory = staffCommonService.getStaffCategory(Integer.parseInt(selectedClass));
            
            if (staffCategory != null) {
                map.addAttribute(MODEL_ATT_SELECTED_CLASS_GRADE_ID, staffCategory.getCatogaryID());
            }
            map.addAttribute(MODEL_ATT_SELECTED_ADDMISSION_NO, selectedAddmission);
            
            return VIEW_PAGE;
        }
        
        int staffId = staffService.findStaffIdForRegistrationNo(teacherTemplate.getTeacherNo());
        
        Staff staff = staffService.findStaff(staffId);
        
        Map<String, Object> params = new HashMap<String, Object>();
        
        params.put(REPORT_PARA_TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_VALUE_TEACHER_WISE_ATTENDANCE_REPORT));
        params.put(REPORT_PARA_TEACHERID,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_VALUE_TEACHER_ID));
        params.put(REPORT_PARA_TEACHERNAME,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_VALUE_TEACHER_NAME));
        params.put(REPORT_PARA_DATE,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_VALUE_DATE_CONSIDERED));
        params.put(REPORT_PARA_TIMEIN,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_VALUE_TIME_IN));
        params.put(REPORT_PARA_TIMEOUT,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_VALUE_TIME_OUT));
        params.put(REPORT_PARA_DATEFROM, teacherTemplate.getDateFrom());
        params.put(REPORT_PARA_DATETO, teacherTemplate.getDateTo());
        params.put(REPORT_PARA_LOGO_PATH, ReportUtil.getReportLogo());
        params.put(
                STYLE_TEMPLATE,
                PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                        + PropertyReader.getPropertyValue(SYSTEM_CONFIG, STYLE_PATH));
        params.put(DATE_FROM,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STAFF_WISE_ATTENDEES_DATE_FROM));
        params.put(DATE_TO, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STAFF_WISE_ATTENDEES_DATE_TO));
        params.put(REPORT_GENERATED_ON,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STAFF_WISE_ATTENDEES_REPORT_GENERATED_ON));
        params.put(GENERATED_DATE, DateUtil.getReportGeneratedDate(PropertyReader.getPropertyValue(
                ReportUtil.REPORT_TEMPLATE, REPORT_GENERATED_DATE_LOCALE)));
        params.put(PAGE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STAFF_WISE_ATTENDEES_PAGE));
        params.put(GPL, AkuraWebConstant.REPORT_GPL);
        params.put(DEPARTURE_DATE_KEY, AkuraWebConstant.EMPTY_STRING);
        
        String year = AkuraWebConstant.EMPTY_STRING;
        if (staff.getDateOfDeparture() != null) {
            year = DateUtil.getStringYear(staff.getDateOfDeparture());
        }
        if (year != AkuraWebConstant.EMPTY_STRING) {
            Date departureDateObj = staff.getDateOfDeparture();
            teacherTemplate.setDateTo(DateUtil.getFormatDate(departureDateObj));
            params.put(DEPARTURE_DATE_KEY,
                    PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, DATE_DEPARTURE_FIELD_KEY));
            params.put(DEPARTURE_DATE_VALUE, DateUtil.getFormatDate(departureDateObj));
        } else {
            params.put(DEPARTURE_DATE_VALUE, AkuraWebConstant.SPACE);
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat(YEAR_MONTH_DAY);
        Date dateFrom = sdf.parse(teacherTemplate.getDateFrom());
        
        if (staff.getDateOfHire() != null) {
            
            String hireDate = DateUtil.getFormatDate(staff.getDateOfHire());
            
            if (staff.getDateOfHire().after(dateFrom)) {
                
                teacherTemplate.setDateFrom(DateUtil.getFormatDate(staff.getDateOfHire()));
                params.put(DATE_DEPARTURE_FIELD,
                        PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, DATE_HIRED_FIELD_KEY));
                params.put(DEPARTURE_DATE_VALUE, hireDate);
                
            }
        }
        
        JRBeanCollectionDataSource jrBeanDataSource =
                attendanceReportingService.getTeacherWiseAttendanceSummary(teacherTemplate);
        
        if (jrBeanDataSource.getRecordCount() != 0) {
            ReportUtil.displayReportInPdfForm(response, jrBeanDataSource, params, REPORT_NAME_TEACHER_WISE_ATTENDANCE);
        } else {
            
            setErrorMessage(map);
            StaffCategory staffCategory = staffCommonService.getStaffCategory(Integer.parseInt(selectedClass));
            
            if (staffCategory != null) {
                map.addAttribute(MODEL_ATT_SELECTED_CLASS_GRADE_ID, staffCategory.getCatogaryID());
            }
            map.addAttribute(MODEL_ATT_SELECTED_ADDMISSION_NO, selectedAddmission);
            
            returnString = VIEW_PAGE;
            
        }
        return returnString;
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
        
        // get all staff list by staff category id ,from selected staff type
        List<Staff> staffLists = staffService.getAllStaffListByCategory(staffCategoryId);
        
        SortUtil.sortStaffNoList(staffLists);
        
        List<String> staffList = new ArrayList<String>();
        
        if (!staffLists.isEmpty()) {
            
            for (Staff s : staffLists) {
                // get departure year
                int year = DateUtil.getYearFromDate(s.getDateOfDepature());
                
                // get current year
                int currentYear = DateUtil.getYearFromDate(DateUtil.currentYear());
                
                if (s.getDateOfDeparture() == null) {
                    String regNo = s.getRegistrationNo();
                    String nameWithInitials = s.getNameWithIntials();
                    String regNoPlusNameWithInitials = regNo+" "+"-"+" "+nameWithInitials;
                    staffList.add(regNoPlusNameWithInitials);
                } else
                // check if the staff is departed on current year.
                if (year == currentYear) {
                    String regNo = s.getRegistrationNo();
                    String nameWithInitials = s.getNameWithIntials();
                    String regNoPlusNameWithInitials = regNo+" "+"-"+" "+nameWithInitials;
                    staffList.add(regNoPlusNameWithInitials);
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
