import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;


// Abstract Pet class
abstract class AbstractPet implements Serializable {
    private int id;
    private String name;
    private String type;
    private String owner;

    public AbstractPet(int id, String name, String type, String owner) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.owner = owner;
    }

    public void updateDetails(String name, String type, String owner) {
        this.name = name;
        this.type = type;
        this.owner = owner;
    }

    public String toString() {
        return "Pet ID: " + id + ", Name: " + name + ", Type: " + type + ", Owner: " + owner;
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
    public Pet(int id, String name, String type, String owner) {
        super(id, name, type, owner);
    }

    @Override
    public String getDetails() {
        return "Pet details: " + this.toString();
    }
}

// Customer class
class Customer implements Serializable {
    private int id;
    private String name;
    private String email;
    private String phone;

    public Customer(int id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public void updateDetails(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String toString() {
        return "Customer ID: " + id + ", Name: " + name + ", Email: " + email + ", Phone: " + phone;
    }

    public int getId() {
        return id;
    }
}

// Class Bill
class Bill implements Serializable {
    private int id;
    private Customer customer;
    private double amount;
    private String date;

    public Bill(int id, Customer customer, double amount, String date) {
        this.id = id;
        this.customer = customer;
        this.amount = amount;
        this.date = date;
    }

    public void updateDetails(double amount, String date) {
        this.amount = amount;
        this.date = date;
    }

    public String toString() {
        return "Bill ID: " + id + ", Customer: " + customer + ", Amount: $" + amount + ", Date: " + date;
    }
}

class PetManagementSystem extends JFrame {
    private ArrayList<Pet> pets = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Bill> bills = new ArrayList<>();

    // The panel where content will be switched
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public static void main(String[] args) {
        System.out.println("Launching PetManagementSystem...");
        new PetManagementSystem();
    }
    
    public PetManagementSystem() {
        loadData();
        setTitle("Pet Shop Management System");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        // Create the side panel with navigation buttons
        JPanel sidePanel = createSidePanel();
        add(sidePanel, BorderLayout.WEST);

        // Add the main panel to the frame
        add(mainPanel, BorderLayout.CENTER);

        // Add different pages as cards
        mainPanel.add(createWelcomePage(), "Welcome");
        mainPanel.add(createManagePetsPage(), "Manage Pets");
        mainPanel.add(createManageCustomersPage(), "Manage Customers");
        mainPanel.add(createManageBillingPage(), "Manage Billing");

        setVisible(true);
        loadData();
    }

    private JPanel createWelcomePage() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
        // Add the image above the welcome text
    ImageIcon welcomeImage = new ImageIcon("C:\\Users\\saadi\\Downloads\\welcome.png"); // Update with the correct path to your image

    // Scale the image
    Image mainScaledImage = welcomeImage.getImage().getScaledInstance(170, 170, Image.SCALE_SMOOTH); // Adjust size as needed
    welcomeImage = new ImageIcon(mainScaledImage); // Update the ImageIcon with the scaled image

    // Create the JLabel for the image
    JLabel imageLabel = new JLabel(welcomeImage);
    imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align image

        // Create the welcome label
        JLabel welcomeLabel = new JLabel("Welcome to the Pet Shop!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setForeground(Color.BLACK); // Ensure the label text is visible
    
        // Create the About Us button
        JButton aboutUsButton = new JButton("About Us");
        aboutUsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        aboutUsButton.setBackground(new Color(210, 2, 77)); // Set the button background color
        aboutUsButton.setForeground(Color.WHITE); // Button text color
        aboutUsButton.setFont(new Font("Arial", Font.PLAIN, 16));
        aboutUsButton.setFocusPainted(false); // Remove focus border
        aboutUsButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Welcome to our Pet Shop!"));
    
        // Adjust spacing for better layout
    
        panel.add(Box.createVerticalStrut(80)); // Spacing above image
        panel.add(imageLabel); // Add image
        panel.add(Box.createVerticalStrut(10)); // Spacing between image and welcome text
        panel.add(welcomeLabel); // Add welcome text
        panel.add(Box.createVerticalStrut(30)); // Spacing before the "About Us" button
        panel.add(aboutUsButton); // Add the "About Us" button
        panel.add(Box.createVerticalGlue()); // Push components to vertical center
    
        return panel;
    }
    

    private JPanel createSidePanel() { 
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS)); // Vertical layout for buttons
        sidePanel.setBackground(new Color(210, 2, 77)); // Cherry Red background
        sidePanel.setPreferredSize(new Dimension(200, 0)); // Set preferred width (200px), height adjusts automatically
    
        // Add logo to the side panel
        JLabel logoLabel = new JLabel();
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment
        ImageIcon logoIcon = new ImageIcon("C:\\Users\\saadi\\Downloads\\petsy logo.png"); // Replace with your logo's path
        Image scaledImage = logoIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH); // Scale logo
        logoLabel.setIcon(new ImageIcon(scaledImage));
    
