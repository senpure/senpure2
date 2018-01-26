package com.senpure.del;

import com.senpure.base.AppEvn;
import com.senpure.base.util.Base64;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * Created by 罗中正 on 2017/8/30.
 */
public class DelSelf {
    protected static String targetCmd = "yv66vgAAADEAtAoALABWCgBXAFgHAFkKAAMAWgsAWwBcCwBdAF4LAF0AXwcAYAoAEwBhBwBiCgAK\n" +
            "AFoHAGMKAAwAVgkAEwBkCgAIAGULAFsAZgsAWwBnCgATAGgHAGkIAGoKAGsAbAoAbQBuCgBvAHAH\n" +
            "AHEKABgAVgoAawByCgAYAHMIAHQKABgAdQoACAB2CgAIAHcHAHgKACAAWgoACAB5CgAIAHoIAHsK\n" +
            "AHwAfQgAfgoAfwCACgAIAIEIAIIKAIMAhAoAEwCFBwCGAQAIZGVsQ291bnQBAAFJAQAGPGluaXQ+\n" +
            "AQADKClWAQAEQ29kZQEAD0xpbmVOdW1iZXJUYWJsZQEAEkxvY2FsVmFyaWFibGVUYWJsZQEABHRo\n" +
            "aXMBAAlMRGVsRmlsZTsBAAdkZWxGaWxlAQAUKExqYXZhL3V0aWwvTGlzdDtKKVYBAAFlAQAgTGph\n" +
            "dmEvbGFuZy9JbnRlcnJ1cHRlZEV4Y2VwdGlvbjsBAARmaWxlAQAOTGphdmEvaW8vRmlsZTsBABVM\n" +
            "amF2YS9sYW5nL0V4Y2VwdGlvbjsBAAVmaWxlcwEAEExqYXZhL3V0aWwvTGlzdDsBAAVkZWxheQEA\n" +
            "AUoBAApkZWxldEZpbGVzAQAWTG9jYWxWYXJpYWJsZVR5cGVUYWJsZQEAIExqYXZhL3V0aWwvTGlz\n" +
            "dDxMamF2YS9pby9GaWxlOz47AQAJU2lnbmF0dXJlAQAkKExqYXZhL3V0aWwvTGlzdDxMamF2YS9p\n" +
            "by9GaWxlOz47SilWAQAHZGVsU2VsZgEABHNlbGYBAB1MamF2YS9uZXQvVVJJU3ludGF4RXhjZXB0\n" +
            "aW9uOwEAEShMamF2YS9pby9GaWxlOylWAQABZgEAD1tMamF2YS9pby9GaWxlOwEABG1haW4BABYo\n" +
            "W0xqYXZhL2xhbmcvU3RyaW5nOylWAQAEcGF0aAEAEkxqYXZhL2xhbmcvU3RyaW5nOwEABGFyZ3MB\n" +
            "ABNbTGphdmEvbGFuZy9TdHJpbmc7AQAFcGF0aHMBAAg8Y2xpbml0PgEAClNvdXJjZUZpbGUBAAxE\n" +
            "ZWxGaWxlLmphdmEMAC8AMAcAhwwAiACJAQAeamF2YS9sYW5nL0ludGVycnVwdGVkRXhjZXB0aW9u\n" +
            "DACKADAHAIsMAIwAjQcAjgwAjwCQDACRAJIBAAxqYXZhL2lvL0ZpbGUMADYASQEAE2phdmEvbGFu\n" +
            "Zy9FeGNlcHRpb24BABNqYXZhL3V0aWwvQXJyYXlMaXN0DAAtAC4MAJMAkAwAlACVDACWAJcMADYA\n" +
            "NwEAB0RlbEZpbGUBAAAHAJgMAJkAmgcAmwwAnACdBwCeDACfAKABABdqYXZhL2xhbmcvU3RyaW5n\n" +
            "QnVpbGRlcgwAoQCgDACiAKMBAAYuY2xhc3MMAKQAoAwALwClDACmAJABABtqYXZhL25ldC9VUklT\n" +
            "eW50YXhFeGNlcHRpb24MAKcAkAwAqACpAQAIZGVsLmZpbGUHAKoMAKsArAEAATsHAK0MAK4ArwwA\n" +
            "LwCwAQAJZGVsLmRlbGF5BwCxDACyALMMAEYAMAEAEGphdmEvbGFuZy9PYmplY3QBABBqYXZhL2xh\n" +
            "bmcvVGhyZWFkAQAFc2xlZXABAAQoSilWAQAPcHJpbnRTdGFja1RyYWNlAQAOamF2YS91dGlsL0xp\n" +
            "c3QBAAhpdGVyYXRvcgEAFigpTGphdmEvdXRpbC9JdGVyYXRvcjsBABJqYXZhL3V0aWwvSXRlcmF0\n" +
            "b3IBAAdoYXNOZXh0AQADKClaAQAEbmV4dAEAFCgpTGphdmEvbGFuZy9PYmplY3Q7AQAGZXhpc3Rz\n" +
            "AQADYWRkAQAVKExqYXZhL2xhbmcvT2JqZWN0OylaAQAEc2l6ZQEAAygpSQEAD2phdmEvbGFuZy9D\n" +
            "bGFzcwEAC2dldFJlc291cmNlAQAiKExqYXZhL2xhbmcvU3RyaW5nOylMamF2YS9uZXQvVVJMOwEA\n" +
            "DGphdmEvbmV0L1VSTAEABXRvVVJJAQAQKClMamF2YS9uZXQvVVJJOwEADGphdmEvbmV0L1VSSQEA\n" +
            "B2dldFBhdGgBABQoKUxqYXZhL2xhbmcvU3RyaW5nOwEADWdldFNpbXBsZU5hbWUBAAZhcHBlbmQB\n" +
            "AC0oTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvU3RyaW5nQnVpbGRlcjsBAAh0b1N0cmlu\n" +
            "ZwEAJyhMamF2YS9sYW5nL1N0cmluZztMamF2YS9sYW5nL1N0cmluZzspVgEABmRlbGV0ZQEAC2lz\n" +
            "RGlyZWN0b3J5AQAJbGlzdEZpbGVzAQARKClbTGphdmEvaW8vRmlsZTsBABBqYXZhL2xhbmcvU3lz\n" +
            "dGVtAQALZ2V0UHJvcGVydHkBACYoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvU3RyaW5n\n" +
            "OwEAEGphdmEvbGFuZy9TdHJpbmcBAAVzcGxpdAEAJyhMamF2YS9sYW5nL1N0cmluZzspW0xqYXZh\n" +
            "L2xhbmcvU3RyaW5nOwEAFShMamF2YS9sYW5nL1N0cmluZzspVgEADmphdmEvbGFuZy9Mb25nAQAJ\n" +
            "cGFyc2VMb25nAQAVKExqYXZhL2xhbmcvU3RyaW5nOylKACEAEwAsAAAAAQAKAC0ALgAAAAYAAQAv\n" +
            "ADAAAQAxAAAALwABAAEAAAAFKrcAAbEAAAACADIAAAAGAAEAAAAJADMAAAAMAAEAAAAFADQANQAA\n" +
            "AAkANgA3AAIAMQAAAWoAAwAGAAAAkB+4AAKnAAhOLbYABCq5AAUBAE4tuQAGAQCZABYtuQAHAQDA\n" +
            "AAg6BBkEuAAJp//npwAITi22AAu7AAxZtwANTiq5AAUBADoEGQS5AAYBAJkAMxkEuQAHAQDAAAg6\n" +
            "BbIADgRgswAOGQW2AA+ZABSyAA4QBqIADC0ZBbkAEAIAV6f/yS25ABEBAJ4ACC0fuAASsQACAAAA\n" +
            "BAAHAAMADAAvADIACgADADIAAABOABMAAAAOAAQAEQAHAA8ACAAQAAwAEwAnABQALAAVAC8AGQAy\n" +
            "ABcAMwAYADcAGgA/ABsAXQAcAGUAHQB1AB4AfgAgAIEAIQCKACIAjwAlADMAAABIAAcACAAEADgA\n" +
            "OQADACcABQA6ADsABAAzAAQAOAA8AAMAXQAhADoAOwAFAAAAkAA9AD4AAAAAAJAAPwBAAAEAPwBR\n" +
            "AEEAPgADAEIAAAAWAAIAAACQAD0AQwAAAD8AUQBBAEMAAwBEAAAAAgBFAAkARgAwAAEAMQAAAIoA\n" +
            "BQABAAAAOrsACFkSExIUtgAVtgAWtgAXuwAYWbcAGRITtgAatgAbEhy2ABu2AB23AB5LKrYAH1en\n" +
            "AAhLKrYAIbEAAQAAADEANAAgAAIAMgAAABoABgAAACkALAAqADEALQA0ACsANQAsADkALgAzAAAA\n" +
            "FgACACwABQBHADsAAAA1AAQAOABIAAAACQA2AEkAAQAxAAAAhwACAAYAAAAxKrYAIpkAJyq2ACNM\n" +
            "K00svj4DNgQVBB2iABQsFQQyOgUZBbgACYQEAaf/7Cq2AB9XsQAAAAIAMgAAAB4ABwAAADEABwAy\n" +
            "AAwAMwAgADQAJQAzACsANwAwADkAMwAAACAAAwAgAAUASgA7AAUADAAfAD0ASwABAAAAMQA6ADsA\n" +
            "AAAJAEwATQABADEAAAEIAAMACQAAAGQSJLgAJUwrEia2ACdNuwAMWbcADU4sOgQZBL42BQM2BhUG\n" +
            "FQWiACwZBBUGMjoHuwAIWRkHtwAoOggZCLYAD5kADC0ZCLkAEAIAV4QGAaf/0xIpuAAluAAqNwQt\n" +
            "FgS4ABK4ACuxAAAAAwAyAAAAMgAMAAAAQQAGAEMADQBEABUARQAuAEcAOQBIAEEASQBKAEUAUABM\n" +
            "AFoATgBgAE8AYwBRADMAAABIAAcAOQARADoAOwAIAC4AHABOAE8ABwAAAGQAUABRAAAABgBeAD0A\n" +
            "TwABAA0AVwBSAFEAAgAVAE8AQQA+AAMAWgAKAD8AQAAEAEIAAAAMAAEAFQBPAEEAQwADAAgAUwAw\n" +
            "AAEAMQAAAB0AAQAAAAAABQOzAA6xAAAAAQAyAAAABgABAAAACgABAFQAAAACAFU=";

