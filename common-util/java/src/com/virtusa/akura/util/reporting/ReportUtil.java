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

package com.virtusa.akura.util.reporting;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.log4j.Logger;

import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.util.PropertyReader;

/**
 * @author Virtusa Corporation
 */

public final class ReportUtil {

    /** Holds the value for reportLogoPath. */
    private static final String REPORT_LOGO_PATH = "reportLogoPath";

    /** Holds the value for application/pdf. */
    private static final String APPLICATION_PDF = "application/pdf";

    /** Holds the value for html. */
    private static final String HTML = "html";

    /** Holds the value for pdf. */
    private static final String PDF = "pdf";

    /** Holds the value for .jrxml. */
    private static final String JRXML_EXT = ".jrxml";

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(ReportUtil.class);

    /**
     * Report template property file.
     */
    public static final String REPORT_TEMPLATE = "reportTemplate";

    /** Holds the value for appserver.home. */
    private static final String APPSERVER_HOME = "appserver.home";

    /** Holds the value for sourceJrxmlPath. */
    private static final String SOURCE_JRXML_PATH = "sourceJrxmlPath";

    /**
     * systemConfig property file.
     */
    public static final String SYSTEM_CONFIG = "systemConfig";

    /**
     * private constructor.
     */
    private ReportUtil() {

    }

    /**
     * Returns the path for the report template. Report template can be in two formats with the extensions of
     * ".jrxml" and ".jasper". Report name and template file name should be similar. Given the name of the
     * report and the correct extension for the template correct path for the template is returned.
     *
     * @param strReportName {@link String}
     * @return path {@link String}
     */
    private static String getJasperTemplatePath(final String strReportName) {

        return PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                + PropertyReader.getPropertyValue(SYSTEM_CONFIG, SOURCE_JRXML_PATH) + strReportName + JRXML_EXT;
    }

    /**
     * Generate the report in given format.
     *
     * @param type {@link String} the type of the report where types are pdf,html,etc.
     * @param name {@link String} name of the report. Name is directly related to the name of the jasper
     *        template and will be used to get the location of the jasper template.
     * @param parameters {@link HashMap} includes the details of the report like title etc.
     * @param ds {@link JRBeanCollectionDataSource} Collections of data to be used in making the report.
     * @return output {@link byte[]}
     * @throws AkuraAppException akuraAppException
     */
    private static byte[] generateReport(String type, String name, Map<String, Object> parameters,
            JRBeanCollectionDataSource ds) throws AkuraAppException {

        final String jasperTemplatePath = getJasperTemplatePath(name);
        final JasperPrint jPrint = generateJasperPrint(jasperTemplatePath, parameters, ds);
        return getOutputStreamForType(type, jPrint);
    }

    /**
     * Generate the jasperPrint object to be used in report generation.
     *
     * @param path {@link String} location of jasper template.
     * @param parameters {@link HashMap} includes the details of the report like title etc.
     * @param ds {@link JRBeanCollectionDataSource} Collections of data to be used in making the report.
     * @return jasperPrint {@link JasperPrint} jasper report object with data.
     * @throws AkuraAppException SMSAppException
     */
    private static JasperPrint generateJasperPrint(String path, Map<String, Object> parameters,
            JRBeanCollectionDataSource ds) throws AkuraAppException {

        JasperPrint jPrint = null;

        try {
            JasperDesign jasperDesign = JRXmlLoader.load(path);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            jPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);
        } catch (JRException e) {
            LOG.error(AkuraConstant.REPORTING_IO_EXCEPTION, e);
            throw new AkuraAppException(AkuraConstant.REPORTING_IO_EXCEPTION, e);
        }

        return jPrint;

    }

    /**
     * Generates the byte output stream for pdf view.
     *
     * @param jPrint {@link JasperPrint}
     * @return output {@link byte[]}
     * @throws AkuraAppException SMSAppException
     */
    private static byte[] generatePdfStream(final JasperPrint jPrint) throws AkuraAppException {

        byte[] output = null;
        try {
            output = JasperExportManager.exportReportToPdf(jPrint);
        } catch (JRException e) {
            LOG.error(AkuraConstant.REPORTING_IO_EXCEPTION, e);
            throw new AkuraAppException(AkuraConstant.REPORTING_IO_EXCEPTION, e);
        }
        return output;
    }

    /**
     * Generate Stream for Html format.
     *
     * @param jPrint {@link JasperPrint} JasperPrint object with data.
     * @return output {@link byte[]}
     * @throws AkuraAppException SMSAppException
     */
    private static byte[] generateHTMLStream(JasperPrint jPrint) throws AkuraAppException {

        final JRExporter exporter = new JRHtmlExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jPrint);
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

        try {
            exporter.exportReport();
        } catch (JRException e) {
            LOG.error(AkuraConstant.REPORTING_IO_EXCEPTION, e);
            throw new AkuraAppException(AkuraConstant.REPORTING_IO_EXCEPTION, e);
        }
        return baos.toByteArray();
    }

    /**
     * Generate Stream for given format.
     *
     * @param type {@link String} type of stream like pdf,html etc.
     * @param jprint {@link JasperPrint} JasperPrint object with data.
     * @return output {@link byte[]}
     * @throws AkuraAppException SMSAppException
     */
    private static byte[] getOutputStreamForType(String type, JasperPrint jprint) throws AkuraAppException {

        byte[] output = null;

        if (PDF.equals(type)) {
            output = generatePdfStream(jprint);
        } else if (HTML.equals(type)) {
            output = generateHTMLStream(jprint);
        }
        return output;
    }

    /**
     * Gets the report logo.
     *
     * @return the report logo.
     */
    public static String getReportLogo() {

        return PropertyReader.getPropertyValue(SYSTEM_CONFIG, APPSERVER_HOME)
                + PropertyReader.getPropertyValue(SYSTEM_CONFIG, REPORT_LOGO_PATH);
    }

    /**
     * Method to show the report in pdf form.
     *
     * @param response of type HttpServletResponse
     * @param jrBeanDataSource of type JRBeanCollectionDataSource
     * @param jrxml of type java.lang.String
     * @param params of type HashMap
     * @throws AkuraAppException SMSAppException
     */
    public static void displayReportInPdfForm(HttpServletResponse response,
            JRBeanCollectionDataSource jrBeanDataSource, Map<String, Object> params, String jrxml)
            throws AkuraAppException {

        byte[] report = generateReport(PDF, jrxml, params, jrBeanDataSource);

        try {
            ServletOutputStream servletOutputStream = response.getOutputStream();
            response.setContentType(APPLICATION_PDF);
            servletOutputStream.write(report);
            servletOutputStream.flush();
            servletOutputStream.close();
        } catch (IOException ex) {
            LOG.error(AkuraConstant.REPORTING_IO_EXCEPTION, ex);
            throw new AkuraAppException(AkuraConstant.REPORTING_IO_EXCEPTION, ex);
        }

    }

}
