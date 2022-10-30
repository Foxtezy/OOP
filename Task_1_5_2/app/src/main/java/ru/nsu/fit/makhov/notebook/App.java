package ru.nsu.fit.makhov.notebook;

import com.beust.jcommander.JCommander;

public class App {

    public static void main(String[] args) {
        Commands commands = new Commands();
        Args arguments = new Args();
        JCommander.newBuilder()
            .addObject(arguments)
            .build()
            .parse(args);
        CommandListener commandListener = new CommandListener();
        System.out.println(commandListener.onMessageReceived(arguments));
    }
}
