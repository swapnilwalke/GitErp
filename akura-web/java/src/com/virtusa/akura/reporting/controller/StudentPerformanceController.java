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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
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
import org.springframework.web.bind.annotation.SessionAttributes;

import com.virtusa.akura.api.dto.CRStudentPerformanceTemplate;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.GradeSubject;
import com.virtusa.akura.api.dto.StudentPerformanceReportData;
import com.virtusa.akura.api.dto.StudentTermMarkPerformance;
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.dto.Term;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.reporting.validator.CRStudentPerformanceBetweenValidator;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;
import com.virtusa.akura.util.StaticDataUtil;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * used to generate student performance reports.
 *
 * @author Virtusa Corporation
 */

@Controller
@SessionAttributes("cRStudentPerformanceTemplate")
public class StudentPerformanceController {

    /** model attribute for selected grade id. */
    private static final String SELECTED_GRADE_ID = "selectedGradeId";

    /** request parameter for grade description. */
    private static final String REQUEST_PARA_GRADE_DESCRIPTION = "gradeDescription";

    /** report parameter for study medium column. */
    private static final String REPORT_PARA_STUDY_MEDIUM = "studyMedium";

    /** report parameter for grade column. */
    private static final String REPORT_PARA_GRADE_DESCRIPTION = REQUEST_PARA_GRADE_DESCRIPTION;

    /** report parameter for subject column. */
    private static final String REPORT_PARA_SUBJECTS2 = "subjects";

    /** attribute for holding maximum number of subject name description charaters. */
    private static final int SUBJECT_NAME_DISPLAY_CRACTERS = 5;

    /** attribute for holding maximum number of subject columns. */
    private static final int SUBJECT_COLUMNS = 20;

    /** attribute for holding string maximum number of columns. */
    private static final int MAX_COLUMNS = 20;

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

    /** attribute for holding string. */
    private static final String STRING_EMPTY = "";

    /** attribute for holding error message key. */
    public static final String NO_DATA_ERROR_MESSAGE = "REPORT.UI.NO.DATA";

    /** attribute for holding message. */
    public static final String MESSAGE = "message";

    /** request mapping value for absent class wise. */
    private static final String REQ_MAP_PERFORMANCE = "/studentPreformanceReport.htm";

    /** report value for subject description label. */
    private static final String REPORT_VALUE_STU_PERFORMANCE_SUBJECT_DESCRIPTION_LABLE_TEXT =
            "StuPerformance_subjectDescriptionLableText";

    /** report value for study medium label. */
    private static final String REPORT_VALUE_STU_PERFORMANCE_STUDY_MEDIUM_LABLE_TEXT =
            "StuPerformance_studyMediumLableText";

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

    /** view method for student performance. */
    private static final String VIEW_METHOD_REPORTING_STUDENT_PERFORMANCE = "reporting/studentPerformance";

    /** model attribute for student performance template. */
    private static final String MODEL_ATT_STUDENT_PERFORMANCE_TEMPLATE = "cRStudentPerformanceTemplate";

    /** report name for jrxml file. */
    private static final String REPORT_NAME_STUDENT_PERFORMANCE_GENERAL = "StudentMarksPerformance";

    /** report parameter for mark range. */
    private static final String REPORT_PARA_MARKS_RANGE_LABLE_TEXT = "marksRangeLableText";

    /** model attribute of class grade list. */
    private static final String MODEL_ATT_GRADE_LIST = "gradeList";

    /** model attribute of term list. */
    private static final String TERM_LIST = "termList";
    
    /** Report property Name. */
    private static final String NOTE_LABEL = "note";
	
	
	  /** model attribute for report note. */
    private static final String REPORT_MAXMARK_NOTE = "REPORT.MAXMARK.NOTE";

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(StudentPerformanceController.class);

    /** CommonService attribute for holding commonService. */
    @Autowired
    private CommonService commonService;

    /** studentService attribute for holding StudentService instance. */
    @Autowired
    private StudentService studentService;

    /**
     * Set CommonService object.
     *
     * @param objCommonService set common service object.
     */
    public void setCommonService(CommonService objCommonService) {

        this.commonService = objCommonService;
    }

    /**
     * Setter method for studentService.
     *
     * @param vStudentService StudentService object
     */
    public void setStudentService(StudentService vStudentService) {

        this.studentService = vStudentService;
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

        // set template date to the model map
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

        return SortUtil.sortGradeList(commonService.getGradeList());
    }

