/*
 * © 2011 Virtusa(Pvt)Ltd. All rights reserved. CONFIDENTIAL AND 

 * PROPRIETARY INFORMATION The information contained herein (the 

 * 'Proprietary Information') is highly confidential and proprietary to and 

 * constitutes trade secrets of Virtusa(Pvt)Ltd. The Proprietary Information

 * for Virtusa(Pvt)Ltd internal use only and shall not be published, 

 * communicated, disclosed or divulged to any person, firm, corporation or 

 * other legal entity, directly or indirectly, without the prior written 

 * consent of Virtusa(Pvt)Ltd Information Management.
 */

package com.virtusa.akura.reporting.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.CRDisciplinaryWrapperTemplate;

/**
 * This class validates fields for Student disciplinary reports criteria,user input data.
 * 
 * @author Virtusa Corporation
 */

public class CRStudentDisciplinaryActionGeneralValidator implements Validator {
    
    /**
     * Return whether object can validated or not.
     * 
     * @param clazz - the class of the CRDisciplinaryWrapperTemplate
     * @return - a boolean true or false
     */
    public boolean supports(Class<?> clazz) {
    
        return CRDisciplinaryWrapperTemplate.class.isAssignableFrom(clazz);
    }
    
    /**
     * Validates the user input for criteria.
     * 
     * @param target - Populated object of CRDisciplinaryWrapperTemplate to validate
     * @param errors - contain errors related to fields.
     */
    public void validate(Object target, Errors errors) {
    
        CRDisciplinaryWrapperTemplate template = (CRDisciplinaryWrapperTemplate) target;
        
        if (template.getcRDisciplinaryActionsGeneralTemplate().getClassDescription().equals("0")
                || template.getcRDisciplinaryActionsGeneralTemplate().getGradeDescription().equals("0")
                || template.getcRDisciplinaryActionsGeneralTemplate().getWarningLevel().equals("0")) {
            
            errors.rejectValue("cRDisciplinaryActionsGeneralTemplate.classDescription",
                    "STQ.UI.MANDATORY.FIELD.REQUIRED");
        }
        
    }
}
