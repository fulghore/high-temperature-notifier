package sumob.gecot.temperature_notifier.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;

    public User(String email) {
        this.email = email;
    }

    //Getters and Setters
    public Long getId() {

        return id;
    }

    public String getEmail() {

        return email;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public void setEmail(String email) {

        this.email = email;
    }
}
