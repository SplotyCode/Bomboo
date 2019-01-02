package de.splotycode.bamboo.core.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;

@EqualsAndHashCode
public class DataFactory {

    private HashMap<String, Object> data = new HashMap<>();

    public <T> T getData(DataKey<T> key) {
        return (T) data.get(key.name);
    }

    /*
     * This will use the name instead of the key name
     * The Key is only for providing the Generic
     */
    public <T> T getData(String name, DataKey<T> key) {
        return (T) data.get(name);
    }

    public <T> void putData(DataKey<T> key, T obj) {
        data.putIfAbsent(key.name, obj);
    }

    public <T> void forePutData(DataKey<T> key, T obj) {
        data.put(key.name, obj);
    }

    public <T> void putData(String name, DataKey<T> key, T obj) {
        data.putIfAbsent(name, obj);
    }

}
