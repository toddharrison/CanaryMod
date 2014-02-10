/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.canarymod.util;

import com.google.common.collect.ImmutableMap;
import com.mojang.util.QueueLogAppender;
import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jline.console.ConsoleReader;
import net.canarymod.chat.TextFormat;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Color;

/**
 *
 * @author willem
 */
public class SysOutWriterThread extends Thread {

    private final OutputStream out;
    private final ConsoleReader reader;

    private static final Pattern colourPattern = Pattern.compile("\u00a7[0-9a-fk-or]", Pattern.CASE_INSENSITIVE);
    private static final ImmutableMap<String, Ansi.Attribute> attributeMap =
            ImmutableMap.<String, Ansi.Attribute>builder()
            .put("\u00a7k", Ansi.Attribute.NEGATIVE_ON)
            .put("\u00a7l", Ansi.Attribute.INTENSITY_BOLD)
            .put("\u00a7m", Ansi.Attribute.STRIKETHROUGH_ON)
            .put("\u00a7n", Ansi.Attribute.UNDERLINE)
            .put("\u00a7o", Ansi.Attribute.ITALIC)
            .put("\u00a7r", Ansi.Attribute.RESET)
            .build();
    private static final ImmutableMap<String, Pair<Ansi.Color, Boolean>> colourMap =
            ImmutableMap.<String, Pair<Ansi.Color, Boolean>>builder()
            .put("\u00a70", new ImmutablePair<Color, Boolean>(Ansi.Color.BLACK, false))
            .put("\u00a71", new ImmutablePair<Color, Boolean>(Ansi.Color.BLUE, false))
            .put("\u00a72", new ImmutablePair<Color, Boolean>(Ansi.Color.GREEN, false))
            .put("\u00a73", new ImmutablePair<Color, Boolean>(Ansi.Color.CYAN, false))
            .put("\u00a74", new ImmutablePair<Color, Boolean>(Ansi.Color.RED, false))
            .put("\u00a75", new ImmutablePair<Color, Boolean>(Ansi.Color.MAGENTA, false))
            .put("\u00a76", new ImmutablePair<Color, Boolean>(Ansi.Color.YELLOW, false))
            .put("\u00a77", new ImmutablePair<Color, Boolean>(Ansi.Color.WHITE, false))
            .put("\u00a78", new ImmutablePair<Color, Boolean>(Ansi.Color.BLACK, true))
            .put("\u00a79", new ImmutablePair<Color, Boolean>(Ansi.Color.BLUE, true))
            .put("\u00a7a", new ImmutablePair<Color, Boolean>(Ansi.Color.GREEN, true))
            .put("\u00a7b", new ImmutablePair<Color, Boolean>(Ansi.Color.CYAN, true))
            .put("\u00a7c", new ImmutablePair<Color, Boolean>(Ansi.Color.RED, true))
            .put("\u00a7d", new ImmutablePair<Color, Boolean>(Ansi.Color.MAGENTA, true))
            .put("\u00a7e", new ImmutablePair<Color, Boolean>(Ansi.Color.YELLOW, true))
            .put("\u00a7f", new ImmutablePair<Color, Boolean>(Ansi.Color.WHITE, true))
            .build();

    public SysOutWriterThread(OutputStream out, ConsoleReader reader) {
        this.out = out;
        this.reader = reader;
    }    

    @Override
    @SuppressWarnings("CallToThreadDumpStack")
    public void run() {
        String message; // Allocate once! :D

        while (true) {
            message = QueueLogAppender.getNextLogEvent("SysOut");
            if (message == null) {
                continue;
            }

            try {
                reader.print(ConsoleReader.RESET_LINE + "");
                reader.flush();
                out.write(replaceColours(message).getBytes());
                out.flush();

                try {
                    reader.drawLine();
                } catch (IOException e) {
                    reader.getCursorBuffer().clear();
                }
                reader.flush();
            } catch (IOException e) {
                // Don't use loggers here, since we're the logger.
                System.err.println("Error while printing to ConsoleReader");
                e.printStackTrace();
            }
        }
    }

    private String replaceColours(String toProcess) throws IOException {
        if (!reader.getTerminal().isAnsiSupported()) {
            return TextFormat.removeFormatting(toProcess);
        } else {
            Matcher matcher = colourPattern.matcher(toProcess);
            boolean result = matcher.find();
            if (result) {
                StringBuffer sb = new StringBuffer();
                do {
                    String match = matcher.group();
                    String replacement = "";
                    if (colourMap.containsKey(match)) {
                        Ansi replace = Ansi.ansi().reset();
                        replace = colourMap.get(match).getRight() ? replace.fgBright(colourMap.get(match).getLeft())
                                                                  : replace.fg(colourMap.get(match).getLeft());
                        replacement = replace.toString();
                    } else if (attributeMap.containsKey(match)) {
                        replacement = Ansi.ansi().a(attributeMap.get(match)).toString();
                    }

                    matcher.appendReplacement(sb, replacement);
                    result = matcher.find();
                } while (result);
                matcher.appendTail(sb);
                sb.append(Ansi.ansi().reset());

                return sb.toString();
            }
            return toProcess;
        }
    }
}
