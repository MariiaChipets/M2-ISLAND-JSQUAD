
public abstract class Life {
    protected final String name;
    protected double weight;
    protected final int maxOnLocation;
    protected boolean isAlive=true;

    public Life(String name, SimulationConfiguration.SpeciesSpecs SPECIES_SPECS_MAP) {
        this.name = name;
        this.weight = SPECIES_SPECS_MAP.weight;
        this.maxOnLocation = SPECIES_SPECS_MAP.maxOnLocation;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public int getMaxOnLocation() {
        return maxOnLocation;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void die () {
        this.isAlive = false;
    }
}
