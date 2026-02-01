package leo;

import leo.command.Command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Leo {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private String filepath;

    public Leo(String filepath) {
        this.filepath = filepath;
        ui = new Ui();
        storage = new Storage(filepath);
        try {
            tasks = new TaskList(storage.loadTasks());
        } catch (IOException e) {
            ui.showError("Failed to load Tasks");
            tasks = new TaskList();
        }
    }

    public void run() {
        boolean isExit = false;

        ui.showWelcome();

        Scanner scanner = new Scanner(System.in);

        //ArrayList<leo.task.Task> lists = loadTasks();
        //ArrayList<leo.task.Task> lists = new ArrayList<>();

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();

                Command command = Parser.parse(fullCommand);
                command.execute(tasks, ui, storage);
                isExit = command.isExit();
            } catch (LeoException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Leo("./data/leo_mem.txt").run();
    }
}
