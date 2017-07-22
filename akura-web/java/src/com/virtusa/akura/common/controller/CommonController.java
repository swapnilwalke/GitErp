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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.dto.Country;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.Stream;
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.CityValidator;
import com.virtusa.akura.common.validator.CountryValidator;
import com.virtusa.akura.common.validator.DistrictValidator;
import com.virtusa.akura.common.validator.ProvinceValidator;
import com.virtusa.akura.common.validator.StreamValidator;
import com.virtusa.akura.common.validator.SubjectValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * This is a controller controls all the common reference data.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class CommonController {

	/**
	 * Logger to log the exceptions.
	 */
	private static final Logger LOG = Logger.getLogger(CommonController.class);

	/** attribute for holding request parameter name of start. */
	private static final String START_FROM = "startFrom";

	/** Model attribute of number of record. */
	private static final String NUMBER_OF_RECORDS = "numberOfRecords";

	/** Represents the retrun url for manage city module. */
	private static final String REDIRECT_MANAGE_CITY_HTM = "redirect:manageCity.htm";

	/** Represents the retrun url for manage country module. */
	private static final String REDIRECT_MANAGE_COUNTRY_HTM = "redirect:manageCountry.htm";

	/** Represents the retrun url for manage city module. */
	private static final String UPDATE_CITY_HTM = "/manageSaveOrUpdateCity.htm";

	/** Represents the retrun url for manage country module. */
	private static final String UPDATE_COUNTRY_HTM = "/manageSaveOrUpdateCountry.htm";

	/** Represents the model attribute for searchCity. */
	private static final String SEARCH_CITY = "searchCity";

	/** Represents the model attribute for searchCountry. */
	private static final String SEARCH_COUNTRY = "searchCountry";

	/** Represents the retrun url for manage city module. */
	private static final String ADMIN_MANAGE_CITY = "reference/manageCity";

	/** Represents the retrun url for manage country module. */
	private static final String ADMIN_MANAGE_COUNTRY = "reference/manageCountry";

	/** Represents the retrun url for manage city module. */
	private static final String MANAGE_SEARCH_CITY = "/manageSearchCity.htm";

	/** Represents the retrun url for manage city module. */
	private static final String MANAGE_SEARCH_COUNTRY = "/manageSearchCountry.htm";

	/** Represents the retrun url for manage city module. */
	private static final String ADMIN_MANAGE_CITY_HTM = "/manageCity.htm";

	/** Represents the retrun url for manage city module. */
	private static final String ADMIN_MANAGE_COUNTRY_HTM = "/manageCountry.htm";

	/** Represents the model attribute for select. */
	private static final String SELECT = "select";

	/** Represents the model attribute for city. */
	private static final String CITY = "city";

	/** Represents the model attribute for country. */
	private static final String COUNTRY = "country";

	/** Represents the model attribute for cityList. */
	private static final String CITY_LIST = "cityList";

	/** Represents the model attribute for countryList. */
	private static final String COUNTRY_LIST = "countryList";

	/** Represents the model attribute for searchDistrict. */
	private static final String SEARCH_DISTRICT = "searchDistrict";

	/** Represents the retrun url for manage district module. */
	private static final String REDIRECT_MANAGE_DISTRICT_HTM = "redirect:manageDistrict.htm";

	/** Represents the retrun url for manage district module. */
	private static final String MANAGE_DISTRICT_HTM = "/manageSaveOrUpdateDistrict.htm";

	/** Represents the retrun url for manage district module. */
	private static final String MANAGE_SEARCH_DISTRICT_HTM = "/manageSearchDistrict.htm";

	/** Represents the retrun url for manage province module. */
	private static final String REDIRECT_MANAGE_PROVINCE_HTM = "redirect:manageProvince.htm";

	/** Represents the retrun url for manage province module. */
	private static final String REDIRECT_MANAGE_PROVINCE_UPDATE_HTM = "/manageSaveOrUpdateProvince.htm";

	/** Represents the model attribute for province. */
	private static final String PROVINCE = "province";

	/** Represents the retrun url for manage province module. */
	private static final String ADMIN_MANAGE_PROVINCE = "reference/manageProvince";

	/** Represents the retrun url for manage province module. */
	private static final String ADMIN_MANAGE_PROVINCE_HTM = "/manageProvince.htm";

	/** Represents the retrun url for manage search province module. */
	private static final String ADMIN_MANAGE_PROVINCE_SEARCH_HTM = "/manageSearchProvince.htm";

	/** Represents the model attribute for search province. */
	private static final String SEARCH_PROVINCE = "searchProvince";

	/** Represents the model attribute for search description. */
	private static final String SEARCH_DESCRIPTION = "searchDescription";

	/** Represents the model attribute meassge. */
	private static final String MESSAGE = "message";

	/** Represents the retrun url for manage city module. */
	private static final String ADMIN_MANAGE_DISTRICT = "reference/manageDistrict";

	/** Represents the retrun url for DELETE district module. */
	private static final String ADMIN_DELETE_DISTRICT = "/manageDeleteDistrict.htm";

	/** Represents the retrun url for DELETE city module. */
	private static final String ADMIN_DELETE_CITY = "/manageDeleteCity.htm";

	/** Represents the retrun url for DELETE country module. */
	private static final String ADMIN_DELETE_COUNTRY = "/manageDeleteCountry.htm";

	/** Represents the retrun url for delete province module. */
	private static final String ADMIN_DELETE_PROVINCE = "/manageDeleteProvince.htm";

	/** Represents the retrun url for manage city module. */
	private static final String ADMIN_MANAGE_DISTRICT_HTM = "/manageDistrict.htm";

	/** Represents the model attribute for distric list. */
	private static final String DISTRICT_LIST = "districtList";

	/** Represents the model attribute for distric . */
	private static final String DISTRICT = "district";

	/** Represents the model attribute for province list. */
	private static final String PROVINCE_LIST = "provinceList";

	/** Represents the retrun url for student reports module. */
	private static final String STUDENT_REPORTS = "Student/Reports";

	/** Represents the retrun url for student reports module. */
	private static final String STUDENT_REPORTS_HTM = "/ReportsLinks.htm";

	/** Represents the retrun url for admin reference module. */
	private static final String ADMIN_REFERENCE_MODULE = "reference/referenceModule";

	/** Represents the retrun url for admin reference module. */
	private static final String ADMIN_REFERENCE_MODULE_HTM = "/referenceModule.htm";

	/** Error message for delete stream. */
	private static final String ERROR_STREAM_DELETE = "REF.UI.STREAM.DELETE";

	/** Error message for stream description. */
	private static final String ERROR_MSG_STREAM_DESCRIPTION = "REF.UI.STREAM.DESCRIPTION";

	/** Error message for stream search no result. */
	private static final String ERROR_MSG_STR_SEARCH_NO_RESULT = "STR.SEARCH.NO.RESULT";

	/** Error message for delete subject. */
	private static final String ERROR_MSG_SUBJECT_DELETE = "REF.UI.SUBJECT.DELETE";

	/** Error message for subject description. */
	private static final String ERROR_MSG_SUBJECT_DESCRIPTION = "REF.UI.SUBJECT.DESCRIPTION";

	/** Error message for search no result. */
	private static final String ERROR_MSG_SEARCH_NO_RESULT = "SUB.SEARCH.NO.RESULT";

	/** Key holding the district description. */
	private static final String REF_UI_DISTRICT_DESCRIPTION = "REF.UI.DISTRICT.DESCRIPTION";

	/** Request attribute of search description. */
	private static final String REQ_SEARCH_DESCRIPTION = "searchDescription";

	/** View post method manage stream. */
	private static final String VIEW_REDIRECT_MANAGE_STREAMS = "redirect:manageStreams.htm";

	/** View get method Admin/ManageStream. */
	private static final String VIEW_ADMIN_MANAGE_STREAMS = "reference/manageStreams";

	/** View post method manage subjects. */
	private static final String VIEW_REDIRECT_MANAGE_SUBJECTS = "redirect:manageSubjects.htm";

	/** View get method Admin/ManageSubjects. */
	private static final String VIEW_ADMIN_MANAGE_SUBJECTS = "reference/manageSubjects";

	/** String attribute for date format. */
	private static final String DATE_FORMAT = "yyyy-MM-dd";

	/** Model attribute of SearchStream. */
	private static final String MODEL_ATT_SEARCH_STREAM = "searchStream";

	/** Model attribute of Stream. */
	private static final String MODEL_ATT_STREAM = "stream";

	/** Model attribute of SearchSubject. */
	private static final String MODEL_ATT_SEARCH_SUBJECT = "searchSubject";

	/** Model attribute of Message. */
	private static final String MODEL_ATT_MESSAGE = "message";

	/** Model attribute of Subject. */
	private static final String MODEL_ATT_SUBJECT = "subject";

	/** Model attribute of StreamList. */
	private static final String MODEL_ATT_STREAM_LIST = "streamList";

	/** Model attribute of SubjectList. */
	private static final String MODEL_ATT_SUBJECT_LIST = "subjectList";

	/** Request mapping value for ManageDeleteStream. */
	private static final String REQ_VALUE_DELETE_STREAM = "/manageDeleteStream.htm";

	/** Request mapping value for SaveOrUpdateStream. */
	private static final String REQ_VALUE_SAVE_OR_UPDATE_STREAM = "/manageSaveOrUpdateStream.htm";

	/** Request mapping value for ManageSearchStream. */
	private static final String REQ_VALUE_SEARCH_STREAM = "/manageSearchStream.htm";

	/** Request mapping value for ManageStream. */
	private static final String REQ_VALUE_MANAGE_STREAMS = "/manageStreams.htm";

	/** Request mapping value for ManageDeleteSubject. */
	private static final String REQ_VALUE_DELETE_SUBJECT = "/manageDeleteSubject.htm";

	/** Request mapping value for SaveOrUpdateSubject. */
	private static final String REQ_VALUE_SAVE_OR_UPDATE_SUBJECT = "/manageSaveOrUpdateSubject.htm";

	/** Request mapping value for ManageSearchSubject. */
	private static final String REQ_VALUE_MANAGE_SEARCH_SUBJECT = "/manageSearchSubject.htm";

	/** Request mapping value for ManageSubject. */
	private static final String REQ_VALUE_MANAGE_SUBJECTS = "/manageSubjects.htm";

	/** Model attribute of "showEditSection". */
	private static final String SHOW_EDIT_SECTION = "showEditSection";

	/** Model attribute of "selectedObjId". */
	private static final String SELECTED_OBJ_ID = "selectedObjId";

	/** attribute for holding message. */
	private static final String EDITPANE = "editpane";

	/**
	 * Represents an instance of the CommonService.
	 */
	private CommonService commonService;

	/**
	 * Represents an instance of the SubjectValidator.
	 */
	private SubjectValidator subjectValidator;

	/**
	 * Represents an instance of the StreamValidator.
	 */
	private StreamValidator streamValidator;

	/**
	 * Represents an instance of the CityValidator.
	 */
	private CityValidator cityValidator;

	/**
	 * Represents an instance of the CountryValidator.
	 */
	private CountryValidator countryValidator;

	/**
	 * Represents an instance of the DistrictValidator.
	 */
	private DistrictValidator districtValidator;

	/**
	 * Represents an instance of the ProvinceValidator.
	 */
	private ProvinceValidator provinceValidator;

	/**
	 * Setter method for the SubjectValidator.
	 * 
	 * @param subjectValidatorVal
	 *            - the instance of the SubjectValidator
	 */
	public final void setSubjectValidator(
			final SubjectValidator subjectValidatorVal) {

		subjectValidator = subjectValidatorVal;
	}

	/**
	 * Setter method for the StreamValidator.
	 * 
	 * @param streamValidatorVal
	 *            - the instance of the StreamValidator
	 */
	public final void setStreamValidator(
			final StreamValidator streamValidatorVal) {

		streamValidator = streamValidatorVal;
	}

	/**
	 * Sets an instance of CommonService.
	 * 
	 * @param commonServiceVal
	 *            - the instance of CommonService
	 */
	public final void setCommonService(final CommonService commonServiceVal) {

		commonService = commonServiceVal;
	}

	/**
	 * Sets an instance of CityValidator.
	 * 
	 * @param cityValidatorVal
	 *            - the instance of CityValidator
	 */
	public final void setCityValidator(final CityValidator cityValidatorVal) {

		cityValidator = cityValidatorVal;
	}

	/**
	 * Sets an instance of CountryValidator.
	 * 
	 * @param countryValidatorVal
	 *            - the instance of CountryValidator
	 */
	public final void setCountryValidator(
			final CountryValidator countryValidatorVal) {

		countryValidator = countryValidatorVal;
	}

	/**
	 * Sets an instance of DistrictValidator.
	 * 
	 * @param districtValidatorVal
	 *            - the instance of DistrictValidator
	 */
	public final void setDistrictValidator(
			final DistrictValidator districtValidatorVal) {

		districtValidator = districtValidatorVal;
	}

	/**
	 * Sets an instance of ProvinceValidator.
	 * 
	 * @param provinceValidatorVal
	 *            - the instance of ProvinceValidator
	 */
	public final void setProvinceValidator(
			final ProvinceValidator provinceValidatorVal) {

		provinceValidator = provinceValidatorVal;
	}

	/**
	 * Navigate method to reference module.
	 * 
	 * @return url to reference module jsp.
	 */
	@RequestMapping(value = ADMIN_REFERENCE_MODULE_HTM)
	public final String referenceModule() {

		return ADMIN_REFERENCE_MODULE;
	}

	/**
	 * Navigate method to Report module.
	 * 
	 * @return url to Report module jsp.
	 */
	@RequestMapping(value = STUDENT_REPORTS_HTM)
	public final String reportModule() {

		return STUDENT_REPORTS;
	}

	/**
	 * Initializes the reference data that is to be previewed on the UI.
	 * 
	 * @param map
	 *            - a HashMap that contains information of the District
	 * @return - name of the view which is redirected to
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing.
	 */
	@RequestMapping(value = ADMIN_MANAGE_DISTRICT_HTM)
	public final String referenceModuleDistrict(final ModelMap map)
			throws AkuraAppException {

		District district = new District();
		List<Province> provinceList = commonService.getProvinceList();
		List<District> districtList = commonService.getDistrictList();
		map.addAttribute(PROVINCE_LIST, SortUtil.sortProvinceList(provinceList));
		map.addAttribute(DISTRICT, district);
		map.addAttribute(DISTRICT_LIST, SortUtil.sortDistrictList(districtList));
		return ADMIN_MANAGE_DISTRICT;

	}

	/**
	 * Searches results for the given province criteria.
	 * 
	 * @param request
	 *            - an object of HttpServletRequest
	 * @param map
	 *            - a HashMap that contains information of the Province
	 * @param province
	 *            - an instance of Province
	 * @return - name of the view which is redirected to
	 * @throws AkuraAppException
	 *             - The exception details that occurred when searching Province
	 *             instances.
	 */
	@RequestMapping(value = ADMIN_MANAGE_PROVINCE_SEARCH_HTM, method = RequestMethod.POST)
	public final String searchProvince(final HttpServletRequest request,
			final ModelMap map,
			@ModelAttribute(PROVINCE) final Province province)
			throws AkuraAppException {

		String searchDescription = request.getParameter(SEARCH_DESCRIPTION)
				.trim();
		List<Province> searchProvince = commonService
				.searchProvince(searchDescription);
		searchProvince = SortUtil.sortProvinceList(searchProvince);

		if (searchProvince.isEmpty()) {
			String message = new ErrorMsgLoader()
					.getErrorMessage("PRO.SEARCH.NO.RESULT");
			map.addAttribute(MESSAGE, message);
		} else {
			map.addAttribute(SEARCH_PROVINCE, searchProvince);
			map.addAttribute(SEARCH_DESCRIPTION, searchDescription);
		}
		return ADMIN_MANAGE_PROVINCE;

	}

	/**
	 * Initializes the reference data that is to be previewed on the UI.
	 * 
	 * @param provinceModel
	 *            - a HashMap that contains information of the Province
	 * @return - name of the view which is redirected to.
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing.
	 */
	@RequestMapping(value = ADMIN_MANAGE_PROVINCE_HTM)
	public final String manageProvince(final ModelMap provinceModel)
			throws AkuraAppException {

		Province province = new Province();
		List<Province> provinceList = commonService.getProvinceList();
		provinceModel.addAttribute(PROVINCE_LIST,
				SortUtil.sortProvinceList(provinceList));
		provinceModel.addAttribute(PROVINCE, province);
		return ADMIN_MANAGE_PROVINCE;

	}

	/**
	 * Saves or updates the relevant province object.
	 * 
	 * @param model
	 *            - a HashMap that contains information of the Province
	 * @param province
	 *            - an instance of Province
	 * @param result
	 *            - containing list of errors and province instance to which
	 *            data was bound
	 * @return - name of the view which is redirected to
	 * @throws AkuraAppException
	 *             - The exception details that occurred when saving or updating
	 *             a Province instance.
	 */
	@RequestMapping(value = REDIRECT_MANAGE_PROVINCE_UPDATE_HTM, method = RequestMethod.POST)
	public final String saveOrUpdateProvince(final ModelMap model,
			@ModelAttribute(PROVINCE) final Province province,
			BindingResult result) throws AkuraAppException {

		String returnResult = ADMIN_MANAGE_PROVINCE;
		LOG.debug("Save or update an instance of Province");
		List<Province> provinceList = commonService.getProvinceList();
		model.addAttribute(PROVINCE_LIST,
				SortUtil.sortProvinceList(provinceList));
		provinceValidator.validate(province, result);

		if (result.hasErrors()) {
			returnResult = ADMIN_MANAGE_PROVINCE;
			model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
			model.addAttribute(SELECTED_OBJ_ID, province.getProvinceId());
		} else {
			String description = province.getDescription().trim();
			description = description.replaceAll("( )+", " ");
			int id = province.getProvinceId();

			try {
				if (id > 0) {
					Province findProvince = commonService.findProvince(id);
					model.addAttribute(SELECTED_OBJ_ID,
							findProvince.getProvinceId());
					findProvince.setDescription(description);
					commonService.updateProvince(findProvince);
				} else {
					province.setDescription(description);
					commonService.addProvince(province);
				}
				returnResult = REDIRECT_MANAGE_PROVINCE_HTM;
			} catch (AkuraAppException e) {
				if (e.getCause() instanceof DataIntegrityViolationException) {
					String message = new ErrorMsgLoader()
							.getErrorMessage("REF.UI.PROVINCE.DESCRIPTION.ERROR");
					model.addAttribute(MESSAGE, message);
					model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
					returnResult = ADMIN_MANAGE_PROVINCE;
				} else {
					throw e;
				}
			}
		}
		return returnResult;
	}

	/**
	 * Saves or updates the relevant district object.
	 * 
	 * @param model
	 *            - a HashMap that contains information of the District
	 * @param newDistrict
	 *            - an instance of District
	 * @param result
	 *            - containing list of errors and district instance to which
	 *            data was bound
	 * @return - name of the view which is redirected to
	 * @throws AkuraAppException
	 *             - The exception details that occurred when saving or updating
	 *             a District instance.
	 */
	@RequestMapping(value = MANAGE_DISTRICT_HTM, method = RequestMethod.POST)
	public final String saveOrUpdateDistrict(final ModelMap model,
			@ModelAttribute(DISTRICT) final District newDistrict,
			BindingResult result) throws AkuraAppException {

		Province province = null;
		String returnResult = ADMIN_MANAGE_DISTRICT;
		List<District> districtList = commonService.getDistrictList();
		List<Province> provinceList = commonService.getProvinceList();
		model.addAttribute(DISTRICT_LIST,
				SortUtil.sortDistrictList(districtList));
		model.addAttribute(PROVINCE_LIST,
				SortUtil.sortProvinceList(provinceList));

		districtValidator.validate(newDistrict, result);

		if (result.hasErrors()) {
			model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
			model.addAttribute(SELECTED_OBJ_ID, newDistrict.getDistrictId());
			returnResult = ADMIN_MANAGE_DISTRICT;
		} else {
			String description = newDistrict.getDescription().trim();
			description = description.replaceAll("( )+", " ");
			int id = newDistrict.getDistrictId();
			String provinceDescription = newDistrict.getProvince()
					.getDescription();
			List<Province> searchProvinceList = commonService
					.searchProvince(provinceDescription);

			if (!searchProvinceList.isEmpty()) {
				province = searchProvinceList.get(0);
			}
			// uses when edit the district data. if the district id is
			// greater than zero, updates it.
			try {

				if (id > 0) {
					District findDistrict = commonService.findDistrict(id);

					if (newDistrict.getProvince().getDescription()
							.equals(SELECT)) {
						findDistrict.setProvince(null);

					} else {
						findDistrict.setProvince(province);
					}
					model.addAttribute(SELECTED_OBJ_ID,
							newDistrict.getDistrictId());
					findDistrict.setDescription(description);
					commonService.updateDistrict(findDistrict);

				} else {
					newDistrict.setDescription(description);

					if (newDistrict.getProvince().getDescription()
							.equals(SELECT)) {
						newDistrict.setProvince(null);

					} else {
						newDistrict.setProvince(province);
					}
					commonService.addDistrict(newDistrict);
				}
				return REDIRECT_MANAGE_DISTRICT_HTM;
			} catch (AkuraAppException e) {

				if (e.getCause() instanceof DataIntegrityViolationException) {
					String message = new ErrorMsgLoader()
							.getErrorMessage(REF_UI_DISTRICT_DESCRIPTION);
					model.addAttribute(MESSAGE, message);
					model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
					newDistrict.setDistrictId(0);
					return ADMIN_MANAGE_DISTRICT;
				} else {
					throw e;
				}
			}

		}
		return returnResult;
	}

	/**
	 * Searches the results for the given district criteria.
	 * 
	 * @param request
	 *            - an object of HttpServletRequest
	 * @param map
	 *            - a HashMap that contains information of the District
	 * @param district
	 *            - an instance of District
	 * @return - name of the view which is redirected to
	 * @throws AkuraAppException
	 *             - The exception details that occurred when searching District
	 *             instances.
	 */
	@RequestMapping(value = MANAGE_SEARCH_DISTRICT_HTM, method = RequestMethod.POST)
	public final String searchDistrict(final HttpServletRequest request,
			final ModelMap map,
			@ModelAttribute(DISTRICT) final District district)
			throws AkuraAppException {

		String searchDescription = request.getParameter(SEARCH_DESCRIPTION);
		List<Province> provinceList = commonService.getProvinceList();
		List<District> searchDistrict = commonService
				.searchDistrict(searchDescription.trim());
		provinceList = SortUtil.sortProvinceList(provinceList);
		searchDistrict = SortUtil.sortDistrictList(searchDistrict);

		if (searchDistrict.isEmpty()) {
			String message = new ErrorMsgLoader()
					.getErrorMessage("DIS.SEARCH.NO.RESULT");
			map.addAttribute(MESSAGE, message);
		} else {
			map.addAttribute(SEARCH_DISTRICT, searchDistrict);
			map.addAttribute(SEARCH_DESCRIPTION, searchDescription);
		}
		map.addAttribute(PROVINCE_LIST, provinceList);
		return ADMIN_MANAGE_DISTRICT;

	}

	/**
	 * Initializes the reference data that is to be previewed on the UI.
	 * 
	 * @param model
	 *            - a HashMap that contains information of the City
	 * @return - name of the view which is redirected to
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing.
	 */
	@RequestMapping(value = ADMIN_MANAGE_CITY_HTM)
	public final String manageCity(final ModelMap model)
			throws AkuraAppException {

		City city = new City();
		List<City> cityList = commonService.getCityList();
		List<District> districtList = commonService.getDistrictList();

		model.addAttribute(CITY_LIST, SortUtil.sortCityList(cityList));
		model.addAttribute(DISTRICT_LIST,
				SortUtil.sortDistrictList(districtList));
		model.addAttribute(CITY, city);

		return ADMIN_MANAGE_CITY;

	}

	/**
	 * Initializes the reference data that is to be previewed on the UI.
	 * 
	 * @param request
	 *            - an object of HttpServletRequest
	 * @param model
	 *            - a HashMap that contains information of the Country
	 * @return - name of the view which is redirected to
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing.
	 */
	@RequestMapping(value = ADMIN_MANAGE_COUNTRY_HTM)
	public final String manageCountry(final ModelMap model,
			final HttpServletRequest request) throws AkuraAppException {

		List<Country> countryList = commonService.getCountryList();

		Country country = new Country();

		List<Country> searchReultsForPage = setPageSize(countryList.size(),
				countryList, request.getParameter(START_FROM), country);

		model.addAttribute(COUNTRY_LIST, searchReultsForPage);
		model.addAttribute(NUMBER_OF_RECORDS, countryList.size());
		model.addAttribute(COUNTRY, country);

		return ADMIN_MANAGE_COUNTRY;

	}

	/**
	 * Searches results for the given city criteria.
	 * 
	 * @param request
	 *            - an object of HttpServletRequest
	 * @param map
	 *            - a HashMap that contains information of the City
	 * @param city
	 *            - an instance of City
	 * @return - name of the view which is redirected to
	 * @throws AkuraAppException
	 *             - The exception details that occurred when searching City
	 *             instances.
	 */
	@RequestMapping(value = MANAGE_SEARCH_CITY, method = RequestMethod.POST)
	public final String searchCity(final HttpServletRequest request,
			final ModelMap map, @ModelAttribute(CITY) final City city)
			throws AkuraAppException {

		String searchDescription = request.getParameter(SEARCH_DESCRIPTION)
				.trim();
		List<District> districtList = commonService.getDistrictList();
		List<City> searchCity = commonService.searchCity(searchDescription);

		if (searchCity.isEmpty()) {
			String message = new ErrorMsgLoader()
					.getErrorMessage("CIT.SEARCH.NO.RESULT");
			map.addAttribute(MESSAGE, message);
		} else {
			map.addAttribute(SEARCH_CITY, SortUtil.sortCityList(searchCity));
		}
		map.addAttribute(DISTRICT_LIST, SortUtil.sortDistrictList(districtList));
		return ADMIN_MANAGE_CITY;

	}

	/**
	 * Searches results for the given country criteria.
	 * 
	 * @param request
	 *            - an object of HttpServletRequest
	 * @param map
	 *            - a HashMap that contains information of the Country
	 * @param country
	 *            - an instance of Country
	 * @return - name of the view which is redirected to
	 * @throws AkuraAppException
	 *             - The exception details that occurred when searching Country
	 *             instances.
	 */
	@RequestMapping(value = MANAGE_SEARCH_COUNTRY, method = RequestMethod.POST)
	public final String searchCountry(final HttpServletRequest request,
			final ModelMap map, @ModelAttribute(COUNTRY) final Country country)
			throws AkuraAppException {

		String searchDescription = request.getParameter(SEARCH_DESCRIPTION)
				.trim();
		List<Country> searchCountry = commonService
				.searchCountry(searchDescription);

		List<Country> searchReultsForPage = setPageSize(searchCountry.size(),
				searchCountry, request.getParameter(START_FROM), country);

		if (searchReultsForPage.isEmpty()) {
			String message = new ErrorMsgLoader()
					.getErrorMessage("COUN.SEARCH.NO.RESULT");
			map.addAttribute(MESSAGE, message);
			map.addAttribute(NUMBER_OF_RECORDS, searchCountry.size());
			map.addAttribute(COUNTRY, country);
		} else {

			map.addAttribute(SEARCH_COUNTRY, searchReultsForPage);
			map.addAttribute(NUMBER_OF_RECORDS, searchCountry.size());
		}
		map.addAttribute(SEARCH_DESCRIPTION, searchDescription);
		return ADMIN_MANAGE_COUNTRY;

	}

	/**
	 * Saves or updates the relevant city object.
	 * 
	 * @param model
	 *            - a HashMap that contains information of the City
	 * @param addCity
	 *            - an instance of City
	 * @param result
	 *            - containing list of errors and city instance to which data
	 *            was bound
	 * @return - name of the view which is redirected to
	 * @throws AkuraAppException
	 *             - The exception details that occurred when saving or updating
	 *             a City instance.
	 */
	@RequestMapping(value = UPDATE_CITY_HTM, method = RequestMethod.POST)
	public final String saveOrUpdateCity(final ModelMap model,
			@ModelAttribute(CITY) final City addCity, BindingResult result)
			throws AkuraAppException {

		String returnResult = REDIRECT_MANAGE_CITY_HTM;
		List<City> cityList = commonService.getCityList();
		List<District> districtList = commonService.getDistrictList();
		model.addAttribute(CITY_LIST, SortUtil.sortCityList(cityList));
		model.addAttribute(DISTRICT_LIST,
				SortUtil.sortDistrictList(districtList));

		cityValidator.validate(addCity, result);

		if (result.hasErrors()) {
			model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
			model.addAttribute(SELECTED_OBJ_ID, addCity.getCityId());
			returnResult = ADMIN_MANAGE_CITY;

		} else {
			String description = addCity.getDescription().trim();
			description = description.replaceAll("( )+", " ");
			int id = addCity.getCityId();
			String districtName = addCity.getDistrict().getDescription();
			List<District> searchDistrictList = commonService
					.searchDistrict(districtName);

			// uses when edit the city data. if the city id is
			// greater than zero updates the object.
			try {

				if (id > 0) {
					City findCity = commonService.findCity(id);
					findCity.setDescription(description);

					if (addCity.getDistrict().getDescription().equals(SELECT)) {
						findCity.setDistrict(null);

					} else {
						findCity.setDistrict(searchDistrictList.get(0));
					}
					model.addAttribute(SELECTED_OBJ_ID, addCity.getCityId());
					commonService.updateCity(findCity);

				} else {
					addCity.setDescription(description);

					if (addCity.getDistrict().getDescription().equals(SELECT)) {
						addCity.setDistrict(null);

					} else {
						addCity.setDistrict(searchDistrictList.get(0));
					}
					commonService.addCity(addCity);
				}
				returnResult = REDIRECT_MANAGE_CITY_HTM;
			} catch (AkuraAppException e) {

				if (e.getCause() instanceof DataIntegrityViolationException) {
					String message = new ErrorMsgLoader()
							.getErrorMessage("REF.UI.CITY.DESCRIPTION");
					model.addAttribute(MESSAGE, message);
					model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
					addCity.setCityId(0);
					returnResult = ADMIN_MANAGE_CITY;
				} else {
					throw e;
				}
			}
		}

		return returnResult;
	}

	/**
	 * Saves or updates the relevant country object.
	 * 
	 * @param request
	 *            - an object of HttpServletRequest
	 * @param model
	 *            - a HashMap that contains information of the Country
	 * @param addCountry
	 *            - an instance of Country
	 * @param result
	 *            - containing list of errors and country instance to which data
	 *            was bound
	 * @return - name of the view which is redirected to
	 * @throws AkuraAppException
	 *             - The exception details that occurred when saving or updating
	 *             a Country instance.
	 */
	@RequestMapping(value = UPDATE_COUNTRY_HTM, method = RequestMethod.POST)
	public final String saveOrUpdateCountry(final HttpServletRequest request,
			final ModelMap model,
			@ModelAttribute(COUNTRY) final Country addCountry,
			BindingResult result) throws AkuraAppException {

		String returnResult = REDIRECT_MANAGE_COUNTRY_HTM;
		List<Country> countryList = commonService.getCountryList();
		List<Country> searchReultsForPage = setPageSize(countryList.size(),
				countryList, request.getParameter(START_FROM), addCountry);

		model.addAttribute(COUNTRY_LIST, searchReultsForPage);
		model.addAttribute(NUMBER_OF_RECORDS, countryList.size());

		countryValidator.validate(addCountry, result);

		if (result.hasErrors()) {
			model.addAttribute(SELECTED_OBJ_ID, addCountry.getCountryId());
			model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
			returnResult = ADMIN_MANAGE_COUNTRY;

		} else {
			String country = addCountry.getCountryName().trim()
					.replaceAll("( )+", " ");
			String countryCode = addCountry.getCountryCode();

			if (commonService.isExistsCountry(country,
					addCountry.getCountryId())) {
				String message = new ErrorMsgLoader()
						.getErrorMessage("REF.UI.RECORD.EXIST.ERROR");

				model.addAttribute(SELECTED_OBJ_ID, addCountry.getCountryId());
				model.addAttribute(COUNTRY, addCountry);
				model.addAttribute(MESSAGE, message);
				model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
				returnResult = ADMIN_MANAGE_COUNTRY;
			} else {

				addCountry.setCountryName(country);
				addCountry.setCountryCode(countryCode);

				// If the country id is zero, it's an new entry.
				if (addCountry.getCountryId() == 0) {
					commonService.addCountry(addCountry);
				} else {
					commonService.updateCountry(addCountry);
				}
			}
		}
		return returnResult;
	}

	/**
	 * Deletes the relevant District object.
	 * 
	 * @param model
	 *            - a HashMap that contains information of the District
	 * @param deleteDistrict
	 *            - the related instance of District
	 * @return - name of the view which is redirected to
	 * @throws AkuraAppException
	 *             - The exception details that occurred when deleting a
	 *             District.
	 */
	@RequestMapping(ADMIN_DELETE_DISTRICT)
	public final String deleteDistrict(
			@ModelAttribute(DISTRICT) final District deleteDistrict,
			final ModelMap model) throws AkuraAppException {

		try {
			District findDistrict = commonService.findDistrict(deleteDistrict
					.getDistrictId());
			commonService.deleteDistrict(findDistrict);
			return REDIRECT_MANAGE_DISTRICT_HTM;
		} catch (AkuraAppException e) {
			String message = new ErrorMsgLoader()
					.getErrorMessage("REF.UI.DISTRICT.DELETE");
			District newDistrict = new District();
			List<District> districtList = commonService.getDistrictList();
			List<Province> provinceList = commonService.getProvinceList();
			model.addAttribute(MESSAGE, message);
			model.addAttribute(DISTRICT, newDistrict);
			model.addAttribute(DISTRICT_LIST,
					SortUtil.sortDistrictList(districtList));
			model.addAttribute(PROVINCE_LIST, provinceList);
			return ADMIN_MANAGE_DISTRICT;
		}
	}

	/**
	 * Deletes the relevant Province object.
	 * 
	 * @param model
	 *            - a HashMap that contains information of the Province
	 * @param deleteProvince
	 *            - an instance of Province
	 * @return - name of the view which is redirected to
	 * @throws AkuraAppException
	 *             - The exception details that occurred when deleting a
	 *             Province.
	 */
	@RequestMapping(ADMIN_DELETE_PROVINCE)
	public final String deleteProvince(
			@ModelAttribute(PROVINCE) final Province deleteProvince,
			final ModelMap model) throws AkuraAppException {

		Province findProvince = commonService.findProvince(deleteProvince
				.getProvinceId());
		try {
			commonService.deleteProvince(findProvince);

			return REDIRECT_MANAGE_PROVINCE_HTM;
		} catch (AkuraAppException e) {

			if (e.getCause() instanceof DataIntegrityViolationException) {
				String message = new ErrorMsgLoader()
						.getErrorMessage("REF.UI.PROVINCE.DELETE");
				Province newProvince = new Province();
				List<Province> provinceList = commonService.getProvinceList();
				model.addAttribute(MESSAGE, message);
				model.addAttribute(PROVINCE, newProvince);
				model.addAttribute(PROVINCE_LIST,
						SortUtil.sortProvinceList(provinceList));
				return ADMIN_MANAGE_PROVINCE;
			} else {
				throw e;
			}
		}
	}

	/**
	 * Deletes the relevant City object.
	 * 
	 * @param deleteCity
	 *            - the relevant instance of City
	 * @param model
	 *            - a HashMap that contains information of the City
	 * @return - name of the view which is redirected to
	 * @throws AkuraAppException
	 *             - The exception details that occurred when deleting a City
	 *             instance.
	 */
	@RequestMapping(ADMIN_DELETE_CITY)
	public final String deleteCity(@ModelAttribute(CITY) final City deleteCity,
			final ModelMap model) throws AkuraAppException {

		try {
			City findCity = commonService.findCity(deleteCity.getCityId());
			commonService.deleteCity(findCity);
			return REDIRECT_MANAGE_CITY_HTM;
		} catch (AkuraAppException e) {

			if (e.getCause() instanceof DataIntegrityViolationException) {
				String message = new ErrorMsgLoader()
						.getErrorMessage("REF.UI.CITY.DELETE");
				City newCity = new City();
				List<City> cityList = commonService.getCityList();
				List<District> districtList = commonService.getDistrictList();
				model.addAttribute(MESSAGE, message);
				model.addAttribute(CITY, newCity);
				model.addAttribute(CITY_LIST, SortUtil.sortCityList(cityList));
				model.addAttribute(DISTRICT_LIST,
						SortUtil.sortDistrictList(districtList));
				return ADMIN_MANAGE_CITY;
			} else {
				throw e;
			}
		}
	}

	/**
	 * Deletes the relevant Country object.
	 * 
	 * @param request
	 *            - an object of HttpServletRequest
	 * @param deleteCountry
	 *            - the relevant instance of Country
	 * @param model
	 *            - a HashMap that contains information of the Country
	 * @return - name of the view which is redirected to
	 * @throws AkuraAppException
	 *             - The exception details that occurred when deleting a Country
	 *             instance.
	 */
	@RequestMapping(ADMIN_DELETE_COUNTRY)
	public final String deleteCountry(
			@ModelAttribute(COUNTRY) final Country deleteCountry,
			final ModelMap model, final HttpServletRequest request)
			throws AkuraAppException {

		try {
			Country findCountry = commonService.findCountry(deleteCountry
					.getCountryId());
			commonService.deleteCountry(findCountry);
			return REDIRECT_MANAGE_COUNTRY_HTM;
		} catch (AkuraAppException e) {

			if (e.getCause() instanceof DataIntegrityViolationException) {
				String message = new ErrorMsgLoader()
						.getErrorMessage("REF.UI.COUNTRY.DELETE");

				List<Country> countryList = commonService.getCountryList();
				List<Country> searchReultsForPage = setPageSize(
						countryList.size(), countryList,
						request.getParameter(START_FROM), deleteCountry);
				model.addAttribute(SEARCH_COUNTRY, searchReultsForPage);
				model.addAttribute(NUMBER_OF_RECORDS, countryList.size());
				model.addAttribute(MESSAGE, message);
				model.addAttribute(COUNTRY, deleteCountry);
				return ADMIN_MANAGE_COUNTRY;
			} else {
				throw e;
			}
		}
	}

	/**
	 * Initializes the reference data that is to be previewed on the UI.
	 * 
	 * @param map
	 *            - a HashMap that contains information of the Subject
	 * @return - name of the view which is redirected to.
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing.
	 */
	@RequestMapping(value = REQ_VALUE_MANAGE_SUBJECTS)
	public final String referenceModuleSubject(final ModelMap map)
			throws AkuraAppException {

		try {
			Subject subject = new Subject();
			List<Subject> subjectList = commonService.getSubjectList();
			List<Stream> streamList = commonService.getStreamList();
			map.addAttribute(MODEL_ATT_SUBJECT_LIST,
					SortUtil.sortSubjectList(subjectList));
			map.addAttribute(MODEL_ATT_STREAM_LIST,
					SortUtil.sortStreamList(streamList));
			map.addAttribute(MODEL_ATT_SUBJECT, subject);

			return VIEW_ADMIN_MANAGE_SUBJECTS;
		} catch (AkuraAppException e) {
			throw e;
		}
	}

	/**
	 * Searches results for the given subject criteria.
	 * 
	 * @param request
	 *            - an object of HttpServletRequest
	 * @param map
	 *            - a HashMap that contains information of the Subject
	 * @param subject
	 *            - an instance of Subject
	 * @return - name of the view which is redirected to
	 * @throws AkuraAppException
	 *             - The exception details that occurred when searching Subject
	 *             instances.
	 */
	@RequestMapping(value = REQ_VALUE_MANAGE_SEARCH_SUBJECT, method = RequestMethod.POST)
	public final String searchSubject(final HttpServletRequest request,
			final ModelMap map,
			@ModelAttribute(MODEL_ATT_SUBJECT) final Subject subject)
			throws AkuraAppException {

		// String message = MODEL_ATT_MESSAGE;
		try {
			List<Stream> streamList = commonService.getStreamList();
			String searchDescription = request.getParameter(
					REQ_SEARCH_DESCRIPTION).trim();
			request.setAttribute(SEARCH_DESCRIPTION, searchDescription);
			List<Subject> searchSubject = commonService
					.searchSubject(searchDescription);
			if (searchSubject.isEmpty()) {
				String message = new ErrorMsgLoader()
						.getErrorMessage(ERROR_MSG_SEARCH_NO_RESULT);
				map.addAttribute(MODEL_ATT_MESSAGE, message);
			} else {
				map.addAttribute(MODEL_ATT_SEARCH_SUBJECT, searchSubject);
			}
			map.addAttribute(MODEL_ATT_STREAM_LIST, streamList);
			return VIEW_ADMIN_MANAGE_SUBJECTS;
		} catch (AkuraAppException e) {
			throw e;
		}
	}

	/**
	 * Saves or updates the relevant subject object.
	 * 
	 * @param model
	 *            - a HashMap that contains information of the Subject
	 * @param subject
	 *            - an instance of Subject
	 * @param result
	 *            - containing list of errors and subject instance to which data
	 *            was bound
	 * @return - name of the view which is redirected to
	 * @throws AkuraAppException
	 *             - The exception details that occurred when saving or updating
	 *             a Subject instance.
	 */
	@RequestMapping(value = REQ_VALUE_SAVE_OR_UPDATE_SUBJECT, method = RequestMethod.POST)
	public final String saveOrUpdateSubject(final ModelMap model,
			@ModelAttribute(MODEL_ATT_SUBJECT) final Subject subject,
			BindingResult result) throws AkuraAppException {

		// String message = MODEL_ATT_MESSAGE;
		Stream stream = null;
		List<Stream> streamList = commonService.getStreamList();
		List<Subject> subjectList = commonService.getSubjectList();
		model.addAttribute(MODEL_ATT_SUBJECT_LIST,
				SortUtil.sortSubjectList(subjectList));
		model.addAttribute(MODEL_ATT_STREAM_LIST,
				SortUtil.sortStreamList(streamList));
		subjectValidator.validate(subject, result);

		if (result.hasErrors()) {
			model.addAttribute(EDITPANE, EDITPANE);
			model.addAttribute(SELECTED_OBJ_ID, subject.getSubjectId());
			return VIEW_ADMIN_MANAGE_SUBJECTS;
		} else {
			String description = subject.getDescription().trim();
			description = description.replaceAll("( )+", " ");
			String streamDescription = subject.getStream().getDescription();
			List<Stream> searchStreamList = commonService
					.searchStream(streamDescription);

			if (!searchStreamList.isEmpty()) {
				stream = searchStreamList.get(0);
			}
			// uses when edit the subject data. if the subject id is
			// greater than zero, updates it.
			int id = subject.getSubjectId();
			String govSubjectCode;
			try {
				if (id > 0) {

					Subject findSubject = commonService.findSubject(id);
					findSubject.setStream(stream);
					findSubject.setDescription(description);
					findSubject.setSubjectCode(subject.getSubjectCode().trim());

					govSubjectCode = subject.getGovSubjectCode();

					// Check the value of govSubjectCode and set "null" if empty
					if (!govSubjectCode.isEmpty()) {
						findSubject.setGovSubjectCode(subject
								.getGovSubjectCode());
					} else {
						findSubject.setGovSubjectCode(null);
					}

					commonService.updateSubject(findSubject);

					return VIEW_REDIRECT_MANAGE_SUBJECTS;

				} else {
					subject.setDescription(description);
					subject.setStream(stream);
					subject.setSubjectCode(subject.getSubjectCode().trim());

					govSubjectCode = subject.getGovSubjectCode();
					// Check the value of govSubjectCode and set "null" if empty
					if (!govSubjectCode.isEmpty()) {
						subject.setGovSubjectCode(subject.getGovSubjectCode());
					} else {
						subject.setGovSubjectCode(null);
					}

					commonService.addSubject(subject);

					return VIEW_REDIRECT_MANAGE_SUBJECTS;

				}
			} catch (AkuraAppException e) {
				if (e.getCause() instanceof DataIntegrityViolationException) {
					String message = new ErrorMsgLoader()
							.getErrorMessage(ERROR_MSG_SUBJECT_DESCRIPTION);
					Subject addSubject = new Subject();
					model.addAttribute(EDITPANE, EDITPANE);
					model.addAttribute(SELECTED_OBJ_ID, subject.getSubjectId());
					model.addAttribute(MODEL_ATT_SUBJECT, addSubject);
					model.addAttribute(MODEL_ATT_MESSAGE, message);

					return VIEW_ADMIN_MANAGE_SUBJECTS;
				} else {
					throw e;
				}
			}
		}
	}

	/**
	 * Deletes the relevant Subject object.
	 * 
	 * @param model
	 *            - a HashMap that contains information of the Subject
	 * @param deleteSubject
	 *            - the releveant instance of Subject
	 * @return - name of the view which is redirected to
	 * @throws AkuraAppException
	 *             - The exception details that occurred when deleting a
	 *             Subject.
	 */
	@RequestMapping(REQ_VALUE_DELETE_SUBJECT)
	public final String deleteSubject(
			@ModelAttribute(MODEL_ATT_SUBJECT) final Subject deleteSubject,
			final ModelMap model) throws AkuraAppException {

		// String message = MODEL_ATT_MESSAGE;
		List<Stream> streamList = commonService.getStreamList();
		List<Subject> subjectList = commonService.getSubjectList();
		try {
			int id = deleteSubject.getSubjectId();
			Subject findSubject = commonService.findSubject(id);
			commonService.deleteSubject(findSubject);

			return VIEW_REDIRECT_MANAGE_SUBJECTS;

		} catch (AkuraAppException e) {
			if (e.getCause() instanceof DataIntegrityViolationException) {
				String message = new ErrorMsgLoader()
						.getErrorMessage(ERROR_MSG_SUBJECT_DELETE);
				model.addAttribute(MODEL_ATT_MESSAGE, message);
				Subject newSubject = new Subject();
				model.addAttribute(MODEL_ATT_STREAM_LIST,
						SortUtil.sortStreamList(streamList));
				model.addAttribute(MODEL_ATT_SUBJECT_LIST,
						SortUtil.sortSubjectList(subjectList));
				model.addAttribute(MODEL_ATT_SUBJECT, newSubject);

				return VIEW_ADMIN_MANAGE_SUBJECTS;

			} else {
				throw e;
			}
		}
	}

	/**
	 * Initializes the reference data that is to be previewed on the UI.
	 * 
	 * @param map
	 *            - a HashMap that contains information of the Stream
	 * @return - name of the view which is redirected to
	 * @throws AkuraAppException
	 *             - The exception details that occurred when processing.
	 */
	@RequestMapping(value = REQ_VALUE_MANAGE_STREAMS)
	public final String referenceModuleStream(final ModelMap map)
			throws AkuraAppException {

		try {
			Stream stream = new Stream();
			List<Stream> streamList = commonService.getStreamList();
			map.addAttribute(MODEL_ATT_STREAM_LIST,
					SortUtil.sortStreamList(streamList));
			map.addAttribute(MODEL_ATT_STREAM, stream);

			return VIEW_ADMIN_MANAGE_STREAMS;
		} catch (AkuraAppException e) {
			throw e;
		}
	}

	/**
	 * Searches results for the given stream criteria.
	 * 
	 * @param request
	 *            - an object of HttpServletRequest
	 * @param map
	 *            - a HashMap that contains information of the Stream
	 * @param stream
	 *            - the releveant instance of Stream
	 * @return - name of the view which is redirected to
	 * @throws AkuraAppException
	 *             - The exception details that occurred when searching Stream
	 *             instances.
	 */
	@RequestMapping(value = REQ_VALUE_SEARCH_STREAM, method = RequestMethod.POST)
	public final String searchStream(final HttpServletRequest request,
			final ModelMap map,
			@ModelAttribute(MODEL_ATT_STREAM) final Stream stream)
			throws AkuraAppException {

		// String message = MODEL_ATT_MESSAGE;
		try {
			String searchDescription = request.getParameter(
					REQ_SEARCH_DESCRIPTION).trim();
			List<Stream> searchStreamList = commonService
					.searchStream(searchDescription);

			if (searchStreamList.isEmpty()) {
				String message = new ErrorMsgLoader()
						.getErrorMessage(ERROR_MSG_STR_SEARCH_NO_RESULT);
				map.addAttribute(MODEL_ATT_MESSAGE, message);
			} else {
				map.addAttribute(MODEL_ATT_SEARCH_STREAM, searchStreamList);
				map.addAttribute(SEARCH_DESCRIPTION, searchDescription);
			}
			return VIEW_ADMIN_MANAGE_STREAMS;
		} catch (AkuraAppException e) {
			throw e;
		}
	}

	/**
	 * Saves or updates the relevant Stream object.
	 * 
	 * @param model
	 *            - a HashMap that contains information of the Stream
	 * @param stream
	 *            - the releveant instance of Stream
	 * @param result
	 *            - containing list of errors and Stream instance to which data
	 *            was bound
	 * @return - name of the view which is redirected to
	 * @throws AkuraAppException
	 *             - The exception details that occurred when saving or updating
	 *             a Stream instance.
	 */
	@RequestMapping(value = REQ_VALUE_SAVE_OR_UPDATE_STREAM, method = RequestMethod.POST)
	public final String saveOrUpdateStream(final ModelMap model,
			@ModelAttribute(MODEL_ATT_STREAM) final Stream stream,
			BindingResult result) throws AkuraAppException {

		// String message = MODEL_ATT_MESSAGE;
		List<Stream> streamList = commonService.getStreamList();
		model.addAttribute(MODEL_ATT_STREAM_LIST,
				SortUtil.sortStreamList(streamList));
		streamValidator.validate(stream, result);

		if (result.hasErrors()) {
			model.addAttribute(EDITPANE, EDITPANE);
			model.addAttribute(SELECTED_OBJ_ID, stream.getStreamId());
			return VIEW_ADMIN_MANAGE_STREAMS;
		} else {
			String description = stream.getDescription().trim();
			// uses when edit the stream data. if the stream id is
			// greater than zero, updates it.
			int id = stream.getStreamId();
			try {
				if (id > 0) {
					Stream findStream = commonService.findStream(id);
					findStream.setDescription(description);
					commonService.updateStream(findStream);
					return VIEW_REDIRECT_MANAGE_STREAMS;

				} else {
					stream.setDescription(description);
					commonService.addStream(stream);
					return VIEW_REDIRECT_MANAGE_STREAMS;

				}
			} catch (AkuraAppException e) {
				if (e.getCause() instanceof DataIntegrityViolationException) {
					String message = new ErrorMsgLoader()
							.getErrorMessage(ERROR_MSG_STREAM_DESCRIPTION);
					Stream addStream = new Stream();
					model.addAttribute(EDITPANE, EDITPANE);
					model.addAttribute(SELECTED_OBJ_ID, stream.getStreamId());
					model.addAttribute(MODEL_ATT_STREAM, addStream);
					model.addAttribute(MODEL_ATT_MESSAGE, message);
					return VIEW_ADMIN_MANAGE_STREAMS;
				} else {
					throw e;
				}
			}
		}
	}

	/**
	 * Deletes the relevant Stream object.
	 * 
	 * @param request
	 *            - an object of HttpServletRequest
	 * @param model
	 *            - a HashMap that contains information of the Stream
	 * @param deleteStream
	 *            - the releveant instance of Stream
	 * @return - name of the view which is redirected to
	 * @throws AkuraAppException
	 *             - The exception details that occurred when deleting a Stream.
	 */
	@RequestMapping(REQ_VALUE_DELETE_STREAM)
	public final String deleteStream(HttpServletRequest request,
			@ModelAttribute(MODEL_ATT_STREAM) final Stream deleteStream,
			final ModelMap model) throws AkuraAppException {

		// message = MODEL_ATT_MESSAGE;
		try {
			int id = deleteStream.getStreamId();
			Stream findStream = commonService.findStream(id);
			commonService.deleteStream(findStream);

		} catch (AkuraAppException e) {
			if (e.getCause() instanceof DataIntegrityViolationException) {
				String message = new ErrorMsgLoader()
						.getErrorMessage(ERROR_STREAM_DELETE);
				Stream newStream = new Stream();
				List<Stream> streamList = commonService.getStreamList();
				model.addAttribute(MODEL_ATT_MESSAGE, message);
				model.addAttribute(MODEL_ATT_STREAM, newStream);
				model.addAttribute(MODEL_ATT_STREAM_LIST,
						SortUtil.sortStreamList(streamList));

			} else {
				throw e;
			}
		}
		// table must fill with previous search criteria
		return this.searchStream(request, model, deleteStream);
	}

	/**
	 * Registers the given custom property editor for all properties of the Date
	 * type.
	 * 
	 * @param binder
	 *            - data binder used to register the Date objects.
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {

		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	/**
	 * Set the page size.
	 * 
	 * @param noOfRecords
	 *            - Contains number of records.
	 * @param countryList
	 *            - CountryList
	 * @param starFrom
	 *            - Contains start value
	 * @param country
	 *            - Contains country object
	 * @return - returns list of countries
	 * @throws AkuraAppException
	 *             - The exception details that occurred when returning
	 *             countryList
	 */
	private List<Country> setPageSize(final int noOfRecords,
			final List<Country> countryList, final String starFrom,
			final Country country) throws AkuraAppException {
		String strFrom = starFrom;

		int startFrom = Integer
				.parseInt(strFrom == null ? AkuraWebConstant.STRING_ZERO
						: strFrom);

		int endNumber = startFrom + AkuraWebConstant.PAGE_SIZE;

		int numberOfRecords;

		numberOfRecords = noOfRecords;

		int maxNumber;

		if (numberOfRecords < endNumber) {
			maxNumber = numberOfRecords;
		} else {
			maxNumber = endNumber;
		}

		country.setMaxNumber(maxNumber);
		country.setStartFrom(startFrom);

		List<Country> searchSortCountry = SortUtil.sortCountryList(countryList);
		List<Country> searchReultsForPage = searchSortCountry.subList(
				startFrom, maxNumber);

		return searchReultsForPage;

	}

}
