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
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.GradeSubject;
import com.virtusa.akura.api.dto.PrizeListReportTemplate;
import com.virtusa.akura.api.dto.StudentAnnualGradeRank;
import com.virtusa.akura.api.dto.StudentGradeSubjectRankView;
import com.virtusa.akura.api.dto.StudentMarksReportData;
import com.virtusa.akura.api.dto.StudentTermMarkDTO;
import com.virtusa.akura.api.dto.StudentsGradeRankView;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.reporting.validator.PrizeTopListValidator;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;
import com.virtusa.akura.util.StaticDataUtil;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * PrizeTopListController is to generate report of student Mark sheet with average/rank.
 *
 * @author Virtusa Corporation
 */
@Controller
public class PrizeTopListController {

    /** Constant for holding termLabel. */
    private static final String TERM_LABEL = "termLabel";

    /** Constant for holding noOfPrizesList. */
    private static final String NO_OF_PRIZES_LIST = "noOfPrizesList";

    /** Constant for holding yearList. */
    private static final String YEAR_LIST = "yearList";

    /** Constant for holding gradeList. */
    private static final String GRADE_LIST = "gradeList";

    /** Constant for holding SubjectPrizeTopList. */
    private static final String PRIZE_TOP_LIST_TEMPLATE = "prizeTopList";

    /** Constant for holding Term. */
    private static final String TERM = "Term ";

    /** Constant for holding subjectPrizeListReport.htm. */
    private static final String SUBJECT_PRIZE_LIST_REPORT_HTM = "subjectPrizeListReport.htm";

    /** Constant for holding gradeSubjectList. */
    private static final String GRADE_SUBJECT_LIST = "gradeSubjectList";

    /** Constant for holding loadSubjectsForPrizeListReport.htm. */
    private static final String LOAD_SUBJECTS_FOR_PRIZE_LIST_REPORT_HTM = "loadSubjectsForPrizeListReport.htm";

    /** Constant for holding reporting/subjectPrizeList. */
    private static final String PRIZE_LIST_PAGE = "reporting/prizeList";

    /** Constant to represent name of command object of the form. */
    private static final String PRIZE_LIST_REPORT_TEMPLATE = "prizeListReportTemplate";

    /** Constant for space. */
    private static final String SPACE = " ";

    /** Constant to hold index for total in list. */
    private static final int TOTAL_INDEX = 5;

    /** Constant for holding GradeSubjectPrizeList_Average. */
    private static final String PRIZE_LIST_AVERAGE = "PrizeList_Average";

    /** Constant for holding GradeSubjectPrizeList_Total. */
    private static final String PRIZE_LIST_TOTAL = "PrizeList_Total";

    /** Constant for holding totalLabel. */
    private static final String TOTAL_LABEL = "totalLabel";

    /** Constant for holding averageLabel. */
    private static final String AVERAGE_LABEL = "averageLabel";

    /** Constant for holding rankLabel. */
    private static final String RANK_LABEL = "rankLabel";

    /** Constant for holding GradeSubjectPrizeList_Rank. */
    private static final String PRIZE_LIST_RANK = "PrizeList_Rank";

    /** Constant for holding message. */
    public static final String MESSAGE = "message";

    /** Constant for holding error message key. */
    public static final String NO_DATA_ERROR_MESSAGE = "REF.UI.NO.DATA";

    /** Constant to holding error message. */
    public static final String MARKING_NOT_COMPLETED = "REPORT.PRIZE.MARKING.NOT.COMPLETED";

    /** Constant for holding ab Stirng. */
    private static final String ABSENT_STRING = " ab ";

    /** Constant for holding grade. */
    private static final String GRADE = "grade";

    /** Constant for holding GradeSubjectPrizeList_Title. */
    private static final String PRIZE_LIST_TITLE = "PrizeList_Title";

    /** Constant for holding GradeSubjectPrizeLis_Year. */
    private static final String PRIZE_LIST_YEAR = "PrizeList_Year";

    /** Constant for holding GradeSubjectPrizeLis_Grade_label. */
    private static final String PRIZE_LIST_GRADE_LABEL = "PrizeList_Grade_label";

    /** Constant for holding GradeSubjectPrizeLis_Name. */
    private static final String PRIZE_LIST_NAME = "PrizeList_Name";

    /** Constant for holding PrizeList_Admission_No. */
    private static final String PRIZE_LIST_ADMISSION_NO = "PrizeList_Admission_No";

    /** Constant for holding message. */
    private static final String TITLE = "title";

