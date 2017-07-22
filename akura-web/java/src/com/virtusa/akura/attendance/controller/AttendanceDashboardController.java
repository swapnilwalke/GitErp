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

package com.virtusa.akura.attendance.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.virtusa.akura.api.dto.AttendanceDashboardDto;
import com.virtusa.akura.api.dto.BestStudentAttendanceTemplate;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.Holiday;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.attendance.service.DailyAttendanceService;
import com.virtusa.akura.attendance.validator.AttendanceDashboardValidator;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.HolidayUtil;
import com.virtusa.akura.util.SortUtil;

/**
 * Controls the enter daily attendance manually related functions.
 *
 * @author Virtusa Corporation
 */
@Controller
public class AttendanceDashboardController {

    /** Attribute for holding string status. */
    private static final String STATUS = "status";

    /** Model attribute of select value. */
    private static final String SELECTED_VALUE = "selectedValue";

    /** Model attribute of select Classes. */
    private static final String SELECT_CLASSES = "selectClasses";

    /** Model attribute of select months. */
    private static final String SELECT_MONTHS = "selectMonths";

    /** Model attribute of bestStudentAttendanceList. */
    private static final String BEST_STUDENT_ATTENDANCE_LIST = "bestStudentAttendanceList";

    /** Model attribute of currentYear. */
    private static final String CURRENT_YEAR = "currentYear";

    /** Model attribute of AttendanceDashboardDto. */
    private static final String MODEL_ATT_ATTENDANCE_DASHBOARD = "AttendanceDashboardDto";

    /** Attribute for holding view name attendance/attendanceDashboard. */
    private static final String VIEW_ATTENDANCE_DASHBOARD = "attendance/attendanceDashboard";

    /** Attribute for holding view name attendance/attendanceDashboard.htm. */
    private static final String ATTENDANCE_DASHBOARD_HTM = "/attendanceDashboard.htm";

    /** Attribute for holding request for grade class link /populateGradeClassListByGradeId.htm. */
    private static final String ATTENDANCE_GRADE_CLASS_LIST_HTM = "/populateGradeClassListByGradeId.htm";

    /** Model attribute of gradeList. */
    private static final String MODEL_ATT_GRADE_LIST = "gradeList";

    /** Model attribute of yearList. */
    private static final String MODEL_ATT_YEAR_LIST = "yearList";

    /** Constant for index value. */
    private static final int MONTH_DECEMBER = 12;

    /** Model attribute for holding selected class. */
    private static final String MODEL_ATT_SELECTED_CLASS_ID = "selectedClassId";

    /** Model attribute for holding selected grade. */
    private static final String MODEL_ATT_SELECTED_GRADE_ID = "selectedGradeId";

    /** Model attribute for holding selected month. */
    private static final String MODEL_ATT_SELECTED_MONTH = "selectedMonth";

    /** Model attribute for holding selected year. */
    private static final String MODEL_ATT_SELECTED_YEAR = "selectedYear";

    /** String for holding working days error message. */
    private static final String WORKING_DAYS_ERROR = "ATTENDANCE.ATTENDANCEDASHBOARD.WORKING.DAYS.ERROR";

    /** Attribute for holding error message key. */
    public static final String NO_DATA_ERROR_MESSAGE = "ATTENDANCE.ATTENDANCEDASHBOARD.NO.DATA";

    /** String for holding no minimum attendance error message. */
    private static final String NOT_MINIMUM_ATTENDANCE_ERROR =
            "ATTENDANCE.ATTENDANCEDASHBOARD.MINIMUM.ATTENDANCE.ERROR";

    /** Model attribute for holding error message. */
    private static final String MODEL_ATT_MESSAGE = "message";

    /** Model attribute for holding dto. */
    private static final String MODEL_ATT_TEMPLATE = "AttendanceDashboardDto";

    /** Represents the model attribute name for the minimum number of the item. */
    private static final String PAGE_MIN_NO = "minNo";

    /** Represents the model attribute name for the maximum number of the item. */
    private static final String PAGE_MAX_NO = "maxNo";

    /**
     * Represents the model attribute name for exceeding the maximum number of items.
     */
    private static final String EXCEED_MAX = "exceedMax";

    /**
     * Represents the model attribute name for exceeding the maximum and minimum number of items.
     */
    private static final String EXCEED_MAX_AND_MIN = "exceedMaxAndMin";

