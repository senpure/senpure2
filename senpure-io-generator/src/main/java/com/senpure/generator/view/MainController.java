package com.senpure.generator.view;


import com.senpure.base.util.StringUtil;
import com.senpure.generator.*;
import com.senpure.generator.appender.TextAreaAppender;
import com.senpure.generator.controller.FileConverter;
import com.senpure.generator.controller.Welcome;
import com.senpure.generator.message.*;
import com.senpure.generator.model.FileData;
import com.senpure.generator.model.MessageData;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateModelException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by 罗中正 on 2017/6/7.
 */
public class MainController implements Initializable {
    private Logger logger = LoggerFactory.getLogger(MainController.class);

    @FXML
    private TextArea textAreaLog;
    @FXML
    private TableView<FileData> messageView;
    @FXML
    private TableColumn<FileData, String> tableName;
    @FXML
    private TableColumn<FileData, String> tablePath;

    @FXML
    private TableView<MessageData> messageCodeView;
    @FXML
    private TableColumn<MessageData, String> tableMessageName;
    @FXML
    private TableColumn<MessageData, Boolean> tableMessageCheckBok;
    @FXML
    private TableColumn<MessageData, String> tableMessageType;
    @FXML
    private TableColumn<MessageData, String> tableMessageExplain;

    @FXML
    private TextField pathPart;
    @FXML
    private TextField pathBean;
    @FXML
    private TextField pathMessage;
    @FXML
    private TextField pathHandler;

    @FXML
    private TextField javaServerCodeRootPath;
    @FXML
    private TextField javaAICodeRootPath;
    @FXML
    private ChoiceBox<File> javaHandler;
    @FXML
    private ChoiceBox<File> javaMessage;
    @FXML
    private ChoiceBox<File> javaBean;

    @FXML
    private CheckBox javaBeanCheckBox;
    @FXML
    private CheckBox javaMessageCheckBox;
    @FXML
    private CheckBox javaHandlerCheckBox;
    @FXML
    private CheckBox javaHandlerCoverCheckBox;

    @FXML
    private TextField luaCodeRootPath;
    @FXML
    private ChoiceBox<File> luaHandler;
    @FXML
    private ChoiceBox<File> luaMessage;

    @FXML
    private CheckBox luaMessageCheckBox;
    @FXML
    private CheckBox luaHandlerCheckBox;
    @FXML
    private CheckBox luaHandlerCoverCheckBox;

    private FileChooser messageChooser;
    private DirectoryChooser messageDirectoryChooser;
    private DirectoryChooser javaServerCodePathChooser;
    private DirectoryChooser javaAICodePathChooser;
    private DirectoryChooser luaCodePathChooser;
    private Set<File> messageFiles = new HashSet<>();
    // private List<XmlMessage> xmlMessages = new ArrayList<>();
    private Map<String, XmlMessage> xmlMessageMap = new HashMap<>();
    // private boolean beanChoose, messageChoose, handlerChoose;
    private boolean messagePreview;

    private Habit habit;
    private boolean messageAllChoose = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TextAreaAppender.setTextArea(textAreaLog);
        Welcome.welcome();
        habit = HabitUtil.loadHabit();
        GeneratorContext.setHabit(habit);
        GeneratorContext.setMainController(this);
        tableName.setCellValueFactory(param -> param.getValue().nameProperty());
        tablePath.setCellValueFactory(param -> param.getValue().pathProperty());
        tableMessageName.setCellValueFactory(param -> param.getValue().nameProperty());
        tableMessageCheckBok.setCellFactory(CheckBoxTableCell.forTableColumn(tableMessageCheckBok));
        tableMessageCheckBok.setCellValueFactory(param -> param.getValue().generateProperty());
        tableMessageType.setCellValueFactory(param -> param.getValue().typeProperty());

        tableMessageExplain.setCellValueFactory(param -> param.getValue().explainProperty());

        javaServerCodeRootPath.setText(habit.getJavaServerCodeRootPath());
        javaAICodeRootPath.setText(habit.getJavaAICodeRootPath());
        luaCodeRootPath.setText(habit.getLuaCodeRootPath());

