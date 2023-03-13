package com.epam.alexkorshunovych.filesharing.repository;

import com.epam.alexkorshunovych.filesharing.repository.interfaces.FileDao;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

@SpringBootTest
class JdbcFileDaoTest extends DBUnitConfig {
    public static final String TRUNCATE_SQL = "TRUNCATE TABLE files";

    @Autowired
    FileDao subject;

    @Autowired
    private DataSource dataSource;

    @AfterEach
    void clearDatabase() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute(TRUNCATE_SQL);
    }

    @Test
    void saveFileTest() throws Exception {
        //given
        File test = createFileWithSize("test", 100_000);
        //when
        subject.saveFile("test1", test.getAbsolutePath());
        //then
        removeFile(test.getAbsolutePath());
        ITable actualTable = getITable();
        Assertions.assertEquals("test1", actualTable.getValue(0, "file_name"));
        Assertions.assertEquals(1, actualTable.getRowCount());
    }

    @Test
    void saveFileExpectedExceptionTest() throws IOException {
        //given
        File test = createFileWithSize("test", 201_000_000);
        //when
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> subject.saveFile("test1", test.getAbsolutePath()));
        removeFile(test.getAbsolutePath());
    }

    @Test
    void retrieveFileTest() throws IOException {
        //given
        File test = createFileWithSize("testRetrieve", 100_000);
        subject.saveFile(test.getName(), test.getAbsolutePath());
        removeFile(test.getAbsolutePath());
        //when
        subject.retrieveFile(test.getName(), "newTestFile");
        //then
        Assertions.assertTrue(Files.isRegularFile(Path.of("newTestFile")));
    }

    @Test
    void expireFileTest() throws Exception {
        //given
        File test = createFileWithSize("testExpire", 100_000);
        subject.saveFile(test.getName(), test.getAbsolutePath());
        removeFile(test.getAbsolutePath());
        ITable savedTable = getITable();
        int id = (int)savedTable.getValue(0, "id");
        //when
        boolean result = subject.expireFile(id);
        //then
        ITable actualTable = getITable();
        LocalDate now = LocalDate.now();
        Assertions.assertTrue(result);
        Assertions.assertEquals(now, LocalDate.parse(actualTable.getValue(0, "expiration_date").toString()));
        Assertions.assertEquals(1, actualTable.getRowCount());
    }

    private ITable getITable() throws Exception {
        IDataSet databaseDataSet = getConnection().createDataSet();
        return databaseDataSet.getTable("FILES");
    }

    private File createFileWithSize(String filename, long sizeInBytes) throws IOException {
        Path tempFile = Files.createTempFile(filename, ".txt");

        RandomAccessFile raf = new RandomAccessFile(tempFile.toFile(), "rw");
        raf.setLength(sizeInBytes);
        raf.close();

        return tempFile.toFile();
    }

    private void removeFile(String path) throws IOException {
        Files.delete(Path.of(path));
    }
}