package org.foodust.minecraftItemInDB.Message;

import lombok.Getter;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum BaseMessage {

    // prefix
    PREFIX(""),
    PREFIX_C(""),

    // command
    COMMAND_ITEM("아이템"),
    COMMAND_SET("등록"),
    COMMAND_GET("가져오기"),
    COMMAND_INFO("정보"),
    COMMAND_RELOAD("리로드"),
    // 기본
    DEFAULT("기본"),
    // Error
    ERROR("에러"),
    ERROR_COMMAND("잘못된 명령어입니다.")
    ;

    private final String message;

    BaseMessage(String message) {
        this.message = message;
    }

    private static final Map<String, BaseMessage> commandInfo = new HashMap<>();

    static {
        for (BaseMessage baseMessage : EnumSet.range(COMMAND_ITEM,COMMAND_RELOAD)) {
            commandInfo.put(baseMessage.message, baseMessage);
        }
    }

    public static BaseMessage getByMessage(String message) {
        return commandInfo.getOrDefault(message, ERROR);
    }

    public String format(Object... args) {
        return String.format(message, args);
    }
}