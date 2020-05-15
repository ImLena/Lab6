package Actions;

import Other.ReadCommand;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * Класс для реализации команды info
 */

public class Info extends Command {
    private static final long serialVersionUID = 32L;
    @Override
    public  void execute (ReadCommand com, SocketChannel channel) throws IOException {
        CommandReceiver commandReceiver = new CommandReceiver(channel);
        commandReceiver.info();
    }
}
