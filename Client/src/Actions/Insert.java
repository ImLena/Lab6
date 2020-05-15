package Actions;

import java.io.IOException;
import java.util.Scanner;

/**
 * Класс для реализации команды insert
 */


public class Insert extends Command {
    transient final CommandReceiver commandReceiver;
    private static final long serialVersionUID = 32L;

    public Insert(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }
    public void help(){
        System.out.println("insert key {element} - add element by key");
    }
    public void execute(String[] args, Scanner in) {
        try {
            commandReceiver.insert(args, in);
        } catch (NumberFormatException | IOException | ClassNotFoundException | InterruptedException e) {
            System.out.println("Not correct key");
        }
    }
}
