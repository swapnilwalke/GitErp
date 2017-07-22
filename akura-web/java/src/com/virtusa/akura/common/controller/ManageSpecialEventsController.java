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

package com.virtusa.akura.common.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.ClubSociety;
import com.virtusa.akura.api.dto.ParticipantCategory;
import com.virtusa.akura.api.dto.SpecialEvents;
import com.virtusa.akura.api.dto.SpecialEventsParticipation;
import com.virtusa.akura.api.dto.SportCategory;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.AkuraWebConstant;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.SpecialEventsValidator;

import com.virtusa.akura.util.SortUtil;

/**
 * This class manages all the SpecialEvents domain related functionalities.
 * 
 * @author Virtusa Corporation
 */
@Controller
public class ManageSpecialEventsController {
    
    /** get method to view SpecialEvent. */
    private static final String VIEW_GET_CREATE_SPECIAL_EVENTS = "reference/manageSpecialEvents";
    
    /** post method to view SpecialEvent. */
    private static final String VIEW_POST_CREAT_SPECIAL_EVENTS = "redirect:manageSpecialEvents.htm";
    
    /** model attribute of SpecialEvent list. */
    private static final String MODEL_ATT_SPECIAL_EVENT_MAP = "specialEventMap";
    
    /** model attribute of Participant Category list. */
    private static final String MODEL_ATT_PARTICIPANT_CATEGORY_LIST = "categoryList";
    
    /** request attribute of `ToList` value. */
    private static final String REQUEST_SELECTED_LIST = "ToList";
    
    /** model attribute of Sport. */
    private static final String MODEL_ATT_SPECIAL_EVENT = "specialEvents";
    
    /** key to hold special event category type 3. */
    private static final int CATEGORY_TYPE_3 = 3;
    
    /** request mapping value for delete specialEvent. */
    private static final String DELETE_SPECIAL_EVENT_HTM = "/deleteSpecialEvent.htm";
    
    /** String attribute for holding DEFALUT_DATE_FORMAT. */
    private static final String DEFALUT_DATE_FORMAT = "yyyy-MM-dd";
    
    /** attribute for holding error message key. */
    private static final String REF_UI_SPECIAL_EVENT_DELETE = "REF.UI.SPECIAL.EVENT.DELETE";
    
    /** attribute for holding error message key. */
    private static final String REF_UI_SPECIAL_EVENT_EDIT = "REF.UI.SPECIAL.EVENT.EDIT";
    
    /** model attribute of message. */
    private static final String MODEL_ATT_MESSAGE = "message"; 
    
    /** model attribute of selected object. */
    private static final String MODEL_ATT_OBJECT_FORM = "specialEvents";
    
    /** model attribute of selected object. */
    private static final String MODEL_ATT_SELECTED_OBJECT = "selectedObj";
    
    /** model attribute of selected object. */
    private static final String MODEL_ATT_SELECTED_OBJECT_ID = "ObjId";  
    
    /** model attribute of selected object. */
    private static final String MODEL_ATT_OBJECT_CATEGORY = "objCategory";
    
    /** model attribute of selected object. */
    private static final String MODEL_ATT_OBJECT_PARTICIPANT = "objParitcipantList";
    
    /** Model attribute of "showEditSection". */
    private static final String SHOW_EDIT_SECTION = "showEditSection";
    
    /** CommonService attribute for holding commonService. */
    private CommonService commonService;
    
    /** SpecialEventsValidator attribute for holding specialEventsValidator. */
    private SpecialEventsValidator specialEventsValidator;
    
    /**
     * Set SpecialEventsValidator object.
     * 
     * @param specialEventsValidatorObj set SpecialEventsValidator object.
     */
    public void setSpecialEventsValidator(SpecialEventsValidator specialEventsValidatorObj) {
    
        this.specialEventsValidator = specialEventsValidatorObj;
    }
    
    /**
     * Set CommonService object.
     * 
     * @param objCommonService set CommonService object.
     */
    public void setCommonService(CommonService objCommonService) {
    
        this.commonService = objCommonService;
    }
    
    /**
     * ManageSpecialEventsController constructor.
     */
    public ManageSpecialEventsController() {
    
    }
    
