package dev.mylesmor.blockshuffle.commands;

import dev.mylesmor.blockshuffle.BlockShuffle;
import dev.mylesmor.blockshuffle.data.Status;
import dev.mylesmor.blockshuffle.util.Permissions;
import dev.mylesmor.blockshuffle.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class Skip {

    /**
     * Allows a player to skip the current round.
     * @param p The player running the command.
     * @param args Not required.
     */
    public static void skip(Player p, String[] args) {
        if (p.hasPermission(Permissions.SKIP)) {
            BlockShuffle.game.skip();
        } else {
            Util.blockShuffleMessage(p, ChatColor.RED, "You don't have permission to use this command!", null);
        }
    }
}
