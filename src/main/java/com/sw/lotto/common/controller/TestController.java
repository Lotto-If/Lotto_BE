package com.sw.lotto.common.controller;

import com.sw.lotto.es.car.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController extends CommonController {

    private final CarService carService;

    @PostMapping("/car")
    public void carTest() {
        carService.test();
    }
}
