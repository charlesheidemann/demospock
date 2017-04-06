package br.org.testing.spock.impl.service

import br.org.testing.spock.api.domain.Person
import br.org.testing.spock.api.repository.PersonRepository
import br.org.testing.spock.api.repository.SocialServiceUsageRecordRepository
import br.org.testing.spock.api.service.PersonService
import br.org.testing.spock.api.service.social.ResultDTO
import br.org.testing.spock.api.service.social.SocialSecurityNumberVerification
import br.org.testing.spock.api.service.social.SocialSecurityNumberVerificationException
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import javax.persistence.EntityExistsException
import javax.persistence.PersistenceException
import java.sql.SQLException

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
    private Person validPerson1, validPerson2;

    def setupSpec() {
        validPerson1 = new Person(name: "John McAfee", socialSecurityNumber: "420-42-8260", email: "john@officialmcafee.com")
        validPerson2 = new Person(name: "Valentino Rossi", socialSecurityNumber: "574-49-0807", email: "vr46@motogp.com")
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
        validPerson1 | SocialSecurityNumberVerificationException.VIOLATION.INACTIVE
        validPerson2 | SocialSecurityNumberVerificationException.VIOLATION.INVALID
    }

    def "should fail with DataSource Exception"() {
        given:
        1 * socialSecurityNumberVerification.verifySocialNumber(_) >> new ResultDTO(socialSecurityNumber: person.socialSecurityNumber, allocation: "Rio do Oeste", lastName: person.name)

        and: 'get SQLException'
        1 * socialServiceUsageRecordRepository.save(_) >> {
            throw new RuntimeException("SQLException")
        }

        and: 'should not invoke'
        0 * personRepository.save(_)

        when:
        personService.register(person)

        then:
        def error = thrown RuntimeException
        error.message.contains("SQLException")

        where:
        person << validPerson1

    }

    def "should fail with JPA Exception"() {
        given:
        1 * socialSecurityNumberVerification.verifySocialNumber(_) >> new ResultDTO(socialSecurityNumber: person.socialSecurityNumber, allocation: "Rio do Oeste", lastName: person.name)

        and: 'should call save ServiceUsageRecord'
        1 * socialServiceUsageRecordRepository.save(_)

        and: 'get EntityExistsException'
        1 * personRepository.save(_) >> {
            throw new EntityExistsException("SocialSecurityNumber already registered: ".concat(person.socialSecurityNumber))
        }

        when:
        personService.register(person)

        then:
        def error = thrown EntityExistsException
        error.message.contains("SocialSecurityNumber already registered: ".concat(person.socialSecurityNumber))

        where:
        person << validPerson1

    }

    def "should run without problem"() {
        given:
        1 * socialSecurityNumberVerification.verifySocialNumber(_) >> new ResultDTO(socialSecurityNumber: person.socialSecurityNumber, allocation: "Rio do Oeste", lastName: person.name)

        when:
        personService.register(person)

        then:
        notThrown(SocialSecurityNumberVerificationException)
        1 * socialServiceUsageRecordRepository.save(_)
        1 * personRepository.save(_)

        where:
        person << validPerson1

    }

}
