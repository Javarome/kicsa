package org.orange.kicsa.presentation.view;

import java.awt.BorderLayout;
import javax.swing.JApplet;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.xml.rpc.Stub;
import org.orange.kicsa.business.skill.SkillTree;
import org.orange.kicsa.service.skill.SkillServiceRemote;

public class TreeApplet extends JApplet {
  SkillServiceRemote skillService;
  SkillTree skillTree;

  public TreeApplet() {
  }

  public void init() {
    try {
      Stub stub = createProxy();
      String[] args = null;
      stub._setProperty("javax.xml.rpc.service.endpoint.address", args);
      SkillServiceRemote skillService = (SkillServiceRemote)stub;
      this.initGUI();
    } catch (Throwable var4) {
      var4.printStackTrace();
    }

  }

  private static Stub createProxy() {
    throw new RuntimeException("Non implémenté");
  }

  public void start() {
  }

  public void stop() {
  }

  public void destroy() {
  }

  public String getAppletInfo() {
    return "TreeApplet info";
  }

  private void initGUI() {
    this.getContentPane().setLayout(new BorderLayout());
    JTree tree = new JTree(this.skillTree);
    this.getContentPane().add(new JScrollPane(tree));
  }
}
