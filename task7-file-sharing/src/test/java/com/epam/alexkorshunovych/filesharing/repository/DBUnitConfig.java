package com.epam.alexkorshunovych.filesharing.repository;

import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.util.Objects;

public abstract class DBUnitConfig extends DataSourceBasedDBTestCase {
    @Override
    protected DataSource getDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(
                "jdbc:postgresql://localhost:5432/file_sharing");
        dataSource.setUser("postgres");
        dataSource.setPassword("postgres");

        return dataSource;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        String file = Objects.requireNonNull(classLoader.getResource("")).getFile();
        return new FlatXmlDataSetBuilder().build(new FileInputStream(file));
    }

    @Override
    protected DatabaseOperation getSetUpOperation() {
        return DatabaseOperation.NONE;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() {
        return DatabaseOperation.NONE;
    }
}
