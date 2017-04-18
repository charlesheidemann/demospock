package br.org.testing.spock.api.repository;

import java.io.Serializable;

import javax.validation.Valid;

import br.org.testing.spock.api.domain.Person;

/**
 * Created by c.heidemann on 05/04/2017.
 */
public interface PersonRepository extends Serializable {

    Long save(@Valid Person p);

}
