/*
 * © 2011 Virtusa(Pvt)Ltd. All rights reserved. CONFIDENTIAL AND

 * PROPRIETARY INFORMATION The information contained herein (the

 * 'Proprietary Information') is highly confidential and proprietary to and

 * constitutes trade secrets of Virtusa(Pvt)Ltd. The Proprietary Information

 * for Virtusa(Pvt)Ltd internal use only and shall not be published,

 * communicated, disclosed or divulged to any person, firm, corporation or

 * other legal entity, directly or indirectly, without the prior written

 * consent of Virtusa(Pvt)Ltd Information Management.
 */

package com.virtusa.akura.reporting.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.virtusa.akura.api.dto.CRExtraCurricularActivitiesTemplate;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.reporting.service.CommonReportingService;
import com.virtusa.akura.reporting.validator.CRStudentExtraCurricularValidator;
import com.virtusa.akura.util.PropertyReader;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * @author Virtusa Corporation
 */

@Controller
@SessionAttributes("cRExtraCurricularActivitiesTemplate")
public class CRStudentExtraCurricularController {

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(CRStudentExtraCurricularController.class);

    /** CommonReportingService Object to retrieve Common Reporting Service related information. */
    @Autowired
    private CommonReportingService commonReportingService;

    /** VIEW_PAGE for holding return jsp page. */
    private static final String VIEW_PAGE = "reporting/studentExtraCurricularActivities";

    /** CRStudentExtraCurricularValidator Object to validate. */

    private CRStudentExtraCurricularValidator cRStudentExtraCurricularValidator;

    /**
     * Set CRStudentExtraCurricularValidator object.
     *
     * @param cRStudentExtraCurricularValidatorRef set CRStudentExtraCurricularValidator object.
     */
    public void setcRStudentExtraCurricularValidator(
            CRStudentExtraCurricularValidator cRStudentExtraCurricularValidatorRef) {

        this.cRStudentExtraCurricularValidator = cRStudentExtraCurricularValidatorRef;
    }

    /**
     * Sets the CommonReportingService object.
     *
     * @param setCommonReportingService {@link CommonReportingService}
     */
    public void setCommonReportingService(final CommonReportingService setCommonReportingService) {

        this.commonReportingService = setCommonReportingService;
    }

    /**
     * Used to collect user the input data to generate the report.
     *
     * @param modelMap of type ModelMap
     * @return java.lang.String
     */

    @RequestMapping(method = RequestMethod.GET)
    public String showReportForm(ModelMap modelMap) {

        LOG.info("Calling StudentExtraCurricularAcitivities.jsp to collect input data");
        CRExtraCurricularActivitiesTemplate cRExtraCurricularActivitiesTemplate =
                new CRExtraCurricularActivitiesTemplate();
        modelMap.addAttribute("cRExtraCurricularActivitiesTemplate", cRExtraCurricularActivitiesTemplate);

        return VIEW_PAGE;
    }

    /**
     * Generate Student Disciplinary Action Report .
     *
     * @param curricularActivitiesTemplate collect user input data to generate the report.
     * @param request of type HttpServletRequest
     * @param response of type HttpServletResponse
     * @param errors of type BindingResult
     * @return java.lang.String
     * @throws AkuraAppException AkuraAppException
     */

    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("cRExtraCurricularActivitiesTemplate")
            CRExtraCurricularActivitiesTemplate curricularActivitiesTemplate,
            BindingResult errors) throws AkuraAppException {

        LOG.info("Start processing user data for extraCurricularActivitiesList Report");

        cRStudentExtraCurricularValidator.validate(curricularActivitiesTemplate, errors);

        if (errors.hasErrors()) {

            return VIEW_PAGE;
        }

        Map<String, Object> params = new HashMap<String, Object>();

        // title area
        params.put("StudentExtraCurricularTitleText", "Student Extra Curricular Activities Report ");
        params.put("admissionNumberLableText", PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                "StuExCurricular_admissionNumberLableText"));
        params.put("fullNameLableText", PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                "StuExCurricular_fullNameLableText"));
        params.put("logoPath", ReportUtil.getReportLogo());
        // columns
        params.put("yearLableText", PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                "StuExCurricular_yearLableText"));
        params.put("clubNameLableText", PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                "StuExCurricular_clubNameLableText"));
        params.put("positionLableText", PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE,
                "StuExCurricular_positionLableText"));

        JRBeanCollectionDataSource extraCurricularActivitiesList =
                commonReportingService.getStudentExtraCurricularActivities(curricularActivitiesTemplate);

        ReportUtil.displayReportInPdfForm(response, extraCurricularActivitiesList, params, "CRStudentExtraCurricular");

        LOG.info("There are:" + extraCurricularActivitiesList.getRecordCount());

        return null;

    }
}
