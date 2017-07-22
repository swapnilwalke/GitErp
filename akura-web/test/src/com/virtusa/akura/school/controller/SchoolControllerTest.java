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

package com.virtusa.akura.school.controller;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.school.service.SchoolService;

/**
 * @author Virtusa Corporation
 */

public class SchoolControllerTest extends BaseWebTest {
    
    /** Represents an instance of the SchoolController. */
    private SchoolController schoolController;
    
    /** Represents an instance of the SchoolService. */
    @Autowired
    private SchoolService schoolService;
    
    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws AkuraAppException {

        schoolController = new SchoolController();
        schoolController.setSchoolService(schoolService);
    }
    
}
