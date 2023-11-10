package client;

import commands.ICommand;

public class Invoker {
    /* Constructor */
    public Invoker() { }

    public void execute(ICommand command) {
        command.execute();
    }
}
