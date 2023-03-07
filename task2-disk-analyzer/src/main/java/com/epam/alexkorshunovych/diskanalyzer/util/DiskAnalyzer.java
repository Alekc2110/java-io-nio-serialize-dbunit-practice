package com.epam.alexkorshunovych.diskanalyzer.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class DiskAnalyzer {

    public void startAnalyze() {
        String path;
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println("input path or 'exit': ");
                path = sc.nextLine();

                if (path.equals("exit")) break;

                System.out.println("input number of function: ");
                int num = Integer.parseInt(sc.nextLine());
                analyzeDisk(path, num);
            }

        } catch (NumberFormatException ex) {
            System.out.println("wrong function type,try again!");
        }
    }

    private void analyzeDisk(String path, int numFunction) {
        switch (numFunction) {
            case 1 -> searchName(path);

            case 2 -> getMaxFileBySize(path);

            case 3 -> getAverageSize(path);

            case 4 -> getCountFilesAndFoldersByLetter(path);

            default -> System.out.println("wrong function number");
        }

    }

    private void getCountFilesAndFoldersByLetter(String path) {
        Map<Character, Long> fileCountByLetter = new HashMap<>();
        Map<Character, Long> folderCountByLetter = new HashMap<>();
        try (Stream<Path> filePaths = Files.walk(Paths.get(path));
             Stream<Path> folderPaths = Files.walk(Paths.get(path))
        ) {
            fileCountByLetter = filePaths
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(fileName -> fileName.toString().charAt(0))
                    .collect(Collectors.groupingBy(
                            Function.identity(),
                            Collectors.counting()));

            folderCountByLetter = folderPaths
                    .filter(Files::isDirectory)
                    .map(Path::getFileName)
                    .filter(Objects::nonNull)
                    .map(fileName -> fileName.toString().charAt(0))
                    .collect(Collectors.groupingBy(
                            Function.identity(),
                            Collectors.counting()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("File count by letter:");
        fileCountByLetter.forEach((key1, value1) -> System.out.println(key1 + ": " + value1 + " files"));

        System.out.println("Folder count by letter:");
        folderCountByLetter.forEach((key, value) -> System.out.println(key + ": " + value + " folders"));
    }

    private void getAverageSize(String path) {
        try (Stream<Path> paths = Files.walk(Paths.get(path), 2)) {
            double average = paths.filter(Files::isRegularFile)
                    .mapToLong(p -> p.toFile().length())
                    .average()
                    .orElse(0);
            System.out.println(average);
        } catch (IOException e) {
            System.out.println("wrong path,try again!");
        }
    }

    private void getMaxFileBySize(String path) {
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.filter(Files::isRegularFile)
                    .sorted((o1, o2) -> (int) (o2.toFile().length() - o1.toFile().length()))
                    .limit(5)
                    .map(p -> p.getFileName().toString())
                    .forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("wrong path,try again!");
        }
    }

    private void searchName(String path) {
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.filter(Files::isRegularFile)
                    .max(Comparator.comparing(p -> (int) p.toFile().getName().chars().filter(c -> c == 's').count()))
                    .ifPresent(System.out::println);

        } catch (IOException e) {
            System.out.println("wrong path,try again!");
        }
    }

}
