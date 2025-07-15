package org.foodust.minecraftItemInDB.module;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.foodust.minecraftItemInDB.MinecraftItemInDB;
import org.foodust.minecraftItemInDB.db.entity.ItemEntity;
import org.foodust.minecraftItemInDB.db.repository.ItemRepository;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Component
public class ItemModule {
    private final MinecraftItemInDB plugin;
    private final ItemRepository itemRepository;

    public ItemModule(MinecraftItemInDB plugin) {
        this.plugin = plugin;
        this.itemRepository = plugin.getItemRepository();
        initialize();
    }

    public void initialize() {
        ItemData.Items.clear();
        List<ItemEntity> all = itemRepository.findAll();
        HashMap<Long, ItemEntity> items = new HashMap<>();
        for (ItemEntity item : all) {
            items.put(item.getId(), item);
        }
        ItemData.Items = items;
    }

    public void saveItem(ItemStack item, Boolean force) throws IOException {


        byte[] bytes = serializeItem(item);
    }

    public ItemStack getItemStackByMaterial(String material) {
        return null;
    }

    public ItemStack getItemStackByMaterial(Material material) {
        return null;
    }

    public ItemStack getItemStackById(Long id) {
        return null;
    }

    public ItemStack getItemStackByDisplayName(String displayName) {
        return null;
    }

    // 저장
    public byte[] serializeItem(ItemStack item) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream)) {
            dataOutput.writeObject(item);
            return outputStream.toByteArray();
        }
    }

    // 복원
    public ItemStack deserializeItem(byte[] data) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
             BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream)) {
            return (ItemStack) dataInput.readObject();
        }
    }
}
