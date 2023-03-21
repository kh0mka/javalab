public class Television extends ElectricalAppliance {
    public Television(String name, int power) {
        super(name, power);
    }

    @Override
    public void use() {
        System.out.println("Статус телевизора: включен");
    }
}