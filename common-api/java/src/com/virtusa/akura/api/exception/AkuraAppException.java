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

package com.virtusa.akura.api.exception;

/**
 * This represents a customized exception class for application specific exceptions.
 * 
 * @author Virtusa Corporation
 */

public class AkuraAppException extends AkuraException {
    
    /**
     * Class serial id.
     */
    private static final long serialVersionUID = -1004771925657146922L;
    
    /**
     * Constructs a default SMSAppException object.
     */
    public AkuraAppException() {
    
        super();
    }
    
    /**
     * Constructs a SMSAppException object with the given error code.
     * 
     * @param strErrorCode - The error code.
     */
    public AkuraAppException(String strErrorCode) {
    
        super(strErrorCode);
    }
    
    /**
     * Constructs a SMSAppException object with the given error code and detailed cause.
     * 
     * @param strErrorCode - the error code.
     * @param cause - the cause of the error.
     */
    public AkuraAppException(String strErrorCode, Throwable cause) {
    
        super(strErrorCode, cause);
    }
    
    /**
     * Constructs a SMSAppException object with the given error message, error code and detailed cause.
     * 
     * @param strErrorMsg - The error message.
     * @param strErrorCode - the error code.
     * @param cause - the cause of the error.
     */
    public AkuraAppException(String strErrorMsg, String strErrorCode, Throwable cause) {
    
        super(strErrorMsg, strErrorCode, cause);
    }
    
}
