package de.ifcore.commons.euvatratesapi;

import java.util.Comparator;
import java.util.Objects;

public class StandardVatRate implements Comparable<StandardVatRate> {
    private static final Comparator<StandardVatRate> COMPARATOR = Comparator
            .comparing(StandardVatRate::getCountryCode)
            .thenComparing(StandardVatRate::getRate);

    private final String countryCode;
    private final Double rate;

    public StandardVatRate(String countryCode, Double rate) {
        this.countryCode = countryCode;
        this.rate = rate;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public Double getRate() {
        return rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StandardVatRate vatRate = (StandardVatRate) o;
        return countryCode.equals(vatRate.countryCode) && rate.equals(vatRate.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryCode, rate);
    }

    @Override
    public int compareTo(StandardVatRate o) {
        return COMPARATOR.compare(o, this);
    }
}
