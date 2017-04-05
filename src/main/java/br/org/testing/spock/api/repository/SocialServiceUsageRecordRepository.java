package br.org.testing.spock.api.repository;

import br.org.testing.spock.api.domain.Person;
import br.org.testing.spock.api.service.social.ResultDTO;

import java.io.Serializable;

/**
 * Created by c.heidemann on 05/04/2017.
 */
public interface SocialServiceUsageRecordRepository extends Serializable {

    void save(ResultDTO result);

}
