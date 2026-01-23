public class Leo {
    public static void main(String[] args) {
        String logo = "██╗     ███████╗ ██████╗ \n" +
                      "██║     ██╔════╝██╔═══██╗ \n" +
                      "██║     █████╗  ██║   ██║ \n" +
                      "██║     ██╔══╝  ██║   ██║ \n" +
                      "███████╗███████╗╚██████╔╝ \n" +
                      "╚══════╝╚══════╝ ╚═════╝ ";


        PrintSep();
        System.out.println("Hello! I'm \n" + logo);
        System.out.println("What can I do for you? \n");
        PrintSep();
        System.out.println("Bye. Hope to see you again soon!\n");
    }

    public static void PrintSep() {
        String sep = "─────────────────✦─────────────────";
        System.out.println(sep);
    }
}