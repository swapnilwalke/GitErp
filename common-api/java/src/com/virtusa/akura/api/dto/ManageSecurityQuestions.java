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

package com.virtusa.akura.api.dto;

/**
 * @author Virtusa Corporation
 */
public class ManageSecurityQuestions extends BaseDomain {
    
    /**
     * Holds serial version id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Holds UserLogin object.
     */
    private UserLogin userLogin;
    
    /**
     * Holds SecurityQuestions object.
     */
    private SecurityQuestions securityQuestions;
    
    /**
     * Holds UserSecurityQuestions object.
     */
    private UserSecurityQuestions userSecurityQuestions;
    
    /**
     * No arg constructor for ManageSecurityQuestions.
     */
    public ManageSecurityQuestions() {
    
    }
    
    /**
     * Getter method for securityQuestions field.
     * 
     * @return SecurityQuestions
     */
    public SecurityQuestions getSecurityQuestions() {
    
        return securityQuestions;
    }
    
    /**
     * Setter method for securityQuestions field.
     * 
     * @param securityQuestionsValue - SecurityQuestions object to set.
     */
    public void setSecurityQuestions(SecurityQuestions securityQuestionsValue) {
    
        this.securityQuestions = securityQuestionsValue;
    }
    
    /**
     * Getter method for userLogin field.
     * 
     * @return UserLogin object.
     */
    public UserLogin getUserLogin() {
    
        return userLogin;
    }
    
    /**
     * Setter method for userLogin field.
     * 
     * @param userLoginValue - UserLogin object to set.
     */
    public void setUserLogin(UserLogin userLoginValue) {
    
        this.userLogin = userLoginValue;
    }
    
    /**
     * Getter method for userSecurityQuestions field.
     * 
     * @return UserSecurityQuestions object.
     */
    public UserSecurityQuestions getUserSecurityQuestions() {
    
        return userSecurityQuestions;
    }
    
    /**
     * Setter method for userSecurityQuestions field.
     * 
     * @param userSecurityQuestionsValue - UserSecurityQuestions object to set.
     */
    public void setUserSecurityQuestions(UserSecurityQuestions userSecurityQuestionsValue) {
    
        this.userSecurityQuestions = userSecurityQuestionsValue;
    }
    
}
