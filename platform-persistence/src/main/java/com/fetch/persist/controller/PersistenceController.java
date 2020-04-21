package com.fetch.persist.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fetch.persist.service.PersistenceService;
import com.fetch.persist.service.TypeLookup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/persist")
public class PersistenceController {

    @Autowired
    PersistenceService persistenceService;

    @GetMapping("findAll")
    @ResponseBody
    String findAll(@RequestParam String type) throws JsonProcessingException {
        return TypeLookup.writeJsonList(persistenceService.findAll(type));
    }

    @GetMapping("findAllByParentId")
    @ResponseBody
    String findAll(@RequestParam String type, @RequestParam String attr,
                   @RequestParam Long value) throws JsonProcessingException {
        return TypeLookup.writeJsonList(persistenceService.findAll(type, attr, value));
    }

    @GetMapping("findOne")
    @ResponseBody
    String findOne(@RequestParam String type, @RequestParam String attr,
                   @RequestParam String value) throws JsonProcessingException {
        String json = TypeLookup.writeJson(persistenceService.findOne(type, attr, value).get());
        return json;
    }
    @GetMapping("find")
    @ResponseBody
    String find(@RequestParam String id, @RequestParam String type) throws JsonProcessingException {
        return TypeLookup.writeJson(persistenceService.find(id, type));
    }

    @PostMapping("save")
    @ResponseBody
    Long save(@RequestBody String json, @RequestParam String type) throws JsonProcessingException {
        return persistenceService.save(type, json);
    }

    @PostMapping("saveList")
    @ResponseBody
    void saveList(@RequestBody String json, @RequestParam String type) throws JsonProcessingException {
        persistenceService.saveList(type, json);
    }

    @PutMapping("update")
    void update(@RequestBody String json, @RequestParam String id,
                @RequestParam String type)
            throws JsonProcessingException {
        persistenceService.update(id, type, json);
    }

    @DeleteMapping("delete")
    void delete(@RequestParam Long id,
                @RequestParam String type)
            throws JsonProcessingException {
        persistenceService.delete(id, type);
    }

    @DeleteMapping("deleteAll")
    void delete(@RequestBody List<Long> ids,
                @RequestParam String type)
            throws JsonProcessingException {
        persistenceService.deleteAll(ids, type);
    }
}