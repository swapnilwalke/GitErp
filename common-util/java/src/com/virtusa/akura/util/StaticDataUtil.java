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

package com.virtusa.akura.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.PropertyResourceBundle;

import org.apache.log4j.Logger;

import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * @author Virtusa Corporation
 */
public final class StaticDataUtil {
    
    /** String attribute for holding TO_DAYS. */
    private static final int TO_DAYS = 24;
    
    /** String attribute for holding TO_HOURS. */
    private static final int TO_HOURS = 60;
    
    /** String attribute for holding TO_MINIUTES. */
    private static final int TO_MINIUTES = 60;
    
    /** String attribute for holding TO_SECONDS. */
    private static final int TO_SECONDS = 1000;
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(StaticDataUtil.class);
    
    /** Holds date formatter. */
    private static SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM d, yyyy");
    
    /**
     * The default constructor.
     */
    private StaticDataUtil() {
    
    }
    
    /**
     * Returns the number of leave days.
     * 
     * @param startDate - started date.
     * @param endDate - end date.
     * @return - the number of days.
     */
    public static int daysBetween(Date startDate, Date endDate) {
    
        return (int) (((endDate.getTime() - startDate.getTime()) / 
                (TO_SECONDS * TO_MINIUTES * TO_HOURS * TO_DAYS)) + 1);
    }
    
    /**
     * Get date when the jsp calls the EL function.
     * 
     * @return the Date string.
     */
    public static String getDate() {
    
        return formatter.format(new Date());
    }
    
    /**
     * Get List of properties according to the given attribute.
     * 
     * @param bundle the property bundle
     * @param tab the tab category name
     * @return the Date string.
     */
    public static String[] getTabContent(PropertyResourceBundle bundle, String tab) {
    
        return bundle.getString(tab + ".subTabs").split(",");
    }
    
    /**
     * Creates an image file from the byte[].
     * 
     * @param imageLoadPath - the location of the image will be created
     * @param image - byte[] of the image
     * @throws AkuraAppException - details when processing
     */
    public static void previewProfile(String imageLoadPath, byte[] image) throws AkuraAppException {
    
        try {
            OutputStream fos = new FileOutputStream(imageLoadPath);
            fos.write(image);
            fos.close();
        } catch (IOException e) {
            LOG.error("Error while retrieving the file" + "-->" + e.toString());
            throw new AkuraAppException(AkuraConstant.FILE_NOT_FOUND, e);
        }
        
    }
    
    /**
     * Removes the extra white spaces between the words.
     * 
     * @param description - description of the object.
     * @return - the description that removed the whitespaces.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    public static String removeExtraWhiteSpace(String description) throws AkuraAppException {
    
        return description.trim().replaceAll("( )+", " ");
    }
    
    /**
     * Retrieves the width of the publication image.
     * 
     * @param sysConfigProperties - name of the System configuration file.
     * @param eventImgWidth - width of the publication image.
     * @return imageheight - specified by an integer variable.
     */
    public static int getImageWidth(String sysConfigProperties, String eventImgWidth) {
    
        String strfileWidth = PropertyReader.getPropertyValue(sysConfigProperties, eventImgWidth);
        return Integer.parseInt(strfileWidth);
    }
    
    /**
     * Retrieves the height of the publication image.
     * 
     * @param sysConfigProperties - name of the System configuration file.
     * @param eventImgHieght - hieght of the publication image.
     * @return imageheight - specified by an integer variable.
     */
    public static int getImageHeight(String sysConfigProperties, String eventImgHieght) {
    
        String strfileHeight = PropertyReader.getPropertyValue(sysConfigProperties, eventImgHieght);
        return Integer.parseInt(strfileHeight);
    }
    
    /**
     * return number with defined minimum no of digits and fractional points.
     * 
     * @param miniimumfractionalPoints minimum fractional points that number has
     * @param number number to format
     * @return string
     */
    public static String setNoOfDigitsInNumber(int miniimumfractionalPoints, double number) {
    
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        nf.setMinimumFractionDigits(miniimumfractionalPoints);
        nf.setMaximumFractionDigits(2);
        return nf.format(number);
    }
    
    /**
     * Return a list with a given size.
     * 
     * @param maxSize required size of the list
     * @return list
     */
    public static List<String> getListWithEmptyValues(int maxSize) {
    
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < maxSize; i++) {
            list.add(AkuraConstant.EMPTY_STRING);
        }
        return list;
    }
    
    /**
     * Appends the string buffer objects with the given separation character.
     * 
     * @param allClasses - the string buffer that appends everything.
     * @param isFirst - the status of the iteration
     * @param classes - the string buffer to be appended.
     * @param separation - the separation character.
     * @return - the status of the iteration.
     */
    public static boolean appendValues(StringBuilder allClasses, boolean isFirst, StringBuilder classes,
            String separation) {
    
        if (isFirst) {
            allClasses.append(classes.toString()); // no comma
            isFirst = false;
        } else {
            allClasses.append(separation); // comma
            allClasses.append(classes.toString());
        }
        classes.delete(0, classes.length());
        return isFirst;
    }
    
    /**
     * Rounds the double or float value into required decimal number.
     * 
     * @param value - the value need to round.
     * @param decimalPoints - the number of decimal places.
     * @return - the rounded number.
     */
    public static double roundNumber(final double value, final int decimalPoints) {
    
        String decimalPattern = AkuraConstant.ROUND_PATTERN;
        for (int i = 0; i < decimalPoints; i++) {
            decimalPattern = decimalPattern + AkuraConstant.ROUNDED_DECIMAL;
        }
        DecimalFormat df = new DecimalFormat(decimalPattern);
        return Double.parseDouble(df.format(value));
    }
    
    /**
     * Calculates the marks according to the maximum mark for the grade subject.
     * 
     * @param marks - the marks.
     * @param maximumMark - the relevant maximum mark for the grade subject key.
     * @return - the rounded mark
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    public static float previewMaximumMark(float marks, int maximumMark) throws AkuraAppException {
    
        final int percentage = 100;
        marks = (marks / percentage) * maximumMark;
        return (float) StaticDataUtil.roundNumber(marks, 0);
    }
}