    /** model attribute for report generated date. */
    private static final String REPORT_GENERATED_DATE_LOCALE = "REPORT.GENERATED.DATE.LOCALE";

    /** Constant for Report_page. */
    private static final String REPORT_PAGE = "Report_page";

    /** SystemConfig property file. */
    public static final String SYSTEM_CONFIG = "systemConfig";

    /** Holds the value for appserver.home. */
    private static final String APPSERVER_HOME = "appserver.home";

    /** Constant for style.path. */
    private static final String STYLE_PATH = "style.path";

    /** Constant for holding logo. */
    private static final String LOGO = "logo";

    /** Constant for holding yearLabel. */
    private static final String YEAR_LABEL = "yearLabel";

    /** Constant for holding gradeLabel. */
    private static final String GRADE_LABEL = "gradeLabel";

    /** Constant for holding nameWithInitials. */
    private static final String NAME_WITH_INITIALS = "nameWithInitials";

    /** Constant for holding admissionNo. */
    private static final String ADMISSION_NO = "admissionNo";

    /** Constant for year. */
    private static final String YEAR_VALUE = "yearValue";

    /** Constant for holding reportGeneratedOn. */
    private static final String REPORT_GENERATED_ON = "reportGeneratedOn";

    /** Constant for page. */
    private static final String PAGE = "page";

    /** Constant for gplComment. */
    private static final String GPL_COMMENT_TEMPL = "gplComment";

    /** Constant for styleTemplate. */
    private static final String STYLE_TEMPLATE = "styleTemplate";

    /** variable for holding string "generatedDate". */
    private static final String GENERATED_DATE = "generatedDate";

    /** Constant for holding Report_reportGeneratedOn. */
    private static final String REPORT_REPORT_GENERATED_ON = "Report_reportGeneratedOn";

    /** Constant for holding maximum no of columns. */
    private static final int MAXIMUM_NO_OF_COLUMNS = 8;

    /** Constant to hold index for rank in list. */
    private static final int RANK_INDEX = 7;

    /** Constant to hold index for average in list. */
    private static final int AVERAGE_INDEX = 6;

    /** Constant to hold maximum no of terms. */
    private static final int MAXIMUM_NO_OF_TERMS = 5;

    /** commonService attribute for holding CommonService instance. */
    private CommonService commonService;

    /** studentService attribute for holding StudentService instance. */
    private StudentService studentService;

    /** prizeTopListValidator attribute for holding PrizeTopListValidator instance. */
    private PrizeTopListValidator prizeTopListValidator;
    
    /** Report property Name. */
    private static final String NOTE_LABEL = "note";
	
	
	  /** model attribute for report note. */
    private static final String REPORT_MAXMARK_NOTE = "REPORT.MAXMARK.NOTE";
	
	  

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
     * @param vStudentService object
     */
    public void setStudentService(StudentService vStudentService) {

        this.studentService = vStudentService;
    }

    /**
     * Setter method for prizeTopListValidator.
     *
     * @param vPrizeTopListValidator object
     */
    public void setPrizeTopListValidator(PrizeTopListValidator vPrizeTopListValidator) {

        this.prizeTopListValidator = vPrizeTopListValidator;
    }

    /**
     * Load page which facilitate input data for generate Grade wise student rank list.
     *
     * @param modelMap ModelMap object
     * @return name of the jsp to direct
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showForm(ModelMap modelMap) {

        PrizeListReportTemplate prizeListReportTemplate = new PrizeListReportTemplate();
        modelMap.addAttribute(PRIZE_LIST_REPORT_TEMPLATE, prizeListReportTemplate);
        return PRIZE_LIST_PAGE;
    }

    /**
     * Load page with grade Subject list.
     *
     * @param modelMap ModelMap object
     * @param prizeListReportTemplate model attribute
     * @return name of the jsp to direct
     * @throws AkuraAppException AkuraAppException throws exception if occur
     */
    @RequestMapping(value = LOAD_SUBJECTS_FOR_PRIZE_LIST_REPORT_HTM)
    public String loadSubjectList(ModelMap modelMap,
            @ModelAttribute(PRIZE_LIST_REPORT_TEMPLATE) PrizeListReportTemplate prizeListReportTemplate)
            throws AkuraAppException {

        int gradeId = prizeListReportTemplate.getGrade().getGradeId();

        if (gradeId != 0) {
            modelMap.addAttribute(GRADE_SUBJECT_LIST, SortUtil.sortGradeSubjectList(commonService
                    .getGradeSubjectList(gradeId)));
        }
        return PRIZE_LIST_PAGE;
    }

