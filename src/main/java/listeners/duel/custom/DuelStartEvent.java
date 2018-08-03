package listeners.duel.custom;

import duel.solo.SoloDuel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@Setter
public class DuelStartEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private SoloDuel duel;

    public DuelStartEvent(SoloDuel duel) {
        this.duel = duel;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
