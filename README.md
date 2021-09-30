# SETUP

this package is currently not published to maven central. to use it, you have to clone this repo and run

`./mvnw install`

Then you can add the dependency to your project:

```xml
<dependency>
    <groupId>de.ifcore.commons</groupId>
    <artifactId>eu-vat-rates-api</artifactId>
    <version>1.0.0</version>
</dependency>
```

# Sample usage

```java
@Service
class YourService {
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
```

see also the official [TEDB - "Taxes in Europe" SOAP web service documentation](https://ec.europa.eu/taxation_customs/system/files/2021-06/soap_webservice_documentation.pdf) 
