package myDiscordBot.command.r8;

import myDiscordBot.command.r8.handle.RateCommand;

public class MonkeRate extends RateCommand {
    protected void setUp(){
        createMachine("monke",":monkey_face:");
        createCommandName("monkerate");
    }
}
