import java.util.Scanner;
import java.util.concurrent.TimeUnit;
public class Main {
    // Input:  --help
    // Output: Msg
    // Cmd:  -w - сколько работать
    //       -b - сколько отдыхать
    //       --help - вызвать помощь
    // Input -w 1 -b 1
    static boolean isTest = false;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Ehi Pomodoro. Напиши, пожалуйста, команду");
        // Считываем ввод пользователя
        String[] userInput = new Scanner(System.in).nextLine().split(" ");
        System.out.println("Пользователь ввел >> " + userInput);

        // все время в минутах
        // время работы
        int workTime =  1;
        // время отдыха
        int breakTime = 1;
        // длина рисунка progress bar
        int sizeBreak = 30;
        int sizeWork = 30;
        int m = 1;
        boolean isCallHelp = false;
        int count = 1;


        for (int i = 0; i < userInput.length; i++) {

            switch (userInput[i]) {
                case "--help" -> {
                    System.out.println("""
                            \n Pomodoro - это приложения для улучшения личной эффективности.
                            -w <time>      - время работы, сколько хочешь работать
                            -b <time>      - время отдыха, сколько хочешь отдыхать
                            -m <count>     - множитель, увеличивает время работы
                            -count <count> - количество итераций
                            --help - вызвать помощь
                            """);
                    isCallHelp = true;
                }
                case "-w" -> workTime = Integer.parseInt(userInput[++i]);
                case "-b" -> breakTime = Integer.parseInt(userInput[++i]);
                case "-count" -> count = Integer.parseInt(userInput[++i]);
                case "-m" -> m = Integer.parseInt(userInput[++i]);
                case "-t" -> isTest = true;

            }

        }

        if (!isCallHelp) {
            System.out.printf("Работаем %d min, отдыхаем %d min, количество подходов %d, множитель %d\n", workTime, breakTime, count, m);
            long startTime = System.currentTimeMillis();

            for (int i = 1; i <= count; i++) {
                timer(workTime, breakTime, sizeBreak, sizeWork);
                workTime*=m;
            }

            long endTime = System.currentTimeMillis();
            System.out.println("Таймер работал " + (endTime - startTime) / (1000 * 60) + " min");
        }

    }

    public static void timer(int workTimeMin, int breakTimeMin, int sizeBreak, int sizeWork) throws InterruptedException {
        printProgress("Время работать:: ", workTimeMin, sizeWork);
        printProgress("Время отдыхать:: ", breakTimeMin, sizeBreak);
    }

    private static void printProgress(String process, int time, int size) throws InterruptedException {
        int length;
        int rep;
        length = 60* time / size;
        rep = 60* time / length;
        int stretchb = size / (3 * time);

        for(int i = 1; i <= rep; i++) {

            double x = i;
            x = 1.0 / 3.0 *x;
            x *= 10;
            x = Math. round(x);
            x /= 10;
            double w = time * stretchb;
            double percent = (x/w) *1000;
            x /=stretchb;
            x *= 10;
            x = Math.round(x);
            x /= 10;
            percent = Math.round(percent);
            percent /= 10;

            System.out.println(process + percent + "%" + (" ").repeat(5 - (String.valueOf(percent).length())) + "[" + ("#").repeat(i) + ("-").repeat(rep - i) + "]    ( " + x + "min / " + time + "min )" + "\r");

            if (!isTest) {
                TimeUnit.SECONDS.sleep(length);
            }

            System.out.println();
        }

    }

}