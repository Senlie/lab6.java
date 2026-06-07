/**
 * Лабораторна робота №6
 * Тема: Наслідування та поліморфізм
 *
 * Номер залікової книжки: 5103
 *
 * Варіант:
 *   C13 = 5103 % 13 = 0 → Ієрархія квітів.
 *   - Створити кілька об'єктів квітів
 *   - Зібрати букет з визначенням його вартості
 *   - Відсортувати квіти за рівнем свіжості
 *   - Знайти квітку в букеті за заданим діапазоном довжин
 *
 * Ієрархія класів:
 *   Flower (абстрактний)
 *   ├── Rose
 *   ├── Tulip
 *   └── Lily
 *
 *   Bouquet — містить масив Flower + аксесуари
 */

import java.util.Arrays;
import java.util.Comparator;

// Абстрактний базовий клас: квітка

abstract class Flower {

    /** Назва квітки */
    private String name;

    /** Ціна у гривнях */
    private double price;

    /** Довжина стебла у сантиметрах */
    private double stemLength;

    /**
     * Рівень свіжості від 1 (зів'яла) до 10 (свіжа)
     * Спільне поле для всіх квіток — основа поліморфізму
     */
    private int freshnessLevel;

    /**
     * @param name          назва
     * @param price         ціна
     * @param stemLength    довжина стебла (см)
     * @param freshnessLevel рівень свіжості (1–10)
     */
    public Flower(String name, double price,
                  double stemLength, int freshnessLevel) {
        this.name = name;
        this.price = price;
        this.stemLength = stemLength;
        this.freshnessLevel = freshnessLevel;
    }

    public String getName()        { return name; }
    public double getPrice()       { return price; }
    public double getStemLength()  { return stemLength; }
    public int getFreshnessLevel() { return freshnessLevel; }

    /**
     * Абстрактний метод — кожна квітка описує свій аромат

     */
    public abstract String getAroma();

    @Override
    public String toString() {
        return String.format(
            "%s [ціна=%.2f грн, стебло=%.1f см, свіжість=%d/10, аромат=%s]",
            name, price, stemLength, freshnessLevel, getAroma()
        );
    }
}

//
// Клас-нащадок: Троянда
//
class Rose extends Flower {

    /** Колір троянди */
    private String color;


    public Rose(double price, double stemLength,
                int freshnessLevel, String color) {
        super("Троянда", price, stemLength, freshnessLevel);
        this.color = color;
    }

    public String getColor() { return color; }

    @Override
    public String getAroma() {
        return "насичений квітковий";
    }

    @Override
    public String toString() {
        return super.toString() + ", колір=" + color;
    }
}
// Клас-нащадок: Тюльпан
class Tulip extends Flower {


    private String petalShape;

    public Tulip(double price, double stemLength,
                 int freshnessLevel, String petalShape) {
        super("Тюльпан", price, stemLength, freshnessLevel);
        this.petalShape = petalShape;
    }

    @Override
    public String getAroma() {
        return "ніжний, легкий";
    }

    @Override
    public String toString() {
        return super.toString() + ", пелюстки=" + petalShape;
    }
}

class Lily extends Flower {

    /** Кількість бутонів */
    private int budCount;

    public Lily(double price, double stemLength,
                int freshnessLevel, int budCount) {
        super("Лілія", price, stemLength, freshnessLevel);
        this.budCount = budCount;
    }

    @Override
    public String getAroma() {
        return "солодкий, інтенсивний";
    }

    @Override
    public String toString() {
        return super.toString() + ", бутонів=" + budCount;
    }
}
// Клас: аксесуар для букета

class Accessory {


    private String name;

    private double price;

    public Accessory(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName()  { return name; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return String.format("%s (%.2f грн)", name, price);
    }
}

// Клас: букет — масив квіток + масив аксесуарів

class Bouquet {

    /** Масив квіток букета */
    private Flower[] flowers;

    /** Масив аксесуарів */
    private Accessory[] accessories;

    public Bouquet(Flower[] flowers, Accessory[] accessories) {
        this.flowers = flowers;
        this.accessories = accessories;
    }

    /**
     * Підраховує загальну вартість букета (квіти + аксесуари)
     */
    public double getTotalPrice() {
        double total = 0;
        for (Flower f : flowers) {
            total += f.getPrice();
        }
        for (Accessory a : accessories) {
            total += a.getPrice();
        }
        return total;
    }

    /**
     * Сортує квіти в букеті за рівнем свіжості за спаданням
     * (найсвіжіші — перші).
     */
    public void sortByFreshness() {
        Arrays.sort(flowers,
            Comparator.comparingInt(Flower::getFreshnessLevel).reversed()
        );
    }

    public Flower[] findByLengthRange(double minLength, double maxLength) {
        int count = 0;
        for (Flower f : flowers) {
            if (f.getStemLength() >= minLength && f.getStemLength() <= maxLength) {
                count++;
            }
        }
        Flower[] result = new Flower[count];
        int idx = 0;
        for (Flower f : flowers) {
            if (f.getStemLength() >= minLength && f.getStemLength() <= maxLength) {
                result[idx++] = f;
            }
        }
        return result;
    }

    public Flower[] getFlowers()       { return flowers; }
    public Accessory[] getAccessories(){ return accessories; }
}

// Головний клас із виконавчим методом
public class Lab6 {

    public static void main(String[] args) {

        try {
            // --- Створення квіток ---
            Flower[] flowers = {
                new Rose(85.0,  60.0, 9, "Червона"),
                new Rose(75.0,  45.0, 6, "Біла"),
                new Tulip(40.0, 35.0, 8, "Округла"),
                new Tulip(45.0, 50.0, 5, "Гостра"),
                new Lily(110.0, 70.0, 7, 4),
                new Lily(95.0,  55.0, 10, 3),
                new Rose(90.0,  65.0, 4, "Рожева")
            };

            // --- Аксесуари ---
            Accessory[] accessories = {
                new Accessory("Стрічка атласна", 25.0),
                new Accessory("Пакувальний папір", 40.0),
                new Accessory("Листівка",         15.0)
            };

            // --- Створення букета ---
            Bouquet bouquet = new Bouquet(flowers, accessories);

            // --- Вивід до сортування ---
            System.out.println("=== Лабораторна робота №6 ===");
            System.out.println("\nБукет до сортування:");
            printFlowers(bouquet.getFlowers());

            System.out.println("\nАксесуари:");
            for (Accessory a : bouquet.getAccessories()) {
                System.out.println("  " + a);
            }
            System.out.printf("%nЗагальна вартість букета: %.2f грн%n",
                bouquet.getTotalPrice());

            // --- Сортування за свіжістю ---
            bouquet.sortByFreshness();
            System.out.println("\nБукет після сортування за свіжістю (↓):");
            printFlowers(bouquet.getFlowers());

            // --- Пошук за діапазоном довжини стебла ---
            double minLen = 50.0;
            double maxLen = 65.0;
            Flower[] found = bouquet.findByLengthRange(minLen, maxLen);

            System.out.printf(
                "%nКвітки з довжиною стебла від %.1f до %.1f см:%n",
                minLen, maxLen
            );
            if (found.length == 0) {
                System.out.println("  Квітки не знайдено.");
            } else {
                printFlowers(found);
            }

        } catch (NullPointerException e) {
            System.err.println("Помилка: null-значення: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Помилка вхідних даних: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Непередбачена помилка: " + e.getMessage());
        }
    }

    static void printFlowers(Flower[] flowers) {
        for (int i = 0; i < flowers.length; i++) {
            System.out.println("  [" + i + "] " + flowers[i]);
        }
    }
}
