package com.isa.jjdzr.configuration;

import com.isa.jjdzr.db.Db;
import com.isa.jjdzr.db.InMemoryDbImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public Db api() {
        return new InMemoryDbImpl();
    }

}
