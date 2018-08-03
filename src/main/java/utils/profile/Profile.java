package utils.profile;

import duel.solo.SendDuel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@Getter
@Setter
public class Profile {

    private UUID player;
    private PlayerState state;

    private HashMap<UUID, SendDuel> duelRequest;

    public Profile(UUID uuid) {
        this.player = uuid;

        duelRequest = new HashMap<UUID, SendDuel>();

        state = PlayerState.LOBBY;
    }

    public Player getPlayerObject() {
        return Bukkit.getPlayer(this.player);
    }

}
