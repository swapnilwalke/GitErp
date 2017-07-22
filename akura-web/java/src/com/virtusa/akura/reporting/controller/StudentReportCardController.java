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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.virtusa.akura.api.dto.AdminDetails;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.GradeSubject;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.dto.StudentReportCardTemplate;
import com.virtusa.akura.api.dto.StudentTermMarkDTO;
import com.virtusa.akura.api.dto.StudentTermMarksRank;
import com.virtusa.akura.api.dto.StudyMedium;
import com.virtusa.akura.api.dto.Term;
import com.virtusa.akura.api.dto.UserInfo;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;
import com.virtusa.akura.util.StaticDataUtil;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * The Class StudentReportCardController is to generate student report card.
 *
 * @author Virtusa Corporation.
 */
@Controller
public class StudentReportCardController {

    /** variable of index one. */
    private int indexOne = 1;

    /** The String of Model attribute. */
    private static final String MODEL_ATT_SELECTED_ADDMISSION_NO = "selectedAddmissionNo";

    /** The Constant model attribute. */
    private static final String MODEL_ATT_SELECTED_CLASS_GRADE_ID = "selectedClassGradeId";

    /** The Constant REMARKS. */
    private static final String REMARKS = "remarks";

    /** The Constant STUDENT_REPORT_CARD_REMARKS. */
    private static final String STUDENT_REPORT_CARD_REMARKS = "StudentReportCard_Remarks";

    /** The Constant STUDENT_REPORT_CARD. */
    private static final String STUDENT_REPORT_CARD = "StudentReportCard";

    /** The Constant DATASOURCE. */
    private static final String DATASOURCE = "datasource";

    /** The Constant LOGO. */
    private static final String LOGO = "logo";

    /** Constant to represent error message in property file for no subjects found for student. */
    private static final String REF_UI_NO_SUBJECT_STUDENT = "REF.UI.NO.SUBJECT.STUDENT";

    /** The Constant CLASS_GRADE_LIST. */
    private static final String CLASS_GRADE_LIST = "classGradeList";

    /** The Constant REPORTING_STUDENT_REPORT_CARD. */
    private static final String REPORTING_STUDENT_REPORT_CARD = "reporting/studentReportCard";

    /** The Constant STUDENT_REPORT_CARD_PAGE_HTM. */
    private static final String STUDENT_REPORT_CARD_PAGE_HTM = "/studentReportCardPage.htm";

    /** The Constant POPULATE_ADDMISSION_NOS_BY_CLASS_HTM. */
    private static final String POPULATE_ADDMISSION_NOS_BY_CLASS_HTM = "/populateAddmissionNosByClass.htm";

    /** The Constant CLASS_GRADE_ID. */
    private static final String CLASS_GRADE_ID = "classGradeId";

    /** The Constant ROLE_TEACHER. */
    private static final String ROLE_TEACHER = "ROLE_TEACHER";

    /** The Constant USER. */
    private static final String USER = "user";

    /** The Constant SELECTED_ADDMISSION. */
    private static final String SELECTED_ADDMISSION = "selectedAddmission";

    /** The Constant SELECTED_CLASS. */
    private static final String SELECTED_CLASS = "selectedClass";

    /** The Constant MESSAGE. */
    private static final String MESSAGE = "message";

    /** The Constant STUDENT_REPORT_CARD_HTM. */
    private static final String STUDENT_REPORT_CARD_HTM = "/studentReportCard.htm";

    /** Constant for styleTemplate. */
    private static final String STYLE_TEMPLATE = "styleTemplate";

    /** property file name. */
    private static final String SYSTEM_CONFIG_PROPERTIES = "systemConfig";

    /** Holds the value for appserver.home. */
    private static final String APPSERVER_HOME = "appserver.home";

    /** Constant for style.path. */
    private static final String STYLE_PATH = "style.path";

    /** The Constant AGE_VALUE. */
    private static final String AGE_VALUE = "age_value";