    /**
     * Represents the model attribute name for exceeding the minimum number of items.
     */
    private static final String EXCEED_MIN = "exceedMin";

    /**
     * Represents the parameter name of the number of items.
     */
    private static final String ITEM_NO = "itemNo";

    /**
     * Represents the parameter name of the pagination.
     */
    private static final String PAGINATE = "paginate";

    /** Label of the pagination next. */
    private static final String PAGINATION_NEXT = "PAGINATION.NEXT";

    /**
     * Represents the maximum number to be displayed at a time.
     */
    private static final int PAGINATION_NO = 6;

    /** Attribute for holding view name getAttendancePagination. */
    private static final String ATTENDANCE_PAGINATION = "/getAttendancePagination.htm";

    /**
     * Log the error messages.
     */
    private static final Logger LOG = Logger.getLogger(AttendanceDashboardController.class);

    /** CommonService attribute for holding commonService. */
    private CommonService commonService;

    /** DailyAttendanceService attribute for holding dailyAttendanceService. */
    private DailyAttendanceService dailyAttendanceService;

    /** AttendanceDashboardValidator attribute for holding attendanceDashboardValidator. */
    private AttendanceDashboardValidator attendanceDashboardValidator;

    /**
     * @param commonServiceVal the commonService to set
     */
    public void setCommonService(CommonService commonServiceVal) {

        this.commonService = commonServiceVal;
    }

    /**
     * @param dailyAttendanceServiceVal the dailyAttendanceService to set
     */
    public void setDailyAttendanceService(DailyAttendanceService dailyAttendanceServiceVal) {

        this.dailyAttendanceService = dailyAttendanceServiceVal;
    }

    /**
     * @param attendanceDashboardValidatorVal the attendanceDashboardValidator to set
     */
    public void setAttendanceDashboardValidator(AttendanceDashboardValidator attendanceDashboardValidatorVal) {

        this.attendanceDashboardValidator = attendanceDashboardValidatorVal;
    }

    /**
     * Method is to return Grade list reference data.
     *
     * @return GradeList - Grade reference data.
     * @throws AkuraAppException - throw SMSExeption.
     */
    @ModelAttribute(MODEL_ATT_GRADE_LIST)
    public List<Grade> populateGradeList() throws AkuraAppException {

        return SortUtil.sortGradeList(commonService.getGradeList());
    }

