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

package com.virtusa.akura.staff.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.EducationalQualification;
import com.virtusa.akura.api.dto.ProfessionalQualification;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.StaffEducation;
import com.virtusa.akura.api.dto.StaffProfessional;
import com.virtusa.akura.api.dto.WrapperQualification;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.service.StaffCommonService;
import com.virtusa.akura.staff.service.StaffService;
import com.virtusa.akura.staff.validator.StaffEducationValidator;
import com.virtusa.akura.staff.validator.StaffProfessionalValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * @author Virtusa Corporation
 */
@Controller
public class StaffQualificationsController {
    
    /** attribute for holding field name for display professional qualification errors. */
    private static final String ERROR_PROFESSIONAL_QUALIFICATION_ID =
            "staffProfessional.professionalQualification.professionalQualificationId";
    
    /** attribute for holding field name for display education qualification errors. */
    private static final String ERROR_EDUCATIONAL_QUALIFICATION_ID =
            "staffEducation.educationalQualification.educationalQualificationId";
    
    /** attribute for holding error key for duplicate description. */
    private static final String STQ_UI_DUPLICATE_DESCRIPTION = "STQ.UI.DUPLICATE.DESCRIPTION";
    
    /** attribute for holding selectedStaffProfesionalQualId. */
    private static final String SELECTED_STAFF_PROFESIONAL_QUAL_ID = "selectedStaffProfesionalQualId";
    
    /** attribute for holding selectedStaffEducationQualId. */
    private static final String SELECTED_STAFF_EDUCATION_QUAL_ID = "selectedStaffEducationQualId";
    
    /** attribute for holding staffId. */
    private static final String STAFF_ID = "staffId";
    
    /** attribute for holding departuredate. */
    private static final String DEPARTURE_DATE = "departureDate";
    
    /** attribute for holding date time format. */
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd";
    
    /** attribute for holding staffQualifications redirect URL. */
    private static final String REDIRECT_STAFF_QUALIFICATIONS = "redirect:staffQualifications.htm";
    
    /** attribute for holding login redirect URL. */
    private static final String REDIRECT_LOGIN = "redirect:login.htm";
    
    /** attribute for holding request value saveStaffEducationQualification. */
    private static final String SAVE_STAFF_EDUCATION_QUALIFICATION = "/saveStaffEducationQualification";
    
    /** attribute for holding request value saveStaffProfessionQualification. */
    private static final String SAVE_STAFF_PROFESSIONAL_QUALIFICATION = "/saveStaffProfessionalQualification";
    
    /** attribute for holding request value deleteStaffProfessional.htm. */
    private static final String DELETE_STAFF_PROFESSIONAL = "/deleteStaffProfessional.htm";
    
    /** attribute for holding request value deleteStaffProfessional.htm. */
    private static final String DELETE_STAFF_EDUCATION = "/deleteStaffEducation.htm";
    
    /** view get method create staff qualifications. */
    private static final String VIEW_STAFF_MEMBER_QUALIFICATION = "staff/staffQualifications";
    
    /** model attribute of Professional Qualifications list. */
    private static final String MODEL_ATT_PROFESSIONAL_QUALIFICATIONS_LIST = "professionalQualificationsList";
    
    /** model attribute of Educational Qualifications list. */
    private static final String MODEL_ATT_EDUCATIONAL_QUALIFICATIONS_LIST = "educationalQualificationsList";
    
    /** model attribute of wrapperQualifications list. */
    private static final String MODEL_ATT_WRAPPER_QUALIFICATION = "wrapperQualification";
    
    /** model attribute of StaffEducation list. */
    private static final String MODEL_ATT_STAFF_EDUCATION_LIST = "staffEducationList";
    
    /** model attribute of StaffProfessional list. */
    private static final String MODEL_ATT_STAFF_PROFESSIONAL_LIST = "staffProfessionalList";
    
    /** staffCommonService attribute for holding StaffCommonService. */
    private StaffCommonService staffCommonService;
    
    /** staffService attribute for holding StaffService. */
    private StaffService staffService;
    
    /** Represents an instance of staffEducationValidator. */
    private StaffEducationValidator staffEducationValidator;
    
    /** Represents an instance of staffProfessionalValidator. */
    private StaffProfessionalValidator staffProfessionalValidator;
    
    /**
     * sets the StaffEducationValidator object.
     * 
     * @param objStaffEducationValidator the StaffEducationValidator to set
     */
    public void setStaffEducationValidator(StaffEducationValidator objStaffEducationValidator) {
    
        this.staffEducationValidator = objStaffEducationValidator;
    }
    
    /**
     * sets the StaffProfessionalValidator object.
     * 
     * @param objStaffProfessionalValidator the StaffProfessionalValidator to set
     */
    public void setStaffProfessionalValidator(StaffProfessionalValidator objStaffProfessionalValidator) {
    
        this.staffProfessionalValidator = objStaffProfessionalValidator;
    }
    