    /**
     * intiBinder method is to bind date class with the date format.
     * 
     * @param binder - data binder used to register the Date objects.
     */
    @InitBinder
    public void initBinder(final WebDataBinder binder) {
       
       binder.getBindingResult();
       SimpleDateFormat dateFormat = new SimpleDateFormat(DEFALUT_DATE_FORMAT);
       binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        
    }
    
    /**
     * handle GET requests for SpecialEvent_details view.
     * 
     * @param model - ModelMap
     * @param request - HttpServletRequest
     * @param session - HttpSession
     * @return the name of the view.
     * @throws AkuraAppException - Detailed exception
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showSpecialEvent(ModelMap model, HttpServletRequest request, HttpSession session)
            throws AkuraAppException {
    
        SpecialEvents specialEvents = (SpecialEvents) model.get("specialEvents");
        
        if (specialEvents == null) {
            specialEvents = new SpecialEvents();
        }
        model.addAttribute("specialEvents", specialEvents);
        
        return VIEW_GET_CREATE_SPECIAL_EVENTS;
    }
    
    /**
     * Create or update SpecialEvent details.
     * 
     * @param request - HttpServletRequest
     * @param model - ModelMap
     * @param specialEvents - SpecialEvents object
     * @param bindingResult - BindingResult
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - if error occurs when save or update a FaithLifeRating instance.
     */
    @RequestMapping(value = "/saveOrUpdateSpecialEvent.htm", method = RequestMethod.POST)
    public String saveOrUpdateSpecialEvent(@ModelAttribute(MODEL_ATT_SPECIAL_EVENT) SpecialEvents specialEvents,
            BindingResult bindingResult, HttpServletRequest request, ModelMap model) throws AkuraAppException {
               
        String[] toList = null; 
        
        if (request.getParameterValues(REQUEST_SELECTED_LIST) != null) {
            toList = request.getParameterValues(REQUEST_SELECTED_LIST);
        }
        
        // Trim the special event name
        specialEvents.setName(specialEvents.getName().trim());
        
        specialEventsValidator.validate(specialEvents, bindingResult);
        
        int intSpecialEventId = specialEvents.getSpecialEventsId();
        String strMessage = null;
        SpecialEvents spEvent;
        String finalToList = null; 
        
        if (bindingResult.hasErrors()) {
            
            if (specialEvents.getSpecialEventsId() != 0) {
                spEvent = commonService.findSpecialEventsById(specialEvents.getSpecialEventsId());
                model.addAttribute(MODEL_ATT_SELECTED_OBJECT, spEvent);
            }
            
            errorMassage(model, specialEvents);
             
            if (request.getParameterValues(REQUEST_SELECTED_LIST) != null) {
                toList = request.getParameterValues(REQUEST_SELECTED_LIST);
                finalToList = this.toListToString(specialEvents, toList);
                
            }
            model.addAttribute(MODEL_ATT_OBJECT_PARTICIPANT, finalToList);
            model.addAttribute(MODEL_ATT_OBJECT_FORM, specialEvents);
            return VIEW_GET_CREATE_SPECIAL_EVENTS;
        } else {
            if (toList == null) {
                
                if (specialEvents.getSpecialEventsId() != 0) {
                    spEvent = commonService.findSpecialEventsById(specialEvents.getSpecialEventsId());
                    model.addAttribute(MODEL_ATT_SELECTED_OBJECT, spEvent);
                }
                
                errorMassage(model, specialEvents);
                if (request.getParameterValues(REQUEST_SELECTED_LIST) != null) {
                    toList = request.getParameterValues(REQUEST_SELECTED_LIST);
                    finalToList = this.toListToString(specialEvents, toList);
                    
                }
                model.addAttribute(MODEL_ATT_OBJECT_PARTICIPANT, finalToList);    
                strMessage = new ErrorMsgLoader().getErrorMessage(AkuraWebConstant.MANDATORY_FIELD_ERROR_CODE);
                model.addAttribute(MODEL_ATT_MESSAGE, strMessage);
                model.addAttribute(MODEL_ATT_OBJECT_FORM, specialEvents);
                return VIEW_GET_CREATE_SPECIAL_EVENTS;
                
            } else {
                try {
                    
                    if (commonService.isExistsSpecialEvents(specialEvents)) {
                        
                        if (intSpecialEventId != 0) {
                            List<String> toListt = new ArrayList<String>();
                            spEvent = commonService.findSpecialEventsById(intSpecialEventId);
                            
                            if (spEvent.getSpecialEventsId() == specialEvents.getSpecialEventsId()
                                    && spEvent.getName().equalsIgnoreCase(specialEvents.getName())
                                    && (spEvent.getDate().compareTo(specialEvents.getDate())) == 0
                                    && spEvent.getParticipantCategory().getParticipantCategoryId() == specialEvents
                                            .getParticipantCategory().getParticipantCategoryId()
                                    && spEvent.getDescription().equalsIgnoreCase(specialEvents.getDescription())) {
                                toList = request.getParameterValues(REQUEST_SELECTED_LIST);
                                List<String> toListtt = Arrays.asList(toList);
                                List<SpecialEventsParticipation> participants =
                                        commonService.getParticipantListBySpecialEvent(spEvent);
                                
                                String participantId = null;
                                int partId = 0;
                                for (SpecialEventsParticipation participant : participants) {    
                                    
                 if(spEvent.getParticipantCategory().getParticipantCategoryId() == AkuraConstant.PARAM_INDEX_ONE   ){
                                        partId = participant.getClassGrade().getClassGradeId();
                }else if(spEvent.getParticipantCategory().getParticipantCategoryId()== AkuraConstant.PARAM_INDEX_TWO){
                                        partId = participant.getSportCategory().getSportCategoryId();
                }else if(spEvent.getParticipantCategory().getParticipantCategoryId()== AkuraConstant.PARAM_INDEX_THREE){
                                        partId = participant.getClubSociety().getClubSocietyId();
                                    }
                                   participantId = String.valueOf(partId);
                                   toListt.add(participantId);                                   
                                }    
                                  if (toListt.equals(toListtt)){
                                      return VIEW_GET_CREATE_SPECIAL_EVENTS;
                                  }
                            }  
                            // editing selected object with detail or participation list
                            if (spEvent.getName().equalsIgnoreCase(specialEvents.getName())
                                    && (spEvent.getDate().compareTo(specialEvents.getDate())) == 0) {
                                
                           // deleting all specilEvents participation list related to given                             
                                List<SpecialEventsParticipation> participants =
                                        commonService.getParticipantListBySpecialEvent(spEvent);          
                                commonService.deleteAllSpecialEventsParticipation(participants);
                                commonService.editSpecialEvents(specialEvents);
                                createSpecialEventsParticipant(toList, spEvent);
                                return VIEW_POST_CREAT_SPECIAL_EVENTS;
                                
                            } else {
                                
                                spEvent = commonService.findSpecialEventsById(specialEvents.getSpecialEventsId());
                                if (request.getParameterValues(REQUEST_SELECTED_LIST) != null) {
                                    toList = request.getParameterValues(REQUEST_SELECTED_LIST);
                                    finalToList = this.toListToString(specialEvents, toList);
                                    
                                }
                                model.addAttribute(MODEL_ATT_OBJECT_PARTICIPANT, finalToList); 
                                model.addAttribute(MODEL_ATT_SELECTED_OBJECT, spEvent);
                                model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
                                model.addAttribute(MODEL_ATT_SELECTED_OBJECT_ID, intSpecialEventId);
                                bindingResult.rejectValue("specialEventsId", AkuraWebConstant.ALREADY_EXIST_ERROR_CODE);
                                return VIEW_GET_CREATE_SPECIAL_EVENTS;
                            }      
                        } else {
                            model.addAttribute(MODEL_ATT_SELECTED_OBJECT_ID, intSpecialEventId);
                            errorMassage(model,specialEvents); 
                            if (request.getParameterValues(REQUEST_SELECTED_LIST) != null) {
                                toList = request.getParameterValues(REQUEST_SELECTED_LIST);
                                finalToList = this.toListToString(specialEvents, toList);    
                            }
                            model.addAttribute(MODEL_ATT_OBJECT_PARTICIPANT, finalToList); 
                            model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
                            bindingResult.rejectValue("specialEventsId", AkuraWebConstant.ALREADY_EXIST_ERROR_CODE);
                            return VIEW_GET_CREATE_SPECIAL_EVENTS;
                        }                      
                    } else {
                        
                        if (intSpecialEventId == 0) {
                            spEvent = commonService.addSpecialEvents(specialEvents);
                            createSpecialEventsParticipant(toList, spEvent);
                            
                        } else {                          
                            spEvent = commonService.findSpecialEventsById(intSpecialEventId);                          
                            SpecialEvents speEvent = commonService.findSpecialEventsById(intSpecialEventId);
                            // deleting all specilEvents participation list related to given specialEvent
                            List<SpecialEventsParticipation> participants =
                                    commonService.getParticipantListBySpecialEvent(speEvent);
                            commonService.deleteAllSpecialEventsParticipation(participants);
                            commonService.editSpecialEvents(specialEvents);                           
                            createSpecialEventsParticipant(toList, speEvent);                            
                        }                       
                    }                   
                } catch (AkuraAppException e) {
                    if (e.getCause() instanceof DataIntegrityViolationException) {
                        strMessage = new ErrorMsgLoader().getErrorMessage(
                                REF_UI_SPECIAL_EVENT_EDIT);                        
                        errorMassage(model,specialEvents);  
                       // SpecialEvents newSpecialEvents = new SpecialEvents();
                        if (request.getParameterValues(REQUEST_SELECTED_LIST) != null) {
                            toList = request.getParameterValues(REQUEST_SELECTED_LIST);
                            finalToList = this.toListToString(specialEvents, toList);               
                        }
                        model.addAttribute(MODEL_ATT_OBJECT_PARTICIPANT, finalToList); 
                        model.addAttribute(MODEL_ATT_SELECTED_OBJECT_ID, intSpecialEventId);
                        model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
                        model.addAttribute(MODEL_ATT_MESSAGE, strMessage);
                       // model.addAttribute("specialEvents", newSpecialEvents);
                        return VIEW_GET_CREATE_SPECIAL_EVENTS;
                    } else {
                        throw e;
                    }
                }               
            }
        }
        return VIEW_POST_CREAT_SPECIAL_EVENTS;
    }
 
