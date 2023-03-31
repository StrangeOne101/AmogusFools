package com.strangeone101.amongusfools.amogusfools;

import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.AvatarAbility;
import com.projectkorra.projectkorra.ability.CoreAbility;
import com.projectkorra.projectkorra.attribute.Attribute;
import net.raphimc.noteblocklib.player.SongPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class AmogusFools extends AvatarAbility implements AddonAbility {

    @Attribute(Attribute.COOLDOWN)
    private long cooldown = 5000;
    @Attribute(Attribute.DURATION)
    private long duration = 32_500;
    private Location location;
    private float yaw;

    private static SongManager manager;
    private SongPlayer song;

    public AmogusFools(Player player) {
        super(player);

        if (CoreAbility.hasAbility(player, getClass())) return;

        if (bPlayer.canBendIgnoreBinds(this)) {
            start();
        } else return;



        int[] distances = {5, 6, 4, 7, 3, 2, 1};
        int[] heights = {0, 1, -1, 2, -2, 3, -3, 4, -4};

        exit:
        for (int d : distances) {
            location = player.getLocation().clone();
            Vector reverse = player.getEyeLocation().getDirection().multiply(-d);
            reverse.setY(0);
            location.add(reverse);
            yaw = player.getLocation().getYaw();

            for (int h : heights) {
                Location moved = location.clone().add(0, h, 0);
                boolean curr = moved.getBlock().getType().isSolid() || moved.getBlock().isLiquid();
                boolean below = moved.getBlock().getRelative(BlockFace.DOWN).getType().isSolid() || moved.getBlock().getRelative(BlockFace.DOWN).isLiquid();

                if (!curr && below) {
                    location = moved;
                    break exit;
                }
            }
        }

        if (location == null) { //Could not find a suitable place to put amogus
            remove();
        } else {
            song = manager.playAt(player.getLocation());
        }
    }

    @Override
    public void progress() {
        if (!bPlayer.canBendIgnoreBinds(this)) {
            remove();
            return;
        }
        if (this.getRunningTicks() % 60 == 0) {
            PixelArt.displayAmogus(location, yaw, 0);
        }

        if (this.getStartTime() + duration < System.currentTimeMillis()) {
            remove();
        }
    }

    @Override
    public void remove() {
        super.remove();
        bPlayer.addCooldown(this);
        song.stop();
    }

    @Override
    public boolean isSneakAbility() {
        return true;
    }

    @Override
    public boolean isHarmlessAbility() {
        return false;
    }

    @Override
    public long getCooldown() {
        return cooldown;
    }

    @Override
    public String getName() {
        return "SUS";
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void load() {
        Bukkit.getPluginManager().registerEvents(new AmongusListener(), ProjectKorra.plugin);

        manager = new SongManager();

        ProjectKorra.log.info("SUS " + getVersion() + " by StrangeOne101 enabled!");
    }

    @Override
    public void stop() {

    }

    @Override
    public String getAuthor() {
        return "StrangeOne101";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }
}
