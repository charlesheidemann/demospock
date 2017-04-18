package br.org.testing.spock.impl.service;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

import br.org.testing.spock.api.domain.Person;
import br.org.testing.spock.api.service.PersonService;

/**
 * Created by c.heidemann on 18/04/2017.
 */

@RunWith(Arquillian.class)
public class ArquillianTest {

    @Inject
    PersonService personService;


    @Deployment
    public static Archive<JAXRSArchive> createDeployment() {
        JAXRSArchive deployment = ShrinkWrap.create( JAXRSArchive.class );
        deployment.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        deployment.addPackages(true, "br.org.testing.spock");
        deployment.addAsResource("persistence.xml", "META-INF/persistence.xml");
        return deployment;
    }


    @Test
    public void should_create_greeting() {
        Person p = new Person();
        p.setEmail("charles@gmail.com");
        p.setName("Charles Heidemann");
        p.setSocialSecurityNumber("125-985-845");
        personService.register(p);
        Assert.assertTrue("Arquillian OK!", true);
    }

}
