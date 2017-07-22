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

import com.virtusa.akura.api.dto.ProfessionalQualification;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.StaffCommonService;
import com.virtusa.akura.common.validator.ProfessionalQualificationValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * ManageProfessionalQualificationController is to handle Add/Edit/Delete/View
 * operations for ManageProfessionalQualification reference module.
 * 
 * @author Virtusa Corporation
 * 
 */
@Controller
public class ManageProfessionalQualificationController {

	/** view post method manage professionalQualification. */
	private static final String VIEW_POST_MANAGE_PROFESSIONALQUALIFICATION = "redirect:manageProfessionalQualification.htm";

	/** view get method manage professionalQualification. */
	private static final String VIEW_GET_MANAGE_PROFESSIONALQUALIFICATION = "reference/manageProfessionalQualification";

	/** model attribute of professionalQualification. */
	private static final String MODEL_ATT_PROFESSIONALQUALIFICATION = "professionalQualification";

	/** model attribute of professionalQualification list. */
	private static final String MODEL_ATT_PROFESSIONALQUALIFICATION_LIST = "professionalQualificationList";
	
	/** model attribute of showPanel. */
	private static final String MODEL_ATT_SHOW_PANEL = "showPanel";

	/** request mapping value for save or update professionalQualification. */
	private static final String REQ_MAP_VALUE_SAVEORUPDATE = "/saveOrUpdateProfessionalQualification.htm";

	/** request mapping value for delete professionalQualification. */
	private static final String REQ_MAP_VALUE_DELETE = "/manageDeleteProfessionalQualification.htm";

	/** request attribute for professionalQualificationId. */
	private static final String REQ_PROFESSIONALQUALIFICATIONID = "professionalQualificationId";

	/** attribute for holding error message key. */
	private static final String ERROR_MSG_KEY = "REF.UI.PROFESSIONALQUALIFICATION.DESCRIPTION.EXIST";

	/** attribute for holding error message key. */
	private static final String ERROR_MSG_DELETE = "REF.UI.PROFESSIONALQUALIFICATION.DELETE";

	/** attribute for holding message. */
	private static final String MESSAGE = "message";
	
	/** String constant TRUE. */
	private static final String TRUE = "TRUE";

	/** Represents staffCommonService - {@link StaffCommonService}. */
	private StaffCommonService staffCommonService;

	/**
	 * Represents professionalQualificationValidator instance of
	 * {@link ProfessionalQualificationValidator}.
	 */
	private ProfessionalQualificationValidator professionalQualificationValidator;

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
	 * Set the professionalQualificationValidator to inject the validator.
	 * 
	 * @param professionalQualificationValidatorValue
	 *            the professionalQualificationValidator to set
	 */
	public void setProfessionalQualificationValidator(
			ProfessionalQualificationValidator professionalQualificationValidatorValue) {
		this.professionalQualificationValidator = professionalQualificationValidatorValue;
	}

	/**
	 * Handle GET requests for professionalQualification_details view.
	 * 
	 * @param model
	 *            - ModelMap
	 * @return the name of the view.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showProfessionalQualificationDetail(ModelMap model) {

		ProfessionalQualification professionalQualification = (ProfessionalQualification) model
				.get(MODEL_ATT_PROFESSIONALQUALIFICATION);

		if (professionalQualification == null) {
			professionalQualification = new ProfessionalQualification();
		}
		model.addAttribute(MODEL_ATT_PROFESSIONALQUALIFICATION,
				professionalQualification);

		return VIEW_GET_MANAGE_PROFESSIONALQUALIFICATION;
	}

	/**
	 * This method handles Add/Edit EducationalQualification details.
	 * 
	 * @param professionalQualification
	 *            - {@link professionalQualification}.
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
	public String saveOrUpdateprofessionalQualification(
			@ModelAttribute(MODEL_ATT_PROFESSIONALQUALIFICATION) ProfessionalQualification professionalQualification,
			BindingResult bindingResult, HttpServletRequest request,
			ModelMap model) throws AkuraAppException {

		professionalQualificationValidator.validate(professionalQualification,
				bindingResult);

		if (bindingResult.hasErrors()) {
			model.addAttribute(MODEL_ATT_PROFESSIONALQUALIFICATION, professionalQualification);
			model.addAttribute(MODEL_ATT_SHOW_PANEL, TRUE);
			return VIEW_GET_MANAGE_PROFESSIONALQUALIFICATION;
		} else {

			String strProfessionalQualification = professionalQualification
					.getDescription().trim();
			int professionalQualificationId = professionalQualification
					.getProfessionalQualificationId();
			/*
			 * in this if statement 2nd part is checking the record that was
			 * created in order to avoid the error message which comes when
			 * saving the same record without editing it and the other part is
			 * checking the professional qualification is already exist, display
			 * a message.
			 */
			if (staffCommonService.isExistsProfessionalQualification(strProfessionalQualification)
					&& ((staffCommonService.findProfessionalQualificationById(professionalQualificationId) == null)
					|| ((staffCommonService.findProfessionalQualificationById(professionalQualificationId) != null)
					&& ! staffCommonService.findProfessionalQualificationById(professionalQualificationId)
							.getDescription().equals(strProfessionalQualification)))) {
				
				String message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_KEY);
				model.addAttribute(MESSAGE, message);
				model.addAttribute(MODEL_ATT_PROFESSIONALQUALIFICATION, professionalQualification);
				model.addAttribute(MODEL_ATT_SHOW_PANEL, TRUE);
				
				return VIEW_GET_MANAGE_PROFESSIONALQUALIFICATION;
				
			} else {
				
				professionalQualification.setDescription(strProfessionalQualification);

				// If the professional qualification id is zero, it's an new
				// entry.
				if (professionalQualificationId == 0) {
				    staffCommonService.createProfessionalQualification(professionalQualification);
				} else {
				    staffCommonService.updateProfessionalQualification(professionalQualification);
				}
			}
		}
		return VIEW_POST_MANAGE_PROFESSIONALQUALIFICATION;
	}

	/**
	 * Delete professional qualification.
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
	public String deleteProfessionalQualification(HttpServletRequest request,
			ModelMap model) throws AkuraAppException {
		try {
			int professionalQualificationId = Integer.parseInt(request
					.getParameter(REQ_PROFESSIONALQUALIFICATIONID));
			staffCommonService
					.deleteProfessionalQualification(professionalQualificationId);
		} catch (AkuraAppException e) {
			if (e.getCause() instanceof DataIntegrityViolationException) {
				String message = new ErrorMsgLoader()
						.getErrorMessage(ERROR_MSG_DELETE);
				ProfessionalQualification newProfessionalQualification = new ProfessionalQualification();
				model.addAttribute(MODEL_ATT_PROFESSIONALQUALIFICATION,
						newProfessionalQualification);
				model.addAttribute(MESSAGE, message);

				return VIEW_GET_MANAGE_PROFESSIONALQUALIFICATION;
			}
		}
		return VIEW_POST_MANAGE_PROFESSIONALQUALIFICATION;
	}

	/**
	 * Method is to return professionalQualification list.
	 * 
	 * @return list of professionalQualification
	 * @throws AkuraAppException
	 *             - Detailed exception
	 */
	@ModelAttribute(MODEL_ATT_PROFESSIONALQUALIFICATION_LIST)
	public List<ProfessionalQualification> populateProfessionalQualificationList()
			throws AkuraAppException {

		return SortUtil.sortProfessionalQualificationList(staffCommonService
				.getProfessionalQualifications());
	}
}
