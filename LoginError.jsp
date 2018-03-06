<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-form.tld" prefix="form" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page isErrorPage="true" %>
<%@ page import="org.apache.log4j.*" %>
<html:html>
<head>
<title>Login error - KiCsa - Orange France</title>
<link rel="STYLESHEET" type="text/css" href="OrangeWeb.css">
</head>
<body>
<table width="100%" border="0" cellspacing="0">
  <tbody> 
  <tr class="noir">
    <td colspan="8"><br>&nbsp;&nbsp;&nbsp;<html:link href="Home"><font class="Helvetica28ptLightBlanc">KiCsa</font></html:link><br><br></td>
  </tr>
  <tr class="orange"><td>
    <font class="douzeBlanc">&nbsp;&nbsp;<html:link href="action/SkillTree" styleClass="menuTop"><bean:message key="menu.skillTree"/></html:link>&nbsp;&nbsp;| &nbsp;
    <html:link href="action/SkillEdit?method=create" styleClass="menuTop"><bean:message key="menu.skillCreate"/></html:link>&nbsp;&nbsp;| &nbsp;
    <html:link href="action/EmployeeEdit?method=create" styleClass="menuTop"><bean:message key="menu.employeeCreate"/></html:link>&nbsp;&nbsp;| &nbsp;
    <html:link href="action/EmployeeSearch" styleClass="menuTop"><bean:message key="menu.employeeDetail"/></html:link>&nbsp;&nbsp;| &nbsp;
    <html:link href="action/Admin" styleClass="menuTop"><bean:message key="menu.admin"/></html:link>&nbsp;&nbsp;| &nbsp;
    <%-- a href="Logout" styleClass="menuTop">Logout</a>&nbsp;&nbsp;| &nbsp; --%>
    <%-- html:link href="SkillSearch" styleClass="menuTop"><bean:message key="menu.skillSearch"/></html:link>&nbsp;&nbsp;| &nbsp;  --%> 
		<html:link href="mailto:jerome.beau@orangefrance.com" styleClass="menuTop"><bean:message key="menu.help"/></html:link></font></td>
  </tr>
  <tr> 
    <td bgcolor="#FFFFFF" colspan="8 height="10" >&nbsp;</td>
  </tr>
</table>
<table border="0" ellpadding="0" cellspacing="0" align="center">
  <tbody> 
  <tr> 
    <td valign="top" > </td>
  </tr>
  <tr> 
    <td valign="middle"> 
      <table border="0"  cellspacing="1" cellpadding="0">
        <tbody> 
        <tr> 
          <td > 
            <p><font class="seize">Login ou mot de passe incorrect</font></p>
          </td>
          <td>  </td>
        </tr>
        </tbody> 
      </table>
    </td>
  </tr>
  <tr> 
    <td align="center"> 
      <table border="0" cellspacing="0" cellpadding="0">
        <tbody> 
        <tr> 
          <td align="center"> </td>
          <td align="center"> <a href="javascript:history.go(-1);"><img src="images/retour.gif" width="79" height="26" border="0"></a></td>
          <td align="center"> </td>
        </tr>
        </tbody> 
      </table>
    </td>
  </tr>
  <tr> 
    <td>&nbsp; </td>
  </tr>
  </tbody> 
</table>
</body>
</html:html>