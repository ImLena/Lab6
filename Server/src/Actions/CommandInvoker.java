package Actions;

import Collections.CommandCollection;
import Other.ReadCommand;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class CommandInvoker {
        public void execute(Command command, ReadCommand com, SocketChannel channel) {
            try {
                    command.execute(com, channel);
            } catch (IllegalStateException | NullPointerException | IOException ex) {
                System.out.println("Command doesn't exist");
            }
        }
}
