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

package com.virtusa.akura.common.service;

import com.virtusa.akura.api.dto.CommonEmailBean;
import com.virtusa.akura.api.exception.AkuraAppException;


/**
 * @author Virtusa Corporation
 *
 */

public interface EmailService {

    /**
     * Sends common  email generated according to content of CommonEmailBean.
     *
     * @param bean <code>CommonEmailBean</code>Contains email content related information.
     * @return boolean check mail sending is successful.
     * @throws AkuraAppException - throw detailed exception
     *
     */
    boolean sendMail(CommonEmailBean bean) throws AkuraAppException;


}
