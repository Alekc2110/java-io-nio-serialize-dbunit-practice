package com.epam.alexkorshunovych.dbunit.repository;

import com.epam.alexkorshunovych.dbunit.entity.Doctor;
import com.epam.alexkorshunovych.dbunit.repository.interfaces.DoctorRepository;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Assert;

import java.io.FileInputStream;
import java.util.List;
import java.util.Objects;

import static org.dbunit.Assertion.assertEqualsIgnoreCols;

public class JpaDoctorRepositoryTest extends BaseDBUnitConfig {

    private final DoctorRepository repository;

    public JpaDoctorRepositoryTest() {
        repository = new JpaDoctorRepository();
    }

    public void testFindAll() {
        //given
        int expectedSize = 5;
        //when
        List<Doctor> doctors = repository.findAll();
        //then
        Assert.assertEquals(expectedSize, doctors.size());

    }

    public void testSave() {
        //given
        Doctor newDoc = Doctor.builder().name("Sam").surName("Neil").position("surgeon").build();
        //when
        Long saved = repository.save(newDoc);
        System.out.println(saved);
        //then
        Assert.assertNotNull(saved);

    }

    public void testDelete() {
        //when
        repository.delete(1L);
        //then
        Doctor doctor = repository.findById(1L);
        Assert.assertNull(doctor);
    }

    public void testFindById() {
        //given
        String expName = "Eva";
        String expSurName = "Mendez";
        //when
        Doctor doctor = repository.findById(5L);
        //then
        Assert.assertEquals(expName, doctor.getName());
        Assert.assertEquals(expSurName, doctor.getSurName());
    }

    public void testGivenDataSetEmptySchemaWhenDataSetCreatedThenTablesAreEqual() throws Exception {
        //given
        IDataSet expectedDataSet = getDataSet();
        ITable expectedTable = expectedDataSet.getTable("DOCTORS");
        //when
        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("DOCTORS");
        //then
        assertEquals(expectedTable.getRowCount(), actualTable.getRowCount());
        assertEquals(expectedTable.getValue(0,"name"), actualTable.getValue(0, "name"));
        assertEquals(expectedTable.getValue(0,"surname"), actualTable.getValue(0, "surname"));
        assertEquals(expectedTable.getValue(0,"position"), actualTable.getValue(0, "position"));
    }

    public void testCompareFinalResultTableWhenSaveNewEntity() throws Exception {
        //given
        Doctor newDoc = Doctor.builder().name("Sam").surName("Neil").position("surgeon").build();
        ClassLoader classLoader = getClass().getClassLoader();
        String file = Objects.requireNonNull(classLoader.getResource("expectedDataSetSave.xml")).getFile();
        FlatXmlDataSet expectedData = new FlatXmlDataSetBuilder().build(new FileInputStream(file));
        ITable doctorsTable = expectedData.getTable("DOCTORS");
        //when
        repository.save(newDoc);
        //then
        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("DOCTORS");

        Assert.assertEquals(doctorsTable.getRowCount(), actualTable.getRowCount());
        Assert.assertEquals(doctorsTable.getValue(5, "name"), newDoc.getName());
        Assert.assertEquals(doctorsTable.getValue(5, "position"), newDoc.getPosition());
        assertEqualsIgnoreCols(doctorsTable, actualTable, new String[] { "id" });
    }

    public void testCompareFinalResultTableWhenDeleteEntity() throws Exception {
        //given
        Long idToDelete = 5L;
        ClassLoader classLoader = getClass().getClassLoader();
        String file = Objects.requireNonNull(classLoader.getResource("expectedDataSetDelete.xml")).getFile();
        FlatXmlDataSet expectedData = new FlatXmlDataSetBuilder().build(new FileInputStream(file));
        ITable doctorsTable = expectedData.getTable("DOCTORS");
        //when
        repository.delete(idToDelete);
        //then
        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("DOCTORS");

        Assert.assertEquals(doctorsTable.getRowCount(), actualTable.getRowCount());
        assertEqualsIgnoreCols(doctorsTable, actualTable, new String[] { "id" });
    }

}