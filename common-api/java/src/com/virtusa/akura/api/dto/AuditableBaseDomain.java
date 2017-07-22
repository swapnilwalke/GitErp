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

package com.virtusa.akura.api.dto;

import java.io.Serializable;

/**
 * All DTOs and POJOs in the application should extend this
 * class if they need to audit.
 * 
 * @author Virtusa Corporation
 */
public abstract class AuditableBaseDomain extends BaseDomain implements Serializable{
    
    /**
     * Generated serial id.
     */
    private static final long serialVersionUID = -5875802756035670138L;
    
    /**
     * method to get required details for auditing. 
     * 
     * @return string 
     */
    public abstract String auditToString();
    
}
