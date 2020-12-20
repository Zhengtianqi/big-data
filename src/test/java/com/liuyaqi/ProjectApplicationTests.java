package com.liuyaqi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.TreeMap;

@SpringBootTest
class ProjectApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void strTest() {
        String line = "33,2016-09-14 14:47:16-07:00,53236,2,0,0,67.67,0,1,9,14 ";
        String[] array = line.split(",");
        System.out.println(array[1].replaceAll("-07:00", ""));

    }

    @Test
    void dateTest() {
        Map<String, Integer> treeMap = new TreeMap<>();
        for (int i = 1; i < 13; i++) {
            String key = i < 10 ? "0" + i : "" + i;
            treeMap.put("2015-" + key, 0);
            treeMap.put("2016-" + key, 0);
            treeMap.put("2017-" + key, 0);
        }
        System.out.println(treeMap);
    }
}
