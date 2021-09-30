package de.ifcore.commons.euvatratesapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class EuVatRatesApiConfiguration {
    @Bean
    public EuVatRatesClient euVatRatesClient() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("de.ifcore.commons.euvatratesapi");
        EuVatRatesClient client = new EuVatRatesClient();
        client.setDefaultUri("https://ec.europa.eu/taxation_customs/tedb/ws/");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
