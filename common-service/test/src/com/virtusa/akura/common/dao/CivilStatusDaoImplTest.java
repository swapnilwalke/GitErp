/*
 * � 2011 Virtusa(Pvt)Ltd. All rights reserved. CONFIDENTIAL AND

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
import com.virtusa.akura.api.dto.CivilStatus;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.dao.CivilStatusDao;

/**
 * This test class, tests all the persistence related functionality for the
 * CivilStatus domain object.
 *
 * @author virtusa corporation
 */
public class CivilStatusDaoImplTest extends BaseCommonTest {

    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private CivilStatusDao civilStatusDao;

    /**
     * Represents an instance of CivilStatus.
     */
    private CivilStatus civilStatus;

    /**
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws Exception {

        // Instantiates a CivilStatus object.
        civilStatus = new CivilStatus();
        civilStatus.setDescription("Married");
        civilStatus.setModifiedTime(new Date());
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
            CivilStatus newCivilStatus = civilStatusDao.save(civilStatus);
            assertNotNull("CivilStatus Object should not null ",
                    newCivilStatus);
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
            CivilStatus newCivilStatus = civilStatusDao.save(civilStatus);
            assertNotNull(newCivilStatus);
            newCivilStatus.setDescription("Un married");
            civilStatusDao.update(newCivilStatus);
            CivilStatus updateCivilStatus =
                    (CivilStatus) civilStatusDao.findById(
                     CivilStatus.class, newCivilStatus.getCivilStatusId());
            assertEquals(newCivilStatus.getDescription(),
                    updateCivilStatus.getDescription());
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
            List<CivilStatus> civilStatusList = new ArrayList<CivilStatus>();
            civilStatusList.add(civilStatus);
            civilStatusDao.saveOrUpdateAll(civilStatusList);
            civilStatusList = civilStatusDao.findAll(CivilStatus.class);
            assertNotNull("CivilStatus Object should be null ",
                    civilStatusList);
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
            CivilStatus newCivilStatus = civilStatusDao.save(civilStatus);
            assertNotNull(newCivilStatus);
            CivilStatus findCivilStatus = (CivilStatus) civilStatusDao.findById(
                    CivilStatus.class, newCivilStatus.getCivilStatusId());
            assertNotNull("CivilStatus Object should not null ",
                    findCivilStatus);
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
            CivilStatus newCivilStatus = civilStatusDao.save(civilStatus);
            assertNotNull(newCivilStatus);
            List<CivilStatus> civilStatusList = civilStatusDao.
            findAll(CivilStatus.class);
            assertTrue(civilStatusList.size() > 0);
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
            civilStatusDao.delete(civilStatus);
        } catch (AkuraAppException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
