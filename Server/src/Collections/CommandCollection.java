package Collections;

import Actions.Command;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class CommandCollection implements Serializable {
    private Command clear;
    private Command cgtp;
    private Command es;
    public Command help;
    private Command history;
    private Command info;
    private Command insert;
    private Command mbcd;
    private Command pd;
    private Command remove;
    private Command rg;
    private Command rig;
    private Command show;
    private Command update;

    private static final long serialVersionUID = 123456789L;

    private final LinkedHashMap<String, Command> commands = new LinkedHashMap<>();
    public void addCom(){
        commands.put("clear", clear);
        commands.put("count_greater_than_price", cgtp);
        commands.put("execute_script", es);
        commands.put("help", help);
        commands.put("history", history);
        commands.put("info", info);
        commands.put("insert", insert);
        commands.put("min_by_creation_date", mbcd);
        commands.put("print_descending", pd);
        commands.put("remove_key", remove);
        commands.put("remove_greater", rg);
        commands.put("replace_if_greater", rig);
        commands.put("show", show);
        commands.put("update", update);
    }

    public Command getCommand(String str){
        return commands.get(str);
    }

    public LinkedHashMap<String, Command> getCommands(){
        return commands;
    }

}
