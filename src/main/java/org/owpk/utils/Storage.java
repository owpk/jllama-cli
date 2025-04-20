package org.owpk.utils;

public interface Storage {
    void save(String key, String value);
    String load(String key);
    void delete(String key);
    void clear();
}
