package com.lightsperfections.slackrelay

import groovyx.net.http.RESTClient
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration;
import spock.lang.Specification;

/**
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 11/18/15
 * Time: 2:31 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringApplicationContextLoader, classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port=9000")
public class ESVSpec extends Specification {

    @Value("\${server.port}")
    private int port;

    @Test
    def "should return 200"() {
        setup:
        def primerEndpoint = new RESTClient( "http://localhost:$port/" )
        when:
        def resp = primerEndpoint.get([ path: 'esv', query : [ input: 'texty' ]])
        then:
        with(resp) {
            status == 200
            contentType == "application/json"
        }
    }
}
