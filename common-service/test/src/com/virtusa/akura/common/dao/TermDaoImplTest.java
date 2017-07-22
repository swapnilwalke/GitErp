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
import com.virtusa.akura.api.dto.Term;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * TermDaoImplTest is to test all the persistent related functionalities for the
 * Term object.
 * 
 * @author Virtusa Corporation
 * 
 */
public class TermDaoImplTest extends BaseCommonTest {

	/**
	 * Holds termDAO instance of {@link TermDao}.
	 */
	@Autowired
	private TermDao termDAO;

	/**
	 * Holds term instance of {@link Term}.
	 */
	private Term term;

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

		term = new Term();
		term.setDescription("Term 1");
		term.setModifiedTime(date);
	}

	/**
	 * Test method for save Term.
	 */
	@Test
	public final void testAddTerm() {
		try {
			Term addTerm = this.termDAO.save(term);
			assertNotNull(" Added Term object can not be null ", addTerm);
		} catch (AkuraAppException e) {
			e.getStackTrace();
		}
	}

	/**
	 * Test method for update Term. This test method covers test for get Term by
	 * id too.
	 */
	@Test
	public final void testModifyTerm() {
		try {
			Term updateTerm = this.termDAO.save(term);
			assertNotNull(updateTerm);

			updateTerm.setDescription("First Term");
			this.termDAO.update(updateTerm);

			Term findTerm = (Term) termDAO.findById(Term.class,
					updateTerm.getTermId());
			assertEquals(updateTerm.getDescription(), findTerm.getDescription());
		} catch (AkuraAppException e) {
			e.getStackTrace();
		}
	}

	/**
	 * Test method for get all the available Terms.
	 */
	@Test
	public final void testGetTerms() {
		try {
			Term addTerm = this.termDAO.save(term);
			assertNotNull(addTerm);

			List<Term> terms = this.termDAO.findAll(Term.class);
			assertTrue(terms.size() > 0);
		} catch (AkuraAppException e) {
			e.getStackTrace();
		}
	}

	/**
	 * Tear down method for each test case. This handles delete Term.
	 */
	@After
	public final void teardown() {
		try {
			this.termDAO.delete(term);
		} catch (AkuraAppException e) {
			e.printStackTrace();
		}
	}
}
