import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
//import java.awt.image.BufferedImage;
//import javax.imageio.ImageIO;

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
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout()); // Use BorderLayout for better panel organization
    
        // Create a side panel
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS)); // Vertical layout for buttons
        sidePanel.setBackground(new Color(210, 2, 77)); // Cherry Red background
        sidePanel.setPreferredSize(new Dimension(200, 0)); // Set preferred width (200px), height adjusts automatically
        
        //  Add logo to the side panel
        JLabel logoLabel = new JLabel();
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment
        ImageIcon logoIcon = new ImageIcon("C:\\Users\\saadi\\Downloads\\petsy logo.png"); // Replace with your logo's path
        Image scaledImage = logoIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH); // Scale logo
        logoLabel.setIcon(new ImageIcon(scaledImage));
        sidePanel.add(Box.createVerticalStrut(20)); // Add spacing above logo
        sidePanel.add(logoLabel);

        // Create buttons for navigation
        JButton managePetsButton = createSideButton("-> Manage Pets");
        JButton manageCustomersButton = createSideButton("-> Manage Customers");
        JButton manageBillingButton = createSideButton("-> Manage Billing");
    
        // Add action listeners for navigation
        managePetsButton.addActionListener(e -> openManagePetsFrame());
        manageCustomersButton.addActionListener(e -> openManageCustomersFrame());
        manageBillingButton.addActionListener(e -> openManageBillingFrame());
    
        // Add buttons to the side panel
        sidePanel.add(Box.createVerticalStrut(20)); // Add spacing at the top
        sidePanel.add(managePetsButton);
        sidePanel.add(Box.createVerticalStrut(10)); // Spacing between buttons
        sidePanel.add(manageCustomersButton);
        sidePanel.add(Box.createVerticalStrut(10)); // Spacing between buttons
        sidePanel.add(manageBillingButton);
        sidePanel.add(Box.createVerticalGlue()); // Push components upwards
    
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE); // Set background color
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Set layout to BoxLayout for vertical stacking

        // Add image
        JLabel imageLabel = new JLabel();
        ImageIcon mainImageIcon = new ImageIcon("C:\\Users\\saadi\\Downloads\\welcome.png"); // Path to your image
        Image mainScaledImage = mainImageIcon.getImage().getScaledInstance(170, 170, Image.SCALE_SMOOTH); // Adjust size as needed
        imageLabel.setIcon(new ImageIcon(mainScaledImage));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align image

        // Add welcome text
        JLabel welcomeLabel = new JLabel("Welcome to the Pet Shop!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD , 20));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align text

        // Add components to mainPanel
        mainPanel.add(Box.createVerticalStrut(20)); // Add spacing at the top
        mainPanel.add(imageLabel); // Add image
        mainPanel.add(Box.createVerticalStrut(10)); // Add spacing between image and text
        mainPanel.add(welcomeLabel); // Add text

    
        // Add panels to the frame
        add(sidePanel, BorderLayout.WEST); // Add side panel to the left
        add(mainPanel, BorderLayout.CENTER); // Main panel in the center
    
        // Load data from files
        loadData();
    
        setVisible(true);
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

    // Helper function to update the JTextArea with current data
    private void updateTextArea(JTextArea textArea, String data) {
        textArea.setText(data);
    }    

    private void openManagePetsFrame() {
        JFrame petsFrame = new JFrame("Manage Pets");
        petsFrame.setSize(500, 500);
        petsFrame.setLayout(new GridBagLayout());
        petsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Adding padding to the components
    
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
    
        // **Update JTextArea with existing pets data when the frame opens**
        petsTextArea.setText(getPetsData());

    
        // Arrange components in GridBagLayout
        gbc.gridx = 0; gbc.gridy = 0; petsFrame.add(idLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; petsFrame.add(idField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; petsFrame.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; petsFrame.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; petsFrame.add(typeLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; petsFrame.add(typeField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; petsFrame.add(ownerLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; petsFrame.add(ownerField, gbc);
    
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
                JOptionPane.showMessageDialog(petsFrame, "Invalid ID.");
            }
        });
    
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; petsFrame.add(addPetButton, gbc);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; petsFrame.add(new JScrollPane(petsTextArea), gbc);
    
        petsFrame.setVisible(true);
    }

    private String getPetsData() {
        StringBuilder data = new StringBuilder("Current Pets:\n");
        for (Pet pet : pets) {
            data.append(pet.toString()).append("\n");
        }
        return data.toString();
    }

    private void openManageCustomersFrame() {
        JFrame customersFrame = new JFrame("Manage Customers");
        customersFrame.setSize(500, 500);
        customersFrame.setLayout(new GridBagLayout());
        customersFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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
        //customersFrame.add(new JScrollPane(customersTextArea), gbc);


        // Arrange components in GridBagLayout
        gbc.gridx = 0; gbc.gridy = 0; customersFrame.add(idLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; customersFrame.add(idField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; customersFrame.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; customersFrame.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; customersFrame.add(emailLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; customersFrame.add(emailField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; customersFrame.add(phoneLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; customersFrame.add(phoneField, gbc);

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
                JOptionPane.showMessageDialog(customersFrame, "Invalid ID.");
            }
        });

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; customersFrame.add(addCustomerButton, gbc);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; customersFrame.add(new JScrollPane(customersTextArea), gbc);

        customersFrame.setVisible(true);
    }

    private String getCustomersData() {
        StringBuilder data = new StringBuilder("Current Customers:\n");
        for (Customer customer : customers) {
            data.append(customer.toString()).append("\n");
        }
        return data.toString();
    }

    private void openManageBillingFrame() {
        JFrame billingFrame = new JFrame("Manage Billing");
        billingFrame.setSize(500, 500);
        billingFrame.setLayout(new GridBagLayout());
        billingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Adding padding to the components

        // Labels and text fields for billing details
        JLabel billIdLabel = new JLabel("Bill ID:");
        JTextField billIdField = new JTextField(20);
        JLabel customerIdLabel = new JLabel("Customer ID:");
        JTextField customerIdField = new JTextField(20);
        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField(20);
        JLabel dateLabel = new JLabel("Date:");
        JTextField dateField = new JTextField(20);

        JTextArea billingTextArea = new JTextArea(10, 40);
        billingTextArea.setEditable(false);

        billingTextArea.setText(getBillingData());


        // Arrange components in GridBagLayout
        gbc.gridx = 0; gbc.gridy = 0; billingFrame.add(billIdLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; billingFrame.add(billIdField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; billingFrame.add(customerIdLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; billingFrame.add(customerIdField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; billingFrame.add(amountLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; billingFrame.add(amountField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; billingFrame.add(dateLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; billingFrame.add(dateField, gbc);

        // Add button to save billing information
        JButton addBillButton = new JButton("Add Bill");
        addBillButton.addActionListener(e -> {
            try {
                int billId = Integer.parseInt(billIdField.getText());
                int customerId = Integer.parseInt(customerIdField.getText());
                double amount = Double.parseDouble(amountField.getText());
                String date = dateField.getText();

                Customer customer = findCustomerById(customerId); // Find customer by ID
                if (customer != null) {
                    Bill bill = new Bill(billId, customer, amount, date);
                    bills.add(bill);
                    updateTextArea(billingTextArea, getBillingData());
                } else {
                    JOptionPane.showMessageDialog(billingFrame, "Customer not found.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(billingFrame, "Invalid input data.");
            }
        });

        // Add button to billing frame
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; billingFrame.add(addBillButton, gbc);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; billingFrame.add(new JScrollPane(billingTextArea), gbc);

        billingFrame.setVisible(true);
    }

    // Helper method to find customer by ID
    private Customer findCustomerById(int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                return customer;
            }
        }
        return null; // Return null if customer not found
    }

    private String getBillingData() {
        StringBuilder data = new StringBuilder("Current Bills:\n");
        for (Bill bill : bills) {
            data.append(bill.toString()).append("\n");
        }
        return data.toString();
    }

    private void saveData() {
        try (ObjectOutputStream petStream = new ObjectOutputStream(new FileOutputStream("pets.ser"));
             ObjectOutputStream customerStream = new ObjectOutputStream(new FileOutputStream("customers.ser"));
             ObjectOutputStream billStream = new ObjectOutputStream(new FileOutputStream("bills.ser"))) {
                //System.out.println(new File("pets.ser").getAbsolutePath());

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
