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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.DailyStudentAttendance;
import com.virtusa.akura.api.dto.Holiday;
import com.virtusa.akura.api.dto.PerDayAttendanceTemplate;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.attendance.service.DailyAttendanceService;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.reporting.validator.PerDayClassWiseAttendanceValidator;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.HolidayUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * Controller to generate Per day class wise Absent list report.
 *
 * @author Virtusa Corporation
 */
@Controller
@SessionAttributes("perDayAttendanceTemplate")
public class PerDayClassWiseAttendanceController {

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(PerDayClassWiseAttendanceController.class);

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

    /** model attribute gradeClassList. */
    public static final String GRADE_CLASS_LIST = "gradeClassList";

    /** attribute for holding perDayAttendanceTemplate. */
    public static final String PER_DAY_ATTENDANCE_TEMPLATE = "perDayAttendanceTemplate";

    /** attribute for holding view name reporting/perDayClassWiseAttendance. */
    public static final String REPORTING_PERDAY_CLASS_WISE_ATTENDANCE = "reporting/perDayClassWiseAttendance";

    /** attribute for holding url /AbsentClassWiseReport.htm. */
    public static final String ABSENT_CLASSWISE_REPORT = "/AbsentClassWiseReport.htm";

    /** attribute for holding error message key. */
    public static final String NO_DATA_ERROR_MESSAGE = "REF.UI.NO.DATA";

    /** attribute for holding error message key. */
    private static final String REPORTING_UI_HOLIDAY = "REPORTING.UI.HOLIDAY";

    /** attribute for holding message. */
    public static final String MESSAGE = "message";

    /** attribute for holding hyphen. */
    public static final String HIPAN = "-";

    /** attribute for holding StudentClassWiseAttendanceReport. */
    public static final String STUDENT_CLASS_WISE_ATTENDANCE_REPORT = "StudentClassWiseAttendanceReport";

    /** attribute for holding ClassWiseAttendanceReport. */
    public static final String CLASS_WISE_REPORT = "ClassWiseAttendanceReport";

    /** attribute for holding sub title. */
    public static final String SUB_TITLE = "subTitle";

    /** attribute for holding sub title. */
    public static final String ATTENDANCE = "attendance";

    /** attribute for holding student present list. */
    public static final String PRESENT_LIST = "StudentPerDayClassWiseReport_present";

    /** attribute for holding all student. */
    public static final String ALL_LIST = "StudentPerDayClassWiseReport_allPresent";

    /** attribute for holding student absent list. */
    public static final String ABSENT_LIST = "StudentPerDayClassWiseReport_absent";

    /** attribute for holding string. */
    private static final String STRING_EMPTY = "";

    /** attribute for holding string. */
    private static final String STRING_COLAN = ":";

    /** attribute for holding string. */
    private static final String COLAN = "colan";

    /** attribute for holding string. */
    private static final String SUMMARY = "summary";

    /** attribute for holding string. */
    private static final String ABSENT = "Absent";

    /** attribute for holding string. */
    private static final String PRESENT = "Present";

    /** attribute for holding string. */
    private static final String STRING_SUMMARY = "StudentPerDayClassWiseReport_summary";

    /** attribute for holding string. */
    private static final String STRING_ATTENDANCE = "StudentPerDayClassWiseReport_atendance";

    /** attribute for holding string. */
    private static final String STUDENT_NAME2 = "studentName";

    /** attribute for holding string. */
    private static final String STUDENT_NAME = "StudentPerDayClassWiseReport_studentName";

    /** attribute for holding string. */
    private static final String ADMISSION_NUMBER = "StudentPerDayClassWiseReport_admissionNo";

    /** attribute for holding string. */
    private static final String ADMISSION_NUM = "admissionNum";

    /** attribute for holding string. */
    private static final String DATE_DESCRIPTION = "dateDescription";

    /** attribute for holding string. */
    private static final String DATE_STRING = "StudentPerDayClassWiseReport_date";

    /** attribute for holding string. */
    private static final String CLASS_NAME = "StudentPerDayClassWiseReport_className";

    /** attribute for holding string. */
    private static final String CLASSNAME = "classname";

    /** attribute for holding string. */
    private static final String ABSENT_COUNT_DES = "AbsentCountDes";

    /** attribute for holding string. */
    private static final String NUMBER_OF_STUDENTS_ABSENT = "StudentPerDayClassWiseReport_noOfStudentsAbsent";

    /** attribute for holding string. */
    private static final String TOTAL_NUMBER_OF_STUDENTS_IN_THE_CLASS =
            "StudentPerDayClassWiseReport_totalNoOfStudentInClass";