    /**
     * errorMassege method to be kept error message in model attribute.
     * 
     * @param model - ModelMap
     * @param specialEvents - SpecialEvents object
     * 
     */
   private void errorMassage(ModelMap model,SpecialEvents specialEvents){
           
       int eCategory = 0;                  
       int intSpecialEventId = specialEvents.getSpecialEventsId();
       
       eCategory = specialEvents.getParticipantCategory().getParticipantCategoryId();
    
       model.addAttribute(SHOW_EDIT_SECTION, SHOW_EDIT_SECTION);
       model.addAttribute(MODEL_ATT_SELECTED_OBJECT_ID, intSpecialEventId);
       model.addAttribute(MODEL_ATT_OBJECT_CATEGORY, eCategory);
   }

  
    /**
     * Create SpecialEventsParticipant objects.
     * 
     * @param toList - List of String
     * @param specialEvent - SpecialEvents object
     * @throws AkuraAppException - if error occurs when save or update a FaithLifeRating instance.
     */
    private void createSpecialEventsParticipant(String[] toList, SpecialEvents specialEvent) throws AkuraAppException {
    
        // get the updated special event
        SpecialEvents speEvent = commonService.getSpecialEventById(specialEvent.getSpecialEventsId());
        for (String z : toList) {
            int id = Integer.parseInt(z);
            SpecialEventsParticipation sep = new SpecialEventsParticipation();
            sep.setSpecialEvents(speEvent);
            if (speEvent.getParticipantCategory().getParticipantCategoryId() == 1) {
                // class wise
                sep.setClassGrade(commonService.findClassGrade(id));
            } else if (speEvent.getParticipantCategory().getParticipantCategoryId() == 2) {
                // sport wise
                sep.setSportCategory(commonService.findSportCategoryById(id));
            } else if (speEvent.getParticipantCategory().getParticipantCategoryId() == CATEGORY_TYPE_3) {
                // clubSociety wise
                sep.setClubSociety(commonService.findClubSocietyById(id));
            }
            
            commonService.addSpecialEventsParticipation(sep);
        }
    }
    
