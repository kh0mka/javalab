import java.util.List;
public class SortDescending implements Runnable {
    private House house;
    public SortDescending(House house) {
        this.house = house;
    }

    @Override
    public void run() {
        List<ElectricalAppliance> sortedByPowerDescending = house.getAppliancesSortedByPowerDescending();
        System.out.println("Сортировка мощности приборов (по убыванию/desc): " + sortedByPowerDescending);
    }
}
