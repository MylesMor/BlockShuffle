package dev.mylesmor.blockshuffle.game;

import dev.mylesmor.blockshuffle.BlockShuffle;
import dev.mylesmor.blockshuffle.util.ScoreboardSign;
import dev.mylesmor.blockshuffle.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BlockShuffleBoard {

    public HashMap<Player, ScoreboardSign> boards = new HashMap<>();

    public BlockShuffleBoard() {
        updateScoreboard();
    };


    public void setScoreboard(Player p) {
        ScoreboardSign sb = new ScoreboardSign(p, Util.prefix);
        sb.create();
        sb.setLine(1, " ");
        boards.put(p, sb);
    }

    public void destroyBoards() {
        for (Map.Entry<Player, ScoreboardSign> entry : boards.entrySet()) {
            entry.getValue().destroy();
        }
    }

    public void updateScoreboard() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(BlockShuffle.plugin, new Runnable() {
            @Override
            public void run() {
                for (Map.Entry<Player, ScoreboardSign> entry : boards.entrySet()) {
                    ScoreboardSign sb = entry.getValue();
                    //TODO Add spectator scoreboard
                    if (BlockShuffle.game != null) {
                        sb.setLine(12, ChatColor.GRAY + "Round: " + ChatColor.YELLOW + BlockShuffle.game.getRound() + ChatColor.GRAY + "/" + BlockShuffle.game.getMaxNumberRounds());
                        sb.setLine(11, "" + ChatColor.BLACK);
                        sb.setLine(10, "" + ChatColor.BLUE);
                        sb.setLine(9, "" + ChatColor.GRAY + "BLOCK TO FIND: ");
                        sb.setLine(8, getCurrentBlock(entry.getKey()));
                        sb.setLine(7, "   ");
                        sb.setLine(6, "    ");
                        sb.setLine(5, ChatColor.GRAY + "Score: " + ChatColor.LIGHT_PURPLE + BlockShuffle.scores.get(entry.getKey()));
                        sb.setLine(4, ChatColor.GRAY + "Found players: " + ChatColor.GREEN + getFoundPlayers());
                        sb.setLine(3, getPlayersRemaining());
                        sb.setLine(2, "     ");
                        sb.setLine(1, getTimeRemaining());
                    } else {
                        sb.setLine(11, " ");
                        sb.setLine(10, "  ");
                        sb.setLine(9, "   ");
                        sb.setLine(8, "    ");
                        sb.setLine(7, "     ");
                        sb.setLine(6, "      ");
                        sb.setLine(5, "       ");
                        sb.setLine(4, "        ");
                        sb.setLine(3, "         ");
                        sb.setLine(2, "          ");
                        sb.setLine(1, ChatColor.GRAY + "Players: " + ChatColor.YELLOW + BlockShuffle.players.size());
                    }
                }
            }
        }, 0L, 5L);
    }

    public String getTimeRemaining() {
        int minute = (int) TimeUnit.SECONDS.toMinutes(BlockShuffle.game.getTimeRemaining());
        int second = BlockShuffle.game.getTimeRemaining() - (minute * 60);
        String timeString;
        if (minute > 3) {
            timeString = String.format(ChatColor.GRAY + "Time remaining: " + ChatColor.GREEN + ChatColor.BOLD + "%02d:%02d", minute, second);
        } else if (minute > 0 && minute < 3) {
            timeString = String.format(ChatColor.GRAY + "Time remaining: " + ChatColor.YELLOW + ChatColor.BOLD + "%02d:%02d", minute, second);
        } else {
            timeString = String.format(ChatColor.GRAY + "Time remaining: " + ChatColor.RED + ChatColor.BOLD + "%02d:%02d", minute, second);
        }
        return timeString;
    }

    public int getFoundPlayers() {
        int number = 0;
        for (boolean found : BlockShuffle.players.values()) {
            if (found) {
                number += 1;
            }
        }
        return number;
    }

    public String getPlayersRemaining() {
        if (BlockShuffle.game.getElimination()) {
            return ChatColor.GRAY + "Players remaining: " + ChatColor.YELLOW + BlockShuffle.players.size();
        }
        return "";
    }

    public String getCurrentBlock(Player p) {
        if (BlockShuffle.players.get(p)) {
            return "" + ChatColor.YELLOW + ChatColor.BOLD + BlockShuffle.game.getCurrentBlock().name().replace("_", " ") + ChatColor.GRAY + " - " + ChatColor.GREEN + ChatColor.BOLD + "FOUND!";
        } else {
            return "" + ChatColor.YELLOW + ChatColor.BOLD + BlockShuffle.game.getCurrentBlock().name().replace("_", " ");
        }
    }

}
