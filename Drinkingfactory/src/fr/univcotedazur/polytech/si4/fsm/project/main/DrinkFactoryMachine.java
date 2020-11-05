package fr.univcotedazur.polytech.si4.fsm.project.main;

import drinkingfactory.TimerService;
import drinkingfactory.drinkingfactory.DrinkingfactoryStatemachine;
import drinkingfactory.drinkingfactory.IDrinkingfactoryStatemachine;
import drinks.Drink;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DrinkFactoryMachine extends JFrame implements IDrinkingfactoryStatemachine.SCInterfaceListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2030629304432075314L;
	private JPanel contentPane;
	private JLabel messagesToUser;
	private DrinkingfactoryStatemachine theFSM;

	private Drink currentDrinkSelected = Drink.Coffee;
	private int currentSugarLevel;
	private int currentSizeLevel;
	private int currentTemperatureLevel;
	private float currentMoneyInserted = 0;
	
	
	/**
	 * @wbp.nonvisual location=311,475
	 */
	private final ImageIcon imageIcon = new ImageIcon();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DrinkFactoryMachine frame = new DrinkFactoryMachine();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
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

		JButton coffeeButton = new JButton("Coffee");
		coffeeButton.setForeground(Color.WHITE);
		coffeeButton.setBackground(Color.DARK_GRAY);
		coffeeButton.setBounds(12, 34, 96, 25);
		coffeeButton.addActionListener(e -> setCurrentDrinkSelected(Drink.Coffee));
		contentPane.add(coffeeButton);

		JButton espressoButton = new JButton("Espresso");
		espressoButton.setForeground(Color.WHITE);
		espressoButton.setBackground(Color.DARK_GRAY);
		espressoButton.setBounds(12, 71, 96, 25);
		espressoButton.addActionListener(e -> setCurrentDrinkSelected(Drink.Espresso));
		contentPane.add(espressoButton);

		JButton teaButton = new JButton("Tea");
		teaButton.setForeground(Color.WHITE);
		teaButton.setBackground(Color.DARK_GRAY);
		teaButton.setBounds(12, 108, 96, 25);
		teaButton.addActionListener(e -> setCurrentDrinkSelected(Drink.Tea));
		contentPane.add(teaButton);

		JButton soupButton = new JButton("Soup");
		soupButton.setForeground(Color.WHITE);
		soupButton.setBackground(Color.DARK_GRAY);
		soupButton.setBounds(12, 145, 96, 25);
		soupButton.addActionListener(e -> setCurrentDrinkSelected(Drink.Soup));
		contentPane.add(soupButton);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setValue(0);
		progressBar.setForeground(Color.LIGHT_GRAY);
		progressBar.setBackground(Color.DARK_GRAY);
		progressBar.setBounds(12, 254, 622, 26);
		contentPane.add(progressBar);

		JSlider sugarSlider = new JSlider();
		sugarSlider.setValue(1);
		sugarSlider.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		sugarSlider.setBackground(Color.DARK_GRAY);
		sugarSlider.setForeground(Color.WHITE);
		sugarSlider.setPaintTicks(true);
		sugarSlider.setMinorTickSpacing(1);
		sugarSlider.setMajorTickSpacing(1);
		sugarSlider.setMaximum(4);
		sugarSlider.setBounds(301, 51, 200, 36);
		sugarSlider.addChangeListener(e -> setSugarLevel(sugarSlider.getValue()));
		contentPane.add(sugarSlider);

		JSlider sizeSlider = new JSlider();
		sizeSlider.setPaintTicks(true);
		sizeSlider.setValue(1);
		sizeSlider.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		sizeSlider.setBackground(Color.DARK_GRAY);
		sizeSlider.setForeground(Color.WHITE);
		sizeSlider.setMinorTickSpacing(1);
		sizeSlider.setMaximum(2);
		sizeSlider.setMajorTickSpacing(1);
		sizeSlider.setBounds(301, 125, 200, 36);
		sizeSlider.addChangeListener(e -> setCurrentSizeLevel(sizeSlider.getValue()));
		contentPane.add(sizeSlider);

		JSlider temperatureSlider = new JSlider();
		temperatureSlider.setPaintLabels(true);
		temperatureSlider.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		temperatureSlider.setValue(2);
		temperatureSlider.setBackground(Color.DARK_GRAY);
		temperatureSlider.setForeground(Color.WHITE);
		temperatureSlider.setPaintTicks(true);
		temperatureSlider.setMajorTickSpacing(1);
		temperatureSlider.setMaximum(3);
		temperatureSlider.setBounds(301, 188, 200, 54);

		Hashtable<Integer, JLabel> temperatureTable = new Hashtable<>();
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

		JButton icedTeaButton = new JButton("Iced Tea");
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

		JButton money50centsButton = new JButton("0.50 €");
		money50centsButton.setForeground(Color.WHITE);
		money50centsButton.setBackground(Color.DARK_GRAY);
		money50centsButton.addActionListener(e -> setCurrentMoneyInserted(getCurrentMoneyInserted() + 0.50f));
		panel.add(money50centsButton);

		JButton money25centsButton = new JButton("0.25 €");
		money25centsButton.setForeground(Color.WHITE);
		money25centsButton.setBackground(Color.DARK_GRAY);
		money25centsButton.addActionListener(e -> setCurrentMoneyInserted(getCurrentMoneyInserted() + 0.25f));
		panel.add(money25centsButton);

		JButton money10centsButton = new JButton("0.10 €");
		money10centsButton.setForeground(Color.WHITE);
		money10centsButton.setBackground(Color.DARK_GRAY);
		money10centsButton.addActionListener(e -> setCurrentMoneyInserted(getCurrentMoneyInserted() + 0.10f));
		panel.add(money10centsButton);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.DARK_GRAY);
		panel_1.setBounds(538, 154, 96, 40);
		contentPane.add(panel_1);

		JButton nfcBiiiipButton = new JButton("biiip");
		nfcBiiiipButton.setForeground(Color.WHITE);
		nfcBiiiipButton.setBackground(Color.DARK_GRAY);
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
		contentPane.add(addCupButton);

		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("./picts/vide2.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JLabel labelForPictures = new JLabel(new ImageIcon(myPicture));
		labelForPictures.setBounds(175, 319, 286, 260);
		contentPane.add(labelForPictures);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.DARK_GRAY);
		panel_2.setBounds(538, 217, 96, 33);
		contentPane.add(panel_2);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setForeground(Color.WHITE);
		cancelButton.setBackground(Color.DARK_GRAY);
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
				labelForPictures.setIcon(new ImageIcon(myPicture));
			}
		});
		updateMessageToUser();
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
		this.currentMoneyInserted = Math.round(currentMoneyInserted*100)/100f;
		updateMessageToUser();
	}

	public float getCurrentMoneyInserted() {
		return currentMoneyInserted;
	}

	private void updateMessageToUser() {
		messagesToUser.setText("<html>Current amount: " + currentMoneyInserted + "€<br>" +
				"Current Drink: " + currentDrinkSelected.getName() + "<br>" +
				"waiting...</html>");
	}

	@Override
	public void onDoResetMoneyRaised() {

	}

	@Override
	public void onDoResetDrinkSettingRaised() {

	}

	@Override
	public void onDoResetMachineRaised() {

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

	}

	@Override
	public void onTakeCoffeePodRaised() {

	}

	@Override
	public void onHeatWaterRaised() {

	}

	@Override
	public void onPutCupRaised() {

	}

	@Override
	public void onAddSugarRaised() {

	}

	@Override
	public void onPourWaterRaised() {

	}

	@Override
	public void onGetDrinkRaised() {

	}

	@Override
	public void onPutBeansRaised() {

	}
}
