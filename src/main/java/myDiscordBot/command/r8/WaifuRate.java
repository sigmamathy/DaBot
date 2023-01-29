package myDiscordBot.command.r8;

import myDiscordBot.command.r8.handle.RateCommand;

public class WaifuRate extends RateCommand {
    protected void setUp(){
        createMachine("waifu",":heart:");
        createCommandName("waifurate");
    }
}
