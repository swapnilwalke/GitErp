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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.dto.StudentWiseAttendanceTemplate;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.reporting.service.AttendanceReportingService;
import com.virtusa.akura.reporting.validator.StudentWiseAttendanceValidator;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * Controller class to generate a report of Student wise attendance.
 */

@Controller
@SessionAttributes("studentWiseAttendanceTemplate")
public class GenerateStudentWiseAttendanceReportController {
    
    /** The Constant POPULATE_ADDMISSION_NOS_BY_CLASS_HTM. */
    private static final String POPULATE_ADDMISSION_NOS_BY_CLASS_HTM = "/populateAddmissionNosByClass2.htm";
    
    /** The Constant CLASS_GRADE_ID. */
    private static final String CLASS_GRADE_ID = "classGradeId";
    
    /** The Constant CLASS_GRADE_LIST. */
    private static final String CLASS_GRADE_LIST = "classGradeList";
    
    /** The Constant SELECTED_CLASS. */
    private static final String SELECTED_CLASS = "selectedClass";
    
    /** error message for mandatory field required. */
    private static final String ERROR_MSG_MANDATORY_FIELD_REQUIRED = "REF.UI.MANDATORY.FIELD.REQUIRED";
    
    /** The Constant SELECTED_ADDMISSION. */
    private static final String SELECTED_ADDMISSION = "selectedAddmission";
    
    /** The String of Model attribute. */
    private static final String MODEL_ATT_SELECTED_ADDMISSION_NO = "selectedAddmissionNo";
    
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
    
    /** attribute for holding string. */
    private static final String STRING_EMPTY = "";
    
    /** attribute for holding error message key. */
    public static final String NO_DATA_ERROR_MESSAGE = "REF.UI.NO.DATA.STUDENT";
    
    /** attribute for holding message. */
    public static final String MESSAGE = "message";
    
    /** report value for time out. */
    private static final String REPORT_VALUE_TIME_OUT = "StuLateAttendees_swipeOutText";
    
    /** report value for time in. */
    private static final String REPORT_VALUE_TIME_IN = "StuLateAttendees_swipeInText";
    
    /** report value for date. */
    private static final String REPORT_VALUE_DATE = "StuWiseAttendees_dateText";
    
    /** report value for student name. */
    private static final String REPORT_VALUE_STUDENT_NAME = "StuLateAttendees_studentNameText";
    
    /** report value for student id. */
    private static final String REPORT_VALUE_STUDENT_ID = "StuLateAttendees_admissionNumberText";
    
    /** report value for report title. */
    private static final String REPORT_VALUE_STUDENT_WISE_ATTENDANCE_REPORT = "StuWiseAttendees_titleText";
    
    /** report parameter for student attendance from. */
    private static final String REPORT_PARA_ATTENDANCE_FROM = "attendanceFrom";
    
    /** report parameter for student attendance from text. */
    private static final String REPORT_PARA_ATTENDANCE_FROM_TEXT = "StuWiseAttendees_attendanceFrom";
    
    /** report parameter for student attendance to text. */
    private static final String REPORT_PARA_ATTENDANCE_TO_TEXT = "StuWiseAttendees_attendanceTo";
    
    /** report parameter for student attendance to. */
    private static final String REPORT_PARA_ATTENDANCE_TO = "attendanceTo";
    
    /** model attribute for student wise attendance template. */
    private static final String MODEL_ATT_STUDENT_WISE_ATTENDANCE_TEMPLATE = "studentWiseAttendanceTemplate";
    
    /** report parameter for logo path. */
    private static final String REPORT_PARA_LOGO_PATH = "logoPath";
    
    /** report parameter for date to. */
    private static final String REPORT_PARA_DATETO = "dateto";
    
    /** report parameter for date from. */
    private static final String REPORT_PARA_DATEFROM = "datefrom";
    
    /** report parameter for student name. */
    private static final String REPORT_PARA_STUDENT = "student";
    
    /** report parameter for time out. */
    private static final String REPORT_PARA_TIMEOUT = "timeout";
    
    /** report parameter for time in. */
    private static final String REPORT_PARA_TIMEIN = "timein";
    
    /** report parameter for date. */
    private static final String REPORT_PARA_DATE = "date";
    
    /** report parameter for student name. */
    private static final String REPORT_PARA_STUDENTNAME = "studentname";
    
    /** report parameter for student id. */
    private static final String REPORT_PARA_STUDENTID = "studentid";
    
    /** report parameter for title. */
    private static final String REPORT_PARA_TITLE = "title";
    
    /** report name for jrxml file. */
    private static final String REPORT_NAME_STUDENT_WISE_ATTENDANCE = "StudentWiseAttendance";
    
    /** request map for report generates. */
    private static final String REQ_MAP_STUDENT_ATTENDANCE_REPORT = "/studentAttendanceReport.htm";
    
    /**
     * attendanceReportingService of type AttendanceReportingService to use methods related to attendance
     * Reporting .
     */
    
    private AttendanceReportingService attendanceReportingService;
    
