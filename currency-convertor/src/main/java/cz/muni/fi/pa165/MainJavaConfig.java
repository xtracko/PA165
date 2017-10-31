package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import cz.muni.fi.pa165.currency.CurrencyConvertorImpl;
import cz.muni.fi.pa165.currency.ExchangeRateTable;
import cz.muni.fi.pa165.currency.ExchangeRateTableImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.math.BigDecimal;
import java.util.Currency;

@Configuration
@EnableAspectJAutoProxy
public class MainJavaConfig {

    @Bean
    ExchangeRateTable exchangeRateTable() {
        return new ExchangeRateTableImpl();
    }

    @Bean
    CurrencyConvertor currencyConvertor() {
        return new CurrencyConvertorImpl(exchangeRateTable());
    }

    @Bean
    LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(MainJavaConfig.class);
        CurrencyConvertor convertor = context.getBean(CurrencyConvertor.class);

        Currency eur = Currency.getInstance("EUR");
        Currency czk = Currency.getInstance("CZK");

        BigDecimal result = convertor.convert(eur, czk, new BigDecimal(1));

        System.out.println(result.toString());
    }
}