    /**
     * Delete specialEvent details.
     * 
     * @param specialEvents - SpecialEvents object
     * @param request - HttpServletRequest
     * @param session - HttpSession
     * @param model - ModelMap
     * @return name of the view which is redirected to.
     * @throws AkuraAppException - if error occurs when deleting a SpecialEvents instance.
     */
    @RequestMapping(value = DELETE_SPECIAL_EVENT_HTM, method = RequestMethod.POST)
    public String deleteSpecialEvents(@ModelAttribute(MODEL_ATT_SPECIAL_EVENT) SpecialEvents specialEvents,
            HttpServletRequest request, HttpSession session, ModelMap model) throws AkuraAppException {
    
        String strMessage = null;
        try {
            
            commonService.deleteSpecialEvents(specialEvents.getSpecialEventsId());
            return VIEW_POST_CREAT_SPECIAL_EVENTS;
            
        } catch (AkuraAppException e) {
            if (e.getCause() instanceof DataIntegrityViolationException) {
                strMessage = new ErrorMsgLoader().getErrorMessage(REF_UI_SPECIAL_EVENT_DELETE);
                SpecialEvents newSpecialEvents = new SpecialEvents();
                model.addAttribute(MODEL_ATT_MESSAGE, strMessage);
                model.addAttribute("specialEvents", newSpecialEvents);
                return VIEW_GET_CREATE_SPECIAL_EVENTS;
            } else {
                throw e;
            }
        }
        
    }
    
