
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Storage {
    private static final String FILE_PATH = "./data/leo_mem.txt";
    static ArrayList<Leo.Task> data = new ArrayList<>();

    public static ArrayList<Leo.Task> load() throws IOException {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            Leo.LeoReply("No File Detected, created new file");
            return data;
        }

        Scanner s = new Scanner(file);

        while (s.hasNext()) {
            data.add(fromSaveState(s.nextLine()));
        }

        s.close();
        return data;
    }

    public static Leo.Task fromSaveState(String line) {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean marked = parts[1].equals("X");
        String task = parts[2];

        return switch (type) {
            case "T" -> new Leo.Todo(task, marked);
            case "D" -> new Leo.Deadline(task, marked, parts[3]);
            case "E" -> new Leo.Event(task, marked, parts[3], parts[4]);
            default -> throw new IllegalArgumentException("Unknown Task Type");
        };

    }


    public static void save (ArrayList<String> data) throws IOException{
        File f = new File(FILE_PATH);
        if (!f.exists()) {
            f.getParentFile().mkdirs();
            f.createNewFile();
        }


        FileWriter fw = new FileWriter(f);
        for (String line : data) {
            fw.write(line);
            fw.write(System.lineSeparator());
        }
        fw.close();


    }
}





