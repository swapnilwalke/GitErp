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

package com.virtusa.akura.staff.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.virtusa.akura.api.dto.Grade;
import com.virtusa.akura.api.dto.Section;
import com.virtusa.akura.api.dto.SectionHead;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.service.StaffCommonService;
import com.virtusa.akura.staff.service.StaffService;
import com.virtusa.akura.staff.validator.SectionHeadValidator;
import com.virtusa.akura.util.DateUtil;
import com.virtusa.akura.util.SortUtil;

/**
 * The SectionHeadAllocationController is to manage staff SectionHeadAllocation
 * tab functionalities such as add, edit and delete SectionHeads.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class SectionHeadAllocationController {

	/** String attribute for holding EDITED_SECTION_HEAD_ID. */
	private static final String EDITED_SECTION_HEAD_ID = "editedSectionHeadId";

	/** String attribute for holding EMPTY. */
	private static final String EMPTY = "Empty";

	/** String attribute for holding CURRENT_YEAR. */
	private static final String CURRENT_YEAR = "currentYear";

	/** String attribute for holding ERROR_MSG. */
	private static final String ERROR_MSG_DUPLICATESTARTDATE = "SEC.UI.SECTIONHEAD.DUPLICATESTARTDATE";

	/** String attribute for holding ERROR_MSG. */
	private static final String MSG_ASSIGNED_SUCCESSFULLY = "SEC.ASSIGNED.SUCCESSFULLY";

	/** String attribute for holding ERROR_MSG. */
	private static final String MSG_UPDATED_SUCCESSFULLY = "SEC.UPDATED.SUCCESSFULLY";

	/** String attribute for holding REQ_MAP_VALUE. */
	private static final String REQ_MAP_VALUE_STAFF_DELETE_SECTION_HEAD = "/staffDeleteSectionHead.htm";

	/** String attribute for holding REQ_MAP_VALUE. */
	private static final String REQ_MAP_VALUE_STAFF_ADD_SECTION_HEAD = "/staffAddSectionHead.htm";

	/** String attribute for holding ERROR_MSG. */
	private static final String ERROR_MSG_SEC_SEARCH_NO_RESULT = "SEC.SEARCH.NO.RESULT";

	/** String attribute for holding MESSAGE. */
	private static final String MODEL_ATT_MESSAGE = "message";

	/** attribute for holding model attribute message. */
	private static final String SUCCESS_MESSAGE = "successMessage";

	/** String attribute for holding SECTION_HEAD_LIST. */
	private static final String MODEL_ATT_SECTION_HEAD_LIST = "sectionHeadList";

	/** String attribute for holding REQ_YEAR. */
	private static final String REQ_YEAR = "year";

	/** String attribute for holding SECTION_HEAD_LIST. */
	private static final String REQ_MAP_VALUE_STAFF_SEARCH_SECTION_HEAD_LIST = "/staffSearchSectionHeadList.htm";

	/** String attribute for holding VIEW_SECTION_HEAD_ALLOCATION. */
	private static final String VIEW_SECTION_HEAD_ALLOCATION = "staff/sectionHeadAllocation";

	/** String attribute for holding THIS_YEAR. */
	private static final String MODEL_ATT_THIS_YEAR = "thisYear";

	/** String attribute for holding SECTION_HEAD. */
	private static final String MODEL_ATT_SECTION_HEAD = "sectionHead";

	/** String attribute for holding GRADE_LIST. */
	private static final String GRADE_LIST = "gradeList";

	/** String attribute for holding STAFF_LIST. */
	private static final String STAFF_LIST = "staffList";

	/** String attribute for holding YEAR_LIST. */
	private static final String YEAR_LIST = "yearList";

	/** attribute for holding edit message. */
	private static final String EDITPANE = "editpane";

	/** String attribute for holding DEFALUT_DATE_FORMAT. */
	private static final String DEFALUT_DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * {@link StaffService}.
	 */
	private StaffService staffService;

	/**
	 * {@link SchoolService}.
	 */
	private CommonService commonService;

	/**
	 * {@link SchoolService}.
	 */
	private StaffCommonService staffCommonService;

	/**
	 * {@link SectionHeadValidator}.
	 */
	private SectionHeadValidator sectionHeadValidator;

	/**
	 * searched Section Head list.
	 */
	private List<SectionHead> sectionHeadList;

	/** model attribute of section list. */
	private static final String SECTION_LIST = "sectionList";

	/**
	 * intiBinder method is to bind date class with the date format.
	 * 
	 * @param binder
	 *            - data binder used to register the Date objects.
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {

		SimpleDateFormat dateFormat = new SimpleDateFormat(DEFALUT_DATE_FORMAT);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));

	}

	/**
	 * Sets an instance of StaffService.
	 * 
	 * @param staffServiceVal
	 *            - the relevant instance of StaffService
	 */
	public void setStaffService(StaffService staffServiceVal) {

		this.staffService = staffServiceVal;
	}

	/**
	 * Sets an instance of SectionHeadValidator.
	 * 
	 * @param sectionHeadValidatorVal
	 *            - the relevant instance of SectionHeadValidator
	 */
	public void setSectionHeadValidator(
			SectionHeadValidator sectionHeadValidatorVal) {

		this.sectionHeadValidator = sectionHeadValidatorVal;
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
	 * Sets the staffCommonService.
	 * 
	 * @param objStaffCommonService
	 *            the staffCommonService to set
	 */
	public void setStaffCommonService(StaffCommonService objStaffCommonService) {

		this.staffCommonService = objStaffCommonService;
	}

	/**
	 * Method to set values to class list in JSP.
	 * 
	 * @return a list of student classes.
	 * @throws AkuraException
	 *             SMS exception throw.
	 */
	@ModelAttribute(GRADE_LIST)
	public List<Grade> populateStudentGradeList() throws AkuraException {

		return SortUtil.sortGradeList(commonService.getGradeList());
	}

	/**
	 * Method to set values to staff list in JSP.
	 * 
	 * @return a list of academic staff.
	 * @throws AkuraException
	 *             SMS exception throw.
	 */
	@ModelAttribute(STAFF_LIST)
	public List<Staff> populateStudentStaffList() throws AkuraException {

		return SortUtil.sortStaffList(staffService.getAcedemicStaff());
	}

	/**
	 * Returns a list of years.
	 * 
	 * @return gradeClassList - a list of years.
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing.
	 */
	@ModelAttribute(YEAR_LIST)
	public List<String> populateYearList() throws AkuraAppException {

		List<String> yearList = new ArrayList<String>();
		final int maxYear = 5;
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		yearList.add(String.valueOf(year + 1));
		yearList.add(String.valueOf(year));
		for (int index = 1; index < maxYear; index++) {
			int newYear = year - index;
			yearList.add(String.valueOf(newYear));
		}
		return yearList;
	}

	/**
	 * handle GET requests for Staff_module_section_head_allocation view.
	 * 
	 * @param model
	 *            - ModelMap
	 * @return the name of the view.
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showSectionHeadAllocationPage(ModelMap model)
			throws AkuraAppException {

		SectionHead sectionHead = (SectionHead) model
				.get(MODEL_ATT_SECTION_HEAD);

		if (sectionHead == null) {
			sectionHead = new SectionHead();
		}
		int thisYear = DateUtil.currentYearOnly();

		model.addAttribute(MODEL_ATT_THIS_YEAR, thisYear);
		model.addAttribute(MODEL_ATT_SECTION_HEAD, sectionHead);
		sectionHeadList = staffService.getSectionHeadList();
		return VIEW_SECTION_HEAD_ALLOCATION;
	}

	/**
	 * handle POST requests for sectionHeadSearch.
	 * 
	 * @param sectionHead
	 *            - SectionHead.
	 * @param request
	 *            - HttpServletRequest.
	 * @param modelMap
	 *            - ModelMap.
	 * @return ModelAndView.
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing.
	 */
	@RequestMapping(method = RequestMethod.POST, value = REQ_MAP_VALUE_STAFF_SEARCH_SECTION_HEAD_LIST)
	public ModelAndView sectionHeadSearch(
			@ModelAttribute(MODEL_ATT_SECTION_HEAD) SectionHead sectionHead,
			final HttpServletRequest request, ModelMap modelMap)
			throws AkuraAppException {

		String year = request.getParameter(REQ_YEAR);
		sectionHeadList = staffService.searchSectionHead(
				sectionHead.getStaff(), year);

		if (sectionHeadList.isEmpty()) {
			String message = new ErrorMsgLoader()
					.getErrorMessage(ERROR_MSG_SEC_SEARCH_NO_RESULT);
			modelMap.addAttribute(MODEL_ATT_SECTION_HEAD_LIST, EMPTY);
			modelMap.addAttribute(MODEL_ATT_MESSAGE, message);
		} else {
			modelMap.addAttribute(MODEL_ATT_SECTION_HEAD_LIST,
					SortUtil.sortSectionHeadList(sectionHeadList));
		}

		modelMap.addAttribute(MODEL_ATT_THIS_YEAR, year);

		return new ModelAndView(VIEW_SECTION_HEAD_ALLOCATION, modelMap);
	}

	/**
	 * handle POST requests for addSectionHead.
	 * 
	 * @param sectionHead
	 *            - SectionHead.
	 * @param bindingResult
	 *            - BindingResult.
	 * @param modelMap
	 *            - ModelMap.
	 * @param request
	 *            - HttpServletRequest.
	 * @return ModelAndView.
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing.
	 */
	@RequestMapping(method = RequestMethod.POST, value = REQ_MAP_VALUE_STAFF_ADD_SECTION_HEAD)
	public ModelAndView addSectionHead(
			@ModelAttribute(MODEL_ATT_SECTION_HEAD) SectionHead sectionHead,
			BindingResult bindingResult, final HttpServletRequest request,
			ModelMap modelMap) throws AkuraAppException {

		if (sectionHead.getGradeId() > 0 || sectionHead.getHiddenGradeId() > 0) {

			sectionHead.setSectionId(null);
			sectionHead.setHiddenSectionId(null);

		} else if (sectionHead.getSectionId() == null) {

			sectionHead.setHiddenSectionId(sectionHead.getSectionId());

		}

		sectionHeadValidator.validate(sectionHead, bindingResult);

		if (!bindingResult.hasErrors()) {

			if (sectionHead.getStaff().getStaffId() == 0
					&& sectionHead.getGradeId() == 0
					&& sectionHead.getSectionId() == null) {

				sectionHead.getStaff().setStaffId(
						sectionHead.getHiddenStaff().getStaffId());
				sectionHead.setGradeId(sectionHead.getHiddenGradeId());
				sectionHead.setSectionId(sectionHead.getSectionId());
			}

			SectionHead head = findDuplicate(sectionHead);
			if (!(sectionHead.getSectionHeadId() > 0) && head == null) {
				staffService.assignSectionHead(sectionHead);
				String message = new ErrorMsgLoader()
						.getErrorMessage(MSG_ASSIGNED_SUCCESSFULLY);
				modelMap.addAttribute(SUCCESS_MESSAGE, message);
			} else {
				if ((sectionHead.getSectionHeadId() == head.getSectionHeadId())
						&& ((head.getStaff().getStaffId() == sectionHead
								.getStaff().getStaffId() || head.getStaff()
								.getStaffId() == sectionHead.getHiddenStaff()
								.getStaffId()) && head.getStartDate().equals(
								sectionHead.getStartDate()))) {

					staffService.updateSectionHead(sectionHead);
					String message = new ErrorMsgLoader()
							.getErrorMessage(MSG_UPDATED_SUCCESSFULLY);
					modelMap.addAttribute(SUCCESS_MESSAGE, message);
				} else {
					String message = new ErrorMsgLoader()
							.getErrorMessage(ERROR_MSG_DUPLICATESTARTDATE);
					modelMap.addAttribute(MODEL_ATT_MESSAGE, message);
					modelMap.addAttribute(EDITPANE, EDITPANE);
					modelMap.addAttribute(EDITED_SECTION_HEAD_ID,
							sectionHead.getSectionHeadId());
				}
			}
		} else {
			modelMap.addAttribute(EDITPANE, EDITPANE);
			modelMap.addAttribute(EDITED_SECTION_HEAD_ID,
					sectionHead.getSectionHeadId());
		}
		int thisYear = DateUtil.currentYearOnly();
		modelMap.addAttribute(MODEL_ATT_THIS_YEAR, thisYear);

		return sectionHeadSearch(sectionHead, request, modelMap);
		// return new ModelAndView(VIEW_SECTION_HEAD_ALLOCATION, modelMap);
	}

	/**
	 * handle POST requests for deleteSectionHead.
	 * 
	 * @param sectionHead
	 *            - SectionHead.
	 * @param request
	 *            - HttpServletRequest.
	 * @return ModelAndView.
	 * @param modelMap
	 *            - ModelMap.
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing.
	 */
	@RequestMapping(method = RequestMethod.POST, value = REQ_MAP_VALUE_STAFF_DELETE_SECTION_HEAD)
	public ModelAndView deleteSectionHead(
			@ModelAttribute(MODEL_ATT_SECTION_HEAD) SectionHead sectionHead,
			final HttpServletRequest request, ModelMap modelMap)
			throws AkuraAppException {

		SectionHead sectionHeadDeleted = staffService
				.getSectionHead(sectionHead.getSectionHeadId());

		staffService.deleteSectionHead(sectionHeadDeleted);

		// modelMap.addAttribute(MODEL_ATT_THIS_YEAR,
		// DateUtil.currentYearOnly());
		return sectionHeadSearch(sectionHead, request, modelMap);

	}

	/**
	 * FindDuplicate entries.
	 * 
	 * @param sectionHead
	 *            - the relevant instance of SectionHead
	 * @return SectionHead object
	 */
	private SectionHead findDuplicate(SectionHead sectionHead) {

		for (SectionHead head : sectionHeadList) {

			if (head.getStaff().getStaffId() == sectionHead.getStaff()
					.getStaffId()
					&& head.getStartDate().equals(sectionHead.getStartDate())) {
				return head;
			}

			if (sectionHead.getGradeId() == 0 && head.getGradeId() == 0) {

				if ((head.getSectionId().equals(sectionHead.getSectionId()))
						&& ((DateUtil.isDateBetween(head.getStartDate(),
								head.getEndDate(), sectionHead.getStartDate())) || (DateUtil
								.isDateBetween(sectionHead.getStartDate(),
										sectionHead.getEndDate(),
										head.getStartDate())))) {
					return head;
				}
			} else {
				if ((head.getGradeId() == sectionHead.getGradeId())
						&& ((DateUtil.isDateBetween(head.getStartDate(),
								head.getEndDate(), sectionHead.getStartDate())) || (DateUtil
								.isDateBetween(sectionHead.getStartDate(),
										sectionHead.getEndDate(),
										head.getStartDate())))) {
					return head;
				}
			}
		}
		return null;
	}

	/**
	 * Get current year.
	 * 
	 * @return Date object
	 * @throws AkuraAppException
	 *             throw this
	 */
	@ModelAttribute(CURRENT_YEAR)
	public Date currentYear() throws AkuraAppException {

		return new Date();
	}

	/**
	 * Get list of available section.
	 * 
	 * @return list of sections.
	 * @throws AkuraAppException
	 *             -throw detailed exception.
	 */
	@ModelAttribute(SECTION_LIST)
	public List<Section> populateSectionList() throws AkuraAppException {

		return SortUtil.sortSectionList(staffCommonService.getSectionsList());

	}
}
