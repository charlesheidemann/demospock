package br.org.testing.spock.impl.repository;

import br.org.testing.spock.api.domain.SocialServiceUsageRecord;
import br.org.testing.spock.api.repository.SocialServiceUsageRecordRepository;
import br.org.testing.spock.api.service.social.ResultDTO;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.Principal;

/**
 * Created by c.heidemann on 05/04/2017.
 */
public class SocialServiceUsageRecordRepositoryImpl implements SocialServiceUsageRecordRepository {

    @PersistenceContext(unitName = "app-persistence-unit")
    private EntityManager em;

    @Override
    public void save(ResultDTO result) {
        SocialServiceUsageRecord usageRecord = new SocialServiceUsageRecord(result.toString());
        em.persist(usageRecord);
    }
}
