package fr.univcotedazur.polytech.si4.fsm.project.main;

import drinkingfactory.TimerService;
import drinkingfactory.drinkingfactory.DrinkingfactoryStatemachine;
import drinkingfactory.drinkingfactory.IDrinkingfactoryStatemachine;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

import javax.imageio.ImageIO;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

public class DrinkFactoryMachine extends JFrame implements IDrinkingfactoryStatemachine.SCInterfaceListener {

    /**
     *
     */

    Thread t;

    //Main Variables
    private static final long serialVersionUID = 2030629304432075314L;
    private DrinkingfactoryStatemachine theFSM;
    private boolean heaterState = false;
    private boolean pouringState = false;
    private boolean coolingState = false;
    private int count = 0;
    private boolean cardBiped = false;
    private boolean usersCup = false;
    private Hashtable<Integer, JLabel> temperatureTable;
    private Hashtable<Integer, JLabel> freshnessTable;
    private Hashtable<Integer, JLabel> sizeTable;
    private Hashtable<Integer, JLabel> sugarTable;
    private int currentTemperatureLevel;
    private Drink currentDrinkSelected;
    private float currentMoneyInserted = 0;
    private float moneyGivingBack = 0;
    private int currentHeatedWaterTemp = 20;
    private int currentWaterVolume = 0;
    private String currentCardHash;
    private Map<String, Client> clientMap;
    private float payingAmount;
    private float promoAmount = 0;
    int coolingTime = 0;

    //UI main component
    private JPanel contentPane;
    private JLabel messagesToUser;
    private JLabel currentPicture;
    private final JSlider temperatureSlider;
    private final JSlider sizeSlider;
    private final JSlider sugarSlider;
    private final JProgressBar progressBar;
    private final JButton money50centsButton;
    private final JButton money25centsButton;
    private final JButton money10centsButton;
    private final JButton coffeeButton;
    private final JButton espressoButton;
    private final JButton teaButton;
    private final JButton soupButton;
    private final JButton icedTeaButton;
    private final JButton nfcBiiiipButton;
    private final JButton cancelButton;
    private final JButton takeCupButton;
    private final JButton takeMoneyButton;
    private final JButton addCupButton;
    private final JCheckBox optionMilk;
    private final JCheckBox optionBread;
    private final JCheckBox optionSugar;
    private final JCheckBox optionIceCream;
    private final JLabel lblSugar;
    private final JLabel lblTemperature;

    // for testing purposes
    private int coffeeStock = 4;
    private int espressoStock = 2;
    private int teaStock = 8;
    private int soupStock = 10;
    private int icedTeaStock = 10;
    private int milkStock = 1;
    private int breadStock = 3;
    private int syrupStock = 5;
    private int vanillaStock = 2;
    private int sugarStock = 10;
    private int spiceStock = 6;

