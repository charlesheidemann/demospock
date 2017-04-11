package br.org.testing.spock.impl.service;

import br.org.testing.spock.api.domain.Person;
import br.org.testing.spock.api.repository.PersonRepository;
import br.org.testing.spock.api.repository.SocialServiceUsageRecordRepository;
import br.org.testing.spock.api.service.PersonService;
import br.org.testing.spock.api.service.social.ResultDTO;
import br.org.testing.spock.api.service.social.SocialSecurityNumberVerification;
import br.org.testing.spock.api.service.social.SocialSecurityNumberVerificationException;
import br.org.testing.spock.impl.repository.PersonRepositoryImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import spock.lang.Shared;

import javax.persistence.EntityExistsException;

/**
 * Created by c.heidemann on 11/04/2017.
 * This is a default test implementation with JUnit/Mockito
 * There is another implementation using Spock Framework
 * @see PersonServiceImplTest
 * It have the same idea for test methods
 */

@RunWith(MockitoJUnitRunner.class)
public class PersonServiceImplJUnitTest {

    @InjectMocks
    private PersonServiceImpl personService;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private SocialSecurityNumberVerification socialSecurityNumberVerification;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private SocialServiceUsageRecordRepository socialServiceUsageRecordRepository;

    private Person validPerson1, validPerson2;

    @Before
    public void beforeTest() {

        validPerson1 = new Person();
        validPerson1.setName("John McAfee");
        validPerson1.setSocialSecurityNumber("420-42-8260");
        validPerson1.setEmail("john@officialmcafee.com");

        validPerson2 = new Person();
        validPerson2.setName("Valentino Rossi");
        validPerson2.setSocialSecurityNumber("574-49-0807");
        validPerson2.setEmail("vr46@motogp.com");

    }

    @Test
    public void shouldFailInactiveSocialSecurityNumber() {
        Mockito.when(socialSecurityNumberVerification.verifySocialNumber(validPerson1.getSocialSecurityNumber())).thenThrow(new SocialSecurityNumberVerificationException(validPerson1.getSocialSecurityNumber(), SocialSecurityNumberVerificationException.VIOLATION.INACTIVE));
        thrown.expect(SocialSecurityNumberVerificationException.class);
        thrown.expectMessage("This is an INACTIVE Social Security Number:");
        personService.register(validPerson1);
    }

    @Test
    public void shouldFailInvalidSocialSecurityNumber() {
        Mockito.when(socialSecurityNumberVerification.verifySocialNumber(validPerson2.getSocialSecurityNumber())).thenThrow(new SocialSecurityNumberVerificationException(validPerson2.getSocialSecurityNumber(), SocialSecurityNumberVerificationException.VIOLATION.INVALID));
        thrown.expect(SocialSecurityNumberVerificationException.class);
        thrown.expectMessage("This is an INVALID Social Security Number:");
        personService.register(validPerson2);
    }

    @Test
    public void shouldFailWithDataSourceException(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setAllocation("Rio do Oeste");
        resultDTO.setSocialSecurityNumber(validPerson1.getSocialSecurityNumber());
        resultDTO.setLastName(validPerson1.getName());
        Mockito.when(socialSecurityNumberVerification.verifySocialNumber(validPerson1.getSocialSecurityNumber())).thenReturn(resultDTO);
        Mockito.doThrow(new RuntimeException("SQLException")).when(socialServiceUsageRecordRepository).save(resultDTO);
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("SQLException");
        personService.register(validPerson1);
    }

    @Test
    public void shouldFailWithJPAException(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setAllocation("Rio do Oeste");
        resultDTO.setSocialSecurityNumber(validPerson1.getSocialSecurityNumber());
        resultDTO.setLastName(validPerson1.getName());
        Mockito.when(socialSecurityNumberVerification.verifySocialNumber(validPerson1.getSocialSecurityNumber())).thenReturn(resultDTO);
        Mockito.doThrow(new EntityExistsException("SocialSecurityNumber already registered: " + validPerson1.getSocialSecurityNumber())).when(personRepository).save(validPerson1);
        thrown.expect(EntityExistsException.class);
        thrown.expectMessage("SocialSecurityNumber already registered");
        personService.register(validPerson1);
        Mockito.verify(socialServiceUsageRecordRepository, Mockito.times(1)).save(resultDTO);
    }

    @Test
    public void shouldRunWithoutProblem(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setAllocation("Rio do Oeste");
        resultDTO.setSocialSecurityNumber(validPerson1.getSocialSecurityNumber());
        resultDTO.setLastName(validPerson1.getName());
        Mockito.when(socialSecurityNumberVerification.verifySocialNumber(validPerson1.getSocialSecurityNumber())).thenReturn(resultDTO);
        personService.register(validPerson1);
        Mockito.verify(socialServiceUsageRecordRepository, Mockito.times(1)).save(resultDTO);
        Mockito.verify(personRepository, Mockito.times(1)).save(validPerson1);
    }

}
