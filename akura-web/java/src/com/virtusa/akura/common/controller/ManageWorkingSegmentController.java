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

import com.virtusa.akura.api.dto.WorkingSegment;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.WorkingSegmentValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * @author Virtusa Corporation
 */
@Controller
public class ManageWorkingSegmentController {
    
    /** view post method manage WorkingSegment. */
    private static final String VIEW_POST_MANAGE_WORKINGSEGMENT = "redirect:manageWorkingSegment.htm";
    
    /** view get method manage WorkingSegment. */
    private static final String VIEW_GET_MANAGE_WORKINGSEGMENT = "reference/manageWorkingSegment";

    /** model attribute of WorkingSegment. */
    private static final String MODEL_ATT_WORKINGSEGMENT = "workingSegment";
    
    /** model attribute of WorkingSegment list. */
    private static final String MODEL_ATT_WORKINGSEGMENT_LIST = "workingSegmentList";
    
    /** request mapping value for save or update WorkingSegment. */
    private static final String REQ_MAP_VALUE_SAVEORUPDATE = "/saveOrUpdateWorkingSegment.htm";
    
    /** request mapping value for delete WorkingSegment. */
    private static final String REQ_MAP_VALUE_DELETE = "/manageDeleteWorkingSegment.htm";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_KEY = "REF.UI.WORKINGSEGMENT.DESCRIPTION.EXIST";
    
    /** attribute for holding error message key. */
    private static final String ERROR_MSG_DELETE = "REF.UI.WORKINGSEGMENT.DELETE";
    
    /** attribute for holding message. */
    private static final String MESSAGE = "message";
    
    /** Model attribute of "showEditSection". */
    private static final String SHOW_EDIT_SECTION = "showEditSection";
    
    /** Model attribute of "selectedObjId". */
    private static final String SELECTED_OBJ_ID = "selectedObjId";
    
    /**
     * Represents workingSegmentValidator instance of {@link WorkingSegmentValidator}.
     */
    private WorkingSegmentValidator workingSegmentValidator;
    
    /**
     * {@link CommonService}.
     */
    private CommonService commonService;
    
    /**
     * Set the workingSegmentValidator to inject the validator.
     * 
     * @param workingSegmentValidatorValue the workingSegmentValidator to set
     */
    public void setWorkingSegmentValidator(WorkingSegmentValidator workingSegmentValidatorValue) {
    
        this.workingSegmentValidator = workingSegmentValidatorValue;
    }
    
    /**
     * @param commonServiceValue the commonService to set
     */
    public void setCommonService(CommonService commonServiceValue) {
    
        this.commonService = commonServiceValue;
    }
    
    /**
	 *
	 */
    public ManageWorkingSegmentController() {
    
    }
    
    /**
     * handle GET requests for WorkingSegment_details view.
     * 
     * @param model - ModelMap
     * @return the name of the view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showWorkingSegmentDetail(ModelMap model) {
    
        WorkingSegment workingSegment = (WorkingSegment) model.get(MODEL_ATT_WORKINGSEGMENT);
        
        if (workingSegment == null) {
            workingSegment = new WorkingSegment();
        }
        model.addAttribute(MODEL_ATT_WORKINGSEGMENT, workingSegment);
        
        return VIEW_GET_MANAGE_WORKINGSEGMENT;
    }
    
    /**
     * Manage WorkingSegment details.
     * 
     * @param workingSegment - {@link WorkingSegment}.
     * @param request - HttpServletRequest
     * @param model {@link ModelMap}
     * @param bindingResult {@link BindingResult}
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - throw this
     */
    @RequestMapping(value = REQ_MAP_VALUE_SAVEORUPDATE, method = RequestMethod.POST)
    public String saveOrUpdateWorkingSegment(@ModelAttribute(MODEL_ATT_WORKINGSEGMENT) WorkingSegment workingSegment,
            BindingResult bindingResult, HttpServletRequest request, ModelMap model) throws AkuraAppException {
    
        workingSegmentValidator.validate(workingSegment, bindingResult);
        
        if (bindingResult.hasErrors()) {
            model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
            model.addAttribute(SELECTED_OBJ_ID, workingSegment.getWorkingSegmentId());
            return VIEW_GET_MANAGE_WORKINGSEGMENT;
        } else {
            
            String strWorkingSegment = workingSegment.getDescription().trim();
            int workingSegmentId = workingSegment.getWorkingSegmentId();
            
            if (commonService.isExistsWorkingSegment(strWorkingSegment)) {
                
                if(workingSegmentId != 0){
                    return VIEW_GET_MANAGE_WORKINGSEGMENT;
                }
                //only show the error message if this is a duplicate entry.
                String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_KEY);
                WorkingSegment newWorkingSegment = new WorkingSegment();
                model.addAttribute(MODEL_ATT_WORKINGSEGMENT, newWorkingSegment);
                model.addAttribute(MESSAGE, message);
                model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
                model.addAttribute(SELECTED_OBJ_ID, workingSegment.getWorkingSegmentId());
                return VIEW_GET_MANAGE_WORKINGSEGMENT;
            } else {
                workingSegment.setDescription(strWorkingSegment);
                
                // If the Working Segment id is zero, it's an new entry.
                if (workingSegmentId == 0) {
                    commonService.createWorkingSegment(workingSegment);
                } else {
                    commonService.updateWorkingSegment(workingSegment);
                }
            }
        }
        return VIEW_POST_MANAGE_WORKINGSEGMENT;
    }
    
    /**
     * Delete working segment.
     * 
     * @param workingSegment - {@link WorkingSegment}.
     * @param request {@link HttpServletRequest}
     * @param model {@link ModelMap}
     * @throws AkuraAppException - Detailed exception
     * @return name of the view.
     */
    @RequestMapping(value = REQ_MAP_VALUE_DELETE, method = RequestMethod.POST)
    public String deleteWorkingSegment(@ModelAttribute(MODEL_ATT_WORKINGSEGMENT) WorkingSegment workingSegment,
            HttpServletRequest request, ModelMap model) throws AkuraAppException {
    
        try {
            commonService.deleteWorkingSegment(workingSegment.getWorkingSegmentId());
        } catch (AkuraAppException e) {
            
            if (e.getCause() instanceof DataIntegrityViolationException) {
                String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_DELETE);
                WorkingSegment newWorkingSegment = new WorkingSegment();
                model.addAttribute(MODEL_ATT_WORKINGSEGMENT, newWorkingSegment);
                model.addAttribute(MESSAGE, message);
                
                return VIEW_GET_MANAGE_WORKINGSEGMENT;
            }
        }
        return VIEW_POST_MANAGE_WORKINGSEGMENT;
    }
    
    
    /**
     * Method is to return WorkingSegment list.
     * 
     * @return list of WorkingSegment
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_WORKINGSEGMENT_LIST)
    public List<WorkingSegment> populateWorkingSegmentList() throws AkuraAppException {
    
        return SortUtil.sortWorkingSegmentList(commonService.getWorkingSegmentList());
    }
}
