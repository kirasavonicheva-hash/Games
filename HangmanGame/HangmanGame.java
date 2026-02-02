import java.util.Scanner;
import java.util.Random;

public class HangmanGame{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        String[] words = {
                "компьютер", "программирование", "алгоритм",
                "переменная", "функция", "объект", "класс",
                "библиотека", "компилятор", "интерфейс"
        };

        String word = words[random.nextInt(words.length)];
        char[] hiddenWord = new char[word.length()];
        boolean[] guessedLetters = new boolean[26];

        for (int i = 0; i < word.length(); i++) {
            hiddenWord[i] = '_';
        }

        int attempts = 6;
        int guessedLettersCount = 0;
        boolean gameOver = false;

        System.out.println("🎮 ИГРА: ВИСЕЛИЦА 🎮");
        System.out.println("====================");
        System.out.println("Угадай слово по буквам!");
        System.out.println("У тебя " + attempts + " попыток.");

        while (!gameOver) {

            drawHangman(attempts);

            System.out.print("\nСлово: ");
            for (char c : hiddenWord) {
                System.out.print(c + " ");
            }
            System.out.println();

            System.out.print("Использованные буквы: ");
            for (int i = 0; i < 26; i++) {
                if (guessedLetters[i]) {
                    System.out.print((char)('а' + i) + " ");
                }
            }
            System.out.println();

            System.out.print("\nВведи букву: ");
            String input = scanner.next().toLowerCase();

            if (input.length() != 1 || input.charAt(0) < 'а' || input.charAt(0) > 'я') {
                System.out.println("❌ Пожалуйста, введи одну русскую букву!");
                continue;
            }

            char letter = input.charAt(0);
            int letterIndex = letter - 'а';

            if (guessedLetters[letterIndex]) {
                System.out.println("⚠️ Ты уже угадывал эту букву!");
                continue;
            }

            guessedLetters[letterIndex] = true;

            boolean found = false;
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == letter) {
                    hiddenWord[i] = letter;
                    guessedLettersCount++;
                    found = true;
                }
            }

            if (found) {
                System.out.println("✅ Верно! Буква '" + letter + "' есть в слове.");

                if (guessedLettersCount == word.length()) {
                    gameOver = true;
                    System.out.println("\n🎉 ПОЗДРАВЛЯЮ! 🎉");
                    System.out.println("Ты угадал слово: " + word);
                    System.out.println("Осталось попыток: " + attempts);
                }
            } else {
                attempts--;
                System.out.println("❌ Нет такой буквы в слове!");
                System.out.println("Осталось попыток: " + attempts);

                if (attempts == 0) {
                    gameOver = true;
                    System.out.println("\n💀 ИГРА ОКОНЧЕНА!");
                    System.out.println("Ты проиграл!");
                    System.out.println("Загаданное слово было: " + word);
                }
            }
        }

        scanner.close();
    }


    public static void drawHangman(int attempts) {
        System.out.println("\nОсталось попыток: " + attempts);

        switch (attempts) {
            case 6:
                System.out.println("  ______");
                System.out.println("  |    |");
                System.out.println("  |");
                System.out.println("  |");
                System.out.println("  |");
                System.out.println("  |");
                System.out.println("__|__");
                break;
            case 5:
                System.out.println("  ______");
                System.out.println("  |    |");
                System.out.println("  |    O");
                System.out.println("  |");
                System.out.println("  |");
                System.out.println("  |");
                System.out.println("__|__");
                break;
            case 4:
                System.out.println("  ______");
                System.out.println("  |    |");
                System.out.println("  |    O");
                System.out.println("  |    |");
                System.out.println("  |");
                System.out.println("  |");
                System.out.println("__|__");
                break;
            case 3:
                System.out.println("  ______");
                System.out.println("  |    |");
                System.out.println("  |    O");
                System.out.println("  |   /|");
                System.out.println("  |");
                System.out.println("  |");
                System.out.println("__|__");
                break;
            case 2:
                System.out.println("  ______");
                System.out.println("  |    |");
                System.out.println("  |    O");
                System.out.println("  |   /|\\");
                System.out.println("  |");
                System.out.println("  |");
                System.out.println("__|__");
                break;
            case 1:
                System.out.println("  ______");
                System.out.println("  |    |");
                System.out.println("  |    O");
                System.out.println("  |   /|\\");
                System.out.println("  |   /");
                System.out.println("  |");
                System.out.println("__|__");
                break;
            case 0:
                System.out.println("  ______");
                System.out.println("  |    |");
                System.out.println("  |    O");
                System.out.println("  |   /|\\");
                System.out.println("  |   / \\");
                System.out.println("  |");
                System.out.println("__|__");
                break;
        }
    }
}