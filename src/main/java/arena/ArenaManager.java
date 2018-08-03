package arena;

import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import practice.FreePractice;

public class ArenaManager {

    private File file = null;

    private Random random = new Random();

    @Getter
    private static ArrayList<Arena> arenas = new ArrayList<Arena>();

    @Getter
    private ArrayList<Arena> available = new ArrayList<Arena>();

    private static ArenaManager manager;

    private ArenaManager() {}

    public static ArenaManager getManager() {
        if(manager == null) {
            manager = new ArenaManager();
        }

        return manager;
    }

    public void loadArenas() {

        file = new File(FreePractice.getInstance().getDataFolder() + "/Arenas");


        /*
        Check if file is a directory, and loop through all of its contents
         */

        if(file.isDirectory()) {

            File[] files = file.listFiles();

            if(files.length > 0) {

                for(int i = 0; i < files.length; i++) {

                    file = new File(FreePractice.getInstance().getDataFolder() + "/Arenas", files[i].getName());

                    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

                    String name = config.getString("name");

                    ArenaType type = ArenaType.valueOf(config.getString("type"));

                    Arena arena = new Arena(name);

                    arena.setType(type);

                    if(config.contains("loc1")) {

                        double x = config.getDouble("loc1.x");
                        double y = config.getDouble("loc1.y");
                        double z = config.getDouble("loc1.z");
                        double pitch = config.getDouble("loc1.pitch");
                        double yaw = config.getDouble("loc1.yaw");
                        String world = config.getString("loc1.world");

                        Location loc1 = new Location(Bukkit.getWorld(world), x, y, z);
                        loc1.setPitch((float) pitch);
                        loc1.setYaw((float) yaw);

                        arena.setLoc1(loc1);
                    }


                    if(config.contains("loc2")) {

                        double x = config.getDouble("loc2.x");
                        double y = config.getDouble("loc2.y");
                        double z = config.getDouble("loc2.z");
                        double pitch = config.getDouble("loc2.pitch");
                        double yaw = config.getDouble("loc2.yaw");
                        String world = config.getString("loc2.world");

                        Location loc2 = new Location(Bukkit.getWorld(world), x, y, z);
                        loc2.setPitch((float) pitch);
                        loc2.setYaw((float) yaw);
                        arena.setLoc2(loc2);
                    }

                    if(config.contains("koth1")) {

                        double x = config.getDouble("koth1.x");
                        double y = config.getDouble("koth1.y");
                        double z = config.getDouble("koth1.z");
                        String world = config.getString("koth1.world");

                        Location koth1 = new Location(Bukkit.getWorld(world), x, y, z);

                        arena.setKoth1(koth1);
                    }

                    if(config.contains("koth2")) {

                        double x = config.getDouble("koth2.x");
                        double y = config.getDouble("koth2.y");
                        double z = config.getDouble("koth2.z");
                        String world = config.getString("koth2.world");

                        Location koth1 = new Location(Bukkit.getWorld(world), x, y, z);

                        arena.setKoth2(koth1);
                    }

                    arenas.add(arena);
                    available.add(arena);
                }
            }
        }

    }

