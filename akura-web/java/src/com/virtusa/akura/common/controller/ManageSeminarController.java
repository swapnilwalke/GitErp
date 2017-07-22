/*
 * © 2011 Virtusa(Pvt)Ltd. All rights reserved. CONFIDENTIAL AND

 * PROPRIETARY INFORMATION The information contained herein (the

 * 'Proprietary Information') is highly confidential and proprietary to and

 * constitutes trade secrets of Virtusa(Pvt)Ltd. The Proprietary Information

 * for Virtusa(Pvt)Ltd internal use only and shall not be published,

 * communicated, disclosed or divulged to any person, firm, corporation or

 * other legal entity, directly or indirectly, without the prior written

 * consent of Virtusa(Pvt)Ltd Information Management.
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

import com.virtusa.akura.api.dto.Seminar;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.SeminarValidator;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.SortUtil;

/**
 * ManageSeminarController is to handle Add/Edit/Delete operations for Seminar
 * reference module.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class ManageSeminarController {

	/** view post method manage Seminar. */
	private static final String VIEW_POST_MANAGE_SEMINAR = "redirect:manageSeminar.htm";

	/** view get method manage Seminar. */
	private static final String VIEW_GET_MANAGE_SEMINAR = "reference/manageSeminar";

	/** model attribute of Seminar. */
	private static final String MODEL_ATT_SEMINAR = "seminar";

	/** model attribute of Seminar list. */
	private static final String MODEL_ATT_SEMINAR_LIST = "seminarList";

	/** model attribute of showPanel. */
	private static final String MODEL_ATT_SHOW_PANEL = "showPanel";

	/** request mapping value for save or update Seminar. */
	private static final String REQ_MAP_VALUE_SAVEORUPDATE = "/saveOrUpdateSeminar.htm";

	/** request mapping value for delete Seminar. */
	private static final String REQ_MAP_VALUE_DELETE = "/manageDeleteSeminar.htm";

	/** request attribute for SeminarId. */
	private static final String REQ_SEMINARID = "seminarId";

	/** attribute for holding error message key. */
	private static final String ERROR_MSG_KEY = "REF.UI.SEMINAR.DESCRIPTION.EXIST";

	/** attribute for holding error message key. */
	private static final String ERROR_MSG_DELETE = "REF.UI.SEMINAR.DELETE";

	/** attribute for holding message. */
	private static final String MESSAGE = "message";

	/** Holds studentService instance of {@link StudentService}. */
	private CommonService commonService;

	/** String constant TRUE. */
	private static final String TRUE = "TRUE";

	/** Holds SeminarValidator object {@link SeminarValidator}. */
	private SeminarValidator seminarValidator;

	/**
	 * Set the common Service to inject the service.
	 * 
	 * @param commonServiceValue
	 *            the commonService to set
	 */
	public void setCommonService(CommonService commonServiceValue) {

		this.commonService = commonServiceValue;
	}

	/**
	 * Set the SeminarValidator to inject the validator.
	 * 
	 * @param seminarValidatorValue
	 *            the SeminarValidator to set
	 */
	public void setSeminarValidator(SeminarValidator seminarValidatorValue) {

		this.seminarValidator = seminarValidatorValue;
	}

	/**
	 * Handle GET requests for Seminar_details view.
	 * 
	 * @param model
	 *            - {@link ModelMap}
	 * @return the name of the view.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showSeminarDetail(ModelMap model) {

		Seminar seminar = (Seminar) model.get(MODEL_ATT_SEMINAR);

		if (seminar == null) {
			seminar = new Seminar();
		}
		model.addAttribute(MODEL_ATT_SEMINAR, seminar);

		return VIEW_GET_MANAGE_SEMINAR;
	}

	/**
	 * This method is to handle Add/Edit Seminar details.
	 * 
	 * @param seminar
	 *            - {@link Seminar}.
	 * @param request
	 *            - {@link HttpServletRequest}
	 * @param model
	 *            {@link ModelMap}
	 * @param bindingResult
	 *            {@link BindingResult}
	 * @return name of the view which is redirected to.
	 * @throws AkuraAppException
	 *             - throw this
	 */
	@RequestMapping(value = REQ_MAP_VALUE_SAVEORUPDATE, method = RequestMethod.POST)
	public String saveOrUpdateSeminar(
			@ModelAttribute(MODEL_ATT_SEMINAR) Seminar seminar,
			BindingResult bindingResult, HttpServletRequest request,
			ModelMap model) throws AkuraAppException {

		seminarValidator.validate(seminar, bindingResult);
		boolean isAdd = false;

		if (bindingResult.hasErrors()) {
			model.addAttribute(MODEL_ATT_SEMINAR, seminar);
			model.addAttribute(MODEL_ATT_SHOW_PANEL, TRUE);
			return VIEW_GET_MANAGE_SEMINAR;
		} else {

			try {

				seminar.setDescription(seminar.getDescription().trim());

				// If the Seminar id is zero, it's an new entry.
				if (seminar.getSeminarId() == 0) {
					isAdd = true;
					commonService.createSeminar(seminar);
				} else {
					commonService.updateSeminar(seminar);
				}
			} catch (AkuraAppException e) {

				if (e.getCause() instanceof DataIntegrityViolationException) {

					String message = new ErrorMsgLoader()
							.getErrorMessage(ERROR_MSG_KEY);
					model.addAttribute(MESSAGE, message);
					model.addAttribute(MODEL_ATT_SHOW_PANEL, TRUE);

					// When exception occur in add, it change the id
					if (isAdd) {
						seminar.setSeminarId(0);
					}
					model.addAttribute(MODEL_ATT_SEMINAR, seminar);

					return VIEW_GET_MANAGE_SEMINAR;
				}
			}
		}
		return VIEW_POST_MANAGE_SEMINAR;
	}

	/**
	 * Delete a Seminar from reference module.
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
	public String deleteSeminar(HttpServletRequest request, ModelMap model)
			throws AkuraAppException {

		try {
			int seminarId = Integer.parseInt(request
					.getParameter(REQ_SEMINARID));
			Seminar seminar = commonService.findSeminarById(seminarId);
			commonService.deleteSeminar(seminar);
		} catch (AkuraAppException e) {
			if (e.getCause() instanceof DataIntegrityViolationException) {
				String message = new ErrorMsgLoader()
						.getErrorMessage(ERROR_MSG_DELETE);
				Seminar newSeminar = new Seminar();
				model.addAttribute(MODEL_ATT_SEMINAR, newSeminar);
				model.addAttribute(MESSAGE, message);

				return VIEW_GET_MANAGE_SEMINAR;
			}
		}
		return VIEW_POST_MANAGE_SEMINAR;
	}

	/**
	 * Method is to return Seminar list.
	 * 
	 * @return list of Seminar
	 * @throws AkuraAppException
	 *             - Detailed exception
	 */
	@ModelAttribute(MODEL_ATT_SEMINAR_LIST)
	public List<Seminar> populateSeminarList() throws AkuraAppException {

		return SortUtil.sortSeminarList(commonService.getSeminars());
	}
}
