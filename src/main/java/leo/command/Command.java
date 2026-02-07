package leo.command;

import leo.LeoException;
import leo.Storage;
import leo.TaskList;
import leo.Ui;

/**
 * Abstract base class for all executable commands in the Leo application.
 * Concrete command classes must implement the execute method.
 */
public abstract class Command {
    /**
     * Executes the command with the given task list, UI, and storage.
     *
     * @param tasks The task list to operate on.
     * @param ui The user interface for displaying results.
     * @param storage The storage system for saving changes.
     * @return The appropriate line that Leo would reply with for GUI
     * @throws LeoException If the command cannot be executed.
     */
    public abstract String execute(TaskList tasks, Ui ui, Storage storage) throws LeoException;

    /**
     * Checks if this command is an exit command.
     *
     * @return true if the command should exit the application, false otherwise.
     */
    public boolean isExit() {
        return false;
    }
}
