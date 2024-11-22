
//package petshop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// Abstract Pet class
abstract class AbstractPet {
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
class Customer {
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


// Class Category (inherits from Pet)
class Category extends Pet {
    private String categoryName;

    public Category(int id, String name, String type, String owner, String categoryName) {
        super(id, name, type, owner);
        this.categoryName = categoryName;
    }

    public void updateDetails(String categoryName) {
        this.categoryName = categoryName;
    }

    public String toString() {
        return super.toString() + ", Category: " + categoryName;
    }
}

// Class Bill
class Bill {
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
public class PetManagementSystem extends JFrame {
    private ArrayList<Pet> pets = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Category> categories = new ArrayList<>();
    private ArrayList<Bill> bills = new ArrayList<>();

    public PetManagementSystem() {
        // Setting up the JFrame
        setTitle("Pet Shop Management System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Adding buttons for each action
        JButton managePetsButton = new JButton("Manage Pets");
        JButton manageCustomersButton = new JButton("Manage Customers");
        JButton manageCategoriesButton = new JButton("Manage Categories");
        JButton manageBillingButton = new JButton("Manage Billing");

        managePetsButton.addActionListener(e -> openManagePetsFrame());
        manageCustomersButton.addActionListener(e -> openManageCustomersFrame());
        manageCategoriesButton.addActionListener(e -> openManageCategoriesFrame());
        manageBillingButton.addActionListener(e -> openManageBillingFrame());

        // Adding buttons to the main frame
        add(managePetsButton);
        add(manageCustomersButton);
        add(manageCategoriesButton);
        add(manageBillingButton);

        setVisible(true);
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
        JLabel petNameLabel = new JLabel("Pet Name:");
        JTextField petNameField = new JTextField(20);
        JLabel petTypeLabel = new JLabel("Pet Type:");
        JTextField petTypeField = new JTextField(20);

        JTextArea customersTextArea = new JTextArea(10, 40);
        customersTextArea.setEditable(false);

        // Arrange components in GridBagLayout
        gbc.gridx = 0; gbc.gridy = 0; customersFrame.add(idLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; customersFrame.add(idField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; customersFrame.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; customersFrame.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; customersFrame.add(emailLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; customersFrame.add(emailField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; customersFrame.add(phoneLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; customersFrame.add(phoneField, gbc);
        gbc.gridx = 0; gbc.gridy = 4; customersFrame.add(petNameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 4; customersFrame.add(petNameField, gbc);
        gbc.gridx = 0; gbc.gridy = 5; customersFrame.add(petTypeLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 5; customersFrame.add(petTypeField, gbc);

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
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(customersFrame, "Invalid ID.");
            }
        });

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2; customersFrame.add(addCustomerButton, gbc);
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2; customersFrame.add(new JScrollPane(customersTextArea), gbc);

        customersFrame.setVisible(true);
    }

    private String getCustomersData() {
        StringBuilder data = new StringBuilder("Current Customers:\n");
        for (Customer customer : customers) {
            data.append(customer.toString()).append("\n");
        }
        return data.toString();
    }
    private void openManageCategoriesFrame() {
        JFrame categoriesFrame = new JFrame("Manage Categories");
        categoriesFrame.setSize(500, 500);
        categoriesFrame.setLayout(new GridBagLayout());
        categoriesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Adding padding to the components

        // Labels and text fields for category details
        JLabel idLabel = new JLabel("Category ID:");
        JTextField idField = new JTextField(20);
        JLabel nameLabel = new JLabel("Category Name:");
        JTextField nameField = new JTextField(20);

        JTextArea categoriesTextArea = new JTextArea(10, 40);
        categoriesTextArea.setEditable(false);

        // Arrange components in GridBagLayout
        gbc.gridx = 0; gbc.gridy = 0; categoriesFrame.add(idLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; categoriesFrame.add(idField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; categoriesFrame.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; categoriesFrame.add(nameField, gbc);

        // Add buttons for managing categories
        JButton addCategoryButton = new JButton("Add Category");
        addCategoryButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                if (!name.isEmpty()) {
                    Category category = new Category(id, "Sample Pet", "Dog", "John Doe", name);
                    categories.add(category);
                    updateTextArea(categoriesTextArea, getCategoriesData());
                } else {
                    JOptionPane.showMessageDialog(categoriesFrame, "Category name cannot be empty.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(categoriesFrame, "Invalid ID.");
            }
        });

        JButton editCategoryButton = new JButton("Edit Category");
        editCategoryButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                Category category = findCategoryById(id);
                if (category != null) {
                    category.updateDetails(nameField.getText());
                    updateTextArea(categoriesTextArea, getCategoriesData());
                    JOptionPane.showMessageDialog(categoriesFrame, "Category updated: " + category);
                } else {
                    JOptionPane.showMessageDialog(categoriesFrame, "Category not found.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(categoriesFrame, "Invalid ID.");
            }
        });

        JButton deleteCategoryButton = new JButton("Delete Category");
        deleteCategoryButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                Category category = findCategoryById(id);
                if (category != null) {
                    categories.remove(category);
                    updateTextArea(categoriesTextArea, getCategoriesData());
                    JOptionPane.showMessageDialog(categoriesFrame, "Category deleted.");
                } else {
                    JOptionPane.showMessageDialog(categoriesFrame, "Category not found.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(categoriesFrame, "Invalid ID.");
            }
        });

        // Add buttons and text area to the frame
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; categoriesFrame.add(addCategoryButton, gbc);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; categoriesFrame.add(editCategoryButton, gbc);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; categoriesFrame.add(deleteCategoryButton, gbc);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; categoriesFrame.add(new JScrollPane(categoriesTextArea), gbc);

        categoriesFrame.setVisible(true);
    }

    // Helper method to find a category by ID
    private Category findCategoryById(int id) {
        for (Category category : categories) {
            if (category.getId() == id) {
                return category;
            }
        }
        return null; // Return null if category not found
    }

    private String getCategoriesData() {
        StringBuilder data = new StringBuilder("Current Categories:\n");
        for (Category category : categories) {
            data.append(category.toString()).append("\n");
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


        public static void main(String[] args) {
        new PetManagementSystem();
    }
}
