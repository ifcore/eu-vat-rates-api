package de.ifcore.commons.euvatratesapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class EuVatRatesApiApplicationTests {
    @Autowired
    private EuVatRatesClient euVatRatesClient;

    @Test
    void testGetStandardRates() {
        LocalDate date = LocalDate.of(2021, 1, 1);
        RetrieveVatRatesRespType response = euVatRatesClient.getVatRates(EuVatRatesClient.EU_COUNTRIES, date);
        Set<StandardVatRate> vatRates = response.vatRateResults.stream()
                .filter(vatRateResults -> RateTypeEnum.STANDARD.equals(vatRateResults.type)
                        && RateValueTypeEnum.DEFAULT.equals(vatRateResults.rate.type))
                .map(vatRateResults -> new StandardVatRate(vatRateResults.memberState, vatRateResults.rate.getValue()))
                .collect(Collectors.toCollection(TreeSet::new));

        // simplification: use the highest rate of a country in case there are multiple (like in Spain, 7% for Canary Islands)
        List<String> countries = new ArrayList<>();
        Set<StandardVatRate> uniqueVatRates = new TreeSet<>();
        for (StandardVatRate vatRate : vatRates) {
            if (!countries.contains(vatRate.getCountryCode())) {
                uniqueVatRates.add(vatRate);
                countries.add(vatRate.getCountryCode());
            }
        }

        assertEquals(uniqueVatRates.size(), EuVatRatesClient.EU_COUNTRIES.size());
        for (StandardVatRate vatRate : uniqueVatRates) {
            assertTrue(vatRate.getRate() >= 17);
        }
    }
}