    /**
     * sets the StaffService object.
     * 
     * @param objStaffService the StaffService to set
     */
    public void setStaffService(StaffService objStaffService) {
    
        this.staffService = objStaffService;
    }
    
    /**
     * sets the StaffCommonService object.
     * 
     * @param staffCommonServiceObj the StaffCommonService to set
     */
    public void setStaffCommonService(StaffCommonService staffCommonServiceObj) {
    
        this.staffCommonService = staffCommonServiceObj;
    }
    
    /**
     * handle GET requests for staff_qualifications_details view.
     * 
     * @param model - ModelMap
     * @param session - HttpSession object
     * @return the name of the view.
     * @throws AkuraAppException - throw detailed exception.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showStaffQualifications(ModelMap model, HttpSession session) throws AkuraAppException {
    
        Staff objStaff = null;
        
        if (session.getAttribute(STAFF_ID) != null && (Integer) session.getAttribute(STAFF_ID) != 0) {
            int staffId = 0;
            staffId = (Integer) session.getAttribute(STAFF_ID);
            objStaff = (Staff) staffService.viewStaff(staffId);
            
            // Assign staff object to wrapperQualification.
            WrapperQualification wrapperQualification = new WrapperQualification();
            
            wrapperQualification.getStaffEducation().setStaff(objStaff);
            wrapperQualification.getStaffProfessional().setStaff(objStaff);
            
            model.addAttribute(MODEL_ATT_WRAPPER_QUALIFICATION, wrapperQualification);
            Date departureDate = staffService.findStaff(objStaff.getStaffId()).getDateOfDeparture();
            model.addAttribute(DEPARTURE_DATE, departureDate);
            
            return VIEW_STAFF_MEMBER_QUALIFICATION;
            
        }
        return REDIRECT_LOGIN;
    }
    
    /**
     * Add staffEducationQualification details in to system.
     * 
     * @param wrapperQualification - {@link wrapperQualification}
     * @param bindingResult - {@link BindingResult}
     * @param model - {@link ModelMap}
     * @return view of the staff qualifications details.
     * @throws AkuraAppException - throw detailed exception.
     */
    @RequestMapping(value = SAVE_STAFF_EDUCATION_QUALIFICATION, method = RequestMethod.POST)
    public String saveStaffEducationQualificationDetails(
            @ModelAttribute(MODEL_ATT_WRAPPER_QUALIFICATION) WrapperQualification wrapperQualification,
            BindingResult bindingResult, ModelMap model) throws AkuraAppException {
    
        StaffEducation staffEducation = wrapperQualification.getStaffEducation();
        staffEducationValidator.validate(staffEducation, bindingResult);
        
        if (bindingResult.hasErrors()) {
            return VIEW_STAFF_MEMBER_QUALIFICATION;
        } else {
            // save Education qualification
            boolean isNew = false;
            try {
                if (staffEducation.getStaffEducationId() != 0) {
                    staffService.updateStaffEducation(staffEducation);
                } else {
                    isNew = true;
                    staffService.addStaffEducation(staffEducation);
                }
                
            } catch (AkuraAppException e) {
                if (e.getCause() instanceof DataIntegrityViolationException) {
                    bindingResult.rejectValue(ERROR_EDUCATIONAL_QUALIFICATION_ID, STQ_UI_DUPLICATE_DESCRIPTION);
                }
                if (isNew) {
                    staffEducation.setStaffEducationId(0);
                    wrapperQualification.setStaffEducation(staffEducation);
                    model.addAttribute(MODEL_ATT_WRAPPER_QUALIFICATION, wrapperQualification);
                }
                return VIEW_STAFF_MEMBER_QUALIFICATION;
            }
        }
        return REDIRECT_STAFF_QUALIFICATIONS;
    }
    
    /**
     * Add staffProfessional details in to system.
     * 
     * @param wrapperQualification - {@link wrapperQualification}
     * @param bindingResult - {@link BindingResult}
     * @param model - {@link ModelMap}
     * @return view of the staff qualifications details.
     * @throws AkuraAppException - throw detailed exception.
     */
    @RequestMapping(value = SAVE_STAFF_PROFESSIONAL_QUALIFICATION, method = RequestMethod.POST)
    public String saveStaffProfessionalQualificationDetails(
            @ModelAttribute(MODEL_ATT_WRAPPER_QUALIFICATION) WrapperQualification wrapperQualification,
            BindingResult bindingResult, ModelMap model) throws AkuraAppException {
    
        StaffProfessional staffProfessional = wrapperQualification.getStaffProfessional();
        staffProfessionalValidator.validate(staffProfessional, bindingResult);
        
        if (bindingResult.hasErrors()) {
            return VIEW_STAFF_MEMBER_QUALIFICATION;
        } else {
            boolean isNew = false;
            // save professional qualification
            try {
                if (staffProfessional.getStaffProfessionalId() != 0) {
                    staffService.updateStaffProfessional(staffProfessional);
                    
                } else {
                    isNew = true;
                    staffService.addStaffProfessional(staffProfessional);
                }
            } catch (AkuraAppException e) {
                if (e.getCause() instanceof DataIntegrityViolationException) {
                    bindingResult.rejectValue(ERROR_PROFESSIONAL_QUALIFICATION_ID, STQ_UI_DUPLICATE_DESCRIPTION);
                }
                if (isNew) {
                    staffProfessional.setStaffProfessionalId(0);
                    wrapperQualification.setStaffProfessional(staffProfessional);
                    model.addAttribute(MODEL_ATT_WRAPPER_QUALIFICATION, wrapperQualification);
                }
                return VIEW_STAFF_MEMBER_QUALIFICATION;
            }
        }
        return REDIRECT_STAFF_QUALIFICATIONS;
    }
    
