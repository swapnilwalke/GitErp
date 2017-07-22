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
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.CommonEmailBean;
import com.virtusa.akura.api.dto.Country;
import com.virtusa.akura.api.dto.Donation;
import com.virtusa.akura.api.dto.DonationType;
import com.virtusa.akura.api.dto.EmploymentStatus;
import com.virtusa.akura.api.dto.Parent;
import com.virtusa.akura.api.dto.ParentDonation;
import com.virtusa.akura.api.dto.ParentWrapper;
import com.virtusa.akura.api.dto.Religion;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentParent;
import com.virtusa.akura.api.dto.UserInfo;
import com.virtusa.akura.api.dto.WorkingSegment;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.service.EmailService;
import com.virtusa.akura.staff.service.StaffService;
import com.virtusa.akura.student.StudentConstant;
import com.virtusa.akura.student.service.ParentService;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.student.validator.StudentDonationValidator;
import com.virtusa.akura.student.validator.StudentParentValidator;
import com.virtusa.akura.util.PhoneNumberValidateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;
import com.virtusa.akura.util.email.EmailUtil;

/**
 * This is a controller where controls all the parent data.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class StudentParentController {
    
    /** symbol for mother . */
    private static final String MOTHER_STRING = "M";
    
    /** symbol for mother . */
    private static final char MOTHER_CHAR = 'M';
    
    /** symbol for guardian . */
    private static final String GUARDIAN_STRING = "G";
    
    /** symbol for guardian . */
    private static final char GUARDIAN_CHAR = 'G';
    
    /** symbol for father . */
    private static final String FATHER_STRING = "F";
    
    /** symbol for father . */
    private static final char FATHER_CHAR = 'F';
    
    /** Logger to log the exceptions. */
    private static final Logger LOG = Logger.getLogger(StudentParentController.class);
    
    /** Success message . */
    private static final String PAR_UI_NEW_RECORD_ADD_SUCCESS = "PAR.UI.NEW_RECORD_ADD_SUCCESS";
    
    /** Error message . */
    private static final String PAR_UI_DEFFERENT_PARENT_ENTITY = "PAR.UI.DEFFERENT_PARENT_ENTITY";
    
    /** Error message . */
    private static final String PAR_UI_PARENT_DETAIL_EXIST = "PAR.UI.PARENT_DETAIL_EXIST";
    
    /** Error message . */
    private static final String PAR_UI_PARENT_ALREADEY_USED = "PAR.UI.PARENT_ALREADEY_USED";
    
    /** Error message . */
    private static final String REF_UI_PARENT_STAFFID_NOTEXIST = "REF.UI.PARENT.STAFFID.NOTEXIST";
    
    /** Success message . */
    private static final String PARENT_UPDATED_SUCCESS = "PAR.UI.PARENT_UPDATED_SUCESS";
    
    /** User mail send error message. */
    private static final String ERROER_SENDING_EMAIL_TO_USER = "PAR.UI.ERROR_SEND_TO_USER";
    
    /** error message for no User email address found. */
    private static final String NO_USER_EMAIL_ADDRESS_FOUND = "PAR.UI.NO_USER_EMAIL_ADRESS";
    
    /** admin mail send error message to user. */
    private static final String MESSAGE_ERROR_SEND_TO_ADMIN = "PAR.UI.ERROR_SEND_TO_ADMIN";
    
    /** user message for success of mail send to admin. */
    private static final String ADMIN_MAIL_SEND_STATUS_MESSAGE = "PAR.UI.SUCCESS_SEND_TO_ADMIN";
    
    /** message to user for success of mail send to the user. */
    private static final String USER_MAIL_SEND_STATUS_MESSAGE = "PAR.UI.SUCCESS_SEND_TO_USER";
    
    /** key value in property file(email template name). */
    private static final String EMAIL_TEMPLATE_NAME = "email.template.changeParentDetail";
    
    /** String attribute for holding DONATION_DESCRIPTION_ERROR_MSG. */
    private static final String DONATION_DESCRIPTION_ERROR_MSG = "REF.UI.PARENT.DONATION.DESCRIPTION";
    
    /** String attribute for holding `REF.UI.PARENT.DONATION.EXISTS`. */
    private static final String DONATION_EXISTS_ERROR_MSG = "REF.UI.PARENT.DONATION.EXISTS";
    
    /** String attribute for holding DONATION_DELETE_MSG. */
    private static final String DONATION_DELETE_MSG = "PAR.UI.DONATION.DELETE";
    
    /** model attribute of success message. */
    private static final String MESSAGE_SUCESS = "messageSuccess";
    
    /** model attribute of message. */
    private static final String MESSAGE = "message";
    
    /** model attribute of message. */
    private static final String DISPLAY_PANEL = "displayPanel";
    
    /** Email attribute name. */
    private static final String TYPE_F_OR_G = "typeForG";
    
    /** URL for parent detail modification request. */
    private static final String SAVE_PARENT_BY_PARENT_HTM = "saveParentByParent.htm";
    
    /** session attribute name for user. */
    private static final String USER = "user";
    
    /** property key value. */
    private static final String ADMIN_EMAIL = "admin.email";
    
    /** email property file name. */
    private static final String EMAIL = "email";
    
    /** model attribute Email sending errors. */
    private static final String ERROR_EMAIL = "errorEmail";
    
    /** model attribute for email status admin. */
    private static final String ADMIN_MAIL_STATUS = "adminMailStatus";
    
    /** model attribute for email status user. */
    private static final String USER_MAIL_STATUS = "userMailStatus";
    
    /** content message for user. */
    private static final String CONTENT_MESSAGE_FOR_USER = "parent.content_message_user";
    
    /** content message for admin. */
    private static final String CONTENT_MESSAGE_FOR_ADMIN = "parent.content_message_admin";
    
    /** greeting message part for user. */
    private static final String DEAR = "Dear ";
    
    /** greeting message for admin. */
    private static final String ADMIN_GREETING = "Dear Administrator";
    
    /** Email attribute name for content. */
    private static final String CONTENT = "content";
    
    /** Email attribute name for greeting. */
    private static final String GREETING = "greeting";
    
    /** Email attribute name for changes map for father or guardian. */
    private static final String CHANGES_FOR_G = "changesForG";
    
    /** Email attribute name admission no for student . */
    private static final String ADDMISION_NO = "addmisionNo";
    
    /** Email attribute name name of the student. */
    private static final String STUDENT_NAME = "studentName";
    
    /** Email attribute name for user name(parent account name). */
    private static final String USER_NAME = "userName";
    
    /** Email subject. */
    private static final String PARENT_DETAIL_MODIFICATION = "parent Detail modification";
    
    /** Represents the error message. */
    private String message;
    
    /** String attribute for holding DEFALUT_DATE_FORMAT. */
    private static final String DEFALUT_DATE_FORMAT = "yyyy-MM-dd";
    
    /** Label for email. */
    private static final String FATHER_LABEL = "Father";
    
    /** Label for email. */
    private static final String GUARDIAN_LABEL = "Guardian";
    
    /** String attribute for holding STUDENT_ID. */
    private static final String STUDENT_ID = "studentId";
    
    /** String attribute for holding WRAPPER_PARENT. */
    private static final String PARENT_WRAPPER = "parentWrapper";
    
    /** String attribute for holding DONATION_LIST. */
    private static final String DONATION_LIST = "donationList";
    
    /** String attribute for holding WORKING_SEGMENT_LIST. */
    private static final String WORKING_SEGMENT_LIST = "workingSegmentList";
    
    /** String attribute for holding RELIGION_LIST. */
    private static final String RELIGION_LIST = "religionList";
    
    /** String attribute for holding CITY_LIST. */
    private static final String CITY_LIST = "cityList";
    
    /** String attribute for holding DONATION_TYPE_LIST. */
    private static final String DONATION_TYPE_LIST = "donationTypeList";
    
    /** String attribute for holding DONATION_TYPE_LIST. */
    private static final String EMPLOYMENT_STATUS_LIST = "emplomentStatusList";
    
    /** String attribute for holding AVERAGE_FAITH_LIFE_RATING. */
    private static final String AVERAGE_FAITH_LIFE_RATING = "averageFaithLifeRating";
    
    /** String attribute for holding AVERAGE_ACADEMIC_LIFE_RATING. */
    private static final String AVERAGE_ACADEMIC_LIFE_RATING = "averageAcademicLifeRating";
    
    /** key to define the averageAttendanceRating. */
    private static final String AVERAGE_ATTENDANCE_RATING = "averageAttendanceRating";
    
    /** key to define the attendanceRating. */
    private static final String MODEL_ATT_ATTENDANCE_RATING = "attendanceRating";
    
    /** model attribute of student faithLife average value. */
    private static final String MODEL_ATT_STUDENT_FAITH_LIFE = "averageFaithLife";
    
    /** model attribute of student academic life average value. */
    private static final String MODEL_ATT_STUDENT_ACADEMIC_LIFE = "averageAcademicLife";
    
    /** String attribute for holding ADMIN_PARENT. */
    private static final String VIEW_PARENT = "student/parentInfo";
    
    /** String attribute for holding REDIRECT_ADMIN_PARENT. */
    private static final String REDIRECT_ADMIN_PARENT = "redirect:AdminParent.htm";
    
    /** String attribute for holding Request Mapping Value for SaveUpdateDonation. */
    private static final String REQ_MAP_VALUE_SAVE_UPDATE_DONATION = "/saveOrUpdateDonation.htm";
    
    /** String attribute for holding Request Mapping Value for DeleteDonation. */
    private static final String REQ_MAP_VALUE_DELETE_DONATION = "/deleteDonation.htm";
    
    /** String attribute for holding Request Mapping Value for SaveAdminParent. */
    private static final String REQ_MAP_VALUE_SAVE_ADMIN_PARENT = "/SaveAdminParent.htm";
    
    /** CommonService attribute for holding commonService. */
    private CommonService commonService;
    
    /** ParentService attribute for holding parentService. */
    private ParentService parentService;
    
    /** StudentService attribute for holding studentService. */
    private StudentService studentService;
    
    /** StaffService attribute for holding staffService. */
    private StaffService staffService;
    
    /** emailService attribute for holding EmailService. */
    private EmailService emailService;
    
    /** Represents an instance of the ParentValidator. */
    private StudentParentValidator parentValidator;
    
    /** Represents an instance of the DonationValidator. */
    private StudentDonationValidator donationValidator;
    
    /** A constant serves as a key for storing path to email properties file. */
    public static final String SYSTEM_CONFIG_PROPERTIES = "systemConfig";
    
    /** String attribute for holding Request Mapping Value for AdminParent. */
    private static final String REQ_MAP_VALUE_ADMIN_PARENT = "/AdminParent.htm";
    
    /** String attribute for holding SELECTED_RELATIONSHIP. */
    private static final String SELECTED_RELATIONSHIP = "selectedRelationship";
    
    /** the field name to check. */
    private static final String DONATION_DONATION_ID = "donation.donationId";
    
    /** The Constant COUNTRY_LIST. */
    private static final String PHONE_COUNTRY_LIST = "countryListPhone";
    
    /** The Constant SELECTED_CLASS. */
    private static final String SELECTED_COUNTRYCODE_RES = "selectedCountryCodeRes";
    
    /** The Constant SELECTED_CLASS. */
    private static final String SELECTED_COUNTRYCODE_MOB = "selectedCountryCodeMob";
    
    /** The Constant SELECTED_CLASS. */
    private static final String SELECTED_COUNTRYCODE_OFF = "selectedCountryCodeOffice";
    
    /** The Constant SELECTED_CLASS. */
    private static final String SELECTED_COUNTRYCODE_FAX = "selectedCountryCodeFax";
    
    /** key to hold message when record added. */
    private static final String MESSAGE_PHONE_NO_COUNTRY_MISMATCH = "STAFF.PHONE.COUNTRY.NO.MATCH";
    
    /** The String of Model attribute. */
    private static final String MODEL_ATT_SELECTED_RES_COUNTRY = "selectedResCountry";
    
    /** The String of Model attribute. */
    private static final String MODEL_ATT_SELECTED_MOB_COUNTRY = "selectedMobCountry";
    
    /** The String of Model attribute. */
    private static final String MODEL_ATT_SELECTED_OFFICE_COUNTRY = "selectedOfficeCountry";
    
    /** The String of Model attribute. */
    private static final String MODEL_ATT_SELECTED_OFFICE_FAX_COUNTRY = "selectedOfficeFaxCountry";
    
    
    /**
     * set the emailService object.
     * 
     * @param emailServiceArg EmailService object to set
     */
    public void setEmailService(EmailService emailServiceArg) {
    
        this.emailService = emailServiceArg;
    }
    
    /**
     * getter method for error message.
     * 
     * @return message
     */
    public String getMessage() {
    
        return message;
    }
    
    /**
     * Setter method for error message.
     * 
     * @param messageTemp - Error message
     */
    public void setMessage(String messageTemp) {
    
        this.message = messageTemp;
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
     * setter method from ParentService.
     * 
     * @param parentServiceVal - parentService
     */
    public void setParentService(ParentService parentServiceVal) {
    
        this.parentService = parentServiceVal;
    }
    
    /**
     * setter method from studentService.
     * 
     * @param studentServiceVal - studentService
     */
    public void setStudentService(StudentService studentServiceVal) {
    
        this.studentService = studentServiceVal;
    }
    
    /**
     * setter method from staffService.
     * 
     * @param staffServiceVal - staffService
     */
    public void setStaffService(StaffService staffServiceVal) {
    
        this.staffService = staffServiceVal;
    }
    
    /**
     * Sets an instance of ParentValidator.
     * 
     * @param parentValidatorVal - the instance of ParentValidator
     */
    public void setParentValidator(StudentParentValidator parentValidatorVal) {
    
        this.parentValidator = parentValidatorVal;
    }
    
    /**
     * Sets an instance of DonationValidator.
     * 
     * @param donationValidatorVal - the instance of DonationValidator
     */
    public void setDonationValidator(StudentDonationValidator donationValidatorVal) {
    
        this.donationValidator = donationValidatorVal;
    }
    
    /**
     * intiBinder method is to bind date class with the date format.
     * 
     * @param binder - data binder used to register the Date objects.
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    
        SimpleDateFormat dateFormat = new SimpleDateFormat(DEFALUT_DATE_FORMAT);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    
    /**
     * Method is to return workingSegment list reference data.
     * 
     * @return WorkingSegmentList - A list that contains Working Segment data.
     * @throws AkuraAppException - The exception details that occurred when retrieving WorkingSegmentList.
     */
    @ModelAttribute(WORKING_SEGMENT_LIST)
    public List<WorkingSegment> populateWorkingSegmentList() throws AkuraAppException {
    
        return SortUtil.sortWorkingSegmentList(commonService.getWorkingSegmentList());
    }
    
    /**
     * Method is to return religion list reference data.
     * 
     * @return ReligionList - A list that contains Religion reference data.
     * @throws AkuraAppException - The exception details that occurred when retrieving ReligionList.
     */
    @ModelAttribute(RELIGION_LIST)
    public List<Religion> populateReligionList() throws AkuraAppException {
    
        return SortUtil.sortReligionList(commonService.getReligionList());
    }
    
    /**
     * Method is to return city list data.
     * 
     * @return CityList - A list that contains city reference data.
     * @throws AkuraAppException - The exception details that occurred when retrieving CityList.
     */
    @ModelAttribute(CITY_LIST)
    public List<City> populateCityList() throws AkuraAppException {
    
        return SortUtil.sortCityList(commonService.getCityList());
    }
    
    /**
     * Method is to return donation type list data.
     * 
     * @return DonationTypeList - A list that contains donation type reference data.
     * @throws AkuraAppException - The exception details that occurred when retrieving DonationTypeList.
     */
    @ModelAttribute(DONATION_TYPE_LIST)
    public List<DonationType> populateDonationType() throws AkuraAppException {
    
        return commonService.viewAllDonationType();
    }
    
    /**
     * Method is to return Employment Status list data.
     * 
     * @return EmploymentStatusList - A list that contains employment status reference data.
     * @throws AkuraAppException - The exception details that occurred when retrieving EmploymentStatusList.
     */
    @ModelAttribute(EMPLOYMENT_STATUS_LIST)
    public List<EmploymentStatus> populateEmploymentStatusList() throws AkuraAppException {
    
        return SortUtil.sortEmploymentStatusList(commonService.findAllEmploymentStatus());
    }
    
    /**
     * handle request for view the parent information.
     * 
     * @param parentWrapper command object
     * @param model to hold model attributes.
     * @param session session
     * @param request request
     * @return view name
     * @throws AkuraAppException when exception occurs
     */
    @RequestMapping(value = REQ_MAP_VALUE_ADMIN_PARENT)
    public String showParentDetailAdminPanel(@ModelAttribute(PARENT_WRAPPER) ParentWrapper parentWrapper,
            ModelMap model, HttpSession session, HttpServletRequest request) throws AkuraAppException {
    
        Integer studentId = (Integer) session.getAttribute(STUDENT_ID);
        
        if (studentId != null) {
            
            // user requesting a relation F= father M= mother G=guardian
            String relationship = request.getParameter(SELECTED_RELATIONSHIP);
            
            if (relationship == null || relationship.isEmpty()) {
                
                // if user did not specify relation type, get available parent
                // info if any. first father then guardian or mother .
                List<StudentParent> listSP = parentService.getStudentParentListByStudentId(studentId);
                
                if (listSP != null && !listSP.isEmpty()) {
                    
                    for (StudentParent studentParentObj : listSP) {
                        
                        if (studentParentObj.getRelationship() == FATHER_CHAR) {
                            parentWrapper.setRelationship(FATHER_STRING);
                            parentWrapper.setParent(studentParentObj.getParent());
                            break;
                            
                        } else if (studentParentObj.getRelationship() == GUARDIAN_CHAR) {
                            parentWrapper.setRelationship(GUARDIAN_STRING);
                            parentWrapper.setParent(studentParentObj.getParent());
                            
                        } else if (studentParentObj.getRelationship() == MOTHER_CHAR) {
                            parentWrapper.setRelationship(MOTHER_STRING);
                            parentWrapper.setParent(studentParentObj.getParent());
                        }
                    }
                } else {// no any parent info found , empty parent object
                    parentWrapper.setRelationship(FATHER_STRING);
                    parentWrapper.setParent(new Parent());
                    
                }
            } else {// user requesting parent with specific relation
                char relation = relationship.charAt(0);
                parentWrapper.setRelationship(Character.toString(relation));
                
                List<StudentParent> list = parentService.getParentListBy(studentId, relation);
                if (list != null && !list.isEmpty()) {
                    parentWrapper.setParent(list.get(0).getParent());
                    
                } else {// requested parent is empty;
                    parentWrapper.setParent(new Parent());
                }
            }
            setDonationListToModel(studentId, model);
        }
        setupStudentRatingDetails(model, session);
        displayPhoneNumberDetails(parentWrapper.getParent(), model);
        return VIEW_PARENT;
    }
    
    /**
     * set the donation list to model map.
     * 
     * @param studentId student id
     * @param model modelMap to set the list
     * @throws AkuraAppException when exception occurs
     */
    private void setDonationListToModel(int studentId, ModelMap model) throws AkuraAppException {
    
        List<Donation> donationList = parentService.getDonationListByStudentId(studentId);
        SortUtil.sortDonationList(donationList);
        model.addAttribute(DONATION_LIST, donationList);
    }
    
    /**
     * Handle request for save or update the parent information.
     * 
     * @param parentWrapper command object
     * @param result validation results
     * @param model to hold model attributes.
     * @param session object
     * @param request object
     * @return view name
     * @throws AkuraAppException when exception occurs
     */
    @RequestMapping(value = REQ_MAP_VALUE_SAVE_ADMIN_PARENT, method = RequestMethod.POST)
    public String saveParent(@ModelAttribute(PARENT_WRAPPER) final ParentWrapper parentWrapper, BindingResult result,
            ModelMap model, HttpSession session, HttpServletRequest request) throws AkuraAppException {
    
        Parent newParent = parentWrapper.getParent();
        Integer studentId = (Integer) session.getAttribute(STUDENT_ID);
        String selectedCountryCodeRes = request.getParameter(SELECTED_COUNTRYCODE_RES);
        String selectedCountryCodeMob = request.getParameter(SELECTED_COUNTRYCODE_MOB);
        String selectedCountryCodeOff = request.getParameter(SELECTED_COUNTRYCODE_OFF);
        String selectedCountryCodeFax = request.getParameter(SELECTED_COUNTRYCODE_FAX);
        // validation
        ErrorMsgLoader errorLoder = new ErrorMsgLoader();
        
        parentValidator.validate(parentWrapper, result);
        if (result.hasErrors()) {
            setDonationListToModel(studentId, model);
            setupStudentRatingDetails(model, session);
            resetCountryFlags(selectedCountryCodeRes, selectedCountryCodeMob, selectedCountryCodeOff,
                    selectedCountryCodeFax, model);
            return VIEW_PARENT;
        }
        
        if (!isValidTeacherAsParent(parentWrapper.getParent())) {
            setDonationListToModel(studentId, model);
            setupStudentRatingDetails(model, session);
            
            message = errorLoder.getErrorMessage(REF_UI_PARENT_STAFFID_NOTEXIST);
            model.addAttribute(MESSAGE, message);
            displayPhoneNumberDetails(parentWrapper.getParent(), model);
            resetCountryFlags(selectedCountryCodeRes, selectedCountryCodeMob, selectedCountryCodeOff,
                    selectedCountryCodeFax, model);
            return VIEW_PARENT;
        }
        if (!parentWrapper.getParent().getResidenceNo().isEmpty() && !selectedCountryCodeRes.isEmpty()) {
            if (parentWrapper.getParent().getResidenceNo() != null 
                    && !selectedCountryCodeRes.equals(AkuraConstant.STRING_ZERO) && PhoneNumberValidateUtil.
                    isValidPhoneNumber(parentWrapper.getParent().getResidenceNo(), selectedCountryCodeRes)) {
                displayResidencePhoneNumberDetails(parentWrapper.getParent(), selectedCountryCodeRes);
            } else {
                displayCountryFlagsWhenError(parentWrapper.getParent(),model, selectedCountryCodeRes,
                        selectedCountryCodeMob, selectedCountryCodeOff, selectedCountryCodeFax);
                return VIEW_PARENT;
            }
        }
        
        if (!parentWrapper.getParent().getMobileNo().isEmpty() && !selectedCountryCodeMob.isEmpty()) {
            if (parentWrapper.getParent().getMobileNo() != null 
                    && !selectedCountryCodeMob.equals(AkuraConstant.STRING_ZERO) && PhoneNumberValidateUtil.
                    isValidPhoneNumber(parentWrapper.getParent().getMobileNo(), selectedCountryCodeMob)) {
                displayMobilePhoneNumberDetails(parentWrapper.getParent(), selectedCountryCodeMob);
            } else {
                displayCountryFlagsWhenError(parentWrapper.getParent(),model, selectedCountryCodeRes,
                        selectedCountryCodeMob, selectedCountryCodeOff, selectedCountryCodeFax);
                return VIEW_PARENT;
            }
        }
        
        if (!parentWrapper.getParent().getOfficeNo().isEmpty() && !selectedCountryCodeOff.isEmpty()) {
            if (parentWrapper.getParent().getOfficeNo() != null 
                    && !selectedCountryCodeOff.equals(AkuraConstant.STRING_ZERO) && PhoneNumberValidateUtil.
                    isValidPhoneNumber(parentWrapper.getParent().getOfficeNo(), selectedCountryCodeOff)) {
                displayOfficePhoneNumberDetails(parentWrapper.getParent(), selectedCountryCodeOff);
            } else {
                displayCountryFlagsWhenError(parentWrapper.getParent(),model, selectedCountryCodeRes,
                        selectedCountryCodeMob, selectedCountryCodeOff, selectedCountryCodeFax);
                return VIEW_PARENT;
            }
        }
        
        if (!parentWrapper.getParent().getOfficeFaxNo().isEmpty() && !selectedCountryCodeFax.isEmpty()) {
            if (parentWrapper.getParent().getOfficeFaxNo() != null 
                    && !selectedCountryCodeFax.equals(AkuraConstant.STRING_ZERO) && PhoneNumberValidateUtil.
                    isValidPhoneNumber(parentWrapper.getParent().getOfficeFaxNo(), selectedCountryCodeFax)) {
                displayOfficeFaxNumberDetails(parentWrapper.getParent(), selectedCountryCodeOff);
            } else {
                displayCountryFlagsWhenError(parentWrapper.getParent(),model, selectedCountryCodeRes,
                        selectedCountryCodeMob, selectedCountryCodeOff, selectedCountryCodeFax);
                return VIEW_PARENT;
            }
        }
        // list must contain atMost one record
        List<StudentParent> list = parentService.getParentListBy(studentId, parentWrapper.getRelationship().charAt(0));
        
        if (list == null || list.isEmpty()) {
            // no appropriate parent record found for this student
            saveOrGetNewSP(parentWrapper, studentId, model, null);
            
        } else {
            StudentParent oldStuParent = list.get(0);
            Parent oldParent = oldStuParent.getParent();
            
            if (oldParent.getNationalIdNo().equals(newParent.getNationalIdNo())) {
                // if no change in NIC no
                parentService.updateParent(newParent);
                model.addAttribute(MESSAGE_SUCESS, errorLoder.getErrorMessage(PARENT_UPDATED_SUCCESS));
            } else {
                // NIC change act as new parent
                
                boolean isOldPUsedOhter = parentService.isUsedInOtherStudent(oldParent, studentId);
                Parent newParentExist = parentService.getParentByNIC(newParent.getNationalIdNo());
                
                if (newParentExist != null) {
                    // if new parent record already exist in DB
                    saveOrGetNewSP(parentWrapper, studentId, model, oldStuParent);
                } else if (isOldPUsedOhter) {
                    // old parent used by other student and new parent does not exist
                    
                    StudentParent newStudetnParent = new StudentParent();
                    newStudetnParent.setStudent(oldStuParent.getStudent());
                    newStudetnParent.setRelationship(parentWrapper.getRelationship().charAt(0));
                    // save new student
                    newParent = parentService.addParent(newParent);
                    newStudetnParent.setParent(newParent);
                    
                    // remove and add new student parent record
                    parentService.deleteStudentParent(oldStuParent);
                    parentService.addStudentParent(newStudetnParent);
                    model.addAttribute(MESSAGE_SUCESS, errorLoder.getErrorMessage(PARENT_UPDATED_SUCCESS));
                    
                } else {
                    // if old parent not in used, and new par not exist it can update the same old
                    // parent record
                    parentService.updateParent(newParent);
                    
                    model.addAttribute(MESSAGE_SUCESS, errorLoder.getErrorMessage(PARENT_UPDATED_SUCCESS));
                    
                }
            }
        }
        
        setDonationListToModel(studentId, model);
        setupStudentRatingDetails(model, session);
        displayPhoneNumberDetails(parentWrapper.getParent(), model);
        return VIEW_PARENT;
    }
    
    /**
     * This method will save or get the existing valid(gender wise) parent and set as student's parent. And
     * existing StudentParent record will remove in DB if there is one.
     * 
     * @param parentWrapper wrapped parent object to save or get
     * @param studentId student_id
     * @param model model to set messages
     * @param existSPRecord to remove the existing StudentParent record before add new record.
     * @throws AkuraAppException if exception occurs.
     */
    private void saveOrGetNewSP(final ParentWrapper parentWrapper, int studentId, final ModelMap model,
            StudentParent existSPRecord) throws AkuraAppException {
    
        Student student = studentService.findStudent(studentId);
        ErrorMsgLoader errorLoder = new ErrorMsgLoader();
        
        // DB may contain parent records without any studentParent relation.
        Parent existParent = parentService.getParentByNIC(parentWrapper.getParent().getNationalIdNo());
        
        if (existParent != null) {// there is a parent in DB on that NIC.
        
            // check exist parent is relate with this student
            boolean isParentUsedSame = parentService.isUsedInSameStudent(existParent, studentId);
            if (isParentUsedSame) {
                // parent use in same student
                model.addAttribute(MESSAGE, errorLoder.getErrorMessage(PAR_UI_PARENT_ALREADEY_USED));
                return;
            }
            
            // if parent matches the gender ,put that parent as this student.
            // else give error message parent exist in other category.
            // but guardian can put any (m,f,g) category.
            
            List<Student> tempSList = null;
            if (parentWrapper.getRelationship().equals(FATHER_STRING)) {
                // check exist parent used as mother relation
                tempSList = parentService.getStudentListBy(existParent.getNationalIdNo(), MOTHER_CHAR);
            } else if (parentWrapper.getRelationship().equals(MOTHER_STRING)) {
                // check exist parent used as father relation
                tempSList = parentService.getStudentListBy(existParent.getNationalIdNo(), FATHER_CHAR);
            }
            
            // if exist parent valid (as gender) to put.
            if (tempSList == null || tempSList.isEmpty()) {
                
                if (existSPRecord != null) {
                    // remove existing Student Parent record
                    parentService.deleteStudentParent(existSPRecord);
                }
                StudentParent studentParent = new StudentParent();
                studentParent.setParent(existParent);
                studentParent.setStudent(student);
                studentParent.setRelationship(parentWrapper.getRelationship().charAt(0));
                parentService.addStudentParent(studentParent);
                parentWrapper.setParent(existParent);
                model.addAttribute(MESSAGE, errorLoder.getErrorMessage(PAR_UI_PARENT_DETAIL_EXIST));
                
            } else {// exist parent used as other gender. Error message
                model.addAttribute(MESSAGE,
                        errorLoder.getErrorMessage(PAR_UI_DEFFERENT_PARENT_ENTITY) + tempSList.get(0).getAdmissionNo());
            }
            
        } else {// no parent in DB ,save new StudentParent object
        
            if (existSPRecord != null) {
                // remove existing Student Parent record
                parentService.deleteStudentParent(existSPRecord);
            }
            
            Parent newParent = parentWrapper.getParent();
            newParent = parentService.addParent(newParent);
            
            StudentParent studentParent = new StudentParent();
            studentParent.setParent(newParent);
            studentParent.setStudent(student);
            studentParent.setRelationship(parentWrapper.getRelationship().charAt(0));
            parentService.addStudentParent(studentParent);
            model.addAttribute(MESSAGE_SUCESS, errorLoder.getErrorMessage(PAR_UI_NEW_RECORD_ADD_SUCCESS));
            
        }
    }
    
    /**
     * Checks the valid teacher if isTeacher is checked. else return true.
     * 
     * @param parent parent.
     * @return the validity.
     * @throws AkuraAppException AkuraAppException
     */
    private boolean isValidTeacherAsParent(Parent parent) throws AkuraAppException {
    
        boolean pStaff = true;
        if (parent.isTeacher()) {
            if (parent.getTeacherRegNo() == null || parent.getTeacherRegNo().trim().isEmpty()) {
                pStaff = false;
            } else {
                pStaff = staffService.isValidRegistrationNo(parent.getTeacherRegNo());
            }
        }
        
        return pStaff;
    }
    
    /**
     * Setups the faith life and academic details rating for student.
     * 
     * @param model the model.
     * @param session the session.
     */
    private void setupStudentRatingDetails(ModelMap model, HttpSession session) {
    
        if (session.getAttribute(AVERAGE_FAITH_LIFE_RATING) != null) {
            double faithLifeAverage = (Double) session.getAttribute(AVERAGE_FAITH_LIFE_RATING);
            model.addAttribute(MODEL_ATT_STUDENT_FAITH_LIFE, (int) Math.round(faithLifeAverage));
        }
        if (session.getAttribute(AVERAGE_ACADEMIC_LIFE_RATING) != null) {
            double academicLifeAverage = (Double) session.getAttribute(AVERAGE_ACADEMIC_LIFE_RATING);
            model.addAttribute(MODEL_ATT_STUDENT_ACADEMIC_LIFE, (int) Math.round(academicLifeAverage));
        }
        if (session.getAttribute(AVERAGE_ATTENDANCE_RATING) != null) {
            double attendanceAverage = (Double) session.getAttribute(AVERAGE_ATTENDANCE_RATING);
            model.addAttribute(MODEL_ATT_ATTENDANCE_RATING, (int) Math.round(attendanceAverage));
        }
        
    }
    
    /**
     * Save or update the Donation object in Admin parent view.
     * 
     * @param parentWrapper - WrapperParent object.
     * @param model - a HashMap that contains information of the parent
     * @param request - an object of HttpServletRequest.
     * @param result - containing list of errors and wrapperParent instance to which data was bound
     * @param session - an object of HttpSession to hold the studentid.
     * @throws AkuraAppException - The exception details that occurred when submitting donation details.
     * @return name of the view which is redirected to.
     */
    @RequestMapping(value = REQ_MAP_VALUE_SAVE_UPDATE_DONATION, method = RequestMethod.POST)
    public String saveDonation(@ModelAttribute(PARENT_WRAPPER) ParentWrapper parentWrapper, BindingResult result,
            ModelMap model, final HttpServletRequest request, HttpSession session) throws AkuraAppException {
    
        // The variable used to return the appropriate page.
        String returnPage = REDIRECT_ADMIN_PARENT;
        Integer studentId = (Integer) session.getAttribute(STUDENT_ID);
        
        donationValidator.validate(parentWrapper, result);
        if (result.hasErrors()) {
            model.addAttribute(DISPLAY_PANEL, Boolean.TRUE);
            returnPage = VIEW_PARENT; // "Admin/Parent_details";
        } else {
            // ParentDonation parentDonation = new ParentDonation();
            Donation donation = parentWrapper.getDonation();
            int donationTypeId = parentWrapper.getDonation().getDonationType().getDonationTypeId();
            DonationType donationType = commonService.findDonationType(donationTypeId);
            if (donationType != null) {
                donation.setDonationType(donationType);
            }
            if (session.getAttribute(STUDENT_ID) != null) {
                
                List<StudentParent> studentParentList = parentService.getStudentParentListByStudentId(studentId);
                if (studentParentList.isEmpty()) {
                    result.rejectValue(DONATION_DONATION_ID, DONATION_DESCRIPTION_ERROR_MSG);
                    returnPage = VIEW_PARENT;//
                } else {
                    Parent studentParent = studentParentList.get(0).getParent();
                    
                    // check donation information is exists
                    boolean isExistisDonation = parentService.isExistsDonation(donation);
                    
                    // save donation.
                    if (!isExistisDonation && (studentParent != null) && (donation.getDonationId() == 0)) {
                        ParentDonation parentDonation = new ParentDonation();
                        Donation saveDonation = parentService.addDonation(donation);
                        parentDonation.setDonation(saveDonation);
                        parentDonation.setParent(studentParent);
                        parentService.addParentDonation(parentDonation);
                        
                    } else if (!isExistisDonation && donation != null && donation.getDonationId() != 0) {
                        parentService.modifyDonation(donation);
                    } else if (isExistisDonation) {
                        message = new ErrorMsgLoader().getErrorMessage(DONATION_EXISTS_ERROR_MSG);
                        model.addAttribute(MESSAGE, message);
                        model.addAttribute(DISPLAY_PANEL, true);
                        returnPage = VIEW_PARENT;//
                    } else {
                        message = new ErrorMsgLoader().getErrorMessage(DONATION_DESCRIPTION_ERROR_MSG);
                        model.addAttribute(MESSAGE, message);
                        model.addAttribute(DISPLAY_PANEL, true);
                        returnPage = VIEW_PARENT;//
                    }
                }
            }
        }
        
        setDonationListToModel(studentId, model);
        setupStudentRatingDetails(model, session);
        return returnPage;
    }
    
    /**
     * Deletes the relevant Donation object.
     * 
     * @param parentWrapper - WrapperParent object.
     * @param model - a HashMap that contains information of the StaffCategory
     * @return - name of the view which is redirected to
     * @param request - an object of HttpServletRequest.
     * @throws AkuraAppException - The exception details that occurred when deleting a donation.
     */
    @RequestMapping(value = REQ_MAP_VALUE_DELETE_DONATION, method = RequestMethod.POST)
    public final String deleteDonation(@ModelAttribute(PARENT_WRAPPER) ParentWrapper parentWrapper, ModelMap model,
            final HttpServletRequest request) throws AkuraAppException {
    
        try {
            Iterator<ParentDonation> it =
                    parentService.getParentDonationsByDonationId(parentWrapper.getDonation().getDonationId())
                            .iterator();
            while (it.hasNext()) {
                ParentDonation parentDonation = it.next();
                parentService.deleteParentDonation(parentDonation);
                parentService.deleteDonation(parentDonation.getDonation());
            }
            return REDIRECT_ADMIN_PARENT;
            
        } catch (AkuraAppException e) {
            LOG.error("ParentController - Exception occured while deleting a donation " + "-->" + e.toString());
            
            if (e.getCause() instanceof DataIntegrityViolationException) {
                message = new ErrorMsgLoader().getErrorMessage(DONATION_DELETE_MSG);
                List<Donation> donationList = parentService.getDonationListByStudentId(1);
                Collections.reverse(donationList);
                model.addAttribute(DONATION_LIST, donationList);
                model.addAttribute(MESSAGE, message);
                return VIEW_PARENT;
            } else {
                throw new AkuraAppException(AkuraWebConstant.HIBERNATE_INVALID_DEL_OPERATION, e);
            }
        }
    }
    
    /**
     * Handle request for Parent Details change by parent.
     * 
     * @param parentWrapper model attribute
     * @param result error object
     * @param model model attribute map
     * @param request HttpServletRequest object
     * @param session HttpSession session object
     * @return String View name
     * @throws AkuraAppException when exception occurs
     */
    @RequestMapping(value = SAVE_PARENT_BY_PARENT_HTM, method = RequestMethod.POST)
    public String editParentByParent(@ModelAttribute(PARENT_WRAPPER) ParentWrapper parentWrapper, BindingResult result,
            ModelMap model, final HttpServletRequest request, HttpSession session) throws AkuraAppException {
    
        int studentId = Integer.parseInt(session.getAttribute(STUDENT_ID).toString());
        
        parentValidator.validate(parentWrapper, result);
        if (result.hasErrors()) {
            setupStudentRatingDetails(model, session);
            setDonationListToModel(studentId, model);
            return VIEW_PARENT;
        }
        
        // get the list of current parent list
        char relation = parentWrapper.getRelationship().charAt(0);
        // new parent info
        Parent requestParent = parentWrapper.getParent();
        
        List<StudentParent> currentParents = parentService.getParentListBy(studentId, relation);
        Parent oldParent = null;
        if (currentParents != null && !currentParents.isEmpty()) {
            oldParent = currentParents.get(0).getParent();
            // response page must not display new parent info should display old record
            parentWrapper.setParent(oldParent);
        } else {
            // response page should not display new parent info
            parentWrapper.setParent(new Parent());
        }
        
        // changes for Father or Guardian
        Map<String, String> parentChages = new HashMap<String, String>();
        
        String parentType = ""; // Father/Mother/Guardian
        if (relation == FATHER_CHAR) {
            parentType = FATHER_LABEL; // father
        } else if (relation == MOTHER_CHAR) {
            parentType = "Mother";
        } else if (relation == GUARDIAN_CHAR) {
            parentType = GUARDIAN_LABEL;
        }
        
        parentChages = setDiffToMap(requestParent, oldParent, parentChages);
        
        // send mails
        if (!parentChages.isEmpty()) {
            Student student = studentService.findStudent(studentId);
            UserInfo userInfo = (UserInfo) session.getAttribute(USER);
            sendEmails(userInfo, student, parentChages, model, parentType);
        } else {
            model.addAttribute(MESSAGE, "No changes found");
        }
        
        setupStudentRatingDetails(model, session);
        setDonationListToModel(studentId, model);
        return VIEW_PARENT;
    }
    
    /**
     * get difference as Map. key-field name, value-new value
     * 
     * @param newParent Object with new values
     * @param oldParent object with old values
     * @param chages changes will set to this map
     * @return Map difference
     * @throws AkuraAppException when exception occurs
     */
    private Map<String, String> setDiffToMap(Parent newParent, Parent oldParent, Map<String, String> chages)
            throws AkuraAppException {
    
        if (newParent != null) {
            // if oldParent exist(not null) -> check difference
            // else add as new parent record,if new record is not empty
            if (newParent.getNationalIdNo() != null
                    && ((oldParent != null && !newParent.getNationalIdNo().equals(oldParent.getNationalIdNo())) 
                            || oldParent == null)) {
                chages.put(StudentConstant.NIC, newParent.getNationalIdNo());
            }
            if (newParent.getFullName() != null
                    && ((oldParent != null && !newParent.getFullName().equals(oldParent.getFullName())) 
                            || oldParent == null)) {
                chages.put(StudentConstant.FULL_NAME, newParent.getFullName());
            }
            if (newParent.getLastName() != null
                    && ((oldParent != null && !newParent.getLastName().equals(oldParent.getLastName())) 
                            || (oldParent == null && !newParent
                            .getLastName().trim().isEmpty()))) {
                chages.put(StudentConstant.LAST_NAME, newParent.getLastName());
            }
            if (newParent.getNameWithInitials() != null
                    && ((oldParent != null && !newParent.getNameWithInitials().equals(oldParent.getNameWithInitials())) 
                            || (oldParent == null && !newParent
                            .getNameWithInitials().trim().isEmpty()))) {
                chages.put(StudentConstant.NAME_WITH_INITIALS, newParent.getNameWithInitials());
            }
            if (newParent.getEmail() != null
                    && ((oldParent != null && !newParent.getEmail().equals(oldParent.getEmail())) 
                            || (oldParent == null && !newParent
                            .getEmail().isEmpty()))) {
                chages.put(StudentConstant.EMAIL_PERSONAL, newParent.getEmail());
            }
            if (newParent.getMobileNo() != null
                    && ((oldParent != null && !newParent.getMobileNo().equals(oldParent.getMobileNo())) 
                            || (oldParent == null && !newParent
                            .getMobileNo().isEmpty()))) {
                
                chages.put(StudentConstant.PHONE_MOB, newParent.getMobileNo());
            }
            if (newParent.getResidenceNo() != null
                    && ((oldParent != null && !newParent.getResidenceNo().equals(oldParent.getResidenceNo()))
                            || (oldParent == null && !newParent
                            .getResidenceNo().isEmpty()))) {
                
                chages.put(StudentConstant.PHONE_RES, newParent.getResidenceNo());
            }
            // past pupil and alumni no
            if (oldParent != null) {
                if (oldParent.isPastPupil() != newParent.isPastPupil()) {
                    if (newParent.isPastPupil()) { // pass pupil selected
                        String value = StudentConstant.YES;
                        if (!newParent.getAlumniId().trim().isEmpty()
                                && newParent.getAlumniId() != oldParent.getAlumniId()) {
                            value += StudentConstant.WITH_ALUMNI_NO + String.valueOf(newParent.getAlumniId());
                        }
                        chages.put(StudentConstant.IS_PAST_PUPIL, value);
                    } else { // pass pupil unselected
                        chages.put(StudentConstant.IS_PAST_PUPIL, StudentConstant.NO);
                    }
                } else if (newParent.getAlumniId() != null && !newParent.getAlumniId().trim().isEmpty()
                        && !newParent.getAlumniId().equals(oldParent.getAlumniId())) {
                    // only change alumni no
                    chages.put(StudentConstant.ALUMNI_NO, String.valueOf(newParent.getAlumniId()));
                }
            } else if (newParent.isPastPupil()) {
                String value = StudentConstant.YES;
                if (!newParent.getAlumniId().trim().isEmpty()) {
                    value += StudentConstant.WITH_ALUMNI_NO + String.valueOf(newParent.getAlumniId());
                }
                chages.put(StudentConstant.IS_PAST_PUPIL, value);
            }
            // parent is teacher
            if ((oldParent != null && oldParent.isTeacher() != newParent.isTeacher())
                    || (oldParent == null && newParent.isTeacher())) {
                
                if (newParent.isTeacher()) { // pass pupil selected
                    chages.put(StudentConstant.IS_TEACHER, StudentConstant.YES);
                    // alumni no
                    if (!newParent.getTeacherRegNo().isEmpty()
                            && ((oldParent != null && !newParent.getTeacherRegNo().equals(oldParent.getTeacherRegNo()))
                                    || oldParent == null)) {
                        
                        chages.put(StudentConstant.TEACHER_REG_NO, newParent.getTeacherRegNo());
                    }
                } else {
                    chages.put(StudentConstant.IS_TEACHER, StudentConstant.NO);
                }
            }
            
            if (newParent.getAddress() != null
                    && ((oldParent != null && !newParent.getAddress().equals(oldParent.getAddress())) 
                            || (oldParent == null && !newParent
                            .getAddress().trim().isEmpty()))) {
                
                chages.put(StudentConstant.ADDRESS, newParent.getAddress());
            }
            if(oldParent != null){
                if (newParent.getCityId() != null && ((oldParent != null && oldParent.getCityId() 
                        != null && newParent.getCityId().intValue() != oldParent
                                .getCityId().intValue()) || oldParent.getCityId() == null)) {
                    // get city name by id
                    if (newParent.getCityId().intValue() > 0) {
                        City city = commonService.findCity(newParent.getCityId());
                        chages.put(StudentConstant.CITY, city.getDescription());
                    } else {
                        chages.put(StudentConstant.CITY, "");
                    }
                }
            }
            
            if (newParent.getTempAddress() != null
                    && ((oldParent != null && !newParent.getTempAddress().equals(oldParent.getTempAddress())) 
                            || (oldParent == null && !newParent
                            .getTempAddress().trim().isEmpty()))) {
                chages.put(StudentConstant.ADDRESS_TEMP, newParent.getTempAddress());
            }
            if(oldParent != null){
                if (newParent.getTempCityId() != null
                        && ((oldParent != null && oldParent.getTempCityId() 
                        != null && newParent.getTempCityId().intValue() != oldParent
                                .getTempCityId().intValue()) || oldParent.getTempCityId() == null)) {
                    // get city name by id
                    if (newParent.getTempCityId().intValue() > 0) {
                        City city = commonService.findCity(newParent.getTempCityId());
                        chages.put(StudentConstant.CITY_TEMP, city.getDescription());
                    } else {
                        chages.put(StudentConstant.CITY_TEMP, "");
                    }
                }
            }
            
            if(oldParent != null){
                if (newParent.getReligionId() != null
                        && ((oldParent != null && oldParent.getReligionId() 
                        != null && newParent.getReligionId().intValue() != oldParent
                                .getReligionId().intValue()) || oldParent.getReligionId() == null)) {
                    // get religion name by id
                    if (newParent.getReligionId() > 0) {
                        Religion religion = commonService.findReligionById(newParent.getReligionId());
                        chages.put(StudentConstant.RELIGION, religion.getDescription());
                    } else {
                        chages.put(StudentConstant.RELIGION, "");
                    }
                }
            }
            helpOfficeDiff(newParent, oldParent, chages);
        }
        return chages;
    }
    
    /**
     * help method to get difference for office details.
     * 
     * @param newParent Object with new values
     * @param oldParent object with old values
     * @param chages returns difference map
     * @throws AkuraAppException when exception occurs
     */
    private void helpOfficeDiff(Parent newParent, Parent oldParent, Map<String, String> chages)
            throws AkuraAppException {
    
        if (newParent.getWorkingSegmentId() != null
                && ((oldParent != null && ((oldParent.getWorkingSegmentId() != null && newParent.getWorkingSegmentId()
                        .intValue() != oldParent.getWorkingSegmentId().intValue())
                        || oldParent.getWorkingSegmentId() == null)) || oldParent == null)) {
            if (newParent.getWorkingSegmentId() > 0) {
                WorkingSegment wokSeg = commonService.findWorkingSegmentById(newParent.getWorkingSegmentId());
                chages.put(StudentConstant.WORKING_SEGMENT, wokSeg.getDescription());
            } else {
                chages.put(StudentConstant.WORKING_SEGMENT, "");
            }
        }
        
        if (newParent.getEmploymentStatusId() != null
                && ((oldParent != null && ((oldParent.getEmploymentStatusId() != null && newParent
                        .getEmploymentStatusId().intValue() 
                        != oldParent.getEmploymentStatusId().intValue()) || oldParent
                        .getEmploymentStatusId() == null)) || oldParent == null)) {
            if (newParent.getEmploymentStatusId() > 0) {
                EmploymentStatus empStatus = commonService.findEmploymentStatusById(newParent.getEmploymentStatusId());
                chages.put(StudentConstant.EMPLOYMENT_STATUS, empStatus.getDescription());
            } else {
                chages.put(StudentConstant.EMPLOYMENT_STATUS, "");
            }
        }
        
        if (newParent.getDesignation() != null
                && ((oldParent != null && !newParent.getDesignation().equals(oldParent.getDesignation())) 
                        || (oldParent == null && !newParent
                        .getDesignation().trim().isEmpty()))) {
            
            chages.put(StudentConstant.DESIGNATION, newParent.getDesignation());
        }
        if (newParent.getIncomeLevel() != null
                && ((oldParent != null && !newParent.getIncomeLevel().equals(oldParent.getIncomeLevel())) 
                        || (oldParent == null && !newParent
                        .getIncomeLevel().trim().isEmpty()))) {
            
            chages.put(StudentConstant.INCOME_LEVEL, newParent.getIncomeLevel());
        }
        if (newParent.getOfficeName() != null
                && ((oldParent != null && !newParent.getOfficeName().equals(oldParent.getOfficeName())) 
                        || (oldParent == null && !newParent
                        .getOfficeName().trim().isEmpty()))) {
            chages.put(StudentConstant.OFFICE_NAME, newParent.getOfficeName());
        }
        if (newParent.getOfficeAddress() != null
                && ((oldParent != null && !newParent.getOfficeAddress().equals(oldParent.getOfficeAddress())) 
                        || (oldParent == null && !newParent
                        .getOfficeAddress().trim().isEmpty()))) {
            chages.put(StudentConstant.ADDRESS_OFFICE, newParent.getOfficeAddress());
        }
        if (newParent.getOfficeEmail() != null
                && ((oldParent != null && !newParent.getOfficeEmail().equals(oldParent.getOfficeEmail())) 
                        || (oldParent == null && !newParent
                        .getOfficeEmail().trim().isEmpty()))) {
            chages.put(StudentConstant.EMAIL_OFFICE, newParent.getOfficeName());
        }
        if (newParent.getOfficeFaxNo() != null
                && ((oldParent != null && !newParent.getOfficeFaxNo().equals(oldParent.getOfficeFaxNo())) 
                        || (oldParent == null && !newParent
                        .getOfficeFaxNo().trim().isEmpty()))) {
            chages.put(StudentConstant.FAX_NO_OFFICE, newParent.getOfficeFaxNo());
        }
        if (newParent.getOfficeNo() != null
                && ((oldParent != null && !newParent.getOfficeNo().equals(oldParent.getOfficeNo()))
                        || (oldParent == null && !newParent
                        .getOfficeNo().trim().isEmpty()))) {
            chages.put(StudentConstant.PHONE_OFFICE, newParent.getOfficeFaxNo());
        }
        if(oldParent != null){
            if (newParent.getOfficeCityId() != null
                    && ((oldParent != null && oldParent.getOfficeCityId() 
                    != null && newParent.getOfficeCityId().intValue() != oldParent
                            .getOfficeCityId().intValue()) || oldParent.getOfficeCityId() == null)) {
                // get city name by id
                if (newParent.getOfficeCityId().intValue() > 0) {
                    City city = commonService.findCity(newParent.getOfficeCityId());
                    chages.put(StudentConstant.CITY_OFFICE, city.getDescription());
                } else {
                    chages.put(StudentConstant.CITY_OFFICE, "");
                }
            }
        }
        
    }
    
    /**
     * email will send for given info to admin and user.
     * 
     * @param userInfo requesting user
     * @param student student info
     * @param parentChages father/guardian changes as map
     * @param model model map to set errors and messages
     * @param typeForG label for Father or Guardian
     */
    private void sendEmails(UserInfo userInfo, Student student, Map<String, String> parentChages, ModelMap model,
            String typeForG) {
    
        String adminEmail = PropertyReader.getPropertyValue(EMAIL, ADMIN_EMAIL);
        ErrorMsgLoader errorLoder = new ErrorMsgLoader();
        
        Map<String, Object> mailData = new HashMap<String, Object>();
        mailData.put(USER_NAME, userInfo.getUsername());
        mailData.put(STUDENT_NAME, student.getFullName());
        mailData.put(ADDMISION_NO, student.getAdmissionNo());
        mailData.put(CHANGES_FOR_G, parentChages);
        mailData.put(TYPE_F_OR_G, typeForG);
        boolean successSend = false;
        try {// send to admin
        
            mailData.put(GREETING, ADMIN_GREETING);
            mailData.put(CONTENT, PropertyReader.getPropertyValue(EMAIL, CONTENT_MESSAGE_FOR_ADMIN));
            sendEmailTo(adminEmail, adminEmail, null, mailData);
            successSend = true;
            model.addAttribute(ADMIN_MAIL_STATUS, errorLoder.getErrorMessage(ADMIN_MAIL_SEND_STATUS_MESSAGE));
            
        } catch (AkuraAppException e) {
            model.addAttribute(ERROR_EMAIL, errorLoder.getErrorMessage(MESSAGE_ERROR_SEND_TO_ADMIN));
            LOG.error("exception sending Mail" + e.toString());
        } catch (MailSendException e) {
            model.addAttribute(ERROR_EMAIL, errorLoder.getErrorMessage(MESSAGE_ERROR_SEND_TO_ADMIN));
            LOG.error("exception sending Mail" + e.toString());
        }
        // send email to user if admin email successfully send
        if (successSend) {
            try {
                
                Parent parentUser = parentService.getParentByNIC(userInfo.getUserLevelIdentifier());
                mailData.put(GREETING, DEAR + parentUser.getFullName());
                mailData.put(CONTENT, PropertyReader.getPropertyValue(EMAIL, CONTENT_MESSAGE_FOR_USER));
                String toaddress = parentUser.getEmail();
                if (!toaddress.isEmpty()) {
                    // cc addresses
                    List<StudentParent> studentParentList =
                            parentService.getStudentParentListByStudentId(student.getStudentId());
                    List<String> ccAddressList = new ArrayList<String>();
                    for (StudentParent sp : studentParentList) {
                        if (!sp.getParent().getEmail().equals(toaddress)) {
                            ccAddressList.add(sp.getParent().getEmail());
                        }
                    }
                    
                    sendEmailTo(adminEmail, toaddress, ccAddressList, mailData);
                    model.addAttribute(USER_MAIL_STATUS, errorLoder.getErrorMessage(USER_MAIL_SEND_STATUS_MESSAGE));
                } else {
                    model.addAttribute(ERROR_EMAIL, errorLoder.getErrorMessage(NO_USER_EMAIL_ADDRESS_FOUND));
                }
            } catch (AkuraAppException e) {
                model.addAttribute(ERROR_EMAIL, errorLoder.getErrorMessage(ERROER_SENDING_EMAIL_TO_USER));
                LOG.error("exception sending Mail" + e.toString());
            } catch (MailSendException e) {
                model.addAttribute(ERROR_EMAIL, errorLoder.getErrorMessage(ERROER_SENDING_EMAIL_TO_USER));
                LOG.error("exception sending Mail" + e.toString());
            }
        }
    }
    
    /**
     * send email to given details.
     * 
     * @param fromAddress email address from
     * @param toAddress email address to
     * @param mailData template data
     * @param ccAddressList cc address list if any
     * @throws AkuraAppException when exception occurs
     */
    private void sendEmailTo(String fromAddress, String toAddress, List<String> ccAddressList,
            Map<String, Object> mailData) throws AkuraAppException {
    
        try {
            
            CommonEmailBean commonEmailBean = new CommonEmailBean();
            String templateName = PropertyReader.getPropertyValue(EMAIL, EMAIL_TEMPLATE_NAME);
            String strSubject = PARENT_DETAIL_MODIFICATION;
            Map<String, Object> mapObjectMap = new HashMap<String, Object>();
            mapObjectMap.put(MESSAGE, mailData);
            EmailUtil.addHeaderForEmail(mapObjectMap);
            
            if (!fromAddress.isEmpty()) {
                commonEmailBean.setFromAddress(fromAddress);
            }
            commonEmailBean.setToAddress(toAddress);
            if (ccAddressList != null) {
                commonEmailBean.setCcAddresses(ccAddressList);
            }
            
            commonEmailBean.setMailTemplate(templateName);
            commonEmailBean.setObjectMap(mapObjectMap);
            commonEmailBean.setSubject(strSubject);
            emailService.sendMail(commonEmailBean);
            commonEmailBean.setAttachementPath(null);
            
        } catch (ResourceNotFoundException e) {
            throw new AkuraAppException(AkuraConstant.EMAIL_ERROR, e);
        }
        
    }
    
    /**
     * Get list of available countries list for .
     * 
     * @return map of country id and short code.
     * @throws AkuraAppException - Throw detail exception when fails.
     */
    @ModelAttribute(PHONE_COUNTRY_LIST)
    public Map<String, Country> populatePhoneCountryList() throws AkuraAppException {
    
        Map<String, Country> phoneMap = new LinkedHashMap<String, Country>();
        List<Country> countryList = null;
        countryList = SortUtil.sortCountries((commonService.getCountryList()));
        for (Country country : countryList) {
            
            if (country.getCountryCode() != null) {
                phoneMap.put(AkuraConstant.PLUS_SIGN +
                        PhoneNumberValidateUtil.findCountryCode(country.getCountryCode()), country);
                
            }
        }
        return phoneMap;
    }
    
    /**
     * display Residence PhoneNumber Details.
     * 
     * @param parent - Parent related data object
     * @param selectedCountryCodeRes - String for selected Residence phone country code  
     * @throws AkuraAppException - Throw detail exception when fails.
     */
    private void displayResidencePhoneNumberDetails(Parent parent, String selectedCountryCodeRes)
            throws AkuraAppException {
    
        String resPhoneNumber = AkuraConstant.EMPTY_STRING;
        if (parent.getResidenceNo().startsWith(AkuraConstant.STRING_ZERO)) {
            
            resPhoneNumber =
                    PhoneNumberValidateUtil.formatPhoneNumber(parent.getResidenceNo(),
                            selectedCountryCodeRes, AkuraConstant.PARAM_INDEX_ZERO);
            parent.setResidenceNo(resPhoneNumber);
        } else {
            resPhoneNumber =
                    PhoneNumberValidateUtil.formatPhoneNumber(
                            AkuraConstant.STRING_ZERO + parent.getResidenceNo(),selectedCountryCodeRes
                            , AkuraConstant.PARAM_INDEX_ZERO);
            parent.setResidenceNo(resPhoneNumber);
        }
        
    }
    
    /**
     * View the staff phone number validation details.
     * 
     * @param parent - Parent related data object
     * @param model - {@link ModelMap}
     * @throws AkuraAppException - Throw detail exception when fails.
     */
    private void displayPhoneNumberDetails(Parent parent, ModelMap model) throws AkuraAppException {
    
        if (parent.getResidenceNo() != null) {
            if (parent.getResidenceNo().contains(AkuraConstant.EMPTY_STRING_SPACE)) {
                String countryCode =
                        parent.getResidenceNo().substring(0, parent.getResidenceNo().indexOf(' ')).replace('+', ' ')
                                .trim();
                String resNumber =
                        parent.getResidenceNo().substring(parent.getResidenceNo().indexOf(' ')).replaceAll("\\s", "");
                parent.setResidenceNo((resNumber.startsWith(AkuraConstant.STRING_ZERO))
                        ? resNumber.substring(1) : resNumber);
                model.addAttribute(MODEL_ATT_SELECTED_RES_COUNTRY,
                        PhoneNumberValidateUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode)));
            } else {
                model.addAttribute(MODEL_ATT_SELECTED_RES_COUNTRY, 0);
            }
        }
        
        if (parent.getMobileNo() != null) {
            if (parent.getMobileNo().contains(AkuraConstant.EMPTY_STRING_SPACE)) {
                String countryCode =
                        parent.getMobileNo().substring(0, parent.getMobileNo().indexOf(' ')).replace('+', ' ').trim();
                String mobNumber =
                        parent.getMobileNo().substring(parent.getMobileNo().indexOf(' ')).replaceAll("\\s", "");
                parent.setMobileNo((mobNumber.startsWith(AkuraConstant.STRING_ZERO))
                        ? mobNumber.substring(1) : mobNumber);
                model.addAttribute(MODEL_ATT_SELECTED_MOB_COUNTRY,
                        PhoneNumberValidateUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode)));
            } else {
                model.addAttribute(MODEL_ATT_SELECTED_MOB_COUNTRY, 0);
            }
        }
        
        if (parent.getOfficeNo() != null) {
            if (parent.getOfficeNo().contains(AkuraConstant.EMPTY_STRING_SPACE)) {
                String countryCode =
                        parent.getOfficeNo()
                                .substring(0, parent.getOfficeNo().indexOf(' ')).replace('+', ' ')
                                .trim();
                String emgResNumber =
                        parent.getOfficeNo()
                                .substring(parent.getOfficeNo().indexOf(' ')).replaceAll("\\s", "");
                parent.setOfficeNo((emgResNumber.startsWith(AkuraConstant.STRING_ZERO)) ? 
                        emgResNumber.substring(1):emgResNumber);
                model.addAttribute(MODEL_ATT_SELECTED_OFFICE_COUNTRY,
                        PhoneNumberValidateUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode)));
            } else {
                model.addAttribute(MODEL_ATT_SELECTED_OFFICE_COUNTRY, 0);
            }
        }
        
        if (parent.getOfficeFaxNo() != null) {
            if (parent.getOfficeFaxNo().contains(AkuraConstant.EMPTY_STRING_SPACE)) {
                String countryCode =
                        parent.getOfficeFaxNo()
                                .substring(0, parent.getOfficeFaxNo().indexOf(' ')).replace('+', ' ')
                                .trim();
                String emgMobNumber =
                        parent.getOfficeFaxNo().substring(parent.getOfficeFaxNo().indexOf(' '))
                                .replaceAll("\\s", "");
                parent.setOfficeFaxNo((emgMobNumber.startsWith(AkuraConstant.STRING_ZERO)) ? 
                        emgMobNumber.substring(1): emgMobNumber);
                model.addAttribute(MODEL_ATT_SELECTED_OFFICE_FAX_COUNTRY,
                        PhoneNumberValidateUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode)));
            } else {
                model.addAttribute(MODEL_ATT_SELECTED_OFFICE_FAX_COUNTRY, 0);
            }
        }
    }
    
    /**
     * Reset the country flag combo boxes.
     * 
     * @param selectedCountryCodeRes - String for selected Residence phone country code  
     * @param selectedCountryCodeMob - String for selected Mobile phone country code  
     * @param selectedCountryCodeOff - String for selected office phone country code  
     * @param selectedCountryCodeFax - String for selected fax phone country code  
     * @param model - ModelMap
     * @throws AkuraAppException - Throw detail exception when fails.
     */
    private void resetCountryFlags(String selectedCountryCodeRes, String selectedCountryCodeMob,
            String selectedCountryCodeOff, String selectedCountryCodeFax, ModelMap model) throws AkuraAppException {
    
        model.addAttribute(MODEL_ATT_SELECTED_RES_COUNTRY,
                (selectedCountryCodeRes.isEmpty()) ? AkuraConstant.STRING_ZERO : selectedCountryCodeRes);
        model.addAttribute(MODEL_ATT_SELECTED_MOB_COUNTRY,
                (selectedCountryCodeMob.isEmpty()) ? AkuraConstant.STRING_ZERO : selectedCountryCodeMob);
        model.addAttribute(MODEL_ATT_SELECTED_OFFICE_COUNTRY,
                (selectedCountryCodeOff.isEmpty()) ? AkuraConstant.STRING_ZERO : selectedCountryCodeOff);
        model.addAttribute(MODEL_ATT_SELECTED_OFFICE_FAX_COUNTRY,
                (selectedCountryCodeFax.isEmpty()) ? AkuraConstant.STRING_ZERO : selectedCountryCodeFax);
    }
    
    /**
     * display Mobile PhoneNumber Details.
     * 
     * @param parent - Parent related data object
     * @param selectedCountryCodeMob - String for selected Mobile phone country code
     * @throws AkuraAppException - Throw detail exception when fails.
     */
    private void displayMobilePhoneNumberDetails(Parent parent, String selectedCountryCodeMob)
            throws AkuraAppException {
    
        String mobPhoneNumber = AkuraConstant.EMPTY_STRING;
        if (parent.getMobileNo().startsWith(AkuraConstant.STRING_ZERO)) {
            mobPhoneNumber =
                    PhoneNumberValidateUtil.formatPhoneNumber(parent.getMobileNo(),
                            selectedCountryCodeMob, AkuraConstant.PARAM_INDEX_ZERO);
            parent.setMobileNo(mobPhoneNumber);
        } else {
            mobPhoneNumber =
                    PhoneNumberValidateUtil.formatPhoneNumber(
                            AkuraConstant.STRING_ZERO + parent.getMobileNo(),selectedCountryCodeMob
                            , AkuraConstant.PARAM_INDEX_ZERO);
            parent.setMobileNo(mobPhoneNumber);
        }
        
    }
    
    /**
     * display Country Flags when error occurs.
     * 
     * @param parent - Parent related data object
     * @param model for display model error
     * @param selectedCountryCodeRes - String for selected Residence phone country code  
     * @param selectedCountryCodeMob - String for selected Mobile phone country code  
     * @param selectedCountryCodeOff - String for selected office phone country code  
     * @param selectedCountryCodeFax - String for selected fax phone country code  
     * @throws AkuraAppException - Throw detail exception when fails.
     */
    private void displayCountryFlagsWhenError(Parent parent, ModelMap model, String selectedCountryCodeRes,
            String selectedCountryCodeMob, String selectedCountryCodeOff, String selectedCountryCodeFax)
            throws AkuraAppException {
    
        String errorMessage = new ErrorMsgLoader().getErrorMessage(MESSAGE_PHONE_NO_COUNTRY_MISMATCH);
        model.addAttribute(MESSAGE, errorMessage);
        displayPhoneNumberDetails(parent, model);
        resetCountryFlags(selectedCountryCodeRes, selectedCountryCodeMob, selectedCountryCodeOff,
                selectedCountryCodeFax, model);
    }
    
    /**
     * display office PhoneNumber Details.
     * 
     * @param parent - Parent related data object
     * @param selectedCountryCodeOff - String for selected office phone country code  
     * @throws AkuraAppException - Throw detail exception when fails.
     */
    private void displayOfficePhoneNumberDetails(Parent parent, String selectedCountryCodeOff)
            throws AkuraAppException {
    
        String offPhoneNumber = AkuraConstant.EMPTY_STRING;
        if (parent.getOfficeNo().startsWith(AkuraConstant.STRING_ZERO)) {
            offPhoneNumber =
                    PhoneNumberValidateUtil.formatPhoneNumber(parent.getOfficeNo(),
                            selectedCountryCodeOff, AkuraConstant.PARAM_INDEX_ZERO);
            parent.setOfficeNo(offPhoneNumber);
        } else {
            offPhoneNumber =
                    PhoneNumberValidateUtil.formatPhoneNumber(
                            AkuraConstant.STRING_ZERO + parent.getOfficeNo(), selectedCountryCodeOff
                            , AkuraConstant.PARAM_INDEX_ZERO);
            parent.setOfficeNo(offPhoneNumber);
        }
        
    }
    
    /**
     * display fax PhoneNumber Details.
     * 
     * @param parent - Parent related data object
     * @param selectedCountryCodeFax - String for selected fax phone country code  
     * @throws AkuraAppException - Throw detail exception when fails.
     */
    private void displayOfficeFaxNumberDetails(Parent parent, String selectedCountryCodeFax)
            throws AkuraAppException {
    
        String faxPhoneNumber = AkuraConstant.EMPTY_STRING;
        if (parent.getOfficeFaxNo().startsWith(AkuraConstant.STRING_ZERO)) {
            faxPhoneNumber =
                    PhoneNumberValidateUtil.formatPhoneNumber(parent.getOfficeFaxNo(),
                            selectedCountryCodeFax, AkuraConstant.PARAM_INDEX_ZERO);
            parent.setOfficeFaxNo(faxPhoneNumber);
        } else {
            faxPhoneNumber =
                    PhoneNumberValidateUtil.formatPhoneNumber(
                            AkuraConstant.STRING_ZERO + parent.getOfficeFaxNo(), selectedCountryCodeFax
                            , AkuraConstant.PARAM_INDEX_ZERO);
            parent.setOfficeFaxNo(faxPhoneNumber);
        }
        
    }
    
}
