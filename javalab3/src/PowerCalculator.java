import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.*;

public class PowerCalculator extends JFrame implements ActionListener {

    private final JRadioButton adminRadio;
    private final JRadioButton userRadio;
    private final ButtonGroup radioButtonGroup;
    private final JTextField powerTextField;
    private final JLabel powerTotal;
    private final JLabel newAppliance;
    private final JLabel newWoltage;
    private final JButton calculateButton;
    private final JButton addApplianceButton;
    private final JButton genCheckBox = new JButton("Generate checkboxes");
    private final JTextField newApplianceTextField;
    private final JTextField newWattageTextField;
    private final HashMap<String, Double> applianceMap;
    private final HashMap<String, Double> currentApplianceMap = new HashMap<>();
    private final JPanel appliancePanel = new JPanel(new GridLayout(10, 3));

    public PowerCalculator() {
        setTitle("Power Calculator");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        adminRadio = new JRadioButton("Admin");
        userRadio = new JRadioButton("User");
        radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(adminRadio);
        radioButtonGroup.add(userRadio);
        adminRadio.addActionListener(this);
        userRadio.addActionListener(this);

        applianceMap = new HashMap<>();

        powerTextField = new JTextField();
        powerTextField.setEditable(false);
        powerTextField.setVisible(false);

        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(this);
        calculateButton.setVisible(false);

        addApplianceButton = new JButton("Add Appliance");
        addApplianceButton.addActionListener(this);
        addApplianceButton.setVisible(false);

        newApplianceTextField = new JTextField();
        newWattageTextField = new JTextField();

        JPanel radioPanel = new JPanel(new FlowLayout());
        radioPanel.add(adminRadio);
        radioPanel.add(userRadio);

        JPanel powerPanel = new JPanel(new FlowLayout());
        powerPanel.add(genCheckBox);
        genCheckBox.addActionListener(this);
        genCheckBox.setVisible(false);
        powerTotal = new JLabel();
        powerTotal.setText("Power (W):");
        powerTotal.setVisible(false);
        powerPanel.add(powerTotal);
        powerPanel.add(powerTextField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(calculateButton);

        JPanel addAppliancePanel = new JPanel(new GridLayout(3, 2));

        newAppliance = new JLabel();
        newWoltage = new JLabel();
        newAppliance.setText("New Appliance:");
        newAppliance.setVisible(false);
        newWoltage.setText("New Wattage (W):");
        newWoltage.setVisible(false);
        addAppliancePanel.add(newAppliance);
        addAppliancePanel.add(newApplianceTextField);
        newApplianceTextField.setVisible(false);
        addAppliancePanel.add(newWoltage);
        addAppliancePanel.add(newWattageTextField);
        newWattageTextField.setVisible(false);
        addAppliancePanel.add(addApplianceButton);
        add(radioPanel, BorderLayout.NORTH);
        add(appliancePanel, BorderLayout.CENTER);
        add(powerPanel, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.EAST);
        add(addAppliancePanel, BorderLayout.WEST);

        setVisible(true);
    }

    public static void main(String[] args) {
        PowerCalculator calculator = new PowerCalculator();
    }

    private void addAppliance(String appliance, double wattage) {

        if (applianceMap.containsKey(appliance)) {
            JOptionPane.showMessageDialog(this, "Appliance already exists: " + appliance);
        } else {

            applianceMap.put(appliance, wattage);
            JOptionPane.showMessageDialog(this, "Appliance added: " + appliance + " (" + wattage + " W)");
        }
    }

    private void addToCurrentAppliance(String appliance, double wattage) {
        currentApplianceMap.put(appliance, wattage);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == adminRadio) {
            powerTextField.setVisible(false);
            calculateButton.setVisible(false);
            powerTotal.setVisible(false);
            addApplianceButton.setVisible(true);
            newApplianceTextField.setVisible(true);
            newWattageTextField.setVisible(true);
            newAppliance.setVisible(true);
            newWoltage.setVisible(true);
            genCheckBox.setVisible(true);
            appliancePanel.setVisible(false);
            revalidate();
            repaint();
        }
        if (e.getSource() == userRadio) {
            powerTextField.setVisible(true);
            calculateButton.setVisible(true);
            powerTotal.setVisible(true);
            addApplianceButton.setVisible(false);
            newApplianceTextField.setVisible(false);
            newWattageTextField.setVisible(false);
            newAppliance.setVisible(false);
            newWoltage.setVisible(false);
            genCheckBox.setVisible(false);
            appliancePanel.setVisible(true);
            revalidate();
            repaint();
        }
        if (e.getSource() == calculateButton) {
            String result = calculateSelectedWattage();
            powerTextField.setText(result);
            revalidate();
            repaint();
        }
        if (e.getSource() == addApplianceButton) {
            String newAppliance = newApplianceTextField.getText();
            if(newAppliance.isEmpty()){
                JOptionPane.showMessageDialog(this, "You did not enter a appliance name");
                return;
            }
            double newWattage = 0;
            try {
                newWattage = Double.parseDouble(newWattageTextField.getText());
            } catch(NumberFormatException exception) {
                JOptionPane.showMessageDialog(this, "Not double values in Wattage");
                return;
            }
            addAppliance(newAppliance, newWattage);
            addToCurrentAppliance(newAppliance, newWattage);
            newApplianceTextField.setText("");
            newWattageTextField.setText("");
        }
        if (e.getSource() == genCheckBox) {
            if (currentApplianceMap.isEmpty()){
                JOptionPane.showMessageDialog(this, "You must add appliances to generate checkboxes");
                return;
            }
            JOptionPane.showMessageDialog(this, "Checkboxes have been successfully generated");
            generateCheckbox(currentApplianceMap);
        }
    }

    public void generateCheckbox(HashMap<String, Double> map) {
        for (String key : map.keySet()) {
            JCheckBox checkbox = new JCheckBox(key);
            checkbox.setText(key + " W: " + map.get(key));
            appliancePanel.add(checkbox);
            checkbox.setVisible(true);
        }
        map.clear();
    }

    public String calculateSelectedWattage() {
        double selectedWattage = 0;
        Component[] components = appliancePanel.getComponents();
        for (Component component : components) {
            if (component instanceof JCheckBox) {
                JCheckBox checkbox = (JCheckBox) component;
                if (checkbox.isSelected()) {
                    String[] parts = checkbox.getText().split(" ");
                    Double wattage = Double.parseDouble(parts[2]);
                    if (wattage != null) {
                        selectedWattage += wattage;
                    }
                }
            }
        }
        return Double.toString(selectedWattage);
    }
}