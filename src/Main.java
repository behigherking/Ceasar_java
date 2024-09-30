import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static String encrypt(String text, int key, boolean isCyrillic) {
        StringBuilder result = new StringBuilder();
        int alphabetSize = isCyrillic ? 32 : 26; // 32 для кириллицы, 26 для латиницы

        // Проходим по каждому символу в тексте
        for (char character : text.toCharArray()) {
            if (Character.isLetter(character)) {
                char base;
                // Определяем базу в зависимости от регистра и языка
                if (isCyrillic) {
                    if (Character.isLowerCase(character)) {
                        base = 'а'; // Кириллица, строчная
                    } else {
                        base = 'А'; // Кириллица, заглавная
                    }
                } else {
                    if (Character.isLowerCase(character)) {
                        base = 'a'; // Латиница, строчная
                    } else {
                        base = 'A'; // Латиница, заглавная
                    }
                }

                // Сдвигаем символ и добавляем в результат q1
                character = (char) ((character - base + key) % alphabetSize + base);
            }
            result.append(character);
        }
        return result.toString();
    }

    public static String decrypt(String text, int key, boolean isCyrillic) {
        int alphabetSize = isCyrillic ? 32 : 26;
        // Для расшифровки используем отрицательный сдвиг
        return encrypt(text, alphabetSize - (key % alphabetSize), isCyrillic);
    }


    public static void main(String[] args) {
        while (true) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Выберите язык: \n1. Латиница\n2. Кириллица");
            int languageChoice = scanner.nextInt();
            boolean isCyrillic = (languageChoice == 2);
            System.out.println("Выберите действие: \n1. Расшифровать\n2. Зашифровать");
            int cryptChoice = scanner.nextInt();
            boolean isDecrypt = (cryptChoice == 1);

            System.out.print("Введите число для шифрования: ");
            int key = scanner.nextInt();


            System.out.println("Введите путь к файлу для чтения");
            scanner.nextLine();
            String filePathToRead = scanner.nextLine();
            System.out.println("Введите путь к файлу для записи:");
            String filePathToWrite = scanner.nextLine();
            StringBuilder textInFile = new StringBuilder();

            try (FileReader reader = new FileReader(filePathToRead)) {
                int character;
                while ((character = reader.read()) != -1) {
                    textInFile.append((char) character);
                }
                System.out.println("Файл успешно прочитан.");
            } catch (IOException exception) {
                exception.getMessage();
            }
            try (FileWriter writer = new FileWriter(filePathToWrite)) {
                writer.write(isDecrypt ? decrypt(textInFile.toString(), key, isCyrillic) : encrypt(textInFile.toString(), key, isCyrillic));
                System.out.println("Файл успешно записан.");
            } catch (IOException exception) {
                exception.getMessage();
            }
        }
    }
}