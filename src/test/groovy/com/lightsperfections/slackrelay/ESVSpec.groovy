package com.lightsperfections.slackrelay;

import spock.lang.Specification;
import groovyx.net.http.HTTPBuilder;
import static groovyx.net.http.ContentType.*;
import static groovyx.net.http.Method.*;

/**
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 11/18/15
 * Time: 2:31 PM
 */
public class ESVSpec extends Specification {
    def "should return 200"() {
        setup:
        def primerEndpoint = new RESTClient( 'http://localhost:8080/' )
        when:
        def resp = primerEndpoint.get([ path: 'esv', query : [ input: 'texty' ]])
        then:
        with(resp) {
            status == 200
            contentType == "application/json"
        }
    }
}