    /**
     * Method is to return current year and previous year.
     *
     * @return List - int.
     * @throws AkuraAppException - throw SMSExeption.
     */
    @ModelAttribute(MODEL_ATT_YEAR_LIST)
    public List<String> populateYearList() throws AkuraAppException {

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

    @RequestMapping(value = ATTENDANCE_GRADE_CLASS_LIST_HTM)
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
            classes.append(AkuraWebConstant.UNDERSCORE_STRING);
            classes.append(classGrade.getClassGradeId());

            if (isFirst) {
                allGradeClass.append(classes.toString()); // no comma
                isFirst = false;
            } else {
                allGradeClass.append(AkuraWebConstant.STRING_COMMA); // comma
                allGradeClass.append(classes.toString());
            }
            classes.delete(0, classes.length());
        }
        return allGradeClass.toString();
    }

    /**
     * Handle GET requests for attendance dash board view.
     *
     * @param request - HttpServletRequest
     * @param model - ModelMap
     * @return the name of the view.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showAttendanceDashboard(HttpServletRequest request, final ModelMap model) throws AkuraAppException {

        AttendanceDashboardDto attendanceDashboard = (AttendanceDashboardDto) model.get(MODEL_ATT_ATTENDANCE_DASHBOARD);
        if (attendanceDashboard == null) {
            attendanceDashboard = new AttendanceDashboardDto();
        }

        // Set 0 for all grade.
        attendanceDashboard.setGradeId(0);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        int month = cal.get(Calendar.MONTH);
        int currentYear = cal.get(Calendar.YEAR);

        // Check month and year and set month as December if current month is January.
        if (month == 0 && currentYear == DateUtil.currentYearOnly()) {
            attendanceDashboard.setMonth(MONTH_DECEMBER);
            attendanceDashboard.setYear(currentYear - 1);
        } else {
            // Set the previous month and current year.
            attendanceDashboard.setMonth(month);
            attendanceDashboard.setYear(currentYear);
        }

        // Get the academic days.
        int academicDays = getAcademicDays(attendanceDashboard);

        // Check the academic days.
        if (academicDays >= AkuraConstant.TEN) {
            boolean flag = false;
            attendanceDashboard.setAcademicDays(academicDays);

            // Get best student attendance list with pagination.
            List<BestStudentAttendanceTemplate> bestStudentAttendanceList =
                    getBestAttendanceInfoWithPagination(model, request, attendanceDashboard, flag);

            model.addAttribute(BEST_STUDENT_ATTENDANCE_LIST, bestStudentAttendanceList);

        }

        // Set attributes.
        setSelectedValues(attendanceDashboard, model);
        model.addAttribute(CURRENT_YEAR, currentYear);
        model.addAttribute(MODEL_ATT_ATTENDANCE_DASHBOARD, attendanceDashboard);
        return VIEW_ATTENDANCE_DASHBOARD;

    }

    /**
     * Handle POST requests for attendance dash board view.
     *
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     * @param attendanceDashboardDto - AttendanceDashboardDto
     * @param result - BindingResult
     * @param modelView - ModelAndView
     * @param modelMap - ModelMap
     * @return the name of the view.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(value = ATTENDANCE_DASHBOARD_HTM, method = RequestMethod.POST)
    public ModelAndView searchAttendanceDashboard(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute() AttendanceDashboardDto attendanceDashboardDto, BindingResult result,
            ModelAndView modelView, ModelMap modelMap) throws AkuraAppException {

        // String for error messages.
        String message = null;

        // Set the selected month and class.
        setMonthAndClass(request, attendanceDashboardDto);

        attendanceDashboardValidator.validate(attendanceDashboardDto, result);

        // Check the validations.
        if (result.hasErrors()) {
            return new ModelAndView(VIEW_ATTENDANCE_DASHBOARD, modelMap);

        } else {

            // Get the academic days.
            int academicDays = getAcademicDays(attendanceDashboardDto);
            if (academicDays >= AkuraConstant.TEN) {

                String selectedClass = request.getParameter(SELECT_CLASSES);

                if (selectedClass != null) {
                    ClassGrade classGradeObj = commonService.findClassGrade(Integer.parseInt(selectedClass));

                    if (classGradeObj != null) {
                        attendanceDashboardDto.setClassDescription(classGradeObj.getDescription());
                    }

                }

                boolean flag = false;

                attendanceDashboardDto.setAcademicDays(academicDays);
                // Get student attendance list
                List<BestStudentAttendanceTemplate> bestStudentAttendanceList =
                        getBestAttendanceInfoWithPagination(modelMap, request, attendanceDashboardDto, flag);

                if (bestStudentAttendanceList != null) {
                    if (!bestStudentAttendanceList.isEmpty()) {
                        modelMap.addAttribute(BEST_STUDENT_ATTENDANCE_LIST, bestStudentAttendanceList);
                    } else {
                        message = new ErrorMsgLoader().getErrorMessage(NOT_MINIMUM_ATTENDANCE_ERROR);
                    }
                } else {
                    message = new ErrorMsgLoader().getErrorMessage(NO_DATA_ERROR_MESSAGE);

                }
            } else {
                message = new ErrorMsgLoader().getErrorMessage(WORKING_DAYS_ERROR);
            }

            setSelectedValues(attendanceDashboardDto, modelMap);
            modelMap.addAttribute(MODEL_ATT_MESSAGE, message);
            modelMap.addAttribute(MODEL_ATT_ATTENDANCE_DASHBOARD, attendanceDashboardDto);

        }

        return new ModelAndView(VIEW_ATTENDANCE_DASHBOARD, modelMap);

    }

    /**
     * Method is return the academic day count.
     *
     * @param attendanceDashboardDto type AttendanceDashboardDto
     * @return academic days type integer
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    private int getAcademicDays(AttendanceDashboardDto attendanceDashboardDto) throws AkuraAppException {

        Date fDate = null;
        Date tDate = null;

        int year = attendanceDashboardDto.getYear();
        if (attendanceDashboardDto.getMonth() == 0) {

            fDate = DateUtil.getFistDayOfSelectedYearMonth(year, 1);
            tDate = DateUtil.getLastDayOfSelectedYearMonth(year, MONTH_DECEMBER);
        } else {
            fDate = DateUtil.getFistDayOfSelectedYearMonth(year, attendanceDashboardDto.getMonth());
            tDate = DateUtil.getLastDayOfSelectedYearMonth(year, attendanceDashboardDto.getMonth());

        }

        Calendar firstDateOfPreviousMonth = Calendar.getInstance();
        Calendar lastDateOfPreviousMonth = Calendar.getInstance();
        firstDateOfPreviousMonth.setTime(fDate);
        lastDateOfPreviousMonth.setTime(tDate);

        List<Holiday> holidayList =
                commonService.findHolidayByYear(firstDateOfPreviousMonth.getTime(), lastDateOfPreviousMonth.getTime());

        return HolidayUtil.countWorkingDays(firstDateOfPreviousMonth, lastDateOfPreviousMonth, holidayList);
    }

    /**
     * Method is set the attendance dash board dto with selected month and selected class.
     *
     * @param request type HttpServletRequest
     * @param attendanceDashboardDto type AttendanceDashboardDto.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    private void setMonthAndClass(HttpServletRequest request, AttendanceDashboardDto attendanceDashboardDto)
            throws AkuraAppException {

        int monthId = 0;
        int classId = 0;
        String selectedMonth = request.getParameter(SELECT_MONTHS);
        if (selectedMonth != null && !selectedMonth.equals(AkuraWebConstant.STRING_ZERO)) {
            monthId = Integer.parseInt(selectedMonth);
        }
        attendanceDashboardDto.setMonth(monthId);

        String selectedClass = request.getParameter(SELECT_CLASSES);
        if (selectedClass != null && !selectedClass.equals(AkuraWebConstant.STRING_ZERO)) {

            ClassGrade classGradeObj =
                    commonService.findClassGrade(Integer.parseInt(request.getParameter(SELECT_CLASSES)));

            if (classGradeObj != null) {
                classId = classGradeObj.getSchoolClass().getClassId();
            }
            attendanceDashboardDto.setSelectedClassId(Integer.parseInt(selectedClass));
        } else {
            attendanceDashboardDto.setSelectedClassId(classId);
        }

        attendanceDashboardDto.setClassGradeId(classId);
    }

    /**
     * Method to set the selected model attribute.
     *
     * @param attendanceDashboardDto - AttendanceDashboardDto
     * @param modelMap - ModelMap
     */
    private void setSelectedValues(AttendanceDashboardDto attendanceDashboardDto, ModelMap modelMap) {

        modelMap.addAttribute(MODEL_ATT_SELECTED_GRADE_ID, attendanceDashboardDto.getGradeId());
        modelMap.addAttribute(MODEL_ATT_SELECTED_CLASS_ID, attendanceDashboardDto.getSelectedClassId());
        modelMap.addAttribute(MODEL_ATT_SELECTED_YEAR, attendanceDashboardDto.getYear());
        modelMap.addAttribute(MODEL_ATT_SELECTED_MONTH, attendanceDashboardDto.getMonth());
    }

    /**
     * Returns the attendance dash board information with pagination.
     *
     * @param modelMap - a model map contains the data.
     * @param request - request scope data.
     * @param response - response scope data.
     * @param attendanceDashboardDto - AttendanceDashboardDto
     * @return - the name of the view to be redirected.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(value = ATTENDANCE_PAGINATION, method = RequestMethod.POST)
    public ModelAndView getPagination(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute(MODEL_ATT_TEMPLATE) AttendanceDashboardDto attendanceDashboardDto, ModelMap modelMap)
            throws AkuraAppException {

        boolean flag = false;
        modelMap.addAttribute(STATUS, Boolean.FALSE);

        String selectedClass = request.getParameter(SELECT_CLASSES);

        if (selectedClass != null) {
            ClassGrade classGradeObj = commonService.findClassGrade(Integer.parseInt(selectedClass));

            if (classGradeObj != null) {
                attendanceDashboardDto.setClassDescription(classGradeObj.getDescription());
            }
        }

        setMonthAndClass(request, attendanceDashboardDto);
        attendanceDashboardDto.setAcademicDays(getAcademicDays(attendanceDashboardDto));
        setSelectedValues(attendanceDashboardDto, modelMap);
        List<BestStudentAttendanceTemplate> bestStudentAttendanceList =
                getBestAttendanceInfoWithPagination(modelMap, request, attendanceDashboardDto, flag);

        if (bestStudentAttendanceList == null) {

            int minNo = attendanceDashboardDto.getMinNo() - PAGINATION_NO;
            attendanceDashboardDto.setMinNo(minNo);
            bestStudentAttendanceList = dailyAttendanceService.getTopAttendanceList(attendanceDashboardDto);
            modelMap.addAttribute(STATUS, Boolean.TRUE);
            modelMap.addAttribute(PAGE_MIN_NO, minNo);

        }

        if (bestStudentAttendanceList != null) {
            if (attendanceDashboardDto.isFlag()) {

                modelMap.addAttribute(PAGE_MAX_NO, bestStudentAttendanceList.size() + PAGINATION_NO);
            } else {
                modelMap.addAttribute(PAGE_MAX_NO, bestStudentAttendanceList.size());
            }
        }

        modelMap.addAttribute(BEST_STUDENT_ATTENDANCE_LIST, bestStudentAttendanceList);

        return new ModelAndView(VIEW_ATTENDANCE_DASHBOARD, modelMap);
    }

    /**
     * Returns the Attendance Dash board information list.
     *
     * @param model - ModelMap.
     * @param attendanceDashboardDto - AttendanceDashboardDto.
     * @param request - an instance of HttpServletRequest contains the parameters of the request.
     * @param flag - flag.
     * @return BestStudentAttendance list.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    private List<BestStudentAttendanceTemplate> getBestAttendanceInfoWithPagination(ModelMap model,
            HttpServletRequest request, AttendanceDashboardDto attendanceDashboardDto, boolean flag)
            throws AkuraAppException {

        try {
            LOG.debug(AttendanceDashboardController.class);

            // minimum number of the item to be published on the page.
            int minNo = 0;

            String pagination = (String) request.getParameter(PAGINATE);
            String minNumber = (String) request.getParameter(ITEM_NO);

            if (minNumber != AkuraWebConstant.EMPTY_STRING && minNumber != null) {
                minNo = Integer.parseInt(minNumber);
            }

            if (pagination != null && pagination != AkuraWebConstant.EMPTY_STRING) {
                if (pagination.equals(new ErrorMsgLoader().getErrorMessage(PAGINATION_NEXT))) {

                    minNo = minNo + PAGINATION_NO;

                    model.addAttribute(PAGE_MIN_NO, minNo);
                    model.addAttribute(PAGE_MAX_NO, PAGINATION_NO + minNo);

                    flag = true;

                } else {
                    minNo = minNo - PAGINATION_NO;

                    if (minNo < 0) {
                        minNo = 0;
                    }
                    model.addAttribute(PAGE_MIN_NO, minNo);

                }
            } else {
                model.addAttribute(PAGE_MIN_NO, minNo);
                model.addAttribute(PAGE_MAX_NO, PAGINATION_NO);
            }

            attendanceDashboardDto.setMinNo(minNo);
            attendanceDashboardDto.setFlag(flag);

            return dailyAttendanceService.getTopAttendanceList(attendanceDashboardDto);

        } catch (AkuraAppException e) {
            throw e;
        }
    }

    /**
     * Retrieves the model attributes to be displayed on the view.
     *
     * @param model - a HashMap contains the information of the pagination.
     * @param minNo - integer
     * @param maxPageNo - integer
     * @param totalNoOfItems - total number of item.
     */
    public void getModelAttributes(ModelMap model, int minNo, int maxPageNo, int totalNoOfItems) {

        if (maxPageNo < totalNoOfItems) {
            model.addAttribute(PAGE_MAX_NO, maxPageNo);
        }
        if (maxPageNo == totalNoOfItems && minNo == 0) {
            model.addAttribute(PAGE_MAX_NO, maxPageNo);
            model.addAttribute(EXCEED_MAX_AND_MIN, EXCEED_MAX_AND_MIN);
        }
        if (maxPageNo < totalNoOfItems && minNo != 0) {
            model.addAttribute(PAGE_MAX_NO, maxPageNo); // exceeds the max then 'next' link is
            // disabled.
        }
        if (maxPageNo >= totalNoOfItems && minNo != 0) {
            model.addAttribute(PAGE_MAX_NO, totalNoOfItems);
            model.addAttribute(EXCEED_MAX, EXCEED_MAX); // exceeds the max then 'next' link is disabled.
        }
        if (maxPageNo > totalNoOfItems && minNo == 0) {
            model.addAttribute(PAGE_MAX_NO, totalNoOfItems);
            model.addAttribute(EXCEED_MAX_AND_MIN, EXCEED_MAX_AND_MIN); // exceeds the max then 'next'

        }
        if (minNo == 0 && maxPageNo < totalNoOfItems) {
            model.addAttribute(EXCEED_MIN, EXCEED_MIN);
        }
    }
}