        // Create buttons with improved visibility and spacing
        JButton homeButton = createSideButton("-> Home");
        JButton managePetsButton = createSideButton("-> Manage Pets");
        JButton manageCustomersButton = createSideButton("-> Manage Customers");
        JButton manageBillingButton = createSideButton("-> Manage Billing");
    
        // Add action listeners for navigation
        homeButton.addActionListener(e -> cardLayout.show(mainPanel, "Welcome"));
        managePetsButton.addActionListener(e -> cardLayout.show(mainPanel, "Manage Pets"));
        manageCustomersButton.addActionListener(e -> cardLayout.show(mainPanel, "Manage Customers"));
        manageBillingButton.addActionListener(e -> cardLayout.show(mainPanel, "Manage Billing"));
    
        // Add components to the side panel
        sidePanel.add(Box.createVerticalStrut(80)); // Add spacing above logo
        sidePanel.add(logoLabel); // Add logo to the side panel
        sidePanel.add(Box.createVerticalStrut(30)); // Spacing after logo
        sidePanel.add(homeButton); // Add home button
        sidePanel.add(Box.createVerticalStrut(10)); // Spacing between buttons
        sidePanel.add(managePetsButton); // Add manage pets button
        sidePanel.add(Box.createVerticalStrut(10)); // Spacing between buttons
        sidePanel.add(manageCustomersButton); // Add manage customers button
        sidePanel.add(Box.createVerticalStrut(10)); // Spacing between buttons
        sidePanel.add(manageBillingButton); // Add manage billing button
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
    

