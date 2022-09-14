import java.io.File;
import java.util.*;
public class Main {

    static Map<String, Accounting.MonthReport> monthReports = new HashMap<>();
    static Map<String ,Accounting.YearReport> yearReports = new HashMap<>();


    public static void main(String[] args) {

        boolean exitProgram = false;
        while (!exitProgram)
        {
            int year;

            printMenu();

            switch (InputReceiver.getInt())
            {
                case 1:
                    System.out.println("Введите путь к файлу или папке с файлами:");
                    readMonthFiles(InputReceiver.getString());
                    break;
                case 2:
                    System.out.println("Введите путь к файлу или папке с файлами:");
                    readYearFiles(InputReceiver.getString());
                    break;
                case 3:
                    System.out.println("Введите год:");
                    year = InputReceiver.getInt();
                    if(year < 1000 || year > 9999){System.out.println("Ошибка, поддерживаемые значения: 1000-9999");}
                    else{checkReports(year);}
                    break;
                case 4:
                    System.out.println("Введите год:");
                    year = InputReceiver.getInt();
                    if(year < 1000 || year > 9999){System.out.println("Ошибка, поддерживаемые значения: 1000-9999");}
                    else{printMonthStats(year);}
                    break;
                case 5:
                    System.out.println("Введите год:");
                    year = InputReceiver.getInt();
                    if(year < 1000 || year > 9999){System.out.println("Ошибка, поддерживаемые значения: 1000-9999");}
                    else{printYearStats(year);}
                    break;
                case 6:
                    System.out.println("Выхожу.");
                    exitProgram = true;
                    break;
                default:
                    System.out.println("Такой команды нет.");
                    break;
            }
        }
    }

    static void printMenu()
    {
        System.out.println("1. Считать все месячные отчёты.");
        System.out.println("2. Считать годовой отчёт.");
        System.out.println("3. Сверить отчёты.");
        System.out.println("4. Вывести информацию о всех месячных отчётах.");
        System.out.println("5. Вывести информацию о годовом отчёте.");
        System.out.println("6. Выйти из приложения.");
        System.out.println("Введите число, соответствующее вашему выбору:");
    }

    static void checkReports(int year)
    {
        try {
            if(!yearReports.containsKey(Integer.toString(year)))
            {
                System.out.println("Не было обработано ни единого файла годового отчёта по указаному году.");
                return;
            }

            List<Integer> monthWithErrs = new ArrayList<>();
            Accounting.YearReport yearRep = yearReports.get(Integer.toString(year));



            for (int m = 1; m <= 12; m++)
            {
                if(yearRep.month.contains(m))
                {
                    int yearExpenses = 0;
                    int yearRevenue = 0;
                    for(int i = 0; i < yearRep.month.size(); i++)
                    {
                        if(yearRep.month.get(i) == m)
                        {
                            if(yearRep.is_expense.get(i))
                            {
                                yearExpenses += yearRep.amount.get(i);
                            }
                            else
                            {
                                yearRevenue += yearRep.amount.get(i);
                            }
                        }
                    }
                    int monthExpenses = 0;
                    int monthRevenue = 0;
                    String key = Integer.toString(year) + Integer.toString(m);
                    if(monthReports.containsKey(key))
                    {
                        for(int i = 0; i < monthReports.get(key).item_name.size(); i++)
                        {
                            if(monthReports.get(key).is_expense.get(i))
                            {
                                monthExpenses += (monthReports.get(key).sum_of_one.get(i) * monthReports.get(key).quantity.get(i));
                            }
                            else
                            {
                                monthRevenue += (monthReports.get(key).sum_of_one.get(i) * monthReports.get(key).quantity.get(i));
                            }
                        }
                        if (monthRevenue != yearRevenue && monthExpenses != yearExpenses)
                        {
                            monthWithErrs.add(m);
                        }
                    }
                    else
                    {
                        monthWithErrs.add(m);
                    }
                }
            }

            if(monthWithErrs.size() == 0)
            {
                System.out.println("Годовой отчёт соответствует месечным отчётам за этот год.");
                return;
            }
            for(Integer n : monthWithErrs)
            {
                System.out.println("Отчёт за " + Accounting.monthName(n).toLowerCase() + " не соответствует годовому отчёту.");
            }
        }
        catch (Throwable e)
        {
            System.out.println("Возникла ошибка обработки, код: " + e.toString());
        }
    }

    static void printMonthStats(int year)
    {
        try {


            List<Accounting.MonthReport> monthReportsList = new ArrayList<>();
            for (int i = 1; i < 13; i++) {
                String key = Integer.toString(year) + Integer.toString(i);
                if (monthReports.containsKey(key)) {
                    monthReportsList.add(monthReports.get(key));
                }
            }
            if (monthReportsList.size() == 0) {
                System.out.println("Не было обработано ни единого файла по указаному году.");
                return;
            }
            System.out.println("*****");
            for (Accounting.MonthReport month : monthReportsList) {
                System.out.println("Месяц: " + Accounting.monthName(month.month));
                int n;
                n = month.maxGainIndex();
                System.out.println("Самый прибыльный товар/услуга: " + month.item_name.get(n) + ", Вырученая прибыль: " + (month.sum_of_one.get(n) * month.quantity.get(n)));
                n = month.maxExpenseIndex();
                System.out.println("Самая большая трата: " + month.item_name.get(n) + ", Потрачено по этому пункту: " + (month.sum_of_one.get(n) * month.quantity.get(n)));
                System.out.println("*****");
            }
        }
        catch (Throwable e)
        {
            System.out.println("Возникла ошибка обработки, код: " + e.toString());
        }
    }

