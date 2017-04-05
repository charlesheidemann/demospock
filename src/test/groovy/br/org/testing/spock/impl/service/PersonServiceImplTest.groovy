package br.org.testing.spock.impl.service

import br.org.testing.spock.api.domain.Person
import br.org.testing.spock.api.repository.PersonRepository
import br.org.testing.spock.api.repository.SocialServiceUsageRecordRepository
import br.org.testing.spock.api.service.PersonService
import br.org.testing.spock.api.service.social.SocialSecurityNumberVerification
import br.org.testing.spock.api.service.social.SocialSecurityNumberVerificationException
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import javax.inject.Inject

/**
 * Created by c.heidemann on 05/04/2017.
 */
class PersonServiceImplTest extends Specification {

    private SocialSecurityNumberVerification socialSecurityNumberVerification = Mock();

    private PersonRepository personRepository = Mock();

    private SocialServiceUsageRecordRepository socialServiceUsageRecordRepository = Mock();

    @Subject
    PersonService personService = new PersonServiceImpl(socialSecurityNumberVerification: socialSecurityNumberVerification, personRepository: personRepository, socialServiceUsageRecordRepository: socialServiceUsageRecordRepository);

    @Shared
    private Person validPerson;

    @Shared
    private Person invalidPerson;

    def setupSpec() {
        validPerson = new Person(name: "John McAfee", socialSecurityNumber: "420-42-8260", email: "john@officialmcafee.com")
        invalidPerson = new Person(email: "someone@gmail.com")
    }

    @Unroll
    def "should fail #VIOLATION Social Security Number"() {

        given:
        socialSecurityNumberVerification.verifySocialNumber(_) >> { String number ->
            throw new SocialSecurityNumberVerificationException(number, VIOLATION);
        }

        when:
        personService.register(person)

        then:
        def error = thrown SocialSecurityNumberVerificationException
        error.message.matches("This is an (INVALID|INACTIVE) Social Security Number: .*");
        0 * personRepository.save(_)

        where:
        person      | VIOLATION
        validPerson | SocialSecurityNumberVerificationException.VIOLATION.INACTIVE
        validPerson | SocialSecurityNumberVerificationException.VIOLATION.INVALID
    }


    def "should return"() {
        given:
        socialSecurityNumberVerification.verifySocialNumber(person) >> { Person person ->
            println "Value = [$person]";
        }
        when:
        personService.register(person)

        then:
        notThrown(SocialSecurityNumberVerificationException)

        where:
        person << validPerson

    }

}
