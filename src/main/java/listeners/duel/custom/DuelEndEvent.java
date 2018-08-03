package listeners.duel.custom;

import duel.solo.SoloDuel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

@Getter
@Setter
public class DuelEndEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private SoloDuel duel;

    private UUID winner, loser;

    public DuelEndEvent(SoloDuel duel, UUID winner, UUID loser) {

        this.duel = duel;

        this.winner = winner;
        this.loser = loser;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
