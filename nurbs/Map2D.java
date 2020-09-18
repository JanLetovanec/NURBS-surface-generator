package jl2119.nurbs;

import java.util.HashMap;
import java.util.Map;

public class Map2D<K, L, V> {
    private Map<K, Map<L, V>> map;

    public Map2D() {
        this.map = new HashMap<>();
    }

    public V getValues(K key1, L key2) {
        if(map.containsKey(key1)){
            return map.get(key1).get(key2);
        }

        return null;
    }

    public void putValue(K key1, L key2, V value){
        Map<L, V> subMap;
        if (map.containsKey(key1)) {
            subMap = map.get(key1);
        }
        else {
            subMap = new HashMap<>();
            map.put(key1, subMap);
        }
        subMap.put(key2, value);
    }

    public boolean containsKey(K key1, L key2) {
        if (!map.containsKey(key1))
            return false;

        return map.get(key1).containsKey(key2);
    }
}
