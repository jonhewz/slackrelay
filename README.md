# SlackRelay
This application speaks "Slack", meaning it accepts the type of Slack POST requests associated with slash commands.
https://api.slack.com/slash-commands

It is modular enough to allow you to build out multiple services. The initial (and maybe only) one currently built out
is used to return ESV Bible text when requested from a Slack slash command.

When a Slack user types 
`/esv passagequery Romans 1`, or simply `/esv Romans 1`, Slack will post a request to this web application. It will
then make a GET request to esvapi.org to get the desired text, and return it back to Slack to be posted in a channel.

This can be expanded in the future to support more of ESV's functionality, such as queries, reading plans, and daily
verses. 

Primarily this is an avenue for me to try building out Slack services.