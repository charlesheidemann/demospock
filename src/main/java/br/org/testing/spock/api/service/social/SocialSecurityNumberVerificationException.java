package br.org.testing.spock.api.service.social;

import javax.ejb.ApplicationException;

/**
 * Created by c.heidemann on 05/04/2017.
 */
@ApplicationException(rollback = true)
public class SocialSecurityNumberVerificationException extends RuntimeException {

    public SocialSecurityNumberVerificationException(String socialNumber, VIOLATION error) {
        super(String.format(error.message(), socialNumber));
    }

    public enum VIOLATION {

        INVALID("This is an INVALID Social Security Number: %s"),

        INACTIVE("This is an INACTIVE Social Security Number: %s");

        VIOLATION(String msg) {
            this.msg = msg;
        }

        private String msg;

        public String message() {
            return this.msg;
        }
    }

}
