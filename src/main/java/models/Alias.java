package models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Alias {
    public enum AllowedAliasTypes {IBAN, ALPHANUMERIC, PHONE_NUMBER}
    @Enumerated(EnumType.STRING)
    @Column(name = "alias_type")
    private Alias.AllowedAliasTypes type;

    @Column(name = "alias_value")
    private String value;

    public Alias(Alias.AllowedAliasTypes type, String value) {
        this.type = type;
        this.value = value;
    }
    public Alias() {}

    // Getters and setters, if necessary
}
