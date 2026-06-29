package com.staehc_remerpus.emerpus.main.utils;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.Team;
import net.minecraft.ChatFormatting;
import java.util.ArrayList;
import java.util.List;

public class f {

    private static List<String> friends = new ArrayList<>();
    private static PlayerTeam friendTeam = null;

    public static void addFriend(String name) {
        if (!friends.contains(name)) {
            friends.add(name);
            updateFriendTeam();
        }
    }

    public static void removeFriend(String name) {
        friends.remove(name);
        updateFriendTeam();
    }

    public static boolean isFriend(Player player) {
        return friends.contains(player.getName().getString());
    }

    public static boolean isFriend(String name) {
        return friends.contains(name);
    }

    public static List<String> getFriends() {
        return new ArrayList<>(friends);
    }

    public static void clearFriends() {
        friends.clear();
        updateFriendTeam();
    }

    private static void updateFriendTeam() {
        // This will be called when friends list changes
        // The actual team creation happens when needed
    }

    public static PlayerTeam getOrCreateFriendTeam(Scoreboard scoreboard) {
        if (friendTeam == null || !scoreboard.getTeamNames().contains("s_f")) {
            // Remove old team if it exists but is invalid
            if (scoreboard.getTeamNames().contains("s_f")) {
                scoreboard.removePlayerTeam(scoreboard.getPlayerTeam("s_f"));
            }
            friendTeam = scoreboard.addPlayerTeam("s_f");
            friendTeam.setColor(ChatFormatting.GREEN);
            friendTeam.setNameTagVisibility(Team.Visibility.NEVER);
        }
        return friendTeam;
    }

    public static void addToFriendTeam(Player player) {
        if (player == null || player.getScoreboard() == null) return;
        PlayerTeam team = getOrCreateFriendTeam(player.getScoreboard());
        player.getScoreboard().addPlayerToTeam(player.getName().getString(), team);
    }

    public static void removeFromFriendTeam(Player player) {
        if (player == null || player.getScoreboard() == null) return;
        player.getScoreboard().removePlayerFromTeam(player.getName().getString());
    }
}