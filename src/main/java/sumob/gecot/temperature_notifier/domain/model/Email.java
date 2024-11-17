package sumob.gecot.temperature_notifier.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    @jakarta.validation.constraints.Email
    private String email;

    // Construtor padrão
    public Email() {
    }

    // Construtor com parâmetro
    public Email(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Email{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email email1)) return false;
        return id != null && id.equals(email1.id) && email.equals(email1.email);
    }

    @Override
    public int hashCode() {
        return 31 * (id != null ? id.hashCode() : 0) + (email != null ? email.hashCode() : 0);
    }

}