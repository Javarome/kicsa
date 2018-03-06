<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-form.tld" prefix="form" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ page errorPage="SystemError.jsp" %>
<html:html>

<head>
<title> <bean:message key="menu.employeeSearch"/> - KiCsa - Orange France</title>
<link rel="STYLESHEET" type="text/css" href="../OrangeWeb.css">
<script language="Javascript1.1" src="../staticJavascript.jsp"></script>
<html:javascript formName="employeeSearchForm" dynamicJavascript="true" staticJavascript="false"/>
</head>
<body link="#FF6600" vlink="#FF6600" alink="#000000">
<%@ include file="Header.jsp" %>
<html:errors/>
<table border="0" align="center" cellpadding="2" cellspacing="0">
  <html:form name="employeeSearchForm" type="org.orange.kicsa.presentation.view.EmployeeSearchForm" action="/EmployeeSearch" onsubmit="return validateEmployeeSearchForm(this);"> 
	<nested:nest property="employee">
  <tr> 
    <td valign="top" > 
      <p><font class="seize"> <bean:message key="menu.employeeSearch"/> 
        </font></p>
    </td>
    <td valign="top">&nbsp;</td>
    <td align="right" valign="top" bgcolor="#FFF8F0"> <span class="normal"><bean:message key="prompt.shortName"/></span> 
      <font class="nomorange"> 
			<nested:select property="id">
				<html:options collection="employeeList" property="key" labelProperty="value"/> 
      </nested:select> </font> </td>
  </tr>
  <tr align="center">
    <td colspan="3" height="5">&nbsp;</td>
  </tr>
  <tr align="center"> 
    <td colspan="3"> 
      <input type="submit" name="searchStart" value="<bean:message key="button.search"/>"> </td>
  </tr>
	</nested:nest>
  </html:form> 
</table>
<%@ include file="Footer.jsp" %>
</body>
</html:html>
