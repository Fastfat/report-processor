import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        // Поехали!
        Scanner sc = new Scanner(System.in);
        MonthlyReport monthlyReport = new MonthlyReport();
        YearlyReport yearlyReport = monthlyReport.year;
        try{
            while (true) {
                printMenu();
                int cmd = sc.nextInt();
                if (cmd == 1){
                    monthlyReport.readFileMonths();
                }
                else if (cmd == 2){
                    yearlyReport.readFileYear();
                }
                else if (cmd == 3){
                    yearlyReport.reportAnalysis();
                }
                else if (cmd == 4){
                    monthlyReport.monthsOutput();
                }
                else if (cmd == 5){
                    yearlyReport.yearOutput();
                }
                else if (cmd == 6) {
                    break;
                }
                else
                    System.out.println("Неправильный ввод команды.");
            }
        } catch (Exception e) {
            System.out.println("Неправильный ввод команды.");
        }

    }
    static void printMenu(){
        System.out.println("Введите команду:");
        System.out.println("1. Считать все месячные отчёты");
        System.out.println("2. Считать годовой отчёт");
        System.out.println("3. Сверить отчёты");
        System.out.println("4. Вывести информацию о всех месячных отчётах");
        System.out.println("5. Вывести информацию о годовом отчёте");
        System.out.println("6. Выход");
    }
}

