package ${pack}.handler;

import ${javaPack}.${type}${name}Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lhxy.game.core.annotation.ReqMsgHandler;
import com.lhxy.game.server.handler.ReqMessageHandler;
import com.lhxy.game.player.struct.Player;
import com.lhxy.game.message.core.ReqMessage;

/**<#if hasExplain>
 * ${explain}处理器
 * </#if>
 * @author senpure-generator
 * @version ${.now?datetime}
 */
@ReqMsgHandler(${id?c})
public class ${type}${name}MessageHandler implements ReqMessageHandler {

    private Logger logger = LoggerFactory.getLogger(${type}${name}MessageHandler.class);

    @Override
    public void action(Player player, ReqMessage reqMessage) {
        ${type}${name}Message  message = (${type}${name}Message) reqMessage;
        //TODO 请在这里写下你的代码

    }

}