    /** The Constant DATE_OF_BIRTH_VALUE. */
    private static final String DATE_OF_BIRTH_VALUE = "dateOfBirth_value";

    /** The Constant MEDIUM_VALUE. */
    private static final String MEDIUM_VALUE = "medium_value";

    /** The Constant CLASS_VALUE. */
    private static final String CLASS_VALUE = "class_value";

    /** The Constant NAME_VALUE. */
    private static final String NAME_VALUE = "name_value";

    /** The Constant GPL_COMMENT. */
    private static final String GPL_COMMENT = "GPLComment";

    /** The Constant REPORT_PAGE. */
    private static final String REPORT_PAGE = "Report_page";

    /** The Constant PAGE. */
    private static final String PAGE = "page";

    /** The Constant STUDENT_REPORT_CARD_POSITION. */
    private static final String STUDENT_REPORT_CARD_POSITION = "StudentReportCard_Position";

    /** The Constant POSITION. */
    private static final String POSITION = "position";

    /** The Constant STUDENT_REPORT_CARD_NUMBER_IN_CLASS. */
    private static final String STUDENT_REPORT_CARD_NUMBER_IN_CLASS = "StudentReportCard_NumberInClass";

    /** The Constant NUMBER_IN_CLASS. */
    private static final String NUMBER_IN_CLASS = "NIClass";

    /** The Constant STUDENT_REPORT_CARD_CLASS_PERCENTAGE. */
    private static final String STUDENT_REPORT_CARD_CLASS_PERCENTAGE = "StudentReportCard_ClassPercentage";

    /** The Constant CLASS_PERCENTAGE. */
    private static final String CLASS_PERCENTAGE = "CPercentage";

    /** The Constant STUDENT_REPORT_CARD_BEST_STUDENT_PERCENTAGE. */
    private static final String STUDENT_REPORT_CARD_BEST_STUDENT_PERCENTAGE = "StudentReportCard_BestStudentPercentage";

    /** The Constant BEST_STUDENT_PERCENTAGE. */
    private static final String BEST_STUDENT_PERCENTAGE = "BSPercentage";

    /** The Constant STUDENT_REPORT_CARD_PERCENTAGE. */
    private static final String REPORT_CARD_PERCENTAGE = "StudentReportCard_Percentage";

    /** The Constant PERCENTAGE. */
    private static final String PERCENTAGE = "percentage";

    /** The Constant STUDENT_REPORT_CARD_TOTAL. */
    private static final String STUDENT_REPORT_CARD_TOTAL = "StudentReportCard_Total";

    /** The Constant TOTAL. */
    private static final String TOTAL = "total";

    /** The Constant TERM3. */
    private static final String TERM3 = "term3";

    /** The Constant TERM2. */
    private static final String TERM2 = "term2";

    /** The Constant TERM1. */
    private static final String TERM1 = "term1";

    /** The Constant STUDENT_REPORT_CARD_MAX_MARKS. */
    private static final String STUDENT_REPORT_CARD_MAX_MARKS = "StudentReportCard_MaxMarks";

    /** The Constant MAX_MARKS. */
    private static final String MAX_MARKS = "maxMarks";

    /** The Constant STUDENT_REPORT_CARD_SUBJECT. */
    private static final String STUDENT_REPORT_CARD_SUBJECT = "StudentReportCard_Subject";

    /** The Constant SUBJECT. */
    private static final String SUBJECT = "subject";

    /** The Constant STUDENT_REPORT_CARD_AGE. */
    private static final String STUDENT_REPORT_CARD_AGE = "StudentReportCard_Age";

    /** The Constant AGE. */
    private static final String AGE = "age";

    /** The Constant STUDENT_REPORT_CARD_DATE_OF_BIRTH. */
    private static final String STUDENT_REPORT_CARD_DATE_OF_BIRTH = "StudentReportCard_DateOfBirth";

    /** The Constant DATE_OF_BIRTH. */
    private static final String DATE_OF_BIRTH = "dateOfBirth";

