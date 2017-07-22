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

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.virtusa.akura.api.dto.CRStudentPerformanceTemplate;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.reporting.service.CommonReportingService;
import com.virtusa.akura.reporting.validator.CRStudentPerformanceBetweenValidator;
import com.virtusa.akura.reporting.validator.CRStudentPerformanceGreaterValidator;
import com.virtusa.akura.reporting.validator.CRStudentPerformanceValidator;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * used to generate student performance reports.
 *
 * @author Virtusa Corporation
 */
@Controller
@SessionAttributes("cRStudentPerformanceTemplate")
public class CRStudentPerformanceController {

    /** attribute for holding select option map key. */
    private static final String SELECT_OPTION = "selectOption";

    /** attribute for holding select option key. */
    private static final String STU_PERFORMANCE_SELECT_OPTION = "StuPerformance_selectOption";

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

    /** attribute for holding style path. */
    private static final String STYLE_PATH = "style.path";

    /** attribute for holding app home. */
    private static final String APPSERVER_HOME = "appserver.home";

    /** attribute for holding system config. */
    private static final String SYSTEM_CONFIG = "systemConfig";

    /** attribute for holding style template. */
    private static final String STYLE_TEMPLATE = "styleTemplate";

    /** String to hold zero. */
    private static final String ZERO = "0";

    /** request mapping value for view class list of grade. */
    private static final String GRADE_CLASS_HTM = "/populateGradeClassList2.htm";

    /** key to hold string selectedValue. */
    private static final String SELECTED_VALUE = "selectedValue";

    /** String to hold parameter selectClasses. */
    private static final String SELECT_CLASSES = "selectClasses";

    /** key to hold error message when mandatory field null. */
    private static final String MANDATORY_FIELD_CAN_T_BE_NULL = "REF.UI.MANDATORY.FIELD.REQUIRED";

    /** attribute for holding string. */
    private static final String STRING_EMPTY = "";

    /** attribute for holding error message key. */
    public static final String NO_DATA_ERROR_MESSAGE = "REF.UI.NO.DATA";

    /** attribute for holding message. */
    public static final String MESSAGE = "message";

    /** request mapping value for absent class wise. */
    private static final String REQ_MAP_PERFORMANCE = "/studentPreformanceReport.htm";

    /** form index 3. */
    private static final String FORM_INDEX_THREE = "3";

    /** form index 2. */
    private static final String FORM_INDEX_TWO = "2";

    /** form index 1. */
    private static final String FORM_INDEX_ONE = "1";

    /** form index for view page. */
    private static final String REQUEST_PARA_FORM_INDEX = "formIndex";

    /** report value for subject description label. */
    private static final String REPORT_VALUE_STU_PERFORMANCE_SUBJECT_DESCRIPTION_LABLE_TEXT =
            "StuPerformance_subjectDescriptionLableText";

    /** report value for marks label. */
    private static final String REPORT_VALUE_STU_PERFORMANCE_MARKS_LABLE_TEXT = "StuPerformance_marksLableText";

    /** report value for marks label. */
    private static final String REPORT_VALUE_STU_PERFORMANCE_MARKS_LABLE_TEXT1 = "StuPerformance_marksLabelText2";

    /** report value for term description label. */
    private static final String REPORT_VALUE_STU_PERFORMANCE_TERM_DESCRIPTION_LABLE_TEXT =
            "StuPerformance_termDescriptionLableText";

    /** report value for full name label. */
    private static final String REPORT_VALUE_STU_PERFORMANCE_FULL_NAME_LABLE_TEXT = "StuPerformance_fullNameLableText";

    /** report value for admission number label. */
    private static final String REPORT_VALUE_STU_PERFORMANCE_ADMISSION_NUMBER_LABLE_TEXT =
            "StuPerformance_admissionNumberLableText";

    /** report value for class label. */
    private static final String REPORT_VALUE_STU_PERFORMANCE_CLASS_LABEL_TEXT2 = "StuPerformance_classLabelText2";

    /** report value for class label. */
    private static final String REPORT_VALUE_STU_PERFORMANCE_CLASS_LABEL_TEXT = "StuPerformance_classLabelText";

