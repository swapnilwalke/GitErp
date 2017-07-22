
package com.virtusa.akura.student.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.SuspendStudent;
import com.virtusa.akura.common.AkuraWebConstant;

/**
 * This class validates the fields of the StudentSuspend domain object.
 * 
 * @author Virtusa Corporation
 */
public class StudentSuspendValidator implements Validator {
    
    /** attribute for holding field name for display professional qualification errors. */
    private static final String FIELD_PROFESSIONAL_QUALIFICATION_ID = "suspendStudent.student.studentId";
    
    /** error message for date field incorrect. */
    private static final String ERROR_MSG_DATE_FIELD_INCORRECT_DATE = "REF.UI.SUSPEND.DATE.FIELD.INCORRECT_DATE";
    
    /**
     * Return whether or not this object can validate objects of the SuspendStudent class.
     * 
     * @param arg0 - the class of the SuspendStudent
     * @return - a boolean true or false
     */
    @Override
    public boolean supports(Class<?> arg0) {

        return SuspendStudent.class.isAssignableFrom(arg0);
    }
    
    /**
     * Validates the object of the SuspendStudent.
     * 
     * @param target - Populated object of SuspendStudent to validate
     * @param errors - Errors object that is building. May contain errors for the fields relating to types.
     */
    public void validate(Object target, Errors errors) {

        SuspendStudent suspendStudent = (SuspendStudent) target;
        
        if (suspendStudent.getFromDate() == null || suspendStudent.getToDate() == null
                || suspendStudent.getDisciplinaryActionId() == 0) {
            
            errors.rejectValue(FIELD_PROFESSIONAL_QUALIFICATION_ID, AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
        } else if (suspendStudent.getToDate() != null) {
            if ((suspendStudent.getStudent().getFirstSchoolDay()).after((suspendStudent.getFromDate()))) {
                errors.rejectValue(FIELD_PROFESSIONAL_QUALIFICATION_ID, ERROR_MSG_DATE_FIELD_INCORRECT_DATE);
            }
            
        }
        
    }
    
}
