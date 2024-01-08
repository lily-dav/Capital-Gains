import java.util.Set;

/**
 * An interface for mapping classes. 
 * All maiing style classes should implement this interface
 * or something very much like it
 * 
 * In general, the key of a map should be a thing that the MAP
 * supports easy search upon.  The value is found via the key
 * 
 * Adapted from Java Map interface
 */
public interface Map151Interface<K, V> {
    /**
     * Add a key value pair to the Map
     * @param key the key 
     * @param val the value
     */
    public void put(K key, V val);
    public V get(K key);
    public boolean containsKey(K key);
    public int size();
    public Set<K> keySet();
}
