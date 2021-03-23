package tools;


import io.javalin.http.Context;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Router {



    public static <T> List<T> resolve(ResultSet resultSet, Function<ResultSet, T> function) throws SQLException {
        List<T> list = new ArrayList<T>();

        while(resultSet.next()) {
            list.add(function.apply(resultSet));
        }
        
        return list;
    }

    private static String resolveCatalog(ResultSet resultSet) throws SQLException {
            List<String> catalogs = resolve(resultSet, rs ->  {

                try {
                    return resultSet.getString(1);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                return null;

            });

            if(!catalogs.isEmpty())
                return catalogs.get(0);

            return null;

    }


    public static void test(Context context, DatabaseFactory factory) throws SQLException {

        try(Connection conn = factory.connection()) {

            DatabaseMetaData dbMetaData = conn.getMetaData();

            String catalog = resolveCatalog(dbMetaData.getCatalogs());

            List<Column> columns =  resolve(dbMetaData.getColumns(catalog, "aqcs", "qc_skd_chk_t", null), resultSet ->  {

                try {
                    return Column.builder()
                                .name(resultSet.getString("COLUMN_NAME").toUpperCase())
                                .nullable(resultSet.getBoolean("NULLABLE"))
                                .autoIncrement(resultSet.getBoolean("IS_AUTOINCREMENT"))
                                .typeName(resultSet.getString("TYPE_NAME").toUpperCase())
                                .dataType(resultSet.getString("DATA_TYPE"))
                                .size(resultSet.getInt("COLUMN_SIZE"))
                                .bufferLength(resultSet.getInt("BUFFER_LENGTH"))
                                .build();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


                return null;
            });

            System.out.println(columns);
            context.json(columns);
        }

    }



}