    /**
     * Method is to return SpecialEvents map.
     * 
     * @return map of Special Events
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_SPECIAL_EVENT_MAP)
    public Map<SpecialEvents, String> populateSpecialEventWithParticipant() throws AkuraAppException {
    
        List<SpecialEvents> specialEventLst = SortUtil.sortSpecialEventsList(commonService.getSpecialEventList());
        
        // Map<SpecialEvents, String> lastMap = new HashMap<SpecialEvents, String>();
        Map<SpecialEvents, String> lastMap = new LinkedHashMap<SpecialEvents, String>();
        List<SpecialEventsParticipation> participants = null;
        
        for (SpecialEvents event : specialEventLst) {
            
            participants = commonService.getParticipantListBySpecialEvent(event);
            StringBuilder allParticipents = new StringBuilder();
            
            StringBuilder participents = new StringBuilder();
            boolean isFirst = true;
            
            for (SpecialEventsParticipation cg : participants) {
                
                if (cg.getClassGrade() != null) {
                    
                    participents.append(cg.getClassGrade().getDescription());
                    
                }
                if (cg.getClubSociety() != null) {
                    
                    participents.append(cg.getClubSociety().getName());
                    
                }
                if (cg.getSportCategory() != null) {
                    
                    participents.append(cg.getSportCategory().getSport().getDescription());
                    participents.append("-");
                    participents.append(cg.getSportCategory().getSportSubCategory().getDescription());
                    
                }
                
                if (isFirst) {
                    allParticipents.append(participents.toString()); // no comma
                    isFirst = false;
                } else {
                    allParticipents.append(", "); // comma
                    allParticipents.append(participents.toString());
                }
                participents.delete(0, participents.length());
            }
            
            lastMap.put(event, allParticipents.toString());
            allParticipents.delete(0, allParticipents.length());
        }
        
        return lastMap;
    }
    
    /**
     * Method is to return String of participant list.
     * 
     * @param specialEvents - SpecialEvents object
     * @param toList - String array
     * @return string of participant list
     * @throws AkuraAppException - Detailed exception
     */
    private String toListToString(@ModelAttribute(MODEL_ATT_SPECIAL_EVENT) SpecialEvents specialEvents, String[] toList)
            throws AkuraAppException {
    
        ClassGrade cg = null;
        SportCategory sc = null;
        ClubSociety cs = null;
        
        StringBuilder allParticipents = new StringBuilder();
        
        StringBuilder participents = new StringBuilder();
        boolean isFirst = true;
        
        for (String z : toList) {
            
            int id = Integer.parseInt(z);
            
            if (specialEvents.getParticipantCategory().getParticipantCategoryId() == 1) {
                // class wise
                cg = commonService.findClassGrade(id);
            } else if (specialEvents.getParticipantCategory().getParticipantCategoryId() == 2) {
                // sport wise
                sc = commonService.findSportCategoryById(id);
            } else if (specialEvents.getParticipantCategory().getParticipantCategoryId() == CATEGORY_TYPE_3) {
                // clubSociety wise
                cs = commonService.findClubSocietyById(id);
            }
            
            if (cg != null) {
                
                participents.append(cg.getDescription());
                
            }
            if (cs != null) {
                
                participents.append(cs.getName());
                
            }
            if (sc != null) {
                
                participents.append(sc.getSport().getDescription());
                participents.append("-");
                participents.append(sc.getSportSubCategory().getDescription());
                
            }
            
            if (isFirst) {
                allParticipents.append(participents.toString()); // no comma
                isFirst = false;
            } else {
                allParticipents.append(", "); // comma
                allParticipents.append(participents.toString());
            }
            participents.delete(0, participents.length());
            
        }
        
        return allParticipents.toString();
    }
    
