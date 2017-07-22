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

import com.virtusa.akura.api.dto.Religion;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.ReligionValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * ManageReligionController is to handle Add/Edit/Delete operations for Religion
 * reference module.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class ManageReligionController {

	/** view post method manage religion. */
	private static final String VIEW_POST_MANAGE_RELIGION = "redirect:manageReligion.htm";

	/** view get method manage religion. */
	private static final String VIEW_GET_MANAGE_RELIGION = "reference/createReligion";

	/** model attribute of religion. */
	private static final String MODEL_ATT_RELIGION = "religion";

	/** model attribute of religion list. */
	private static final String MODEL_ATT_RELIGION_LIST = "religionList";

	/** request mapping value for save or update religion. */
	private static final String REQ_MAP_VALUE_SAVEORUPDATE = "/saveOrUpdateReligion.htm";

	/** request mapping value for delete religion. */
	private static final String REQ_MAP_VALUE_DELETE = "/manageDeleteReligion.htm";

	/** request attribute for religionId. */
	private static final String REQ_RELIGIONID = "religionId";

	/** attribute for holding error message key. */
	private static final String ERROR_MSG_KEY = "REF.UI.RELIGION.DESCRIPTION.EXIST";

	/** attribute for holding error message key. */
	private static final String ERROR_MSG_DELETE = "REF.UI.RELIGION.DELETE";

	/** attribute for holding message. */
	private static final String MESSAGE = "message";

	/** model attribute of selected object. */
	private static final String MODEL_ATT_SELECTED_OBJECT = "selectedObj";

	/**
	 * {@link CommonService}.
	 */
	private CommonService commonService;

	/**
	 * {@link ReligionValidator}.
	 */
	private ReligionValidator religionValidator;

	/**
	 * @param commonServiceVlaue
	 *            the commonService to set
	 */
	public void setCommonService(CommonService commonServiceVlaue) {

		this.commonService = commonServiceVlaue;
	}

	/**
	 * @param religionValidatorValue
	 *            the religionValidator to set
	 */
	public void setReligionValidator(ReligionValidator religionValidatorValue) {

		this.religionValidator = religionValidatorValue;
	}

	/**
	 *
	 */
	public ManageReligionController() {

	}

	/**
	 * handle GET requests for religion_details view.
	 * 
	 * @param model
	 *            - ModelMap
	 * @return the name of the view.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showReligionDetail(ModelMap model) {

		Religion religion = (Religion) model.get(MODEL_ATT_RELIGION);

		if (religion == null) {
			religion = new Religion();
		}
		model.addAttribute(MODEL_ATT_RELIGION, religion);

		return VIEW_GET_MANAGE_RELIGION;
	}

	/**
	 * This is to manage Add and Edit religion details.
	 * 
	 * @param religion
	 *            - {@link Religion}.
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
	public String saveOrUpdateReligion(HttpServletRequest request,
			ModelMap model,
			@ModelAttribute(MODEL_ATT_RELIGION) Religion religion,
			BindingResult bindingResult) throws AkuraAppException {

		Religion selectedObj = null;
		religionValidator.validate(religion, bindingResult);

		if (bindingResult.hasErrors()) {
			int id = religion.getReligionId();
			if (id == 0) {
				// to keep add panel when validation error occurred
				Religion newReligion = new Religion();
				newReligion.setDescription(religion.getDescription());
				model.addAttribute(MODEL_ATT_SELECTED_OBJECT, newReligion);
				model.addAttribute(MODEL_ATT_RELIGION, religion);
			} else {
				// to keep edit panel when validation error is occurred
				selectedObj = commonService.findReligionById(religion
						.getReligionId());
				model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
				model.addAttribute(MODEL_ATT_RELIGION, religion);
			}

			return VIEW_GET_MANAGE_RELIGION;

		} else {
			String strReligion = religion.getDescription().trim();

			if (commonService.isExistsReligion(strReligion,
					religion.getReligionId())) {

				if (religion.getReligionId() != 0
						&& (commonService.findReligionById(
								religion.getReligionId()).getDescription()
								.equals(religion.getDescription()))) {
					commonService.updateReligion(religion);
				} else {
					Religion newReligion = new Religion();
					newReligion.setDescription(religion.getDescription());
					newReligion.setReligionId(religion.getReligionId());
					model.addAttribute(MODEL_ATT_SELECTED_OBJECT, newReligion);
					model.addAttribute(MODEL_ATT_RELIGION, religion);
					String message = new ErrorMsgLoader()
							.getErrorMessage(ERROR_MSG_KEY);
					model.addAttribute(MESSAGE, message);
					return VIEW_GET_MANAGE_RELIGION;
				}

			} else {
				religion.setDescription(strReligion);

				// If the religion id is zero, it's an new entry.
				if (religion.getReligionId() == 0) {
					commonService.createReligion(religion);
				} else {
					commonService.updateReligion(religion);
				}
			}
		}
		return VIEW_POST_MANAGE_RELIGION;
	}

	/**
	 * This method handles delete religion.
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
	public String deleteReligion(HttpServletRequest request, ModelMap model)
			throws AkuraAppException {

		try {
			int religionId = Integer.parseInt(request
					.getParameter(REQ_RELIGIONID));
			commonService.deleteReligion(religionId);
		} catch (AkuraAppException e) {
			if (e.getCause() instanceof DataIntegrityViolationException) {
				String message = new ErrorMsgLoader()
						.getErrorMessage(ERROR_MSG_DELETE);
				Religion newReligion = new Religion();
				model.addAttribute(MODEL_ATT_RELIGION, newReligion);
				model.addAttribute(MESSAGE, message);

				return VIEW_GET_MANAGE_RELIGION;
			}
		}
		return VIEW_POST_MANAGE_RELIGION;
	}

	/**
	 * Method is to get religion list.
	 * 
	 * @return list of religion
	 * @throws AkuraAppException
	 *             - Detailed exception
	 */
	@ModelAttribute(MODEL_ATT_RELIGION_LIST)
	public List<Religion> populateReligionList() throws AkuraAppException {

		return SortUtil.sortReligionList(commonService.getReligionList());
	}
}
