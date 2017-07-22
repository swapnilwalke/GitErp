/*
 * < ï¿½KURA, This application manages the daily activities of a Teacher and a Student of a School>
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

package com.virtusa.akura.common;

/**
 * Class to keep constants.
 *
 * @author Virtusa Corporation
 */
public final class AkuraWebConstant {

    /**
     * Constructor.
     */
    private AkuraWebConstant() {

    }

    /**
     * Key representing zero string.
     */
    public static final String STRING_ZERO = "0";

    /**
     * Key representing absent string.
     */
    public static final String ABSENT_STRING = "ab";

    /**
     * Key representing underscore string.
     */
    public static final String UNDERSCORE_STRING = "_";

    /**
     * Key representing an empty string.
     */
    public static final String EMPTY_STRING = "";

    /**
     * Key representing an dash string.
     */
    public static final String DASH_STRING = "-";
    
    /**
     * Key representing an space string.
     */
    public static final String SPACE_STRING = " ";


    /**
     * Key representing an comma string.
     */
    public static final String STRING_COMMA = ",";

    /**
     * Key representing an dot string.
     */
    public static final String STRING_DOT = ".";

    /**
     * Key representing an Subject.
     */
    public static final String SUBJECT_STRING = "Subject";

    /**
     * Key representing an Last List.
     */
    public static final String LAST_LIST = "LastList";

    /**
     * Key representing an Assignment Marks List.
     */
    public static final String ASSIGNMENT_MARKS_LIST = "AssignmentMarksList";

    /**
     * Key representing an Term List.
     */
    public static final String SUB_TERM_LIST = "SubTermList";

    /**
     * Key representing an Term List.
     */
    public static final String TERM_LIST = "TermList";

    /**
     * Key representing an Year.
     */
    public static final String YEAR_STRING = "Year";

    /**
     * Key representing an answer regex pattern.
     */
    public static final String ANSWER_PATTERN = "[a-zA-Z\\s]*";

    /**
     * Key representing an year.
     */
    public static final String YEAR = "year";

    /**
     * Key representing an error.
     */
    public static final String ERROR = "FOG.ERROR";

    /**
     * Key representing an error in email.
     */
    public static final String FORGOT_USERNAME_ERROR_EMAIL = "FOG.ERROR.USERNAME.EMAIL";

    /**
     * Key representing an error in user name.
     */
    public static final String USERNAME_ERROR = "FOG.ERROR.USERNAME";

    /**
     * Key representing an error in security question answers.
     */
    public static final String NO_ANSWERS_ERROR = "FOR.ERROR.SECURITYQUESTION.NOANSWER.ERROR";

    /**
     * Key representing an error in user account.
     */
    public static final String USER_BLOCKED_ERROR = "FOG.ERROR.ACCOUNTBLOCKED";

    /**
     * Key representing invalid hibernate operation - delete new record.
     */
    public static final String HIBERNATE_INVALID_DEL_OPERATION = "GEN.DB.INVALID.OPERATION.DEL";

    /**
     * Key representing duplicate entry for a system user.
     */
    public static final String REFERENCE_DUPLICATE_NAME_ENTRY = "REF.SYS.USER.NAME";

    /**
     * Key representing duplicate entry for a system username.
     */
    public static final String REFERENCE_DUPLICATE_USERNAME_ENTRY = "REF.SYS.USER.USERNAME";

    /**
     * Key representing an error message for a classTeacher search with no results.
     */
    public static final String CLASSTEACHER_SEARCH_NO_RESULT = "CLASSTEACHER.SEARCH.NO.RESULT";

    /**
     * Key representing duplicate entry for new school details.
     */
    public static final String SCHOOL_DUPLICATE_ENTRY = "SCH.DETAILS";

    /** Key to representing invalid registration no entry. */
    public static final String INVALID_STAFF_REG_NO = "REF.UI.TEACHER.FIELD.INVALID";

    /**
     * Key representing an error message for a staff search with no results.
     */
    public static final String STAFF_SEARCH_NORESULT = "STA.SEARCH.NO.RESULT";

    /**
     * Key representing an error message for a student search with no results.
     */
    public static final String STUDENT_SEARCH_NORESULT = "STU.SEARCH.NO.RESULT";

