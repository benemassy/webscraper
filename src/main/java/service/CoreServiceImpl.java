package service;

import com.google.inject.Inject;
import dao.DataStore;
import entity.CoreEntity;
import com.google.inject.Singleton;

import java.util.*;

@SuppressWarnings("unused")
@Singleton
public class CoreServiceImpl implements CoreService<CoreEntity> {

    private static DataStore dataStore;

    @Inject
    public CoreServiceImpl(DataStore store) {
        dataStore = store;
    }

    public void addItem(CoreEntity item){

        if(item != null) {
            dataStore.put(item);
        }
    }

    @Override
    public void addAllItems(List<CoreEntity> items) {
        items.stream().forEach(item -> dataStore.put(item));
    }

    public CoreEntity getItem(String id){
        return dataStore.get(id);
    }

    @Override
    public Collection<CoreEntity> getAllItems(Class classType) {
        return dataStore.getAll();
    }

    @Override
    public void deleteAllItems() {
        dataStore.deleteAll();
    }


}
