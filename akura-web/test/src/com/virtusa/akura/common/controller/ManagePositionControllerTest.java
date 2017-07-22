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

import com.virtusa.akura.api.dto.Position;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.validator.PositionValidator;

/**
 * This test class, tests all the functionality for the Position object.
 *
 * @author Virtusa Corporation
 */
public class ManagePositionControllerTest extends BaseWebTest {

	/**
	 * Represents an instance of ManagePositionController.
	 */
	private ManagePositionController managePositionController;

	/**
	 * Represents an instance of MockHttpServletRequest.
	 */
	private MockHttpServletRequest request;

	/**
	 * Represents an instance of ModelMap.
	 */
	private ModelMap model;

	/**
	 * Represents an instance of Position.
	 */
	private Position position;

	/**
	 * Represents an instance of PositionValidator.
	 */
	@Autowired
	private PositionValidator positionValidator;

	/**
	 * Represents an instance of BindingResult.
	 */
	@Mock
	private BindingResult mockBindingResult;

	/**
	 * @throws AkuraAppException
	 *             when an error has occurred during processing.
	 */
	@Before
	public void setUp() throws AkuraAppException {
		MockitoAnnotations.initMocks(this);
		assertNotNull(mockBindingResult);
		position = new Position();
		model = new ModelMap();
		request= new MockHttpServletRequest();
		managePositionController = new ManagePositionController();
		position.setDescription("position");

	}

	 /**
     * Test method for
     * {@link com.virtusa.sms.common.controller.ManagePositionController# saveOrUpdatePosition(
     * org.springframework.ui.ModelMap, com.virtusa.sms.api.dto.Position,
     * org.springframework.validation.BindingResult)}.
     *
     *@throws AkuraAppException when an error has occurred during processing.
     */
	@Test
    public void testSaveOrUpdatePosition() throws AkuraAppException {
		managePositionController.setPositionValidator(positionValidator);
		Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
		String view = managePositionController.saveOrUpdatePosition(position, mockBindingResult, request, model);
		assertEquals("reference/managePosition", view);
		Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
		String savedView =  managePositionController.saveOrUpdatePosition(position, mockBindingResult, request, model);
		assertEquals("redirect:managePosition.htm", savedView);
		request.addParameter("positionId", ""+position.getPositionId());

    }

	/**
	 * @throws AkuraAppException
	 *             when an error has occurred during processing.
	 */
	@After
	public void tearDown() throws AkuraAppException {

		managePositionController.deletePosition(request, model);

	}

}
