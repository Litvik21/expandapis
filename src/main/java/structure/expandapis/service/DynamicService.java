package structure.expandapis.service;

import structure.expandapis.dto.Payload;
import java.util.List;
import java.util.Map;

public interface DynamicService {
    void saveDynamicRecords(Payload payload);

    List<Map<String, Object>> findAll();
}
