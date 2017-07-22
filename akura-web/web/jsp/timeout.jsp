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

<%@page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<head>
<link rel="shortcut icon" href="resources/images/favicon.ico" type="image/x-icon" />
</head>

<html>
<title><spring:message code="application.session.timeout.title"/></title>
<body>
<h2><spring:message code="application.session.invalid"/></h2>

<p>
<spring:message code="application.session.timeout.message.partone"/> <a href="<c:url value='/'/>"><spring:message code="application.session.timeout.message.parttwo"/></a>.
</p>
</body>
</html>
