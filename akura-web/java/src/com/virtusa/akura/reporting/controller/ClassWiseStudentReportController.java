/*
 * < �KURA, This application manages the daily activities of a Teacher and a Student of a School>
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

import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.ClassWiseStudentReportTemplate;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.reporting.validator.ClassWiseStudentReportTemplateValidator;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * The Class ClassWiseStudentReportController.
 *
 * @author Virtusa Corporation
 */
@Controller
public class ClassWiseStudentReportController {

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

    /** The Constant REPORT_PAGE. */
    private static final String REPORT_PAGE = "Report_page";

    /** The Constant PAGE. */
    private static final String PAGE = "page";

    /** The Constant GPL. */
    private static final String GPL = "GPLComment";

    /** attribute for holding select option map key. */
    private static final String REPORT_GENERATED_ON = "Class_Wise_Student_Report_Generate";

    /** attribute for holding select option map key. */
    private static final String REPORT_GENERATE_FIELD = "reportGenerate";

    /** attribute for holding select option map key. */
    private static final String NO_DATA_TEXT = "Class_Wise_Student_Report_NoData";

    /** attribute for holding select option map key. */
    private static final String FULL_NAME_TEXT = "Class_Wise_Student_Report_FullName";

    /** attribute for holding select option map key. */
    private static final String ADMISSION_NO_TEXT = "Class_Wise_Student_Report_AdmissionNo";

    /** attribute for holding select option map key. */
    private static final String YEAR_TEXT = "Class_Wise_Student_Report_Year";

    /** attribute for holding select option map key. */
    private static final String TOTAL_STUDENTS_TEXT = "Class_Wise_Student_Report_Total";

    /** attribute for holding select option map key. */
    private static final String CLASS_TEXT = "Class_Wise_Student_Report_Class";

    /** attribute for holding select option map key. */
    private static final String STUDENTS_LIST_TEXT = "Class_Wise_Student_Report_Title";

    /** attribute for holding select option map key. */
    private static final String NO_DATA_FIELD = "noData";

    /** attribute for holding select option map key. */
    private static final String FULL_NAME_FIELD = "fullName";

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

    /** The Constant TOTAL_SUDENTS. */
    private static final String TOTAL_SUDENTS = "totalSudents";

    /** The Constant DATASOURCE. */
    private static final String DATASOURCE = "datasource";

    /** The Constant CLASS_WISE_STUDENTS_REPORT. */
    private static final String CLASS_WISE_STUDENTS_REPORT = "classWiseStudentsReport";

    /** The Constant CLASS_WISE_STUDENT_LIST_REPORT_HTM. */
    private static final String CLASS_WISE_STUDENT_LIST_REPORT_HTM = "/classWiseStudentListReport.htm";

    /** The Constant CLASS_GRADE_LIST. */
    private static final String CLASS_GRADE_LIST = "classGradeList";

    /** The Constant CLASS_WISE_STUDENT_REPORT_TEMPLATE. */
    private static final String CLASS_WISE_STUDENT_REPORT_TEMPLATE = "ClassWiseStudentReportTemplate";

    /** The Constant REPORTING_CLASS_WISE_STUDENT_REPORT. */
    private static final String REPORTING_CLASS_WISE_STUDENT_REPORT = "reporting/classWiseStudentReport";

    /** The Constant APPSERVER_HOME. */
    private static final String APPSERVER_HOME = "appserver.home";

    /** The Constant COM_SMS_PATH_IMAGE. */
    private static final String COM_SMS_PATH_IMAGE = "large.logo.image.path";

    /** The Constant LOGO. */
    private static final String LOGO = "logo";

    /** The Constant SYSTEM_CONFIG_PROPERTIES. */
    public static final String SYSTEM_CONFIG_PROPERTIES = "systemConfig";

    /** The Constant EMAIL_PROPERTIES. */
    public static final String EMAIL_PROPERTIES = "email";

    /** attribute for holding message. */
    public static final String MESSAGE = "message";

    /** attribute for holding error message key. */
    public static final String NO_DATA_ERROR_MESSAGE = "REF.UI.NO.DATA";

    /** The school service. */
    private CommonService commonService;

    /** The student service. */
    private StudentService studentService;

    /** The class wise student report template validator. */
    private ClassWiseStudentReportTemplateValidator classWiseStudentReportTemplateValidator;

    /**
     * Sets the common service.
     *
     * @param objCommonService the commonService to set
     */
    public final void setCommonService(CommonService objCommonService) {

        this.commonService = objCommonService;
    }

    /**
     * Sets the student service.
     *
     * @param objStudentService the studentService to set
     */
    public final void setStudentService(StudentService objStudentService) {

        this.studentService = objStudentService;
    }

