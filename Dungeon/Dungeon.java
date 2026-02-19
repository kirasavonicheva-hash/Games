import java.util.Random;
import java.util.Scanner;
public class Dungeon {
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();
    private static Player player;
    private static boolean gameRunning = true;
    public static void main(String[] args) {
        System.out.println("====================================");
        System.out.println("   ДОБРО ПОЖАЛОВАТЬ В ПОДЗЕМЕЛЬЕ!");
        System.out.println("====================================");
        System.out.print("Введите имя героя: ");
        String name = scanner.nextLine();
        player = new Player(name);
        while (gameRunning && player.isAlive()) {
            showMenu();
            int choice = getIntInput();
            handleChoice(choice);
        }
        if (!player.isAlive()) {
            System.out.println("\n ИГРА ОКОНЧЕНА! Вы погибли в подземелье...");
        }
    }
    private static void showMenu() {
        System.out.println("\n═══════════════════════════════");
        System.out.println("Здоровье:  " + player.health + "/" + player.maxHealth);
        System.out.println("Уровень:  " + player.level + " (Опыт: " + player.exp + "/" + (player.level * 100) + ")");
        System.out.println("Золото:  " + player.gold);
        System.out.println("═══════════════════════════════");
        System.out.println("1.   Искать монстров");
        System.out.println("2.  Лечиться (15 золота)");
        System.out.println("3.  Магазин");
        System.out.println("4.  Инвентарь");
        System.out.println("5.  Отдых (восстановление)");
        System.out.println("6.  Выйти из игры");
        System.out.print("Выберите действие: ");
    }
    private static void handleChoice(int choice) {
        switch (choice) {
            case 1 -> fightMonster();
            case 2 -> heal();
            case 3 -> shop();
            case 4 -> showInventory();
            case 5 -> rest();
            case 6 -> {
                System.out.println("До встречи, герой!");
                gameRunning = false;
            }
            default -> System.out.println("Неверный выбор!");
        }
    }
    private static void fightMonster() {
        Monster monster = generateMonster();
        System.out.println("\n⚔️ Вы встретили " + monster.name + " (Уровень " + monster.level + ")!");
        System.out.println("Монстр:  " + monster.health + " |  " + monster.damage + " урона");
        while (monster.isAlive() && player.isAlive()) {
            System.out.println("\n1.  Атаковать");
            System.out.println("2.  Защищаться (половина урона)");
            System.out.println("3.  Сбежать");
            System.out.print("Ваш ход: ");
            int action = getIntInput();
            if (action == 1) {
                int playerDamage = player.attack();
                monster.takeDamage(playerDamage);
                System.out.println("Вы нанесли " + playerDamage + " урона!");
                if (!monster.isAlive()) {
                    int expGain = monster.level * 20;
                    int goldGain = monster.level * 10 + random.nextInt(20);
                    player.addExp(expGain);
                    player.gold += goldGain;
                    System.out.println("🎉 Победа! +" + expGain + " опыта, +" + goldGain + " золота!");
                    if (random.nextInt(100) < 30) {
                        Item loot = generateRandomItem();
                        player.addItem(loot);
                        System.out.println(" Вы нашли: " + loot.name);
                    }
                    break;
                }
                int monsterDamage = monster.attack();
                player.takeDamage(monsterDamage);
                System.out.println("Монстр нанес " + monsterDamage + " урона!");
            } else if (action == 2) {
                int monsterDamage = monster.attack() / 2;
                player.takeDamage(monsterDamage);
                System.out.println("Вы заблокировали урон, получив " + monsterDamage + " урона!");
            } else if (action == 3) {
                if (random.nextInt(100) < 50) {
                    System.out.println(" Вы успешно сбежали!");
                    break;
                } else {
                    System.out.println(" Не удалось сбежать!");
                    int monsterDamage = monster.attack();
                    player.takeDamage(monsterDamage);
                    System.out.println("Монстр нанес " + monsterDamage + " урона!");
                }
            }
        }
    }
    private static Monster generateMonster() {
        String[] names = {"Гоблин", "Орк", "Скелет", "Зомби", "Вампир", "Дракон", "Тролль"};
        int level = Math.max(1, player.level - 1 + random.nextInt(3));
        String name = names[random.nextInt(names.length)];
        if (level > 5 && name.equals("Дракон")) {
            return new Dragon(level);
        }
        int health = 30 + level * 15 + random.nextInt(20);
        int damage = 5 + level * 3 + random.nextInt(5);
        return new Monster(name, level, health, damage);
    }
    private static void heal() {
        if (player.gold >= 15) {
            player.gold -= 15;
            player.health = Math.min(player.maxHealth, player.health + 30);
            System.out.println(" Вы выпили зелье! Здоровье: " + player.health + "/" + player.maxHealth);
        } else {
            System.out.println(" Недостаточно золота!");
        }
    }
    private static void shop() {
        System.out.println("\n Добро пожаловать в магазин!");
        System.out.println("1.  Меч (+5 урона) - 50 золота");
        System.out.println("2.  Щит (+10 защиты) - 40 золота");
        System.out.println("3.  Зелье здоровья - 20 золота");
        System.out.println("4.  Выйти");
        System.out.print("Выберите товар: ");
        int choice = getIntInput();
        switch (choice) {
            case 1 -> buyItem("Меч", 50, ItemType.WEAPON, 5);
            case 2 -> buyItem("Щит", 40, ItemType.ARMOR, 10);
            case 3 -> buyItem("Зелье", 20, ItemType.POTION, 30);
            case 4 -> System.out.println("До свидания!");
        }
    }
    private static void buyItem(String name, int price, ItemType type, int value) {
        if (player.gold >= price) {
            player.gold -= price;
            if (type == ItemType.POTION) {
                player.health = Math.min(player.maxHealth, player.health + value);
                System.out.println("Вы использовали зелье! Здоровье: " + player.health);
            } else {
                player.addItem(new Item(name, type, value));
                System.out.println(" Куплено: " + name);
            }
        } else {
            System.out.println(" Недостаточно золота!");
        }
    }
    private static void showInventory() {
        System.out.println("\n ИНВЕНТАРЬ:");
        if (player.inventory.isEmpty()) {
            System.out.println("Пусто...");
        } else {
            for (int i = 0; i < player.inventory.size(); i++) {
                Item item = player.inventory.get(i);
                System.out.println((i + 1) + ". " + item);
            }
            System.out.println("\n0. Назад");
            System.out.print("Выберите предмет для использования: ");

            int choice = getIntInput();
            if (choice > 0 && choice <= player.inventory.size()) {
                useItem(player.inventory.get(choice - 1));
            }
        }
    }
    private static void useItem(Item item) {
        switch (item.type) {
            case WEAPON:
                player.damageBonus += item.value;
                System.out.println(" Вы экипировали " + item.name + "! Урон увеличен на " + item.value);
                break;
            case ARMOR:
                player.defenseBonus += item.value;
                System.out.println(" Вы экипировали " + item.name + "! Защита увеличена на " + item.value);
                break;
            case POTION:
                player.health = Math.min(player.maxHealth, player.health + item.value);
                System.out.println(" Вы использовали зелье! Восстановлено " + item.value + " здоровья");
                break;
        }
        player.inventory.remove(item);
    }
    private static void rest() {
        System.out.println(" Вы отдыхаете у костра...");
        player.health = Math.min(player.maxHealth, player.health + 10);
        System.out.println("Восстановлено 10 здоровья. Текущее: " + player.health + "/" + player.maxHealth);
    }
    private static Item generateRandomItem() {
        String[] names = {"Ржавый меч", "Старый щит", "Магическое кольцо", "Зелье опыта"};
        ItemType[] types = {ItemType.WEAPON, ItemType.ARMOR, ItemType.WEAPON, ItemType.POTION};
        int[] values = {8, 12, 15, 25};
        int index = random.nextInt(names.length);
        return new Item(names[index], types[index], values[index]);
    }
    private static int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    static class Player {
        String name;
        int health;
        int maxHealth;
        int level;
        int exp;
        int gold;
        int damageBonus;
        int defenseBonus;
        java.util.ArrayList<Item> inventory;
        Player(String name) {
            this.name = name;
            this.maxHealth = 100;
            this.health = 100;
            this.level = 1;
            this.exp = 0;
            this.gold = 50;
            this.damageBonus = 0;
            this.defenseBonus = 0;
            this.inventory = new java.util.ArrayList<>();
        }
        int attack() {
            return 10 + level * 2 + damageBonus + random.nextInt(5);
        }
        void takeDamage(int damage) {
            int actualDamage = Math.max(1, damage - defenseBonus / 2);
            health -= actualDamage;
            if (health < 0) health = 0;
        }
        void addExp(int amount) {
            exp += amount;
            while (exp >= level * 100) {
                level++;
                maxHealth += 20;
                health = maxHealth;
                System.out.println(" УРОВЕНЬ ПОВЫШЕН! Теперь вы " + level + " уровня!");
            }
        }
        void addItem(Item item) {
            inventory.add(item);
        }
        boolean isAlive() {
            return health > 0;
        }
    }
    static class Monster {
        String name;
        int level;
        int health;
        int maxHealth;
        int damage;
        Monster(String name, int level, int health, int damage) {
            this.name = name;
            this.level = level;
            this.health = health;
            this.maxHealth = health;
            this.damage = damage;
        }
        int attack() {
            return damage + random.nextInt(5);
        }
        void takeDamage(int damage) {
            health -= damage;
        }
        boolean isAlive() {
            return health > 0;
        }
    }
    static class Dragon extends Monster {
        Dragon(int level) {
            super(" Дракон", level, 100 + level * 30, 15 + level * 5);
        }
        @Override
        int attack() {
            System.out.println(" Дракон дышит огнем!");
            return super.attack() + 10;
        }
    }
    enum ItemType { WEAPON, ARMOR, POTION }
    static class Item {
        String name;
        ItemType type;
        int value;
        Item(String name, ItemType type, int value) {
            this.name = name;
            this.type = type;
            this.value = value;
        }
        @Override
        public String toString() {
            String typeStr = switch (type) {
                case WEAPON -> " Оружие";
                case ARMOR -> " Броня";
                case POTION -> " Зелье";
            };
            return name + " (" + typeStr + ", +" + value + ")";
        }
    }

}
