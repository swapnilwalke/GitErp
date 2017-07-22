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

import com.virtusa.akura.api.dto.EducationalQualification;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.StaffCommonService;
import com.virtusa.akura.common.validator.EducationalQualificationValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * ManageEducationalQualificationController is to handle Add/Edit/Delete/View
 * operations for ManageEducationalQualification reference module.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class ManageEducationalQualificationController {

	/** view post method manage EducationalQualification. */
	private static final String VIEW_POST_MANAGE_EDUCATIONALQUALIFICATION = "redirect:manageEducationalQualification.htm";

	/** view get method manage EducationalQualification. */
	private static final String VIEW_GET_MANAGE_EDUCATIONALQUALIFICATION = "reference/manageEducationalQualification";

	/** model attribute of EducationalQualification. */
	private static final String MODEL_ATT_EDUCATIONALQUALIFICATION = "educationalQualification";

	/** model attribute of EducationalQualification list. */
	private static final String MODEL_ATT_EDUCATIONALQUALIFICATION_LIST = "educationalQualificationList";
	
	/** model attribute of showPanel. */
	private static final String MODEL_ATT_SHOW_PANEL = "showPanel";

	/** request mapping value for save or update EducationalQualification. */
	private static final String REQ_MAP_VALUE_SAVEORUPDATE = "/saveOrUpdateEducationalQualification.htm";

	/** request mapping value for delete EducationalQualification. */
	private static final String REQ_MAP_VALUE_DELETE = "/manageDeleteEducationalQualification.htm";

	/** request attribute for positionId. */
	private static final String REQ_EDUCATIONALQUALIFICATIONID = "educationalQualificationId";

	/** attribute for holding error message key. */
	private static final String ERROR_MSG_KEY = "REF.UI.EDUCATIONALQUALIFICATION.DESCRIPTION.EXIST";

	/** attribute for holding error message key. */
	private static final String ERROR_MSG_DELETE = "REF.UI.EDUCATIONALQUALIFICATION.DELETE";

	/** attribute for holding message. */
	private static final String MESSAGE = "message";
	
	/** String constant TRUE. */
	private static final String TRUE = "TRUE";

	/** Represents staffCommonService - {@link StaffCommonService}. */
	private StaffCommonService staffCommonService;

	/**
	 * Represents educationalQualificationValidator instance of
	 * {@link EducationalQualificationValidator}.
	 */
	private EducationalQualificationValidator educationalQualificationValidator;

	/**
	 * Set the staffCommonService to inject the service.
	 * 
	 * @param staffCommonServiceVlaue
	 *            the staffCommonService to set
	 */
	public void setStaffCommonService(StaffCommonService staffCommonServiceVlaue) {

		this.staffCommonService = staffCommonServiceVlaue;
	}

	/**
	 * Set the educationalQualificationValidator to inject the validator.
	 * 
	 * @param educationalQualificationValidatorValue
	 *            the educationalQualificationValidator to set
	 */
	public void setEducationalQualificationValidator(
			EducationalQualificationValidator educationalQualificationValidatorValue) {

		this.educationalQualificationValidator = educationalQualificationValidatorValue;
	}

	/**
	 * Handle GET requests for EducationalQualification_details view.
	 * 
	 * @param model
	 *            - ModelMap
	 * @return the name of the view.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showEducationalQualificationDetail(ModelMap model) {

		EducationalQualification educationalQualification = (EducationalQualification) model
				.get(MODEL_ATT_EDUCATIONALQUALIFICATION);

		if (educationalQualification == null) {
			educationalQualification = new EducationalQualification();
		}
		model.addAttribute(MODEL_ATT_EDUCATIONALQUALIFICATION,
				educationalQualification);

		return VIEW_GET_MANAGE_EDUCATIONALQUALIFICATION;
	}

	/**
	 * This method handles Add/Edit EducationalQualification details.
	 * 
	 * @param educationalQualification
	 *            - {@link EducationalQualification}.
	 * @param request
	 *            - HttpServletRequest
	 * @param model
	 *            {@link ModelMap}
	 * @param bindingResult
	 *            {@link BindingResult}
	 * @return name of the view which is redirected to.
	 * @throws AkuraAppException
	 *             - throw this
	 */
	@RequestMapping(value = REQ_MAP_VALUE_SAVEORUPDATE, method = RequestMethod.POST)
	public String saveOrUpdateEducationalQualification(
			@ModelAttribute(MODEL_ATT_EDUCATIONALQUALIFICATION) EducationalQualification educationalQualification,
			BindingResult bindingResult, HttpServletRequest request,
			ModelMap model) throws AkuraAppException {

		educationalQualificationValidator.validate(educationalQualification,
				bindingResult);

		if (bindingResult.hasErrors()) {
			model.addAttribute(MODEL_ATT_EDUCATIONALQUALIFICATION, educationalQualification);
			model.addAttribute(MODEL_ATT_SHOW_PANEL, TRUE);
			return VIEW_GET_MANAGE_EDUCATIONALQUALIFICATION;
		} else {

			String strEducationalQualification = educationalQualification
					.getDescription().trim();
			int educationalQualificationId = educationalQualification
					.getEducationalQualificationId();

			/*
			 * in this if statement first part is checking the record that
			 * was created in order to avoid the error message which comes when
			 * saving the same record without editing it
			 * and the other part is checking the educational
			 * qualification is already exist, display a message.
			 */
			
			if (staffCommonService.isExistsEducationalQualification(strEducationalQualification)
					&& ((staffCommonService.findEducationalQualificationById(educationalQualificationId) == null)
					|| ((staffCommonService.findEducationalQualificationById(educationalQualificationId) != null)
					&& ! staffCommonService.findEducationalQualificationById(educationalQualificationId)
							.getDescription().equals(strEducationalQualification)))) {
				
				String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_KEY);
				model.addAttribute(MESSAGE, message);
				model.addAttribute(MODEL_ATT_EDUCATIONALQUALIFICATION, educationalQualification);
				model.addAttribute(MODEL_ATT_SHOW_PANEL, TRUE);
				
				return VIEW_GET_MANAGE_EDUCATIONALQUALIFICATION;
				
			} else {
				
				educationalQualification.setDescription(strEducationalQualification);

				// If the educational qualification id is zero, it's an new
				// entry.
				if (educationalQualificationId == 0) {
				    staffCommonService.createEducationalQualification(educationalQualification);
				} else {
				    staffCommonService.updateEducationalQualification(educationalQualification);
				}
			}
		}
		return VIEW_POST_MANAGE_EDUCATIONALQUALIFICATION;
	}

	/**
	 * Delete a educational qualification.
	 * 
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param model
	 *            {@link ModelMap}
	 * @throws AkuraAppException
	 *             - Detailed exception
	 * @return name of the view.
	 */
	@RequestMapping(value = REQ_MAP_VALUE_DELETE, method = RequestMethod.POST)
	public String deleteEducationalQualification(HttpServletRequest request,
			ModelMap model) throws AkuraAppException {

		try {
			int educationalQualificationId = Integer.parseInt(request
					.getParameter(REQ_EDUCATIONALQUALIFICATIONID));
			staffCommonService
					.deleteEducationalQualification(educationalQualificationId);
		} catch (AkuraAppException e) {
			if (e.getCause() instanceof DataIntegrityViolationException) {
				String message = new ErrorMsgLoader()
						.getErrorMessage(ERROR_MSG_DELETE);
				EducationalQualification newEducationalQualification = new EducationalQualification();
				model.addAttribute(MODEL_ATT_EDUCATIONALQUALIFICATION,
						newEducationalQualification);
				model.addAttribute(MESSAGE, message);

				return VIEW_GET_MANAGE_EDUCATIONALQUALIFICATION;
			}
		}
		return VIEW_POST_MANAGE_EDUCATIONALQUALIFICATION;
	}

	
	

	/**
	 * Method is to return EducationalQualification list.
	 * 
	 * @return list of EducationalQualification
	 * @throws AkuraAppException
	 *             - Detailed exception
	 */
	@ModelAttribute(MODEL_ATT_EDUCATIONALQUALIFICATION_LIST)
	public List<EducationalQualification> populateEducationalQualificationList()
			throws AkuraAppException {

		return SortUtil.sortEducationalQualificationList(staffCommonService
				.getEducationalQualifications());
	}
}
