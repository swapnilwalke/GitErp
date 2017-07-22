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
import com.virtusa.akura.api.dto.Publication;
import com.virtusa.akura.api.dto.PublicationType;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This test class, tests all the persistence related functionality for the
 * Publication domain object.
 *
 * @author Virtusa Corporation
 */
public class PublicationDaoImplTest extends BaseCommonTest {

    /** Represents an instance of Publication. */
    private Publication publication;

    /** Represents the header description. */
    private static final String HEADER = "This is a test for header.";

    /**  Represents the message description. */
    private static final String MESSAGE = "This is a test for message.";

    /**  Represents new header description. */
    private static final String NEW_HEADER = "This is a new test";

    /** Injects an instance of PublicationDao. */
    @Autowired
    private PublicationDao publicationDao;

    /** Injects an instance of PublicationTypeDao. */
    @Autowired
    private PublicationTypeDao publicationTypeDao;

	/**
	 * @throws AkuraAppException - The exception details that occurred when processing.
	 */
	@Before
	public void setUp() throws AkuraAppException {
	    List<PublicationType> typeList = publicationTypeDao.findAll(PublicationType.class);
        assertTrue(typeList.size() > 0);
	    publication = new Publication();
        publication.setHeader(HEADER);
        publication.setMessage(MESSAGE);
        publication.setExpiryDate(new Date());
        publication.setpType(typeList.get(0));
	}

	/**
	 * Test method for {@link com.virtusa.sms.api.dao.BaseDaoImpl#save(com.virtusa.akura.api.dto.BaseDomain)}.
	 * @throws AkuraAppException - The exception details that occurred when processing.
	 */
	@Test
	public void testSave() throws AkuraAppException {
            Publication newPublication = publicationDao.save(publication);
            assertNotNull(newPublication);

	}

	/**
	 * Test method for {@link com.virtusa.sms.api.dao.BaseDaoImpl#update(com.virtusa.akura.api.dto.BaseDomain)}.
	 * @throws AkuraAppException - The exception details that occurred when processing.
	 */
	@Test
	public void testUpdate() throws AkuraAppException {
	    Publication newPublication = publicationDao.save(publication);
        assertNotNull(newPublication);
        publication.setHeader(NEW_HEADER);
        publicationDao.update(publication);
        Publication editedPublication = (Publication) publicationDao.findById(
                Publication.class, publication.getPublicationId());
        assertEquals(NEW_HEADER , editedPublication.getHeader());
	}


	/**
	 * Test method for {@link com.virtusa.sms.api.dao.BaseDaoImpl#findAll(java.lang.Class)}.
	 * @throws AkuraAppException - The exception details that occurred when processing.
	 */
	@Test
	public void testFindAll() throws AkuraAppException {
		List<Publication> publicationList = publicationDao.findAll(Publication.class);
		assertTrue(publicationList.size() > 0);
	}

	/**
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @After
    public void tearDown() throws AkuraAppException {
        publicationDao.delete(publication);
    }
}
