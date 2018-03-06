package org.orange.kicsa.presentation.control;

import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionServlet;
import org.orange.kicsa.Application;

public class KicsaActionServlet extends ActionServlet {
  private Logger log;
  public static final String LOG4J_FILE_KEY = "log4j-init-file";
  public static final String ROOT_ID = "rootId";

  public KicsaActionServlet() {
  }

  public void init() throws ServletException {
    super.init();
    System.out.println("Intialisation de struts... OK");
    ServletContext servletContext = this.getServletContext();
    String logConfigFileName = servletContext.getInitParameter("log4j-init-file");
    URL logConfigResource = null;

    try {
      logConfigResource = servletContext.getResource(logConfigFileName);
    } catch (MalformedURLException var6) {
      var6.printStackTrace();
    }

    System.out.println("Chargement de la ressource de log... OK");

    try {
      Application.init(logConfigResource);
      this.log = Logger.getLogger(this.getClass());
    } catch (Throwable var5) {
      throw new ServletException("L'application n'a pu s'initialiser en raison de " + var5.getClass().getName() + ": " + var5.getMessage());
    }

    servletContext.setAttribute("rootId", new Integer(Application.getSkillFactory().getRootId()));
  }

  public String getServletInfo() {
    return "Spécialisation Skill de la ActionServlet de Struts";
  }
}
