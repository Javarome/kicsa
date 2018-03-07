package test.org.orange.kicsa.business.skill;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.orange.kicsa.Application;
import org.orange.kicsa.business.skill.SkillTree;
import org.orange.util.integration.DAOException;

public class TestSkillTree extends TestCase {
  static SkillTree skillTree;

  public TestSkillTree(String name) {
    super(name);
  }

  public static Test suite() {
    TestSuite suite = new TestSuite();
    return suite;
  }

  protected void setUp() throws DAOException {
    try {
      Application.init((URL)null);
      skillTree = Application.getSkillFactory().getTree();
    } catch (Throwable var2) {
      Assert.fail(var2.toString());
    }

  }

  protected void tearDown() {
  }

  public void testDisplay() {
    JTree tree = new JTree(skillTree);
    JFrame f = new JFrame("Skill tree");
    f.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
    f.getContentPane().add(new JScrollPane(tree));
    f.pack();
    f.setVisible(true);
  }
}