    /**
     * Returns list of terms.
     *
     * @return the term list.
     * @throws AkuraAppException AkuraAppException
     */
    @ModelAttribute(TERM_LIST)
    public List<Term> populateTermList() throws AkuraAppException {

        return SortUtil.sortTermList(commonService.getTermList());
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

        // get the grade id of selected grade in UI
        int gradeId = Integer.parseInt(request.getParameter(REQUEST_PARA_GRADE_DESCRIPTION));

        LOG.info("Start processing user data for Student Perforance Report");
        Map<String, Object> params = new HashMap<String, Object>();

        // get the inserted marks range and assigned to variable for display in report
        String inBetweenText =
                studentTemplate.getInBetweenLessValue() >= 0 && studentTemplate.getInBetweenGreaterValue() > 0
                        ? MessageFormat.format(PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                                STU_PERFORMANCE_MARKS_RANGE_IN_BETWEEN), studentTemplate.getInBetweenLessValue(),
                                studentTemplate.getInBetweenGreaterValue()) : null;

        cRStudentPerformanceBetweenValidator.validate(studentTemplate, errors);
        if (errors.hasErrors()) {
            map.addAttribute(SELECTED_GRADE_ID, gradeId);
            return VIEW_METHOD_REPORTING_STUDENT_PERFORMANCE;
        }
        // set range marks in report
        params.put(REPORT_PARA_MARKS_RANGE_LABLE_TEXT, inBetweenText);

        // find grade object by grade id
        Grade gradeObj = commonService.findGradeById(gradeId);

        // get grade description of selected grade
        String gradeDescription = gradeObj.getDescription();

        // get term id as integer , getting from template
        int termId = Integer.parseInt(studentTemplate.getTerm());

        // list of grade subjects which is related to the grade
        List<GradeSubject> gradeSubjects = commonService.getGradeSubjectList(gradeId);

        // sort the grade subjects
        SortUtil.sortGradeSubjectList(gradeSubjects);

        // set the number of subject columns
        List<String> subjects = StaticDataUtil.getListWithEmptyValues(SUBJECT_COLUMNS);

        List<Integer> gradeSubjectsIds = new LinkedList<Integer>();

        // get all the subjects assigned for particular grade up to 20 subjects
        if (gradeSubjects.size() > 0) {
            for (int i = 0; i < SUBJECT_COLUMNS; i++) {

                Subject subject = gradeSubjects.get(i).getSubject();
                String subjectDescription = subject.getDescription();

                if (subject.getSubjectCode() == null) {

                    if (subjectDescription.length() >= SUBJECT_NAME_DISPLAY_CRACTERS) {

                        subjects.set(i, subjectDescription.substring(0, SUBJECT_NAME_DISPLAY_CRACTERS));
                    } else {

                        subjects.set(i, subjectDescription);

                    }
                } else {
                    subjects.set(i, subject.getSubjectCode());
                }
                gradeSubjectsIds.add(gradeSubjects.get(i).getGradeSubjectId());

                if (i > (gradeSubjects.size() - 2)) {
                    break;
                }
            }
        }

        // get the system date - year
        Date currentDate = new Date();
        String year = DateUtil.getStringYear(currentDate);

        // get selected less than mark
        float lessMark = studentTemplate.getInBetweenLessValue();

        // get selected grater than mark
        float graterMark = studentTemplate.getInBetweenGreaterValue();

        // StudentTermMarkPerformance dto for hbm, DB map
        // get all students in selected grade, term, and with in marks range, for only current year
        List<StudentTermMarkPerformance> studentTermsMarksList =
                studentService.getTermMarksByGradeYear(gradeDescription, termId, year, lessMark, graterMark);

        // get the data source as for reporting data
        List<StudentPerformanceReportData> reportDataList =
                this.processReportData(studentTermsMarksList, gradeSubjectsIds);

        // set grade description for template
        studentTemplate.setGradeDescription(gradeDescription);

        // set subjects list for report parameter
        params.put(REPORT_PARA_SUBJECTS2, subjects);

        // set grade description as field
        params.put(REPORT_PARA_GRADE_DESCRIPTION, gradeDescription);

