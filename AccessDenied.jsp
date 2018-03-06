<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-form.tld" prefix="form" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page isErrorPage="true" %>
<%@ page import="org.apache.log4j.*" %>
<html:html>
<head>
<title>Accès refusé - KiCsa - Orange France</title>
<link rel="STYLESHEET" type="text/css" href="../OrangeWeb.css">
</head>
<body>
<%@ include file="Header.jsp" %>
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
            <p><font class="seize">Vous n'êtes pas autorisé à accéder à cette page</font></p>
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
          <td align="center"> <a href="javascript:history.go(-1);"><img src="../images/retour.gif" width="79" height="26" border="0"></a></td>
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