package br.org.testing.spock.impl.service;

import br.org.testing.spock.api.domain.Person;
import br.org.testing.spock.api.domain.SocialServiceUsageRecord;
import br.org.testing.spock.api.repository.PersonRepository;
import br.org.testing.spock.api.repository.SocialServiceUsageRecordRepository;
import br.org.testing.spock.api.service.PersonService;
import br.org.testing.spock.api.service.social.ResultDTO;
import br.org.testing.spock.api.service.social.SocialSecurityNumberVerification;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import java.security.Principal;

/**
 * Created by c.heidemann on 05/04/2017.
 */
@Stateless
public class PersonServiceImpl implements PersonService {

    @Inject
    SocialSecurityNumberVerification socialSecurityNumberVerification;

    @Inject
    SocialServiceUsageRecordRepository socialServiceUsageRecordRepository;

    @Inject
    PersonRepository personRepository;

    @Override
    public void register(@Valid Person p) {
        final ResultDTO resultDTO = socialSecurityNumberVerification.verifySocialNumber(p.getSocialSecurityNumber());
        socialServiceUsageRecordRepository.save(resultDTO);
        personRepository.save(p);
    }
}