    /**
     * This method initiate the requested report generation.
     *
     * @param response http servlet response
     * @param prizeListReportTemplate - model Attribute
     * @param bindingResult the binding result
     * @param modelMap - model map to set data
     * @return ModleAndView object
     * @throws AkuraException throws if exception occur
     */
    @RequestMapping(value = SUBJECT_PRIZE_LIST_REPORT_HTM, method = RequestMethod.POST)
    public ModelAndView generateSubjectprizeListReport(HttpServletResponse response,
            @ModelAttribute(PRIZE_LIST_REPORT_TEMPLATE) PrizeListReportTemplate prizeListReportTemplate,
            BindingResult bindingResult, ModelMap modelMap) throws AkuraException {

        ModelAndView modelAndView = null;
        prizeTopListValidator.validate(prizeListReportTemplate, bindingResult);

        if (bindingResult.hasErrors()) {
            modelAndView = new ModelAndView(PRIZE_LIST_PAGE, PRIZE_LIST_REPORT_TEMPLATE, prizeListReportTemplate);

            int gradeId = prizeListReportTemplate.getGrade().getGradeId();

            if (gradeId != 0) {
                modelAndView.addObject(GRADE_SUBJECT_LIST, SortUtil.sortGradeSubjectList(commonService
                        .getGradeSubjectList(gradeId)));
            }
        } else {

            if (studentService.isAnnualMarkingDoneForGrade(prizeListReportTemplate.getGrade().getGradeId(), Integer
                    .parseInt(prizeListReportTemplate.getYear()))) {

                Map<String, Object> map = new HashMap<String, Object>();
                List<StudentMarksReportData> reportDataList = null;

                if (prizeListReportTemplate.getGradeSubject().getGradeSubjectId() == 0) {
                    List<StudentAnnualGradeRank> annualGradeRankList =
                            studentService.findStudentAnnualGradeRank(prizeListReportTemplate.getGrade().getGradeId(),
                                    Integer.parseInt(prizeListReportTemplate.getYear()), prizeListReportTemplate
                                            .getNoOfPrizes());
                    reportDataList = this.processTotalPrizeReportData(annualGradeRankList, map);

                } else {
                    List<StudentGradeSubjectRankView> gradeSubjectRanklist =
                            studentService.getStudentGradeSubjectRankList(prizeListReportTemplate.getGradeSubject()
                                    .getGradeSubjectId(), Integer.parseInt(prizeListReportTemplate.getYear()),
                                    prizeListReportTemplate.getNoOfPrizes());
                    reportDataList = this.processSubjectPrizeReportData(gradeSubjectRanklist, map);
                }

                GradeSubject gradeSubject =
                        commonService.findGradeSubject(prizeListReportTemplate.getGradeSubject().getGradeSubjectId());

                this.populateGeneralReportDetails(map, prizeListReportTemplate, gradeSubject);
                JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(reportDataList);

                if (datasource.getRecordCount() != 0) {

                    displayReportForm(response, datasource, map, PRIZE_TOP_LIST_TEMPLATE);

                } else {

                    modelAndView =
                            new ModelAndView(PRIZE_LIST_PAGE, PRIZE_LIST_REPORT_TEMPLATE, prizeListReportTemplate);

                    int gradeId = prizeListReportTemplate.getGrade().getGradeId();

                    if (gradeId != 0) {
                        modelAndView.addObject(GRADE_SUBJECT_LIST, SortUtil.sortGradeSubjectList(commonService
                                .getGradeSubjectList(gradeId)));
                    }
                    setErrorMessage(modelMap, NO_DATA_ERROR_MESSAGE);

                }

            } else {
                modelAndView = new ModelAndView(PRIZE_LIST_PAGE, PRIZE_LIST_REPORT_TEMPLATE, prizeListReportTemplate);

                int gradeId = prizeListReportTemplate.getGrade().getGradeId();

                if (gradeId != 0) {
                    modelAndView.addObject(GRADE_SUBJECT_LIST, SortUtil.sortGradeSubjectList(commonService
                            .getGradeSubjectList(gradeId)));
                }
                setErrorMessage(modelMap, MARKING_NOT_COMPLETED);
            }
        }

        return modelAndView;
    }

