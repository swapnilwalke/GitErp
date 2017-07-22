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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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

import com.virtusa.akura.api.dto.SpecialEvents;
import com.virtusa.akura.api.dto.SpecialEventsParticipation;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentSpecialEventAttendanceTemplate;
import com.virtusa.akura.api.dto.StudentSpecialEventParticipationView;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.attendance.service.SpecialEventAttendanceService;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.reporting.validator.StudentSpecialEventAttendanceReportValidator;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * StudentSpecialEventAttendanceController is to generate report. for Student attendance on special events.
 *
 * @author Virtusa Corporation
 */
@Controller
public class StudentSpecialEventAttendanceReportController {

    /** variable for holding string "Student_SpecialEventAttendance_Students_Present". */
    private static final String STUDENT_SPECIAL_EVENT_ATTENDANCE_STUDENTS_PRESENT =
            "Student_SpecialEventAttendance_Students_Present";

    /** variable for holding string "Student_SpecialEventAttendance_No_Of_Students". */
    private static final String STUDENT_SPECIAL_EVENT_ATTENDANCE_NO_OF_STUDENTS =
            "Student_SpecialEventAttendance_No_Of_Students";

    /** variable for holding string "Student_SpecialEventAttendance_Filtered_By". */
    private static final String STUDENT_SPECIAL_EVENT_ATTENDANCE_FILTERED_BY =
            "Student_SpecialEventAttendance_Filtered_By";

    /** variable for holding string "Student_SpecialEventAttendance_Event_Name". */
    private static final String STUDENT_SPECIAL_EVENT_ATTENDANCE_EVENT_NAME =
            "Student_SpecialEventAttendance_Event_Name";

    /** variable for holding string "dateLabel". */
    private static final String DATE_LABEL = "dateLabel";

    /** variable for holding string "eventNameLabel". */
    private static final String EVENT_NAME_LABEL = "eventNameLabel";

    /** variable for holding string "filteredByLabel". */
    private static final String FILTERED_BY_LABEL = "filteredByLabel";

    /** variable for holding string "studentPresentLabel". */
    private static final String STUDENT_PRESENT_LABEL = "studentPresentLabel";

    /** variable for holding string "totalStudentsLabel". */
    private static final String TOTAL_STUDENTS_LABEL = "totalStudentsLabel";

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

    /** Constant for style.path. */
    private static final String STYLE_PATH = "style.path";

    /** Constant for styleTemplate. */
    private static final String STYLE_TEMPLATE = "styleTemplate";

    /** Constant for holding eventDate. */
    private static final String EVENT_DATE = "eventDate";

    /** Constant for holding /studentSpecialEventAttendance.htm. */
    private static final String STUDENT_SPECIAL_EVENT_ATTENDANCE_HTM = "/studentSpecialEventAttendance.htm";

    /** Constant for holding filterOptionsList. */
    private static final String FILTER_OPTIONS_LIST = "filterOptionsList";

    /** Constant for holding error message key. */
    public static final String NO_DATA_ERROR_MESSAGE = "REF.UI.NO.DATA";

    /** Constant for holding message. */
    public static final String MESSAGE = "message";

    /** Constant to represent space. */
    private static final String SPACE = " ";

    /** Constant to represent empty string. */
    private static final String STRING = "";

    /** Constant to represent filteredName. */
    private static final String FILTERED_NAME = "filteredName";

    /** Constant to represent club wise participation category id. */
    private static final int CLUB_WISE_PARTICIPATION_CATEGORY_ID = 3;

    /** Constant to represent class wise participation category id. */
    private static final int CLASS_WISE_PARTICIPATION_CATEGORY_ID = 1;

    /** Constant to represents admissionNo. */
    private static final String ADMISSION_NO = "admissionNo";

    /** Constant to represents studentNameHeader. */
    private static final String STUDENT_NAME_HEADER = "studentNameHeader";

    /** Constant to represents participation. */
    private static final String PARTICIPATION = "participation";

