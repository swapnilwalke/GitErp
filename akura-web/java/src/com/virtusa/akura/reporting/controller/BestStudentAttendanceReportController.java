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

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.virtusa.akura.api.dto.BestStudentAttendanceTemplate;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.Holiday;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.api.exception.NotCoverMinimumAttendanceException;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.reporting.service.AttendanceReportingService;
import com.virtusa.akura.reporting.validator.GradeWiseBestStudentAttendanceValidator;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.HolidayUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * This class is for generating the grade wise best student attendance report, according to user given input,
 * to be viewed grade wise or class wise or both, and year or monthly wise.
 */

@Controller
public class BestStudentAttendanceReportController {

    /** report key value for holding academic days. */
    private static final String REPORT_ACDAMIC_DAYS = "acdamicDays";

    /** report key value for holding academic days label. */
    private static final String REPORT_ACDAMIC_DAY_LABEL = "acdamicDayLabel";

    /** report key value for holding present days. */
    private static final String REPORT_PRESENT_DAYS_LABEL = "presentDaysLabel";

    /** report key value for holding student names. */
    private static final String REPORT_STUDENTNAME = "studentname";

    /** report key value for holding student id. */
    private static final String REPORT_STUDENTID = "studentid";

    /** report key value for holding class description. */
    private static final String REPORT_CLASS_DESCRIPTION = "classDescription";

    /** report key value for holding year. */
    private static final String REPORT_YEAR2 = "year";

    /** report key value for holding year label. */
    private static final String REPORT_YEAR_LABEL = "yearLabel";

    /** report key value for holding month. */
    private static final String REPORT_MONTH2 = "month";

    /** report key value for holding month label. */
    private static final String REPORT_MONTH_LABEL = "monthLabel";

    /** report key value for holding grade description. */
    private static final String REPORT_GRADE_DESCRIPTION = "gradeDescription";

    /** report key value for holding grade label. */
    private static final String REPORT_GRADE_LABEL = "gradeLabel";

    /** report key value for holding title. */
    private static final String REPORT_TITLE = "title";

    /** report parameter value for holding present days. */
    private static final String PRESENT_DAYS = "BestStudentReport_presentDays";

    /** report parameter value for holding student name. */
    private static final String STUDENT_NAME = "BestStudentReport_studentName";

    /** report parameter value for holding student number. */
    private static final String ADDMISSION_NO = "BestStudentReport_admissionNum";

    /** report parameter value for holding student class. */
    private static final String CLASS = "BestStudentReport_class";

    /** report parameter value for holding year. */
    private static final String YEAR = "BestStudentReport_year";

    /** report parameter value for holding month. */
    private static final String MONTH = "BestStudentReport_month";

    /** report parameter value for holding grade. */
    private static final String GRADE = "BestStudentReport_grade";

    /** report parameter value for holding title of the report. */
    private static final String TITLE_OF_REPORT = "BestStudentReport_title";

    /** report parameter value for holding academic days. */
    private static final String ACADEMIC_DAYS = "BestStudentReport_academicDays";

    /** string for holding working days error message. */
    private static final String WORKING_DAYS_ERROR = "BESTSTUDENT.REPORT.WORKING.DAYS.ERROR";

    /** string for holding jrxml file. */
    private static final String REPORT_JRXML = "GradeWiseBestStudentAttendance";

    /** request map attribute for holding client request best student attendance. */
    private static final String REQ_MAP_BEST_STUDENT_VALUE = "/bestStudentAttendanceReport.htm";

    /** request map attribute for holding client request year month. */
    private static final String REQ_MAP_YEARMONTH_VALUE = "/populateYearMonthList.htm";

    /** request map attribute for holding client request grade class. */
    private static final String REQ_MAP_GRADECLASS_VALUE = "/populateGradeClassListById.htm";

    /** request attribute for holding selected value. */
    private static final String REQ_SELECTED_VALUE = "selectedValue";

    /** view for grade wise best student attendance report. */
    private static final String REPORTING_GRADE_WISE_BEST_STUDENT_ATTENDANCE =
            "reporting/gradeWiseBestStudentAttendance";

    /** model attribute for holding command object. */
    private static final String BEST_STUDENT_ATTENDANCE_TEMPLATE = "bestStudentAttendanceTemplate";

