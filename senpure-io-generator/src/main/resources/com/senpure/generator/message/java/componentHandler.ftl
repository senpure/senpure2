package ${pack}.handler;

import ${pack}.message.${type}${name}Message;
import com.senpure.io.handler.AbstractComponentMessageHandler;
import io.netty.channel.Channel;

/**<#if hasExplain>
 * ${explain}处理器
 *</#if>
 * @author senpure-generator
 * @version ${.now?datetime}
 */
public class ${type}${name}MessageHandler extends AbstractComponentMessageHandler<${type}${name}Message> {

    @Override
    public void execute(Channel channel, int token, int playerId, ${type}${name}Message message) {
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