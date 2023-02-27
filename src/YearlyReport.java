import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class YearlyReport {
    HashMap<Integer, ArrayList<String[]>> yearlyInfo = new HashMap<>();
    ArrayList<String[]> yearly = new ArrayList<>();
    String path = "";
    String readingResult;
    String[] result;
    String[] resultValues;
    int yearDate;
    int wrongCounter = 0;
    HashMap<Integer, ArrayList<Integer>> monthsInfo = new HashMap<>();

    public YearlyReport(){}
    public YearlyReport(HashMap<Integer, ArrayList<Integer>> monthsInfo){
        this.monthsInfo = monthsInfo;
    }

    public void readFileYear()
    {
        try{
            File dir = new File("C:\\Users\\User\\dev\\java-sprint1-hw\\resources");
            for(File item : Objects.requireNonNull(dir.listFiles())){
                path = dir + "\\" + item.getName();
                readingResult = readFileContentsOrNull(path);
                String firstLetter = String.valueOf(item.getName().charAt(0));

                if (firstLetter.equals("y")){
                    yearDate = Integer.parseInt(item.getName().substring(2, 6)); //Узнаем год файла
                    result = readingResult.split("\\n");

                    for (int i = 1; i < result.length; i++) {
                        resultValues = result[i].split(",");
                        yearly.add(resultValues);
                    }
                    yearlyInfo.put(yearDate, yearly);
                    yearly = new ArrayList<>();
                }
                else if (!firstLetter.equals("m"))
                    wrongCounter++;
            }
            System.out.println("Годовой отчет был считан!");
            if (wrongCounter == 1)
                System.out.println("В вашей папке, находящейся по пути " + dir + " есть 1 файл с неправильным именем.");
            else if (wrongCounter > 1)
                System.out.println("В вашей папке, находящейся по пути " + dir + " есть " + wrongCounter + " файлов с неправильным именем.");
        } catch (NullPointerException e) {
            System.out.println("В данной директории отсутствуют файлы!");
        }
    }
    public void yearOutput(){
        if (yearlyInfo.keySet().isEmpty())
            System.out.println("Информации о годовом отчете не было найдено.");

        int numOfProfit = 0;
        int numOfExpense = 0;
        int trueNumMonth = -1;
        int falseNumMonth = -2;
        int profit = 0;
        int profitCount = 0;
        int expense = 0;
        int expenseCount = 0;
        for (int numYear : yearlyInfo.keySet()) {
            yearly = yearlyInfo.get(numYear);
            System.out.println(numYear);
            for (String[] numMassive : yearly) {
                if (numMassive[2].equals("false")){
                    falseNumMonth = Integer.parseInt(numMassive[0]);
                    numOfProfit = Integer.parseInt(numMassive[1]);
                    profit += numOfProfit;
                    profitCount ++;
                }
                else if (numMassive[2].equals("true")){
                    trueNumMonth = Integer.parseInt(numMassive[0]);
                    numOfExpense = Integer.parseInt(numMassive[1]);
                    expense += numOfExpense;
                    expenseCount ++;
                }
                if (trueNumMonth == falseNumMonth){
                    System.out.println("Прибыль за " + trueNumMonth + "-й месяц составила: " + (numOfProfit - numOfExpense));
                }
            }
            System.out.println("Средний расход за все месяцы в году: " + ((double) expense / expenseCount));
            System.out.println("Средний доход за все месяцы в году: " + ((double) profit / profitCount));
        }
    }
    public void reportAnalysis() {
        if ((yearlyInfo.keySet().isEmpty()) && (monthsInfo.keySet().isEmpty())) {
            System.out.println("Информации о годовом и месячных отчетах не было найдено.");
            return;
        }
        else if (yearlyInfo.keySet().isEmpty()) {
            System.out.println("Информации о годовом отчете не было найдено.");
            return;
        }
        else if (monthsInfo.keySet().isEmpty()) {
            System.out.println("Информации о месячных отчетах не было найдено.");
            return;
        }
        int trueNumMonth = -1;
        int falseNumMonth = -2;
        int numOfProfit = 0;
        int numOfExpense = 0;
        int reportsCounter = 0;
        for (int numYear : yearlyInfo.keySet()) {
            yearly = yearlyInfo.get(numYear);
            for (String[] numMassive : yearly) {
                if (numMassive[2].equals("false")){
                    falseNumMonth = Integer.parseInt(numMassive[0]);
                    numOfProfit = Integer.parseInt(numMassive[1]);
                }
                else if (numMassive[2].equals("true")){
                    trueNumMonth = Integer.parseInt(numMassive[0]);
                    numOfExpense = Integer.parseInt(numMassive[1]);
                }
                if (trueNumMonth == falseNumMonth){
                    ArrayList<Integer> monthlyValue = monthsInfo.get(trueNumMonth);
                    if ((numOfProfit != monthlyValue.get(0)) || (numOfExpense != monthlyValue.get(1))){
                        System.out.println("Обнаружено несоответствие в месяце: " + numMonthString(Integer.parseInt(numMassive[0])));
                        reportsCounter++;
                    }
                }
            }
        }
        if (reportsCounter == 0)
            System.out.println("Несоответствий обнаружено не было!");
    }
    private String readFileContentsOrNull(String path)
    {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }
    public String numMonthString(int numMonth) {
        List<String> months = Arrays.asList("Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь");
        if (numMonth > 0 && numMonth < 13)
            return months.get(numMonth-1);
        return null;
    }
}
