/*
 * � 2011 Virtusa(Pvt)Ltd. All rights reserved. CONFIDENTIAL AND

 * PROPRIETARY INFORMATION The information contained herein (the

 * 'Proprietary Information') is highly confidential and proprietary to and

 * constitutes trade secrets of Virtusa(Pvt)Ltd. The Proprietary Information

 * for Virtusa(Pvt)Ltd internal use only and shall not be published,

 * communicated, disclosed or divulged to any person, firm, corporation or

 * other legal entity, directly or indirectly, without the prior written

 * consent of Virtusa(Pvt)Ltd Information Management.
 */

package com.virtusa.akura.common.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.virtusa.akura.api.dto.Publication;
import com.virtusa.akura.api.dto.PublicationType;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.PublicationValidator;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.SortUtil;
import com.virtusa.akura.util.StaticDataUtil;

/**
 * This class manages all the Publication domain related functionalities.
 *
 * @author Virtusa Corporation
 */
@Controller
public class PublicationController {

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(PublicationController.class);

    /**
     * Represent the key of error message for duplicate publication.
     */
    private static final String PUBLICATION_EXIST = "REF.UI.PUBLICATION.EXIST";

    /**
     *  * Represents the key for the width for image.
     */
    private static final String IMAGE_WIDTH = "imageFile.width";

    /**
     * Represents the key for the height for image.
     */
    private static final String IMAGE_HIGHT = "imageFile.height";

    /**
     * Represents the name of the property file.
     */
    private static final String SYSTEM_CONFIG_PROPERTIES = "systemConfig";

    /**
     * Represents the model attribute name for the publication list.
     */
    private static final String PUBLICATION_LIST = "publicationList";

    /**
     * Represents the extension of the default image.
     */
    private static final String IMAGE_EXTENSION = ".jpg";

    /**
     * Represents the relative folder path for the publication images.
     */
    private static final String PUB_FOLDER_REL_PATH = "resources/publicationImages/";

    /**
     * Represents the key for the path of publication images path.
     */
    private static final String PUB_FOLDER_CONFIG_KEY = "publicationFolder.path";

    /** Holds the value for appserver.home. */
    private static final String APPSERVER_HOME = "appserver.home";

    /**
     * Represents the model attribute name for the image height.
     */
    private static final String IMAGE_HEIGHT_NAME = "imageHeight";

    /**
     * Represents the model attribute name for the width of the image.
     */
    private static final String IMAGE_WIDTH_NAME = "imageWidth";

    /**
     * Represents the name of the view to be redirected.
     */
    private static final String VIEW_MANAGE_PUBLICATION = "reference/managePublication";

    /**
     * Represents the name of the view for a new publication.
     */
    private static final String NEW_MANAGE_PUBLICATION = "/managePublication.htm";

    /**
     * Represents the name of the view for saving and updating the publication.
     */
    private static final String SAVE_OR_UPDATE_VIEW_NAME = "/saveOrUpdatePublication.htm";

    /**
     * Represents the name of the redirected view to the new publication.
     */
    private static final String REDIRECT_NEW_MANAGE_PUBLICATION = "redirect:managePublication.htm";

    /**
     * Represents the path of the default publication image.
     */
    private static final String DEFAULT_IMG_PATH = "resources/publicationImages/school_event.jpg";

    /**
     * Represents the model attribute name for the publicationType list.
     */
    private static final String PUBLICATION_TYPE_LIST = "publicationTypeList";

    /**
     * Represents the model attribute name for the error message.
     */
    private static final String MODEL_MESSAGE_NAME = "message";

    /**
     * Represents the command name for the publication.
     */
    private static final String MODEL_COMMAND_NAME = "publication";

    /**
     * Represents the name of the view for deletion of a publication.
     */
    private static final String DELETE_PUBLICATION = "/manageDeletePublication.htm";

    /**
     * Represents the model attribute name for the default image.
     */
    private static final String DEFAULT_IMG_NAME = "defaultImage";

