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
package com.virtusa.akura.common.dao;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.common.BaseCommonTest;
import com.virtusa.akura.api.dto.SubTerm;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * SubTermDaoImplTest is to test all the persistent related functionalities for the
 * SubTerm object.
 *
 * @author Virtusa Corporation
 *
 */
public class SubTermDaoImplTest extends BaseCommonTest {

	/**
	 * Holds subTermDAO instance of {@link SubTermDao}.
	 */
	@Autowired
	private SubTermDao subTermDAO;

	/**
	 * Holds subTerm instance of {@link SubTerm}.
	 */
	private SubTerm subTerm;

	/**
	 * Current date of {@link Date}.
	 */
	private Date date;

	/**
	 * Instantiate the set up method.
	 *
	 * @throws java.lang.Exception
	 *             {@link Exception}
	 */
	@Before
	public final void setUp() throws Exception {
		date = new Date();

		subTerm = new SubTerm();
		subTerm.setDescription("SubTerm 1");
		subTerm.setModifiedTime(date);
	}

	/**
	 * Test method for save SubTerm.
	 */
	@Test
	public final void testAddSubTerm() {
		try {
			SubTerm addSubTerm = this.subTermDAO.save(subTerm);
			assertNotNull(" Added SubTerm object can not be null ", addSubTerm);
		} catch (AkuraAppException e) {
			e.getStackTrace();
		}
	}

	/**
	 * Test method for update SubTerm. This test method covers test for get SubTerm by
	 * id too.
	 */
	@Test
	public final void testModifySubTerm() {
		try {
			SubTerm updateSubTerm = this.subTermDAO.save(subTerm);
			assertNotNull(updateSubTerm);

			updateSubTerm.setDescription("SubTerm one");
			this.subTermDAO.update(updateSubTerm);

			SubTerm findSubTerm = (SubTerm) subTermDAO.findById(SubTerm.class,
					updateSubTerm.getSubTermId());
			assertEquals(updateSubTerm.getDescription(), findSubTerm.getDescription());
		} catch (AkuraAppException e) {
			e.getStackTrace();
		}
	}

	/**
	 * Test method for get all the available SubTerms.
	 */
	@Test
	public final void testGetSubTerms() {
		try {
			SubTerm addSubTerm = this.subTermDAO.save(subTerm);
			assertNotNull(addSubTerm);

			List<SubTerm> subTerms = this.subTermDAO.findAll(SubTerm.class);
			assertTrue(subTerms.size() > 0);
		} catch (AkuraAppException e) {
			e.getStackTrace();
		}
	}

	/**
     * Test method for
     * {@link com.virtusa.akura.common.dao.SubTermDaoImpl#
     * getSubTermsOfATerm(java.lang.Integer)}.
     *
     */
	public final void getSubTermsOfATerm() {
	    try {
            List<SubTerm> subTermList = subTermDAO.getSubTermsOfATerm(subTerm.getSubTermId());
            assertTrue(subTermList.size() > 0);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
	}

	/**
	 * Tear down method for each test case. This handles delete SubTerm.
	 */
	@After
	public final void teardown() {
		try {
			this.subTermDAO.delete(subTerm);
		} catch (AkuraAppException e) {
			e.printStackTrace();
		}
	}
}
