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

package com.virtusa.akura.util.email;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.util.PropertyReader;

/**
 * Email utility class contains common methods related to email.
 *
 * @author Virtusa Corporation
 */
public final class EmailUtil {

    /** Holds the value for maildata. */
    private static final String MAILDATA = "maildata";

    /**
     * The constant used to hold the map key for school tracker.
     */
    private static final String SCHOOL_TRACKER_KEY = "schoolTracker";

    /**
     * The constant used to hold the map key for school name.
     */
    private static final String SCHOOL_NAME_KEY = "schoolName";

    /**
     * The constant used to hold the resource loader key of the velocity properties school name.
     */
    private static final String SCHOOL_NAME = "SCHOOL.NAME";

    /**
     * The constant used to hold the resource loader key of the velocity properties school tracker.
     */
    private static final String SCHOOL_TRACKER = "SCHOOL.TRACKER";

    /**
     * The constant used to hold the resource loader key of the velocity properties mail type.
     */
    private static final String VELOCITY_PROPERTY_LOADER_KEY = "class.resource.loader.class";

    /**
     * The constant used to hold the resource loader values of the velocity properties mail type.
     */
    private static final String VELOCITY_PROPERTY_LOADER_VALUE =
            "org.apache.velocity.runtime.resource.loader.FileResourceLoader";

    /**
     * The constant, Holds the resource loader values of the velocity properties mail type.
     */
    private static final String RESOURCE_LOADER_PATH_KEY = "file.resource.loader.path";

    /**
     * A constant serves as a key for storing path to application properties file.
     */
    public static final String APPLICATION_PROPERTIES = "application";

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(EmailUtil.class);

    /**
     * Holds constructor.
     */
    private EmailUtil() {

    }

    /**
     * This method is used to initialize the velocity template.
     *
     * @param templateFloder - Holds the absolute folder structure for the particular template file.
     * @param templateName - Holds the name of the velocity template.
     * @return Velocity Template if template initializing failed.
     * @throws AkuraAppException the SMSAppException.
     */
    public static Template initializeVelocityTemplate(String templateFloder, String templateName)
            throws AkuraAppException {

        Properties properties = new Properties();
        Template template = null;
        try {
            properties.setProperty(VELOCITY_PROPERTY_LOADER_KEY, VELOCITY_PROPERTY_LOADER_VALUE);

            if (templateFloder != null) {
                properties.setProperty(RESOURCE_LOADER_PATH_KEY, templateFloder);
                VelocityEngine velocityEngine = new VelocityEngine();
                velocityEngine.init(properties);
                /* next, get the Template */
                if (templateName != null) {

                    template = velocityEngine.getTemplate(templateName);

                }
            }
        } catch (ResourceNotFoundException e) {
            LOG.error(AkuraConstant.EMAIL_ERROR, e);
            throw new AkuraAppException(AkuraConstant.EMAIL_ERROR, e);
        } catch (ParseErrorException e) {
            LOG.error(AkuraConstant.EMAIL_ERROR, e);
            throw new AkuraAppException(AkuraConstant.EMAIL_ERROR, e);
        } catch (Exception e) {
            LOG.error(AkuraConstant.EMAIL_ERROR, e);
            throw new AkuraAppException(AkuraConstant.EMAIL_ERROR, e);
        }
        return template;
    }

    /**
     * Method used to get the content according to given data map and Velocity template.
     *
     * @param folderOfTemplate - Holds template folder.
     * @param nameOfTemplate - Holds the Velocity template.
     * @param objectMap - Holds the object map.
     * @return mail content.
     * @throws AkuraAppException the SMSAppException.
     */
    public static String getTextContent(String folderOfTemplate, String nameOfTemplate, Map<String, Object> objectMap)
            throws AkuraAppException {

        String mailContent = null;
        Template template = initializeVelocityTemplate(folderOfTemplate, nameOfTemplate);
        if (template != null) {

            VelocityContext velocityContext = new VelocityContext();
            StringWriter writer = null;
            writer = new StringWriter();
            velocityContext.put(MAILDATA, objectMap);
            try {
                template.merge(velocityContext, writer);
                mailContent = writer.toString();
            } catch (ResourceNotFoundException e) {
                LOG.error(AkuraConstant.EMAIL_ERROR, e);
                throw new AkuraAppException(AkuraConstant.EMAIL_ERROR, e);
            } catch (ParseErrorException e) {
                LOG.error(AkuraConstant.EMAIL_ERROR, e);
                throw new AkuraAppException(AkuraConstant.EMAIL_ERROR, e);
            } catch (MethodInvocationException e) {
                LOG.error(AkuraConstant.EMAIL_ERROR, e);
                throw new AkuraAppException(AkuraConstant.EMAIL_ERROR, e);
            } catch (IOException e) {
                LOG.error(AkuraConstant.EMAIL_ERROR, e);
                throw new AkuraAppException(AkuraConstant.EMAIL_ERROR, e);
            }

        }
        return mailContent;
    }

    /**
     * Holds email header information including school logo and school name.
     *
     * @param mapObjectMap - holds {@link Map}
     */
    public static void addHeaderForEmail(Map<String, Object> mapObjectMap) {

        mapObjectMap.put(SCHOOL_NAME_KEY, PropertyReader.getPropertyValue(APPLICATION_PROPERTIES, SCHOOL_NAME));
        mapObjectMap.put(SCHOOL_TRACKER_KEY, PropertyReader.getPropertyValue(APPLICATION_PROPERTIES, SCHOOL_TRACKER));
    }

}
