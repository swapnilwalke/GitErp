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
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
import com.virtusa.akura.api.dto.ClassWiseStudentReportData;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.GradeSubject;
import com.virtusa.akura.api.dto.MediumWiseClassSubjectAverageView;
import com.virtusa.akura.api.dto.StudentMarkSheetTemplate;
import com.virtusa.akura.api.dto.StudentTermMarkDTO;
import com.virtusa.akura.api.dto.StudentTermMarksRank;
import com.virtusa.akura.api.dto.StudyMedium;
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.dto.SubjectAverageTermMarks;
import com.virtusa.akura.api.dto.SubjectAveragesTemplate;
import com.virtusa.akura.api.dto.Term;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.api.exception.AkuraException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.reporting.validator.ClassWiseStudentMarksSheetValidator;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;
import com.virtusa.akura.util.StaticDataUtil;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * ClassWiseStudentMarksSheet is to generate report of student Mark sheet with average/rank.
 *
 * @author Virtusa Corporation
 */
@Controller
public class ClassWiseStudentMarksSheetController {

    /** Constant for holding ClassWiseStudentMarkSheet_Average. */
    private static final String CLASS_WISE_STUDENT_MARK_SHEET_AVERAGE = "ClassWiseStudentMarkSheet_Average";

    /** Constant for holding ClassWiseStudentMarkSheet_Total. */
    private static final String CLASS_WISE_STUDENT_MARK_SHEET_TOTAL = "ClassWiseStudentMarkSheet_Total";

    /** Constant for holding ClassWiseStudentMarkSheet_RANK. */
    private static final String CLASS_WISE_STUDENT_MARK_SHEET_RANK = "ClassWiseStudentMarkSheet_RANK";

    /** Constant to hold possible sorted option. */
    private static final String NAME = "name";

    /** Constant for holding ClassWiseStudentMarkSheet_Medium_Average. */
    private static final String CLASS_WISE_STUDENT_MARK_SHEET_MEDIUM_AVERAGE =
            "ClassWiseStudentMarkSheet_Medium_Average";

    /** Constant for holding ClassWiseStudentMarkSheet_Study_Medium. */
    private static final String CLASS_WISE_STUDENT_MARK_SHEET_STUDY_MEDIUM = "ClassWiseStudentMarkSheet_Study_Medium";

    /** Constant for holding mediumLabel. */
    private static final String MEDIUM_LABEL = "mediumLabel";

    /** Constant for holding sub report datasource. */
    private static final String MEDIUM_WISE_SUBJECT_AVERAGE_DATASOURCE = "datasource1";

    /** Constant for holding main datasource. */
    private static final String MAIN_DATASOURCE = "datasource";

    /** Constant to holding error message. */
    public static final String MARKING_NOT_COMPLETED = "REPORT.PRIZE.MARKING.NOT.COMPLETED";

    /** Constant for holding subjectAverageLabel. */
    private static final String SUBJECT_AVERAGE_LABEL = "subjectAverageLabel";

    /** Constant for holding ClassWiseStudentMarkSheet_Subject_Average. */
    private static final String CLASS_WISE_STUDENT_MARK_SHEET_SUBJECT_AVERAGE =
            "ClassWiseStudentMarkSheet_Subject_Average";

    /** Constant for holding ClassWiseStudentMarkSheet_Term. */
    private static final String CLASS_WISE_STUDENT_MARK_SHEET_TERM = "ClassWiseStudentMarkSheet_Term";

    /** Constant for holding termLabel. */
    private static final String TERM_LABEL = "termLabel";

    /** Constant for holding index in the list for average. */
    private static final int AVERAGE_INDEX = 21;

    /** Constant for holding index in the list for total. */
    private static final int TOTAL_INDEX = 20;

    /** Constant for holding maximum no of columns. */
    private static final int MAXIMUM_NO_OF_COLUMNS = 23;

    /** Constant for holding ab Stirng. */
    private static final String ABSENT_STRING = " ab ";

    /** Constant for holding ClassWiseStudentMarkSheet_Admission_No. */
    private static final String CLASS_WISE_STUDENT_MARK_SHEET_ADMISSION_NO = "ClassWiseStudentMarkSheet_Admission_No";

