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

package com.virtusa.akura.common.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.Publication;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.util.ValidatorExpressionUtil;

/**
 * This validation class validates the fields of the Publication domain object.
 *
 * @author Virtusa Corporation
 */
public class PublicationValidator implements Validator {

    /**
     * Maximum size of the image.
     */
    private static final int IMAGE_SIZE = 1048576;

    /**
     * Minimum size of the image.
     */
    private static final int MIN_IMG_SIZE = 0;

    /**
     * Maximum size of the message.
     */
    private static final int MESSAGE_MAX_SIZE = 250;

    /**
     * Represents the field publicationId of the publication.
     */
    private static final String REJECT_VALUE = "header";

    /**
     * Represents the pattern for the image.
     */
    private static final String IMAGE_REG_PATTERN = "REFERENCE.PUBLICATION.IMAGE.VALIDATOR";

    /**
     * Key represents the empty file.
     */
    private static final String ERROR_EMPTY_FILE = "error.empty.file";

    /**
     * Represents the error message for incorrect image extension.
     */
    private static final String IMG_EXTENTION_ERROR_MSG = "PUB.UI.IMAGE.EXTENSION.ERROR";

    /**
     * Represents the error message for larger than the allowed image size.
     */
    private static final String IMG_SIZE_ERROR_MSG = "PUB.UI.IMAGE.SIZE.ERROR";

    /** Represents the error message for larger than the message size. */
    private static final String MESSAGE_SIZE = "MESSAGE.SIZE";

    /** attribute for holding space character. */
    private static final String STRING_SPACE = "";

    /** attribute for holding regular expression to replace. */
    private static final String IMAGE_NAME_PATTERN = "REFERENCE.PUBLICATION.IMAGE.VALIDATOR";

    /**
     * Return whether or not this object can validate objects of the Publication class.
     *
     * @param clazz - the class of the Publication
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> clazz) {

        return Publication.class.isAssignableFrom(clazz);
    }

    /**
     * Validates the object of the Publication.
     *
     * @param target - Populated object of Publication to validate
     * @param errors - Errors object that is building. May contain errors for the fields relating to types.
     */
    public void validate(Object target, Errors errors) {

        Publication publication = (Publication) target;
        if (publication.getHeader().trim().length() == 0 || publication.getMessage().trim().length() == 0
                || (publication.getExpiryDate() == null || publication.getpType().getpTypeId() == 0)) {
            errors.rejectValue(REJECT_VALUE, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        } else {
            if (publication.getMessage().trim().length() > MESSAGE_MAX_SIZE) {
                errors.rejectValue(REJECT_VALUE, ERROR_EMPTY_FILE, new ErrorMsgLoader().getErrorMessage(MESSAGE_SIZE));
            } else {
                // validates the profile image of publication
                if (publication.getMultipartFile() != null) {
                    if (publication.getMultipartFile().getSize() > MIN_IMG_SIZE) {
                        String imageName = publication.getMultipartFile().getOriginalFilename();
                        if (imageName != null && imageName != "") {
                            String checkimg = imageName.toLowerCase();
                          
                            String imagePattern = ValidatorExpressionUtil.getValidatorPattern(IMAGE_REG_PATTERN);

                            Pattern imgPattern = Pattern.compile(imagePattern);
                            Matcher matcher = imgPattern.matcher(checkimg);
                            ErrorMsgLoader errorMsgLoader = new ErrorMsgLoader();

                            if (!matcher.matches()) {
                                errors.rejectValue(REJECT_VALUE, ERROR_EMPTY_FILE, errorMsgLoader
                                        .getErrorMessage(IMG_EXTENTION_ERROR_MSG));
                            } else {
                                if (publication.getMultipartFile().getSize() > IMAGE_SIZE) {
                                    errors.rejectValue(REJECT_VALUE, ERROR_EMPTY_FILE, errorMsgLoader
                                            .getErrorMessage(IMG_SIZE_ERROR_MSG));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
