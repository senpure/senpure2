package com.senpure.generator;

import com.senpure.generator.view.MainController;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by 罗中正 on 2017/6/8.
 */
public class GeneratorContext {

    private static  Stage primaryStage;
    private static  Habit habit;

    private static  MainController mainController;
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        GeneratorContext.primaryStage = primaryStage;
    }

    public static Habit getHabit() {
        return habit;
    }

    public static void setHabit(Habit habit) {
        GeneratorContext.habit = habit;
    }

    public File ftlDirtory()
    {
        return null;
    }

    public static MainController getMainController() {
        return mainController;
    }

    public static  void setMainController(MainController mainController) {
        GeneratorContext.mainController = mainController;
    }
}
