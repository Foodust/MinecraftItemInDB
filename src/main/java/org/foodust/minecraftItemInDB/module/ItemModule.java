package org.foodust.minecraftItemInDB.module;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.foodust.minecraftItemInDB.MinecraftItemInDB;
import org.foodust.minecraftItemInDB.db.entity.ItemEntity;
import org.foodust.minecraftItemInDB.db.repository.ItemRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

    public ItemEntity saveItem(ItemStack item, Long id) throws IOException {
        ItemEntity itemEntity = itemRepository.findById(id).orElse(null);
        if (itemEntity == null) return null;
        byte[] bytes = serializeItem(item);
        itemEntity.setMaterial(item.getType().toString());
        itemEntity.setItemBlob(bytes);

        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            if (itemMeta.hasDisplayName()) {
                itemEntity.setDisplayName(itemMeta.getDisplayName());
            }
            if (itemMeta.hasLore()) {
                itemEntity.setLore(itemMeta.getLore());
            }
            if (itemMeta.hasCustomModelData()) {
                itemEntity.setCustomModelData(itemMeta.getCustomModelData());
            }
        }
        return itemRepository.save(itemEntity);
    }

    public ItemEntity saveItem(ItemStack item) throws IOException {
        ItemEntity.ItemEntityBuilder builder = ItemEntity.builder();
        builder.material(item.getType().toString());
        byte[] bytes = serializeItem(item);
        builder.itemBlob(bytes);

        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            if (itemMeta.hasDisplayName()) {
                builder.displayName(itemMeta.getDisplayName());
            }
            if (itemMeta.hasLore()) {
                builder.lore(itemMeta.getLore());
            }
            if (itemMeta.hasCustomModelData()) {
                builder.customModelData(itemMeta.getCustomModelData());
            }
        }
        return itemRepository.save(builder.build());
    }

    public ItemEntity getInfo(ItemStack item) {
        return itemRepository.findOne(matches(item)).orElse(null);
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

    public List<ItemStack> getItemStackByDisplayName(String displayName) {
        List<ItemStack> items = new ArrayList<>();
        List<ItemEntity> byDisplayName = itemRepository.findByDisplayName(displayName);
        byDisplayName.forEach(entity -> {
            try {
                ItemStack newItem = deserializeItem(entity.getItemBlob());
                items.add(newItem);
            } catch (Exception ignore) {
            }
        });
        return items;
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

    public Specification<ItemEntity> matches(ItemStack item) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            String material = item.getType().toString();
            ItemMeta meta = item.getItemMeta();
            if (material != null) {
                predicates.add(criteriaBuilder.equal(root.get("material"), material));
            }
            if (meta != null) {
                if (meta.hasDisplayName()) {
                    predicates.add(criteriaBuilder.equal(root.get("display_name"), meta.getDisplayName()));
                }

                if (meta.hasCustomModelData()) {
                    predicates.add(criteriaBuilder.equal(root.get("custom_model_data"), meta.getCustomModelData()));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
