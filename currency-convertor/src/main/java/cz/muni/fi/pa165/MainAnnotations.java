package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import cz.muni.fi.pa165.currency.CurrencyConvertorImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Currency;

@Configuration
@ComponentScan
public class MainAnnotations {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(MainAnnotations.class);

        CurrencyConvertor convertor = context.getBean(CurrencyConvertor.class);

        Currency eur = Currency.getInstance("EUR");
        Currency czk = Currency.getInstance("CZK");

        BigDecimal result = convertor.convert(eur, czk, new BigDecimal(1));

        System.out.println(result.toString());
    }
}