    /**
     * Key representing an error message for a parent search with no results.
     */
    public static final String PARENT_SEARCH_NORESULT = "PAR.SEARCH.NO.RESULT";

    /**
     * Key representing an error message for a bloodGroup search with no results.
     */
    public static final String BLOODGROUP_SEARCH_NORESULT = "BLO.SEARCH.NO.RESULT";

    /**
     * Key representing an error message for a nationality search with no results.
     */
    public static final String NATIONALITY_SEARCH_NORESULT = "NAT.SEARCH.NO.RESULT";

    /**
     * Key representing an error message for a province search with no results.
     */
    public static final String PROVINCE_SEARCH_NORESULT = "PRO.SEARCH.NO.RESULT";

    /**
     * Key representing an error message for a district search with no results.
     */
    public static final String DISTRICT_SEARCH_NORESULT = "DIS.SEARCH.NO.RESULT";

    /**
     * Key representing an error message for a city search with no results.
     */
    public static final String CITY_SEARCH_NORESULT = "CIT.SEARCH.NO.RESULT";

    /**
     * Key representing an error message for a country search with no results.
     */
    public static final String COUNTRY_SEARCH_NORESULT = "COUN.SEARCH.NO.RESULT";

    /**
     * Key representing an error message for a subject search with no results.
     */
    public static final String SUBJECT_SEARCH_NORESULT = "SUB.SEARCH.NO.RESULT";

    /**
     * Key representing an error message for a stream search with no results.
     */
    public static final String STREAM_SEARCH_NORESULT = "STR.SEARCH.NO.RESULT";

    /**
     * The mandatory field error code is to display when mandatory fields not filled, interpretable as message
     * key.
     */
    public static final String MANDATORY_FIELD_ERROR_CODE = "REF.UI.MANDATORY.FIELD.REQUIRED";

    /**
     * The select a scholarship error code is to display when scholarship is not selected, interpretable as
     * message key.
     */
    public static final String SCHOLARSHIP_SELECT_ERROR_CODE = "STUDENT.SCHOLARSHIP.REPORT.ERROR";

    /**
     * The mandatory field error code is to display when mandatory fields not selected, interpretable as
     * message key.
     */
    public static final String MANDATORY_SLECTED_FIELD_ERROR_CODE = "REF.UI.MANDATORY.FIELD.SELECTED.REQUIRED";

    /** Error message is to display when the field mismatch. */
    public static final String MISMATCH_ERROR = "REF.UI.FIELD.TYPE";

    /** Error message is to display when the country code mismatch. */
    public static final String COUNTRY_CODE_MISMATCH_ERROR = "REF.UI.FIELD.COUNTRY.CODE.TYPE";

    /** Error message is to display when the description field length too long. */
    public static final String DESCRIPTION_LENGTH_ERROR = "REF.UI.CLUBSOCIETY.DESCRIPTION.LENGTH";

    /** Error message is to display when the field mismatch the marks type. */
    public static final String MISMATCH_ERROR_MARKS = "Mismatch the field type.";

    /** Error message is to display when an error occurred during file copying. */
    public static final String FILE_COPY_ERROR = "STU.UI.FILE.COPY";

    /** Error message is to display when an error occurred during writing the file. */
    public static final String FILE_IO_EXCEPTION = "STU.UI.FILE.IO ";

    /** The message to display after change password. */
    public static final String PASSWORD_CHANGE_SUCCESSFUL_MESSAGE =
            "An email has been sent to your account with the new password.";

    /** The message to display after username is sent. */
    public static final String USERNAEM_SEND_SUCCESSFUL_MESSAGE =
            "An email has been sent to your account with the username.";

    /** The message to display after change password. */
    public static final String PASSWORD_RESET_SUCCESSFUL_MESSAGE = "Password reset successful";

    /** The email authentication error message. */
    public static final String EMAIL_AUTHENTICATION_ERROR = "EMAIL.AUTHENTICATION.ERROR";

    /** The email send error message. */
    public static final String EMAIL_SEND_ERROR = "EMAIL.SEND.ERROR";

    /** The incorrect old password error message. */
    public static final String USER_INCORRECT_ANSWERS = "USER.UI.WRAPPERSECURITYQUESTION.INCORRECT.ANSWER";

