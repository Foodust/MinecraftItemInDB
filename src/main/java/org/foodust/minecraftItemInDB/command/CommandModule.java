package org.foodust.minecraftItemInDB.command;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.foodust.minecraftItemInDB.MinecraftItemInDB;
import org.foodust.minecraftItemInDB.db.entity.ItemEntity;
import org.foodust.minecraftItemInDB.module.ItemModule;

public class CommandModule {

    private final MinecraftItemInDB plugin;
    private final ItemModule itemModule;

    public CommandModule(MinecraftItemInDB plugin) {
        this.plugin = plugin;
        this.itemModule = plugin.getItemModule();
    }

    public void commandSet(CommandSender sender, String[] data) {
        if (!(sender instanceof Player player)) return;
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR) return;
        ItemEntity itemEntity = null;
        try {
            if (data.length > 1) {
                String stringId = data[0];
                long id = Long.parseLong(stringId);
                itemEntity = itemModule.saveItem(item, id);
            } else {
                itemEntity = itemModule.saveItem(item);
            }
        } catch (Exception exception) {
            sender.sendMessage(exception.getMessage());
        }
        if (itemEntity != null) {
            sender.sendMessage(String.format("§r아이템이 설정됨 - id : %d", itemEntity.getId()));
        }
    }

    public void commandGet(CommandSender sender, String[] data) {

    }

    public void commandInfo(CommandSender sender, String[] data) {


        sender.sendMessage("========정보=======");
        sender.sendMessage();
        sender.sendMessage();
        sender.sendMessage("==================");
    }

    public void commandReload(CommandSender sender, String[] data) {
        plugin.getItemModule().initialize();
        sender.sendMessage("리로드 되었습니다.");
    }
}