    /**
     * This method is used to process the report data.
     *
     * @param gradeSubjectRanklist list of StudentGradeSubjectRankView objects
     * @param map to hold data to show in report
     * @return list of ClassWiseStudentReportData objects
     * @throws AkuraAppException the akura app exception
     */
    private List<StudentMarksReportData> processSubjectPrizeReportData(
            List<StudentGradeSubjectRankView> gradeSubjectRanklist, Map<String, Object> map) throws AkuraAppException {

        List<StudentMarksReportData> reportDataList = new ArrayList<StudentMarksReportData>();

        StudentMarksReportData studentMarksReportData = null;

        for (StudentGradeSubjectRankView studentGradeSubjectRankView : gradeSubjectRanklist) {
            studentMarksReportData = new StudentMarksReportData();
            studentMarksReportData.setAdmissionNo(studentGradeSubjectRankView.getAdmissionNo());
            studentMarksReportData.setName(studentGradeSubjectRankView.getNameWithInitials());

            List<String> marks = StaticDataUtil.getListWithEmptyValues(MAXIMUM_NO_OF_COLUMNS);
            List<String> termLabels = StaticDataUtil.getListWithEmptyValues(MAXIMUM_NO_OF_TERMS);
            marks.set(AVERAGE_INDEX, StaticDataUtil.setNoOfDigitsInNumber(2, studentGradeSubjectRankView.getAverage()));
            marks.set(RANK_INDEX, String.valueOf(studentGradeSubjectRankView.getRank()));
            marks.set(TOTAL_INDEX, String.valueOf(studentGradeSubjectRankView.getTotal()));

            List<StudentTermMarkDTO> list =
                    studentService.findTermMarksByClassInfoIdAndGradeSubject(studentGradeSubjectRankView
                            .getStudentClassInfoId(), studentGradeSubjectRankView.getGradeSubjectId());
            for (int i = 0; i < MAXIMUM_NO_OF_TERMS; i++) {

                if (list.size() > i) {
                    termLabels.set(i, TERM + (i + 1));

                    if (list.get(i).isAbsent()) {

                        marks.set(i, ABSENT_STRING);
                    }
                    switch(list.get(i).getTermId()){
                    case 1:
                    		 marks.set(0, String.valueOf(list.get(i).getMarks()));
                    		 break;
                    case 2:
               		 		 marks.set(1, String.valueOf(list.get(i).getMarks()));
               		 		 break;
                    case 3:
          		 		 	marks.set(2, String.valueOf(list.get(i).getMarks()));
          		 		 	break;
                    case 4:
          		 		 	marks.set(3, String.valueOf(list.get(i).getMarks()));
          		 		 	break;
                    case 5:
          		 		 	marks.set(4, String.valueOf(list.get(i).getMarks()));
          		 		 	break;
          		 	default:
          		 			break;
                    }
                    
                }
            }
            map.put(TERM_LABEL, termLabels);
            studentMarksReportData.setMarks(marks);
            reportDataList.add(studentMarksReportData);
        }
       
        return reportDataList;
    }

    /**
     * This method is used to process the report data.
     *
     * @param annualGradeRankList list of StudentAnnualGradeRank objects
     * @param map to hold data to show in report
     * @return list of ClassWiseStudentReportData objects
     * @throws AkuraAppException the akura app exception
     */
    private List<StudentMarksReportData> processTotalPrizeReportData(List<StudentAnnualGradeRank> annualGradeRankList,
            Map<String, Object> map) throws AkuraAppException {

        List<StudentMarksReportData> reportDataList = new ArrayList<StudentMarksReportData>();

        StudentMarksReportData studentMarksReportData = null;

        for (StudentAnnualGradeRank studentAnnualGradeRank : annualGradeRankList) {
            studentMarksReportData = new StudentMarksReportData();
            studentMarksReportData.setAdmissionNo(studentAnnualGradeRank.getAdmissionNo());
            studentMarksReportData.setName(studentAnnualGradeRank.getNameWithInitials());

            List<String> marks = StaticDataUtil.getListWithEmptyValues(MAXIMUM_NO_OF_COLUMNS);
            List<String> termLabels = StaticDataUtil.getListWithEmptyValues(MAXIMUM_NO_OF_COLUMNS);
            marks.set(AVERAGE_INDEX, StaticDataUtil.setNoOfDigitsInNumber(2, studentAnnualGradeRank.getAverage()));
            marks.set(RANK_INDEX, String.valueOf(studentAnnualGradeRank.getRank()));
            marks.set(TOTAL_INDEX, String.valueOf(studentAnnualGradeRank.getTotal()));

            List<StudentsGradeRankView> list =
                    studentService.findTermWiseStudentTotalMarks(studentAnnualGradeRank.getStudentClassInfoId());

            for (int i = 0; i < MAXIMUM_NO_OF_TERMS; i++) {

                if (list.size() > i) {
                    termLabels.set(i, TERM + (i + 1));
                   // marks.set(i, String.valueOf(list.get(i).getTotal()));
                   
                    switch (list.get(i).getId().getTermId()) {
					case 1:
						marks.set(0, String.valueOf(list.get(i).getTotal()));
						break;
					case 2:
						marks.set(1, String.valueOf(list.get(i).getTotal()));

						break;
					case 3:
						marks.set(2, String.valueOf(list.get(i).getTotal()));

						break;
					case 4:
						marks.set(3, String.valueOf(list.get(i).getTotal()));
						break;
					case 5:
						marks.set(4, String.valueOf(list.get(i).getTotal()));

						break;
					default:
						break;
					}

                }
            }
            map.put(TERM_LABEL, termLabels);
            studentMarksReportData.setMarks(marks);
            reportDataList.add(studentMarksReportData);
        }
        return reportDataList;
    }

