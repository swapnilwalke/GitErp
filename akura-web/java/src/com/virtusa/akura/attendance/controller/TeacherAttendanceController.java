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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.ListUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.DailyTeacherAttendance;
import com.virtusa.akura.api.dto.Holiday;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.StaffLeave;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.attendance.service.DailyAttendanceService;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.staff.service.StaffService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;

/**
 * Controls the enter daily attendance manually related functions of staff members.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class TeacherAttendanceController {
    
    /** The Constant APPLICATION. */
    private static final String APPLICATION = "application";

    /** attribute for holding url "TeacherDailyAttendanceReload.htm. */
    private static final String TEACHER_DAILY_ATTENDANCE_RELOAD_HTM = "TeacherDailyAttendanceReload.htm";

    /** attribute for holding url "onChangeDateList.htm. */
    private static final String ON_CHANGE_DATE_LIST_HTM = "onChangeDateList.htm";
    
    /** attribute for holding error message key. */
    private static final String ATTENDANCE_SAVE_FAIL = "ATTENDANCE.SAVE.FAIL";
    
    /** attribute for holding string staffType. */
    private static final String STAFF_TYPE = "staffType";
    
    /** attribute for holding string Non Academic Staff. */
    private static final String NON_ACADEMIC_STAFF = "ATTENDANCE.TEACHERATTENDANCE.STAFFTYPE.NON.ACADEMIC";
    
    /** attribute for holding string Academic Staff. */
    private static final String ACADEMIC_STAFF = "ATTENDANCE.TEACHERATTENDANCE.STAFFTYPE.ACADEMIC";
    
    /** attribute for holding string false. */
    private static final String FALSE = "false";
    
    /** attribute for holding string true. */
    private static final String TRUE = "true";
    
    /** attribute for holding string staffTypeList. */
    private static final String STAFF_TYPE_LIST = "staffTypeList";
    
    /** attribute for holding string staffIdList. */
    private static final String STAFF_ID_LIST = "staffIdList";
    
    /** attribute for holding string attendanceList. */
    private static final String ATTENDANCE_LIST = "attendanceList";
    
    /** attribute for holding error message key. */
    private static final String ATTENDANCE_SEARCH_NO_RESULT = "ATTENDANCE.SEARCH.NO.STAFF";
    
    /** attribute for holding string date. */
    private static final String DATE = "date";
    
    /** attribute for holding string Please Select. */
    private static final String PLEASE_SELECT = "0";
    
    /** attribute for holding string select. */
    private static final String SELECT = "select";
    
    /** attribute for holding url searchTeacherDailyAttendance.htm. */
    private static final String SEARCH_TEACHER_ATTENDANCE_HTM = "searchTeacherDailyAttendance.htm";
    
    /** attribute for holding string total. */
    private static final String TOTAL = "total";
    
    /** attribute for holding string currentDate. */
    private static final String CURRENT_DATE = "currentDate";
    
    /** attribute for holding number five. */
    private static final int CONSTANT_FIVE = 5;
    
    /** attribute for holding string. */
    private static final String END_YEAR = "-12-31";
    
    /** attribute for holding string. */
    private static final String START_YEAR = "-01-01";
    
    /** attribute for holding date format. */
    private static final String YYYY_MM_DD = "yyyy-MM-dd";
    
    /** attribute for holding view name attendance/teacherAttendance. */
    private static final String ATTENDANCE_DAILY_ATTENDANCE = "attendance/teacherAttendance";
    
    /** attribute for holding message. */
    private static final String MESSAGE = "message";
    
    /** Model attribute of staffList. */
    private static final String MODEL_ATT_STAFF_LIST = "staffList";
    
    /** Model attribute of staffListWithLeaves. */
    private static final String MODEL_ATT_ALLOWABLE_LEAVE_LIST = "allowableLeaveList";
    
    /** Model attribute of currentDate. */
    private static final String MODEL_ATT_CURRENT_DATE = "dateList";
    
    /** attribute for holding url /saveorupdateTeacherAttendance.htm. */
    private static final String REQ_VALUE_SAVEORUPDATE_TEACHER_ATTENDANCE = "/saveorupdateTeacherAttendance.htm";
    
    /** attribute for holding minus two. */
    private static final int CONSTANT_MINUS_TWO = -2;
    
    /** Represents the default swipe out time. */
    private static final String DEFAULT_TIME_OUT = "default.swipe.out";
    
    /** Represents the default swipe in time. */
    private static final String DEFAULT_TIME_IN = "default.swipe.in";
    
    /** Represents the default swipe out time. */
	private static final String DEFAULT_TIME_OUT_HLFDAY = "default.swipe.out.halfday";

	/** Represents the default swipe in time. */
	private static final String DEFAULT_TIME_IN_HLFDAY = "default.swipe.in.halfday";
    
    /** Represent the name of the property file. */
    private static final String SYSTEM_CONFIG = "systemConfig";
    
    /** DailyAttendanceService attribute for holding dailyAttendanceService. */
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
     * Set dailyAttendanceService object.
     * 
     * @param dailyAttendanceServiceRef set dailyAttendanceService object.
     */
    public void setDailyAttendanceService(DailyAttendanceService dailyAttendanceServiceRef) {
    
        this.dailyAttendanceService = dailyAttendanceServiceRef;
    }
    
    /**
     * staffService To invoke service methods.
     */
    private StaffService staffService;
    
    /**
     * setter to inject StaffService object.
     * 
     * @param staffServiceRef the staffService to set
     */
    
    public void setStaffService(StaffService staffServiceRef) {
    
        this.staffService = staffServiceRef;
    }
    
    /**
     * Method is to return date list.
     * 
     * @return current date - String
     * @throws AkuraAppException - throw AkuraAppException.
     */
    @ModelAttribute(MODEL_ATT_CURRENT_DATE)
    public List<String> populateDateList() throws AkuraAppException {
    
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        List<Holiday> holidayList = getHolidayList(String.valueOf(year));
        List<String> dateList = new ArrayList<String>();
        
        while (true) {
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.SATURDAY) {
                calendar.add(Calendar.DATE, -1);
                
            } else if (dayOfWeek == Calendar.SUNDAY) {
                calendar.add(Calendar.DATE, CONSTANT_MINUS_TWO);
                
            } else {
                
                Date date = calendar.getTime();
                if (!DateUtil.isHoliday(holidayList, DateUtil.getParseDate(DateUtil.getFormatDate(date)))) {
                    dateList.add(DateUtil.getFormatDate(date));
                    calendar.add(Calendar.DATE, -1);
                } else {
                    calendar.add(Calendar.DATE, -1);
                }
                
            }
            
            if (dateList.size() == CONSTANT_FIVE) {
                break;
            }
            
        }
        
        return dateList;
    }
    
    /**
     * Get the Holiday List for the given time period.
     * 
     * @param year - string year.
     * @return ListHoliday - a list containing holiday objects.
     * @throws AkuraAppException - throw AkuraAppException
     */
    private List<Holiday> getHolidayList(String year) throws AkuraAppException {
    
        String strStartDate = year + START_YEAR;
        String strEndDate = year + END_YEAR;
        
        return commonService.findHolidayByYear(DateUtil.getParseDate(strStartDate), DateUtil.getParseDate(strEndDate));
        
    }
    
    /**
     * Method is to return staff Type List.
     * 
     * @return staffTypeList - map of staff type
     * @throws AkuraAppException - throw AkuraAppException.
     */
    @ModelAttribute(STAFF_TYPE_LIST)
    public Map<String, String> populateStaffList() throws AkuraAppException {
    
        Map<String, String> staffTypeList = new HashMap<String, String>();
        staffTypeList.put(FALSE, PropertyReader.getPropertyValue(APPLICATION, NON_ACADEMIC_STAFF));
        staffTypeList.put(TRUE, PropertyReader.getPropertyValue(APPLICATION, ACADEMIC_STAFF));
        
        return staffTypeList;
    }
    
    /**
     * Method to remove save data when date List change.
     * 
     * @param request Http request.
     * @param model - model map to set data.
     * @return String value of jsp page to direct.
     * @throws AkuraAppException throw exception if occur.
     */
    @RequestMapping(value = ON_CHANGE_DATE_LIST_HTM, method = RequestMethod.POST)
    public String onChageDateList(HttpServletRequest request, ModelMap model) throws AkuraAppException {
    
        String staffType = request.getParameter(SELECT);
        String date = request.getParameter(DATE);
        List<Staff> staffList = new ArrayList<Staff>();
        model.addAttribute(CURRENT_DATE, date);
        model.addAttribute(STAFF_TYPE, staffType);
        model.addAttribute(MODEL_ATT_STAFF_LIST, SortUtil.sortStaffListByRegistrationNumber(staffList));
        
        return ATTENDANCE_DAILY_ATTENDANCE;
    }
    
    /**
     * Method to remove save data when date List change.
     * 
     * @param request Http request.
     * @param model - model map to set data.
     * @return String value of jsp page to direct.
     * @throws AkuraAppException throw exception if occur.
     */
    @RequestMapping(value = TEACHER_DAILY_ATTENDANCE_RELOAD_HTM, method = RequestMethod.POST)
    public String reloadPage(HttpServletRequest request, ModelMap model) throws AkuraAppException {
    
        String staffType = request.getParameter(SELECT);
        if(staffType!= null){
            model.addAttribute(STAFF_TYPE, staffType);
        }
        DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD);
        Calendar calendar = Calendar.getInstance();
        String currentDate = dateFormat.format(calendar.getTime());
        model.addAttribute(CURRENT_DATE, currentDate);
        
        return ATTENDANCE_DAILY_ATTENDANCE;
    }
    
    /**
     * Navigate method for enter staff daily attendance manually .
     * 
     * @param modelMap of type ModelMap
     * @return java.lang.String
     */
    
    @RequestMapping(method = RequestMethod.GET)
    public String showDailyTeacherAttendance(final ModelMap modelMap) {
       
        DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD);
        Calendar calendar = Calendar.getInstance();
        String currentDate = dateFormat.format(calendar.getTime());
        modelMap.addAttribute(CURRENT_DATE, currentDate);
        
        return ATTENDANCE_DAILY_ATTENDANCE;
    }
    
    /**
     * method to search attendance for given date and staffType.
     * 
     * @param request Http request.
     * @param map model map to set data.
     * @return String value of jsp page to direct.
     * @throws AkuraAppException throw exception if occur.
     */
    @RequestMapping(method = RequestMethod.POST, value = SEARCH_TEACHER_ATTENDANCE_HTM)
    public String searchTeacherAttendance(HttpServletRequest request, ModelMap map) throws AkuraAppException {
    
        String message;
        
        String staffType = request.getParameter(SELECT);
        if (staffType.equals(PLEASE_SELECT)) {
            message = new ErrorMsgLoader().getErrorMessage(AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
            map.addAttribute(MESSAGE, message);
        } else {
            int total = 0;
            String date = request.getParameter(DATE);
            boolean selectedStafftype = Boolean.parseBoolean(staffType);
            List<DailyTeacherAttendance> dailyTeacherAttendanceList =
                    dailyAttendanceService.getTeacherAttandanceList(DateUtil.getParseDate(date), selectedStafftype);
            
            if (!dailyTeacherAttendanceList.isEmpty()) {
                total = dailyTeacherAttendanceList.size();
            }
            List<Staff> staffList = staffService.getStaffByType(selectedStafftype, DateUtil.getParseDate(date));
            
            List<StaffLeave> allowableLeaveList = staffService.getAllowableStaffLeaveListByStaffTypeAndDate(
        			selectedStafftype, DateUtil.getParseDate(date)
			);
            
            if (staffList.isEmpty()) {
                message = new ErrorMsgLoader().getErrorMessage(ATTENDANCE_SEARCH_NO_RESULT);
                map.addAttribute(MESSAGE, message);
                map.addAttribute(CURRENT_DATE, date);
                map.addAttribute(STAFF_TYPE, staffType);
            } else {
                
                map.addAttribute(ATTENDANCE_LIST, dailyTeacherAttendanceList);
                map.addAttribute(TOTAL, total);
                map.addAttribute(CURRENT_DATE, date);
                map.addAttribute(STAFF_TYPE, staffType);
                map.addAttribute(MODEL_ATT_STAFF_LIST, SortUtil.sortStaffListByRegistrationNumber(staffList));
                map.addAttribute(MODEL_ATT_ALLOWABLE_LEAVE_LIST, allowableLeaveList);
                
            }
            
        }
        return ATTENDANCE_DAILY_ATTENDANCE;
        
    }
    
    /**
     * method to save or update teacher attendance object.
     * 
     * @param request Http request.
     * @param map model map to set data.
     * @return String value of jsp page to direct.
     * @throws AkuraAppException throw exception if occur.
     */
    @RequestMapping(method = RequestMethod.POST, value = REQ_VALUE_SAVEORUPDATE_TEACHER_ATTENDANCE)
    public String saveorupdateTeacherAttendance(HttpServletRequest request, ModelMap map) throws AkuraAppException {
    
    	String[] staffIdList = request.getParameterValues(STAFF_ID_LIST);
		String staffType = request.getParameter(SELECT);
		// selected date
		String date = request.getParameter(DATE);
		
		// get the daily teacher attendance list for given date and staff type
		List<DailyTeacherAttendance> dailyTeacherAttendanceList = dailyAttendanceService
				.getTeacherAttandanceList(DateUtil.getParseDate(date), Boolean.parseBoolean(staffType));
		
		// get teacher list who are on half-day.
		List<StaffLeave> halfdayTeacherAttendanceList = dailyAttendanceService
				.gethalfDayTeacherAttandanceList(DateUtil.getParseDate(date), Boolean.parseBoolean(staffType));
		
		List<String> pastAttendanceList = new ArrayList<String>();
		List<String> halfDayTeacherList = new ArrayList<String>();
		
		// get the present teacher id list
		for (DailyTeacherAttendance dailyAttendanceList : dailyTeacherAttendanceList) {
			pastAttendanceList.add(dailyAttendanceList.getStaffId().toString());
		}
		
		// get the half-day teacher id list
		for (StaffLeave dailyAttendanceList : halfdayTeacherAttendanceList) {
			Integer intObj = new Integer(dailyAttendanceList.getStaffId());
			halfDayTeacherList.add(intObj.toString());
		}
		
		try {
			if (staffIdList != null) {
				
				// selected staffType
				@SuppressWarnings("unchecked")
				// get the absent teachers list
				List<String> toBeRemoved = ListUtils.subtract((pastAttendanceList), Arrays.asList(staffIdList));
				
				@SuppressWarnings("unchecked")
				// get the present teachers list
				List<String> toBeAdd = ListUtils.subtract(
						Arrays.asList(staffIdList), pastAttendanceList
				);
				
				@SuppressWarnings("unchecked")
				List<String> toBeAddWithOutHalfDay = ListUtils.subtract(toBeAdd, halfDayTeacherList);
				
				@SuppressWarnings("unchecked")
				// To be added staff with approved half day leave
				List<String> toBeAddWithHalfDay = ListUtils.subtract(
						toBeAdd, toBeAddWithOutHalfDay
				);
				
				// add present teachers
				if (!toBeAddWithOutHalfDay.isEmpty() || !toBeAddWithHalfDay.isEmpty()) {

					String timeIn = PropertyReader.getPropertyValue(SYSTEM_CONFIG, DEFAULT_TIME_IN);
					String timeOut = PropertyReader.getPropertyValue(SYSTEM_CONFIG, DEFAULT_TIME_OUT);
					String timeInHlfDay = PropertyReader.getPropertyValue(SYSTEM_CONFIG, DEFAULT_TIME_IN_HLFDAY);
					String timeOutHlfDay = PropertyReader.getPropertyValue(SYSTEM_CONFIG, DEFAULT_TIME_OUT_HLFDAY);
					
					List<DailyTeacherAttendance> saveList = new ArrayList<DailyTeacherAttendance>();
					for (String presentTeacherId : toBeAddWithOutHalfDay) {

						DailyTeacherAttendance dailyTeacherAttendance = new DailyTeacherAttendance();
						dailyTeacherAttendance.setStaffId(Integer.valueOf(presentTeacherId));
						dailyTeacherAttendance.setDate(DateUtil.getParseDate(date));
						dailyTeacherAttendance.setTimeIn(timeIn);
						dailyTeacherAttendance.setTimeOut(timeOut);
						
						saveList.add(dailyTeacherAttendance);
					}
					
					// add halfday teachers to DB
					for (String presentTeacherId : toBeAddWithHalfDay) {

						DailyTeacherAttendance dailyTeacherAttendanceH = new DailyTeacherAttendance();
						dailyTeacherAttendanceH.setStaffId(Integer.valueOf(presentTeacherId));
						dailyTeacherAttendanceH.setDate(DateUtil.getParseDate(date));
						dailyTeacherAttendanceH.setTimeIn(timeInHlfDay);
						dailyTeacherAttendanceH.setTimeOut(timeOutHlfDay);
						
						saveList.add(dailyTeacherAttendanceH);
					}
					
					dailyAttendanceService.saveDailyTeacherAttendance(saveList);
                    
                }
                // remove absent teachers
                if (!toBeRemoved.isEmpty()) {
                   
                        deleteAbsentTeachers(date, toBeRemoved);
                    
                }
                
                return searchTeacherAttendance(request, map);
                
            } else if (staffIdList == null && (!dailyTeacherAttendanceList.isEmpty())) {
                
                deleteAbsentTeachers(date, pastAttendanceList);
                return searchTeacherAttendance(request, map);
            }
        }catch (AkuraAppException e) {
            if (e.getCause() instanceof DataIntegrityViolationException) {
                String message = new ErrorMsgLoader().getErrorMessage(ATTENDANCE_SAVE_FAIL);
                map.addAttribute(MESSAGE, message);
                return ATTENDANCE_DAILY_ATTENDANCE;
                
            } else {
                throw e;
            }
        }
        
        return searchTeacherAttendance(request, map);
        
    }
    
    /**
     * method to remove the absent teachers records.
     * 
     * @param date of type Date
     * @param toBeRemoved - list of type string
     * @throws AkuraAppException throw exception if occur
     */
    private void deleteAbsentTeachers(String date, List<String> toBeRemoved) throws AkuraAppException {
    
        for (String presentTeacherId : toBeRemoved) {
            
            List<DailyTeacherAttendance> dailyTeacherAttendanceObject =
                    dailyAttendanceService.findByTeacherId(Integer.parseInt(presentTeacherId),
                            DateUtil.getParseDate(date));
            dailyAttendanceService.deleteDailyTeacherAttendance(dailyTeacherAttendanceObject.get(0));
        }
    }
}
