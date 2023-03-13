package com.epam.alexkorshunovych.filesharing.repository;

public final class QuerySQL {

    private QuerySQL() {
        throw new IllegalStateException("Utility class");
    }
    public static final String SAVE_SQL = "INSERT INTO files (file_name, file_data) VALUES (?, ?)";
    public static final String GET_SQL = "SELECT file_data FROM files WHERE file_name = ?";
    public static final String CALL_SQL = "CALL expire_file(?,?)";
}
