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

package com.virtusa.akura.util;

/**
 * The Class ValidatorExpressionUtil.
 */
public final class ValidatorExpressionUtil {
    
    /** The Constant VALIDATOR_EXPRESSION. */
    private static final String VALIDATOR_EXPRESSION = "validatorExpression";

    /**
     * Instantiates a new validator expression util.
     */
    private ValidatorExpressionUtil() {

    }
    
    /**
     * Gets the validator pattern by passing key.
     *
     * @param key the key
     * @return the validator pattern
     */
    public static String getValidatorPattern(String key) {
         return PropertyReader.getPropertyValue(VALIDATOR_EXPRESSION, key);
    }
}
