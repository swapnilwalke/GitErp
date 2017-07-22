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

package com.virtusa.akura.util;

import org.apache.log4j.Logger;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * Phone Number validate util class include method for validate given phone number according to the country.
 * 
 * @author Virtusa Corporation
 */
public final class PhoneNumberValidateUtil {
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(PhoneNumberValidateUtil.class);
    
    /**
     * private constructor.
     */
    private PhoneNumberValidateUtil() {
    
    }
    
    /**
     * This method use to validate the phone number according to the country code.
     * 
     * @param phoneNumber - String input of phone number
     * @param countryShortCode - short code for the country according to ISO Source -
     *        http://www.iso.org/iso/country_names_and_code_elements
     * @return isValid for phone number
     * @throws AkuraAppException Akura App Exception
     */
    public static boolean isValidPhoneNumber(String phoneNumber, String countryShortCode) throws AkuraAppException {
    
        boolean isValid = false;
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            PhoneNumber givenPhoneNumber = phoneUtil.parse(phoneNumber, countryShortCode.toUpperCase());
            isValid = phoneUtil.isValidNumber(givenPhoneNumber);
        } catch (NumberParseException e) {
            LOG.error(AkuraConstant.PHONENUMBER_ERROR, e);
            throw new AkuraAppException(AkuraConstant.PHONENUMBER_ERROR, e);
        }
        return isValid;
    }
    
    /**
     * This method use to return the country code using country short code.
     * 
     * @param countryShortCode - short code for the country according to ISO Source -
     *        http://www.iso.org/iso/country_names_and_code_elements
     * @return country code for the given country short code
     */
    public static int findCountryCode(String countryShortCode) {
    
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        return phoneUtil.getCountryCodeForRegion(countryShortCode.toUpperCase());
        
    }
    
    /**
     * This method use to return the country short code using country code.
     * 
     * @param countryCode - country code as a integer
     * @return country code for the given country short code
     */
    public static String getRegionCodeForCountryCode(int countryCode) {
    
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        return phoneUtil.getRegionCodeForCountryCode(countryCode);
        
    }
    
    /**
     * This method use to format the phone number according to international format.
     * 
     * @param phoneNumber - String input of phone number
     * @param countryShortCode - short code for the country according to ISO Source -
     *        http://www.iso.org/iso/country_names_and_code_elements
     * @param type phone number format type 0:international, 1:national, 2:E164      
     * @return internationalFormattedPhoneNumber for the given phone number
     * @throws AkuraAppException Akura App Exception
     */
    public static String formatPhoneNumber(String phoneNumber, String countryShortCode, int type)
            throws AkuraAppException {
    
        String formattedPhoneNumber = AkuraConstant.EMPTY_STRING;
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        
        try {
            PhoneNumber givenPhoneNumber = phoneUtil.parse(phoneNumber, countryShortCode.toUpperCase());
            switch (type) {
                case 0:
                    formattedPhoneNumber = phoneUtil.format(givenPhoneNumber, PhoneNumberFormat.INTERNATIONAL);
                    break;
                case 1:
                    formattedPhoneNumber = phoneUtil.format(givenPhoneNumber, PhoneNumberFormat.NATIONAL);
                    break;
                case 2:
                    formattedPhoneNumber = phoneUtil.format(givenPhoneNumber, PhoneNumberFormat.E164);
                    break;
                default:
                    break;
            }
            
        } catch (NumberParseException e) {
            LOG.error(AkuraConstant.PHONENUMBER_ERROR, e);
            throw new AkuraAppException(AkuraConstant.PHONENUMBER_ERROR, e);
        }
        return formattedPhoneNumber;
    }
}
