package com.example.platform.controller;

import com.example.platform.model.Code;
import com.example.platform.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api")
public class ApiController {

    private final AppService service;

    @Autowired
    public ApiController(AppService service) {
        this.service = service;
    }

    @PostMapping(value = "/code/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addCode(@RequestBody Code codeToAdd) {
        return ResponseEntity.ok(String.format("{ \"id\" : \"%s\" }", service.saveCode(codeToAdd)));
    }

    @GetMapping(value = "/code/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Code> findCodeById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(service.findCodeById(id));
    }

    @GetMapping(value = "/code/latest", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Code>> getRecentlyUploadedCodes() {
        return ResponseEntity.ok(service.findRecent());
    }
}
