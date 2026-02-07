package leo.command;

import leo.LeoException;
import leo.Storage;
import leo.TaskList;
import leo.Ui;

/** Command to display all tasks in the list. */
public class ListCommand extends Command {

    /**
     * @inheritDoc
     *      Executes list command by displaying all tasks with numbering.
     */

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws LeoException {
        if (tasks.isEmpty()) {
            throw new LeoException("(List is empty)");
        } else {
            StringBuilder listText = new StringBuilder();
            for (int i = 0; i < tasks.size(); i++) {
                listText.append((i + 1))
                        .append(". ")
                        .append(tasks.get(i))
                        .append("\n");
            }
            return ui.showLeoReply(listText.toString());
        }
    }
}
