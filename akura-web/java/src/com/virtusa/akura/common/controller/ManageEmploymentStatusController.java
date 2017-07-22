package com.virtusa.akura.common.controller;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.virtusa.akura.api.dto.EmploymentStatus;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.ErrorMsgLoader;
import com.virtusa.akura.common.service.CommonService;
import com.virtusa.akura.common.validator.EmploymentStatusValidator;
import com.virtusa.akura.util.SortUtil;

/**
 * ManageEmploymentStatusController is to handle Add/Edit/Delete operations for Employment Status 
 * in reference module.
 * 
 * @author mchapman
 *
 */
@Controller
public class ManageEmploymentStatusController {

    /** view method Employment Status. */
    private static final String VIEW_GET_MANAGE_EMPLOYMENT_STATUS = "reference/manageEmploymentStatus";

    /** model attribute of EmploymentStatus. */
    private static final String MODEL_ATT_EMPLOYMENT_STATUS = "employmentStatus";
        
    /** string variable for error message duplicate. */
    private static final String DUPLICATE_DESCRIPTION_ERROR = "STQ.UI.DUPLICATE.DESCRIPTION";
    
    /** string variable for DESCRIPTION. */
    private static final String DESCRIPTION = "description";

    /** string variable for error message delete. */
    private static final String ERROR_MSG_EMPLOYMENTSTATUS_DELETE = "REF.UI.EMPLOYMENTSTATUS.DELETE";

    /** view post method Employment Status. */
    private static final String VIEW_POST_MANAGE_EMPLOYMENT_STATUS = "redirect:manageEmploymentStatus.htm";

    /** request mapping value for save or update student EmploymentStatus. */
    private static final String REQ_MAP_VALUE_SAVEORUPDATE = "/manageSaveOrUpdateEmploymentStatus.htm";

    /** model attribute of EmploymentStatus list. */
    private static final String MODEL_ATT_EMPLOYMENT_STATUS_LIST = "employmentStatusList";

    /** request mapping value for delete Employment Status. */
    private static final String REQ_MAP_VALUE_DELETE = "/manageDeleteEmploymentStatus.htm";

    /** attribute for holding message. */
    private static final String MESSAGE = "message";
    
    /** model attribute of selected object. */
	private static final String MODEL_ATT_SELECTED_OBJECT = "selectedObj";
	
	/** attribute for holding error message key. */
	private static final String ERROR_MSG_KEY = "REF.UI.RECORD.EXIST.ERROR";
    /**
     * Represents an instance of EmploymentStatusValidator.
     */
    private EmploymentStatusValidator employmentStatusValidator;

    /** CommonService attribute for holding commonService. */
    private CommonService commonService;

    /**
     * setter method from CommonService.
     *
     * @param commonServiceVal - CommonService
     */

    public void setCommonService(CommonService commonServiceVal) {

        this.commonService = commonServiceVal;
    }

    /**
     * Sets an instance of EmploymentStatusValidator.
     *
     * @param employmentStatusValidatorVal - the relevant instance of EmploymentStatusValidator
     */
    public void setEmploymentStatusValidator(EmploymentStatusValidator employmentStatusValidatorVal) {

        employmentStatusValidator = employmentStatusValidatorVal;
    }

    /**
     * Handle GET requests for Employment Status view.
     * 
     * @param modelMap - {@link ModelMap}
     * @return the name of the view.
     * @throws AkuraAppException - AkuraAppException
     */
	@RequestMapping(method = RequestMethod.GET)
	public String showEmploymentStatus(final ModelMap modelMap) throws AkuraAppException {
		
		EmploymentStatus employmentStatus = new EmploymentStatus();
		modelMap.addAttribute(MODEL_ATT_EMPLOYMENT_STATUS, employmentStatus);
		return VIEW_GET_MANAGE_EMPLOYMENT_STATUS;
		
	}
	
	/**
     * Method is to return Employment Status list.
     * 
     * @return list of Employment Status
     * @throws AkuraAppException - Detailed exception
     */
    @ModelAttribute(MODEL_ATT_EMPLOYMENT_STATUS_LIST)
    public final List<EmploymentStatus> populateEmploymentStatusList() throws AkuraAppException {

        return SortUtil.sortEmploymentStatusList(commonService.findAllEmploymentStatus());
    }

