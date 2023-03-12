package com.epam.alexkorshunovych.dbunit.repository;

import com.epam.alexkorshunovych.dbunit.entity.Patient;
import com.epam.alexkorshunovych.dbunit.repository.interfaces.PatientRepository;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Assert;

import java.io.FileInputStream;
import java.util.List;
import java.util.Objects;

import static org.dbunit.Assertion.assertEqualsIgnoreCols;

public class JpaPatientRepositoryTest extends BaseDBUnitConfig {

    private final PatientRepository repository;

    public JpaPatientRepositoryTest() {
        repository = new JpaPatientRepository();
    }

    public void testFindAll() {
        //given
        int expectedSize = 5;
        //when
        List<Patient> patients = repository.findAll();
        //then
        Assert.assertEquals(expectedSize, patients.size());
    }

    public void testSave() {
        //given
        Patient newPatient = Patient.builder().name("Ben").surName("Cohen").age(25).build();
        //when
        Long saved = repository.save(newPatient);
        //then
        Assert.assertNotNull(saved);
    }

    public void testDeletePatientsGreaterThen() {
        //given
        int deleteGreaterThen = 30;
        int deletedCount = 2;
        //when
        int deletedEntities = repository.deletePatientsGreaterThen(deleteGreaterThen);
        //then
        Assert.assertEquals(deletedCount, deletedEntities);
    }

    public void testCompareFinalResultTableWhenSaveNewEntity() throws Exception {
        //given
        Patient newPatient = Patient.builder().name("Ali").surName("Mohamad").age(45).build();
        ClassLoader classLoader = getClass().getClassLoader();
        String file = Objects.requireNonNull(classLoader.getResource("expectedDataSetSave.xml")).getFile();
        FlatXmlDataSet expectedData = new FlatXmlDataSetBuilder().build(new FileInputStream(file));
        ITable patientsTable = expectedData.getTable("PATIENTS");
        //when
        repository.save(newPatient);
        //then
        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("PATIENTS");

        Assert.assertEquals(patientsTable.getRowCount(), actualTable.getRowCount());
        Assert.assertEquals(patientsTable.getValue(5, "name"), newPatient.getName());
        Assert.assertEquals(patientsTable.getValue(5, "surname"), newPatient.getSurName());
        assertEqualsIgnoreCols(patientsTable, actualTable, new String[] { "id" });
    }

    public void testCompareFinalResultTableWhenDeletePatientsGreaterThenGivenAge() throws Exception {
        //given
        ClassLoader classLoader = getClass().getClassLoader();
        String file = Objects.requireNonNull(classLoader.getResource("expectedDatasetDeleteGt30.xml")).getFile();
        FlatXmlDataSet expectedData = new FlatXmlDataSetBuilder().build(new FileInputStream(file));
        ITable patientsTable = expectedData.getTable("PATIENTS");
        //when
        repository.deletePatientsGreaterThen(30);
        //then
        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("PATIENTS");

        Assert.assertEquals(patientsTable.getRowCount(), actualTable.getRowCount());
        assertEqualsIgnoreCols(patientsTable, actualTable, new String[] { "id" });
    }
}