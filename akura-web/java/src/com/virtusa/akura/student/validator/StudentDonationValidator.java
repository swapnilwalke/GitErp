/*
 * < ÀKURA, This application manages the daily activities of a Teacher and a Student of a School>
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

import com.virtusa.akura.api.dto.Donation;
import com.virtusa.akura.api.dto.ParentWrapper;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * This validator class validates the fields of the donation domain object.
 * 
 * @author Virtusa Corporation
 */
public class StudentDonationValidator implements Validator {
    
    /** attribute for holding empty character. */
    //private static final String STRING_EMPTY = "";
    
    /** the field name to check. */
    private static final String DONATION_DONATION_TYPE_DONATION_TYPE_ID = "donation.donationType.donationTypeId";
    
    /** the field name to check. */
  //  private static final String DONATION_AMOUNT = "donation.amount";
    
    /** the field name to check. */
    private static final String DONATION_PURPOSE = "donation.purpose";
    
    /** the field name to check. */
    private static final String DONATION_DONATION_ID = "donation.donationId";
    
    /** Error message is to display when the purpose is not entered. */
    private static final String PURPOSE_MISMATCH_ERROR = "REF.UI.FIELD.TYPE";
    
    /** Error message is to display when the amount is not entered. */
  //  private static final String AMOUNT_MISMATCH_ERROR = "typeMismatch.donation.amount";
    
    /** Error message is to display when the mandatory information is not entered. */
    private static final String DONATION_TYPE_REQ = "PAR.UI.DONATION.MAND.DONATIONTYPE.REQUIRED";
    
    /**
     * Return whether or not this object can validate objects of the Donation class.
     * 
     * @param clazz - the class of the Donation
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> clazz) {
    
        return Donation.class.isAssignableFrom(clazz);
    }
    
    /**
     * Validates the object of the Donation.
     * 
     * @param target - Populated object of Donation to validate
     * @param errors - Errors object that is building. May contain errors for the fields relating to types.
     */
    public void validate(Object target, Errors errors) {
    
        ParentWrapper wrapperParent = (ParentWrapper) target;
        Donation donation = (Donation) wrapperParent.getDonation();
        
        Pattern stringOnly =
                Pattern.compile(ValidatorExpressionUtil.getValidatorPattern("PARENT_VALIDATOR.STRING_ONLY_PATTERN"));
        Matcher makeMatch = stringOnly.matcher(donation.getPurpose());
        
       /* Pattern digitsOnly =
                Pattern.compile(ValidatorExpressionUtil.getValidatorPattern("DONATION_VALIDATOR.DIGIT_ONLY_PATTERN"));
        Matcher makeMatch2 = digitsOnly.matcher(String.valueOf(STRING_EMPTY) + String.valueOf(donation.getAmount()));*/
        
        if (donation.getPurpose().trim().isEmpty() || 
                donation.getDonationType() == null || donation.getDate() == null) {
            errors.rejectValue(DONATION_DONATION_ID, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
            
        } else {
            if (makeMatch.find()) {
                errors.rejectValue(DONATION_PURPOSE, PURPOSE_MISMATCH_ERROR);
            }
         /*   if (makeMatch2.find()) {
                errors.rejectValue(DONATION_AMOUNT, AMOUNT_MISMATCH_ERROR);
            }*/
            if (donation.getDonationType() == null) {
                errors.rejectValue(DONATION_DONATION_TYPE_DONATION_TYPE_ID, DONATION_TYPE_REQ);
            }
        }
    }
    
}
