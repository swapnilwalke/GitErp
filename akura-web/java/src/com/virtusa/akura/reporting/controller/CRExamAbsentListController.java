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

import com.virtusa.akura.api.dto.CRExamAbsentTemplate;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.SchoolClass;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.reporting.service.CommonReportingService;
import com.virtusa.akura.reporting.validator.CRExamAbsentListValidator;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * Controller to generate Exam Absent list report.
 *
 * @author Virtusa Corporation
 */
@Controller
@SessionAttributes("cRExamAbsentTemplate")
public class CRExamAbsentListController {

    /** The Constant model attribute. */
    private static final String MODEL_ATT_SELECTED_GRADE_ID = "selectedGradeId";

    /** The Constant REPORT_PAGE. */
    private static final String REPORT_PAGE = "Report_page";

    /** The Constant PAGE. */
    private static final String PAGE = "page";

    /** The Constant GPL. */
    private static final String GPL = "GPLComment";

    /** variable for holding string "generatedDate". */
    private static final String GENERATED_DATE = "generatedDate";

    /** variable for holding string "ExamAbsentClassWise_reportGeneratedOn". */
    private static final String CLASS_WISE_REPORT_GENERATED_ON_KEY = "ExamAbsentClassWise_reportGeneratedOn";

    /** variable for holding string "reportGeneratedOn". */
    private static final String REPORT_GENERATED_ON = "reportGeneratedOn";

    /** variable for holding string zero. */
    private static final String STRING_ZERO = "0";

    /** variable for holding string comma. */
    private static final String STRING_COMMA = ",";

    /** variable for holding string under core. */
    private static final String STRING_UNDERSCORE = "_";

    /** attribute for holding style path. */
    private static final String STYLE_PATH = "style.path";

    /** attribute for holding app home. */
    private static final String APPSERVER_HOME = "appserver.home";

    /** attribute for holding system config. */
    private static final String SYSTEM_CONFIG = "systemConfig";

    /** attribute for holding style template. */
    private static final String STYLE_TEMPLATE = "styleTemplate";

    /** request mapping value for view class list of grade. */
    private static final String GRADE_CLASS_HTM = "/populateGradeClassList1.htm";

    /** key to hold string selectedValue. */
    private static final String SELECTED_VALUE = "selectedValue";

    /** String to hold parameter selectClasses. */
    private static final String SELECT_CLASSES = "selectClasses";

    /** key to hold error message when mandatory field null. */
    private static final String MANDATORY_FIELD_CAN_T_BE_NULL = "REF.UI.MANDATORY.FIELD.REQUIRED";

    /** report parameter for class label text. */
    private static final String REPORT_PARA_CLASS_LABEL_TEXT = "ClassLabelText";

    /** attribute for holding string. */
    private static final String STRING_EMPTY = "";

    /** attribute for holding message. */
    public static final String MESSAGE = "message";

    /** attribute for holding error message key. */
    public static final String NO_DATA_ERROR_MESSAGE = "REF.UI.NO.DATA";

    /** report value for subject description. */
    private static final String REPORT_VALUE_EXAM_ABSENT_CLASS_WISE_SUBJECT_DESCRIPTION_LABLE_TEXT =
            "ExamAbsentClassWise_subjectDescriptionLableText";

    /** report value for class description. */
    private static final String REPORT_VALUE_EXAM_ABSENT_CLASS_WISE_TERM_DESCRIPTION_LABLE_TEXT =
            "ExamAbsentClassWise_termDescriptionLableText";

    /** report value for class wise full name. */
    private static final String REPORT_VALUE_EXAM_ABSENT_CLASS_WISE_FULL_NAME_LABLE_TEXT =
            "ExamAbsentClassWise_fullNameLableText";

    /** report value for class wise admission number. */
    private static final String REPORT_VALUE_EXAM_ABSENT_CLASS_WISE_ADMISSION_NUMBER_TEXT =
            "ExamAbsentClassWise_admissionNumberText";

