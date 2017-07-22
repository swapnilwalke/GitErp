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

import com.virtusa.akura.api.dto.BloodGroup;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseCommonTest;

/**
 * This test class, tests all the persistence related functionality for the
 * BloodGroup domain object.
 *
 * @author adesaram
 */
public class BloodGroupDaoImplTest extends BaseCommonTest {

    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private BloodGroupDao bloodGroupDao;

    /**
     * Represents an instance of BloodGroup.
     */
    private BloodGroup bloodGroup;

    /**
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws Exception {

        // Instantiates a BloodGroup object.
        bloodGroup = new BloodGroup();
        bloodGroup.setDescription("A");
        bloodGroup.setModifiedTime(new Date());
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
            BloodGroup newbloodGroup = bloodGroupDao.save(bloodGroup);
            assertNotNull("BloodGroup Type object should not null ",
                    newbloodGroup);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test method for {@link com.virtusa.sms.api.dao.BaseDaoImpl#
     * update(com.virtusa.akura.api.dto.BaseDomain)}.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testUpdate() throws AkuraAppException {

        try {
            BloodGroup newBloodGroup = bloodGroupDao.save(bloodGroup);
            assertNotNull(newBloodGroup);
            newBloodGroup.setDescription("Mathematics");
            bloodGroupDao.update(newBloodGroup);
            BloodGroup updateBloodGroup = (BloodGroup) bloodGroupDao.findById(
                    BloodGroup.class, newBloodGroup.getBloodGroupId());
            assertEquals(newBloodGroup.getDescription(),
                    updateBloodGroup.getDescription());
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
            List<BloodGroup> bloodGroupList = new ArrayList<BloodGroup>();
            bloodGroupList.add(bloodGroup);
            bloodGroupDao.saveOrUpdateAll(bloodGroupList);
            bloodGroupList = bloodGroupDao.findAll(BloodGroup.class);
            assertTrue(bloodGroupList.size() > 0);
        } catch (AkuraAppException e) {
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
            BloodGroup newBloodGroup = bloodGroupDao.save(bloodGroup);
            assertNotNull(newBloodGroup);
            BloodGroup findBloodGroup = (BloodGroup) bloodGroupDao.findById(
                    BloodGroup.class, newBloodGroup.getBloodGroupId());
            assertNotNull("BloodGroup type object should not null ",
                    findBloodGroup);
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
            BloodGroup newBloodGroup = bloodGroupDao.save(bloodGroup);
            assertNotNull(newBloodGroup);
            List<BloodGroup> bloodGroupList = bloodGroupDao.findAll(
                    BloodGroup.class);
            assertTrue(bloodGroupList.size() > 0);
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
            bloodGroupDao.delete(bloodGroup);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
}