    /** attribute for holding string. */
    private static final String TOTAL_STUDENTS_DES = "TotalStudentsDes";

    /** attribute for holding string. */
    private static final String PRESENT_COUNT_DES = "PresentCountDes";

    /** attribute for holding string. */
    private static final String NUMBER_OF_STUDENTS_PRESENT = "StudentPerDayClassWiseReport_noOfPresents";

    /** attribute for holding string. */
    private static final String STUDENT_ATTANDANCE_REPORT = "StudentPerDayClassWiseReport_titile";

    /** attribute for holding string. */
    private static final String TITLE = "title";

    /** attribute for holding string. */
    private static final String DATE = "date";

    /** attribute for holding string. */
    private static final String CLASS_DESCRIPTION = "ClassDescription";

    /** attribute for holding string. */
    private static final String ABSENT_COUNT = "AbsentCount";

    /** attribute for holding string. */
    private static final String TOTAL_STUDENTS = "TotalStudents";

    /** attribute for holding string. */
    private static final String PRESENT_COUNT = "PresentCount";

    /** attribute for holding string. */
    private static final String LOGO_PATH = "logoPath";

    /** attribute for holding string. */
    private static final String DATASOURCE = "datasource";

    /** attribute for holding string. */
    private static final String END_YEAR = "-12-31";

    /** attribute for holding string. */
    private static final String START_YEAR = "-01-01";

    /** attribute for holding string style.path. */
    private static final String STYLE_PATH = "style.path";

    /** attribute for holding string appserver.home. */
    private static final String APPSERVER_HOME = "appserver.home";

    /** attribute for holding string systemConfig. */
    private static final String SYSTEM_CONFIG = "systemConfig";

    /** attribute for holding string styleTemplate. */
    private static final String STYLE_TEMPLATE = "styleTemplate";

    /** perDayClassWiseAttendanceValidator attribute for holding PerDayClassWiseAttendanceValidator. */
    private PerDayClassWiseAttendanceValidator perDayClassWiseAttendanceValidator;

    /**
     * studentService To invoke service methods.
     */
    private StudentService studentService;

    /**
     * dailyAttendanceService To invoke service methods.
     */
    private DailyAttendanceService dailyAttendanceService;

    /**
     * commonService To invoke service methods.
     */
    private CommonService commonService;

    /**
     * Set CommonService.
     *
     * @param commonServiceRef of type CommonService
     */
    public void setCommonService(CommonService commonServiceRef) {

        this.commonService = commonServiceRef;
    }

    /**
     * setter to inject DailyAttendanceService object.
     *
     * @param dailyAttendanceServiceRef the DailyAttendanceService to set
     */

    public void setDailyAttendanceService(DailyAttendanceService dailyAttendanceServiceRef) {

        this.dailyAttendanceService = dailyAttendanceServiceRef;
    }

    /**
     * setter to inject StudentService object.
     *
     * @param objStudentService the studentService to set
     */
    public void setStudentService(StudentService objStudentService) {

        this.studentService = objStudentService;
    }

    /**
     * Sets the setPerDayClassWiseAttendanceValidator object.
     *
     * @param perDayClassWiseAttendanceValidatorRef set PerDayClassWiseAttendanceValidator object
     */
    public void setPerDayClassWiseAttendanceValidator(
            PerDayClassWiseAttendanceValidator perDayClassWiseAttendanceValidatorRef) {

        this.perDayClassWiseAttendanceValidator = perDayClassWiseAttendanceValidatorRef;
    }

    /**
     * Method to set values to class list in JSP.
     *
     * @return a list of student classes.
     * @throws AkuraAppException SMS exception throw.
     */
    @ModelAttribute(GRADE_CLASS_LIST)
    public List<ClassGrade> populateStudentClassList() throws AkuraAppException {

        return SortUtil.sortClassGradeList(commonService.getClassGradeList());
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

        LOG.info("Calling perDayClassWiseAttendance.jsp to colect input data");
        PerDayAttendanceTemplate perDayAttendanceTemplate = new PerDayAttendanceTemplate();
        modelMap.addAttribute(PER_DAY_ATTENDANCE_TEMPLATE, perDayAttendanceTemplate);

        return REPORTING_PERDAY_CLASS_WISE_ATTENDANCE;
    }

    /**
     * Generate Student Class Wise Exam Absentee List Report.
     *
     * @param perDayAttendanceTemplate dto to transfer user input data to generate the report.
     * @param request of type HttpServletRequest
     * @param response of type HttpServletResponse
     * @param errors errors
     * @param map of type ModelMap
     * @return java.lang.String
     * @throws AkuraAppException throw exception if occur.
     * @throws JRException throw exception if occur.
     */