    /** report value for grade class label. */
    private static final String REPORT_VALUE_STU_PERFORMANCE_GRADE_CLASS_LABLE_TEXT =
            "StuPerformance_gradeClassLableText";

    /** report value for title. */
    private static final String REPORT_VALUE_STU_PERFORMANCE_TITLE_TEXT = "StuPerformance_titleText";

    /** report parameter for mark label. */
    private static final String REPORT_PARA_MARKS_LABEL_TEXT = "MarksLabelText";

    /** report parameter for subject description label. */
    private static final String REPORT_PARA_SUBJECT_DESCRIPTION_LABLE_TEXT = "subjectDescriptionLableText";

    /** report parameter for term description label. */
    private static final String REPORT_PARA_TERM_DESCRIPTION_LABLE_TEXT = "termDescriptionLableText";

    /** report parameter for full name label. */
    private static final String REPORT_PARA_FULL_NAME_LABLE_TEXT = "fullNameLableText";

    /** report parameter for admission number label. */
    private static final String REPORT_PARA_ADMISSION_NUMBER_LABLE_TEXT = "admissionNumberLableText";

    /** report parameter for class label. */
    private static final String REPORT_PARA_CLASS_LABEL_TEXT2 = "classLabelText2";

    /** report parameter for class label. */
    private static final String REPORT_PARA_CLASS_LABEL_TEXT = "classLabelText";

    /** report parameter for grade class label. */
    private static final String REPORT_PARA_GRADE_CLASS_LABLE_TEXT = "gradeClassLableText";

    /** report parameter for title. */
    private static final String REPORT_PARA_STUDENT_PERFORMANCE_GENERAL_TITLE_TEXT =
            "StudentPerformanceGeneralTitleText";

    /** report parameter for logo path. */
    private static final String REPORT_PARA_LOGO_PATH = "logoPath";

    /** report parameter for marks. */
    private static final String REPORT_PARA_MARKS = "marks";

    /** mark range in between. */
    private static final String STU_PERFORMANCE_MARKS_RANGE_IN_BETWEEN = "StuPerformance_marksRangeInBetween";

    /** mark range grater than. */
    private static final String STU_PERFORMANCE_MARKS_RANGE_GEATER_THAN = "StuPerformance_marksRangeGeaterThan";

    /** mark range less than. */
    private static final String STU_PERFORMANCE_MARKS_RANGE_LESS_THAN = "StuPerformance_marksRangeLessThan";

    /** view method for student performance. */
    private static final String VIEW_METHOD_REPORTING_STUDENT_PERFORMANCE = "reporting/studentPerformance";

    /** model attribute for student performance template. */
    private static final String MODEL_ATT_STUDENT_PERFORMANCE_TEMPLATE = "cRStudentPerformanceTemplate";

    /** report name for jrxml file. */
    private static final String REPORT_NAME_STUDENT_PERFORMANCE_GENERAL = "CRStudentPerformanceGeneral";

    /** report parameter for mark range. */
    private static final String REPORT_PARA_MARKS_RANGE_LABLE_TEXT = "marksRangeLableText";

    /** model attribute of class grade list. */
    private static final String MODEL_ATT_GRADE_LIST = "gradeList";

    /** model attribute of class list. */
    private static final String MODEL_ATT_CLASS_LIST = "classList";

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(CRStudentPerformanceController.class);

    /**
     * CommonReportingService Object to retrieve Common Reporting Service related information.
     */
    @Autowired
    private CommonReportingService commonReportingService;

    /** CommonService attribute for holding commonService. */
    @Autowired
    private CommonService commonService;

    /**
     * Sets the CommonReportingService object.
     *
     * @param setCommonReportingService {@link CommonReportingService}
     */
    public void setCommonReportingService(final CommonReportingService setCommonReportingService) {

        this.commonReportingService = setCommonReportingService;
    }

    /**
     * Set CommonService object.
     *
     * @param objCommonService set common service object.
     */
    public void setCommonService(CommonService objCommonService) {

        this.commonService = objCommonService;
    }

    /**
     * cRStudentPerformanceValidator attribute for holding CRStudentPerformanceValidator object.
     */
    private CRStudentPerformanceValidator cRStudentPerformanceValidator;

