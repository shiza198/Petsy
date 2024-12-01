import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import javax.swing.*;

// Abstract Pet class
abstract class AbstractPet implements Serializable {
    private int id;
    private String name;
    private String type;
    private String owner;
    private double price; // New attribute for price

    public AbstractPet(int id, String name, String type, String owner, double price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.owner = owner;
        this.price = price;
    }

    public void updateDetails(String name, String type, String owner) {
        this.name = name;
        this.type = type;
        this.owner = owner;
    }

    // Getter and setter for price
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = price;
    }

    @Override
    public String toString() {
        return "Pet ID: " + id + ", Name: " + name + ", Type: " + type + 
               ", Owner: " + owner + ", Price: $" + price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getOwner() {
        return owner;
    }

    public abstract String getDetails();
}


// Concrete Pet class extending AbstractPet
class Pet extends AbstractPet {
    public Pet(int id, String name, String type, String owner, double price) {
        super(id, name, type, owner, price);
    }

    @Override
    public String getDetails() {
        return "Pet details: " + this.toString();
    }
}


class Customer implements Serializable {
    private int id;
    private String name;
    private String email;
    private String phone;

    public Customer(int id, String name, String email, String phone) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public void updateDetails(String name, String email, String phone) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Customer ID: " + id + ", Name: " + name + ", Email: " + email + ", Phone: " + phone;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return email != null && pattern.matcher(email).matches();
    }
}


// Class Bill
class Bill implements Serializable {
    private int id;
    private Customer customer;
    private Pet pet; // Reference to the pet
    private int quantity; // New field for quantity
    private double amount;
    private String date;

