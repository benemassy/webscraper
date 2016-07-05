package service;

import entity.CoreEntity;

import java.util.Collection;
import java.util.List;

@SuppressWarnings("unused")
public interface CoreService<T extends CoreEntity> {

    void addItem(T item);

    void addAllItems(List<T> item);

    T getItem(String id);

    Collection<T> getAllItems(Class<T> classType);

    void deleteAllItems();

}
