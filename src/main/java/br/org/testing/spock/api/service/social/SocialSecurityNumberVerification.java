package br.org.testing.spock.api.service.social;

/**
 * Created by c.heidemann on 05/04/2017.
 */
public interface SocialSecurityNumberVerification {

    /**
     * Verify SSN online and receive immediate result
     */
    ResultDTO verifySocialNumber(String socialSecurityNumber);

}