    public Bill(int id, Customer customer, Pet pet, int quantity, String date) {
        this.id = id;
        this.customer = customer;
        this.pet = pet;
        this.quantity = quantity;
        this.amount = pet.getPrice() * quantity;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Pet getPet() {
        return pet;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDate() {
        return date;
    }

    public double calculateTotalAmount() {
        return amount; // Total amount already calculated during initialization
    }

    @Override
    public String toString() {
        return "Bill ID: " + id + ", Customer: " + customer + ", Pet: " + pet.getName() +
               ", Quantity: " + quantity + ", Amount: $" + calculateTotalAmount() + ", Date: " + date;
    }
}


// Main class for Pet Management System
class PetManagementSystem extends JFrame {
    public static void main(String[] args) {
        new PetManagementSystem();
    }

    private ArrayList<Pet> pets = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Bill> bills = new ArrayList<>();

    public PetManagementSystem() {
        // Setting up the JFrame
        setTitle("Pet Shop Management System");
        setSize(750, 550 );
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout()); // Use BorderLayout for better panel organization

        add(createSidePanel(), BorderLayout.WEST); // Use createSidePanel() here
        showWelcomePage(); // Show the welcome page initially

        // Load data from files
        loadData();

        setVisible(true);
    }

    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS)); // Vertical layout for buttons
        sidePanel.setBackground(new Color(210, 2, 77)); // Cherry Red background
        sidePanel.setPreferredSize(new Dimension(200, 0)); // Set preferred width (200px), height adjusts automatically

        // Add logo to the side panel
        JLabel logoLabel = new JLabel();
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment
        ImageIcon logoIcon = new ImageIcon("c:\\Users\\saadi\\Downloads\\petsy logo.png"); // Replace with your logo's path
        Image scaledImage = logoIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH); // Scale logo
        logoLabel.setIcon(new ImageIcon(scaledImage));

        // Create buttons for navigation
        JButton homeButton = createSideButton(" Home");
        JButton managePetsButton = createSideButton(" Manage Pets");
        JButton manageCustomersButton = createSideButton(" Manage Customers");
        JButton manageBillingButton = createSideButton(" Manage Billing");

        // Add action listeners for navigation
        homeButton.addActionListener(e -> showWelcomePage());
        managePetsButton.addActionListener(e -> showManagePetsPage());
        manageCustomersButton.addActionListener(e -> showManageCustomersPage());
        manageBillingButton.addActionListener(e -> showManageBillingPage());

        // Add buttons to the side panel
        sidePanel.add(Box.createVerticalStrut(80)); // Add spacing above logo
        sidePanel.add(logoLabel);
        sidePanel.add(Box.createVerticalStrut(30)); // Add spacing at the top
        sidePanel.add(homeButton); // Add Home button
        sidePanel.add(Box.createVerticalStrut(10)); // Spacing between buttons
        sidePanel.add(managePetsButton);
        sidePanel.add(Box.createVerticalStrut(10)); // Spacing between buttons
        sidePanel.add(manageCustomersButton);
        sidePanel.add(Box.createVerticalStrut(10)); // Spacing between buttons
        sidePanel.add(manageBillingButton);
        sidePanel.add(Box.createVerticalGlue()); // Push components downwards to center

        return sidePanel;
    }

    private JButton createSideButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFocusPainted(false); // Remove focus border when clicked
        button.setContentAreaFilled(false); // Remove the button's background
        button.setBorderPainted(false); // Remove the button's border
        button.setOpaque(false); // Ensure transparency
        button.setForeground(Color.WHITE); // Set the text color
        button.setFont(new Font("Arial", Font.BOLD, 14)); // Set the font style and size
        button.setPreferredSize(new Dimension(200, 40)); // Preferred size for consistent layout
        return button;
    }

    private void showWelcomePage() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE); // Set background color

        // Add image
        JLabel imageLabel = new JLabel();
        ImageIcon mainImageIcon = new ImageIcon("c:\\Users\\saadi\\Downloads\\welcome.png"); // Path to your image
        Image mainScaledImage = mainImageIcon.getImage().getScaledInstance(170, 170, Image.SCALE_SMOOTH); // Adjust size as needed
        imageLabel.setIcon(new ImageIcon(mainScaledImage));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align image

        // Add welcome text
        JLabel welcomeLabel = new JLabel("Welcome to the Pet Shop!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align text

        // Add "About Us" button
        JButton aboutUsButton = new JButton("About Us");
        aboutUsButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the button
        aboutUsButton.setFocusPainted(false); // Remove focus border when clicked
        aboutUsButton.setContentAreaFilled(false); // Remove button's background
        aboutUsButton.setOpaque(true); // Ensure custom background is visible
        aboutUsButton.setBackground(new Color(210, 2, 77)); // Cherry Red background
        aboutUsButton.setForeground(Color.WHITE); // White text
        aboutUsButton.setFont(new Font("Arial", Font.BOLD, 14)); // Font style and size
        aboutUsButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // Padding for better appearance
        aboutUsButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 2, 77), 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        )); // Add rounded corners

        // Add ActionListener to display a message
        aboutUsButton.addActionListener(e -> {
            // Custom panel for the dialog
            JPanel messagePanel = new JPanel();
            messagePanel.setBackground(Color.WHITE); // Set the dialog's background color
            messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));

            // Add centered text with HTML styling
            JLabel aboutUsMessage = new JLabel("<html><div style='text-align: center;'>Welcome to our Pet Shop!<br>We care about your pets and provide the best services.</div></html>");
            aboutUsMessage.setFont(new Font("Arial", Font.PLAIN, 14));
            aboutUsMessage.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the component in the panel
            messagePanel.add(aboutUsMessage);

            // Create custom OK button
            JButton okButton = new JButton("OK");
            okButton.setFocusPainted(false); // Remove focus border when clicked
            okButton.setContentAreaFilled(true); // Ensure background is visible
            okButton.setOpaque(true);
            okButton.setBackground(new Color(210, 2, 77)); // Cherry red background
            okButton.setForeground(Color.WHITE); // White text
            okButton.setFont(new Font("Arial", Font.BOLD, 14)); // Bold font
            okButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // Padding for better appearance
            okButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Dialog creation
            JDialog aboutUsDialog = new JDialog(this, "About Us", true); // Modal dialog
            aboutUsDialog.setSize(350, 200);
            aboutUsDialog.setResizable(false);
            aboutUsDialog.setLayout(new BoxLayout(aboutUsDialog.getContentPane(), BoxLayout.Y_AXIS));
            aboutUsDialog.getContentPane().setBackground(Color.WHITE); // Set dialog background

            // Add components to dialog
            aboutUsDialog.add(Box.createVerticalStrut(20)); // Add spacing above text
            aboutUsDialog.add(messagePanel); // Add message
            aboutUsDialog.add(Box.createVerticalStrut(20)); // Add spacing above button
            aboutUsDialog.add(okButton); // Add OK button
            aboutUsDialog.add(Box.createVerticalStrut(10)); // Add spacing below button

            // Add action to close the dialog
            okButton.addActionListener(event -> aboutUsDialog.dispose());

            aboutUsDialog.setLocationRelativeTo(this); // Center the dialog relative to the parent frame
            aboutUsDialog.setVisible(true);
        });


        // Add components to mainPanel
        mainPanel.add(Box.createVerticalStrut(80)); // Spacing above image
        mainPanel.add(imageLabel); // Add image
        mainPanel.add(Box.createVerticalStrut(10)); // Spacing between image and welcome text
        mainPanel.add(welcomeLabel); // Add welcome text
        mainPanel.add(Box.createVerticalStrut(30)); // Spacing before the "About Us" button
        mainPanel.add(aboutUsButton); // Add the "About Us" button
        mainPanel.add(Box.createVerticalGlue()); // Push components to vertical center

        // Update the main panel
        this.getContentPane().removeAll();
        this.add(createSidePanel(), BorderLayout.WEST);
        this.add(mainPanel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    private void showManagePetsPage() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Adding padding to the components

        // Labels and text fields for pet details
        JLabel idLabel = new JLabel("Pet ID:");
        JTextField idField = new JTextField(20);
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);
        JLabel typeLabel = new JLabel("Type:");

        // Create JComboBox for pet types
        String[] petTypes = {"Dog", "Cat", "Bird"};
        JComboBox<String> typeComboBox = new JComboBox<>(petTypes); // Dropdown for pet types

        JLabel ownerLabel = new JLabel("Owner:");
        JTextField ownerField = new JTextField(20);
        JLabel priceLabel = new JLabel("Price:"); // New field for Price
        JTextField priceField = new JTextField(20); // New text field for Price

        JTextArea petsTextArea = new JTextArea(10, 40);
        petsTextArea.setEditable(false);
        petsTextArea.setText(getPetsData());

        // Arrange components in GridBagLayout
        gbc.gridx = 0; gbc.gridy = 0; mainPanel.add(idLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; mainPanel.add(idField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; mainPanel.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; mainPanel.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; mainPanel.add(typeLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; mainPanel.add(typeComboBox, gbc); // Add JComboBox for type
        gbc.gridx = 0; gbc.gridy = 3; mainPanel.add(ownerLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; mainPanel.add(ownerField, gbc);
        gbc.gridx = 0; gbc.gridy = 4; mainPanel.add(priceLabel, gbc); // Add price label
        gbc.gridx = 1; gbc.gridy = 4; mainPanel.add(priceField, gbc);
        // Add button to save pet
        JButton addPetButton = new JButton("Add Pet");
        addPetButton.setFocusPainted(false); // Remove focus border
        addPetButton.setContentAreaFilled(false); // Remove default button background
        addPetButton.setOpaque(true); // Enable custom background
        addPetButton.setBackground(new Color(210, 2, 77)); // Set cherry red background
        addPetButton.setForeground(Color.WHITE); // Set text color to white
        addPetButton.setFont(new Font("Arial", Font.BOLD, 14)); // Set font style and size
        addPetButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 2, 77), 2), // Border with cherry red
                BorderFactory.createEmptyBorder(5, 10, 5, 10) // Padding inside the border
        ));

        addPetButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                if (!isPetIdUnique(id)) {
                    JOptionPane.showMessageDialog(this, "Pet ID already exists! Please choose a unique ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Do not proceed if ID is not unique
                }

                String name = nameField.getText();
                String type = (String) typeComboBox.getSelectedItem(); // Get selected type from JComboBox
                String owner = ownerField.getText();
                double price = Double.parseDouble(priceField.getText()); // Get price from input

                Pet pet = new Pet(id, name, type, owner, price);
                pets.add(pet);
                updateTextArea(petsTextArea, getPetsData());
                saveData();
                clearPetFields(idField, nameField, typeComboBox, ownerField, priceField); // Clear the input fields
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input data.");
            }
        });


        // Add buttons to main panel
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; mainPanel.add(addPetButton, gbc);
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2; mainPanel.add(new JScrollPane(petsTextArea), gbc);

        // Update the main panel
        this.getContentPane().removeAll();
        this.add(createSidePanel(), BorderLayout.WEST);
        this.add(mainPanel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }
    private void clearPetFields(JTextField idField, JTextField nameField, JComboBox<String> typeComboBox, JTextField ownerField, JTextField priceField) {
        idField.setText("");
        nameField.setText("");
        typeComboBox.setSelectedIndex(0); // Reset the JComboBox to the first item
        ownerField.setText("");
        priceField.setText("");
    }

    private boolean isPetIdUnique(int id) {
        for (Pet pet : pets) {
            if (pet.getId() == id) {
                return false; // Pet ID already exists
            }
        }
        return true; // Pet ID is unique
    }

    private String getPetsData() {
        StringBuilder data = new StringBuilder("Current Pets:\n");
        for (Pet pet : pets) {
            data.append(pet.toString()).append("\n");
        }
        return data.toString();
    }

    private void showManageCustomersPage() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Adding padding to the components

        // Labels and text fields for customer details
        JLabel idLabel = new JLabel("Customer ID:");
        JTextField idField = new JTextField(20);
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);
        JLabel phoneLabel = new JLabel("Phone:");
        JTextField phoneField = new JTextField(20);

        JTextArea customersTextArea = new JTextArea(10, 40);
        customersTextArea.setEditable(false);
        customersTextArea.setText(getCustomersData());

        // Arrange components in GridBagLayout
        gbc.gridx = 0; gbc.gridy = 0; mainPanel.add(idLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; mainPanel.add(idField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; mainPanel.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; mainPanel.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; mainPanel.add(emailLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; mainPanel.add(emailField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; mainPanel.add(phoneLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; mainPanel.add(phoneField, gbc);

        // Add button to save customer
        JButton addCustomerButton = new JButton("Add Customer");
        addCustomerButton.setFocusPainted(false); // Remove focus border
        addCustomerButton.setContentAreaFilled(false); // Remove default button background
        addCustomerButton.setOpaque(true); // Enable custom background
        addCustomerButton.setBackground(new Color(210, 2, 77)); // Set cherry red background
        addCustomerButton.setForeground(Color.WHITE); // Set text color to white
        addCustomerButton.setFont(new Font("Arial", Font.BOLD, 14)); // Set font style and size
        addCustomerButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 2, 77), 2), // Border with cherry red
                BorderFactory.createEmptyBorder(5, 10, 5, 10) // Padding inside the border
        ));

        addCustomerButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                if (!isCustomerIdUnique(id)) {
                    JOptionPane.showMessageDialog(this, "Customer ID already exists! Please choose a unique ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Do not proceed if ID is not unique
                }

                String name = nameField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();

                if (!Customer.isValidEmail(email)) {
                    JOptionPane.showMessageDialog(this, "Invalid email format. Please enter a valid email.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Customer customer = new Customer(id, name, email, phone);
                customers.add(customer);
                updateTextArea(customersTextArea, getCustomersData());
                saveData();
                clearCustomerFields(idField, nameField, emailField, phoneField);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        // Add buttons to main panel
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; mainPanel.add(addCustomerButton, gbc);
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2; mainPanel.add(new JScrollPane(customersTextArea), gbc);

        // Update the main panel
        this.getContentPane().removeAll();
        this.add(createSidePanel(), BorderLayout.WEST);
        this.add(mainPanel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    // Method to check if Customer ID is unique
    private boolean isCustomerIdUnique(int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                return false; // Customer ID already exists
            }
        }
        return true; // Customer ID is unique
    }

    private void clearCustomerFields(JTextField idField, JTextField nameField, JTextField emailField, JTextField phoneField) {
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
    }

    private String getCustomersData() {
        StringBuilder data = new StringBuilder("Current Customers:\n");
        for (Customer customer : customers) {
            data.append(customer.toString()).append("\n");
        }
        return data.toString();
    }

    private void showManageBillingPage() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Adding padding to the components
    
        // Labels and text fields for billing details
        JLabel billIdLabel = new JLabel("Bill ID:");
        JTextField billIdField = new JTextField(20);
        JLabel customerIdLabel = new JLabel("Customer ID:");
        JTextField customerIdField = new JTextField(20);
        JLabel petIdLabel = new JLabel("Pet ID:"); // New field for Pet ID
        JTextField petIdField = new JTextField(20);
        JLabel quantityLabel = new JLabel("Quantity:"); // New field for Quantity
        JTextField quantityField = new JTextField(20);
        JLabel dateLabel = new JLabel("Date:");
        JTextField dateField = new JTextField(20);
    
        JTextArea billingTextArea = new JTextArea(10, 40);
        billingTextArea.setEditable(false);
        billingTextArea.setText(getBillingData());
    
        // Arrange components in GridBagLayout
        gbc.gridx = 0; gbc.gridy = 0; mainPanel.add(billIdLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; mainPanel.add(billIdField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; mainPanel.add(customerIdLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; mainPanel.add(customerIdField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; mainPanel.add(petIdLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; mainPanel.add(petIdField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; mainPanel.add(quantityLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; mainPanel.add(quantityField, gbc);
        gbc.gridx = 0; gbc.gridy = 4; mainPanel.add(dateLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 4; mainPanel.add(dateField, gbc);
    
     
        // Add button to save billing information
        JButton addBillButton = new JButton("Add Bill");
        addBillButton.setFocusPainted(false); // Remove focus border
        addBillButton.setContentAreaFilled(true); // Use custom button background
        addBillButton.setOpaque(true); // Enable custom background
        addBillButton.setBackground(new Color(210, 2, 77)); // Cherry red background
        addBillButton.setForeground(Color.WHITE); // White text
        addBillButton.setFont(new Font("Arial", Font.BOLD, 14)); // Bold font
        addBillButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Remove any visible border
        addBillButton.addActionListener(e -> {
            try {
                int billId = Integer.parseInt(billIdField.getText());
                if (!isBillIdUnique(billId)) {
                    JOptionPane.showMessageDialog(this, "Bill ID already exists! Please choose a unique ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Do not proceed if ID is not unique
                }

                int customerId = Integer.parseInt(customerIdField.getText());
                int petId = Integer.parseInt(petIdField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                String date = dateField.getText();

                Customer customer = findCustomerById(customerId); // Find customer by ID
                Pet pet = findPetById(petId); // Find pet by ID
                if (customer != null && pet != null) {
                    Bill bill = new Bill(billId, customer, pet, quantity, date);
                    bills.add(bill);
                    updateTextArea(billingTextArea, getBillingData());
                    saveData();
                    clearBillingFields(billIdField, customerIdField, petIdField, quantityField, dateField);
                } else {
                    JOptionPane.showMessageDialog(this, "Customer or pet not found.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input data.");
            }
        });

        // Add button to show receipt
        JButton showReceiptButton = new JButton("Show Receipt");
        showReceiptButton.setFocusPainted(false); // Remove focus border
        showReceiptButton.setContentAreaFilled(true); // Use custom button background
        showReceiptButton.setOpaque(true); // Enable custom background
        showReceiptButton.setBackground(new Color(210, 2, 77)); // Cherry red background
        showReceiptButton.setForeground(Color.WHITE); // White text
        showReceiptButton.setFont(new Font("Arial", Font.BOLD, 14)); // Bold font
        showReceiptButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Remove any visible border
        showReceiptButton.addActionListener(e -> {
            String billIdText = billIdField.getText();
        
            if (billIdText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a Bill ID.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            try {
                int billId = Integer.parseInt(billIdText);
                Bill bill = findBillById(billId);
                if (bill != null) {
                    JFrame receiptFrame = new JFrame("Bill Receipt");
                    receiptFrame.setSize(300, 350);
                    receiptFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    receiptFrame.setBackground(Color.WHITE);
        
                    JPanel receiptPanel = new JPanel();
                    receiptPanel.setBackground(Color.WHITE);
                    receiptPanel.setLayout(new BoxLayout(receiptPanel, BoxLayout.Y_AXIS));
        
                    String receiptText = "<html><div style='text-align: center;'>" +
                            "<h2 style='margin-bottom: 15px;'>Bill Receipt</h2>" +
                            "<p style='margin-bottom: 10px;'>Bill ID: " + bill.getId() + "</p>" +
                            "<p style='margin-bottom: 10px;'>Customer Name: " + bill.getCustomer().getName() + "</p>" +
                            "<p style='margin-bottom: 10px;'>Pet Name: " + bill.getPet().getName() + "</p>" +
                            "<p style='margin-bottom: 10px;'>Quantity: " + bill.getQuantity() + "</p>" +
                            "<p style='margin-bottom: 10px;'>Date: " + bill.getDate() + "</p>" +
                            "<p style='margin-bottom: 10px;'>Amount: $" + bill.calculateTotalAmount() + "</p>" +
                            "</div></html>";
        
                    JLabel receiptLabel = new JLabel(receiptText, SwingConstants.CENTER);
                    receiptLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    receiptPanel.add(receiptLabel);
        
                    receiptPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add space below receipt text
        
                    JButton closeButton = new JButton("Close");
                    closeButton.setFocusPainted(false);
                    closeButton.setContentAreaFilled(true);
                    closeButton.setOpaque(true);
                    closeButton.setBackground(new Color(210, 2, 77)); // Cherry red background
                    closeButton.setForeground(Color.WHITE); // White text
                    closeButton.setFont(new Font("Arial", Font.BOLD, 14)); // Bold font
                    closeButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // No border
                    closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                    closeButton.addActionListener(event -> receiptFrame.dispose());
                    receiptPanel.add(closeButton);
        
                    receiptFrame.add(receiptPanel);
                    receiptFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Bill not found.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Bill ID. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        

        // Create a panel to hold both buttons side by side
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(addBillButton);
        buttonPanel.add(showReceiptButton);

        // Add the button panel to the main panel
        gbc.gridx = 0; 
        gbc.gridy = 5; 
        gbc.gridwidth = 2; 
        mainPanel.add(buttonPanel, gbc);

        gbc.gridx = 0; gbc.gridy = 7; mainPanel.add(new JScrollPane(billingTextArea), gbc);
    
        // Update the main panel
        this.getContentPane().removeAll();
        this.add(createSidePanel(), BorderLayout.WEST);
        this.add(mainPanel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }
    
    private Bill findBillById(int id) {
        for (Bill bill : bills) {
            if (bill.getId() == id) {
                return bill;
            }
        }
        return null; // Return null if bill not found
    }

    // Method to check if Bill ID is unique
    private boolean isBillIdUnique(int id) {
        for (Bill bill : bills) {
            if (bill.getId() == id) {
                return false; // Bill ID already exists
            }
        }
        return true; // Bill ID is unique
    }

    private void clearBillingFields(JTextField billIdField, JTextField customerIdField, JTextField petIdField, JTextField quantityField, JTextField dateField) {
        billIdField.setText("");
        customerIdField.setText("");
        petIdField.setText("");
        quantityField.setText("");
        dateField.setText("");
    }

    private String getBillingData() {
        StringBuilder data = new StringBuilder("Current Bills:\n");
        for (Bill bill : bills) {
            data.append(bill.toString()).append("\n");
        }
        return data.toString();
    }

    private void updateTextArea(JTextArea textArea, String data) {
        textArea.setText(data);
    }

    private Pet findPetById(int id) {
        for (Pet pet : pets) {
            if (pet.getId() == id) {
                return pet;
            }
        }
        return null; // Return null if pet not found
    }

    private Customer findCustomerById(int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                return customer;
            }
        }
        return null; // Return null if customer not found
    }

    private void saveData() {
        try (ObjectOutputStream petStream = new ObjectOutputStream(new FileOutputStream("pets.ser"));
             ObjectOutputStream customerStream = new ObjectOutputStream(new FileOutputStream("customers.ser"));
             ObjectOutputStream billStream = new ObjectOutputStream(new FileOutputStream("bills.ser"))) {
            petStream.writeObject(pets);
            customerStream.writeObject(customers);
            billStream.writeObject(bills);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage());
        }
    }

    private void loadData() {
        try {
            File petFile = new File("pets.ser");
            if (petFile.exists()) {
                try (ObjectInputStream petStream = new ObjectInputStream(new FileInputStream(petFile))) {
                    pets = (ArrayList<Pet>) petStream.readObject();
                }
            }

            File customerFile = new File("customers.ser");
            if (customerFile.exists()) {
                try (ObjectInputStream customerStream = new ObjectInputStream(new FileInputStream(customerFile))) {
                    customers = (ArrayList<Customer>) customerStream.readObject();
                }
            }

            File billFile = new File("bills.ser");
            if (billFile.exists()) {
                try (ObjectInputStream billStream = new ObjectInputStream(new FileInputStream(billFile))) {
                    bills = (ArrayList<Bill>) billStream.readObject();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}