package myDiscordBot.command.Cpublic.r8.handle;

import myDiscordBot.command.DiscordEvent;
import myDiscordBot.command.DiscordCommand;
import myDiscordBot.command.exception.BadArgumentsException;
import net.dv8tion.jda.api.EmbedBuilder;

public abstract class RateCommand extends DiscordCommand {

    private RateMachine machine;
    private String commandName;

    protected RateCommand(){}

    protected abstract void setUp();

    protected final void createCommandName(String commandName){
        this.commandName = commandName;
    }

    protected final void createMachine(String name, String emoji){
        machine = new RateMachine(name, emoji);
    }

    @Override
    public final void handle(DiscordEvent e) {
        String userName = e.author.getName();
        String userAvatar = e.author.getAvatarUrl();

        if (e.args.length == 1)
            e.textChannel.sendMessage(machine.getEmbedBuilder(userName, userName, userAvatar).build()).queue();
        else
            e.textChannel.sendMessage(machine.getEmbedBuilder(e.args[1], userName, userAvatar).build()).queue();
    }

    @Override
    public final void errorHandle(DiscordEvent e) {
        if (e.args.length > 2){
            new BadArgumentsException().send(e);
            error();
        }
    }

    @Override
    protected final String getName() {
        setUp();
        return commandName;
    }

    @Override
    public EmbedBuilder getHelp() {
        return null;
    }
}
