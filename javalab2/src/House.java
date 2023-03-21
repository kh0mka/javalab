import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import static java.util.stream.Collectors.*;

public class House {
    private final List<ElectricalAppliance> appliances;

    public House() {
        this.appliances = new ArrayList<>();
        appliances.add(new Lamp("Лампа", 25));
        appliances.add(new Television("Телевизор", 120));
        appliances.add(new Kettle("Электрический чайник", 170));
        appliances.add(new Refrigerator("Холодильник", 200));
    }
    public void addAppliance(ElectricalAppliance appliance) {
        appliances.add(appliance);
    }

    /**
     * Возвращает общую мощность включенных приборов в доме.
     */
    public int getTotalPower() {
        return appliances.stream()
                .filter(ElectricalAppliance::isPluggedIn)
                .mapToInt(ElectricalAppliance::getPower)
                .sum();
    }

    /**
     * Включает прибор в розетку.
     */
    public void plugInAppliance(ElectricalAppliance appliance) {
        appliance.plugIn();
    }

    /**
     * Возвращает список приборов, отсортированный по мощности в порядке возрастания.
     */
    public List<ElectricalAppliance> getAppliancesSortedByPowerAscending() {
        List<ElectricalAppliance> sortedAppliances = appliances.stream()
                .sorted(Comparator.comparingInt(ElectricalAppliance::getPower))
                .collect(toList());
        return sortedAppliances;
    }

    /**
     * Возвращает список приборов, отсортированный по мощности в порядке убывания.
     */
    public List<ElectricalAppliance> getAppliancesSortedByPowerDescending() {
        List<ElectricalAppliance> sortedAppliances = appliances.stream()
                .sorted(Comparator.comparingInt(ElectricalAppliance::getPower).reversed())
                .collect(toList());
        return sortedAppliances;
    }

    /**
     * Возвращает список не включенных приборов в доме.
     */
    public List<ElectricalAppliance> getUnpluggedAppliances() {
        List<ElectricalAppliance> unpluggedAppliances = appliances.stream()
                .filter(appliance -> !appliance.isPluggedIn())
                .collect(toList());
        return unpluggedAppliances;
    }
}