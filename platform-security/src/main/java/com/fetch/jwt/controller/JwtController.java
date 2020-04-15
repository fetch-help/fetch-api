package com.fetch.jwt.controller;

import com.fetch.jwt.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/token")
public class JwtController {

    @NonNull
    @Autowired
    JwtTokenService tokenService;

    @PostMapping("create")
    @ResponseBody
    public String createToken(
            @NotNull @RequestParam("username") final String username) {
        final Map<String, String> map = new HashMap<>();
        map.put("username", username);
        return tokenService.expiring(map);
    }

    @PostMapping("validate")
    @ResponseBody
    public Map<String, String> validateToken(
            @NotNull @RequestParam("token") final String token) {
        return tokenService.trusted(token);
    }
}
