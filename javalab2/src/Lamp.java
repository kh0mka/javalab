public class Lamp extends ElectricalAppliance {
    public Lamp(String name, int power) {
        super(name, power);
    }

    @Override
    public void use() {
        System.out.println("Статус лампы: включен");
    }
}