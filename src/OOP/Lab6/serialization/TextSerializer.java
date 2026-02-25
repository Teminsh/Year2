package OOP.Lab6.serialization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextSerializer implements Serializer {

    //region serialize
    @Override
    public String serialize(Map<String, Object> dict) {
        StringBuilder sb = new StringBuilder();
        sb.append("v:").append(dict.getOrDefault("v", "1")).append("\n");

        Object bObj = dict.get("b");
        if (bObj instanceof List<?> list) {
            for (Object item : list) {
                if (item instanceof Map<?, ?> b) {
                    sb.append("b:");
                    sb.append(b.get("k")).append("|");
                    sb.append(b.get("t")).append("|");

                    Object pObj = b.get("p");
                    if (pObj instanceof Map<?, ?> params && !params.isEmpty()) {
                        List<String> pList = new ArrayList<>();
                        params.forEach((k, v) -> pList.add(k + "=" + v));
                        sb.append(String.join(",", pList));
                    }
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }
    //endregion

    //region deserialize
    @Override
    public Map<String, Object> deserialize(String payload) {
        Map<String, Object> root = new HashMap<>();
        List<Map<String, Object>> bindings = new ArrayList<>();
        root.put("b", bindings);

        if (payload == null || payload.isBlank()) return root;

        String[] lines = payload.split("\n");
        for (String line : lines) {
            if (line.startsWith("v:")) {
                root.put("v", line.substring(2));
            } else if (line.startsWith("b:")) {
                String[] parts = line.substring(2).split("\\|", -1);
                if (parts.length < 2) continue;

                Map<String, Object> b = new HashMap<>();
                b.put("k", parts[0]);
                b.put("t", parts[1]);

                Map<String, Object> params = new HashMap<>();
                if (parts.length > 2 && !parts[2].isEmpty()) {
                    String[] pPairs = parts[2].split(",");
                    for (String pair : pPairs) {
                        String[] kv = pair.split("=");
                        if (kv.length == 2) {
                            params.put(kv[0], kv[1]);
                        }
                    }
                }
                b.put("p", params);
                bindings.add(b);
            }
        }
        return root;
    }
    //endregion
}
