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

/**
 * Collects user inputs to generate disciplinary actions report.
 * 
 * @author Virtusa Corporation
 */
public class CRDisciplinaryWrapperTemplate {
    
    /**
     * Holds the cRDisciplinaryActionsGeneralTemplate.
     */
    private CRDisciplinaryActionsGeneralTemplate cRDisciplinaryActionsGeneralTemplate;
    
    /**
     * Holds the cRDisciplinaryActionsTemplate.
     */
    private CRDisciplinaryActionsTemplate cRDisciplinaryActionsTemplate;
    
    /**
     * @return Returns the cRDisciplinaryActionsGeneralTemplate.
     */
    public CRDisciplinaryActionsGeneralTemplate getcRDisciplinaryActionsGeneralTemplate() {
    
        return cRDisciplinaryActionsGeneralTemplate;
    }
    
    /**
     * @param cRDisciplinaryActionsGeneralTemplateRef Sets the value for the
     *        CRDisciplinaryActionsGeneralTemplate.
     */
    
    public void setcRDisciplinaryActionsGeneralTemplate(
            CRDisciplinaryActionsGeneralTemplate cRDisciplinaryActionsGeneralTemplateRef) {
    
        this.cRDisciplinaryActionsGeneralTemplate = cRDisciplinaryActionsGeneralTemplateRef;
    }
    
    /**
     * @return Returns the cRDisciplinaryActionsTemplate.
     */
    
    public CRDisciplinaryActionsTemplate getcRDisciplinaryActionsTemplate() {
    
        return cRDisciplinaryActionsTemplate;
    }
    
    /**
     * @param cRDisciplinaryActionsTemplateRef Sets the value for the CRDisciplinaryActionsTemplate.
     */
    
    public void setcRDisciplinaryActionsTemplate(CRDisciplinaryActionsTemplate cRDisciplinaryActionsTemplateRef) {
    
        this.cRDisciplinaryActionsTemplate = cRDisciplinaryActionsTemplateRef;
    }
    
}
