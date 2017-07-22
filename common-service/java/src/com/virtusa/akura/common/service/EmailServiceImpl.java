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

package com.virtusa.akura.common.service;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.virtusa.akura.api.dto.CommonEmailBean;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.email.EmailUtil;

/**
 * @author Virtusa Corporation
 */

public class EmailServiceImpl implements EmailService {
    
    /** string constant for log message. */
    private static final String LOG_IDENTYFY_ERROR_WHEN_PROCESSING_THE_TEMPLATE =
            "Identyfy Error When Processing The Template";
    
    /** string constant for log message. */
    private static final String LOG_TEMPLATE_MATCHING_FAILED = "Template matching Failed";
    
    /** Holds the value for Progress Report.pdf. */
    private static final String PROGRESS_REPORT_PDF = "Progress Report.pdf";
    
    /** Holds the value for header. */
    private static final String HEADDER = "headder";
    
    /** the default path for the email. */
    private static final int DEFAULT_EMAIL_PORT = 25;
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOGGER = Logger.getLogger(EmailServiceImpl.class);
    
    /**
     * A constant serves as a key for storing path to systemConfig properties file.
     */
    public static final String SYSTEM_CONFIG_PROPERTIES = "systemConfig";
    
    /**
     * A constant serves as a key for storing path to email properties file.
     */
    public static final String EMAIL_PROPERTIES = "email";
    
    /**
     * A constant serves as a key for storing application path.
     */
    public static final String APPSERVER_HOME = "appserver.home";
    
    /**
     * A constant serves as a key for storing Template path for email templates.
     */
    public static final String TEMPLATE_PATH = "email.templates.path";
    
    /**
     * A constant serves as a key for storing Template path for header image.
     */
    public static final String IMAGE_PATH = "large.logo.image.path";
    
    /**
     * A constant serves as a key for storing Email Host in property file.
     */
    public static final String EMAIL_HOST = "email.host";
    
    /**
     * A constant serves as a key for storing Email Server port in property file.
     */
    public static final String EMAIL_PORT = "email.port";
    
    /**
     * A constant serve as the key for storing Email login username.
     */
    public static final String EMAIL_USERNAME = "email.username";
    
    /**
     * A constant serve as the key for storing Email login password.
     */
    public static final String EMAIL_PASSWORD = "email.password";
    
    /**
     * mailSender for sending email through mailAPI.
     */
    private JavaMailSender mailSender;
    
    /**
     * Returns the mail sender.
     * 
     * @return the java mail sender.
     */
    public final JavaMailSender getMailSender() {

        return mailSender;
    }
    
    /**
     * Sets the java mail sender.
     * 
     * @param sender java mail sender.
     */
    public final void setMailSender(JavaMailSender sender) {

        mailSender = sender;
    }
    
    /**
     * Sends common email generated according to content of CommonEmailBean.
     * 
     * @param bean <code>CommonEmailBean</code>Contains email content related information.
     * @return boolean check mail sending is successful.
     * @exception AkuraAppException the AkuraAppException.
     */
    
    public boolean sendMail(final CommonEmailBean bean) throws AkuraAppException {

        Integer port = DEFAULT_EMAIL_PORT;
        String host = PropertyReader.getPropertyValue(EMAIL_PROPERTIES, EMAIL_HOST);
        String userName = PropertyReader.getPropertyValue(EMAIL_PROPERTIES, EMAIL_USERNAME);
        String password = PropertyReader.getPropertyValue(EMAIL_PROPERTIES, EMAIL_PASSWORD);
        
        try {
            port = Integer.valueOf(PropertyReader.getPropertyValue(EMAIL_PROPERTIES, EMAIL_PORT).toString());
        } catch (NumberFormatException e) {
            port = DEFAULT_EMAIL_PORT;
        }
        
        ((JavaMailSenderImpl) mailSender).setHost(host);
        ((JavaMailSenderImpl) mailSender).setPort(port);
        ((JavaMailSenderImpl) mailSender).setUsername(userName);
        ((JavaMailSenderImpl) mailSender).setPassword(password);
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        
        try {
            helper = new MimeMessageHelper(message, true);
            InternetAddress[] addresses = null;
            addresses = InternetAddress.parse(bean.getToAddress());
            if (addresses != null) {
                helper.setTo(addresses);
            }
            if (bean.getFromAddress() != null) {
                helper.setFrom(bean.getFromAddress());
            }
            if (bean.getCcAddresses() != null && !bean.getCcAddresses().isEmpty()) {
                helper.setCc(bean.getCcAddresses().toArray(new String[0]));
            }
            if (bean.getSubject() != null) {
                helper.setSubject(bean.getSubject());
            }
            if (bean.isAttachMail()) {
                File file = new File(bean.getAttachementPath());
                helper.addAttachment(PROGRESS_REPORT_PDF, file);
            }
            helper.setText(getcontent(bean), true);
            String imagePath = null;
            imagePath =
                    PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, APPSERVER_HOME)
                            + PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, IMAGE_PATH);
            FileSystemResource res = new FileSystemResource(new File(imagePath));
            helper.addInline(HEADDER, res);
        } catch (MessagingException e1) {
            LOGGER.error(AkuraConstant.EMAIL_ERROR, e1);
            throw new AkuraAppException(AkuraConstant.EMAIL_ERROR, e1);
        }catch (IllegalArgumentException e) {
            LOGGER.error(AkuraConstant.EMAIL_ERROR, e);
            throw new AkuraAppException(AkuraConstant.EMAIL_ERROR, e);
        }
        
        this.mailSender.send(message);
        
        return true;
    }
    
    /**
     * Method to get email content.
     * 
     * @param bean Holds CommonEmailBean object.
     * @return email content.
     * @throws AkuraAppException the AkuraAppException.
     */
    private String getcontent(final CommonEmailBean bean) throws AkuraAppException {

        String templatePath = null;
        templatePath =
                PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, APPSERVER_HOME)
                        + PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, TEMPLATE_PATH);
        String emailTemplateContent = null;
        String templateName = bean.getMailTemplate();
        
        if (templateName != null) {
            emailTemplateContent = EmailUtil.getTextContent(templatePath, templateName, bean.getObjectMap());
            if (emailTemplateContent == null) {
                LOGGER.debug(LOG_IDENTYFY_ERROR_WHEN_PROCESSING_THE_TEMPLATE);
            }
        } else {
            LOGGER.debug(LOG_TEMPLATE_MATCHING_FAILED);
        }
        
        return emailTemplateContent;
        
    }
    
}
