public class HashMap<K, T> {

  private HashEntry<K, T>[] table;
  private int size;
  private final int maxSize = 96769;

  public HashMap() {
    table = new HashEntry[maxSize];
    size = maxSize;
  }

  public HashMap(int size) {
    table = new HashEntry[size];
    this.size = size;
  }

  public void put(K key, T value) throws Exception {
    int pos = inRange(key);
    if (table[pos] != null && !table[pos].getKey().equals(key)) {
      throw new Exception("Collition");
    }

    table[pos] = new HashEntry<>(key, value);
  }

  public T get(K key) throws Exception {
    int pos = inRange(key);
    if (!exist(key)) {
      throw new Exception("Element not found.");
    }
    return table[pos].getData();
  }

  public void remove(K key) throws Exception {
    int pos = inRange(key);
    if (table[pos] != null && !table[pos].getKey().equals(key)) {
      throw new Exception("not found");
    }

    table[pos] = null;
  }

  public boolean exist(K key) {
    int pos = inRange(key);
    return table[pos] != null;
  }

  private int inRange(K key) {
    return hashFunc(key) % size;
  }

  public int hashFunc(K key) {
    int hashVal = key.hashCode();

    if (hashVal < 0) {
      hashVal = Math.abs(hashVal) + size;
    }

    return hashVal;
  }

}