    /** The Constant STUDENT_REPORT_CARD_MEDIUM. */
    private static final String STUDENT_REPORT_CARD_MEDIUM = "StudentReportCard_Medium";

    /** The Constant MEDIUM. */
    private static final String MEDIUM = "medium";

    /** The Constant STUDENT_REPORT_CARD_CLASS. */
    private static final String STUDENT_REPORT_CARD_CLASS = "StudentReportCard_Class";

    /** The Constant CLASS. */
    private static final String CLASS = "class";

    /** The Constant STUDENT_REPORT_CARD_NAME. */
    private static final String STUDENT_REPORT_CARD_NAME = "StudentReportCard_Name";

    /** The Constant NAME. */
    private static final String NAME = "name";

    /** The Constant REPORT_GENERATED_DATE_LOCALE. */
    private static final String REPORT_GENERATED_DATE_LOCALE = "REPORT.GENERATED.DATE.LOCALE";

    /** The Constant GENERATED_DATE. */
    private static final String GENERATED_DATE = "generatedDate";

    /** The Constant REPORT_REPORT_GENERATED_ON. */
    private static final String REPORT_REPORT_GENERATED_ON = "Report_reportGeneratedOn";

    /** The Constant GENERATED_ON. */
    private static final String GENERATED_ON = "generatedOn";

    /** The Constant STUDENT_REPORT_CARD_TITLE. */
    private static final String STUDENT_REPORT_CARD_TITLE = "StudentReportCard_Title";

    /** The Constant TITLE. */
    private static final String TITLE = "title";

    /** The Constant TERM3_STUDENT_COUNT. */
    private static final String TERM3_STUDENT_COUNT = "term3StudentCount";

    /** The Constant TERM3_CLASS_AVG. */
    private static final String TERM3_CLASS_AVG = "term3ClassAvg";

    /** The Constant TERM3_BEST_STUDENT_AVG. */
    private static final String TERM3_BEST_STUDENT_AVG = "term3BestStudentAvg";

    /** The Constant TERM3_RANK. */
    private static final String TERM3_RANK = "term3Rank";

    /** The Constant TERM3_AVERAGE. */
    private static final String TERM3_AVERAGE = "term3Average";

    /** The Constant TERM3_TOTAL. */
    private static final String TERM3_TOTAL = "term3Total";

    /** The Constant TERM2_STUDENT_COUNT. */
    private static final String TERM2_STUDENT_COUNT = "term2StudentCount";

    /** The Constant TERM2_CLASS_AVG. */
    private static final String TERM2_CLASS_AVG = "term2ClassAvg";

    /** The Constant TERM2_BEST_STUDENT_AVG. */
    private static final String TERM2_BEST_STUDENT_AVG = "term2BestStudentAvg";

    /** The Constant TERM2_RANK. */
    private static final String TERM2_RANK = "term2Rank";

    /** The Constant TERM2_AVERAGE. */
    private static final String TERM2_AVERAGE = "term2Average";

    /** The Constant TERM2_TOTAL. */
    private static final String TERM2_TOTAL = "term2Total";

    /** The Constant TERM1_STUDENT_COUNT. */
    private static final String TERM1_STUDENT_COUNT = "term1StudentCount";

    /** The Constant TERM1_CLASS_AVG. */
    private static final String TERM1_CLASS_AVG = "term1ClassAvg";

    /** The Constant TERM1_BEST_STUDENT_AVG. */
    private static final String TERM1_BEST_STUDENT_AVG = "term1BestStudentAvg";

    /** The Constant TERM1_RANK. */
    private static final String TERM1_RANK = "term1Rank";

    /** The Constant TERM1_AVERAGE. */
    private static final String TERM1_AVERAGE = "term1Average";

    /** The Constant TERM1_TOTAL. */
    private static final String TERM1_TOTAL = "term1Total";

    /** The Constant STUDENT_REPORT_CARD_TERM3. */
    private static final String STUDENT_REPORT_CARD_TERM3 = "StudentReportCard_Term3";

