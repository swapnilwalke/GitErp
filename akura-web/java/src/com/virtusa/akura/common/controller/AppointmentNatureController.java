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

import com.virtusa.akura.api.dto.AppointmentNature;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.StaffCommonService;
import com.virtusa.akura.common.validator.AppointmentNatureValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * AppointmentNatureController is to handle Add/Edit/Delete operations for Appointment reference module.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class AppointmentNatureController {
    
    /** view post method manage Nature of the appointment. */
    private static final String VIEW_POST_MANAGE_APPOINTMENT_NATURE = "redirect:manageAppointmentNature.htm";
    
    /** view get method manage Nature of the appointment. */
    private static final String VIEW_GET_MANAGE_APPOINTMENT_NATURE = "reference/manageAppointmentNature";
    
    /** model attribute of appointment nature. */
    private static final String MODEL_ATT_APPOINTMENT_NATURE = "appointmentNature";
    
    /** model attribute of appointment nature list. */
    private static final String MODEL_ATT_APPOINTMENT_NATURE_LIST = "appointmentNatureList";
    
    /** request mapping value for save or update appointment nature. */
    private static final String REQ_MAP_VALUE_SAVEORUPDATE = "/saveOrUpdateAppointmentNature.htm";
    
    /** request mapping value for delete appointment nature. */
    private static final String REQ_MAP_VALUE_DELETE = "/manageDeleteAppointmentNature.htm";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_KEY = "REF.UI.RECORD.EXIST.ERROR";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_DELETE = "REF.UI.APPOINTMENTNATURE.DELETE";
    
    /** attribute for holding message. */
    private static final String MESSAGE = "message";
    
    /** model attribute of selected object. */
    private static final String MODEL_ATT_SELECTED_OBJECT = "selectedObj";
    
    /** Model attribute of "showEditSection". */
    private static final String SHOW_EDIT_SECTION = "showEditSection";
    
    /**
     * {@link CommonService}.
     */
    private StaffCommonService staffCommonService;
    
    /**
     * {@link AppointmentNatureValidator}.
     */
    private AppointmentNatureValidator appointmentNatureValidator;
    
    /**
     * @param staffCommonServiceVlaue the staffCommonService to set
     */
    public void setStaffCommonService(StaffCommonService staffCommonServiceVlaue) {
    
        this.staffCommonService = staffCommonServiceVlaue;
    }
    
    /**
     * @param appointmentNatureValidaterValue the appointmentNatureValidator to set
     */
    public void setAppointmentNatureValidator(AppointmentNatureValidator appointmentNatureValidaterValue) {
    
        this.appointmentNatureValidator = appointmentNatureValidaterValue;
    }
    
    /**
     * Default constructor.
     */
    public AppointmentNatureController() {
    
        super();
    }
    
    /**
     * handle GET requests for appointmentNature_details view.
     * 
     * @param model - ModelMap
     * @return the name of the view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showAppointmentNature(ModelMap model) {
    
        AppointmentNature appointmentNature = (AppointmentNature) model.get(MODEL_ATT_APPOINTMENT_NATURE);
        
        if (appointmentNature == null) {
            appointmentNature = new AppointmentNature();
        }
        model.addAttribute(MODEL_ATT_APPOINTMENT_NATURE, appointmentNature);
        
        return VIEW_GET_MANAGE_APPOINTMENT_NATURE;
    }
    
    /**
     * This is to manage Add and Edit Appointment nature details.
     * 
     * @param appointmentNature - {@link AppointmentNature}.
     * @param request - HttpServletRequest
     * @param model {@link ModelMap}
     * @param bindingResult {@link BindingResult}
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - throw this
     */
    @RequestMapping(value = REQ_MAP_VALUE_SAVEORUPDATE, method = RequestMethod.POST)
    public String saveOrUpdateAppointmentNature(HttpServletRequest request, ModelMap model,
            @ModelAttribute(MODEL_ATT_APPOINTMENT_NATURE) AppointmentNature appointmentNature,
            BindingResult bindingResult) throws AkuraAppException {
    
        AppointmentNature selectedObj = null;
        appointmentNatureValidator.validate(appointmentNature, bindingResult);
        
        if (bindingResult.hasErrors()) {
            if (appointmentNature.getNatureId() != 0) {
                selectedObj = staffCommonService.getAppointmentNatureById(appointmentNature.getNatureId());
                model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
            }
            model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
            return VIEW_GET_MANAGE_APPOINTMENT_NATURE;
            
        } else {
            
            String strAppointmentNature = appointmentNature.getDescription().trim().replaceAll("\\s+", " ");
            String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_KEY);
            appointmentNature.setDescription(strAppointmentNature);
            
            if (staffCommonService.isExistsAppointmentNature(strAppointmentNature)) {
                
                if (appointmentNature.getNatureId() != 0
                        && staffCommonService.getAppointmentNatureById(appointmentNature.getNatureId()).getDescription()
                                .compareToIgnoreCase(strAppointmentNature) == 0) {
                    
                    if (staffCommonService.getAppointmentNatureById(appointmentNature.getNatureId()).getDescription()
                            .equals(strAppointmentNature)) {
                        
                        AppointmentNature newAppointmentNature = new AppointmentNature();
                        newAppointmentNature = staffCommonService.getAppointmentNatureById(appointmentNature.getNatureId());
                        model.addAttribute(MODEL_ATT_APPOINTMENT_NATURE, newAppointmentNature);
                        
                    } else {
                        
                        staffCommonService.updateAppointmentNature(appointmentNature);
                        AppointmentNature newAppointmentNature = new AppointmentNature();
                        newAppointmentNature = staffCommonService.getAppointmentNatureById(appointmentNature.getNatureId());
                        model.addAttribute(MODEL_ATT_APPOINTMENT_NATURE, newAppointmentNature);
                        
                    }
                    
                } else {
                    selectedObj = staffCommonService.getAppointmentNatureById(appointmentNature.getNatureId());
                    model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
                    model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
                    model.addAttribute(MESSAGE, message);
                    return VIEW_GET_MANAGE_APPOINTMENT_NATURE;
                }
                
            } else {
                
                if (appointmentNature.getNatureId() == 0) {
                    
                    staffCommonService.addAppointmentNature(appointmentNature);
                    
                } else {
                    
                    staffCommonService.updateAppointmentNature(appointmentNature);
                }
            }
        }
        return VIEW_POST_MANAGE_APPOINTMENT_NATURE;
    }
    
    /**
     * Deletes the relevant Appointment nature object.
     * 
     * @param model - a HashMap that contains information of the Appointment nature
     * @param appointmentNature - an instance of AppointmentNature
     * @param request - HttpServletRequest
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when deleting a AppointmentNature
     *         instance.
     */
    @RequestMapping(value = REQ_MAP_VALUE_DELETE, method = RequestMethod.POST)
    public final String deleteAppointmentNature(final ModelMap model,
            @ModelAttribute(MODEL_ATT_APPOINTMENT_NATURE) final AppointmentNature appointmentNature,
            HttpServletRequest request) throws AkuraAppException {
    
        String message;
        try {
            
            // get the appointmentNature id - it is auto generated on DB table
            int id = appointmentNature.getNatureId();
            
            // get appointmentNature object by passing relevant appointmentNature id.
            AppointmentNature findAppointmentNature = staffCommonService.getAppointmentNatureById(id);
            
            // call delete method to be deleted relevant class object
            staffCommonService.deleteAppointmentNature(findAppointmentNature);
            
            return VIEW_POST_MANAGE_APPOINTMENT_NATURE;
            
        } catch (AkuraAppException e) {
            
            if (e.getCause() instanceof DataIntegrityViolationException) {
                
                message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_DELETE);
                AppointmentNature newAppointmentNature = new AppointmentNature();
                model.addAttribute(MESSAGE, message);
                model.addAttribute(MODEL_ATT_APPOINTMENT_NATURE, newAppointmentNature);
                return VIEW_GET_MANAGE_APPOINTMENT_NATURE;
                
            } else {
                throw e;
            }
        }
    }
    
    /**
     * Method is to get appointment nature list.
     * 
     * @return list of appointment natures
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_APPOINTMENT_NATURE_LIST)
    public List<AppointmentNature> populateAppointmentNatureList() throws AkuraAppException {
    
        return SortUtil.sortAppointmentNatureList(staffCommonService.getAppointmentNatureList());
    }
}
