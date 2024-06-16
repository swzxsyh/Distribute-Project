package com.distribute.persistence.config;

import com.pushtechnology.diffusion.client.Diffusion;
import com.pushtechnology.diffusion.client.session.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class DiffusionClientConfig {

    @Bean
    public Session diffusionSession() {
        Session session = Diffusion.sessions()
                .principal("")
                .password("")
                // url
                .open("");

        session.addListener(((session1, state, state1) -> log.info("Session state from :{} to :{}", state, state)));
        session.addListener(((session1, reason, thr) -> log.error("Session error:{}, e:{}", reason, thr)));

        return session;
    }
}
