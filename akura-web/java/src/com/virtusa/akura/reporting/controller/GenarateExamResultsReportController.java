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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.virtusa.akura.api.dto.Exam;
import com.virtusa.akura.api.dto.ExamResults;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraException;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.reporting.validator.ExamResultsReportValidator;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * GenarateExamResultsReportController is to generate Exam Results using the Exam Index No and Exam Name.
 *
 * @author Virtusa Corporation
 */

public class GenarateExamResultsReportController {

    /** The student service. */
    private StudentService studentService;

    /** The common service. */
    private CommonService commonService;

    /** The Exam Results Report Validator. */
    private ExamResultsReportValidator examResultsReportValidator;

    /** The Constant PAGE. */
    private static final String PAGE = "page";

    /** The Constant GPL. */
    private static final String GPL = "GPLComment";

    /** The Constant REPORT_GENERATED_ON. */
    private static final String REPORT_GENERATED_ON = "reportGeneratedOn";

    /** variable for holding string "generatedDate". */
    private static final String GENERATED_DATE = "generatedDate";

    /** model attribute for report generated date. */
    private static final String REPORT_GENERATED_DATE_LOCALE = "REPORT.GENERATED.DATE.LOCALE";

    /** The Constant REPORT_PAGE. */
    private static final String REPORT_PAGE = "Report_page";

    /** attribute for holding style path. */
    private static final String STYLE_PATH = "style.path";

    /** attribute for holding app home. */
    private static final String APPSERVER_HOME = "appserver.home";

    /** attribute for holding system config. */
    private static final String SYSTEM_CONFIG = "systemConfig";

    /** attribute for holding style template. */
    private static final String STYLE_TEMPLATE = "styleTemplate";

    /** The Constant REPORT_GENERATED_ON_TEXT. */
    private static final String REPORT_GENERATED_ON_TEXT = "Report_reportGeneratedOn";

    /** The Constant DATASOURCE. */
    private static final String DATASOURCE = "datasource";

    /** The Constant PRINCIPLE. */
    private static final String PRINCIPLE = "principle";

    /** The Constant DATE. */
    private static final String DATE = "date";

    /** The Constant MARKS_GRADE. */
    private static final String MARKS_GRADE = "marksGrade";

    /** The Constant SUBJECTS. */
    private static final String SUBJECTS = "subjects";

    /** The Constant INDEX_NO. */
    private static final String INDEX_NO = "indexNo";

    /** The Constant REPORT_FULL_NAME. */
    private static final String FULL_NAME = "fullName";

    /** The Constant LOGO_PATH. */
    private static final String LOGO_PATH = "logoPath";

    /** The Constant TITLE. */
    private static final String TITLE = "title";

    /** The Constant EXAM_ADMISSION_NO. */
    private static final String EXAM_ADMISSION_NO = "examAdmissionNo";

    /** The Constant EXAM_REULTS_REPORT. */
    private static final String EXAM_REULTS_REPORT = "examReultsReport";

    /** The Constant EXAM_LIST. */
    private static final String EXAM_LIST = "examList";

    /** The Constant REPORTING_EXAM_RESULTS_VIEW. */
    private static final String REPORTING_EXAM_RESULTS_VIEW = "reporting/examResults";

    /** The Constant EXAM_RESULTS_MODEL. */
    private static final String EXAM_RESULTS_MODEL = "examResults";

    /** The Constant PRINCIPLE_TEXT. */
    private static final String PRINCIPLE_TEXT = "ExamResultsReport_Principle";

    /** The Constant DATE_TEXT. */
    private static final String DATE_TEXT = "ExamResultsReport_Date";

    /** The Constant MARKS_GRADE_TEXT. */
    private static final String MARKS_GRADE_TEXT = "ExamResultsReport_Marks";

    /** The Constant SUBJECTS_TEXT. */
    private static final String SUBJECTS_TEXT = "ExamResultsReport_Subjects";

    /** The Constant EXAM_INDEX_NO_TEXT. */
    private static final String EXAM_INDEX_NO_TEXT = "ExamResultsReport_IndexNo";

    /** The Constant FULL_NAME_TEXT. */
    private static final String FULL_NAME_TEXT = "ExamResultsReport_FullName";

    /** The Constant REPORT_SCHOOL_NAME. */
    private static final String REPORT_SCHOOL_NAME = "REPORT.SCHOOL.NAME";
  
    /** The Constant REPORT_SCHOOL_NAME. */
    private static final String REPORT_EXAM_RESULTS_TITLE = "ExamResultsReport_Title";
    
