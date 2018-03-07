package org.orange.kicsa.business.skill;

import org.orange.kicsa.business.Editable;

public interface RelationshipType extends Editable {
  String getName();

  void setName(String var1);

  boolean isParent();

  void setParent(boolean var1);
}
