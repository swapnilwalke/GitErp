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

import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.AppointmentClassification;
import com.virtusa.akura.api.dto.MethodOfTravel;
import com.virtusa.akura.api.dto.Nationality;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.NationalityValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * This class handles the requests for domains of the Nationality related
 * functions.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class NationalityController {

	/**
	 * Logger to log the exceptions.
	 */
	private static final Logger LOG = Logger
			.getLogger(NationalityController.class);

	/**
	 * Represents an instance of NationalityValidator.
	 */
	private NationalityValidator nationalityValidator;

	/** CommonService attribute for holding commonService. */
	private CommonService commonService;

	/** view get method manage nationality. */
	private static final String VIEW_GET_MANAGE_NATIONALITY = "reference/manageNationality";

	/** view post method nationality. */
	private static final String VIEW_POST_MANAGE_NATIONALITY = "redirect:manageNationality.htm";

	/** model attribute of Nationality . */
	private static final String NATIONALITY = "nationality";

	/** model attribute of Nationality List. */
	private static final String MODEL_ATT_NATIONALITY_LIST = "nationalityList";

	/** request mapping value for save/update nationality. */
	private static final String REQ_MAP_SAVE_UPDATE_NATIONALITY = "/manageSaveOrUpdateNationality.htm";

	/** request mapping value for manage nationality. */
	private static final String REQ_MAP_MANAGE_NATIONALITY = "/manageNationality.htm";

	/** request mapping value for manage delete nationality. */
	private static final String REQ_MAP_DELETE_NATIONALITY = "/manageDeleteNationality.htm";

	/** attribute for holding message. */
	private static final String MESSAGE = "message";

	/** model attribute of selected object. */
	private static final String MODEL_ATT_SELECTED_OBJECT = "selectedObj";

	/** model attribute of id. */
	private static final int ZERO = 0;

	/**
	 * setter method from CommonService.
	 * 
	 * @param commonServiceVal
	 *            - CommonService
	 */

	public void setCommonService(CommonService commonServiceVal) {

		this.commonService = commonServiceVal;
	}

	/**
	 * Sets an instance of NationalityValidator.
	 * 
	 * @param nationalityValidatorVal
	 *            - the relevant instance of NationalityValidator
	 */
	public void setNationalityValidator(
			NationalityValidator nationalityValidatorVal) {

		nationalityValidator = nationalityValidatorVal;
	}

	/**
	 * Method is to return nationality reference data.
	 * 
	 * @throws AkuraAppException
	 *             - AkuraAppException
	 * @return nationalityList - nationality reference list.
	 */
	@ModelAttribute(MODEL_ATT_NATIONALITY_LIST)
	public final List<Nationality> populateNationalityList()
			throws AkuraAppException {

		return SortUtil.sortNationalityList(commonService.getNationalityList());
	}

	/**
	 * Initializes the reference data that is to be loaded on the requested view
	 * page.
	 * 
	 * @param model
	 *            - a HashMap that contains information of the Nationality
	 * @return - name of the view which is redirected to
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing.
	 */
	@RequestMapping(REQ_MAP_MANAGE_NATIONALITY)
	public final String manageNationality(final ModelMap model)
			throws AkuraAppException {

		Nationality nationality = new Nationality();
		model.addAttribute(NATIONALITY, nationality);
		return VIEW_GET_MANAGE_NATIONALITY;
	}

	/**
	 * Saves or updates the Nationality.
	 * 
	 * @param model
	 *            - a HashMap that contains information of the Nationality
	 * @param nationality
	 *            - an instance of Nationality
	 * @param result
	 *            - containing list of errors and nationality instance to which
	 *            data was bound
	 * @return - name of the view which is redirected to
	 * @throws AkuraAppException
	 *             - The exception details that occurred when saving or updating
	 *             a Nationality.
	 */
	@RequestMapping(value = REQ_MAP_SAVE_UPDATE_NATIONALITY, method = RequestMethod.POST)
	public final String saveOrUpdateNationality(
			@ModelAttribute(NATIONALITY) final Nationality nationality,
			BindingResult result, final ModelMap model)
			throws AkuraAppException {

		Nationality selectedObj = null;
		nationalityValidator.validate(nationality, result);

		if (result.hasErrors()) {
			int id = nationality.getNationalityId();
			if (id == 0) {

				Nationality newNationality = new Nationality();
				newNationality.setDescription(nationality.getDescription());
				newNationality.setNationalityId(0);

				model.addAttribute(MODEL_ATT_SELECTED_OBJECT, newNationality);
				model.addAttribute(NATIONALITY, nationality);
			} else {

				selectedObj = commonService.findNationality(nationality
						.getNationalityId());
				model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
				model.addAttribute(NATIONALITY, nationality);
			}
			return VIEW_GET_MANAGE_NATIONALITY;
		} else {
			int id = nationality.getNationalityId();
			String description = nationality.getDescription().trim();
			description = description.replaceAll("( )+", " ");
			boolean isAdd = false;

			// uses when edit the nationality. if the nationality id
			// is greater than 0 that id will be updated.
			try {

				if (id > 0) {
					selectedObj = commonService.findNationality(new Integer(
							nationality.getNationalityId()));
					model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
					model.addAttribute(NATIONALITY, nationality);

					Nationality findNationality = commonService
							.findNationality(new Integer(id));
					findNationality.setDescription(description);
					commonService.updateNationality(findNationality);
				} else {
					isAdd = true;
					nationality.setDescription(description);
					commonService.addNationality(nationality);
				}
				return VIEW_POST_MANAGE_NATIONALITY;
			} catch (AkuraAppException e) {

				if (e.getCause() instanceof DataIntegrityViolationException) {
					String message;
					Nationality newNationality = new Nationality();
					newNationality.setDescription(nationality.getDescription());
					newNationality.setNationalityId(ZERO);

					model.addAttribute(MODEL_ATT_SELECTED_OBJECT,
							newNationality);

					message = new ErrorMsgLoader()
							.getErrorMessage("REF.UI.NATIONALITY.DESCRIPTION");
					model.addAttribute(MESSAGE, message);
					if (! isAdd) {
						selectedObj = commonService.findNationality(nationality
								.getNationalityId());
						model.addAttribute(MODEL_ATT_SELECTED_OBJECT,
								selectedObj);
						model.addAttribute(NATIONALITY, nationality);
					} else {
						nationality.setNationalityId(ZERO);
					}

					model.addAttribute(NATIONALITY, nationality);

					return VIEW_GET_MANAGE_NATIONALITY;
				} else {
					throw e;
				}
			}
		}
	}

	/**
	 * Deletes the relevant Nationality object.
	 * 
	 * @param model
	 *            - a HashMap that contains information of the Nationality
	 * @param nationality
	 *            - an instance of Nationality
	 * @return - name of the view which is redirected to
	 * @throws AkuraAppException
	 *             - The exception details that occurred when deleting a
	 *             Nationality instance.
	 */
	@RequestMapping(REQ_MAP_DELETE_NATIONALITY)
	public final String deleteNationality(final ModelMap model,
			@ModelAttribute(NATIONALITY) final Nationality nationality)
			throws AkuraAppException {

		try {
			int id = nationality.getNationalityId();
			Nationality findNationality = commonService.findNationality(id);
			commonService.deleteNationality(findNationality);
			return VIEW_POST_MANAGE_NATIONALITY;
		} catch (AkuraAppException e) {
			LOG.debug("Database error has occured");

			if (e.getCause() instanceof DataIntegrityViolationException) {
				String message;
				message = new ErrorMsgLoader()
						.getErrorMessage("REF.UI.NATIONALITY.DELETE");
				Nationality newNationality = new Nationality();
				model.addAttribute(MESSAGE, message);
				model.addAttribute(NATIONALITY, newNationality);
				return VIEW_GET_MANAGE_NATIONALITY;
			} else {
				throw e;
			}
		}
	}

}
