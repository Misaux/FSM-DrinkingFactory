package fr.univcotedazur.polytech.si4.fsm.project.main;

import drinkingfactory.TimerService;
import drinkingfactory.drinkingfactory.DrinkingfactoryStatemachine;
import drinkingfactory.drinkingfactory.IDrinkingfactoryStatemachine;
import drinks.Drink;
import drinks.DrinkSize;
import orders.Order;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import javax.swing.Timer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

public class DrinkFactoryMachine extends JFrame implements IDrinkingfactoryStatemachine.SCInterfaceListener {

    /**
     *
     */
    private static final long serialVersionUID = 2030629304432075314L;
    private JPanel contentPane;
    private JLabel messagesToUser;
    private JLabel currentPicture;
    private DrinkingfactoryStatemachine theFSM;
    private boolean heaterState = false;
    private boolean pouringState = false;
    private int count = 0;
    private boolean cardBiped = false;
    private boolean cupPlaced = false;
    private Hashtable<Integer, JLabel> temperatureTable;

    private Drink currentDrinkSelected;
    private int currentSugarLevel;
    private int currentSizeLevel;
    private int currentTemperatureLevel;
    private float currentMoneyInserted = 0;
    private float moneyGivingBack = 0;
    private int currentHeatedWaterTemp = 20;
    private int currentWaterVolume=0;

    private JButton money50centsButton;
    private JButton money25centsButton;
    private JButton money10centsButton;
    JSlider temperatureSlider;
    JSlider sizeSlider;
    JSlider sugarSlider;
    JProgressBar progressBar;
    JButton coffeeButton;
    JButton espressoButton;
    JButton teaButton;
    JButton soupButton;
    JButton icedTeaButton;
    JButton nfcBiiiipButton;
    JButton cancelButton;
    JButton takeCupButton;


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

