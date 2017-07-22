package com.virtusa.akura.api.dto;

/**
 * @author Virtusa Corporation
 */
public class StudentScholarshipTemplate implements java.io.Serializable{
    
    /**
     * Serial version ID.
     */
    private static final long serialVersionUID = 1L;
    
    /** key to hold string scholarship. */
    private String scholarship;
    
    /** key to hold string sponsorship. */
    private String sponsorship;
    
    /** key to hold int totalCount. */
    private int totalCount;
    
    /** key to hold string admissionNo. */
    private String admissionNo;
    
    /** key to hold string nameWithInitials. */
    private String nameWithInitials;
    
    /** key to hold string description. */
    private String description;
    
    

    /**
     * @return the totalCount
     */
    public int getTotalCount() {
    
        return totalCount;
    }
    
    /**
     * @param totalCountVal the totalCount to set
     */
    public void setTotalCount(int totalCountVal) {
    
        this.totalCount = totalCountVal;
    }  
    
    /**
     * @return the sponsorship
     */
    public String getSponsorship() {
    
        return sponsorship;
    }
    
    /**
     * @param sponsorshipVal the sponsorship to set
     */
    public void setSponsorship(String sponsorshipVal) {
    
        this.sponsorship = sponsorshipVal;
    }
    
    /**
     * @return the scholarship
     */
    public String getScholarship() {
    
        return scholarship;
    }
    
    /**
     * @param scholarshipVal the scholarship to set
     */
    public void setScholarship(String scholarshipVal) {
    
        this.scholarship = scholarshipVal;
    }
    
    /**
     * @return the admissionNo
     */
    public String getAdmissionNo() {
    
        return admissionNo;
    }
    
    /**
     * @param admissionNoVal the admissionNo to set
     */
    public void setAdmissionNo(String admissionNoVal) {
    
        this.admissionNo = admissionNoVal;
    }
    /**
     * @return the nameWithInitials
     */
    public String getNameWithInitials() {
    
        return nameWithInitials;
    }
    
    /**
     * @param nameWithInitialsVal the nameWithInitials to set
     */
    public void setNameWithInitials(String nameWithInitialsVal) {
    
        this.nameWithInitials = nameWithInitialsVal;
    }
    /**
     * @return the description
     */
    public String getDescription() {
    
        return description;
    }
    
    /**
     * @param descriptionVal the description to set
     */
    public void setDescription(String descriptionVal) {
    
        this.description = descriptionVal;
    }
    
    
}
