package arena;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

@Getter
@Setter
public class Arena {

    private String name;
    private ArenaType type;
    private Location loc1, loc2, koth1, koth2;

    private int kothTimer, kothCurrentTimer;

    public Arena(String name) {
        this.name = name;

        type = ArenaType.NORMAL;

        kothTimer = 60 * 3;
        kothCurrentTimer = kothTimer;

    }

}
