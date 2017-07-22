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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.virtusa.akura.api.dto.AppointmentClassification;
import com.virtusa.akura.api.dto.AppointmentNature;
import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.CivilStatus;
import com.virtusa.akura.api.dto.Country;
import com.virtusa.akura.api.dto.Race;
import com.virtusa.akura.api.dto.Religion;
import com.virtusa.akura.api.dto.Section;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.StaffCategory;
import com.virtusa.akura.api.dto.StaffEducation;
import com.virtusa.akura.api.dto.StaffExternalActivity;
import com.virtusa.akura.api.dto.StaffProfessional;
import com.virtusa.akura.api.dto.StaffProfileReportTemplate;
import com.virtusa.akura.api.dto.StaffServiceCategory;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.service.StaffCommonService;
import com.virtusa.akura.reporting.validator.StaffProfileReportValidator;
import com.virtusa.akura.staff.service.StaffService;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;
import com.virtusa.akura.util.StaticDataUtil;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * The Staff Profile Controller.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class StaffProfileReportController {
    
    /** error message for mandatory field required. */
    private static final String ERROR_MSG_MANDATORY_FIELD_REQUIRED = "REF.UI.MANDATORY.FIELD.REQUIRED";
    
    /** attribute for holding message. */
    public static final String MESSAGE = "message";
    
    /** The Constant SELECTED_ADDMISSION. */
    private static final String SELECTED_ADDMISSION = "selectedAddmission";
    
    /** The Constant SELECTED_STATUS. */
    private static final String SELECTED_STATUS = "selectedStatus";
    
    /** The Constant POPULATE_REGISTRATION_NO_BY_STAFF_CATEGORY_HTM. */
    private static final String POPULATE_REGISTRATION_NO_BY_STAFF_CATEGORY_HTM =
            "/populateRegistrationNosByStaffCategoryType.htm";
    
    /** The Constant STAFF_CATEGORY_ID. */
    private static final String STAFF_CATEGORY_ID = "catogaryID";
    
    /** The constant for String "staffCategoryList". */
    private static final String STAFF_CATEGORY_LIST = "staffCategoryList";
    
    /** The Constant STAFF_PROFILE_REPORT_HTM. */
    private static final String STAFF_PROFILE_REPORT_HTM = "/staffProfileReport.htm";
    
    /** The Constant SHOW_STAFF_PROFILE_REPORT_HTM. */
    private static final String SHOW_STAFF_PROFILE_REPORT_HTM = "/showStaffProfileReport.htm";
    
    /** The Constant DASH. */
    private static final String DASH = " - ";
    
    /** The Constant STAFF_REGISTRATON_NOT_EXISTS_MSG. */
    private static final String STAFF_REGISTRATON_NOT_EXISTS_MSG = "STAFF.PROFILE.REPORT.REGNO.NOT.EXISTS";
    
    /** The Constant STAFF_REG_NO. */
    private static final String STAFF_REG_NO = "staffRegNo";
    
    /** The Constant PHOTO_FIELD. */
    private static final String PHOTO_FIELD = "photo";
    
    /** The Constant SEMINAR_LIST_FIELD. */
    private static final String SEMINAR_LIST_FIELD = "seminarList";
    
    /** The Constant SEMINAR_FIELD. */
    private static final String SEMINAR_FIELD = "seminar";
    
    /** The Constant EXTERNAL_ACTIVITY_LIST_FIELD. */
    private static final String EXTERNAL_ACTIVITY_LIST_FIELD = "externalActivityList";
    
    /** The Constant EXTERNAL_ACTIVITY_FIELD. */
    private static final String EXTERNAL_ACTIVITY_FIELD = "externalActivity";
    
    /** The Constant CLUB_SOCIETY_LIST_FIELD. */
    private static final String CLUB_SOCIETY_LIST_FIELD = "clubSocietyList";
    
    /** The Constant CLUB_SOCIETY_FIELD. */
    private static final String CLUB_SOCIETY_FIELD = "clubSociety";
    
    /** The Constant SPORTS_LIST_FIELD. */
    private static final String SPORTS_LIST_FIELD = "sportsList";
    
    /** The Constant SPORTS_FIELD. */
    private static final String SPORTS_FIELD = "sports";
    
    /** The Constant EXTRA_CURRICULAR_FIELD. */
    private static final String EXTRA_CURRICULAR_FIELD = "extraCurricular";
    
    /** The Constant SALARY_FIELD. */
    private static final String SALARY_FIELD = "salary";
    
    /** The Constant NO_OF_DAYS_FIELD. */
    private static final String NO_OF_DAYS_FIELD = "noOfDays";
    
    /** The Constant LEAVE_FIELD. */
    private static final String LEAVE_FIELD = "leave";
    
    /** The Constant GRADE_FIELD. */
    private static final String GRADE_FIELD = "grade";
    
    /** The Constant CLASS_FIELD. */
    private static final String CLASS_FIELD = "class";
    
    /** The Constant SERVICE_FIELD. */
    private static final String SERVICE_FIELD = "service";
    
    /** The Constant PROFESSIONAL_LIST_FIELD. */
    private static final String PROFESSIONAL_LIST_FIELD = "professionalList";
    
    /** The Constant PROFESSIONAL_FIELD. */
    private static final String PROFESSIONAL_FIELD = "professional";
    
    /** The Constant EDUCATIONAL_LIST_FIELD. */
    private static final String EDUCATIONAL_LIST_FIELD = "educationalList";
    
    /** The Constant EDUCATIONAL_FIELD. */
    private static final String EDUCATIONAL_FIELD = "educational";
    
    /** The Constant STAFF_CATEGORY_FIELD. */
    private static final String STAFF_CATEGORY_FIELD = "staffCategory";
    
    /** The Constant MEDIUM_FIELD. */
    private static final String MEDIUM_FIELD = "medium";
    
    /** The Constant SUBJECTS_LIST_FIELD. */
    private static final String SUBJECTS_LIST_FIELD = "subjectsList";
    
    /** The Constant SUBJECTS_FIELD. */
    private static final String SUBJECTS_FIELD = "subjects";
    
    /** The Constant APPOINTMENT_CLASSIFICATION_FIELD. */
    private static final String APPOINTMENT_CLASSIFICATION_FIELD = "appointmentClassification";
    
    /** The Constant APPOINTMENT_NATURE_FIELD. */
    private static final String APPOINTMENT_NATURE_FIELD = "appointmentNature";
    
    /** The Constant HIRE_DATE_FIELD. */
    private static final String HIRE_DATE_FIELD = "hireDate";
    
    /** The Constant APPOINTMENT_DATE_FIELD. */
    private static final String APPOINTMENT_DATE_FIELD = "appointmentDate";
    
    /** The Constant APPOINTMENT_DATE_FIELD. */
    private static final String APPOINTMENT_DATE_FIELD_FLAG = "appointmentDateFlag";
    
    /** The Constant GENDER_FIELD. */
    private static final String GENDER_FIELD = "gender";
    
    /** The Constant DATE_OF_BIRTH_FIELD. */
    private static final String DATE_OF_BIRTH_FIELD = "dateOfBirth";
    
    /** The Constant DATE_OF_BIRTH_FIELD. */
    private static final String DATE_OF_BIRTH_FIELD_FLAG = "dobFlag";
    
    /** The Constant CIVIL_STATUS_FIELD. */
    private static final String CIVIL_STATUS_FIELD = "civilStatus";
    
    /** The Constant RELIGION_FIELD. */
    private static final String RELIGION_FIELD = "religion";
    
    /** The Constant RACE_FIELD. */
    private static final String RACE_FIELD = "race";
    
    /** The Constant EMAIL_FIELD. */
    private static final String EMAIL_FIELD = "email";
    
    /** The Constant MOBILE_FIELD. */
    private static final String MOBILE_FIELD = "mobile";
    
    /** The Constant HOME_FIELD. */
    private static final String HOME_FIELD = "home";
    
    /** The Constant CONTACT_DEAILS_FIELD. */
    private static final String CONTACT_DEAILS_FIELD = "contactDeails";
    
    /** The Constant ADDRESS_FIELD. */
    private static final String ADDRESS_FIELD = "address";
    
    /** The Constant SECTION_FIELD. */
    private static final String SECTION_FIELD = "section";
    
    /** The Constant NAME_INITIALS_FIELD. */
    private static final String NAME_INITIALS_FIELD = "nameInitials";
    
    /** The Constant FULL_NAME_FIELD. */
    private static final String FULL_NAME_FIELD = "fullName";
    
    /** The Constant NIC_NO_FIELD. */
    private static final String NIC_NO_FIELD = "nicNo";
    
    /** The Constant STAFF_PROFILE_REPORT_URL. */
    private static final String STAFF_PROFILE_REPORT_URL = "staffProfileReport";
    
    /** The Constant REPORTING_STAFF_PROFILE_REPORT_VIEW. */
    private static final String REPORTING_STAFF_PROFILE_REPORT_VIEW = "reporting/staffProfileReport";
    
    /** The Constant STAFF_PROFILE_REPORT_TEMPLATE. */
    private static final String STAFF_PROFILE_REPORT_TEMPLATE = "staffProfileReportTemplate";
    
    /** The Constant INVOLVEMENT_OF_EXTRACURRICULAR. */
    private static final String INVOLVEMENT_OF_EXTRACURRICULAR = "StaffProfileReport_InvolvementExternalActivity";
    
    /** The Constant SEMINARS_DONE. */
    private static final String SEMINARS_DONE = "StaffProfileReport_Seminar";
    
    /** The Constant EXTERNAL_ACTIVITY. */
    private static final String EXTERNAL_ACTIVITY = "StaffProfileReport_ExternalActivity";
    
    /** The Constant CLUBS_SOCIETY. */
    private static final String CLUBS_SOCIETY = "StaffProfileReport_ClubsSociety";
    
    /** The Constant SPORTS. */
    private static final String SPORTS = "StaffProfileReport_Sports";
    
    /** The Constant BASIC_SALARY. */
    private static final String BASIC_SALARY = "StaffProfileReport_BasicSalary";
    
    /** The Constant LEAVE_COUNT_FOR_PREVIOUS_YEAR. */
    private static final String LEAVE_COUNT_FOR_PREVIOUS_YEAR = "StaffProfileReport_LeaveCount";
    
    /** The Constant GRADE. */
    private static final String GRADE = "StaffProfileReport_Grade";
    
    /** The Constant CLASS. */
    private static final String CLASS = "StaffProfileReport_Class";
    
    /** The Constant SERVICE. */
    private static final String SERVICE = "StaffProfileReport_Service";
    
    /** The Constant PROFESSIONAL_QUALIFICATIONS. */
    private static final String PROFESSIONAL_QUALIFICATIONS = "StaffProfileReport_ProfessionalQualifications";
    
    /** The Constant EDUCATIONAL_QUALIFICATIONS. */
    private static final String EDUCATIONAL_QUALIFICATIONS = "StaffProfileReport_EducationalQualifications";
    
    /** The Constant DESIGNATION_BARED. */
    private static final String DESIGNATION_BARED = "StaffProfileReport_DesignationBared";
    
    /** The Constant MEDIUM_OF_APPOINTMENT. */
    private static final String MEDIUM_OF_APPOINTMENT = "StaffProfileReport_MediumOfAppointment";
    
    /** The Constant SUBJECTS. */
    private static final String SUBJECTS = "StaffProfileReport_Subjects";
    
    /** The Constant CLASSIFICATION_OF_APPOINTMENT. */
    private static final String CLASSIFICATION_OF_APPOINTMENT = "StaffProfileReport_ClassificationOfAppointment";
    
    /** The Constant NATURE_OF_APPOINTMENT. */
    private static final String NATURE_OF_APPOINTMENT = "StaffProfileReport_NatureOfAppointment";
    
    /** The Constant DATE_OF_HIRE. */
    private static final String DATE_OF_HIRE = "StaffProfileReport_Date_Of_Hire";
    
    /** The Constant DATE_OF_FIRST_APPOINTMENT. */
    private static final String DATE_OF_FIRST_APPOINTMENT = "StaffProfileReport_Date_Of_First_Appointment";
    
    /** The Constant GENDER. */
    private static final String GENDER = "StaffProfileReport_Gender";
    
    /** The Constant DATE_OF_BIRTH. */
    private static final String DATE_OF_BIRTH = "StaffProfileReport_Date_Of_Birth";
    
    /** The Constant CIVIL_STATUS. */
    private static final String CIVIL_STATUS = "StaffProfileReport_Civil_Status";
    
    /** The Constant RELIGION. */
    private static final String RELIGION = "StaffProfileReport_Religion";
    
    /** The Constant RACE. */
    private static final String RACE = "StaffProfileReport_Race";
    
    /** The Constant EMAIL. */
    private static final String EMAIL = "StaffProfileReport_Email";
    
    /** The Constant MOBILE. */
    private static final String MOBILE = "StaffProfileReport_Mobile";
    
    /** The Constant HOME. */
    private static final String HOME = "StaffProfileReport_Home";
    
    /** The Constant CONTACT_DETAILS. */
    private static final String CONTACT_DETAILS = "StaffProfileReport_Contact_Details";
    
    /** The Constant ADDRESS. */
    private static final String ADDRESS = "StaffProfileReport_Address";
    
    /** The Constant SECTION. */
    private static final String SECTION = "StaffProfileReport_Section";
    
    /** The Constant NAME_INITIALS. */
    private static final String NAME_INITIALS = "StaffProfileReport_Name_Initials";
    
    /** The Constant FULL_NAME. */
    private static final String FULL_NAME = "StaffProfileReport_Full_Name";
    
    /** The Constant SSN_NIC_NO. */
    private static final String SSN_NIC_NO = "StaffProfileReport_NIC_No";
    
    /** The Constant STAFF_PROFILE_REPORT. */
    private static final String STAFF_PROFILE_REPORT = "StaffProfileReport_Title";
    
    /** The Staff Service. */
    private StaffService staffService;
    
    /** The Staff Profile Report Validator. */
    private StaffProfileReportValidator staffProfileReportValidator;
    
    /** The Constant DATASOURCE. */
    private static final String DATASOURCE = "datasource";
    
    /** The Constant LOGO_PATH. */
    private static final String LOGO_PATH = "logoPath";
    
    /** The Constant TITLE. */
    private static final String TITLE = "title";
    
    /** attribute for holding style path. */
    private static final String STYLE_PATH = "style.path";
    
    /** attribute for holding app home. */
    private static final String APPSERVER_HOME = "appserver.home";
    
    /** attribute for holding system config. */
    private static final String SYSTEM_CONFIG = "systemConfig";
    
    /** attribute for holding style template. */
    private static final String STYLE_TEMPLATE = "styleTemplate";
    
    /** property file name. */
    private static final String SYSTEM_CONFIG_PROPERTIES = "systemConfig";
    
    /** key to define the image folder path. */
    private static final String IMAGE_FOLDER_PATH = "imageFolder.path";
    
    /** key to define the image file format. */
    private static final String FILE_EXT = ".jpg";
    
    /** The Constant REPORT_GENERATED_ON. */
    private static final String REPORT_GENERATED_ON = "reportGeneratedOn";
    
    /** variable for holding string "generatedDate". */
    private static final String GENERATED_DATE = "generatedDate";
    
    /** The Constant PAGE. */
    private static final String PAGE = "page";
    
    /** The Constant GPL. */
    private static final String GPL = "GPLComment";
    
    /** The Constant REPORT_PAGE. */
    private static final String REPORT_PAGE = "Report_page";
    
    /** The Constant REPORT_GENERATED_ON_TEXT. */
    private static final String REPORT_GENERATED_ON_TEXT = "Report_reportGeneratedOn";
    
    /** model attribute for report generated date. */
    private static final String REPORT_GENERATED_DATE_LOCALE = "REPORT.GENERATED.DATE.LOCALE";
    
    /** key to hold string of year end date. */
    private static final String END_DATE = "-12-31";
    
    /** key to hold string of year start date. */
    private static final String START_DATE = "-01-01";
    
    /** The Constant DEPARTURE_DATE_KEY. */
    private static final String DEPARTURE_DATE_KEY = "dateDeparture";
    
    /** The Constant DEPARTURE_DATE_VALUE. */
    private static final String DEPARTURE_DATE_VALUE = "departureDate";
    
    /** attribute for holding DATE_DEPARTURE_FIELD_KEY. */
    private static final String DATE_DEPARTURE_FIELD_KEY = "StaffPresentAndAbsentDays_Report_DateTodeparture";
    
    /** The Constant model attribute. */
    private static final String MODEL_ATT_SELECTED_STAFF_CATEGORY_ID = "selectedStaffCategoryId";
    
    /** The Constant SELECTED_CLASS. */
    private static final String SELECTED_CATEGORY = "selectedCategory";
    
    /** The String of Model attribute. */
    private static final String MODEL_ATT_SELECTED_ADDMISSION_NO = "selectedAddmissionNo";
    
    /** The String of Model attribute. */
    private static final String MODEL_ATT_SELECTED_STATUS = "selectedStatus";
    
    /** model attribute of LastList object. */
    private static final String MODEL_CONTENTS_LIST = "contentsList";
    
    /** The Constant PAGE. */
    private static final String FLAG = "Flag";
    
    /** The Constant INDEX_SEVEN. */
    private static final int INDEX_TWENTYFIVE = 25;
    
    /** The Constant INDEX_SEVEN. */
    private static final int INDEX_TWENTYFOUR = 24;
    
    /** The Constant INDEX_SEVEN. */
    private static final int INDEX_TWENTYTHREE = 23;
    
    /** The Constant INDEX_SEVEN. */
    private static final int INDEX_TWENTYTWO = 22;
    
    /** The Constant INDEX_SEVEN. */
    private static final int INDEX_TWENTYONE = 21;
    
    /** The Constant INDEX_SEVEN. */
    private static final int INDEX_TWENTY = 20;
    
    /** The Constant INDEX_SEVEN. */
    private static final int INDEX_NINETEEN = 19;
    
    /** The Constant INDEX_SEVEN. */
    private static final int INDEX_EIGHTEEN = 18;
    
    /** The Constant INDEX_SEVEN. */
    private static final int INDEX_SEVENTEEN = 17;
    
    /** The Constant INDEX_SEVEN. */
    private static final int INDEX_SIXTEEN = 16;
    
    /** The Constant INDEX_SEVEN. */
    private static final int INDEX_FIFTEEN = 15;
    
    /** The Constant INDEX_SEVEN. */
    private static final int INDEX_FOURTEEN = 14;
    
    /** The Constant INDEX_SEVEN. */
    private static final int INDEX_THIRTEEN = 13;
    
    /** The Constant INDEX_SEVEN. */
    private static final int INDEX_TWELVE = 12;
    
    /** The Constant INDEX_SEVEN. */
    private static final int INDEX_ELEVEN = 11;
    
    /** The Constant INDEX_SEVEN. */
    private static final int INDEX_TEN = 10;
    
    /** The Constant INDEX_SEVEN. */
    private static final int INDEX_NINE = 9;
    
    /** The Constant INDEX_SEVEN. */
    private static final int INDEX_EIGHT = 8;
    
    /** The Constant INDEX_SEVEN. */
    private static final int INDEX_SEVEN = 7;
    
    /** The Constant INDEX_SIX. */
    private static final int INDEX_SIX = 6;
    
    /** The Constant INDEX_FIVE. */
    private static final int INDEX_FIVE = 5;
    
    /** The Constant INDEX_FOUR. */
    private static final int INDEX_FOUR = 4;
    
    /** The Constant INDEX_THREE. */
    private static final int INDEX_THREE = 3;
    
    /** request attribute of `ToList` value. */
    private static final String REQUEST_TO_LIST = "ToList";
    
    /** Represents the array that selected to the 'FromList'. */
    private static final String REMOVED_FROM_ARRAY = "removedFromArray";
    
    /** Represents the array that selected to the 'ToList'. */
    private static final String SELECTED_ARRAY = "selectedArray";
    
    /** The Constant DATASOURCE_KEY. */
    private static final String MAINREPORT_DATASOURCE = "datasource";
    
    /** The Sting variable for report constant. */
    private static final String REQ_FLAG = "flag";
    
    /** The Sting variable for report constant. */
    private static final Float FLOAT_ZERO = -0.0F;
    
    /** key to hold date format. */
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    
    /** Represents the array that selected toList. */
    private static final String SELECTED_TO_LIST = "selectedToList";
    
    /**
     * staffCommonService To invoke service methods.
     */
    private StaffCommonService staffCommonService;
    
    /**
     * CommonService To invoke service methods.
     */
    private CommonService commonService;
    
    /**
     * Set StaffCommonService.
     * 
     * @param staffCommonServiceRef of type StaffCommonService
     */
    public void setStaffCommonService(StaffCommonService staffCommonServiceRef) {
    
        this.staffCommonService = staffCommonServiceRef;
    }
    
    /**
     * Set Staff Service.
     * 
     * @param staffServiceRef staffService
     */
    public void setStaffService(StaffService staffServiceRef) {
    
        this.staffService = staffServiceRef;
    }
    
    /**
     * Set StaffProfileReportValidator.
     * 
     * @param staffProfileReportValidatorRef staffService
     */
    public void setStaffProfileReportValidator(StaffProfileReportValidator staffProfileReportValidatorRef) {
    
        this.staffProfileReportValidator = staffProfileReportValidatorRef;
    }
    
    /**
     * Set Common Service Service.
     * 
     * @param commonServic CommonService
     */
    public void setCommonService(CommonService commonServic) {
    
        this.commonService = commonServic;
    }
    
    /**
     * intiBinder method is to bind date class with the date format.
     * 
     * @param binder - data binder used to register the Date objects.
     */
    @InitBinder
    public void initBinder(final WebDataBinder binder) {
    
        binder.getBindingResult();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        
    }
    
    /**
     * Display Form View for of the controller and binding it to staffProfileReportTemplate.
     * 
     * @param modelMap of type ModelMap
     * @param staffCategory of the staff
     * @throws AkuraAppException AkuraAppException
     * @return java.lang.String
     */
    
    @RequestMapping(value = SHOW_STAFF_PROFILE_REPORT_HTM, method = RequestMethod.GET)
    public String showReportForm(ModelMap modelMap, StaffCategory staffCategory) throws AkuraAppException{
    
        modelMap.addAttribute(STAFF_PROFILE_REPORT_TEMPLATE, new StaffProfileReportTemplate());
        
        Map<Integer, String> contentsList = populateReportData(staffCategory);
        modelMap.addAttribute(MODEL_CONTENTS_LIST, contentsList);
        return REPORTING_STAFF_PROFILE_REPORT_VIEW;
    }
    
    /**
     * Perform the logic of the controller to Generate Exam Results Report.
     * 
     * @param response of type HttpServletResponse
     * @param staffProfileReportTemplate - model object
     * @param errors - BindingResult
     * @param request - HttpServletRequest
     * @return modelAndView
     * @param modelMap of type modelMap.
     * @throws AkuraAppException AkuraAppException
     * @throws ParseException ParseException
     */
    
    @RequestMapping(value = STAFF_PROFILE_REPORT_HTM)
    public ModelAndView onSubmit(HttpServletResponse response,
            @ModelAttribute(STAFF_PROFILE_REPORT_TEMPLATE) StaffProfileReportTemplate staffProfileReportTemplate,
            BindingResult errors, HttpServletRequest request, ModelMap modelMap) throws AkuraAppException,
            ParseException {
    
        String redirectURL = STAFF_PROFILE_REPORT_URL;
        Map<String, Object> map = new HashMap<String, Object>();
        String[] toListStr1 =
                new String[] { "2", String.valueOf(INDEX_THREE), String.valueOf(INDEX_FOUR),
                        String.valueOf(INDEX_FIVE), String.valueOf(INDEX_SIX), String.valueOf(INDEX_SEVEN),
                        String.valueOf(INDEX_EIGHT), String.valueOf(INDEX_NINE), String.valueOf(INDEX_TEN),
                        String.valueOf(INDEX_ELEVEN), String.valueOf(INDEX_TWELVE), String.valueOf(INDEX_THIRTEEN),
                        String.valueOf(INDEX_FOURTEEN), String.valueOf(INDEX_FIFTEEN), String.valueOf(INDEX_SIXTEEN),
                        String.valueOf(INDEX_SEVENTEEN), String.valueOf(INDEX_EIGHTEEN),
                        String.valueOf(INDEX_NINETEEN), String.valueOf(INDEX_TWENTY), String.valueOf(INDEX_TWENTYONE),
                        String.valueOf(INDEX_TWENTYTWO), String.valueOf(INDEX_TWENTYTHREE),
                        String.valueOf(INDEX_TWENTYFOUR), String.valueOf(INDEX_TWENTYFIVE) };
        
        String selectedAddmission = request.getParameter(SELECTED_ADDMISSION);
        String selectedStatus = request.getParameter(SELECTED_STATUS);
        String selectedCategory = request.getParameter(SELECTED_CATEGORY);
        String selectedList = null;
        String fromList = null;
        StaffCategory staffCategory = staffCommonService.getStaffCategory(Integer.parseInt(selectedCategory));
        
        String flag = request.getParameter(REQ_FLAG);
        boolean booleanFlag = Boolean.parseBoolean(flag);
        if (!booleanFlag) {
            toListStr1 = request.getParameterValues(REQUEST_TO_LIST);
            selectedList = (String) request.getParameter(SELECTED_ARRAY);
            fromList = (String) request.getParameter(REMOVED_FROM_ARRAY);
        }
        map.put(DEPARTURE_DATE_KEY, AkuraWebConstant.EMPTY_STRING);     
        if (selectedCategory != null) {
            if (selectedCategory.equals(AkuraConstant.STRING_ZERO)) {
                String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_MANDATORY_FIELD_REQUIRED);
                modelMap.addAttribute(MESSAGE, message);
                modelMap.addAttribute(MODEL_ATT_SELECTED_STATUS, selectedStatus);
                redirectURL = REPORTING_STAFF_PROFILE_REPORT_VIEW;
                return new ModelAndView(redirectURL, map);
            }
        }
        if (selectedStatus != null) {
            if (selectedStatus.equals(AkuraConstant.STRING_ZERO)) {
                
                String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_MANDATORY_FIELD_REQUIRED);
                modelMap.addAttribute(MESSAGE, message); 
                if (staffCategory != null) {
                    modelMap.addAttribute(MODEL_ATT_SELECTED_STAFF_CATEGORY_ID, staffCategory.getCatogaryID());
                } else {
                    modelMap.addAttribute(MODEL_ATT_SELECTED_STAFF_CATEGORY_ID, 0);
                }
                modelMap.addAttribute(MODEL_ATT_SELECTED_ADDMISSION_NO, selectedAddmission);
                modelMap.addAttribute(MODEL_ATT_SELECTED_STATUS, selectedStatus);
                modelMap.addAttribute(SELECTED_TO_LIST, selectedList);
                modelMap.addAttribute(REMOVED_FROM_ARRAY, fromList);
                if (toListStr1 == null) {
                    redirectURL = showReportForm(modelMap,staffCategory);
                } else {
                    redirectURL = REPORTING_STAFF_PROFILE_REPORT_VIEW;
                }
                return new ModelAndView(redirectURL, map);
            }
        }
        if (selectedAddmission != null) {
            staffProfileReportTemplate.setStaffRegNo(selectedAddmission);
        } else {
            if (staffCategory != null) {
                modelMap.addAttribute(MODEL_ATT_SELECTED_STAFF_CATEGORY_ID, staffCategory.getCatogaryID());
            }
            modelMap.addAttribute(MODEL_ATT_SELECTED_STATUS, selectedStatus);
            modelMap.addAttribute(SELECTED_TO_LIST, selectedList);
            modelMap.addAttribute(REMOVED_FROM_ARRAY, fromList);
            if (toListStr1 == null) {
                redirectURL = showReportForm(modelMap,staffCategory);
            } else {
                redirectURL = REPORTING_STAFF_PROFILE_REPORT_VIEW;
            }
            return new ModelAndView(redirectURL, map);
        }
        staffProfileReportValidator.validate(staffProfileReportTemplate, errors);
        if (errors.hasErrors()) {
            if (staffCategory != null) {
                modelMap.addAttribute(MODEL_ATT_SELECTED_STAFF_CATEGORY_ID, staffCategory.getCatogaryID());
            }
            modelMap.addAttribute(MODEL_ATT_SELECTED_ADDMISSION_NO, selectedAddmission);
            modelMap.addAttribute(MODEL_ATT_SELECTED_STATUS, selectedStatus);
            modelMap.addAttribute(SELECTED_TO_LIST, selectedList);
            modelMap.addAttribute(REMOVED_FROM_ARRAY, fromList);
            if (toListStr1 == null) {
                redirectURL = showReportForm(modelMap,staffCategory);
            } else {
                redirectURL = REPORTING_STAFF_PROFILE_REPORT_VIEW;
            }
            
            return new ModelAndView(redirectURL, map);
            
        } else {
            String staffRegNo = staffProfileReportTemplate.getStaffRegNo().trim();
            if (selectedAddmission.equals(AkuraConstant.STRING_ZERO)) {
                String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_MANDATORY_FIELD_REQUIRED);
                modelMap.addAttribute(MESSAGE, message);
                
                if (staffCategory != null) {
                    modelMap.addAttribute(MODEL_ATT_SELECTED_STAFF_CATEGORY_ID, staffCategory.getCatogaryID());
                } else {
                    modelMap.addAttribute(MODEL_ATT_SELECTED_STAFF_CATEGORY_ID, 0);
                }
                
                modelMap.addAttribute(MODEL_ATT_SELECTED_STATUS, selectedStatus);
                modelMap.addAttribute(SELECTED_TO_LIST, selectedList);
                modelMap.addAttribute(REMOVED_FROM_ARRAY, fromList);
                if (toListStr1 == null) {
                    redirectURL = showReportForm(modelMap,staffCategory);
                } else {
                    redirectURL = REPORTING_STAFF_PROFILE_REPORT_VIEW;
                }
                return new ModelAndView(redirectURL, map);
            }
            if (staffService.isValidRegistrationNo(staffRegNo)) {
                
                int staffId = staffService.findStaffIdForRegistrationNo(staffRegNo);
                
                Staff staff = staffService.findStaff(staffId);
                
                String year = AkuraWebConstant.EMPTY_STRING;
                if (staff != null && staff.getDateOfDeparture() != null) {
                    year = DateUtil.getStringYear(staff.getDateOfDeparture());
                }
                
                if (year != AkuraWebConstant.EMPTY_STRING) {
                    Date departureDateObj = staff.getDateOfDeparture();
                    map.put(DEPARTURE_DATE_KEY,
                            PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, DATE_DEPARTURE_FIELD_KEY));
                    map.put(DEPARTURE_DATE_VALUE, DateUtil.getFormatDate(departureDateObj));
                    
                } else {
                    map.put(DEPARTURE_DATE_VALUE, AkuraWebConstant.SPACE);
                }
                
                List<StaffProfileReportTemplate> staffProfileList =
                        staffService.getStaffProfileTemplateByStaffId(staffId);
                
                if (staffProfileList != null && !staffProfileList.isEmpty()) {
                    generateReport(map, staff, staffProfileList, toListStr1);
                }
            } else {
                errors.rejectValue(STAFF_REG_NO, STAFF_REGISTRATON_NOT_EXISTS_MSG);
                redirectURL = REPORTING_STAFF_PROFILE_REPORT_VIEW;
            }
        }
        return new ModelAndView(redirectURL, map);
    }
    
    /**
     * Perform the logic of the controller to Generate Staff Profile Report.
     * 
     * @param map Map object with staff profile report data
     * @param staff staff object
     * @param staffProfileList staff profile list
     * @param toListStr1 String array with selected data
     * @throws AkuraAppException - throws when fails.
     * @throws ParseException - throws when fails.
     */
    public void generateReport(Map<String, Object> map, Staff staff, List<StaffProfileReportTemplate> staffProfileList,
            String[] toListStr1) throws AkuraAppException, ParseException {
    
        setDefaultStaffProfileReportProperties(map);
        // Set Staff Profile Report properties.
        if (toListStr1 == null) {
            populateStaffPersonalDetails(map, staffProfileList);
        } else {
            int[] toList = new int[toListStr1.length];
            for (int i = 0; i < toListStr1.length; i++) {
                toList[i] = Integer.parseInt(toListStr1[i]);
            }
            populateStaffPersonalDetails(map, staffProfileList);
            
            if (staff != null) {
                
                for (int i = 0; i < toList.length; i++) {
                    
                    switch (toList[i]) {
                        case 2:
                            populateStaffSection(map, staff, staffProfileList);
                            break;
                        case INDEX_THREE:
                            populateStaffAddress(map, staff, staffProfileList);
                            break;
                        case INDEX_FOUR:
                            populateStaffContactDetails(map, staff, staffProfileList);
                            break;
                        case INDEX_FIVE:
                            populateStaffRace(map, staff, staffProfileList);
                            break;
                        case INDEX_SIX:
                            populateStaffReligion(map, staff, staffProfileList);
                            break;
                        case INDEX_SEVEN:
                            populateStaffCivilStatus(map, staff, staffProfileList);
                            break;
                        case INDEX_EIGHT:
                            populateStaffDateOfBirth(map, staff, staffProfileList);
                            break;
                        case INDEX_NINE:
                            populateStaffGender(map, staff, staffProfileList);
                            break;
                        case INDEX_TEN:
                            populateStaffDateOfAppointment(map, staff, staffProfileList);
                            break;
                        case INDEX_ELEVEN:
                            populateStaffDateOfHire(map, staff, staffProfileList);
                            break;
                        case INDEX_TWELVE:
                            populateStaffNatureOfAppointment(map, staff, staffProfileList);
                            break;
                        case INDEX_THIRTEEN:
                            populateStaffClassificationOfAppointment(map, staff, staffProfileList);
                            break;
                        case INDEX_FOURTEEN:
                            //if(staff.getStaffCategory().isAcademic()){
                                populateStaffSubjectTaughtAtPresent(map, staff, staffProfileList);
                            //}
                            break;
                        case INDEX_FIFTEEN:
                            //if(staff.getStaffCategory().isAcademic()){
                                populateStaffMediumOfAppointment(map, staff, staffProfileList);
                            //}
                            break;
                        case INDEX_SIXTEEN:
                            populateStaffDesignationBared(map, staff, staffProfileList);
                            break;
                        case INDEX_SEVENTEEN:
                            populateStaffEducationalQualifications(map, staff, staffProfileList);
                            break;
                        case INDEX_EIGHTEEN:
                            populateStaffProfessionalQualifications(map, staff, staffProfileList);
                            break;
                        case INDEX_NINETEEN:
                            populateStaffService(map, staff, staffProfileList);
                            break;
                        case INDEX_TWENTY:
                            populateStaffClass(map, staff, staffProfileList);
                            break;
                        case INDEX_TWENTYONE:
                            populateStaffGrade(map, staff, staffProfileList);
                            break;
                        case INDEX_TWENTYTWO:
                            populateStaffLeaveTotalCount(map, staff, staffProfileList);
                            break;
                        case INDEX_TWENTYTHREE:
                            populateStaffBasicSalary(map, staff, staffProfileList);
                            break;
                        case INDEX_TWENTYFOUR:
                            populateStaffExtraCurricularDetails(map, staff, staffProfileList);
                            break;
                        case INDEX_TWENTYFIVE:
                            populateStaffSeminarDetails(map, staff, staffProfileList);
                            break;
                        default:
                            break;
                    }
                }
                
            }
        }
    }
    
    /**
     * Get the staff Leave count for the previous year.
     * 
     * @param staffId - staffId
     * @return staffLeaveCount
     * @throws AkuraAppException AkuraAppException
     * @throws ParseException ParseException
     */
    private int populateStaffLeaveCount(int staffId) throws AkuraAppException, ParseException {
    
        int previousYear = DateUtil.currentYearOnly() - 1;
        String strYr = String.valueOf(previousYear);
        String strStartDate = strYr + START_DATE;
        String strEndDate = strYr + END_DATE;
        return staffService.getStaffLeaveCountByPreviousYear(DateUtil.convertStringToDate(strStartDate),
                DateUtil.convertStringToDate(strEndDate), staffId);
    }
    
    /**
     * Populate Staff Sport List by staff id.
     * 
     * @param staffId staffId
     * @return List of staff sport list.
     * @throws AkuraAppException AkuraAppException
     */
    private List<String> populateStaffSportList(int staffId) throws AkuraAppException {
    
        List<Object[]> staffSportList = staffService.getStaffSportByStaffId(staffId);
        List<String> sportList = new ArrayList<String>();
        int i = 0, j = 1, x = 2;
        for (Object[] tempSport : staffSportList) {
            sportList.add(tempSport[i] + DASH + tempSport[j] + DASH + tempSport[x]);
        }
        return sportList;
    }
    
    /**
     * Populate Staff External Activity List by staff id.
     * 
     * @param staffId - staffId
     * @return List of staff external activity.
     * @throws AkuraAppException AkuraAppException
     */
    private List<String> populateStaffExternalActivityList(int staffId) throws AkuraAppException {
    
        List<StaffExternalActivity> externalActivityList = staffService.getStaffExternalActivityList(staffId, null);
        List<String> exaternalList = new ArrayList<String>();
        for (StaffExternalActivity tempActivity : externalActivityList) {
            if (tempActivity == externalActivityList.get(0)) {
                exaternalList.add(tempActivity.getExternalActivity() + DASH + tempActivity.getAchievement());
            } else {
                exaternalList.add(AkuraConstant.BR_TAG + tempActivity.getExternalActivity() + DASH
                        + tempActivity.getAchievement());
            }
        }
        return exaternalList;
    }
    
    /**
     * Populate Staff Club Society List by the staff id.
     * 
     * @param staffId - staffId
     * @return List of Staff Club Society list
     * @throws AkuraAppException AkuraAppException
     */
    private List<String> populateStaffClubSocietyList(int staffId) throws AkuraAppException {
    
        List<Object[]> staffClubSocietyList = staffService.getStaffClubSocietyByStaffId(staffId);
        List<String> clubSocietyList = new ArrayList<String>();
        int i = 0, j = 1;
        for (Object[] tempObject : staffClubSocietyList) {
            clubSocietyList.add(tempObject[i] + DASH + tempObject[j]);
        }
        return clubSocietyList;
    }
    
    /**
     * Populate Staff Professional Qualification List for the given staff id.
     * 
     * @param staffId - staffId
     * @return List of Professional Qualification
     * @throws AkuraAppException AkuraAppException
     */
    private List<String> populateStaffProfessionalQualificationList(int staffId) throws AkuraAppException {
    
        List<StaffProfessional> staffProList = staffService.getStaffProfessionalListForStaff(staffId);
        List<String> professionalList = new ArrayList<String>();
        for (StaffProfessional tempStaffPro : staffProList) {
            professionalList.add(tempStaffPro.getProfessionalQualification().getDescription());
        }
        return professionalList;
    }
    
    /**
     * Populate Staff Education Qualification List for the given staff id.
     * 
     * @param staffId - staffId
     * @return List of Education Qualification
     * @throws AkuraAppException AkuraAppException
     */
    private List<String> populateStaffEducationalQualificationList(int staffId) throws AkuraAppException {
    
        List<StaffEducation> staffEduList = staffService.getStaffEducationListForStaff(staffId);
        List<String> educationalList = new ArrayList<String>();
        for (StaffEducation tempStaffEdu : staffEduList) {
            educationalList.add(tempStaffEdu.getEducationalQualification().getDescription());
        }
        return educationalList;
    }
    
    /**
     * Populate registration no by staff category.
     * 
     * @param request the request
     * @return the list
     * @throws AkuraAppException - throws when fails.
     */
    @RequestMapping(value = POPULATE_REGISTRATION_NO_BY_STAFF_CATEGORY_HTM)
    @ResponseBody
    public String populateRegistrationNoByCategory(HttpServletRequest request) throws AkuraAppException {
    
        int staffCategoryId = Integer.parseInt(request.getParameter(STAFF_CATEGORY_ID));
        int statusID = Integer.parseInt(request.getParameter("statusID"));
        // get staff list by staff category id ,from selected staff type
        StaffCategory staffCategory = staffCommonService.getStaffCategory(staffCategoryId);
        
        List<Staff> staffLists = null;
        if (statusID == 1) {
            staffLists = staffService.getStaffListByCategory(staffCategoryId);
        }
        if (statusID == 2) {
            staffLists = staffService.getDepatureStaffListByCategory(staffCategoryId);
        }
        if (statusID == 0) {
            staffLists = staffService.getAllStaffListByCategory(staffCategoryId);
        }
        
        SortUtil.sortStaffNoList(staffLists);
        
        List<String> staffList = new ArrayList<String>();
        
        if (!staffLists.isEmpty()) {
            
            for (Staff s : staffLists) {
                staffList.add(s.getRegistrationNo() + AkuraConstant.DASH_SIGN + s.getNameWithIntials());
            }
        }
        
        StringBuilder addmissionBuilder = new StringBuilder();
        
        Collections.sort(staffList);
        
        if (!staffList.isEmpty()) {
            
            for (String admission : staffList) {
                addmissionBuilder.append(admission);
                addmissionBuilder.append(AkuraWebConstant.STRING_COMMA);
            }
        }
        
        addmissionBuilder.append(AkuraConstant.PLUS_SIGN);
        Map<Integer, String> contentsList = populateReportData(staffCategory);
        String tableData = "";
        for (Map.Entry<Integer, String> entry : contentsList .entrySet()){
            tableData = entry.getKey() + AkuraConstant.EQUAL_SIGN + entry.getValue();
            addmissionBuilder.append(tableData);
            addmissionBuilder.append(AkuraWebConstant.STRING_COMMA);
        }
        
        return addmissionBuilder.toString();
    }
    
    /**
     * Load the list of Staff categories to load on report.
     * 
     * @return a list of available staff categories.
     * @throws AkuraAppException - throws when fails.
     */
    @ModelAttribute(STAFF_CATEGORY_LIST)
    public List<StaffCategory> populateListOfStaffCategories() throws AkuraAppException {
    
        return SortUtil.sortStaffCategoryList(staffCommonService.getAllStaffCategories());
        
    }
    
    /**
     * Populate staff personal details.
     * 
     * @param map Map object with staff profile report data
     * @throws AkuraAppException - throws when fails.
     */
    private void setDefaultStaffProfileReportProperties(Map<String, Object> map) throws AkuraAppException {
    
        JRBeanCollectionDataSource jrBeanDataSource = new JRBeanCollectionDataSource(null);
        
        map.put(TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STAFF_PROFILE_REPORT));
        map.put(LOGO_PATH, ReportUtil.getReportLogo());
        map.put(STYLE_TEMPLATE,
                PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                        + PropertyReader.getPropertyValue(SYSTEM_CONFIG, STYLE_PATH));
        map.put(REPORT_GENERATED_ON,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_GENERATED_ON_TEXT));
        map.put(GENERATED_DATE, DateUtil.getReportGeneratedDate(PropertyReader.getPropertyValue(
                ReportUtil.REPORT_TEMPLATE, REPORT_GENERATED_DATE_LOCALE)));
        
        map.put(PAGE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_PAGE));
        map.put(GPL, AkuraWebConstant.REPORT_GPL);
        
        map.put(DATASOURCE, jrBeanDataSource);
        map.put(NIC_NO_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, SSN_NIC_NO));
        map.put(FULL_NAME_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, FULL_NAME));
        map.put(NAME_INITIALS_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, NAME_INITIALS));
        map.put(SECTION_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, SECTION));
        map.put(ADDRESS_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, ADDRESS));
        map.put(CONTACT_DEAILS_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, CONTACT_DETAILS));
        map.put(HOME_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, HOME));
        map.put(MOBILE_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, MOBILE));
        map.put(EMAIL_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, EMAIL));
        map.put(RACE_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, RACE));
        map.put(RELIGION_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, RELIGION));
        map.put(CIVIL_STATUS_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, CIVIL_STATUS));
        map.put(DATE_OF_BIRTH_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, DATE_OF_BIRTH));
        map.put(GENDER_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, GENDER));
        map.put(APPOINTMENT_DATE_FIELD,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, DATE_OF_FIRST_APPOINTMENT));
        map.put(HIRE_DATE_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, DATE_OF_HIRE));
        map.put(APPOINTMENT_NATURE_FIELD,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, NATURE_OF_APPOINTMENT));
        map.put(APPOINTMENT_CLASSIFICATION_FIELD,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, CLASSIFICATION_OF_APPOINTMENT));
        map.put(SUBJECTS_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, SUBJECTS));
        map.put(SUBJECTS_LIST_FIELD, null);
        map.put(MEDIUM_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, MEDIUM_OF_APPOINTMENT));
        map.put(STAFF_CATEGORY_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, DESIGNATION_BARED));
        map.put(EDUCATIONAL_FIELD,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, EDUCATIONAL_QUALIFICATIONS));
        map.put(EDUCATIONAL_LIST_FIELD, null);
        map.put(PROFESSIONAL_FIELD,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, PROFESSIONAL_QUALIFICATIONS));
        map.put(PROFESSIONAL_LIST_FIELD, null);
        map.put(SERVICE_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, SERVICE));
        map.put(CLASS_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, CLASS));
        map.put(GRADE_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, GRADE));
        map.put(NO_OF_DAYS_FIELD, null);
        map.put(LEAVE_FIELD,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, LEAVE_COUNT_FOR_PREVIOUS_YEAR));
        map.put(SALARY_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, BASIC_SALARY));
        map.put(EXTRA_CURRICULAR_FIELD,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, INVOLVEMENT_OF_EXTRACURRICULAR));
        map.put(SPORTS_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, SPORTS));
        map.put(SPORTS_LIST_FIELD, null);
        map.put(CLUB_SOCIETY_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, CLUBS_SOCIETY));
        map.put(CLUB_SOCIETY_LIST_FIELD, null);
        map.put(DATE_OF_BIRTH_FIELD_FLAG, AkuraWebConstant.EMPTY_STRING);
        map.put(EXTERNAL_ACTIVITY_FIELD,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, EXTERNAL_ACTIVITY));
        map.put(EXTERNAL_ACTIVITY_LIST_FIELD, null);
        map.put(SEMINAR_FIELD,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, SEMINARS_DONE));
        map.put(SEMINAR_LIST_FIELD, null);
        map.put(PHOTO_FIELD, null);
        
    }
    
    /**
     * Populate student personal details.
     * 
     * @param map Map object with staff profile report data
     * @param staffProfileList the staff personal details list
     * @throws AkuraAppException - throws when fails.
     */
    private void populateStaffPersonalDetails(Map<String, Object> map,
            List<StaffProfileReportTemplate> staffProfileList) throws AkuraAppException {
    
        for (int i = 0; i < staffProfileList.size(); i++) {
            staffProfileList.get(0).setSection(AkuraWebConstant.EMPTY_STRING);
            staffProfileList.get(0).setMobileNo(AkuraWebConstant.EMPTY_STRING);
            staffProfileList.get(0).setEmail(AkuraWebConstant.EMPTY_STRING);
            staffProfileList.get(0).setResidenceNo(AkuraWebConstant.EMPTY_STRING);
            staffProfileList.get(0).setAddress(AkuraWebConstant.EMPTY_STRING);
            staffProfileList.get(0).setRace(AkuraWebConstant.EMPTY_STRING);
            staffProfileList.get(0).setReligion(AkuraWebConstant.EMPTY_STRING);
            staffProfileList.get(0).setCivilStatus(AkuraWebConstant.EMPTY_STRING);
            staffProfileList.get(0).setDateOfBirth(null);
            staffProfileList.get(0).setGender(AkuraWebConstant.EMPTY_STRING);
            staffProfileList.get(0).setFirstAppointmetDate(null);
            staffProfileList.get(0).setDateOfHire(null);
            staffProfileList.get(0).setAppointmentNature(AkuraWebConstant.EMPTY_STRING);
            staffProfileList.get(0).setAppointmentClassification(AkuraWebConstant.EMPTY_STRING);
            staffProfileList.get(0).setStudyMedium(AkuraWebConstant.EMPTY_STRING);
            staffProfileList.get(0).setStaffCategory(AkuraWebConstant.EMPTY_STRING);
            staffProfileList.get(0).setEducationalQualification(AkuraWebConstant.EMPTY_STRING);
            staffProfileList.get(0).setProfessionalQualification(AkuraWebConstant.EMPTY_STRING);
            staffProfileList.get(0).setStaffServiceCategory(AkuraWebConstant.EMPTY_STRING);
            staffProfileList.get(0).setStaffClass(AkuraWebConstant.EMPTY_STRING);
            staffProfileList.get(0).setStaffGrade(AkuraWebConstant.EMPTY_STRING);
            staffProfileList.get(0).setBasicSalary(FLOAT_ZERO);
            staffProfileList.get(0).setCity(AkuraWebConstant.EMPTY_STRING);
            staffProfileList.get(0).setCountry(AkuraWebConstant.EMPTY_STRING);
        }
        JRDataSource datasource = new JRBeanCollectionDataSource(staffProfileList);
        map.put(MAINREPORT_DATASOURCE, datasource);
        
        map.put(TITLE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, STAFF_PROFILE_REPORT));
        map.put(LOGO_PATH, ReportUtil.getReportLogo());
        map.put(STYLE_TEMPLATE,
                PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                        + PropertyReader.getPropertyValue(SYSTEM_CONFIG, STYLE_PATH));
        map.put(REPORT_GENERATED_ON,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_GENERATED_ON_TEXT));
        map.put(GENERATED_DATE, DateUtil.getReportGeneratedDate(PropertyReader.getPropertyValue(
                ReportUtil.REPORT_TEMPLATE, REPORT_GENERATED_DATE_LOCALE)));
        
        map.put(PAGE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, REPORT_PAGE));
        map.put(GPL, AkuraWebConstant.REPORT_GPL);
        map.put(NIC_NO_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, SSN_NIC_NO));
        map.put(FULL_NAME_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, FULL_NAME));
        map.put(NAME_INITIALS_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, NAME_INITIALS));
        map.put(CONTACT_DEAILS_FIELD, null);
        map.put(NO_OF_DAYS_FIELD, "");
        if (staffProfileList.get(0).getPhoto() != null && staffProfileList.get(0).getPhoto().length > 0) {
            byte[] image = staffProfileList.get(0).getPhoto();
            String imageLoadPath =
                    PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, APPSERVER_HOME)
                            + PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, IMAGE_FOLDER_PATH);
            imageLoadPath = imageLoadPath + staffProfileList.get(0).getStaffRegNo() + FILE_EXT;
            StaticDataUtil.previewProfile(imageLoadPath, image);
            map.put(PHOTO_FIELD, imageLoadPath);
        }
    }
    
    /**
     * Populate staff gender details.
     * 
     * @param map Map object with staff profile report data
     * @param staff Staff Object
     * @param staffProfileList the staff personal details list
     * @throws AkuraAppException - throws when fails.
     */
    private void populateStaffGender(Map<String, Object> map, Staff staff,
            List<StaffProfileReportTemplate> staffProfileList) throws AkuraAppException {
    
        if (staffProfileList != null) {
            staffProfileList.get(0).setGender(String.valueOf(staff.getGender()));
            map.put(GENDER_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, GENDER));
        }
    }
    
    /**
     * Populate staff Section Details.
     * 
     * @param map Map object with staff profile report data
     * @param staff object
     * @param staffProfileList the staff personal details list
     * @throws AkuraAppException - throws when fails.
     */
    private void populateStaffSection(Map<String, Object> map, Staff staff,
            List<StaffProfileReportTemplate> staffProfileList) throws AkuraAppException {
    
        if (staffProfileList != null && staff.getSection() != null) {
            Section section = staffCommonService.findSection(staff.getSection());
            staffProfileList.get(0).setSection(section.getDescription());
        } else {
            if (staffProfileList != null) {
                staffProfileList.get(0).setSection(AkuraConstant.EMPTY_STRING_SPACE);
            }
            
        }
        map.put(SECTION_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, SECTION));
    }
    
    /**
     * Populate staff Religion Details.
     * 
     * @param map Map object with staff profile report data
     * @param staff object
     * @param staffProfileList the staff personal details list
     * @throws AkuraAppException - throws when fails.
     */
    private void populateStaffReligion(Map<String, Object> map, Staff staff,
            List<StaffProfileReportTemplate> staffProfileList) throws AkuraAppException {
    
        if (staffProfileList != null && staff.getReligionId() != null) {
            Religion religion = commonService.findReligionById(staff.getReligionId());
            if (religion.getDescription() != null) {
                staffProfileList.get(0).setReligion(religion.getDescription());
            }
        } else {
            if (staffProfileList != null) {
                staffProfileList.get(0).setReligion(AkuraConstant.EMPTY_STRING_SPACE);
            }
            
        }
        map.put(RELIGION_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, RELIGION));
    }
    
    /**
     * Populate staff Civil Status Details.
     * 
     * @param map Map object with staff profile report data
     * @param staff object
     * @param staffProfileList the staff personal details list
     * @throws AkuraAppException - throws when fails.
     */
    private void populateStaffCivilStatus(Map<String, Object> map, Staff staff,
            List<StaffProfileReportTemplate> staffProfileList) throws AkuraAppException {
    
        if (staffProfileList != null && staff.getCivilStatus() != null) {
            CivilStatus civilStatus = staffCommonService.findCivilStatus(staff.getCivilStatus());
            staffProfileList.get(0).setCivilStatus(civilStatus.getDescription());
        } else {
            if (staffProfileList != null) {
                staffProfileList.get(0).setCivilStatus(AkuraConstant.EMPTY_STRING_SPACE);
            }
            
        }
        map.put(CIVIL_STATUS_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, CIVIL_STATUS));
    }
    
    /**
     * Populate staff Date Of Birth Details.
     * 
     * @param map Map object with staff profile report data
     * @param staff object
     * @param staffProfileList the staff personal details list
     * @throws AkuraAppException - throws when fails.
     * @throws ParseException parse Exception when fails.
     */
    private void populateStaffDateOfBirth(Map<String, Object> map, Staff staff,
            List<StaffProfileReportTemplate> staffProfileList) throws AkuraAppException, ParseException {
    
        if (staffProfileList != null && staff.getDateOfBirth() != null) {
            staffProfileList.get(0).setDateOfBirth(staff.getDateOfBirth());
        } else {
            if (staffProfileList != null) {
                map.put(DATE_OF_BIRTH_FIELD_FLAG, FLAG);
            }
            
        }
        map.put(DATE_OF_BIRTH_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, DATE_OF_BIRTH));
    }
    
    /**
     * Populate staff Address Details.
     * 
     * @param map Map object with staff profile report data
     * @param staff object
     * @param staffProfileList the staff personal details list
     * @throws AkuraAppException - throws when fails.
     */
    private void populateStaffAddress(Map<String, Object> map, Staff staff,
            List<StaffProfileReportTemplate> staffProfileList) throws AkuraAppException {
    
        if (staff.getAddress() != null) {
            staffProfileList.get(0).setAddress(staff.getAddress());
        } else {
            staffProfileList.get(0).setAddress(AkuraConstant.EMPTY_STRING_SPACE);
        }
        
        if (staff.getCityId() != null) {
            City city = commonService.findCity(staff.getCityId());
            staffProfileList.get(0).setCity(city.getDescription());
        } else {
            staffProfileList.get(0).setCity(AkuraConstant.EMPTY_STRING_SPACE);
        }
        
        if (staff.getCountryId() != null) {
            Country country = commonService.findCountry(staff.getCountryId());
            staffProfileList.get(0).setCountry(country.getCountryName());
        } else {
            staffProfileList.get(0).setCountry(AkuraConstant.EMPTY_STRING_SPACE);
        }
        
        map.put(ADDRESS_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, ADDRESS));
    }
    
    /**
     * Populate staff Contact Details.
     * 
     * @param map Map object with staff profile report data
     * @param staff object
     * @param staffProfileList the staff personal details list
     * @throws AkuraAppException - throws when fails.
     */
    private void populateStaffContactDetails(Map<String, Object> map, Staff staff,
            List<StaffProfileReportTemplate> staffProfileList) throws AkuraAppException {
    
        if (staffProfileList != null && staff.getAddress() != null || staff.getMobileNo() != null
                || staff.getEmail() != null) {
            
            staffProfileList.get(0).setResidenceNo(staff.getResidenceNo());
            staffProfileList.get(0).setMobileNo(staff.getMobileNo());
            staffProfileList.get(0).setEmail(staff.getEmail());
            map.put(CONTACT_DEAILS_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, CONTACT_DETAILS));
            map.put(HOME_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, HOME));
            map.put(MOBILE_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, MOBILE));
            map.put(EMAIL_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, EMAIL));
        }
    }
    
    /**
     * Populate staff Race Details.
     * 
     * @param map Map object with staff profile report data
     * @param staff object
     * @param staffProfileList the staff personal details list
     * @throws AkuraAppException - throws when fails.
     */
    private void populateStaffRace(Map<String, Object> map, Staff staff,
            List<StaffProfileReportTemplate> staffProfileList) throws AkuraAppException {
    
        if (staffProfileList != null && staff.getRace() != null) {
            Race race = commonService.findRace(staff.getRace());
            staffProfileList.get(0).setRace(race.getDescription());
        } else {
            if (staffProfileList != null) {
                staffProfileList.get(0).setRace(AkuraConstant.EMPTY_STRING_SPACE);
            }
            
        }
        map.put(RACE_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, RACE));
    }
    
    /**
     * Populate staff Date Of Appointment Details.
     * 
     * @param map Map object with staff profile report data
     * @param staff object
     * @param staffProfileList the staff personal details list
     * @throws AkuraAppException - throws when fails.
     */
    private void populateStaffDateOfAppointment(Map<String, Object> map, Staff staff,
            List<StaffProfileReportTemplate> staffProfileList) throws AkuraAppException {
    
        if (staff.getFirstAppointmetDate() != null) {
            staffProfileList.get(0).setFirstAppointmetDate(staff.getFirstAppointmetDate());
        } else {
            map.put(APPOINTMENT_DATE_FIELD_FLAG, FLAG);
        }
        map.put(APPOINTMENT_DATE_FIELD,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, DATE_OF_FIRST_APPOINTMENT));
    }
    
    /**
     * Populate staff Date Of Hire Details.
     * 
     * @param map Map object with staff profile report data
     * @param staff object
     * @param staffProfileList the staff personal details list
     * @throws AkuraAppException - throws when fails.
     */
    private void populateStaffDateOfHire(Map<String, Object> map, Staff staff,
            List<StaffProfileReportTemplate> staffProfileList) throws AkuraAppException {
    
        if (staffProfileList != null && staff.getDateOfHire() != null) {
            staffProfileList.get(0).setDateOfHire(staff.getDateOfHire());
            map.put(HIRE_DATE_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, DATE_OF_HIRE));
        }
    }
    
    /**
     * Populate staff Nature Of Appointment Details.
     * 
     * @param map Map object with staff profile report data
     * @param staff object
     * @param staffProfileList the staff personal details list
     * @throws AkuraAppException - throws when fails.
     */
    private void populateStaffNatureOfAppointment(Map<String, Object> map, Staff staff,
            List<StaffProfileReportTemplate> staffProfileList) throws AkuraAppException {
    
        if (staffProfileList != null && staff.getAppointmentNature() != null) {
            
            AppointmentNature appointmentNature =
                    staffCommonService.getAppointmentNatureById(staff.getAppointmentNature());
            staffProfileList.get(0).setAppointmentNature(appointmentNature.getDescription());
        } else {
            if (staffProfileList != null) {
                staffProfileList.get(0).setAppointmentNature(AkuraConstant.EMPTY_STRING_SPACE);
            }
            
        }
        map.put(APPOINTMENT_NATURE_FIELD,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, NATURE_OF_APPOINTMENT));
    }
    
    /**
     * Populate staff Classification Of Appointment Details.
     * 
     * @param map Map object with staff profile report data
     * @param staff object
     * @param staffProfileList the staff personal details list
     * @throws AkuraAppException - throws when fails.
     */
    private void populateStaffClassificationOfAppointment(Map<String, Object> map, Staff staff,
            List<StaffProfileReportTemplate> staffProfileList) throws AkuraAppException {
    
        if (staffProfileList != null && staff.getAppointmentClassification() != null) {
            AppointmentClassification appointmentClassification =
                    staffCommonService.findAppointmentClassification(staff.getAppointmentClassification());
            staffProfileList.get(0).setAppointmentClassification(appointmentClassification.getDescription());
        } else {
            if (staffProfileList != null) {
                staffProfileList.get(0).setAppointmentClassification(AkuraConstant.EMPTY_STRING_SPACE);
            }
        }
        map.put(APPOINTMENT_CLASSIFICATION_FIELD,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, CLASSIFICATION_OF_APPOINTMENT));
    }
    
    /**
     * Populate staff Subject Taught At Present Details.
     * 
     * @param map Map object with staff profile report data
     * @param staff object
     * @param staffProfileList the staff personal details list
     * @throws AkuraAppException - throws when fails.
     */
    private void populateStaffSubjectTaughtAtPresent(Map<String, Object> map, Staff staff,
            List<StaffProfileReportTemplate> staffProfileList) throws AkuraAppException {
    
        if (staffProfileList != null) {
            // Populate Subject taught at present for a staff
            List<String> subjectTeacherList =
                    staffService.getCurrentSubjectsByStaffId(staff.getStaffId(), DateUtil.getStringYear(new Date()));
            //if(staff.getStaffCategory().isAcademic()){
                map.put(SUBJECTS_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, SUBJECTS));
                map.put(SUBJECTS_LIST_FIELD, subjectTeacherList);
            //}
        }
    }
    
    /**
     * Populate staff Medium Of Appointment Details.
     * 
     * @param map Map object with staff profile report data
     * @param staff object
     * @param staffProfileList the staff personal details list
     * @throws AkuraAppException - throws when fails.
     */
    private void populateStaffMediumOfAppointment(Map<String, Object> map, Staff staff,
            List<StaffProfileReportTemplate> staffProfileList) throws AkuraAppException {
        //if(staff.getStaffCategory().isAcademic()){
            if (staffProfileList != null && staff.getStudyMedium() != null) {
                staffProfileList.get(0).setStudyMedium(staff.getStudyMedium().getStudyMediumName());
            } else {
                if (staffProfileList != null) {
                    staffProfileList.get(0).setStudyMedium(AkuraConstant.EMPTY_STRING_SPACE);
                }
                
            }
        /*}else{
            staffProfileList.get(0).setStudyMedium(AkuraConstant.EMPTY_STRING);
        }*/
        map.put(MEDIUM_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, MEDIUM_OF_APPOINTMENT));
    }
    
    /**
     * Populate staff Designation Bared Details.
     * 
     * @param map Map object with staff profile report data
     * @param staff object
     * @param staffProfileList the staff personal details list
     * @throws AkuraAppException - throws when fails.
     */
    private void populateStaffDesignationBared(Map<String, Object> map, Staff staff,
            List<StaffProfileReportTemplate> staffProfileList) throws AkuraAppException {
    
        if (staffProfileList != null && staff.getStaffCategory() != null) {
            staffProfileList.get(0).setStaffCategory(staff.getStaffCategory().getDescription());
        } else {
            if (staffProfileList != null) {
                staffProfileList.get(0).setStaffCategory(AkuraConstant.EMPTY_STRING_SPACE);
            }
            
        }
        map.put(STAFF_CATEGORY_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, DESIGNATION_BARED));
    }
    
    /**
     * Populate staff Educational Qualifications Details.
     * 
     * @param map Map object with staff profile report data
     * @param staff object
     * @param staffProfileList the staff personal details list
     * @throws AkuraAppException - throws when fails.
     */
    private void populateStaffEducationalQualifications(Map<String, Object> map, Staff staff,
            List<StaffProfileReportTemplate> staffProfileList) throws AkuraAppException {
    
        if (staffProfileList != null) {
            // Populate Educational Qualification List
            List<String> educationalList = populateStaffEducationalQualificationList(staff.getStaffId());
            map.put(EDUCATIONAL_FIELD,
                    PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, EDUCATIONAL_QUALIFICATIONS));
            map.put(EDUCATIONAL_LIST_FIELD, educationalList);
        }
    }
    
    /**
     * Populate staff Professional Qualification Details.
     * 
     * @param map Map object with staff profile report data
     * @param staff object
     * @param staffProfileList the staff personal details list
     * @throws AkuraAppException - throws when fails.
     */
    private void populateStaffProfessionalQualifications(Map<String, Object> map, Staff staff,
            List<StaffProfileReportTemplate> staffProfileList) throws AkuraAppException {
    
        if (staffProfileList != null) {
            // Populate Professional Qualification List
            List<String> professionalList = populateStaffProfessionalQualificationList(staff.getStaffId());
            map.put(PROFESSIONAL_FIELD,
                    PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, PROFESSIONAL_QUALIFICATIONS));
            map.put(PROFESSIONAL_LIST_FIELD, professionalList);
        }
    }
    
    /**
     * Populate staff Service Details.
     * 
     * @param map Map object with staff profile report data
     * @param staff object
     * @param staffProfileList the staff personal details list
     * @throws AkuraAppException - throws when fails.
     */
    private void populateStaffService(Map<String, Object> map, Staff staff,
            List<StaffProfileReportTemplate> staffProfileList) throws AkuraAppException {
    
        if (staffProfileList != null && staff.getStaffServiceCategory() != null) {
            StaffServiceCategory staffServiceCategory =
                    staffCommonService.findStaffServiceCategoryById(staff.getStaffServiceCategory());
            staffProfileList.get(0).setStaffServiceCategory(staffServiceCategory.getDescription());
        } else {
            if (staffProfileList != null) {
                staffProfileList.get(0).setStaffServiceCategory(AkuraConstant.EMPTY_STRING_SPACE);
            }
            
        }
        map.put(SERVICE_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, SERVICE));
    }
    
    /**
     * Populate staff Class Details.
     * 
     * @param map Map object with staff profile report data
     * @param staff object
     * @param staffProfileList the staff personal details list
     * @throws AkuraAppException - throws when fails.
     */
    private void populateStaffClass(Map<String, Object> map, Staff staff,
            List<StaffProfileReportTemplate> staffProfileList) throws AkuraAppException {
    
        if (staffProfileList != null && staff.getStaffClass() != null) {
            staffProfileList.get(0).setStaffClass(staff.getStaffClass());
        } else {
            if (staffProfileList != null) {
                staffProfileList.get(0).setStaffClass(AkuraConstant.EMPTY_STRING_SPACE);
            }
            
        }
        map.put(CLASS_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, CLASS));
    }
    
    /**
     * Populate staff Grade Field Details.
     * 
     * @param map Map object with staff profile report data
     * @param staff object
     * @param staffProfileList the staff personal details list
     * @throws AkuraAppException - throws when fails.
     */
    private void populateStaffGrade(Map<String, Object> map, Staff staff,
            List<StaffProfileReportTemplate> staffProfileList) throws AkuraAppException {
    
        if (staff.getStaffGrade() != null) {
            staffProfileList.get(0).setStaffGrade(staff.getStaffGrade());
        } else {
            staffProfileList.get(0).setStaffGrade(AkuraConstant.EMPTY_STRING_SPACE);
            
        }
        map.put(GRADE_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, GRADE));
    }
    
    /**
     * Populate staff Leave Count Details.
     * 
     * @param map Map object with staff profile report data
     * @param staff object
     * @param staffProfileList the staff personal details list
     * @throws AkuraAppException - throws when fails.
     * @throws ParseException - throws when fails.
     */
    private void populateStaffLeaveTotalCount(Map<String, Object> map, Staff staff,
            List<StaffProfileReportTemplate> staffProfileList) throws AkuraAppException, ParseException {
    
        if (staffProfileList != null) {
            // Get Staff Leave Count List
            String noOfDays = String.valueOf(populateStaffLeaveCount(staff.getStaffId()));
            map.put(NO_OF_DAYS_FIELD, noOfDays);
        } else {
            map.put(NO_OF_DAYS_FIELD, 0);
        }
    }
    
    /**
     * Populate staff Basic Salary Details.
     * 
     * @param map Map object with staff profile report data
     * @param staff object
     * @param staffProfileList the staff personal details list
     * @throws AkuraAppException - throws when fails.
     */
    private void populateStaffBasicSalary(Map<String, Object> map, Staff staff,
            List<StaffProfileReportTemplate> staffProfileList) throws AkuraAppException {
    
        if (staff.getBasicSalary() > 0) {
            staffProfileList.get(0).setBasicSalary(staff.getBasicSalary());
        } else {
            staffProfileList.get(0).setBasicSalary(0.0F);
            
        }
        map.put(SALARY_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, BASIC_SALARY));
        
    }
    
    /**
     * Populate staff Extra curricular Details.
     * 
     * @param map Map object with staff profile report data
     * @param staff object
     * @param staffProfileList the staff personal details list
     * @throws AkuraAppException - throws when fails.
     */
    private void populateStaffExtraCurricularDetails(Map<String, Object> map, Staff staff,
            List<StaffProfileReportTemplate> staffProfileList) throws AkuraAppException {
    
        if (staffProfileList != null) {
            // Populate Staff Sport List
            List<String> sportList = populateStaffSportList(staff.getStaffId());
            // Populate Club Society List
            List<String> clubSocietyList = populateStaffClubSocietyList(staff.getStaffId());
            // Populate Staff External Activity List
            List<String> exaternalList = populateStaffExternalActivityList(staff.getStaffId());
            map.put(EXTRA_CURRICULAR_FIELD,
                    PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, INVOLVEMENT_OF_EXTRACURRICULAR));
            map.put(SPORTS_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, SPORTS));
            map.put(SPORTS_LIST_FIELD, sportList);
            map.put(CLUB_SOCIETY_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, CLUBS_SOCIETY));
            map.put(CLUB_SOCIETY_LIST_FIELD, clubSocietyList);
            map.put(EXTERNAL_ACTIVITY_FIELD,
                    PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, EXTERNAL_ACTIVITY));
            map.put(EXTERNAL_ACTIVITY_LIST_FIELD, exaternalList);
        }
    }
    
    /**
     * Populate staff Seminar Details.
     * 
     * @param map Map object with staff profile report data
     * @param staff object
     * @param staffProfileList the staff personal details list
     * @throws AkuraAppException - throws when fails.
     */
    @SuppressWarnings("unchecked")
    private void populateStaffSeminarDetails(Map<String, Object> map, Staff staff,
            List<StaffProfileReportTemplate> staffProfileList) throws AkuraAppException {
    
        if (staffProfileList != null) {
            // Populate Staff Seminar List
            List<String> staffSeminarList = (List<String>) staffService.getStaffSeminarList(staff.getStaffId(), null);
            map.put(SEMINAR_FIELD, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, SEMINARS_DONE));
            map.put(SEMINAR_LIST_FIELD, staffSeminarList);
        }
    }
    
    /**
     * Populate staff report data.
     * 
     * @param staffCategory  category of the staff
     * @throws AkuraAppException - throws when fails.
     * @return map of report property
     */
    private Map<Integer, String> populateReportData(StaffCategory staffCategory) throws AkuraAppException {
        Map<Integer, String> contentsList = new TreeMap<Integer, String>();
        
        contentsList.put(2, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, SECTION));
        contentsList.put(INDEX_THREE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, ADDRESS));
        contentsList.put(INDEX_FOUR, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, CONTACT_DETAILS));
        contentsList.put(INDEX_FIVE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, RACE));
        contentsList.put(INDEX_SIX, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, RELIGION));
        contentsList.put(INDEX_SEVEN, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, CIVIL_STATUS));
        contentsList.put(INDEX_EIGHT, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, DATE_OF_BIRTH));
        contentsList.put(INDEX_NINE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, GENDER));
        contentsList.put(INDEX_TEN,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, DATE_OF_FIRST_APPOINTMENT));
        contentsList.put(INDEX_ELEVEN, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, DATE_OF_HIRE));
        contentsList.put(INDEX_TWELVE,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, NATURE_OF_APPOINTMENT));
        contentsList.put(INDEX_THIRTEEN,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, CLASSIFICATION_OF_APPOINTMENT));
        if(staffCategory != null && staffCategory.isAcademic()){
            contentsList.put(INDEX_FOURTEEN, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, SUBJECTS));
            contentsList.put(INDEX_FIFTEEN,
                    PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, MEDIUM_OF_APPOINTMENT));
        }
        contentsList.put(INDEX_SIXTEEN, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, DESIGNATION_BARED));
        contentsList.put(INDEX_SEVENTEEN,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, EDUCATIONAL_QUALIFICATIONS));
        contentsList.put(INDEX_EIGHTEEN,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, PROFESSIONAL_QUALIFICATIONS));
        contentsList.put(INDEX_NINETEEN, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, SERVICE));
        contentsList.put(INDEX_TWENTY, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, CLASS));
        contentsList.put(INDEX_TWENTYONE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, GRADE));
        contentsList.put(INDEX_TWENTYTWO,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, LEAVE_COUNT_FOR_PREVIOUS_YEAR));
        contentsList.put(INDEX_TWENTYTHREE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, BASIC_SALARY));
        contentsList.put(INDEX_TWENTYFOUR,
                PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, INVOLVEMENT_OF_EXTRACURRICULAR));
        contentsList.put(INDEX_TWENTYFIVE, PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, SEMINARS_DONE));
        return contentsList;
    }
}
