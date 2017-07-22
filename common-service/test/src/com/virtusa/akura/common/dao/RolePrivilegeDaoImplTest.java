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
import com.virtusa.akura.api.dto.RolePrivilege;
import com.virtusa.akura.api.dto.Tab;
import com.virtusa.akura.api.dto.UserRole;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.BaseCommonTest;

/**
 * RolePrivilegeDaoImplTest is to test all the persistent related functionalities for the RolePrivilegeDaoImpl
 * object.
 * 
 * @author Virtusa Corporation
 */
public class RolePrivilegeDaoImplTest extends BaseCommonTest {
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private RolePrivilegeDao rolePrivilegeDao;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private UserRoleDao userRoleDao;
    
    /**
     * Represents an instance of RolePrivilege.
     */
    private RolePrivilege rolePrivilege;
    
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
        newmodule.setName("RolePrivilegeDaoImplTest :TestModuleName");
        module = moduleDao.save(newmodule);
        assertNotNull("Module saved to db ", module);
        
        // Instantiates a Tab object.
        Tab newTab = new Tab();
        newTab.setName("RolePrivilegeDaoImplTest :TestTabName");
        newTab.setModule(module);
        tab = tabDao.save(newTab);
        assertNotNull("Tab saved to db ", tab);
        
        // Instantiates a Page object.
        Page newPage = new Page();
        newPage.setName("RolePrivilegeDaoImplTest :TestPageName");
        newPage.setUrl("RolePrivilegeDaoImplTest :Test URL");
        newPage.setTab(tab);
        page = pageDao.save(newPage);
        assertNotNull("Page saved to db ", page);
        
        // Instantiates a Privilege object.
        Privilege newprivilege = new Privilege();
        newprivilege.setName("RolePrivilegeDaoImplTest :TestPrivilegeName");
        newprivilege.setPage(page);
        privilege = privilegeDao.save(newprivilege);
        assertNotNull("Privilege saved to db ", privilege);
        
        // Instantiates a UserRole object.
        UserRole newUserRole = new UserRole();
        newUserRole.setRole("RolePrivilegeDaoImplTest :TestUserRole Name");
        userRole = userRoleDao.save(newUserRole);
        assertNotNull("UserRole saved to db ", userRole);
        
        // Instantiates a UserRole object.
        UserRole newUpdatedUserRole = new UserRole();
        newUpdatedUserRole.setRole("RolePrivilegeDaoImplTest :TestUserRole Name");
        updatedUserRole = userRoleDao.save(newUpdatedUserRole);
        assertNotNull("UserRole saved to db ", updatedUserRole);
        
        // Instantiates a RolePrivilege object.
        rolePrivilege = new RolePrivilege();
        rolePrivilege.setPrivilege(privilege);
        rolePrivilege.setRole(userRole);
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
            RolePrivilege newRolePrivilege = rolePrivilegeDao.save(rolePrivilege);
            assertNotNull("RolePrivilege Type object should not null ", newRolePrivilege);
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
            RolePrivilege newRolePrivilege = rolePrivilegeDao.save(rolePrivilege);
            assertNotNull(newRolePrivilege);
            
            newRolePrivilege.setRole(updatedUserRole);
            rolePrivilegeDao.update(newRolePrivilege);
            
            RolePrivilege updateRolePrivilege =
                    (RolePrivilege) rolePrivilegeDao.findById(RolePrivilege.class,
                            newRolePrivilege.getRolePrivilegeId());
            assertEquals(newRolePrivilege.getRole().getRole(), updateRolePrivilege.getRole().getRole());
            
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
            List<RolePrivilege> rolePrivilegeList = new ArrayList<RolePrivilege>();
            rolePrivilegeList.add(rolePrivilege);
            rolePrivilegeDao.saveOrUpdateAll(rolePrivilegeList);
            rolePrivilegeList = rolePrivilegeDao.findAll(RolePrivilege.class);
            assertTrue(rolePrivilegeList.size() > 0);
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
            RolePrivilege newRolePrivilege = rolePrivilegeDao.save(rolePrivilege);
            assertNotNull(newRolePrivilege);
            RolePrivilege findRolePrivilege =
                    (RolePrivilege) rolePrivilegeDao.findById(RolePrivilege.class,
                            newRolePrivilege.getRolePrivilegeId());
            assertNotNull("RolePrivilege type object should not null ", findRolePrivilege);
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
            RolePrivilege newRolePrivilege = rolePrivilegeDao.save(rolePrivilege);
            assertNotNull(newRolePrivilege);
            List<RolePrivilege> rolePrivilegeList = rolePrivilegeDao.findAll(RolePrivilege.class);
            assertTrue(rolePrivilegeList.size() > 0);
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
    public final void testDeleteRolePrivilegesByUserRole() throws AkuraAppException {

        try {
            RolePrivilege newRolePrivilege = rolePrivilegeDao.save(rolePrivilege);
            assertNotNull(newRolePrivilege);
            
            rolePrivilegeDao.deleteRolePrivilegesByUserRole(userRole);
            
            assertNull(rolePrivilegeDao.findById(RolePrivilege.class, newRolePrivilege.getRolePrivilegeId()));
        } catch (DataAccessException e) {
            e.printStackTrace();
        } finally {
            rolePrivilegeDao.save(rolePrivilege);
        }
        
    }
    
    /**
     * Test method for getRolePrivilegesByUserRole.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetRolePrivilegesByUserRole() throws AkuraAppException {

        try {
            RolePrivilege newRolePrivilege = rolePrivilegeDao.save(rolePrivilege);
            assertNotNull(newRolePrivilege);
            
            List<RolePrivilege> rolePrivilegeList = rolePrivilegeDao.getRolePrivilegesByUserRole(userRole);
            
            assertTrue((rolePrivilegeList.size() > 0));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * Test method for getPrivilegesByUserRole.
     * 
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testGetPrivilegesByUserRole() throws AkuraAppException {

        try {
            RolePrivilege newRolePrivilege = rolePrivilegeDao.save(rolePrivilege);
            assertNotNull(newRolePrivilege);
            
            List<Privilege> privilegeList = rolePrivilegeDao.getPrivilegesByUserRole(userRole);
            
            assertTrue((privilegeList.size() > 0));
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
            rolePrivilegeDao.delete(rolePrivilege);
            userRoleDao.delete(userRole);
            userRoleDao.delete(updatedUserRole);
            privilegeDao.delete(privilege);
            pageDao.delete(page);
            tabDao.delete(tab);
            moduleDao.delete(module);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
}
