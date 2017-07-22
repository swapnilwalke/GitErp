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

package com.virtusa.akura.student.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.common.AkuraWebConstant;

/**
 * This class validates the fields of the Student domain object.
 *
 * @author Virtusa Corporation
 */
public class StudentDetailValidator implements Validator {

	/** The Constant STUDENT_TRAVEL_METHOD_OPTION3. */
    private static final int IMAGE_SIZE = 1048576;

    /** The Constant STUDENT_TRAVEL_METHOD_OPTION3. */
    private static final String STUDENT_TRAVEL_METHOD_OPTION3 = "STUDENT.TRAVEL.METHOD.OPTION3";

    /** The Constant STUDENT_TRAVEL_METHOD_OPTION2. */
    private static final String STUDENT_TRAVEL_METHOD_OPTION2 = "STUDENT.TRAVEL.METHOD.OPTION2";

    /** The Constant TRAVEL_ID3. */
    private static final String TRAVEL_ID3 = "travelId3";

    /** The Constant TRAVEL_ID2. */
    private static final String TRAVEL_ID2 = "travelId2";

    /** key to hold field profile image. */
    private static final String PROFILE_IMAGE = "studentId";

    /** key to hold field emergency contact email. */
    private static final String EMERGENCY_CONTACT_EMAIL_ADDRESS = "emergencyContactEmailAddress";

    /** key to hold field email. */
    private static final String EMAIL = "email";

    /** key to hold field last name. */
    private static final String LAST_NAME = "lastName";

    /** key to hold field name with initial. */
    private static final String NAME_WITH_INITIAL = "nameWtInitials";

    /** key to hold field full name. */
    private static final String FULL_NAME = "fullName";

    /** key to hold field admission number. */
    private static final String ADMISSION_NO = "admissionNo";

    /** key to hold field student id. */
    private static final String STUDENT_ID = "studentId";

    /** key to hold pattern of name with initial. */
    private static final String PATTERN_NAME_WITH_INITIAL = "[a-zA-Z. ]*";

    /** key to hold pattern of name. */
    private static final String PATTERN_NAME = "[a-zA-Z\\s]*";

    /** key to hold pattern of email field. */
    private static final String PATTERN_EMAIL =
            "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";

    /** key to hold pattern of admission number field. */
    private static final String PATTERN_ADDMISSION_NO = "[a-zA-Z0-9]*";

    /** key to hold pattern of admission number field. */
    private static final String PATTERN_STRING = "[a-zA-Z]*";

    /** key to hold pattern of image. */
    private static final String PATTERN_IMAGE = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";

    /** key to hold field name of "emergencyContactLastName". */
    private static final String EMERGENCY_CONTACT_LAST_NAME = "emergencyContactLastName";
    
    /** key to hold field name of "emergencyContactNameWtInitials". */
    private static final String EMERGENCY_CONTACT_NAME_WT_INITIALS = "emergencyContactNameWtInitials";
    
    /** key to hold field name of "emergencyContactFullName". */
    private static final String EMERGENCY_CONTACT_FULL_NAME = "emergencyContactFullName";
    
    /** key to hold pattern of string phone numbers. */
    private static final String PATTERN_PHONE = "[^0-9]";
    
    /** key to hold field residence No. */
    private static final String RESIDENCE_NUMBER = "residenceNo";
    

