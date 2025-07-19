package org.foodust.minecraftItemInDB.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.foodust.minecraftItemInDB.Message.BaseMessage;
import org.foodust.minecraftItemInDB.MinecraftItemInDB;

import java.util.Objects;

public class CommandManager implements CommandExecutor {

    private final MinecraftItemInDB plugin;
    private final CommandModule commandModule;

    public CommandManager(MinecraftItemInDB plugin) {
        this.plugin = plugin;
        this.commandModule = new CommandModule(plugin);
        Objects.requireNonNull(plugin.getCommand(BaseMessage.COMMAND_ITEM.getMessage())).setExecutor(this);
        Objects.requireNonNull(plugin.getCommand(BaseMessage.COMMAND_ITEM.getMessage())).setTabCompleter(new CommandSub());
    }

    @Override
    public boolean onCommand( CommandSender commandSender, Command command, String label, String[] args) {
        if (!label.equalsIgnoreCase(BaseMessage.COMMAND_ITEM.getMessage())) {
            return false;
        }
        String subCommand = args[0];
        BaseMessage byMessage = BaseMessage.getByMessage(subCommand);
        switch (byMessage) {
            case COMMAND_GET -> commandModule.commandGet(commandSender, args);
            case COMMAND_SET -> commandModule.commandSet(commandSender, args);
            case COMMAND_INFO -> commandModule.commandInfo(commandSender, args);
            case COMMAND_RELOAD -> commandModule.commandReload(commandSender, args);
        }
        return false;
    }
}
