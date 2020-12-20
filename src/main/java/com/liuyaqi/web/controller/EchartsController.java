package com.liuyaqi.web.controller;

import com.liuyaqi.web.entity.NumberOfPersonAboutFactor;
import com.liuyaqi.web.entity.PersonNumber;
import com.liuyaqi.web.service.EchartsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping(value = "/f")
public class EchartsController {

    @Autowired
    private EchartsService echartsService;

    @GetMapping(value = "/index")
    public String index() {
        return "index";
    }

    @ResponseBody
    @GetMapping(value = "/getNumberOfPerson")
    @CachePut(value = "getNumberOfPerson")
    public List<PersonNumber> getNumberOfPerson() {
        return echartsService.listNumberOfTravelDate();
    }

    @ResponseBody
    @GetMapping(value = "/getNumberOfPersonAboutFactor")
    @CachePut(value = "getNumberOfPersonAboutFactor")
    public List<NumberOfPersonAboutFactor> getNumberOfPersonAboutFactor() {
        return echartsService.getNumberOfPersonAboutFactor();
    }
}
