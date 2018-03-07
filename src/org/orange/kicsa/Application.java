package org.orange.kicsa;

import com.sun.jdmk.TraceManager;
import com.sun.jdmk.comm.HtmlAdaptorServer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.NoSuchElementException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.orange.kicsa.business.skill.Skill;
import org.orange.kicsa.business.skill.SkillFactory;
import org.orange.kicsa.integration.AbstractDAOFactory;
import org.orange.kicsa.service.skill.SkillService;
import org.orange.kicsa.service.skill.SkillServicePool;
import org.orange.util.Preferences;

public class Application {
  public static final String SKILL_FACTORY_CLASS_NAME = "skillFactory";
  public static final String SKILL_FACTORY_CLASS_NAME_DEFAULT;
  private static SkillFactory skillFactory;
  public static final String DAO_FACTORY_CLASS_NAME = "daoFactory";
  public static final String DAO_FACTORY_CLASS_NAME_DEFAULT;
  private static AbstractDAOFactory daoFactory;
  private static MBeanServer mBeanServer;
  private static Logger log;

  public Application() {
  }

  public static void init(URL someLogResource) throws Throwable {
    initLog(someLogResource);
    initManagement();
    initDAOFactory();
    initSkillFactory();
    initDatabase();
    log.info("Application initialisée");
  }

  public static void initSkillFactory() throws Throwable {
    Preferences preferences = Preferences.systemNodeForPackage(class$org$orange$kicsa$Application == null ? (class$org$orange$kicsa$Application = class$("org.orange.kicsa.Application")) : class$org$orange$kicsa$Application);
    String skillFactoryClassName = preferences.get("skillFactory", SKILL_FACTORY_CLASS_NAME_DEFAULT);
    if (log.isDebugEnabled()) {
      log.debug("Chargement de la classe " + skillFactoryClassName);
    }

    Class skillFactoryClass = Class.forName(skillFactoryClassName);
    skillFactory = (SkillFactory)skillFactoryClass.newInstance();
    log.info("skillFactory=" + skillFactory);
  }

  public static SkillFactory getSkillFactory() {
    return skillFactory;
  }

  private static void initDatabase() throws Throwable {
    SkillServicePool skillServicePool = null;
    SkillService skillService = null;

    try {
      skillServicePool = SkillServicePool.getInstance();
      skillService = (SkillService)skillServicePool.borrowObject();
      Skill databaseRootSkill = null;

      try {
        databaseRootSkill = skillService.findByPrimaryKey(getSkillFactory().getRootKey());
        log.info("Compétence racine trouvée dans la base");
      } catch (SkillNotFoundException var10) {
        log.warn("Compétence racine non trouvée dans la base -> création...");
        databaseRootSkill = skillService.createRoot(getSkillFactory().getRoot());
      }

      log.info("Compétence racine dans la base = " + databaseRootSkill);
    } catch (NoSuchElementException var11) {
      log.error(var11 + ": Une cause possible est une fabrique nulle pour le " + skillServicePool);
      throw var11;
    } catch (Exception var12) {
      log.error("Impossible d'initialiser l'accès aux données en raison de " + var12.getClass().getName() + ": " + var12.getMessage());
      var12.printStackTrace();
      throw new RuntimeException("Impossible d'initialiser l'accès aux données en raison de" + var12.getClass().getName() + ": " + var12.getMessage());
    } finally {
      skillServicePool.returnObject(skillService);
    }

  }

  public static void initDAOFactory() throws Throwable {
    Preferences preferences = Preferences.systemNodeForPackage(class$org$orange$kicsa$Application == null ? (class$org$orange$kicsa$Application = class$("org.orange.kicsa.Application")) : class$org$orange$kicsa$Application);
    String daoFactoryClassName = preferences.get("daoFactory", DAO_FACTORY_CLASS_NAME_DEFAULT);
    Class daoFactoryClass = Class.forName(daoFactoryClassName);
    Method singletonMethod = daoFactoryClass.getMethod("getInstance", (Class[])null);

    try {
      daoFactory = (AbstractDAOFactory)singletonMethod.invoke((Object)null, (Object[])null);
      log.info("daoFactory=" + daoFactory);
    } catch (InvocationTargetException var5) {
      throw var5.getTargetException();
    }
  }

  public static AbstractDAOFactory getDAOFactory() {
    return daoFactory;
  }

  private static void initLog(URL logResource) {
    if (logResource != null) {
      PropertyConfigurator.configure(logResource);
    }

    log = Logger.getLogger(class$org$orange$kicsa$Application == null ? (class$org$orange$kicsa$Application = class$("org.orange.kicsa.Application")) : class$org$orange$kicsa$Application);
  }

  private static void initManagement() {
    Logger log = Logger.getLogger(class$org$orange$kicsa$Application == null ? (class$org$orange$kicsa$Application = class$("org.orange.kicsa.Application")) : class$org$orange$kicsa$Application);

    try {
      System.setProperty("DEBUG_LEVEL", "true");
      TraceManager.parseTraceProperties();
      mBeanServer = MBeanServerFactory.createMBeanServer();
      log.info("Création d'un nouvel adaptateur JMX HTML");
      HtmlAdaptorServer jmxHtmlAdaptor = new HtmlAdaptorServer();
      ObjectName jmxHtmlAdaptorName = new ObjectName("Adaptor:name=html,port=8082");
      log.info("Enregistrement JMX de " + jmxHtmlAdaptorName);
      mBeanServer.registerMBean(jmxHtmlAdaptor, jmxHtmlAdaptorName);
      jmxHtmlAdaptor.start();
      log.info("L'adaptateur JMX HTML est " + jmxHtmlAdaptor.getStateString() + " sur " + jmxHtmlAdaptor.getHost() + ":" + jmxHtmlAdaptor.getPort());
    } catch (Throwable var3) {
      log.error("Impossible de créer l'adaptateur JMX HTML en raison d'une " + var3.getClass().getName() + ": " + var3.getMessage());
      var3.printStackTrace();
    }

  }

  public static MBeanServer getMBeanServer() {
    return mBeanServer;
  }

  static {
    SKILL_FACTORY_CLASS_NAME_DEFAULT = (class$org$orange$kicsa$business$skill$SkillFactoryImpl == null ? (class$org$orange$kicsa$business$skill$SkillFactoryImpl = class$("org.orange.kicsa.business.skill.SkillFactoryImpl")) : class$org$orange$kicsa$business$skill$SkillFactoryImpl).getName();
    DAO_FACTORY_CLASS_NAME_DEFAULT = (class$org$orange$kicsa$integration$JDBCDAOFactory == null ? (class$org$orange$kicsa$integration$JDBCDAOFactory = class$("org.orange.kicsa.integration.JDBCDAOFactory")) : class$org$orange$kicsa$integration$JDBCDAOFactory).getName();
  }
}
