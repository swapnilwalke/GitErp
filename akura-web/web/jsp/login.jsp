<%--
    < ÀKURA, This application manages the daily activities of a Teacher and a Student of a School>

    Copyright (C) 2012 Virtusa Corporation.
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
 --%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>
<%@ taglib uri="/WEB-INF/tags/" prefix="el" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE HTML>
<html>
<head>
<title><spring:message code="APPLICATION.NAME"/></title>
<link href="resources/css/css_reset.css" rel="stylesheet" type="text/css">
<link href="resources/css/main_style.css" rel="stylesheet" type="text/css">
<link href="resources/css/theme.css" rel="stylesheet" type="text/css">
<link rel="shortcut icon" href="resources/images/favicon.ico" type="image/x-icon" />
</head>
<body>
<div id="topbar">
  <div id="topbar_wraper">
    <div class="date_stamp">${el:getDate()}</div>
    <div class="clearfix"></div>
  </div>
</div>
<div id="page_container">
  <div class="clearfix"></div>
  <div id="content_main">
   <form method="post" action="j_spring_security_check">

      <div id="login_pane" class="section_full_search">
      	<div style=" float:left; "><img src="resources/images/akura-logo.png" width="100" height="41">  <span style="font-size:14px; color:#356BA7;"><spring:message code="APPLICATION.VERSION"/></span></div>
        <div class="float_right" style="margin:15px 0 5px 0; "><img src="resources/images/virtusa-logo.jpg"></div>
        <div class="clearfix"></div>
        <div class="box_border">
          <div class="Login_leftblock"><img src="resources/images/logo_large.jpg">
            <div class="school_name"><spring:message code="SCHOOL.NAME"/><span><spring:message code="SCHOOL.TRACKER"/></span></div>
          </div>
          <div class="Login_rightblock">
            <div class="clearfix"></div>
            <div class="box_border" style="margin:23px 10px 0 0; background-image:none; ">
            <label class="required_sign"><c:if test="${loginError != null}">
            	${loginError}
            </c:if></label>
              <div class="row">
                <div class="frmlabel">
                   <label for="j_username"><spring:message code="application.login"/></label>
                </div>
                <div class="frmvalue">
                   <input type="text" name="j_username"/>
                </div>
              </div>
              <div class="row">
                <div class="frmlabel">
                  <label for="j_password"><spring:message code="application.password"/></label>
                </div>
                <div class="frmvalue">
                   <input type="password" name="j_password"/>
                </div>
              </div>
              <div class="clearfix"></div>
              <div class="row">
                <div class="frmlabel"> &nbsp; </div>
                <div class="frmvalue">
                  <div class="buttion_bar_type3" >
                   <input type="submit" value='<spring:message code="REF.UI.LOGIN"/>' class="button"/>

</div>
                </div>
              </div>
              <div class="float_left"><a href="forgotPassword.htm"><spring:message code="application.forgotPassword"/></a></div>
              <div class="clearfix"></div>
              <div class="float_left"><a href="forgotUsername.htm"><spring:message code="application.forgotUsername"/></a></div>
              <div class="clearfix"></div>
            </div>
          </div>
          <div class="clearfix"></div>
        </div>
      </div>
    </form>
  </div>
</div>
<h:footer/>
</body>
</html>
