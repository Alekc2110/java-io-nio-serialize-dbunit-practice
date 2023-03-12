package com.epam.alexkorshunovych.dbunit.repository;

import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.util.Objects;

public abstract class BaseDBUnitConfig extends DataSourceBasedDBTestCase {
    @Override
    protected DataSource getDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(
                "jdbc:postgresql://localhost:5432/dbunit");
        dataSource.setUser("postgres");
        dataSource.setPassword("postgres");

        return dataSource;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        String file = Objects.requireNonNull(classLoader.getResource("actualDataset.xml")).getFile();
        return new FlatXmlDataSetBuilder().build(new FileInputStream(file));
    }

    @Override
    protected DatabaseOperation getSetUpOperation() {
        return DatabaseOperation.CLEAN_INSERT;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() {
        return DatabaseOperation.DELETE_ALL;
    }
}
