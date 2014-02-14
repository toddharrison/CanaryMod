package net.canarymod.util;

import java.io.IOException;
import java.util.List;
import jline.console.ConsoleReader;
import jline.console.UserInterruptException;
import jline.console.completer.Completer;
import net.canarymod.Canary;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.dedicated.DedicatedServer;
import org.apache.logging.log4j.LogManager;
import org.fusesource.jansi.Ansi;

/**
 * Thread that handles the console I/O.
 *
 * @author Willem Mulder
 */
public class ConsoleHandlerThread extends Thread {
    private final ConsoleReader reader;
    private final DedicatedServer server;

    public ConsoleHandlerThread(DedicatedServer server, ConsoleReader reader) {
        this.reader = reader;
        this.server = server;

        setName("Console handler");
    }

    @Override
    public void run() {
        try {
            reader.setPrompt("> ");
            reader.setHandleUserInterrupt(true);
            reader.addCompleter(new Completer() {
                @Override
                public int complete(String buffer, int cursor, List<CharSequence> candidates) {
                    String toComplete = buffer.substring(0, cursor);
                    String[] args = toComplete.split("\\s+");

                    List<String> completions;
                    if (toComplete.indexOf(' ') < 0) {
                        completions = Canary.commands().matchCommandNames(Canary.getServer(), toComplete, false);
                    } else {
                        completions = Canary.commands().tabComplete(Canary.getServer(), args[0], args);
                    }

                    if (completions == null) {
                        return -1;
                    }

                    candidates.addAll(completions);
                    return candidates.size() > 0 ? toComplete.lastIndexOf(' ') + 1 : -1;
                }
            });

            while (!interrupted() && !server.ae() && server.p()) {
                try {
                    String line = reader.readLine();
                    if (line == null) {
                        throw new UserInterruptException(null);
                    }

                    server.a(line, (ICommandSender) server);
                } catch (UserInterruptException e) {
                    String line = e.getPartialLine();
                    if (line != null && !line.isEmpty()) {
                        // Print a nice ^C where they ^C'd
                        reader.print(Ansi.ansi().cursorUp(1).a("\r> ").a(line).a("^C").cursorDown(1).a("\r").toString());
                        continue;
                    }
                    Canary.commands().parseCommand(Canary.getServer(), "stop", new String[]{"stop",
                                                                                    line == null ? "EOF" : "Ctrl-C",
                                                                                    "at", "console"});
                    break;
                }
            }

            reader.print("\r"); // remove the prompt
            reader.shutdown();
        } catch (IOException e) {
            LogManager.getLogger().error("Exception handling console input", e);
        }

    }

    public void shutdown() {
        System.err.println("Shutting down console reader");
        try {
            reader.print("\r"); // remove the prompt
        } catch (IOException notAllThatImportant) {}
        reader.shutdown();
    }
}