    /** Constant for holding subjectAvaregeList. */
    private static final String SUBJECT_AVAREGE_LIST = "subjectAvaregeList";

    /** Constant for Report_page. */
    private static final String REPORT_PAGE = "Report_page";

    /** Constant for page. */
    private static final String PAGE = "page";

    /** variable for holding string "generatedDate". */
    private static final String GENERATED_DATE = "generatedDate";

    /** Constant for year. */
    private static final String YEAR = "year";

    /** Constant for gplComment. */
    private static final String GPL_COMMENT_TEMPL = "gplComment";

    /** Constant for ClassWiseStudentMarkSheet_Year. */
    private static final String CLASS_WISE_STUDENT_MARK_SHEET_YEAR = "ClassWiseStudentMarkSheet_Year";

    /** Constant for year. */
    private static final String YEAR_VALUE = "yearValue";

    /** Constant for report title. */
    private static final String CLASS_WISE_STUDENT_MARK_SHEET_NAME = "ClassWiseStudentMarkSheet_Name";

    /** Constant for classWiseStudentMarksReport.htm. */
    private static final String CLASS_WISE_STUDENT_MARKS_REPORT_HTM = "classWiseStudentMarksReport.htm";

    /** Constant for styleTemplate. */
    private static final String STYLE_TEMPLATE = "styleTemplate";

    /** SystemConfig property file. */
    public static final String SYSTEM_CONFIG = "systemConfig";

    /** Holds the value for appserver.home. */
    private static final String APPSERVER_HOME = "appserver.home";

    /** Constant for style.path. */
    private static final String STYLE_PATH = "style.path";

    /** Constant for holding Report_reportGeneratedOn. */
    private static final String REPORT_REPORT_GENERATED_ON = "Report_reportGeneratedOn";

    /** Constant for holding ClassWiseStudentMarkSheet_Class_Name. */
    private static final String CLASS_WISE_STUDENT_MARK_SHEET_CLASS_NAME = "ClassWiseStudentMarkSheet_Class_Name";

    /** Constant for holding ClassWiseStudentMarkSheet_Title. */
    private static final String CLASS_WISE_STUDENT_MARK_SHEET_TITLE = "ClassWiseStudentMarkSheet_Title";

    /** Constant for holding n/a. */
    //private static final String N_A = "n/a";

    /** Constant for holding nameWithInitials. */
    private static final String NAME_WITH_INITIALS = "nameWithInitials";

    /** Constant for holding admissionNo. */
    private static final String ADMISSION_NO = "admissionNo";

    /** Constant for holding subjects. */
    private static final String SUBJECTS = "subjects";

    /** Constant for holding classWiseStudentMarkSheet. */
    private static final String CLASS_WISE_STUDENT_MARK_SHEET = "classWiseStudentMarkSheetReport";

    /** Constant for holding logo. */
    private static final String LOGO = "logo";

    /** Constant for holding classGradeList. */
    private static final String CLASS_GRADE_LIST = "classGradeList";

    /** Constant for holding /loadClassListForStudentMarkSheetReport.htm. */
    private static final String LOAD_CLASS_LIST_FOR_STUDENT_MARK_SHEET_REPORT_HTM =
            "/loadClassListForStudentMarkSheetReport.htm";

    /** Constant for holding modle attribute name. */
    private static final String STUDENT_MARK_SHEET_TEMPLATE = "studentMarkSheetTemplate";

    /** Constant for holding reporting/classWiseStudentMarksSheet. */
    private static final String REPORTING_CLASS_WISE_STUDENT_MARKS_SHEET = "reporting/classWiseStudentMarksSheet";

    /** Constant for holding message. */
    private static final String TITLE = "title";

    /** Constant for holding reportGeneratedOn. */
    private static final String CLASS_NAME = "className";

    /** Constant for holding reportGeneratedOn. */
    private static final String REPORT_GENERATED_ON = "reportGeneratedOn";

    /** Constant for holding term. */
    private static final String TERM = "term";

    /** Constant for holding class. */
    private static final String CLASS = "class";

    /** Constant for holding gradeList. */
    private static final String GRADE_LIST = "gradeList";

    /** Constant for holding termList. */
    private static final String TERM_LIST = "termList";

