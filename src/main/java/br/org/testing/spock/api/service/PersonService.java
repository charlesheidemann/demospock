package br.org.testing.spock.api.service;

import br.org.testing.spock.api.domain.Person;

import java.io.Serializable;

import javax.validation.Valid;

/**
 * Created by c.heidemann on 05/04/2017.
 */
public interface PersonService extends Serializable {

    void register(@Valid Person p);

}
