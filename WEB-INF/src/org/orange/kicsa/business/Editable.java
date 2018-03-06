package org.orange.kicsa.business;

public interface Editable {
  boolean isSync();

  void setSync(boolean var1);

  boolean isNew();

  void setNew(boolean var1);

  boolean isDirty();

  void setDirty(boolean var1);

  boolean isDeleted();

  void setDeleted(boolean var1);

  public static class Status implements Editable, Cloneable {
    private static final int SYNC_ID = 0;
    private static final int NEW_ID = 1;
    private static final int DIRTY_ID = 2;
    private static final int DELETED_ID = 3;
    public static final Editable.Status SYNC = new Editable.Status(0);
    public static final Editable.Status NEW = new Editable.Status(1);
    public static final Editable.Status DIRTY = new Editable.Status(2);
    public static final Editable.Status DELETED = new Editable.Status(3);
    private int id;

    private Status(int someId) {
      this.id = someId;
    }

    public boolean equals(Object someObject) {
      boolean equal = false;
      if (someObject instanceof Editable.Status) {
        equal = ((Editable.Status)someObject).id == this.id;
      }

      return equal;
    }

    public boolean isSync() {
      return this.id == 0;
    }

    public void setSync(boolean sync) {
      if (sync) {
        this.id = 0;
      }

    }

    public boolean isNew() {
      return this.id == 1;
    }

    public void setNew(boolean someNewState) {
      if (someNewState) {
        this.id = 1;
      }

    }

    public boolean isDirty() {
      return this.id == 2;
    }

    public void setDirty(boolean someDirtyState) {
      if (someDirtyState) {
        this.id = 2;
      }

    }

    public boolean isDeleted() {
      return this.id == 3;
    }

    public void setDeleted(boolean someDeletedState) {
      if (someDeletedState) {
        this.id = 3;
      }

    }

    public String toString() {
      return "Status {" + this.id + "}";
    }

    public Object clone() {
      try {
        Object clone = super.clone();
        return clone;
      } catch (CloneNotSupportedException var2) {
        throw new RuntimeException(var2.getClass().getName() + ": " + var2.getMessage());
      }
    }
  }
}
