package myDiscordBot.command.r8;

import myDiscordBot.command.r8.handle.RateCommand;

public class GaeRate extends RateCommand {
    protected void setUp(){
        createMachine("gae",":rainbow_flag:");
        createCommandName("gaerate");
    }
}
