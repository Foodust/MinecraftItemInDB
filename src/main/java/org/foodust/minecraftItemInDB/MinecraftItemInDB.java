package org.foodust.minecraftItemInDB;

import lombok.Getter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.foodust.minecraftItemInDB.db.repository.ItemRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Getter
public final class MinecraftItemInDB extends JavaPlugin {

    private Plugin plugin;
    private ItemRepository itemRepository;
    // Spring 컨텍스트
    private AnnotationConfigApplicationContext context;
    @Override
    public void onEnable() {
        // Plugin startup logic
        this.plugin = this;

        // Spring 컨텍스트 초기화
        try {
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
            context = new AnnotationConfigApplicationContext();
            context.scan("org.foodust.minecraftItemInDB");
            context.refresh();

            itemRepository = (ItemRepository) context.getBean("itemRepository");


            getLogger().info("데이터베이스 연결 성공!");
        } catch (Exception e) {
            getLogger().severe("데이터베이스 연결 실패: " + e.getMessage());
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
