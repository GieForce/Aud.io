package log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;

public class Logger {
    private static java.util.logging.Logger logger;

    private Logger() {
    }

    public static void setupLogger(String logname) {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            FileHandler fh = new FileHandler(String.format("logs/%s-%s.log",logname, timeStamp));
            fh.setLevel(Level.ALL);
            logger = java.util.logging.Logger.getLogger(logname);
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    public static void log(Level level, String message) {
        logger.log(level, message);
    }
}
