package structure.expandapis.dto;

import java.util.List;
import java.util.Map;

public record Payload(String table,
                      List<Map<String, Object>> records) {
}
