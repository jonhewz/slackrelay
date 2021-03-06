package com.lightsperfections.slackrelay

import groovy.transform.Field
import groovyx.net.http.RESTClient
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.ContentType.URLENC
import static groovyx.net.http.ContentType.TEXT
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
@IntegrationTest(["server.port=9000"])
public class ESVSpec extends Specification {

    @Value("\${server.port}")
    private int port;

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

    @Test
    def "POST with all params returns success"() {
        setup:
        def client = new RESTClient( "http://localhost:$port/" )
        client.handler.failure = client.handler.success
        def paramMap = [token: 'TEST',
                        team_id: 'TEST',
                        team_domain: 'teamDomain',
                        channel_id: 'channelId',
                        channel_name: 'channelName',
                        user_id: 'userId',
                        user_name: 'userName',
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

    @Test
    def "Invalid token returns unauthorized"() {
        setup:
        def client = new RESTClient( "http://localhost:$port/" )
        client.handler.failure = client.handler.success
        def paramMap = [token: 'TEST',
                        team_id: 'sultans of swing',
                        team_domain: 'teamDomain',
                        channel_id: 'channelId',
                        channel_name: 'channelName',
                        user_id: 'userId',
                        user_name: 'userName',
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
            status == 401
        }
    }

    @Test
    def "POST with missing param returns 400"() {
        setup:
        def partialParamMap = [team_id: 'TEST',
                               team_domain: 'teamDomain',
                               channel_id: 'channelId',
                               channel_name: 'channelName',
                               user_id: 'userId',
                               user_name: 'userName',
                               command: 'command',
                               text: 'text']

        def client = new RESTClient( "http://localhost:$port/" )
        client.handler.failure = client.handler.success
        when:
        def resp = client.post(
                path: 'esv',
                requestContentType: URLENC,
                body: partialParamMap
        )
        then:
        with(resp) {
            status == 400
        }
    }

    @Test
    def "ESV passageQuery case-insensitivity" () {
        setup:
        def partialParamMap = [token: 'TEST',
                               team_id: 'TEST',
                               team_domain: 'teamDomain',
                               channel_id: 'channelId',
                               channel_name: 'channelName',
                               user_id: 'userId',
                               user_name: 'userName',
                               command: 'command',
                               text: 'PASSAGEQUERY 1 thessalonians 5:21' ]

        def client = new RESTClient( "http://localhost:$port/" )
        client.handler.failure = client.handler.success
        when:
        def resp = client.post(
                path: 'esv',
                body: partialParamMap,
                requestContentType:URLENC
        )
        then:
        with(resp) {
            status == 200
            data.text.contains('test everything; hold fast')
        }
    }

    @Test
    def "ESV passageQuery default to passagequery" () {
        setup:
        def partialParamMap = [token: 'TEST',
                               team_id: 'TEST',
                               team_domain: 'teamDomain',
                               channel_id: 'channelId',
                               channel_name: 'channelName',
                               user_id: 'userId',
                               user_name: 'userName',
                               command: 'command',
                               text: ' 1 thessalonians 5:21  ' ]

        def client = new RESTClient( "http://localhost:$port/" )
        client.handler.failure = client.handler.success
        when:
        def resp = client.post(
                path: 'esv',
                body: partialParamMap,
                requestContentType:URLENC
        )
        then:
        with(resp) {
            status == 200
            data.text.contains('test everything')
        }
    }

    @Test
    def "ESV with no params defaults to HELP" () {
        setup:
        def partialParamMap = [token: 'TEST',
                               team_id: 'TEST',
                               team_domain: 'teamDomain',
                               channel_id: 'channelId',
                               channel_name: 'channelName',
                               user_id: 'userId',
                               user_name: 'userName',
                               command: 'command',
                               text: ' ' ]

        def client = new RESTClient( "http://localhost:$port/" )
        client.handler.failure = client.handler.success
        when:
        def resp = client.post(
                path: 'esv',
                body: partialParamMap,
                requestContentType:URLENC
        )
        then:
        with(resp) {
            status == 200
            data.text.contains('ESV Help')
        }
    }

    @Test
    def "ESV with invalid reference " () {
        setup:
        def partialParamMap = [token: 'TEST',
                               team_id: 'TEST',
                               team_domain: 'teamDomain',
                               channel_id: 'channelId',
                               channel_name: 'channelName',
                               user_id: 'userId',
                               user_name: 'userName',
                               command: 'command',
                               text: 'enoch 1 ' ]

        def client = new RESTClient( "http://localhost:$port/" )
        client.handler.failure = client.handler.success
        when:
        def resp = client.post(
                path: 'esv',
                body: partialParamMap,
                requestContentType:URLENC
        )
        then:
        with(resp) {
            status == 200
            data.text.contains('No passage found for your query')
        }
    }
}
