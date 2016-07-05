package dao;

import entity.CoreEntity;

import java.util.Collection;

public interface DataStore {

    void deleteAll();

    CoreEntity get(String id);

    Collection<CoreEntity> getAll();

    void put(CoreEntity entity);

}
