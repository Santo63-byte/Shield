package com.sbyte.shield.core.base.abst;

import java.util.Set;

public interface CacheManager<K, V> {
    void loadAllCaches();
    void loadCache(String cacheName);
    void put(K key, V value);
    void put(K key, Set<String> value);
    Object get(String cacheName);
    void evict(String cacheName, K key);
    void clear(String cacheName);
    // Convenience methods for specific cache lookups
    boolean isPresentInCache(String cacheName, K key);
    boolean isCacheEmpty(String cacheName);
    void reloadCache(String cacheName);
    void reloadAllCaches();
}
