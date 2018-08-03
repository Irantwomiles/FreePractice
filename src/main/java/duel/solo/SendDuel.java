package duel.solo;

import gametype.GameMode;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.ChatColor;

import java.util.UUID;

@Getter
@Setter
public class SendDuel {

    private UUID sender, reciever;

    private GameMode game;

    public SendDuel(UUID sender, UUID reciever, GameMode game) {
        this.sender = sender;
        this.reciever = reciever;
        this.game = game;

        Player s = Bukkit.getPlayer(sender);
        Player r = Bukkit.getPlayer(reciever);

        TextComponent senderName = new TextComponent(s.getName());
        senderName.setColor(ChatColor.YELLOW);

        TextComponent message = new TextComponent(" has sent a duel request for Game Mode ");
        message.setColor(ChatColor.GRAY);

        TextComponent gameMode = new TextComponent(game.getName());
        gameMode.setColor(ChatColor.GOLD);

        TextComponent accept = new TextComponent(" [Accept] ");
        accept.setColor(ChatColor.GREEN);
        accept.setBold(true);
        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/accept " + s.getName()));

        TextComponent deny = new TextComponent("[Deny]");
        deny.setColor(ChatColor.RED);
        deny.setBold(true);
        deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/deny " + s.getName()));

        senderName.addExtra(message);
        senderName.addExtra(gameMode);
        senderName.addExtra(accept);
        senderName.addExtra(deny);

        r.spigot().sendMessage(senderName);

        s.sendMessage(ChatColor.GREEN + "Duel request sent to " + ChatColor.BLUE + r.getName());
    }
}

