package com.lightsperfections.slackrelay.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 12/24/15
 * Time: 9:30 AM
 */
public class SlackResponse {
    private final String responseType;
    private final String text;
    // TODO: Extend to include attachment support

    private SlackResponse(String responseType, String text) {
        this.responseType = responseType;
        this.text = text;
    }

    // Convenience factory  methods to abstract the details of Slack's method for showing messages publicly or privately
    // in a channel.
    public static SlackResponse createPrivate(String text) {
        return new SlackResponse("ephemeral", text);
    }

    public static SlackResponse createPublic(String text) {
        return new SlackResponse("in_channel", text);
    }

    @JsonProperty("response_type")
    public String getResponseType() {
        return responseType;
    }

    public String getText() {
        return text;
    }
}
