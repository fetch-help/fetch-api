package com.fetch.persist.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fetch.persist.service.PersistenceService;
import com.fetch.persist.service.TypeLookup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersistenceController {

    @Autowired
    PersistenceService persistenceService;

    @GetMapping("api/persist/findAll")
    @ResponseBody
    String findAll(@RequestParam String type)throws JsonProcessingException {
        return TypeLookup.writeJsonList(persistenceService.findAll(type));
    }

    @GetMapping("api/persist/find")
    @ResponseBody
    String find(@RequestParam String id, @RequestParam String type)throws JsonProcessingException {
        return TypeLookup.writeJson(persistenceService.find(id, type));
    }

    @PostMapping("api/persist/save")
    @ResponseBody
    Long save(@RequestBody String json, @RequestParam String type)throws JsonProcessingException {
        return persistenceService.save(type, json);
    }

    @PutMapping("api/persist/update")
    void update(@RequestBody String json, @RequestParam String id, @RequestParam String type)throws JsonProcessingException {
        persistenceService.update(id, type, json);
    }
}