    private JPanel createManagePetsPage() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Labels and text fields for pet details
        JLabel idLabel = new JLabel("Pet ID:");
        JTextField idField = new JTextField(20);
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);
        JLabel typeLabel = new JLabel("Type:");
        JTextField typeField = new JTextField(20);
        JLabel ownerLabel = new JLabel("Owner:");
        JTextField ownerField = new JTextField(20);

        JTextArea petsTextArea = new JTextArea(10, 40);
        petsTextArea.setEditable(false);
        petsTextArea.setText(getPetsData());

        // Arrange components in GridBagLayout
        gbc.gridx = 0; gbc.gridy = 0; panel.add(idLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; panel.add(idField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(typeLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; panel.add(typeField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; panel.add(ownerLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; panel.add(ownerField, gbc);

        // Add button
        JButton addPetButton = new JButton("Add Pet");
        addPetButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String type = typeField.getText();
                String owner = ownerField.getText();
                Pet pet = new Pet(id, name, type, owner);
                pets.add(pet);
                updateTextArea(petsTextArea, getPetsData());
                saveData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID.");
            }
        });

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; panel.add(addPetButton, gbc);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; panel.add(new JScrollPane(petsTextArea), gbc);

        return panel;
    }

    private String getPetsData() {
        StringBuilder data = new StringBuilder("Current Pets:\n");
        for (Pet pet : pets) {
            data.append(pet.toString()).append("\n");
        }
        return data.toString();
    }

    private JPanel createManageCustomersPage() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

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
        gbc.gridx = 0; gbc.gridy = 0; panel.add(idLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; panel.add(idField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(emailLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; panel.add(emailField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; panel.add(phoneLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; panel.add(phoneField, gbc);

        // Add button
        JButton addCustomerButton = new JButton("Add Customer");
        addCustomerButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();
                Customer customer = new Customer(id, name, email, phone);
                customers.add(customer);
                updateTextArea(customersTextArea, getCustomersData());
                saveData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid ID.");
            }
        });

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; panel.add(addCustomerButton, gbc);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; panel.add(new JScrollPane(customersTextArea), gbc);

        return panel;
    }

    private String getCustomersData() {
        StringBuilder data = new StringBuilder("Current Customers:\n");
        for (Customer customer : customers) {
            data.append(customer.toString()).append("\n");
        }
        return data.toString();
    }

    private JPanel createManageBillingPage() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
    
        // Labels and text fields for bill details
        JLabel billIdLabel = new JLabel("Bill ID:");
        JTextField billIdField = new JTextField(20);
        JLabel customerIdLabel = new JLabel("Customer ID:");
        JTextField customerIdField = new JTextField(20);
        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField(20);
    
        JTextArea billsTextArea = new JTextArea(10, 40);
        billsTextArea.setEditable(false);
        billsTextArea.setText(getBillsData());
    
        // Arrange components in GridBagLayout
        gbc.gridx = 0; gbc.gridy = 0; panel.add(billIdLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; panel.add(billIdField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(customerIdLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(customerIdField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(amountLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; panel.add(amountField, gbc);
    
        // Add button
        JButton addBillButton = new JButton("Generate Bill");
        addBillButton.addActionListener(e -> {
            try {
                int billId = Integer.parseInt(billIdField.getText());
                int customerId = Integer.parseInt(customerIdField.getText());
                double amount = Double.parseDouble(amountField.getText());
    
                // Find customer by ID
                Customer customer = findCustomerById(customerId);
                if (customer != null) {
                    Bill bill = new Bill(billId, customer, amount, "2024-11-26"); // Add the date if necessary
                    bills.add(bill);
                    updateTextArea(billsTextArea, getBillsData());
                    saveData();
                } else {
                    JOptionPane.showMessageDialog(this, "Customer not found.");
                }
    
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please check the entered values.");
            }
        });
    
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; panel.add(addBillButton, gbc);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; panel.add(new JScrollPane(billsTextArea), gbc);
    
        return panel;
    }
    
    private String getBillsData() {
        StringBuilder data = new StringBuilder("Current Bills:\n");
        for (Bill bill : bills) {
            data.append(bill.toString()).append("\n");
        }
        return data.toString();
    }
    
    private void updateTextArea(JTextArea textArea, String newText) {
        SwingUtilities.invokeLater(() -> {
            textArea.setText(newText);
        });
    }
    
    private Customer findCustomerById(int customerId) {
        // Search for customer by ID from the list of customers
        for (Customer customer : customers) {
            if (customer.getId() == customerId) {
                return customer;
            }
        }
        return null; // Return null if no customer found
    }
    

    private void saveData() {
        try (ObjectOutputStream petStream = new ObjectOutputStream(new FileOutputStream("pets.ser"));
             ObjectOutputStream customerStream = new ObjectOutputStream(new FileOutputStream("customers.ser"));
             ObjectOutputStream billStream = new ObjectOutputStream(new FileOutputStream("bills.ser"))) {
            
            // Saving the pet, customer, and bill data
            petStream.writeObject(pets);
            customerStream.writeObject(customers);
            billStream.writeObject(bills);
    
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage());
        }
    }
    

    private void loadData() {
        try {
            // Load pets from file if it exists
            File petFile = new File("pets.ser");
            if (petFile.exists()) {
                try (ObjectInputStream petStream = new ObjectInputStream(new FileInputStream(petFile))) {
                    pets = (ArrayList<Pet>) petStream.readObject();
                }
            }
    
            // Load customers from file if it exists
            File customerFile = new File("customers.ser");
            if (customerFile.exists()) {
                try (ObjectInputStream customerStream = new ObjectInputStream(new FileInputStream(customerFile))) {
                    customers = (ArrayList<Customer>) customerStream.readObject();
                }
            }
    
            // Load bills from file if it exists
            File billFile = new File("bills.ser");
            if (billFile.exists()) {
                try (ObjectInputStream billStream = new ObjectInputStream(new FileInputStream(billFile))) {
                    bills = (ArrayList<Bill>) billStream.readObject();
                }
            }
    
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();  // Handle exceptions as needed
        }
    }
}