    /** report value for class wise class label. */
    private static final String REPORT_VALUE_EXAM_ABSENT_CLASS_WISE_CLASS_LABEL_TEXT =
            "ExamAbsentClassWise_classLabelText";

    /** report value for class wise grade label. */
    private static final String REPORT_VALUE_EXAM_ABSENT_CLASS_WISE_GRADE_LABLE_TEXT =
            "ExamAbsentClassWise_gradeLableText";

    /** report value for class wise title. */
    private static final String REPORT_VALUE_EXAM_ABSENT_CLASS_WISE_TITLE_TEXT = "ExamAbsentClassWise_titleText";

    /** report name for jrxml file. */
    private static final String REPORT_NAME_EXAM_ABSENTEE_LIST_CLASS_WISE = "CRExamAbsenteeListClassWise";

    /** report parameter for logo . */
    private static final String REPORT_PARA_LOGO_PATH = "logoPath";

    /** report parameter for subject description label. */
    private static final String REPORT_PARA_SUBJECT_DESCRIPTION_LABLE_TEXT = "subjectDescriptionLableText";

    /** report parameter for term description label. */
    private static final String REPORT_PARA_TERM_DESCRIPTION_LABLE_TEXT = "termDescriptionLableText";

    /** report parameter for full name. */
    private static final String REPORT_PARA_FULL_NAME_LABLE_TEXT = "fullNameLableText";

    /** report parameter for admission no. */
    private static final String REPORT_PARA_ADMISSION_NO_TEXT = "ADMISSION_NO_TEXT";

    /** report parameter for class. */
    private static final String REPORT_PARA_CLASS_TEXT = "ClassText";

    /** report parameter for grade. */
    private static final String REPORT_PARA_GRADE_TEXT = "gradeText";

    /** report parameter for grade label. */
    private static final String REPORT_PARA_GRADE_LABLE_TEXT = "gradeLableText";

    /** report parameter for title. */
    private static final String REPORT_PARA_EXAM_ABSENTEE_LIST_CLASS_WISE_TITLE_TEXT =
            "ExamAbsenteeListClassWiseTitleText";

    /** view method for exam absent class wise. */
    private static final String VIEW_REPORTING_EXAM_ABSENT_CLASS_WISE = "reporting/examAbsentClassWise";

    /** model attribute for exam absent template. */
    private static final String MODEL_ATT_EXAM_ABSENT_TEMPLATE = "cRExamAbsentTemplate";

    /** model attribute for report generated date. */
    private static final String REPORT_GENERATED_DATE_LOCALE = "REPORT.GENERATED.DATE.LOCALE";

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(CRExamAbsentListController.class);

    /** CommonReportingService Object to retrieve Common Reporting Service related information. */
    @Autowired
    private CommonReportingService commonReportingService;

    /** CommonService attribute for holding CommonService. */
    @Autowired
    private CommonService commonService;

    /** cRExamAbsentListValidator attribute for holding CRExamAbsentListValidator. */
    private CRExamAbsentListValidator cRExamAbsentListValidator;

    /** model attribute of class grade list. */
    private static final String MODEL_ATT_GRADE_LIST = "gradeList";

    /** model attribute of class list. */
    private static final String MODEL_ATT_CLASS_LIST = "classList";

    /** request mapping value for absent class wise. */
    private static final String REQ_MAP_ABSENT_CLASS_WISE = "/ExamAbsentClassWiseReport.htm";

    /**
     * Sets the cRExamAbsentListValidator object.
     *
     * @param cRExamAbsentListValidatorRef {@link CommonReportingService}
     */
    public void setcRExamAbsentListValidator(CRExamAbsentListValidator cRExamAbsentListValidatorRef) {

        this.cRExamAbsentListValidator = cRExamAbsentListValidatorRef;
    }

    /**
     * Sets the CommonReportingService object.
     *
     * @param setCommonReportingService {@link CommonReportingService}
     */
    public void setCommonReportingService(final CommonReportingService setCommonReportingService) {

        this.commonReportingService = setCommonReportingService;
    }

