package com.chag.ptr.cocoa.json.parser.controller;

import com.chag.ptr.cocoa.json.parser.service.ParseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final ParseService parseService;

    @GetMapping("/")
    public ModelAndView show(ModelAndView model) {
        model.addObject("result", "{}");
        model.setViewName("index");
        return model;
    }

    @PostMapping("/")
    public ModelAndView parse(ModelAndView model, @RequestParam String json) {
        String result;
        try {
            result = parseService.parse(json);
        } catch (Exception e) {
            result = "{\"isError\":true}";
        }
        model.addObject("json", json);
        model.addObject("result", result);
        model.setViewName("index");
        return model;
    }
}
