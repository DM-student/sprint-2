import java.util.Scanner;

public class InputReceiver
{
    private static Scanner scanner = new Scanner(System.in);
    public static Integer getInt()
    {
        while (true)
        {
            try
            {
                return Integer.parseInt(scanner.nextLine());
            } catch (Throwable e)
            {
                System.out.println("Ошибка ввода, введите число.");
            }
        }
    }
    public static String getString()
    {
        while (true)
        {
            try
            {
                return scanner.nextLine();
            } catch (Throwable e)
            {
                System.out.println("Ошибка ввода.");
            }
        }
    }
}
