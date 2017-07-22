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

import com.virtusa.akura.api.dto.PrefectType;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.student.validator.PrefectTypeValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * This class handles the requests for domains of the Add Prefect Types and
 * related functions.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class PrefectTypeController {

	/** string variable for error message descruiption. */
	private static final String ERROR_MSG_PREFECTTYPE_DESCRIPTION = "REF.UI.PREFECTTYPE.DESCRIPTION";

	/** string variable for error message delete. */
	private static final String ERROR_MSG_PREFECTTYPE_DELETE = "REF.UI.PREFECTTYPE.DELETE";

	/** view post method student Prefect Type. */
	private static final String VIEW_POST_MANAGE_STUDENT_PREFECT_TYPE = "redirect:managePrefectType.htm";

	/** view get method student Prefect Type. */
	private static final String VIEW_GET_STUDENT_PREFECT_TYPE = "reference/managePrefectType";

	/** request mapping value for save or update student PrefectType. */
	private static final String REQ_MAP_VALUE_SAVEORUPDATE = "/manageSaveOrUpdatePrefectType.htm";

	/** model attribute of student Prefect Type. */
	private static final String MODEL_ATT_STUDENT_PREFECT_TYPE = "prefectType";

	/** model attribute of student PrefectType list. */
	private static final String MODEL_ATT_STUDENT_PREFECT_TYPE_LIST = "prefectTypeList";

	/** request mapping value for delete Student Prefect Type. */
	private static final String REQ_MAP_VALUE_DELETE = "/manageDeletePrefectType.htm";

	/** request mapping value for add Student Prefect Type. */
	private static final String REQ_MAP_VALUE_ADD = "/managePrefectType.htm";

	/** attribute for holding message. */
	private static final String MESSAGE = "message";

	/** model attribute of selected object. */
	private static final String MODEL_ATT_SELECTED_OBJECT = "selectedObj";

	/** Model attribute of "showEditSection". */
	private static final String SHOW_EDIT_SECTION = "showEditSection";

	/**
	 * Represents an instance of PrefectTypeValidator.
	 */
	private PrefectTypeValidator prefectTypeValidator;

	/** CommonService attribute for holding commonService. */
	private CommonService commonService;

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
	 * Sets an instance of PrefectTypeValidator.
	 * 
	 * @param prefectTypeValidatorVal
	 *            - the relevant instance of PrefectTypeValidator
	 */
	public void setPrefectTypeValidator(
			PrefectTypeValidator prefectTypeValidatorVal) {

		prefectTypeValidator = prefectTypeValidatorVal;
	}

	/**
	 * Method is to return prefectType reference data.
	 * 
	 * @throws AkuraAppException
	 *             - AkuraAppException
	 * @return prefectTypeList - prefectType reference list.
	 */
	@ModelAttribute(MODEL_ATT_STUDENT_PREFECT_TYPE_LIST)
	public final List<PrefectType> populatePrefectTypeList()
			throws AkuraAppException {

		return SortUtil.sortPrefectTypeList(commonService.getPrefectTypeList());
	}

	/**
	 * Initializes the reference data that is to be previewed on the UI.
	 * 
	 * @param model
	 *            - a HashMap that contains information of the PrefectType.
	 * @return - name of the view which is redirected to
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing.
	 */
	@RequestMapping(REQ_MAP_VALUE_ADD)
	public final String addPrefectType(final ModelMap model)
			throws AkuraAppException {

		PrefectType prefectType = new PrefectType();
		model.addAttribute(MODEL_ATT_STUDENT_PREFECT_TYPE, prefectType);
		return VIEW_GET_STUDENT_PREFECT_TYPE;
	}

	/**
	 * Saves or updates the relevant PrefectType.
	 * 
	 * @param model
	 *            - a HashMap that contains information of the PrefectType
	 * @param prefectType
	 *            - an instance of PrefectType
	 * @param result
	 *            - containing list of errors and prefectType instance to which
	 *            data was bound
	 * @return - name of the view which is redirected to
	 * @throws AkuraAppException
	 *             - The exception details that occurred when saving or updating
	 *             a PrefectType instance.
	 */
	@RequestMapping(value = REQ_MAP_VALUE_SAVEORUPDATE, method = RequestMethod.POST)
	public final String saveOrUpdatePerfectType(
			@ModelAttribute(MODEL_ATT_STUDENT_PREFECT_TYPE) final PrefectType prefectType,
			BindingResult result, final ModelMap model)
			throws AkuraAppException {

		PrefectType selectedObject = null;

		prefectTypeValidator.validate(prefectType, result);
		if (result.hasErrors()) {

			selectedObject = commonService.findPrefectTypeById(prefectType.getPrefectTypeId());
			model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObject);

			model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);

			return VIEW_GET_STUDENT_PREFECT_TYPE;
		} else {
			String description = prefectType.getDescription();
			description = description.trim();
			int id = prefectType.getPrefectTypeId();
			try {
				if (id > 0) {
					PrefectType findPrefectType = commonService
							.findPrefectType(id);
					findPrefectType.setDescription(description);
					commonService.updatePrefectType(findPrefectType);
				} else {
					prefectType.setDescription(description);
					commonService.addPrefectType(prefectType);
				}

				return VIEW_POST_MANAGE_STUDENT_PREFECT_TYPE;
			} catch (AkuraAppException e) {
				if (e.getCause() instanceof DataIntegrityViolationException) {

					if(id>0){
					selectedObject = commonService.findPrefectTypeById(id);
					model.addAttribute(MODEL_ATT_SELECTED_OBJECT,selectedObject);
					model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
					}else{
						prefectType.setPrefectTypeId(id);
						model.addAttribute(MODEL_ATT_SELECTED_OBJECT,prefectType);
						model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);	
					}

					prefectTypeMessages(model);
					return VIEW_GET_STUDENT_PREFECT_TYPE;
				} else {
					throw e;
				}
			}
		}

	}

	/**
	 * Deletes the relevant PrefectType object.
	 * 
	 * @param model
	 *            - a HashMap that contains information of the PrefectType
	 * @param prefectType
	 *            - an instance of PrefectType
	 * @return - name of the view which is redirected to
	 * @throws AkuraAppException
	 *             - The exception details that occurred when deleting a
	 *             PrefectType instance.
	 */
	@RequestMapping(REQ_MAP_VALUE_DELETE)
	public final String deletePrefectType(
			final ModelMap model,
			@ModelAttribute(MODEL_ATT_STUDENT_PREFECT_TYPE) final PrefectType prefectType)
			throws AkuraAppException {

		String message;
		try {
			int id = prefectType.getPrefectTypeId();
			PrefectType findPrefectType = commonService.findPrefectType(id);
			commonService.deletePrefectType(findPrefectType);
			return VIEW_POST_MANAGE_STUDENT_PREFECT_TYPE;
		} catch (AkuraAppException e) {
			if (e.getCause() instanceof DataIntegrityViolationException) {
				message = new ErrorMsgLoader()
						.getErrorMessage(ERROR_MSG_PREFECTTYPE_DELETE);
				PrefectType newPrefectType = new PrefectType();
				model.addAttribute(MESSAGE, message);
				model.addAttribute(MODEL_ATT_STUDENT_PREFECT_TYPE,
						newPrefectType);
				return VIEW_GET_STUDENT_PREFECT_TYPE;
			} else {
				throw e;
			}
		}
	}

	/**
	 * prefectType method to be kept error message in model attribute.
	 * 
	 * @param model
	 *            - a HashMap that contains information of the PrefectType
	 */
	private void prefectTypeMessages(final ModelMap model) {

		String message;

		message = new ErrorMsgLoader()
				.getErrorMessage(ERROR_MSG_PREFECTTYPE_DESCRIPTION);
		
		model.addAttribute(MESSAGE, message);

	}

}
