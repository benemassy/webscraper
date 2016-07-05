package dao;

import com.google.inject.Singleton;
import entity.CoreEntity;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by benmassey on 04/07/2016.
 */

@Singleton
public class DataStoreImpl implements DataStore {

    private static Map<String,CoreEntity> store = new TreeMap<>();

    public void deleteAll() {
        store.clear();
    }

    public CoreEntity get(String id) {
        return store.get(id);
    }

    public Collection<CoreEntity> getAll() {
        return store.values();
    }

    public void put(CoreEntity entity) {
        store.put(entity.getDescription(),entity);
    }

}
