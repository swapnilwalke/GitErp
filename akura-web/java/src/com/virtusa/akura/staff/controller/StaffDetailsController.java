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

package com.virtusa.akura.staff.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.virtusa.akura.api.dto.AppointmentClassification;
import com.virtusa.akura.api.dto.AppointmentNature;
import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.CivilStatus;
import com.virtusa.akura.api.dto.Country;
import com.virtusa.akura.api.dto.EducationalQualification;
import com.virtusa.akura.api.dto.ProfessionalQualification;
import com.virtusa.akura.api.dto.Race;
import com.virtusa.akura.api.dto.Religion;
import com.virtusa.akura.api.dto.Section;
import com.virtusa.akura.api.dto.SectionHead;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.StaffCategory;
import com.virtusa.akura.api.dto.StaffServiceCategory;
import com.virtusa.akura.api.dto.StudyMedium;
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.service.StaffCommonService;
import com.virtusa.akura.common.service.UserService;
import com.virtusa.akura.staff.service.StaffService;
import com.virtusa.akura.staff.validator.StaffDetailsValidator;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PhoneNumberValidateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;

import com.virtusa.akura.util.StaticDataUtil;

/**
 * The StaffDetailsController is to handle Add/Edit/View staff member details.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class StaffDetailsController {
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(StaffDetailsController.class);
    
    /** Represents the attribute for read only of the date of departure. */
    private static final String READ_ONLY = "readOnly";
    
    /** Represents the attribute for readOnlyJoinDate of the date of join. */
    private static final String READ_ONLY_JOIN_DATE_MODEL = "readOnlyJoinDate";
    
    /** The Constant COUNTRY_LIST. */
    private static final String COUNTRY_LIST = "countryList";
    
    /** model attribute of subject list . */
    private static final String CORE_SUBJECT_LIST = "coreSubjectList";
    
    /** model attribute of study medium list . */
    private static final String STUDY_MEDIUM_LIST = "studyMediumList";
    
    /** view get method staff. */
    private static final String VIEW_GET_STAFF = "staff/staffMemberDetails";
    
    /** model attribute of staff. */
    private static final String MODEL_ATT_STAFF = "staff";
    
    /** model attribute of religion list. */
    private static final String MODEL_ATT_RELIGION_LIST = "religionList";
    
    /** model attribute of staff category list. */
    private static final String MODEL_ATT_STAFF_CATEGORY_LIST = "staffCategoryList";
    
    /** model attribute of EducationalQualification list. */
    private static final String MODEL_ATT_EDUCATIONALQUALIFICATION_LIST = "educationalQualificationList";
    
    /** model attribute of professionalQualification list. */
    private static final String MODEL_ATT_PROFESSIONALQUALIFICATION_LIST = "professionalQualificationList";
    
    /** model attribute of city list. */
    private static final String MODEL_ATT_CITY_LIST = "cityList";
    
    /** property file name. */
    private static final String SYSTEM_CONFIG_PROPERTIES = "systemConfig";
    
    /** key to get the image width from property file. */
    private static final String PROFILE_IMAGE_WIDTH = "imageFile.width";
    
    /** key to get the image height from property file. */
    private static final String PROFILE_IMAGE_HEIGHT = "imageFile.height";
    
    /** model attribute of image height . */
    private static final String MODEL_IMAGE_HEIGHT = "ImageHeight";
    
    /** model attribute of image width . */
    private static final String MODEL_IMAGE_WIDTH = "ImageWidth";
    
    /** request mapping value for add new staff. */
    private static final String REQ_MAP_VALUE_ADD_NEW = "/newStaffDetails.htm";
    
    /** request mapping value for view staff. */
    private static final String REQ_MAP_VALUE_VIEW = "/viewStaffMemberDetails.htm";
    
    /** request mapping value for save staff. */
    private static final String REQ_MAP_VALUE_SAVE = "/saveStaffDetails";
    
    /** key to hold message when record added. */
    private static final String MESSAGE_RECORD_ADDED = "STA.UI.RECORD.ADDED";
    
    /** key to hold message when record added. */
    private static final String MESSAGE_RECORD_MODIFIED = "STA.UI.RECORD.MODIFIED";
    
    /** key to hold message to record in log. */
    private static final String ERROR_RETRIEVING_FILE = "Error while retrieving the file --> ";
    
    /** key to hold message when record is duplicate. */
    private static final String DUPLICATE_DESCRIPTION_MESSAGE = "STA.UI.DUPLICATE.DESCRIPTION";
    
    /** model attribute of message. */
    private static final String MESSAGE = "message";
    
    /** key to hold request method. */
    private static final String REQUEST_METHOD = "GET";
    
    /** key to hold date format. */
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    
    /** key to hold redirect path to login. */
    private static final String REDIRECT_LOGIN = "redirect:login.htm";
    
    /** key to hold. */
    private static final String RESOURCES_PROFILE_IMAGES = "resources/profileImages/";
    
    /** key to hold. */
    private static final String FILE_EXT = ".jpg";
    
    /** model attribute of staff. */
    private static final String IMAGE_PATH = "ImagePath";
    
    /** Holds the value for appserver.home. */
    private static final String APPSERVER_HOME = "appserver.home";
    
    /** key to hold. */
    private static final String IMAGE_FOLDER_PATH = "imageFolder.path";
    
    /** key to hold. */
    private static final String STAFF_ID = "staffId";
    
    /** key to hold. */
    private static final String RESOURCES_NO_PROFILE_IMAGE = "resources/profileImages/no_profile_image.jpg";
    
    /** model attribute of staff. */
    private static final String DEFAULT_IMAGE = "defaultImage";
    
    /** model attribute of staff. */
    private static final String STAFF_ID_CHECK = "staffIdCheck";
    
    /** CommonService attribute for holding commonService. */
    private CommonService commonService;
    
    /** StaffCommonService attribute for holding staffCommonService. */
    private StaffCommonService staffCommonService;
    
    /** Represents an instance of StaffService. */
    private StaffService staffService;
    
    /** UserService attribute for holding userService. */
    private UserService userService;
    
    /** Represents an instance of staffDetailsValidator. */
    private StaffDetailsValidator staffDetailsValidator;
    
    /** key to hold field core subject. */
    private static final String CORE_SUBJECT = "coreSubject.subjectId";
    
    /** key to hold field study medium. */
    private static final String STUDY_MEDIUM = "studyMedium.studyMediumId";
    
    /** model attribute of section list. */
    private static final String SECTION_LIST = "sectionList";
    
    /** model attribute of race list. */
    private static final String RACE_LIST = "raceList";
    
    /** model attribute of civil status list. */
    private static final String CIVIL_STATUS_LIST = "civilStatusList";
    
    /** model attribute of appointmentNature list. */
    private static final String APPOINTMENT_NATURE_LIST = "appointmentNatureList";
    
    /** model attribute of appointmentClassification list. */
    private static final String APPOINTMENT_CLASSIFICATION_LIST = "appointmentClassificationList";
    
    /** model attribute of staffService list. */
    private static final String STAFF_SERVICE_LIST = "staffServiceList";
    
    /** The Constant "STA.UI.EMAIL.EXIST.ERROR". */
    private static final String STA_UI_EMAIL_EXIST_ERROR = "STA.UI.EMAIL.EXIST.ERROR";
    
    /** The Constant "email". */
    private static final String EMAIL = "email";
    
    /** Represents the key for the image database size. */
    private static final String IMAGE_DATABASE_SIZE = "IMAGE.DATABASE.SIZE";
    
    /** Represents the key for the error messages. */
    private static final String MODEL_ERROR = "modelError";
    
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
    
    /**
     * @param commonServiceValue the commonService to set
     */
    public void setCommonService(CommonService commonServiceValue) {
    
        this.commonService = commonServiceValue;
    }
    
    /**
     * @param staffCommonServiceValue the commonService to set
     */
    public void setStaffCommonService(StaffCommonService staffCommonServiceValue) {
    
        this.staffCommonService = staffCommonServiceValue;
    }
    
    /**
     * @param userServiceValue the userService to set
     */
    public void setUserService(UserService userServiceValue) {
    
        this.userService = userServiceValue;
    }
    
    /**
     * @param staffServiceValue the staffService to set
     */
    public void setStaffService(StaffService staffServiceValue) {
    
        this.staffService = staffServiceValue;
    }
    
    /**
     * @param staffDetailsValidatorValue the staffDetailsValidator to set
     */
    public void setStaffDetailsValidator(StaffDetailsValidator staffDetailsValidatorValue) {
    
        this.staffDetailsValidator = staffDetailsValidatorValue;
    }
    
    /**
     * Add new staff member form.
     * 
     * @param request - the request object
     * @param model - {@link ModelMap}
     * @return view of the staff details.
     * @throws AkuraAppException - Throw detail exception.
     */
    @RequestMapping(value = REQ_MAP_VALUE_ADD_NEW)
    public String showStaffDetailsForm(HttpServletRequest request, ModelMap model) throws AkuraAppException {
    
        Staff staff = new Staff();
        
        model.addAttribute(STAFF_ID_CHECK, Boolean.TRUE);
        model.addAttribute(MODEL_ATT_STAFF, staff);
        displayPhoneNumberDetails(staff, model);
        model.addAttribute(DEFAULT_IMAGE, RESOURCES_NO_PROFILE_IMAGE);
        return VIEW_GET_STAFF;
    }
    
    /**
     * View the staff details.
     * 
     * @param request - the request object
     * @param model - {@link ModelMap}
     * @param session - {@link HttpSession}
     * @return view of the staff details.
     * @throws AkuraAppException - Throw detail exception.
     */
    @RequestMapping(value = REQ_MAP_VALUE_VIEW)
    public String loadStaffDetails(HttpServletRequest request, ModelMap model, HttpSession session)
            throws AkuraAppException {
    
        if ((session.getAttribute(STAFF_ID) != null) && ((Integer) session.getAttribute(STAFF_ID) != 0)) {
            int staffId = Integer.parseInt(session.getAttribute(STAFF_ID).toString());
            Staff staff = (Staff) staffService.viewStaff(staffId);
            model.addAttribute(STAFF_ID_CHECK, Boolean.FALSE);
            
            displayPhoneNumberDetails(staff, model);
            
            model.addAttribute(MODEL_ATT_STAFF, staff);
            session.setAttribute(MODEL_ATT_STAFF, staff);
            
            // keeps a deleted flag.
            isDeletedFlag(model, staff);
            
            if (staff.getPhoto() != null && staff.getPhoto().length > 0) {
                byte[] image = staff.getPhoto();
                String imageLoadPath =
                        PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, APPSERVER_HOME)
                                + PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, IMAGE_FOLDER_PATH);
                imageLoadPath = imageLoadPath + staff.getRegistrationNo() + FILE_EXT;
                StaticDataUtil.previewProfile(imageLoadPath, image);
                model.addAttribute(IMAGE_PATH, RESOURCES_PROFILE_IMAGES + staff.getRegistrationNo() + FILE_EXT);
            } else {
                model.addAttribute(IMAGE_PATH, RESOURCES_NO_PROFILE_IMAGE);
            }
            return VIEW_GET_STAFF;
        }
        
        return REDIRECT_LOGIN;
    }
    
    /**
     * Registers the given custom property editor for all properties of the Date type.
     * 
     * @param binder - data binder used to register the Date objects.
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    
        binder.getBindingResult();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    
    /**
     * Add staff details in to system.
     * 
     * @param staff - {@link Staff}
     * @param bindingResult - {@link BindingResult}
     * @param model - {@link ModelMap}
     * @param request - {@link HttpServletRequest}
     * @return view of the student details.
     * @throws AkuraAppException - throw detailed exception.
     */
    @RequestMapping(value = REQ_MAP_VALUE_SAVE)
    public String saveStaffDetails(@ModelAttribute(MODEL_ATT_STAFF) Staff staff, BindingResult bindingResult,
            ModelMap model, HttpServletRequest request) throws AkuraAppException {
    
        String selectedCountryCodeRes = request.getParameter(SELECTED_COUNTRYCODE_RES);
        String selectedCountryCodeMob = request.getParameter(SELECTED_COUNTRYCODE_MOB);
        String selectedCountryCodeEmgRes = request.getParameter(SELECTED_COUNTRYCODE_EMG_RES);
        String selectedCountryCodeEmgMob = request.getParameter(SELECTED_COUNTRYCODE_EMG_MOB);
        
        if (request.getMethod().equals(REQUEST_METHOD)) {
            return VIEW_GET_STAFF;
        }
        
        staffDetailsValidator.validate(staff, bindingResult);
        if (bindingResult.hasErrors()) {
            handleValidationError(staff, model);
            resetCountryFlags(selectedCountryCodeRes, selectedCountryCodeMob, selectedCountryCodeEmgRes,
                    selectedCountryCodeEmgMob, model);
            return VIEW_GET_STAFF;
        }
        checkEmailExistOrNot(staff, bindingResult);
        if (bindingResult.hasErrors()) {
            handleValidationError(staff, model);
            resetCountryFlags(selectedCountryCodeRes, selectedCountryCodeMob, selectedCountryCodeEmgRes,
                    selectedCountryCodeEmgMob, model);
            return VIEW_GET_STAFF;
        }
        
        if (!staff.getResidenceNo().isEmpty() && !selectedCountryCodeRes.isEmpty()) {
            if (staff.getResidenceNo() != null && !selectedCountryCodeRes.equals(AkuraConstant.STRING_ZERO) 
                    && PhoneNumberValidateUtil.isValidPhoneNumber(staff.getResidenceNo(), selectedCountryCodeRes)) {
                displayResidencePhoneNumberDetails(staff, selectedCountryCodeRes);
            } else {
                displayCountryFlagsWhenError(staff, model, selectedCountryCodeRes,
                        selectedCountryCodeMob, selectedCountryCodeEmgRes, selectedCountryCodeEmgMob);
                return VIEW_GET_STAFF;
            }
        }
        
        if (!staff.getMobileNo().isEmpty() && !selectedCountryCodeMob.isEmpty()) {
            if (staff.getMobileNo() != null && !selectedCountryCodeMob.equals(AkuraConstant.STRING_ZERO) 
                    && PhoneNumberValidateUtil.isValidPhoneNumber(staff.getMobileNo(), selectedCountryCodeMob)) {
                displayMobilePhoneNumberDetails(staff, selectedCountryCodeMob);
            } else {
                displayCountryFlagsWhenError(staff, model, selectedCountryCodeRes,
                        selectedCountryCodeMob, selectedCountryCodeEmgRes, selectedCountryCodeEmgMob);
                return VIEW_GET_STAFF;
            }
        }
        
        if (!staff.getEmergencyContactResidenceNo().isEmpty() && !selectedCountryCodeEmgRes.isEmpty()) {
            if (staff.getEmergencyContactResidenceNo() != null && !selectedCountryCodeEmgRes
                    .equals(AkuraConstant.STRING_ZERO) && PhoneNumberValidateUtil.
                    isValidPhoneNumber(staff.getEmergencyContactResidenceNo(), selectedCountryCodeEmgRes)) {
                displayEmgResidencePhoneNumberDetails(staff, selectedCountryCodeEmgRes);
            } else {
                displayCountryFlagsWhenError(staff, model, selectedCountryCodeRes,
                        selectedCountryCodeMob, selectedCountryCodeEmgRes, selectedCountryCodeEmgMob);
                return VIEW_GET_STAFF;
            }
        }
        
        if (!staff.getEmergencyContactMobileNo().isEmpty() && !selectedCountryCodeEmgMob.isEmpty()) {
            if (staff.getEmergencyContactMobileNo() != null && !selectedCountryCodeEmgMob
                    .equals(AkuraConstant.STRING_ZERO) && PhoneNumberValidateUtil.
                    isValidPhoneNumber(staff.getEmergencyContactMobileNo(), selectedCountryCodeEmgMob)) {
                displayEmgMobilePhoneNumberDetails(staff, selectedCountryCodeEmgMob);
            } else {
                displayCountryFlagsWhenError(staff, model, selectedCountryCodeRes,
                        selectedCountryCodeMob, selectedCountryCodeEmgRes, selectedCountryCodeEmgMob);
                return VIEW_GET_STAFF;
            }
        }
        
        if(staff.getRegistrationNo()!=null){
            staff.setRegistrationNo(staff.getRegistrationNo().trim());
        }
        boolean isAcademicCategory = validateCoreSubject(staff, bindingResult);
        if (bindingResult.hasErrors()) {
            handleValidationError(staff, model);
            resetCountryFlags(selectedCountryCodeRes, selectedCountryCodeMob, selectedCountryCodeEmgRes,
                    selectedCountryCodeEmgMob, model);
            return VIEW_GET_STAFF;
        } else {
            boolean isNew = false;
            
            try {
                if (!isAcademicCategory) {
                    
                    staff.setCoreSubject(null);
                    staff.setStudyMedium(null);
                }
                
                int maxStaffId = staffService.getMaxStaffId();
                if (maxStaffId < staff.getStaffId()) {
                    uploadImage(staff);
                    staffService.addStaff(staff);
                    String message = new ErrorMsgLoader().getErrorMessage(MESSAGE_RECORD_ADDED);
                    model.addAttribute(MESSAGE, message);
                    
                } else if (staff.getStaffId() == 0) {
                    isNew = true;
                    uploadImage(staff);
                    staffService.addStaff(staff);
                    String message = new ErrorMsgLoader().getErrorMessage(MESSAGE_RECORD_ADDED);
                    model.addAttribute(MESSAGE, message);
                } else {
                    return updateStaffDetails(staff, model, request);
                }
            } catch (IOException e) {
                LOG.error(ERROR_RETRIEVING_FILE + e.toString());
                throw new AkuraAppException(AkuraConstant.FILE_NOT_FOUND, e);
            } catch (AkuraAppException e) {
                if (e.getCause() instanceof TransientDataAccessResourceException) {
                    handleTransientDataAccessResourceException(staff, bindingResult, model, isNew);
                    resetCountryFlags(selectedCountryCodeRes, selectedCountryCodeMob, selectedCountryCodeEmgRes,
                            selectedCountryCodeEmgMob, model);
                    return VIEW_GET_STAFF;
                }
                if (e.getCause() instanceof DataIntegrityViolationException) {
                    handleDataIntegrityViolation(staff, bindingResult, model, isNew);
                    resetCountryFlags(selectedCountryCodeRes, selectedCountryCodeMob, selectedCountryCodeEmgRes,
                            selectedCountryCodeEmgMob, model);
                    return VIEW_GET_STAFF;
                }
            }
        }
        return showStaffDetailsForm(request, model);
    }
    
    /**
     * Updates the staff Details.
     * 
     * @param staff the staff.
     * @param model the model.
     * @param request the request
     * @return the return path.
     * @throws AkuraAppException AkuraAppException.
     * @throws IOException IOException.
     */
    private String updateStaffDetails(Staff staff, ModelMap model, HttpServletRequest request)
            throws AkuraAppException, IOException {
    
        Staff staffObjDb = staffService.findStaff(staff.getStaffId());
        
        if (staff.getMultipartFile() != null) {
            MultipartFile multipartFile = staff.getMultipartFile();
            if (multipartFile.getSize() > 0) {
                staff.setPhoto(multipartFile.getBytes());
            } else {
                if (staffObjDb.getPhoto() != null && staffObjDb.getPhoto().length > 0) {
                    staff.setPhoto(staffObjDb.getPhoto());
                }
            }
            
        }
        
        staffService.modifyStaff(staff);
        
        // Update end date in section head when update the date of departure.
        if (staff.getDateOfDeparture() != null) {
            updateEndDateForDepartureStaff(staff.getStaffId(), staff.getDateOfDeparture());
        }
        
        // Update if user login exist for this staff.
        UserLogin userLogin = userService.getUserLoginByIdentificationNo(staff.getRegistrationNo());
        if (userLogin != null) {
            userLogin.setUserIdentificationNo(staff.getRegistrationNo());
            userService.updateUser(userLogin);
        }
        String message = new ErrorMsgLoader().getErrorMessage(MESSAGE_RECORD_MODIFIED);
        model.addAttribute(MESSAGE, message);
        model.addAttribute(MODEL_ATT_STAFF, staff);
        return loadStaffDetails(request, model, request.getSession());
    }
    
    /**
     * Update the end date in section head.
     * 
     * @param staffId the id of staff
     * @param dateOfDeparture the date
     * @throws AkuraAppException AkuraAppException.
     */
    private void updateEndDateForDepartureStaff(int staffId, Date dateOfDeparture) throws AkuraAppException {
    
        // Getting section head list by the staff id.
        List<SectionHead> sectionHeadList = staffService.getSectionalHeadsById(staffId, dateOfDeparture);
        
        if (!sectionHeadList.isEmpty()) {
            List<SectionHead> updatedSectionHeadList = new ArrayList<SectionHead>();
            
            // Change departure date to section end date.
            for (SectionHead sectionHead : sectionHeadList) {
                sectionHead.setEndDate(dateOfDeparture);
                updatedSectionHeadList.add(sectionHead);
            }
            // Update section head list with departure date.
            staffService.updateSectionHeadList(updatedSectionHeadList);
        }
        
    }
    
    /**
     * Validtaes the core subjects.
     * 
     * @param staff the staff
     * @param bindingResult the binding results.
     * @return the validate.
     * @throws AkuraAppException AkuraAppException.
     */
    private boolean validateCoreSubject(Staff staff, BindingResult bindingResult) throws AkuraAppException {
    
        boolean isAcademicCategory = false;
        int cID = staff.getStaffCategory().getCatogaryID();
        if (cID != 0) {
            isAcademicCategory = staffCommonService.isAcademicStaffCategory(cID);
        }
        if (isAcademicCategory) {
            if (staff.getCoreSubject().getSubjectId() == 0) {
                
                bindingResult.rejectValue(CORE_SUBJECT, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
                
            } else if (staff.getStudyMedium().getStudyMediumId() == 0) {
                
                bindingResult.rejectValue(STUDY_MEDIUM, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
                
            }
        }
        return isAcademicCategory;
    }
    
    /**
     * loads the image from given image file.
     * 
     * @param staff - Staff
     * @throws IOException - detail exception during file processing
     */
    private void uploadImage(Staff staff) throws IOException {
    
        if (staff.getMultipartFile() != null) {
            MultipartFile multipartFile = staff.getMultipartFile();
            if (multipartFile.getSize() > 0) {
                staff.setPhoto(multipartFile.getBytes());
            }
            
        }
    }
    
    /**
     * handles when DataIntegrity violation occur.
     * 
     * @param staff -Staff
     * @param bindingResult -BindingResult
     * @param model -ModelMap
     * @param isNew -boolean
     * @throws AkuraAppException - detail exception during the processing
     */
    private void handleDataIntegrityViolation(Staff staff, BindingResult bindingResult, ModelMap model, boolean isNew)
            throws AkuraAppException {
    
        String imagePath = RESOURCES_NO_PROFILE_IMAGE;
        if (staff != null) {
            Staff staffDB = staffService.findStaff(staff.getStaffId());
            if (staffDB != null) {
                imagePath = getImagePath(staff, imagePath, staffDB);
            }
        }
        bindingResult.rejectValue(STAFF_ID, DUPLICATE_DESCRIPTION_MESSAGE);
        if (isNew) {
            staff.setStaffId(0);
            model.addAttribute(MODEL_ATT_STAFF, staff);
        }
        model.addAttribute(IMAGE_PATH, imagePath);
        
    }
    
    /**
     * handles when TransientDataAccessResourceException violation occur.
     * 
     * @param staff -Staff
     * @param bindingResult -BindingResult
     * @param model -ModelMap
     * @param isNew -boolean
     * @throws AkuraAppException - detail exception during the processing
     */
    private void handleTransientDataAccessResourceException(Staff staff, BindingResult bindingResult, ModelMap model,
            boolean isNew) throws AkuraAppException {
    
        String imagePath = RESOURCES_NO_PROFILE_IMAGE;
        if (staff != null) {
            Staff staffDB = staffService.findStaff(staff.getStaffId());
            if (staffDB != null) {
                imagePath = getImagePath(staff, imagePath, staffDB);
            }
        }
        model.addAttribute(MODEL_ERROR, new ErrorMsgLoader().getErrorMessage(IMAGE_DATABASE_SIZE));
        if (isNew) {
            staff.setStaffId(0);
            model.addAttribute(MODEL_ATT_STAFF, staff);
        }
        model.addAttribute(IMAGE_PATH, imagePath);
        
    }
    
    /**
     * handles when validation error occur.
     * 
     * @param staff -Staff
     * @param model -ModelMap
     * @throws AkuraAppException - detail exception during the processing
     */
    private void handleValidationError(Staff staff, ModelMap model) throws AkuraAppException {
    
        String imagepath = RESOURCES_NO_PROFILE_IMAGE;
        if (staff != null && staff.getStaffId() > 0) {
            Staff staffDB = staffService.findStaff(staff.getStaffId());
            if (staffDB != null) {
                imagepath = getImagePath(staff, imagepath, staffDB);
            }
        }
        
        model.addAttribute(IMAGE_PATH, imagepath);
    }
    
    /**
     * gets the path of the profile image.
     * 
     * @param staff -Staff
     * @param imagepath -String
     * @param staffDB -Staff
     * @return profile image path
     * @throws AkuraAppException - detail exception during the processing
     */
    private String getImagePath(Staff staff, String imagepath, Staff staffDB) throws AkuraAppException {
    
        if (staffDB.getPhoto() != null && staffDB.getPhoto().length > 0) {
            byte[] image = staffDB.getPhoto();
            String imageLoadPath =
                    PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, APPSERVER_HOME)
                            + PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, IMAGE_FOLDER_PATH);
            imageLoadPath = imageLoadPath + staff.getRegistrationNo() + FILE_EXT;
            StaticDataUtil.previewProfile(imageLoadPath, image);
            imagepath = RESOURCES_PROFILE_IMAGES + staff.getRegistrationNo() + FILE_EXT;
        }
        return imagepath;
    }
    
    /**
     * Get list of available religion.
     * 
     * @return list of religion.
     * @throws AkuraAppException - throw detailed exception
     */
    @ModelAttribute(MODEL_ATT_RELIGION_LIST)
    public List<Religion> populateReligionList() throws AkuraAppException {
    
        return SortUtil.sortReligionList(commonService.getReligionList());
    }
    
    /**
     * Get list of available EducationalQualification.
     * 
     * @return list of EducationalQualification.
     * @throws AkuraAppException - throw detailed exception
     */
    @ModelAttribute(MODEL_ATT_EDUCATIONALQUALIFICATION_LIST)
    public List<EducationalQualification> populateEducationalQualificationList() throws AkuraAppException {
    
        return SortUtil.sortEducationalQualificationList(staffCommonService.getEducationalQualifications());
    }
    
    /**
     * Get list of available ProfessionalQualification.
     * 
     * @return list of ProfessionalQualification.
     * @throws AkuraAppException - throw detailed exception
     */
    @ModelAttribute(MODEL_ATT_PROFESSIONALQUALIFICATION_LIST)
    public List<ProfessionalQualification> populateProfessionalQualificationList() throws AkuraAppException {
    
        return SortUtil.sortProfessionalQualificationList(staffCommonService.getProfessionalQualifications());
    }
    
    /**
     * Get list of available StaffCategory.
     * 
     * @return list of StaffCategory.
     * @throws AkuraAppException - throw detailed exception
     */
    @ModelAttribute(MODEL_ATT_STAFF_CATEGORY_LIST)
    public List<StaffCategory> populateStaffCategoryList() throws AkuraAppException {
    
        return SortUtil.sortStaffCategoryList(staffCommonService.getAllStaffCategories());
    }
    
    /**
     * Get list of available City.
     * 
     * @return list of City.
     * @throws AkuraAppException - throw detailed exception
     */
    @ModelAttribute(MODEL_ATT_CITY_LIST)
    public List<City> populateCityList() throws AkuraAppException {
    
        return SortUtil.sortCityList(commonService.getCityList());
    }
    
    /**
     * Method to gets the image width.
     * 
     * @return imagewidth - specified by an integer variable.
     */
    @ModelAttribute(MODEL_IMAGE_WIDTH)
    public int getImageWidth() {
    
        String strfileWidth = PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, PROFILE_IMAGE_WIDTH);
        return Integer.parseInt(strfileWidth);
    }
    
    /**
     * Method to gets the image height.
     * 
     * @return imageheight - specified by an integer variable.
     */
    @ModelAttribute(MODEL_IMAGE_HEIGHT)
    public int getImageHeight() {
    
        String strfileHeight = PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, PROFILE_IMAGE_HEIGHT);
        return Integer.parseInt(strfileHeight);
    }
    
    /**
     * Get list of available subjects.
     * 
     * @return list of cities.
     * @throws AkuraAppException -throw detailed exception.
     */
    @ModelAttribute(CORE_SUBJECT_LIST)
    public List<Subject> populateCoreSubjectList() throws AkuraAppException {
    
        return SortUtil.sortSubjectList(commonService.getSubjectList());
    }
    
    /**
     * Get list of available study mediums.
     * 
     * @return list of study mediums.
     * @throws AkuraAppException -throw detailed exception.
     */
    @ModelAttribute(STUDY_MEDIUM_LIST)
    public List<StudyMedium> populateStudyMediumList() throws AkuraAppException {
    
        return SortUtil.sortStudyMedium(commonService.getStudyMediumList());
        
    }
    
    /**
     * Get list of available section.
     * 
     * @return list of sections.
     * @throws AkuraAppException -throw detailed exception.
     */
    @ModelAttribute(SECTION_LIST)
    public List<Section> populateSectionList() throws AkuraAppException {
    
        return staffCommonService.getSectionsList();
        
    }
    
    /**
     * Get list of available race.
     * 
     * @return list of race.
     * @throws AkuraAppException -throw detailed exception.
     */
    @ModelAttribute(RACE_LIST)
    public List<Race> populateRaceList() throws AkuraAppException {
    
        return SortUtil.sortRaceList(commonService.getRaceList());
        
    }
    
    /**
     * Get list of available CivilStatus.
     * 
     * @return list of CivilStatus.
     * @throws AkuraAppException -throw detailed exception.
     */
    @ModelAttribute(CIVIL_STATUS_LIST)
    public List<CivilStatus> populateCivilStatusList() throws AkuraAppException {
    
        return SortUtil.sortCivilStatusList(staffCommonService.getCivilStatusList());
        
    }
    
    /**
     * Get list of available AppointmentNature.
     * 
     * @return list of AppointmentNature.
     * @throws AkuraAppException -throw detailed exception.
     */
    @ModelAttribute(APPOINTMENT_NATURE_LIST)
    public List<AppointmentNature> populateAppointmentNatureList() throws AkuraAppException {
    
        return staffCommonService.getAppointmentNatureList();
        
    }
    
    /**
     * Get list of available AppointmentClassification.
     * 
     * @return list of AppointmentClassification.
     * @throws AkuraAppException -throw detailed exception.
     */
    @ModelAttribute(APPOINTMENT_CLASSIFICATION_LIST)
    public List<AppointmentClassification> populateAppointmentClassificationList() throws AkuraAppException {
    
        return staffCommonService.getAppointmentClassificationList();
        
    }
    
    /**
     * Get list of available StaffServiceCategory.
     * 
     * @return list of StaffServiceCategory.
     * @throws AkuraAppException -throw detailed exception.
     */
    @ModelAttribute(STAFF_SERVICE_LIST)
    public List<StaffServiceCategory> populateStaffServiceList() throws AkuraAppException {
    
        return staffCommonService.getStaffServiceCategoryList();
        
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
     * Check the email availability.
     * 
     * @param staff to get email.
     * @param errors to bind errors.
     * @return true if email exist.
     * @throws AkuraAppException - throw detailed exception if fails.
     */
    private boolean checkEmailExistOrNot(Staff staff, Errors errors) throws AkuraAppException {
    
        boolean exist = false;
        
        if (staff.getEmail() != null && staff.getEmail().trim().length() != 0
                && staffService.checkEmailAvailability(staff.getEmail())) {
            if (staff.getStaffId() == 0) {
                errors.rejectValue(EMAIL, STA_UI_EMAIL_EXIST_ERROR);
                exist = true;
                
            } else if (!staff.getEmail().trim().equals(staffService.findStaff(staff.getStaffId()).getEmail())) {
                errors.rejectValue(EMAIL, STA_UI_EMAIL_EXIST_ERROR);
                exist = true;
                
            }
        }
        return exist;
    }
    
    /**
     * Checks the date of departure status and keep in the model map.
     * 
     * @param model - the map contains the status of the deleted flag.
     * @param staff - the relevant staff instance.
     */
    
    private void isDeletedFlag(final ModelMap model, final Staff staff) {
    
        Date currentDate = new Date();
        
        Date dateOfDeparture = staff.getDateOfDeparture();
        Date dateOfHire = staff.getDateOfHire();
        
        if (dateOfDeparture != null) {
            
            if (DateUtil.isDateBeforeGivenDate(currentDate, dateOfDeparture)) {
                
                model.addAttribute(READ_ONLY, Boolean.TRUE);
                
            }
        }
        if (dateOfHire != null) {
            if (currentDate.after(dateOfHire)) {
                model.addAttribute(READ_ONLY_JOIN_DATE_MODEL, Boolean.TRUE);
            }
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
                phoneMap.put(AkuraConstant.PLUS_SIGN
                        + PhoneNumberValidateUtil.findCountryCode(country.getCountryCode()), country);
                
            }
        }
        return phoneMap;
    }
    
    /**
     * View the staff phone number validation details.
     * 
     * @param staff - staff related data object
     * @param model - {@link ModelMap}
     * @throws AkuraAppException - Throw detail exception when fails.
     */
    private void displayPhoneNumberDetails(Staff staff, ModelMap model) throws AkuraAppException {
    
        if (staff.getResidenceNo() != null) {
            if (staff.getResidenceNo().contains(AkuraConstant.EMPTY_STRING_SPACE)) {
                String countryCode =
                        staff.getResidenceNo().substring(0, staff.getResidenceNo().indexOf(' ')).replace('+', ' ')
                                .trim();
                String resNumber =
                        staff.getResidenceNo().substring(staff.getResidenceNo().indexOf(' ')).replaceAll("\\s", "");
                staff.setResidenceNo((resNumber.startsWith(AkuraConstant.STRING_ZERO))
                        ? resNumber.substring(1): resNumber);
                model.addAttribute(MODEL_ATT_SELECTED_RES_COUNTRY,
                        PhoneNumberValidateUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode)));
            } else {
                model.addAttribute(MODEL_ATT_SELECTED_RES_COUNTRY, 0);
            }
        }
        
        if (staff.getMobileNo() != null) {
            if (staff.getMobileNo().contains(AkuraConstant.EMPTY_STRING_SPACE)) {
                String countryCode =
                        staff.getMobileNo().substring(0, staff.getMobileNo().indexOf(' ')).replace('+', ' ').trim();
                String mobNumber =
                        staff.getMobileNo().substring(staff.getMobileNo().indexOf(' ')).replaceAll("\\s", "");
                staff.setMobileNo((mobNumber.startsWith(AkuraConstant.STRING_ZERO))
                        ? mobNumber.substring(1): mobNumber);
                model.addAttribute(MODEL_ATT_SELECTED_MOB_COUNTRY,
                        PhoneNumberValidateUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode)));
            } else {
                model.addAttribute(MODEL_ATT_SELECTED_MOB_COUNTRY, 0);
            }
        }
        
        if (staff.getEmergencyContactResidenceNo() != null) {
            if (staff.getEmergencyContactResidenceNo().contains(AkuraConstant.EMPTY_STRING_SPACE)) {
                String countryCode =
                        staff.getEmergencyContactResidenceNo()
                                .substring(0, staff.getEmergencyContactResidenceNo().indexOf(' ')).replace('+', ' ')
                                .trim();
                String emgResNumber =
                        staff.getEmergencyContactResidenceNo()
                                .substring(staff.getEmergencyContactResidenceNo().indexOf(' ')).replaceAll("\\s", "");
                staff.setEmergencyContactResidenceNo((emgResNumber.startsWith(AkuraConstant.STRING_ZERO)) ? 
                        emgResNumber.substring(1):emgResNumber);
                model.addAttribute(MODEL_ATT_SELECTED_EMG_RES_COUNTRY,
                        PhoneNumberValidateUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode)));
            } else {
                model.addAttribute(MODEL_ATT_SELECTED_EMG_RES_COUNTRY, 0);
            }
        }
        
        if (staff.getEmergencyContactMobileNo() != null) {
            if (staff.getEmergencyContactMobileNo().contains(AkuraConstant.EMPTY_STRING_SPACE)) {
                String countryCode =
                        staff.getEmergencyContactMobileNo()
                                .substring(0, staff.getEmergencyContactMobileNo().indexOf(' ')).replace('+', ' ')
                                .trim();
                String emgMobNumber =
                        staff.getEmergencyContactMobileNo().substring(staff.getEmergencyContactMobileNo().indexOf(' '))
                                .replaceAll("\\s", "");
                staff.setEmergencyContactMobileNo((emgMobNumber.startsWith(AkuraConstant.STRING_ZERO)) ? 
                        emgMobNumber.substring(1): emgMobNumber);
                model.addAttribute(MODEL_ATT_SELECTED_EMG_MOB_COUNTRY,
                        PhoneNumberValidateUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode)));
            } else {
                model.addAttribute(MODEL_ATT_SELECTED_EMG_MOB_COUNTRY, 0);
            }
        }
    }
    
    /**
     * Reset the country flag combo boxes.
     * 
     * @param selectedCountryCodeRes - String for selected Residence phone country code
     * @param selectedCountryCodeMob - String for selected Mobile phone country code
     * @param selectedCountryCodeEmgRes - String for selected Emergency Residence phone country code
     * @param selectedCountryCodeEmgMob - String for selected Emergency mobile phone country code
     * @param model - ModelMap
     * @throws AkuraAppException - Throw detail exception when fails.
     */
    private void resetCountryFlags(String selectedCountryCodeRes, String selectedCountryCodeMob,
            String selectedCountryCodeEmgRes, String selectedCountryCodeEmgMob, ModelMap model)
            throws AkuraAppException {
    
        model.addAttribute(MODEL_ATT_SELECTED_RES_COUNTRY,
                (selectedCountryCodeRes.isEmpty()) ? AkuraConstant.STRING_ZERO : selectedCountryCodeRes);
        model.addAttribute(MODEL_ATT_SELECTED_MOB_COUNTRY,
                (selectedCountryCodeMob.isEmpty()) ? AkuraConstant.STRING_ZERO : selectedCountryCodeMob);
        model.addAttribute(MODEL_ATT_SELECTED_EMG_RES_COUNTRY,
                (selectedCountryCodeEmgRes.isEmpty()) ? AkuraConstant.STRING_ZERO : selectedCountryCodeEmgRes);
        model.addAttribute(MODEL_ATT_SELECTED_EMG_MOB_COUNTRY,
                (selectedCountryCodeEmgMob.isEmpty()) ? AkuraConstant.STRING_ZERO : selectedCountryCodeEmgMob);
    }
    
    /**
     * display Residence PhoneNumber Details.
     * 
     * @param staff - staff related data object
     * @param selectedCountryCodeRes - String for selected Residence phone country code
     * @throws AkuraAppException - Throw detail exception when fails.
     */
    private void displayResidencePhoneNumberDetails(Staff staff, String selectedCountryCodeRes)
            throws AkuraAppException {
    
        String resPhoneNumber = AkuraConstant.EMPTY_STRING;
        if (staff.getResidenceNo().startsWith(AkuraConstant.STRING_ZERO)) {
            
            resPhoneNumber =
                    PhoneNumberValidateUtil.formatPhoneNumber(staff.getResidenceNo(),
                            selectedCountryCodeRes, AkuraConstant.PARAM_INDEX_ZERO);
            staff.setResidenceNo(resPhoneNumber);
        } else {
            resPhoneNumber =
                    PhoneNumberValidateUtil.formatPhoneNumber(
                            AkuraConstant.STRING_ZERO + staff.getResidenceNo(), selectedCountryCodeRes
                            , AkuraConstant.PARAM_INDEX_ZERO);
            staff.setResidenceNo(resPhoneNumber);
        }
        
    }
    
    /**
     * display Mobile PhoneNumber Details.
     * 
     * @param staff - staff related data object
     * @param selectedCountryCodeMob - String for selected Mobile phone country code
     * @throws AkuraAppException - Throw detail exception when fails.
     */
    private void displayMobilePhoneNumberDetails(Staff staff, String selectedCountryCodeMob)
            throws AkuraAppException {
    
        String mobPhoneNumber = AkuraConstant.EMPTY_STRING;
        if (staff.getMobileNo().startsWith(AkuraConstant.STRING_ZERO)) {
            mobPhoneNumber =
                    PhoneNumberValidateUtil.formatPhoneNumber(staff.getMobileNo(),
                            selectedCountryCodeMob, AkuraConstant.PARAM_INDEX_ZERO);
            staff.setMobileNo(mobPhoneNumber);
        } else {
            mobPhoneNumber =
                    PhoneNumberValidateUtil.formatPhoneNumber(
                            AkuraConstant.STRING_ZERO + staff.getMobileNo(), selectedCountryCodeMob
                            , AkuraConstant.PARAM_INDEX_ZERO);
            staff.setMobileNo(mobPhoneNumber);
        }
        
    }
    
    /**
     * display EmgResidence PhoneNumber Details.
     * 
     * @param staff - staff related data object
     * @param selectedCountryCodeEmgRes - String for selected Emergency Residence phone country code
     * @throws AkuraAppException - Throw detail exception when fails.
     */
    private void displayEmgResidencePhoneNumberDetails(Staff staff, String selectedCountryCodeEmgRes)
            throws AkuraAppException {
    
        String emgResPhoneNumber = AkuraConstant.EMPTY_STRING;
        if (staff.getEmergencyContactResidenceNo().startsWith(AkuraConstant.STRING_ZERO)) {
            emgResPhoneNumber =
                    PhoneNumberValidateUtil.formatPhoneNumber(staff.getEmergencyContactResidenceNo(),
                            selectedCountryCodeEmgRes, AkuraConstant.PARAM_INDEX_ZERO);
            staff.setEmergencyContactResidenceNo(emgResPhoneNumber);
        } else {
            emgResPhoneNumber =
                    PhoneNumberValidateUtil.formatPhoneNumber(
                        AkuraConstant.STRING_ZERO + staff.getEmergencyContactResidenceNo(), selectedCountryCodeEmgRes
                        , AkuraConstant.PARAM_INDEX_ZERO);
            staff.setEmergencyContactResidenceNo(emgResPhoneNumber);
        }
        
    }
    
    /**
     * display EmgMobile PhoneNumber Details.
     * 
     * @param staff - staff related data object
     * @param selectedCountryCodeEmgMob - String for selected Emergency Residence phone country code
     * @throws AkuraAppException - Throw detail exception when fails.
     */
    private void displayEmgMobilePhoneNumberDetails(Staff staff, String selectedCountryCodeEmgMob)
            throws AkuraAppException {
    
        String emgMobPhoneNumber = AkuraConstant.EMPTY_STRING;
        if (staff.getEmergencyContactMobileNo().startsWith(AkuraConstant.STRING_ZERO)) {
            emgMobPhoneNumber =
                    PhoneNumberValidateUtil.formatPhoneNumber(staff.getEmergencyContactMobileNo(),
                            selectedCountryCodeEmgMob, AkuraConstant.PARAM_INDEX_ZERO);
            staff.setEmergencyContactMobileNo(emgMobPhoneNumber);
        } else {
            emgMobPhoneNumber =
                    PhoneNumberValidateUtil.formatPhoneNumber(
                            AkuraConstant.STRING_ZERO + staff.getEmergencyContactMobileNo(), selectedCountryCodeEmgMob
                            , AkuraConstant.PARAM_INDEX_ZERO);
            staff.setEmergencyContactMobileNo(emgMobPhoneNumber);
        }
        
    }
    
    /**
     * display Country Flags when error occurs.
     * 
     * @param staff - staff related data object
     * @param model for display the model error
     * @param selectedCountryCodeRes - String for selected Residence phone country code
     * @param selectedCountryCodeMob - String for selected Mobile phone country code
     * @param selectedCountryCodeEmgRes - String for selected Emergency Residence phone country code
     * @param selectedCountryCodeEmgMob - String for selected Emergency mobile phone country code
     * @throws AkuraAppException - Throw detail exception when fails.
     */
    private void displayCountryFlagsWhenError(Staff staff, ModelMap model, String selectedCountryCodeRes,
            String selectedCountryCodeMob, String selectedCountryCodeEmgRes, String selectedCountryCodeEmgMob)
            throws AkuraAppException {
    
        String message = new ErrorMsgLoader().getErrorMessage(MESSAGE_PHONE_NO_COUNTRY_MISMATCH);
        model.addAttribute(MODEL_ERROR, message);
        displayPhoneNumberDetails(staff, model);
        handleValidationError(staff, model);
        resetCountryFlags(selectedCountryCodeRes, selectedCountryCodeMob, selectedCountryCodeEmgRes,
                selectedCountryCodeEmgMob, model);
        
    }
}
