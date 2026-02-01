package leo.command;

import leo.LeoException;
import leo.Storage;
import leo.TaskList;
import leo.Ui;

import java.util.ArrayList;

public class ListCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LeoException {
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
            ui.showLeoReply(listText.toString());
        }
    }
}
