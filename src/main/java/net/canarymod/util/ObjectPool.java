package net.canarymod.util;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An object pool. Can pool things by any kind of key.
 *
 * @author Chris (damagefilter)
 */
public class ObjectPool<K, V> {
    private int poolCapacity;
    private int loadCapacityTolerance;
    private long highLoadThreshold;
    private long lastPrune;
    private boolean hasHighLoad;

    private ConcurrentHashMap<K, V> pool;

    /**
     * Instantiate a new object pool
     * @param capacity
     *                  the standard capacity
     * @param capacityTolerance
     *                  the extra capacity that can be applied to the standard capacity in high-load situations
     * @param highLoadThreshold
     *                  Determines which time between two cache prunes will be considered high-load.
     *                  It is assumed that when cache prunes occur often, more objects need to be added to the pool.
     *                  This is considered a high-load situation.
     *                  You probably want this to be a crazy huge value like 100000000
     */
    public ObjectPool(int capacity, int capacityTolerance, long highLoadThreshold) {
        this.pool = new ConcurrentHashMap<K, V>(capacity) {
        };
        this.poolCapacity = capacity;
        this.loadCapacityTolerance = capacityTolerance;
        this.highLoadThreshold = highLoadThreshold;
    }

    /**
     * Get an object from the pool by the given key value
     *
     * @param key the key
     * @return a pooled object or null if no object was pooled under the given value
     */
    public V get(K key) {
        if (pool.containsKey(key)) {
            return pool.get(key);
        }
        return null;
    }

    /**
     * Adds a new value to the pool.
     * If there already is something stored at the given key, it will be overridden.
     * If the pool size is exceeded existing elements will be removed until 1/3 of the
     * pool is empty again.
     * @param key the key
     * @param value the value
     *
     * @return the inserted value for convenience
     */
    public V add(K key, V value) {
        // Don't prune on high load
        if (pool.size()+1 > getCapacity()) {
            prune(true);
        }
        pool.put(key, value);
        return value;
    }

    /**
     * Check whether this pool has something stored at the given key value
     * @param key the key
     * @return true if there is a pooled object, false otherwise
     */
    public boolean has(K key) {
        return pool.containsKey(key);
    }

    private int getCapacity() {
        int capacity = poolCapacity;
        if (hasHighLoad) {
            capacity += loadCapacityTolerance;
        }
        return capacity;
    }

    private void prune(boolean checkLoad) {
        if (checkLoad) {
            checkLoad();
        }
        int capacity = getCapacity();

        int rest = capacity - (capacity / 3);
        if (rest > pool.size()) {
            rest = pool.size() / 3;
        }
        Iterator<java.util.Map.Entry<K, V>> iterator = pool.entrySet().iterator();
        while (pool.size() > rest) {
            if (iterator.hasNext()) {
                iterator.next();
                iterator.remove();
            }
            else {
                break;
            }
        }
    }

    private void checkLoad() {
        long now = System.nanoTime();
        // So if the time between last cache prune+loadfactor > now, the new prune occured too early.
        // Consider this a high-load situation
        boolean result = lastPrune + highLoadThreshold > now;
        boolean lastLoad = hasHighLoad;
        // Make sure when pruning is done we're pruning against the small capacity
        hasHighLoad = result;

        if (result != lastLoad && !result) {
            prune(false);
        }
        lastPrune = now;
    }

}
