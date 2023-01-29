package myDiscordBot.command.Cpublic.r8.handle;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.HashMap;
import java.util.Random;

public class RateMachine
{
    String name, emoji;
    EmbedBuilder eb = new EmbedBuilder();
    HashMap<String,Integer> specialCase = new HashMap<>();
    public RateMachine(String name, String emoji){
        this.name = name;
        this.emoji = emoji;
        eb.setColor(new Color(0x40A6EE));
        eb.setTitle(name + " r8 machine");
    }
    public EmbedBuilder getEmbedBuilder(String target, String userName, String userAvatar){
        int rate = new Random().nextInt(102);
        if (specialCase.containsKey(target))
            rate = specialCase.get(target);
        eb.setDescription(target + " is " + rate + "% " + name + " " + emoji);
        if (rate == 101)
            eb.setDescription(target + " is ∞% " + name + " " + emoji);
        eb.setFooter("Summoned by " + userName, userAvatar);
        return eb;
    }
    public void setSpecialCase(String target, int rate){
        specialCase.put(target, rate);
    }
}
