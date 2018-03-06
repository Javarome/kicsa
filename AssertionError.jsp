<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-form.tld" prefix="form" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page isErrorPage="true" %>
<html:html>
<head>
<title>Erreur - KiCsa - Orange France</title>
<link rel="STYLESHEET" type="text/css" href="../OrangeWeb.css">
</head>
<body>
<%@ include file="Header.jsp" %>
<table border="0" ellpadding="0" cellspacing="0" align="center">
  <tbody> 
  <tr> 
    <td valign="top"> </td>
  </tr>
  <tr> 
    <td valign="middle"> 
      <table border="0"  cellspacing="1" cellpadding="0">
        <tbody> 
        <tr> 
          <td > 
            <p><font class="error"><html:errors /></font></p>
          </td>
          <td>  </td>
        </tr>
        </tbody> 
      </table>
    </td>
  </tr>
  <tr> 
    <td> 
      <table border="0" cellspacing="0" cellpadding="0" align="left">
        <tbody> 
        <tr> 
          <td align="center"> </td>
          <td align="center"> <a href="http://mobirec.ftm.francetelecom.fr/annuaire/Recherche/Recherche.asp"><img src="../images/retour.gif" width="79" height="26" border="0"></a></td>
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