package gametype;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public class GameMode {

    private String name;
    private GameType type;

    private ItemStack[] inv, armor, chest;

    private boolean editable, ranked;

    private ItemStack display = new ItemStack(Material.DIAMOND_SWORD);

    public GameMode(String name, ItemStack[] inv, ItemStack[] armor) {

        this.name = name;

        this.inv = inv;
        this.armor = armor;

        this.ranked = true;
        this.editable = true;

        chest = new ItemStack[54];

        type = GameType.NORMAL;

    }

}
