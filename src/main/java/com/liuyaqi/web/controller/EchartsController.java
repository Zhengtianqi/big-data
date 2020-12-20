package com.liuyaqi.web.controller;

import com.liuyaqi.web.entity.TravelNumber;
import com.liuyaqi.web.service.EchartsService;
import org.mortbay.util.ajax.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping(value = "/e")
public class EchartsController {

    @Autowired
    private EchartsService echartsService;

    @GetMapping(value = "/index")
    @ResponseBody
    public String index(ModelMap model) {
        List<TravelNumber> list = echartsService.listNumberOfTravelDate();
        model.addAttribute("numberOfTravelDate", list);
        return JSON.toString(list);
    }
}
