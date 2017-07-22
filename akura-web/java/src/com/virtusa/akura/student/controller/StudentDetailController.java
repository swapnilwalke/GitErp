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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.virtusa.akura.api.dto.AdminDetails;
import com.virtusa.akura.api.dto.BloodGroup;
import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.Country;
import com.virtusa.akura.api.dto.House;
import com.virtusa.akura.api.dto.MethodOfTravel;
import com.virtusa.akura.api.dto.Nationality;
import com.virtusa.akura.api.dto.ParentDetails;
import com.virtusa.akura.api.dto.Race;
import com.virtusa.akura.api.dto.Religion;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentClassInfo;
import com.virtusa.akura.api.dto.StudentDetails;
import com.virtusa.akura.api.dto.StudentDisability;
import com.virtusa.akura.api.dto.StudyMedium;
import com.virtusa.akura.api.dto.UserInfo;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.delegate.LoginDelegate;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.service.UserService;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.student.validator.StudentDetailValidator;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PhoneNumberValidateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;
import com.virtusa.akura.util.StaticDataUtil;

/**
 * Student Detail Controller.
 */
@Controller
@SessionAttributes("student")
public class StudentDetailController {
    
    /** Query string parameter name for redirect with error message . */
    private static final String QUERY_STRING_UPDATE = "?successUpdate=";
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(StudentDetailController.class);
    
    /** String attribute for holding DEFALUT_DATE_FORMAT. */
    private static final String DEFALUT_DATE_FORMAT = "yyyy-MM-dd";
    
    /** view get method student details page. */
    private static final String VIEW_GET_STUDENT_DETAIL_PAGE = "student/studentDetails";
    
    /** property file name. */
    private static final String SYSTEM_CONFIG_PROPERTIES = "systemConfig";
    
    /** key to get the image width from property file. */
    private static final String PROFILE_IMAGE_WIDTH = "imageFile.width";
    
    /** key to get the image height from property file. */
    private static final String PROFILE_IMAGE_HEIGHT = "imageFile.height";
    
    /** model attribute of student faithLife average value. */
    private static final String MODEL_ATT_STUDENT_FAITH_LIFE = "averageFaithLife";
    
    /** model attribute of student academic life average value. */
    private static final String MODEL_ATT_STUDENT_ACADEMIC_LIFE = "averageAcademicLife";
    
    /** model attribute of religion list. */
    private static final String MODEL_ATT_RELIGION_LIST = "religionList";
    
    /** model attribute of medium list. */
    private static final String MODEL_ATT_MEDIUM_LIST = "studyMediumList";
    
    /** model attribute of house list. */
    private static final String MODEL_ATT_HOUSE_LIST = "houseList";
    
    /** model attribute of city list. */
    private static final String MODEL_ATT_CITY_LIST = "cityList";
    
    /** model attribute of nationality list. */
    private static final String MODEL_ATT_NATIONALITY_LIST = "nationalityList";
    
    /** model attribute of blood group list. */
    private static final String MODEL_ATT_BOOLD_GROUP_LIST = "bloodGroupList";
    
    /** model attribute of method of travel list. */
    private static final String MODEL_ATT_METHOD_OF_TRAVEL_LIST = "methodOfTravelList";
    
    /** model attribute of emergency contact relation list. */
    private static final String MODEL_ATT_EMERGENCY_CONTACT_RELATION_LIST = "emergencyContactRelationList";
    
    /** request mapping value for new student detail. */
    private static final String REQ_MAP_NEW_STUDENT_DETAIL = "/newStudentDetails.htm";
    
    /** request mapping value for student detail. */
    private static final String REQ_MAP_STUDENT_DETAIL = "/studentDetails.htm";
    
    /** request mapping value for save student detail. */
    private static final String REQ_MAP_SAVE_STUDENT_DETAIL = "/saveStudentDetails.htm";
    
    /** model attribute of image height. */
    private static final String MODEL_ATT_IMAGE_HEIGHT = "ImageHeight";
    
    /** model attribute of image width. */
    private static final String MODEL_ATT_IMAGE_WIDTH = "ImageWidth";
    
    /** view post method new student details. */
    private static final String VIEW_NEW_STUDENT_DETAIL = "redirect:newStudentDetails.htm";
    
    /** view post method student details. */
    private static final String VIEW_STUDENT_DETAIL = "redirect:studentDetails.htm";
    
    /** key to define the image folder path. */
    private static final String IMAGE_FOLDER_PATH = "imageFolder.path";
    
    /** Holds the value for appserver.home. */
    private static final String APPSERVER_HOME = "appserver.home";
    
    /** key to define the image file format. */
    private static final String FILE_EXT = ".jpg";
    
    /** key to define the profile image path. */
    private static final String RESOURCES_PROFILE_IMAGES = "resources/profileImages/";
    
    /** model attribute of image path. */
    private static final String MODEL_ATT_IMAGE_PATH = "ImagePath";
    
    /** model attribute of student disability. */
    private static final String MODEL_ATT_STUDENT_DISABILITY = "studentDisability";
    
    /** key to define the averageAcademicLifeRating. */
    private static final String AVERAGE_ACADEMIC_LIFE_RATING = "averageAcademicLifeRating";
    
    /** key to define the averageAcademicLifeRating. */
    private static final String AVERAGE_ATTENDANCE_RATING = "averageAttendanceRating";
    
    /** key to define the averageAcademicLifeRating. */
    private static final String MODEL_ATT_ATTENDANCE_RATING = "attendanceRating";
    
    /** key to define the averageFaithLifeRating. */
    private static final String AVERAGE_FAITH_LIFE_RATING = "averageFaithLifeRating";
    
    /** key to define the user. */
    private static final String USER = "user";
    
    /** key to define the default image. */
    private static final String RESOURCES_NO_PROFILE_IMAGE = "resources/profileImages/no_profile_image.jpg";
    
    /** model attribute of student default image. */
    private static final String MODEL_ATT_DEFAULT_IMAGE = "defaultImage";
    
    /** model attribute of student id check. */
    private static final String MODEL_ATT_STUDENT_ID_CHECK = "studentIdCheck";
    
    /** key to define student grade. */
    private static final String STUDENT_GRADE = "studentGrade";
    
    /** key to define student id. */
    private static final String STUDENT_ID = "studentId";
    
    /** key to define studentClass. */
    private static final String STUDENT_CLASS = "studentClass";
    
    /** key to define student id. */
    private static final String SIBLING_ADMISSIONNO = "siblingAdmitionNo";
    
    /** key to define event value. */
    private static final String EVENT_VALUE = "eventValue";
    
    /** model attribute of string message. */
    private static final String MODEL_ATT_MESSAGE = "message";
    
    /** model attribute of student. */
    private static final String MODEL_ATT_STUDENT = "student";
    
    /** key to define the message when student profile created. */
    private static final String MESSAGE_STUDENT_PROFILE_CREATED = "STUDENT.PROFILE.CREATED";
    
    /** key to define the error message when admission number value violated. */
    private static final String ERR_TUDENT_ADMISSIONNO_VIOLATE = "REF.UI.STUDENT.ADMISSIONNO.VIOLATEERROR";
    
    /** key to define the error message when admission number value violated. */
    private static final String ERR_SIBLING_ADMISSIONNO_VIOLATE = "REF.UI.STUDENT.SIBLING.ADMISSIONNO.VIOLATEERROR";
    
    /** key to define the error message when admission number value duplicate. */
    private static final String ERR_STUDENT_ADMISSIONNO_DUPLCATE = "REF.UI.STUDENT.ADMISSIONNO.DUPLCATEERROR";
    
    /** key to define the detail error message when retrieve file. */
    private static final String ERROR_WHILE_RETRIEVING_FILE = "Error while retrieving the file --> ";
    
    /** key to define the relationship guardian. */
    private static final String GUARDIAN = "Guardian";
    
    /** key to define the relationship mother. */
    private static final String MOTHER = "Mother";
    
    /** key to define the relationship father. */
    private static final String FATHER = "Father";
    
    /** model attribute of country list . */
    private static final String COUNTRY_LIST = "countryList";
    
    /** model attribute of country list . */
    private static final String RACE_LIST = "raceList";
    
    /** Represents the key for the image database size. */
    private static final String IMAGE_DATABASE_SIZE = "IMAGE.DATABASE.SIZE";
    
    /** Represents the key for the error message. */
    private static final String ERROR_MESSAGE = "errorMessage";
    
    /** Represents the string "studentGradeClass". */
    private static final String STUDENT_GRADE_CLASS = "studentGradeClass";
    
    /** Represents the string "subjectsExist". */
    private static final String SUBJECTS_EXIST = "subjectsExist";
    
    /** Represents the string "subjectsExistOrNot". */
    private static final String SUBJECTS_EXIST_OR_NOT = "subjectsExistOrNot";
    
