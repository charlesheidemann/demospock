package br.org.testing.spock.api.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by c.heidemann on 05/04/2017.
 */

@Entity
@Table(name = "PERSON")
public class Person implements Serializable {

    @Id
    @SequenceGenerator(name = "PERSON_ID_GENERATOR", sequenceName = "SE_PERSON")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSON_ID_GENERATOR")
    @Column(name = "ID_PERSON")
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String socialSecurityNumber;

    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }
}
