# SLACKRELAY
This application speaks "Slack", meaning it accepts the type of Slack POST requests associated with slash commands.
https://api.slack.com/slash-commands

It is modular enough to allow you to build out multiple services. The first two implemented are !esv and !logos
While it has some cool desired functionality, it is largely an avenue for me to try building out Slack services
and running them in AWS.

## ESV
Relay slack requests to esvapi.org to retrieve Bible passages. When a Slack user types
`/esv passagequery Romans 1`, or simply `/esv Romans 1`, Slack will post a request to this web application. It will
then make a GET request to esvapi.org to get the desired text, and return it back to Slack to be posted in a channel.

This can be expanded in the future to support more of ESV's functionality, such as queries, reading plans, and daily
verses. 

## LOGOS
This command will supply a Slack user with a daily set of reading. It is modeled after the scenario where
someone wants to read their Bible every day, adhering to a Daily Bible Reading Plan. Slack, with its app support and
access-anywhere website, as well as its mature bot capabilities, makes Slack a prime candidate for a front-end
interface.

The goal is to make it easy to build out custom plans to your liking. The type of plan I want to capture is something like Professor Horner's system
(https://www.facebook.com/Prof-Horners-Bible-Reading-System-148160145252358/). It has you read 10 chapters a day, one
from each of 10 book groups. (For example, Matthew-Mark-Luke-John is a book group, Psalms is its own). As you finish 
the books in the group you go back to the beginning of the first book in that group. Since each group has a different length, you will 
(probably) never read the same 10 chapters in the same day.

The concept is simple, and does not require you to adhere to Horner's categorization. Some guy named Nate decided to tweak this plan to his liking: 
http://nathanielclaiborne.com/prof-horners-bible-reading-plan/. While he created different groupings, he also had some 
of his groups where he would read more than a chapter from that group before moving on. This is an additional piece
of configurability that is worth adding.

User chats with a Daily Reading bot in slack. It is not a public conversation. They can give the bot several commands:
* `/logos set [plan_name|bookmark_indexes]` - choose a reading plan, or set the bookmark indexes for your current plan
* `/logos pop` - get the next passage
* `/logos status` - progress info
* `/logos plans` - listing of plans
* `/logos stats` - user reporting
