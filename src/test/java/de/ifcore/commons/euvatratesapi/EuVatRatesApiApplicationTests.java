package de.ifcore.commons.euvatratesapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class EuVatRatesApiApplicationTests {
    @Autowired
    private EuVatRatesClient euVatRatesClient;

    @Test
    void testRequest() {
        LocalDate date = LocalDate.of(2021, 1, 1);
        List<String> isoCodes = Arrays.asList("DE", "AT");
        RetrieveVatRatesRespType vatRates = euVatRatesClient.getVatRates(isoCodes, date);
        System.out.println(vatRates);
    }
}
