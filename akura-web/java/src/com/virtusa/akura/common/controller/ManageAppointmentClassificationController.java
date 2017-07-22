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

package com.virtusa.akura.common.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.AppointmentClassification;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.StaffCommonService;
import com.virtusa.akura.common.validator.AppointmentClassificationValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * ManageAppointmentClassificationController is to handle Add/Edit/Delete operations for
 * AppointmentClassification reference module.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class ManageAppointmentClassificationController {
    
    /** view post method manage appointmentClassification. */
    private static final String VIEW_POST_MANAGE_APPOINTMENTCLASSIFICATION =
            "redirect:manageAppointmentClassification.htm";
    
    /** view get method manage appointmentClassification. */
    private static final String VIEW_GET_MANAGE_APPOINTMENTCLASSIFICATION = "reference/manageAppointmentClassification";
    
    /** model attribute of appointmentClassification. */
    private static final String MODEL_ATT_APPOINTMENTCLASSIFICATION = "appointmentClassification";
    
    /** model attribute of appointmentClassification list. */
    private static final String MODEL_ATT_APPOINTMENTCLASSIFICATION_LIST = "appointmentClassificationList";
    
    /** request mapping value for save or update appointmentClassification. */
    private static final String REQ_MAP_VALUE_SAVEORUPDATE = "/saveOrUpdateAppointmentClassification.htm";
    
    /** request mapping value for delete appointmentClassification. */
    private static final String REQ_MAP_VALUE_DELETE = "/manageDeleteAppointmentClassification.htm";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_KEY = "REF.UI.RECORD.EXIST.ERROR";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_DELETE = "REF.UI.APPOINTMENTCLASSIFICATION.DELETE";
    
    /** attribute for holding message. */
    private static final String MESSAGE = "message";
    
    /** model attribute of selected object. */
    private static final String MODEL_ATT_SELECTED_OBJECT = "selectedObj";
    
    /** model attribute of object. */
    private static final String MODEL_ATT_OBJECT = "appointmentClassification";
    
    /** Model attribute of "showEditSection". */
    private static final String SHOW_EDIT_SECTION = "showEditSection";
    
    /**
     * {@link StaffCommonService}.
     */
    private StaffCommonService staffCommonService;
    
    /**
     * {@link AppointmentClassificationValidator}.
     */
    private AppointmentClassificationValidator appointmentClassificationValidator;
    
    /**
     * @param staffCommonServiceVlaue the staffCommonService to set
     */
    public void setStaffCommonService(StaffCommonService staffCommonServiceVlaue) {
    
        this.staffCommonService = staffCommonServiceVlaue;
    }
    
    /**
     * @param appointmentClassificationValidatorValue the appointmentClassificationValidator to set
     */
    public void setAppointmentClassificationValidator(
            AppointmentClassificationValidator appointmentClassificationValidatorValue) {
    
        this.appointmentClassificationValidator = appointmentClassificationValidatorValue;
    }
    
    /**
     * ManageAppointmentClassificationController constructor.
     */
    public ManageAppointmentClassificationController() {
    
    }
    
    /**
     * handle GET requests for appointmentClassification_details view.
     * 
     * @param model - ModelMap
     * @return the name of the view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showAppointmentClassificationDetail(ModelMap model) {
    
        AppointmentClassification appointmentClassification =
                (AppointmentClassification) model.get(MODEL_ATT_APPOINTMENTCLASSIFICATION);
        
        if (appointmentClassification == null) {
            appointmentClassification = new AppointmentClassification();
        }
        model.addAttribute(MODEL_ATT_APPOINTMENTCLASSIFICATION, appointmentClassification);
        
        return VIEW_GET_MANAGE_APPOINTMENTCLASSIFICATION;
    }
    
    /**
     * This is to manage Add and Edit appointmentClassification details.
     * 
     * @param appointmentClassification - {@link AppointmentClassification}.
     * @param model {@link ModelMap}
     * @param bindingResult {@link BindingResult}
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - throw this
     */
    @RequestMapping(value = REQ_MAP_VALUE_SAVEORUPDATE, method = RequestMethod.POST)
    public String saveOrUpdateAppointmentClassification(ModelMap model,
            @ModelAttribute(MODEL_ATT_APPOINTMENTCLASSIFICATION) AppointmentClassification appointmentClassification,
            BindingResult bindingResult) throws AkuraAppException {
    
        AppointmentClassification selectedObj = null;
        appointmentClassificationValidator.validate(appointmentClassification, bindingResult);
        
        if (bindingResult.hasErrors()) {
            if (appointmentClassification.getClassificationId() != 0) {
                selectedObj =
                        staffCommonService.findAppointmentClassification(appointmentClassification.getClassificationId());
                model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
            }
            model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
            return VIEW_GET_MANAGE_APPOINTMENTCLASSIFICATION;
            
        } else {
            String strAppointmentClassification =
                    appointmentClassification.getDescription().trim().replaceAll("\\s+", " ");
            String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_KEY);
            appointmentClassification.setDescription(strAppointmentClassification);
            
            if (staffCommonService.isExistsAppointmentClassification(strAppointmentClassification)) {
                if (appointmentClassification.getClassificationId() != 0) {
                    
                    if (staffCommonService.findAppointmentClassification(appointmentClassification.getClassificationId())
                            .getDescription().compareToIgnoreCase(strAppointmentClassification) == 0) {
                        
                        if (staffCommonService
                                .findAppointmentClassification(appointmentClassification.getClassificationId())
                                .getDescription().equals(strAppointmentClassification)) {
                            
                            AppointmentClassification newAppointmentClassification = new AppointmentClassification();
                            newAppointmentClassification =
                                    staffCommonService.findAppointmentClassification(appointmentClassification
                                            .getClassificationId());
                            model.addAttribute(MODEL_ATT_APPOINTMENTCLASSIFICATION, newAppointmentClassification);
                            
                        } else {
                            
                            staffCommonService.updateAppointmentClassification(appointmentClassification);
                            AppointmentClassification newAppointmentClassification = new AppointmentClassification();
                            newAppointmentClassification =
                                    staffCommonService.findAppointmentClassification(appointmentClassification
                                            .getClassificationId());
                            model.addAttribute(MODEL_ATT_APPOINTMENTCLASSIFICATION, newAppointmentClassification);
                            
                        }
                        
                    } else {
                        selectedObj =
                                staffCommonService.findAppointmentClassification(appointmentClassification
                                        .getClassificationId());
                        model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
                        model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
                        model.addAttribute(MESSAGE, message);
                        return VIEW_GET_MANAGE_APPOINTMENTCLASSIFICATION;
                    }
                    
                } else {
                    model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
                    model.addAttribute(MESSAGE, message);
                    return VIEW_GET_MANAGE_APPOINTMENTCLASSIFICATION;
                    
                }
                
            } else {
                
                if (appointmentClassification.getClassificationId() == 0) {
                    
                    staffCommonService.addAppointmentClassification(appointmentClassification);
                    
                } else {
                    
                    staffCommonService.updateAppointmentClassification(appointmentClassification);
                }
            }
        }
        return VIEW_POST_MANAGE_APPOINTMENTCLASSIFICATION;
    }
    
    /**
     * Deletes the relevant AppointmentClassification object.
     * 
     * @param model - a HashMap that contains information of the Appointment nature
     * @param request {@link HttpServletRequest}
     * @param appointmentClassification - an instance of AppointmentClassification
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when deleting a
     *         AppointmentClassification instance.
     */
    @RequestMapping(REQ_MAP_VALUE_DELETE)
    public final String deleteAppointmentClassification(
            final ModelMap model,
            HttpServletRequest request,
            @ModelAttribute(MODEL_ATT_APPOINTMENTCLASSIFICATION) final AppointmentClassification appointmentClassification)
            throws AkuraAppException {
    
        String message;
        try {
            
            // get the classification id - it is auto generated on DB table
            int id = appointmentClassification.getClassificationId();
            
            // get appointmentClassification object by passing relevant classification id.
            AppointmentClassification findAppointmentClassification = staffCommonService.findAppointmentClassification(id);
            
            // call delete method to be deleted relevant class object
            staffCommonService.deleteAppointmentClassification(findAppointmentClassification);
            
            return VIEW_POST_MANAGE_APPOINTMENTCLASSIFICATION;
            
        } catch (AkuraAppException e) {
            
            // check , if there are violations of deleting record, when they have references.
            if (e.getCause() instanceof DataIntegrityViolationException) {
                
                message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_DELETE);
                AppointmentClassification newAppointmentClassification = new AppointmentClassification();
                model.addAttribute(MESSAGE, message);
                model.addAttribute(MODEL_ATT_APPOINTMENTCLASSIFICATION, newAppointmentClassification);
                
                return VIEW_GET_MANAGE_APPOINTMENTCLASSIFICATION;
                
            } else {
                throw e;
            }
        }
    }
    
    /**
     * Method is to get AppointmentClassification list.
     * 
     * @return list of AppointmentClassification
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_APPOINTMENTCLASSIFICATION_LIST)
    public List<AppointmentClassification> populateAppointmentClassificationList() throws AkuraAppException {
    
        return SortUtil.sortAppointmentClassificationList(staffCommonService.getAppointmentClassificationList());
    }
    
}
