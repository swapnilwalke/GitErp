
package com.virtusa.akura.reporting.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.virtusa.akura.api.dto.BestStudentAttendanceTemplate;
import com.virtusa.akura.common.AkuraWebConstant;

/**
 * This validator class validates the fields of the Best Student Attendance domain object.
 *
 * @author Virtusa Corporation
 */
public class GradeWiseBestStudentAttendanceValidator implements Validator {

    /** the constant of error field to check. */
    private static final String REF_UI_MANDATORY_FIELD_REQUIRED = "REF.UI.MANDATORY.FIELD.REQUIRED";

    /** the field name to check grade. */
    private static final String GRADE_DESCRIPTION = "gradeDescription";

    /**
     * Return whether or not this object can validate objects of the Grade Wise Best Student Attendance class.
     *
     * @param myClass - the class of the Parent
     * @return - a boolean true or false
     */
    @Override
    public boolean supports(Class<?> myClass) {

        return BestStudentAttendanceTemplate.class.equals(myClass);
    }

    /**
     * Validates the object of the Student Attendance.
     *
     * @param object - Populated object of Best Student Attendance to validate
     * @param errors - Errors object that is building. May contain errors for the fields relating to types.
     */
    public void validate(Object object, Errors errors) {

        BestStudentAttendanceTemplate temp = (BestStudentAttendanceTemplate) object;

        String zeroVal = AkuraWebConstant.STRING_ZERO;

        if ((temp.getGradeDescription().equals(zeroVal) || temp.getYear() == 0)) {
            errors.rejectValue(GRADE_DESCRIPTION, REF_UI_MANDATORY_FIELD_REQUIRED);
        }

    }

}
