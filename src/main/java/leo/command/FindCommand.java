package leo.command;

import java.util.ArrayList;

import leo.LeoException;
import leo.Storage;
import leo.TaskList;
import leo.Ui;
import leo.task.Task;

/** Command to search for tasks containing a specific keyword. */
public class FindCommand extends Command {
    private String taskDesc;
    public FindCommand(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    /**
     * @inheritDoc
     *      Obtains an ArrayList of Tasks that contains the matching keyword
     *      and display the results to the user.
     *
     * @throws LeoException Throws when no matching keyword found.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws LeoException {
        ArrayList<Task> taskFound = tasks.find(taskDesc);
        StringBuilder listText = new StringBuilder();

        for (int i = 0; i < taskFound.size(); i++) {
            listText.append((i + 1))
                    .append(". ")
                    .append(taskFound.get(i))
                    .append("\n");
        }
        return ui.showLeoReply(listText.toString());
    }


    @Override
    public boolean isExit() {
        return false;
    }
}
