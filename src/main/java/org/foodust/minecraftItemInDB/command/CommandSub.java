package org.foodust.minecraftItemInDB.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.foodust.minecraftItemInDB.Message.BaseMessage;
import org.foodust.minecraftItemInDB.module.ItemData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandSub implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        // 첫 번째 인자 자동완성
        if (args.length == 1) {
            completions.add(BaseMessage.COMMAND_GET.getMessage());
            completions.add(BaseMessage.COMMAND_SET.getMessage());
            completions.add(BaseMessage.COMMAND_RELOAD.getMessage());
            return filterCompletions(completions, args[0]);
        }
        if (args.length == 2 && (args[0].equalsIgnoreCase(BaseMessage.COMMAND_SET.getMessage()))) {
            ItemData.Items.forEach((aLong, itemEntity) -> {
                completions.add(aLong.toString());
            });
            return filterCompletions(completions, args[1]);
        }

        if (args.length == 2 && (args[0].equalsIgnoreCase(BaseMessage.COMMAND_GET.getMessage()))) {
            ItemData.Items.forEach((aLong, itemEntity) -> {
                completions.add(itemEntity.getDisplayName());
            });
            return filterCompletions(completions, args[1]);
        }

        return completions;
    }

    // 입력된 접두어로 시작하는 항목만 필터링
    private List<String> filterCompletions(List<String> completions, String prefix) {
        return completions.stream()
                .filter(s -> s.toLowerCase().startsWith(prefix.toLowerCase()))
                .collect(Collectors.toList());
    }
}