    @RequestMapping(value = ABSENT_CLASSWISE_REPORT, method = RequestMethod.POST)
    public String onSubmit(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute(PER_DAY_ATTENDANCE_TEMPLATE) PerDayAttendanceTemplate perDayAttendanceTemplate,
            BindingResult errors, ModelMap map) throws AkuraAppException, JRException {

        perDayClassWiseAttendanceValidator.validate(perDayAttendanceTemplate, errors);
        String returnString = STRING_EMPTY;
        if (!errors.hasErrors()) {

            String selectedDate = perDayAttendanceTemplate.getDateConsidered();
            int year = DateUtil.getYearFromDate(DateUtil.getDateTypeYearValue(selectedDate));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateUtil.getParseDate(selectedDate));
            List<Holiday> holidayList = getHolidayList(String.valueOf(year) + String.valueOf(STRING_EMPTY));
            if (!HolidayUtil.isHoliday(holidayList, DateUtil.getParseDate(selectedDate), calendar)) {
                int classGradeId = Integer.parseInt(perDayAttendanceTemplate.getClassDescription());

                // get the student list in the particular class
                List<StudentClassInfo> studentClassInfoList =
                        studentService.getPresentClassStudentList(classGradeId, year);

                // if students exists in the given class
                if (!studentClassInfoList.isEmpty()) {
                    // get student attendance list for a particular date.
                    List<DailyStudentAttendance> studentAttendanceList =
                            dailyAttendanceService.getStudentAttandanceList(DateUtil.getParseDate(selectedDate),
                                    classGradeId);
                    // get the present students in the class
                    List<Student> prsentStudentList = getStudentList(studentAttendanceList, classGradeId, year);

                    // get the absent students in the class
                    Map<String, Student> absentMap =
                            getAbsentStudentList(classGradeId, year, prsentStudentList, studentClassInfoList);

                    Map<String, Object> parameterMap = new HashMap<String, Object>();
                    // set templates to the data source
                    JRBeanCollectionDataSource datasource = null;

                    if (perDayAttendanceTemplate.getReportType() == 1) {
                        datasource =
                                new JRBeanCollectionDataSource(setAllStudentTemplate(prsentStudentList, absentMap));
                        parameterMap.put(ATTENDANCE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                                STRING_ATTENDANCE));
                        parameterMap.put(SUB_TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                                ALL_LIST));

                    } else if (perDayAttendanceTemplate.getReportType() == 2) {
                        datasource = new JRBeanCollectionDataSource(setStudentTemplate(prsentStudentList));
                        parameterMap.put(SUB_TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                                PRESENT_LIST));
                        
                        if(prsentStudentList.size()==0){
                        	map.addAttribute(MESSAGE, "No present Students Found");
                        	return REPORTING_PERDAY_CLASS_WISE_ATTENDANCE;
                        }

                    } else {

                        datasource = new JRBeanCollectionDataSource(setAbsentTemplate(absentMap));
                        parameterMap.put(SUB_TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                                ABSENT_LIST));
                        
