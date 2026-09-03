import java.util.concurrent.*;

public class IslandSimulation {
    public final IslandMap islandMap = new IslandMap();

    // Один Scheduled пул для всіх циклічних задач
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
    private int stepCount = 0;

    public IslandSimulation() {
        fillIslandInitialData(); // треба заповнити початкові дані острова
    }

    // запуск розкладу завдань
    public void start() {
        System.out.println("Життя на острові почалося!");

        // Ростуть рослини (кожні 2 секунди)
        scheduler.scheduleAtFixedRate(this::growPlantsTask, 0, 2, TimeUnit.SECONDS);
        // Життєвий цикл тварин (кожні 2 сек)
        scheduler.scheduleAtFixedRate(this::animalsLifeCycleTask, 0, 2, TimeUnit.SECONDS);
        // Виведення статистики (кожні 2 сек).змістила на 1 сек для точності рахунку
        scheduler.scheduleAtFixedRate(this::printStatisticsTask, 1, 2, TimeUnit.SECONDS);
    }

    private void growPlantsTask() {
        for (int x = 0; x < islandMap.getWIDTH(); x++) {
            for (int y = 0; y < islandMap.getHEIGHT(); y++) {
                Location loc = islandMap.getLocations(x, y);
                if (loc.getPlants().size() < 200) {
                    loc.addPlant(new Plant());
                }
            }
        }
    }

    private void animalsLifeCycleTask() {
        for (int x = 0; x < islandMap.getWIDTH(); x++) {
            for (int y = 0; y < islandMap.getHEIGHT(); y++) {
                Location loc = islandMap.getLocations(x, y);

                // треба скинути прапорці розмноження перед початком нового кола
                loc.getAnimals().forEach(Animal::resetFlags);

                for (Animal animal : loc.getAnimals()) {
                    if (!animal.isAlive()) continue;

                    animal.eat(loc);
                    animal.reproduce(loc);
                    animal.starve();

                    // пересування тварини
                    if (animal.isAlive() && animal.getMaxSpeed() > 0) {
                        Location target = getRandomAdjacentLocation(loc, animal.getMaxSpeed());
                        animal.move(loc, target);
                    }
                }
            }
        }
    }

    private Location getRandomAdjacentLocation(Location current, int speed) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int nextX = current.getX() + random.nextInt(-speed, speed + 1);
        int nextY = current.getY() + random.nextInt(-speed, speed + 1);
        Location target = islandMap.getLocations(nextX, nextY);
        return (target != null) ? target : current;
    }

    private void printStatisticsTask() {
        stepCount++;
        int totalAnimals = 0;
        int totalPlants = 0;

        for (int x = 0; x <= islandMap.getWIDTH(); x++) {
            for (int y = 0; y <= islandMap.getHEIGHT(); y++) {
                Location loc = islandMap.getLocations(x, y);
                totalAnimals += loc.getAnimals().size();
                totalPlants += loc.getPlants().size();
            }
        }

        // Вивід статистики в консоль на кожному колі
        System.out.println("КОЛО ЖИТТЯ № " + stepCount);
        System.out.println("Кількість рослин на острові: " + totalPlants);
        System.out.println("Кількість живих тварин:      " + totalAnimals);
        System.out.println("-------------------------\n");

        if (totalAnimals == 0 && totalPlants == 0) {
            System.out.println("GAME OVER. Острів спорожнів.");
            scheduler.shutdown();
        }
    }

    // Первинне рандомне заселення острова тваринами та рослинами
    private void fillIslandInitialData() {
        for (int x=0; x<islandMap.getWIDTH(); x++) {
            for (int y=0; y<islandMap.getHEIGHT(); y++) {
                Location loc = islandMap.getLocations(x, y);
                // Додамо на кожну клітинку трохи випадкових істот для старту симуляції
                if (Math.random() < 0.3) loc.addAnimal(new Wolf());
                if (Math.random() < 0.6) loc.addAnimal(new Rabbit());
                if (Math.random() < 0.5) loc.addAnimal(new Duck());
                if (Math.random() < 0.7) loc.addAnimal(new Caterpillar());
                loc.addPlant(new Plant());
            }
        }
    }
}
