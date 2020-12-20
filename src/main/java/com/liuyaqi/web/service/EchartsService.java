package com.liuyaqi.web.service;

import com.liuyaqi.common.Constants;
import com.liuyaqi.util.HbaseUtils;
import com.liuyaqi.web.entity.NumberOfPersonAboutFactor;
import com.liuyaqi.web.entity.PersonNumber;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EchartsService {

    public List<PersonNumber> listNumberOfTravelDate() {
        List<PersonNumber> list = new ArrayList<>();

        HbaseUtils hbaseUtils = new HbaseUtils();
        hbaseUtils.init();
        ResultScanner results = hbaseUtils.getNoDealData("travel");
        for (Result result : results) {
            while (result.advance()) {
                Cell cell = result.current();
                String row = Bytes.toString(CellUtil.cloneRow(cell));
                String cf = Bytes.toString(CellUtil.cloneFamily(cell));
                String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
                String val = Bytes.toString(CellUtil.cloneValue(cell));
//                System.out.println(row + "--->" + cf + "--->" + qualifier + "--->" + val);
                if ("number_people".equals(cf) && Constants.isInteger(val)) {
                    PersonNumber travelNumber = new PersonNumber();
                    travelNumber.setDate(row);
                    travelNumber.setNumber(Integer.parseInt(val));
                    list.add(travelNumber);
                }
            }
        }
        hbaseUtils.close();
        Map<String, Integer> map = new TreeMap<>();
        for (PersonNumber personNumber : list) {
            String[] key = personNumber.getDate().split(" ")[0].split("-");
            int value = personNumber.getNumber();
            String day = key[0] + "-" + key[1] + "-" + key[2];
            if (map.containsKey(day)) {
                if (map.get(day) < value) {
                    map.put(day, value);
                }
            } else {
                map.put(day, value);
            }
        }
        Map<String, Integer> treeMap = new TreeMap<>();
        for (int i = 1; i < 13; i++) {
            String key = i < 10 ? "0" + i : "" + i;
            treeMap.put("2015-" + key, 0);
            treeMap.put("2016-" + key, 0);
            treeMap.put("2017-" + key, 0);
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey().substring(0, 7);
            int value = entry.getValue();
            if (treeMap.containsKey(key)) {
                treeMap.put(key, treeMap.get(key) + value);
            } else {
                treeMap.put(key, value);
            }
        }
        List<PersonNumber> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : treeMap.entrySet()) {
            PersonNumber travelNumber = new PersonNumber();
            travelNumber.setDate(entry.getKey());
            travelNumber.setNumber(entry.getValue());
            result.add(travelNumber);
        }
        return result;
    }

    public List<NumberOfPersonAboutFactor> getNumberOfPersonAboutFactor() {

        HbaseUtils hbaseUtils = new HbaseUtils();
        hbaseUtils.init();
        ResultScanner results = hbaseUtils.getNoDealData("travel");
        Map<String, NumberOfPersonAboutFactor> initMap = new TreeMap<>();
        for (Result result : results) {
            while (result.advance()) {
                Cell cell = result.current();
                String row = Bytes.toString(CellUtil.cloneRow(cell));
                String cf = Bytes.toString(CellUtil.cloneFamily(cell));
                String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
                String val = Bytes.toString(CellUtil.cloneValue(cell));
//                System.out.println(row + "--->" + cf + "--->" + qualifier + "--->" + val);
                NumberOfPersonAboutFactor numberOfPersonAboutFactor;
                if (!initMap.containsKey(row)) {
                    numberOfPersonAboutFactor = new NumberOfPersonAboutFactor();
                } else {
                    numberOfPersonAboutFactor = initMap.get(row);
                }
                numberOfPersonAboutFactor.setDate(row);
                if ("number_people".equals(cf) && Constants.isInteger(val)) {
                    numberOfPersonAboutFactor.setNumberPeople(Integer.parseInt(val));
                }
                if ("factor".equals(cf) && "day_of_week".equals(qualifier)) {
                    numberOfPersonAboutFactor.setDayOfWeek(Integer.parseInt(val));
                }
                if ("factor".equals(cf) && "is_weekend".equals(qualifier)) {
                    numberOfPersonAboutFactor.setIsWeekend(Integer.parseInt(val));
                }
                if ("factor".equals(cf) && "is_holiday".equals(qualifier)) {
                    numberOfPersonAboutFactor.setIsHoliday(Integer.parseInt(val));
                }
                if ("factor".equals(cf) && "temperature".equals(qualifier)) {
                    numberOfPersonAboutFactor.setTemperature(Float.parseFloat(val));
                }
                if ("factor".equals(cf) && "is_start_of_semester".equals(qualifier)) {
                    numberOfPersonAboutFactor.setIsStartOfSemester(Integer.parseInt(val));
                }
                if ("factor".equals(cf) && "is_during_semester".equals(qualifier)) {
                    numberOfPersonAboutFactor.setIsDuringSemester(Integer.parseInt(val));
                }
                initMap.put(row, numberOfPersonAboutFactor);
            }
        }
        hbaseUtils.close();

        Map<String, NumberOfPersonAboutFactor> map = new TreeMap<>();
        for (Map.Entry<String, NumberOfPersonAboutFactor> me : initMap.entrySet()) {
            String[] key = me.getValue().getDate().split(" ")[0].split("-");
            int value = me.getValue().getNumberPeople();
            String day = key[0] + "-" + key[1] + "-" + key[2];
            if (map.containsKey(day)) {
                if (map.get(day).getNumberPeople() < value) {
                    map.put(day, me.getValue());
                }
            } else {
                map.put(day, me.getValue());
            }
        }
        List<NumberOfPersonAboutFactor> result = new ArrayList<>();
        for (Map.Entry<String, NumberOfPersonAboutFactor> entry : map.entrySet()) {
            result.add(entry.getValue());
        }
        return result;
    }
}