    /**
     * Method is to return Participant Category List reference data.
     * 
     * @return ParticipantCategoryList - Participant Category reference data.
     * @throws AkuraAppException - throw SMSExeption.
     */
    @ModelAttribute(MODEL_ATT_PARTICIPANT_CATEGORY_LIST)
    public List<ParticipantCategory> populateParticipantCategoryList() throws AkuraAppException {
    
        return SortUtil.sortParticipantCategory(commonService.getParticipantCategoryList());
    }
    
    /**
     * Method is to return participant list.
     * 
     * @param specialEvents - SpecialEvents object
     * @param request - HttpServletRequest
     * @param modelMap - ModelMap attribute.
     * @return list of FaithLifeComment
     * @throws AkuraAppException - Detailed exception
     */
    @ResponseBody
    @RequestMapping(value = "/findParticipentList.htm")
    public String populateParticipents(@ModelAttribute(MODEL_ATT_SPECIAL_EVENT) SpecialEvents specialEvents,
            ModelMap modelMap, HttpServletRequest request) throws AkuraAppException {
    
        StringBuilder allParticipents = new StringBuilder();
        // int categoryId = specialEvents.getParticipantCategory().getParticipantCategoryId();
        int categoryId = Integer.parseInt(request.getParameter("selectedValue"));
        if (categoryId == 1) {
            List<ClassGrade> classGradeList = SortUtil.sortClassGradeList(commonService.getClassGradeList());
            
            boolean isFirst = true;
            StringBuilder classes = new StringBuilder();
            
            for (ClassGrade cg : classGradeList) {
                classes.append(cg.getDescription());
                classes.append("_");
                classes.append(cg.getClassGradeId());
                
                if (isFirst) {
                    allParticipents.append(classes.toString()); // no comma
                    isFirst = false;
                } else {
                    allParticipents.append(","); // comma
                    allParticipents.append(classes.toString());
                }
                classes.delete(0, classes.length());
            }
            
        }
        
        if (categoryId == 2) {
            List<SportCategory> sportCategoryList =
                    SortUtil.sortSportCategoriesList(commonService.getSportCategoriesList());
            
            boolean isFirst = true;
            StringBuilder sportCategories = new StringBuilder();
            
            for (SportCategory sc : sportCategoryList) {
                sportCategories.append(sc.getSport().getDescription());
                sportCategories.append("-");
                sportCategories.append(sc.getSportSubCategory().getDescription());
                sportCategories.append("_");
                sportCategories.append(sc.getSportCategoryId());
                
                if (isFirst) {
                    allParticipents.append(sportCategories.toString()); // no comma
                    isFirst = false;
                } else {
                    allParticipents.append(","); // comma
                    allParticipents.append(sportCategories.toString());
                }
                sportCategories.delete(0, sportCategories.length());
            }
        }
        
        if (categoryId == CATEGORY_TYPE_3) {
            
            List<ClubSociety> clubSocietyList = SortUtil.sortClubSocietyList(commonService.getClubsSocietiesList());
            
            boolean isFirst = true;
            StringBuilder clubSocieties = new StringBuilder();
            
            for (ClubSociety cs : clubSocietyList) {
                clubSocieties.append(cs.getName());
                clubSocieties.append("_");
                clubSocieties.append(cs.getClubSocietyId());
                
                if (isFirst) {
                    allParticipents.append(clubSocieties.toString()); // no comma
                    isFirst = false;
                } else {
                    allParticipents.append(","); // comma
                    allParticipents.append(clubSocieties.toString());
                }
                clubSocieties.delete(0, clubSocieties.length());
            }
            
        }
        return allParticipents.toString();
    }
    
    
    
}
