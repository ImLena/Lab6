package Other;

import Actions.Command;
import Collections.Ticket;

import java.io.Serializable;

/**
 * Класс, служащий для передачи команд с клиента на сервер
 */
public class ReadCommand implements Serializable {
    private Command comm;
    private String strArgs;
    private Ticket tic;
    private static final long serialVersionUID = 32L;

    public ReadCommand(Command comm, String strArgs, Ticket tic) {
        this.comm=comm;
        this.strArgs=strArgs;
        this.tic = tic;
    }

    public Command getComm() {
        return comm;
    }

    public Ticket getTicket() {
        return tic;
    }

    public String getStrArgs() {
        return strArgs;
    }

}
