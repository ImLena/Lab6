package Actions;

import Collections.Ticket;
import Other.ReadCommand;
import Requests.Server;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * Класс для реализации команды insert
 */


public class Insert extends Command {
    private static final long serialVersionUID = 32L;

    @Override
    public  void execute(ReadCommand com, SocketChannel channel) throws IOException {
        CommandReceiver commandReceiver = new CommandReceiver(channel);
        try {
            String arg = com.getStrArgs();
            Long id = Long.valueOf(arg);
            Ticket tic = com.getTicket();
            if (tic != null) {
                commandReceiver.insert(id, tic);
            } else {
                Server.answer(channel, "Ticket expected");
            }
        } catch (NumberFormatException | IOException e) {
            Server.answer(channel, "Not correct key");
        }
    }
}