    /** Represents the string successfully updated message. */
    private static final String COMMON_MESSAGE_SUCCESSFULLY_UPDATED = "COMMON.MESSAGE.SUCCESSFULLY.UPDATED";
    
    /** The Constant COUNTRY_LIST. */
    private static final String PHONE_COUNTRY_LIST = "countryListPhone";
    
    /** The Constant SELECTED_CLASS. */
    private static final String SELECTED_COUNTRYCODE_RES = "selectedCountryCodeRes";
    
    /** The Constant SELECTED_CLASS. */
    private static final String SELECTED_COUNTRYCODE_MOB = "selectedCountryCodeMob";
    
    /** The Constant SELECTED_CLASS. */
    private static final String SELECTED_COUNTRYCODE_EMG_RES = "selectedCountryCodeEmgRes";
    
    /** The Constant SELECTED_CLASS. */
    private static final String SELECTED_COUNTRYCODE_EMG_MOB = "selectedCountryCodeEmgMob";
    
    /** The Constant SELECTED_CLASS. */
    private static final String SELECTED_COUNTRYCODE_EMG_OFFICE = "selectedCountryCodeEmgOffice";
    
    /** key to hold message when record added. */
    private static final String MESSAGE_PHONE_NO_COUNTRY_MISMATCH = "STAFF.PHONE.COUNTRY.NO.MATCH";
    
    /** The String of Model attribute. */
    private static final String MODEL_ATT_SELECTED_RES_COUNTRY = "selectedResCountry";
    
    /** The String of Model attribute. */
    private static final String MODEL_ATT_SELECTED_MOB_COUNTRY = "selectedMobCountry";
    
    /** The String of Model attribute. */
    private static final String MODEL_ATT_SELECTED_EMG_MOB_COUNTRY = "selectedEmgMobCountry";
    
    /** The String of Model attribute. */
    private static final String MODEL_ATT_SELECTED_EMG_RES_COUNTRY = "selectedEmgResCountry";
    
    /** The String of Model attribute. */
    private static final String MODEL_ATT_SELECTED_EMG_OFF_COUNTRY = "selectedEmgOffCountry";
    
    /** CommonService attribute for holding commonService. */
    private CommonService commonService;
    
    /** StudentService attribute for holding studentService. */
    private StudentService studentService;
    
    /** StudentDetailValidator attribute for holding studentDetailValidator. */
    private StudentDetailValidator studentDetailValidator;
    
    /** Represents an instance of StudentLoginService. */
    private LoginDelegate studentLoginDelegate;
    
    /** UserService attribute for holding userService. */
    private UserService userService;
    
    /**
     * Sets an instance of StudentLoginService.
     * 
     * @param studentLoginDelegateVal - an instance of StudentLoginService.
     */
    public void setStudentLoginDelegate(LoginDelegate studentLoginDelegateVal) {
    
        this.studentLoginDelegate = studentLoginDelegateVal;
    }
    
    /**
     * setter method from CommonService.
     * 
     * @param commonServiceVal - CommonService
     */
    
    public void setCommonService(CommonService commonServiceVal) {
    
        this.commonService = commonServiceVal;
    }
    
    /**
     * setter for StudentService.
     * 
     * @param studentServiceVal - studentService
     */
    public void setStudentService(StudentService studentServiceVal) {
    
        this.studentService = studentServiceVal;
    }
    
    /**
     * Setter method for studentDetailValidator.
     * 
     * @param studentDetailValidatorVal the studentDetailValidator to set
     */
    public void setStudentDetailValidator(StudentDetailValidator studentDetailValidatorVal) {
    
        this.studentDetailValidator = studentDetailValidatorVal;
    }
    
    /**
     * Set the userService to inject the userService.
     * 
     * @param userServiceValue the userService to set
     */
    public void setUserService(UserService userServiceValue) {
    
        this.userService = userServiceValue;
    }
    
    /**
     * intiBinder method is to bind date class with the date format.
     * 
     * @param binder - data binder used to register the Date objects.
     */
    @InitBinder
    public void initBinder(final WebDataBinder binder) {
    
        SimpleDateFormat dateFormat = new SimpleDateFormat(DEFALUT_DATE_FORMAT);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        
    }
    
    /**
     * Method is to return religion list reference data.
     * 
     * @throws AkuraAppException - AkuraAppException
     * @return religionList - Religion reference data.
     */
    @ModelAttribute(MODEL_ATT_RELIGION_LIST)
    public List<Religion> populateReligionList() throws AkuraAppException {
    
        List<Religion> religionList = commonService.getReligionList();
        SortUtil.sortReligionList(religionList);
        return religionList;
    }
    
    /**
     * Method is to return studyMedium list reference data.
     * 
     * @throws AkuraAppException - AkuraAppException
     * @return studyMediumList - studyMedium reference data.
     */
    @ModelAttribute(MODEL_ATT_MEDIUM_LIST)
    public List<StudyMedium> populateStudyMediumList() throws AkuraAppException {
    
        List<StudyMedium> studyMediumList = commonService.getStudyMediumList();
        SortUtil.sortStudyMedium(studyMediumList);
        return studyMediumList;
    }
    
    /**
     * Method is to return house reference data.
     * 
     * @throws AkuraAppException - AkuraAppException
     * @return houseList - house reference list.
     */
    @ModelAttribute(MODEL_ATT_HOUSE_LIST)
    public List<House> populateHouseList() throws AkuraAppException {
    
        List<House> houseList = commonService.getHouseList();
        SortUtil.sortHouseList(houseList);
        return houseList;
    }
    
    /**
     * Method is to return house reference data.
     * 
     * @throws AkuraAppException - AkuraAppException
     * @return cityList - house reference list.
     */
    @ModelAttribute(MODEL_ATT_CITY_LIST)
    public List<City> populateCityList() throws AkuraAppException {
    
        List<City> cityList = commonService.getCityList();
        SortUtil.sortCityList(cityList);
        return cityList;
    }
    
    /**
     * Method is to return nationality reference data.
     * 
     * @throws AkuraAppException - AkuraAppException
     * @return nationalityList - nationality reference list.
     */
    @ModelAttribute(MODEL_ATT_NATIONALITY_LIST)
    public final List<Nationality> populateNationalityList() throws AkuraAppException {
    
        List<Nationality> nationalityList = commonService.getNationalityList();
        SortUtil.sortNationalityList(nationalityList);
        return nationalityList;
    }
    
    /**
     * Method is to return bloodGroup reference data.
     * 
     * @throws AkuraAppException - AkuraAppException
     * @return bloodGroupList - bloodGroup reference list.
     */
    @ModelAttribute(MODEL_ATT_BOOLD_GROUP_LIST)
    public final List<BloodGroup> populateBloodGroupList() throws AkuraAppException {
    
        List<BloodGroup> bloodGroupList = commonService.getBloodGroupList();
        SortUtil.sortBloodGroupList(bloodGroupList);
        return bloodGroupList;
    }
    
    /**
     * Method is to return methodOfTravel reference data.
     * 
     * @throws AkuraAppException - AkuraAppException
     * @return MethodOfTravel - MethodOfTravel reference list.
     */
    @ModelAttribute(MODEL_ATT_METHOD_OF_TRAVEL_LIST)
    public List<MethodOfTravel> populateMethodOfTravelList() throws AkuraAppException {
    
        List<MethodOfTravel> methodOfTravelList = commonService.getMethodOfTravelList();
        SortUtil.sortMethodOfTravelList(methodOfTravelList);
        return methodOfTravelList;
    }
    
    /**
     * Method is to return emergency relationships data.
     * 
     * @throws AkuraAppException - AkuraAppException
     * @return relationshipList - relationship list.
     */
    @ModelAttribute(MODEL_ATT_EMERGENCY_CONTACT_RELATION_LIST)
    public List<String> populateEmergencyContactRelationList() throws AkuraAppException {
    
        List<String> relationshipList = new ArrayList<String>();
        relationshipList.add(FATHER);
        relationshipList.add(MOTHER);
        relationshipList.add(GUARDIAN);
        
        return relationshipList;
    }
    
    /**
     * handle GET requests for New Student Details Form.
     * 
     * @param model - ModelMap
     * @param session - HttpSession
     * @param request - HttpServletRequest
     * @return the name of the view.
     * @throws AkuraAppException - AkuraAppException
     */
    @RequestMapping(REQ_MAP_NEW_STUDENT_DETAIL)
    public String addNewStudentDetailForm(ModelMap model, HttpSession session, HttpServletRequest request)
            throws AkuraAppException {
    
        Student student = (Student) model.get(MODEL_ATT_STUDENT);
        
        if (request.getParameter(EVENT_VALUE) == null && student != null && student.getStudentId() > 0) {
            String strMessage = new ErrorMsgLoader().getErrorMessage(MESSAGE_STUDENT_PROFILE_CREATED);
            model.addAttribute(MODEL_ATT_MESSAGE, strMessage);
        }
        
        student = new Student();
        student.setIsOldBoy(Boolean.FALSE);
        student.setStatusId(1);
        session.removeAttribute(STUDENT_ID);
        session.removeAttribute(MODEL_ATT_STUDENT);
        session.removeAttribute(STUDENT_GRADE);
        session.removeAttribute(STUDENT_CLASS);
        session.removeAttribute(STUDENT_GRADE_CLASS);
        model.addAttribute(MODEL_ATT_STUDENT, student);
        model.addAttribute(MODEL_ATT_STUDENT_ID_CHECK, Boolean.TRUE);
        model.addAttribute(MODEL_ATT_DEFAULT_IMAGE, RESOURCES_NO_PROFILE_IMAGE);
        return VIEW_GET_STUDENT_DETAIL_PAGE;
    }
    