    /** request attribute for holding select class value. */
    private static final String REQ_SELECT_CLASSES = "selectClasses";

    /** request attribute for holding month value. */
    private static final String REQ_SELECT_MONTHS = "selectMonths";

    /** model attribute for holding error message. */
    private static final String MODEL_ATT_MESSAGE = "message";

    /** model attribute for holding selected month. */
    private static final String MODEL_ATT_SELECTED_MONTH = "selectedMonth";

    /** model attribute for holding selected year. */
    private static final String MODEL_ATT_SELECTED_YEAR = "selectedYear";

    /** model attribute for holding year map. */
    private static final String MODEL_ATT_YEAR = "yearList";

    /** model attribute for holding grade map. */
    private static final String MODEL_ATT_GRADE = "gradeList";

    /** model attribute for holding selected class. */
    private static final String MODEL_ATT_SELECTED_CLASS_ID = "selectedClassId";

    /** model attribute for holding selected grade. */
    private static final String MODEL_ATT_SELECTED_GRADE_ID = "selectedGradeId";

    /** attribute for holding error message key. */
    public static final String NO_DATA_ERROR_MESSAGE = "REPORT.UI.NO.DATA";

    /** variable for holding string "reportGeneratedOn". */
    private static final String REPORT_GENERATED_ON = "reportGeneratedOn";

    /** variable for holding string "ExamAbsentClassWise_reportGeneratedOn". */
    private static final String CLASS_WISE_REPORT_GENERATED_ON_KEY = "ExamAbsentClassWise_reportGeneratedOn";

    /** model attribute for report generated date. */
    private static final String REPORT_GENERATED_DATE_LOCALE = "REPORT.GENERATED.DATE.LOCALE";

    /** The Constant REPORT_PAGE. */
    private static final String REPORT_PAGE = "Report_page";

    /** The Constant PAGE. */
    private static final String PAGE = "page";

    /** The Constant GPL. */
    private static final String GPL = "GPLComment";

    /** variable for holding string "generatedDate". */
    private static final String GENERATED_DATE = "generatedDate";

    /** key to hold string of year end date. */
    private static final String END_DATE = "-12-31";

    /** key to hold string of year start date. */
    private static final String START_DATE = "-01-01";

    /** Constant for index value. */
    private static final int INDEX_TOWEL = 12;

    /** Constant for index value. */
    private static final int INDEX_ELEVAN = 11;

    /** Constant for index value. */
    private static final int INDEX_TEN = 10;

    /** Constant for index value. */
    private static final int INDEX_NINE = 9;

    /** Constant for index value. */
    private static final int INDEX_EIGHT = 8;

    /** Constant for index value. */
    private static final int INDEX_SEVEN = 7;

    /** Constant for index value. */
    private static final int INDEX_SIX = 6;

    /** Constant for index value. */
    private static final int INDEX_FIVE = 5;

    /** Constant for index value. */
    private static final int INDEX_FOUR = 4;

    /** Constant for index value. */
    private static final int INDEX_THREE = 3;

    /** CommonService attribute for holding commonService. */
    private CommonService commonService;

    /** StudentService attribute for holding studentService. */
    private StudentService studentService;

    /**
     * attendanceReportingService of type AttendanceReportingService to use methods related to attendance
     * Reporting .
     */
    private AttendanceReportingService attendanceReportingService;

    /**
     * getter for StudentService.
     *
     * @return studentService.
     */
    public StudentService getStudentService() {

        return studentService;
    }

    /**
     * setter for StudentService.
     *
     * @param stuService - studentService
     */
    public void setStudentService(StudentService stuService) {

        this.studentService = stuService;
    }

    /** gradeWiseBestStudentAttendanceValidator attribute for holding GradeWiseBestStudentAttendanceValidator. */
    private GradeWiseBestStudentAttendanceValidator gradeWiseBestStudentAttendanceValidator;

