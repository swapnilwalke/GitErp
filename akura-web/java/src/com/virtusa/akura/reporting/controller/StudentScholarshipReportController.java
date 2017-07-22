
package com.virtusa.akura.reporting.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.virtusa.akura.api.dto.Scholarship;
import com.virtusa.akura.api.dto.StudentScholarshipTemplate;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.reporting.service.CommonReportingService;
import com.virtusa.akura.reporting.validator.StudentScholarshipReportValidator;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * Controller class to generate a report of scholarship wise student .
 */

@Controller
public class StudentScholarshipReportController {
    
    /** The Constant LOGO. */
    private static final String LOGO = "logo";
    
    /** Constant for Not applicable. */
    private static final String NOT_APPLICABLE = "N/A";
    
    /** Constant for count. */
    private static final String COUNT = "count";
    
    /** Constant for Student Count. */
    private static final String STUDENT_COUNT_LABEL = "Student Count :";
    
    /** Constant for countLabel. */
    private static final String COUNT_LABEL = "countLabel";
    
    /** Constant for Class. */
    private static final String CLASS = "Class";
    
    /** Constant for classLabel. */
    private static final String CLASS_LABEL = "classLabel";
    
    /** Constant for Student Name. */
    private static final String STUDENT_NAME = "Student Name";
    
    /** Constant for nameLabel. */
    private static final String NAME_LABEL = "nameLabel";
    
    /** Constant for Admission No. */
    private static final String ADMISSION_NO = "Admission No";
    
    /** Constant for admissionLabel. */
    private static final String ADMISSION_LABEL = "admissionLabel";
    
    /** Constant for sponsorship. */
    private static final String SPONSORSHIP = "sponsorship";
    
    /** Constant for Sponsorship Label value. */
    private static final String SPONSORSHIP_LABEL_VALUE = "Sponsorship :";
    
    /** Constant for sponsorshipLable. */
    private static final String SPONSORSHIP_LABLE = "sponsorshipLable";
    
    /** Constant for scholarshipName. */
    private static final String SCHOLARSHIP_NAME = "scholarshipName";
    
    /** Constant for Scholarship Name. */
    private static final String SCHOLARSHIP_NAME_LABEL = "Scholarship Name :";
    
    /** Constant for scholarshipLabelName. */
    private static final String SCHOLARSHIP_LABEL_NAME = "scholarshipLabelName";
    
    /** Constant for Report Generated On. */
    private static final String REPORT_GENERATED_ON2 = "Report Generated On :";
    
    /** Constant for Student Scholarship Report. */
    private static final String STUDENT_SCHOLARSHIP_REPORT = "Student Scholarship Report";
    
    /** Constant for title. */
    private static final String TITLE = "title";
    
    /** Constant for No student assigned to the selected scholarship. */
    private static final String NO_STUDENT_ASSIGNED_TO_THE_SELECTED_SCHOLARSHIP =
            "No student assigned to the selected scholarship";
    
    /** Constant for message. */
    private static final String MESSAGE = "message";
    
    /** Constant for reporting/studentScholarshipReport. */
    private static final String REPORTING_STUDENT_SCHOLARSHIP_REPORT = "reporting/studentScholarshipReport";
    
    /** Constant for /generateStudentScholarshipReport.htm. */
    private static final String GENERATE_STUDENT_SCHOLARSHIP_REPORT_HTM = "/generateStudentScholarshipReport.htm";
    
    /** Constant for scholarshipList. */
    private static final String SCHOLARSHIP_LIST = "scholarshipList";
    
    /** Constant for scholarshipCommand. */
    private static final String SCHOLARSHIP_COMMAND = "scholarshipCommand";
    
    /** Constant for styleTemplate. */
    private static final String STYLE_TEMPLATE = "styleTemplate";
    
    /** property file name. */
    private static final String SYSTEM_CONFIG_PROPERTIES = "systemConfig";
    
    /** Holds the value for appserver.home. */
    private static final String APPSERVER_HOME = "appserver.home";
    
    /** Constant for style.path. */
    private static final String STYLE_PATH = "style.path";
    
    /** The Constant GENERATED_ON. */
    private static final String GENERATED_ON = "generatedOn";
    
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
    
    /** The reference to the studentScholarshipReportValidator. */
    private StudentScholarshipReportValidator studentScholarshipReportValidator;
    
    /** The reference to the CommonService. */
    private CommonService commonService;
    
    /** The Constant for sponsorshipVal . */
    private String sponsorshipVal;
    
    /** The Constant STUDENT_SUMMARY_REPORT. */
    private static final String STUDENTS_SCHOLARSHIP_REPORT = "studentScholarshipReport";
    
    /** The Constant DATASOURCE. */
    private static final String DATASOURCE = "datasource";
    
    /** CommonReportingService attribute for holding commonReportingService. */
    @Autowired
    private CommonReportingService commonReportingService;
    
    /**
     * Sets CommonReportingService object.
     * 
     * @param objCommonReportingService set common service object.
     */
    public void setCommonReportingService(CommonReportingService objCommonReportingService) {
    
        this.commonReportingService = objCommonReportingService;
    }
    
    /**
     * Sets CommonService object.
     * 
     * @param commonServiceValue set common service object.
     */
    public final void setCommonService(CommonService commonServiceValue) {
    
        this.commonService = commonServiceValue;
        
    }
    
