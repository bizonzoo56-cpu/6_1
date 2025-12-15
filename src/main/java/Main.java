import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class WrongStudentName extends Exception { }
class WrongAge extends Exception { }
class WrongDateOfBirth extends Exception { }


class Main {
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        while(true) {
            try {
                int ex = menu();

                switch(ex) {
                    case 1: exercise1(); break;
                    case 2: exercise2(); break;
                    case 3: exercise3(); break;
                    default: 
                        if (ex != -1) return;
                        break;
                }
            } catch(IOException e) {
                 System.out.println("Wystąpił błąd wejścia/wyjścia (I/O error).");
            } catch(WrongStudentName e) {
                System.out.println("Błędne imię studenta! Imię nie może zawierać spacji.");
            } catch(WrongAge e) {
                System.out.println("Błędny wiek! Wiek musi być w zakresie 1-99.");
            } catch(WrongDateOfBirth e) {
                System.out.println("Błędna data urodzenia! Format musi być DD-MM-YYYY (np. 28-02-2023).");
            }
        }
    }

    public static int menu() {
        System.out.println("\nWciśnij:");
        System.out.println("1 - aby dodać studenta");
        System.out.println("2 - aby wypisać wszystkich studentów");
        System.out.println("3 - aby wyszukać studenta po imieniu");
        System.out.println("0 - aby wyjść z programu");

        String input = scan.nextLine();

        while (input.trim().isEmpty() && scan.hasNextLine()) {
             input = scan.nextLine();
        }

        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            System.out.println("Błędny wybór! Wprowadź cyfrę (0-3).");
            return -1;
        }
    }

    public static String ReadName() throws WrongStudentName {
        System.out.println("Podaj imię: ");
        String name = scan.nextLine();
        if(name.contains(" "))
            throw new WrongStudentName();

        return name;
    }

    private static boolean isValidDateOfBirth(String date) {
        Pattern pattern = Pattern.compile("^\\d{2}-\\d{2}-\\d{4}$");
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    public static void exercise1() throws IOException, WrongStudentName, WrongAge, WrongDateOfBirth {
        var name = ReadName();

        System.out.println("Podaj wiek (1-99): ");
        int age;

        if (scan.hasNextInt()) {
            age = scan.nextInt();
            scan.nextLine();
        } else {
            scan.nextLine();
            throw new WrongAge();
        }

        if (age < 1 || age > 99) {
            throw new WrongAge();
        }

        System.out.println("Podaj datę urodzenia DD-MM-YYYY: ");
        var date = scan.nextLine();

        if (!isValidDateOfBirth(date)) {
            throw new WrongDateOfBirth();
        }

        (new Service()).addStudent(new Student(name, age, date));
    }

    public static void exercise2() throws IOException {
        var students = (new Service()).getStudents();
        for(Student current : students) {
            System.out.println(current.ToString());
        }
    }

    public static void exercise3() throws IOException {
        System.out.println("Podaj imię: ");
        var name = scan.nextLine();
        var wanted = (new Service()).findStudentByName(name);
        if(wanted == null)
            System.out.println("Nie znaleziono...");
        else {
            System.out.println("Znaleziono: ");
            System.out.println(wanted.ToString());
        }
    }
}