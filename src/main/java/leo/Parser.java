package leo;

import leo.command.Command;
import leo.command.DeadlineCommand;
import leo.command.DeleteCommand;
import leo.command.EventCommand;
import leo.command.ExitCommand;
import leo.command.ListCommand;
import leo.command.MarkCommand;
import leo.command.TodoCommand;
import leo.command.UnmarkCommand;

public class Parser {
    enum CommandType {
        BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, UNKNOWN
    }

    public static Command parse(String line) throws LeoException {
        if (line == null || line.trim().isEmpty()) {
            throw new LeoException("Command cannot be empty.");
        }

        String[] parts = line.trim().split("\\s+", 2);
        CommandType command = parseCommand(parts[0]);

        switch (command) {
            case LIST, BYE:
                break;
            default:
                if (parts.length < 2) {
                    throw new LeoException("Missing description or index.");
                }
        }

        switch (command) {
        case LIST:
            return new ListCommand();

        case BYE:
            return new ExitCommand();

        case MARK:
            try {
                int index = Integer.parseInt(parts[1]) - 1;

                return new MarkCommand(index);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                throw new LeoException("Please provide a valid task number.");
            }

        case UNMARK:
            try {
                int index = Integer.parseInt(parts[1]) - 1;
                return new UnmarkCommand(index);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                throw new LeoException("Please provide a valid task number.");
            }

        case TODO:
            return new TodoCommand(parts[1]);

        case DEADLINE:
            return new DeadlineCommand(parts[1]);

        case EVENT:
            return new EventCommand(parts[1]);

        case DELETE:
            try {
                int index = Integer.parseInt(parts[1]) - 1;
                return new DeleteCommand(index);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                throw new LeoException("Please provide a valid task number.");
            }

        case UNKNOWN:

        default:
            throw new LeoException("Unknown command: " + parts[0]);
        }
    }

    private static CommandType parseCommand(String input) {
        try {
            return CommandType.valueOf(input.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return CommandType.UNKNOWN;
        }
    }

}
