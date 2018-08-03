package duel;

import arena.Arena;
import gametype.GameMode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
public class Duel {

    private DuelType type;

    private GameMode game;

    private ArrayList<UUID> players, spectators;

    private boolean ranked;

    private Arena arena;

    private int matchDuration;

    public Duel(DuelType type, GameMode game) {

        this.type = type;

        players = new ArrayList<UUID>();
        spectators = new ArrayList<UUID>();

        ranked = false;

        this.game = game;
    }

    public Duel() {

        players = new ArrayList<UUID>();
        spectators = new ArrayList<UUID>();

        ranked = false;
    }

}