    /**
     * This method used to populate report required data.
     *
     * @param map to hold data to show in report
     * @param prizeListReportTemplate command object of relevant page
     * @param gradeSubject - gradeSubject object.
     * @throws AkuraAppException throws when exception occurs
     */
    private void populateGeneralReportDetails(Map<String, Object> map, PrizeListReportTemplate prizeListReportTemplate,
            GradeSubject gradeSubject) throws AkuraAppException {

        Grade grade = commonService.findGradeById(prizeListReportTemplate.getGrade().getGradeId());

        map.put(LOGO, ReportUtil.getReportLogo());
        map.put(ADMISSION_NO, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, PRIZE_LIST_ADMISSION_NO));
        map.put(NAME_WITH_INITIALS, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, PRIZE_LIST_NAME));

        map.put(GRADE_LABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, PRIZE_LIST_GRADE_LABEL));

        map.put(TOTAL_LABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, PRIZE_LIST_TOTAL));
        map.put(AVERAGE_LABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, PRIZE_LIST_AVERAGE));

        map.put(RANK_LABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, PRIZE_LIST_RANK));

        map.put(GRADE, grade.getDescription());
        map.put(YEAR_LABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, PRIZE_LIST_YEAR));
        map.put(REPORT_GENERATED_ON, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_REPORT_GENERATED_ON));

        map.put(TITLE, ((prizeListReportTemplate.getGradeSubject().getGradeSubjectId() == 0) ? "" : gradeSubject
                .getSubject().getDescription()
                + SPACE)
                + PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, PRIZE_LIST_TITLE));
        map.put(YEAR_VALUE, prizeListReportTemplate.getYear());

        map.put(PAGE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_PAGE));
        map.put(GPL_COMMENT_TEMPL, AkuraWebConstant.REPORT_GPL);
        map.put(STYLE_TEMPLATE, PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                + PropertyReader.getPropertyValue(SYSTEM_CONFIG, STYLE_PATH));
        map.put(GENERATED_DATE, DateUtil.getReportGeneratedDate(PropertyReader.getPropertyValue(
                ReportUtil.REPORT_TEMPLATE, REPORT_GENERATED_DATE_LOCALE)));
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
     * Return list of no of prizes.
     *
     * @return list
     */
    @ModelAttribute(NO_OF_PRIZES_LIST)
    public List<Integer> populatePrizesNoList() {

        List<Integer> noOfPrizes = new ArrayList<Integer>();

        for (int i = AkuraWebConstant.MINIMUM_NO_OF_PRIZES; i <= AkuraWebConstant.MAXIMUM_NO_OF_PRIZES; i =
                i + AkuraWebConstant.MINIMUM_NO_OF_PRIZES) {
            noOfPrizes.add(i);
        }
        return noOfPrizes;
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

    /**
     * This method to generate report for students attendance for special event.
     *
     * @param response of type HttpServletResponse
     * @param jrBeanDataSource of type JRBeanCollectionDataSource
     * @param jrxml of type java.lang.String
     * @param map of type HashMap
     * @throws AkuraException throws when exception occur
     */
    private void displayReportForm(HttpServletResponse response, JRBeanCollectionDataSource jrBeanDataSource,
            Map<String, Object> map, String jrxml) throws AkuraException {

        ReportUtil.displayReportInPdfForm(response, jrBeanDataSource, map, jrxml);

    }
}
