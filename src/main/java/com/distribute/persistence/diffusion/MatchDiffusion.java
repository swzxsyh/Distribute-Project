package com.distribute.persistence.diffusion;

import com.pushtechnology.diffusion.client.features.Topics;
import com.pushtechnology.diffusion.client.session.Session;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class MatchDiffusion extends AbstractDiffusionClient {

    private final Session session;

    static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1, new BasicThreadFactory.Builder().namingPattern("match-retry-%d").daemon(true).build());

    private final static String TOPIC = "/match";

    @Autowired
    public MatchDiffusion(Session session) {
        this.session = session;
    }

    //    @PostConstruct
    public void subscribeTopic() {
        Topics topics = session.feature(Topics.class);

        doSubscribe(topics, TOPIC);

        topics.subscribe(TOPIC)
                .whenComplete((result, ex) -> {
                    if (Objects.nonNull(ex)) {
                        log.error("topic :{} error, ex:", TOPIC, ex);
                        // do
                        executor.scheduleAtFixedRate(() -> {
                            Session.State state = topics.getSession().getState();
                            if (state.isClosed()) {
                                log.info("reconnecting");
                                // do monitor
                                // do reconnect
                            }
                        }, 1, 5, TimeUnit.SECONDS);
                    } else {
                        log.info("Subscribe :{}", TOPIC);
                    }
                });

    }
}