    /**
     * Sets the common service.
     *
     * @param objCommonService the commonService to set
     */
    public void setCommonService(CommonService objCommonService) {

        this.commonService = objCommonService;
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

        LOG.info("Calling ExamAbsentClassWise.jsp to colect input data");
        CRExamAbsentTemplate cRExamAbsentTemplate = new CRExamAbsentTemplate();
        modelMap.addAttribute(MODEL_ATT_EXAM_ABSENT_TEMPLATE, cRExamAbsentTemplate);

        return VIEW_REPORTING_EXAM_ABSENT_CLASS_WISE;
    }

    /**
     * Used to collect user the input data to generate the report.
     *
     * @return gradeList object
     * @throws AkuraAppException throw exception if occur.
     */
    @ModelAttribute(MODEL_ATT_GRADE_LIST)
    public List<Grade> populateGradeList() throws AkuraAppException {

        return SortUtil.sortGradeList(commonService.getGradeList());
    }

    /**
     * Used to collect user the input data to generate the report.
     *
     * @return classList object
     * @throws AkuraAppException throw exception if occur.
     */
    @ModelAttribute(MODEL_ATT_CLASS_LIST)
    public List<SchoolClass> populateClassList() throws AkuraAppException {

        return commonService.getClassList();
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
        List<ClassGrade> classGrades = SortUtil.sortClassGradeList(commonService.getClassGradeListByGrade(grade));
        boolean isFirst = true;
        StringBuilder classes = new StringBuilder();

        for (ClassGrade classGrade : classGrades) {
            classes.append(classGrade.getDescription());
            classes.append(STRING_UNDERSCORE);
            classes.append(classGrade.getClassGradeId());

            if (isFirst) {
                allGradeClass.append(classes.toString()); // no comma
                isFirst = false;
            } else {
                allGradeClass.append(STRING_COMMA); // comma
                allGradeClass.append(classes.toString());
            }
            classes.delete(0, classes.length());
        }
        return allGradeClass.toString();
    }

    /**
     * Generate Student Class Wise Exam Absentee List Report .
     *
     * @param cRExamAbsentTemplate dto to transfer user input data to generate the report.
     * @param request of type HttpServletRequest
     * @param response of type HttpServletResponse
     * @param errors errors
     * @return java.lang.String
     * @param map of type ModelMap
     * @throws AkuraAppException AkuraAppException
     */

