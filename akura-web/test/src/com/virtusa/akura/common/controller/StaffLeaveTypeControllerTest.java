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

import com.virtusa.akura.api.dto.StaffLeaveType;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseWebTest;
import com.virtusa.akura.common.service.StaffCommonService;
import com.virtusa.akura.common.validator.StaffLeaveTypeValidator;

import org.junit.Test;

public class StaffLeaveTypeControllerTest extends BaseWebTest {


	/**
	 * Represents an instance of ManageHouseController.
	 */
	private StaffLeaveTypeController staffLeaveTypeController;

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
	 * Represents an instance of House.
	 */
	private StaffLeaveType staffLeaveType;

	/**
	 * Represents an instance of ProvinceValidator.
	 */
	@Autowired
	private StaffLeaveTypeValidator staffLeaveTypeValidator;

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
		staffLeaveType = new StaffLeaveType();
		model = new ModelMap();
		request= new MockHttpServletRequest();
		staffLeaveTypeController = new StaffLeaveTypeController();
		staffLeaveType.setDescription("staffLeaveTypee");
		staffLeaveType.setMaxStaffLeaves(14);
		staffLeaveTypeController.setStaffCommonService(staffCommonService);
	}

	 /**
     * Test method for
     * {@link com.virtusa.sms.common.controller.ManageHouseController# saveOrUpdateHouse(
     * org.springframework.ui.ModelMap, com.virtusa.sms.api.dto.House,
     * org.springframework.validation.BindingResult)}.
     *
     *@throws AkuraAppException when an error has occurred during processing.
     */
	@Test
    public void testSaveOrUpdateStaffLeaveType() throws AkuraAppException {
		staffLeaveTypeController.setStaffLeaveTypeValidator(staffLeaveTypeValidator);
		Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
		String view = staffLeaveTypeController.saveOrUpdateStaffLeaveType(staffLeaveType,
				mockBindingResult,request, model);
		assertEquals("reference/manageStaffLeaveTypes", view);
		Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
		String savedView =  staffLeaveTypeController.saveOrUpdateStaffLeaveType(staffLeaveType,
				mockBindingResult,request, model);
		assertEquals("redirect:manageStaffLeaveTypes.htm", savedView);
		request.addParameter("staffLeaveTypeId", ""+staffLeaveType.getStaffLeaveTypeId());


    }

	/**
	 * @throws AkuraAppException
	 *             when an error has occurred during processing.
	 */
	@After
    public void tearDown() throws AkuraAppException {

		staffLeaveTypeController.deleteStaffLeaveType(request, model);

	}

}
