package com.example.platform.controller;

import com.example.platform.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping()
public class WebController {

    private final AppService service;

    @Autowired
    public WebController(AppService service) {
        this.service = service;
    }

    @GetMapping(value = "/code/new", produces = MediaType.TEXT_HTML_VALUE)
    public String getHtmlFormToAddCode(Model model) {
        String code = "// write your code here";
        model.addAttribute("code", code);
        return "text_area_page";
    }

    @GetMapping(value = "/code/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public String getHtmlCodeById(@PathVariable("id") UUID id, Model model) {
        model.addAttribute("code", service.findCodeById(id));
        return "show";
    }

    @GetMapping(value = "/code/latest", produces = MediaType.TEXT_HTML_VALUE)
    public String getRecentlyUploadedHtmlCodes(Model model) {
        model.addAttribute("codes", service.findRecent());
        return "codeList";
    }
}