    /**
     * Return whether or not this object can validate objects of the BloodGroup class.
     *
     * @param clazz - the class of the BloodGroup
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> clazz) {

        return Student.class.isAssignableFrom(clazz);
    }

    /**
     * Validates the object of the Student.
     *
     * @param target - Populated object of Student to validate
     * @param errors - Errors object that is building. May contain errors for the fields relating to types.
     */
    public void validate(Object target, Errors errors) {

        Student student = (Student) target;

        if (student.getAdmissionNo().trim().isEmpty() || student.getFullName().trim().isEmpty()

        || student.getNameWtInitials().trim().isEmpty()
        || student.getLanguageId() == 0
        || student.getLastName().trim().isEmpty()
                || student.getFirstSchoolDay() == null || (student.getGender()!='M' && student.getGender()!='F')) {
            errors.rejectValue(STUDENT_ID, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        }

        if (student.getTravelId2() != null && (student.getTravelId().intValue() == student.getTravelId2().intValue())) {
            errors.rejectValue(TRAVEL_ID2, STUDENT_TRAVEL_METHOD_OPTION2);
        }

        if (student.getTravelId3() != null
                && ((student.getTravelId().intValue() == student.getTravelId3().intValue()) ||
                       (student.getTravelId2().intValue() == student.getTravelId3().intValue()))) {
            errors.rejectValue(TRAVEL_ID3, STUDENT_TRAVEL_METHOD_OPTION3);
        }

        String addmissionNumber = PATTERN_ADDMISSION_NO;
        String emailPattern = PATTERN_EMAIL;
        String studentEmail = student.getEmail();
        String namePattern = PATTERN_NAME;
        String nameWithInitialsPattern = PATTERN_NAME_WITH_INITIAL;
        String parish = PATTERN_STRING;

        if (!student.getAdmissionNo().trim().isEmpty() && !student.getAdmissionNo().trim().matches(addmissionNumber)) {
            errors.rejectValue(ADMISSION_NO, AkuraWebConstant.STUDENT_ADMISSION_NUMBER);
        }

        if (!student.getSiblingAdmitionNo().trim().isEmpty()
                && !student.getSiblingAdmitionNo().trim().matches(addmissionNumber)) {
            errors.rejectValue(ADMISSION_NO, AkuraWebConstant.STUDENT_SIBLING_ADMISSION_NUMBER);
        }

        if (!student.getParish().trim().isEmpty() && !student.getParish().trim().matches(parish)) {
            errors.rejectValue(ADMISSION_NO, AkuraWebConstant.PARISH_MISMATCH_ERROR);
        }

        if (!student.getFullName().trim().isEmpty() && !student.getFullName().trim().matches(namePattern)) {
            errors.rejectValue(FULL_NAME, AkuraWebConstant.FULL_NAME_MISMATCH_ERROR);
        }

        if (!student.getNameWtInitials().trim().isEmpty()
                && !student.getNameWtInitials().trim().matches(nameWithInitialsPattern)) {
            errors.rejectValue(NAME_WITH_INITIAL, AkuraWebConstant.NAME_WITH_INITIAL_MISMATCH_ERROR);

        }

        if (!student.getLastName().trim().isEmpty() && !student.getLastName().trim().matches(namePattern)) {
            errors.rejectValue(LAST_NAME, AkuraWebConstant.LAST_NAME_MISMATCH_ERROR);

        }
        
        if (!student.getEmergencyContactFullName().trim().isEmpty()
                && !student.getEmergencyContactFullName().trim().matches(namePattern)) {
            errors.rejectValue(EMERGENCY_CONTACT_FULL_NAME, AkuraWebConstant.FULL_NAME_MISMATCH_ERROR);
        }
        
        if (!student.getEmergencyContactNameWtInitials().trim().isEmpty()
                && !student.getEmergencyContactNameWtInitials().trim().matches(namePattern)) {
            errors.rejectValue(EMERGENCY_CONTACT_NAME_WT_INITIALS, AkuraWebConstant.NAME_WITH_INITIAL_MISMATCH_ERROR);
        }
        
        if (!student.getEmergencyContactLastName().trim().isEmpty()
                && !student.getEmergencyContactLastName().trim().matches(nameWithInitialsPattern)) {
            errors.rejectValue(EMERGENCY_CONTACT_LAST_NAME, AkuraWebConstant.LAST_NAME_MISMATCH_ERROR);
        }

		if ((!student.getEmergencyContactEmailAddress().trim().isEmpty() && !student
						.getEmergencyContactEmailAddress().trim().matches(emailPattern))) {
			errors.rejectValue(EMERGENCY_CONTACT_EMAIL_ADDRESS,AkuraWebConstant.EMAIL_MISMATCH_ERROR);
			
		} else if (!studentEmail.trim().isEmpty() && !studentEmail.matches(emailPattern)) {
			errors.rejectValue(EMAIL, AkuraWebConstant.EMAIL_MISMATCH_ERROR);
		}

        int minImageSize = 0;
        // validate the profile image of staff
        if (student.getMPhoto().getSize() > minImageSize) {
            String image = student.getMPhoto().getOriginalFilename();
            if (image != "") {
                String checkimg = image.toLowerCase();
                String imagePattern = PATTERN_IMAGE;

                Pattern pattern = Pattern.compile(imagePattern);
                Matcher matcher = pattern.matcher(checkimg);
                if (!matcher.matches()) {
                    errors.rejectValue(PROFILE_IMAGE, AkuraWebConstant.FILE_EMPTY_ERROR,
                            AkuraWebConstant.IMAGE_MISMATCH_ERROR);
                } else {
                    if (student.getMPhoto().getSize() > IMAGE_SIZE) {
                        errors.rejectValue(PROFILE_IMAGE, AkuraWebConstant.IMANGE_SIZE_ERROR,
                                AkuraWebConstant.IMAGE_FILE_SIZE_ERROR);
                    }
                }
            }
        }
        
        // validate the Phone Number
        Pattern digitsAndSpacesOnly = Pattern.compile(PATTERN_PHONE);
        Matcher makeMatch1 = digitsAndSpacesOnly.matcher(String.valueOf(student.getResidenceNo()));
        Matcher makeMatch2 = digitsAndSpacesOnly.matcher(String.valueOf(student.getMobileNo()));
        Matcher makeMatch3 = digitsAndSpacesOnly.matcher(String.valueOf(student.getEmergencyContactResidenceNo()));
        Matcher makeMatch4 = digitsAndSpacesOnly.matcher(String.valueOf(student.getEmergencyContactMobileNo()));
        Matcher makeMatch5 = digitsAndSpacesOnly.matcher(String.valueOf(student.getEmergencyContactOfficeNo()));
        if (makeMatch1.find() || makeMatch2.find() || makeMatch3.find() || makeMatch4.find() || makeMatch5.find()) {
            errors.rejectValue(RESIDENCE_NUMBER, AkuraWebConstant.COUNTRY_PHONE_NUMBER_MISMATCH_ERROR);
        }
    }
}