    /** Constant for holding yearList. */
    private static final String YEAR_LIST = "yearList";

    /** Constant for holding message. */
    public static final String MESSAGE = "message";

    /** Constant for holding error message key. */
    public static final String NO_DATA_ERROR_MESSAGE = "REF.UI.NO.DATA";

    /** Maximum length of subject. */
    private static final int MAX_LENGTH_OF_SUBJECT = 5;

    /** Maximum no of subjects is shown in report. */
    private static final int MAXIMUM_SUBJECTS = 20;

    /** model attribute for report generated date. */
    private static final String REPORT_GENERATED_DATE_LOCALE = "REPORT.GENERATED.DATE.LOCALE";
    
    /** Report property Name. */
    private static final String NOTE_LABEL = "note";
	
	
	  /** model attribute for report note. */
    private static final String REPORT_MAXMARK_NOTE = "REPORT.MAXMARK.NOTE";
    
    

    /** commonService attribute for holding CommonService instance. */
    private CommonService commonService;

    /** studentService attribute for holding StudentService instance. */
    private StudentService studentService;

    /**
     * ClassWiseStudentMarksSheetValidator attribute for holding ClassWiseStudentMarksSheetValidator instance.
     */
    private ClassWiseStudentMarksSheetValidator classWiseStudentMarksSheetValidator;

