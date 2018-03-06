<%@ page language="java" isErrorPage="true" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-form.tld" prefix="form" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html:html>
<head>
<title>Erreur - KiCsa - Orange France</title>
<link rel="STYLESHEET" type="text/css" href="../dsi.css">
</head>
<body>
<%@ include file="Header.jsp" %>
<table border="0" ellpadding="0" cellspacing="0">
  <tbody> 
  <tr> 
    <td valign="top" width="80%"> </td>
  </tr>
  <tr> 
    <td valign="middle"> 
      <table border="0"  cellspacing="1" cellpadding="0">
        <tbody> 
        <tr> 
          <td width="280"> 
            <p>    <span class="titre_noir_2"><%= exception.getMessage()%></span></p>
          </td>
          <td>  </td>
        </tr>
        </tbody> 
      </table>
    </td>
  </tr>
  <tr> 
    <td> 
      <table border="0" width="100%" cellspacing="0" cellpadding="0" align="left">
        <tbody> 
        <tr> 
          <td width="33%" align="center"> </td>
          <td width="33%" align="center"> <a href="http://mobirec.ftm.francetelecom.fr/annuaire/Recherche/Recherche.asp"><img src="../images/retour.gif" width="79" height="26" border="0"></a></td>
          <td width="34%" align="center"> </td>
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