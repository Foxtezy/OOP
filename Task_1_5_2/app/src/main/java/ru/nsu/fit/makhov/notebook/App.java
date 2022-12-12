package ru.nsu.fit.makhov.notebook;

import com.beust.jcommander.JCommander;

public class App {

    public static void main(String[] args) {
        String[] testArgs = {"--show"};
        Args arguments = new Args();
        JCommander.newBuilder()
            .addObject(arguments)
            .build()
            .parse(testArgs);
        CommandListener commandListener = new CommandListener();
        System.out.println(commandListener.onMessageReceived(arguments));
    }
}