    /**
     * Setter method for commonService.
     *
     * @param vCommonService CommonService object
     */
    public void setCommonService(CommonService vCommonService) {

        this.commonService = vCommonService;
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
     * Setter method for classWiseStudentMarksSheetValidator.
     *
     * @param vClassWiseStudentMarksSheetValidator object
     */
    public void setClassWiseStudentMarksSheetValidator(
            ClassWiseStudentMarksSheetValidator vClassWiseStudentMarksSheetValidator) {

        this.classWiseStudentMarksSheetValidator = vClassWiseStudentMarksSheetValidator;
    }

    /**
     * Load page which facilitate input data for generate class wise student mark sheet.
     *
     * @param modelMap ModelMap object
     * @return name of the jsp to direct
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showStudentMarkSheetReportForm(ModelMap modelMap) {

        StudentMarkSheetTemplate studentMarkSheetTemplate = new StudentMarkSheetTemplate();
        modelMap.addAttribute(STUDENT_MARK_SHEET_TEMPLATE, studentMarkSheetTemplate);
        return REPORTING_CLASS_WISE_STUDENT_MARKS_SHEET;
    }

    /**
     * Load page with class list.
     *
     * @param modelMap ModelMap object
     * @param studentMarkSheetTemplate model attribute
     * @return name of the jsp to direct
     * @throws AkuraAppException AkuraAppException throws exception if occur
     */
    @RequestMapping(value = LOAD_CLASS_LIST_FOR_STUDENT_MARK_SHEET_REPORT_HTM)
    public String loadClassList(ModelMap modelMap,
            @ModelAttribute(STUDENT_MARK_SHEET_TEMPLATE) StudentMarkSheetTemplate studentMarkSheetTemplate)
            throws AkuraAppException {

        int gradeId = studentMarkSheetTemplate.getGrade().getGradeId();

        if (gradeId != 0) {
            Grade grade = commonService.findGradeById(gradeId);
            List<ClassGrade> classGrades = SortUtil.sortClassGradeList(commonService.getClassGradeListByGrade(grade));
            modelMap.addAttribute(CLASS_GRADE_LIST, classGrades);
        }

        modelMap.addAttribute(STUDENT_MARK_SHEET_TEMPLATE, studentMarkSheetTemplate);

        return REPORTING_CLASS_WISE_STUDENT_MARKS_SHEET;
    }

    /**
     * This method initiate the requested report generation.
     *
     * @param response http servlet response
     * @param studentMarkSheetTemplate - model Attribute
     * @param bindingResult the binding result
     * @param modelMap - model map to set data
     * @return ModleAndView object
     * @throws AkuraException throws if exception occur
     */
    @RequestMapping(value = CLASS_WISE_STUDENT_MARKS_REPORT_HTM, method = RequestMethod.POST)
    public ModelAndView generateClassWiseStudentMarksSheetReport(HttpServletResponse response,
            @ModelAttribute(STUDENT_MARK_SHEET_TEMPLATE) StudentMarkSheetTemplate studentMarkSheetTemplate,
            BindingResult bindingResult, ModelMap modelMap) throws AkuraException {

        ModelAndView modelAndView = null;
        classWiseStudentMarksSheetValidator.validate(studentMarkSheetTemplate, bindingResult);

        if (bindingResult.hasErrors()) {

            modelAndView =
                    new ModelAndView(REPORTING_CLASS_WISE_STUDENT_MARKS_SHEET, STUDENT_MARK_SHEET_TEMPLATE,
                            studentMarkSheetTemplate);

            if (studentMarkSheetTemplate.getGrade().getGradeId() != 0) {

                Grade grade = commonService.findGradeById(studentMarkSheetTemplate.getGrade().getGradeId());
                List<ClassGrade> classGrades =
                        SortUtil.sortClassGradeList(commonService.getClassGradeListByGrade(grade));
                modelAndView.addObject(CLASS_GRADE_LIST, classGrades);
            }
        } else {

            if (studentService.isMarkingCompletedForTerm(studentMarkSheetTemplate.getClassGrade().getClassGradeId(),
                    studentMarkSheetTemplate.getTerm().getTermId(), Integer
                            .parseInt(studentMarkSheetTemplate.getYear()))) {

                Map<String, Object> map = new HashMap<String, Object>();
                map.put(LOGO, ReportUtil.getReportLogo());

                List<GradeSubject> gradeSubjects =
                        commonService.getGradeSubjectList(studentMarkSheetTemplate.getGrade().getGradeId());
                ClassGrade classGrade =
                        commonService.findClassGrade(studentMarkSheetTemplate.getClassGrade().getClassGradeId());

                SortUtil.sortGradeSubjectList(gradeSubjects);
                List<String> subjects = StaticDataUtil.getListWithEmptyValues(MAXIMUM_NO_OF_COLUMNS);
                List<Integer> gradeSubjectsIds = new LinkedList<Integer>();
                List<String> subjectsAverageList = StaticDataUtil.getListWithEmptyValues(MAXIMUM_SUBJECTS);
                Term term = commonService.findTermById(studentMarkSheetTemplate.getTerm().getTermId());

                if (gradeSubjects.size() > 0) {

                    for (int i = 0; i < MAXIMUM_SUBJECTS; i++) {

                        Subject subject = gradeSubjects.get(i).getSubject();
                        String subjectDescription = subject.getDescription();
                        
                        if(subject.getSubjectCode() == null) {
                            
                            if (subjectDescription.length() >= MAX_LENGTH_OF_SUBJECT) {

                                subjects.set(i, subjectDescription.substring(0, MAX_LENGTH_OF_SUBJECT));
                            } else {
                            
                                subjects.set(i, subjectDescription);
                          
                            }
                        } else {
                            subjects.set(i, subject.getSubjectCode());
                        }
                        gradeSubjectsIds.add(gradeSubjects.get(i).getGradeSubjectId());

                        SubjectAverageTermMarks subjectAverageTermMarks =
                                studentService.getSubjectAverageTermMarksById(classGrade.getDescription(), Integer
                                        .parseInt(studentMarkSheetTemplate.getYear()), gradeSubjects.get(i)
                                        .getGradeSubjectId(), term.getDescription());

                        subjectsAverageList.set(i, StaticDataUtil.setNoOfDigitsInNumber(2, subjectAverageTermMarks
                                .getAverage()));

                        if (i > (gradeSubjects.size() - 2)) {
                            break;
                        }
                    }
                }
                subjects.set(TOTAL_INDEX, 
                        PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, 
                                                        CLASS_WISE_STUDENT_MARK_SHEET_TOTAL));
                subjects.set(AVERAGE_INDEX, 
                        PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, 
                                                        CLASS_WISE_STUDENT_MARK_SHEET_AVERAGE));
                subjects.set(AkuraConstant.RANK_INDEX,
                        PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, 
                                                        CLASS_WISE_STUDENT_MARK_SHEET_RANK));
                List<StudentTermMarkDTO> studentTermsMarksList =
                        studentService.getTermMarksByTermGradeYear(studentMarkSheetTemplate.getClassGrade()
                                .getClassGradeId(), studentMarkSheetTemplate.getTerm().getTermId(),
                                studentMarkSheetTemplate.getYear());
                List<ClassWiseStudentReportData> reportDataList =
                        this.processReportData(studentTermsMarksList, gradeSubjectsIds, studentMarkSheetTemplate);

                map.put(SUBJECTS, subjects);
                map.put(SUBJECT_AVAREGE_LIST, subjectsAverageList);
                map.put(ADMISSION_NO, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                        CLASS_WISE_STUDENT_MARK_SHEET_ADMISSION_NO));

