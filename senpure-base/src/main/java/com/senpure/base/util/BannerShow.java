package com.senpure.base.util;


import com.senpure.base.AppEvn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 罗中正 on 2017/4/28.
 */

public class BannerShow {
    private static Logger LOGGER = LoggerFactory.getLogger(BannerShow.class);

    static List<String> strs = new ArrayList<>(16);

    static {
        strs.add(" _______               _______    _______    _______    _______    _______");
        strs.add("(  ____ \\  |\\     /|  (  ____ \\  (  ____ \\  (  ____ \\  (  ____ \\  (  ____ \\");
        strs.add("| (    \\/  | )   ( |  | (    \\/  | (    \\/  | (    \\/  | (    \\/  | (    \\/");
        strs.add("| (_____   | |   | |  | |        | |        | (__      | (_____   | (_____");
        strs.add("(_____  )  | |   | |  | |        | |        |  __)     (_____  )  (_____  )");
        strs.add("      ) |  | |   | |  | |        | |        | (              ) |        ) |");
        strs.add("/\\____) |  | (___) |  | (____/\\  | (____/\\  | (____/\\  /\\____) |  /\\____) |");
        strs.add("\\_______)  (_______)  (_______/  (_______/  (_______/  \\_______)  \\_______)");
    }

    public static void show() {
        LOGGER.debug("\n\n{}", str());

    }

    public static String str() {
        List<String> strs = null;
        try {
            InputStream
                    inputStream = BannerShow.class.getClassLoader().getResourceAsStream("showbanner.txt");
            // 建立一个输入流对象reader
            if (inputStream != null) {
                InputStreamReader reader = new InputStreamReader(
                        inputStream);
                BufferedReader br = new BufferedReader(reader);
                strs = new ArrayList<>();
                while (br.ready()) {
                    String line = br.readLine();
                    strs.add(line);
                }
                br.close();
                reader.close();
                inputStream.close();
            }

        } catch (IOException e) {

        }
        if (strs == null || strs.size() == 0) {
            strs = BannerShow.strs;
        }
        int length = 0;
        for (int i = 0; i < strs.size(); i++) {
            int tl = strs.get(i).length();
            length = tl > length ? tl : length;
        }
        if (length % 2 != 0) {
            length++;
        }
        int realLength = length;

        if (AppEvn.isWindowsOS()) {

        }
        length >>= 1;
        StringBuilder banner = new StringBuilder();
           String top = "";
        top = getStr("═", length);
        top = "╔" + top + "╗";

        top = AnsiOutput.toString(new Object[]{AnsiColor.BRIGHT_RED, top});
        banner.append(top);
        banner.append("\n");
        String empty;
        //empty = "║
        //
        if (AppEvn.isWindowsOS()) {
            empty = getStr("  ", length);
        } else {
            empty = getStr(" ", length);
        }
        empty = getStr(" ", realLength);

        empty = "║" + empty + "║";
        empty = AnsiOutput.toString(new Object[]{AnsiColor.BRIGHT_RED, empty});
        for (int i = 0; i < strs.size(); i++) {
            banner.append(AnsiOutput.toString(new Object[]{AnsiColor.BRIGHT_RED, "║"}));
            banner.append(AnsiOutput.toString(new Object[]{AnsiColor.BRIGHT_CYAN, strs.get(i)}));
            int l = strs.get(i).length();
            String temp = getStr(" ", realLength - l);
            banner.append(AnsiOutput.toString(new Object[]{AnsiColor.BRIGHT_RED, temp + "║"}));
            banner.append("\n");
        }
        banner.append(empty);
        banner.append("\n");
        String down = getStr("═", length);
        down = "╚" + down + "╝";
        down = AnsiOutput.toString(new Object[]{AnsiColor.BRIGHT_RED, down});
        banner.append(down);
        banner.append("\n");
        return banner.toString();
    }

    private static String getStr(String ch, int num) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            sb.append(ch);

        }
        return sb.toString();
    }

    public static void bannerText( String name) {
        List<String> strs = new ArrayList<>();

        try {

            InputStream inputStream;
            inputStream = BannerShow.class.getClassLoader().getResourceAsStream(name);
            // 建立一个输入流对象reader
            InputStreamReader reader = new InputStreamReader(
                    inputStream);
            BufferedReader br = new BufferedReader(reader);
            strs = new ArrayList<>();
            while (br.ready()) {
                String line = br.readLine();
                // strs.add(line);
                line = line.replace("\\", "\\\\");
                System.out.println("strs.add(\"" + line + "\") ;");
            }
            br.close();
            reader.close();
            inputStream.close();
        } catch (IOException e) {

        }
    }

    public static void main(String[] args) {
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);

        show();

        // System.out.println("═".length());
    }
}
