/*
 * © 2011 Virtusa(Pvt)Ltd. All rights reserved. CONFIDENTIAL AND

 * PROPRIETARY INFORMATION The information contained herein (the

 * 'Proprietary Information') is highly confidential and proprietary to and

 * constitutes trade secrets of Virtusa(Pvt)Ltd. The Proprietary Information

 * for Virtusa(Pvt)Ltd internal use only and shall not be published,

 * communicated, disclosed or divulged to any person, firm, corporation or

 * other legal entity, directly or indirectly, without the prior written

 * consent of Virtusa(Pvt)Ltd Information Management.
 */

package com.virtusa.akura.common.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.common.BaseCommonTest;
import com.virtusa.akura.api.dto.Nationality;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.dao.NationalityDao;

/**
 * This test class, tests all the persistence related functionality for the
 * Nationality domain object.
 *
 * @author adesaram
 */
public class NationalityDaoImplTest extends BaseCommonTest {

    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private NationalityDao nationalityDao;

    /**
     * Represents an instance of Nationality.
     */
    private Nationality nationality;

    /**
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws Exception {

        // Instantiates a Nationality object.
        nationality = new Nationality();
        nationality.setDescription("Sri Lankan");
        nationality.setModifiedTime(new Date());
    }

    /**
     * Test method for {@link com.virtusa.sms.api.dao.BaseDaoImpl#
     * save(com.virtusa.akura.api.dto.BaseDomain)}.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testSave() throws AkuraAppException {

        try {
            Nationality newNationality = nationalityDao.save(nationality);
            assertNotNull("Nationality type Object should not null ",
                    newNationality);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test method for {@link com.virtusa.sms.api.dao.BaseDaoImpl#
     * update(com.virtusa.akura.api.dto.BaseDomain)}
     * .
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testUpdate() throws AkuraAppException {

        try {
            Nationality newNationality = nationalityDao.save(nationality);
            assertNotNull(newNationality);
            newNationality.setDescription("Mathematics");
            nationalityDao.update(newNationality);
            Nationality updateNationality =
                    (Nationality) nationalityDao.findById(
                     Nationality.class, newNationality.getNationalityId());
            assertEquals(newNationality.getDescription(),
                    updateNationality.getDescription());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test method for {@link com.virtusa.sms.api.dao.BaseDaoImpl#
     * saveOrUpdateAll(java.util.List)}.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testSaveOrUpdateAll() throws AkuraAppException {

        try {
            List<Nationality> nationalityList = new ArrayList<Nationality>();
            nationalityList.add(nationality);
            nationalityDao.saveOrUpdateAll(nationalityList);
            nationalityList = nationalityDao.findAll(Nationality.class);
            assertNotNull("Nationality Type Object should be null ",
                    nationalityList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test method for {@link com.virtusa.sms.api.dao.BaseDaoImpl#
     * findById(java.lang.Class, int)}.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testFindById() throws AkuraAppException {

        try {
            Nationality newNationality = nationalityDao.save(nationality);
            assertNotNull(newNationality);
            Nationality findNationality = (Nationality) nationalityDao.findById(
                    Nationality.class, newNationality.getNationalityId());
            assertNotNull("Nationality Type Object should not null ",
                    findNationality);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test method for {@link com.virtusa.sms.api.dao.BaseDaoImpl#
     * findAll(java.lang.Class)}.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testFindAll() throws AkuraAppException {

        try {
            Nationality newNationality = nationalityDao.save(nationality);
            assertNotNull(newNationality);
            List<Nationality> nationalityList = nationalityDao.
            findAll(Nationality.class);
            assertTrue(nationalityList.size() > 0);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tear down method for each test case.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @After
    public final void teardown() throws AkuraAppException {

        try {
            nationalityDao.delete(nationality);
        } catch (AkuraAppException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
