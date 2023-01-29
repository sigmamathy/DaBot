package myDiscordBot.command.r8;

import myDiscordBot.command.r8.handle.RateCommand;

public class SusRate extends RateCommand {
    protected void setUp(){
        createMachine("sus","ඞ");
        createCommandName("susrate");
    }
}
