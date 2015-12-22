package com.lightsperfections.slackrelay;

import com.lightsperfections.slackrelay.services.DependentServiceException;
import com.lightsperfections.slackrelay.services.InternalImplementationException;
import com.lightsperfections.slackrelay.services.SlackRelayService;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


    AnnotationConfigApplicationContext context =
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
    public ResponseEntity<String> greeting(@RequestParam("token") String token,
                           @RequestParam("team_id") String teamId,
                           @RequestParam("team_domain") String teamDomain,
                           @RequestParam("channel_id") String channelId,
                           @RequestParam("channel_name") String channelName,
                           @RequestParam("user_id") String userId,
                           @RequestParam("user_name") String userName,
                           @RequestParam("command") String command,
                           @RequestParam("text") String text) {

        // Authenticate based on Slack Token. This can be expanded to allow multiple tokens or to
        // authenticate on any of the other pieces of data sent by Slack. But for now, simple.
        try {
            String authorizedSlackToken = context.getBean("authorizedSlackToken", String.class);

            // Server error - need to pass in the allowed slack token
            if (authorizedSlackToken == null) {
                return new ResponseEntity<String>("Authorization misconfiguration", HttpStatus.INTERNAL_SERVER_ERROR);

            // Client error - token mismatch
            } else if (!authorizedSlackToken.equalsIgnoreCase(token)) {
                return new ResponseEntity<String>("Authorization denied for provided token", HttpStatus.UNAUTHORIZED);
            }

            // Else - good to, carry on.

        } catch (NoSuchBeanDefinitionException e) {
            return new ResponseEntity<String>("Authorization unconfigured", HttpStatus.INTERNAL_SERVER_ERROR);
        }



        // Parse out optional subcommand. In the example "/esv passageQuery Matthew 5:14", "esv" is
        // the command, "passageQuery" is the subcommand, and "Matthew 5:14" is what should be passed
        // into the service.
        //
        // However, assuming that passageQuery is the service marked PRIMARY, the user could just
        // specify "/esv Matthew 5:14" and it should return the results of a passageQuery.

        SlackRelayService service;
        int tokenLocation = text.indexOf(" ");
        String subcommand = text.substring(0, tokenLocation == -1 ? 0 : tokenLocation);
        if (subcommand.length() > 0) {

            // Try to get the service with the NAME of the provided subcommand. If that doesn't work,
            // use the unimplemented service to correctly bubble-up the problem
            try {
                service = context.getBean(SlackRelayService.class, subcommand);
            } catch (NoSuchBeanDefinitionException e) {
                service = context.getBean("unimplemented", SlackRelayService.class);
            }

            // Peel the subcommand out of "text", and proceed
            text = text.substring(tokenLocation);
        } else {
            // No subcommand was provided, so just use the default one.
            service = context.getBean(SlackRelayService.class);
        }


        
        // The relayed request can fail because the dependent service is having problems, or because _this_
        // application has bugs. Trying to be helpful in diagnosing the problem.
        String responseText;
        try {
            responseText = service.performAction(text);
        } catch (DependentServiceException e) {
            return new ResponseEntity<String> (service.getName() + " failed.", HttpStatus.BAD_GATEWAY);

        } catch (InternalImplementationException e) {
            return new ResponseEntity<String> ("Internal error trying to proxy request to " + service.getName() + ".",
                    HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity<String> (responseText, HttpStatus.OK);

    }
}
