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
 * This represents the base exception class for the School Management System(SMS). All customized exceptions
 * defined for this system should extend this class.
 * 
 * @author Virtusa Corporation
 */

public abstract class AkuraException extends Exception {
    
    /**
     * Class serial id.
     */
    private static final long serialVersionUID = -230773364309409472L;
    
    /**
     * Represents the error code for a specific error message.
     */
    private String errorCode;
    
    /**
     * Represents an instance of the ErrorMsgUtil class.
     */
    private ErrorMsgLoader errorMsgLoader = new ErrorMsgLoader();
    
    /**
     * Constructs a default SMSException object.
     */
    public AkuraException() {
    
        super();
    }
    
    /**
     * Constructs a SMSException object with the given error code.
     * 
     * @param errCode - the error code
     */
    public AkuraException(String errCode) {
    
        super();
        this.errorCode = errCode;
    }
    
    /**
     * Constructs a SMSException object with the specified error code and cause.
     * 
     * @param errCode - The specific error code.
     * @param cause - The cause of the exception.
     */
    public AkuraException(String errCode, Throwable cause) {
    
        super(cause);
        this.errorCode = errCode;
    }
    
    /**
     * Constructs a SMSException object with the specified error message, code and cause.
     * 
     * @param cause - The cause of the exception.
     * @param strErrorMsg - the specific error message.
     * @param errCode - The specific error code
     */
    public AkuraException(String strErrorMsg, String errCode, Throwable cause) {
    
        super(strErrorMsg, cause);
        this.errorCode = errCode;
    }
    
    /**
     * Returns the error code provided for this object.
     * 
     * @return - The error code.
     */
    public String getErrorCode() {
    
        return errorCode;
    }
    
    /**
     * Sets the error code for this object.
     * 
     * @param errCode - The error code to be set.
     */
    public void setErrorCode(String errCode) {
    
        this.errorCode = errCode;
    }
    
    /**
     * This method overrides the java.lang.Throwable , getLocalizedMessage() method. If an error code has been
     * set for this object, it would return the error message for the corresponding error code together with
     * the detailed exception, else, it would only return the detailed exception.
     * 
     * @return - The exception details.
     */
    public String getLocalizedMessage() {
    
        if (this.errorCode == null || this.errorCode.isEmpty()) {
            return getMessage();
        }
        
        return errorMsgLoader.getErrorMessage(errorCode) + getMessage();
        
    }
}