    /** The Constant NO_RESULTS_ERROR_MSG. */
    private static final String NO_RESULTS_ERROR_MSG = "EXAM.RESULTS.REPORT.NO_RESULTS";

    /** The Constant DESCRIPTION_TEXT. */
    private static final String DESCRIPTION_TEXT = "ExamResultsReport_Description";

    /** The Constant DESCRIPTION. */
    private static final String DESCRIPTION = "description";

    /**
     * Set Student Service.
     *
     * @param studentServiceRef studentService
     */
    public void setStudentService(StudentService studentServiceRef) {

        this.studentService = studentServiceRef;
    }

    /**
     * Set Common Service.
     *
     * @param commonServiceRef commonService
     */
    public void setCommonService(CommonService commonServiceRef) {

        this.commonService = commonServiceRef;
    }

    /**
     * Set Exam Results Report Validator.
     *
     * @param examResultsReportValidatorRef examResultsReportValidator
     */
    public void setExamResultsReportValidator(ExamResultsReportValidator examResultsReportValidatorRef) {

        this.examResultsReportValidator = examResultsReportValidatorRef;
    }

    /**
     * Display Form View for of the controller and binding it to ExamResult.
     *
     * @param modelMap of type ModelMap
     * @return java.lang.String
     */

    @RequestMapping(method = RequestMethod.GET)
    public String showReportForm(ModelMap modelMap) {

        modelMap.addAttribute(EXAM_RESULTS_MODEL, new ExamResults());
        return REPORTING_EXAM_RESULTS_VIEW;
    }

    /**
     * Populate exam list.
     *
     * @return the exam list
     * @throws AkuraException the akura exception
     */
    @ModelAttribute(EXAM_LIST)
    public List<Exam> populateExamList() throws AkuraException {

        return SortUtil.sortExamList(commonService.getExamList());
    }

    /**
     * Perform the logic of the controller to Generate Exam Results Report.
     *
     * @param response of type HttpServletResponse
     * @param exam - model object
     * @param errors - BindingResult
     * @return modelAndView
     * @throws AkuraAppException AkuraAppException
     */
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView onSubmit(HttpServletResponse response, @ModelAttribute(EXAM_RESULTS_MODEL) ExamResults exam,
            BindingResult errors) throws AkuraAppException {

        String redirectURL = EXAM_REULTS_REPORT;
        Map<String, Object> map = new HashMap<String, Object>();
        examResultsReportValidator.validate(exam, errors);

        if (errors.hasErrors()) {
            redirectURL = REPORTING_EXAM_RESULTS_VIEW;
        } else {
            String examAdmissionNo = exam.getExamAdmissionNo();
            int examId = exam.getExamId();

            List<ExamResults> examResultsList =
                    studentService.findExamResultsByExamAdmissionNo(examAdmissionNo, examId);

            if (!examResultsList.isEmpty()) {
                ExamResults tempExamResult = examResultsList.get(0);

                JRBeanCollectionDataSource jrBeanDataSource = new JRBeanCollectionDataSource(examResultsList);

                map.put(TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_EXAM_RESULTS_TITLE));
                map.put(LOGO_PATH, ReportUtil.getReportLogo());
                map.put(STYLE_TEMPLATE, PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                        + PropertyReader.getPropertyValue(SYSTEM_CONFIG, STYLE_PATH));
                map.put(FULL_NAME, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, FULL_NAME_TEXT));
                map.put(INDEX_NO, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, EXAM_INDEX_NO_TEXT));
                map.put(SUBJECTS, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, SUBJECTS_TEXT));
                map.put(MARKS_GRADE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, MARKS_GRADE_TEXT));
                if (!tempExamResult.isMarkType()) {
                    map.put(DESCRIPTION, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, DESCRIPTION_TEXT));
                }
                map.put(DATE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, DATE_TEXT));
                map.put(PRINCIPLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, PRINCIPLE_TEXT));
                map.put(REPORT_GENERATED_ON, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                        REPORT_GENERATED_ON_TEXT));
                map.put(GENERATED_DATE, DateUtil.getReportGeneratedDate(PropertyReader.getPropertyValue(
                        ReportUtil.REPORT_TEMPLATE, REPORT_GENERATED_DATE_LOCALE)));
                map.put(PAGE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_PAGE));
                map.put(GPL, AkuraWebConstant.REPORT_GPL);
                map.put(DATASOURCE, jrBeanDataSource);

            } else {
                errors.rejectValue(EXAM_ADMISSION_NO, NO_RESULTS_ERROR_MSG);
                redirectURL = REPORTING_EXAM_RESULTS_VIEW;
            }
        }

        return new ModelAndView(redirectURL, map);
    }

}
