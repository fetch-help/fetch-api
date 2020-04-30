package com.fetch.merchant.controller;

import com.fetch.merchant.model.KV;
import com.fetch.merchant.model.PostCode;
import com.fetch.merchant.service.PersistClientAdapter;
import com.fetch.merchant.util.Haversine;
import com.fetch.persist.model.PostalCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/merchant")
public class MerchantPostcodeController extends MerchantAbstractController{

    Logger log = LoggerFactory.getLogger(MerchantPostcodeController.class);

    @Autowired
    PersistClientAdapter persistClient;

    private static final BigInteger INIT64 = new BigInteger("cbf29ce484222325", 16);
    private static final BigInteger PRIME64 = new BigInteger("100000001b3", 16);
    private static final BigInteger MOD64 = new BigInteger("2").pow(64);


    final CopyOnWriteArrayList<PostCode<String>> merchantPostCodesList = new CopyOnWriteArrayList<>();

    final Comparator<Map.Entry<String, Double>> valueComparator = (Map.Entry<String, Double> e1, Map.Entry<String, Double> e2) -> {
        Double v1 = e1.getValue();
        Double v2 = e2.getValue();
        return v1.compareTo(v2);
    };


    @PostMapping("/postCodes")
    @ResponseBody
    public KV<String, String> post(@RequestBody List<PostCode<String>> postCodes) {
        Set<String> existingPostCodes = merchantPostCodesList.stream().map(PostCode::getPostCode).collect(Collectors.toSet());
        List<PostCode<String>> postCodesToAdd = postCodes.stream().filter(p -> !existingPostCodes.contains(p.getPostCode())).collect(Collectors.toList());
        int before = merchantPostCodesList.size();
        merchantPostCodesList.addAll(postCodesToAdd);
        int after = merchantPostCodesList.size();
        return new KV<String, String>(String.join("=","before", String.valueOf(before)), String.join("=",
                "after", String.valueOf(after)));
    }


    @GetMapping("/postCodes")
    @ResponseBody
    public List<PostCode<String>> get() {
        return new ArrayList<PostCode<String>>(merchantPostCodesList);
    }

    @GetMapping("/findMerchantPostCodesByDistance")
    @ResponseBody
    public Map<String, Double> findBy(@NotNull @RequestParam String postalCode, @RequestParam String time) {

        PostalCode pCode = persistClient.findByPostCode(postalCode);
        if(pCode ==null){
            throw new IllegalArgumentException(postalCode);
        }
        final PostCode<String> postCode = new PostCode<>(pCode.getCode(), pCode.getLat(), pCode.getLon());
        Map<String, Double> postcodeAndDistance = new HashMap<>();
        final Haversine h1 = new Haversine();
        merchantPostCodesList.forEach(p -> {
            postcodeAndDistance.put(p.getPostCode(), h1.distance(p, postCode));
        });
        //get all the entries and convert Set to List
        List<Map.Entry<String, Double>> listOfEntries = new ArrayList<Map.Entry<String, Double>>(postcodeAndDistance.entrySet());
        // sorting list of entries
        Collections.sort(listOfEntries, valueComparator);
        Map<String, Double> sortedByDistance = new LinkedHashMap<String, Double>(listOfEntries.size());
        // copying entries
        for(Map.Entry<String, Double> entry : listOfEntries){
            sortedByDistance.put(entry.getKey(), entry.getValue());
        }
        return sortedByDistance;
    }

    @DeleteMapping("/clearMerchantPostCodes")
    public void clear() {
        merchantPostCodesList.clear();
    }


    private PostCode<Long> getPostCode(PostCode<String> p) {
        return new PostCode<Long>(fnv164(p.getPostCode()), p.getLat(), p.getLon());
    }

    private long fnv164(String key) {
        byte[] data = key.getBytes();
        BigInteger has = INIT64;
        for (byte b : data) {
            has = has.multiply(PRIME64).mod(MOD64);
            has = has.xor(BigInteger.valueOf((int) b & 0xff));
        }

        return has.longValueExact();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegal(IllegalArgumentException exc) {
        log.error("Error handing request", exc);
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleIllegal(Exception exc) {
        log.error("Error handing request", exc);
        return ResponseEntity.unprocessableEntity().build();
    }
}
