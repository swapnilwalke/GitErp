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

package com.virtusa.akura.student.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.Achievement;
import com.virtusa.akura.api.dto.ClubSociety;
import com.virtusa.akura.api.dto.Position;
import com.virtusa.akura.api.dto.Sport;
import com.virtusa.akura.api.dto.SportCategory;
import com.virtusa.akura.api.dto.SportSub;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.dto.StudentClubSociety;
import com.virtusa.akura.api.dto.StudentSport;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.api.exception.StudentClubAndSocietyAchievementDeleteException;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.delegate.LoginDelegate;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;

/**
 * CoCurricularActivityController is to handle Add/Edit/Delete/View operations for Student CoCurricular
 * module.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class CoCurricularActivityController {
    
    /**
     * key to hold string selectedGrade.
     */
    private static final String SELECTED_GRADE = "selectedGrade";
    
    /**
     * key to hold model attribute StudentClassId.
     */
    private static final String MODEL_ATT_SELECTED_ID = "selectedStudClassId";
    
    /** model attribute of studentGrade list. */
    private static final String MODEL_ATT_STUDENT_GRADE_LIST = "studentGradeList";
    
    /** The session attribute for hold academic faith life rate. */
    private static final String AVERAGE_ACADEMIC_LIFE_RATING = "averageAcademicLifeRating";
    
    /** The session attribute for hold faith life rate. */
    private static final String AVERAGE_FAITH_LIFE_RATING = "averageFaithLifeRating";
    
    /** The Constant for hold year format. */
    private static final String YYYY = "yyyy";
    
    /** attribute for holding action method. */
    private static final String ACTION_FOR_POPULATE_DATA = "/populateData.htm";
    
    /** The Constant for hold archivment description exist. */
    private static final String REF_UI_STUDENT_ACHIEVEMENT_EXIST = "REF.UI.STUDENT.ACHIEVEMENT.DESCRIPTION.EXIST";
    
    /** The Constant for hold archivment field type. */
    private static final String REF_UI_FIELD_TYPE_ACHIEVEMENT = "REF.UI.FIELD.TYPE.ACHIEVEMENT";
    
    /** The Constant for hold archivment description length. */
    private static final String REF_UI_FIELD_LENGTH = "REF.UI.FIELD.LENGTH";
    
    /** The Constant for hold error message for club description exist. */
    private static final String REF_UI_STUDENT_CLUB_DESCRIPTION_EXIST = "REF.UI.STUDENT.CLUB.DESCRIPTION.EXIST";
    
    /** The Constant for hold error message for spot description exist. */
    private static final String REF_UI_STUDENT_SPORT_DESCRIPTION_EXIST = "REF.UI.STUDENT.SPORT.DESCRIPTION.EXIST";
    
    /** The Constant for hold null. */
    private static final String STRING_NULL = "";
    
    /** The Constant for hold null. */
    private static final int ZERO = 0;
    
    /** The Constant FIRST_YEAR. */
    private static final String FIRST_YEAR = "FirstYear";
    
    /** The Constant YEAR_COUNT. */
    private static final String YEAR_COUNT = "YearCount";
    
    /** Logger to log the exceptions. */
    private static final Logger LOG = Logger.getLogger(CoCurricularActivityController.class);
    
    /** view get method create student co-curricular. */
    private static final String VIEW_GET_STUDENT_ACTIVITY = "student/coCurricular";
    
    /** attribute for holding action method. */
    private static final String ACTION_FOR_CO_CURRICULAR_DETAILS = "/coCurricularActivity.htm";
    
    /** attribute for holding action method. */
    private static final String ACTION_FOR_STUDENT_SPORTS = "/saveOrUpdateStudentSport.htm";
    
    /** attribute for holding action method. */
    private static final String ACTION_FOR_DELETE_STUDENT_CLUB_SOCIETY = "/deleteStudentClubsociety.htm";
    
    /** attribute for holding action method. */
    private static final String ACTION_FOR_STUDENT_CLUB_SOCIETY = "/saveOrUpdateStudentClubSociety.htm";
    
    /** attribute for holding action method. */
    private static final String ACTION_FOR_DELETE_STUDENT_SPORT = "/deleteStudentSport.htm";
    
    /** attribute for holding action method. */
    private static final String ACTION_FOR_SAVE_UPDATE_ACHIEVEMENT = "/saveOrUpdateAchievement.htm";
    
    /** attribute for holding action method. */
    private static final String ACTION_FOR_DELETE_ACHIEVEMENT = "/deleteAchievement.htm";
    
    /** model attribute of student Sport list. */
    private static final String MODEL_ATT_STUDENT_SPORT_LIST = "studentSportList";
    
    /** model attribute of student Club Society list. */
    private static final String MODEL_ATT_STUDENT_CLUB_SOCIETY_LIST = "studentClubSocietyList";
    
    /** model attribute of Sport list. */
    private static final String MODEL_ATT_SPORT_LIST = "sportList";
    
    /** model attribute of Club or Society list. */
    private static final String MODEL_ATT_CLUB_SOCIETY_LIST = "clubSocietyList";
    
    /** model attribute of Sport Sub list. */
    private static final String MODEL_ATT_SPORT_SUB_LIST = "sportSubList";
    
    /** model attribute of position list. */
    private static final String MODEL_ATT_POSITION_LIST = "positionList";
    
    /** model attribute of massage. */
    private static final String MODEL_ATT_MESSAGE_CLUB = "cmessage";
    
    /** model attribute of massage. */
    private static final String MODEL_ATT_MESSAGE_SPORT = "smessage";
    
    /** model attribute of selected object. */
    private static final String MODEL_ATT_SELECTED_CLUB_OBJECT = "selectedStClubObj";
    
    /** model attribute of selected object. */
    private static final String MODEL_ATT_SELECTED_SPORT_OBJECT = "selectedStSportObj";
    
    /** model attribute of selected year. */
    private static final String MODEL_ATT_DATE = "selectedYr";
    
    /** model attribute of Achievement list. */
    private static final String MODEL_ATT_ACHIEVEMENT_LIST = "achievementList";
    
    /** model attribute of selected achievement object. */
    private static final String MODEL_ATT_SELECTED_ACHIEVEMENT_OBJECT = "selectedAchivObj";
    
    /** model attribute of message. */
    private static final String MODEL_ATT_ACHIEVEMENT_MESSAGE = "amessage";
    
    /** model attribute of student faithLife average value. */
    private static final String MODEL_ATT_STUDENT_FAITH_LIFE = "averageFaithLife";
    
    /** model attribute of student academic life average value. */
    private static final String MODEL_ATT_STUDENT_ACADEMIC_LIFE = "averageAcademicLife";
    
    /** key to define the averageAttendanceRating. */
    private static final String AVERAGE_ATTENDANCE_RATING = "averageAttendanceRating";
    
    /** key to define the attendanceRating. */
    private static final String MODEL_ATT_ATTENDANCE_RATING = "attendanceRating";
    
    /** constant hold request parameter for color. */
    private static final String PARAM_COLOUR = "colour";
    
    /** constant hold request parameter for student position. */
    private static final String STUDENT_POSITION = "sPosition";
    
    /** constant hold request parameter for sport sub id. */
    private static final String SPORT_SUB_ID = "sportSub";
    
    /** constant hold request parameter for sport id. */
    private static final String SPORT_ID = "sport";
    
    /** constant hold request parameter for selected student sport id. */
    private static final String SELECTED_STUDENT_SPORT_ID = "selectedStudentSportId";
    
    /** constant hold parameter for flag. */
    private static final String ACH_FLAG = "flag";
    
    /** constant hold parameter for student club society. */
    private static final String STUD_CLUB_SOCIETY = "studClubSociety";
    
    /** constant hold parameter for club achievement. */
    private static final String CLUB_ACHIEVEMENT = "clubAchievement";
    
    /** constant hold parameter for selected achiement id. */
    private static final String SELECTED_ACHIEVEMENT_ID = "selectedAchievementId";
    
    /** constant hold parameter for achievement. */
    private static final String STR_ACHIEVEMENT = "achievement";
    
    /** constant hold parameter for activity. */
    private static final String ACTIVITY = "activity";
    
    /** constant hold regular expression pattern. */
    private static final String REGEX_PATTERN = "[^A-Za-z ]";
    
    /** constant hold parameter for internal achievement. */
    private static final String INTERNAL_ACHIEVEMENT = "1";
    
    /** constant hold parameter for external achievement. */
    private static final String EXTERNAL_ACHIEVEMENT = "0";
    
    /** constant hold parameter for student id. */
    private static final String STUDENT_ID = "studentId";
    
    /** constant hold parameter club position. */
    private static final String POSITION_ID = "cPosition";
    
    /** constant hold parameter for club society id. */
    private static final String CLUB_SOCIETY_ID = "clubSociety";
    
    /** constant hold parameter for selected Student ClubSociety Id. */
    private static final String SELECTED_STUDENT_CLUB_SOCIETY_ID = "selectedStudentClubSocietyId";
    
    /** constant to hold maximum length of description. */
    private static final int LENGTH_DESCRIPTION = 45;
    
    /** Holds the AkuraErrorMsg property file name. */
    private static final String AKURA_ERROR_MSG_PROPERTY = "AkuraErrorMsg";
    
    /** attribute for holding error key "STU.UI.STUDENT.CLUBANDSOCIETY.ACHIEVEMENT.DELETE.ERROR". */
    private static final String CLUB_AND_SOCIETY_DELETE_ERROR =
            "STU.UI.STUDENT.CLUBANDSOCIETY.ACHIEVEMENT.DELETE.ERROR";
    
    /** StudentService attribute for holding studentService. */
    private StudentService studentService;
    
    /** CommonService attribute for holding commonService. */
    private CommonService commonService;
    
    /** Represents an instance of StudentLoginService. */
    private LoginDelegate studentLoginDelegate;
    
    /** constant hold message "errorDeleteClubSociety". */
    private static final String ERROR_DELETE_CLUB_SOCIETY = "errorDeleteClubSociety";
    
    /**
     * Sets an instance of StudentLoginService.
     * 
     * @param studentLoginDelegateVal - an instance of StudentLoginService.
     */
    public void setStudentLoginDelegate(LoginDelegate studentLoginDelegateVal) {

        this.studentLoginDelegate = studentLoginDelegateVal;
    }
    
    /**
     * sets the CommonService object.
     * 
     * @param commonServiceObj the commonService to set
     */
    public void setCommonService(CommonService commonServiceObj) {

        this.commonService = commonServiceObj;
    }
    
    /**
     * sets the StudentService object.
     * 
     * @param studentServiceObj the studentService to set
     */
    public void setStudentService(StudentService studentServiceObj) {

        this.studentService = studentServiceObj;
    }
    
    /**
     * handle GET requests for student co curricular_details view.
     * 
     * @throws AkuraAppException - if error occurs when view co curricular page.
     * @return the name of the view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showStudentCoCurricular() throws AkuraAppException {

        return VIEW_GET_STUDENT_ACTIVITY;
    }
    
    /**
     * Create or update student sport details.
     * 
     * @param request - HttpServletRequest
     * @param session - HttpSession
     * @param model - ModelMap
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - if error occurs when save or update a student sport instance.
     */
    @RequestMapping(value = ACTION_FOR_STUDENT_SPORTS, method = RequestMethod.POST)
    public String saveOrUpdateStudentSport(HttpServletRequest request, HttpSession session, ModelMap model)
            throws AkuraAppException {

        String strMessage = null;
        StudentSport selectedStudentSport = null;
        String strStudentSportId = request.getParameter(SELECTED_STUDENT_SPORT_ID);
        String strSportId = request.getParameter(SPORT_ID);
        String strSportSubId = request.getParameter(SPORT_SUB_ID);
        String strPositionId = request.getParameter(STUDENT_POSITION);
        String strColour = request.getParameter(PARAM_COLOUR);
        
        int colour = 0;
        
        if (strColour != null) {
            colour = 1;
        }
        
        int studentClassInfoId = (Integer.parseInt(request.getParameter(SELECTED_GRADE)));
        
        StudentClassInfo studentClassInfo = studentService.findStudentClassInfoById(studentClassInfoId);
        
        String strYear = DateUtil.getStringYear(studentClassInfo.getYear());
        
        Date date = DateUtil.getDateTypeYearValue(strYear);
        
        // validation
        if (Integer.parseInt(strSportId) == ZERO ||
        		Integer.parseInt(strSportSubId) == ZERO || Integer.parseInt(strPositionId) == ZERO) {
            strMessage = new ErrorMsgLoader().getErrorMessage(AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
            model.addAttribute(MODEL_ATT_MESSAGE_SPORT, strMessage);
            request.setAttribute(SPORT_ID, strSportId);
            request.setAttribute(SPORT_SUB_ID, strSportSubId);
            request.setAttribute(STUDENT_POSITION, strPositionId);
            request.setAttribute(PARAM_COLOUR, strColour);
            
            if (!STRING_NULL.equals(strStudentSportId)) {
            	selectedStudentSport = studentService.findStudentSportObjById(Integer.parseInt(strStudentSportId));
            	model.addAttribute(MODEL_ATT_SELECTED_SPORT_OBJECT, selectedStudentSport);
            }
            
            return populateStudentCoCurricularData(request, session, model);
        }
        
        // find the sportCategory using the sport id and sport sub id
        int sportCategoryId = searchSportCategory(Integer.parseInt(strSportId), Integer.parseInt(strSportSubId));
        
        // edit student sport.
        if (!STRING_NULL.equals(strStudentSportId)) {
            int studentSportId = Integer.parseInt(strStudentSportId);
            
            selectedStudentSport = studentService.findStudentSportObjById(studentSportId);
            if (!isExistsStudentSport(getSessionStudentId(session), sportCategoryId, date)) {
                
                SportCategory objSportCategory = (SportCategory) commonService.findSportCategoryById(sportCategoryId);
                selectedStudentSport.setSportCategory(objSportCategory);
                
                updateStudentSport(strPositionId, colour, selectedStudentSport);
                
            } else {
                
                // sport for student already exist.
                // editing same object, with different position.
                if (selectedStudentSport.getSportCategory().getSportCategoryId() == sportCategoryId) {
                    
                    updateStudentSport(strPositionId, colour, selectedStudentSport);
                    
                } else {
                    strMessage = new ErrorMsgLoader().getErrorMessage(REF_UI_STUDENT_SPORT_DESCRIPTION_EXIST);
                    model.addAttribute(MODEL_ATT_MESSAGE_SPORT, strMessage);
                    model.addAttribute(MODEL_ATT_SELECTED_SPORT_OBJECT, selectedStudentSport);
                    request.setAttribute(SPORT_ID, strSportId);
                    request.setAttribute(SPORT_SUB_ID, strSportSubId);
                    request.setAttribute(STUDENT_POSITION, strPositionId);
                    request.setAttribute(PARAM_COLOUR, strColour);
                    
                    return populateStudentCoCurricularData(request, session, model);
                }
            }
            
        } else {
            if (STRING_NULL.equals(strStudentSportId)) {
                
                // Add New sport for student.
                if (!isExistsStudentSport(getSessionStudentId(session), sportCategoryId, date)) {
                    
                    addStudentSport(session, strPositionId, colour, date, sportCategoryId);
                } else {
                    strMessage = new ErrorMsgLoader().getErrorMessage(REF_UI_STUDENT_SPORT_DESCRIPTION_EXIST);
                    model.addAttribute(MODEL_ATT_MESSAGE_SPORT, strMessage);
                    request.setAttribute(SPORT_ID, strSportId);
                    request.setAttribute(SPORT_SUB_ID, strSportSubId);
                    request.setAttribute(STUDENT_POSITION, strPositionId);
                    request.setAttribute(PARAM_COLOUR, strColour);
                    return populateStudentCoCurricularData(request, session, model);
                }
            }
        }
        return populateStudentCoCurricularData(request, session, model);
    }
    
    /**
     * Manages adding student sport.
     * 
     * @param session - holds {@link HttpSession}
     * @param strPositionId - holds String type
     * @param colour - holds integer type
     * @param date - holds date type
     * @param sportCategoryId - holds integer type
     * @throws AkuraAppException - throw detail exception when add student sport
     */
    private void addStudentSport(HttpSession session, String strPositionId, int colour, Date date, int sportCategoryId)
            throws AkuraAppException {

        SportCategory objSportCategory = (SportCategory) commonService.findSportCategoryById(sportCategoryId);
        Student objStudent = (Student) studentService.findStudent(getSessionStudentId(session));
        Position objPosition = (Position) commonService.findPositionById(Integer.parseInt(strPositionId));
        
        StudentSport studentSport = new StudentSport();
        studentSport.setSportCategory(objSportCategory);
        studentSport.setPosition(objPosition);
        studentSport.setSportColour(colour);
        studentSport.setStatus(null);
        studentSport.setStudent(objStudent);
        studentSport.setYear(date);
        
        studentService.addStudentSport(studentSport);
    }
    
    /**
     * Update student sport details.
     * 
     * @param strPositionId - holds string type
     * @param colour - holds integer
     * @param selectedStudentSport - holds StudentSport object
     * @throws AkuraAppException - throw detail exception when update student sport
     */
    private void updateStudentSport(String strPositionId, int colour, StudentSport selectedStudentSport)
            throws AkuraAppException {

        Position objPosition = (Position) commonService.findPositionById(Integer.parseInt(strPositionId));
        
        selectedStudentSport.setPosition(objPosition);
        selectedStudentSport.setSportColour(colour);
        
        studentService.editStudentSport(selectedStudentSport);
    }
    
    /**
     * Read student id from session.
     * 
     * @param session - holds {@link HttpSession}
     * @return student id integer type
     */
    private int getSessionStudentId(HttpSession session) {

        int studentId = 0;
        if (session.getAttribute(STUDENT_ID) != null) {
            studentId = (Integer) session.getAttribute(STUDENT_ID);
        }
        return studentId;
    }
    
    /**
     * Find SportCategory Id for given sportId and SportSubId.
     * 
     * @param sportId - id of the sport object
     * @param sportSubId - id of the sportSub object
     * @return id of the SportCategory- integer
     * @throws AkuraAppException - if error occurs when save or update a student sport instance.
     */
    public int searchSportCategory(int sportId, int sportSubId) throws AkuraAppException {

        int sportCategoryId = 0;
        
        List<SportCategory> sportCategoryList = commonService.findSportCategoryObj(sportId, sportSubId);
        
        // sportCategory object already exists.
        if (sportCategoryList.size() > 0) {
            sportCategoryId = sportCategoryList.get(0).getSportCategoryId();
        } else {
            
            // create new SportCategory and getSportCategoryId
            sportCategoryId = getSportCategoryId(sportId, sportSubId);
        }
        return sportCategoryId;
    }
    
    /**
     * Create new SportCategory and getSportCategoryId.
     * 
     * @param sportId - holds integer type
     * @param sportSubId - holds integer type
     * @return sportCategoryId
     * @throws AkuraAppException - throw detail exception
     */
    private int getSportCategoryId(int sportId, int sportSubId) throws AkuraAppException {

        SportCategory sportCategoryObj = new SportCategory();
        sportCategoryObj.setSport(commonService.findSportById(sportId));
        sportCategoryObj.setSportSubCategory(commonService.findSportSubById(sportSubId));
        SportCategory sportCategory = commonService.addSportCategory(sportCategoryObj);
        
        return sportCategory.getSportCategoryId();
        
    }
    
    /**
     * Create or update student clubSociety details.
     * 
     * @param request - HttpServletRequest
     * @param session - HttpSession
     * @param model - ModelMap
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - if error occurs when save or update a student clubSociety instance.
     */
    @RequestMapping(value = ACTION_FOR_STUDENT_CLUB_SOCIETY, method = RequestMethod.POST)
    public String saveOrUpdateStudentClubsociety(HttpServletRequest request, HttpSession session, ModelMap model)
            throws AkuraAppException {

        String strMessage = null;
        StudentClubSociety selectedStudentClubSociety = null;
        
        String strClubSocietyId = request.getParameter(CLUB_SOCIETY_ID);
        String strPositionId = request.getParameter(POSITION_ID);
        String strStClubSocietyId = request.getParameter(SELECTED_STUDENT_CLUB_SOCIETY_ID);
        
        // validation
        if (Integer.parseInt(strClubSocietyId) == 0 || Integer.parseInt(strPositionId) == 0) {
            strMessage = new ErrorMsgLoader().getErrorMessage(AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
            request.setAttribute(POSITION_ID, strPositionId);
            request.setAttribute(CLUB_SOCIETY_ID, strClubSocietyId);
            model.addAttribute(MODEL_ATT_MESSAGE_CLUB, strMessage);
            if (!STRING_NULL.equals(strStClubSocietyId)) {
            	selectedStudentClubSociety =
            			studentService.findStudentClubSocietyObjById(Integer.parseInt(strStClubSocietyId));
            	model.addAttribute(MODEL_ATT_SELECTED_CLUB_OBJECT, selectedStudentClubSociety);
            }
            return populateStudentCoCurricularData(request, session, model);
        }
        
        int positionId = Integer.parseInt(strPositionId);
        int clubSocietyId = Integer.parseInt(strClubSocietyId);
        int studentClassInfoId = (Integer.parseInt(request.getParameter(SELECTED_GRADE)));
        
        StudentClassInfo studentClassInfo = studentService.findStudentClassInfoById(studentClassInfoId);
        
        String strYear = DateUtil.getStringYear(studentClassInfo.getYear());
        
        Date date = DateUtil.getDateTypeYearValue(strYear);
        
        
        try{
            
        // Modify selected student clubSociety.
        if (!STRING_NULL.equals(strStClubSocietyId)) {
            
            int stClubSocietyId = Integer.parseInt(strStClubSocietyId);
            selectedStudentClubSociety =
                    studentService.findStudentClubSocietyObjById(stClubSocietyId);
            
            int clubSocietyIdBeforeUpdate = selectedStudentClubSociety.getClubSociety().getClubSocietyId(); 

            if (!isExistsStudentClub(getSessionStudentId(session), clubSocietyId, date)) {
                
                // Set the student club society details
                setStudentClubSocietyDetail(session, clubSocietyId, date, selectedStudentClubSociety);
                
                // Set the student club society position and update the student club society
                updateStudentClubSocietyPosition(positionId, selectedStudentClubSociety, clubSocietyIdBeforeUpdate);
            } else {
                
                // clubSociety for student already exist. modify same object, with different position.
                if (selectedStudentClubSociety.getClubSociety().getClubSocietyId() == clubSocietyId) {
                    
                    // Set the student club society position and update the student club society
                    updateStudentClubSocietyPosition(positionId, selectedStudentClubSociety, clubSocietyIdBeforeUpdate);
                } else {
                    strMessage = new ErrorMsgLoader().getErrorMessage(REF_UI_STUDENT_CLUB_DESCRIPTION_EXIST);
                    model.addAttribute(MODEL_ATT_MESSAGE_CLUB, strMessage);
                    model.addAttribute(MODEL_ATT_SELECTED_CLUB_OBJECT, selectedStudentClubSociety);
                    request.setAttribute(POSITION_ID, strPositionId);
                    request.setAttribute(CLUB_SOCIETY_ID, strClubSocietyId);
                    selectedStudentClubSociety =
                    		studentService.findStudentClubSocietyObjById(Integer.parseInt(strStClubSocietyId));
                    model.addAttribute(MODEL_ATT_SELECTED_CLUB_OBJECT, selectedStudentClubSociety);
                    
                    return populateStudentCoCurricularData(request, session, model);
                }
            }
        } else {
            if (STRING_NULL.equals(strStClubSocietyId)) {
                
                // Add New clubSociety for student.
                if (!isExistsStudentClub(getSessionStudentId(session), clubSocietyId, date)) {
                    addStudentClubSociety(session, positionId, clubSocietyId, date);
                } else {
                    strMessage = new ErrorMsgLoader().getErrorMessage(REF_UI_STUDENT_CLUB_DESCRIPTION_EXIST);
                    model.addAttribute(MODEL_ATT_MESSAGE_CLUB, strMessage);
                    request.setAttribute(POSITION_ID, strPositionId);
                    request.setAttribute(CLUB_SOCIETY_ID, strClubSocietyId);
                    return populateStudentCoCurricularData(request, session, model);
                }
            }
        }
        
        }catch (StudentClubAndSocietyAchievementDeleteException e) {
            model.addAttribute(ERROR_DELETE_CLUB_SOCIETY, e.getErrorCode()); 
        }
        return populateStudentCoCurricularData(request, session, model);
    }
    
    /**
     * Handles add Student club society.
     * 
     * @param session - holds {@link HttpSession}
     * @param positionId - holds integer type
     * @param clubSocietyId - holds integer type
     * @param date - holds date type
     * @throws AkuraAppException - throw detailed exception
     */
    private void addStudentClubSociety(HttpSession session, int positionId, int clubSocietyId, Date date)
            throws AkuraAppException {

        ClubSociety objClubSociety = (ClubSociety) commonService.findClubSocietyById(clubSocietyId);
        Position objPosition = (Position) commonService.findPositionById(positionId);
        Student objStudent = (Student) studentService.findStudent(getSessionStudentId(session));
        
        StudentClubSociety studentClubSociety = new StudentClubSociety();
        studentClubSociety.setClubSociety(objClubSociety);
        studentClubSociety.setPosition(objPosition);
        studentClubSociety.setStatus(null);
        studentClubSociety.setStudent(objStudent);
        studentClubSociety.setYear(date);
        
        studentService.addStudentClubSociety(studentClubSociety);
    }
    
    /**
     * Sets Student club society properties.
     * 
     * @param session - holds {@link HttpSession}
     * @param clubSocietyId - holds integer type
     * @param date - holds date type
     * @param selectedStudentClubSociety - holds {@link StudentClubSociety}
     * @throws AkuraAppException - throw detailed exception
     */
    private void setStudentClubSocietyDetail(HttpSession session, int clubSocietyId, Date date,
            StudentClubSociety selectedStudentClubSociety) throws AkuraAppException {

        ClubSociety objClubSociety = (ClubSociety) commonService.findClubSocietyById(clubSocietyId);
        Student objStudent = (Student) studentService.findStudent(getSessionStudentId(session));
        selectedStudentClubSociety.setClubSociety(objClubSociety);
        selectedStudentClubSociety.setYear(date);
        selectedStudentClubSociety.setStatus(null);
        selectedStudentClubSociety.setStudent(objStudent);
    }
    
    /**
     * Sets the student club society position and update the student club society.
     * 
     * @param positionId - holds integer type
     * @param selectedStudentClubSociety - {@link StudentClubSociety}
     * @param clubSocietyIdBeforUpdate - the club society id of the student before update.
     * @throws AkuraAppException - throw detailed exception
     */
    private void updateStudentClubSocietyPosition(int positionId, StudentClubSociety selectedStudentClubSociety,
            int clubSocietyIdBeforUpdate)
            throws AkuraAppException {

        // Before modify this StduentClubSociety, check if it has achievements associated with it.
        List<Achievement> achievementList =
                studentService.findStudentClubAchievementObj(selectedStudentClubSociety.getStudent().getStudentId(),
                        "", clubSocietyIdBeforUpdate, selectedStudentClubSociety.getYear());
        
        if(!achievementList.isEmpty()){
            
            throw new StudentClubAndSocietyAchievementDeleteException(PropertyReader.getPropertyValue(
                    AKURA_ERROR_MSG_PROPERTY, CLUB_AND_SOCIETY_DELETE_ERROR));
        }

        
        Position objPosition = (Position) commonService.findPositionById(positionId);
        selectedStudentClubSociety.setPosition(objPosition);
        studentService.editStudentClubSocietyDetail(selectedStudentClubSociety);
    }
    
    /**
     * Delete Sport details.
     * 
     * @param request - HttpServletRequest
     * @param session - HttpSession
     * @param model - ModelMap
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - if error occurs when deleting a Sport instance.
     */
    @RequestMapping(value = ACTION_FOR_DELETE_STUDENT_CLUB_SOCIETY, method = RequestMethod.POST)
    public String deleteStudentClubsociety(HttpServletRequest request, HttpSession session, ModelMap model)
            throws AkuraAppException {

        String strStClubSocietyId = request.getParameter(SELECTED_STUDENT_CLUB_SOCIETY_ID);
        int stClubSocietyId = Integer.parseInt(strStClubSocietyId);
        
        try {
            studentService.deleteStudentClubSocietyDetail(stClubSocietyId);
        } catch (StudentClubAndSocietyAchievementDeleteException e) {
            model.addAttribute(ERROR_DELETE_CLUB_SOCIETY, e.getErrorCode());    
            
        } catch (DataAccessException ex) {
            LOG.error("ManageCoCurricularActivityController - error occured while deleting StudentClubSociety " + "-->"
                    + ex.toString());
            throw new AkuraAppException(AkuraWebConstant.HIBERNATE_INVALID_DEL_OPERATION, ex);
        } catch (AkuraAppException ex) {
            LOG.error("ManageCoCurricularActivityController - error occured while deleting studentClubSociety object "
                    + "-->" + ex.toString());
            throw new AkuraAppException(AkuraWebConstant.HIBERNATE_INVALID_DEL_OPERATION, ex);
        }
        
        return populateStudentCoCurricularData(request, session, model);
    }
    
    /**
     * Delete student Sport details.
     * 
     * @param request - HttpServletRequest
     * @param session - HttpSession
     * @param model - ModelMap
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - if error occurs when deleting a studentSport instance.
     */
    @RequestMapping(value = ACTION_FOR_DELETE_STUDENT_SPORT, method = RequestMethod.POST)
    public String deleteStudentSport(HttpServletRequest request, HttpSession session, ModelMap model)
            throws AkuraAppException {

        String strStSportId = request.getParameter(SELECTED_STUDENT_SPORT_ID);
        int stSportId = Integer.parseInt(strStSportId);
        
        try {
            studentService.deleteStudentSport(stSportId);
        } catch (DataAccessException ex) {
            LOG.error("ManageCoCurricularActivityController - error occured while deleting StudentSport " + "-->"
                    + ex.toString());
            throw new AkuraAppException(AkuraWebConstant.HIBERNATE_INVALID_DEL_OPERATION, ex);
        } catch (AkuraAppException ex) {
            LOG.error("ManageCoCurricularActivityController - error occured while deleting studentSport object "
                    + "-->" + ex.toString());
            throw new AkuraAppException(AkuraWebConstant.HIBERNATE_INVALID_DEL_OPERATION, ex);
        }
        
        return populateStudentCoCurricularData(request, session, model);
        
    }
    
    /**
     * Method is to return Sport list.
     * 
     * @return list of Sport
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_SPORT_LIST)
    public List<Sport> populateSportList() throws AkuraAppException {

        return SortUtil.sortSportList(commonService.getSportsList());
    }
    
    /**
     * Method is to return SportSub list.
     * 
     * @return list of SportSub
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_SPORT_SUB_LIST)
    public List<SportSub> populateSportSubList() throws AkuraAppException {

        return SortUtil.sortSportSubList(commonService.getSportSubsList());
    }
    
    /**
     * Method is to return ClubSociety list.
     * 
     * @return list of ClubSociety
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_CLUB_SOCIETY_LIST)
    public List<ClubSociety> populateClubSocietyList() throws AkuraAppException {

        return SortUtil.sortClubSocietyList(commonService.getClubsSocietiesList());
    }
    
    /**
     * Method is to return Position list.
     * 
     * @return list of Positions
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_POSITION_LIST)
    public List<Position> populatePositionList() throws AkuraAppException {

        return SortUtil.sortPositionList(commonService.getPositionList());
    }
    
    /**
     * Check whether studentSport is already added.
     * 
     * @param studentId - int
     * @param sportCategoryId - int
     * @param year - Date
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    private boolean isExistsStudentSport(int studentId, int sportCategoryId, Date year) throws AkuraAppException {

        boolean isExists = false;
        List<StudentSport> stSport = studentService.findStudentSportObj(studentId, sportCategoryId, year);
        
        if (stSport.size() > 0) {
            isExists = true;
        }
        return isExists;
    }
    
    /**
     * Check whether club for student is already added.
     * 
     * @param studentId - int
     * @param clubSocietyId - int
     * @param year - Date
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    private boolean isExistsStudentClub(int studentId, int clubSocietyId, Date year) throws AkuraAppException {

        boolean isExists = false;
        List<StudentClubSociety> stClubSociety =
                studentService.findStudentClubSocietyObj(studentId, clubSocietyId, year);
        if (stClubSociety.size() > 0) {
            isExists = true;
        }
        return isExists;
    }
    
    /**
     * Create or update Achievement object details.
     * 
     * @param request - HttpServletRequest
     * @param session - HttpSession
     * @param model - ModelMap
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - if error occurs when save or update a Achievement instance.
     */
    @RequestMapping(value = ACTION_FOR_SAVE_UPDATE_ACHIEVEMENT, method = RequestMethod.POST)
    public String saveOrUpdateAchievement(HttpServletRequest request, HttpSession session, ModelMap model)
            throws AkuraAppException {
        String strMessage = null;
        int studentId = getSessionStudentId(session);
        String strActivity = request.getParameter(ACTIVITY).trim();
        String strAchievement = request.getParameter(STR_ACHIEVEMENT).trim();
        String strAchievementId = request.getParameter(SELECTED_ACHIEVEMENT_ID);
        String flag = request.getParameter(ACH_FLAG);
        
        int studentClassInfoId = (Integer.parseInt(request.getParameter(SELECTED_GRADE)));
        StudentClassInfo studentClassInfo = studentService.findStudentClassInfoById(studentClassInfoId);
        String strYr = DateUtil.getStringYear(studentClassInfo.getYear());
        String studClubSocietyId = request.getParameter(STUD_CLUB_SOCIETY);
        String strIntAchievement = request.getParameter(CLUB_ACHIEVEMENT).trim();
        Date date = DateUtil.getDateTypeYearValue(strYr);
        
        // validation external achievement
        if (flag.equals(EXTERNAL_ACHIEVEMENT)) {
            if (strActivity == null || strActivity == STRING_NULL || strAchievement == null
                    || strAchievement == STRING_NULL) {
                strMessage = new ErrorMsgLoader().getErrorMessage(AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
                request.setAttribute(STUD_CLUB_SOCIETY, studClubSocietyId);
                request.setAttribute(ACTIVITY, strActivity);
                request.setAttribute(CLUB_ACHIEVEMENT, strIntAchievement);
                request.setAttribute(STR_ACHIEVEMENT, strAchievement);
                
                Achievement selectedAchivObject = getAchievementAtError(strAchievementId);
                model.addAttribute(MODEL_ATT_SELECTED_ACHIEVEMENT_OBJECT, selectedAchivObject);
                model.addAttribute(ACH_FLAG, flag);
                
                return populateErrorMessage(request, session, model, strMessage, flag);
            } else {
                if (strActivity.length() > LENGTH_DESCRIPTION) {
                    strMessage = new ErrorMsgLoader().getErrorMessage(REF_UI_FIELD_LENGTH);
                    request.setAttribute(STUD_CLUB_SOCIETY, studClubSocietyId);
                    request.setAttribute(ACTIVITY, strActivity);
                    request.setAttribute(CLUB_ACHIEVEMENT, strIntAchievement);
                    request.setAttribute(STR_ACHIEVEMENT, strAchievement);
                    
                    Achievement selectedAchivObject = getAchievementAtError(strAchievementId);
                    model.addAttribute(MODEL_ATT_SELECTED_ACHIEVEMENT_OBJECT, selectedAchivObject);
                    model.addAttribute(ACH_FLAG, flag);
                    
                    return populateErrorMessage(request, session, model, strMessage, flag);
                }
                
                Pattern alphabetandSpaceOnly = Pattern.compile(REGEX_PATTERN);
                Matcher makeMatch = alphabetandSpaceOnly.matcher(strActivity);
                
                if (makeMatch.find()) {
                    strMessage = new ErrorMsgLoader().getErrorMessage(REF_UI_FIELD_TYPE_ACHIEVEMENT);
                    request.setAttribute(STUD_CLUB_SOCIETY, studClubSocietyId);
                    request.setAttribute(ACTIVITY, strActivity);
                    request.setAttribute(CLUB_ACHIEVEMENT, strIntAchievement);
                    request.setAttribute(STR_ACHIEVEMENT, strAchievement);
                    
                    Achievement selectedAchivObject = getAchievementAtError(strAchievementId);                    
                    model.addAttribute(MODEL_ATT_SELECTED_ACHIEVEMENT_OBJECT, selectedAchivObject);
                    model.addAttribute(ACH_FLAG, flag);
                    
                    return populateErrorMessage(request, session, model, strMessage, flag);
                }
            }
        }
        
        // validation internal achievement
        if (flag.equals(INTERNAL_ACHIEVEMENT)) {
            if (strIntAchievement == null || strIntAchievement == STRING_NULL
                    || Integer.parseInt(studClubSocietyId) == 0) {
            	
                strMessage = new ErrorMsgLoader().getErrorMessage(AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
                request.setAttribute(STUD_CLUB_SOCIETY, studClubSocietyId);
                request.setAttribute(ACTIVITY, strActivity);
                request.setAttribute(CLUB_ACHIEVEMENT, strIntAchievement);
                request.setAttribute(STR_ACHIEVEMENT, strAchievement);
                
                Achievement selectedAchivObject = getAchievementAtError(strAchievementId);
                model.addAttribute(MODEL_ATT_SELECTED_ACHIEVEMENT_OBJECT, selectedAchivObject);
                model.addAttribute(ACH_FLAG, flag);
                
                return populateErrorMessage(request, session, model, strMessage, flag);
                
            } else if (strIntAchievement.length() > LENGTH_DESCRIPTION) {
            	
                strMessage = new ErrorMsgLoader().getErrorMessage(REF_UI_FIELD_LENGTH);
                request.setAttribute(STUD_CLUB_SOCIETY, studClubSocietyId);
                request.setAttribute(ACTIVITY, strActivity);
                request.setAttribute(CLUB_ACHIEVEMENT, strIntAchievement);
                request.setAttribute(STR_ACHIEVEMENT, strAchievement);
                
                Achievement selectedAchivObject = getAchievementAtError(strAchievementId);
                model.addAttribute(MODEL_ATT_SELECTED_ACHIEVEMENT_OBJECT, selectedAchivObject);
                model.addAttribute(ACH_FLAG, flag);
                
                return populateErrorMessage(request, session, model, strMessage, flag);
            }
        }
        
        int intstudentClubSocId = 0;
        if (studClubSocietyId != null && Integer.parseInt(studClubSocietyId) != 0) {
            intstudentClubSocId = Integer.parseInt(studClubSocietyId);
        }
        
        ClubSociety clubSocietyObj = null;
        if (intstudentClubSocId > 0) {
            clubSocietyObj = commonService.findClubSocietyById(intstudentClubSocId);
        }
        // edit achievement
        if (!STRING_NULL.equals(strAchievementId)) {
            int achievementId = Integer.parseInt(strAchievementId);
            
            Achievement selectedAchivObject = studentService.findAchievementById(achievementId);
            // if this Achievement already not exist editing.
            if (!isExistsAchievement(studentId, strAchievement, strActivity, date)
                    && !isExistsClubAchievement(studentId, strIntAchievement, intstudentClubSocId, date)) {
                
                if (strIntAchievement != null && strIntAchievement != STRING_NULL) {
                    selectedAchivObject.setDescription(strIntAchievement);
                }
                if (strAchievement != null && strAchievement != STRING_NULL) {
                    selectedAchivObject.setDescription(strAchievement);
                }
                selectedAchivObject.setActivity(strActivity);
                selectedAchivObject.setClubSociety(clubSocietyObj);
                selectedAchivObject.setYear(date);
                
                studentService.editAchievement(selectedAchivObject);
                
            } else {
                // achievement already exist.
                strMessage = new ErrorMsgLoader().getErrorMessage(REF_UI_STUDENT_ACHIEVEMENT_EXIST);
                model.addAttribute(MODEL_ATT_ACHIEVEMENT_MESSAGE, strMessage);
                model.addAttribute(MODEL_ATT_SELECTED_ACHIEVEMENT_OBJECT, selectedAchivObject);
                model.addAttribute(ACH_FLAG, flag);
                
                request.setAttribute(STUD_CLUB_SOCIETY, studClubSocietyId);
                request.setAttribute(ACTIVITY, strActivity);
                request.setAttribute(CLUB_ACHIEVEMENT, strIntAchievement);
                request.setAttribute(STR_ACHIEVEMENT, strAchievement);
                return populateStudentCoCurricularData(request, session, model);
                
            }
            
        } else {
            if (STRING_NULL.equals(strAchievementId)) {
                // add new
                if (!isExistsAchievement(studentId, strAchievement, strActivity, date)
                        && !isExistsClubAchievement(studentId, strIntAchievement, intstudentClubSocId, date)) {
                    Achievement achievement = new Achievement();
                    
                    Student objStudent = (Student) studentService.findStudent(studentId);
                    
                    if (strAchievement != null && strAchievement != STRING_NULL) {
                        achievement.setDescription(strAchievement);
                    }
                    if (strIntAchievement != null && strIntAchievement != STRING_NULL) {
                        achievement.setDescription(strIntAchievement);
                    }
                    achievement.setActivity(strActivity);
                    achievement.setStudent(objStudent);
                    achievement.setYear(date);
                    achievement.setClubSociety(clubSocietyObj);
                    achievement.setAcademic(false);
                    
                    studentService.addAchievement(achievement);
                    
                } else {
                    strMessage = new ErrorMsgLoader().getErrorMessage(REF_UI_STUDENT_ACHIEVEMENT_EXIST);
                    request.setAttribute(STUD_CLUB_SOCIETY, studClubSocietyId);
                    request.setAttribute(ACTIVITY, strActivity);
                    request.setAttribute(CLUB_ACHIEVEMENT, strIntAchievement);
                    request.setAttribute(STR_ACHIEVEMENT, strAchievement);
                    return populateErrorMessage(request, session, model, strMessage, flag);
                }
            }
        }
        
        return populateStudentCoCurricularData(request, session, model);
    }
    
    /**
     * Return relevant Achievement object considering Add/Update operation.
     * 
     * @param strAchievementId String
     * @return Achievement object
     * @throws AkuraAppException - if error occurs when save or update a Achievement instance.
     */
    private Achievement getAchievementAtError(String strAchievementId) throws AkuraAppException {
    	
    	Achievement achievement;
        if (!STRING_NULL.equals(strAchievementId)) {
            int achievementId = Integer.parseInt(strAchievementId);
            achievement = studentService.findAchievementById(achievementId);
        } else {
        	achievement = new Achievement();
        	achievement.setAchievementId(ZERO);
        }
        
        return achievement;
    	
    }
    
    /**
     * Populate error messages related to achievement.
     * 
     * @param request - holds {@link HttpServletRequest}
     * @param session - holds {@link HttpSession}
     * @param model - holds {@link ModelMap}
     * @param strMessage - holds string type
     * @param flag - holds string type
     * @return error messages
     * @throws AkuraAppException - Detailed exception
     */
    private String populateErrorMessage(HttpServletRequest request, HttpSession session, ModelMap model,
            String strMessage, String flag) throws AkuraAppException {

        model.addAttribute(MODEL_ATT_ACHIEVEMENT_MESSAGE, strMessage);
        model.addAttribute(ACH_FLAG, flag);
        return populateStudentCoCurricularData(request, session, model);
    }
    
    /**
     * Delete Achievement details.
     * 
     * @param request - HttpServletRequest
     * @param session - HttpSession
     * @param model - ModelMap
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - if error occurs when deleting a Achievement instance.
     */
    @RequestMapping(value = ACTION_FOR_DELETE_ACHIEVEMENT, method = RequestMethod.POST)
    public String deleteAchievement(HttpServletRequest request, HttpSession session, ModelMap model)
            throws AkuraAppException {

        int achievementId = Integer.parseInt(request.getParameter(SELECTED_ACHIEVEMENT_ID));
        try {
            studentService.deleteAchievement(achievementId);
        } catch (DataAccessException ex) {
            LOG.error("ManageCoCurricularController - error occured while deleting achievement " + "-->"
                    + ex.toString());
            throw new AkuraAppException(AkuraWebConstant.HIBERNATE_INVALID_DEL_OPERATION, ex);
        } catch (AkuraAppException ex) {
            LOG.error("ManageCoCurricularController - error occured while deleting achievement object " + "-->"
                    + ex.toString());
            throw new AkuraAppException(AkuraWebConstant.HIBERNATE_INVALID_DEL_OPERATION, ex);
        }
        
        return populateStudentCoCurricularData(request, session, model);
    }
    
    /**
     * Check whether Achievement is already added.
     * 
     * @param studentId - student Id
     * @param strDescription - achievement
     * @param strActivity - activity
     * @param year - Date
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    private boolean isExistsAchievement(int studentId, String strDescription, String strActivity, Date year)
            throws AkuraAppException {

        boolean isExists = false;
        List<Achievement> stAchievement =
                studentService.findStudentAchievementObj(studentId, strDescription, strActivity, year);
        
        if (stAchievement.size() > 0) {
            isExists = true;
        }
        return isExists;
        
    }
    
    /**
     * Check whether Achievement is already added for the club and society.
     * 
     * @param studentId - student Id
     * @param strDescription - achievement
     * @param clubSocietyId - int
     * @param year - Date
     * @return true if it already exist else false
     * @throws AkuraAppException - Detailed exception
     */
    private boolean isExistsClubAchievement(int studentId, String strDescription, int clubSocietyId, Date year)
            throws AkuraAppException {

        boolean isExists = false;
        List<Achievement> stAchievement =
                studentService.findStudentClubAchievementObj(studentId, strDescription, clubSocietyId, year);
        
        if (stAchievement.size() > 0) {
            isExists = true;
        }
        return isExists;
        
    }
    
    /**
     * populate data for co curricular page for relevant student for a selected year.
     * 
     * @param request - HttpServletRequest
     * @param session - HttpSession
     * @param model - ModelMap
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - if error occurs when deleting a Achievement instance.
     */
    @RequestMapping(value = ACTION_FOR_POPULATE_DATA, method = RequestMethod.POST)
    public String populateStudentCoCurricularData(HttpServletRequest request, HttpSession session, ModelMap model)
            throws AkuraAppException {

        int studentId = 0;
        Date date = null;
        String strYr = null;
        int intYear = 0;
        String studentGrade = null;
        int studentClassInfoId = 0;
        String selectedStudClassId = null;
        
        if (session.getAttribute(STUDENT_ID) != null) {
            studentId = (Integer) session.getAttribute(STUDENT_ID);
        }
        if (request.getParameter(SELECTED_GRADE) != null) {
            studentClassInfoId = (Integer.parseInt(request.getParameter(SELECTED_GRADE)));
            
            StudentClassInfo studentClassInfo = studentService.findStudentClassInfoById(studentClassInfoId);
            strYr = DateUtil.getStringYear(studentClassInfo.getYear());
            
            selectedStudClassId = studentClassInfo.getClassGrade().getGrade().getDescription();
            date = DateUtil.getDateTypeYearValue(strYr);
            
            if (DateUtil.getStringYear(studentClassInfo.getYear()).equals(DateUtil.getStringYear(new Date()))) {
                
                model.addAttribute("EnableAddEditDelete", "none");
                
            }
        } else {
            Date currentDate = new Date();
            SimpleDateFormat simpleDateformat = new SimpleDateFormat(YYYY);
            strYr = simpleDateformat.format(currentDate);
            
            intYear = Integer.parseInt(strYr);
            List<StudentClassInfo> studentClassInfo = studentService.getStudentClassInfoByStudentId(studentId, intYear);
            
            if (!studentClassInfo.isEmpty()) {
                studentGrade = studentClassInfo.get(0).getClassGrade().getGrade().getDescription();
            }
            
            selectedStudClassId = studentGrade;
            date = DateUtil.getDateTypeYearValue(strYr);
            model.addAttribute("EnableAddEditDelete", "none");
        }
        
        Student studentObj = studentService.findStudent(studentId);
        Date fYear = studentObj.getFirstSchoolDay();
        int firstYear = DateUtil.getYearFromDate(fYear);
        
        List<StudentSport> studentSports = studentService.getSportsListForStudent(studentId, date);
        
        List<Achievement> achievementListNew = new ArrayList<Achievement>();
        List<Achievement> achievementList = studentService.getAchievementsListForStudent(studentId, date);
        for (Achievement achivementObj : achievementList) {
            if (!achivementObj.isAcademic()) {
                achievementListNew.add(achivementObj);
            }
        }
        List<StudentClubSociety> studentClubSocieties = studentService.getClubSocietyListForStudent(studentId, date);
        
        int yrCount = this.yearCount(studentId);
        
        setupStudentRatingDetails(model, session, date, studentId);
        
        model.addAttribute(MODEL_ATT_STUDENT_SPORT_LIST, studentSports);
        model.addAttribute(MODEL_ATT_ACHIEVEMENT_LIST, achievementListNew);
        model.addAttribute(MODEL_ATT_STUDENT_CLUB_SOCIETY_LIST, studentClubSocieties);
        model.addAttribute(MODEL_ATT_SELECTED_ID, selectedStudClassId);
        model.addAttribute(MODEL_ATT_DATE, strYr);
        model.addAttribute(YEAR_COUNT, yrCount);
        model.addAttribute(FIRST_YEAR, firstYear);
        
        return VIEW_GET_STUDENT_ACTIVITY;
    }
    
    /**
     * Setups the faith life and academic details rating for student.
     * 
     * @param model the model.
     * @param date - Date
     * @param session the session.
     * @param studentId - int
     * @throws AkuraAppException - Detailed exception
     */
    private void setupStudentRatingDetails(ModelMap model, HttpSession session, Date date, int studentId)
            throws AkuraAppException {

        double academicLifeAverage = 0.0;
        double faithLifeAverage = 0.0;
        double attendanceAverage = 0.0;
        if (date.equals(DateUtil.getDateTypeYearValue(Integer.toString(DateUtil.currentYearOnly())))) {
            if (session.getAttribute(AVERAGE_FAITH_LIFE_RATING) != null) {
                faithLifeAverage = (Double) session.getAttribute(AVERAGE_FAITH_LIFE_RATING);
                model.addAttribute(MODEL_ATT_STUDENT_FAITH_LIFE, (int) Math.round(faithLifeAverage));
            }
            if (session.getAttribute(AVERAGE_ACADEMIC_LIFE_RATING) != null) {
                academicLifeAverage = (Double) session.getAttribute(AVERAGE_ACADEMIC_LIFE_RATING);
                model.addAttribute(MODEL_ATT_STUDENT_ACADEMIC_LIFE, (int) Math.round(academicLifeAverage));
            }
            if (session.getAttribute(AVERAGE_ATTENDANCE_RATING) != null) {
                attendanceAverage = (Double) session.getAttribute(AVERAGE_ATTENDANCE_RATING);
                model.addAttribute(MODEL_ATT_ATTENDANCE_RATING, (int) Math.round(attendanceAverage));
            }
        } else {
            Map<String, Double> averageMap = studentLoginDelegate.populateStudentProgressBar(studentId, date);
            
            faithLifeAverage = averageMap.get(AVERAGE_FAITH_LIFE_RATING);
            model.addAttribute(MODEL_ATT_STUDENT_FAITH_LIFE, (int) Math.round(faithLifeAverage));
            
            academicLifeAverage = averageMap.get(AVERAGE_ACADEMIC_LIFE_RATING);
            model.addAttribute(MODEL_ATT_STUDENT_ACADEMIC_LIFE, (int) Math.round(academicLifeAverage));
            
            attendanceAverage = averageMap.get(AVERAGE_ATTENDANCE_RATING);
            model.addAttribute(MODEL_ATT_ATTENDANCE_RATING, (int) Math.round(attendanceAverage));
        }
        
    }
    
    /**
     * Method is to return StudentClassInfo list.
     * 
     * @param session - HttpSession session
     * @return list of StudentClassInfo
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_STUDENT_GRADE_LIST)
    public List<StudentClassInfo> populateStudentGradeList(HttpSession session) throws AkuraAppException {

        int studentId = 0;
        if (session.getAttribute(STUDENT_ID) != null) {
            studentId = (Integer) session.getAttribute(STUDENT_ID);
        }
        List<StudentClassInfo> gredeList = studentService.getStudentClassInfoByStudentId2(studentId);
        List<StudentClassInfo> selectedGradeList = new ArrayList<StudentClassInfo>();
        
        for (StudentClassInfo classInfo : gredeList) {
            if (!classInfo.getYear().after(DateUtil.currentYear())) {
                selectedGradeList.add(classInfo);
            }
            
        }
        
        return selectedGradeList;
        
    }
    
    /**
     * handle GET requests for CoCurricular-details view.
     * 
     * @param request - HttpServletRequest
     * @param model - ModelMap attribute.
     * @param session - HttpSession
     * @return the name of the view.
     * @throws AkuraAppException when exception occurs
     */
    @RequestMapping(value = ACTION_FOR_CO_CURRICULAR_DETAILS, method = RequestMethod.GET)
    public String showYearList(HttpServletRequest request, HttpSession session, ModelMap model)
            throws AkuraAppException {

        if (session.getAttribute(STUDENT_ID) != null) {
            int studentId = (Integer) session.getAttribute(STUDENT_ID);
            
            Student studentObj = studentService.findStudent(studentId);
            Date fYear = studentObj.getFirstSchoolDay();
            int firstYear = DateUtil.getYearFromDate(fYear);
            int yrCount = this.yearCount(studentId);
            model.addAttribute(YEAR_COUNT, yrCount);
            model.addAttribute(FIRST_YEAR, firstYear);
        }
        return populateStudentCoCurricularData(request, session, model);
        
    }
    
    /**
     * method to find number of year the student in school.
     * 
     * @param studentId - integer
     * @return number of year in integer.
     * @throws AkuraAppException when exception occurs
     */
    private int yearCount(int studentId) throws AkuraAppException {

        int yearCount = 0;
        // Retrieving the student object and the first school year
        if (studentId > 0) {
            Student studentObj = studentService.findStudent(studentId);
            Date fYear = studentObj.getFirstSchoolDay();
            int firstYear = DateUtil.getYearFromDate(fYear);
            
            Calendar cal = Calendar.getInstance();
            int currentYear = cal.get(Calendar.YEAR);
            if (currentYear < firstYear) {
                yearCount = firstYear - currentYear; // Year difference
            } else {
                yearCount = currentYear - firstYear; // Year difference
            }
        }
        return yearCount;
    }
    
}
