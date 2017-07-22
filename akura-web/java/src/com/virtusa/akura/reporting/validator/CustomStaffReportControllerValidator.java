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

package com.virtusa.akura.reporting.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.SchoolTeacherAndSectionHeadList;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.common.AkuraWebConstant;

/**
 * This validator class validates the fields of the SchoolTeacherAndSectionHeadList domain object.
 *
 * @author Virtusa Corporation
 */
public class CustomStaffReportControllerValidator implements Validator {

    /** The constant for string "listType". */
    private static final String LIST_TYPE = "listType";

    /**
     * Return whether or not this object can validate objects of the SchoolTeacherAndSectionHeadList class.
     *
     * @param clazz - the class of the SchoolTeacherAndSectionHeadList
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> clazz) {

        return CustomStaffReportControllerValidator.class.isAssignableFrom(clazz);
    }

    /**
     * Validates the object of the SchoolTeacherAndSectionHeadList.
     *
     * @param target - Populated object of SchoolTeacherAndSectionHeadList to validate
     * @param errors - Errors object that is building. May contain errors for the fields relating to types.
     */
    public void validate(Object target, Errors errors) {

        SchoolTeacherAndSectionHeadList schoolTeacherAndSectionHeadList = (SchoolTeacherAndSectionHeadList) target;

        if (schoolTeacherAndSectionHeadList != null) {

            if ((schoolTeacherAndSectionHeadList.getListType() == 
                AkuraConstant.PARAM_INDEX_THREE && schoolTeacherAndSectionHeadList
                .getGradeId() == AkuraConstant.PARAM_INDEX_ZERO)) {
                
                errors.rejectValue(LIST_TYPE, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
            }

            if ((schoolTeacherAndSectionHeadList.getListType() == 
                    AkuraConstant.PARAM_INDEX_ONE && schoolTeacherAndSectionHeadList
                            .getCatogaryID() == AkuraConstant.PARAM_INDEX_ZERO)) {

                errors.rejectValue(LIST_TYPE, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
            }

        }

    }
}
