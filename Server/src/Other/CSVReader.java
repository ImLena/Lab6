package Other;

import Collections.TicketMap;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Класс, считывающий данные из файла и записывающий их в коллекцию
 */


public class CSVReader{

    public static Date initdate = new Date();
    TicketMap tm = new TicketMap();


    public void CSVReader(String path) {

        String csvFile = path;
        Path p = Paths.get(path);
        String line = "";

        try {
            if (Files.isReadable(p)) {
                FileInputStream fstream = new FileInputStream(csvFile);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

                while ((line = br.readLine()) != null) {
                    tm.parseToMap(line);


                }
            }else System.out.println("You can't read file");

        } catch (FileNotFoundException e) {
            System.out.println("This file doesn't exist, check path and name and restart program");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e){
            System.out.println("Mistake in file");
        }
    }


}