    static void printYearStats(int year)
    {
        try {


            if (!yearReports.containsKey(Integer.toString(year))) {
                System.out.println("Не было обработано ни единого файла по указаному году.");
                return;
            }
            Accounting.YearReport yearRep = yearReports.get(Integer.toString(year));
            System.out.println("Прибыль за " + yearRep.year + " год:");
            int expenses = 0;
            int revenue = 0;
            int months = 0;
            for (int m = 1; m <= 12; m++) {
                if (yearRep.month.contains(m)) {
                    months++;
                    int gain = 0;
                    System.out.print(Accounting.monthName(m) + ": ");
                    for (int i = 0; i < yearRep.month.size(); i++) {
                        if (yearRep.month.get(i) == m) {
                            if (yearRep.is_expense.get(i)) {
                                gain -= yearRep.amount.get(i);
                                expenses += yearRep.amount.get(i);
                            } else {
                                gain += yearRep.amount.get(i);
                                revenue += yearRep.amount.get(i);
                            }
                        }
                    }
                    System.out.println(gain);
                }
            }
            System.out.println("*****");
            System.out.println("Средний расход за все месяцы в году: " + (expenses / months));
            System.out.println("Средний доход за все месяцы в году: " + (revenue / months));
            System.out.println("*****");
        }
        catch (Throwable e)
        {
            System.out.println("Возникла ошибка обработки, код: " + e.toString());
        }
    }

    static void readYearFiles(String path)
    {

        try {
            String postfix = path.substring(path.length() - 4, path.length()).toLowerCase();
            String prefix = path.substring(path.length() - 10, path.length() - 8).toLowerCase();

            if(postfix.equals(".csv") && prefix.equals("y."))
            {
                try
                {
                    Accounting.YearReport yearReport = new Accounting.YearReport(path);
                    String key = Integer.toString(yearReport.year);
                    if(yearReports.containsKey(key))
                    {
                        yearReports.replace(key, yearReport);
                    }
                    else
                    {
                        yearReports.put(key, yearReport);
                    }
                    System.out.println("Файл обработан.");
                }
                catch (Throwable e)
                {
                    System.out.println("Ошибка обработки файла.");
                }
            }
            else
            {
                int errs = 0;
                File folder = new File(path);
                File[] listOfFiles = folder.listFiles();
                for (int i = 0; i < listOfFiles.length; i++)
                {
                    try
                    {
                        String filePath = listOfFiles[i].getPath();
                        postfix = filePath.substring(filePath.length() - 4, filePath.length()).toLowerCase();
                        prefix = filePath.substring(filePath.length() - 10, filePath.length() - 8).toLowerCase();
                        if(postfix.equals(".csv") && prefix.equals("y."))
                        {
                            Accounting.YearReport yearReport = new Accounting.YearReport(filePath);
                            String key = Integer.toString(yearReport.year);
                            if(yearReports.containsKey(key))
                            {
                                yearReports.replace(key, yearReport);
                            }
                            else
                            {
                                yearReports.put(key, yearReport);
                            }
                        }
                        else {throw new Exception("Wrong format or prefix.");}
                    }
                    catch (Throwable e)
                    {
                        errs++;
                    }
                }

                System.out.println("Обработано файлов: " + (listOfFiles.length - errs));
            }
        }
        catch (Throwable e)
        {
            System.out.println("Ошибка обработки, проверьте наличие файла/папки, его название и свойства.");
        }
    }

    static void readMonthFiles(String path)
    {

        try {
            String postfix = path.substring(path.length() - 4, path.length()).toLowerCase();
            String prefix = path.substring(path.length() - 12, path.length() - 10).toLowerCase();

            if(postfix.equals(".csv") && prefix.equals("m."))
            {
                try
                {
                    Accounting.MonthReport monthReport = new Accounting.MonthReport(path);
                    String key = Integer.toString(monthReport.year) + Integer.toString(monthReport.month);
                    if(monthReports.containsKey(key))
                    {
                        monthReports.replace(key, monthReport);
                    }
                    else
                    {
                        monthReports.put(key, monthReport);
                    }
                    System.out.println("Файл обработан.");
                }
                catch (Throwable e)
                {
                    System.out.println("Ошибка обработки файла.");
                }
            }
            else
            {
                int errs = 0;
                File folder = new File(path);
                File[] listOfFiles = folder.listFiles();
                for (int i = 0; i < listOfFiles.length; i++)
                {
                    try
                    {
                        String filePath = listOfFiles[i].getPath();
                        postfix = filePath.substring(filePath.length() - 4, filePath.length()).toLowerCase();
                        prefix = filePath.substring(filePath.length() - 12, filePath.length() - 10).toLowerCase();
                        if(postfix.equals(".csv") && prefix.equals("m."))
                        {
                            Accounting.MonthReport monthReport = new Accounting.MonthReport(filePath);
                            String key = Integer.toString(monthReport.year) + Integer.toString(monthReport.month);
                            if(monthReports.containsKey(key))
                            {
                                monthReports.replace(key, monthReport);
                            }
                            else
                            {
                                monthReports.put(key, monthReport);
                            }
                        }
                        else {throw new Exception("Wrong format or prefix.");}
                    }
                    catch (Throwable e)
                    {
                        errs++;
                    }
                }

                System.out.println("Обработано файлов: " + (listOfFiles.length - errs));
            }
        }
        catch (Throwable e)
        {
            System.out.println("Ошибка обработки, проверьте наличие файла/папки, его название и свойства.");
        }
    }
}

