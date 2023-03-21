public class Refrigerator extends ElectricalAppliance {
    public Refrigerator(String name, int power) {
        super(name, power);
    }

    @Override
    public void use() {
        System.out.println("Статус холодильника: включен");
    }
}