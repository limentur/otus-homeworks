package ru.otus.cachehw;

import java.util.*;

public class MyCache<K, V> implements HwCache<K, V> {
    Map<K,V> cacheMap = new WeakHashMap <>();
    HwListener<K,V> listener = new HwListener<K, V>() {
        @Override
        public void notify(K key, V value, String action) {
        }
    };


    @Override
    public void put(K key, V value) {
        cacheMap.put(key,value);
    }

    @Override
    public void remove(K key) {
        cacheMap.remove(key);
    }

    @Override
    public V get(K key) {
        return cacheMap.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        this.listener = listener;
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        this.listener=null;
    }
}
