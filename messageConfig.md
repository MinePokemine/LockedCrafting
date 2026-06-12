# How To Configure Messages

To configure this plugin, go to your server directory, then go to: \
    /plugins/LockedCrafting/config.yml

In the config.yml file, there is a section called _messages_.
You can configure these.

### Missing Requirements (messages.missingReqs)

When a player tries to craft an item but is blocked by this plugin,
the player will be sent a message in chat telling them why.
You can configure this message however you want, under messages.missingReqs \
The message goes like this:

1. It sends the message in pre
2. If sayMissing is set to true, it tells the player what items they are missing, with each item separated by a new line
3. It sends the message in between
4. If sayThere is set to true, it tells the player what required items they have, with each item separated by a new line.