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

import com.virtusa.akura.api.dto.StaffLeaveType;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.StaffCommonService;
import com.virtusa.akura.common.validator.StaffLeaveTypeValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * StaffLeaveTypeController is to handle Add/Edit/Delete operations for Staff Leave Type reference module.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class StaffLeaveTypeController {
    
    /** view post method manage StaffLeaveType. */
    private static final String VIEW_POST_MANAGE_STAFFLEAVE_TYPE = "redirect:manageStaffLeaveTypes.htm";
    
    /** view get method manage StaffLeaveType. */
    private static final String VIEW_GET_STAFFLEAVE_TYPE = "reference/manageStaffLeaveTypes";
    
    /** model attribute of StaffLeaveType. */
    private static final String MODEL_ATT_STAFFLEAVE_TYPE = "staffLeaveType";
    
    /** model attribute of StaffLeaveType list. */
    private static final String MODEL_ATT_STAFFLEAVE_TYPE_LIST = "staffleaveTypeList";
    
    /** model attribute of MaxStaffLeaves . */
    private static final String MODEL_ATT_MAXSTAFFLEAVES = "maxStaffLeaves";
    
    /** request mapping value for save or update StaffLeaveType. */
    private static final String REQ_MAP_VALUE_SAVEORUPDATE = "/manageSaveOrUpdateStaffleaveTypes.htm";
    
    /** request mapping value for delete StaffLeaveType. */
    private static final String REQ_MAP_VALUE_DELETE = "/manageDeleteStaffLeaveType.htm";
    
    /** request attribute for StaffLeaveTypeId. */
    private static final String REQ_STAFFLEAVE_TYPEID = "staffLeaveTypeId";
    
    /** request attribute for message. */
    private static final String MESSAGE = "message";
    
    /** model attribute of selected Staff object. */
    private static final String MODEL_ATT_SELECTED_OBJECT = "selectedObj";
    
    /** model attribute of selected Max Staff Leave Number object. */
    private static final String MODEL_ATT_SELECTED_MAX_NO_OF_LEAVE = "editedMaxStaffLeaveVal";
    
    /** Constant to represent string regex expression. */
    private static final String STRING_REGEX = "( )+";
    
    /** Constant to represent string space. */
    private static final String STRING_SPACE = "";
    
    /**
     * string constant for holding error key `REF.UI.STAFFLEAVETYPE.DESCRIPTION.EXIST`.
     */
    private static final String ERROR_MSG_KEY = "STA.UI.DUPLICATE.DESCRIPTION";
    
    /** string constant for holding error key `REF.UI.STAFFLEAVETYPE.DELETE`. */
    private static final String ERROR_MSG_DELETE = "REF.UI.STAFFLEAVETYPE.DELETE";
    
    /** string constant for holding error key `REF.UI.STAFFLEAVETYPE.EDIT`. */
    private static final String ERROR_MSG_EDIT = "REF.UI.STAFFLEAVETYPE.EDIT";
    
    /** string constant for holding error key `REF.UI.STAFFLEAVETYPE.EDIT`. */
    private static final String ERROR_MSG_MAX_STAFF_LEAVES = "REF.UI.STAFFLEAVETYPE.MAXSTAFFLEAVES";
    
    /** Holds houseValidator instance {@link StaffLeaveTypeValidator}. */
    private StaffLeaveTypeValidator staffLeaveTypeValidator;
    
    /** Model attribute of "showEditSection". */
    private static final String SHOW_EDIT_SECTION = "showEditSection";
    
    /** staffCommonService attribute for holding StaffCommonService. */
    private StaffCommonService staffCommonService;
    
    /**
     * Set the StaffCommonService to inject the StaffCommonService.
     * 
     * @param staffCommonServiceVlaue the StaffCommonService to set
     */
    
    public void setStaffCommonService(StaffCommonService staffCommonServiceVlaue) {
    
        this.staffCommonService = staffCommonServiceVlaue;
    }
    
    /**
     * Set the staffLeaveType Validator to inject the validator.
     * 
     * @param staffLeaveTypeValidatorValue ValidatorValue the StaffLeaveType Validator to set
     */
    public void setStaffLeaveTypeValidator(StaffLeaveTypeValidator staffLeaveTypeValidatorValue) {
    
        this.staffLeaveTypeValidator = staffLeaveTypeValidatorValue;
    }
    
    /**
     * Handle GET requests for StaffLeaveType _details view.
     * 
     * @param model - ModelMap
     * @return the name of the view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showStaffLeaveTypeDetail(ModelMap model) {
    
        StaffLeaveType staffLeaveType = (StaffLeaveType) model.get(MODEL_ATT_STAFFLEAVE_TYPE);
        
        if (staffLeaveType == null) {
            staffLeaveType = new StaffLeaveType();
        }
        model.addAttribute(MODEL_ATT_STAFFLEAVE_TYPE, staffLeaveType);
        
        return VIEW_GET_STAFFLEAVE_TYPE;
    }
    
    /**
     * This method is to handle Add/Edit StaffLeaveType details.
     * 
     * @param staffLeaveType - {@link StaffLeaveType }
     * @param request - {@link HttpServletRequest}
     * @param model - {@link ModelMap}
     * @param result - {@link BindingResult}
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - throw this exception
     */
    @RequestMapping(value = REQ_MAP_VALUE_SAVEORUPDATE, method = RequestMethod.POST)
    public final String saveOrUpdateStaffLeaveType(
            @ModelAttribute(MODEL_ATT_STAFFLEAVE_TYPE) final StaffLeaveType staffLeaveType, BindingResult result,
            HttpServletRequest request, final ModelMap model) throws AkuraAppException {
    
        String strMessage = null;
        String des = staffLeaveType.getDescription();
        String strDescription = (staffLeaveType.getDescription().trim()).replaceAll(STRING_REGEX, STRING_SPACE);
        int intStaffLeaveTypeId = staffLeaveType.getStaffLeaveTypeId();
        String maxs = request.getParameter(MODEL_ATT_MAXSTAFFLEAVES).trim();
        StaffLeaveType newStaffLeaveType =
                staffCommonService.findStaffLeaveTypeById(staffLeaveType.getStaffLeaveTypeId());
        int max = 0;
        
        staffLeaveType.setDescription(des);
        try {
            if (maxs.equals("")) {
                max = AkuraWebConstant.SPACE_MAXSTAFFLEAVES;
            } else {
                max = Integer.parseInt(maxs);
            }
            
            staffLeaveType.setMaxStaffLeaves(max);
        } catch (NumberFormatException e) {
            staffLeaveType.setMaxStaffLeaves(AkuraWebConstant.STRING_MAXSTAFFLEAVES);
        }
        
        staffLeaveTypeValidator.validate(staffLeaveType, result);
        try {
            if (result.hasErrors()) {
                
                model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
                model.addAttribute(MODEL_ATT_SELECTED_OBJECT, newStaffLeaveType);
                model.addAttribute(MODEL_ATT_SELECTED_MAX_NO_OF_LEAVE, staffLeaveType.getMaxStaffLeaves());
                return VIEW_GET_STAFFLEAVE_TYPE;
                
            } else {
                // if StaffLeaveType already exist.
                if (staffCommonService.isExistsStaffLeaveType(strDescription, intStaffLeaveTypeId, intStaffLeaveTypeId)) {
                    
                    strMessage = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_KEY);
                    
                    model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
                    model.addAttribute(MODEL_ATT_SELECTED_OBJECT, newStaffLeaveType);
                    model.addAttribute(MODEL_ATT_SELECTED_MAX_NO_OF_LEAVE, staffLeaveType.getMaxStaffLeaves());
                    model.addAttribute(MESSAGE, strMessage);
                    
                    return VIEW_GET_STAFFLEAVE_TYPE;
                } else {
                    
                    if (staffLeaveType.getMaxStaffLeaves() != 0) {
                        
                        staffLeaveType.setDescription(des);
                        staffLeaveType.setMaxStaffLeaves(max);
                        if (intStaffLeaveTypeId == 0) {
                            staffCommonService.addStaffLeaveType(staffLeaveType);
                        } else {
                            if (staffCommonService.isAlreadyAllocated(intStaffLeaveTypeId)) {
                                
                                String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_EDIT);
                                model.addAttribute(MODEL_ATT_SELECTED_OBJECT, newStaffLeaveType);
                                model.addAttribute(MODEL_ATT_SELECTED_MAX_NO_OF_LEAVE,
                                        staffLeaveType.getMaxStaffLeaves());
                                model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
                                model.addAttribute(MESSAGE, message);
                                return VIEW_GET_STAFFLEAVE_TYPE;
                            } else {
                                staffCommonService.updateStaffLeaveType(staffLeaveType);
                            }
                        }
                    } else {
                        String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_MAX_STAFF_LEAVES);
                        
                        model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
                        model.addAttribute(MODEL_ATT_SELECTED_OBJECT, newStaffLeaveType);
                        model.addAttribute(MODEL_ATT_SELECTED_MAX_NO_OF_LEAVE, staffLeaveType.getMaxStaffLeaves());
                        model.addAttribute(MESSAGE, message);
                        return VIEW_GET_STAFFLEAVE_TYPE;
                    }
                    
                }
            }
        } catch (AkuraAppException ex) {
            throw ex;
        }
        return VIEW_POST_MANAGE_STAFFLEAVE_TYPE;
    }
    
    /**
     * Delete a StaffLeaveType from reference module.
     * 
     * @param request {@link HttpServletRequest}
     * @param model {@link ModelMap}
     * @throws AkuraAppException - Detailed exception
     * @return name of the view.
     */
    @RequestMapping(value = REQ_MAP_VALUE_DELETE, method = RequestMethod.POST)
    public String deleteStaffLeaveType(HttpServletRequest request, ModelMap model) throws AkuraAppException {
    
        try {
            int staffLeaveTypeId = Integer.parseInt(request.getParameter(REQ_STAFFLEAVE_TYPEID));
            staffCommonService.deleteStaffLeaveType(staffLeaveTypeId);
        } catch (AkuraAppException e) {
            if (e.getCause() instanceof DataIntegrityViolationException) {
                String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_DELETE);
                StaffLeaveType newStaffLeaveType = new StaffLeaveType();
                model.addAttribute(MODEL_ATT_STAFFLEAVE_TYPE, newStaffLeaveType);
                model.addAttribute(MESSAGE, message);
                
                return VIEW_GET_STAFFLEAVE_TYPE;
            }
        }
        
        return VIEW_POST_MANAGE_STAFFLEAVE_TYPE;
    }
    
    /**
     * Method is to return StaffLeaveType list.
     * 
     * @return list of StaffLeaveType
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_STAFFLEAVE_TYPE_LIST)
    public final List<StaffLeaveType> populateStaffLeaveTypeList() throws AkuraAppException {
    
        return SortUtil.sortStaffLeaveList(staffCommonService.getStaffLeaveTypeList());
        
    }
}
