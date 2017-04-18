package br.org.testing.spock.impl.service;

import javax.enterprise.inject.Default;

import br.org.testing.spock.api.service.social.ResultDTO;
import br.org.testing.spock.api.service.social.SocialSecurityNumberVerification;

@Default
public class SocialSecurityNumberVerificationImpl implements SocialSecurityNumberVerification{

    @Override
    public ResultDTO verifySocialNumber(String socialSecurityNumber) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setSocialSecurityNumber(socialSecurityNumber);
        resultDTO.setLastName("Heidemann");
        resultDTO.setAllocation("Rio do Oeste");
        return resultDTO;
    }

}