    /** The Constant STUDENT_REPORT_CARD_TERM2. */
    private static final String STUDENT_REPORT_CARD_TERM2 = "StudentReportCard_Term2";

    /** The Constant STUDENT_REPORT_CARD_TERM1. */
    private static final String STUDENT_REPORT_CARD_TERM1 = "StudentReportCard_Term1";

    /** The Constant INDEX_ZERO. */
    private static final int INDEX_ZERO = 0;

    /** The Constant INDEX_ONE. */
    private static final int INDEX_ONE = 1;

    /** The common service. */
    private CommonService commonService;

    /** The student service. */
    private StudentService studentService;

    /**
     * Sets the common service.
     *
     * @param objCommonService the commonService to set
     */
    public void setCommonService(CommonService objCommonService) {

        this.commonService = objCommonService;
    }

    /**
     * Sets the student service.
     *
     * @param objStudentService the studentService to set
     */
    public void setStudentService(StudentService objStudentService) {

        this.studentService = objStudentService;
    }

    /**
     * Show student report card page.
     *
     * @param modelMap the model map
     * @return the model and view
     * @throws AkuraAppException the akura app exception
     */
    @RequestMapping(value = STUDENT_REPORT_CARD_PAGE_HTM)
    public ModelAndView showStudentReportCardPage(ModelMap modelMap) throws AkuraAppException {

        return new ModelAndView(REPORTING_STUDENT_REPORT_CARD);
    }