    /** Constant to represents report.attendance.student.participation. */
    private static final String REPORT_ATTENDANCE_STUDENT_PARTICIPATION =
            "Student_SpecialEventAttendance_participation";

    /** Constant to represents report.attendance.student.name. */
    private static final String REPORT_ATTENDANCE_STUDENT_NAME = "Student_SpecialEventAttendance_studentName";

    /** Constant to represents report.attendance.student.admissionNo. */
    private static final String REPORT_ATTENDANCE_STUDENT_ADMISSION_NO = "Student_SpecialEventAttendance_admissionNo";

    /** Constant to represents ab. */
    private static final String AB = "ab";

    /** Constant to represents Present. */
    private static final String PRESENT = "Present";

    /** Constant to represents NoOfPresentStudents. */
    private static final String EVENT_NAME = "eventName";

    /** Constant to represents NoOfPresentStudents. */
    private static final String NO_OF_PRESENT_STUDENTS = "NoOfPresentStudents";

    /** Constant to represents totalNoOfStudents. */
    private static final String TOTAL_NO_OF_STUDENTS = "totalNoOfStudents";

    /** Constant to represents /findEventAttendanceFilters.htm. */
    private static final String FIND_EVENT_ATTENDANCE_FILTERS_HTM = "/findEventAttendanceFilters.htm";

    /** Constant to represents StudentSpecialEventAttendance. */
    private static final String STUDENT_SPECIAL_EVENT_ATTENDANCE = "studentSpecialEventAttendance";

    /** model attribute of Special event list . */
    private static final String SPECIAL_EVENT_LIST = "specialEventList";

    /** The Constant TITLE. */
    private static final String TITLE = "title";

    /** The Constant REPORT_STUDENT_SPECIAL_EVENT_ATTENDANCE. */
    private static final String REPORT_STUDENT_SPECIAL_EVENT_ATTENDANCE = "Student_SpecialEventAttendance_titleText";

    /** The Constant STUDENT_SUMMARY_TEMPLATE. */
    private static final String STUDENT_SPECIAL_EVENT_ATTENDANCE_TEMPLATE = "studSpecEventAttendeTempl";

    /** The Constant EMAIL_PROPERTIES. */
    public static final String EMAIL_PROPERTIES = "email";

    /** The Constant LOGO. */
    private static final String LOGO = "logo";

    /** SystemConfig property file. */
    public static final String SYSTEM_CONFIG = "systemConfig";

    /** Holds the value for appserver.home. */
    private static final String APPSERVER_HOME = "appserver.home";

    /** The Constant REPORTING_STUDENT_SUMMARY_REPORT_VIEW. */
    private static final String STUDENT_SPECIAL_EVENT_ATTENDANCE_REPORT_VIEW =
            "reporting/studentSpecialEventAttendanceReport";

    /** commonService attribute to hold CommonService instance. */
    private CommonService commonService;

    /** specialEventAttendanceService attribute to hold SpecialEventAttendanceService instance. */
    private SpecialEventAttendanceService specialEventAttendanceService;

    /** The student service. */
    private StudentService studentService;

    /** The studentSpecialEventAttendanceReport validator. */
    private StudentSpecialEventAttendanceReportValidator studentSpecialEventAttendanceReportValidator;

    /**
     * Setter method for studentService.
     *
     * @param vStudentService studentServiceImpl object
     */
    public void setStudentService(StudentService vStudentService) {

        this.studentService = vStudentService;
    }

    /**
     * Setter method for commonService.
     *
     * @param vCommonService commonServiceImpl object
     */
    public void setCommonService(CommonService vCommonService) {

        this.commonService = vCommonService;
    }

    /**
     * Setter method for specialEventAttendanceService.
     *
     * @param vSpecialEventAttendanceService SpecialEventAttendanceServiceImpl object
     */
    public void setSpecialEventAttendanceService(SpecialEventAttendanceService vSpecialEventAttendanceService) {

        this.specialEventAttendanceService = vSpecialEventAttendanceService;
    }

