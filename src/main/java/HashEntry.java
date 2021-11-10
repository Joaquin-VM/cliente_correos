public class HashEntry<K, T>{
    private K key;
    private T data;

    public HashEntry(K key, T data) {
        this.key = key;
        this.data = data;
    }

    public K getKey() {
        return key;
    }

    public T getData() {
        return data;
    }
}
