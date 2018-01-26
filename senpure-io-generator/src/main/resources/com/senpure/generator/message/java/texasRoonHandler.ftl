package ${pack}.handler;

import ${pack}.message.${type}${name}Message;
import com.senpure.io.message.AbsMessageHandler;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

/**<#if hasExplain>
 * ${explain}处理器
 * </#if>
 * @author senpure-generator
 * @version ${.now?datetime}
 */
@Component
public class ${type}${name}MessageHandler extends RoomHandler<${type}${name}Message> {

    @Override
    public void execute(Channel channel, ${type}${name}Message message,int playerId,RoomLogic room) {
        //TODO 请在这里写下你的代码

    }

    @Override
    public int handlerId() {
    return ${id?c};
    }

    @Override
    public ${type}${name}Message getEmptyMessage() {
    return new ${type}${name}Message();
    }

    }