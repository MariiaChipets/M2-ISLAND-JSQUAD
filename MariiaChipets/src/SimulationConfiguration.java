import java.util.HashMap;
import java.util.Map;

public class SimulationConfiguration {

    // Структура для зберігання індивідуальних параметрів виду
    public static class SpeciesSpecs {
        public final double weight;
        public final int maxOnLocation;
        public final int maxSpeed;
        public final double neededFood;

        public SpeciesSpecs(double weight, int maxOnLocation, int maxSpeed, double neededFood) {
            this.weight = weight;
            this.maxOnLocation = maxOnLocation;
            this.maxSpeed = maxSpeed;
            this.neededFood = neededFood;
        }
    }

    private static final Map<String, SpeciesSpecs> SPECIES_SPECS_MAP = new HashMap<>();
    private static final Map<String, Map<String, Integer>> EATING_MATRIX = new HashMap<>();

    static {
        // Заповнення характеристик
        SPECIES_SPECS_MAP.put("Вовк", new SpeciesSpecs(50, 30, 3, 8));
        SPECIES_SPECS_MAP.put("Удав", new SpeciesSpecs(15, 30, 1, 3));
        SPECIES_SPECS_MAP.put("Лисиця", new SpeciesSpecs(8, 30, 2, 2));
        SPECIES_SPECS_MAP.put("Ведмідь", new SpeciesSpecs(500, 5, 2, 80));
        SPECIES_SPECS_MAP.put("Орел", new SpeciesSpecs(6, 20, 3, 1));
        SPECIES_SPECS_MAP.put("Кінь", new SpeciesSpecs(400, 20, 4, 60));
        SPECIES_SPECS_MAP.put("Олень", new SpeciesSpecs(300, 20, 4, 50));
        SPECIES_SPECS_MAP.put("Кролик", new SpeciesSpecs(2, 150, 2, 0.45));
        SPECIES_SPECS_MAP.put("Миша", new SpeciesSpecs(0.05, 500, 1, 0.01));
        SPECIES_SPECS_MAP.put("Коза", new SpeciesSpecs(60, 140, 3, 10));
        SPECIES_SPECS_MAP.put("Вівця", new SpeciesSpecs(70, 140, 3, 15));
        SPECIES_SPECS_MAP.put("Кабан", new SpeciesSpecs(400, 50, 2, 50));
        SPECIES_SPECS_MAP.put("Буйвол", new SpeciesSpecs(700, 10, 3, 100));
        SPECIES_SPECS_MAP.put("Качка", new SpeciesSpecs(1, 200, 4, 0.15));
        SPECIES_SPECS_MAP.put("Гусінь", new SpeciesSpecs(0.01, 1000, 0, 0));
        SPECIES_SPECS_MAP.put("Рослина", new SpeciesSpecs(1, 200, 0, 0));

        // Заповнення матриці поїдання хижаків
        addDiet("Вовк", "Кінь", 10, "Олень", 15, "Кролик", 60, "Миша", 80, "Коза", 60, "Вівця", 70, "Кабан", 15, "Качка", 40);
        addDiet("Удав", "Лисиця", 15, "Кролик", 20, "Миша", 40, "Качка", 10);
        addDiet("Лисиця", "Кролик", 70, "Миша", 90, "Качка", 60, "Гусінь", 40);
        addDiet("Ведмідь", "Удав", 80, "Кінь", 40, "Олень", 80, "Кролик", 80, "Миша", 90, "Коза", 70, "Вівця", 70, "Кабан", 50, "Буйвол", 20, "Качка", 10);
        addDiet("Орел", "Лисиця", 10, "Кролик", 90, "Миша", 90, "Качка", 80);

        // Заповнення матриці поїдання всеїдних (тих, що їдять рослини, тварин та гусінь)
        addDiet("Кінь", "Рослина", 100);
        addDiet("Олень", "Рослина", 100);
        addDiet("Кролик", "Рослина", 100);
        addDiet("Миша", "Гусінь", 90, "Рослина", 100);
        addDiet("Коза", "Рослина", 100);
        addDiet("Вівця", "Рослина", 100);
        addDiet("Кабан", "Миша", 50, "Гусінь", 90, "Рослина", 100);
        addDiet("Буйвол", "Рослина", 100);
        addDiet("Качка", "Гусінь", 90, "Рослина", 100);
        addDiet("Гусінь", "Рослина", 100);
    }

    // Допоміжний метод для швидкого заповнення мапи раціону (парні аргументи: "Жертва", "Відсоток")
    private static void addDiet(String predator, Object... preyAndChances) {
        Map<String, Integer> diet = new HashMap<>();
        for (int i = 0; i < preyAndChances.length; i += 2) {
            diet.put((String) preyAndChances[i], (Integer) preyAndChances[i + 1]);
        }
        EATING_MATRIX.put(predator, diet);
    }

    public static SpeciesSpecs getSpecs(String name) {
        return SPECIES_SPECS_MAP.get(name);
    }

    public static int getEatingProbability(String predator, String prey) {
        if (EATING_MATRIX.containsKey(predator) && EATING_MATRIX.containsKey(prey)) {
            return EATING_MATRIX.get(predator).get(prey);
        }
        return 0; // 0% (якщо істоти немає в раціоні)
    }
}


