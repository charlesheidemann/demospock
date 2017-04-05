package br.org.testing.spock.api.repository;

import br.org.testing.spock.api.domain.Person;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * Created by c.heidemann on 05/04/2017.
 */
public interface PersonRepository extends Serializable {

    Long save(Person p);

}
