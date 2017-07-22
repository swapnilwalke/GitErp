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

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.WarningLevel;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.student.validator.WarningLevelValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * This class handles the requests for domains of the Add Warning Levels and related functions.
 *
 * @author Virtusa Corporation
 */
@Controller
public class WarningLevelController {

    /** string variable for error message description. */
    private static final String ERROR_MSG_WARNINGLEVEL_DESCRIPTION = "REF.UI.RECORD.EXIST.ERROR";

    /** string variable for error message delete. */
    private static final String ERROR_MSG_WARNINGLEVEL_DELETE = "REF.UI.WARNINGLEVEL.DELETE";

    /** view post method student Warning Level. */
    private static final String VIEW_POST_MANAGE_STUDENT_WARNING_LEVEL = "redirect:manageWarningLevel.htm";

    /** view get method student Warning Level. */
    private static final String VIEW_GET_STUDENT_WARNING_LEVEL = "reference/manageWarningLevel";

    /** request mapping value for save or update student Warning Level. */
    private static final String REQ_MAP_VALUE_SAVEORUPDATE = "/manageSaveOrUpdateWarningLevel.htm";

    /** model attribute of student Warning Level. */
    private static final String MODEL_ATT_STUDENT_WARNING_LEVEL = "warningLevel";

    /** model attribute of student Warning Level list. */
    private static final String MODEL_ATT_STUDENT_WARNING_LEVEL_LIST = "warningLevelList";

    /** model attribute of selected sport object. */
    private static final String MODEL_ATT_SELECTED_OBJECT = "selectedObj";
    
    /** model attribute of edit pane add remove. */
    private static final String EDIT_PANE = "editPane";

    /** request mapping value for delete Student Warning Level. */
    private static final String REQ_MAP_VALUE_DELETE = "/manageDeleteWarningLevel.htm";

    /** attribute for holding message. */
    private static final String MESSAGE = "message";

    /** CommonService attribute for holding commonService. */
    private CommonService commonService;

    /**
     * Represents an instance of WarningLevelValidator.
     */
    private WarningLevelValidator warningLevelValidator;

    /**
     * setter method from CommonService.
     *
     * @param commonServiceVal - CommonService
     */

    public void setCommonService(CommonService commonServiceVal) {

        this.commonService = commonServiceVal;
    }

    /**
     * Sets an instance of WarningLevelValidator.
     *
     * @param warningLevelValidatorVal - WarningLevelValidator
     */
    public void setWarningLevelValidator(WarningLevelValidator warningLevelValidatorVal) {

        warningLevelValidator = warningLevelValidatorVal;
    }

    /**
     * Initializes the reference data that is to be previewed on the UI.
     *
     * @param model - a HashMap that contains information of the WarningLevel.
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when processing.
     */

    @RequestMapping(method = RequestMethod.GET)
    public final String addWarningLevel(final ModelMap model) throws AkuraAppException {

        WarningLevel warningLevel = new WarningLevel();
        model.addAttribute(MODEL_ATT_STUDENT_WARNING_LEVEL, warningLevel);
        return VIEW_GET_STUDENT_WARNING_LEVEL;
    }

    /**
     * Method is to return WarningLevel reference data.
     *
     * @throws AkuraAppException - AkuraAppException
     * @return WarningLevelList - warningLevel reference list.
     */

    @ModelAttribute(MODEL_ATT_STUDENT_WARNING_LEVEL_LIST)
    public final List<WarningLevel> populateWarningLevelList() throws AkuraAppException {

        return SortUtil.sortWarningLevelList(commonService.viewAllWarningLevelInfo());

    }

    /**
     * Saves or updates the relevant WarningLevel.
     *
     * @param model - a HashMap that contains information of the WarningLevel
     * @param warningLevel - WarningLevel
     * @param result - containing list of errors and WarningLevel instance to which data was bound
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when saving or updating a PrefectType
     *         instance.
     */

    @RequestMapping(value = REQ_MAP_VALUE_SAVEORUPDATE, method = RequestMethod.POST)
    public final String saveOrUpdateWarningLevel(
            @ModelAttribute(MODEL_ATT_STUDENT_WARNING_LEVEL) final WarningLevel warningLevel, BindingResult result,
            final ModelMap model) throws AkuraAppException {

        int intWarningLevelId = warningLevel.getWarningLevelId();
        WarningLevel selectedObject = null;
        String strMessage = null;

        warningLevelValidator.validate(warningLevel, result);
        if (result.hasErrors()) {
        	
        	selectedObject = commonService.findWarningLevelById(warningLevel.getWarningLevelId());
        	model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObject);
        	model.addAttribute(EDIT_PANE, EDIT_PANE);
            return VIEW_GET_STUDENT_WARNING_LEVEL;
            
        } else {
            if (commonService.isExistWarningLevel(warningLevel)) {

                if (intWarningLevelId != 0) {
                	
                    selectedObject = commonService.findWarningLevelById(intWarningLevelId);
                    model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObject);
                    strMessage = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_WARNINGLEVEL_DESCRIPTION);
                    model.addAttribute(EDIT_PANE, EDIT_PANE);
                    model.addAttribute(MESSAGE, strMessage);
                    
                } else {
                    strMessage = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_WARNINGLEVEL_DESCRIPTION);
                    model.addAttribute(EDIT_PANE, EDIT_PANE);
                    model.addAttribute(MESSAGE, strMessage);
                }
                return VIEW_GET_STUDENT_WARNING_LEVEL;

            } else {
                if (intWarningLevelId == 0) {
                    commonService.addWarningLevelsInfo(warningLevel);
                } else {
                    commonService.editWarningLevelInfo(warningLevel);
                }
            }
            return VIEW_POST_MANAGE_STUDENT_WARNING_LEVEL;
        }

    }

    /**
     * Deletes the relevant WarningLevel object.
     *
     * @param model - a HashMap that contains information of the WarningLevel
     * @param warningLevel - WarningLevel
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when deleting a WarningLevel instance.
     */

    @RequestMapping(REQ_MAP_VALUE_DELETE)
    public final String deleteWarningLevel(final ModelMap model,
            @ModelAttribute(MODEL_ATT_STUDENT_WARNING_LEVEL) final WarningLevel warningLevel) throws AkuraAppException {

        String message;
        try {
            WarningLevel warningLevelObj=commonService.findWarningLevelById(warningLevel.getWarningLevelId());
            commonService.deleteWarningLevelsInfo(warningLevelObj);
            return VIEW_POST_MANAGE_STUDENT_WARNING_LEVEL;
        } catch (AkuraAppException e) {
            if (e.getCause() instanceof DataIntegrityViolationException) {
                message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_WARNINGLEVEL_DELETE);
                WarningLevel newWarningLevel = new WarningLevel();
                model.addAttribute(MESSAGE, message);
                model.addAttribute(MODEL_ATT_STUDENT_WARNING_LEVEL, newWarningLevel);
                return VIEW_GET_STUDENT_WARNING_LEVEL;
            } else {
                throw e;
            }

        }

    }

}