    /**
     * Set CRStudentPerformanceValidator object.
     *
     * @param cRStudentPerformanceValidatorRef set CRStudentPerformanceValidator object.
     */
    public void setcRStudentPerformanceValidator(CRStudentPerformanceValidator cRStudentPerformanceValidatorRef) {

        this.cRStudentPerformanceValidator = cRStudentPerformanceValidatorRef;
    }

    /**
     * cRStudentPerformanceGreaterValidator attribute for holding CRStudentPerformanceGreaterValidator object.
     */
    private CRStudentPerformanceGreaterValidator cRStudentPerformanceGreaterValidator;

    /**
     * Set CRStudentPerformanceGreaterValidator object.
     *
     * @param cRStudentPerformanceGreaterValidatorRef set CRStudentPerformanceGreaterValidator object.
     */
    public void setcRStudentPerformanceGreaterValidator(
            CRStudentPerformanceGreaterValidator cRStudentPerformanceGreaterValidatorRef) {

        this.cRStudentPerformanceGreaterValidator = cRStudentPerformanceGreaterValidatorRef;
    }

    /**
     * cRStudentPerformanceBetweenValidator attribute for holding CRStudentPerformanceBetweenValidator object.
     */

    private CRStudentPerformanceBetweenValidator cRStudentPerformanceBetweenValidator;

    /**
     * Set CRStudentPerformanceBetweenValidator object.
     *
     * @param cRStudentPerformanceBetweenValidatorRef set CRStudentPerformanceBetweenValidator object.
     */
    public void setcRStudentPerformanceBetweenValidator(
            CRStudentPerformanceBetweenValidator cRStudentPerformanceBetweenValidatorRef) {

        this.cRStudentPerformanceBetweenValidator = cRStudentPerformanceBetweenValidatorRef;
    }

    /**
     * Used to collect user the input data to generate the report.
     *
     * @param modelMap of type ModelMap
     * @return java.lang.String
     * @throws AkuraAppException throw exception if occur.
     */

    @RequestMapping(method = RequestMethod.GET)
    public String showReportForm(ModelMap modelMap) throws AkuraAppException {

        LOG.info("Calling StudentPerformance.jsp to collect data for input pages");
        CRStudentPerformanceTemplate cRStudentPerformanceTemplate = new CRStudentPerformanceTemplate();

        modelMap.addAttribute(MODEL_ATT_STUDENT_PERFORMANCE_TEMPLATE, cRStudentPerformanceTemplate);
        String selectOption =
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STU_PERFORMANCE_SELECT_OPTION);
        modelMap.addAttribute(SELECT_OPTION, selectOption);

