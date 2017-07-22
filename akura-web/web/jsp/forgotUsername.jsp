<%--
    < �KURA, This application manages the daily activities of a Teacher and a Student of a School>
    
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

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<%@ taglib uri="/WEB-INF/tags/" prefix="el"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE HTML>
<html>
<head>
<title><spring:message code="APPLICATION.NAME"/> <spring:message code="application.forgotUsername.pagetitle"/></title>
<link href="resources/css/css_reset.css" rel="stylesheet"
	type="text/css">
<link href="resources/css/main_style.css" rel="stylesheet"
	type="text/css">
<link href="resources/css/theme.css" rel="stylesheet" type="text/css">
<link rel="shortcut icon" href="resources/images/favicon.ico" type="image/x-icon" />
<script language="javascript" src="resources/js/main.js"></script>
<script type="text/javascript">
	function reSet() {

		document.passwordRecover.email.value = '';
		document.getElementById("enterValidUserName").style.display = "none";
		document.getElementById("passwordChange").style.display = "none";
	}

	function submitEmail() {

		document.passwordRecover.action = "submitEmail.htm";
		document.passwordRecover.submit();
	}

	function back() {
		document.passwordRecovery.action = "login";
		document.passwordRecover.submit();
	}

	function userMessage(message, messageInvalidUser) {

		if (message == null) {
			document.getElementById("passwordChange").style.display = "none";
		} else {
			document.getElementById("passwordChange").style.display = "block";
		}

		 if (messageInvalidUser != null) {
			document.getElementById("enterValidUserName").style.display = "block";
		} else {
			document.getElementById("enterValidUserName").style.display = "none";
		} 
	}
</script>

</head>
<body onload="userMessage('${message}','${messageInvalidUser}');">
	<div id="topbar">
		<div id="topbar_wraper">
			<div class="date_stamp">${el:getDate()} | <a style="color: white;" href="login.htm"><spring:message code="application.login.url"/></a></div>
			<div class="clearfix"></div>
		</div>
	</div>
	<div id="page_container">
		<div class="clearfix"></div>
		<div id="content_main"></div>

		<form:form method="POST" action="" commandName="UserLogin"
			name="passwordRecover">

			<div id="login_pane" class="section_full_search">
				<div class="float_right" style="margin: 15px 0 5px 0;">
					<img src="resources/images/virtusa-logo.jpg">
				</div>
				<div class="clearfix"></div>
				<div class="box_border">
				<div class="help_link"><a href="#" onClick="PopupCenterScroll('<c:url value="resources/html/akura-help/topics/admin/forgotUsernameHelp.html"/>','helpWindow',780,550)"><img src="resources/images/ico_help.png" width="20" height="20" align="absmiddle"><spring:message code="application.help"/></a></div>
					<div class="Login_leftblock">
						<img src="resources/images/logo_large.jpg">
						<div class="school_name">
							<span><spring:message code="SCHOOL.NAME"/></span><span><spring:message code="SCHOOL.TRACKER"/></span>
						</div>
					</div>
					<div class="Login_rightblock">

						<div class="clearfix"></div>
						<div class="box_border"
							style="margin: 23px 10px 0 0; background-image: none;">
							<div class="row">
								<div class="frmlabel" align="left">
									<span class="required_sign">*</span><label><spring:message code="application.forgotUsername.username"/></label>
								</div>
								<div class="frmvalue">
									<form:input path="email" />
								</div>
								
									</div>
							<div class="row">
								<div class="frmlabel">&nbsp;</div>
								<div class="frmvalue">
									<div class="buttion_bar_type3">

										<input type="button" onclick="submitEmail();"
											value='<spring:message code="REF.UI.SUBMIT"/>' class="button"> <input type="button"
											name="reset" value='<spring:message code="REF.UI.RESET"/>' class="button" onclick="reSet();">
									</div>
								</div>			
							</div>


							<div class="row" name="passwordChange" id="passwordChange">
								<div class="frmvalue">
									<span class="success_sign"><c:if
											test="${message != null}">${message}
									</c:if>                                    
											
									</span>
								</div>
							</div>

							<div class="row" name="mailErrorMessage" id="mailErrorMessage">
								<div class="frmvalue">
									<span class="required_sign"> <c:if
											test="${mailErrors != null}">${mailErrors}
									</c:if> </span>
								
							
							<label class="required_sign"> <spring:bind path="UserLogin.*">
							<c:forEach items="${status.errorMessages}" var="error">
							<c:out value="${error}" />
							<br>
							</c:forEach>
							</spring:bind></label>
							</div>
							</div>

							<div class="clearfix"></div>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</form:form>
	</div>
	<h:footer />
</body>
</html>
