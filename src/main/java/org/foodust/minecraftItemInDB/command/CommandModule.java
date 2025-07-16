package org.foodust.minecraftItemInDB.command;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.foodust.minecraftItemInDB.MinecraftItemInDB;
import org.foodust.minecraftItemInDB.db.entity.ItemEntity;
import org.foodust.minecraftItemInDB.module.ItemModule;

import java.util.List;
import java.util.Objects;

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
            itemModule.initialize();
        }
    }

    public void commandGet(CommandSender sender, String[] data) {
        if (!(sender instanceof Player player)) return;
        if (data.length < 1) return;
        String displayName = data[0];
        List<ItemStack> items = itemModule.getItemStackByDisplayName(displayName);
        player.getInventory().addItem(items.toArray(new ItemStack[0]));
    }

    public void commandInfo(CommandSender sender, String[] data) {
        if (!(sender instanceof Player player)) return;
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR) return;
        ItemMeta meta = item.getItemMeta();
        sender.sendMessage("======== 아이템 정보 ========");
        sender.sendMessage("아이템 종류: " + item.getType().name());
        if (meta != null && meta.hasDisplayName()) {
            sender.sendMessage("표시 이름: " + meta.getDisplayName());
        } else {
            sender.sendMessage("표시 이름: " + "없음");
        }
        if (meta != null && meta.hasCustomModelData()) {
            sender.sendMessage("모델 데이터: " + meta.getCustomModelData());
        } else {
            sender.sendMessage("모델 데이터: 없음");
        }
        if (meta != null && meta.hasLore()) {
            sender.sendMessage("아이템 설명:");
            for (String line : Objects.requireNonNull(meta.getLore())) {
                sender.sendMessage("- " + line);
            }
        } else {
            sender.sendMessage("아이템 설명: 없음");
        }
        sender.sendMessage("============================");
    }

    public void commandReload(CommandSender sender, String[] data) {
        plugin.getItemModule().initialize();
        sender.sendMessage("리로드 되었습니다.");
    }
}
