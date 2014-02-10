package net.canarymod.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import net.canarymod.config.Configuration;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.FormattedMessageFactory;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;

/**
 * Simple logger implementation that directly uses {@link System#out}.
 * @author Willem Mulder (14mRh4X0r)
 */
public class ShutdownLogger implements Logger {
    private static final SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss");
    private static final MessageFactory messageFactory = new FormattedMessageFactory();
    private final String name;

    public ShutdownLogger(Class<?> clazz) {
        this(clazz.getCanonicalName());
    }

    public ShutdownLogger(String name) {
        this.name = name;
    }

    @Override
    public void catching(Level level, Throwable t) {
        log(level, (Object) null, t);
    }

    @Override
    public void catching(Throwable t) {
        catching(Level.ERROR, t);
    }

    @Override
    public void debug(Marker marker, Message msg) {
        debug(marker, msg, null);
    }

    @Override
    public void debug(Marker marker, Message msg, Throwable t) {
        log(Level.DEBUG, msg, t);
    }

    @Override
    public void debug(Marker marker, Object message) {
        debug(marker, getMessageFactory().newMessage(message));
    }

    @Override
    public void debug(Marker marker, Object message, Throwable t) {
        debug(marker, getMessageFactory().newMessage(message), t);
    }

    @Override
    public void debug(Marker marker, String message) {
        debug(marker, getMessageFactory().newMessage(message));
    }

    @Override
    public void debug(Marker marker, String message, Object... params) {
        debug(marker, getMessageFactory().newMessage(message, params));
    }

    @Override
    public void debug(Marker marker, String message, Throwable t) {
        debug(marker, getMessageFactory().newMessage(message), t);
    }

    @Override
    public void debug(Message msg) {
        debug(null, msg);
    }

    @Override
    public void debug(Message msg, Throwable t) {
        debug(null, msg, t);
    }

    @Override
    public void debug(Object message) {
        debug(null, message);
    }

    @Override
    public void debug(Object message, Throwable t) {
        debug(null, message, t);
    }

    @Override
    public void debug(String message) {
        debug(null, message);
    }

    @Override
    public void debug(String message, Object... params) {
        debug(null, message, params);
    }

    @Override
    public void debug(String message, Throwable t) {
        debug(null, message, t);
    }

    @Override
    public void entry() {
    }

    @Override
    public void entry(Object... params) {
    }

    @Override
    public void error(Marker marker, Message msg) {
        error(marker, msg, null);
    }

    @Override
    public void error(Marker marker, Message msg, Throwable t) {
        log(Level.ERROR, marker, msg, t);
    }

    @Override
    public void error(Marker marker, Object message) {
        error(marker, getMessageFactory().newMessage(message));
    }

    @Override
    public void error(Marker marker, Object message, Throwable t) {
        error(marker, getMessageFactory().newMessage(message), t);
    }

    @Override
    public void error(Marker marker, String message) {
        error(marker, getMessageFactory().newMessage(message));
    }

    @Override
    public void error(Marker marker, String message, Object... params) {
        error(marker, getMessageFactory().newMessage(message, params));
    }

    @Override
    public void error(Marker marker, String message, Throwable t) {
        error(marker, getMessageFactory().newMessage(message), t);
    }

    @Override
    public void error(Message msg) {
        error(null, msg);
    }

    @Override
    public void error(Message msg, Throwable t) {
        error(null, msg, t);
    }

    @Override
    public void error(Object message) {
        error(null, getMessageFactory().newMessage(message));
    }

    @Override
    public void error(Object message, Throwable t) {
        error(null, getMessageFactory().newMessage(message), t);
    }

    @Override
    public void error(String message) {
        error(null, getMessageFactory().newMessage(message));
    }

    @Override
    public void error(String message, Object... params) {
        error(null, getMessageFactory().newMessage(message, params));
    }

    @Override
    public void error(String message, Throwable t) {
        error(null, getMessageFactory().newMessage(message), t);
    }

    @Override
    public void exit() {
    }

    @Override
    public <R> R exit(R result) {
        return result;
    }

    @Override
    public void fatal(Marker marker, Message msg) {
        fatal(marker, msg, null);
    }

    @Override
    public void fatal(Marker marker, Message msg, Throwable t) {
        log(Level.FATAL, msg, t);
    }

    @Override
    public void fatal(Marker marker, Object message) {
        fatal(marker, getMessageFactory().newMessage(message));
    }