        setForeground(Color.WHITE);
        setFont(new Font("Cantarell", Font.BOLD, 22));
        setBackground(Color.DARK_GRAY);
        setTitle("Drinking Factory Machine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 650, 650);
        contentPane = new JPanel();
        contentPane.setBackground(Color.DARK_GRAY);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        messagesToUser = new JLabel("<html>This is<br>place to communicate <br> with the user");
        messagesToUser.setForeground(Color.WHITE);
        messagesToUser.setHorizontalAlignment(SwingConstants.LEFT);
        messagesToUser.setVerticalAlignment(SwingConstants.TOP);
        messagesToUser.setToolTipText("message to the user");
        messagesToUser.setBackground(Color.WHITE);
        messagesToUser.setBounds(126, 34, 165, 175);
        contentPane.add(messagesToUser);

        JLabel lblCoins = new JLabel("Coins");
        lblCoins.setForeground(Color.WHITE);
        lblCoins.setHorizontalAlignment(SwingConstants.CENTER);
        lblCoins.setBounds(538, 12, 44, 15);
        contentPane.add(lblCoins);

        coffeeButton = new JButton("Coffee");
        coffeeButton.setForeground(Color.WHITE);
        coffeeButton.setBackground(Color.DARK_GRAY);
        coffeeButton.setBounds(12, 34, 96, 25);
        coffeeButton.addActionListener(e -> {
            setCurrentDrinkSelected(Drink.Coffee);
            theFSM.raiseDrinkSelected();
        });
        contentPane.add(coffeeButton);

        espressoButton = new JButton("Espresso");
        espressoButton.setForeground(Color.WHITE);
        espressoButton.setBackground(Color.DARK_GRAY);
        espressoButton.setBounds(12, 71, 96, 25);
        espressoButton.addActionListener(e -> {
            setCurrentDrinkSelected(Drink.Espresso);
            theFSM.raiseDrinkSelected();
        });
        contentPane.add(espressoButton);

        teaButton = new JButton("Tea");
        teaButton.setForeground(Color.WHITE);
        teaButton.setBackground(Color.DARK_GRAY);
        teaButton.setBounds(12, 108, 96, 25);
        teaButton.addActionListener(e -> {
            setCurrentDrinkSelected(Drink.Tea);
            theFSM.raiseDrinkSelected();
        });
        contentPane.add(teaButton);

        soupButton = new JButton("Soup");
        soupButton.setForeground(Color.WHITE);
        soupButton.setBackground(Color.DARK_GRAY);
        soupButton.setBounds(12, 145, 96, 25);
        soupButton.addActionListener(e -> {
            setCurrentDrinkSelected(Drink.Soup);
            theFSM.raiseDrinkSelected();
        });
        contentPane.add(soupButton);

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setValue(0);
        progressBar.setForeground(Color.LIGHT_GRAY);
        progressBar.setBackground(Color.DARK_GRAY);
        progressBar.setBounds(12, 254, 622, 26);
        contentPane.add(progressBar);

        sugarSlider = new JSlider();
        sugarSlider.setValue(1);
        sugarSlider.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        sugarSlider.setBackground(Color.DARK_GRAY);
        sugarSlider.setForeground(Color.WHITE);
        sugarSlider.setPaintTicks(true);
        sugarSlider.setMinorTickSpacing(1);
        sugarSlider.setMajorTickSpacing(1);
        sugarSlider.setMaximum(4);
        sugarSlider.setBounds(301, 51, 200, 36);
        sugarSlider.addChangeListener(e -> {
            setSugarLevel(sugarSlider.getValue());
            theFSM.raiseUserAction();
        });
        contentPane.add(sugarSlider);

        sizeSlider = new JSlider();
        sizeSlider.setPaintTicks(true);
        sizeSlider.setValue(1);
        sizeSlider.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        sizeSlider.setBackground(Color.DARK_GRAY);
        sizeSlider.setForeground(Color.WHITE);
        sizeSlider.setMinorTickSpacing(1);
        sizeSlider.setMaximum(2);
        sizeSlider.setMajorTickSpacing(1);
        sizeSlider.setBounds(301, 125, 200, 36);
        sizeSlider.addChangeListener(e -> {
            setCurrentSizeLevel(sizeSlider.getValue());
            theFSM.raiseUserAction();
        });
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
        });

        temperatureTable = new Hashtable<>();
        temperatureTable.put(0, new JLabel("20°C"));
        temperatureTable.put(1, new JLabel("35°C"));
        temperatureTable.put(2, new JLabel("60°C"));
        temperatureTable.put(3, new JLabel("85°C"));
        for (JLabel l : temperatureTable.values()) {
            l.setForeground(Color.WHITE);
        }
        temperatureSlider.setLabelTable(temperatureTable);

        temperatureSlider.addChangeListener(e -> setCurrentTemperatureLevel(temperatureSlider.getValue()));

        contentPane.add(temperatureSlider);

        icedTeaButton = new JButton("Iced Tea");
        icedTeaButton.setForeground(Color.WHITE);
        icedTeaButton.setBackground(Color.DARK_GRAY);
        icedTeaButton.setBounds(12, 182, 96, 25);
        contentPane.add(icedTeaButton);

        JLabel lblSugar = new JLabel("Sugar");
        lblSugar.setForeground(Color.WHITE);
        lblSugar.setBackground(Color.DARK_GRAY);
        lblSugar.setHorizontalAlignment(SwingConstants.CENTER);
        lblSugar.setBounds(380, 34, 44, 15);
        contentPane.add(lblSugar);

        JLabel lblSize = new JLabel("Size");
        lblSize.setForeground(Color.WHITE);
        lblSize.setBackground(Color.DARK_GRAY);
        lblSize.setHorizontalAlignment(SwingConstants.CENTER);
        lblSize.setBounds(380, 113, 44, 15);
        contentPane.add(lblSize);

        JLabel lblTemperature = new JLabel("Temperature");
        lblTemperature.setForeground(Color.WHITE);
        lblTemperature.setBackground(Color.DARK_GRAY);
        lblTemperature.setHorizontalAlignment(SwingConstants.CENTER);
        lblTemperature.setBounds(363, 173, 96, 15);
        contentPane.add(lblTemperature);

        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        lblCoins.setLabelFor(panel);
        panel.setBounds(538, 25, 96, 97);
        contentPane.add(panel);

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

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(Color.DARK_GRAY);
        panel_1.setBounds(538, 154, 96, 40);
        contentPane.add(panel_1);

        nfcBiiiipButton = new JButton("biiip");
        nfcBiiiipButton.setForeground(Color.WHITE);
        nfcBiiiipButton.setBackground(Color.DARK_GRAY);
        nfcBiiiipButton.addActionListener(e -> {
            cardBiped = true;
            theFSM.raiseAddMoney();
        });
        panel_1.add(nfcBiiiipButton);

        JLabel lblNfc = new JLabel("NFC");
        lblNfc.setForeground(Color.WHITE);
        lblNfc.setHorizontalAlignment(SwingConstants.CENTER);
        lblNfc.setBounds(541, 139, 41, 15);
        contentPane.add(lblNfc);

        JSeparator separator = new JSeparator();
        separator.setBounds(12, 292, 622, 15);
        contentPane.add(separator);

        JButton addCupButton = new JButton("Add cup");
        addCupButton.setForeground(Color.WHITE);
        addCupButton.setBackground(Color.DARK_GRAY);
        addCupButton.setBounds(45, 336, 96, 25);
        addCupButton.addActionListener(e -> {
            cupPlaced = true;
            updateMessageToUser();
        });
        contentPane.add(addCupButton);

        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File("./picts/vide2.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentPicture = new JLabel(new ImageIcon(myPicture));
        currentPicture.setBounds(175, 319, 286, 260);
        contentPane.add(currentPicture);

        takeCupButton = new JButton("Take cup");
        takeCupButton.setForeground(Color.WHITE);
        takeCupButton.setBackground(Color.DARK_GRAY);
        takeCupButton.setBounds(495, 336, 115, 25);
        takeCupButton.addActionListener(e -> {
            onDoResetMachineRaised();
            updateMessageToUser();
            BufferedImage emptyPicture = null;
            try {
                emptyPicture = ImageIO.read(new File("./picts/vide2.jpg"));
            } catch (IOException ee) {
                ee.printStackTrace();
            }
            currentPicture.setIcon(new ImageIcon(emptyPicture));
        });
        contentPane.add(takeCupButton);
        takeCupButton.setVisible(false);

        JPanel panel_2 = new JPanel();
        panel_2.setBackground(Color.DARK_GRAY);
        panel_2.setBounds(538, 217, 96, 33);
        contentPane.add(panel_2);

        cancelButton = new JButton("Cancel");
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setBackground(Color.DARK_GRAY);
        cancelButton.addActionListener(e -> {
            onDoResetMachineRaised();
        });
        panel_2.add(cancelButton);

        // listeners
        addCupButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                BufferedImage myPicture = null;
                try {
                    myPicture = ImageIO.read(new File("./picts/ownCup.jpg"));
                } catch (IOException ee) {
                    ee.printStackTrace();
                }
                currentPicture.setIcon(new ImageIcon(myPicture));
            }
        });
        updateMessageToUser();

        ActionListener doOnTimer = (e -> {
            simulateWaterTemp();
            simulateWaterPouring();
        });

        //timers
        Timer waterHeatTimer = new Timer(200, doOnTimer);
        waterHeatTimer.start();


    }

    public void setCurrentDrinkSelected(Drink currentDrinkSelected) {
        this.currentDrinkSelected = currentDrinkSelected;
        updateMessageToUser();
    }

    public void setSugarLevel(int sugarLevel) {
        this.currentSugarLevel = sugarLevel;
    }

    public void setCurrentSizeLevel(int currentSizeLevel) {
        this.currentSizeLevel = currentSizeLevel;
    }

    public void setCurrentTemperatureLevel(int currentTemperatureLevel) {
        this.currentTemperatureLevel = currentTemperatureLevel;
    }

    public void setCurrentMoneyInserted(float currentMoneyInserted) {
        this.currentMoneyInserted = Math.round(currentMoneyInserted * 100) / 100f;
        updateMessageToUser();
    }

    public float getCurrentMoneyInserted() {
        return currentMoneyInserted;
    }

    private void updateMessageToUser() {
        messagesToUser.setText("<html>Current amount: " + currentMoneyInserted + "€<br>" +
                "Card Biped: " + cardBiped + "<br>" +
                "Current Drink: " + ((currentDrinkSelected != null) ? currentDrinkSelected.getName() : "none") + "<br>" +
                ((cupPlaced) ? "Cup OK" : "No Cup") + "<br>" +
                "Water Temp: " + currentHeatedWaterTemp + "<br>" +
                "Giving back: " + moneyGivingBack + "€<br>" +
                "waiting...</html>");
    }

    private void simulateWaterTemp() {
        if (heaterState) {
            currentHeatedWaterTemp += 1;
        } else {
            count = (count + 1) % 10;
            if (count == 0 && currentHeatedWaterTemp > 20) {
                currentHeatedWaterTemp -= 1;
            }
        }
        if (currentHeatedWaterTemp >= Integer.parseInt(temperatureTable.get(temperatureSlider.getValue()).getText().substring(0,2))){
            heaterState = false;
            theFSM.raiseHeatReached();
        }
        updateMessageToUser();
    }

    private void simulateWaterPouring(){
        if(pouringState){
            System.out.println(currentWaterVolume);
            currentWaterVolume += 1;
        }
        if (currentWaterVolume >= this.getSize(sizeSlider.getValue())){
            pouringState = false;
            theFSM.raiseCupFilled();
            takeCupButton.setVisible(true);
        }
        updateMessageToUser();
    }

    private void checkPayment() {
        if (currentDrinkSelected != null) {
            if (currentMoneyInserted >= currentDrinkSelected.getPrice()) {
                setCurrentMoneyInserted(currentMoneyInserted - currentDrinkSelected.getPrice());
                preparationInProgress(true);
                theFSM.raisePaymentValidate();
            } else if (cardBiped) {
                preparationInProgress(true);
                theFSM.raisePaymentValidate();
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
    }

    @Override
    public void onDoResetMoneyRaised() {
        if (currentMoneyInserted != 0) {
            moneyGivingBack = Math.round(currentMoneyInserted * 100) / 100f;
        }
        setCurrentMoneyInserted(0);
    }

    @Override
    public void onDoResetMachineRaised() {
        onDoResetMoneyRaised();
        takeCupButton.setVisible(false);
        cardBiped = false;
        currentDrinkSelected = null;
        preparationInProgress(false);
        progressBar.setValue(0);
        sugarSlider.setValue(1);
        sizeSlider.setValue(1);
        temperatureSlider.setValue(2);

    }

    @Override
    public void onDoCleaningRaised() {

    }

    @Override
    public void onGrindBeansRaised() {

    }

    @Override
    public void onTakeTeaBagRaised() {

    }

    @Override
    public void onTakeOffTeaBagRaised() {
        //TeaBagRaised
    }

    @Override
    public void onTakeCoffeePodRaised() {
        //TeaCoffeeRaised
    }

    @Override
    public void onHeatWaterRaised() {
        heaterState = true;
    }

    @Override
    public void onPutCupRaised() {
        if(!cupPlaced){
            cupPlaced = true;
            BufferedImage myPicture = null;
            try {
                myPicture = ImageIO.read(new File("./picts/gobeletPolluant.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            currentPicture.setIcon(new ImageIcon(myPicture));
        }
    }

    @Override
    public void onAddSugarRaised() {

    }

    @Override
    public void onPourWaterRaised() {
        if(cupPlaced){
            pouringState = true;
        }
    }

    @Override
    public void onGetDrinkRaised() {

    }

    @Override
    public void onPutBeansRaised() {

    }

    @Override
    public void onCheckPaymentRaised() {
        checkPayment();
    }

    public int getTemperature(int sliderValue){
        switch (sliderValue) {
            case 0: return 20;
            case 1: return 35;
            case 2: return 60;
            case 3: return 85;
            default: return 0;
        }
    }

    public int getSize(int sliderValue){
        switch(sliderValue){
            case 0: return 10;
            case 1: return 25;
            case 2: return 50;
            default: return 0;
        }
    }

}