        initChooser();
        initTemplate();
    }

    private void initChooser() {
        messageChooser = new FileChooser();
        messageChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("message", "*message.xml"),
                new FileChooser.ExtensionFilter("xml", "*.xml")
        );

        // messageChooser.setInitialDirectory(new File("/"));
        messageChooser.setInitialDirectory(new File(habit.getMessageChooserPath()));
        messageDirectoryChooser = new DirectoryChooser();
        messageDirectoryChooser.setInitialDirectory(new File(habit.getMessageDirectoryChooserPath()));
        javaServerCodePathChooser = new DirectoryChooser();
        javaServerCodePathChooser.setInitialDirectory(new File(habit.getJavaServerCodePathChooserPath()));
        javaAICodePathChooser = new DirectoryChooser();
        javaAICodePathChooser.setInitialDirectory(new File(habit.getJavaAICodePathChooserPath()));

        luaCodePathChooser = new DirectoryChooser();
        luaCodePathChooser.setInitialDirectory(new File(habit.getLuaCodePathChooserPath()));


    }

    private void initTemplate() {
        File javaFolder = new File(TemplateUtil.templateDir(), "java");
        File[] files = javaFolder.listFiles();
        for (File file : files) {
            if (file.getName().toLowerCase().endsWith("handler.ftl")) {
                javaHandler.getItems().add(file);
                if (file.getName().equals(habit.getJavaHandlerTemplate())) {
                    javaHandler.getSelectionModel().select(file);
                }
            } else if (file.getName().toLowerCase().endsWith("bean.ftl")) {
                javaBean.getItems().add(file);
                if (file.getName().equals(habit.getJavaBeanTemplate())) {
                    javaBean.getSelectionModel().select(file);
                }
            } else if (file.getName().toLowerCase().endsWith("message.ftl")) {
                javaMessage.getItems().add(file);
                if (file.getName().equals(habit.getJavaMessageTemplate())) {
                    javaMessage.getSelectionModel().select(file);
                }
            }
        }

        FileConverter fileConverter = new FileConverter();
        javaHandler.setConverter(fileConverter);
        javaBean.setConverter(fileConverter);
        javaMessage.setConverter(fileConverter);
        javaHandlerCoverCheckBox.setSelected(habit.isJavaHandlerCover());

        File luaFolder = new File(TemplateUtil.templateDir(), "lua");
        files = luaFolder.listFiles();
        for (File file : files) {
            if (file.getName().toLowerCase().endsWith("handler.ftl")) {
                luaHandler.getItems().add(file);
                if (file.getName().equals(habit.getLuaHandlerTemplate())) {
                    luaHandler.getSelectionModel().select(file);
                }
            } else if (file.getName().toLowerCase().endsWith("message.ftl")) {
                luaMessage.getItems().add(file);
                if (file.getName().equals(habit.getJavaMessageTemplate())) {
                    luaMessage.getSelectionModel().select(file);
                }
            }
        }
        luaHandler.setConverter(fileConverter);
        luaMessage.setConverter(fileConverter);
        luaHandlerCoverCheckBox.setSelected(habit.isLuaHandlerCover());
    }

    private void addFileToView(File file) {
        if (messageFiles.add(file)) {
            FileData fileData = new FileData(file);
            messageView.getItems().add(fileData);
        } else {

            logger.warn("已经存在{}->{}", file.getName(), file.getAbsolutePath());
        }


    }

    public void addMessageFile() {

        logger.debug("增加消息文件");
        List<File> files = messageChooser.showOpenMultipleDialog(GeneratorContext.getPrimaryStage());
        if (files != null) {
            messageChooser.setInitialDirectory(files.get(0).getParentFile());
            habit.setMessageChooserPath(files.get(0).getParentFile().toString());
            logger.debug(habit.getMessageChooserPath());
            files.forEach(file ->
                    {
                        logger.debug("文件全路径:{}", file.getAbsolutePath());
                        addFileToView(file);
                    }


            );
            //logger.debug("文件全路径:{}",file.getAbsolutePath());
        } else {
            logger.debug("没有选择任何文件");
        }
    }

    public void addMessageFolder() {
        logger.debug("增加消息文件夹");

        File file = messageDirectoryChooser.showDialog(GeneratorContext.getPrimaryStage());
        if (file != null) {
            int count = 0;
            messageDirectoryChooser.setInitialDirectory(file.getParentFile());

            habit.setMessageDirectoryChooserPath(file.getParent());
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.isDirectory()) {
                    continue;
                }
                if (f.getName().endsWith(".xml")) {
                    count++;
                    logger.debug("文件全路径:{}", f.getAbsolutePath());
                    addFileToView(f);
                }
            }
            if (count == 0) {
                logger.debug("没有符合条件的文件");
            }

        } else {
            logger.debug("没有选择任何文件");
        }
    }

    public void removeMessage() {
        int index = messageView.getSelectionModel().getFocusedIndex();

        if (index > -1) {
            FileData fileData = messageView.getItems().remove(index);
            messageFiles.remove(fileData.getFile());
            logger.debug("移除 {} -> {}", fileData.getName(), fileData.getPath());

        } else {

            logger.debug("没有选择任何文件");
        }

    }


    public void chooseJavaServerCodeRootPath() {

        File file = javaServerCodePathChooser.showDialog(GeneratorContext.getPrimaryStage());
        if (file != null) {
            javaServerCodeRootPath.setText(file.getAbsolutePath());

            javaServerCodePathChooser.setInitialDirectory(file.getParentFile());
            habit.setJavaServerCodeRootPath(file.getAbsolutePath());
            habit.setJavaServerCodePathChooserPath(file.getParent());
        }
    }

    public void chooseJavaAICodeRootPath() {

        File file = javaAICodePathChooser.showDialog(GeneratorContext.getPrimaryStage());
        if (file != null) {
            javaAICodeRootPath.setText(file.getAbsolutePath());
            javaAICodePathChooser.setInitialDirectory(file.getParentFile());
            habit.setJavaAICodeRootPath(file.getAbsolutePath());
            habit.setJavaAICodePathChooserPath(file.getParent());
        }
    }

    public void chooseLuaCodeRootPath() {
        File file = luaCodePathChooser.showDialog(GeneratorContext.getPrimaryStage());
        if (file != null) {
            luaCodeRootPath.setText(file.getAbsolutePath());
            luaCodePathChooser.setInitialDirectory(file.getParentFile());
            habit.setLuaCodeRootPath(file.getAbsolutePath());
            habit.setLuaCodePathChooserPath(file.getParent());
        }
    }

    public void chooseMessageCS() {
        logger.debug("选择上CS类型的message");
        messageCodeView.getItems().forEach(messageData ->
                {
                    if ("CS".equalsIgnoreCase(messageData.getMessage().getType())) {
                        messageData.setGenerate(true);
                    }
                }

        );
    }

    public void chooseMessageSS() {
        logger.debug("选择上SS类型的message");
        messageCodeView.getItems().forEach(messageData ->
                {
                    if ("SS".equalsIgnoreCase(messageData.getMessage().getType())) {
                        messageData.setGenerate(true);
                    }
                }

        );
    }

    public void chooseMessageSC() {
        logger.debug("选择上SC类型的message");
        messageCodeView.getItems().forEach(messageData ->
                {
                    if ("SC".equalsIgnoreCase(messageData.getMessage().getType())) {
                        messageData.setGenerate(true);
                    }
                }

        );
    }

    public void chooseMessageNA() {
        logger.debug("选择上NA类型的message(bean)");
        messageCodeView.getItems().forEach(messageData ->
                {
                    if ("NA".equalsIgnoreCase(messageData.getMessage().getType())) {
                        messageData.setGenerate(true);
                    }
                }

        );
    }

    public void chooseMessageAll() {
        messageAllChoose = !messageAllChoose;
        logger.debug(messageAllChoose ? "选择所有message" : "取消所有message");
        messageCodeView.getItems().forEach(messageData ->
                messageData.setGenerate(messageAllChoose)
        );
    }

    public void messageCodeViewClick() {

        MessageData messageData =
                messageCodeView.getSelectionModel().getSelectedItem();
        if (messageData != null) {
            messageData.setGenerate(!messageData.isGenerate());
        }


    }

    public void messagePreview() {
        messagePreview = true;
        //   xmlMessages.clear();
        xmlMessageMap.clear();
        messageCodeView.getItems().clear();
        for (File file : messageFiles) {
            XmlMessage xmlMessage = XmlReader.readXml(file);

            xmlMessageMap.put(xmlMessage.getPack(), xmlMessage);
            for (Message message : xmlMessage.getMessages()) {
                MessageData messageData = new MessageData(message, xmlMessage);
                if (!messageCodeView.getItems().contains(messageData)) {
                    messageCodeView.getItems().add(messageData);
                } else {
                    message.setGenerate(false);
                }

            }
            for (Bean bean : xmlMessage.getBeans()) {
                MessageData messageData = new MessageData(bean, xmlMessage);
                if (!messageCodeView.getItems().contains(messageData)) {
                    messageCodeView.getItems().add(messageData);
                } else {
                    bean.setGenerate(true);
                }

            }
        }

    }

    public void writeHabit() {
        habit.setJavaBeanTemplate(javaBean.getSelectionModel().getSelectedItem().getName());
        habit.setJavaMessageTemplate(javaMessage.getSelectionModel().getSelectedItem().getName());
        habit.setJavaHandlerTemplate(javaHandler.getSelectionModel().getSelectedItem().getName());
        habit.setJavaServerCodeRootPath(javaServerCodeRootPath.getText());
        habit.setJavaAICodeRootPath(javaAICodeRootPath.getText());
        habit.setJavaHandlerCover(javaHandlerCoverCheckBox.isSelected());

        habit.setLuaCodeRootPath(luaCodeRootPath.getText());
        habit.setLuaMessageTemplate(luaMessage.getSelectionModel().getSelectedItem().getName());
        habit.setLuaHandlerTemplate(luaHandler.getSelectionModel().getSelectedItem().getName());
        habit.setLuaHandlerCover(luaHandlerCoverCheckBox.isSelected());
    }

    public void clearLog() {
        textAreaLog.clear();
    }

    public void prepCodeGenerate(String codePath) {
        if (!messagePreview || xmlMessageMap.size() == 0) {
            messagePreview();
        }
        CheckMessageUtil.loadMessage(codePath);
    }

    public void javaServerCodeGenerate() throws IOException {
        prepCodeGenerate(javaServerCodeRootPath.getText());
        javaCodeGenerate(false);
    }

    public void javaAICodeGenerate() throws IOException {
        prepCodeGenerate(javaAICodeRootPath.getText());
        javaCodeGenerate(true);
    }

    public void javaCodeGenerate(boolean ai) throws IOException {


        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

        cfg.setDirectoryForTemplateLoading(new File(TemplateUtil.templateDir(), "java"));
        if (javaBeanCheckBox.isSelected()) {
            Template template = cfg.getTemplate(javaBean.getSelectionModel().getSelectedItem().getName(), "utf-8");
            for (MessageData messageData : messageCodeView.getItems()) {
                if (messageData.isGenerate() && "NA".equalsIgnoreCase(messageData.getType())) {
                    Bean bean = messageData.getMessage();
                    File file = new File(ai ? javaAICodeRootPath.getText() : javaServerCodeRootPath.getText(),
                            bean.getPack().replace(".", File.separator)
                                    + File.separator + "bean" + File.separator + bean.getName() + ".java");
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    logger.trace("生成 bean {}\n               {}", file.getName(), file.getAbsolutePath());
                    Generator.generate(bean, template, file);
                }

            }

        }
        if (javaMessageCheckBox.isSelected()) {
            Template template = cfg.getTemplate(javaMessage.getSelectionModel().getSelectedItem().getName(), "utf-8");

            for (MessageData messageData : messageCodeView.getItems()) {
                if (messageData.isGenerate() && !"NA".equalsIgnoreCase(messageData.getType())) {
                    Message message = (Message) messageData.getMessage();
                    File file = new File(ai ? javaAICodeRootPath.getText() : javaServerCodeRootPath.getText(), message
                            .getPack().replace(".", File.separator)
                            + File.separator + "message" + File.separator + message.getType() + message.getName() + "Message.java");
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    boolean can = CheckMessageUtil.checkMessage(message, "Message");
                    if (can) {
                        logger.trace("生成 message {}\n               {}", file.getName(), file.getAbsolutePath());
                        Generator.generate(message, template, file);
                        CheckMessageUtil.messsageSuccess(message);
                    }


                }
            }
        }

        if (javaHandlerCheckBox.isSelected()) {
            Template templateHandler = cfg.getTemplate(javaHandler.getSelectionModel().getSelectedItem().getName(), "utf-8");
            for (MessageData messageData : messageCodeView.getItems()) {
                if (messageData.getType().endsWith(ai ? "C" : "S")) {
                    Message message = (Message) messageData.getMessage();
                    if (message.isGenerate()) {
                        File savaHandler = new File(ai ? javaAICodeRootPath.getText() : javaServerCodeRootPath.getText(), message
                                .getPack().replace(".", File.separator)
                                + File.separator + "handler" + File.separator + message.getType() + message.getName() + "MessageHandler.java");
                        if (!savaHandler.getParentFile().exists()) {
                            savaHandler.getParentFile().mkdirs();
                        }
                        boolean can = CheckMessageUtil.checkMessage(message, "Handler");
                        if (can) {
                            if (savaHandler.exists() && !javaHandlerCoverCheckBox.isSelected()) {

                                logger.warn("handler 存在 不能生成{}\n               {}", savaHandler.getName(), savaHandler.getAbsolutePath());
                            } else {

                                logger.trace("生成 handler {}\n               {}", savaHandler.getName(), savaHandler.getAbsolutePath());
                                Generator.generate(message, templateHandler, savaHandler);
                            }
                        } else {

                        }


                    }
                }
            }
        }
        logger.debug("java {}代码生成完成", ai ? "AI" : "服务器");

    }

    public void luaCodeGenerate() throws IOException, TemplateModelException {
        prepCodeGenerate(luaCodeRootPath.getText());
        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        cfg.setDirectoryForTemplateLoading(new File(TemplateUtil.templateDir(), "lua"));
        cfg.setSharedVariable("rightPad", new RightPad());
        cfg.setSharedVariable("luaNameStyle", new LuaNameStyle());
        //cfg.setSharedVariable("luaNamespace", "MSG.");
        Iterator<Map.Entry<String, XmlMessage>> iterator = xmlMessageMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, XmlMessage> entry = iterator.next();

            XmlMessage value = entry.getValue();
          value = MessageUtil.convert2Lua(value);
            XmlMessage xmlMessage = new XmlMessage();
            xmlMessage.setMessageNameMaxLen(value.getMessageNameMaxLen());
            xmlMessage.setNameMaxLen(value.getNameMaxLen());
            xmlMessage.setBeanNameMaxLen(value.getBeanNameMaxLen());
            xmlMessage.setId(value.getId());
            xmlMessage.setPack(value.getPack());
            xmlMessage.setModel(value.getModel());
            xmlMessage.setLuaNamespace("Net_"+StringUtil.toUpperFirstLetter(value.getModel())+"_");
            for (Bean bean : value.getBeans()) {
                if (bean.isGenerate()) {
                    xmlMessage.getBeans().add(bean);
                }

            }
            for (Message message : value.getMessages()) {
                if (message.isGenerate()) {
                    boolean can = CheckMessageUtil.checkMessage(message, "MessageAndHandler");
                    if (can) {
                        xmlMessage.getMessages().add(message);
                    }

                }

            }
            String fileName = xmlMessage.getModel();
            int index = StringUtil.indexOf(fileName, ".", 1, true);
            if (index > -1) {
                fileName = fileName.substring(index + 1);
            }
            fileName = StringUtil.toUpperFirstLetter(fileName);
            if (luaMessageCheckBox.isSelected()) {
                File file = new File(luaCodeRootPath.getText(),
                       fileName + "Message.lua");
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                Template template = cfg.getTemplate(luaMessage.getSelectionModel().getSelectedItem().getName(), "utf-8");
                logger.trace("生成 lua message {}\n               {}", file.getName(), file.getAbsolutePath());
                Generator.generate(xmlMessage, template, file);

                for (Message message : xmlMessage.getMessages()) {
                    CheckMessageUtil.messsageSuccess(message);
                }

            }
            if (luaHandlerCheckBox.isSelected()) {
                Template template = cfg.getTemplate(luaHandler.getSelectionModel().getSelectedItem().getName(), "utf-8");
//                File file = new File(luaCodeRootPath.getText(), xmlMessage
//                        .getPack().replace(".", File.separator)
//                        + File.separator + fileName + "MessageHandler.lua");
                File file = new File(luaCodeRootPath.getText(),  fileName + "MessageHandler.lua");
                if (file.exists() && !luaHandlerCoverCheckBox.isSelected()) {

                    logger.warn("hander 文件 {} 存在，请先删除之后，再生成              \n{}", file.getName(), file.getAbsolutePath());
                } else {
                    logger.trace("生成 lua Handler {}\n               {}", file.getName(), file.getAbsolutePath());
                    Generator.generate(xmlMessage, template, file);
                }

            }
        }

        logger.debug("lua 代码生成完成");
    }
}
