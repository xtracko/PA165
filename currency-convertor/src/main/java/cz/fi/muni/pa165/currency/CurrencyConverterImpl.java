package cz.fi.muni.pa165.currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;


/**
 * This is base implementation of {@link CurrencyConverter}.
 *
 * @author petr.adamek@embedit.cz
 */
public class CurrencyConverterImpl implements CurrencyConverter {

    private final ExchangeRateTable exchangeRateTable;
    //private final Logger logger = LoggerFactory.getLogger(CurrencyConverterImpl.class);

    public CurrencyConverterImpl(ExchangeRateTable exchangeRateTable) {
        if (exchangeRateTable == null)
            throw new IllegalArgumentException("exchangeRateTable is null");
        this.exchangeRateTable = exchangeRateTable;
    }

    @Override
    public BigDecimal convert(Currency sourceCurrency, Currency targetCurrency, BigDecimal sourceAmount)  {
        if (sourceCurrency == null) {
            throw new IllegalArgumentException("sourceCurrency is null");
        }
        if (targetCurrency == null) {
            throw new IllegalArgumentException("targetCurrency is null");
        }
        if (sourceAmount == null) {
            throw new IllegalArgumentException("sourceAmount is null");
        }

        try {
            BigDecimal exchangeRate = exchangeRateTable.getExchangeRate(sourceCurrency, targetCurrency);
            if (exchangeRate == null) {
                throw new UnknownExchangeRateException("Exchange rate is not known");
            }

            return sourceAmount.multiply(exchangeRate).setScale(2, RoundingMode.HALF_EVEN);
        } catch(ExternalServiceFailureException ex) {
            throw new UnknownExchangeRateException("ExchangeRateTable internal error", ex);
        }
    }

}
