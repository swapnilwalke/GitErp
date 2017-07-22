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

package com.virtusa.akura.staff.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * This is a Validator for Staff object which validates property description is null/empty and it should
 * contain only alphabet characters and space between words.
 * 
 * @author Virtusa Corporation
 */
public class StaffDetailsValidator implements Validator {
    
    /** key to hold pattern of image. */
    private static final String PATTERN_IMAGE = "STAFF.STAFFDETAILS.VALIDATOR.IMAGE";
    
    /** key to hold pattern of salary. */
    private static final String PATTERN_DIGITS = "[^0-9.]";
    
    /** key to hold pattern of string name with initial. */
    private static final String PATTERN_NAME_WITH_INITIAL = "STAFF.STAFFDETAILS.VALIDATOR.NAMEWTINITIAL";
    
    /** key to hold pattern of string name. */
    private static final String PATTERN_NAME = "STAFF.STAFFDETAILS.VALIDATOR.NAME";
    
    /** key to hold pattern of string email address. */
    private static final String PATTERN_EMAIL = "STAFF.STAFFDETAILS.VALIDATOR.EMAIL";
    
    /** key to hold field multipart file. */
    private static final String MULTIPART_FILE = "multipartFile";
    
    /** key to hold field basic salary. */
    private static final String BASIC_SALARY = "basicSalary";
    
    /** key to hold field email. */
    private static final String EMAIL = "email";
    
    /** key to hold field name with initial. */
    private static final String NAME_WITH_INTIALS = "nameWithIntials";
    
    /** key to hold field last name. */
    private static final String LAST_NAME = "lastName";
    
    /** key to hold field full name. */
    private static final String FULL_NAME = "fullName";
    
    /** key to hold field emergency mobile contact name. */
    private static final String EMERGENCY_CONTACT_NAME = "emergencyContactName";
    
    /** key to hold field staff id. */
    private static final String STAFF_ID = "staffId";
    
    /** key to hold field registrationNo. */
    private static final String REGISTRATION_NO = "registrationNo";
    
    /** key to hold pattern of string phone numbers. */
    private static final String PATTERN_PHONE = "[^0-9]";
    
    /** value to change KB to MB. */
    private static final int IMAGE_SIZE = 1048576;
    
    /**
     * min size of the image.
     */
    private static final int MINI_IMAGE_SIZE = 0;
    
    /** key to hold pattern of admission number field. */
    private static final String PATTERN_REG_NO = "STAFF.STAFFDETAILS.VALIDATOR.REGNO";
    
    /** Error message is to display when the contact details residence field mismatch. */
    public static final String CR_MISMATCH_ERROR = "STA.UI.CR.NO";
    
    /** Error message is to display when the contact details mobile field mismatch. */
    public static final String CM_MISMATCH_ERROR = "STA.UI.CM.NO";
    
    /** Error message is to display when the emergency contact details mobile field mismatch. */
    public static final String EM_MISMATCH_ERROR = "STA.UI.EM.NO";
    
    /** Error message is to display when the emergency contact details residence field mismatch. */
    public static final String ER_MISMATCH_ERROR = "STA.UI.ER.NO";
    
    /**
     * Error message is to display when both contact details residence and contact details mobile fields
     * mismatch.
     */
    public static final String CR_MISMATCH_ERROR_CM_MISMATCH_ERROR = "STA.UI.CR.NO.STA.UI.CM.NO";
    
    /**
     * Error message is to display when both contact details residence and emergency contact details residence
     * fields mismatch.
     */
    public static final String CR_MISMATCH_ERROR_ER_MISMATCH_ERROR = "STA.UI.CR.NO.STA.UI.ER.NO";
    
    /**
     * Error message is to display when both contact details residence and emergency contact details mobile
     * fields mismatch.
     */
    public static final String CR_MISMATCH_ERROR_EM_MISMATCH_ERROR = "STA.UI.CR.NO.STA.UI.EM.NO";
    
    /**
     * Error message is to display when both contact details mobile and emergency contact details residence
     * fields mismatch.
     */
    public static final String CM_MISMATCH_ERROR_ER_MISMATCH_ERROR = "STA.UI.CM.NO.STA.UI.ER.NO";
    