        return VIEW_METHOD_REPORTING_STUDENT_PERFORMANCE;
    }

    /**
     * Returns a list of Grades.
     *
     * @return grade List - a list of grades.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @ModelAttribute(MODEL_ATT_GRADE_LIST)
    public List<Grade> populateGradeList() throws AkuraAppException {

        return commonService.getGradeList();
    }

    /**
     * Returns a list of Class.
     *
     * @return Class List - a list of Class.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @ModelAttribute(MODEL_ATT_CLASS_LIST)
    public List<ClassGrade> populateSchoolClassList() throws AkuraAppException {

        return commonService.getClassGradeList();
    }

    /**
     * Generate Student Performance Reports .
     *
     * @param studentTemplate collect user input data to generate the report.
     * @param request of type HttpServletRequest
     * @param response of type HttpServletResponse
     * @param errors of type BindingResult
     * @return java.lang.String
     * @param map of type ModelMap
     * @throws AkuraAppException AkuraAppException
     */

    @RequestMapping(value = REQ_MAP_PERFORMANCE, method = RequestMethod.POST)
    public String onSubmit(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute(MODEL_ATT_STUDENT_PERFORMANCE_TEMPLATE) CRStudentPerformanceTemplate studentTemplate,
            BindingResult errors, ModelMap map) throws AkuraAppException {

        String returnString = STRING_EMPTY;

        LOG.info("print criteria ***");
        LOG.info("cRStudentPerformanceTemplate " + studentTemplate.getGradeDescription() + " / "
                + studentTemplate.getClassDescription() + " / less = " + studentTemplate.getLessThan() + " / greate = "
                + studentTemplate.getGreaterThan() + " / In between = " + studentTemplate.getInBetweenLessValue()
                + " to " + studentTemplate.getInBetweenGreaterValue());

        LOG.info("Start processing user data for Student Perforance Report");
        Map<String, Object> params = new HashMap<String, Object>();

        // report 1,2 and 3 respectively less than, greater than and in between
        // used same template and same
        // data - common parameter and method called taken out

        // create criteria text
        String lessThanText =
                studentTemplate.getLessThan() > 0 ? MessageFormat.format(PropertyReader.getPropertyValue(
                        ReportUtil.REPORT_TEMPLATE, STU_PERFORMANCE_MARKS_RANGE_LESS_THAN), studentTemplate
                        .getLessThan()) : null;
        String greaterThanText =
                studentTemplate.getGreaterThan() > 0 ? MessageFormat.format(PropertyReader.getPropertyValue(
                        ReportUtil.REPORT_TEMPLATE, STU_PERFORMANCE_MARKS_RANGE_GEATER_THAN), studentTemplate
                        .getGreaterThan()) : null;
        String inBetweenText =
                studentTemplate.getInBetweenLessValue() > 0 && studentTemplate.getInBetweenGreaterValue() > 0
                        ? MessageFormat.format(PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                                STU_PERFORMANCE_MARKS_RANGE_IN_BETWEEN), studentTemplate.getInBetweenLessValue(),
                                studentTemplate.getInBetweenGreaterValue()) : null;

        // end common data for report 1,2 and 3

        if (request.getParameter(REQUEST_PARA_FORM_INDEX).equals(FORM_INDEX_ONE)) {
            // title area
            cRStudentPerformanceValidator.validate(studentTemplate, errors);

            if (errors.hasErrors()) {

                return VIEW_METHOD_REPORTING_STUDENT_PERFORMANCE;
            }
            params.put(REPORT_PARA_MARKS_RANGE_LABLE_TEXT, lessThanText);
        }
        if (request.getParameter(REQUEST_PARA_FORM_INDEX).equals(FORM_INDEX_TWO)) {
            // title area
            cRStudentPerformanceGreaterValidator.validate(studentTemplate, errors);
            if (errors.hasErrors()) {
                return VIEW_METHOD_REPORTING_STUDENT_PERFORMANCE;
            }
            params.put(REPORT_PARA_MARKS_RANGE_LABLE_TEXT, greaterThanText);

        }
        if (request.getParameter(REQUEST_PARA_FORM_INDEX).equals(FORM_INDEX_THREE)) {
            cRStudentPerformanceBetweenValidator.validate(studentTemplate, errors);
            if (errors.hasErrors()) {
                return VIEW_METHOD_REPORTING_STUDENT_PERFORMANCE;
            }
            params.put(REPORT_PARA_MARKS_RANGE_LABLE_TEXT, inBetweenText);
        }

        if (request.getParameter(SELECT_CLASSES).equals(ZERO)) {

            String message = new ErrorMsgLoader().getErrorMessage(MANDATORY_FIELD_CAN_T_BE_NULL);
            map.addAttribute(MESSAGE, message);
            return VIEW_METHOD_REPORTING_STUDENT_PERFORMANCE;
        }

        int classGradeId = Integer.parseInt(request.getParameter(SELECT_CLASSES));

        ClassGrade classGradeObj = commonService.findClassGrade(classGradeId);
        String gradeDescription = classGradeObj.getGrade().getDescription();
        String classDescription = classGradeObj.getDescription();

        studentTemplate.setGradeDescriptionLess(gradeDescription);
        studentTemplate.setClassDescriptionLess(classDescription);
        studentTemplate.setGradeDescriptionGreater(gradeDescription);
        studentTemplate.setClassDescriptionGreater(classDescription);
        studentTemplate.setGradeDescription(gradeDescription);
        studentTemplate.setClassDescription(classDescription);

        // title area
        params.put(REPORT_PARA_LOGO_PATH, ReportUtil.getReportLogo());
        params.put(REPORT_PARA_STUDENT_PERFORMANCE_GENERAL_TITLE_TEXT, PropertyReader.getPropertyValue(
                ReportUtil.REPORT_TEMPLATE, REPORT_VALUE_STU_PERFORMANCE_TITLE_TEXT));
        params.put(REPORT_PARA_GRADE_CLASS_LABLE_TEXT, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_VALUE_STU_PERFORMANCE_GRADE_CLASS_LABLE_TEXT));

        params.put(REPORT_PARA_CLASS_LABEL_TEXT, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_VALUE_STU_PERFORMANCE_CLASS_LABEL_TEXT));
        params.put(REPORT_PARA_CLASS_LABEL_TEXT2, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_VALUE_STU_PERFORMANCE_CLASS_LABEL_TEXT2));
        params.put(REPORT_PARA_ADMISSION_NUMBER_LABLE_TEXT, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_VALUE_STU_PERFORMANCE_ADMISSION_NUMBER_LABLE_TEXT));
        params.put(REPORT_PARA_FULL_NAME_LABLE_TEXT, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_VALUE_STU_PERFORMANCE_FULL_NAME_LABLE_TEXT));
        params.put(REPORT_PARA_TERM_DESCRIPTION_LABLE_TEXT, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_VALUE_STU_PERFORMANCE_TERM_DESCRIPTION_LABLE_TEXT));
        params.put(REPORT_PARA_SUBJECT_DESCRIPTION_LABLE_TEXT, PropertyReader.getPropertyValue(
                ReportUtil.REPORT_TEMPLATE, REPORT_VALUE_STU_PERFORMANCE_SUBJECT_DESCRIPTION_LABLE_TEXT));
        params.put(REPORT_PARA_MARKS_LABEL_TEXT, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_VALUE_STU_PERFORMANCE_MARKS_LABLE_TEXT));
        params.put(REPORT_PARA_MARKS, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_VALUE_STU_PERFORMANCE_MARKS_LABLE_TEXT1));
        params.put(STYLE_TEMPLATE, PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                + PropertyReader.getPropertyValue(SYSTEM_CONFIG, STYLE_PATH));
        params.put(REPORT_GENERATED_ON, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_GENERATED_ON_TEXT));
        params.put(GENERATED_DATE, DateUtil.getReportGeneratedDate(PropertyReader.getPropertyValue(
                ReportUtil.REPORT_TEMPLATE, REPORT_GENERATED_DATE_LOCALE)));
        params.put(PAGE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_PAGE));
        params.put(GPL, AkuraWebConstant.REPORT_GPL);

        // common call to make 1,2 and 3 reports
        JRBeanCollectionDataSource disciplinaryActionsList =
                commonReportingService.getStudentPerformance(studentTemplate);

        if (disciplinaryActionsList.getRecordCount() != 0) {
            ReportUtil.displayReportInPdfForm(response, disciplinaryActionsList, params,
                    REPORT_NAME_STUDENT_PERFORMANCE_GENERAL);
        } else {
            setErrorMessage(map);
            returnString = VIEW_METHOD_REPORTING_STUDENT_PERFORMANCE;
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
     * Method is to return GradeClass list.
     *
     * @param request - HttpServletRequest
     * @param modelMap - ModelMap attribute.
     * @return list of classGrade
     * @throws AkuraAppException - Detailed exception
     */

    @RequestMapping(value = GRADE_CLASS_HTM)
    @ResponseBody
    public String populateGradeClass(ModelMap modelMap, HttpServletRequest request) throws AkuraAppException {

        StringBuilder allGradeClass = new StringBuilder();
        int gradeId = Integer.parseInt(request.getParameter(SELECTED_VALUE));
        Grade grade = commonService.findGradeById(gradeId);
        List<ClassGrade> classGrades = commonService.getClassGradeListByGrade(grade);
        boolean isFirst = true;
        StringBuilder classes = new StringBuilder();

        for (ClassGrade classGrade : classGrades) {
            classes.append(classGrade.getDescription());
            classes.append("_");
            classes.append(classGrade.getClassGradeId());

            if (isFirst) {
                allGradeClass.append(classes.toString()); // no comma
                isFirst = false;
            } else {
                allGradeClass.append(","); // comma
                allGradeClass.append(classes.toString());
            }
            classes.delete(0, classes.length());
        }
        return allGradeClass.toString();
    }
}