    /**
     * Generate upper school student report card.
     *
     * @param request the request
     * @param modelMap the model map
     * @return the model and view
     * @throws AkuraAppException the akura app exception
     */
    @RequestMapping(value = STUDENT_REPORT_CARD_HTM)
    public ModelAndView generateStudentReportCard(HttpServletRequest request, ModelMap modelMap)
            throws AkuraAppException {

        String selectedAddmission = AkuraWebConstant.EMPTY_STRING;
        String selectedClass = request.getParameter(SELECTED_CLASS);
        ClassGrade classGrade = commonService.findClassGrade(Integer.parseInt(selectedClass));
        if (classGrade != null) {
            modelMap.addAttribute(MODEL_ATT_SELECTED_CLASS_GRADE_ID, classGrade.getClassGradeId());
        }
        selectedAddmission = request.getParameter(SELECTED_ADDMISSION);
        Map<String, Object> map = new HashMap<String, Object>();

        String redirectURL = STUDENT_REPORT_CARD;

        if (AkuraWebConstant.STRING_ZERO.equals(selectedClass) || selectedAddmission == null
                || AkuraWebConstant.STRING_ZERO.equals(selectedAddmission)) {
            String message = new ErrorMsgLoader().getErrorMessage(AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
            modelMap.addAttribute(MESSAGE, message);
            redirectURL = REPORTING_STUDENT_REPORT_CARD;
        } else {

            Student student = null;
            List<Student> studentList = studentService.getStudentPersonalDetailsByAdmissionNo(selectedAddmission);

            if (studentList != null) {
                student = studentList.get(INDEX_ZERO);

                if (!studentService.getStudentTermMarksByStudentIdYearClassGrade(student.getStudentId(),
                        DateUtil.currentYearOnly(), classGrade.getClassGradeId()).isEmpty()) {

                    List<GradeSubject> gradeSubjects =
                            commonService.getGradeSubjectListByClassGradeId(Integer.parseInt(selectedClass));

                    SortUtil.sortGradeSubjectList(gradeSubjects);

                    boolean markingCompletedTerm1 = false;
                    boolean markingCompletedTerm2 = false;
                    boolean markingCompletedTerm3 = false;

                    List<Term> terms = commonService.getTermList();

                    for (Term term : terms) {
                        if (term.getTermId() == AkuraConstant.PARAM_INDEX_ONE) {

                            markingCompletedTerm1 =
                                    studentService.isMarkingCompletedForTerm(classGrade.getClassGradeId(), term
                                            .getTermId(), DateUtil.currentYearOnly());

                        } else if (term.getTermId() == AkuraConstant.PARAM_INDEX_TWO) {

                            markingCompletedTerm2 =
                                    studentService.isMarkingCompletedForTerm(classGrade.getClassGradeId(), term
                                            .getTermId(), DateUtil.currentYearOnly());

                        } else if (term.getTermId() == AkuraConstant.PARAM_INDEX_THREE) {

                            markingCompletedTerm3 =
                                    studentService.isMarkingCompletedForTerm(classGrade.getClassGradeId(), term
                                            .getTermId(), DateUtil.currentYearOnly());

                        }
                    }

                    List<StudentReportCardTemplate> studentReportCardTemplateList =
                            new ArrayList<StudentReportCardTemplate>();

                    int studentClassInfoId =
                            populateDataSourceList(student, gradeSubjects, studentReportCardTemplateList,
                                    markingCompletedTerm1, markingCompletedTerm2, markingCompletedTerm3);

                    JRDataSource datasource = new JRBeanCollectionDataSource(studentReportCardTemplateList);
                    map.put(DATASOURCE, datasource);

                    for (Term term : terms) {

                        Date currYear = DateUtil.currentYear();

                        StudentTermMarksRank studentTermMarksRank =
                                studentService.getStudentTermMarksRank(studentClassInfoId, term.getTermId());

                        Double bestStudentAverage =
                                studentService.getBestStudentAverage(Integer.parseInt(selectedClass), term
                                        .getDescription(), currYear);
                        Object[] classAverage =
                                studentService.getClassAverage(Integer.parseInt(selectedClass), term.getDescription(),
                                        currYear);

                        if (term.getTermId() == AkuraConstant.PARAM_INDEX_ONE) {

                            populateFirstTermMap(map, studentTermMarksRank, bestStudentAverage, classAverage,
                                    markingCompletedTerm1);
                        } else if (term.getTermId() == AkuraConstant.PARAM_INDEX_TWO) {

                            populateSecondTermMap(map, studentTermMarksRank, bestStudentAverage, classAverage,
                                    markingCompletedTerm2);
                        } else if (term.getTermId() == AkuraConstant.PARAM_INDEX_THREE) {

                            populateThirdTermMap(map, studentTermMarksRank, bestStudentAverage, classAverage,
                                    markingCompletedTerm3);
                        }
                    }

                    populateLabelMap(map);

                    populateValueMap(map, student, classGrade);

                } else {
                    String message = new ErrorMsgLoader().getErrorMessage(REF_UI_NO_SUBJECT_STUDENT);
                    modelMap.addAttribute(MODEL_ATT_SELECTED_ADDMISSION_NO, selectedAddmission);
                    modelMap.addAttribute(MESSAGE, message);
                    redirectURL = REPORTING_STUDENT_REPORT_CARD;
                }

            }

        }

        return new ModelAndView(redirectURL, map);
    }

    /**
     * Populate value map.
     *
     * @param map the map
     * @param student the student
     * @param classGrade the class grade
     * @throws AkuraAppException the akura app exception
     */
    private void populateValueMap(Map<String, Object> map, Student student, ClassGrade classGrade)
            throws AkuraAppException {

        map.put(NAME_VALUE, student.getNameWtInitials());
        map.put(CLASS_VALUE, classGrade.getDescription());

        String studentMedium = AkuraWebConstant.EMPTY_STRING;

        if (student.getLanguageId() > INDEX_ZERO) {
            StudyMedium medium = commonService.getStudyMediumById(student.getLanguageId());
            studentMedium = medium.getStudyMediumName();
        }
        map.put(MEDIUM_VALUE, studentMedium);
        map.put(DATE_OF_BIRTH_VALUE, student.getDateOfBirth());

        int age = INDEX_ZERO;
        if (student.getDateOfBirth() != null) {
            age = DateUtil.getAge(student.getDateOfBirth());
            map.put(AGE_VALUE, age);
        }
    }

    /**
     * Populate label map.
     *
     * @param map the map
     * @throws AkuraAppException the akura app exception
     */
    private void populateLabelMap(Map<String, Object> map) throws AkuraAppException {

        map.put(STYLE_TEMPLATE, PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, APPSERVER_HOME)
                + PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, STYLE_PATH));
        map.put(LOGO, ReportUtil.getReportLogo());
        map.put(TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STUDENT_REPORT_CARD_TITLE));
        map.put(GENERATED_ON, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_REPORT_GENERATED_ON));
        map.put(GENERATED_DATE, DateUtil.getReportGeneratedDate(PropertyReader.getPropertyValue(
                ReportUtil.REPORT_TEMPLATE, REPORT_GENERATED_DATE_LOCALE)));
        map.put(NAME, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STUDENT_REPORT_CARD_NAME));
        map.put(CLASS, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STUDENT_REPORT_CARD_CLASS));
        map.put(MEDIUM, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STUDENT_REPORT_CARD_MEDIUM));
        map.put(DATE_OF_BIRTH, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                STUDENT_REPORT_CARD_DATE_OF_BIRTH));
        map.put(AGE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STUDENT_REPORT_CARD_AGE));
        map.put(SUBJECT, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STUDENT_REPORT_CARD_SUBJECT));
        map.put(MAX_MARKS, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STUDENT_REPORT_CARD_MAX_MARKS));
        map.put(REMARKS, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STUDENT_REPORT_CARD_REMARKS));
        map.put(TERM1, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STUDENT_REPORT_CARD_TERM1));
        map.put(TERM2, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STUDENT_REPORT_CARD_TERM2));
        map.put(TERM3, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STUDENT_REPORT_CARD_TERM3));
        map.put(TOTAL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STUDENT_REPORT_CARD_TOTAL));
        map.put(PERCENTAGE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_CARD_PERCENTAGE));
        map.put(BEST_STUDENT_PERCENTAGE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                STUDENT_REPORT_CARD_BEST_STUDENT_PERCENTAGE));
        map.put(CLASS_PERCENTAGE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                STUDENT_REPORT_CARD_CLASS_PERCENTAGE));
        map.put(NUMBER_IN_CLASS, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                STUDENT_REPORT_CARD_NUMBER_IN_CLASS));
        map.put(POSITION, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STUDENT_REPORT_CARD_POSITION));
        map.put(PAGE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_PAGE));
        map.put(GPL_COMMENT, AkuraWebConstant.REPORT_GPL);
    }

    /**
     * Populate third term map.
     *
     * @param map the map
     * @param studentTermMarksRank the student term marks rank
     * @param bestStudentAverage the best student average
     * @param classAverage the class average
     * @param marksCompleted boolean value - whether marking done on third term
     */
    private void populateThirdTermMap(Map<String, Object> map, StudentTermMarksRank studentTermMarksRank,
            Double bestStudentAverage, Object[] classAverage, boolean marksCompleted) {

        if (marksCompleted) {
            if (studentTermMarksRank != null) {
                map.put(TERM3_TOTAL, studentTermMarksRank.getTotal());
                map.put(TERM3_AVERAGE, studentTermMarksRank.getAverage());
                map.put(TERM3_RANK, studentTermMarksRank.getRank());
            }
            map.put(TERM3_BEST_STUDENT_AVG, bestStudentAverage);
            map.put(TERM3_CLASS_AVG, (Double) classAverage[indexOne]);

            if (classAverage[INDEX_ZERO] != null) {
                map.put(TERM3_STUDENT_COUNT, (Long) classAverage[INDEX_ZERO]);
            }
        }
    }

    /**
     * Populate second term map.
     *
     * @param map the map
     * @param studentTermMarksRank the student term marks rank
     * @param bestStudentAverage the best student average
     * @param classAverage the class average
     * @param marksCompleted boolean value - whether marking done on second term
     */
    private void populateSecondTermMap(Map<String, Object> map, StudentTermMarksRank studentTermMarksRank,
            Double bestStudentAverage, Object[] classAverage, boolean marksCompleted) {

        if (marksCompleted) {

            if (studentTermMarksRank != null) {
                map.put(TERM2_TOTAL, studentTermMarksRank.getTotal());
                map.put(TERM2_AVERAGE, studentTermMarksRank.getAverage());
                map.put(TERM2_RANK, studentTermMarksRank.getRank());
            }

            map.put(TERM2_BEST_STUDENT_AVG, bestStudentAverage);
            map.put(TERM2_CLASS_AVG, (Double) classAverage[indexOne]);
            if (classAverage[INDEX_ZERO] != null) {
                map.put(TERM2_STUDENT_COUNT, (Long) classAverage[INDEX_ZERO]);
            }
        }
    }

    /**
     * Populate first term map.
     *
     * @param map the map
     * @param studentTermMarksRank the student term marks rank
     * @param bestStudentAverage the best student average
     * @param classAverage the class average
     * @param marksCompleted boolean value - whether marking done on first term
     */
    private void populateFirstTermMap(Map<String, Object> map, StudentTermMarksRank studentTermMarksRank,
            Double bestStudentAverage, Object[] classAverage, boolean marksCompleted) {

        if (marksCompleted) {
            if (studentTermMarksRank != null) {
                map.put(TERM1_TOTAL, studentTermMarksRank.getTotal());
                map.put(TERM1_AVERAGE, studentTermMarksRank.getAverage());
                map.put(TERM1_RANK, studentTermMarksRank.getRank());
            }
            map.put(TERM1_BEST_STUDENT_AVG, bestStudentAverage);
            map.put(TERM1_CLASS_AVG, (Double) classAverage[indexOne]);
            if (classAverage[INDEX_ZERO] != null) {
                map.put(TERM1_STUDENT_COUNT, (Long) classAverage[INDEX_ZERO]);
            }
        }
    }

    /**
     * Populate data source list.
     *
     * @param student the student
     * @param gradeSubjects the grade subjects
     * @param studentReportCardTemplateList the student report card template list
     * @param markingCompletedTerm1 - boolean value - whether marking done on first term
     * @param markingCompletedTerm2 - boolean value - whether marking done on second term
     * @param markingCompletedTerm3 - boolean value - whether marking done on third term
     * @return the int
     * @throws AkuraAppException the akura app exception
     */
    private int populateDataSourceList(Student student, List<GradeSubject> gradeSubjects,
            List<StudentReportCardTemplate> studentReportCardTemplateList, boolean markingCompletedTerm1,
            boolean markingCompletedTerm2, boolean markingCompletedTerm3) throws AkuraAppException {

        int studentClassInfoId = INDEX_ZERO;
        int countRows = INDEX_ONE;
        for (GradeSubject gradeSubject : gradeSubjects) {

            List<StudentTermMarkDTO> studentTermMarksList =
                    studentService.getStudentTermMarksByStudentIdYearSubject(student.getStudentId(), DateUtil
                            .currentYearOnly(), gradeSubject.getGradeSubjectId());

            StudentReportCardTemplate studentReportCardTemplate = new StudentReportCardTemplate();
            studentReportCardTemplate.setSubject(gradeSubject.getSubject().getDescription());

            // set maximum marks for each subject
            studentReportCardTemplate.setMaxMarks(gradeSubject.getMaximumMark());
            studentReportCardTemplate.setId(countRows++);

            studentClassInfoId =
                    populateReportCardTemplate(studentClassInfoId, studentTermMarksList, studentReportCardTemplate,
                            markingCompletedTerm1, markingCompletedTerm2, markingCompletedTerm3, gradeSubject
                                    .getGradeSubjectId());

            studentReportCardTemplateList.add(studentReportCardTemplate);
        }
        return studentClassInfoId;
    }

    /**
     * Populate student report card template.
     *
     * @param studentClassInfoId the student class info id
     * @param studentTermMarksList the student term marks list
     * @param studentReportCardTemplate the student report card template
     * @param markingCompletedTerm1 - boolean value - whether marking done on first term
     * @param markingCompletedTerm2 - boolean value - whether marking done on second term
     * @param markingCompletedTerm3 - boolean value - whether marking done on third term
     * @param gradeSubjectId - gradeSubjectId .
     * @return the int
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    private int populateReportCardTemplate(int studentClassInfoId, List<StudentTermMarkDTO> studentTermMarksList,
            StudentReportCardTemplate studentReportCardTemplate, boolean markingCompletedTerm1,
            boolean markingCompletedTerm2, boolean markingCompletedTerm3, int gradeSubjectId) throws AkuraAppException {

        int maxMark = commonService.findGradeSubjectMaxMarkById(gradeSubjectId);

        if (studentTermMarksList.isEmpty()) {
            studentReportCardTemplate.setFirstTermMarks(null);
            studentReportCardTemplate.setSecondTermMarks(null);
            studentReportCardTemplate.setThirdTermMarks(null);
        } else {
            for (StudentTermMarkDTO studentTermMarkDTO : studentTermMarksList) {
                studentClassInfoId = studentTermMarkDTO.getStudentClassInfoId();
                float getPreviewMarks = StaticDataUtil.previewMaximumMark(studentTermMarkDTO.getMarks(), maxMark);

                if (studentTermMarkDTO.getTermId() == AkuraConstant.PARAM_INDEX_ONE) {
                    if (markingCompletedTerm1) {
                        if (studentTermMarkDTO.isAbsent()) {
                            studentReportCardTemplate.setFirstTermAbsent(AkuraConstant.ABSENT);
                        } else {
                            studentReportCardTemplate.setFirstTermMarks(getPreviewMarks);

                        }
                    }
                } else if (studentTermMarkDTO.getTermId() == AkuraConstant.PARAM_INDEX_TWO) {
                    if (markingCompletedTerm2) {
                        if (studentTermMarkDTO.isAbsent()) {
                            studentReportCardTemplate.setSecondTermAbsent(AkuraConstant.ABSENT);
                        } else {
                            studentReportCardTemplate.setSecondTermMarks(getPreviewMarks);
                        }
                    }
                } else if (studentTermMarkDTO.getTermId() == AkuraConstant.PARAM_INDEX_THREE) {
                    if (markingCompletedTerm3) {
                        if (studentTermMarkDTO.isAbsent()) {
                            studentReportCardTemplate.setThirdTermAbsent(AkuraConstant.ABSENT);
                        } else {
                            studentReportCardTemplate.setThirdTermMarks(getPreviewMarks);
                        }
                    }
                }
            }
        }

        return studentClassInfoId;
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

        List<ClassGrade> classGrades = null;
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute(USER);

        if (userInfo instanceof AdminDetails) {
            AdminDetails adminDetails = (AdminDetails) userInfo;
            if (ROLE_TEACHER.equals(adminDetails.getRole().trim())) {
                classGrades = commonService.getClassGradeByStaffRegistrationNo(adminDetails.getRegistrationNo());
            } else {
                classGrades = SortUtil.sortClassGradeList(commonService.getClassGradeList());
            }
        }
        return classGrades;
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
        
        List<StudentClassInfo> studentClassInfoList = studentService.getClassStudentList(
            Integer.parseInt(classGradeId), DateUtil.currentYearOnly()
        );

        StringBuilder dataStringBuilder = new StringBuilder();

        if (!studentClassInfoList.isEmpty()) {

            for (StudentClassInfo classInfo : studentClassInfoList) {
                dataStringBuilder.append(
                    classInfo.getStudent().getAdmissionNo() + "&nbsp;&nbsp;-&nbsp;&nbsp;"
                    + classInfo.getStudent().getNameWtInitials()
                );
                dataStringBuilder.append(AkuraWebConstant.STRING_COMMA);
            }
        }

        return dataStringBuilder.toString();
    }
}
