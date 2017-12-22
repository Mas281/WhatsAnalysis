# WhatsAnalysis

A small chat parsing and analysis tool for WhatsApp written in Java.

This program combines WhatsApp's feature of being able to export chats and the magic of regular expressions to allow you to parse and analyse chats.

This was made a while ago when I first learnt regex and wanted to apply it to something. There are several uncommon (yet possible) situations which may break the regex (for example having someone in a chat whos name contains a colon for some reason). Also, if you send a message with multiple lines where one of the lines follows the regex exactly, it will think that it is a separate message, which unfortunately isn't preventable.

## Usage

See the test file at src/test/java/ChatParseTest.java for example usage

## Maven

You can use JitPack to access the project through Maven:
```xml
<repository>
    <id>jitpack-repo</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>com.github.Mas281</groupId>
    <artifactId>WhatsAnalysis</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