    /** The invalid answers error message. */
    public static final String USER_INVALID_ANSWERS = "USER.UI.WRAPPERSECURITYQUESTION.INVALID.ANSWER";

    /** The constant for security question one id. */
    public static final String USER_SECURITY_QUESTION_ONE_ID = "userQuestionOne.securityQuestions.securityQuestionsId";

    /** The constant for security question two id. */
    public static final String USER_SECURITY_QUESTION_TWO_ID = "userQuestionTwo.securityQuestions.securityQuestionsId";

    /** The same security question selected error. */
    public static final String USER_SAME_SECURITY_QUESTION = "USER.UI.SAME.QUESTION.ERROR";

    /** The security questions exist. */
    public static final String USER_SECURITY_QUESTIONS_EXIST = "USER.SECURITY.QUESTIONS.EXIST";

    /** The security questions exist message. */
    public static final String USER_SECURITY_QUESTIONS_EXIST_MESSAGE = "questionsExistMessage";

    /** The incorrect old password error message. */
    public static final String USER_INCORRECT_OLD_PASSWORD = "USER.CHANGEPASSWORD.ERROR.OLDPASSWORD";

    /** The incorrect new password and confirm password error message. */
    public static final String INCORRECT_COMPARE_PASSWORD = "USER.CHANGEPASSWORD.ERROR.COMPAREPASSWORD";

    /** The constant for user Name. */
    public static final String USER_NAME = "userName";

    /** The constant for user name. */
    public static final String USER_NAME_LOW = "username";

    /** The constant for password. */
    public static final String PASSWORD = "password";

    /** The constant for name. */
    public static final String NAME = "name";

    /**
     * The mandatory field error code is to display when mandatory fields contact not filled, interpretable as
     * message key.
     */
    public static final String MANDATORY_FIELD_CONTACT_ERROR_CODE = "STA.UI.MANDATORY.FIELD.PHONE.NO";

    /** Error message is to display when the mobile field mismatch. */
    public static final String MOBILE_MISMATCH_ERROR = "STA.UI.MOBILE.NO.FIELD.TYPE";

    /** Error message is to display when the mobile field mismatch. */
    public static final String RESIDENCE_MISMATCH_ERROR = "STA.UI.RESIDENCE.NO.FIELD.TYPE";

    /** Error message is to display when the office field mismatch. */
    public static final String OFFICE_MISMATCH_ERROR = "STA.UI.OFFICE.NO.FIELD.TYPE";

    /** Error message is to display when the full name field mismatch. */
    public static final String FULL_NAME_MISMATCH_ERROR = "STA.UI.FULLNAME.FIELD.TYPE";

    /** Error message is to display when the full name field mismatch. */
    public static final String LAST_NAME_MISMATCH_ERROR = "STA.UI.LASTNAME.FIELD.TYPE";

    /** Error message is to display when the name with initial field mismatch. */
    public static final String NAME_WITH_INITIAL_MISMATCH_ERROR = "STA.UI.NAME.INITIALS.FIELD.TYPE";

    /** Error message is to display when the NIC field mismatch. */
    public static final String NIC_MISMATCH_ERROR = "STA.UI.NIC.FIELD.TYPE";

    /** Error message is to display when the NIC field mismatch. */
    public static final String PARISH_MISMATCH_ERROR = "STA.UI.PARISH.FIELD.TYPE";

    /** Error message is to display when the email field mismatch. */
    public static final String EMAIL_MISMATCH_ERROR = "STA.UI.EMAIL.ERROR";

    /** Error message is to display when the basic salary field mismatch. */
    public static final String BASIC_SALARY_MISMATCH_ERROR = "typeMismatch.staff.basicSalary";

    /** Error message is to display when the file is empty. */
    public static final String FILE_EMPTY_ERROR = "IMG.UI.IMAGE.FIELD.TYPE";

    /** Error message is to display when the Image size is large. */
    public static final String IMANGE_SIZE_ERROR = "IMG.UI.IMAGE.FILE.SIZE";

    /** Error message is to display when the image field mismatch. */
    public static final String IMAGE_MISMATCH_ERROR = "Image extension is incorrect.";

    /** Error message is to display when the image field mismatch. */
    public static final String IMAGE_FILE_SIZE_ERROR = "Image file size is lareger than 6MB.";

