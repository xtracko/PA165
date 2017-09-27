package cz.fi.muni.pa165.currency;

import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Currency;

public class CurrencyConverterImplTest {

    private static Currency CZK = Currency.getInstance("CZK");
    private static Currency EUR = Currency.getInstance("EUR");

    private CurrencyConverter converter;

    @Mock
    private ExchangeRateTable exchangeRate;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setup() {
        converter = new CurrencyConverterImpl(exchangeRate);
    }

    @Test
    public void testConvert() throws ExternalServiceFailureException {
        // Don't forget to test border values and proper rounding.
        when(exchangeRate.getExchangeRate(EUR, CZK))
                .thenReturn(new BigDecimal("10"));

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(converter.convert(EUR, CZK, new BigDecimal("1.0005")))
                .isEqualTo(new BigDecimal("10.00"));
        softly.assertThat(converter.convert(EUR, CZK, new BigDecimal("1.0006")))
                .isEqualTo(new BigDecimal("10.01"));
        softly.assertThat(converter.convert(EUR, CZK, new BigDecimal("1.0004")))
                .isEqualTo(new BigDecimal("10.00"));
        softly.assertThat(converter.convert(EUR, CZK, new BigDecimal("1.0015")))
                .isEqualTo(new BigDecimal("10.02"));
        softly.assertAll();
    }

    @Test
    public void testConvertWithNullSourceCurrency() {
        assertThatThrownBy(() -> converter.convert(null, CZK, BigDecimal.ONE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testConvertWithNullTargetCurrency() {
        assertThatThrownBy(() -> converter.convert(EUR, null, BigDecimal.ONE))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testConvertWithNullSourceAmount() {
        assertThatThrownBy(() -> converter.convert(EUR, CZK, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testConvertWithUnknownCurrency() throws ExternalServiceFailureException {
        doThrow(ExternalServiceFailureException.class)
                .when(exchangeRate).getExchangeRate(EUR, CZK);
        assertThatThrownBy(() -> converter.convert(EUR, CZK, BigDecimal.ONE))
                .isInstanceOf(UnknownExchangeRateException.class);
    }

    @Test
    public void testConvertWithExternalServiceFailure() throws ExternalServiceFailureException {
        doThrow(ExternalServiceFailureException.class)
                .when(exchangeRate).getExchangeRate(EUR, CZK);
        assertThatThrownBy(() -> converter.convert(EUR, CZK, BigDecimal.ONE))
                .isInstanceOf(UnknownExchangeRateException.class);
    }

}
