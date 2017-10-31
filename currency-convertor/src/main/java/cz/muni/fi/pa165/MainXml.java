package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainXml {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        CurrencyConvertor convertor = (CurrencyConvertor) context.getBean("currencyConvertor");

        Currency eur = Currency.getInstance("EUR");
        Currency czk = Currency.getInstance("CZK");

        BigDecimal result = convertor.convert(eur, czk, new BigDecimal(1));

        System.out.println(result.toString());
    }

}
