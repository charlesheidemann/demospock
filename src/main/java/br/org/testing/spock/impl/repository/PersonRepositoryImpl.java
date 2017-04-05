package br.org.testing.spock.impl.repository;

import br.org.testing.spock.api.domain.Person;
import br.org.testing.spock.api.repository.PersonRepository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

/**
 * Created by c.heidemann on 05/04/2017.
 */

@Stateless
public class PersonRepositoryImpl implements PersonRepository {

    @PersistenceContext(unitName = "app-persistence-unit")
    private EntityManager em;

    @Override
    public Long save(@Valid Person p) {
        em.persist(p);
        return p.getId();
    }

}
