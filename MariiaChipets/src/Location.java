import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Location {
    private final int X;
    private final int Y;

    // CopyOnWriteArrayList забезпечує потокобезпечність під час ітерації та модифікації в пулі потоків
    private final List<Animal> ANIMALS = new CopyOnWriteArrayList<>();
    private final List<Plant> PLANTS = new CopyOnWriteArrayList<>();

    public Location(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    public void addAnimal (Animal a) {ANIMALS.add(a);}
    public void removeAnimal (Animal a) {ANIMALS.remove(a);}
    public void addPlant(Plant p) {PLANTS.add(p);}

    public List<Animal> getAnimals() {return ANIMALS;}
    public List<Plant> getPlants() {return PLANTS;}
    public int getX() {return X;}
    public int getY() {return Y;}
}