    /**
     * Setter method for studentSpecialEventAttendanceReportValidator.
     *
     * @param vStudentSpecialEventAttendanceReportValidator object
     */
    public void setStudentSpecialEventAttendanceReportValidator(
            StudentSpecialEventAttendanceReportValidator vStudentSpecialEventAttendanceReportValidator) {

        this.studentSpecialEventAttendanceReportValidator = vStudentSpecialEventAttendanceReportValidator;
    }

    /**
     * Method load jsp page to input data for generating student attendance on special events.
     *
     * @param modelMap - model map to set data
     * @return String value of jsp page to direct
     * @throws AkuraAppException throws exception if occur
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showStudentSpecialEventAttendanceReportForm(final ModelMap modelMap) throws AkuraAppException {

        modelMap.addAttribute(STUDENT_SPECIAL_EVENT_ATTENDANCE_TEMPLATE, new StudentSpecialEventAttendanceTemplate());

        return STUDENT_SPECIAL_EVENT_ATTENDANCE_REPORT_VIEW;
    }

    /**
     * Method load jsp page with filter options list.
     *
     * @param modelMap - model map to set data
     * @param template - model Attribute
     * @return String value of jsp page to direct
     * @throws AkuraAppException throws exception if occur
     */
    @RequestMapping(value = FIND_EVENT_ATTENDANCE_FILTERS_HTM)
    public String loadSpecialEventAttendanceFilters(final ModelMap modelMap,
            @ModelAttribute(STUDENT_SPECIAL_EVENT_ATTENDANCE_TEMPLATE) StudentSpecialEventAttendanceTemplate template)
            throws AkuraAppException {

        modelMap.addAttribute(STUDENT_SPECIAL_EVENT_ATTENDANCE_TEMPLATE, template);
        modelMap.addAttribute(FILTER_OPTIONS_LIST, this.loadSpecialEventParticipationList(template.getSpecialEvents()
                .getSpecialEventsId()));

        return STUDENT_SPECIAL_EVENT_ATTENDANCE_REPORT_VIEW;
    }

    /**
     * This method initiate the requested report generation.
     *
     * @param response http servlet response
     * @param template - model Attribute
     * @param bindingResult the binding result
     * @param modelMap - model map to set data
     * @return null
     * @throws AkuraException thorws if exception occur
     */
    @RequestMapping(value = STUDENT_SPECIAL_EVENT_ATTENDANCE_HTM, method = RequestMethod.POST)
    public ModelAndView generateStudentSpecialEventAttendanceReport(HttpServletResponse response,
            @ModelAttribute(STUDENT_SPECIAL_EVENT_ATTENDANCE_TEMPLATE) StudentSpecialEventAttendanceTemplate template,
            BindingResult bindingResult, ModelMap modelMap) throws AkuraException {

        ModelAndView modelAndView = null;

        studentSpecialEventAttendanceReportValidator.validate(template, bindingResult);

        if (bindingResult.hasErrors()) {

            modelMap.addAttribute(STUDENT_SPECIAL_EVENT_ATTENDANCE_TEMPLATE, template);

            modelAndView = new ModelAndView(STUDENT_SPECIAL_EVENT_ATTENDANCE_REPORT_VIEW);

            modelAndView.addObject(FILTER_OPTIONS_LIST, this.loadSpecialEventParticipationList(template
                    .getSpecialEvents().getSpecialEventsId()));
        } else {

            Map<String, Object> map = new HashMap<String, Object>();

            SpecialEvents specialEvents =
                    commonService.getSpecialEventById(template.getSpecialEvents().getSpecialEventsId());

            this.setStudentFilteringName(specialEvents.getParticipantCategory().getParticipantCategoryId(), template
                    .getSpecialEventsParticipation().getSpecialEventsParticipationId(), map);

            map.put(EVENT_DATE, DateUtil.getLongDate(specialEvents.getDate()));
            JRBeanCollectionDataSource datasource =
                    new JRBeanCollectionDataSource(this.processStudentList(specialEvents, template, map));
            populateStudentPersonalDetails(map, specialEvents);

            if (datasource.getRecordCount() != 0) {
                displayReportForm(response, datasource, map, STUDENT_SPECIAL_EVENT_ATTENDANCE);
            } else {
                List<SpecialEventsParticipation> specialEventParticipantList =
                        commonService.getParticipantListBySpecialEvent(specialEvents);
                modelMap.addAttribute(FILTER_OPTIONS_LIST, specialEventParticipantList);
                modelAndView = new ModelAndView(STUDENT_SPECIAL_EVENT_ATTENDANCE_REPORT_VIEW);
                setErrorMessage(modelMap);
            }
        }
        return modelAndView;
    }

