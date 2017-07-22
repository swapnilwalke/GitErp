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

package com.virtusa.akura.common.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.UserSecurityQuestions;
import com.virtusa.akura.api.dto.WrapperSecurityQuestions;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * This validator class validates the fields of the SecurityQuestions.
 * 
 * @author Virtusa Corporation
 */
public class SecurityQuestionsValidator implements Validator {
    
    /** attribute for holding "USER.SECURITYQUESTIONS.ANSWERPATTERN.VALIDATOR". */
    private static final String SECURITYQUESTION_VALIDATOR_PATTERN = "USER.SECURITYQUESTIONS.ANSWERPATTERN.VALIDATOR";
    
    /**
     * Return whether or not this object can validate objects of the WrapperSecurityQuestions class.
     * 
     * @param clazz - the class of the WrapperSecurityQuestions
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> clazz) {
    
        return WrapperSecurityQuestions.class.isAssignableFrom(clazz);
    }
    
    /**
     * The actual SecurityQuestions Validator method validates to make sure that the SecurotyQuestions Object
     * passed is null or empty.
     * 
     * @param object - the target object pass in for validation.
     * @param errors - contextual state about the validation process
     */
    public void validate(Object object, Errors errors) {
    
        WrapperSecurityQuestions wrapperSecurityQuestions = (WrapperSecurityQuestions) object;

        String validatorPattern = ValidatorExpressionUtil.getValidatorPattern(SECURITYQUESTION_VALIDATOR_PATTERN);
        
        if (wrapperSecurityQuestions != null) {
            UserSecurityQuestions userSecurityQuestionsOne = wrapperSecurityQuestions.getUserQuestionOne();
            UserSecurityQuestions userSecurityQuestionsTwo = wrapperSecurityQuestions.getUserQuestionTwo();
            
            if ((userSecurityQuestionsOne != null && (userSecurityQuestionsOne.getUserLogin() == null
                    || userSecurityQuestionsOne.getSecurityQuestions() == null
                    || userSecurityQuestionsOne.getSecurityQuestions().getSecurityQuestionsId() == 0
                    || userSecurityQuestionsOne.getAnswer() == null || userSecurityQuestionsOne.getAnswer().trim()
                    .isEmpty()))
                    || (userSecurityQuestionsTwo != null && (userSecurityQuestionsTwo.getUserLogin() == null
                            || userSecurityQuestionsTwo.getSecurityQuestions() == null
                            || userSecurityQuestionsTwo.getSecurityQuestions().getSecurityQuestionsId() == 0
                            || userSecurityQuestionsTwo.getAnswer() == null || userSecurityQuestionsTwo.getAnswer()
                            .trim().isEmpty()))) {
                errors.rejectValue(AkuraWebConstant.USER_SECURITY_QUESTION_ONE_ID,
                        AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
            }
            
            if (userSecurityQuestionsOne != null
                    && userSecurityQuestionsTwo != null
                    && userSecurityQuestionsOne.getSecurityQuestions().getSecurityQuestionsId() == 
                        userSecurityQuestionsTwo
                            .getSecurityQuestions().getSecurityQuestionsId()) {
                errors.rejectValue(AkuraWebConstant.USER_SECURITY_QUESTION_TWO_ID,
                        AkuraWebConstant.USER_SAME_SECURITY_QUESTION);
            }
            
            if ((userSecurityQuestionsOne != null && (!userSecurityQuestionsOne.getAnswer().trim()
                    .matches(validatorPattern)))
                    || ((userSecurityQuestionsTwo != null && !userSecurityQuestionsTwo.getAnswer().trim()
                            .matches(validatorPattern)))) {
                errors.rejectValue(AkuraWebConstant.USER_SECURITY_QUESTION_ONE_ID,
                        AkuraWebConstant.USER_INVALID_ANSWERS);
            }
        }
        
    }
}
