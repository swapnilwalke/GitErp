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
import com.virtusa.akura.api.dto.RoleTab;
import com.virtusa.akura.api.dto.Tab;
import com.virtusa.akura.api.dto.UserRole;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseCommonTest;

/**
 * RoleTabDaoImplTest is to test all the persistent related functionalities for the RoleTabDaoImpl object.
 * 
 * @author Virtusa Corporation
 */
public class RoleTabDaoImplTest extends BaseCommonTest {
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private RoleTabDao roleTabDao;
    
    /**
     * Represents an instance of RoleTab.
     */
    private RoleTab roleTab;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private UserRoleDao userRoleDao;
    
    /**
     * Represents an instance of UserRole.
     */
    private UserRole userRole;
    
    /**
     * Represents an instance of UserRole.
     */
    private UserRole updatedUserRole;
    
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
        newmodule.setName("RoleTabDaoImplTest :TestModuleName");
        module = moduleDao.save(newmodule);
        assertNotNull("Module saved to db ", module);
        
        // Instantiates a Tab object.
        Tab newTab = new Tab();
        newTab.setName("RoleTabDaoImplTest :TestTabName");
        newTab.setModule(module);
        tab = tabDao.save(newTab);
        assertNotNull("Tab saved to db ", tab);
        
        // Instantiates a UserRole object.
        UserRole newUserRole = new UserRole();
        newUserRole.setRole("RoleTabDaoImplTest :TestUserRole Name");
        userRole = userRoleDao.save(newUserRole);
        assertNotNull("UserRole saved to db ", userRole);
        
        // Instantiates a UserRole object.
        UserRole newUpdatedUserRole = new UserRole();
        newUpdatedUserRole.setRole("RoleTabDaoImplTest :TestUserRole Name");
        updatedUserRole = userRoleDao.save(newUpdatedUserRole);
        assertNotNull("UserRole saved to db ", updatedUserRole);
        
        // Instantiates a RoleTab object.
        roleTab = new RoleTab();
        roleTab.setTab(tab);
        roleTab.setRole(userRole);
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
            RoleTab newRoleTab = roleTabDao.save(roleTab);
            assertNotNull("RoleTab Type object should not null ", newRoleTab);
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
            RoleTab newRoleTab = roleTabDao.save(roleTab);
            assertNotNull(newRoleTab);
            
            newRoleTab.setRole(updatedUserRole);
            roleTabDao.update(newRoleTab);
            
            RoleTab updateRoleTab = (RoleTab) roleTabDao.findById(RoleTab.class, newRoleTab.getRoleTabId());
            assertEquals(newRoleTab.getRole().getRole(), updateRoleTab.getRole().getRole());
            
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
            List<RoleTab> roleTabList = new ArrayList<RoleTab>();
            roleTabList.add(roleTab);
            roleTabDao.saveOrUpdateAll(roleTabList);
            roleTabList = roleTabDao.findAll(RoleTab.class);
            assertTrue(roleTabList.size() > 0);
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
            RoleTab newRoleTab = roleTabDao.save(roleTab);
            assertNotNull(newRoleTab);
            RoleTab findRoleTab = (RoleTab) roleTabDao.findById(RoleTab.class, newRoleTab.getRoleTabId());
            assertNotNull("RoleTab type object should not null ", findRoleTab);
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
            RoleTab newRoleTab = roleTabDao.save(roleTab);
            assertNotNull(newRoleTab);
            List<RoleTab> roleTabList = roleTabDao.findAll(RoleTab.class);
            assertTrue(roleTabList.size() > 0);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method for deleteRoleTabsByUserRole.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testDeleteRoleTabsByUserRole() throws AkuraAppException {

        try {
            RoleTab newRoleTab = roleTabDao.save(roleTab);
            assertNotNull(newRoleTab);
            
            roleTabDao.deleteRoleTabsByUserRole(userRole);
            
            assertNull(roleTabDao.findById(RoleTab.class, newRoleTab.getRoleTabId()));
        } catch (DataAccessException e) {
            e.printStackTrace();
        } finally {
            roleTabDao.save(roleTab);
        }
        
    }
    
    /**
     * Test method for getRoleTabsByUserRole.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetRoleTabsByUserRole() throws AkuraAppException {

        try {
            RoleTab newRoleTab = roleTabDao.save(roleTab);
            assertNotNull(newRoleTab);
            
            List<RoleTab> roleTabList = roleTabDao.getRoleTabsByUserRole(userRole);
            
            assertTrue((roleTabList.size() > 0));
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
            roleTabDao.delete(roleTab);
            userRoleDao.delete(userRole);
            userRoleDao.delete(updatedUserRole);
            tabDao.delete(tab);
            moduleDao.delete(module);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
}
