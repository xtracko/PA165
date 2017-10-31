package cz.muni.fi.pa165.currency;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;

@Service
public class ExchangeRateTableImpl implements ExchangeRateTable {
    private static final Currency eur = Currency.getInstance("EUR");
    private static final Currency czk = Currency.getInstance("CZK");

    @Override
    public BigDecimal getExchangeRate(Currency sourceCurrency, Currency targetCurrency) throws ExternalServiceFailureException {
        if (sourceCurrency == eur && targetCurrency == czk)
            return new BigDecimal(27);
        return null;
    }
}
