package Actions;

import Other.ReadCommand;
import Requests.Server;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * Класс для реализации команды count_greater_than_price
 */

public class CountGreaterThanPrice extends Command {
    private static final long serialVersionUID = 32L;

    @Override
    public void execute(ReadCommand com, SocketChannel channel) throws IOException {
        CommandReceiver commandReceiver = new CommandReceiver(channel);
        String price = com.getStrArgs();
        float pr = Float.parseFloat(price);

        try {
            commandReceiver.count_greater_than_price(pr);
        }catch (NumberFormatException e){
            Server.answer(channel, "Wrong type of price, enter command again");
        } catch (NullPointerException e){

        }
    }
}
