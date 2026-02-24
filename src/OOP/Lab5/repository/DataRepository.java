package OOP.Lab5.repository;

import OOP.Lab5.model.Identifiable;

import java.io.*;
import java.util.*;

public class DataRepository<T extends Serializable & Identifiable> implements IDataRepository<T> {

    //region Fields
    private final String filePath;
    private final List<T> cache;
    //endregion

    public DataRepository(String filePath) {
        this.filePath = filePath;
        this.cache = loadFromFile();
    }

    //region File I/O
    @SuppressWarnings("unchecked")
    private List<T> loadFromFile() {
        File file = new File(filePath);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<T>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(cache);
        } catch (IOException e) {
            throw new RuntimeException("Не удалось сохранить данные в файл: " + filePath, e);
        }
    }
    //endregion

    //region CRUD
    @Override
    public List<T> getAll() {
        return Collections.unmodifiableList(cache);
    }

    @Override
    public T getById(int id) {
        return cache.stream()
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Невозможно добавить пустой (null) элемент");
        }
        if (getById(item.getId()) != null) {
            throw new IllegalArgumentException("Элемент с ID " + item.getId() + " уже существует");
        }
        cache.add(item);
        saveToFile();
    }

    @Override
    public void update(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Невозможно обновить пустой (null) элемент");
        }
        for (int i = 0; i < cache.size(); i++) {
            if (cache.get(i).getId() == item.getId()) {
                cache.set(i, item);
                saveToFile();
                return;
            }
        }
        throw new IllegalArgumentException("Элемент с ID " + item.getId() + " не найден для обновления");
    }

    @Override
    public void delete(T item) {
        if (item == null) return;
        if (cache.removeIf(existing -> existing.getId() == item.getId())) {
            saveToFile();
        }
    }
    //endregion
}
