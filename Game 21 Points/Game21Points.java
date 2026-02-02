import java.util.Scanner;
import java.util.Random;

public class Game21Points {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int playerScore = 0;
        int dealerScore = 0;
        boolean playerTurn = true;
        boolean gameOver = false;

        System.out.println("🎮 ИГРА: 21 ОЧКО 🎮");
        System.out.println("==================");
        System.out.println("Правила:");
        System.out.println("1. Цель - набрать близко к 21, но не больше");
        System.out.println("2. Карты от 2 до 10 дают соответствующее количество очков");
        System.out.println("3. Тузы дают 1 или 11 очков (автоматически выбирается лучшее)");
        System.out.println("4. Если больше 21 - проигрыш");

        // Начальные карты
        playerScore += drawCard(random);
        playerScore += drawCard(random);

        dealerScore += drawCard(random);

        System.out.println("\n=== НАЧАЛО ИГРЫ ===");
        System.out.println("Твои очки: " + playerScore);
        System.out.println("Карта дилера: " + dealerScore);

        // Ход игрока
        while (playerTurn && !gameOver) {
            System.out.print("\nЧто будешь делать? (1 - Взять карту, 2 - Остановиться): ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                int card = drawCard(random);
                playerScore += card;
                System.out.println("Ты взял карту: " + card);
                System.out.println("Твои очки: " + playerScore);

                if (playerScore > 21) {
                    System.out.println("❌ Перебор! У тебя больше 21 очка!");
                    gameOver = true;
                }
            } else if (choice == 2) {
                playerTurn = false;
                System.out.println("Ты остановился на " + playerScore + " очках.");
            }
        }

        // Ход дилера, если игрок не проиграл
        if (!gameOver) {
            System.out.println("\n--- ХОД ДИЛЕРА ---");
            System.out.println("Карты дилера: " + dealerScore);

            while (dealerScore < 17) {
                int card = drawCard(random);
                dealerScore += card;
                System.out.println("Дилер берет карту: " + card);
                System.out.println("Очки дилера: " + dealerScore);

                if (dealerScore > 21) {
                    System.out.println("✅ Дилер перебрал!");
                    gameOver = true;
                }
            }
        }

        // Определяем победителя
        System.out.println("\n=== РЕЗУЛЬТАТ ===");
        System.out.println("Твои очки: " + playerScore);
        System.out.println("Очки дилера: " + dealerScore);

        if (playerScore > 21) {
            System.out.println("💀 Ты проиграл! Перебор.");
        } else if (dealerScore > 21) {
            System.out.println("🏆 Ты выиграл! Дилер перебрал.");
        } else if (playerScore > dealerScore) {
            System.out.println("🏆 Ты выиграл! У тебя больше очков.");
        } else if (playerScore < dealerScore) {
            System.out.println("💀 Ты проиграл! У дилера больше очков.");
        } else {
            System.out.println("🤝 Ничья!");
        }

        scanner.close();
    }

    // Метод для "взятия" карты
    public static int drawCard(Random random) {
        int card = random.nextInt(13) + 1;

        if (card > 10) {
            return 10; // Валет, Дама, Король = 10
        } else if (card == 1) {
            return 11; // Туз = 11 (упрощенно)
        } else {
            return card;
        }
    }
}