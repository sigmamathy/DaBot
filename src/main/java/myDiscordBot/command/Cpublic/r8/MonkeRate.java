package myDiscordBot.command.Cpublic.r8;

import myDiscordBot.command.Cpublic.r8.handle.RateCommand;

public class MonkeRate extends RateCommand {
    protected void setUp(){
        createMachine("monke",":monkey_face:");
        createCommandName("monkerate");
    }
}
