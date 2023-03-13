package com.epam.alexkorshunovych.filesharing.repository;

import com.epam.alexkorshunovych.filesharing.repository.interfaces.FileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.epam.alexkorshunovych.filesharing.repository.QuerySQL.*;

@Repository
public class JdbcFileDao implements FileDao {

    private static final int MAX_FILE_SIZE = 200_000_000;

    @Autowired
    private DataSource dataSource;

    @Override
    public void saveFile(String fileName, String path) {
        long length = Paths.get(path).toFile().length();
        if(length > MAX_FILE_SIZE){
            throw new UnsupportedOperationException("Cannot save file > 200mb");
        }
        try (Connection conn = dataSource.getConnection();
             FileInputStream inputStream = new FileInputStream(path);
             PreparedStatement ps = conn.prepareStatement(SAVE_SQL);
        ) {
            conn.setAutoCommit(false);

            ps.setString(1, fileName);
            ps.setBinaryStream(2, inputStream, length);
            ps.executeUpdate();

            conn.commit();
            conn.setAutoCommit(true);
        } catch (IOException | SQLException e) {
            System.err.println("Could not save file: " + fileName);
            e.printStackTrace();
        }
    }

    @Override
    public void retrieveFile(String fileName, String filePath) {
        try (Connection connection = dataSource.getConnection()
        ) {
            PreparedStatement statement = connection.prepareStatement(GET_SQL);
            statement.setString(1, fileName);
            ResultSet resultSet = statement.executeQuery();
            InputStream inputStream = null;
            FileOutputStream outputStream = null;
            if (resultSet.next()) {
               inputStream = resultSet.getBinaryStream("file_data");
                outputStream = new FileOutputStream(filePath);
                inputStream.transferTo(outputStream);
            }

            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (SQLException | IOException e) {
            System.err.println("Could not retrieve file: " + fileName);
            e.printStackTrace();
        }
    }

    @Override
    public void expireFile(int fileId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(CALL_SQL, fileId);
    }

}
