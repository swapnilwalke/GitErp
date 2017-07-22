/*
 * < �KURA, This application manages the daily activities of a Teacher and a Student of a School>
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

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dto.Module;
import com.virtusa.akura.api.dto.Page;
import com.virtusa.akura.api.dto.Privilege;
import com.virtusa.akura.api.dto.Tab;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseCommonTest;

/**
 * PrivilegeDaoImplTest is to test all the persistent related functionalities for the PrivilegeDaoImpl object.
 * 
 * @author Virtusa Corporation
 */
public class PrivilegeDaoImplTest extends BaseCommonTest {
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private PrivilegeDao privilegeDao;
    
    /**
     * Represents an instance of Privilege.
     */
    private Privilege privilege;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private PageDao pageDao;
    
    /**
     * Represents an instance of Page.
     */
    private Page page;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private TabDao tabDao;
    
    /**
     * Represents an instance of Tab.
     */
    private Tab tab;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private ModuleDao moduleDao;
    
    /**
     * Represents an instance of Module.
     */
    private Module module;
    
    /**
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws Exception {

        // Instantiates a Module object.
        Module newmodule = new Module();
        newmodule.setName("PrivilegeDaoImplTest :TestModuleName");
        module = moduleDao.save(newmodule);
        assertNotNull("Module saved to db ", module);
        
        // Instantiates a Tab object.
        Tab newTab = new Tab();
        newTab.setName("PrivilegeDaoImplTest :TestTabName");
        newTab.setModule(module);
        tab = tabDao.save(newTab);
        assertNotNull("Tab saved to db ", tab);
        
        // Instantiates a Page object.
        Page newPage = new Page();
        newPage.setName("PrivilegeDaoImplTest :TestPageName");
        newPage.setUrl("PrivilegeDaoImplTest :Test URL");
        newPage.setTab(tab);
        page = pageDao.save(newPage);
        assertNotNull("Page saved to db ", page);
        
        // Instantiates a Privilege object.
        privilege = new Privilege();
        privilege.setName("PrivilegeDaoImplTest :TestPrivilegeName");
        privilege.setPage(page);
    }
    
    /**
     * Test method for {@link com.virtusa.sms.api.dao.BaseDaoImpl# save(com.virtusa.akura.api.dto.BaseDomain)}
     * .
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testSave() throws AkuraAppException {

        try {
            Privilege newPrivilege = privilegeDao.save(privilege);
            assertNotNull("Privilege Type object should not null ", newPrivilege);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for
     * {@link com.virtusa.sms.api.dao.BaseDaoImpl# update(com.virtusa.akura.api.dto.BaseDomain)}.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testUpdate() throws AkuraAppException {

        try {
            Privilege newPrivilege = privilegeDao.save(privilege);
            assertNotNull(newPrivilege);
            newPrivilege.setName("PrivilegeDaoImplTest :New Test Privilege Name");
            privilegeDao.update(newPrivilege);
            Privilege updatePrivilege =
                    (Privilege) privilegeDao.findById(Privilege.class, newPrivilege.getPrivilegeId());
            assertEquals(newPrivilege.getName(), updatePrivilege.getName());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for {@link com.virtusa.sms.api.dao.BaseDaoImpl# saveOrUpdateAll(java.util.List)}.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testSaveOrUpdateAll() throws AkuraAppException {

        try {
            List<Privilege> privilegeList = new ArrayList<Privilege>();
            privilegeList.add(privilege);
            privilegeDao.saveOrUpdateAll(privilegeList);
            privilegeList = privilegeDao.findAll(Privilege.class);
            assertTrue(privilegeList.size() > 0);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for {@link com.virtusa.sms.api.dao.BaseDaoImpl# findById(java.lang.Class, int)}.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testFindById() throws AkuraAppException {

        try {
            Privilege newPrivilege = privilegeDao.save(privilege);
            assertNotNull(newPrivilege);
            Privilege findPrivilege = (Privilege) privilegeDao.findById(Privilege.class, newPrivilege.getPrivilegeId());
            assertNotNull("Privilege type object should not null ", findPrivilege);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for {@link com.virtusa.sms.api.dao.BaseDaoImpl# findAll(java.lang.Class)}.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testFindAll() throws AkuraAppException {

        try {
            Privilege newPrivilege = privilegeDao.save(privilege);
            assertNotNull(newPrivilege);
            List<Privilege> privilegeList = privilegeDao.findAll(Privilege.class);
            assertTrue(privilegeList.size() > 0);
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
            privilegeDao.delete(privilege);
            pageDao.delete(page);
            tabDao.delete(tab);
            moduleDao.delete(module);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
}
