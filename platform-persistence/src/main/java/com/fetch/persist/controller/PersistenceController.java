package com.fetch.persist.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fetch.persist.service.PersistenceService;
import com.fetch.persist.service.TypeLookup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/persist")
public class PersistenceController {

    @Autowired
    PersistenceService persistenceService;

    @GetMapping("findAll")
    @ResponseBody
    String findAll(@RequestParam String type)throws JsonProcessingException {
        return TypeLookup.writeJsonList(persistenceService.findAll(type));
    }

    @GetMapping("find")
    @ResponseBody
    String find(@RequestParam String id, @RequestParam String type)throws JsonProcessingException {
        return TypeLookup.writeJson(persistenceService.find(id, type));
    }

    @PostMapping("save")
    @ResponseBody
    Long save(@RequestBody String json, @RequestParam String type)throws JsonProcessingException {
        return persistenceService.save(type, json);
    }

    @PutMapping("update")
    void update(@RequestBody String json, @RequestParam String id, @RequestParam String type)throws JsonProcessingException {
        persistenceService.update(id, type, json);
    }
}