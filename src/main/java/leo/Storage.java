package leo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import leo.task.Deadline;
import leo.task.Event;
import leo.task.Task;
import leo.task.Todo;

/** Manages file storage operations for task data persistence */
public class Storage {
    private static ArrayList<Task> datas = new ArrayList<>();
    private String filePath;

    /**
     * Creates a Storage instance with specified file path.
     *
     * @param filePath The path to the storage file.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from storage file.
     *
     * @return List of loaded tasks.
     * @throws IOException If file operations fail.
     */
    public ArrayList<Task> load() throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            return datas;
        }
        Scanner s = new Scanner(file);
        while (s.hasNext()) {
            Task t = convertFromSave(s.nextLine());
            assert t != null : "Converted task should not be null";
            datas.add(t);
        }
        s.close();
        return datas;
    }

    /**
     * Converts a saved string line back to a Task object.
     *
     * @param line The saved task string.
     * @return The reconstructed Task object.
     */
    public Task convertFromSave(String line) {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean marked = parts[1].equals("X");
        String task = parts[2];

        return switch (type) {
        case "T" -> new Todo(task, marked);
        case "D" -> {
            if (parts.length < 4) {
                throw new IllegalArgumentException("Corrupted save file: Deadline missing date");
            }
            yield new Deadline(task, marked, LocalDateTime.parse(parts[3]));
        }
        case "E" -> {
            if (parts.length < 5) {
                throw new IllegalArgumentException("Corrupted save file: Event missing time info");
            }
            yield new Event(task, marked, parts[3], parts[4]);
        }
        default -> throw new IllegalArgumentException("Unknown leo.task.Task Type");
        };

    }

    /**
     * Saves task data to storage file.
     *
     * @param data List of task strings to save.
     * @throws IOException If file operations fail.
     */
    public void save(ArrayList<String> data) throws IOException {
        File f = new File(filePath);
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

    /**
     * Loads tasks from storage file (alias for load()).
     *
     * @return List of loaded tasks.
     * @throws IOException If file operations fail.
     */
    public ArrayList<Task> loadTasks() throws IOException {
        ArrayList<Task> tasks = load();
        return tasks;
    }
}