    /**
     * Sets the gradeWiseBestStudentAttendanceValidator object.
     *
     * @param refGradeWiseBestStudentAttendanceValidator - validate object of
     *        GradeWiseBestStudentAttendanceValidator.
     */
    public void setGradeWiseBestStudentAttendanceValidator(
            GradeWiseBestStudentAttendanceValidator refGradeWiseBestStudentAttendanceValidator) {

        this.gradeWiseBestStudentAttendanceValidator = refGradeWiseBestStudentAttendanceValidator;
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
     * Set AttendanceReportingService.
     *
     * @param attendanceReportingServiceRef of type AttendanceReportingService
     */

    public void setAttendanceReportingService(AttendanceReportingService attendanceReportingServiceRef) {

        this.attendanceReportingService = attendanceReportingServiceRef;
    }

    /**
     * handle GET requests for grade wise student attendance view.
     *
     * @param model - ModelMap
     * @throws AkuraAppException AkuraAppException
     * @return the name of the view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showReportForm(final ModelMap model) throws AkuraAppException {

        BestStudentAttendanceTemplate bestStudentAttendanceTemplate = new BestStudentAttendanceTemplate();
        model.addAttribute(BEST_STUDENT_ATTENDANCE_TEMPLATE, bestStudentAttendanceTemplate);
        return REPORTING_GRADE_WISE_BEST_STUDENT_ATTENDANCE;
    }

    /**
     * Returns a list of Grades.
     *
     * @return grade List - a list of grades.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @ModelAttribute(MODEL_ATT_GRADE)
    public List<Grade> populateGradeList() throws AkuraAppException {

        return SortUtil.sortGradeList(commonService.getGradeList());
    }

    /**
     * @return the year List.
     * @throws AkuraAppException AkuraAppException
     */
    @ModelAttribute(MODEL_ATT_YEAR)
    public List<String> populateYearList() throws AkuraAppException {

        // get only current year, and previous year only
        return DateUtil.getPastYears(2);
    }

    /**
     * Method is to return GradeClass list.
     *
     * @param request - HttpServletRequest
     * @param modelMap - ModelMap attribute.
     * @return list of classGrade
     * @throws AkuraAppException - Detailed exception
     */

    @RequestMapping(value = REQ_MAP_GRADECLASS_VALUE)
    @ResponseBody
    public String populateGradeClass(ModelMap modelMap, HttpServletRequest request) throws AkuraAppException {

        StringBuilder allGradeClass = new StringBuilder();

        // get the selected grade id from UI
        int gradeId = Integer.parseInt(request.getParameter(REQ_SELECTED_VALUE));

        // find grade object by grade id
        Grade grade = commonService.findGradeById(gradeId);

        // get all the classes under that grade
        List<ClassGrade> classGrades = SortUtil.sortClassGradeList(commonService.getClassGradeListByGrade(grade));
        boolean isFirst = true;
        StringBuilder classes = new StringBuilder();

        // get classes
        for (ClassGrade classGrade : classGrades) {
            classes.append(classGrade.getDescription());
            classes.append(AkuraWebConstant.UNDERSCORE_STRING);
            classes.append(classGrade.getClassGradeId());

            if (isFirst) {
                allGradeClass.append(classes.toString()); // append with no comma
                isFirst = false;
            } else {
                allGradeClass.append(AkuraWebConstant.STRING_COMMA); // append with comma
                allGradeClass.append(classes.toString());
            }
            classes.delete(0, classes.length());
        }
        // return all grades by appending class name with grade name , and with comma seperate grade id
        return allGradeClass.toString();
    }

    /**
     * Method is to return YearMonths list.
     *
     * @param request - HttpServletRequest
     * @param modelMap - ModelMap attribute.
     * @return list of classGrade
     * @throws AkuraAppException - Detailed exception
     */

    @RequestMapping(value = REQ_MAP_YEARMONTH_VALUE)
    @ResponseBody
    public String populateYearMonths(ModelMap modelMap, HttpServletRequest request) throws AkuraAppException {

        // get selected year from UI
        int year = Integer.parseInt(request.getParameter(REQ_SELECTED_VALUE));

        // call method to be get all the months accoding to selected year & return months to UI
        return DateUtil.getMonthsBySelectedYear(year, getMonthNames());

    }

    /**
     * Method is to generate the report.
     *
     * @param request - HttpServletRequest
     * @param map - ModelMap attribute.
     * @param response - response of HttpServletResponse
     * @param errors - BindingResult for validate.
     * @param bestAttendanceTemplate - object of BestStudentAttendanceTemplateset to all values to this
     *        template
     * @return report as PDF Object,on JSP
     * @throws AkuraAppException - Detailed exception
     */
    @RequestMapping(value = REQ_MAP_BEST_STUDENT_VALUE, method = RequestMethod.POST)
    public String generateReport(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute(BEST_STUDENT_ATTENDANCE_TEMPLATE) BestStudentAttendanceTemplate bestAttendanceTemplate,
            BindingResult errors, ModelMap map) throws AkuraAppException {

        String viewPage = REPORTING_GRADE_WISE_BEST_STUDENT_ATTENDANCE;

        // get selected month id from UI
        String selectedMonth = request.getParameter(REQ_SELECT_MONTHS);

        // get grade id from path - command name
        int gradeId = Integer.parseInt(bestAttendanceTemplate.getGradeDescription());

        // get year id from path - command name
        int year = bestAttendanceTemplate.getYear();

        int monthId = 0;
        int classGradeId = 0;

        if (selectedMonth != null) {
            monthId = Integer.parseInt(selectedMonth);
        }

        setValueToTemplateBeforeValidate(bestAttendanceTemplate, monthId);

        // get class grade if, the class is selected from UI
        if ((request.getParameter(REQ_SELECT_CLASSES) != null)
                && (!request.getParameter(REQ_SELECT_CLASSES).equals(AkuraWebConstant.STRING_ZERO))) {
            classGradeId = Integer.parseInt(request.getParameter(REQ_SELECT_CLASSES));
        }

        // validate if not selected mandatory fields in UI
        gradeWiseBestStudentAttendanceValidator.validate(bestAttendanceTemplate, errors);

        if (errors.hasErrors()) {

            // if throws any validation messages to UI, then keep already selected values in UI
            setSelectedValues(map, gradeId, classGradeId, year, selectedMonth);
        } else {
            String gradeDescription = AkuraWebConstant.EMPTY_STRING;
            ClassGrade classGradeObj = commonService.findClassGrade(classGradeId);

            gradeDescription = setGradeDescription(gradeId, classGradeObj);

            int workingDays = 0;
            Date fDate = null;
            Date tDate = null;
            Calendar fromDate = Calendar.getInstance();
            Calendar toDate = Calendar.getInstance();

            // get from date & last date of year wise
            if (monthId == 0) {

                // get first day of January - 01
                fDate = DateUtil.getFistDayOfSelectedYearMonth(year, 1);
                // get last day of December - 31
                tDate = DateUtil.getLastDayOfSelectedYearMonth(year, INDEX_TOWEL);
            } else {

                // get first day of selected month
                fDate = DateUtil.getFistDayOfSelectedYearMonth(year, monthId);
                // get the last day of selected month
                tDate = DateUtil.getLastDayOfSelectedYearMonth(year, monthId);
            }

            toDate.setTime(tDate);
            fromDate.setTime(fDate);

            // get holiday list
            List<Holiday> holidayList = getHolidayList(fDate, tDate);

            // get number of working days
            workingDays = HolidayUtil.countWorkingDays(fromDate, toDate, holidayList);

            // check at least 10 school days for selected month
            if (workingDays < INDEX_TEN) {
                setLessWorkingDaysErrorMessage(map, selectedMonth, gradeId, year, classGradeId);
            } else {

                // get month name ,ex:- January, February... by giving month id
                String monthDescription = setMonthNameBySelectedYear(monthId);

                bestAttendanceTemplate.setAcademicDays(workingDays);

                // set report parameters for the report
                Map<String, Object> params =
                        setReportParameters(bestAttendanceTemplate, gradeDescription, monthDescription);

                if (monthId == 0) {
                    // this method is to be moved working days label & value ,if not having selected month
                    moveReportParameters(workingDays, params);
                }

                int classId = 0;
                if (classGradeObj != null) {
                    classId = classGradeObj.getSchoolClass().getClassId();
                }

                // set all ids to the best attendance template
                setDataToBestAttendanceTemplate(bestAttendanceTemplate, gradeId, year, monthId, classId);

                // pass that template(bind with all ids), to SP call
                JRBeanCollectionDataSource gradeWiseBestStudentAttendanceList =
                        attendanceReportingService.getBestStudentAttendance(bestAttendanceTemplate);

                List<Object> array = getGradeWiseBestStudentAttendanceList(gradeWiseBestStudentAttendanceList);

                // if array list is not null
                if (array != null) {

                    try {

                        List<BestStudentAttendanceTemplate> reportTempList =
                                new ArrayList<BestStudentAttendanceTemplate>();

                        // set data to report to be checked the condition
                        attendanceReportingService.setDataToReportTemplate(gradeDescription, classGradeObj,
                                workingDays, array, reportTempList);

                        // pass that report data template to the JRBeanCollectionDataSource
                        JRBeanCollectionDataSource jRBeanDataSource = new JRBeanCollectionDataSource(reportTempList);

                        // if that Jasper Data source is not empty
                        if (jRBeanDataSource.getRecordCount() != 0) {

                            // call report functions , in order to generate the report
                            ReportUtil.displayReportInPdfForm(response, jRBeanDataSource, params, REPORT_JRXML);
                        }

                    } catch (NotCoverMinimumAttendanceException e) {

                        String message = e.getErrorCode();

                        // if Jasper Data source is empty, then display error for not cover minimum
                        // attendance
                        setErrorForNotCoverMinimumAttendance(map, selectedMonth, gradeId, year, classGradeId, message);
                    }

                } else {
                    setErrorMessageForNoDataFound(map, selectedMonth, gradeId, year, classGradeId);
                }
            }
        }
        return viewPage;
    }

    /**
     * Method is to set error message for if found no data in the relavent view - table.
     *
     * @param map - model map.
     * @param selectedMonth -selectedMonth.
     * @param gradeId - gradeId.
     * @param year - year.
     * @param classGradeId - classGradeId.
     */
    private void setErrorMessageForNoDataFound(ModelMap map, String selectedMonth, int gradeId, int year,
            int classGradeId) {

        // if array list is not empty , then display no data found error message.
        String message = new ErrorMsgLoader().getErrorMessage(NO_DATA_ERROR_MESSAGE);
        setErrorMessage(map, gradeId, classGradeId, year, selectedMonth, message);
    }

    /**
     * Method is to set grade description.
     *
     * @param classGradeObj - class grade object.
     * @param gradeId -gradeId.
     * @return gradeDescription - gradeDescription.
     * @throws AkuraAppException - Detailed exception
     */
    private String setGradeDescription(int gradeId, ClassGrade classGradeObj) throws AkuraAppException {

        String gradeDescription;
        // if the grade class is selected
        if (classGradeObj != null) {
            gradeDescription = classGradeObj.getGrade().getDescription();
        } else {
            gradeDescription = commonService.findGradeById(gradeId).getDescription();
        }
        return gradeDescription;
    }

    /**
     * Method is to set less (less than 10) working days error message.
     *
     * @param map - model map.
     * @param selectedMonth -selectedMonth.
     * @param gradeId -gradeId.
     * @param year - year.
     * @param classGradeId - classGradeId.
     */
    private void setLessWorkingDaysErrorMessage(ModelMap map, String selectedMonth, int gradeId, int year,
            int classGradeId) {

        String message = new ErrorMsgLoader().getErrorMessage(WORKING_DAYS_ERROR);
        setErrorMessage(map, gradeId, classGradeId, year, selectedMonth, message);
    }

    /**
     * Method is to set values for best student attendance report template before call validate function..
     *
     * @param bestAttendanceTemplate - bestAttendanceTemplate.
     * @param monthId - monthId.
     * @throws AkuraAppException - Detailed exception
     */
    private void setValueToTemplateBeforeValidate(BestStudentAttendanceTemplate bestAttendanceTemplate, int monthId)
            throws AkuraAppException {

        // set the month and year to the attendance template , before validate
        bestAttendanceTemplate.setMonthId(monthId);
        bestAttendanceTemplate.setCurrentYear(DateUtil.currentYearOnly());
    }

    /**
     * Method is to get all array object list.
     *
     * @param gradeWiseBestStudentAttendanceList - gradeWiseBestStudentAttendanceList.
     * @return array - array object list
     */
    @SuppressWarnings("unchecked")
    private List<Object> getGradeWiseBestStudentAttendanceList(
            JRBeanCollectionDataSource gradeWiseBestStudentAttendanceList) {

        // get all data list from JRBeanCollectionDataSource
        List<Object> array = null;
        if (gradeWiseBestStudentAttendanceList.getRecordCount() != 0) {
            array = (List<Object>) gradeWiseBestStudentAttendanceList.getData();
        }

        // return array object list
        return array;
    }

    /**
     * Method is to set error message for if not found at least minimum attendance in the relavent view -
     * table.
     *
     * @param map - model map.
     * @param selectedMonth -selectedMonth.
     * @param gradeId - gradeId.
     * @param year - year.
     * @param classGradeId - classGradeId.
     * @param errorMessage - error message.
     */
    private void setErrorForNotCoverMinimumAttendance(ModelMap map, String selectedMonth, int gradeId, int year,
            int classGradeId, String errorMessage) {

        setErrorMessage(map, gradeId, classGradeId, year, selectedMonth, errorMessage);
    }

    /**
     * Method is to set date to template into order to bind and send for SP call.
     *
     * @param bestAttendanceTemplate - bestAttendanceTemplate.
     * @param gradeId - gradeId .
     * @param year - year.
     * @param monthId - monthId.
     * @param classId - classId.
     */
    private void setDataToBestAttendanceTemplate(BestStudentAttendanceTemplate bestAttendanceTemplate, int gradeId,
            int year, int monthId, int classId) {

        bestAttendanceTemplate.setGradeId(gradeId);
        bestAttendanceTemplate.setClassId(classId);
        bestAttendanceTemplate.setYear(year);
        bestAttendanceTemplate.setMonthId(monthId);
    }

    /**
     * Method is to set report parameters , which are replace by academic days, instead of month.
     *
     * @param workingDays - workingDays .
     * @param params - parameters of report.
     */
    private void moveReportParameters(int workingDays, Map<String, Object> params) {

        // if month label is not display ,then replace month label by academic days label
        params.put(REPORT_MONTH_LABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, ACADEMIC_DAYS));
        // then month value is not display ,then replace month value by academic days value
        params.put(REPORT_MONTH2, String.valueOf(workingDays));
        // set non usable labels and values as empty
        params.put(REPORT_ACDAMIC_DAY_LABEL, AkuraWebConstant.EMPTY_STRING);
        params.put(REPORT_ACDAMIC_DAYS, AkuraWebConstant.EMPTY_STRING);
    }