    /**
     * Represents the model attribute name for the edited publication object.
     */
    private static final String EDIT_PUBLICATION = "editPublication";
    
    /**
     * Represents the model attribute name for the edited publication object.
     */
    private static final String SUB_NAME = "subformheader"; 

    /**
     * Represents the default format of the date.
     */
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * Represents the name of the view to retrieve the preview of image.
     */
    private static final String IMAGE_FOR_THE_PUB = "/getImageForThePub.htm";

    /**
     * Represents the name of the view to retrieve the preview of image.
     */
    private static final String IMAGE_PATH_NAME = "imagePath";

    /**
     * Represents the name of the view to retrieve the preview of image.
     */
    private static final String LOG_MESSAGE_SAVE_OR_UPDATE = "Save or update an instance of Publication";

    /** Represents the key for the image database size. */
    private static final String PUBLICATION_DATABASE_SIZE = "IMAGE.DATABASE.SIZE";

    /** Represents the key for the "showEditWindow". */
    private static final String SHOW_EDIT_WINDOW = "showEditWindow";

    /** Model attribute of "selectedObjId". */
    private static final String SELECTED_OBJ_ID = "selectedObjId";
    
    /**
     * Represents an instance of the CommonService.
     */
    private CommonService commonService;

    /**
     * Represents an instance of PublicationValidator.
     */
    private PublicationValidator publicationValidator;

    /**
     * Injects an instance of PublicationValidator to the controller.
     *
     * @param pubPublicationValidator - an instance of PublicationValidator.
     */
    public void setPublicationValidator(final PublicationValidator pubPublicationValidator) {

        this.publicationValidator = pubPublicationValidator;
    }

    /**
     * Sets an instance of CommonService.
     *
     * @param commonServiceVal - the instance of CommonService
     */
    public final void setCommonService(final CommonService commonServiceVal) {

        commonService = commonServiceVal;
    }

    /**
     * Returns a list of publicationTypes.
     *
     * @return a list of publicationTypes.
     * @throws AkuraAppException - The exception details that occurred when retrieving the publicationType
     *         list.
     */
    @ModelAttribute(PUBLICATION_TYPE_LIST)
    public List<PublicationType> getPublicationTypes() throws AkuraAppException {

        return SortUtil.sortPublicationTypeList(commonService.getPublicationTypes());
    }

