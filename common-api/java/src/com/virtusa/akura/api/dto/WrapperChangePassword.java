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
 * WrapperChangePassword is used to bind results related to changePassword.
 * 
 * @author Virtusa Corporation.
 */
public class WrapperChangePassword extends BaseDomain {
    
    /**
     * Holds serial version id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Property oldPassword type String.
     */
    private String oldPassword;
    
    /**
     * Property newPassword type String.
     */
    private String newPassword;
    
    /**
     * Property confirmPassword type String.
     */
    private String confirmPassword;
    
    /**
     * holds the username of the user.
     */
    private String username;
    
    /**
     * holds the email address for the user.
     */
    private String email;
    
    /**
     * Constructor for WrapperChangePassword.
     */
    public WrapperChangePassword() {

    }
    
    /**
     * Get the old password.
     * 
     * @return the old password.
     */
    public String getOldPassword() {

        return oldPassword;
    }
    
    /**
     * Set the old password.
     * 
     * @param oldPasswordVal the old password to set.
     */
    public void setOldPassword(String oldPasswordVal) {

        this.oldPassword = oldPasswordVal;
    }
    
    /**
     * Get the new password.
     * 
     * @return the new password.
     */
    public String getNewPassword() {

        return newPassword;
    }
    
    /**
     * Set the new password.
     * 
     * @param newPasswordVal the new password to set.
     */
    public void setNewPassword(String newPasswordVal) {

        this.newPassword = newPasswordVal;
    }
    
    /**
     * Get the confirm password.
     * 
     * @return the confirmPassword.
     */
    public String getConfirmPassword() {

        return confirmPassword;
    }
    
    /**
     * Set the confirm password.
     * 
     * @param confirmPasswordVal the confirm password to set.
     */
    public void setConfirmPassword(String confirmPasswordVal) {

        this.confirmPassword = confirmPasswordVal;
    }
    
    /**
     * return the username of the user.
     * 
     * @return the username.
     */
    public String getUsername() {

        return username;
    }
    
    /**
     * set the username of the user.
     * 
     * @param strUsername the username to set
     */
    public void setUsername(String strUsername) {

        this.username = strUsername;
    }
    
    /**
     * return email address of the user.
     * 
     * @return the email
     */
    public String getEmail() {

        return email;
    }
    
    /**
     * set the email address of the user.
     * 
     * @param emailVal the email to set
     */
    public void setEmail(String emailVal) {

        this.email = emailVal;
    }
    
    /**
     * Returns the details for the current object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {

        return "WrapperChangePassword [oldPassword=" + oldPassword + ", newPassword=" + newPassword
                + ", confirmPassword=" + confirmPassword + ", username=" + username + ", email=" + email + "]";
    }
    
}
