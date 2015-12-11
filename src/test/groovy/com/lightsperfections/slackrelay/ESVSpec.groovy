package com.lightsperfections.slackrelay

import groovyx.net.http.RESTClient
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.ContentType.URLENC
import groovyx.net.http.ContentType.*
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
@IntegrationTest("server.port=8080")
public class ESVSpec extends Specification {

    @Value("\${server.port}")
    private int port;
/*
    @Test
    def "GET is an invalid request method"() {
        setup:
        def client = new RESTClient( "http://localhost:$port/" )
        client.handler.failure = client.handler.success
        when:
        def resp = client.get([ path: 'esv', query : [ input: 'phil' ]])
        then:
        with(resp) {
            status == 405
        }
    }
*/
    @Test
    def "POST with all params returns success"() {
        setup:
        def client = new RESTClient( "http://localhost:$port/" )
        client.handler.failure = client.handler.success
        def paramMap = [token: 'token',
                        teamId: 'teamId',
                        channelId: 'channelId',
                        channelName: 'channelName',
                        userId: 'userId',
                        userName: 'userName',
                        command: 'command',
                        text: 'text']
        when:
        def resp = client.post(
                path: 'esv',
                requestContentType: URLENC,
                body: paramMap
        )
        then:
        with(resp) {
            status == 200
        }
    }
}