    /**
     * Error message is to display when both contact details mobile and emergency contact details mobile
     * fields mismatch.
     */
    public static final String CM_MISMATCH_ERROR_EM_MISMATCH_ERROR = "STA.UI.CM.NO.STA.UI.EM.NO";
    
    /**
     * Error message is to display when both emergency contact details residence and emergency contact details
     * mobile fields mismatch.
     */
    public static final String ER_MISMATCH_ERROR_EM_MISMATCH_ERROR = "STA.UI.ER.NO.STA.UI.EM.NO";
    
    /**
     * Error message is to display when contact details residence , contact details mobile and emergency
     * contact details residence fields mismatch.
     */
    public static final String CR_MISMATCH_ERROR_CM_MISMATCH_ERROR_ER_MISMATCH_ERROR =
            "STA.UI.CR.NO.STA.UI.CM.NO.STA.UI.ER.NO";
    
    /**
     * Error message is to display when contact details residence , contact details mobile and emergency
     * contact details mobile fields mismatch.
     */
    public static final String CR_MISMATCH_ERROR_CM_MISMATCH_ERROR_EM_MISMATCH_ERROR =
            "STA.UI.CR.NO.STA.UI.CM.NO.STA.UI.EM.NO";
    
    /**
     * Error message is to display when contact details mobile , emergency contact details residence and
     * emergency contact details mobile fields mismatch.
     */
    public static final String CM_MISMATCH_ERROR_ER_MISMATCH_ERROR_EM_MISMATCH_ERROR =
            "STA.UI.CM.NO.STA.UI.ER.NO.STA.UI.EM.NO";
    
    /**
     * Error message is to display when contact details mobile , emergency contact details residence and
     * emergency contact details mobile fields mismatch.
     */
    public static final String CM_MISMATCH_ERROR_ER_MISMATCH_ERROR_ER_MISMATCH_ERROR =
            "STA.UI.CM.NO.STA.UI.ER.NO.STA.UI.ER.NO";
    
    /** Error message is to display when all phone number types are invalid. */
    public static final String ALL_PHONE_NUMBERS_MISMATCH_ERROR = "STA.UI.All.PHONE.NUMBERS.NO.FIELD.TYPE";
    
    /** key to hold field residence No. */
    private static final String RESIDENCE_NUMBER = "residenceNo";
    
    /**
     * Staff is the class of the actual object instance that is to be validated.
     * 
     * @param clazz - the Class that this Validator is being asked if it can validate
     * @return - true if this Validator can indeed validate instances of the supplied clazz
     */
    public boolean supports(Class<?> clazz) {
    
        return Staff.class.isAssignableFrom(clazz);
    }
    
