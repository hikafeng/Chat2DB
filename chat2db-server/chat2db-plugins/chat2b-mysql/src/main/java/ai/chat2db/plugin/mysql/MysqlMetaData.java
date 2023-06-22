package ai.chat2db.plugin.mysql;

import ai.chat2db.spi.MetaData;
import ai.chat2db.spi.jdbc.DefaultMetaService;
import ai.chat2db.spi.sql.SQLExecutor;
import jakarta.validation.constraints.NotEmpty;

import java.sql.SQLException;

public class MysqlMetaData extends DefaultMetaService implements MetaData {
    @Override
    public String tableDDL(@NotEmpty String databaseName, String schemaName, @NotEmpty String tableName) {
        String sql = "SHOW CREATE TABLE " + format(databaseName) + "."
                + format( tableName);
        return SQLExecutor.getInstance().executeSql(sql, resultSet -> {
            try {
                if (resultSet.next()) {
                    return resultSet.getString("Create Table");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return null;
        });
    }

    public static String format(String tableName) {
        return "`" + tableName + "`";
    }
}
