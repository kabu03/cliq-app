package validators;

import java.util.Arrays;
import java.util.List;

public class CurrencyValidator implements Validator<String> {

    @Override
    public void validate(String s) {
        List<String> allowedCurrencies = Arrays.asList("JOD", "USD", "EUR", "GBP", "HUF");
        if (!allowedCurrencies.contains(s)) {
            throw new IllegalArgumentException("Invalid currency: " + s + " The allowed currencies are: JOD, USD, EUR, GBP, HUF.");
        }
    }
}
