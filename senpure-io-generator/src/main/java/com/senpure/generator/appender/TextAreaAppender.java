package com.senpure.generator.appender;

import javafx.scene.control.TextArea;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.Serializable;

/**
 * Created by 罗中正 on 2017/6/8.
 */
@Plugin(name = "TextArea", category = "Core", elementType = "appender", printObject = true)
public class TextAreaAppender extends AbstractAppender {
    private static TextArea textArea;

    public static  void celar()
    {
        textArea.clear();
    }
    public static void setTextArea(TextArea textArea) {
        TextAreaAppender.textArea = textArea;
    }

    @Override
    public void append(LogEvent event) {

        if (textArea != null) {

            textArea.insertText(0,new String(getLayout().toByteArray(event)));
            //textArea.appendText(     new String(getLayout().toByteArray(event)));
        }
    }

    public TextAreaAppender(String name, Filter filter, Layout<? extends Serializable> layout) {
        super(name, filter, layout);
    }

    @PluginFactory
    public static TextAreaAppender createAppender(@PluginAttribute("name") String name,
                                                  @PluginElement("Filter") final Filter filter,
                                                  @PluginElement("Layout") Layout<? extends Serializable> layout) {

        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new TextAreaAppender(name, filter, layout);
    }


}
