package OOP.Lab6.serialization;

import java.util.Map;

public interface Serializer {
    String serialize(Map<String, Object> dict);
    Map<String, Object> deserialize(String payload);
}
