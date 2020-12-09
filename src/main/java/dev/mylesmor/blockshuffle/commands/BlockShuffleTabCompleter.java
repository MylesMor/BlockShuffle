package dev.mylesmor.blockshuffle.commands;

import dev.mylesmor.blockshuffle.BlockShuffle;
import dev.mylesmor.blockshuffle.util.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BlockShuffleTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (args.length == 1) {
                ArrayList<String> commands = new ArrayList<>();
                if (p.hasPermission(Permissions.JOIN)) {
                    commands.add("join");
                }
                if (p.hasPermission(Permissions.START)) {
                    commands.add("start");
                }
                if (p.hasPermission(Permissions.SKIP)) {
                    commands.add("join");
                }
                return commands;
            }
            if (args.length == 2) {
                if ("start".equalsIgnoreCase(args[0])) {
                    return new ArrayList<>(BlockShuffle.games.keySet());
                }
            }
        }
        return null;
    }
}
