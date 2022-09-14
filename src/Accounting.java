import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Accounting {
    public static class MonthReport
    {
        public int month;
        public int year;
        public List<String> item_name;
        public List<Boolean> is_expense;
        public List<Integer> quantity;
        public List<Integer> sum_of_one;



        public MonthReport(String path) throws IOException {

            month = Integer.parseInt(path.substring(path.length() - 6, path.length() - 4));
            year = Integer.parseInt(path.substring(path.length() - 10, path.length() - 6));
            item_name = new ArrayList<>();
            is_expense = new ArrayList<>();
            quantity = new ArrayList<>();
            sum_of_one = new ArrayList<>();

            List<String> strList = Files.readAllLines(Path.of(path));
            strList.remove(0);
            for(String str : strList)
            {
                String[] strParsed = str.split(",");
                item_name.add(strParsed[0]);
                is_expense.add(Boolean.parseBoolean(strParsed[1]));
                quantity.add(Integer.parseInt(strParsed[2]));
                sum_of_one.add(Integer.parseInt(strParsed[3]));
            }
        }
        public MonthReport()
        {
            item_name = new ArrayList<>();
            is_expense = new ArrayList<>();
            quantity = new ArrayList<>();
            sum_of_one = new ArrayList<>();
        }
        public int maxExpenseIndex()
        {
            int max = 0;
            int maxIndex = -1;
            for(int i = 0; i < item_name.size(); i++)
            {
                if(is_expense.get(i) && sum_of_one.get(i) * quantity.get(i) > max)
                {
                    maxIndex = i;
                    max = sum_of_one.get(i) * quantity.get(i);
                }
            }
            return maxIndex;
        }
        public int maxGainIndex()
        {
            int max = 0;
            int maxIndex = -1;
            for(int i = 0; i < item_name.size(); i++)
            {
                if(!is_expense.get(i) && sum_of_one.get(i) * quantity.get(i) > max)
                {
                    maxIndex = i;
                    max = sum_of_one.get(i) * quantity.get(i);
                }
            }
            return maxIndex;
        }
    }

    public static class YearReport
    {
        public int year;
        public List<Integer> month;
        public List<Boolean> is_expense;
        public List<Integer> amount;



        public YearReport(String path) throws IOException {
            year = Integer.parseInt(path.substring(path.length() - 8, path.length() - 4));
            month = new ArrayList<>();
            amount = new ArrayList<>();
            is_expense = new ArrayList<>();

            List<String> strList = Files.readAllLines(Path.of(path));
            strList.remove(0);
            for(String str : strList)
            {
                String[] strParsed = str.split(",");
                month.add(Integer.parseInt(strParsed[0]));
                amount.add(Integer.parseInt(strParsed[1]));
                is_expense.add(Boolean.parseBoolean(strParsed[2]));
            }

        }
        public YearReport()
        {
            month = new ArrayList<>();
            amount = new ArrayList<>();
            is_expense = new ArrayList<>();
        }
    }

    public static String monthName(int month)
    {
        switch (month)
        {
            case 1:
                return "Январь";
            case 2:
                return "Февраль";
            case 3:
                return "Март";
            case 4:
                return "Апрель";
            case 5:
                return "Май";
            case 6:
                return "Июнь";
            case 7:
                return "Июль";
            case 8:
                return "Август";
            case 9:
                return "Сентябрь";
            case 10:
                return "Октябрь";
            case 11:
                return "Ноябрь";
            case 12:
                return "Декабрь";
            default:
                return null;
        }
    }
}
