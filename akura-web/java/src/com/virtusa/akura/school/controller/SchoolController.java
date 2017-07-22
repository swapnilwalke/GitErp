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

package com.virtusa.akura.school.controller;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

import com.virtusa.akura.api.dto.Country;
import com.virtusa.akura.api.dto.District;
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.dto.School;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.school.service.SchoolService;
import com.virtusa.akura.school.validator.SchoolValidator;
import com.virtusa.akura.staff.service.StaffService;
import com.virtusa.akura.student.service.StudentService;
import com.virtusa.akura.util.PhoneNumberValidateUtil;
import com.virtusa.akura.util.SortUtil;

/**
 * SchoolController is to handle Add/Edit/Delete/Search View operations for Subject,Stream in school module.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class SchoolController {
    
    /** View post method AdminWelcome. */
    private static final String VIEW_REDIRECT_ADMIN_WELCOME = "redirect:adminWelcome.htm";
    
    /** View get method Admin/SchoolModule. */
    private static final String VIEW_ADMIN_SCHOOL_MODULE = "school/schoolModule";
    
    /** View get method Admin/SchoolDetail. */
    private static final String VIEW_ADMIN_SCHOOL_DETAIL = "school/schoolDetail";
    
    /** String attribute for date format. */
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    
    /** Model attribute of School. */
    private static final String MODEL_ATT_SCHOOL = "school";
    
    /** Model attribute of ProvinceList. */
    private static final String MODEL_ATT_PROVINCE_LIST = "provinceList";
    
    /** Model attribute of DistrictList. */
    private static final String MODEL_ATT_DISTRICT_LIST = "districtList";
    
    /** Model attribute of StaffList. */
    private static final String MODEL_ATT_STAFF_LIST = "staffList";
    
    /** Model attribute of EditSchool. */
    private static final String MODEL_ATT_EDIT_SCHOOL = "editSchool";
    
    /** Model attribute of countryList. */
    private static final String COUNTRY_LIST = "countryList";
    
    /** Model attribute of VicePrinciple. */
    private static final String MODEL_ATT_VICE_PRINCIPAL = "vicePrincipal";
    
    /** Model attribute of Principle. */
    private static final String MODEL_ATT_PRINCIPAL = "principal";
    
    /** Request mapping value for Welcome. */
    private static final String REQ_VALUE_WELCOME = "/welcome.htm";
    
    /** Request mapping value for SchoolModule. */
    private static final String REQ_VALUE_SCHOOL_MODULE = "/schoolModule.htm";
    
    /** Request mapping value for SaveOrUpdateSchool. */
    private static final String REQ_VALUE_SAVE_OR_UPDATE_SCHOOL = "/saveOrUpdateSchool.htm";
    
    /** Request mapping value for SchoolDetail. */
    private static final String REQ_VALUE_SCHOOL_DETAIL = "/schoolDetail.htm";
    
    /** Represents the model value for successful addition. */
    private static final String SUCCESSFULLY_ADDED = "REF.UI.SCHOOL.SUCCESSFULLY_ADDED";
    
    /** Represents the model value for successful edition. */
    private static final String SUCCESSFULLY_EDITED = "REF.UI.SCHOOL.SUCCESSFULLY_EDITED";
    
    /** Represents the model attribute name for the message. */
    private static final String MESSAGE = "message";
    
    /** Represents the key for the error messages. */
    private static final String MODEL_ERROR = "modelError";
    
    /** key to hold message when record added. */
    private static final String MESSAGE_PHONE_NO_COUNTRY_MISMATCH = "STAFF.PHONE.COUNTRY.NO.MATCH";
    
    /** The Constant SELECTED_CLASS. */
    private static final String SELECTED_COUNTRYCODE_PHONE = "selectedCountryCodePhone";
    
    /** The Constant SELECTED_CLASS. */
    private static final String SELECTED_COUNTRYCODE_FAX = "selectedCountryCodeFax";
    
    /** The String of Model attribute. */
    private static final String MODEL_ATT_SELECTED_PHONE_COUNTRY = "selectedPhoneCountry";
    
    /** The String of Model attribute. */
    private static final String MODEL_ATT_SELECTED_FAX_COUNTRY = "selectedFaxCountry";
    
    /** The Constant COUNTRY_LIST. */
    private static final String PHONE_COUNTRY_LIST = "countryListPhone";
    
    /**
     * Represents an instance of the SchoolService.
     */
    private SchoolService schoolService;
    
    /**
     * Represents an instance of the CommonService.
     */
    private CommonService commonService;
    
    /**
     * Represents an instance of the SchoolValidator.
     */
    private SchoolValidator schoolValidator;
    
    /**
     * Represents an instance of the StaffService.
     */
    private StaffService staffService;
    
    /**
     * Represents an instance of the StudentService.
     */
    private StudentService studentService;
    
    /**
     * Setter method for the SchoolService.
     * 
     * @param schoolServiceVal - the instance of the SchoolService
     */
    public final void setSchoolService(final SchoolService schoolServiceVal) {
    
        schoolService = schoolServiceVal;
    }
    
    /**
     * Sets an instance of SchoolValidator.
     * 
     * @param schoolValidatorVal - the instance of the SchoolValidator
     */
    public final void setSchoolValidator(final SchoolValidator schoolValidatorVal) {
    
        schoolValidator = schoolValidatorVal;
    }
    
    /**
     * Sets an instance of CommonService.
     * 
     * @param commonServiceVal - the instance of the CommonService
     */
    public final void setCommonService(final CommonService commonServiceVal) {
    
        commonService = commonServiceVal;
    }
    
    /**
     * Sets an instance of StaffService.
     * 
     * @param staffServiceVal - the instance of the staffService
     */
    public final void setStaffService(final StaffService staffServiceVal) {
    
        staffService = staffServiceVal;
    }
    
    /**
     * Sets an instance of StudentService.
     * 
     * @param studentServiceVal - the instance of the studentService
     */
    public void setStudentService(StudentService studentServiceVal) {
    
        this.studentService = studentServiceVal;
    }
    
    /**
     * Initializes the reference data that is to be previewed on the UI.If school details are not empty
     * displays that detatils.
     * 
     * @param map - a HashMap that contains information of the School
     * @return - name of the view which is redirected to.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(value = REQ_VALUE_SCHOOL_DETAIL)
    public final String referenceModuleSchool(final ModelMap map) throws AkuraAppException {
    
        try {
            
            School school = new School();
            List<School> schoolList = schoolService.getSchoolList();
            List<Province> provinceList = commonService.getProvinceList();
            List<District> districtList = commonService.getDistrictList();
            Collection<Staff> staffList =
                    SortUtil.sortStaffListByLastName(staffService.getStaffByType(true, new Date()));
            
            setStudentsAndStaffCount(school);
            
            if (!schoolList.isEmpty()) {
                School findSchool = schoolList.get(0);
                for (Staff staff : staffList) {
                    
                    if (staff.getStaffId() == findSchool.getPrincipalId()) {
                        map.addAttribute(MODEL_ATT_PRINCIPAL, staff.getNameWithIntials());
                    }
                    if ((findSchool.getVicePrincipalId() != null)
                            && (staff.getStaffId() == findSchool.getVicePrincipalId().intValue())) {
                        map.addAttribute(MODEL_ATT_VICE_PRINCIPAL, staff.getNameWithIntials());
                    }
                }
                map.addAttribute(MODEL_ATT_EDIT_SCHOOL, findSchool);
                displayPhoneNumberDetails( findSchool, map);
            }
            map.addAttribute(MODEL_ATT_STAFF_LIST, staffList);
            map.addAttribute(MODEL_ATT_DISTRICT_LIST, districtList);
            map.addAttribute(MODEL_ATT_PROVINCE_LIST, provinceList);
            displayPhoneNumberDetails( school, map);
            map.addAttribute(MODEL_ATT_SCHOOL, school);
            
            return VIEW_ADMIN_SCHOOL_DETAIL;
        } catch (AkuraAppException e) {
            throw e;
        }
    }
    
    /**
     * Registers the given custom property editor for all properties of the Date type.
     * 
     * @param binder - data binder used to register the Date objects.
     */
    @InitBinder
    public final void initBinder(final WebDataBinder binder) {
    
        binder.getBindingResult();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    
    /**
     * Saves or updates the relevant School object.
     * 
     * @param model - a HashMap that contains information of the School
     * @param school - the releveant instance of School
     * @param result - containing list of errors and School instance to which data was bound
     * @param request - Request
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when saving a School instance.
     */
    @RequestMapping(value = REQ_VALUE_SAVE_OR_UPDATE_SCHOOL, method = RequestMethod.POST)
    public final String saveSchool(final ModelMap model, @ModelAttribute(MODEL_ATT_SCHOOL) final School school,
            final BindingResult result,HttpServletRequest request) throws AkuraAppException {
        String  selectedCounteryCodePhone=request.getParameter(SELECTED_COUNTRYCODE_PHONE);
        String  selectedCounteryCodeFax=request.getParameter(SELECTED_COUNTRYCODE_FAX);
        try {
            List<Province> provinceList = commonService.getProvinceList();
            List<District> districtList = commonService.getDistrictList();
            Collection<Staff> staffList =
                    SortUtil.sortStaffListByLastName(staffService.getStaffByType(true, new Date()));
            
            model.addAttribute(MODEL_ATT_STAFF_LIST, staffList);
            model.addAttribute(MODEL_ATT_DISTRICT_LIST, districtList);
            model.addAttribute(MODEL_ATT_PROVINCE_LIST, provinceList);
            school.setSchoolId(school.getSchoolId());
            
            setStudentsAndStaffCount(school);
            
            schoolValidator.validate(school, result);
            if (result.hasErrors()) {
                resetCountryFlags(selectedCounteryCodePhone, selectedCounteryCodeFax, model);
                return VIEW_ADMIN_SCHOOL_DETAIL;
            } else {
                
                int countryId = school.getCountry().getCountryId();
                Country country = commonService.findCountry(countryId);
                
                int provinceId = school.getProvince().getProvinceId();
                Province province = null; // not mandatory
                if (provinceId > 0) {
                    province = commonService.findProvince(provinceId);
                }
                int districtId = school.getDistrict().getDistrictId();
                District district = null; // not mandatory
                if (districtId > 0) {
                    district = commonService.findDistrict(districtId);
                }
                
                school.setName(school.getName().trim().replaceAll("( )+", " "));

                if (!school.getContactNo().isEmpty() && !selectedCounteryCodePhone.isEmpty()) {
                    if (school.getContactNo() != null && !selectedCounteryCodePhone.equals(AkuraConstant.STRING_ZERO) 
                            && PhoneNumberValidateUtil.isValidPhoneNumber(school.getContactNo(), 
                            selectedCounteryCodePhone)) {
                            displayResPhoneNumberDetails(school,selectedCounteryCodePhone);
                    } else {
                        displayCountryFlagsWhenError(school, model, selectedCounteryCodePhone,
                                selectedCounteryCodeFax);
                        return VIEW_ADMIN_SCHOOL_DETAIL;
                    }
                }
         
                if (!school.getFaxNo().isEmpty() && !selectedCounteryCodeFax.isEmpty()) {
                    if (school.getFaxNo() != null && !selectedCounteryCodeFax.equals(AkuraConstant.STRING_ZERO)
                            && PhoneNumberValidateUtil.isValidPhoneNumber(school.getFaxNo(), selectedCounteryCodeFax)) {
                        displayFaxNumberDetails(school,selectedCounteryCodeFax);
                    } else {
                        displayCountryFlagsWhenError(school, model, selectedCounteryCodePhone,
                                selectedCounteryCodeFax);
                        return VIEW_ADMIN_SCHOOL_DETAIL;
                    }
                }
                
                school.setWebsite(school.getWebsite().trim());
                school.setEmail(school.getEmail().trim());
                school.setAddress(school.getAddress().trim().replaceAll("( )+", " "));
                school.setFacilities(school.getFacilities().trim().replaceAll("( )+", " "));
                school.setPrimaryAndSecondarySchoolInfo(school.getPrimaryAndSecondarySchoolInfo().trim()
                        .replaceAll("( )+", " "));
                school.setDistrict(district);
                school.setProvince(province);
                school.setCountry(country);
                
                ErrorMsgLoader errorLoder = new ErrorMsgLoader();
                if (school.getSchoolId() > 0) {
                    schoolService.updateSchool(school);
                    model.addAttribute(MESSAGE, errorLoder.getErrorMessage(SUCCESSFULLY_EDITED));
                } else {
                    if (schoolService.getSchoolList().isEmpty()) {
                        schoolService.addSchool(school);
                        model.addAttribute(MESSAGE, errorLoder.getErrorMessage(SUCCESSFULLY_ADDED));
                    }
                }
            }
            displayPhoneNumberDetails(school, model);
            return VIEW_ADMIN_SCHOOL_DETAIL;
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Directs to the School Module.
     * 
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(value = REQ_VALUE_SCHOOL_MODULE)
    public final String schoolModule() throws AkuraAppException {
    
        return VIEW_ADMIN_SCHOOL_MODULE;
    }
    
    /**
     * Directs to the Welcome page.
     * 
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(value = REQ_VALUE_WELCOME)
    public final String welcome() throws AkuraAppException {
    
        return VIEW_REDIRECT_ADMIN_WELCOME;
    }
    
    /**
     * Get list of available countries list.
     * 
     * @return list of countries.
     * @throws AkuraAppException -throw detailed exception.
     */
    @ModelAttribute(COUNTRY_LIST)
    public final List<Country> populateCountryList() throws AkuraAppException {
    
        return SortUtil.sortCountries((commonService.getCountryList()));
        
    }
    
    /**
     * This method will set the number of students and staff members in the school to {@link School} .
     * 
     * @param school - The school to be set the staff members and students count.
     * @throws AkuraAppException - The exception throws when failed to retrieve the students or staff counts.
     */
    private void setStudentsAndStaffCount(final School school) throws AkuraAppException {
    
        // Set the number of students in school.
        school.setNoOfStudents(studentService.getNumberOfStudntsInSchool());
        
        // Set the number of staff members in the school.
        school.setNoOfStaff(staffService.getNumberOfStaffInSchool());
    }
    
    /**
     * Reset the country flag combo boxes.
     * 
     * @param selectedCountryCodePhone - String for selected phone country code
     * @param selectedCountryCodeFax - String for selected fax country code
     * @param model - object for model error
     * @throws AkuraAppException - Throw detail exception when fails.
     */
    private void resetCountryFlags(String selectedCountryCodePhone, String selectedCountryCodeFax ,ModelMap model)
            throws AkuraAppException {
    
        
        model.addAttribute(MODEL_ATT_SELECTED_PHONE_COUNTRY,
                (selectedCountryCodePhone.isEmpty()) ? AkuraConstant.STRING_ZERO : selectedCountryCodePhone);
        model.addAttribute(MODEL_ATT_SELECTED_FAX_COUNTRY,
                (selectedCountryCodeFax.isEmpty()) ? AkuraConstant.STRING_ZERO : selectedCountryCodeFax);
    }
    
    /**
     * View the staff phone number validation details.
     * 
     * @param school - School related data object
     * @param model - {@link ModelMap}
     * @throws AkuraAppException - Throw detail exception when fails.
     */
    private void displayPhoneNumberDetails(School school, ModelMap model) throws AkuraAppException{
        
        if(school.getContactNo() != null){
            if(school.getContactNo().contains(AkuraConstant.EMPTY_STRING_SPACE)){
                String countryCode = school.getContactNo().
                        substring(0,school.getContactNo().indexOf(' ')).replace('+', ' ').trim();
                String resNumber = school.getContactNo().
                        substring(school.getContactNo().indexOf(' ')).replaceAll("\\s","");
                school.setContactNo((resNumber.startsWith(AkuraConstant.STRING_ZERO)) ? 
                        resNumber.substring(1): resNumber);
                model.addAttribute(MODEL_ATT_SELECTED_PHONE_COUNTRY, 
                        PhoneNumberValidateUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode)));
            }else{
                model.addAttribute(MODEL_ATT_SELECTED_PHONE_COUNTRY,0);
            }
        }
        
        if(school.getFaxNo() != null){
            if(school.getFaxNo().contains(AkuraConstant.EMPTY_STRING_SPACE)){
                String countryCode = school.getFaxNo().
                        substring(0,school.getFaxNo().indexOf(' ')).replace('+', ' ').trim();
                String resNumber = school.getFaxNo().
                        substring(school.getFaxNo().indexOf(' ')).replaceAll("\\s","");
                school.setFaxNo((resNumber.startsWith(AkuraConstant.STRING_ZERO)) ? 
                        resNumber.substring(1): resNumber);
                model.addAttribute(MODEL_ATT_SELECTED_FAX_COUNTRY, 
                        PhoneNumberValidateUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode)));
            }else{
                model.addAttribute(MODEL_ATT_SELECTED_FAX_COUNTRY,0);
            }
        }       
    } 
    
    /**
     * display  PhoneNumber Details.
     * 
     * @param school - School related data object
     * @param selectedCounteryCodePhone - String for selected phone country code
     * @throws AkuraAppException - Throw detail exception when fails.
     */
    private void displayResPhoneNumberDetails(School school, String selectedCounteryCodePhone)
            throws AkuraAppException {
    
        String resPhoneNumber = AkuraConstant.EMPTY_STRING;
        if (school.getContactNo().startsWith(AkuraConstant.STRING_ZERO)) {
            
            resPhoneNumber =
                    PhoneNumberValidateUtil.formatPhoneNumber(school.getContactNo(),
                            selectedCounteryCodePhone, AkuraConstant.PARAM_INDEX_ZERO);
            school.setContactNo(resPhoneNumber);
        } else {
            resPhoneNumber =
                    PhoneNumberValidateUtil.formatPhoneNumber(
                            AkuraConstant.STRING_ZERO + school.getContactNo(), selectedCounteryCodePhone
                            , AkuraConstant.PARAM_INDEX_ZERO);
            school.setContactNo(resPhoneNumber);
        }
        
    }
    /**
     * display  FaxNumber Details.
     * 
     * @param school - School related data object
     * @param selectedCounteryCodeFax - String for selected fax country code
     * @throws AkuraAppException - Throw detail exception when fails.
     */
    
    private void displayFaxNumberDetails(School school, String selectedCounteryCodeFax)
            throws AkuraAppException {
    
        String resPhoneNumber = AkuraConstant.EMPTY_STRING;
        if (school.getFaxNo().startsWith(AkuraConstant.STRING_ZERO)) {
            
            resPhoneNumber =
                    PhoneNumberValidateUtil.formatPhoneNumber(school.getFaxNo(),
                            selectedCounteryCodeFax, AkuraConstant.PARAM_INDEX_ZERO);
            school.setFaxNo(resPhoneNumber);
        } else {
            resPhoneNumber =
                    PhoneNumberValidateUtil.formatPhoneNumber(
                            AkuraConstant.STRING_ZERO + school.getFaxNo(), selectedCounteryCodeFax
                            , AkuraConstant.PARAM_INDEX_ZERO);
            school.setFaxNo(resPhoneNumber);
        }
        
    }
    
    /**
     * Get list of available countries list for .
     * 
     * @return map of country id and short code.
     * @throws AkuraAppException - Throw detail exception when fails.
     */
    @ModelAttribute(PHONE_COUNTRY_LIST)
    public Map<String, Country> populatePhoneCountryList() throws AkuraAppException {
    
        Map<String, Country> phoneMap = new LinkedHashMap<String, Country>();
        List<Country> countryList = null;
        countryList = SortUtil.sortCountries((commonService.getCountryList()));
        for (Country country : countryList) {
            
            if (country.getCountryCode() != null) {
                phoneMap.put(AkuraConstant.PLUS_SIGN 
                        + PhoneNumberValidateUtil.findCountryCode(country.getCountryCode()), country);
                
            }
        }
        return phoneMap;
    }
    
    /**
     * display Country Flags when error occurs.
     * 
     * @param school - School related data object
     * @param model model error
     * @param selectedCounteryCodePhone - String for selected phone country code  
     * @param selectedCounteryCodeFax - String for selected fax country code  
     * @throws AkuraAppException - Throw detail exception when fails.
     */
    private void displayCountryFlagsWhenError(School school, ModelMap model, String selectedCounteryCodePhone,
            String selectedCounteryCodeFax)
            throws AkuraAppException {
    
        String message = new ErrorMsgLoader().getErrorMessage(MESSAGE_PHONE_NO_COUNTRY_MISMATCH);
        model.addAttribute(MODEL_ERROR, message);
        displayPhoneNumberDetails(school, model);
        resetCountryFlags(selectedCounteryCodePhone, selectedCounteryCodeFax, model);
    }
}