    /**
     * handle GET requests for Student_details view.
     * 
     * @param model - ModelMap
     * @param session - HttpSession
     * @param response - HttpServletResponse
     * @param request - represents an instance of HttpServletRequest
     * @return the name of the view.
     * @throws AkuraAppException - throws AkuraAppException when exception occurs
     * @throws FileNotFoundException - throws FileNotFoundException when exception occurs
     */
    @PreAuthorize("hasRole('save_student')")
    @RequestMapping(REQ_MAP_STUDENT_DETAIL)
    public String showStudentDetailForm(ModelMap model, HttpSession session, HttpServletResponse response,
            HttpServletRequest request) throws AkuraAppException, FileNotFoundException {
    
        UserInfo userInfo = (UserInfo) session.getAttribute(USER);
        Student student = null;
        request.setAttribute(SUBJECTS_EXIST_OR_NOT,
                session.getAttribute(SUBJECTS_EXIST) != null ? (String) session.getAttribute(SUBJECTS_EXIST) : null);
        
        if (userInfo instanceof StudentDetails) {
            
            student = studentService.findStudent(userInfo.getUserId());
            
            populateFaithLifeAndAcademicRating(userInfo.getUserId(), session, model);
            
        } else if (userInfo instanceof ParentDetails) {
            
            int studentId = Integer.parseInt(session.getAttribute(STUDENT_ID).toString());
            
            populateFaithLifeAndAcademicRating(studentId, session, model);
            
            student = studentService.findStudent(studentId);
            
        } else if (userInfo instanceof AdminDetails) {
            
            AdminDetails adminDetails = (AdminDetails) userInfo;
            
            if (session.getAttribute(STUDENT_ID) != null) {
                int studentId = Integer.parseInt(session.getAttribute(STUDENT_ID).toString());
                adminDetails.setSearchStudentId(studentId);
                session.setAttribute(USER, adminDetails);
                
                populateFaithLifeAndAcademicRating(studentId, session, model);
            }
            
            student = studentService.findStudent(adminDetails.getSearchStudentId());
            
        } else {
            
            int studentId = 0;
            if (session.getAttribute(STUDENT_ID) != null) {
                studentId = Integer.parseInt(session.getAttribute(STUDENT_ID).toString());
                populateFaithLifeAndAcademicRating(studentId, session, model);
            }
            student = studentService.findStudent(studentId);
            
        }
        
        // Add student and student grade to session to display student summary
        // in sub tabs of student.
        if (student != null) {
            session.setAttribute(MODEL_ATT_STUDENT, student);
            model.addAttribute(MODEL_ATT_STUDENT_ID_CHECK, Boolean.FALSE);
            
            List<StudentClassInfo> studentClassInfo =
                    studentService.getStudentClassInfoByStudentId(student.getStudentId(), DateUtil.currentYearOnly());
            if (!studentClassInfo.isEmpty()) {
                session.setAttribute(STUDENT_GRADE, 
                        studentClassInfo.get(0).getClassGrade().getGrade().getDescription());
                session.setAttribute(STUDENT_CLASS, studentClassInfo.get(0).getClassGrade().getSchoolClass()
                        .getDescription());
                session.setAttribute(STUDENT_GRADE_CLASS, studentClassInfo.get(0).getClassGrade().getClassGradeId());
                
            } else {
                session.setAttribute(STUDENT_GRADE, "");
                session.setAttribute(STUDENT_CLASS, "");
                session.setAttribute(STUDENT_GRADE_CLASS, "");
            }
            
            StudentDisability stuDisability = null;
            
            if (student.getStudentDisabilities() != null && !student.getStudentDisabilities().isEmpty()) {
                List<StudentDisability> studentDisability =
                        new ArrayList<StudentDisability>(student.getStudentDisabilities());
                stuDisability = studentDisability.get(0);
            }
            model.addAttribute(MODEL_ATT_STUDENT_DISABILITY, stuDisability);
            
            if (student.getPhoto() != null && student.getPhoto().length > 0) {
                byte[] image = student.getPhoto();
                String imageLoadPath =
                        PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, APPSERVER_HOME)
                                + PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, IMAGE_FOLDER_PATH);
                imageLoadPath = imageLoadPath + student.getAdmissionNo() + FILE_EXT;
                StaticDataUtil.previewProfile(imageLoadPath, image);
                model.addAttribute(MODEL_ATT_IMAGE_PATH, RESOURCES_PROFILE_IMAGES +
                        student.getAdmissionNo() + FILE_EXT);
            } else {
                model.addAttribute(MODEL_ATT_IMAGE_PATH, RESOURCES_NO_PROFILE_IMAGE);
            }
        }
        
        if (student == null) {
            student = new Student();
        }
        displayPhoneNumberDetails(student, model);
        model.addAttribute(MODEL_ATT_STUDENT, student);
        
        return VIEW_GET_STUDENT_DETAIL_PAGE;
    }
    
    /**
     * @param student - Student obj.
     * @param result - BindingResult.
     * @param session - HttpSession
     * @param model - a hashMap that contains student's data
     * @param request - represents an instance of HttpServletRequest
     * @throws AkuraAppException - AkuraAppException.
     * @return name of the view which is redirected to.
     */
    @RequestMapping(REQ_MAP_SAVE_STUDENT_DETAIL)
    public String onSubmit(@ModelAttribute(MODEL_ATT_STUDENT) Student student, BindingResult result,
            HttpSession session, HttpServletRequest request, ModelMap model) throws AkuraAppException {
    
        String returnResult = VIEW_GET_STUDENT_DETAIL_PAGE;
        studentDetailValidator.validate(student, result);
        String selectedCountryCodeRes = request.getParameter(SELECTED_COUNTRYCODE_RES);
        String selectedCountryCodeMob = request.getParameter(SELECTED_COUNTRYCODE_MOB);
        String selectedCountryCodeEmgRes = request.getParameter(SELECTED_COUNTRYCODE_EMG_RES);
        String selectedCountryCodeEmgMob = request.getParameter(SELECTED_COUNTRYCODE_EMG_MOB);
        String selectedCountryCodeEmgOff = request.getParameter(SELECTED_COUNTRYCODE_EMG_OFFICE);
        try {
            if (result.hasErrors()) {
                handleValidationError(student, model);
                resetCountryFlags(selectedCountryCodeRes, selectedCountryCodeMob, selectedCountryCodeEmgRes,
                        selectedCountryCodeEmgMob, selectedCountryCodeEmgOff, model);
                return VIEW_GET_STUDENT_DETAIL_PAGE;
            }
            
            trimProperties(student);
            UserInfo userInfo = (UserInfo) session.getAttribute(USER);
            
            if (userInfo instanceof StudentDetails
                    && !userInfo.getUserLevelIdentifier().equals(student.getAdmissionNo())) {
                handleValidationError(student, model);
                result.rejectValue(STUDENT_ID, ERR_TUDENT_ADMISSIONNO_VIOLATE);
                resetCountryFlags(selectedCountryCodeRes, selectedCountryCodeMob, selectedCountryCodeEmgRes,
                        selectedCountryCodeEmgMob, selectedCountryCodeEmgOff, model);
                return VIEW_GET_STUDENT_DETAIL_PAGE;
            }
            
            // if check for initial save
            if (student != null && student.getStudentId() != 0) {
                
                Student stuObDB = studentService.findStudent(student.getStudentId());
                if (stuObDB == null) {
                    student.setStudentId(0);
                } else {
                    String admissionNoDB = stuObDB.getAdmissionNo();
                    
                    if (!admissionNoDB.equals(student.getAdmissionNo())) {
                        if (studentService.isAdmissionNoExist(student.getAdmissionNo())) {
                            handleValidationError(student, model);
                            result.rejectValue(STUDENT_ID, ERR_STUDENT_ADMISSIONNO_DUPLCATE);
                            resetCountryFlags(selectedCountryCodeRes, selectedCountryCodeMob,
                                    selectedCountryCodeEmgRes, selectedCountryCodeEmgMob, selectedCountryCodeEmgOff,
                                    model);
                            return VIEW_GET_STUDENT_DETAIL_PAGE;
                        } else {
                            
                            if (!student.getSiblingAdmitionNo().trim().isEmpty()) {
                                if (!studentService.isAdmissionNoExist(student.getSiblingAdmitionNo())
                                        || student.getAdmissionNo().equals(student.getSiblingAdmitionNo())) {
                                    result.rejectValue(SIBLING_ADMISSIONNO, ERR_SIBLING_ADMISSIONNO_VIOLATE);
                                    resetCountryFlags(selectedCountryCodeRes, selectedCountryCodeMob,
                                            selectedCountryCodeEmgRes, selectedCountryCodeEmgMob,
                                            selectedCountryCodeEmgOff, model);
                                    return VIEW_GET_STUDENT_DETAIL_PAGE;
                                }
                            }
                            if (!student.getResidenceNo().isEmpty() && !selectedCountryCodeRes.isEmpty()) {
                                if (student.getResidenceNo() != null && !selectedCountryCodeRes.equals(
                                        AkuraConstant.STRING_ZERO) && PhoneNumberValidateUtil.isValidPhoneNumber(
                                                student.getResidenceNo(),selectedCountryCodeRes)) {
                                    displayResidencePhoneNumberDetails(student, selectedCountryCodeRes);
                                } else {
                                    displayCountryFlagsWhenError(student, model, selectedCountryCodeRes
                                        , selectedCountryCodeMob,selectedCountryCodeEmgRes, selectedCountryCodeEmgMob,
                                            selectedCountryCodeEmgOff);
                                    return VIEW_GET_STUDENT_DETAIL_PAGE;
                                }
                            }
                            
                            if (!student.getMobileNo().isEmpty() && !selectedCountryCodeMob.isEmpty()) {
                                if (student.getMobileNo() != null && !selectedCountryCodeMob.equals(
                                        AkuraConstant.STRING_ZERO) && PhoneNumberValidateUtil.isValidPhoneNumber(
                                                student.getMobileNo(), selectedCountryCodeMob)) {
                                    displayMobilePhoneNumberDetails(student, selectedCountryCodeMob);
                                } else {
                                    displayCountryFlagsWhenError(student, model, selectedCountryCodeRes,
                                        selectedCountryCodeMob, selectedCountryCodeEmgRes, selectedCountryCodeEmgMob,
                                            selectedCountryCodeEmgOff);
                                    return VIEW_GET_STUDENT_DETAIL_PAGE;
                                }
                            }
                            
                            if (!student.getEmergencyContactResidenceNo().isEmpty()
                                    && !selectedCountryCodeEmgRes.isEmpty()) {
                                if (student.getEmergencyContactResidenceNo() != null
                                        && !selectedCountryCodeEmgRes.equals(AkuraConstant.STRING_ZERO) && 
                                        PhoneNumberValidateUtil.isValidPhoneNumber(
                                            student.getEmergencyContactResidenceNo(), selectedCountryCodeEmgRes)) {
                                    displayEmgResidencePhoneNumberDetails(student, selectedCountryCodeEmgRes);
                                } else {
                                    displayCountryFlagsWhenError(student, model, selectedCountryCodeRes, 
                                        selectedCountryCodeMob,selectedCountryCodeEmgRes, selectedCountryCodeEmgMob,
                                            selectedCountryCodeEmgOff);
                                    return VIEW_GET_STUDENT_DETAIL_PAGE;
                                }
                            }
                            
                            if (!student.getEmergencyContactMobileNo().isEmpty() && 
                                    !selectedCountryCodeEmgMob.isEmpty()) {
                                if (student.getEmergencyContactMobileNo() != null
                                        && !selectedCountryCodeEmgMob.equals(AkuraConstant.STRING_ZERO) && 
                                        PhoneNumberValidateUtil.isValidPhoneNumber(
                                                student.getEmergencyContactMobileNo(), selectedCountryCodeEmgMob)) {
                                    displayEmgMobilePhoneNumberDetails(student, selectedCountryCodeEmgMob);
                                } else {
                                    displayCountryFlagsWhenError(student, model, selectedCountryCodeRes,
                                        selectedCountryCodeMob, selectedCountryCodeEmgRes, selectedCountryCodeEmgMob,
                                            selectedCountryCodeEmgOff);
                                    return VIEW_GET_STUDENT_DETAIL_PAGE;
                                }
                            }
                            
                            if (!student.getEmergencyContactOfficeNo().isEmpty()
                                    && !selectedCountryCodeEmgOff.isEmpty()) {
                                if (student.getEmergencyContactOfficeNo() != null
                                        && !selectedCountryCodeEmgOff.equals(AkuraConstant.STRING_ZERO) && 
                                        PhoneNumberValidateUtil.isValidPhoneNumber(
                                                student.getEmergencyContactOfficeNo(), selectedCountryCodeEmgOff)) {
                                    displayEmgOfficePhoneNumberDetails(student, selectedCountryCodeEmgOff);
                                } else {
                                    displayCountryFlagsWhenError(student, model, selectedCountryCodeRes,
                                        selectedCountryCodeMob, selectedCountryCodeEmgRes, selectedCountryCodeEmgMob,
                                            selectedCountryCodeEmgOff);
                                    return VIEW_GET_STUDENT_DETAIL_PAGE;
                                }
                            }
                            updateStudent(student);
                            
                            // Update if user login exist for this student
                            UserLogin userLogin = userService.getUserLoginByIdentificationNo(stuObDB.getAdmissionNo());
                            if (userLogin != null) {
                                userLogin.setUserIdentificationNo(student.getAdmissionNo());
                                userService.updateUser(userLogin);
                            }
                            
                            // updated message pass through query string
                            String successUpdate =
                                    new ErrorMsgLoader().getErrorMessage(COMMON_MESSAGE_SUCCESSFULLY_UPDATED);
                            return VIEW_STUDENT_DETAIL + QUERY_STRING_UPDATE + successUpdate;
                        }
                    } else {
                        try {
                            if (student.getMPhoto() != null) {
                                MultipartFile multipartFile = student.getMPhoto();
                                if (multipartFile.getSize() > 0) {
                                    student.setPhoto(multipartFile.getBytes());
                                }
                            }
                        } catch (IOException e) {
                            
                            LOG.error(ERROR_WHILE_RETRIEVING_FILE + e.toString());
                            throw new AkuraAppException(AkuraConstant.FILE_NOT_FOUND, e);
                        }
                        if (!student.getSiblingAdmitionNo().trim().isEmpty()) {
                            if (!studentService.isAdmissionNoExist(student.getSiblingAdmitionNo())
                                    || student.getAdmissionNo().equals(student.getSiblingAdmitionNo())) {
                                result.rejectValue(SIBLING_ADMISSIONNO, ERR_SIBLING_ADMISSIONNO_VIOLATE);
                                returnResult = VIEW_GET_STUDENT_DETAIL_PAGE;
                                resetCountryFlags(selectedCountryCodeRes, selectedCountryCodeMob,
                                        selectedCountryCodeEmgRes, selectedCountryCodeEmgMob,
                                        selectedCountryCodeEmgOff, model);
                                return VIEW_GET_STUDENT_DETAIL_PAGE;
                            }
                        }
                        
                        if (!student.getResidenceNo().isEmpty() && !selectedCountryCodeRes.isEmpty()) {
                            if (student.getResidenceNo() != null && !selectedCountryCodeRes.equals(
                                    AkuraConstant.STRING_ZERO) && PhoneNumberValidateUtil.
                                    isValidPhoneNumber(student.getResidenceNo(),selectedCountryCodeRes)) {
                                displayResidencePhoneNumberDetails(student, selectedCountryCodeRes);
                            } else {
                                displayCountryFlagsWhenError(student, model, selectedCountryCodeRes,
                                    selectedCountryCodeMob,selectedCountryCodeEmgRes, selectedCountryCodeEmgMob,
                                    selectedCountryCodeEmgOff);
                                return VIEW_GET_STUDENT_DETAIL_PAGE;
                            }
                        }
                        
                        if (!student.getMobileNo().isEmpty() && !selectedCountryCodeMob.isEmpty()) {
                            if (student.getMobileNo() != null && !selectedCountryCodeMob.equals(
                                    AkuraConstant.STRING_ZERO) && PhoneNumberValidateUtil.
                                    isValidPhoneNumber(student.getMobileNo(), selectedCountryCodeMob)) {
                                displayMobilePhoneNumberDetails(student, selectedCountryCodeMob);
                            } else {
                                displayCountryFlagsWhenError(student, model, selectedCountryCodeRes, 
                                    selectedCountryCodeMob,selectedCountryCodeEmgRes, selectedCountryCodeEmgMob,
                                        selectedCountryCodeEmgOff);
                                return VIEW_GET_STUDENT_DETAIL_PAGE;
                            }
                        }
                        
                        if (!student.getEmergencyContactResidenceNo().isEmpty() 
                                && !selectedCountryCodeEmgRes.isEmpty()) {
                            if (student.getEmergencyContactResidenceNo() != null
                                    && !selectedCountryCodeEmgRes.equals(AkuraConstant.STRING_ZERO) && 
                                    PhoneNumberValidateUtil.isValidPhoneNumber(
                                            student.getEmergencyContactResidenceNo(), selectedCountryCodeEmgRes)) {
                                displayEmgResidencePhoneNumberDetails(student, selectedCountryCodeEmgRes);
                            } else {
                                displayCountryFlagsWhenError(student, model, selectedCountryCodeRes,
                                        selectedCountryCodeMob, selectedCountryCodeEmgRes, selectedCountryCodeEmgMob,
                                            selectedCountryCodeEmgOff);
                                return VIEW_GET_STUDENT_DETAIL_PAGE;
                            }
                        }
                        
                        if (!student.getEmergencyContactMobileNo().isEmpty() && !selectedCountryCodeEmgMob.isEmpty()) {
                            if (student.getEmergencyContactMobileNo() != null 
                                    && !selectedCountryCodeEmgMob.equals(AkuraConstant.STRING_ZERO) && 
                                    PhoneNumberValidateUtil.isValidPhoneNumber(student.getEmergencyContactMobileNo(),
                                            selectedCountryCodeEmgMob)) {
                                displayEmgMobilePhoneNumberDetails(student, selectedCountryCodeEmgMob);
                            } else {
                                displayCountryFlagsWhenError(student, model, selectedCountryCodeRes,
                                    selectedCountryCodeMob,selectedCountryCodeEmgRes, selectedCountryCodeEmgMob,
                                        selectedCountryCodeEmgOff);
                                return VIEW_GET_STUDENT_DETAIL_PAGE;
                            }
                        }
                        
                        if (!student.getEmergencyContactOfficeNo().isEmpty() && !selectedCountryCodeEmgOff.isEmpty()) {
                            if (student.getEmergencyContactOfficeNo() != null && 
                                    !selectedCountryCodeEmgOff.equals(AkuraConstant.STRING_ZERO) && 
                                    PhoneNumberValidateUtil.isValidPhoneNumber(student.getEmergencyContactOfficeNo(),
                                            selectedCountryCodeEmgOff)) {
                                displayEmgOfficePhoneNumberDetails(student, selectedCountryCodeEmgOff);
                            } else {
                                displayCountryFlagsWhenError(student, model, selectedCountryCodeRes,
                                        selectedCountryCodeMob, selectedCountryCodeEmgRes, selectedCountryCodeEmgMob,
                                            selectedCountryCodeEmgOff);
                                return VIEW_GET_STUDENT_DETAIL_PAGE;
                            }
                        }
                        updateStudent(student);
                        
                        // updated message pass through query string
                        String successUpdate =
                                new ErrorMsgLoader().getErrorMessage(COMMON_MESSAGE_SUCCESSFULLY_UPDATED);
                        return VIEW_STUDENT_DETAIL + QUERY_STRING_UPDATE + successUpdate;
                    }
                }
            }
            
            if (student != null && student.getStudentId() == 0) {
                if (studentService.isAdmissionNoExist(student.getAdmissionNo())) {
                    
                    model.addAttribute(MODEL_ATT_IMAGE_PATH, RESOURCES_NO_PROFILE_IMAGE);
                    result.rejectValue(STUDENT_ID, ERR_STUDENT_ADMISSIONNO_DUPLCATE);
                    returnResult = VIEW_GET_STUDENT_DETAIL_PAGE;
                    resetCountryFlags(selectedCountryCodeRes, selectedCountryCodeMob, selectedCountryCodeEmgRes,
                            selectedCountryCodeEmgMob, selectedCountryCodeEmgOff, model);
                    return VIEW_GET_STUDENT_DETAIL_PAGE;
                } else {
                    if (student.getMPhoto() != null) {
                        try {
                            MultipartFile multipartFile = student.getMPhoto();
                            if (multipartFile.getSize() > 0) {
                                student.setPhoto(multipartFile.getBytes());
                            }
                        } catch (IOException e) {
                            LOG.error(ERROR_WHILE_RETRIEVING_FILE + e.toString());
                            throw new AkuraAppException(AkuraConstant.FILE_NOT_FOUND, e);
                        }
                    }
                    if (!student.getSiblingAdmitionNo().trim().isEmpty()) {
                        if (!studentService.isAdmissionNoExist(student.getSiblingAdmitionNo())
                                || student.getAdmissionNo().equals(student.getSiblingAdmitionNo())) {
                            result.rejectValue(SIBLING_ADMISSIONNO, ERR_SIBLING_ADMISSIONNO_VIOLATE);
                            returnResult = VIEW_GET_STUDENT_DETAIL_PAGE;
                            resetCountryFlags(selectedCountryCodeRes, selectedCountryCodeMob,
                                    selectedCountryCodeEmgRes, selectedCountryCodeEmgMob, selectedCountryCodeEmgOff,
                                    model);
                            return VIEW_GET_STUDENT_DETAIL_PAGE;
                        }
                    }
                    
                    student.setStatusId(1);
                    
                    if (!student.getResidenceNo().isEmpty() && !selectedCountryCodeRes.isEmpty()) {
                        if (student.getResidenceNo() != null && !selectedCountryCodeRes.equals(
                                AkuraConstant.STRING_ZERO) && PhoneNumberValidateUtil.
                                isValidPhoneNumber(student.getResidenceNo(),selectedCountryCodeRes)) {
                            displayResidencePhoneNumberDetails(student, selectedCountryCodeRes);
                        } else {
                            displayCountryFlagsWhenError(student, model, selectedCountryCodeRes,
                                selectedCountryCodeMob,selectedCountryCodeEmgRes, selectedCountryCodeEmgMob,
                                selectedCountryCodeEmgOff);
                            return VIEW_GET_STUDENT_DETAIL_PAGE;
                        }
                    }
                    
                    if (!student.getMobileNo().isEmpty() && !selectedCountryCodeMob.isEmpty()) {
                        if (student.getMobileNo() != null && !selectedCountryCodeMob.equals(
                                AkuraConstant.STRING_ZERO) && PhoneNumberValidateUtil.
                                isValidPhoneNumber(student.getMobileNo(), selectedCountryCodeMob)) {
                            displayMobilePhoneNumberDetails(student, selectedCountryCodeMob);
                        } else {
                            displayCountryFlagsWhenError(student, model, selectedCountryCodeRes, 
                                selectedCountryCodeMob,selectedCountryCodeEmgRes, selectedCountryCodeEmgMob,
                                    selectedCountryCodeEmgOff);
                            return VIEW_GET_STUDENT_DETAIL_PAGE;
                        }
                    }
                    
                    if (!student.getEmergencyContactResidenceNo().isEmpty() 
                            && !selectedCountryCodeEmgRes.isEmpty()) {
                        if (student.getEmergencyContactResidenceNo() != null
                                && !selectedCountryCodeEmgRes.equals(AkuraConstant.STRING_ZERO) && 
                                PhoneNumberValidateUtil.isValidPhoneNumber(
                                        student.getEmergencyContactResidenceNo(), selectedCountryCodeEmgRes)) {
                            displayEmgResidencePhoneNumberDetails(student, selectedCountryCodeEmgRes);
                        } else {
                            displayCountryFlagsWhenError(student, model, selectedCountryCodeRes,
                                    selectedCountryCodeMob, selectedCountryCodeEmgRes, selectedCountryCodeEmgMob,
                                        selectedCountryCodeEmgOff);
                            return VIEW_GET_STUDENT_DETAIL_PAGE;
                        }
                    }
                    
                    if (!student.getEmergencyContactMobileNo().isEmpty() && !selectedCountryCodeEmgMob.isEmpty()) {
                        if (student.getEmergencyContactMobileNo() != null 
                                && !selectedCountryCodeEmgMob.equals(AkuraConstant.STRING_ZERO) && 
                                PhoneNumberValidateUtil.isValidPhoneNumber(student.getEmergencyContactMobileNo(),
                                        selectedCountryCodeEmgMob)) {
                            displayEmgMobilePhoneNumberDetails(student, selectedCountryCodeEmgMob);
                        } else {
                            displayCountryFlagsWhenError(student, model, selectedCountryCodeRes,
                                selectedCountryCodeMob,selectedCountryCodeEmgRes, selectedCountryCodeEmgMob,
                                    selectedCountryCodeEmgOff);
                            return VIEW_GET_STUDENT_DETAIL_PAGE;
                        }
                    }
                    
                    if (!student.getEmergencyContactOfficeNo().isEmpty() && !selectedCountryCodeEmgOff.isEmpty()) {
                        if (student.getEmergencyContactOfficeNo() != null && 
                                !selectedCountryCodeEmgOff.equals(AkuraConstant.STRING_ZERO) && 
                                PhoneNumberValidateUtil.isValidPhoneNumber(student.getEmergencyContactOfficeNo(),
                                        selectedCountryCodeEmgOff)) {
                            displayEmgOfficePhoneNumberDetails(student, selectedCountryCodeEmgOff);
                        } else {
                            displayCountryFlagsWhenError(student, model, selectedCountryCodeRes,
                                    selectedCountryCodeMob, selectedCountryCodeEmgRes, selectedCountryCodeEmgMob,
                                        selectedCountryCodeEmgOff);
                            return VIEW_GET_STUDENT_DETAIL_PAGE;
                        }
                    }

                    studentService.saveStudent(student);
                    if (checkStudentDisabilityFilled(student.getStudentDisability())) {
                        student.getStudentDisability().setStudentId(student.getStudentId());
                        trimStudentDisabilityObj(student.getStudentDisability());
                        studentService.saveStudentDisability(student.getStudentDisability());
                    }
                    returnResult = VIEW_NEW_STUDENT_DETAIL;
                }
            }
        } catch (AkuraAppException e) {
            if (e.getCause() instanceof TransientDataAccessResourceException) {
                String message = new ErrorMsgLoader().getErrorMessage(IMAGE_DATABASE_SIZE);
                model.addAttribute(ERROR_MESSAGE, message);
                resetCountryFlags(selectedCountryCodeRes, selectedCountryCodeMob, selectedCountryCodeEmgRes,
                        selectedCountryCodeEmgMob, selectedCountryCodeEmgOff, model);
                return returnResult;
            }
        }
        
        return VIEW_NEW_STUDENT_DETAIL;
    }
    
    /**
     * Trims the properties of the student.
     * 
     * @param student - the instance of the student.
     */
    private void trimProperties(Student student) {
    
    	student.setAdmissionNo(student.getAdmissionNo().trim());
        student.setFullName(student.getFullName().trim());
        student.setNameWtInitials(student.getNameWtInitials().trim());
        student.setLastName(student.getLastName().trim());
    }
    
    /**
     * Method to gets the image width.
     * 
     * @return imagewidth - specified by an integer variable.
     */
    @ModelAttribute(MODEL_ATT_IMAGE_WIDTH)
    public int getImageWidth() {
    
        String strfileWidth = PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, PROFILE_IMAGE_WIDTH);
        return Integer.parseInt(strfileWidth);
    }
    
    /**
     * Method to gets the image height.
     * 
     * @return imageheight - specified by an integer variable.
     */
    @ModelAttribute(MODEL_ATT_IMAGE_HEIGHT)
    public int getImageHeight() {
    
        String strfileHeight = PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, PROFILE_IMAGE_HEIGHT);
        return Integer.parseInt(strfileHeight);
    }
    
    /**
     * Method to trim the values in the student disability object before save or update.
     * 
     * @param studentDisabilityObj - StudentDisability object to trim.
     */
    private void trimStudentDisabilityObj(StudentDisability studentDisabilityObj) {
    
        if (!"".equals(studentDisabilityObj.getAllergiesMedicalNotes())
                || studentDisabilityObj.getAllergiesMedicalNotes() != null) {
            studentDisabilityObj.setAllergiesMedicalNotes(studentDisabilityObj.getAllergiesMedicalNotes().trim());
        }
        if (!"".equals(studentDisabilityObj.getBehaviourDifficulties())
                || studentDisabilityObj.getBehaviourDifficulties() != null) {
            studentDisabilityObj.setBehaviourDifficulties(studentDisabilityObj.getBehaviourDifficulties().trim());
        }
        if (!"".equals(studentDisabilityObj.getDisabilityInfo()) || studentDisabilityObj.getDisabilityInfo() != null) {
            studentDisabilityObj.setDisabilityInfo(studentDisabilityObj.getDisabilityInfo().trim());
        }
        if (!"".equals(studentDisabilityObj.getDyslexia()) || studentDisabilityObj.getDyslexia() != null) {
            studentDisabilityObj.setDyslexia(studentDisabilityObj.getDyslexia().trim());
        }
        if (!"".equals(studentDisabilityObj.getFits()) || studentDisabilityObj.getFits() != null) {
            studentDisabilityObj.setFits(studentDisabilityObj.getFits().trim());
        }
        if (!"".equals(studentDisabilityObj.getHearingImpairment())
                || studentDisabilityObj.getHearingImpairment() != null) {
            studentDisabilityObj.setHearingImpairment(studentDisabilityObj.getHearingImpairment().trim());
        }
        if (!"".equals(studentDisabilityObj.getOtherInfo()) || studentDisabilityObj.getOtherInfo() != null) {
            studentDisabilityObj.setOtherInfo(studentDisabilityObj.getOtherInfo().trim());
        }
        if (!"".equals(studentDisabilityObj.getPhysicalDisabilities())
                || studentDisabilityObj.getPhysicalDisabilities() != null) {
            studentDisabilityObj.setPhysicalDisabilities(studentDisabilityObj.getPhysicalDisabilities().trim());
        }
        if (!"".equals(studentDisabilityObj.getSpeechDifficulties())
                || studentDisabilityObj.getSpeechDifficulties() != null) {
            studentDisabilityObj.setSpeechDifficulties(studentDisabilityObj.getSpeechDifficulties().trim());
        }
        if (!"".equals(studentDisabilityObj.getVisualImpairment())
                || studentDisabilityObj.getVisualImpairment() != null) {
            studentDisabilityObj.setVisualImpairment(studentDisabilityObj.getVisualImpairment().trim());
        }
    }
    
    /**
     * Method to check whether at least one student disability detail has filled.
     * 
     * @param studentDisabilityObj - StudentDisability object input from user.
     * @return disabilityFilled - true if filled at least one student disability detail.
     */
    private boolean checkStudentDisabilityFilled(StudentDisability studentDisabilityObj) {
    
        boolean disabilityFilled = false;
        
        if (!"".equals(studentDisabilityObj.getAllergiesMedicalNotes())
                || !"".equals(studentDisabilityObj.getBehaviourDifficulties())
                || !"".equals(studentDisabilityObj.getDisabilityInfo())
                || !"".equals(studentDisabilityObj.getDyslexia()) || !"".equals(studentDisabilityObj.getFits())
                || !"".equals(studentDisabilityObj.getHearingImpairment())
                || !"".equals(studentDisabilityObj.getOtherInfo())
                || !"".equals(studentDisabilityObj.getPhysicalDisabilities())
                || !"".equals(studentDisabilityObj.getSpeechDifficulties())
                || !"".equals(studentDisabilityObj.getVisualImpairment())) {
            
            disabilityFilled = true;
        }
        
        return disabilityFilled;
    }
    
    /**
     * Common piece of code for update student with different conditions. This handles edit student with Add
     * or Edit student disability.
     * 
     * @param student - Student object
     * @throws AkuraAppException - throw the detailed exception.
     */
    private void updateStudent(Student student) throws AkuraAppException {
    
        studentService.updateStudent(student);
        StudentDisability disability = studentService.findStudentDisability(student.getStudentId());
        student.getStudentDisability().setStudentId(student.getStudentId());
        trimStudentDisabilityObj(student.getStudentDisability());
        
        if (disability != null) {
            studentService.updateStudentDisability(student.getStudentDisability());
        } else {
            if (checkStudentDisabilityFilled(student.getStudentDisability())) {
                studentService.saveStudentDisability(student.getStudentDisability());
            }
        }
        
    }
    
    /**
     * This method populates the academic life and faith life ratings in the rating bar.
     * 
     * @param studentId the id of the student.
     * @param session to get session data.
     * @param model ModelMap to store model data.
     * @throws AkuraAppException when fails to load ratings.
     */
    private void populateFaithLifeAndAcademicRating(int studentId, HttpSession session, ModelMap model)
            throws AkuraAppException {
    
        String currentYear = Integer.toString(DateUtil.currentYearOnly());
        Date year = DateUtil.getDateTypeYearValue(currentYear);
        
        Map<String, Double> averageMap = studentLoginDelegate.populateStudentProgressBar(studentId, year);
        
        double averFaithLife = averageMap.get(AVERAGE_FAITH_LIFE_RATING);
        session.setAttribute(AVERAGE_FAITH_LIFE_RATING, averFaithLife);
        model.addAttribute(MODEL_ATT_STUDENT_FAITH_LIFE, (int) Math.round(averFaithLife));
        
        double averAcademicLife = averageMap.get(AVERAGE_ACADEMIC_LIFE_RATING);
        session.setAttribute(AVERAGE_ACADEMIC_LIFE_RATING, averAcademicLife);
        model.addAttribute(MODEL_ATT_STUDENT_ACADEMIC_LIFE, (int) Math.round(averAcademicLife));
        
        double attendanceAverage = averageMap.get(AVERAGE_ATTENDANCE_RATING);
        session.setAttribute(AVERAGE_ATTENDANCE_RATING, attendanceAverage);
        model.addAttribute(MODEL_ATT_ATTENDANCE_RATING, (int) Math.round(attendanceAverage));
    }
    
    /**
     * Get list of available countries list.
     * 
     * @return list of countries.
     * @throws AkuraAppException -throw detailed exception.
     */
    @ModelAttribute(COUNTRY_LIST)
    public List<Country> populateCountryList() throws AkuraAppException {
    
        return SortUtil.sortCountries((commonService.getCountryList()));
        
    }
    
    /**
     * Get list of available countries list.
     * 
     * @return list of countries.
     * @throws AkuraAppException -throw detailed exception.
     */
    @ModelAttribute(RACE_LIST)
    public List<Race> populateRaceList() throws AkuraAppException {
    
        return SortUtil.sortRaceList(commonService.getRaceList());
        
    }
    
    /**
     * Get list of available countries list for .
     * 
     * @return map of country id and short code.
     * @throws AkuraAppException -throw detailed exception.
     */
    @ModelAttribute(PHONE_COUNTRY_LIST)
    public Map<String, Country> populatePhoneCountryList() throws AkuraAppException {
    
        Map<String, Country> phoneMap = new LinkedHashMap<String, Country>();
        List<Country> countryList = null;
        countryList = SortUtil.sortCountries((commonService.getCountryList()));
        for (Country country : countryList) {
            
            if (country.getCountryCode() != null) {
                phoneMap.put(AkuraConstant.PLUS_SIGN + PhoneNumberValidateUtil.findCountryCode(
                        country.getCountryCode()), country);
                
            }
        }
        return phoneMap;
    }
    
    /**
     * display Residence PhoneNumber Details.
     * 
     * @param student - Student related data object
     * @param selectedCountryCodeRes - String for selected Residence phone country code
     * @throws AkuraAppException - Throw detail exception when fails.
     */
    private void displayResidencePhoneNumberDetails(Student student, String selectedCountryCodeRes)
            throws AkuraAppException {
    
        String resPhoneNumber = AkuraConstant.EMPTY_STRING;
        if (student.getResidenceNo().startsWith(AkuraConstant.STRING_ZERO)) {
            
            resPhoneNumber =
                    PhoneNumberValidateUtil.formatPhoneNumber(student.getResidenceNo(),
                            selectedCountryCodeRes, AkuraConstant.PARAM_INDEX_ZERO);
            student.setResidenceNo(resPhoneNumber);
        } else {
            resPhoneNumber =
                    PhoneNumberValidateUtil.formatPhoneNumber(
                            AkuraConstant.STRING_ZERO + student.getResidenceNo(), selectedCountryCodeRes
                            , AkuraConstant.PARAM_INDEX_ZERO);
            student.setResidenceNo(resPhoneNumber);
        }
        
    }
    
    /**
     * handles when validation error occur.
     * 
     * @param student - Student related data object
     * @param model - ModelMap
     * @throws AkuraAppException - detail exception during the processing
     */
    private void handleValidationError(Student student, ModelMap model) throws AkuraAppException {
    
        String imagepath = RESOURCES_NO_PROFILE_IMAGE;
        if (student != null && student.getStudentId() > 0) {
            Student studentDB = studentService.findStudent(student.getStudentId());
            if (studentDB != null) {
                imagepath = getImagePath(student, imagepath, studentDB);
            }
        }
        
        model.addAttribute(MODEL_ATT_IMAGE_PATH, imagepath);
    }
    
    /**
     * gets the path of the profile image.
     * 
     * @param student - Student related data object
     * @param imagepath -String
     * @param studentDB -Student
     * @return profile image path
     * @throws AkuraAppException - detail exception during the processing
     */
    private String getImagePath(Student student, String imagepath, Student studentDB) throws AkuraAppException {
    
        if (studentDB.getPhoto() != null && studentDB.getPhoto().length > 0) {
            byte[] image = studentDB.getPhoto();
            String imageLoadPath =
                    PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, APPSERVER_HOME)
                            + PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, IMAGE_FOLDER_PATH);
            imageLoadPath = imageLoadPath + student.getAdmissionNo() + FILE_EXT;
            StaticDataUtil.previewProfile(imageLoadPath, image);
            imagepath = RESOURCES_PROFILE_IMAGES + student.getAdmissionNo() + FILE_EXT;
        }
        return imagepath;
    }
    
    /**
     * Reset the country flag combo boxes.
     * 
     * @param selectedCountryCodeRes - String for selected Residence phone country code
     * @param selectedCountryCodeMob - String for selected Mobile phone country code
     * @param selectedCountryCodeEmgRes - String for selected Emergency Residence phone country code
     * @param selectedCountryCodeEmgMob - String for selected Emergency mobile phone country code   
     * @param selectedCountryCodeEmgOff - String for selected Emergency office phone country code  
     * @param model - ModelMap
     * @throws AkuraAppException - Throw detail exception when fails.
     */
    private void resetCountryFlags(String selectedCountryCodeRes, String selectedCountryCodeMob,
            String selectedCountryCodeEmgRes, String selectedCountryCodeEmgMob, String selectedCountryCodeEmgOff,
            ModelMap model) throws AkuraAppException {
    
        model.addAttribute(MODEL_ATT_SELECTED_RES_COUNTRY, 
                (selectedCountryCodeRes.isEmpty()) ? AkuraConstant.STRING_ZERO : selectedCountryCodeRes);
        model.addAttribute(MODEL_ATT_SELECTED_MOB_COUNTRY,
                (selectedCountryCodeMob.isEmpty()) ? AkuraConstant.STRING_ZERO : selectedCountryCodeMob);
        model.addAttribute(MODEL_ATT_SELECTED_EMG_RES_COUNTRY,
                (selectedCountryCodeEmgRes.isEmpty()) ? AkuraConstant.STRING_ZERO : selectedCountryCodeEmgRes);
        model.addAttribute(MODEL_ATT_SELECTED_EMG_MOB_COUNTRY,
                (selectedCountryCodeEmgMob.isEmpty()) ? AkuraConstant.STRING_ZERO : selectedCountryCodeEmgMob);
        model.addAttribute(MODEL_ATT_SELECTED_EMG_OFF_COUNTRY,
                (selectedCountryCodeEmgOff.isEmpty()) ? AkuraConstant.STRING_ZERO : selectedCountryCodeEmgOff);
    }
    
    /**
     * View the staff phone number validation details.
     * 
     * @param student - Student related data object
     * @param model - {@link ModelMap}
     * @throws AkuraAppException - Throw detail exception when fails.
     */
    private void displayPhoneNumberDetails(Student student, ModelMap model) throws AkuraAppException {
    
        if (student.getResidenceNo() != null) {
            if (student.getResidenceNo().contains(AkuraConstant.EMPTY_STRING_SPACE)) {
                String countryCode =
                        student.getResidenceNo().substring(0, student.getResidenceNo().indexOf(' ')).replace('+', ' ')
                                .trim();
                String resNumber =
                        student.getResidenceNo().substring(student.getResidenceNo().indexOf(' ')).replaceAll("\\s", "");
                student.setResidenceNo((resNumber.startsWith(AkuraConstant.STRING_ZERO)) 
                        ? resNumber.substring(1): resNumber);
                model.addAttribute(MODEL_ATT_SELECTED_RES_COUNTRY,
                        PhoneNumberValidateUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode)));
            } else {
                model.addAttribute(MODEL_ATT_SELECTED_RES_COUNTRY, 0);
            }
        }
        
        if (student.getMobileNo() != null) {
            if (student.getMobileNo().contains(AkuraConstant.EMPTY_STRING_SPACE)) {
                String countryCode =
                        student.getMobileNo().substring(0, student.getMobileNo().indexOf(' ')).replace('+', ' ').trim();
                String mobNumber =
                        student.getMobileNo().substring(student.getMobileNo().indexOf(' ')).replaceAll("\\s", "");
                student.setMobileNo((mobNumber.startsWith(AkuraConstant.STRING_ZERO)) 
                        ? mobNumber.substring(1): mobNumber);
                model.addAttribute(MODEL_ATT_SELECTED_MOB_COUNTRY,
                        PhoneNumberValidateUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode)));
            } else {
                model.addAttribute(MODEL_ATT_SELECTED_MOB_COUNTRY, 0);
            }
        }
        
        if (student.getEmergencyContactResidenceNo() != null) {
            if (student.getEmergencyContactResidenceNo().contains(AkuraConstant.EMPTY_STRING_SPACE)) {
                String countryCode =
                        student.getEmergencyContactResidenceNo()
                                .substring(0, student.getEmergencyContactResidenceNo().indexOf(' ')).replace('+', ' ')
                                .trim();
                String emgResNumber =
                        student.getEmergencyContactResidenceNo()
                                .substring(student.getEmergencyContactResidenceNo().indexOf(' ')).replaceAll("\\s", "");
                student.setEmergencyContactResidenceNo((emgResNumber.startsWith(AkuraConstant.STRING_ZERO)) ? 
                        emgResNumber.substring(1):emgResNumber);
                model.addAttribute(MODEL_ATT_SELECTED_EMG_RES_COUNTRY,
                        PhoneNumberValidateUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode)));
            } else {
                model.addAttribute(MODEL_ATT_SELECTED_EMG_RES_COUNTRY, 0);
            }
        }
        
        if (student.getEmergencyContactMobileNo() != null) {
            if (student.getEmergencyContactMobileNo().contains(AkuraConstant.EMPTY_STRING_SPACE)) {
                String countryCode =
                        student.getEmergencyContactMobileNo()
                                .substring(0, student.getEmergencyContactMobileNo().indexOf(' ')).replace('+', ' ')
                                .trim();
                String emgMobNumber =
                        student.getEmergencyContactMobileNo()
                                .substring(student.getEmergencyContactMobileNo().indexOf(' ')).replaceAll("\\s", "");
                student.setEmergencyContactMobileNo((emgMobNumber.startsWith(AkuraConstant.STRING_ZERO)) ? 
                        emgMobNumber.substring(1): emgMobNumber);
                model.addAttribute(MODEL_ATT_SELECTED_EMG_MOB_COUNTRY,
                        PhoneNumberValidateUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode)));
            } else {
                model.addAttribute(MODEL_ATT_SELECTED_EMG_MOB_COUNTRY, 0);
            }
        }
        
        if (student.getEmergencyContactOfficeNo() != null) {
            if (student.getEmergencyContactOfficeNo().contains(AkuraConstant.EMPTY_STRING_SPACE)) {
                String countryCode =
                        student.getEmergencyContactOfficeNo()
                                .substring(0, student.getEmergencyContactOfficeNo().indexOf(' ')).replace('+', ' ')
                                .trim();
                String emgOffNumber =
                        student.getEmergencyContactOfficeNo()
                                .substring(student.getEmergencyContactOfficeNo().indexOf(' ')).replaceAll("\\s", "");
                student.setEmergencyContactOfficeNo((emgOffNumber.startsWith(AkuraConstant.STRING_ZERO)) ? 
                        emgOffNumber.substring(1): emgOffNumber);
                model.addAttribute(MODEL_ATT_SELECTED_EMG_OFF_COUNTRY,
                        PhoneNumberValidateUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode)));
            } else {
                model.addAttribute(MODEL_ATT_SELECTED_EMG_OFF_COUNTRY, 0);
            }
        }
    }
    
    /**
     * display Mobile PhoneNumber Details.
     * 
     * @param student - Student related data object
     * @param selectedCountryCodeMob - String for selected Mobile phone country code
     * @throws AkuraAppException - Throw detail exception when fails.
     */
    private void displayMobilePhoneNumberDetails(Student student, String selectedCountryCodeMob)
            throws AkuraAppException {
    
        String mobPhoneNumber = AkuraConstant.EMPTY_STRING;
        if (student.getMobileNo().startsWith(AkuraConstant.STRING_ZERO)) {
            mobPhoneNumber =
                    PhoneNumberValidateUtil.formatPhoneNumber(student.getMobileNo(),
                            selectedCountryCodeMob, AkuraConstant.PARAM_INDEX_ZERO);
            student.setMobileNo(mobPhoneNumber);
        } else {
            mobPhoneNumber =
                    PhoneNumberValidateUtil.formatPhoneNumber(
                            AkuraConstant.STRING_ZERO + student.getMobileNo(),
                            selectedCountryCodeMob, AkuraConstant.PARAM_INDEX_ZERO);
            student.setMobileNo(mobPhoneNumber);
        }
        
    }
    
    /**
     * display EmgResidence PhoneNumber Details.
     * 
     * @param student - Student related data object
     * @param selectedCountryCodeEmgRes - String for selected Emergency Residence phone country code
     * @throws AkuraAppException - Throw detail exception when fails.
     */
    private void displayEmgResidencePhoneNumberDetails(Student student, String selectedCountryCodeEmgRes)
            throws AkuraAppException {
    
        String emgResPhoneNumber = AkuraConstant.EMPTY_STRING;
        if (student.getEmergencyContactResidenceNo().startsWith(AkuraConstant.STRING_ZERO)) {
            emgResPhoneNumber =
                    PhoneNumberValidateUtil.formatPhoneNumber(student.getEmergencyContactResidenceNo(),
                            selectedCountryCodeEmgRes, AkuraConstant.PARAM_INDEX_ZERO);
            student.setEmergencyContactResidenceNo(emgResPhoneNumber);
        } else {
            emgResPhoneNumber =
                    PhoneNumberValidateUtil.formatPhoneNumber(
                            AkuraConstant.STRING_ZERO + student.getEmergencyContactResidenceNo(),
                            selectedCountryCodeEmgRes, AkuraConstant.PARAM_INDEX_ZERO);
            student.setEmergencyContactResidenceNo(emgResPhoneNumber);
        }
        
    }
    
    /**
     * display EmgMobile PhoneNumber Details.
     * 
     * @param student - Student related data object
     * @param selectedCountryCodeEmgMob - String for selected Emergency mobile phone country code
     * @throws AkuraAppException - Throw detail exception when fails.
     */
    private void displayEmgMobilePhoneNumberDetails(Student student, String selectedCountryCodeEmgMob)
            throws AkuraAppException {
    
        String emgMobPhoneNumber = AkuraConstant.EMPTY_STRING;
        if (student.getEmergencyContactMobileNo().startsWith(AkuraConstant.STRING_ZERO)) {
            emgMobPhoneNumber =
                    PhoneNumberValidateUtil.formatPhoneNumber(student.getEmergencyContactMobileNo(),
                            selectedCountryCodeEmgMob, AkuraConstant.PARAM_INDEX_ZERO);
            student.setEmergencyContactMobileNo(emgMobPhoneNumber);
        } else {
            emgMobPhoneNumber =
                    PhoneNumberValidateUtil.formatPhoneNumber(
                            AkuraConstant.STRING_ZERO + student.getEmergencyContactMobileNo(), 
                            selectedCountryCodeEmgMob, AkuraConstant.PARAM_INDEX_ZERO);
            student.setEmergencyContactMobileNo(emgMobPhoneNumber);
        }
        
    }
    
    /**
     * display EmgOffice PhoneNumber Details.
     * 
     * @param student - Student related data object
     * @param selectedCountryCodeEmgOffice - String for selected Emergency office phone country code 
     * @throws AkuraAppException - Throw detail exception when fails.
     */
    private void displayEmgOfficePhoneNumberDetails(Student student, String selectedCountryCodeEmgOffice)
            throws AkuraAppException {
    
        String emgOffPhoneNumber = AkuraConstant.EMPTY_STRING;
        if (student.getEmergencyContactOfficeNo().startsWith(AkuraConstant.STRING_ZERO)) {
            emgOffPhoneNumber =
                    PhoneNumberValidateUtil.formatPhoneNumber(student.getEmergencyContactOfficeNo(),
                            selectedCountryCodeEmgOffice, AkuraConstant.PARAM_INDEX_ZERO);
            student.setEmergencyContactOfficeNo(emgOffPhoneNumber);
        } else {
            emgOffPhoneNumber =
                    PhoneNumberValidateUtil.formatPhoneNumber(
                            AkuraConstant.STRING_ZERO + student.getEmergencyContactOfficeNo(),
                            selectedCountryCodeEmgOffice, AkuraConstant.PARAM_INDEX_ZERO);
            student.setEmergencyContactOfficeNo(emgOffPhoneNumber);
        }
        
    }
    
    /**
     * display Country Flags when error occurs.
     * 
     * @param student - Student related data object
     * @param model for display the model error
     * @param selectedCountryCodeRes - String for selected Residence phone country code
     * @param selectedCountryCodeMob - String for selected Mobile phone country code
     * @param selectedCountryCodeEmgRes - String for selected Emergency Residence phone country code
     * @param selectedCountryCodeEmgMob - String for selected Emergency mobile phone country code   
     * @param selectedCountryCodeEmgOff - String for selected Emergency office phone country code      
     * @throws AkuraAppException - Throw detail exception when fails.
     */
    private void displayCountryFlagsWhenError(Student student, ModelMap model, String selectedCountryCodeRes,
            String selectedCountryCodeMob, String selectedCountryCodeEmgRes, String selectedCountryCodeEmgMob,
            String selectedCountryCodeEmgOff) throws AkuraAppException {
    
        String message = new ErrorMsgLoader().getErrorMessage(MESSAGE_PHONE_NO_COUNTRY_MISMATCH);
        model.addAttribute(ERROR_MESSAGE, message);
        displayPhoneNumberDetails(student, model);
        handleValidationError(student, model);
        resetCountryFlags(selectedCountryCodeRes, selectedCountryCodeMob, selectedCountryCodeEmgRes,
                selectedCountryCodeEmgMob, selectedCountryCodeEmgOff, model);
    }
    
}
