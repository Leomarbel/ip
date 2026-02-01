package leo;

import leo.command.*;

public class Parser {
    enum Command_Enum {
        BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, UNKNOWN
    }
    public static Command parse(String commands) throws LeoException {

        String[] parts = commands.trim().split("\\s+", 2);
        Command_Enum command = ParseCommand(parts[0]);

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
            throw new LeoException("Unknown command: " + command);
        }
    }

    private static Command_Enum ParseCommand(String input) {
        try {
            return Command_Enum.valueOf(input.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return Command_Enum.UNKNOWN;
        }
    }

}
