public abstract class ElectricalAppliance {
    private String name;
    private int power;
    private boolean isPluggedIn;

    public ElectricalAppliance(String name, int power) {
        this.name = name;
        this.power = power;
        this.isPluggedIn = false;
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public boolean isPluggedIn() {
        return isPluggedIn;
    }

    public void plugIn() {
        this.isPluggedIn = true;
    }

    public String toString()
    {
        return name + " - " + power;
    }
    public abstract void use();
}