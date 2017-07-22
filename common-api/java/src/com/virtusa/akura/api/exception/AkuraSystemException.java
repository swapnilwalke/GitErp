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
 * This represents a customized exception class for system specific exceptions.
 * 
 * @author Virtusa Corporation
 */

public class AkuraSystemException extends AkuraException {
    
    /**
     * Class serial id.
     */
    private static final long serialVersionUID = -4556451012987848066L;
    
    /**
     * Constructs a default SMSSystemException object.
     */
    public AkuraSystemException() {
    
        super();
    }
    
    /**
     * overrides the java.lang.exception getLocalizedMessage method.
     * 
     * @return - the detail error message.
     */
    public String getLocalizedMessage() {
    
        return super.getMessage();
    }
}