    /**
     * Registers the given custom property editor for all properties of the Date type.
     * 
     * @param binder - data binder used to register the Date objects.
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    
        final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    
    /**
     * Method is to delete staffEducation.
     * 
     * @param request - HttpServletRequest
     * @return redirect to staff Qualification page.
     * @throws AkuraAppException Exception
     */
    @RequestMapping(value = DELETE_STAFF_EDUCATION, method = RequestMethod.POST)
    public String deleteStaffEducation(HttpServletRequest request) throws AkuraAppException {
    
        final int staffEducationId = Integer.parseInt(request.getParameter(SELECTED_STAFF_EDUCATION_QUAL_ID));
        staffService.deleteStaffEducation((StaffEducation) staffService.viewStaffEducation(staffEducationId));
        
        return REDIRECT_STAFF_QUALIFICATIONS;
    }
    
    /**
     * Method is to delete staff Professional.
     * 
     * @param request - HttpServletRequest
     * @return redirect to staff Qualification page.
     * @throws AkuraAppException Exception
     */
    @RequestMapping(value = DELETE_STAFF_PROFESSIONAL, method = RequestMethod.POST)
    public String deleteStaffProfessional(HttpServletRequest request) throws AkuraAppException {
    
        final int staffProffesionalId = Integer.parseInt(request.getParameter(SELECTED_STAFF_PROFESIONAL_QUAL_ID));
        staffService.deleteStaffProfessional((StaffProfessional) staffService
                .viewStaffProfessional(staffProffesionalId));
        
        return REDIRECT_STAFF_QUALIFICATIONS;
    }
    
    /**
     * Method is to return StaffEducation list.
     * 
     * @param session - HttpSession object
     * @return list of StaffEducation
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_STAFF_EDUCATION_LIST)
    public List<StaffEducation> populateStaffEducationList(HttpSession session) throws AkuraAppException {
    
        List<StaffEducation> staffEducationList = null;
        
        if (session.getAttribute(STAFF_ID) != null && (Integer) session.getAttribute(STAFF_ID) != 0) {
            staffEducationList = staffService.getStaffEducationListForStaff((Integer) session.getAttribute(STAFF_ID));
        }
        return staffEducationList;
    }
    
    /**
     * Method is to return StaffProfessional list.
     * 
     * @param session - HttpSession object
     * @return list of StaffProfessional
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_STAFF_PROFESSIONAL_LIST)
    public List<StaffProfessional> populateStaffProfessionalList(HttpSession session) throws AkuraAppException {
    
        List<StaffProfessional> staffProfessionalList = null;
        
        if (session.getAttribute(STAFF_ID) != null && (Integer) session.getAttribute(STAFF_ID) != 0) {
            staffProfessionalList =
                    staffService.getStaffProfessionalListForStaff((Integer) session.getAttribute(STAFF_ID));
        }
        return staffProfessionalList;
    }
    
    /**
     * Method is to return Professional Qualifications list.
     * 
     * @return list of Professional Qualifications
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_PROFESSIONAL_QUALIFICATIONS_LIST)
    public List<ProfessionalQualification> populateProfessionalQualificationsList() throws AkuraAppException {
    
        List<ProfessionalQualification> professionalQualificationsList =
                staffCommonService.getProfessionalQualifications();
        SortUtil.sortProfessionalQualificationList(professionalQualificationsList);
        return professionalQualificationsList;
    }
    
    /**
     * Method is to return Education Qualifications list.
     * 
     * @return list of Education Qualifications
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_EDUCATIONAL_QUALIFICATIONS_LIST)
    public List<EducationalQualification> populateEducationQualificationsList() throws AkuraAppException {
    
        List<EducationalQualification> educationalQualificationsList =
                staffCommonService.getEducationalQualifications();
        SortUtil.sortEducationalQualificationList(educationalQualificationsList);
        return educationalQualificationsList;
    }
    
}
