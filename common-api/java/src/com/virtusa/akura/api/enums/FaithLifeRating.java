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

package com.virtusa.akura.api.enums;

/**
 * This represents the enum file which defines faith life rating.
 * 
 * @author Virtusa Corporation
 */
public enum FaithLifeRating {
    
    /** Holds A = 100. */
    A(100),
    /** Holds B = 70. */
    B(70),
    /** Holds C = 60. */
    C(60),
    /** Holds D = 50. */
    D(50),
    /** Holds S = 40. */
    S(40),
    /** Holds F = 20. */
    F(20);
    
    /** Holds grading value. */
    private int rating;
    
    /**
     * Enum constructor with parameter rating value.
     * 
     * @param ratingValue - type int.
     */
    FaithLifeRating(int ratingValue) {
    
        this.rating = ratingValue;
    }
    
    /**
     * Get the rating.
     * 
     * @return rating.
     */
    public int getRating() {
    
        return rating;
    }
    
}
