package com.fetch.web.controller;

import com.fetch.web.model.DataTable;
import com.fetch.web.service.PersistClientAdapter;
import com.fetch.persist.model.Merchant;
import com.fetch.persist.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/component")
public class WebComponentController {

    Logger log = LoggerFactory.getLogger(WebComponentController.class);

    @Autowired
    PersistClientAdapter persistClient;

    @GetMapping
    public DataTable get(@RequestParam("merchantId") long merchantId,
                         @RequestParam("draw") int draw,
                         @RequestParam("start") int start,
                         @RequestParam("length") int length,
                         @RequestParam(value = "search[value]") String search) {

        //TODO check merchant
        //TODO search
        //TODO filter
        List<Product> l = persistClient.getProducts(merchantId);
        return getDataTable(l, draw, l.size(), l.size(), start, length);
    }

    private DataTable getDataTable(List<Product> products, int draw, long total, long recsFiltered,
                                   int start, int length) {


        DataTable dataTable = new DataTable();
        dataTable.setDraw(draw);
        dataTable.setRecordsTotal((int) total);
        dataTable.setRecordsFiltered((int) recsFiltered);
        int idx = 0;
        for (Product p : products) {
            if (idx >= start && idx < (start + length)) {
                log.debug("idx "+idx+" "+ p.getId());

                String[] rec = new String[4];
                rec[0] = p.getId().toString();
                rec[1] = p.getName();
                rec[2] = p.getDescription();
                rec[3] = String.valueOf(p.getPrice());
                dataTable.addData(rec);
            }
            ++idx;
            if (idx >= (start + length)) {
                break;
            }
        }
        return dataTable;
    }
}