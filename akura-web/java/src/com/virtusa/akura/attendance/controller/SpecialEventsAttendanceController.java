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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.ListUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.SpecialEvents;
import com.virtusa.akura.api.dto.SpecialEventsAttendance;
import com.virtusa.akura.api.dto.SpecialEventsParticipation;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.dto.StudentClubSociety;
import com.virtusa.akura.api.dto.StudentSport;
import com.virtusa.akura.api.dto.StudentTemporaryLeave;
import com.virtusa.akura.api.dto.SuspendStudent;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.attendance.service.DailyAttendanceService;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.SortUtil;

/**
 * Controls the special event attendance related functions.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class SpecialEventsAttendanceController {
    
    /** attribute for holding string 1. */
    private static final int CLASS_WISE = 1;
    
    /** attribute for holding string 2. */
    private static final int SPORTS_WISE = 2;
    
    /** attribute for holding url changeParticipationList.htm. */
    private static final String CHANGE_PARTICIPATION_LIST_HTM = "changeParticipationList.htm";
    
    /** attribute for holding string participationId. */
    private static final String PARTICIPATION_ID = "participationId";
    
    /** attribute for holding string zero. */
    private static final String STRING_ZERO = "0";
    
    /** attribute for holding string selectedParticipant. */
    private static final String SELECTED_PARTICIPANT = "selectedParticipant";
    
    /** attribute for holding string specialEventsId. */
    private static final String SPECIAL_EVENTS_ID = "specialEventsId";
    
    /** attribute for holding string participationList. */
    private static final String PARTICIPATION_LIST = "participationList";
    
    /** attribute for holding string selectedSpecialEvent. */
    private static final String SELECTED_SPECIAL_EVENT = "selectedSpecialEvent";
    
    /** attribute for holding url saveorupdateSpecialEventsAttendance.htm. */
    private static final String SAVEORUPDATE_SPECIAL_EVENTS_ATTENDANCE_HTM = "saveorupdateSpecialEventsAttendance.htm";
    
    /** attribute for holding url searchSpecialEventsAttendance.htm. */
    private static final String SEARCH_SPECIAL_EVENTS_ATTENDANCE_HTM = "searchSpecialEventsAttendance.htm";
    
    /** attribute for holding url populateParticipantList.htm. */
    private static final String POPULATE_PARTICIPANT_LIST_HTM = "populateParticipantList.htm";
    
    /** attribute for holding string specialEventsList. */
    private static final String SPECIAL_EVENTS_LIST = "specialEventsList";
    
    /** attribute for holding view name attendance/specialEventsAttendance. */
    private static final String ATTENDANCE_SPECIAL_EVENTS_ATTENDANCE = "attendance/specialEventsAttendance";
    
    /** attribute for holding error message key. */
    private static final String ATTENDANCE_SAVE_FAIL = "ATTENDANCE.SAVE.FAIL";
    
    /** attribute for holding string three. */
    private static final int CLUB_WISE = 3;
    
    /** CommonService attribute for holding commonService. */
    private CommonService commonService;
    
    /** attribute for holding message. */
    private static final String MESSAGE = "message";
    
    /** attribute for holding string attendanceList. */
    private static final String ATTENDANCE_LIST = "attendanceList";
    
    /** Model attribute of studentList. */
    private static final String MODEL_ATT_STUDENT_LIST = "studentList";
    
    /** attribute for holding string studentIdList. */
    private static final String STUDENT_ID_LIST = "studentIdList";
    
    /** attribute for holding string total. */
    private static final String TOTAL = "total";
    
    /** attribute for holding error message key. */
    private static final String ATTENDANCE_SEARCH_NO_RESULT = "ATTENDANCE.SEARCH.NO.RESULT";
    
    /** DailyAttendanceService attribute for holding dailyAttendanceService. */
    private DailyAttendanceService dailyAttendanceService;
    
    /**
     * studentService To invoke service methods.
     */
    private StudentService studentService;
    
    /**
     * Set SchoolService object.
     * 
     * @param objCommonService set school service object.
     */
    public void setCommonService(CommonService objCommonService) {

        this.commonService = objCommonService;
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
     * Set dailyAttendanceService object.
     * 
     * @param dailyAttendanceServiceRef set dailyAttendanceService object.
     */
    public void setDailyAttendanceService(DailyAttendanceService dailyAttendanceServiceRef) {

        this.dailyAttendanceService = dailyAttendanceServiceRef;
    }
    
    /**
     * Method is to return SpecialEvents object list.
     * 
     * @return SpecialEventList - SpecialEvents object list.
     * @throws AkuraAppException - throw SMSExeption.
     */
    @ModelAttribute(SPECIAL_EVENTS_LIST)
    public List<SpecialEvents> populateSpecialEventsList() throws AkuraAppException {

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        Date currentDate = DateUtil.getParseDate(DateUtil.getFormatDate(date));
        List<SpecialEvents> allSpecialEvents = commonService.getSpecialEventList();
        List<SpecialEvents> specialEventsList = new ArrayList<SpecialEvents>();
        for (SpecialEvents event : allSpecialEvents) {
            if ((event.getDate().before(currentDate) || event.getDate().equals(currentDate))
                    && DateUtil.getYearFromDate(event.getDate()) >= DateUtil.currentYearOnly()) {
                specialEventsList.add(event);
            }
        }
        
        return SortUtil.sortSpecialEventsList(specialEventsList);
    }
    
    /**
     * Navigate method for enter special events attendance manually .
     * 
     * @param modelMap of type ModelMap
     * @return java.lang.String
     * @throws AkuraAppException throw exception if occur.
     */
    
    @RequestMapping(method = RequestMethod.GET)
    public String showDailyAttendance(final ModelMap modelMap) throws AkuraAppException {

        return ATTENDANCE_SPECIAL_EVENTS_ATTENDANCE;
    }
    
    /**
     * Method to reload page when event list change after search.
     * 
     * @param request Http request.
     * @param model - model map to set data.
     * @return String value of jsp page to direct.
     * @throws AkuraAppException throw exception if occur.
     */
    @RequestMapping(value = POPULATE_PARTICIPANT_LIST_HTM, method = RequestMethod.POST)
    public String populateData(HttpServletRequest request, ModelMap model) throws AkuraAppException {

        int selectSpecialEvent = Integer.parseInt(request.getParameter(SELECTED_SPECIAL_EVENT));
        
        model.addAttribute(PARTICIPATION_LIST, commonService.getSpecialEventParticipationListById(selectSpecialEvent));
        model.addAttribute(SPECIAL_EVENTS_ID, selectSpecialEvent);
        
        return ATTENDANCE_SPECIAL_EVENTS_ATTENDANCE;
    }
    
    /**
     * Method to populate participation List when participation list change after search.
     * 
     * @param request Http request.
     * @param model - model map to set data.
     * @return String value of jsp page to direct.
     * @throws AkuraAppException throw exception if occur.
     */
    @RequestMapping(value = CHANGE_PARTICIPATION_LIST_HTM, method = RequestMethod.POST)
    public String onChageParticipationList(HttpServletRequest request, ModelMap model) throws AkuraAppException {

        int selectSpecialEvent = Integer.parseInt(request.getParameter(SELECTED_SPECIAL_EVENT));
        String participationId = request.getParameter(SELECTED_PARTICIPANT);
        
        model.addAttribute(PARTICIPATION_LIST, commonService.getSpecialEventParticipationListById(selectSpecialEvent));
        model.addAttribute(SPECIAL_EVENTS_ID, selectSpecialEvent);
        model.addAttribute(PARTICIPATION_ID, participationId);
        List<Student> studentMap = new ArrayList<Student>();
        model.addAttribute(MODEL_ATT_STUDENT_LIST, studentMap);
        
        return ATTENDANCE_SPECIAL_EVENTS_ATTENDANCE;
    }
    
    /**
     * method to search attendance for given date and class.
     * 
     * @param request Http request.
     * @param map model map to set data.
     * @return String value of jsp page to direct.
     * @throws AkuraAppException throw exception if occur.
     */
    @RequestMapping(method = RequestMethod.POST, value = SEARCH_SPECIAL_EVENTS_ATTENDANCE_HTM)
    public String searchSpecialEventsAttendance(HttpServletRequest request, ModelMap map) throws AkuraAppException {

        String message;
        
        String specialEventId = request.getParameter(SELECTED_SPECIAL_EVENT);
        String participationId = request.getParameter(SELECTED_PARTICIPANT);
        
        if (specialEventId.equals(STRING_ZERO) || participationId.equals(STRING_ZERO)) {
            message = new ErrorMsgLoader().getErrorMessage(AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
            map.addAttribute(MESSAGE, message);
        } else {
            List<Student> studentMap = new ArrayList<Student>();
            SpecialEventsParticipation specialEventsParticipation =
                    commonService.getSpecialEventsParticipation(Integer.parseInt(participationId));
            setStudentMap(studentMap, specialEventsParticipation);
            if (studentMap.isEmpty()) {
                message = new ErrorMsgLoader().getErrorMessage(ATTENDANCE_SEARCH_NO_RESULT);
                map.addAttribute(MESSAGE, message);
            } else {
                List<SpecialEventsAttendance> specialEventsAttendances =
                        dailyAttendanceService.getSpecialEventAttandanceList(Integer.parseInt(participationId));
                map.addAttribute(ATTENDANCE_LIST, specialEventsAttendances);
                map.addAttribute(TOTAL, specialEventsAttendances.size());
                map.addAttribute(MODEL_ATT_STUDENT_LIST, studentMap);
                
            }
            
        }
        map.addAttribute(SPECIAL_EVENTS_ID, specialEventId);
        map.addAttribute(PARTICIPATION_LIST,
                commonService.getSpecialEventParticipationListById(Integer.parseInt(specialEventId)));
        map.addAttribute(PARTICIPATION_ID, participationId);
        return ATTENDANCE_SPECIAL_EVENTS_ATTENDANCE;
        
    }
    
    /**
     * Method to set student map to parse to the jsp.
     * 
     * @param studentMap - list contain student objects
     * @param specialEventsParticipation - SpecialEventsParticipation object
     * @throws AkuraAppException throw exception if occur.
     */
    @SuppressWarnings("unchecked")
    private void setStudentMap(List<Student> studentMap, SpecialEventsParticipation specialEventsParticipation)
            throws AkuraAppException {

        // get the event scheduled date.
        Date eventDate = specialEventsParticipation.getSpecialEvents().getDate();
        
        if (specialEventsParticipation.getSpecialEvents().getParticipantCategory().getParticipantCategoryId() 
                    == CLASS_WISE) {
            List<StudentClassInfo> studentList =
                    studentService.getPresentClassStudentList(specialEventsParticipation.getClassGrade()
                            .getClassGradeId(), DateUtil.getYearFromDate(specialEventsParticipation.getSpecialEvents()
                            .getDate()));
            
            List<StudentClassInfo> nonCurrentStudentsIntheClass =
                    studentService.getNonCurrentStudentClassInfoList(specialEventsParticipation.getClassGrade()
                            .getClassGradeId(), DateUtil.getYearFromDate(specialEventsParticipation.getSpecialEvents()
                            .getDate()), specialEventsParticipation.getSpecialEvents().getDate());
            
            studentList = ListUtils.subtract(studentList, nonCurrentStudentsIntheClass);
            
            // get the suspended and temporary leaved students who are yet to be activated.
            List<StudentClassInfo> suspendedAndTemporaryLeavedStudentsToBeActive =
                    studentService.getSuspendedAndTemporaryLeavedStudentsToBeActive(specialEventsParticipation
                            .getClassGrade().getClassGradeId(), DateUtil.getYearFromDate(specialEventsParticipation
                            .getSpecialEvents().getDate()), specialEventsParticipation.getSpecialEvents().getDate());
            
            // remove suspended and temporary leaved students from the current student list.
            studentList = ListUtils.subtract(studentList, suspendedAndTemporaryLeavedStudentsToBeActive);
            
            SortUtil.sortStudentListByAdmissionNo(studentList);
            for (StudentClassInfo studentClassInfoList : studentList) {
                
                studentMap.add(studentClassInfoList.getStudent());
            }
            
        } else if (specialEventsParticipation.getSpecialEvents().getParticipantCategory().getParticipantCategoryId() 
                    == SPORTS_WISE) {
            
            List<StudentSport> studentsSports =
                    studentService.getStudentListforSportsCategory(specialEventsParticipation.getSportCategory()
                            .getSportCategoryId(), specialEventsParticipation.getSpecialEvents().getDate());
            
            List<StudentSport> nonCurrentStudentSports =
                    studentService.getNonCurrentStudentListforSportsCategory(specialEventsParticipation
                            .getSportCategory().getSportCategoryId(), specialEventsParticipation.getSpecialEvents()
                            .getDate(), eventDate);
            
            studentsSports = ListUtils.subtract(studentsSports, nonCurrentStudentSports);
            
            SortUtil.sortStudentSportByAdmissionNo(studentsSports);
            for (StudentSport studentSportList : studentsSports) {
                
                studentMap.add(studentSportList.getStudent());
            }
            
        } else if (specialEventsParticipation.getSpecialEvents().getParticipantCategory().getParticipantCategoryId() 
                    == CLUB_WISE) {
            List<StudentClubSociety> studentClubSocieties =
                    studentService.getStudentListforClubSociety(specialEventsParticipation.getClubSociety()
                            .getClubSocietyId(), specialEventsParticipation.getSpecialEvents().getDate());
            
            List<StudentClubSociety> nonCurrentClubSocieties =
                    studentService.getNonCurrentStudentListByClubSociety(specialEventsParticipation.getClubSociety()
                            .getClubSocietyId(), specialEventsParticipation.getSpecialEvents().getDate(), eventDate);
            
            studentClubSocieties = ListUtils.subtract(studentClubSocieties, nonCurrentClubSocieties);
            
            SortUtil.sortStudentClubSocityByAdmissionNo(studentClubSocieties);
            for (StudentClubSociety studentClubSocietyList : studentClubSocieties) {
                
                studentMap.add(studentClubSocietyList.getStudent());
            }
        }
    }
    
    /**
     * method to save or update Special Events Attendance object.
     * 
     * @param request Http request.
     * @param map model map to set data.
     * @return String value of jsp page to direct.
     * @throws AkuraAppException throw exception if occur.
     */
    
    @RequestMapping(method = RequestMethod.POST, value = SAVEORUPDATE_SPECIAL_EVENTS_ATTENDANCE_HTM)
    public String saveorupdateSpecialAttendance(HttpServletRequest request, ModelMap map) throws AkuraAppException {

        String[] studentID = request.getParameterValues(STUDENT_ID_LIST);
        int participationId = Integer.parseInt(request.getParameter(SELECTED_PARTICIPANT));
        
        List<SpecialEventsAttendance> specialEventsAttendances =
                dailyAttendanceService.getSpecialEventAttandanceList(participationId);
        List<String> pastAttendanceList = new ArrayList<String>();
        // get the present students id list
        for (SpecialEventsAttendance specialEventsAttendancesList : specialEventsAttendances) {
            pastAttendanceList.add(String.valueOf(specialEventsAttendancesList.getStudentId()));
        }
        try {
            if (studentID != null) {
                
                @SuppressWarnings("unchecked")
                // get the absent student list
                List<String> toBeRomoved = ListUtils.subtract((pastAttendanceList), Arrays.asList(studentID));
                
                @SuppressWarnings("unchecked")
                // get the present student list
                List<String> toBeAdd = ListUtils.subtract(Arrays.asList(studentID), pastAttendanceList);
                
                // add present students
                if (!toBeAdd.isEmpty()) {
                    
                    addSpecialEventAttendance(participationId, toBeAdd);
                }
                // remove absent students
                if (!toBeRomoved.isEmpty()) {
                    
                    deleteSpecialEventAttendance(participationId, toBeRomoved);
                }
                
                return searchSpecialEventsAttendance(request, map);
            } else if (studentID == null && (!specialEventsAttendances.isEmpty())) {
                
                deleteSpecialEventAttendance(participationId, pastAttendanceList);
                
                return searchSpecialEventsAttendance(request, map);
            }
            
        } catch (AkuraAppException e) {
            if (e.getCause() instanceof DataIntegrityViolationException) {
                String message = new ErrorMsgLoader().getErrorMessage(ATTENDANCE_SAVE_FAIL);
                
                map.addAttribute(MESSAGE, message);
                return ATTENDANCE_SPECIAL_EVENTS_ATTENDANCE;
            } else {
                throw e;
            }
        }
        
        return searchSpecialEventsAttendance(request, map);
        
    }
    
    /**
     * Adds the special event attendance.
     * 
     * @param participationId the participation id
     * @param toBeAdd the to be add
     * @throws AkuraAppException the akura app exception
     */
    private void addSpecialEventAttendance(int participationId, List<String> toBeAdd) throws AkuraAppException {

        List<SpecialEventsAttendance> saveList = new ArrayList<SpecialEventsAttendance>();
        
        for (String presentStudentId : toBeAdd) {
            
            SpecialEventsAttendance specialEventsAttendance = new SpecialEventsAttendance();
            specialEventsAttendance.setSpecialEventsParticipation(commonService
                    .getSpecialEventsParticipation(participationId));
            specialEventsAttendance.setStudentId(Integer.parseInt(presentStudentId));
            saveList.add(specialEventsAttendance);
        }
        dailyAttendanceService.saveSpecialEventsAttendance(saveList);
    }
    
    /**
     * method to delete the student special event attendance records.
     * 
     * @param participationId of type integer
     * @param toBeRomoved - list of type string
     * @throws AkuraAppException throw exception if occur.
     */
    private void deleteSpecialEventAttendance(int participationId, List<String> toBeRomoved) throws AkuraAppException {

        for (String presentStudentId : toBeRomoved) {
            
            List<SpecialEventsAttendance> specialEventsAttendanceObject =
                    dailyAttendanceService.getSpecialEventsAttendanceObjectByStudentId(
                            Integer.parseInt(presentStudentId), participationId);
            dailyAttendanceService.deleteSpecialEventAttendance(specialEventsAttendanceObject.get(0));
        }
    }
}
