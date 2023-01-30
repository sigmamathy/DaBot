package myDiscordBot.command;

import net.dv8tion.jda.api.EmbedBuilder;

// Base class of all commands
public abstract class DiscordCommand
{
    // Only for inheritance
    protected DiscordCommand() {}

    // A lazy function that executes command if required condition is satisfied
    public final void exe(DiscordEvent e)
    {
        if (!e.args[0].equalsIgnoreCase(getName())) return;
        if (errorHandle(e))
            handle(e);
    }

    // Handles the command implemented by the child class
    protected abstract void handle(DiscordEvent e);

    // Handles errors implemented by the child class
    protected abstract boolean errorHandle(DiscordEvent e);

    // Returns the name of the command
    protected abstract String getName();

    // Returns an embed builder for help
    public abstract EmbedBuilder getHelp();
}