    /**
     * Returns a list of publication list, height and the width of the image.
     *
     * @param model - a HashMap contains the publication list, height and the width of the image.
     * @return - a HashMap of the related data.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @ModelAttribute(PUBLICATION_LIST)
    public List<Publication> getPublications(final ModelMap model) throws AkuraAppException {

        StaticDataUtil.getImageHeight(SYSTEM_CONFIG_PROPERTIES, IMAGE_HIGHT);
        StaticDataUtil.getImageWidth(SYSTEM_CONFIG_PROPERTIES, IMAGE_WIDTH);
        model.addAttribute(IMAGE_HEIGHT_NAME, StaticDataUtil.getImageHeight(SYSTEM_CONFIG_PROPERTIES, IMAGE_HIGHT));
        model.addAttribute(IMAGE_WIDTH_NAME, StaticDataUtil.getImageWidth(SYSTEM_CONFIG_PROPERTIES, IMAGE_WIDTH));
        List<Publication> publicationList = SortUtil.sortPublicationsListByDate(commonService.getLatestPublications());
       // Collections.reverse(publicationList);
        return publicationList;
    }

    /**
     * Returns an instance of the publication and the path of the default image.
     *
     * @param model - a HashMap contains the data to be processed.
     * @return - a HashMap of the data to be processed.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(NEW_MANAGE_PUBLICATION)
    public String managePublication(final ModelMap model) throws AkuraAppException {

        final Publication publication = new Publication();
        model.addAttribute(MODEL_COMMAND_NAME, publication);
        model.addAttribute(DEFAULT_IMG_NAME, DEFAULT_IMG_PATH);
        return VIEW_MANAGE_PUBLICATION;
    }

    /**
     * Registers the given custom property editor for all properties of the Date type.
     *
     * @param binder - data binder used to register the Date objects.
     */
    @InitBinder
    public void initBinder(final WebDataBinder binder) {

        binder.getBindingResult();
        final SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * Saves or updates the relevant instance of publication.
     *
     * @param model - a HashMap contains the details of the publication.
     * @param publication - an instance of Publication.
     * @param result - the results of the errors.
     * @return - the view to be redirected.
     * @throws AkuraAppException - The exception details that occurred when saving or updating.
     */
    @RequestMapping(value = SAVE_OR_UPDATE_VIEW_NAME, method = RequestMethod.POST)
    public String saveOrUpdatePublication(final ModelMap model,
            @ModelAttribute(MODEL_COMMAND_NAME) final Publication publication, BindingResult result)
            throws AkuraAppException {

        LOG.debug(LOG_MESSAGE_SAVE_OR_UPDATE);
        String returnResult = VIEW_MANAGE_PUBLICATION; // name of the view to be redirected.
        publicationValidator.validate(publication, result);
        if (result.hasErrors()) {
            previewDatabaseImage(publication, model);
            model.addAttribute(SHOW_EDIT_WINDOW, SHOW_EDIT_WINDOW);
            model.addAttribute(SELECTED_OBJ_ID, publication.getPublicationId());
        } else {
            try {
                if (publication != null) {
                    MultipartFile multipartFile = publication.getMultipartFile();
                    // removes the extra white spaces between the words.
                    publication.setHeader(StaticDataUtil.removeExtraWhiteSpace(publication.getHeader()));
                    publication.setMessage(StaticDataUtil.removeExtraWhiteSpace(publication.getMessage()));
                    PublicationType publicationType =
                            commonService.findPublicationType(publication.getpType().getpTypeId());
                    publication.setpType(publicationType);
                    if (multipartFile != null && multipartFile.getSize() > 0) {
                        publication.setImage(multipartFile.getBytes());
                    }
                    if (publication.getPublicationId() > 0) {
                        if (publication.getMultipartFile() != null) {
                            MultipartFile findMultipartFile = publication.getMultipartFile();
                            if (findMultipartFile.getBytes().length > 0) {
                                publication.setImage(findMultipartFile.getBytes());
                            } else {
                                Publication findPublication =
                                        commonService.findPublication(publication.getPublicationId());
                                publication.setImage(findPublication.getImage());
                            }
                        }

                        commonService.updatePublication(publication);
                        model.addAttribute(PUBLICATION_LIST , getPublications(model));
                    } else {
                        boolean isExist = commonService.isExistPublication(publication.getHeader());
                        if(!isExist){
                        commonService.addPublication(publication);
                        returnResult = REDIRECT_NEW_MANAGE_PUBLICATION;
                        } else {
                            String message = new ErrorMsgLoader().getErrorMessage(PUBLICATION_EXIST);
                            model.addAttribute(MODEL_MESSAGE_NAME, message);
                            model.addAttribute(SHOW_EDIT_WINDOW, SHOW_EDIT_WINDOW);
                            model.addAttribute(SELECTED_OBJ_ID, publication.getPublicationId());
                            returnResult = VIEW_MANAGE_PUBLICATION;
                        }

                    }
                }
            } catch (AkuraAppException e) {
                if (e.getCause() instanceof TransientDataAccessResourceException){
                    String message = new ErrorMsgLoader().getErrorMessage(PUBLICATION_DATABASE_SIZE);
                    Publication newPublication = new Publication();
                    model.addAttribute(MODEL_COMMAND_NAME, newPublication);
                    model.addAttribute(MODEL_MESSAGE_NAME, message);
                    model.addAttribute(SHOW_EDIT_WINDOW, SHOW_EDIT_WINDOW);
                    model.addAttribute(SELECTED_OBJ_ID, publication.getPublicationId());
                    returnResult = VIEW_MANAGE_PUBLICATION;
                }
                if(e.getCause() instanceof DataIntegrityViolationException) {
                    String message = new ErrorMsgLoader().getErrorMessage(PUBLICATION_EXIST);
                    Publication newPublication = new Publication();
                    model.addAttribute(MODEL_COMMAND_NAME, newPublication);
                    model.addAttribute(MODEL_MESSAGE_NAME, message);
                    model.addAttribute(SHOW_EDIT_WINDOW, SHOW_EDIT_WINDOW);
                    model.addAttribute(SELECTED_OBJ_ID, publication.getPublicationId());
                    returnResult = VIEW_MANAGE_PUBLICATION;
                }
            } catch (IOException e) {
                LOG.debug("Error when retrieving the preview of the image ....", e);
            }
        }
        return returnResult;
    }

    /**
     * Deletes the relevant Publication object.
     *
     * @param model - a HashMap that contains information of the Publication
     * @param publication - an instance of Publication
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when deleting a Publication.
     */
    @RequestMapping(DELETE_PUBLICATION)
    public final String deletePublication(@ModelAttribute(MODEL_COMMAND_NAME) final Publication publication,
            final ModelMap model) throws AkuraAppException {

        String returnResult = REDIRECT_NEW_MANAGE_PUBLICATION;
        try {
            Publication findPublication = commonService.findPublication(publication.getPublicationId());
            commonService.deletePublication(findPublication);
        } catch (AkuraAppException e) {
            if (e.getCause() instanceof DataIntegrityViolationException) {
                String message = new ErrorMsgLoader().getErrorMessage("REF.UI.PROVINCE.DELETE");
                Publication newPublication = new Publication();
                model.addAttribute(MODEL_MESSAGE_NAME, message);
                model.addAttribute(MODEL_COMMAND_NAME, newPublication);
                returnResult = VIEW_MANAGE_PUBLICATION;
            } else {
                throw e;
            }
        }
        return returnResult;
    }

    /**
     * Retrieves the image from the database if an image exists for a given instance of the publication.
     *
     * @param publication - an instance of Publication.
     * @param model - a HashMap contains the information for the process.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    public void previewDatabaseImage(Publication publication, ModelMap model) throws AkuraAppException {

        if (publication != null && publication.getPublicationId() > 0) {
            model.addAttribute(IMAGE_PATH_NAME, DEFAULT_IMG_PATH);
            Publication publicationDB = commonService.findPublication(publication.getPublicationId());
            if (publicationDB.getImage() != null && publicationDB.getImage().length > 0) {
                byte[] image = publicationDB.getImage();
                String imageLoadPath =
                        PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, APPSERVER_HOME)
                                + PropertyReader.getPropertyValue(SYSTEM_CONFIG_PROPERTIES, PUB_FOLDER_CONFIG_KEY);
                imageLoadPath = imageLoadPath + publication.getPublicationId() + IMAGE_EXTENSION;
                StaticDataUtil.previewProfile(imageLoadPath, image);
                model.addAttribute(IMAGE_PATH_NAME, PUB_FOLDER_REL_PATH + publication.getPublicationId()
                        + IMAGE_EXTENSION);
            }
        }
    }

    /**
     * Retrieves the image for a given key of Publication.
     *
     * @param publication - an instance of publication.
     * @param model - a HashMap contains the information for processing.
     * @return - the name of the view to be redirected.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @RequestMapping(IMAGE_FOR_THE_PUB)
    public String getImageForThePub(@ModelAttribute(MODEL_COMMAND_NAME) final Publication publication,
            final ModelMap model) throws AkuraAppException {

        int publicationId = publication.getPublicationId();
        Publication findPublication = commonService.findPublication(publicationId);
        previewDatabaseImage(findPublication, model);
        model.addAttribute(EDIT_PUBLICATION, findPublication);
        model.addAttribute(SUB_NAME, "subformheader");
        return VIEW_MANAGE_PUBLICATION;
    }
}