    /** Error message is to display when the admission number field mismatch. */
    public static final String STUDENT_ADMISSION_NUMBER = "STU.UI.ADDMISSIONNO.FIELD.TYPE";

    /** Error message is to display when the admission number field mismatch. */
    public static final String STUDENT_SIBLING_ADMISSION_NUMBER = "STU.UI.SIBLING.ADDMISSIONNO.FIELD.TYPE";

    /** Error message is to display when the trying to add duplicate holiday entry. */
    public static final String HOLIDAY_EXIST = "REF.UI.HOLIDAY.EXIST";

    /** Error message is to display when the trying to add duplicate student discipline entry. */
    public static final String STUDENT_DESCIPLINE_EXIST = "STU.UI.STUDENT.DISCIPLINE.EXIST";

    /** Error message is to display when unable to delete holiday entry. */
    public static final String CANT_DELETE_HOLIDAY = "REF.UI.HOLIDAY.DELETE";

    /** Error message is to display when description character mismatch. */
    public static final String FIELD_CHARACTER_HOLIDAY = "REF.UI.FIELD.TYPE.HOLIDAY";

    /** Error message is to display when user role contains invalid characters. */
    public static final String USER_ROLE_INVALID = "USER.ROLE.MANAGE.USER.ROLE.INVALID";

    /**
     * already exist error code is to display when object already exist, interpretable as message key.
     */
    public static final String ALREADY_EXIST_ERROR_CODE = "REF.UI.FIELD.EXIST";

    /** Represents the key for the user. */
    public static final String USER = "user";

    /** index one. */
    public static final int PARAM_INDEX_ONE = 1;

    /** index two. */
    public static final int PARAM_INDEX_TWO = 2;

    /** index three. */
    public static final int PARAM_INDEX_THREE = 3;

    /** index four. */
    public static final int PARAM_INDEX_FOUR = 4;

    /** index FIVE. */
    public static final int PARAM_INDEX_FIVE = 5;

    /** index SIX. */
    public static final int PARAM_INDEX_SIX = 6;

    /** index SEVEN. */
    public static final int PARAM_INDEX_SEVEN = 7;

    /** Number of records per page. */
    public static final int PAGE_SIZE = 10;

    /** Faith Life Rating 1. */
    public static final int FAITH_LIFE_RATING_1 = 100;

    /** Faith Life Rating 2. */
    public static final int FAITH_LIFE_RATING_2 = 75;

    /** Faith Life Rating 3. */
    public static final int FAITH_LIFE_RATING_3 = 50;

    /** Represents the key for the date error. */
    public static final String UI_PUBLICATION_DATE_ERROR = "UI.PUBLICATION.DATE.ERROR";

    /** Constant to hold maximum no of prizes. */
    public static final int MAXIMUM_NO_OF_PRIZES = 20;

    /** Constant to hold minimum no of prizes. */
    public static final int MINIMUM_NO_OF_PRIZES = 5;

    /** Constant to hold space. */
    public static final String SPACE = " ";

    /** The Constant GPL Text. */
    public static final String REPORT_GPL =
            "Copyright © 2012-2013 Virtusa Corporation, AKURA contributors and others. "
                    + "AKURA is Free Software released under the GNU/GPL License.";

    /** Constant for holding maxStaffLeaves used in class staffLeaveTypeController . */
    public static final int SPACE_MAXSTAFFLEAVES = 100;

    /** Constant for holding maxStaffLeaves used in class staffLeaveTypeController . */
    public static final int STRING_MAXSTAFFLEAVES = 101;

    /** attribute for holding style path. */
    public static final String STYLE_PATH = "style.path";

    /** attribute for holding app home. */
    public static final String APPSERVER_HOME = "appserver.home";

    /** attribute for holding system config. */
    public static final String SYSTEM_CONFIG = "systemConfig";

    /** attribute for holding style template. */
    public static final String STYLE_TEMPLATE = "styleTemplate";

    /** report parameter for logo . */
    public static final String REPORT_PARA_LOGO_PATH = "logoPath";

    /** Error message is to display when the country and phone number mismatch. */
    public static final String COUNTRY_PHONE_NUMBER_MISMATCH_ERROR = "STAFF.PHONE.COUNTRY.NO.MATCH";

}
