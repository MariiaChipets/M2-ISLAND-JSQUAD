import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal extends Life {
    protected final int maxSpeed;
    protected final double neededFood;
    protected double currentSatiety;
    private boolean hasReproducedThisTurn = false; // Прапорець, щоб істота не розмножувалася безкінечно за один такт

    public Animal(String name) {
        super(name, SimulationConfiguration.getSpecs(name));
        SimulationConfiguration.SpeciesSpecs SPECIES_SPECS_MAP = SimulationConfiguration.getSpecs(name);
        this.maxSpeed = SPECIES_SPECS_MAP.maxSpeed;
        this.neededFood = SPECIES_SPECS_MAP.neededFood;
        this.currentSatiety = SPECIES_SPECS_MAP.neededFood / 2.0; // початковий стан живності: напівсита
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void resetFlags() {
        this.hasReproducedThisTurn = false;
    }

    //УНІВЕРСАЛЬНИЙ МЕТОД ХАРЧУВАННЯ
    public void eat(Location location) {
        if (!isAlive || currentSatiety >= neededFood || neededFood == 0) return;

        ThreadLocalRandom random = ThreadLocalRandom.current();

        // Тварина їсть іншу тварину (якщо це хижак чи всеїдна тварина)
        List<Animal> animalsAreHere = location.getAnimals();
        for (Animal target : animalsAreHere) {
            if (target != this && target.isAlive()) {
                int animalChance = SimulationConfiguration.getEatingProbability(this.name, target.getName());
                if (animalChance > 0 && random.nextInt(100) < animalChance) {
                    target.die();
                    location.removeAnimal(target);
                    this.currentSatiety = Math.min(neededFood, currentSatiety + target.getWeight());
                    System.out.println("[" + this.name + "] з'їв [" + target.getName() + "] на клітинці (" + location.getX() + "," + location.getY() + ")");
                    return;
                }
            }
        }

        // Тварина їсть рослину (якщо тварина травоїдна/всеїдна)
        int plantChance = SimulationConfiguration.getEatingProbability(this.name, "Рослина");
        if (plantChance > 0 && !location.getPlants().isEmpty()) {
            Plant plant = location.getPlants().get(0);
            plant.die();
            location.getPlants().remove(plant);
            this.currentSatiety = Math.min(neededFood, currentSatiety + plant.getWeight());
            System.out.println("[" + this.name + "] з'їв Рослину на клітинці (" + location.getX() + "," + location.getY() + ")");
        }
    }

    //УНІВЕРСАЛЬНИЙ МЕТОД ПЕРЕСУВАННЯ
    public void move(Location current, Location target) {
        if (!isAlive || maxSpeed == 0 || current == target) return;
        current.removeAnimal(this);
        target.addAnimal(this);
    }

    //УНІВЕРСАЛЬНИЙ МЕТОД РОЗМНОЖЕННЯ
    public void reproduce(Location location) {
        if (!isAlive || this.hasReproducedThisTurn) return;

        long sameSpeciesCount = location.getAnimals().stream()
                .filter(a -> a.getName().equals(this.name) && a.isAlive() && !a.hasReproducedThisTurn)
                .count();

        // Якщо є хоча б ще одна вільна пара і не перевищено ліміт на клітинку
        if (sameSpeciesCount >= 2 && location.getAnimals().size()<this.maxOnLocation){
            Animal baby = createNewInstance();
            if (baby != null) {
                location.addAnimal(baby);
                this.hasReproducedThisTurn = true;

                // Знаходимо партнера і теж блокуємо йому розмноження на цей такт
                location.getAnimals().stream()
                        .filter(a -> a.getName().equals(this.name) && a != this && !a.hasReproducedThisTurn)
                        .findFirst()
                        .ifPresent(partner -> partner.hasReproducedThisTurn = true);

                System.out.println("Народився новий [" + this.name + "] на клітинці (" + location.getX() + "," + location.getY() + ")");
            }
        }
    }

    // Фабричний метод, який кожен нащадок реалізує сам для створення копії
    protected abstract Animal createNewInstance();

    // можливе голодування
    public void starve(){
        if(neededFood > 0){
            this.currentSatiety -= (neededFood* 0.15); // втрачає 15% ситості за хід;
            if(this.currentSatiety <= 0){
                die();
                System.out.println("[" + this.name + "] помер від голоду.");
            }
        }
    }
}

