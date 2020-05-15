package Actions;

import Other.ReadCommand;

import java.io.IOException;
import java.io.Serializable;
import java.nio.channels.SocketChannel;

public abstract class Command implements Serializable {

    private static final long serialVersionUID = 32L;
    public abstract void execute(ReadCommand com, SocketChannel channel) throws IOException;

}
