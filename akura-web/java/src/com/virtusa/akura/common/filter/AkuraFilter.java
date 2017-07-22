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
package com.virtusa.akura.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This an Interceptor (servlet filter like) to add to response header no-cache
 * for pages.
 *
 * @author Virtusa Corporation
 */
public class AkuraFilter implements Filter {

    /** Holds the exprires value.  */
    private static final String EXPRIRES_VALUE = "0";

    /** Holds the exprires key.  */
    private static final String EXPIRES = "Expires";

    /** Holds the Pragma value.  */
    private static final String NO_CACHE = "no-cache";

    /** Holds the Pragma key.  */
    private static final String PRAGMA = "Pragma";

    /** Holds the cache control value.  */
    private static final String NO_CACHE_NO_STORE_MUST_REVALIDATE = "no-cache, no-store, must-revalidate";

    /** Holds the cache control key.  */
    private static final String CACHE_CONTROL = "Cache-Control";

    /**
     * {@inheritDoc}
     */
    public void destroy() {

    }

    /**
     * {@inheritDoc}
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader(CACHE_CONTROL,
            NO_CACHE_NO_STORE_MUST_REVALIDATE); // HTTP 1.1.
        response.setHeader(PRAGMA, NO_CACHE); // HTTP 1.0.
        response.setHeader(EXPIRES, EXPRIRES_VALUE); // Proxies.
        chain.doFilter(request, response);

    }

    /**
     * {@inheritDoc}
     */
    public void init(FilterConfig arg0) throws ServletException {

    }
}
