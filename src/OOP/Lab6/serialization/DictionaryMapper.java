package OOP.Lab6.serialization;

import java.util.Map;

public interface DictionaryMapper<T> {
    Map<String, Object> toMap(T obj);
    T fromMap(Map<String, Object> dict);
}