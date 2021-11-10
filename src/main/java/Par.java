public class Par implements Comparable<Par> {

  private String key;
  private AvlTree<Email> value;

  public Par() {
    key = new String();
    value = new AvlTree<>();
  }

  public Par(String key) {
    this.key = key;
    value = new AvlTree<>();
  }

  public Par(String key, AvlTree<Email> value) {
    this.key = key;
    this.value = value;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public AvlTree<Email> getValue() {
    return value;
  }

  public void setValue(AvlTree<Email> value) {
    this.value = value;
  }

  public void addEmail(Email m) {
    value.insert(m);
  }

  public void deleteEmail(Email m) {
    value.remove(m);
  }

  public boolean arbolEmpty() {
    return value.isEmpty();
  }

  @Override
  public String toString() {
    return "Par: Key: " + key + ", Value: " + value;
  }

  @Override
  public int compareTo(Par par2) {
    return key.compareTo(par2.getKey());
  }

  @Override
  public boolean equals(Object obj) {
    Par par2 = (Par) obj;
    return key.equals(par2.getKey());
  }

  @Override
  public int hashCode() {
    return key.hashCode();
  }

}
