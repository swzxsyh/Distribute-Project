package com.distribute.facade.controller;


import com.distribute.application.InputService;
import com.distribute.domain.action.BatchResultVo;
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
    private InputService inputService;

    @PostMapping("/multi")
    public Result<?> multi(@RequestBody BatchInputDto batchInputDto) throws InterruptedException {
        BatchResultVo vo = inputService.multi(batchInputDto);
        return Result.success(vo);
    }

}