    /**
     * Validate the supplied object.
     * 
     * @param object - the object that is to be validated
     * @param errors - contextual state about the validation process
     */
    public void validate(Object object, Errors errors) {
    
        Staff staff = (Staff) object;
        
        String emailPattern = ValidatorExpressionUtil.getValidatorPattern(PATTERN_EMAIL);
        String namePattern = ValidatorExpressionUtil.getValidatorPattern(PATTERN_NAME);
        String nameWithInitialsPattern = ValidatorExpressionUtil.getValidatorPattern(PATTERN_NAME_WITH_INITIAL);
        String regNoPattern = ValidatorExpressionUtil.getValidatorPattern(PATTERN_REG_NO);
        
        if (staff.getRegistrationNo().isEmpty() || staff.getFullName().isEmpty()
                || staff.getNameWithIntials().isEmpty() || staff.getLastName().isEmpty()
                || staff.getStaffCategory() == null || staff.getDateOfHire() == null
                || staff.getRegistrationNo().isEmpty() || staff.getNationalID().trim().isEmpty()
                || staff.getStaffCategory().getCatogaryID() == 0
                || (staff.getGender() != 'M' && staff.getGender() != 'F')) {
            errors.rejectValue(STAFF_ID, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
            
        }
        
        // Validate staff registration number
        if (!staff.getRegistrationNo().trim().isEmpty() && !staff.getRegistrationNo().trim().matches(regNoPattern)) {
            errors.rejectValue(REGISTRATION_NO, AkuraWebConstant.INVALID_STAFF_REG_NO);
        }
        
        // validate the Full Name
        if (!staff.getFullName().trim().isEmpty() && !staff.getFullName().trim().matches(namePattern)) {
            errors.rejectValue(FULL_NAME, AkuraWebConstant.FULL_NAME_MISMATCH_ERROR);
        }
        
        // validate the LastName
        if (!staff.getLastName().trim().isEmpty() && !staff.getLastName().trim().matches(namePattern)) {
            errors.rejectValue(LAST_NAME, AkuraWebConstant.LAST_NAME_MISMATCH_ERROR);
        }
        // validate the NameWithIntials
        if (!staff.getNameWithIntials().trim().isEmpty()
                && !staff.getNameWithIntials().trim().matches(nameWithInitialsPattern)) {
            errors.rejectValue(NAME_WITH_INTIALS, AkuraWebConstant.NAME_WITH_INITIAL_MISMATCH_ERROR);
        }
        
        // validate the email
        if (!staff.getEmail().trim().isEmpty() && !staff.getEmail().matches(emailPattern)) {
            errors.rejectValue(EMAIL, AkuraWebConstant.EMAIL_MISMATCH_ERROR);
        }
        // validate emergency contact name
        if (!staff.getEmergencyContactName().trim().isEmpty() 
                && !staff.getEmergencyContactName().matches(namePattern)) {
            errors.rejectValue(EMERGENCY_CONTACT_NAME, AkuraWebConstant.NAME_WITH_INITIAL_MISMATCH_ERROR);
        }
        
        // validate the basic salary
        Pattern digitsOnly = Pattern.compile(PATTERN_DIGITS);
        Matcher makeMatch2 = digitsOnly.matcher(String.valueOf(staff.getBasicSalary()));
        if (makeMatch2.find()) {
            errors.rejectValue(BASIC_SALARY, AkuraWebConstant.BASIC_SALARY_MISMATCH_ERROR);
        }
        
        // validate the Phone Number
        Pattern digitsAndSpacesOnly = Pattern.compile(PATTERN_PHONE);
        Matcher makeMatch3 = digitsAndSpacesOnly.matcher(String.valueOf(staff.getResidenceNo()));
        Matcher makeMatch4 = digitsAndSpacesOnly.matcher(String.valueOf(staff.getMobileNo()));
        Matcher makeMatch5 = digitsAndSpacesOnly.matcher(String.valueOf(staff.getEmergencyContactResidenceNo()));
        Matcher makeMatch6 = digitsAndSpacesOnly.matcher(String.valueOf(staff.getEmergencyContactMobileNo()));
        if (makeMatch3.find() || makeMatch4.find() || makeMatch5.find() || makeMatch6.find()) {
            errors.rejectValue(RESIDENCE_NUMBER, AkuraWebConstant.COUNTRY_PHONE_NUMBER_MISMATCH_ERROR);
        }
        
        // validate the profile image of staff
        if (staff.getMultipartFile() != null && staff.getMultipartFile().getSize() > MINI_IMAGE_SIZE) {
            String image = staff.getMultipartFile().getOriginalFilename();
            if (image != "") {
                String checkimg = image.toLowerCase();
                String imagePattern = ValidatorExpressionUtil.getValidatorPattern(PATTERN_IMAGE);
                
                Pattern pattern = Pattern.compile(imagePattern);
                Matcher matcher = pattern.matcher(checkimg);
                if (!matcher.matches()) {
                    errors.rejectValue(MULTIPART_FILE, AkuraWebConstant.FILE_EMPTY_ERROR,
                            AkuraWebConstant.IMAGE_MISMATCH_ERROR);
                } else {
                    if (staff.getMultipartFile().getSize() > IMAGE_SIZE) {
                        errors.rejectValue(MULTIPART_FILE, AkuraWebConstant.FILE_EMPTY_ERROR,
                                AkuraWebConstant.IMAGE_FILE_SIZE_ERROR);
                    }
                }
            }
        }
    }
    
}
