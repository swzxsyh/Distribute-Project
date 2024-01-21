package com.distribute.facade.controller;


import com.distribute.application.DistributeService;
import com.distribute.facade.dto.BatchInputDto;
import com.distribute.persistence.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/distributed")
public class InputController {

    @Autowired
    private DistributeService distributeService;

    @PostMapping("/multi")
    public Result<?> multi(@RequestBody BatchInputDto batchInputDto) throws InterruptedException {
        Boolean success = distributeService.multi(batchInputDto);
        return success ? Result.success() : Result.error("Batch失败");
    }

}
