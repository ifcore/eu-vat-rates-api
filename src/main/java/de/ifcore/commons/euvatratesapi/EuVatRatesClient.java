package de.ifcore.commons.euvatratesapi;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.SoapMessage;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.util.List;

public class EuVatRatesClient extends WebServiceGatewaySupport {
    public RetrieveVatRatesRespType getVatRates(List<String> isoCodes, LocalDate situationOn) {
        try {
            ObjectFactory factory = new ObjectFactory();
            RetrieveVatRatesReqType reqType = factory.createRetrieveVatRatesReqType();
            reqType.setMemberStates(toStateList(isoCodes));

            XMLGregorianCalendar xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar();
            xmlCal.setYear(situationOn.getYear());
            xmlCal.setMonth(situationOn.getMonthValue());
            xmlCal.setDay(situationOn.getDayOfMonth());
            reqType.setSituationOn(xmlCal);

            JAXBElement<RetrieveVatRatesReqType> request = factory.createRetrieveVatRatesReqMsg(reqType);

            @SuppressWarnings("unchecked")
            JAXBElement<RetrieveVatRatesRespType> response = (JAXBElement<RetrieveVatRatesRespType>) getWebServiceTemplate().marshalSendAndReceive(request, message -> ((SoapMessage) message).setSoapAction("urn:ec.europa.eu:taxud:tedb:services:v1:VatRetrievalService/RetrieveVatRates"));

            return response.getValue();
        } catch (DatatypeConfigurationException e) {
            throw new IllegalStateException(e);
        }
    }

    private RequestMemberStateType toStateList(List<String> isoCodes) {
        RequestMemberStateType result = new RequestMemberStateType();
        result.getIsoCode().addAll(isoCodes);
        return result;
    }
}