    /**
     * Sets the class wise student report template validator.
     *
     * @param objClassWiseStudentReportTemplateValidator the classWiseStudentReportTemplateValidator to set
     */
    public final void setClassWiseStudentReportTemplateValidator(
            ClassWiseStudentReportTemplateValidator objClassWiseStudentReportTemplateValidator) {

        this.classWiseStudentReportTemplateValidator = objClassWiseStudentReportTemplateValidator;
    }

    /**
     * Method handles view page of class wise students report form.
     *
     * @param modelMap of type ModelMap
     * @return java.lang.String
     */
    @RequestMapping(method = RequestMethod.GET)
    public final String showClassWiseStudentsReportForm(final ModelMap modelMap) {

        modelMap.addAttribute(CLASS_WISE_STUDENT_REPORT_TEMPLATE, new ClassWiseStudentReportTemplate());
        return REPORTING_CLASS_WISE_STUDENT_REPORT;
    }

    /**
     * Generate class wise students report.
     *
     * @param classWiseReportTemplate the class wise student report template
     * @param bindingResult the binding result
     * @param modelView the model view
     * @param modelMap the model map
     * @return the model and view
     * @throws AkuraAppException the sMS app exception
     */
    @RequestMapping(value = CLASS_WISE_STUDENT_LIST_REPORT_HTM, method = RequestMethod.POST)
    public ModelAndView generateClassWiseStudentsReport(
            @ModelAttribute(CLASS_WISE_STUDENT_REPORT_TEMPLATE) ClassWiseStudentReportTemplate classWiseReportTemplate,
            BindingResult bindingResult, ModelAndView modelView, ModelMap modelMap) throws AkuraAppException {

        classWiseStudentReportTemplateValidator.validate(classWiseReportTemplate, bindingResult);

        String redirectURL = CLASS_WISE_STUDENTS_REPORT;
        Map<String, Object> map = new HashMap<String, Object>();

        if (bindingResult.hasErrors()) {
            redirectURL = REPORTING_CLASS_WISE_STUDENT_REPORT;
        } else {

            List<StudentClassInfo> studentClassInfoList =
                    studentService.getClassStudentList(classWiseReportTemplate.getClassGradeId(), DateUtil
                            .currentYearOnly());
            if (studentClassInfoList != null) {
                if (!studentClassInfoList.isEmpty()) {

                    for(StudentClassInfo stuClassInfoObject:studentClassInfoList)
                    {
                        Student studentObject=stuClassInfoObject.getStudent();

                        studentObject.setFullName(studentObject.getNameWtInitials());
                    }


                    SortUtil.sortStudentClassInfoList(studentClassInfoList);
                    JRDataSource datasource = new JRBeanCollectionDataSource(studentClassInfoList);
                    map.put(DATASOURCE, datasource);
                    map.put(LOGO, PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, APPSERVER_HOME)
                            + PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, COM_SMS_PATH_IMAGE));
                    map.put(TOTAL_SUDENTS, studentClassInfoList.size());
                    map.put(TITLE_FIELD, PropertyReader
                            .getPropertyValue(ReportUtil.REPORT_TEMPLATE, STUDENTS_LIST_TEXT));
                    map.put(CLASS_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, CLASS_TEXT));
                    map.put(TOTAL_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                            TOTAL_STUDENTS_TEXT));
                    map.put(YEAR_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, YEAR_TEXT));
                    map.put(ADMISSION_NO_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                            ADMISSION_NO_TEXT));
                    map.put(FULL_NAME_FIELD, PropertyReader
                            .getPropertyValue(ReportUtil.REPORT_TEMPLATE, FULL_NAME_TEXT));
                    map.put(NO_DATA_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, NO_DATA_TEXT));
                    map.put(STYLE_TEMPLATE, PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                            + PropertyReader.getPropertyValue(SYSTEM_CONFIG, STYLE_PATH));
                    map.put(REPORT_GENERATE_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                            REPORT_GENERATED_ON));
                    map.put(GENERATED_DATE, DateUtil.getReportGeneratedDate(PropertyReader.getPropertyValue(
                            ReportUtil.REPORT_TEMPLATE, REPORT_GENERATED_DATE_LOCALE)));
                    map.put(PAGE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_PAGE));
                    map.put(GPL, AkuraWebConstant.REPORT_GPL);
                } else {

                    String message = new ErrorMsgLoader().getErrorMessage(NO_DATA_ERROR_MESSAGE);
                    modelMap.addAttribute(MESSAGE, message);
                    redirectURL = REPORTING_CLASS_WISE_STUDENT_REPORT;
                    return new ModelAndView(redirectURL, modelMap);
                }

            }
        }

        return new ModelAndView(redirectURL, map);
    }

    /**
     * Populate class grade list.
     *
     * @return the list
     * @throws AkuraAppException the sMS app exception
     */
    @ModelAttribute(CLASS_GRADE_LIST)
    public List<ClassGrade> populateClassGradeList() throws AkuraAppException {

        return SortUtil.sortClassGradeList(commonService.getClassGradeList());
    }
}
