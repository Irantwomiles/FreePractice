package duel.solo;

import duel.Duel;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SoloDuel extends Duel {

    private UUID player1, player2;

    public SoloDuel(UUID player1, UUID player2) {

        super();

        this.player1 = player1;
        this.player2 = player2;
    }

}