    public void saveArenas() {

        /*
        Loop through all arenas and save the ones that dont exist
         */
        for(Arena arena : arenas) {

            file = new File(FreePractice.getInstance().getDataFolder() + "/Arenas", arena.getName() + ".yml");

            if(!file.exists()) {

                file = new File(FreePractice.getInstance().getDataFolder() + "/Arenas", arena.getName() + ".yml");

                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

                config.createSection("name");
                config.createSection("type");

                config.set("name", arena.getName());
                config.set("type", arena.getType().toString());

                /*
                Has loc1 been set
                 */
                if(arena.getLoc1() != null) {
                    config.createSection("loc1.x");
                    config.createSection("loc1.z");
                    config.createSection("loc1.y");
                    config.createSection("loc1.pitch");
                    config.createSection("loc1.yaw");
                    config.createSection("loc1.world");

                    config.set("loc1.x", arena.getLoc1().getX());
                    config.set("loc1.y", arena.getLoc1().getY());
                    config.set("loc1.z", arena.getLoc1().getZ());
                    config.set("loc1.pitch", arena.getLoc1().getPitch());
                    config.set("loc1.yaw", arena.getLoc1().getYaw());
                    config.set("loc1.world", arena.getLoc1().getWorld().getName());
                }

                /*
                Has loc2 been set
                 */
                if(arena.getLoc2() != null) {
                    config.createSection("loc2.x");
                    config.createSection("loc2.z");
                    config.createSection("loc2.y");
                    config.createSection("loc2.pitch");
                    config.createSection("loc2.yaw");
                    config.createSection("loc2.world");

                    config.set("loc2.x", arena.getLoc2().getX());
                    config.set("loc2.y", arena.getLoc2().getY());
                    config.set("loc2.z", arena.getLoc2().getZ());
                    config.set("loc2.pitch", arena.getLoc2().getPitch());
                    config.set("loc2.yaw", arena.getLoc2().getYaw());
                    config.set("loc2.world", arena.getLoc2().getWorld().getName());
                }

                /*
                Koth1 loc
                 */
                if(arena.getKoth1() != null) {
                    config.createSection("koth1.x");
                    config.createSection("koth1.z");
                    config.createSection("koth1.y");
                    config.createSection("koth1.world");

                    config.set("koth1.x", arena.getLoc1().getX());
                    config.set("koth1.y", arena.getLoc1().getY());
                    config.set("koth1.z", arena.getLoc1().getZ());
                    config.set("koth1.world", arena.getLoc1().getWorld().getName());
                }


                /*
                Koth2 loc
                 */
                if(arena.getKoth2() != null) {
                    config.createSection("koth2.x");
                    config.createSection("koth2.z");
                    config.createSection("koth2.y");
                    config.createSection("koth2.world");

                    config.set("koth2.x", arena.getLoc1().getX());
                    config.set("koth2.y", arena.getLoc1().getY());
                    config.set("koth2.z", arena.getLoc1().getZ());
                    config.set("koth2.world", arena.getLoc1().getWorld().getName());
                }

                try{
                    config.save(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {

                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

                config.set("name", arena.getName());
                config.set("type", arena.getType().toString());

                /*
                Updating loc1
                 */
                if(arena.getLoc1() != null) {

                    if(config.contains("loc1")) {
                        config.set("loc1.x", arena.getLoc1().getX());
                        config.set("loc1.y", arena.getLoc1().getY());
                        config.set("loc1.z", arena.getLoc1().getZ());
                        config.set("loc1.pitch", arena.getLoc1().getPitch());
                        config.set("loc1.yaw", arena.getLoc1().getYaw());

                        if(arena.getLoc1().getWorld() != null) {
                            config.set("loc1.world", arena.getLoc1().getWorld().getName());
                        }

                    } else {
                        config.createSection("loc1.x");
                        config.createSection("loc1.z");
                        config.createSection("loc1.y");
                        config.createSection("loc1.pitch");
                        config.createSection("loc1.yaw");
                        config.createSection("loc1.world");

                        config.set("loc1.x", arena.getLoc1().getX());
                        config.set("loc1.y", arena.getLoc1().getY());
                        config.set("loc1.z", arena.getLoc1().getZ());
                        config.set("loc1.pitch", arena.getLoc1().getPitch());
                        config.set("loc1.yaw", arena.getLoc1().getYaw());
                        config.set("loc1.world", arena.getLoc1().getWorld().getName());
                    }
                } else {
                    config.set("loc1", null);
                }


                /*
                Updating loc2
                 */
                if(arena.getLoc2() != null) {

                    if(config.contains("loc2")) {
                        config.set("loc2.x", arena.getLoc2().getX());
                        config.set("loc2.y", arena.getLoc2().getY());
                        config.set("loc2.z", arena.getLoc2().getZ());
                        config.set("loc2.pitch", arena.getLoc2().getPitch());
                        config.set("loc2.yaw", arena.getLoc2().getYaw());

                        if(arena.getLoc2().getWorld() != null) {
                            config.set("loc2.world", arena.getLoc2().getWorld().getName());
                        }
                    } else {
                        config.createSection("loc2.x");
                        config.createSection("loc2.z");
                        config.createSection("loc2.y");
                        config.createSection("loc2.pitch");
                        config.createSection("loc2.yaw");
                        config.createSection("loc2.world");

                        config.set("loc2.x", arena.getLoc2().getX());
                        config.set("loc2.y", arena.getLoc2().getY());
                        config.set("loc2.z", arena.getLoc2().getZ());
                        config.set("loc2.pitch", arena.getLoc2().getPitch());
                        config.set("loc2.yaw", arena.getLoc2().getYaw());
                        config.set("loc2.world", arena.getLoc2().getWorld().getName());
                    }
                } else {
                    config.set("loc2", null);
                }


                /*
                Updating Koth loc1
                 */

                if(arena.getKoth1() != null) {

                    if(config.contains("koth1")) {
                        config.set("koth1.x", arena.getKoth1().getX());
                        config.set("koth1.y", arena.getKoth1().getY());
                        config.set("koth1.z", arena.getKoth1().getZ());

                        if(arena.getKoth1().getWorld() != null) {
                            config.set("koth1.world", arena.getKoth1().getWorld().getName());
                        }
                    } else {
                        config.createSection("koth1.x");
                        config.createSection("koth1.z");
                        config.createSection("koth1.y");
                        config.createSection("koth1.world");

                        config.set("koth1.x", arena.getKoth1().getX());
                        config.set("koth1.y", arena.getKoth1().getY());
                        config.set("koth1.z", arena.getKoth1().getZ());
                        config.set("koth1.world", arena.getKoth1().getWorld().getName());
                    }
                } else {
                    config.set("koth1", null);
                }


                /*
                Updating koth2 loc
                 */

                if(arena.getKoth2() != null) {

                    if(config.contains("koth1")) {
                        config.set("koth2.x", arena.getKoth2().getX());
                        config.set("koth2.y", arena.getKoth2().getY());
                        config.set("koth2.z", arena.getKoth2().getZ());

                        if(arena.getKoth2().getWorld() != null) {
                            config.set("koth2.world", arena.getKoth2().getWorld().getName());
                        }
                    } else {
                        config.createSection("koth2.x");
                        config.createSection("koth2.z");
                        config.createSection("koth2.y");
                        config.createSection("koth2.world");

                        config.set("koth2.x", arena.getKoth2().getX());
                        config.set("koth2.y", arena.getKoth2().getY());
                        config.set("koth2.z", arena.getKoth2().getZ());
                        config.set("koth2.world", arena.getKoth2().getWorld().getName());
                    }
                } else {
                    config.set("koth2", null);
                }

                try{
                    config.save(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }

    }

    public ArrayList<Arena> getArenaByType(ArenaType type) {

        ArrayList<Arena> list = new ArrayList<Arena>();

        for(Arena arena : available) {
            if(arena.getType() == type) {
                list.add(arena);
            }
        }

        return list;
    }

    public Arena getRandomArena(ArenaType type) {

        if(getArenaByType(type).size() > 0) {


            int rand = random.nextInt(getArenaByType(type).size());

            return getArenaByType(type).get(rand);

        }

        return null;
    }

    public Arena getArenaByName(String name) {

        for(Arena arena : arenas) {
            if(arena.getName().equalsIgnoreCase(name)) {
                return arena;
            }
        }

        return null;
    }

    public boolean doesArenaExist(String name) {

        for(Arena arena : arenas) {
            if(arena.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }

        return false;
    }
}
