/*
 * � 2011 Virtusa(Pvt)Ltd. All rights reserved. CONFIDENTIAL AND

 * PROPRIETARY INFORMATION The information contained herein (the

 * 'Proprietary Information') is highly confidential and proprietary to and

 * constitutes trade secrets of Virtusa(Pvt)Ltd. The Proprietary Information

 * for Virtusa(Pvt)Ltd internal use only and shall not be published,

 * communicated, disclosed or divulged to any person, firm, corporation or

 * other legal entity, directly or indirectly, without the prior written

 * consent of Virtusa(Pvt)Ltd Information Management.
 */

package com.virtusa.akura.common.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.virtusa.akura.api.dto.Section;
import com.virtusa.akura.common.AkuraWebConstant;

/**
 * This class validates the fields of the Section domain object.
 * @author Virtusa Corporation
 */
public class SectionValidator implements Validator {
    
    /** the field name to check. */
    private static final String FIELD_NAME = "description";
    
    /** the common field name to check. */
    private static final String COMMON_FIELD_NAME = "sectionId";
    
    /** description can only have alphabet characters with space. */
    private static final String REGEX_PATTERN = "REFERENCE.SECTION.VALIDATOR";
    
    /**
     * Section is the class of the actual object instance that is to be validated.
     * @param clazz - the Class that this Validator is being asked if it can validate
     * @return - true if this Validator can indeed validate instances of the supplied clazz
     */
    public boolean supports(Class<?> clazz) {

        return Section.class.isAssignableFrom(clazz);
    }
    
    /**
     * Validates the object of the Section.
     * @param object - Populated object of Section to validate.
     * @param errors - Errors object that is building. May contain errors for the fields relating to types.
     */
    public void validate(Object object, Errors errors) {

        Section section = (Section) object;
        
        if ("".equals(section.getDescription().trim())) {
            errors.rejectValue(COMMON_FIELD_NAME, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        }
        
        Pattern stringOnly = Pattern.compile(REGEX_PATTERN);
        Matcher makeMatch = stringOnly.matcher(section.getDescription());
        
        if (makeMatch.find()) {
            errors.rejectValue(FIELD_NAME, AkuraWebConstant.MISMATCH_ERROR);
        }
    }
}