        // set parameter names for report attributes
        params.put(REPORT_PARA_TERM_DESCRIPTION_LABLE_TEXT, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_VALUE_STU_PERFORMANCE_TERM_DESCRIPTION_LABLE_TEXT));
        params.put(REPORT_PARA_STUDY_MEDIUM, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_VALUE_STU_PERFORMANCE_STUDY_MEDIUM_LABLE_TEXT));
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
        params.put(REPORT_PARA_SUBJECT_DESCRIPTION_LABLE_TEXT, PropertyReader.getPropertyValue(
                ReportUtil.REPORT_TEMPLATE, REPORT_VALUE_STU_PERFORMANCE_SUBJECT_DESCRIPTION_LABLE_TEXT));
        params.put(REPORT_PARA_MARKS_LABEL_TEXT, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_VALUE_STU_PERFORMANCE_MARKS_LABLE_TEXT));
        params.put(REPORT_PARA_MARKS, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_VALUE_STU_PERFORMANCE_MARKS_LABLE_TEXT1));
        params.put(REPORT_PARA_LOGO_PATH, ReportUtil.getReportLogo());
        params.put(STYLE_TEMPLATE, PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                + PropertyReader.getPropertyValue(SYSTEM_CONFIG, STYLE_PATH));
        params.put(REPORT_GENERATED_ON, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_GENERATED_ON_TEXT));
        params.put(GENERATED_DATE, DateUtil.getReportGeneratedDate(PropertyReader.getPropertyValue(
                ReportUtil.REPORT_TEMPLATE, REPORT_GENERATED_DATE_LOCALE)));
        params.put(PAGE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_PAGE));
        params.put(GPL, AkuraWebConstant.REPORT_GPL);
        params.put(NOTE_LABEL,PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_MAXMARK_NOTE));
        // generate report - pass data source list to Jasper report
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportDataList);

        // check report data is empty
        if (dataSource.getRecordCount() != 0) {
            ReportUtil.displayReportInPdfForm(response, dataSource, params, REPORT_NAME_STUDENT_PERFORMANCE_GENERAL);
        } else {
            // set error message , no data message
            setErrorMessage(map, gradeId);
            returnString = VIEW_METHOD_REPORTING_STUDENT_PERFORMANCE;
        }
        return returnString;

    }

    /**
     * set the error messages when returning to the jsp.
     *
     * @param gradeId - gradeId.
     * @param map - ModelMap
     */
    private void setErrorMessage(ModelMap map, int gradeId) {

        String message = new ErrorMsgLoader().getErrorMessage(NO_DATA_ERROR_MESSAGE);
        map.addAttribute(SELECTED_GRADE_ID, gradeId);
        map.addAttribute(MESSAGE, message);
    }

    /**
     * This method is used to process the report data.
     *
     * @param studentTermsMarksList list of StudentTermMarkPerformance objects
     * @param gradeSubjectsIds list of grade subject ids
     * @return list of StudentPerformanceReportData objects
     * @throws AkuraAppException the akura app exception
     */
    List<StudentPerformanceReportData> processReportData(List<StudentTermMarkPerformance> studentTermsMarksList,
            List<Integer> gradeSubjectsIds) throws AkuraAppException {

        // this class object list for holding data in report
        List<StudentPerformanceReportData> list = new ArrayList<StudentPerformanceReportData>();

        int tempStudentId = 0;
        StudentPerformanceReportData cwrd = null;

        // marks is holding list of marks for each subjects
        List<String> marks = null;

        // go through each data with student, and assigned those with report's template class
        for (StudentTermMarkPerformance studentTermMarkPerformance : studentTermsMarksList) {

            if (tempStudentId != studentTermMarkPerformance.getStudentId()) {
                if (cwrd != null) {
                    list.add(cwrd);
                }
                cwrd = new StudentPerformanceReportData();
                // set number of columns for marks , same to subjects columns
                marks = StaticDataUtil.getListWithEmptyValues(MAX_COLUMNS);

                // set data for report template - StudentPerformanceReportData
                cwrd.setAdmissionNo(studentTermMarkPerformance.getAdmissionNo());
                cwrd.setName(studentTermMarkPerformance.getNameWithInitials());
                cwrd.setMarks(marks);
                cwrd.setMedium(studentTermMarkPerformance.getMedium());
                cwrd.setTerm(studentTermMarkPerformance.getTerm());
                cwrd.setClassDescription(studentTermMarkPerformance.getClassDescription());

                if (gradeSubjectsIds.contains(studentTermMarkPerformance.getGradeSubjectId())) {

                    // set mark for particular grade subject , for selected student
                    marks.set(gradeSubjectsIds.indexOf(studentTermMarkPerformance.getGradeSubjectId()), StaticDataUtil
                            .setNoOfDigitsInNumber(0, studentTermMarkPerformance.getMarks()));
                }
            } else {
                if (gradeSubjectsIds.contains(studentTermMarkPerformance.getGradeSubjectId())) {

                    // set mark for particular grade subject , for selected student
                    marks.set(gradeSubjectsIds.indexOf(studentTermMarkPerformance.getGradeSubjectId()), StaticDataUtil
                            .setNoOfDigitsInNumber(0, studentTermMarkPerformance.getMarks()));
                }
            }

            tempStudentId = studentTermMarkPerformance.getStudentId();
        }
        if (cwrd != null) {
            list.add(cwrd);
        }
        return list;
    }

}
