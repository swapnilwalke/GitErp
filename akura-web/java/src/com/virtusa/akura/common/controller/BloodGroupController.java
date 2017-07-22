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

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.BloodGroup;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.BloodGroupValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * This class handles the requests for domains of the BloodGroup and the Nationality related functions.
 *
 * @author Virtusa Corporation
 */
@Controller
public class BloodGroupController {

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(BloodGroupController.class);

    /**
     * Represents an instance of BloodGroupValidator.
     */
    private BloodGroupValidator bloodGroupValidator;

    /** CommonService attribute for holding commonService. */
    private CommonService commonService;

    /** view get method manage blood group. */
    private static final String VIEW_GET_MANAGE_BLOOD_GROUP = "reference/manageBloodGroup";

    /** view post method blood group. */
    private static final String VIEW_POST_MANAGE_BLOOD_GROUP = "redirect:manageBloodGroup.htm";

    /** model attribute of Blood Group . */
    private static final String BLOOD_GROUP = "bloodGroup";

    /** model attribute of BloodGroup List. */
    private static final String MODEL_ATT_BLOOD_GROUP_LIST = "bloodGroupList";

    /** request mapping value for manage blood group. */
    private static final String REQ_MAP_MANAGE_BLOOD_GROUP = "/manageBloodGroup.htm";

    /** request mapping value for manage delete blood group. */
    private static final String REQ_MAP_DELETE_BLOOD_GROUP = "/manageDeleteBloodGroup.htm";

    /** request mapping value for save/update blood group. */
    private static final String REQ_MAP_SAVE_UPDATE_BLOOD_GROUP = "/manageSaveOrUpdateBloodGroup.htm";

    /** attribute for holding message. */
    private static final String MESSAGE = "message";

    /**
     * setter method from CommonService.
     *
     * @param commonServiceVal - CommonService
     */

    public void setCommonService(CommonService commonServiceVal) {

        this.commonService = commonServiceVal;
    }

    /**
     * Sets an instance of BloodGroupValidator.
     *
     * @param bloodGroupValidatorVal - the relevant instance of BloodGroupValidator
     */
    public void setBloodGroupValidator(BloodGroupValidator bloodGroupValidatorVal) {

        bloodGroupValidator = bloodGroupValidatorVal;
    }

    /**
     * Method is to return bloodGroup reference data.
     *
     * @throws AkuraAppException - AkuraAppException
     * @return bloodGroupList - bloodGroup reference list.
     */
    @ModelAttribute(MODEL_ATT_BLOOD_GROUP_LIST)
    public final List<BloodGroup> populateBloodGroupList() throws AkuraAppException {

        return SortUtil.sortBloodGroupList(commonService.getBloodGroupList());
    }

    /**
     * Initializes the reference data that is to be previewed on the UI.
     *
     * @param model - a HashMap that contains information of the BloodGroup
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(REQ_MAP_MANAGE_BLOOD_GROUP)
    public final String manageBloodGroup(final ModelMap model) throws AkuraAppException {

        BloodGroup bloodGroup = new BloodGroup();
        model.addAttribute(BLOOD_GROUP, bloodGroup);
        return VIEW_GET_MANAGE_BLOOD_GROUP;
    }

    /**
     * Saves or updates the relevant BloodGroup.
     *
     * @param model - a HashMap that contains information of the BloodGroup
     * @param bloodGroup - an instance of BloodGroup
     * @param result - containing list of errors and bloodGroup instance to which data was bound
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when saving or updating a BloodGroup
     *         instance.
     */
    @RequestMapping(value = REQ_MAP_SAVE_UPDATE_BLOOD_GROUP, method = RequestMethod.POST)
    public final String saveOrUpdateBloodGroup(@ModelAttribute(BLOOD_GROUP) final BloodGroup bloodGroup,
            BindingResult result, final ModelMap model) throws AkuraAppException {

        bloodGroupValidator.validate(bloodGroup, result);

        if (result.hasErrors()) {
            return VIEW_GET_MANAGE_BLOOD_GROUP;
        } else {
            String description = bloodGroup.getDescription();
            description = description.trim().toUpperCase();
            int id = bloodGroup.getBloodGroupId();

            // uses when edit the bloodGroup. If the bloodGroup id is
            // greater than 0 update this object.
            try {

                if (id > 0) {

                    // finds the bloodGroup with the given key
                    BloodGroup findBloodGroup = commonService.findBloodGroup(id);
                    // sets the description to this retrieved bloodGroup object.
                    findBloodGroup.setDescription(description);
                    commonService.updateBloodGroup(findBloodGroup);
                } else {
                    bloodGroup.setDescription(description);
                    bloodGroup.setModifiedTime(new Date());
                    commonService.addBloodGroup(bloodGroup);
                }

                return VIEW_POST_MANAGE_BLOOD_GROUP;
            } catch (AkuraAppException e) {

                if (e.getCause() instanceof DataIntegrityViolationException) {

                    String message;
                    message = new ErrorMsgLoader().getErrorMessage("REF.UI.BLOODGROUP.DESCRIPTION");
                    BloodGroup newBloodGroup = new BloodGroup();
                    model.addAttribute(BLOOD_GROUP, newBloodGroup);
                    model.addAttribute(MESSAGE, message);
                    return VIEW_GET_MANAGE_BLOOD_GROUP;
                } else {
                    throw e;
                }
            }
        }
    }

    /**
     * Deletes the relevant BloodGroup object.
     *
     * @param model - a HashMap that contains information of the BloodGroup
     * @param bloodGroup - an instance of BloodGroup
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when deleting a BloodGroup instance.
     */
    @RequestMapping(REQ_MAP_DELETE_BLOOD_GROUP)
    public final String deleteBloodGroup(final ModelMap model, @ModelAttribute(BLOOD_GROUP) final BloodGroup bloodGroup)
            throws AkuraAppException {

        try {
            int id = bloodGroup.getBloodGroupId();
            BloodGroup findBloodGroup = commonService.findBloodGroup(id);
            commonService.deleteBloodGroup(findBloodGroup);
            return VIEW_POST_MANAGE_BLOOD_GROUP;
        } catch (AkuraAppException e) {

            if (e.getCause() instanceof DataIntegrityViolationException) {
                String message;
                message = new ErrorMsgLoader().getErrorMessage("REF.UI.BLOODGROUP.DELETE");
                BloodGroup newBloodGroup = new BloodGroup();
                model.addAttribute(MESSAGE, message);
                model.addAttribute(BLOOD_GROUP, newBloodGroup);
                return VIEW_GET_MANAGE_BLOOD_GROUP;
            } else {
                throw e;
            }
        }
    }

}
