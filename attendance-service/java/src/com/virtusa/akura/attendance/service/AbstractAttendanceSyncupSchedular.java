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

package com.virtusa.akura.attendance.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Repository;

import com.virtusa.akura.api.dto.AttendanceShedular;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.util.DateUtil;

/**
 * This Service class schedules to copy the data of the student and teacher attendance data into the database.
 *
 * @author Virtusa Corporation
 */
@Repository
public abstract class AbstractAttendanceSyncupSchedular implements AttendanceSyncupScheduler {

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(AbstractAttendanceSyncupSchedular.class);

    /** Represents the number of the switch case. */
    private static final int CASE_FOUR = 4;

    /** Represents the number of the switch case. */
    private static final int CASE_THREE = 3;

    /** Represents the size of the byte array. */
    private static final int BYTE_ARRAY_SIZE = 1024;

    /** Represents the default time format. */
    private static final String DEFAULT_TIME = "HH:mm:ss";

    /** Represents the date with the time. */
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /** Represents a current reading file of the copied directory. */
    private File currentFile;

    /** Represents the map contains the status of the roll back of the spreadsheet files. */
    private Map<File, Boolean> rolbackStatus = new LinkedHashMap<File, Boolean>();

    /**
     * Retrieves the currently reading spreadsheet.
     *
     * @return - the currently reading spreadsheet.
     */
    public File getCurrentFile() {

        return currentFile;
    }

    /**
     * Sets the currently reading spreadsheet.
     *
     * @param fileCurrentFile - the currently reading spreadsheet.
     */
    public void setCurrentFile(File fileCurrentFile) {

        this.currentFile = fileCurrentFile;
    }

    /** {@inheritDoc} */
    public abstract void extract() throws AkuraAppException;

