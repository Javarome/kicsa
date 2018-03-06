<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ page errorPage="SystemError.jsp" %>
<html:html>
  
<head>
<title>Login - KiCsa - Orange France</title>
<link rel="STYLESHEET" type="text/css" href="OrangeWeb.css">
<script language="Javascript1.1" src="../staticJavascript.jsp"></script>
<html:javascript formName="skillForm" dynamicJavascript="true" staticJavascript="false"/>
</head>

<body>
<%@ include file="Header.jsp" %>
<div align="center"><font class="seize">Cette section requiert des droits sp&eacute;cifiques</font> 
</div>
<table border="0" cellpadding="2" cellspacing="0" align="center">
  <tr> 
    <td height="19" colspan="2" >&nbsp;</td>
  </tr>
<html:messages id="message"/>
<html:errors/> 
<html:form name="loginForm" type="org.orange.kicsa.presentation.view.LoginForm" action="/Login" onsubmit="return validateLoginForm(this);"> 
    <tr> 
      <td><bean:message key="prompt.login"/> </td>
      <td> 
        <nested:text property="username"/>
      </td>
    </tr>
    <tr > 
      <td> <bean:message key="prompt.password"/> </td>
      <td> 
        <nested:password property="password"/>
      </td>
    </tr>
    <tr > 
      <td colspan="2"> 
        <div align="center"> 
          <input type="submit" name="Submit" value="Login">
        </div>
      </td>
    </tr>
  </html:form>
</table>
</body>
</html:html>
