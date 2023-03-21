public class Main {
    public static void main(String[] args) {
        House house = new House();

        for (ElectricalAppliance appliance : house.getUnpluggedAppliances()) {
            house.plugInAppliance(appliance);
            appliance.use();
        }

        SortAscending sortAscending = new SortAscending(house);
        Thread sortAscendingThread = new Thread(sortAscending);

        SortDescending sortDescending = new SortDescending(house);
        Thread sortDescendingThread = new Thread(sortDescending);

        sortAscendingThread.start();

        try {
            sortAscendingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Запускаем третий поток, который сортирует приборы по мощности в порядке убывания
        sortDescendingThread.start();

        int totalPowerConsumption = house.getTotalPower();
        System.out.println("Суммарная потребляемая мощность: " + totalPowerConsumption + " Вт");
    }
}