    /**
     * Iterates over all files in the temporary directory of the Student directory.
     *
     * @param folder - the temporary directory of the Student directory.
     */
    protected void copyFiles(File folder) {

        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            String excelPath = listOfFiles[i].getAbsolutePath();

            String threadName = Thread.currentThread().getName();
            LOG.info(threadName + AkuraConstant.SCHEDULAR_EXTRACT_BEGAN);
            copyFile(excelPath, getCopiedPath(listOfFiles[i].getName()));
             LOG.info(AkuraConstant.SCHEDULAR_EXTRACT_COMPLETED);
        }
    }

    /**
     * Splits the given admission number from dot.
     *
     * @param addmissionNo - given number of the admissionNo.
     * @return - the admissionNo.
     */
    protected String[] splitFromDot(String addmissionNo) {

        return addmissionNo.split("\\.");
    }

    /**
     * This method is used to print the cell data to the text file.
     *
     * @param cellDataList - List of the data's in the spreadsheet.
     * @param rolbackStatusPrint - a map contains the file name for the key and the roll back status for the
     *        value.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    private void printToTextFile(List<List<XSSFCell>> cellDataList, Map<File, Boolean> rolbackStatusPrint)
            throws AkuraAppException {

        List<AttendanceShedular> attendanceList = new ArrayList<AttendanceShedular>();

        for (List<XSSFCell> cellTempList : cellDataList) {
            AttendanceShedular attendance = new AttendanceShedular();
            
            for (int j = 0; j < cellTempList.size(); j++) {
                XSSFCell xssfCell = (XSSFCell) cellTempList.get(j);
                String stringCellValue = xssfCell.toString();

                // get attendance object.
                getAttendance(attendance, j, stringCellValue, xssfCell);

            }
            attendanceList.add(attendance);
        }
        formatFile(attendanceList, rolbackStatus);
    }

    /**
     * Converts the date into database format from the value taken from the cell.
     *
     * @param dateArray - array of string contains the date.
     * @param stringDate - represents the date.
     * @return - the date.
     */
    private String convertDateFromCell(String[] dateArray, String stringDate) {

        for (int index = 2; (index < dateArray.length && index >= 0); index--) {
            stringDate = stringDate + dateArray[index];
            if (index != 0) {
                stringDate = stringDate.concat("-");
            }
        }
        return stringDate;
    }

    /**
     * Returns the time related to the spreadsheet date.
     *
     * @param cell - the cell of the spreadsheet.
     * @return - the time of the spreadsheet.
     */
    private String convertDateToTime(XSSFCell cell) {

        cell.getRawValue();
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_TIME);
        return sdf.format(cell.getDateCellValue());
    }

    /**
     * Converts the data of the spreadsheet into the text file with the database format.
     *
     * @param attendance - an instance of the AttendanceShedular.
     * @param columnNo - number of the column.
     * @param stringCellValue - value of the cell.
     * @param cell - an cell of the spreadsheet.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    private void getAttendance(AttendanceShedular attendance, int columnNo, String stringCellValue, XSSFCell cell)
            throws AkuraAppException {

        switch (columnNo) {
            case 0:
                attendance.setAddmissionNo(stringCellValue);
                break;
            case 1:
                if (stringCellValue.indexOf('-') > -1) {
                    attendance.setDate((cell.getDateCellValue()));
                } else {
                    String[] dateArray = stringCellValue.split("/");
                    String stringDate = "";
                    stringDate = convertDateFromCell(dateArray, stringDate);

                    try {
                        Date date = DateUtil.convertStringToDate(stringDate);
                        attendance.setDate(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 2:
                String strDate = convertDateToTime(cell);
                attendance.setTimeIn(strDate);
                break;
            case CASE_THREE:
                attendance.setStatus(Double.parseDouble(stringCellValue));
                break;
            case CASE_FOUR:
                attendance.setReaderId(stringCellValue);
                break;
            default:
                break;
        }
    }

    /** {@inheritDoc} */
    public abstract void clean() throws AkuraAppException;

    /**
     * Deletes the spreadsheets and the directories.
     *
     * @param folder - the folder for the StudentAttendance or the StaffAttendance.
     * @param tempDirectory - the temporary folder for the StudentAttendance or the StaffAttendance.
     */
    protected void deleteFiles(File folder, File tempDirectory) {

        File[] listOfFiles = tempDirectory.listFiles();
        for (File directoryFile : listOfFiles) {
            for (Entry<File, Boolean> entry : rolbackStatus.entrySet()) {
                File key = entry.getKey();
                String fileName = key.getName();
                fileName = (fileName.split(","))[1];
                boolean value = entry.getValue();
                if ((directoryFile.getName()).equals(fileName) && !value) {
                    boolean success = directoryFile.delete();
                    if (success) {
                        LOG.info(AkuraConstant.SCHEDULAR_CLEAN_COPID_DELETE);
                        break;
                    } else {
                        LOG.info(AkuraConstant.SCHEDULAR_CLEAN_COPID_DELETE_SUCCESS);
                        break;
                    }
                } else {
                    LOG.info(AkuraConstant.ROLBACK_FILE_ERROR + directoryFile);
                }

            }

        }
        if (folder.exists()) {
            File[] temporaryFiles = folder.listFiles();
            if (temporaryFiles.length > 0) {
                for (File file : temporaryFiles) {
                    LOG.info(file.delete() + AkuraConstant.FOLDER_DELETED);
                }
            }
        }
        if (rolbackStatus.size() < 0) {
            LOG.info(tempDirectory.delete() + AkuraConstant.FOLDER_DELETED);
        }
    }

    /**
     * Copies the spreadsheet form the specific location to another location.
     * @param fileName - name of the file in the related attendance directory.
     *
     * @return - the path of the specific location that spreadsheet is.
     */
    protected abstract String getCopiedPath(String fileName);

    /**
     * Copies the file into a specific location from the specific directory.
     *
     * @param path - the path of the current location.
     * @param copiedPath - the path of the location to be copied the file.
     */
    protected void copyFile(String path, String copiedPath) {

        File file = getCopyDirLocation();

        LOG.debug(file.mkdirs());
        if (file.exists()) {
            try {
                LOG.info(AkuraConstant.SCHEDULAR_EXTRACT_WORKING);
                File f1 = new File(path);
                File f2 = new File(copiedPath);
                InputStream in = new FileInputStream(f1);

                // For Overwrite the file.
                OutputStream out = new FileOutputStream(f2);

                byte[] buf = new byte[BYTE_ARRAY_SIZE];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                LOG.info(AkuraConstant.SCHEDULAR_EXTRACT_COPIED);
            } catch (FileNotFoundException ex) {
                LOG.info(AkuraConstant.SCHEDULAR_EXTRACT_NOT_FOUND);
            } catch (IOException e) {
                LOG.info(AkuraConstant.SCHEDULAR_EXTRACT_ERROR);
            }
        }
    }

    /**
     * Returns the path of the student directory.
     *
     * @return - the path of the student directory.
     */
    protected abstract File getCopyDirLocation();

    /** {@inheritDoc} */
    public void transform() throws AkuraAppException {

        String threadName = Thread.currentThread().getName();
        LOG.info(threadName + AkuraConstant.SCHEDULAR_TRANSFORM_BEGAN);

        File folder = getCopyDirLocation();
        if (folder.exists()) {
            File[] listOfFiles = folder.listFiles();
            if (listOfFiles.length > 0) {
                for (File file : listOfFiles) {
                    /** create a new instance for cellDataList. */
                    List<List<XSSFCell>> cellDataList = new ArrayList<List<XSSFCell>>();
                    try {
                        LOG.info(AkuraConstant.SCHEDULAR_TRANSFORM_WORKING);
                        currentFile = file;
                        rolbackStatus.put(currentFile, Boolean.FALSE);
                        /**
                         * Create a new instance for FileInputStream class
                         */
                        FileInputStream fileInputStream = new FileInputStream(file);

                        /**
                         * Create a new instance for HSSFWorkBook Class
                         */
                        Workbook workBook = new XSSFWorkbook(fileInputStream);
                        XSSFSheet xssfSheet = (XSSFSheet) workBook.getSheetAt(0);

                        /**
                         * Iterates the rows and cells of the spreadsheet to get all the data.
                         */
                        Iterator<Row> rowIterator = xssfSheet.rowIterator();

                        while (rowIterator.hasNext()) {
                            XSSFRow xssfFRow = (XSSFRow) rowIterator.next();
                            Iterator<Cell> iterator = xssfFRow.cellIterator();
                            List<XSSFCell> cellTempList = new ArrayList<XSSFCell>();
                            while (iterator.hasNext()) {
                                XSSFCell xssfCell = (XSSFCell) iterator.next();
                                cellTempList.add(xssfCell);
                            }
                            cellDataList.add(cellTempList);
                        }
                    } catch (IOException e) {
                        throw new AkuraAppException(AkuraConstant.SCHEDULAR_TRANSFORM_ERROR, e);
                    }

                    /**
                     * Call the printToTextFile method to print the cell data in the text file.
                     */
                    printToTextFile(cellDataList, rolbackStatus);
                    LOG.info(threadName + AkuraConstant.SCHEDULAR_TRANSFORM_COMPLETED);
                }
            }
        }
    }

    /**
     * Transforms the data of the spreadsheet into the format of the database.
     *
     * @param attendanceList - a list of attendance.
     * @param rolbackStatusMap - a map contains the file name for the key and the roll back status for the
     *        value.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    protected abstract void formatFile(List<AttendanceShedular> attendanceList, Map<File, Boolean> rolbackStatusMap)
            throws AkuraAppException;
}