    /** VIEW_PAGE for holding return jsp page. */
    private static final String VIEW_PAGE = "reporting/studentWiseAttendance";
    
    /** The student service. */
    private StudentService studentService;
    
    /** The common service. */
    private CommonService commonService;
    
    /** The Constant model attribute. */
    private static final String MODEL_ATT_SELECTED_CLASS_GRADE_ID = "selectedClassGradeId";
    
    /**
     * Sets the student service.
     * 
     * @param objStudentService the studentService to set
     */
    public final void setStudentService(final StudentService objStudentService) {
    
        this.studentService = objStudentService;
    }
    
    /**
     * Sets the common service.
     * 
     * @param objCommonService the commonService to set
     */
    public final void setCommonService(CommonService objCommonService) {
    
        this.commonService = objCommonService;
    }
    
    /**
     * studentWiseAttendanceValidator of type StudentWiseAttendanceValidator to use methods related to
     * Validation .
     */
    
    private StudentWiseAttendanceValidator studentWiseAttendanceValidator;
    
    /**
     * Set AttendanceReportingService.
     * 
     * @param attendanceReportingServiceRef of type AttendanceReportingService
     */
    
    public void setAttendanceReportingService(AttendanceReportingService attendanceReportingServiceRef) {
    
        this.attendanceReportingService = attendanceReportingServiceRef;
    }
    
    /**
     * Set StudentWiseAttendanceValidator.
     * 
     * @param studentWiseAttendanceValidatorVal of type StudentWiseAttendanceValidator
     */
    
    public void setStudentWiseAttendanceValidator(StudentWiseAttendanceValidator studentWiseAttendanceValidatorVal) {
    
        this.studentWiseAttendanceValidator = studentWiseAttendanceValidatorVal;
    }
    
    /**
     * Display Form View for of the controller and binding it to MonthlyStudentAttendanceTemplate.
     * 
     * @param modelMap of type ModelMap
     * @return java.lang.String
     */
    
    @RequestMapping(method = RequestMethod.GET)
    public String showReportForm(ModelMap modelMap) {
    
        StudentWiseAttendanceTemplate studentWiseAttendanceTemplate = new StudentWiseAttendanceTemplate();
        modelMap.addAttribute(MODEL_ATT_STUDENT_WISE_ATTENDANCE_TEMPLATE, studentWiseAttendanceTemplate);
        
        return VIEW_PAGE;
    }
    
    /**
     * Perform the logic of the controller to generate Teacher Wise Attendance Report .
     * 
     * @param studentTemplate of type StudentWiseAttendanceTemplate
     * @param request of type HttpServletRequest
     * @param response of type HttpServletResponse
     * @param errors of type BindingResult
     * @return java.lang.String
     * @param map of type ModelMap
     * @throws AkuraAppException AkuraAppException
     */
    
    @RequestMapping(value = REQ_MAP_STUDENT_ATTENDANCE_REPORT, method = RequestMethod.POST)
    public String onSubmit(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute(MODEL_ATT_STUDENT_WISE_ATTENDANCE_TEMPLATE) StudentWiseAttendanceTemplate studentTemplate,
            BindingResult errors, ModelMap map) throws AkuraAppException {
    
        String selectedClass = request.getParameter(SELECTED_CLASS);
        String reqAddmission = request.getParameter(SELECTED_ADDMISSION);
        
        String[] noParts =
                reqAddmission.split(AkuraWebConstant.SPACE_STRING + AkuraWebConstant.DASH_STRING
                        + AkuraWebConstant.SPACE_STRING);
        String selectedAddmission = noParts[0];
        
        ClassGrade classGrade = commonService.findClassGrade(Integer.parseInt(selectedClass));
        studentTemplate.setStudentID(selectedAddmission);
        
        if (selectedAddmission == null) {
            
            String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_MANDATORY_FIELD_REQUIRED);
            map.addAttribute(MESSAGE, message);
            if (classGrade != null) {
                map.addAttribute(MODEL_ATT_SELECTED_CLASS_GRADE_ID, classGrade.getClassGradeId());
            }
            
            return VIEW_PAGE;
        }
        
        String returnString = STRING_EMPTY;
        studentWiseAttendanceValidator.validate(studentTemplate, errors);
        
        if (errors.hasErrors()) {
            if (classGrade != null) {
                map.addAttribute(MODEL_ATT_SELECTED_CLASS_GRADE_ID, classGrade.getClassGradeId());
            }
            map.addAttribute(MODEL_ATT_SELECTED_ADDMISSION_NO, selectedAddmission);
            return VIEW_PAGE;
        }
        
        if (selectedClass.equals(AkuraWebConstant.STRING_ZERO)) {
            
            String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_MANDATORY_FIELD_REQUIRED);
            map.addAttribute(MESSAGE, message);
            if (classGrade != null) {
                map.addAttribute(MODEL_ATT_SELECTED_CLASS_GRADE_ID, classGrade.getClassGradeId());
            }
            map.addAttribute(MODEL_ATT_SELECTED_ADDMISSION_NO, selectedAddmission);
            
