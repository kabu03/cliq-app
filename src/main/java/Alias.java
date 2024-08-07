public record Alias(Alias.AllowedAliasTypes type, String value) {
    public enum AllowedAliasTypes {IBAN, ALPHANUMERIC, PHONE_NUMBER}
}
