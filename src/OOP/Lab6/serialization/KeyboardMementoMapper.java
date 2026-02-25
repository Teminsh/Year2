package OOP.Lab6.serialization;

import OOP.Lab6.KeyboardMemento;
import OOP.Lab6.commands.CommandSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyboardMementoMapper implements DictionaryMapper<KeyboardMemento> {
    @Override
    public Map<String, Object> toMap(KeyboardMemento obj) {
        Map<String, Object> root = new HashMap<>();
        List<Map<String, Object>> items = new ArrayList<>();

        obj.bindings().forEach((keyCombo, spec) -> {
            Map<String, Object> it = new HashMap<>();
            it.put("k", keyCombo);
            it.put("t", spec.type());
            it.put("p", spec.params());
            items.add(it);
        });

        root.put("v", 1);
        root.put("b", items);
        return root;
    }

    @Override
    public KeyboardMemento fromMap(Map<String, Object> dict) {
        Object raw = dict.get("b");
        if (!(raw instanceof List<?> list)) throw new IllegalArgumentException("Invalid state: b");

        Map<String, CommandSpec> bindings = new HashMap<>();

        for (Object o : list) {
            if (!(o instanceof Map<?, ?> m)) continue;

            String k = String.valueOf(m.get("k"));
            String t = String.valueOf(m.get("t"));

            Map<String, Object> p = new HashMap<>();
            Object rp = m.get("p");
            if (rp instanceof Map<?, ?> pm) {
                pm.forEach((pk, pv) -> p.put(String.valueOf(pk), pv));
            }

            bindings.put(k, new CommandSpec(t, p));
        }

        return new KeyboardMemento(Map.copyOf(bindings));
    }
}
