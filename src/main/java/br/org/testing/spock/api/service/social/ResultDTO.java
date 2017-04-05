package br.org.testing.spock.api.service.social;

import java.io.Serializable;

/**
 * Created by c.heidemann on 05/04/2017.
 */
public class ResultDTO implements Serializable {

    private String socialSecurityNumber;

    private String lastName;

    private String allocation;

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAllocation() {
        return allocation;
    }

    public void setAllocation(String allocation) {
        this.allocation = allocation;
    }

    @Override
    public String toString() {
        return "ResultDTO{" +
                "socialSecurityNumber='" + socialSecurityNumber + '\'' +
                ", lastName='" + lastName + '\'' +
                ", allocation='" + allocation + '\'' +
                '}';
    }
}
