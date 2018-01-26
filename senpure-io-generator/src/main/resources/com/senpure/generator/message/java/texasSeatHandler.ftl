package ${pack}.handler;

import ${pack}.message.${type}${name}Message;
import com.jhd.game.texasholdem.logic.RoomLogic;
import com.jhd.game.texasholdem.logic.Seat;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

/**<#if hasExplain>
 * ${explain}处理器
 * </#if>
 * @author senpure-generator
 * @version ${.now?datetime}
 */
@Component
public class ${type}${name}MessageHandler extends SeatHandler<${type}${name}Message> {

    @Override
    public void execute(Channel channel, ${type}${name}Message message,int playerId,RoomLogic room,Seat seat) {
        //auto Generator
        room.player${name}(message,seat);

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