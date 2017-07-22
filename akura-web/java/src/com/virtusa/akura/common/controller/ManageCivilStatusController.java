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

import com.virtusa.akura.api.dto.CivilStatus;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.StaffCommonService;
import com.virtusa.akura.common.validator.CivilStatusValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * This class handles the requests for domains of the CivilStatus related functions.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class ManageCivilStatusController {
    
    /**
     * Represents an instance of CivilStatusValidator.
     */
    private CivilStatusValidator civilStatusValidator;
    
    /** StaffCommonService attribute for holding staffCommonService. */
    private StaffCommonService staffCommonService;
    
    /** view get method manage civilStatus. */
    private static final String VIEW_GET_MANAGE_CIVILSTATUS = "reference/manageCivilStatus";
    
    /** view post method civilStatus. */
    private static final String VIEW_POST_MANAGE_CIVILSTATUS = "redirect:manageCivilStatus.htm";
    
    /** model attribute of CivilStatus . */
    private static final String CIVILSTATUS = "civilStatus";
    
    /** model attribute of CivilStatus List. */
    private static final String MODEL_ATT_CIVILSTATUS_LIST = "civilStatusList";
    
    /** request mapping value for save/update civilStatus. */
    private static final String REQ_MAP_SAVE_UPDATE_CIVILSTATUS = "/manageSaveOrUpdateCivilStatus.htm";
    
    /** request mapping value for manage civilStatus. */
    private static final String REQ_MAP_MANAGE_CIVILSTATUS = "/manageCivilStatus.htm";
    
    /** request mapping value for manage delete civilStatus. */
    private static final String REQ_MAP_DELETE_CIVILSTATUS = "/manageDeleteCivilStatus.htm";
    
    /** attribute for holding message. */
    private static final String MESSAGE = "message";
    
    /** Error message for delete civil status. */
    private static final String ERROR_MSG_CIVILSTATUS_DELETE = "REF.UI.CIVILSTATUS.DELETE";   
   
   
    /** Error message for already exist. */
    private static final String ERROR_MSG_RECORD_EXIST = "REF.UI.RECORD.EXIST.ERROR";
    
    /** attribute for holding space. */
    private static final String SPACE = " ";
    
    /** attribute for holding space. */
    private static final String VALUE= "\\s+";
    
    /** Model attribute of "selectedObjId". */
    private static final String SELECTED_OBJ_ID = "selectedObjId";
    
    /** attribute for holding message. */
    private static final String EDITPANE = "editpane";
   
    /**
     * setter method from StaffCommonService.
     * 
     * @param staffCommonServiceVal - StaffCommonService
     */
    
    public void setStaffCommonService(StaffCommonService staffCommonServiceVal) {

        this.staffCommonService = staffCommonServiceVal;
    }
    
    /**
     * Sets an instance of CivilStatusValidator.
     * 
     * @param civilStatusValidatorVal - the relevant instance of CivilStatusValidator
     */
    public void setCivilStatusValidator(CivilStatusValidator civilStatusValidatorVal) {

        civilStatusValidator = civilStatusValidatorVal;
    }
    
    /**
     * Method is to return CivilStatus reference data.
     * 
     * @return civilStatusList - civilStatus reference list.
     * @throws AkuraAppException - The exception details that occurred when populating a CivilStatus list.
     */
    @ModelAttribute(MODEL_ATT_CIVILSTATUS_LIST)
    public final List<CivilStatus> populateCivilStatusList() throws AkuraAppException {

        return SortUtil.sortCivilStatusList(staffCommonService.getCivilStatusList());
    }
    
    /**
     * Initializes the reference data that is to be loaded on the requested view page.
     * 
     * @param model - a HashMap that contains information of the CivilStatus
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(REQ_MAP_MANAGE_CIVILSTATUS)
    public final String manageCivilStatus(final ModelMap model) throws AkuraAppException {

        CivilStatus civilStatus = (CivilStatus) model.get(CIVILSTATUS);
        
        if (civilStatus == null) {
            civilStatus = new CivilStatus();
        }
        
        model.addAttribute(CIVILSTATUS, civilStatus);
        return VIEW_GET_MANAGE_CIVILSTATUS;
    }
    
    /**
     * NewCivilStatus Create or update CivilStatus object details.
     * 
     * @param civilStatus - CivilStatus object.
     * @param bindingResult - BindingResult
     * @param request - HttpServletRequest
     * @param model - ModelMap
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - if error occurs when save or update a CivilStatus instance.
     */
    
    @RequestMapping(value = REQ_MAP_SAVE_UPDATE_CIVILSTATUS, method = RequestMethod.POST)
    public String saveOrUpdateCivilStatus(@ModelAttribute(CIVILSTATUS) CivilStatus civilStatus,
            BindingResult bindingResult, HttpServletRequest request, ModelMap model) throws AkuraAppException {

        int intCivilStatusId = civilStatus.getCivilStatusId();
        String strMessage = null;
        String strDescription = null;
        
        // validations
        civilStatusValidator.validate(civilStatus, bindingResult);
        
        if (bindingResult.hasErrors()) {
            model.addAttribute(EDITPANE, EDITPANE);
           // int civilStatusid = civilStatus.getCivilStatusId();
            model.addAttribute(SELECTED_OBJ_ID,intCivilStatusId);
            return VIEW_GET_MANAGE_CIVILSTATUS;
            
        } else {
            strDescription = civilStatus.getDescription().trim().replaceAll(VALUE, SPACE);
            strMessage = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_RECORD_EXIST);
            civilStatus.setDescription(strDescription);
            
            // civilStatus already exist.
            if (staffCommonService.isExistsCivilStatus(strDescription)) {
                if (intCivilStatusId == 0) {
                    strMessage = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_RECORD_EXIST);
                    model.addAttribute(EDITPANE, EDITPANE);
                    model.addAttribute(SELECTED_OBJ_ID,intCivilStatusId);
                    model.addAttribute(MESSAGE, strMessage);
                    civilStatus.setDescription(strDescription);
                    return VIEW_GET_MANAGE_CIVILSTATUS;
                    
                } else {
                    if (staffCommonService.findCivilStatus(civilStatus.getCivilStatusId()).getDescription()
                            .compareToIgnoreCase(strDescription) == 0) {
                        
                        if (staffCommonService.findCivilStatus(civilStatus.getCivilStatusId()).getDescription()
                                .equals(strDescription)) {
                            
                            CivilStatus newCivilStatus = new CivilStatus();
                            newCivilStatus = staffCommonService.findCivilStatus(civilStatus.getCivilStatusId());
                            model.addAttribute(CIVILSTATUS, newCivilStatus);
                            
                        } else {
                            
                            staffCommonService.updateCivilStatus(civilStatus);
                            CivilStatus newCivilStatus = new CivilStatus();
                            newCivilStatus = staffCommonService.findCivilStatus(civilStatus.getCivilStatusId());
                            model.addAttribute(CIVILSTATUS, newCivilStatus);
                            
                        }
                        
                    } else {
                        strMessage = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_RECORD_EXIST);
                        model.addAttribute(EDITPANE, EDITPANE);
                        model.addAttribute(SELECTED_OBJ_ID,intCivilStatusId);
                        model.addAttribute(MESSAGE, strMessage);
                        return VIEW_GET_MANAGE_CIVILSTATUS;
                    }
                    
                }
                
            } else {
                if (intCivilStatusId == 0) {
                    staffCommonService.addCivilStatus(civilStatus);
                } else {
                    try {
                        staffCommonService.updateCivilStatus(civilStatus);
                    } catch (AkuraAppException e) {
                        strMessage = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_RECORD_EXIST);
                        model.addAttribute(EDITPANE, EDITPANE);
                        model.addAttribute(SELECTED_OBJ_ID,intCivilStatusId);
                        CivilStatus newCivilStatus = staffCommonService.findCivilStatus(intCivilStatusId);
                        model.addAttribute(CIVILSTATUS, newCivilStatus);
                        model.addAttribute(MESSAGE, strMessage);
                        return VIEW_GET_MANAGE_CIVILSTATUS;
                    }
                }
                
            }
            
        }
        
        return VIEW_POST_MANAGE_CIVILSTATUS;
    }
    
    /**
     * Deletes the relevant CivilStatus object.
     * 
     * @param model - a HashMap that contains information of the CivilStatus
     * @param civilStatus - an instance of CivilStatus
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when deleting a CivilStatus instance.
     */
    @RequestMapping(REQ_MAP_DELETE_CIVILSTATUS)
    public final String deleteCivilStatus(final ModelMap model,
            @ModelAttribute(CIVILSTATUS) final CivilStatus civilStatus) throws AkuraAppException {

        try {
            int id = civilStatus.getCivilStatusId();
            CivilStatus findCivilStatus = staffCommonService.findCivilStatus(id);
            staffCommonService.deleteCivilStatus(findCivilStatus);
            return VIEW_POST_MANAGE_CIVILSTATUS;
        } catch (AkuraAppException e) {
            
            if (e.getCause() instanceof DataIntegrityViolationException) {
                String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_CIVILSTATUS_DELETE);
                CivilStatus newCivilStatus = new CivilStatus();
                model.addAttribute(MESSAGE, message);
                model.addAttribute(CIVILSTATUS, newCivilStatus);
                return VIEW_GET_MANAGE_CIVILSTATUS;
            } else {
                throw e;
            }
        }
    }
    
}
