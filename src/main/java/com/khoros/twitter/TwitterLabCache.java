package com.khoros.twitter;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

public class TwitterLabCache<K, V> {

    private Map<K, V> cache;

    public TwitterLabCache() {
        this.cache = new HashMap<K, V>();
    }

    public V get(K key) {
        return cache.get(key);
    }

    public V put(K key, V value) {
        return cache.put(key, value);
    }

    public void clear() {
        cache.clear();
    }

    public int size() {
        return cache.size();
    }

    public boolean containsKey(K key) {
        return cache.containsKey(key);
    }

    public V remove(K key) {
        return cache.remove(key);
    }

    public Collection<V> values() {
        return cache.values();
    }

}
