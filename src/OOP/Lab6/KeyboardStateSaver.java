package OOP.Lab6;

import OOP.Lab6.io.DualOutput;
import OOP.Lab6.serialization.DictionaryMapper;
import OOP.Lab6.serialization.Serializer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class KeyboardStateSaver {
    //region fields
    private final Path file;
    private final Serializer serializer;
    private final DictionaryMapper<KeyboardMemento> mapper;
    //endregion

    public KeyboardStateSaver(Path file, Serializer serializer, DictionaryMapper<KeyboardMemento> mapper) {
        this.file = file;
        this.serializer = serializer;
        this.mapper = mapper;
    }

    public void saveFrom(Keyboard keyboard) throws Exception {
        KeyboardMemento memento = keyboard.createMemento();
        Map<String, Object> dict = mapper.toMap(memento);
        String payload = serializer.serialize(dict);
        Files.writeString(file, payload);
    }

    public void tryLoadInto(Keyboard keyboard, DualOutput output) {
        try {
            if (!Files.exists(file)) return;
            String payload = Files.readString(file);
            if (payload.isBlank()) return;

            Map<String, Object> dict = serializer.deserialize(payload);
            KeyboardMemento memento = mapper.fromMap(dict);
            keyboard.restoreFrom(memento);
        } catch (Exception e) {
            output.log("SYSTEM", "Failed to load state. Starting fresh.");
        }
    }
}