    /**
     * Saves or updates the relevant EmploymentStatus.
     *
     * @param model - a HashMap that contains information of the EmploymentStatus
     * @param employmentStatus - an instance of EmploymentStatus
     * @param result - containing list of errors and employmentStatus instance to which data was bound
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when saving or updating a EmploymentStatus
     *         instance.
     */
    @RequestMapping(value = REQ_MAP_VALUE_SAVEORUPDATE, method = RequestMethod.POST)
    public final String saveOrUpdateEmploymentStatus(
            @ModelAttribute(MODEL_ATT_EMPLOYMENT_STATUS) final EmploymentStatus employmentStatus, BindingResult result,
            final ModelMap model) throws AkuraAppException {

    	EmploymentStatus selectedObj = null;
        employmentStatusValidator.validate(employmentStatus, result);
        
        if (result.hasErrors()) {
        	int id = employmentStatus.getEmploymentStatusId();
			if (id == 0) {
				// to keep add panel when validation error occurred
				EmploymentStatus newEmploymentStatus = new EmploymentStatus();
				newEmploymentStatus.setDescription(employmentStatus.getDescription());
				model.addAttribute(MODEL_ATT_SELECTED_OBJECT, newEmploymentStatus);
				model.addAttribute(MODEL_ATT_EMPLOYMENT_STATUS, employmentStatus);
			} else {
				// to keep edit panel when validation error is occurred
				selectedObj = commonService.findEmploymentStatusById(employmentStatus.getEmploymentStatusId());
				model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
				model.addAttribute(MODEL_ATT_EMPLOYMENT_STATUS, employmentStatus);
			}
            return VIEW_GET_MANAGE_EMPLOYMENT_STATUS;
        } else {
        	
            String description = employmentStatus.getDescription().trim();
            int id = employmentStatus.getEmploymentStatusId();

            if (isExistsEmploymentStatus(description)) {
                
                if (id != 0) {
                	selectedObj = commonService.findEmploymentStatusById(employmentStatus.getEmploymentStatusId());
    				model.addAttribute(MODEL_ATT_SELECTED_OBJECT, selectedObj);
    				model.addAttribute(MODEL_ATT_EMPLOYMENT_STATUS, employmentStatus);
    				String message = new ErrorMsgLoader()
					.getErrorMessage(ERROR_MSG_KEY);
    				model.addAttribute(MESSAGE, message);
                    EmploymentStatus findEmploymentStatus = commonService
                            .findEmploymentStatusById(id);
                    //model.addAttribute(MODEL_ATT_EMPLOYMENT_STATUS, findEmploymentStatus);
                } else {
                    result.rejectValue(DESCRIPTION, DUPLICATE_DESCRIPTION_ERROR);
                    EmploymentStatus newEmploymentStatus = new EmploymentStatus();
    				newEmploymentStatus.setDescription(employmentStatus.getDescription());
    				model.addAttribute(MODEL_ATT_SELECTED_OBJECT, newEmploymentStatus);
    				model.addAttribute(MODEL_ATT_EMPLOYMENT_STATUS, employmentStatus);
                    
                }
                return VIEW_GET_MANAGE_EMPLOYMENT_STATUS;
            } else {
                employmentStatus.setDescription(description);
            	if (id > 0) {
                    commonService.updateEmploymentStatus(employmentStatus);
    			} else {
    				commonService.addEmploymentStatus(employmentStatus);
    			}
            }
			
			return VIEW_POST_MANAGE_EMPLOYMENT_STATUS;
           
        }
    }
    
    /**
     * Deletes the relevant EmploymentStatus object.
     *
     * @param model - a HashMap that contains information of the PrefectType
     * @param employmentStatus - an instance of EmploymentStatus
     * @return - name of the view which is redirected to
     * @throws AkuraAppException - The exception details that occurred when deleting a EmploymentStatus instance.
     */
    @RequestMapping(value = REQ_MAP_VALUE_DELETE)
    public final String deleteEmploymentStatus (final ModelMap model,
    		@ModelAttribute(MODEL_ATT_EMPLOYMENT_STATUS) final EmploymentStatus employmentStatus) 
    		        throws AkuraAppException {
        String message;
        try {
            int id = employmentStatus.getEmploymentStatusId();
            EmploymentStatus findEmploymentStatus = commonService.findEmploymentStatusById(id);
            commonService.deleteEmploymentStatus(findEmploymentStatus);
            return VIEW_POST_MANAGE_EMPLOYMENT_STATUS;
        } catch (AkuraAppException e) {
            if (e.getCause() instanceof DataIntegrityViolationException) {
                message = new ErrorMsgLoader().getErrorMessage(ERROR_MSG_EMPLOYMENTSTATUS_DELETE);
                EmploymentStatus newEmploymentStatus = new EmploymentStatus();
                model.addAttribute(MESSAGE, message);
                model.addAttribute(MODEL_ATT_EMPLOYMENT_STATUS, newEmploymentStatus);
                return VIEW_GET_MANAGE_EMPLOYMENT_STATUS;
            } else {
                throw e;
            }
        }
    }
    
    /**
     * Check whether the Employment Status exists or not.
     * 
     * @param employmentStatusDescription - employmentStatusDescription
     * @return isExists - return true if exists.
     * @throws AkuraAppException - AkuraAppException
     */
    public boolean isExistsEmploymentStatus(String employmentStatusDescription) throws AkuraAppException {
    	
    	boolean isExists = false;
    	List<EmploymentStatus> employmentStatusList = commonService.findAllEmploymentStatus();
    	
    	for (EmploymentStatus employmentStatus : employmentStatusList) {
			if (employmentStatus.getDescription().equalsIgnoreCase(employmentStatusDescription)) {
				isExists = true;
				break;
			}
		}
    	return isExists;
	}
}

	