    /**
     * This method used to populate report required data.
     *
     * @param map to hold data to show in report
     * @param specialEvents pass the special event object
     * @throws AkuraAppException throws when exception occurs
     */
    private void populateStudentPersonalDetails(Map<String, Object> map, SpecialEvents specialEvents)
            throws AkuraAppException {

        map.put(TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_STUDENT_SPECIAL_EVENT_ATTENDANCE));

        map.put(LOGO, ReportUtil.getReportLogo());
        map.put(EVENT_NAME, specialEvents.getName());
        map.put(ADMISSION_NO, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_ATTENDANCE_STUDENT_ADMISSION_NO));
        map.put(STUDENT_NAME_HEADER, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_ATTENDANCE_STUDENT_NAME));
        map.put(PARTICIPATION, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_ATTENDANCE_STUDENT_PARTICIPATION));
        map.put(STYLE_TEMPLATE, PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                + PropertyReader.getPropertyValue(SYSTEM_CONFIG, STYLE_PATH));
        map.put(REPORT_GENERATED_ON, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                REPORT_GENERATED_ON_TEXT));
        map.put(GENERATED_DATE, DateUtil.getReportGeneratedDate(PropertyReader.getPropertyValue(
                ReportUtil.REPORT_TEMPLATE, REPORT_GENERATED_DATE_LOCALE)));
        map.put(PAGE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_PAGE));
        map.put(GPL, AkuraWebConstant.REPORT_GPL);

        map.put(TOTAL_STUDENTS_LABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                STUDENT_SPECIAL_EVENT_ATTENDANCE_NO_OF_STUDENTS));
        map.put(STUDENT_PRESENT_LABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                STUDENT_SPECIAL_EVENT_ATTENDANCE_STUDENTS_PRESENT));
        map.put(FILTERED_BY_LABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                STUDENT_SPECIAL_EVENT_ATTENDANCE_FILTERED_BY));

        map.put(EVENT_NAME_LABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                STUDENT_SPECIAL_EVENT_ATTENDANCE_EVENT_NAME));
        map.put(DATE_LABEL, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                "Student_SpecialEventAttendance_Date"));

    }

    /**
     * This method is used to get list of StudentSpecialEventAttendanceTemplate to show data in report.
     *
     * @param specialEvents selected special event object
     * @param studSpecEventAttendeTempl list of integers
     * @param map to hold data to show in report
     * @return list of StudentSpecialEventAttendanceTemplate
     * @throws AkuraAppException throws when exception occur
     */
    private List<StudentSpecialEventAttendanceTemplate> processStudentList(SpecialEvents specialEvents,
            StudentSpecialEventAttendanceTemplate studSpecEventAttendeTempl, Map<String, Object> map)
            throws AkuraAppException {

        List<StudentSpecialEventParticipationView> allocatedStudents =
                studentService.getAllocatedStudentListForEvent(studSpecEventAttendeTempl
                        .getSpecialEventsParticipation().getSpecialEventsParticipationId(), specialEvents.getDate());

        List<Integer> studentIdList =
                specialEventAttendanceService.getStudentsIDOfSpecialEventAttendance(studSpecEventAttendeTempl
                        .getSpecialEventsParticipation().getSpecialEventsParticipationId());

        List<StudentSpecialEventAttendanceTemplate> templateObjlist =
                new ArrayList<StudentSpecialEventAttendanceTemplate>();
        int totalStudents = 0;
        int presentStudents = 0;
        for (StudentSpecialEventParticipationView student : allocatedStudents) {
            totalStudents++;
            Student studentt = studentService.findStudent(student.getStudentId());
            Date departureDate = studentt.getDateOfDeparture();
            Date specialEventDate = specialEvents.getDate();

            if (departureDate == null || departureDate != null && departureDate.after(specialEventDate)) {
                StudentSpecialEventAttendanceTemplate studEventAttendanceTempl =
                        new StudentSpecialEventAttendanceTemplate();
                studEventAttendanceTempl.setStudentAdmissionNo(student.getId().getAdmissionNo());
                studEventAttendanceTempl.setStudentName(student.getNameWithInitals());
                if (studentIdList.contains(student.getStudentId())) {
                    presentStudents++;
                    studEventAttendanceTempl.setAttendance(PRESENT);
                } else {
                    studEventAttendanceTempl.setAttendance(AB);
                }
                templateObjlist.add(studEventAttendanceTempl);
            }
        }
        map.put(TOTAL_NO_OF_STUDENTS, Integer.toString(totalStudents));
        map.put(NO_OF_PRESENT_STUDENTS, Integer.toString(presentStudents));
        return SortUtil.sortStudentSpecialEventsAttendanceList(templateObjlist);
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

    /**
     * Return filtered name of the report.
     *
     * @param eventCategoryId event participation category id
     * @param participationId selected participation id for report
     * @param map to hold data to show in report
     * @throws AkuraAppException throws when exception occur
     */
    private void setStudentFilteringName(int eventCategoryId, int participationId, Map<String, Object> map)
            throws AkuraAppException {

        SpecialEventsParticipation specialEventsParticipation =
                commonService.getSpecialEventsParticipation(participationId);
        String filteredName = STRING;
        switch (eventCategoryId) {
            case CLASS_WISE_PARTICIPATION_CATEGORY_ID:
                filteredName = specialEventsParticipation.getClassGrade().getDescription();
                break;
            case CLUB_WISE_PARTICIPATION_CATEGORY_ID:
                filteredName = specialEventsParticipation.getClubSociety().getName();
                break;
            default:
                filteredName =
                        specialEventsParticipation.getSportCategory().getSportSubCategory().getDescription() + SPACE
                                + specialEventsParticipation.getSportCategory().getSport().getDescription();
                break;
        }
        map.put(FILTERED_NAME, filteredName);
    }

    /**
     * Set the error messages when returning to the jsp.
     *
     * @param modelMap - ModelMap
     */
    private void setErrorMessage(ModelMap modelMap) {

        String message = new ErrorMsgLoader().getErrorMessage(NO_DATA_ERROR_MESSAGE);
        modelMap.addAttribute(MESSAGE, message);
    }

    /**
     * This method load special event participation by event id.
     *
     * @param specialEventId special event id
     * @return list of special Event participation objects.
     * @throws AkuraAppException -throw detailed exception.
     */
    private List<SpecialEventsParticipation> loadSpecialEventParticipationList(int specialEventId)
            throws AkuraAppException {

        SpecialEvents specialEvents = commonService.getSpecialEventById(specialEventId);

        return commonService.getParticipantListBySpecialEvent(specialEvents);

    }

    /**
     * Populate available special events in a list.
     *
     * @return list of special events
     * @throws AkuraAppException -throw detailed exception.
     */
    @ModelAttribute(SPECIAL_EVENT_LIST)
    public List<SpecialEvents> populateSpecialEventList() throws AkuraAppException {

        Calendar calender = Calendar.getInstance();
        Date date = calender.getTime();
        Date currentDate = DateUtil.getParseDate(DateUtil.getFormatDate(date));
        List<SpecialEvents> specialEeventList = commonService.getSpecialEventList();

        for (Iterator<SpecialEvents> speEventIterator = specialEeventList.iterator(); speEventIterator.hasNext();) {
            SpecialEvents specialEvents = (SpecialEvents) speEventIterator.next();
            if (specialEvents.getDate().after(currentDate)) {
                speEventIterator.remove();
            }
        }
        return SortUtil.sortSpecialEventsListByName(specialEeventList);
    }
}
