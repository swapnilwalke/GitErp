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

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.virtusa.akura.api.dto.AnnualStudentAttendanceTemplate;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.Holiday;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.reporting.service.AttendanceReportingService;
import com.virtusa.akura.reporting.validator.AnnualStudentAttendanceTemplateValidator;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * Controller to generate Annual Student Attendance report.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class AnnualStudentAttendanceController {
        
    /** The Constant ANNUAL_STUDENT_ATTENDANCE_REPORT_HTM. */
    private static final String ANNUAL_STUDENT_ATTENDANCE_REPORT_HTM = "/annualStudentAttendanceReport.htm";
    
    /** The Constant ANNUAL_STUDENT_ATTENDANCE_TEMPLATE. */
    private static final String ANNUAL_STUDENT_ATTENDANCE_TEMPLATE = "AnnualStudentAttendanceTemplate";
    
    /** The Constant REPORTING_ANNUAL_STUDENT_ATTENDANCE_REPORT. */
    private static final String REPORTING_ANNUAL_STUDENT_ATTENDANCE_REPORT = "reporting/annualStudentAttendanceReport";
    
    /** The Constant CLASS_GRADE_LIST. */
    private static final String CLASS_GRADE_LIST = "classGradeList";
    
    /** attribute for holding select option map key. */
    private static final String REPORT_GENERATED_ON = "Annual_Student_Attendance_Report_Generate";
    
    /** attribute for holding select option map key. */
    private static final String REPORT_GENERATE_FIELD = "reportGenerate";
    
    /** attribute for holding select option map key. */
    private static final String NO_DATA_TEXT = "Annual_Student_Attendance_Report_NoData";
    
    /** attribute for holding select option map key. */
    private static final String FULL_NAME_TEXT = "Annual_Student_Attendance_Report_FullName";
    
    /** attribute for holding select option map key. */
    private static final String ADMISSION_NO_TEXT = "Class_Wise_Student_Report_AdmissionNo";
    
    /** attribute for holding select option map key. */
    private static final String YEAR_TEXT = "Annual_Student_Attendance_Report_Year";
    
    /** attribute for holding select option map key. */
    private static final String TOTAL_STUDENTS_TEXT = "Annual_Student_Attendance_Report_Total";
    
    /** attribute for holding select option map key. */
    private static final String CLASS_TEXT = "Annual_Student_Attendance_Report_Class";
    
    /** attribute for holding select option map key. */
    private static final String STUDENTS_LIST_TEXT = "Annual_Student_Attendance_Report_Title";
    
    /** The Constant APPSERVER_HOME. */
    private static final String APPSERVER_HOME = "appserver.home";
    
    /** The Constant COM_AKURA_PATH_IMAGE. */
    private static final String COM_AKURA_PATH_IMAGE = "large.logo.image.path";
    
    /** The Constant LOGO. */
    private static final String LOGO = "logo";
    
    /** The Constant SYSTEM_CONFIG_PROPERTIES. */
    public static final String SYSTEM_CONFIG_PROPERTIES = "systemConfig";
    
    /** attribute for holding select option map key. */
    private static final String NO_DATA_FIELD = "noData";
    
    /** attribute for holding select option map key. */
    private static final String FULL_NAME_FIELD = "fullName";
    
    /** attribute for holding select option map key. */
    private static final String PERCENTAGE_FIELD = "percentage";
    
    /** attribute for holding select option map key. */
    private static final String ADMISSION_NO_FIELD = "admissionNo";
    
    /** attribute for holding select option map key. */
    private static final String YEAR_FIELD = "year";
    
    /** attribute for holding select option map key. */
    private static final String TOTAL_FIELD = "total";
    
    /** attribute for holding select option map key. */
    private static final String CLASS_FIELD = "class";
    
    /** attribute for holding select option map key. */
    private static final String TITLE_FIELD = "title";
    
    /** attribute for holding style path. */
    private static final String STYLE_PATH = "style.path";
    
    /** attribute for holding system config. */
    private static final String SYSTEM_CONFIG = "systemConfig";
    
    /** attribute for holding style template. */
    private static final String STYLE_TEMPLATE = "styleTemplate";
    
    /** variable for holding string "generatedDate". */
    private static final String GENERATED_DATE = "generatedDate";
    
    /** model attribute for report generated date. */
    private static final String REPORT_GENERATED_DATE_LOCALE = "REPORT.GENERATED.DATE.LOCALE";
    
    /** The Constant CLASS_GRADE_DESCRIPTION. */
    private static final String CLASS_GRADE_DESCRIPTION = "classGradeDescription";
    
    /** The Constant YEAR_VALUE. */
    private static final String YEAR_VALUE = "yearValue";
    
    /** The Constant PERCENTAGE_TEXT. */
    private static final String PERCENTAGE_TEXT = "Annual_Student_Attendance_Report_Percentage";
    
    /** The Constant ANNUAL_STUDENT_ATTENDANCE_REPORT. */
    private static final String ANNUAL_STUDENT_ATTENDANCE_REPORT = "annualStudentAttendanceReport";
    
    /** The Constant TOTAL_SUDENTS. */
    private static final String TOTAL_SUDENTS = "totalSudents";
    
    /** The Constant DATASOURCE. */
    private static final String DATASOURCE = "datasource";
    
    /** The Constant REPORT_PAGE. */
    private static final String REPORT_PAGE = "Report_page";
    
    /** The Constant PAGE. */
    private static final String PAGE = "page";
    
    /** The Constant GPL. */
    private static final String GPL = "GPLComment";
    
    /** attribute for holding message. */
    public static final String MESSAGE = "message";
    
    /** attribute for holding error message key. */
    public static final String NO_DATA_ERROR_MESSAGE = "REF.UI.NO.DATA";
    
    /** key to hold string of year start date. */
    private static final String START_DATE = "-01-01";
    
    /** The school service. */
    private CommonService commonService;
    
    /** The attendance reporting service. */
    private AttendanceReportingService attendanceReportingService;
    
    /** The annual student attendance report template validator. */
    private AnnualStudentAttendanceTemplateValidator annualStudentAttendanceTemplateValidator;

    /**
     * Sets the common service.
     *
     * @param objCommonService the commonService to set
     */
    public final void setCommonService(CommonService objCommonService) {
    
        this.commonService = objCommonService;
    }
        
    /**
     * @param objAttendanceReportingService the attendanceReportingService to set
     */
    public void setAttendanceReportingService(AttendanceReportingService objAttendanceReportingService) {
    
        this.attendanceReportingService = objAttendanceReportingService;
    }
    
    /**
     * @param objAnnualStudentAttendanceValidator the annualStudentAttendanceTemplateValidator to set
     */
    public final void setAnnualStudentAttendanceTemplateValidator(
            AnnualStudentAttendanceTemplateValidator objAnnualStudentAttendanceValidator) {
    
        this.annualStudentAttendanceTemplateValidator = objAnnualStudentAttendanceValidator;
    }

    /**
     * Method handles view page of class wise students report form.
     * 
     * @param modelMap of type ModelMap
     * @return java.lang.String
     */
    @RequestMapping(method = RequestMethod.GET)
    public final String showAnnualStudentAttendanceReportForm(final ModelMap modelMap) {
    
        modelMap.addAttribute(ANNUAL_STUDENT_ATTENDANCE_TEMPLATE, new AnnualStudentAttendanceTemplate());
        return REPORTING_ANNUAL_STUDENT_ATTENDANCE_REPORT;
    }
    
    /**
     * Populate class grade list.
     *
     * @return the list
     * @throws AkuraAppException the Akura app exception
     */
    @ModelAttribute(CLASS_GRADE_LIST)
    public List<ClassGrade> populateClassGradeList() throws AkuraAppException {
    
        return SortUtil.sortClassGradeList(commonService.getClassGradeList());
    }
    
    
    /**
     * Generate class wise students report.
     *
     * @param annualStudentAttendanceTemplate the annual Student Attendance Template
     * @param bindingResult the binding result
     * @param modelView the model view
     * @return the model and view
     * @throws AkuraAppException the Akura app exception
     */
    @RequestMapping(value = ANNUAL_STUDENT_ATTENDANCE_REPORT_HTM, method = RequestMethod.POST)
    public ModelAndView generateAnnualStudentAttendanceReport(
            @ModelAttribute(ANNUAL_STUDENT_ATTENDANCE_TEMPLATE) AnnualStudentAttendanceTemplate 
            annualStudentAttendanceTemplate, BindingResult bindingResult, ModelAndView modelView) 
                    throws AkuraAppException {
    
        annualStudentAttendanceTemplateValidator.validate(annualStudentAttendanceTemplate, bindingResult);
        
        String redirectURL = ANNUAL_STUDENT_ATTENDANCE_REPORT;
        Map<String, Object> map = new HashMap<String, Object>();
        
        if (bindingResult.hasErrors()) {
            redirectURL = REPORTING_ANNUAL_STUDENT_ATTENDANCE_REPORT;
        } else {
            
            Date currentDate = new Date();
            String strYr = DateUtil.getStringYear(currentDate);
            
            // set the start date to first day of current year.
            String strStartDate = strYr + START_DATE;
            
            // convert start date type string to Date.
            Date startDate = DateUtil.getParseDate(strStartDate);
            
            // get the holiday list.
            List<Holiday> holidayList = getHolidayList(startDate, currentDate);
            
            // get the annual student attendance list.
            List<AnnualStudentAttendanceTemplate> annualStudentAttendanceList =
                    attendanceReportingService.getAnnualStudentAttendancePercentage(
                            annualStudentAttendanceTemplate.getClassGradeId(), startDate, holidayList);
            
            if (annualStudentAttendanceList != null && !annualStudentAttendanceList.isEmpty()) {
                
                // put data to generate the report.
                setPropertyValuesToReport(map, annualStudentAttendanceList);
                
                JRDataSource datasource = new JRBeanCollectionDataSource(annualStudentAttendanceList);
                map.put(DATASOURCE, datasource);
                
            } else {
                String message = new ErrorMsgLoader().getErrorMessage(NO_DATA_ERROR_MESSAGE);
                map.put(MESSAGE, message);
                redirectURL = REPORTING_ANNUAL_STUDENT_ATTENDANCE_REPORT;
                
            }
            
        }
        
        return new ModelAndView(redirectURL, map);
    }

    /**
     * set the property values for report.
     * 
     * @param map of Map. 
     * @param annualStudentAttendanceList of annualStudentAttendanceList.
     * @throws AkuraAppException the Akura app exception
     */
    private void setPropertyValuesToReport(Map<String, Object> map,
            List<AnnualStudentAttendanceTemplate> annualStudentAttendanceList) throws AkuraAppException {
    
        map.put(TOTAL_SUDENTS, annualStudentAttendanceList.size());
        map.put(YEAR_VALUE, annualStudentAttendanceList.get(0).getYear());
        map.put(CLASS_GRADE_DESCRIPTION, annualStudentAttendanceList.get(0).getClassName());
        
        map.put(LOGO,
                PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, APPSERVER_HOME)
                        + PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, COM_AKURA_PATH_IMAGE));
        
        map.put(TITLE_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STUDENTS_LIST_TEXT));
        map.put(CLASS_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, CLASS_TEXT));
        map.put(TOTAL_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, TOTAL_STUDENTS_TEXT));
        map.put(YEAR_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, YEAR_TEXT));
        map.put(ADMISSION_NO_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, ADMISSION_NO_TEXT));
        map.put(FULL_NAME_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, FULL_NAME_TEXT));
        map.put(PERCENTAGE_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, PERCENTAGE_TEXT));
        map.put(NO_DATA_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, NO_DATA_TEXT));
        map.put(STYLE_TEMPLATE,
                PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                        + PropertyReader.getPropertyValue(SYSTEM_CONFIG, STYLE_PATH));
        map.put(REPORT_GENERATE_FIELD, PropertyReader.getPropertyValue(
                ReportUtil.REPORT_TEMPLATE, REPORT_GENERATED_ON));
        map.put(GENERATED_DATE, DateUtil.getReportGeneratedDate(PropertyReader.getPropertyValue(
                ReportUtil.REPORT_TEMPLATE, REPORT_GENERATED_DATE_LOCALE)));
        map.put(PAGE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_PAGE));
        map.put(GPL, AkuraWebConstant.REPORT_GPL);
    }
    
    /**
     * get holiday list.
     * 
     * @param startDate of type Date.
     * @param currentDate of type Date.
     * @return list of hoiday. 
     * @throws AkuraAppException the Akura app exception
     */
    private List<Holiday> getHolidayList(Date startDate, Date currentDate) throws AkuraAppException {
    
        return commonService.findHolidayByYear(startDate, currentDate);
        
    }
}
