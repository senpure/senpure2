package com.senpure.io;


import com.senpure.base.util.Assert;
import com.senpure.io.message.ComponentMessageHandler;
import com.senpure.io.message.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 罗中正 on 2017/5/27.
 */
public class ComponentMessageHandlerUtil {
    private static Map<Integer, ComponentMessageHandler> handlerMap = new HashMap<>();
    private static ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    public static void execute(Runnable runnable) {
        service.execute(runnable);
    }

    public static void execute(int playerId, Message message) {

        service.execute(() -> handlerMap.get(message.getMessageId()).execute(playerId, message));
    }
    public static ComponentMessageHandler getHandler(int messageId) {
        return handlerMap.get(messageId);
    }
    public static void regMessageHandler(ComponentMessageHandler handler) {
        Assert.isNull(handlerMap.get(handler.handlerId()), handler.handlerId()+" -> " + handler.getEmptyMessage().getClass().getName() + "  处理程序已经存在");
        handlerMap.put(handler.handlerId(), handler);
    }

    public static Message getEmptyMessage(int messageId) {
        ComponentMessageHandler handler = handlerMap.get(messageId);
        if (handler == null) {
            return null;
        }
        return handler.getEmptyMessage();
    }
}