    /**
     * @wbp.nonvisual location=311,475
     */
    private final ImageIcon imageIcon = new ImageIcon();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                DrinkFactoryMachine frame = new DrinkFactoryMachine();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public DrinkFactoryMachine() {
        theFSM = new DrinkingfactoryStatemachine();
        TimerService timer = new TimerService();
        theFSM.setTimer(timer);
        theFSM.init();
        theFSM.enter();

        theFSM.getSCInterface().getListeners().add(this);

        //Testing user for discount: Card Hash is "test"
        clientMap = new HashMap<>() {{
            put("test", new Client("test"));
        }};

        // for test purpose
        for (int i = 0; i < 8; i++) {
            clientMap.get("test").getPromo(0.35f);
        }

        setForeground(Color.WHITE);
        setFont(new Font("Cantarell", Font.BOLD, 22));
        setBackground(Color.DARK_GRAY);
        setTitle("Drinking Factory Machine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 650, 680);
        contentPane = new JPanel();
        contentPane.setBackground(Color.DARK_GRAY);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        //Labels
        JLabel lblSize = new JLabel("Size");
        lblSize.setForeground(Color.WHITE);
        lblSize.setBackground(Color.DARK_GRAY);
        lblSize.setHorizontalAlignment(SwingConstants.CENTER);
        lblSize.setBounds(380, 93, 44, 15);
        contentPane.add(lblSize);

        lblSugar = new JLabel("Sugar");
        lblSugar.setForeground(Color.WHITE);
        lblSugar.setBackground(Color.DARK_GRAY);
        lblSugar.setHorizontalAlignment(SwingConstants.CENTER);
        lblSugar.setBounds(380, 14, 44, 15);
        contentPane.add(lblSugar);

        JLabel lblCoins = new JLabel("Coins");
        lblCoins.setForeground(Color.WHITE);
        lblCoins.setHorizontalAlignment(SwingConstants.CENTER);
        lblCoins.setBounds(538, 12, 44, 15);
        contentPane.add(lblCoins);

        lblTemperature = new JLabel("Temperature");
        lblTemperature.setForeground(Color.WHITE);
        lblTemperature.setBackground(Color.DARK_GRAY);
        lblTemperature.setHorizontalAlignment(SwingConstants.CENTER);
        lblTemperature.setBounds(363, 173, 96, 15);
        contentPane.add(lblTemperature);

        JLabel lblNfc = new JLabel("NFC");
        lblNfc.setForeground(Color.WHITE);
        lblNfc.setHorizontalAlignment(SwingConstants.CENTER);
        lblNfc.setBounds(541, 139, 41, 15);
        contentPane.add(lblNfc);

        //Panels
        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        lblCoins.setLabelFor(panel);
        panel.setBounds(538, 25, 96, 97);
        contentPane.add(panel);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(Color.DARK_GRAY);
        panel_1.setBounds(538, 154, 96, 40);
        contentPane.add(panel_1);

        JPanel panel_2 = new JPanel();
        panel_2.setBackground(Color.DARK_GRAY);
        panel_2.setBounds(538, 217, 96, 33);
        contentPane.add(panel_2);

        JSeparator separator = new JSeparator();
        separator.setBounds(12, 312, 622, 15);
        contentPane.add(separator);

        messagesToUser = new JLabel("<html>This is<br>place to communicate <br> with the user");
        messagesToUser.setForeground(Color.WHITE);
        messagesToUser.setHorizontalAlignment(SwingConstants.LEFT);
        messagesToUser.setVerticalAlignment(SwingConstants.TOP);
        messagesToUser.setToolTipText("message to the user");
        messagesToUser.setBackground(Color.WHITE);
        messagesToUser.setBounds(126, 34, 165, 150);
        contentPane.add(messagesToUser);

        //options CheckBox
        optionMilk = new JCheckBox("");
        optionMilk.setBounds(126, 175, 100, 20);
        optionMilk.setBackground(Color.DARK_GRAY);
        optionMilk.setForeground(Color.WHITE);
        optionMilk.setEnabled(true);
        optionMilk.setText("Milk");
        optionMilk.setVisible(false);
        optionMilk.addActionListener(e -> {
            theFSM.setOptionDrink(((JCheckBox) e.getSource()).isSelected());
            onCheckPaymentRaised();
            theFSM.raiseUserAction();
        });
        contentPane.add(optionMilk);

        optionBread = new JCheckBox("");
        optionBread.setBounds(126, 250, 100, 20);
        optionBread.setBackground(Color.DARK_GRAY);
        optionBread.setForeground(Color.WHITE);
        optionBread.setEnabled(true);
        optionBread.setVisible(false);
        optionBread.setText("Bread");
        optionBread.addActionListener(e -> {
            theFSM.setOptionDrink(((JCheckBox) e.getSource()).isSelected());
            onCheckPaymentRaised();
            theFSM.raiseUserAction();
        });
        contentPane.add(optionBread);

        optionSugar = new JCheckBox("");
        optionSugar.setBounds(126, 200, 100, 20);
        optionSugar.setBackground(Color.DARK_GRAY);
        optionSugar.setForeground(Color.WHITE);
        optionSugar.setEnabled(true);
        optionSugar.setVisible(false);
        optionSugar.setText("Maple Syrup");
        optionSugar.addActionListener(e -> {
            theFSM.setOptionSugar(((JCheckBox) e.getSource()).isSelected());
            onCheckPaymentRaised();
            theFSM.raiseUserAction();
        });
        contentPane.add(optionSugar);

        optionIceCream = new JCheckBox("");
        optionIceCream.setBounds(126, 225, 150, 20);
        optionIceCream.setBackground(Color.DARK_GRAY);
        optionIceCream.setForeground(Color.WHITE);
        optionIceCream.setEnabled(true);
        optionIceCream.setText("Vanilla Ice Cream");
        optionIceCream.setVisible(false);
        optionIceCream.addActionListener(e -> {
            theFSM.setOptionIceCream(((JCheckBox) e.getSource()).isSelected());
            onCheckPaymentRaised();
            theFSM.raiseUserAction();
        });
        contentPane.add(optionIceCream);

        //Buttons Drink
        coffeeButton = new JButton("Coffee");
        coffeeButton.setForeground(Color.WHITE);
        coffeeButton.setBackground(Color.DARK_GRAY);
        coffeeButton.setBounds(12, 34, 96, 25);
        coffeeButton.addActionListener(e -> {
            setCurrentDrinkSelected(Drink.Coffee);
            theFSM.raiseCoffeeSelected();
        });
        contentPane.add(coffeeButton);

        espressoButton = new JButton("Espresso");
        espressoButton.setForeground(Color.WHITE);
        espressoButton.setBackground(Color.DARK_GRAY);
        espressoButton.setBounds(12, 71, 96, 25);
        espressoButton.addActionListener(e -> {
            setCurrentDrinkSelected(Drink.Espresso);
            theFSM.raiseEspressoSelected();
        });
        contentPane.add(espressoButton);

        teaButton = new JButton("Tea");
        teaButton.setForeground(Color.WHITE);
        teaButton.setBackground(Color.DARK_GRAY);
        teaButton.setBounds(12, 108, 96, 25);
        teaButton.addActionListener(e -> {
            setCurrentDrinkSelected(Drink.Tea);
            theFSM.raiseTeaSelected();
        });
        contentPane.add(teaButton);

        soupButton = new JButton("Soup");
        soupButton.setForeground(Color.WHITE);
        soupButton.setBackground(Color.DARK_GRAY);
        soupButton.setBounds(12, 145, 96, 25);
        soupButton.addActionListener(e -> {
            setCurrentDrinkSelected(Drink.Soup);
            theFSM.raiseSoupSelected();
        });
        contentPane.add(soupButton);

        icedTeaButton = new JButton("Iced Tea");
        icedTeaButton.setForeground(Color.WHITE);
        icedTeaButton.setBackground(Color.DARK_GRAY);
        icedTeaButton.setBounds(12, 182, 96, 25);
        icedTeaButton.addActionListener(e -> {
            setCurrentDrinkSelected(Drink.IcedTea);
            theFSM.raiseIcedTeaSelected();
        });
        contentPane.add(icedTeaButton);

        //Buttons Money
        money50centsButton = new JButton("0.50 €");
        money50centsButton.setForeground(Color.WHITE);
        money50centsButton.setBackground(Color.DARK_GRAY);
        money50centsButton.addActionListener(e -> {
            setCurrentMoneyInserted(getCurrentMoneyInserted() + 0.50f);
            theFSM.raiseAddMoney();
        });
        panel.add(money50centsButton);

        money25centsButton = new JButton("0.25 €");
        money25centsButton.setForeground(Color.WHITE);
        money25centsButton.setBackground(Color.DARK_GRAY);
        money25centsButton.addActionListener(e -> {
            setCurrentMoneyInserted(getCurrentMoneyInserted() + 0.25f);
            theFSM.raiseAddMoney();
        });
        panel.add(money25centsButton);

        money10centsButton = new JButton("0.10 €");
        money10centsButton.setForeground(Color.WHITE);
        money10centsButton.setBackground(Color.DARK_GRAY);
        money10centsButton.addActionListener(e -> {
            setCurrentMoneyInserted(getCurrentMoneyInserted() + 0.10f);
            theFSM.raiseAddMoney();
        });
        panel.add(money10centsButton);

        nfcBiiiipButton = new JButton("biiip");
        nfcBiiiipButton.setForeground(Color.WHITE);
        nfcBiiiipButton.setBackground(Color.DARK_GRAY);
        nfcBiiiipButton.addActionListener(e -> {
            String s = (String) JOptionPane.showInputDialog(
                    contentPane,
                    "Enter Card ID:\n",
                    "NFC",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "");

            if ((s != null) && (s.length() > 0)) {
                currentCardHash = s;
            }
            cardBiped = true;
            theFSM.raiseAddMoney();
        });
        panel_1.add(nfcBiiiipButton);

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setValue(0);
        progressBar.setForeground(Color.LIGHT_GRAY);
        progressBar.setBackground(Color.DARK_GRAY);
        progressBar.setBounds(12, 274, 622, 26);
        contentPane.add(progressBar);

        //Sliders
        sugarSlider = new JSlider();
        sugarSlider.setValue(1);
        sugarSlider.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        sugarSlider.setBackground(Color.DARK_GRAY);
        sugarSlider.setForeground(Color.WHITE);
        sugarSlider.setPaintTicks(true);
        sugarSlider.setPaintLabels(true);
        sugarSlider.setMinorTickSpacing(1);
        sugarSlider.setMajorTickSpacing(1);
        sugarSlider.setMaximum(4);
        sugarSlider.setBounds(301, 31, 200, 56);
        sugarSlider.addChangeListener(e -> {
            theFSM.raiseUserAction();
        });
        sugarTable = new Hashtable<>();
        sugarTable.put(0, new JLabel("0"));
        sugarTable.put(1, new JLabel("1"));
        sugarTable.put(2, new JLabel("2"));
        sugarTable.put(3, new JLabel("3"));
        sugarTable.put(4, new JLabel("4"));
        for (JLabel l : sugarTable.values()) {
            l.setForeground(Color.WHITE);
        }
        sugarSlider.setLabelTable(sugarTable);
        contentPane.add(sugarSlider);

        sizeSlider = new JSlider();
        sizeSlider.setPaintTicks(true);
        sizeSlider.setPaintLabels(true);
        sizeSlider.setValue(1);
        sizeSlider.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        sizeSlider.setBackground(Color.DARK_GRAY);
        sizeSlider.setForeground(Color.WHITE);
        sizeSlider.setMinorTickSpacing(1);
        sizeSlider.setMaximum(2);
        sizeSlider.setMajorTickSpacing(1);
        sizeSlider.setBounds(301, 105, 200, 54);
        sizeSlider.addChangeListener(e -> {
            switch (sizeSlider.getValue()) {
                case 0:
                    theFSM.setDrinkSize("big");
                    break;
                case 1:
                    theFSM.setDrinkSize("medium");
                    break;
                case 2:
                    theFSM.setDrinkSize("small");
                    break;
                default:
                    break;
            }
            theFSM.raiseUserAction();
            onCheckPaymentRaised();
        });
        sizeTable = new Hashtable<>();
        sizeTable.put(0, new JLabel("big"));
        sizeTable.put(1, new JLabel("medium"));
        sizeTable.put(2, new JLabel("small"));
        for (JLabel l : sizeTable.values()) {
            l.setForeground(Color.WHITE);
        }
        sizeSlider.setLabelTable(sizeTable);
        contentPane.add(sizeSlider);

        temperatureSlider = new JSlider();
        temperatureSlider.setPaintLabels(true);
        temperatureSlider.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        temperatureSlider.setValue(2);
        temperatureSlider.setBackground(Color.DARK_GRAY);
        temperatureSlider.setForeground(Color.WHITE);
        temperatureSlider.setPaintTicks(true);
        temperatureSlider.setMajorTickSpacing(1);
        temperatureSlider.setMaximum(3);
        temperatureSlider.setBounds(301, 188, 200, 54);
        temperatureSlider.addChangeListener(e -> {
            theFSM.raiseUserAction();
            setCurrentTemperatureLevel(getTemperature(temperatureSlider.getValue()));
        });

        temperatureTable = new Hashtable<>();
        temperatureTable.put(0, new JLabel("20°C"));
        temperatureTable.put(1, new JLabel("35°C"));
        temperatureTable.put(2, new JLabel("60°C"));
        temperatureTable.put(3, new JLabel("85°C"));
        freshnessTable = new Hashtable<>();
        freshnessTable.put(0, new JLabel("20°C"));
        freshnessTable.put(1, new JLabel("15°C"));
        freshnessTable.put(2, new JLabel("8°C"));
        freshnessTable.put(3, new JLabel("3°C"));
        for (JLabel l : temperatureTable.values()) {
            l.setForeground(Color.WHITE);
        }
        for (JLabel l : freshnessTable.values()) {
            l.setForeground(Color.WHITE);
        }
        temperatureSlider.setLabelTable(temperatureTable);
        currentTemperatureLevel = getTemperature(temperatureSlider.getValue());
        contentPane.add(temperatureSlider);

        //Other Buttons
        takeCupButton = new JButton("Take cup");
        takeCupButton.setForeground(Color.WHITE);
        takeCupButton.setBackground(Color.DARK_GRAY);
        takeCupButton.setBounds(495, 336, 115, 25);
        takeCupButton.addActionListener(e -> {
            takeCup();
        });
        contentPane.add(takeCupButton);
        takeCupButton.setVisible(false);

        addCupButton = new JButton("Add cup");
        addCupButton.setForeground(Color.WHITE);
        addCupButton.setBackground(Color.DARK_GRAY);
        addCupButton.setBounds(45, 336, 96, 25);
        addCupButton.addActionListener(e -> {
            usersCup = true;
            BufferedImage myPicture = null;
            try {
                myPicture = ImageIO.read(new File("./picts/ownCup.jpg"));
            } catch (IOException ee) {
                ee.printStackTrace();
            }
            currentPicture.setIcon(new ImageIcon(myPicture));
            theFSM.setCupPlaced(true);
            addCupButton.setVisible(false);
            takeCupButton.setVisible(true);
            theFSM.raiseUserAction();
            updateMessageToUser();
        });
        contentPane.add(addCupButton);

        takeMoneyButton = new JButton("Take money");
        takeMoneyButton.setForeground(Color.WHITE);
        takeMoneyButton.setBackground(Color.DARK_GRAY);
        takeMoneyButton.setBounds(495, 366, 115, 25);
        takeMoneyButton.addActionListener(e -> {
            takeBackMoney();
            updateMessageToUser();
        });
        contentPane.add(takeMoneyButton);
        takeMoneyButton.setVisible(false);

        cancelButton = new JButton("Cancel");
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBackground(Color.DARK_GRAY);
        cancelButton.addActionListener(e -> {
            onDoResetMachineRaised();
        });
        panel_2.add(cancelButton);

        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File("./picts/vide2.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentPicture = new JLabel(new ImageIcon(myPicture));
        currentPicture.setBounds(175, 339, 286, 260);
        contentPane.add(currentPicture);

        // listeners
        updateMessageToUser();

        ActionListener doOnTimer = (e -> {
            simulateWaterTemp();
            simulateWaterPouring();
            simulateCooling();
        });

        //timers
        Timer waterHeatTimer = new Timer(200, doOnTimer);
        waterHeatTimer.start();

        Runnable r = () -> {
            while (true) {
                theFSM.runCycle();
                try {
                    this.progressBar.setValue((int) theFSM.getProgress());
                    if (theFSM.isStateActive(DrinkingfactoryStatemachine.State.machine_management_Preparation_r1_step4_r1_sync)) {
                        takeCupButton.setVisible(true);
                    }
                    updateMessageToUser();
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };
        t = new Thread(r);
        t.start();

    }

    //setters
    public void setCurrentDrinkSelected(Drink currentDrinkSelected) {
        this.currentDrinkSelected = currentDrinkSelected;
        updateMessageToUser();
    }

    public void setCurrentTemperatureLevel(int currentTemperatureLevel) {
        this.currentTemperatureLevel = currentTemperatureLevel;
    }

    public void setCurrentMoneyInserted(float currentMoneyInserted) {
        this.currentMoneyInserted = Math.round(currentMoneyInserted * 100) / 100f;
        updateMessageToUser();
    }

    //getters
    public float getCurrentMoneyInserted() {
        return currentMoneyInserted;
    }

    public int getTemperature(int sliderValue) {
        if (currentDrinkSelected != Drink.IcedTea) {
            switch (sliderValue) {
                case 0:
                    return 20;
                case 1:
                    return 35;
                case 2:
                    return 60;
                case 3:
                    return 85;
                default:
                    return 0;
            }
        } else {
            return 60;
        }
    }

    public int getSize(int sliderValue) {
        switch (sliderValue) {
            case 0:
                return 50;
            case 1:
                return 25;
            case 2:
                return 10;
            default:
                return 0;
        }
    }

    //UI update
    private void updateMessageToUser() {
        if (!theFSM.isStateActive(DrinkingfactoryStatemachine.State.machine_management_Cleaning)) {
            if (currentDrinkSelected != null) {
                messagesToUser.setText("<html>Current amount: " + currentMoneyInserted + "€<br>" +
                        "Card Biped: " + cardBiped + "<br>" +
                        "Current Drink: " + currentDrinkSelected.getName() + "<br>" +
                        "Drink Cost: " + currentDrinkSelected.getPrice() + "€<br>" +
                        ((theFSM.getCupPlaced()) ? "Cup OK" : "No Cup") + "<br>" +
                        "Water Temp: " + currentHeatedWaterTemp + "<br>" +
                        "Paying: " + payingAmount + "€<br>" +
                        ((promoAmount != 0) ? "Promo!!!<br>" : "") +
                        "Giving back: " + moneyGivingBack + "€<br>" +
                        "</html>");
            } else {
                messagesToUser.setText("<html>Current amount: " + currentMoneyInserted + "€<br>" +
                        "Card Biped: " + cardBiped + "<br>" +
                        "Current Drink: " + "none" + "<br>" +
                        "Drink Cost: " + "0.0" + "€<br>" +
                        ((theFSM.getCupPlaced()) ? "Cup OK" : "No Cup") + "<br>" +
                        "Water Temp: " + currentHeatedWaterTemp + "<br>" +
                        "Paying: " + "0.0" + "€<br>" +
                        "Giving back: " + moneyGivingBack + "€<br>" +
                        "</html>");
            }
        }
    }

    private void preparationInProgress(boolean state) {
        state = !state;
        money10centsButton.setEnabled(state);
        money25centsButton.setEnabled(state);
        money50centsButton.setEnabled(state);

        temperatureSlider.setEnabled(state);
        sizeSlider.setEnabled(state);
        sugarSlider.setEnabled(state);

        coffeeButton.setEnabled(state);
        espressoButton.setEnabled(state);
        teaButton.setEnabled(state);
        soupButton.setEnabled(state);
        icedTeaButton.setEnabled(state);
        nfcBiiiipButton.setEnabled(state);
        cancelButton.setEnabled(state);
        addCupButton.setVisible(state);

        optionIceCream.setEnabled(state);
        optionSugar.setEnabled(state);
        optionMilk.setEnabled(state);
        optionBread.setEnabled(state);
    }

    //simulators
    private void simulateWaterTemp() {
        if (heaterState) {
            currentHeatedWaterTemp += 1;
        } else {
            count = (count + 1) % 20;
            if (count == 0 && currentHeatedWaterTemp > 20) {
                currentHeatedWaterTemp -= 1;
            } else if (count == 0 && currentHeatedWaterTemp < 20) {
                currentHeatedWaterTemp += 1;
            }
        }
        if (currentHeatedWaterTemp >= currentTemperatureLevel && heaterState) {
            heaterState = false;
            theFSM.raiseTempReached();
        }
        updateMessageToUser();
    }

    private void simulateWaterPouring() {
        if (pouringState) {
            System.out.println(currentWaterVolume);
            currentWaterVolume += 1;
        }
        if (currentDrinkSelected == Drink.Espresso) {
            if (currentWaterVolume >= this.getSize(1) && pouringState) {
                pouringState = false;
                theFSM.raiseCupFilled();
            }
        } else if (currentWaterVolume >= this.getSize(sizeSlider.getValue()) && pouringState) {
            pouringState = false;
            if (!theFSM.getDrinkName().equals("tea")) {
                theFSM.raiseCupFilled();
            } else {
                theFSM.raiseCupFilled();
            }
        }
        updateMessageToUser();
    }

    private void simulateCooling() {
        if (coolingState) {
            coolingTime += 1;
            System.out.println(coolingTime);
            if (coolingTime >= ((theFSM.getDrinkSize().equals("medium")) ? temperatureSlider.getValue() * 30 : temperatureSlider.getValue() * 50)) {
                coolingTime = 0;
                coolingState = false;
                theFSM.raiseTempReached();
            }
        }
    }

    public void takeBackMoney() {
        // simulate user taking the money coming out
        moneyGivingBack = 0;
        takeMoneyButton.setVisible(false);
    }

    public void takeCup() {
        // simulate the user picking the cup
        updateMessageToUser();
        BufferedImage emptyPicture = null;
        try {
            emptyPicture = ImageIO.read(new File("./picts/vide2.jpg"));
        } catch (IOException ee) {
            ee.printStackTrace();
        }
        currentPicture.setIcon(new ImageIcon(emptyPicture));
        if (currentWaterVolume > 1) {
            currentWaterVolume = 0;
            theFSM.raiseCupGrabbed();
        } else {
            addCupButton.setVisible(true);
        }
        takeCupButton.setVisible(false);
        theFSM.setCupPlaced(false);
        System.out.println("Cup taken");
    }

    //payment management
    private float calculatePayingAmount() {
        payingAmount = currentDrinkSelected.getPrice();

        if (optionMilk.isSelected()) {
            payingAmount += 0.1f;
        }
        if (optionBread.isSelected()) {
            payingAmount += 0.3f;
        }
        if (optionSugar.isSelected()) {
            payingAmount += 0.1f;
        }
        if (optionIceCream.isSelected()) {
            payingAmount += 0.6f;
        }
        if (currentDrinkSelected == Drink.IcedTea && theFSM.getDrinkSize().equals("big")) {
            payingAmount += 0.25f;
        }

        if (cardBiped) {
            promoAmount = clientMap.get(currentCardHash).getPromo(payingAmount);
            payingAmount = Math.max(payingAmount - promoAmount - ((theFSM.getCupPlaced()) ? 0.1f : 0), 0);
        } else {
            payingAmount = payingAmount - ((theFSM.getCupPlaced()) ? 0.1f : 0);
        }
        payingAmount = Math.round(payingAmount * 100) / 100f;

        return payingAmount;
    }

    // Stock management simulation
    private void takeOffStock() {
        switch (currentDrinkSelected) {
            case Tea:
                teaStock--;
                break;
            case Soup:
                soupStock--;
                break;
            case Coffee:
                coffeeStock--;
                break;
            case IcedTea:
                icedTeaStock--;
                break;
            case Espresso:
                espressoStock--;
                break;
            default:
                break;
        }
        if (optionBread.isSelected()) {
            breadStock--;
        }
        if (optionMilk.isSelected()) {
            milkStock--;
        }
        if (optionIceCream.isSelected()) {
            vanillaStock--;
        }
        if (optionSugar.isSelected()) {
            syrupStock--;
        }
        else {
            if (currentDrinkSelected != Drink.Soup) {
                sugarStock -= sugarSlider.getValue();
            }
            else {
                spiceStock -= sugarSlider.getValue();
            }
        }
    }

    //Overrides
    @Override
    public void onDisplayOptionsRaised() {
        // change UI according to the selected drink
        switch (currentDrinkSelected) {
            case Coffee:
            case Espresso:
                lblSugar.setText("Sugar");
                lblTemperature.setText("Temperature");
                optionMilk.setVisible(true);
                optionBread.setVisible(false);
                optionSugar.setVisible(true);
                optionIceCream.setVisible(true);
                optionMilk.setSelected(false);
                optionSugar.setSelected(false);
                optionIceCream.setSelected(false);
                optionBread.setSelected(false);
                sizeSlider.setMaximum(2);
                temperatureSlider.setLabelTable(temperatureTable);
                if (sugarStock <= 4){
                    sugarSlider.setMaximum(sugarStock);
                }
                else {
                    sugarSlider.setMaximum(4);
                }
                break;
            case Tea:
                lblSugar.setText("Sugar");
                lblTemperature.setText("Temperature");
                optionMilk.setVisible(true);
                optionBread.setVisible(false);
                optionSugar.setVisible(true);
                if (sugarStock <= 4){
                    sugarSlider.setMaximum(sugarStock);
                }
                else {
                    sugarSlider.setMaximum(4);
                }
                optionIceCream.setVisible(false);
                optionMilk.setSelected(false);
                optionSugar.setSelected(false);
                optionIceCream.setSelected(false);
                optionBread.setSelected(false);
                sizeSlider.setMaximum(2);
                temperatureSlider.setLabelTable(temperatureTable);
                break;
            case Soup:
                lblSugar.setText("Spices");
                lblTemperature.setText("Temperature");
                optionMilk.setVisible(false);
                optionSugar.setVisible(false);
                optionIceCream.setVisible(false);
                optionBread.setVisible(true);
                optionMilk.setSelected(false);
                optionSugar.setSelected(false);
                optionIceCream.setSelected(false);
                optionBread.setSelected(false);
                sizeSlider.setMaximum(2);
                temperatureSlider.setLabelTable(temperatureTable);
                if (spiceStock <= 4){
                    sugarSlider.setMaximum(spiceStock);
                }
                else {
                    sugarSlider.setMaximum(4);
                }
                break;
            case IcedTea:
                lblSugar.setText("Sugar");
                lblTemperature.setText("Freshness");
                optionMilk.setVisible(false);
                optionBread.setVisible(false);
                optionSugar.setVisible(true);
                optionIceCream.setVisible(false);
                optionMilk.setSelected(false);
                optionSugar.setSelected(false);
                optionIceCream.setSelected(false);
                optionBread.setSelected(false);
                if (sugarStock <= 4){
                    sugarSlider.setMaximum(sugarStock);
                }
                else {
                    sugarSlider.setMaximum(4);
                }
                sizeSlider.setMaximum(1);
                temperatureSlider.setLabelTable(freshnessTable);
                break;
            default:
                optionMilk.setSelected(false);
                optionSugar.setSelected(false);
                optionIceCream.setSelected(false);
                sizeSlider.setMaximum(2);
                optionBread.setSelected(false);
                if (sugarStock <= 4){
                    sugarSlider.setMaximum(sugarStock);
                }
                else {
                    sugarSlider.setMaximum(4);
                }
                break;
        }
    }

    @Override
    public void onDoResetMoneyRaised() {
        if (currentMoneyInserted != 0) {
            moneyGivingBack = Math.round((moneyGivingBack + currentMoneyInserted) * 100) / 100f;
            takeMoneyButton.setVisible(true);
        }
        setCurrentMoneyInserted(0);
    }

    @Override
    public void onDoResetMachineRaised() {
        onDoResetMoneyRaised();
        payingAmount = 0;
        promoAmount = 0;
        cardBiped = false;
        usersCup = false;
        currentDrinkSelected = null;
        preparationInProgress(false);
        optionMilk.setSelected(false);
        optionMilk.setVisible(false);
        optionBread.setSelected(false);
        optionBread.setVisible(false);
        optionSugar.setSelected(false);
        optionSugar.setVisible(false);
        optionIceCream.setSelected(false);
        optionIceCream.setVisible(false);
        sugarSlider.setValue(1);
        sizeSlider.setValue(1);
        sizeSlider.setMaximum(2);
        temperatureSlider.setValue(2);
        System.out.println("params reseted");
    }

    @Override
    public void onDoCleaningRaised() {
        if (!theFSM.isStateActive(DrinkingfactoryStatemachine.State.machine_management_Standby)) {
            messagesToUser.setText("<html>Cleaning...</html>");
        }
    }

    // real life actions
    @Override
    public void onGrindBeansRaised() {
        // grind beans for the espresso according to the size selected
    }

    @Override
    public void onTakeTeaBagRaised() {
        //take a tea bag from the stock
    }

    @Override
    public void onTakeOffTeaBagRaised() {
        //take off the tea bag in the cup
    }

    @Override
    public void onTakeCoffeePodRaised() {
        //take a coffee pod from the stock
    }

    @Override
    public void onHeatWaterRaised() {
        //start the water heater
        heaterState = true;
        addCupButton.setVisible(false);
        takeCupButton.setVisible(false);
    }

    @Override
    public void onPutCupRaised() {
        // put a throwable cup from the machine
        if (!theFSM.getCupPlaced()) {
            BufferedImage myPicture = null;
            try {
                myPicture = ImageIO.read(new File("./picts/gobeletPolluant.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            currentPicture.setIcon(new ImageIcon(myPicture));
            takeCupButton.setVisible(false);
        }
    }

    @Override
    public void onAddSugarRaised() {
        // add sugar according to the amount selected
    }

    @Override
    public void onPourWaterRaised() {
        // start pouring some water
        if (theFSM.getCupPlaced()) {
            pouringState = true;
        }
    }

    @Override
    public void onPutBeansRaised() {
        //put the beans for the espresso in the grinder
    }

    @Override
    public void onCheckPaymentRaised() {
        if (currentDrinkSelected != null) {
            if (cardBiped) {
                preparationInProgress(true);
                if (!clientMap.containsKey(currentCardHash)) {
                    clientMap.put(currentCardHash, new Client(currentCardHash));
                }
                calculatePayingAmount();
                takeOffStock();
                theFSM.raisePaymentValidate(); // debit from card
            } else if (currentMoneyInserted >= calculatePayingAmount()) {
                setCurrentMoneyInserted(currentMoneyInserted - payingAmount);
                preparationInProgress(true);
                takeOffStock();
                theFSM.raisePaymentValidate();
            }
        }
    }

    @Override
    public void onCheckStocksRaised() {
        // disable buttons if unavailable
        if (coffeeStock <= 0) {
            coffeeButton.setEnabled(false);
        }
        if (espressoStock <= 0) {
            espressoButton.setEnabled(false);
        }
        if (teaStock <= 0) {
            teaButton.setEnabled(false);
        }
        if (soupStock <= 0) {
            soupButton.setEnabled(false);
        }
        if (icedTeaStock <= 0) {
            icedTeaButton.setEnabled(false);
        }
        if (syrupStock <= 0) {
            optionSugar.setEnabled(false);
        }
        if (vanillaStock <= 0) {
            optionIceCream.setEnabled(false);
        }
        if (milkStock <= 0) {
            optionMilk.setEnabled(false);
        }
        if (breadStock <= 0) {
            optionBread.setEnabled(false);
        }
        if (sugarStock <= 4) {
            sugarSlider.setMaximum(sugarStock);
        }
    }

    @Override
    public void onPourMilkRaised() {
        // pour milk in the cup
    }

    @Override
    public void onPourSyrupRaised() {
        // pour syrup in the cup
    }

    @Override
    public void onAddBreadRaised() {
        // add bread in the soup
    }

    @Override
    public void onAddIceCreamRaised() {
        // add some vanilla ice cream
    }

    @Override
    public void onPourSoupRaised() {
        // pour soup in the cup
    }

    @Override
    public void onPutSpiceRaised() {
        // put spices according to the amount selected
    }

    @Override
    public void onLockDoorRaised() {
        // lock the door before cooling
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File("./picts/Stainless-Steel-Doors-2.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentPicture.setIcon(new ImageIcon(myPicture));
        takeCupButton.setVisible(false);
    }

    @Override
    public void onUnlockDoorRaised() {
        // unlock the door after cooling
        BufferedImage myPicture = null;
        try {
            if (usersCup) {
                myPicture = ImageIO.read(new File("./picts/ownCup.jpg"));
            }
            else {
                myPicture = ImageIO.read(new File("./picts/gobeletPolluant.jpg"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentPicture.setIcon(new ImageIcon(myPicture));
        takeCupButton.setVisible(false);
    }

    @Override
    public void onNitrogenInjectionRaised() {
        // start the nitrogen cooling
        coolingState = true;
    }

    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        super.finalize();
        t.stop();
    }
}
