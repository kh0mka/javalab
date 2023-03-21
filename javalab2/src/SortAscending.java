import java.util.List;
public class SortAscending implements Runnable {
    private House house;

    public SortAscending(House house) {
        this.house = house;
    }

    @Override
    public void run() {
        List<ElectricalAppliance> sortedByPowerAscending = house.getAppliancesSortedByPowerAscending();
        System.out.println("Сортировка мощности приборов (по возрастанию/asc): " + sortedByPowerAscending);
    }
}