    @Override
    public void fatal(Marker marker, Object message, Throwable t) {
        fatal(marker, getMessageFactory().newMessage(message), t);
    }

    @Override
    public void fatal(Marker marker, String message) {
        fatal(marker, getMessageFactory().newMessage(message));
    }

    @Override
    public void fatal(Marker marker, String message, Object... params) {
        fatal(marker, getMessageFactory().newMessage(message, params));
    }

    @Override
    public void fatal(Marker marker, String message, Throwable t) {
        fatal(marker, getMessageFactory().newMessage(message), t);
    }

    @Override
    public void fatal(Message msg) {
        fatal(null, msg);
    }

    @Override
    public void fatal(Message msg, Throwable t) {
        fatal(null, msg, t);
    }

    @Override
    public void fatal(Object message) {
        fatal(null, getMessageFactory().newMessage(message));
    }

    @Override
    public void fatal(Object message, Throwable t) {
        fatal(null, getMessageFactory().newMessage(message), t);
    }

    @Override
    public void fatal(String message) {
        fatal(null, getMessageFactory().newMessage(message));
    }

    @Override
    public void fatal(String message, Object... params) {
        fatal(null, getMessageFactory().newMessage(message, params));
    }

    @Override
    public void fatal(String message, Throwable t) {
        fatal(null, getMessageFactory().newMessage(message), t);
    }

    @Override
    public MessageFactory getMessageFactory() {
        return messageFactory;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void info(Marker marker, Message msg) {
        info(marker, msg, null);
    }

    @Override
    public void info(Marker marker, Message msg, Throwable t) {
        log(Level.INFO, marker, msg, t);
    }

    @Override
    public void info(Marker marker, Object message) {
        info(marker, getMessageFactory().newMessage(message));
    }

    @Override
    public void info(Marker marker, Object message, Throwable t) {
        info(marker, getMessageFactory().newMessage(message), t);
    }

    @Override
    public void info(Marker marker, String message) {
        info(marker, getMessageFactory().newMessage(message));
    }

    @Override
    public void info(Marker marker, String message, Object... params) {
        info(marker, getMessageFactory().newMessage(message, params));
    }

    @Override
    public void info(Marker marker, String message, Throwable t) {
        info(marker, getMessageFactory().newMessage(message), t);
    }

    @Override
    public void info(Message msg) {
        info(null, msg);
    }

    @Override
    public void info(Message msg, Throwable t) {
        info(null, msg, t);
    }

    @Override
    public void info(Object message) {
        info(null, getMessageFactory().newMessage(message));
    }

    @Override
    public void info(Object message, Throwable t) {
        info(null, getMessageFactory().newMessage(message), t);
    }

    @Override
    public void info(String message) {
        info(null, getMessageFactory().newMessage(message));
    }

    @Override
    public void info(String message, Object... params) {
        info(null, getMessageFactory().newMessage(message, params));
    }

    @Override
    public void info(String message, Throwable t) {
        info(null, getMessageFactory().newMessage(message), t);
    }

    @Override
    public boolean isDebugEnabled() {
        return Configuration.getServerConfig().isDebugMode();
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return isDebugEnabled();
    }

    @Override
    public boolean isEnabled(Level level) {
        return level.intLevel() > (isDebugEnabled() ? Level.DEBUG.intLevel() : Level.INFO.intLevel());
    }

    @Override
    public boolean isEnabled(Level level, Marker marker) {
        return isEnabled(level);
    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return true;
    }

    @Override
    public boolean isFatalEnabled() {
        return true;
    }

    @Override
    public boolean isFatalEnabled(Marker marker) {
        return true;
    }

    @Override
    public boolean isInfoEnabled() {
        return true;
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return true;
    }

    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return false;
    }

    @Override
    public boolean isWarnEnabled() {
        return true;
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return true;
    }

    @Override
    public void log(Level level, Marker marker, Message msg) {
        log(level, marker, msg, null);
    }

    @Override
    @SuppressWarnings("CallToThreadDumpStack")
    public void log(Level level, Marker marker, Message msg, Throwable t) {
        System.out.println(String.format("[%s] [%s] [%s]%s: %s", date.format(new Date()), name, level.toString(),
                                                                 marker == null ? "" : String.format(" [%s]", marker.getName()),
                                                                 msg.getFormattedMessage()));
        if (t != null) {
            t.printStackTrace();
        }
    }

    @Override
    public void log(Level level, Marker marker, Object message) {
        log(level, marker, getMessageFactory().newMessage(message));
    }

