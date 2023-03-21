public class Kettle extends ElectricalAppliance {
    public Kettle(String name, int power) {
        super(name, power);
    }

    @Override
    public void use() {
        System.out.println("Статус электрического чайника: включен");
    }
}