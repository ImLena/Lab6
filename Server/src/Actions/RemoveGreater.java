package Actions;

import Collections.Ticket;
import Other.ReadCommand;
import Requests.Server;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * Класс для реализации команды remove_greater
 */

public class RemoveGreater extends Command{

    private static final long serialVersionUID = 32L;

    @Override
    public void execute(ReadCommand com, SocketChannel channel) throws IOException {
        CommandReceiver commandReceiver = new CommandReceiver(channel);
        try{
        Ticket tic = com.getTicket();
            if (tic != null) {
                commandReceiver.remove_greater(tic);
            }else {
                Server.answer(channel, "Ticket expected");
            }
        } catch (NumberFormatException e) {
            Server.answer(channel, "Not correct key");
        }
    }
}