                map.put(NAME_WITH_INITIALS, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                        CLASS_WISE_STUDENT_MARK_SHEET_NAME));

                JRBeanCollectionDataSource datasource = null;

                if (NAME.equals(studentMarkSheetTemplate.getSortedBy())) {

                    datasource =
                            new JRBeanCollectionDataSource(SortUtil
                                    .sortClassWiseStudentReportDataByName(reportDataList));

                } else {

                    datasource =
                            new JRBeanCollectionDataSource(SortUtil
                                    .sortClassWiseStudentReportDataByRank(reportDataList));

                }
                populateReportGenearlDetails(map, studentMarkSheetTemplate);

                if (datasource.getRecordCount() != 0) {
                    
                    map.put(MAIN_DATASOURCE, datasource);
                    this.populateMediumWiseSubjectAverageDetails(gradeSubjects, classGrade, studentMarkSheetTemplate,
                            term.getDescription(), map);
                    modelAndView = new ModelAndView(CLASS_WISE_STUDENT_MARK_SHEET, map);                  
                } else {
                    modelAndView =
                            new ModelAndView(REPORTING_CLASS_WISE_STUDENT_MARKS_SHEET, STUDENT_MARK_SHEET_TEMPLATE,
                                    studentMarkSheetTemplate);

                    Grade grade = commonService.findGradeById(studentMarkSheetTemplate.getGrade().getGradeId());
                    List<ClassGrade> classGrades =
                            SortUtil.sortClassGradeList(commonService.getClassGradeListByGrade(grade));
                    modelAndView.addObject(CLASS_GRADE_LIST, classGrades);

                    setErrorMessage(modelMap, NO_DATA_ERROR_MESSAGE);
                }
            } else {
                modelAndView =
                        new ModelAndView(REPORTING_CLASS_WISE_STUDENT_MARKS_SHEET, STUDENT_MARK_SHEET_TEMPLATE,
                                studentMarkSheetTemplate);

                Grade grade = commonService.findGradeById(studentMarkSheetTemplate.getGrade().getGradeId());
                List<ClassGrade> classGrades =
                        SortUtil.sortClassGradeList(commonService.getClassGradeListByGrade(grade));
                modelAndView.addObject(CLASS_GRADE_LIST, classGrades);

                setErrorMessage(modelMap, MARKING_NOT_COMPLETED);
            }
        }
        return modelAndView;
    }

    /**
     * This method is used to process the report data.
     *
     * @param studentTermsMarksList list of StudentTermMarkDTO objects
     * @param gradeSubjectsIds list of grade subject ids
     * @param studentMarkSheetTemplate model object
     * @return list of ClassWiseStudentReportData objects
     * @throws AkuraAppException throws if exception occurs
     */
    private List<ClassWiseStudentReportData> processReportData(List<StudentTermMarkDTO> studentTermsMarksList,
            List<Integer> gradeSubjectsIds, StudentMarkSheetTemplate studentMarkSheetTemplate) 
                                                                                throws AkuraAppException {

        List<ClassWiseStudentReportData> list = new ArrayList<ClassWiseStudentReportData>();

        int tempStudentId = 0;
        ClassWiseStudentReportData cwrd = null;
        List<String> marks = null;

        for (StudentTermMarkDTO studentTermMarkDTO : studentTermsMarksList) {

            StudentTermMarksRank studentTermMarksRank =
                    studentService.getStudentTermMarksRank(studentTermMarkDTO.getStudentClassInfoId(),
                            studentTermMarkDTO.getTermId());
            if (tempStudentId != studentTermMarkDTO.getStudentId()) {

                if (cwrd != null) {
                    list.add(cwrd);
                }
                cwrd = new ClassWiseStudentReportData();
                marks = StaticDataUtil.getListWithEmptyValues(MAXIMUM_NO_OF_COLUMNS);

                for (int i = 0; i < gradeSubjectsIds.size(); i++) {
                    marks.set(i, "");
                }
                marks.set(TOTAL_INDEX, StaticDataUtil.setNoOfDigitsInNumber(0, studentTermMarksRank.getTotal()));
                marks.set(AVERAGE_INDEX, StaticDataUtil.setNoOfDigitsInNumber(2, studentTermMarksRank.getAverage()));
                marks.set(AkuraConstant.RANK_INDEX, String.valueOf(studentTermMarksRank.getRank()));

                cwrd.setAdmissionNo(studentTermMarkDTO.getAdmissionNo());
                cwrd.setName(studentTermMarkDTO.getNameWithInitials());
                cwrd.setStudyMedium(commonService.getStudyMediumById(studentTermMarkDTO.getLanguageId())
                        .getStudyMediumName().substring(0, 1));
                cwrd.setMarks(marks);

                if (gradeSubjectsIds.contains(studentTermMarkDTO.getGradeSubjectId())) {

                    if (studentTermMarkDTO.isAbsent()) {
                        marks.set(gradeSubjectsIds.indexOf(studentTermMarkDTO.getGradeSubjectId()), ABSENT_STRING);

                    } else {
                        marks.set(gradeSubjectsIds.indexOf(studentTermMarkDTO.getGradeSubjectId()), StaticDataUtil
                                .setNoOfDigitsInNumber(0, studentTermMarkDTO.getMarks()));
                    }
                }
            } else {

                if (gradeSubjectsIds.contains(studentTermMarkDTO.getGradeSubjectId())) {

                    if (studentTermMarkDTO.isAbsent()) {
                        marks.set(gradeSubjectsIds.indexOf(studentTermMarkDTO.getGradeSubjectId()), ABSENT_STRING);

                    } else {
                        marks.set(gradeSubjectsIds.indexOf(studentTermMarkDTO.getGradeSubjectId()), StaticDataUtil
                                .setNoOfDigitsInNumber(0, studentTermMarkDTO.getMarks()));
                    }
                }
            }
            tempStudentId = studentTermMarkDTO.getStudentId();
        }

        if (cwrd != null) {
            list.add(cwrd);
        }
        return list;
    }

    /**
     * Populate medium wise subject average details.
     *
     * @param gradeSubjects list of grade subjects assign to class
     * @param classGrade classGrade object
     * @param studentMarkSheetTemplate model object
     * @param termDescription term description
     * @param map to hold data to show in report
     * @throws AkuraAppException throws if exception occurs
     */
    private void populateMediumWiseSubjectAverageDetails(List<GradeSubject> gradeSubjects, ClassGrade classGrade,
            StudentMarkSheetTemplate studentMarkSheetTemplate, String termDescription, Map<String, Object> map)
            throws AkuraAppException {

        List<SubjectAveragesTemplate> list = new ArrayList<SubjectAveragesTemplate>();

        List<Integer> studyMediumList =
                studentService.getStudyMediumsInClass(studentMarkSheetTemplate.getClassGrade().getClassGradeId(),
                        Integer.parseInt(studentMarkSheetTemplate.getYear()), termDescription);

        for (int studyMediumId : studyMediumList) {

            SubjectAveragesTemplate subjectAveragesTempl = new SubjectAveragesTemplate();
            List<String> subjectsAverageList = StaticDataUtil.getListWithEmptyValues(MAXIMUM_SUBJECTS);

            for (int i = 0; i < MAXIMUM_SUBJECTS; i++) {

                MediumWiseClassSubjectAverageView mediumWiseClassSubjectAvg =
                        studentService.getMediumWiseClassSubjectAverage(studentMarkSheetTemplate.getClassGrade()
                                .getClassGradeId(), Integer.parseInt(studentMarkSheetTemplate.getYear()), gradeSubjects
                                .get(i).getGradeSubjectId(), termDescription, studyMediumId);

                if (i == 0) {
                    StudyMedium studyMedium = commonService.getStudyMediumById(studyMediumId);
                    subjectAveragesTempl.setStudyMedium(studyMedium.getStudyMediumName()
                            + AkuraWebConstant.SPACE
                            + PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                                    CLASS_WISE_STUDENT_MARK_SHEET_MEDIUM_AVERAGE));
                }

                if (mediumWiseClassSubjectAvg == null) {

                    subjectsAverageList.set(i, "");

                } else {
                    subjectsAverageList.set(i, StaticDataUtil.setNoOfDigitsInNumber(2, mediumWiseClassSubjectAvg
                            .getAverage()));
                }

                if (i > (gradeSubjects.size() - 2)) {
                    break;
                }
            }
            subjectAveragesTempl.setSubjectAverageList(subjectsAverageList);
            list.add(subjectAveragesTempl);
        }

        JRDataSource datasource1 = new JRBeanCollectionDataSource(list);

        map.put(MEDIUM_WISE_SUBJECT_AVERAGE_DATASOURCE, datasource1);

    }

    /**
     * This method used to populate report required data.
     *
     * @param map to hold data to show in report
     * @param studentMarkSheetTemplate command object of relevant page
     * @throws AkuraAppException throws when exception occurs
     */
    private void populateReportGenearlDetails(Map<String, Object> map, 
                            StudentMarkSheetTemplate studentMarkSheetTemplate)
            throws AkuraAppException {

        ClassGrade classGrade =
                commonService.findClassGrade(studentMarkSheetTemplate.getClassGrade().getClassGradeId());
        Term term = commonService.findTermById(studentMarkSheetTemplate.getTerm().getTermId());

        map.put(CLASS, classGrade.getDescription());
        map.put(TERM, term.getDescription());
        map.put(REPORT_GENERATED_ON, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_REPORT_GENERATED_ON));
        map.put(CLASS_NAME, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                CLASS_WISE_STUDENT_MARK_SHEET_CLASS_NAME));
        map
                .put(TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                        CLASS_WISE_STUDENT_MARK_SHEET_TITLE));
        map.put(YEAR_VALUE, studentMarkSheetTemplate.getYear());
        map.put(YEAR, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, CLASS_WISE_STUDENT_MARK_SHEET_YEAR));
        map.put(PAGE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_PAGE));
        map.put(GPL_COMMENT_TEMPL, AkuraWebConstant.REPORT_GPL);
        map.put(STYLE_TEMPLATE, PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                + PropertyReader.getPropertyValue(SYSTEM_CONFIG, STYLE_PATH));

        map.put(TERM_LABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                CLASS_WISE_STUDENT_MARK_SHEET_TERM));
        map.put(GENERATED_DATE, DateUtil.getReportGeneratedDate(PropertyReader.getPropertyValue(
                ReportUtil.REPORT_TEMPLATE, REPORT_GENERATED_DATE_LOCALE)));
        map.put(SUBJECT_AVERAGE_LABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                CLASS_WISE_STUDENT_MARK_SHEET_SUBJECT_AVERAGE));
        map.put(MEDIUM_LABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                CLASS_WISE_STUDENT_MARK_SHEET_STUDY_MEDIUM));
        map.put(NOTE_LABEL,PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_MAXMARK_NOTE));
    }

    /**
     * Returns list of grades.
     *
     * @return the grade list.
     * @throws AkuraAppException AkuraAppException
     */
    @ModelAttribute(GRADE_LIST)
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
     * Returns a list of years.
     *
     * @return gradeClassList - a list of years.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @ModelAttribute(YEAR_LIST)
    public List<String> populateYearList() throws AkuraAppException {

        List<String> yearList = new ArrayList<String>();
        final int maxYear = 5;
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        yearList.add(String.valueOf(year));

        for (int index = 1; index < maxYear; index++) {
            int newYear = year - index;
            yearList.add(String.valueOf(newYear));
        }
        return yearList;
    }

    /**
     * Set the error messages when returning to the jsp.
     *
     * @param modelMap - ModelMap
     * @param errorMessge reference to error message in property file
     */
    private void setErrorMessage(ModelMap modelMap, String errorMessge) {

        String message = new ErrorMsgLoader().getErrorMessage(errorMessge);
        modelMap.addAttribute(MESSAGE, message);
    }
}
