package com.epam.alexkorshunovych.diskanalyzer;

import com.epam.alexkorshunovych.diskanalyzer.util.DiskAnalyzer;
import com.epam.alexkorshunovych.diskanalyzer.util.Simple;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.BiFunction;

public class Main {
    public static void main(String[] args) {
//        DiskAnalyzer analyzer = new DiskAnalyzer();
//        analyzer.startAnalyze();
//        HashMap<Integer, String> map = new HashMap<>();
//        Map<Integer, String> synchronizedMap = Collections.synchronizedMap(map);
        Map<Simple, String> map = new ConcurrentHashMap<>();

        map.put(new Simple(1), "1");
        map.put(new Simple(2), "2");
        map.put(new Simple(3), "3");
        map.put(new Simple(4), "4");
        map.put(new Simple(5), "5");
        map.put(new Simple(5), "6");
        map.put(new Simple(7), "7");
        System.out.println(map.size());

        map.remove(new Simple(5));
        System.out.println(map.size());


//        concurrentHashMap.compute(1, (integer, s) -> integer + s + "*");
//        concurrentHashMap.computeIfPresent(1, (i, s)-> s+"!");
//        concurrentHashMap.merge(1, "5", (i, s)-> s);

        System.out.println(map);
        NavigableSet<Integer> integers = new TreeSet<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(10);
        integers.add(12);
        integers.add(14);
//        System.out.println(integers.lower(12));
        SortedSet<Integer> integers1 = integers.tailSet(3, false);
//        System.out.println(integers1);
        

    }
}
