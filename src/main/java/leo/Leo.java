package leo;

import java.io.IOException;

import leo.command.Command;



/**
 * The main class for the Leo task management application.
 * Leo is a chatbot that helps users manage their tasks, including todos,
 * deadlines, and events. It provides functionality to add, delete, mark,
 * unmark, and list tasks, with persistent storage to a file.
 */
public class Leo {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private String startupMessage = null;



    /**
     * Constructs a new Leo instance with the specified file path for data storage.
     * Initializes the user interface, storage system, and loads existing tasks
     * from the storage file. If loading fails, starts with an empty task list.
     *
     * @param filepath The path to the file where tasks will be stored and loaded from.
     */
    public Leo(String filepath) {
        ui = new Ui();
        storage = new Storage(filepath);
        try {
            tasks = new TaskList(storage.loadTasks());
        } catch (IOException e) {
            //ui.showError("Failed to load Tasks");
            tasks = new TaskList();
            startupMessage = "No previous save found. Starting with an empty task list.";

        }
    }

    /**
     * Starts the Leo application and enters the main command loop.
     * Displays a welcome message, then continuously reads user commands,
     * parses them, executes the corresponding actions, and displays results
     * until an exit command is received.
     */
    public void run() {
        boolean isExit = false;
        ui.showWelcome();
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

    /**
     * The main entry point for the Leo application.
     * Creates a new Leo instance with the default storage file path
     * and starts the application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Leo("./data/leo_mem.txt").run();
    }

    public String getResponse(String input) {
        try {
            Command command = Parser.parse(input);
            return command.execute(tasks, ui, storage);
        } catch (LeoException e) {
            return e.getMessage();
        }
    }

    public String getStartupMessage() {
        return startupMessage;
    }

}
