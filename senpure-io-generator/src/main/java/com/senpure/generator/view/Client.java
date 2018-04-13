package com.senpure.generator.view;


import com.senpure.base.AppEvn;
import com.senpure.base.util.BannerShow;
import com.senpure.generator.GeneratorContext;
import com.senpure.generator.HabitUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.fusesource.jansi.AnsiConsole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ansi.AnsiOutput;

import java.util.ResourceBundle;

/**
 * Created by 罗中正 on 2017/6/7.
 */
public class Client extends Application {
    private Logger logger = LoggerFactory.getLogger(Client.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            GeneratorContext.setPrimaryStage(primaryStage);
            ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n/i18n");
            // Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"),resourceBundle);

            primaryStage.setTitle("代码生成工具 senpure-generator");

            // primaryStage.setScene(new Scene(root, 300, 275));
            //  primaryStage.show();

            FXMLLoader loader = new FXMLLoader();

            //loader.setLocation(getClass().getClassLoader().getResource("/main.fxml"));
            loader.setLocation(getClass().getResource("main.fxml"));
            loader.setResources(resourceBundle);

            //loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(loader.load());

            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setFullScreen(false);

            //Thread.currentThread().setUncaughtExceptionHandler((t, e) -> logger.error("出现错误", e));
            Thread.setDefaultUncaughtExceptionHandler((t, e) -> logger.error("出现错误", e));
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                GeneratorContext.getMainController().writeHabit();
                HabitUtil.saveHabit(GeneratorContext.getHabit());
            }));
        } catch (Exception e) {
            logger.error("出现错误", e);
        }


    }


    public static void main(String[] args) {


        AnsiConsole.systemInstall();
        AnsiConsole.systemUninstall();
        System.setProperty("PID", AppEvn.getPid());
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
        BannerShow.show();
        launch(args);


    }


}
