package OOP.Lab5.repository;

import java.util.List;

public interface IDataRepository<T> {
    List<T> getAll();
    T getById(int id);
    void add(T item);
    void update(T item);
    void delete(T item);
}