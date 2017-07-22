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

import com.virtusa.akura.api.dto.MethodOfTravel;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.MethodOfTravelValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * ManageMethodOfTravelController is to handle Add/Edit/Delete operations for
 * MethodOfTravel reference module.
 * 
 * @author Virtusa Corporation
 * 
 */
@Controller
public class ManageMethodOfTravelController {

	/** view post method manage MethodOfTravel. */
	private static final String VIEW_POST_MANAGE_METHODOFTRAVEL = "redirect:manageMethodOfTravel.htm";

	/** view get method manage MethodOfTravel. */
	private static final String VIEW_GET_MANAGE_METHODOFTRAVEL = "reference/manageMethodOfTravel";

	/** model attribute of MethodOfTravel. */
	private static final String MODEL_ATT_METHODOFTRAVEL = "methodOfTravel";

	/** model attribute of MethodOfTravel list. */
	private static final String MODEL_ATT_METHODOFTRAVEL_LIST = "methodOfTravelList";

	/** request mapping value for save or update MethodOfTravel. */
	private static final String REQ_MAP_VALUE_SAVEORUPDATE = "/saveOrUpdateMethodOfTravel.htm";

	/** request mapping value for delete MethodOfTravel. */
	private static final String REQ_MAP_VALUE_DELETE = "/manageDeleteMethodOfTravel.htm";

	/** request attribute for travelId. */
	private static final String REQ_TRAVELID = "travelId";

	/** attribute for holding error message key. */
	private static final String ERROR_MSG_KEY = "REF.UI.METHODOFTRAVEL.DESCRIPTION.EXIST";

	/** attribute for holding error message key. */
	private static final String ERROR_MSG_DELETE = "REF.UI.METHODOFTRAVEL.DELETE";

	/** attribute for holding message. */
	private static final String MESSAGE = "message";

	/** Represents commonService instance of {@link CommonService}. */
	private CommonService commonService;

	/**
	 * Represents methodOfTravelValidator instance of
	 * {@link MethodOfTravelValidator}.
	 */
	private MethodOfTravelValidator methodOfTravelValidator;

	/** model attribute of selected object. */
	private static final String MODEL_ATT_SELECTED_OBJECT = "selectedObj";

	/**
	 * Set the methodOfTravelValidator to inject the validator.
	 * 
	 * @param methodOfTravelValidatorValue
	 *            the methodOfTravelValidator to set
	 */
	public void setMethodOfTravelValidator(
			MethodOfTravelValidator methodOfTravelValidatorValue) {
		this.methodOfTravelValidator = methodOfTravelValidatorValue;
	}

	/**
	 * Sets the common service.
	 * 
	 * @param objCommonService
	 *            the commonService to set
	 */
	public void setCommonService(CommonService objCommonService) {

		this.commonService = objCommonService;
	}

