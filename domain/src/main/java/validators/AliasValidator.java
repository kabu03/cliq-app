package validators;

import models.Alias;

public class AliasValidator implements Validator<Alias> {

    public void validate(Alias alias) {
        if (alias.getType() == Alias.AllowedAliasTypes.PHONE_NUMBER) {
            assert alias.getValue() != null;
            if (alias.getValue().length() != 10) {
                throw new IllegalArgumentException("Phone number must be 10 digits." +
                        "The phone number you inserted is " + alias.getValue().length() + " digits long.");
            }
            if (alias.getValue().charAt(0) != 0) {
                throw new IllegalArgumentException("Phone number must start with 0, not " + alias.getValue().charAt(0));
            }
        }
        if (alias.getType() == Alias.AllowedAliasTypes.IBAN) {
            assert alias.getValue() != null;
            if (alias.getValue().length() != 30) {
                throw new IllegalArgumentException("IBAN must be 30 characters long." +
                        "The IBAN you inserted is " + alias.getValue().length() + " characters long.");
            }
        }
    }
}