    /**
     * Sets StudentScholarshipReportValidator object.
     * 
     * @param studentScholarshipReportValidatorRef set StudentScholarshipReportValidator object.
     */
    public void setStudentScholarshipReportValidator(
            StudentScholarshipReportValidator studentScholarshipReportValidatorRef) {
    
        this.studentScholarshipReportValidator = studentScholarshipReportValidatorRef;
    }
    
    /**
     * Populate scholarship lisr.
     * 
     * @return the list
     * @throws AkuraAppException the akura app exception
     */
    @ModelAttribute(SCHOLARSHIP_LIST)
    public List<Scholarship> populateScholarshipList() throws AkuraAppException {
    
        return SortUtil.sortScholarshipList(commonService.getScholarshipList());
    }
    
    /**
     * Display Form View for of the controller and binding it to StudentScholarshipTemplate.
     * 
     * @param model of type ModelMap
     * @return java.lang.String
     * @throws AkuraAppException AkuraAppException
     */
    
    @RequestMapping(method = RequestMethod.GET)
    public String showReportForm(final ModelMap model) throws AkuraAppException {
    
        StudentScholarshipTemplate studentScholarshipTemplate = new StudentScholarshipTemplate();
        model.addAttribute(SCHOLARSHIP_COMMAND, studentScholarshipTemplate);
        return REPORTING_STUDENT_SCHOLARSHIP_REPORT;
    }
    
    /**
     * Perform the logic of the controller to generate scholarship wise student report .
     * 
     * @param studentTemplate of type StudentScholarshipTemplate
     * @param response of type HttpServletResponse
     * @param errors of type BindingResult
     * @return java.lang.String
     * @param map of type ModelMap
     * @throws AkuraAppException AkuraAppException
     */
    @RequestMapping(value = GENERATE_STUDENT_SCHOLARSHIP_REPORT_HTM)
    public ModelAndView generateReport(HttpServletResponse response,
            @ModelAttribute(SCHOLARSHIP_COMMAND) StudentScholarshipTemplate studentTemplate, BindingResult errors,
            ModelMap map) throws AkuraAppException {
    
        String returnView = STUDENTS_SCHOLARSHIP_REPORT;
        
        Map<String, Object> params = new HashMap<String, Object>();
        studentScholarshipReportValidator.validate(studentTemplate, errors);
        

        
        if (!errors.hasErrors()) {
            JRBeanCollectionDataSource studentScholarship =
                    commonReportingService.getStudentScholarshipList(studentTemplate);
            
            @SuppressWarnings("unchecked")
            List<Object> studentList = (List<Object>) studentScholarship.getData();
            
            SortUtil.sortStudentTemplateListByAdmissionNumber(studentList);
            
            map.put(DATASOURCE, studentList);
            
            if (studentScholarship.getRecordCount() == 0) {
                returnView = REPORTING_STUDENT_SCHOLARSHIP_REPORT;
                map.addAttribute(MESSAGE, NO_STUDENT_ASSIGNED_TO_THE_SELECTED_SCHOLARSHIP);
                
            } else {
                
                setReportProperties(studentTemplate, studentScholarship, params);
                
            }
            
        } else {

            returnView = REPORTING_STUDENT_SCHOLARSHIP_REPORT;

        }
        return new ModelAndView(returnView, params);
        
    }
    
    /**
     * Perform the logic of the controller to generate scholarship wise student report .
     * 
     * @param studentTemplate of type StudentScholarshipTemplate
     * @param studentScholarship of type JRBeanCollectionDataSource
     * @param params of type Map
     * @throws AkuraAppException AkuraAppException
     */
    private void setReportProperties(StudentScholarshipTemplate studentTemplate,
            JRBeanCollectionDataSource studentScholarship, Map<String, Object> params) throws AkuraAppException {
    
        params.put(TITLE, STUDENT_SCHOLARSHIP_REPORT);
        params.put(LOGO, ReportUtil.getReportLogo());
        params.put(GENERATED_ON, REPORT_GENERATED_ON2);
        params.put(GENERATED_DATE, DateUtil.getReportGeneratedDate(PropertyReader.getPropertyValue(
                ReportUtil.REPORT_TEMPLATE, REPORT_GENERATED_DATE_LOCALE)));
        params.put(SCHOLARSHIP_LABEL_NAME, SCHOLARSHIP_NAME_LABEL);
        params.put(SCHOLARSHIP_NAME, studentTemplate.getScholarship());
        params.put(SPONSORSHIP_LABLE, SPONSORSHIP_LABEL_VALUE);
        
        sponsorshipVal = commonService.getSponsorship(studentTemplate.getScholarship());
        
        if (!(sponsorshipVal.equals(AkuraWebConstant.EMPTY_STRING))) {
            params.put(SPONSORSHIP, sponsorshipVal);
            
        } else {
            params.put(SPONSORSHIP, NOT_APPLICABLE);
        }
        params.put(ADMISSION_LABEL, ADMISSION_NO);
        
        params.put(NAME_LABEL, STUDENT_NAME);
        
        params.put(CLASS_LABEL, CLASS);
        
        params.put(STYLE_TEMPLATE, PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, APPSERVER_HOME)
                + PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, STYLE_PATH));
        params.put(PAGE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_PAGE));
        params.put(GPL, AkuraWebConstant.REPORT_GPL);
        params.put(COUNT_LABEL, STUDENT_COUNT_LABEL);
        params.put(COUNT, studentScholarship.getRecordCount());
    }
    
}