            return VIEW_PAGE;
        }
        
        Map<String, Object> params = new HashMap<String, Object>();
        
        params.put(REPORT_PARA_TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_VALUE_STUDENT_WISE_ATTENDANCE_REPORT));
        params.put(REPORT_PARA_STUDENTID,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_VALUE_STUDENT_ID));
        params.put(REPORT_PARA_STUDENTNAME,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_VALUE_STUDENT_NAME));
        params.put(REPORT_PARA_DATE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_VALUE_DATE));
        params.put(REPORT_PARA_TIMEIN,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_VALUE_TIME_IN));
        params.put(REPORT_PARA_TIMEOUT,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_VALUE_TIME_OUT));
        params.put(REPORT_PARA_STUDENT, studentTemplate.getDateFrom());
        params.put(REPORT_PARA_DATEFROM, studentTemplate.getDateFrom());
        params.put(REPORT_PARA_DATETO, studentTemplate.getDateTo());
        params.put(REPORT_PARA_ATTENDANCE_FROM,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_PARA_ATTENDANCE_FROM_TEXT));
        params.put(REPORT_PARA_ATTENDANCE_TO,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_PARA_ATTENDANCE_TO_TEXT));
        params.put(REPORT_PARA_LOGO_PATH, ReportUtil.getReportLogo());
        params.put(
                STYLE_TEMPLATE,
                PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                        + PropertyReader.getPropertyValue(SYSTEM_CONFIG, STYLE_PATH));
        params.put(REPORT_GENERATED_ON,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_GENERATED_ON_TEXT));
        params.put(GENERATED_DATE, DateUtil.getReportGeneratedDate(PropertyReader.getPropertyValue(
                ReportUtil.REPORT_TEMPLATE, REPORT_GENERATED_DATE_LOCALE)));
        params.put(PAGE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_PAGE));
        params.put(GPL, AkuraWebConstant.REPORT_GPL);
        
        String todateString = studentTemplate.getDateTo();
        String fromDateString = studentTemplate.getDateFrom();
        
        Date toDate = DateUtil.getParseDate(todateString);
        Date fromDate = DateUtil.getParseDate(fromDateString);
        
        int studentId = studentService.findStudentIdForAdmissionNo(selectedAddmission);
        Student student = studentService.findStudent(studentId);
        Date departureDate = student.getDateOfDeparture();
        
        if (departureDate != null) {
            if (departureDate.before(toDate) && departureDate.after(fromDate)) {
                String depatureDateString = DateUtil.getFormatDate(departureDate);
                studentTemplate.setDateTo(depatureDateString);
                
            }
        }
        
        JRBeanCollectionDataSource studentWiseAttendanceSummary =
                attendanceReportingService.getStudentWiseAttendanceSummary(studentTemplate);
        
        if (studentWiseAttendanceSummary.getRecordCount() != 0) {
            ReportUtil.displayReportInPdfForm(response, studentWiseAttendanceSummary, params,
                    REPORT_NAME_STUDENT_WISE_ATTENDANCE);
        } else {
            setErrorMessage(map);
            if (classGrade != null) {
                map.addAttribute(MODEL_ATT_SELECTED_CLASS_GRADE_ID, classGrade.getClassGradeId());
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
     * Populate addmission nos by class.
     * 
     * @param request the request
     * @return the list
     * @throws AkuraAppException the akura app exception
     */
    @RequestMapping(value = POPULATE_ADDMISSION_NOS_BY_CLASS_HTM)
    @ResponseBody
    public String populateAddmissionNosByClass(HttpServletRequest request) throws AkuraAppException {
    
        String classGradeId = request.getParameter(CLASS_GRADE_ID);
        
        List<String> addmissionList = new ArrayList<String>();
        
        List<StudentClassInfo> studentList =
                studentService.getStudentClassInfoOfClassByYear(Integer.parseInt(classGradeId), DateUtil.currentYear());
        
        SortUtil.sortStudentListByAdmissionNo(studentList);
        
        if (!studentList.isEmpty()) {
            for (StudentClassInfo s : studentList) {
                addmissionList.add(s.getStudent().getAdmissionNo() + AkuraWebConstant.SPACE_STRING
                        + AkuraWebConstant.DASH_STRING + AkuraWebConstant.SPACE_STRING
                        + s.getStudent().getNameWtInitials());
            }
        }
        
        StringBuilder addmissionBuilder = new StringBuilder();
        
        if (!addmissionList.isEmpty()) {
            
            for (String admission : addmissionList) {
                addmissionBuilder.append(admission);
                addmissionBuilder.append(AkuraWebConstant.STRING_COMMA);
            }
        }
        
        return addmissionBuilder.toString();
    }
    
    /**
     * Populate class grade list.
     * 
     * @param request the request
     * @return the list
     * @throws AkuraAppException the akura app exception
     */
    @ModelAttribute(CLASS_GRADE_LIST)
    public List<ClassGrade> populateClassGradeList(HttpServletRequest request) throws AkuraAppException {
    
        return SortUtil.sortClassGradeList(commonService.getClassGradeList());
        
    }
    
}
