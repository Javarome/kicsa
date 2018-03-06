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
    <html:link href="Eaction/mployeeEdit?method=create" styleClass="menuTop"><bean:message key="menu.employeeCreate"/></html:link>&nbsp;&nbsp;| &nbsp;
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
<div align="center"><font class="seize">Cette section requiert des droits sp&eacute;cifiques</font> 
</div>
<html:messages id="message"/> <html:errors/> 
<table border="0" cellpadding="2" cellspacing="0" align="center">
  <tr> 
    <td height="19" colspan="2" >&nbsp;</td>
  </tr>
  <form action="j_security_check" method="post">
    <tr> 
      <td > <bean:message key="prompt.login"/> </td>
      <td> 
        <input type="text" name="j_username">
      </td>
    </tr>
    <tr > 
      <td> <bean:message key="prompt.password"/> </td>
      <td> 
        <input type="password" name="j_password">
      </td>
    </tr>
    <tr > 
      <td colspan="2"> 
        <div align="center"> 
          <input type="submit" name="Submit" value="Login">
        </div>
      </td>
    </tr>
  </form>
</table>
</body>
</html:html>