    @Override
    public void log(Level level, Marker marker, Object message, Throwable t) {
        log(level, marker, getMessageFactory().newMessage(message), t);
    }

    @Override
    public void log(Level level, Marker marker, String message) {
        log(level, marker, getMessageFactory().newMessage(message));
    }

    @Override
    public void log(Level level, Marker marker, String message, Object... params) {
        log(level, marker, getMessageFactory().newMessage(message, params));
    }

    @Override
    public void log(Level level, Marker marker, String message, Throwable t) {
        log(level, marker, getMessageFactory().newMessage(message), t);
    }

    @Override
    public void log(Level level, Message msg) {
        log(level, null, msg);
    }

    @Override
    public void log(Level level, Message msg, Throwable t) {
        log(level, null, msg, t);
    }

    @Override
    public void log(Level level, Object message) {
        log(level, null, getMessageFactory().newMessage(message));
    }

    @Override
    public void log(Level level, Object message, Throwable t) {
        log(level, null, getMessageFactory().newMessage(message), t);
    }

    @Override
    public void log(Level level, String message) {
        log(level, null, getMessageFactory().newMessage(message));
    }

    @Override
    public void log(Level level, String message, Object... params) {
        log(level, null, getMessageFactory().newMessage(message, params));
    }

    @Override
    public void log(Level level, String message, Throwable t) {
        log(level, null, getMessageFactory().newMessage(message), t);
    }

    @Override
    public void printf(Level level, Marker marker, String format, Object... params) {
        log(level, marker, getMessageFactory().newMessage(format, params));
    }

    @Override
    public void printf(Level level, String format, Object... params) {
        log(level, null, getMessageFactory().newMessage(format, params));
    }

    @Override
    public <T extends Throwable> T throwing(Level level, T t) {
        log(level, null, (Message) null, t);
        return t;
    }

    @Override
    public <T extends Throwable> T throwing(T t) {
        log(Level.ERROR, null, (Message) null, t);
        return t;
    }

    @Override
    public void trace(Marker marker, Message msg) {
    }

    @Override
    public void trace(Marker marker, Message msg, Throwable t) {
    }

    @Override
    public void trace(Marker marker, Object message) {
    }

    @Override
    public void trace(Marker marker, Object message, Throwable t) {
    }

    @Override
    public void trace(Marker marker, String message) {
    }

    @Override
    public void trace(Marker marker, String message, Object... params) {
    }

    @Override
    public void trace(Marker marker, String message, Throwable t) {
    }

    @Override
    public void trace(Message msg) {
    }

    @Override
    public void trace(Message msg, Throwable t) {
    }

    @Override
    public void trace(Object message) {
    }

    @Override
    public void trace(Object message, Throwable t) {
    }

    @Override
    public void trace(String message) {
    }

    @Override
    public void trace(String message, Object... params) {
    }

    @Override
    public void trace(String message, Throwable t) {
    }

    @Override
    public void warn(Marker marker, Message msg) {
        warn(marker, msg, null);
    }

    @Override
    public void warn(Marker marker, Message msg, Throwable t) {
        log(Level.WARN, msg, t);
    }

    @Override
    public void warn(Marker marker, Object message) {
        warn(marker, getMessageFactory().newMessage(message));
    }

    @Override
    public void warn(Marker marker, Object message, Throwable t) {
        warn(marker, getMessageFactory().newMessage(message), t);
    }

    @Override
    public void warn(Marker marker, String message) {
        warn(marker, getMessageFactory().newMessage(message));
    }

    @Override
    public void warn(Marker marker, String message, Object... params) {
        warn(marker, getMessageFactory().newMessage(message, params));
    }

    @Override
    public void warn(Marker marker, String message, Throwable t) {
        warn(marker, getMessageFactory().newMessage(message), t);
    }

    @Override
    public void warn(Message msg) {
        warn(null, msg);
    }

    @Override
    public void warn(Message msg, Throwable t) {
        warn(null, msg, t);
    }

    @Override
    public void warn(Object message) {
        warn(null, message);
    }

    @Override
    public void warn(Object message, Throwable t) {
        warn(null, message, t);
    }

    @Override
    public void warn(String message) {
        warn(null, message);
    }

    @Override
    public void warn(String message, Object... params) {
        warn(null, message, params);
    }

    @Override
    public void warn(String message, Throwable t) {
        warn(null, message, t);
    }

}
