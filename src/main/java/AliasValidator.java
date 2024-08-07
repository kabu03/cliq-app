public class AliasValidator implements Validator<Alias> {

    public void validate(Alias alias) {
        if (alias.type() == Alias.AllowedAliasTypes.PHONE_NUMBER) {
            assert alias.value() != null;
            if (alias.value().length() != 10) {
                throw new IllegalArgumentException("Phone number must be 10 digits." +
                        "The phone number you inserted is " + alias.value().length() + " digits long.");
            }
            if (alias.value().charAt(0) != 0) {
                throw new IllegalArgumentException("Phone number must start with 0, not " + alias.value().charAt(0));
            }
        }
        if (alias.type() == Alias.AllowedAliasTypes.IBAN) {
            assert alias.value() != null;
            if (alias.value().length() != 30) {
                throw new IllegalArgumentException("IBAN must be 30 characters long." +
                        "The IBAN you inserted is " + alias.value().length() + " characters long.");
            }
        }
    }
}
