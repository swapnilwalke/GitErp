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

package com.virtusa.akura.common.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.virtusa.akura.api.dto.Section;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.service.StaffCommonService;
import com.virtusa.akura.common.validator.SectionValidator;

/**
 * This test class, tests all the presentation related functionality for the Section domain object.
 *
 * @author Virtusa Corporation
 */
public class SectionControllerTest extends BaseWebTest {

    /**
     * Represents an instance of SectionController.
     */
    private SectionController controller;

    /**
     * Represents an instance of StaffCommonService.
     */
    @Autowired
    private StaffCommonService staffCommonService;

    /**
     * Represents an instance of MockHttpServletRequest.
     */
    private MockHttpServletRequest request;

    /**
     * Represents an instance of ModelMap.
     */
    private ModelMap model;

    /**
     * Represents an instance of BindingResult.
     */
    @Mock
    private BindingResult mockBindingResult;

    /**
     * Represents an instance of Section.
     */
    private Section section;

    /** SectionValidator attribute for holding SectionValidator. */
    @Autowired
    private SectionValidator sectionValidator;



    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Before
    public void setUp() throws AkuraAppException {

        MockitoAnnotations.initMocks(this);
        assertNotNull(mockBindingResult);
        model = new ModelMap();
        request = new MockHttpServletRequest();

        section = new Section();
        section.setDescription("testSectionDescription");

        model.addAttribute("section", section);

        controller = new SectionController();
        controller.setStaffCommonService(staffCommonService);
    }

    /**
     * Test method for ShowSectionDetail of SectionController.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testShowSectionDetail() throws AkuraAppException {

        section = staffCommonService.createSection(section);
        assertNotNull("Section should not be null " + section);

        String result = controller.showSectionDetail(model);
        assertNotNull("ModelAndView should not be null", result);

    }

    /**
     * Test method for
     * {@link com.virtusa.sms.common.controller.SectionController# saveOrUpdateSection
     * (org.springframework.ui.ModelMap, com.virtusa.sms.api.dto.Section,
     *  org.springframework.validation.BindingResult)}.
     *
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @Test
    public void testSaveOrUpdateSection() throws AkuraAppException {
        try{
        controller.setSectionValidator(sectionValidator);
        Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
        String view = controller.saveOrUpdateSection(request,model,section,mockBindingResult);

        assertEquals("reference/manageSection", view);

        Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
        String savedView = controller.saveOrUpdateSection(request, model,section, mockBindingResult );

        assertEquals("redirect:manageSection.htm", savedView);
        assertTrue(model.size() > 0);

        request.addParameter("sectionId", "" + section.getSectionId());
        
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }

    }

    /**
     * @throws AkuraAppException when an error has occurred during processing.
     */
    @After
    public void tearDown() throws AkuraAppException {
        try {
            String result =  controller.deleteSection(model,section );
            assertNotNull("Section should not be null " + result);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }

    }

}
