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

package com.virtusa.akura.student.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dto.PrefectType;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.dao.PrefectTypeDao;
import com.virtusa.akura.student.BaseStudentTest;

/**
 * @author Virtusa Corporation
 */
public class StudentPrefectTypeDaoImplTest extends BaseStudentTest{

	/**
     * Holds prefectTypeDao instance of {@link PrefectTypeDao}.
     */
    @Autowired
    private PrefectTypeDao prefectTypeDao;

    /**
     * Holds prefectType instance of {@link PrefectType}.
     */
    private PrefectType prefectType;

    /**
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws Exception {

        // Instantiates a PrefectType object.
        prefectType = new PrefectType();
        prefectType.setDescription("Head Prefects");
        prefectType.setModifiedTime(new Date());
    }

    /**
     * Test method for {@link com.virtusa.sms.api.dao.BaseDaoImpl#
     * save(com.virtusa.sms.api.dto.BaseDomain)}.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testSave() throws AkuraAppException {
        try {
        	PrefectType newPrefectType=prefectTypeDao.save(prefectType);
            assertNotNull("PrefectType object should not null ", newPrefectType);
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
            List<PrefectType> prefectTypeList = new ArrayList<PrefectType>();
            prefectTypeList.add(prefectType);
            prefectTypeDao.saveOrUpdateAll(prefectTypeList);
            prefectTypeList = prefectTypeDao.findAll(PrefectType.class);
            assertTrue(prefectTypeList.size() > 0);
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
            prefectTypeDao.delete(prefectType);
        } catch (AkuraAppException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
