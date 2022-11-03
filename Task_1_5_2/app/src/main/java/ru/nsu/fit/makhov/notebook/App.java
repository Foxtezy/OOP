package ru.nsu.fit.makhov.notebook;

import com.beust.jcommander.JCommander;

public class App {

    public static void main(String[] args) {
        String[] testArgs = {"--show", "03.11.2022 22:22", "03.11.2022 22:27", "a", "2"};
        Args arguments = new Args();
        JCommander.newBuilder()
            .addObject(arguments)
            .build()
            .parse(testArgs);
        CommandListener commandListener = new CommandListener();
        System.out.println(commandListener.onMessageReceived(arguments));
    }
}
