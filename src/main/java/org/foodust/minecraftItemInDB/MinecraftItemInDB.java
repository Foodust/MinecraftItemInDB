package org.foodust.minecraftItemInDB;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.foodust.minecraftItemInDB.command.CommandManager;
import org.foodust.minecraftItemInDB.db.repository.ItemRepository;
import org.foodust.minecraftItemInDB.module.ItemModule;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Getter
public final class MinecraftItemInDB extends JavaPlugin {

    private static MinecraftItemInDB plugin;
    private ItemRepository itemRepository;
    private ItemModule itemModule;
    // Spring 컨텍스트
    private AnnotationConfigApplicationContext context;

    public static MinecraftItemInDB getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;


        // Spring 컨텍스트 초기화
        try {
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
            context = new AnnotationConfigApplicationContext();
            context.scan("org.foodust.minecraftItemInDB");
            context.refresh();

            itemRepository = context.getBean(ItemRepository.class);
            itemModule = context.getBean(ItemModule.class);

            CommandManager commandManager = new CommandManager(this);

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
