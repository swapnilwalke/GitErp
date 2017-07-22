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

import com.virtusa.akura.api.dto.Scholarship;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.ScholarshipValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * ManageScholarshipController is to handle Add/Edit/Delete operations for House reference module.
 *
 * @author Virtusa Corporation
 */

@Controller
public class ManageScholarshipController {

    /** view post method manage Scholarship. */
    private static final String VIEW_POST_MANAGE_SCHOLARSHIP = "redirect:manageScholarship.htm";

    /** view get method manage Scholarship. */
    private static final String VIEW_GET_MANAGE_SCHOLARSHIP = "reference/manageScholarship";

    /** model attribute of Scholarship. */
    private static final String MODEL_ATT_SCHOLARSHIP = "scholarship";

    /** model attribute of Scholarship list. */
    private static final String MODEL_ATT_SCHOLARSHIP_LIST = "scholarshipList";

    /** request mapping value for save or update Scholarship. */
    private static final String REQ_MAP_VALUE_SAVEORUPDATE = "/saveOrUpdateScholarship.htm";

    /** request mapping value for delete Scholarship. */
    private static final String REQ_MAP_VALUE_DELETE = "/manageDeleteScholarship.htm";

    /** attribute for holding error message key. */
    private static final String ERROR_MSG_KEY = "REF.UI.SCHOLARSHIP.DESCRIPTION.EXIST";

    /** attribute for holding error message key. */
    private static final String ERROR_MSG_DELETE = "REF.UI.SCHOLARSHIP.DELETE";

    /** attribute for holding message. */
    private static final String MESSAGE = "message";
    
    
    /** Model attribute of "showEditSection". */
    private static final String SHOW_EDIT_SECTION = "showEditSection";
    
    
    /** model attribute of selected  object. */
    private static final String MODEL_ATT_SELECTED_OBJECT = "selectedObj";
    

    /**
     * {@link CommonService}.
     */
    private CommonService commonService;

    /**
     * Repersents an instance of scholarshipValidator {@link ScholarshipValidator}.
     */
    private ScholarshipValidator scholarshipValidator;

    /**
     * Injects an instance of ScholarshipValidator.
     *
     * @param scholarshipValidatorValue - an instance of ScholarshipValidator.
     */
    public void setScholarshipValidator(ScholarshipValidator scholarshipValidatorValue) {

        this.scholarshipValidator = scholarshipValidatorValue;
    }

    
    /**
     * Sets the common service.
     *
     * @param objCommonService the commonService to set
     */
    public void setCommonService(CommonService objCommonService) {
    
        this.commonService = objCommonService;
    }

    /**
     * Returns a newly created Scholarship object.
     *
     * @param model - a HashMap contatins the scholarship related information.
     * @return - the name of the view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showScholarshipDetail(final ModelMap model) {

        Scholarship scholarship = (Scholarship) model.get(MODEL_ATT_SCHOLARSHIP);

        if (scholarship == null) {
            scholarship = new Scholarship();
        }
        model.addAttribute(MODEL_ATT_SCHOLARSHIP, scholarship);

        return VIEW_GET_MANAGE_SCHOLARSHIP;
    }

    /**
     * Saves or updates the Scholarship related information.
     *
     * @param scholarship - {@link Scholarship}.
     * @param request - HttpServletRequest
     * @param model {@link ModelMap}
     * @param bindingResult {@link BindingResult}
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - throw this
     */
    @RequestMapping(value = REQ_MAP_VALUE_SAVEORUPDATE, method = RequestMethod.POST)
    public String saveOrUpdateScholarship(@ModelAttribute(MODEL_ATT_SCHOLARSHIP) final Scholarship scholarship,
            final BindingResult bindingResult, final HttpServletRequest request, final ModelMap model)
            throws AkuraAppException {

        String returnResult = VIEW_GET_MANAGE_SCHOLARSHIP;
        scholarshipValidator.validate(scholarship, bindingResult);

        if (!bindingResult.hasErrors()) {

            final String strScholarship = scholarship.getName().trim();
            try {
                scholarship.setName(strScholarship);

                // If the scholarship id is zero, it's an new entry.
                if (scholarship.getScholarshipId() == 0) {
                    commonService.createScholarship(scholarship);
                } else {
                    commonService.updateScholarship(scholarship);
                }
                returnResult = VIEW_POST_MANAGE_SCHOLARSHIP;
            } catch (AkuraAppException e) {
                if (e.getCause() instanceof DataIntegrityViolationException) {
                    String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_KEY);
                    Scholarship newScholarship = commonService.findScholarshipById(scholarship.getScholarshipId());
                    model.addAttribute(MODEL_ATT_SELECTED_OBJECT, newScholarship);
                    model.addAttribute(MESSAGE, message);
                    model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
                    returnResult = VIEW_GET_MANAGE_SCHOLARSHIP;
                }
                
            } finally {
                scholarship.setScholarshipId(0);
            }
        }
        model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
        return returnResult;
    }

    /**
     * Deletes scholarship from reference module.
     *
     * @param scholarship - {@link Scholarship}.
     * @param request - {@link HttpServletRequest}
     * @param model - {@link ModelMap}
     * @return name of the view.
     * @throws AkuraAppException - Detailed exception
     */
    @RequestMapping(value = REQ_MAP_VALUE_DELETE, method = RequestMethod.POST)
    public String deleteScholarship(@ModelAttribute(MODEL_ATT_SCHOLARSHIP) final Scholarship scholarship,
            final HttpServletRequest request, final ModelMap model) throws AkuraAppException {

        String returnResult = VIEW_POST_MANAGE_SCHOLARSHIP;
        try {
            commonService.deleteScholarship(scholarship.getScholarshipId());
        } catch (AkuraAppException e) {
            if (e.getCause() instanceof DataIntegrityViolationException) {
                final String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_DELETE);
                final Scholarship newScholarship = new Scholarship();
                model.addAttribute(MODEL_ATT_SCHOLARSHIP, newScholarship);
                model.addAttribute(MESSAGE, message);

                returnResult = VIEW_GET_MANAGE_SCHOLARSHIP;
            }
        }
        return returnResult;
    }

    /**
     * Returns a sorted list of scholarship.
     *
     * @return - a list of Scholarship.
     * @throws AkuraAppException - Detailed the exception
     */
    @ModelAttribute(MODEL_ATT_SCHOLARSHIP_LIST)
    public List<Scholarship> populateScholarshipList() throws AkuraAppException {

        return SortUtil.sortScholarshipList(commonService.getScholarshipList());
    }
}
