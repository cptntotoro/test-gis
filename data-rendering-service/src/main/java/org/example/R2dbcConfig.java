package org.example;

import io.r2dbc.spi.ConnectionFactory;
import org.example.util.GeometryReadConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class R2dbcConfig extends AbstractR2dbcConfiguration {

    private final ConnectionFactory connectionFactory;

    public R2dbcConfig(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public ConnectionFactory connectionFactory() {
        return connectionFactory;
    }

    @Override
    @Bean
    public R2dbcCustomConversions r2dbcCustomConversions() {
        List<Converter<?, ?>> converterList = new ArrayList<>();
        converterList.add(new GeometryReadConverter());
        return new R2dbcCustomConversions(getStoreConversions(), converterList);
    }
}
