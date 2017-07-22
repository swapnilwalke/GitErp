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
    <div class="date_stamp">${el:getDate()} &nbsp;&nbsp;<spring:message code="HEADER.WELCOME.WELCOME"/> ${userLogin.firstName} ${userLogin.lastName}</div>
    <div class="global_links"><a href="adminWelcome.htm"><spring:message code="HEADER.WELCOME.HOME"/></a> | <a href='<c:url value="/j_spring_security_logout"/>'><spring:message code="HEADER.WELCOME.LOGOUT"/></a></div>
    <div class="clearfix"></div>
  </div>
</div>
<div id="page_container">
  <div class="clearfix"></div>
  <div id="content_main">
      <div id="login_pane" class="section_full_search">
        <div class="float_right" style="margin:15px 0 5px 0; "><img src="resources/images/virtusa-logo.jpg"></div>
        <div class="clearfix"></div>
        <div class="box_border">
          <div class="Login_leftblock"><img src="resources/images/logo_large.jpg">
            <div class="school_name"><spring:message code="SCHOOL.NAME"/><span><spring:message code="SCHOOL.TRACKER"/></span></div>
          </div>
          <div align="left">
            <h1>
		     	<c:if test="${pageNotFound != null}">
		     		${pageNotFound}
		     	</c:if>
		     	<c:if test="${accessDenied != null}">
		     		${accessDenied}
		     	</c:if>
		     	<c:if test="${methodNotAllowed != null}">
		     		${methodNotAllowed}
		     	</c:if>
		     	<c:if test="${smsAppException != null}">
		     		${smsAppException}
		     	</c:if>
		     	<c:if test="${dbConnectionError != null}">
		     		${dbConnectionError}
		     	</c:if>
		     </h1>
		     <a href="adminWelcome.htm"><spring:message code="application.home"/></a>
          </div>
          <div class="clearfix"></div>
        </div>
      </div>
  </div>
</div>
<h:footer/>
</body>
</html>