    @RequestMapping(value = REQ_MAP_ABSENT_CLASS_WISE, method = RequestMethod.POST)
    public String onSubmit(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute(MODEL_ATT_EXAM_ABSENT_TEMPLATE) CRExamAbsentTemplate cRExamAbsentTemplate,
            BindingResult errors, ModelMap map) throws AkuraAppException {

        String returnString = STRING_EMPTY;

        int gradeId = Integer.parseInt(cRExamAbsentTemplate.getGradeDescription());

        LOG.info("Start processing user data for Student Class Wise Exam Absentee List Report");
        cRExamAbsentListValidator.validate(cRExamAbsentTemplate, errors);

        if (errors.hasErrors()) {

            map.addAttribute(MODEL_ATT_SELECTED_GRADE_ID, gradeId);
            return VIEW_REPORTING_EXAM_ABSENT_CLASS_WISE;
        } else {
            if (request.getParameter(SELECT_CLASSES).equals(STRING_ZERO)) {
                String message = new ErrorMsgLoader().getErrorMessage(MANDATORY_FIELD_CAN_T_BE_NULL);
                map.addAttribute(MODEL_ATT_SELECTED_GRADE_ID, gradeId);
                map.addAttribute(MESSAGE, message);
                return VIEW_REPORTING_EXAM_ABSENT_CLASS_WISE;
            }

            Map<String, Object> params = new HashMap<String, Object>();

            int classGradeId = Integer.parseInt(request.getParameter(SELECT_CLASSES));
            ClassGrade classGradeObj = commonService.findClassGrade(classGradeId);

            String gradeDescription = classGradeObj.getGrade().getDescription();
            String classDescription = classGradeObj.getDescription();

            params.put(REPORT_PARA_EXAM_ABSENTEE_LIST_CLASS_WISE_TITLE_TEXT, PropertyReader.getPropertyValue(
                    ReportUtil.REPORT_TEMPLATE, REPORT_VALUE_EXAM_ABSENT_CLASS_WISE_TITLE_TEXT));
            params.put(REPORT_PARA_GRADE_LABLE_TEXT, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    REPORT_VALUE_EXAM_ABSENT_CLASS_WISE_GRADE_LABLE_TEXT));
            params.put(REPORT_PARA_GRADE_TEXT, gradeDescription);
            params.put(REPORT_PARA_CLASS_LABEL_TEXT, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    REPORT_VALUE_EXAM_ABSENT_CLASS_WISE_CLASS_LABEL_TEXT));
            params.put(REPORT_PARA_CLASS_TEXT, classDescription);
            params.put(REPORT_PARA_ADMISSION_NO_TEXT, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    REPORT_VALUE_EXAM_ABSENT_CLASS_WISE_ADMISSION_NUMBER_TEXT));
            params.put(REPORT_PARA_FULL_NAME_LABLE_TEXT, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    REPORT_VALUE_EXAM_ABSENT_CLASS_WISE_FULL_NAME_LABLE_TEXT));
            params.put(REPORT_PARA_TERM_DESCRIPTION_LABLE_TEXT, PropertyReader.getPropertyValue(
                    ReportUtil.REPORT_TEMPLATE, REPORT_VALUE_EXAM_ABSENT_CLASS_WISE_TERM_DESCRIPTION_LABLE_TEXT));
            params.put(REPORT_PARA_SUBJECT_DESCRIPTION_LABLE_TEXT, PropertyReader.getPropertyValue(
                    ReportUtil.REPORT_TEMPLATE, REPORT_VALUE_EXAM_ABSENT_CLASS_WISE_SUBJECT_DESCRIPTION_LABLE_TEXT));
            params.put(REPORT_PARA_LOGO_PATH, ReportUtil.getReportLogo());
            params.put(STYLE_TEMPLATE, PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                    + PropertyReader.getPropertyValue(SYSTEM_CONFIG, STYLE_PATH));
            params.put(REPORT_GENERATED_ON, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                    CLASS_WISE_REPORT_GENERATED_ON_KEY));
            params.put(GENERATED_DATE, DateUtil.getReportGeneratedDate(PropertyReader.getPropertyValue(
                    ReportUtil.REPORT_TEMPLATE, REPORT_GENERATED_DATE_LOCALE)));
            params.put(PAGE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_PAGE));
            params.put(GPL, AkuraWebConstant.REPORT_GPL);

            cRExamAbsentTemplate.setGradeDescription(gradeDescription);
            cRExamAbsentTemplate.setClassDescription(classDescription);

            JRBeanCollectionDataSource classWiseExamAbsenteeList =
                    commonReportingService.getStudentClassWiseExamAbsenteeList(cRExamAbsentTemplate);

            if (classWiseExamAbsenteeList.getRecordCount() != 0) {
                ReportUtil.displayReportInPdfForm(response, classWiseExamAbsenteeList, params,
                        REPORT_NAME_EXAM_ABSENTEE_LIST_CLASS_WISE);
            } else {
                setErrorMessage(map, gradeId, request.getParameter(SELECT_CLASSES));
                returnString = VIEW_REPORTING_EXAM_ABSENT_CLASS_WISE;
            }

            LOG.info("There are:" + classWiseExamAbsenteeList.getRecordCount());

            return returnString;
        }

    }

    /**
     * set the error messages when returning to the jsp.
     *
     * @param gradeId - gradeId.
     * @param strClassId - strClassId.
     * @param map - ModelMap
     */
    private void setErrorMessage(ModelMap map, int gradeId, String strClassId) {

        String message = new ErrorMsgLoader().getErrorMessage(NO_DATA_ERROR_MESSAGE);
        map.addAttribute(MODEL_ATT_SELECTED_GRADE_ID, gradeId);
        map.addAttribute("selectedClassId", strClassId);
        map.addAttribute(MESSAGE, message);
    }
}
