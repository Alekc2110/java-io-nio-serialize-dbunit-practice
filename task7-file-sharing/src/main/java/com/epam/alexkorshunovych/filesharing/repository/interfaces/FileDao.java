package com.epam.alexkorshunovych.filesharing.repository.interfaces;

public interface FileDao {
    void saveFile(String name, String filePath);
    void retrieveFile(String fileName, String filePath);
    boolean expireFile(int fileId);
}
