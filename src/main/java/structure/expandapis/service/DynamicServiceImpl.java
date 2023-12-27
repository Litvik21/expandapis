package structure.expandapis.service;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import structure.expandapis.dto.Payload;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class DynamicServiceImpl implements DynamicService {
    private static final String SQL_FIND_TABLE =
            "SHOW TABLES LIKE ?";
    private static final String SQL_FIND_ALL =
            "SELECT * FROM products";

    private JdbcTemplate jdbcTemplate;

    @Transactional
    @Override
    public void saveDynamicRecords(Payload payload) {
        String tableName = payload.table();

        createTableIfNotExists(tableName, payload.records());

        List<Map<String, Object>> records = payload.records();
        for (Map<String, Object> recordMap : records) {
            saveRecord(tableName, recordMap);
        }
    }

    @Override
    public List<Map<String, Object>> findAll() {
        return jdbcTemplate.queryForList(SQL_FIND_ALL);
    }

    private void createTableIfNotExists(String tableName, List<Map<String, Object>> records) {
        Object[] params = {tableName};

        if (jdbcTemplate.queryForList(SQL_FIND_TABLE, params).isEmpty()) {
            StringBuilder createTableQuery = new StringBuilder("CREATE TABLE ");
            createTableQuery.append(tableName).append(" (id BIGINT AUTO_INCREMENT PRIMARY KEY)");

            jdbcTemplate.update(createTableQuery.toString());
        }

        for (Map<String,Object> record : records) {
            for (Map.Entry<String, Object> entry : record.entrySet()) {
                String columnName = entry.getKey();

                if (columnExists(tableName, columnName)) {
                    addColumnToTable(tableName, columnName);
                }
            }
        }
    }

    private boolean columnExists(String tableName, String columnName) {
        String query = "SHOW COLUMNS FROM " + tableName + " LIKE ?";
        return jdbcTemplate.queryForList(query, columnName).isEmpty();
    }

    private void addColumnToTable(String tableName, String columnName) {
        String query = "ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " VARCHAR(255)";
        jdbcTemplate.update(query);
    }



    private void saveRecord(String tableName, Map<String, Object> recordMap) {
        StringBuilder queryBuilder = new StringBuilder("INSERT INTO ").append(tableName).append(" (");
        StringBuilder valuesBuilder = new StringBuilder(" VALUES (");

        for (Map.Entry<String, Object> entry : recordMap.entrySet()) {
            String columnName = entry.getKey();
            queryBuilder.append(columnName).append(",");
            valuesBuilder.append("?,");
        }

        queryBuilder.deleteCharAt(queryBuilder.length() - 1);
        valuesBuilder.deleteCharAt(valuesBuilder.length() - 1);

        String queryString = queryBuilder.append(")").append(valuesBuilder).append(")").toString();

        Object[] params = recordMap.values().stream().map(Object::toString).toArray();

        jdbcTemplate.update(queryString, params);
    }
}
