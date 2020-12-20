package com.liuyaqi.web.service;

import com.liuyaqi.common.Constants;
import com.liuyaqi.util.HbaseUtils;
import com.liuyaqi.web.entity.TravelNumber;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EchartsService {

    public List<TravelNumber> listNumberOfTravelDate() {
        List<TravelNumber> list = new ArrayList<>();

        HbaseUtils hbaseUtils2 = new HbaseUtils();
        hbaseUtils2.init();
        ResultScanner results = hbaseUtils2.getNoDealData("travel");
        for (Result result : results) {
            while (result.advance()) {
                Cell cell = result.current();
                String row = Bytes.toString(CellUtil.cloneRow(cell));
                String cf = Bytes.toString(CellUtil.cloneFamily(cell));
                String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
                String val = Bytes.toString(CellUtil.cloneValue(cell));
                System.out.println(row + "--->" + cf + "--->" + qualifier + "--->" + val);
                if ("number_people".equals(cf) && Constants.isInteger(val)) {
                    TravelNumber travelNumber = new TravelNumber();
                    travelNumber.setDate(row);
                    travelNumber.setNumber(Integer.parseInt(val));
                    list.add(travelNumber);
                }
            }
        }
        hbaseUtils2.close();
        return list;
    }

}