	/**
	 * Handle GET requests for MethodOfTravel_details view.
	 * 
	 * @param model
	 *            - ModelMap
	 * @return the name of the view.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showMethodOfTravelDetail(ModelMap model) {

		MethodOfTravel methodOfTravel = (MethodOfTravel) model
				.get(MODEL_ATT_METHODOFTRAVEL);

		if (methodOfTravel == null) {
			methodOfTravel = new MethodOfTravel();
		}
		model.addAttribute(MODEL_ATT_METHODOFTRAVEL, methodOfTravel);

		return VIEW_GET_MANAGE_METHODOFTRAVEL;
	}

	/**
	 * This method is to Add/Edit MethodOfTravel details.
	 * 
	 * @param methodOfTravel
	 *            - {@link MethodOfTravel}.
	 * @param request
	 *            - {@link HttpServletRequest}
	 * @param model
	 *            - {@link ModelMap}
	 * @param bindingResult
	 *            - {@link BindingResult}
	 * @return name of the view which is redirected to.
	 * @throws AkuraAppException
	 *             - throw this detailed exception
	 */
	@RequestMapping(value = REQ_MAP_VALUE_SAVEORUPDATE, method = RequestMethod.POST)
	public String saveOrUpdateMethodOfTravel(
			@ModelAttribute(MODEL_ATT_METHODOFTRAVEL) MethodOfTravel methodOfTravel,
			BindingResult bindingResult, HttpServletRequest request,
			ModelMap model) throws AkuraAppException {

		MethodOfTravel selectedObj = null;
		methodOfTravelValidator.validate(methodOfTravel, bindingResult);

		if (bindingResult.hasErrors()) {
			int id = methodOfTravel.getTravelId();
			if (id == 0) {
				// to keep add panel when validation error for add new travel
				// method is occurred
				MethodOfTravel newMethodOfTravel = new MethodOfTravel();
				newMethodOfTravel.setTravelMethod(methodOfTravel
						.getTravelMethod());
				model.addAttribute(MODEL_ATT_SELECTED_OBJECT, newMethodOfTravel);
				model.addAttribute(MODEL_ATT_METHODOFTRAVEL, methodOfTravel);
			} else {
				// to keep edit panel when validation error for editing travel
				// method is occurred
				selectedObj = commonService
						.findMethodOfTravelById(methodOfTravel.getTravelId());
				model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
				model.addAttribute(MODEL_ATT_METHODOFTRAVEL, methodOfTravel);
			}
			return VIEW_GET_MANAGE_METHODOFTRAVEL;
		} else {

			String strMethodOfTravel = methodOfTravel.getTravelMethod().trim();
			int methodOfTravelId = methodOfTravel.getTravelId();

			// If the MethodOfTravel is already added, populate a message.
			if (commonService.isExistsMethodOfTravel(strMethodOfTravel)) {

				if (methodOfTravelId == 0) {
					MethodOfTravel newMethodOfTravel = new MethodOfTravel();
					newMethodOfTravel.setTravelMethod(methodOfTravel
							.getTravelMethod());
					model.addAttribute(MODEL_ATT_SELECTED_OBJECT,
							newMethodOfTravel);
					// methodOfTravel
					model.addAttribute(MODEL_ATT_METHODOFTRAVEL,
							newMethodOfTravel);
					String message = new ErrorMsgLoader()
							.getErrorMessage(ERROR_MSG_KEY);

					model.addAttribute(MESSAGE, message);
				} else {

					selectedObj = commonService
							.findMethodOfTravelById(methodOfTravel
									.getTravelId());
					model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
					model.addAttribute(MODEL_ATT_METHODOFTRAVEL, methodOfTravel);
					String message = new ErrorMsgLoader()
							.getErrorMessage(ERROR_MSG_KEY);
					model.addAttribute(MESSAGE, message);
					// MethodOfTravel newMethodOfTravel = new MethodOfTravel();
					model.addAttribute(MODEL_ATT_METHODOFTRAVEL, methodOfTravel);
				}
				return VIEW_GET_MANAGE_METHODOFTRAVEL;
			} else {
				methodOfTravel.setTravelMethod(strMethodOfTravel);

				// If the travelId id is zero, it's an new entry.
				if (methodOfTravelId == 0) {
					commonService.createMethodOfTravel(methodOfTravel);
				} else {
					commonService.updateMethodOfTravel(methodOfTravel);
				}
			}
		}
		return VIEW_POST_MANAGE_METHODOFTRAVEL;
	}

	/**
	 * Delete a travel method from reference module.
	 * 
	 * @param request
	 *            - {@link HttpServletRequest}
	 * @param model
	 *            - {@link ModelMap}
	 * @throws AkuraAppException
	 *             - Detailed exception
	 * @return name of the view.
	 */
	@RequestMapping(value = REQ_MAP_VALUE_DELETE, method = RequestMethod.POST)
	public String deleteMethodOfTravel(HttpServletRequest request,
			ModelMap model) throws AkuraAppException {
		try {
			int methodOfTravelId = Integer.parseInt(request
					.getParameter(REQ_TRAVELID));
			commonService.deleteMethodOfTravel(methodOfTravelId);
		} catch (AkuraAppException e) {
			if (e.getCause() instanceof DataIntegrityViolationException) {
				String message = new ErrorMsgLoader()
						.getErrorMessage(ERROR_MSG_DELETE);
				MethodOfTravel newMethodOfTravel = new MethodOfTravel();
				model.addAttribute(MODEL_ATT_METHODOFTRAVEL, newMethodOfTravel);
				model.addAttribute(MESSAGE, message);

				return VIEW_GET_MANAGE_METHODOFTRAVEL;
			}
		}
		return VIEW_POST_MANAGE_METHODOFTRAVEL;
	}

	/**
	 * Method is to get list of MethodOfTravel.
	 * 
	 * @return list of MethodOfTravel
	 * @throws AkuraAppException
	 *             - Detailed exception
	 */
	@ModelAttribute(MODEL_ATT_METHODOFTRAVEL_LIST)
	public List<MethodOfTravel> populateMethodOfTravelList()
			throws AkuraAppException {
		return SortUtil.sortMethodOfTravelList(commonService
				.getMethodOfTravelList());
	}
}