                        if(absentMap.size()==0){
                        	map.addAttribute(MESSAGE, "No absent Students Found");
                        	return REPORTING_PERDAY_CLASS_WISE_ATTENDANCE;
                        }
                    }

                    parameterMap.put(DATASOURCE, datasource);
                    parameterMap.put(LOGO_PATH, ReportUtil.getReportLogo());
                    parameterMap.put(PRESENT_COUNT, prsentStudentList.size());
                    parameterMap.put(TOTAL_STUDENTS, studentClassInfoList.size());
                    parameterMap.put(ABSENT_COUNT, absentMap.size());
                    parameterMap.put(STYLE_TEMPLATE, PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                            + PropertyReader.getPropertyValue(SYSTEM_CONFIG, STYLE_PATH));

                    parameterMap.put(CLASS_DESCRIPTION, commonService.findClassGrade(classGradeId).getDescription());
                    parameterMap.put(DATE, selectedDate);
                    setStringParameters(parameterMap);
                    if (perDayAttendanceTemplate.getReportType() == 1) {
                        ReportUtil.displayReportInPdfForm(response, datasource, parameterMap, CLASS_WISE_REPORT);
                    } else {
                        ReportUtil.displayReportInPdfForm(response, datasource, parameterMap,
                                STUDENT_CLASS_WISE_ATTENDANCE_REPORT);
                    }

                    returnString = null;
                    // return null;
                } else {
                    returnString = REPORTING_PERDAY_CLASS_WISE_ATTENDANCE;
                    setErrorMessage(map);
                }

            } else {
                String message = new ErrorMsgLoader().getErrorMessage(REPORTING_UI_HOLIDAY);
                map.addAttribute(MESSAGE, message);
                returnString = REPORTING_PERDAY_CLASS_WISE_ATTENDANCE;
            }

        } else {
            returnString = REPORTING_PERDAY_CLASS_WISE_ATTENDANCE;
            // return REPORTING_PERDAY_CLASS_WISE_ATTENDANCE;
        }
        return returnString;

    }

    /**
     * set the all students name and admission numbers to display in the report.
     *
     * @param prsentStudentList of type student
     * @param absentMap map to holds absent students
     * @return studentTemplateList of type object
     * @throws AkuraAppException throw exception if occur.
     */
    private List<PerDayAttendanceTemplate> setAllStudentTemplate(List<Student> prsentStudentList,
            Map<String, Student> absentMap) throws AkuraAppException {

        List<PerDayAttendanceTemplate> perDayAttendanceTemplatesList = new ArrayList<PerDayAttendanceTemplate>();
        if (!prsentStudentList.isEmpty()) {

            for (Student presentStudent : prsentStudentList) {

                PerDayAttendanceTemplate tempPerDayAttendanceTemplate = new PerDayAttendanceTemplate();
                Student student = studentService.findStudent(presentStudent.getStudentId());
                tempPerDayAttendanceTemplate.setStudentName(student.getNameWtInitials());
                tempPerDayAttendanceTemplate.setStudentAdmissionNo(student.getAdmissionNo());
                tempPerDayAttendanceTemplate.setAbsent(PRESENT);
                perDayAttendanceTemplatesList.add(tempPerDayAttendanceTemplate);
            }

        }

        if (!absentMap.isEmpty()) {
            for (Map.Entry<String, Student> a : absentMap.entrySet()) {
                PerDayAttendanceTemplate tempPerDayAttendanceTemplate = new PerDayAttendanceTemplate();
                tempPerDayAttendanceTemplate.setStudentName(a.getValue().getNameWtInitials());
                tempPerDayAttendanceTemplate.setStudentAdmissionNo(a.getValue().getAdmissionNo());
                tempPerDayAttendanceTemplate.setAbsent(ABSENT);
                perDayAttendanceTemplatesList.add(tempPerDayAttendanceTemplate);
            }

        }
        return SortUtil.sortPerDayAttendanceTemplateListByAdmissionNumber(perDayAttendanceTemplatesList);
    }

    /**
     * Get the Holiday List for the given time period.
     *
     * @param year - string year.
     * @return ListHoliday - a list containing holiday objects.
     * @throws AkuraAppException - AkuraAppException
     */
    private List<Holiday> getHolidayList(String year) throws AkuraAppException {

        String strStartDate = year + START_YEAR;
        String strEndDate = year + END_YEAR;

        Date startDateToSearch = DateUtil.getParseDate(strStartDate);
        Date endDateToSearch = DateUtil.getParseDate(strEndDate);

        return commonService.findHolidayByYear(startDateToSearch, endDateToSearch);
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
     * set String parameters to the map.
     *
     * @param parameterMap - of type map
     * @throws AkuraAppException AkuraAppException.
     */
    private void setStringParameters(Map<String, Object> parameterMap) throws AkuraAppException {

        parameterMap.put(TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STUDENT_ATTANDANCE_REPORT));
        parameterMap.put(PRESENT_COUNT_DES, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                NUMBER_OF_STUDENTS_PRESENT));
        parameterMap.put(TOTAL_STUDENTS_DES, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                TOTAL_NUMBER_OF_STUDENTS_IN_THE_CLASS));
        parameterMap.put(ABSENT_COUNT_DES, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                NUMBER_OF_STUDENTS_ABSENT));
        parameterMap.put(CLASSNAME, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, CLASS_NAME));
        parameterMap.put(DATE_DESCRIPTION, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, DATE_STRING));
        parameterMap.put(ADMISSION_NUM, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, ADMISSION_NUMBER));
        parameterMap.put(STUDENT_NAME2, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STUDENT_NAME));
        parameterMap.put(COLAN, STRING_COLAN);
        parameterMap.put(SUMMARY, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STRING_SUMMARY));
        parameterMap.put(REPORT_GENERATED_ON, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_GENERATED_ON_TEXT));
        parameterMap.put(GENERATED_DATE, DateUtil.getReportGeneratedDate(PropertyReader.getPropertyValue(
                ReportUtil.REPORT_TEMPLATE, REPORT_GENERATED_DATE_LOCALE)));
        parameterMap.put(PAGE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_PAGE));
        parameterMap.put(GPL, AkuraWebConstant.REPORT_GPL);
    }

    /**
     * get the present student in the class of the particular day.
     *
     * @param studentAttendanceList of type DailyStudentAttendance
     * @param classGradeId - integer
     * @param year - integer
     * @return list of type student
     * @throws AkuraAppException throw exception if occur.
     */
    private List<Student> getStudentList(List<DailyStudentAttendance> studentAttendanceList, int classGradeId, int year)
            throws AkuraAppException {

        List<Student> studentList = new ArrayList<Student>();

        if (!studentAttendanceList.isEmpty()) {
            Iterator<DailyStudentAttendance> studentAttendanceIterator = studentAttendanceList.iterator();

            while (studentAttendanceIterator.hasNext()) {
                DailyStudentAttendance dailyStudentAttendance = studentAttendanceIterator.next();
                Student student = studentService.findStudent(dailyStudentAttendance.getStudentId());
                if (!student.getIsOldBoy()) {
                    studentList.add(student);
                }

            }

        }

        return studentList;
    }

    /**
     * get absent students in the class.
     *
     * @param classGradeId - int
     * @param year - int
     * @param studentList - present students list
     * @param studentClassInfoList - list of students in the class
     * @return map of absent students
     * @throws AkuraAppException throw exception if occur.
     */
    private Map<String, Student> getAbsentStudentList(int classGradeId, int year, List<Student> studentList,
            List<StudentClassInfo> studentClassInfoList) throws AkuraAppException {

        Map<String, Student> absentMap = new TreeMap<String, Student>();

        if (!studentClassInfoList.isEmpty()) {

            Iterator<StudentClassInfo> classinfoItr = studentClassInfoList.iterator();

            while (classinfoItr.hasNext()) {
                StudentClassInfo stuClsInfo = classinfoItr.next();
                absentMap.put(stuClsInfo.getStudent().getAdmissionNo(), stuClsInfo.getStudent());

            }
        }
        Map<String, Student> absentTempMap = new TreeMap<String, Student>();
        absentTempMap.putAll(absentMap);

        // remove the present students from map.
        for (Map.Entry<String, Student> entry : absentTempMap.entrySet()) {

            for (Student stu : studentList) {

                if ((stu.getAdmissionNo().equals(entry.getValue().getAdmissionNo()))) {
                    absentMap.remove(stu.getAdmissionNo());
                }
            }
        }

        return absentMap;
    }

    /**
     * set the present students name and admission numbers to display in the report.
     *
     * @param prsentStudentList of type student
     * @return studentTemplateList of type object
     */
    private List<Object> setStudentTemplate(List<Student> prsentStudentList) {

        List<Object> studentTemplateList = new ArrayList<Object>();
        // if all the students absent.
        if (prsentStudentList.isEmpty()) {
            Student student = new Student();
            student.setAdmissionNo(HIPAN);
            student.setFullName(HIPAN);
            studentTemplateList.add(student);

        } else {
            // set the present student name and admission number to display in the report.
            for (Student s : prsentStudentList) {
                Student student = new Student();
                student.setAdmissionNo(s.getAdmissionNo());
                student.setFullName(s.getNameWtInitials());
                studentTemplateList.add(student);
            }
        }

        return SortUtil.sortStudentListByAdmissionNumber(studentTemplateList);
    }

    /**
     * set the absent students name and admission numbers to display in the report.
     *
     * @param absentMap map to holds absent students
     * @return studentAbsentTemplateList of type object
     */
    private List<Object> setAbsentTemplate(Map<String, Student> absentMap) {

        List<Object> studentAbsentTemplateList = new ArrayList<Object>();

        // if all the students presents.
        if (absentMap.isEmpty()) {

            Student student = new Student();
            student.setAdmissionNo(HIPAN);
            student.setFullName(HIPAN);
            studentAbsentTemplateList.add(student);

        } else {
            // set the absent student name and admission number to display in the report.
            for (Map.Entry<String, Student> a : absentMap.entrySet()) {
                Student student = new Student();
                student.setAdmissionNo(a.getValue().getAdmissionNo());
                student.setFullName(a.getValue().getNameWtInitials());
                studentAbsentTemplateList.add(student);
            }
        }

        return SortUtil.sortStudentListByAdmissionNumber(studentAbsentTemplateList);
    }

}