    /**
     * Method is to return month description by given month id.
     *
     * @param monthId - monthId .
     * @return list of classGrade
     */
    private String setMonthNameBySelectedYear(int monthId) {

        String monthDescription = AkuraWebConstant.EMPTY_STRING;
        Map<Integer, String> yearMap = getMonthNames();

        for (Map.Entry<Integer, String> tempEntry : yearMap.entrySet()) {

            if (tempEntry.getKey() == monthId) {
                monthDescription = tempEntry.getValue();
                break;
            }
        }
        return monthDescription;
    }

    /**
     * Method is to return month description by given month id.
     *
     * @param bestAttendanceTemplate - bestAttendanceTemplate of the template for report parameters.
     * @param gradeDescription - gradeDescription.
     * @param monthDescription - monthDescription.
     * @return list of map for report parameters.
     */
    private Map<String, Object> setReportParameters(BestStudentAttendanceTemplate bestAttendanceTemplate,
            String gradeDescription, String monthDescription) {

        Map<String, Object> params = new HashMap<String, Object>();

        params.put(REPORT_TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, TITLE_OF_REPORT));
        params.put(REPORT_GRADE_LABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, GRADE));
        params.put(REPORT_GRADE_DESCRIPTION, gradeDescription);
        params.put(REPORT_MONTH_LABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, MONTH));
        params.put(REPORT_MONTH2, monthDescription);
        params.put(REPORT_YEAR_LABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, YEAR));
        params.put(REPORT_YEAR2, String.valueOf(bestAttendanceTemplate.getYear()));
        params.put(REPORT_CLASS_DESCRIPTION, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, CLASS));
        params.put(REPORT_STUDENTID, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, ADDMISSION_NO));
        params.put(REPORT_STUDENTNAME, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STUDENT_NAME));
        params
                .put(REPORT_PRESENT_DAYS_LABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                        PRESENT_DAYS));
        params
                .put(REPORT_ACDAMIC_DAY_LABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                        ACADEMIC_DAYS));
        params.put(REPORT_ACDAMIC_DAYS, String.valueOf(bestAttendanceTemplate.getAcademicDays()));

        params.put(AkuraWebConstant.REPORT_PARA_LOGO_PATH, ReportUtil.getReportLogo());
        params.put(AkuraWebConstant.STYLE_TEMPLATE, PropertyReader.getPropertyValue(AkuraWebConstant.SYSTEM_CONFIG,
                AkuraWebConstant.APPSERVER_HOME)
                + PropertyReader.getPropertyValue(AkuraWebConstant.SYSTEM_CONFIG, AkuraWebConstant.STYLE_PATH));
        params.put(REPORT_GENERATED_ON, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                CLASS_WISE_REPORT_GENERATED_ON_KEY));
        params.put(GENERATED_DATE, DateUtil.getReportGeneratedDate(PropertyReader.getPropertyValue(
                ReportUtil.REPORT_TEMPLATE, REPORT_GENERATED_DATE_LOCALE)));
        params.put(PAGE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_PAGE));
        params.put(GPL, AkuraWebConstant.REPORT_GPL);
        return params;
    }

    /**
     * this method is for getting all the month names.
     *
     * @return Map of month names.
     */
    private Map<Integer, String> getMonthNames() {

        Map<Integer, String> yearMap = new TreeMap<Integer, String>();

        String[] months = new DateFormatSymbols().getMonths();

        int indexZero = 0;
        int indexOne = 1;
        int indexTwo = 2;
        int indexThree = INDEX_THREE;
        int indexFour = INDEX_FOUR;
        int indexFive = INDEX_FIVE;

        yearMap.put(1, months[indexZero]);
        yearMap.put(2, months[indexOne]);
        yearMap.put(INDEX_THREE, months[indexTwo]);
        yearMap.put(INDEX_FOUR, months[indexThree]);
        yearMap.put(INDEX_FIVE, months[indexFour]);
        yearMap.put(INDEX_SIX, months[indexFive]);
        yearMap.put(INDEX_SEVEN, months[INDEX_SIX]);
        yearMap.put(INDEX_EIGHT, months[INDEX_SEVEN]);
        yearMap.put(INDEX_NINE, months[INDEX_EIGHT]);
        yearMap.put(INDEX_TEN, months[INDEX_NINE]);
        yearMap.put(INDEX_ELEVAN, months[INDEX_TEN]);
        yearMap.put(INDEX_TOWEL, months[INDEX_ELEVAN]);

        return yearMap;
    }

    /**
     * set the error messages when returning to the jsp.
     *
     * @param gradeId - grade ID.
     * @param classGradeId - classGradeId.
     * @param year - year.
     * @param month - month.
     * @param errorMessage - error message.
     * @param map - ModelMap
     */
    private void setErrorMessage(ModelMap map, int gradeId, int classGradeId, int year, String month,
            String errorMessage) {

        setSelectedValues(map, gradeId, classGradeId, year, month);
        map.addAttribute(MODEL_ATT_MESSAGE, errorMessage);
    }

    /**
     * set the messages for model attribute.
     *
     * @param gradeId - grade ID.
     * @param classGradeId - classGradeId.
     * @param year - year.
     * @param month - month.
     * @param map - ModelMap
     */
    private void setSelectedValues(ModelMap map, int gradeId, int classGradeId, int year, String month) {

        map.addAttribute(MODEL_ATT_SELECTED_GRADE_ID, gradeId);
        map.addAttribute(MODEL_ATT_SELECTED_CLASS_ID, classGradeId);
        map.addAttribute(MODEL_ATT_SELECTED_YEAR, year);
        map.addAttribute(MODEL_ATT_SELECTED_MONTH, month);
    }

    /**
     * get number of working days.
     *
     * @param startDate - startDate.
     * @param endDate - endDate.
     * @return holidayList - holidayList list of holiday.
     * @throws AkuraAppException - The exception details that occurred when re join temporary leave student
     *         instance.
     */
    private List<Holiday> getHolidayList(Date startDate, Date endDate) throws AkuraAppException {

        String strYr = DateUtil.getStringYear(startDate);

        String strStartDate = strYr + START_DATE;
        String strEndDate = strYr + END_DATE;

        Date startDateToSearch = DateUtil.getParseDate(strStartDate);
        Date endDateToSearch = DateUtil.getParseDate(strEndDate);

        return commonService.findHolidayByYear(startDateToSearch, endDateToSearch);
    }
}