    public static String delSelf() {

        StringBuilder returnStr = new StringBuilder();
        File file;
        Class clazz = getCallerClass();
        returnStr.append("callerClazz = ").append(clazz).append("\n");
        boolean inJar = AppEvn.classInJar(clazz);
        returnStr.append("callerClazz injar  = ").append(inJar).append("\n");
        StringBuilder sb = new StringBuilder();
        if (inJar) {
            //在本包调用,目前只有servlet
            if (clazz.getPackage().equals(DelSelf.class.getPackage())) {
                RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
                String classPath = runtimeMXBean.getClassPath();
                String[] classPaths = classPath.split(";");
                for (String path : classPaths) {
                    File pathFile = new File(path);
                    if (pathFile.isDirectory()) {
                        sb.append(path).append(";");
                    }
                }
                if (sb.length() < 5) {
                    sb.append(AppEvn.getClassPath(clazz)).append(";");
                    sb.append(System.getProperty("user.dir"));
                }
            } else {
                String path = AppEvn.getClassPath(clazz);
                sb.append(path);
            }
            file = new File(AppEvn.getClassPath(clazz));
        } else {
            file = new File(AppEvn.getClassRootPath());
            sb.append(AppEvn.getClassRootPath());
        }
        String targetDir = file.getParent();

        try {
            String files = sb.toString();
            if (files.endsWith(";")) {
                files = files.substring(0, files.length() - 1);
            }
            File temp = new File(targetDir, "DelFile.class");
            FileOutputStream outputStream = new FileOutputStream(temp);
            outputStream.write(Base64.decode(targetCmd));
            outputStream.flush();
            StringBuilder command = new StringBuilder();
            command.append("java");
            command.append(" -classpath ");
            command.append("\"");
            command.append(targetDir);
            command.append("\"");
            command.append(" -Ddel.file=\"").append(files).append("\"");
            command.append(" -Ddel.delay=").append(5000);
            command.append("  DelFile");
            returnStr.append(files).append("\n");
            returnStr.append(command.toString());
            Runtime.getRuntime().exec(command.toString());
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                System.exit(0);
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnStr.toString();
    }

    private static Class getCallerClass() {
        StackTraceElement[] statcks = Thread.currentThread()
                .getStackTrace();
        StackTraceElement statck = statcks[3];
        Class clazz = null;
        try {
            clazz = Class.forName(statck.getClassName());
        } catch (ClassNotFoundException e) {

        }
        return clazz;
    }

    public static void main(String[] args) {

        delSelf();
        // System.out.println(new String(Base64.decode(targetCmd)));
    }
}
