package com.lightsperfections.slackrelay;

import com.lightsperfections.slackrelay.beans.SlackResponse;
import com.lightsperfections.slackrelay.services.DependentServiceException;
import com.lightsperfections.slackrelay.services.InternalImplementationException;
import com.lightsperfections.slackrelay.services.SlackRelayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * SlackCommandController is intended to be called by Slack slash commands, as documented here:
 * https://api.slack.com/slash-commands
 *
 * It is intended to be a fairly thin wrapper around whatever back-end services the Slack user wishes
 * to interface with.
 *
 * Each endpoint should be defined as a POST, since this is Slack's favored method. Each endpoint should
 * also require each of the params, since they are guaranteed to be sent by Slack and therefore can
 * be used to (lightly) authenticate clients.
 *
 * The text param contains variable text provided by the user. It is the only provided data that should
 * be relayed to the back-end service.
 */

@RestController
public class SlackCommandController {
    private Logger logger = LoggerFactory.getLogger("SlackCommandController");

    private AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(SlackRelayConfig.class);

    /**
     * This is the generic endpoint for all ESV activity. The ESV api supports many actions: passageQuery,
     * query, readingPlanQuery, queryInfo, readingPlanInfo, verse, dailyVerse.
     *
     * The text param can contain an optional action, and this can be extended to support as many actions
     * as are desired. If no action is provided it will default to a passageLookup
     *
     * @param token
     * @param teamId
     * @param teamDomain
     * @param channelId
     * @param channelName
     * @param userId
     * @param userName
     * @param command
     * @param text
     * @return
     */
    @RequestMapping(value = "/esv", method = RequestMethod.POST)
    public ResponseEntity<SlackResponse> esv(
                           @RequestParam("token") String token,
                           @RequestParam("team_id") String teamId,
                           @RequestParam("team_domain") String teamDomain,
                           @RequestParam("channel_id") String channelId,
                           @RequestParam("channel_name") String channelName,
                           @RequestParam("user_id") String userId,
                           @RequestParam("user_name") String userName,
                           @RequestParam("command") String command,
                           @RequestParam("text") String text) {

        // Perform basic authentication
        ResponseEntity<SlackResponse> authResponse = authenticateRequest(token);
        if (authResponse != null) {
            return authResponse;
        }

        // Parse out optional subcommand. In the example "/esv passageQuery Matthew 5:14", "esv" is
        // the command, "passageQuery" is the subcommand, and "Matthew 5:14" is what should be passed
        // into the service.
        //
        // However, assuming that passageQuery is the service marked PRIMARY, the user could just
        // specify "/esv Matthew 5:14" and it should return the results of a passageQuery.

        // Try to find a more specific service
        SlackRelayService service;
        text = text.trim().toLowerCase();
        String[] tokens = text.split("\\s+");

        // If no parameters are provided, fall back to HELP
        if (tokens.length == 1 && "".equals(tokens[0])) {
            service = context.getBean("esv.help", SlackRelayService.class);
        } else {
            int firstParamIndex = 1;

            // If the first param is not a valid subcommand assume passageQuery
            if (!context.isBeanNameInUse("esv." + tokens[0])) {
                service = context.getBean("esv.passagequery", SlackRelayService.class);
                firstParamIndex = 0;

            // The first param is a valid command - use that service and pass the rest of the params in
            } else {
                service = context.getBean("esv." + tokens[0], SlackRelayService.class);
            }

            // Reconstitute the tokens back into the text field (minus subcommand), and proceed
            text = "";
            for (int i = firstParamIndex; i < tokens.length; i++) {
                text += tokens[i] + (i < tokens.length - 1 ? " " : "");
            }
        }

        return runService(service, userName, text);
    }

    /**
     * This is the generic endpoint for all Logos activity. Logos is a set of commands
     * to support Daily Bible Readings.
     *
     * @param token
     * @param teamId
     * @param teamDomain
     * @param channelId
     * @param channelName
     * @param userId
     * @param userName
     * @param command
     * @param text
     * @return
     */
    @RequestMapping(value = "/logos", method = RequestMethod.POST)
    public ResponseEntity<SlackResponse> logos(
            @RequestParam("token") String token,
            @RequestParam("team_id") String teamId,
            @RequestParam("team_domain") String teamDomain,
            @RequestParam("channel_id") String channelId,
            @RequestParam("channel_name") String channelName,
            @RequestParam("user_id") String userId,
            @RequestParam("user_name") String userName,
            @RequestParam("command") String command,
            @RequestParam("text") String text) {

        // Perform basic authentication
        ResponseEntity<SlackResponse> authResponse = authenticateRequest(token);
        if (authResponse != null) {
            return authResponse;
        }

        // Try to find a more specific service
        SlackRelayService service;
        text = text.trim().toLowerCase();
        String[] tokens = text.split("\\s+");

        // If no subcommand is provided, or one is provided that's not a registered
        // service, fall back to HELP
        if ("".equals(tokens[0]) || !context.isBeanNameInUse("logos." + tokens[0])) {
            service = context.getBean("logos.help", SlackRelayService.class);
        } else {
            service = context.getBean("logos." + tokens[0], SlackRelayService.class);
        }

        // Reconstitute the tokens back into the text field (minus subcommand), and proceed
        text = "";
        for (int i = 1; i < tokens.length; i++) {
            text += tokens[i] + (i < tokens.length - 1 ? " " : "");
        }

        return runService(service, userName, text);
    }


    /**
     * Helper method to abstract out authentication details.
     * @param token
     * @return
     */
    private ResponseEntity<SlackResponse> authenticateRequest(String token) {
        ResponseEntity<SlackResponse> responseEntity = null;

        // Authenticate based on Slack Token. This can be expanded to allow multiple tokens or to
        // authenticate on any of the other pieces of data sent by Slack. But for now, simple.
        try {
            String authorizedSlackToken = context.getBean("authorizedSlackToken", String.class);

            // Server error - need to pass the allowed slack token into the application at startup
            if (authorizedSlackToken == null) {
                return new ResponseEntity<SlackResponse>(
                        SlackResponse.createPrivate("Authorization misconfiguration"),
                        HttpStatus.INTERNAL_SERVER_ERROR);

                // Client error - token mismatch
            } else if (!authorizedSlackToken.equalsIgnoreCase(token)) {
                return new ResponseEntity<SlackResponse>(
                        SlackResponse.createPrivate("Authorization denied for provided token"),
                        HttpStatus.UNAUTHORIZED);
            }

            // Else - good to go, carry on.

        } catch (NoSuchBeanDefinitionException e) {
            return new ResponseEntity<SlackResponse>(
                    SlackResponse.createPrivate("Authorization unconfigured"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }


    /**
     * Helper method to abstract out the logic of Exception handling.
     * @param service
     * @param text
     * @return
     */
    private ResponseEntity<SlackResponse> runService(SlackRelayService service, String userName, String text) {

        // The relayed request can fail because the dependent service is having problems, or because _this_
        // application has bugs. Trying to be helpful in diagnosing the problem.
        String responseText;
        try {
            responseText = service.performAction(userName, text);
        } catch (DependentServiceException e) {
            return new ResponseEntity<SlackResponse> (
                    SlackResponse.createPrivate(service.getName() + " failed."),
                    HttpStatus.BAD_GATEWAY);

        } catch (InternalImplementationException e) {
            return new ResponseEntity<SlackResponse> (
                    SlackResponse.createPublic("Internal error trying to proxy request to " + service.getName() + "."),
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<SlackResponse> (
                SlackResponse.createPublic(responseText),
                HttpStatus.OK);

    }
}
