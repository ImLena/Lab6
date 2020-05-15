package Actions;

import Other.ReadCommand;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * Класс для реализации команды min_by_creation_date
 */

public class MinByCreationDate extends Command {

    private static final long serialVersionUID = 32L;

    @Override
    public void execute(ReadCommand c, SocketChannel channel) throws IOException {
        CommandReceiver commandReceiver = new CommandReceiver(channel);
        commandReceiver.min_by_creation_date();
    }
}
