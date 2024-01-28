package com.distribute.domain;


import com.distribute.domain.action.Outcome;
import com.distribute.facade.dto.BatchInputDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class BatchOddsService {

    public void doBatchOdds() {
        log.info("doBatchOdds");
    }

    public Outcome batch(BatchInputDto.Odds odds) {
        return new Outcome();
    }
}
