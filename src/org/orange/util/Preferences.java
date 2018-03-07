package org.orange.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.apache.log4j.Logger;

public class Preferences {
  private String fileName;
  private static Map preferencesMap = new HashMap();
  private Properties properties;
  private Logger log = Logger.getLogger(this.getClass());

  public static Preferences systemNodeForPackage(Class somePackageClass) {
    Assert.notNullArgument(preferencesMap, "Le dictionnaire de préférences est nul");
    Assert.notNullArgument(somePackageClass, "Impossible de fournir des préférences pour le package d'une classe nulle");
    Preferences preferences = (Preferences)preferencesMap.get(somePackageClass);
    if (preferences == null) {
      preferences = new Preferences(somePackageClass);
      preferencesMap.put(somePackageClass, preferences);
    }

    return preferences;
  }

  public int getInt(String someKey, int defaultValue) {
    return Integer.parseInt(this.get(someKey, String.valueOf(defaultValue)));
  }

  public String get(String someKey, String defaultValue) {
    return this.properties.getProperty(someKey, defaultValue);
  }

  public void put(String someKey, String someValue) {
    this.properties.setProperty(someKey, someValue);
  }

  public String[] keys() {
    Set keySet = this.properties.keySet();
    String[] keyStrings = new String[keySet.size()];
    Iterator keysIterator = keySet.iterator();

    for(int var4 = 0; keysIterator.hasNext(); keyStrings[var4++] = (String)keysIterator.next()) {
      ;
    }

    return keyStrings;
  }

  protected Preferences(Class somePackageClass) {
    Assert.notNullArgument(somePackageClass, "somePackageClass cannot be null");
    this.properties = new Properties();

    try {
      this.fileName = somePackageClass.getName();
      this.fileName = this.fileName.substring(this.fileName.lastIndexOf(".") + 1) + ".properties";
      this.log.info("Loading preferences from " + this.fileName);
      InputStream propertiesStream = somePackageClass.getResourceAsStream(this.fileName);
      if (propertiesStream == null) {
        throw new FileNotFoundException(this.fileName + " at the same location than the class " + somePackageClass.getClass());
      } else {
        this.properties.load(propertiesStream);
      }
    } catch (IOException var3) {
      throw new RuntimeException(var3.getClass().getName() + ": " + var3.getMessage());
    }
  }

  public String toString() {
    return this.properties.toString();
  }

  public void putInt(String someKey, int someValue) {
    this.properties.put(someKey, String.valueOf(someValue));
  }

  public void flush() throws BackingStoreException {
    try {
      FileOutputStream propertiesOutputStream = new FileOutputStream(this.fileName);

      try {
        this.properties.store(propertiesOutputStream, (String)null);
      } finally {
        propertiesOutputStream.close();
      }

    } catch (Exception var7) {
      throw new BackingStoreException(var7);
    }
  }
}
