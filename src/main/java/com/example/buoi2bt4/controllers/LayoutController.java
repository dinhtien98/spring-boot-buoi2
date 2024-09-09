package com.example.buoi2bt4.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/layout")
public class LayoutController {
    @Operation(summary = "get layout", description = "List product of layout") // mô tả API
    @GetMapping("/layout1")
    public String layout1() {
        return "layout1";
    }
    @GetMapping("/layout2")
    public String layout2() {
        return "layout2";
    }
}
