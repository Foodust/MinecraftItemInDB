package org.foodust.minecraftItemInDB.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.foodust.minecraftItemInDB.Message.BaseMessage;
import org.foodust.minecraftItemInDB.MinecraftItemInDB;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CommandManager implements CommandExecutor {

    private final MinecraftItemInDB plugin;

    public CommandManager(MinecraftItemInDB plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand(BaseMessage.COMMAND_ITEM.getMessage())).setExecutor(this);
        Objects.requireNonNull(plugin.getCommand(BaseMessage.COMMAND_ITEM.getMessage())).setTabCompleter(new CommandSub());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!label.equalsIgnoreCase(BaseMessage.COMMAND_ITEM.getMessage())) {
            return false;
        }
        String subCommand = args[0];
        BaseMessage byMessage = BaseMessage.getByMessage(subCommand);
        switch (byMessage) {
            case COMMAND_GET -> {

            }
            case COMMAND_SET -> {

            }
            case COMMAND_RELOAD -> {
                plugin.getItemModule().initialize();
                commandSender.sendMessage("리로드 되었습니다.");
            }
        }
        return false;
    }
}
