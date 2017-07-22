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

package com.virtusa.akura.student.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.virtusa.akura.api.dto.ParentDetails;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.UserInfo;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.student.service.ParentService;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.StaticDataUtil;

/**
 * ParentsChildsControler, Child Detail for the Parent .
 */
@Controller
public class ParentsChildsControler {
    
    /** View name. */
    private static final String STUDENT_CHILD_LIST = "student/childList";
    
    /** Model attribute Name for child list. */
    private static final String CHILD_LIST = "childList";
    
    /** Represents the key for the user. */
    public static final String USER = "user";
    
    /** URL to get childList. */
    private static final String GET_CHILD_LIST_HTM = "getChildList.htm";
    
    /** ParentService attribute for holding parentService. */
    private ParentService parentService;
    
    /** property file name. */
    private static final String SYSTEM_CONFIG_PROPERTIES = "systemConfig";
    
    /** Holds the value for appserver.home. */
    private static final String APPSERVER_HOME = "appserver.home";
    
    /** key to define the image folder path. */
    private static final String IMAGE_FOLDER_PATH = "imageFolder.path";
    
    /** key to define the image file format. */
    private static final String FILE_EXT = ".jpg";
    
    /** model attribute of image path. */
    private static final String MODEL_ATT_IMAGE_PATH = "imagePaths";
    
    /** key to define the profile image path. */
    private static final String RESOURCES_PROFILE_IMAGES = "resources/profileImages/";
    
    /** key to define the default image. */
    private static final String RESOURCES_NO_PROFILE_IMAGE = "resources/profileImages/no_profile_image.jpg";
    
    /**
     * Get the list of Children details.
     * 
     * @param model - ModelMap
     * @param session - HttpSession
     * @return - view name
     * @throws AkuraAppException when exceptions occur
     */
    @RequestMapping(value = GET_CHILD_LIST_HTM)
    public String getChildList(ModelMap model, HttpSession session) throws AkuraAppException {

        // user should be Parent
        UserInfo parentDetails = (ParentDetails) session.getAttribute(USER);
        
        List<Student> childList = parentService.getAllChilds(parentDetails.getUserLevelIdentifier());
        model.addAttribute(CHILD_LIST, childList);
        
        // map contains image paths for children
        // map key -> getAdmissionNo value -> server Path
        Map<String, String> imagePaths = new HashMap<String, String>();
        // set Temp images
        if (childList != null && !childList.isEmpty()) {
            for (Student student : childList) {
                
                String addNO = student.getAdmissionNo();
                
                if (student.getPhoto() != null && student.getPhoto().length > 0) {
                    byte[] image = student.getPhoto();
                    String imageLoadPath =
                            PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, APPSERVER_HOME)
                                    + PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, IMAGE_FOLDER_PATH);
                    imageLoadPath = imageLoadPath + addNO + FILE_EXT;
                    StaticDataUtil.previewProfile(imageLoadPath, image);
                    imagePaths.put(addNO, RESOURCES_PROFILE_IMAGES + addNO + FILE_EXT);
                    
                } else {
                    imagePaths.put(addNO, RESOURCES_NO_PROFILE_IMAGE);
                }
            }
        }
        model.addAttribute(MODEL_ATT_IMAGE_PATH, imagePaths);
        
        return STUDENT_CHILD_LIST;
    }
    
    /**
     * setter method from ParentService.
     * 
     * @param parentServiceVal - parentService
     */
    public void setParentService(ParentService parentServiceVal) {

        this.parentService = parentServiceVal;
    }
    
}
