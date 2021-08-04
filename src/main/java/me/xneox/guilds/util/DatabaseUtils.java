package me.xneox.guilds.util;

import co.aikar.idb.DB;
import co.aikar.idb.Database;
import co.aikar.idb.DatabaseOptions;
import co.aikar.idb.PooledDatabaseOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseUtils {
    public static void connect() {
        DatabaseOptions.DatabaseOptionsBuilder builder = DatabaseOptions.builder()
                .sqlite(HookUtils.DIRECTORY + "/database.db");

        Database database = PooledDatabaseOptions.builder().options(builder.build()).createHikariDatabase();
        DB.setGlobalDatabase(database);
    }

    public static List<String> serializeList(String result) {
        return new ArrayList<>(Arrays.asList(result.split(",")));
    }

    public static String deserializeList(List<String> list) {
        return String.join(",", list);
    }
}
