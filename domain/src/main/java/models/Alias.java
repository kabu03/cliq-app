package models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class Alias {
    @Enumerated(EnumType.STRING)
    @Column(name = "alias_type")
    private Alias.AllowedAliasTypes type;
    @Column(name = "alias_value")
    private String value;

    public Alias(Alias.AllowedAliasTypes type, String value) {
        this.type = type;
        this.value = value;
    }

    public Alias() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alias alias = (Alias) o;
        return type == alias.type && value.equals(alias.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }

    public enum AllowedAliasTypes {IBAN, ALPHANUMERIC, PHONE_NUMBER}
}
