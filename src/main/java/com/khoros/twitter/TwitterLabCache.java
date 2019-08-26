package com.khoros.twitter;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class TwitterLabCache<T> {

    private Map<T, T> cache;

    public TwitterLabCache() {
        this.cache = new TreeMap<T, T>();
    }

    public T get(T key) {
        return cache.get(key);
    }

    public T put(T key, T value) {
        return cache.put(key, value);
    }

    public void clear() {
        cache.clear();
    }

    public int size() {
        return cache.size();
    }

    public T remove(T key) {
        return cache.remove(key);
    }

    public Collection<T> values() {
        return cache.values();
    }

}
