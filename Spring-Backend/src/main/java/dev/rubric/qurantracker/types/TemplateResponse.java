package dev.rubric.qurantracker.types;

public class TemplateResponse<K, V> {
    private K key;
    private V value;

    public TemplateResponse(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public TemplateResponse() {}

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public void setResponse(K success, V message) {
        setKey(success);
        setValue(message);
    }
}
