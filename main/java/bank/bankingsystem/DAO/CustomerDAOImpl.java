package bank.bankingsystem.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import bank.bankingsystem.models.*;

/**
 * Implementation of CustomerDAO for SQLite.
 */
public class CustomerDAOImpl implements CustomerDAO {
    
    @Override
    public Integer save(Customer customer) {
        String sql = """
            INSERT INTO customers (firstName, lastName, address, employed, employerName, employerAddress, customerType, nationalId, registrationNumber)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getAddress());
            pstmt.setInt(4, customer.isEmployed() ? 1 : 0);
            pstmt.setString(5, customer.getEmployerName());
            pstmt.setString(6, customer.getEmployerAddress());
            
            if (customer instanceof Individual) {
                Individual ind = (Individual) customer;
                pstmt.setString(7, "INDIVIDUAL");
                pstmt.setString(8, ind.getNationalId());
                pstmt.setString(9, null);
            } else if (customer instanceof Company) {
                Company comp = (Company) customer;
                pstmt.setString(7, "COMPANY");
                pstmt.setString(8, null);
                pstmt.setString(9, comp.getRegistrationNumber());
            } else {
                throw new IllegalArgumentException("Unknown customer type");
            }
            
            pstmt.executeUpdate();
            
            // Use SQLite's last_insert_rowid() instead of getGeneratedKeys()
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Debugging
            throw new RuntimeException("Error saving customer: " + e.getMessage(), e);
        }
        return null;
    }
    
    @Override
    public Customer findById(Integer id) {
        String sql = "SELECT * FROM customers WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCustomer(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding customer", e);
        }
        return null;
    }
    
    @Override
    public List<Customer> findAll() {
        String sql = "SELECT * FROM customers ORDER BY id";
        List<Customer> customers = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                customers.add(mapResultSetToCustomer(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all customers", e);
        }
        return customers;
    }
    
    @Override
    public void update(Customer customer) {
        String sql = """
            UPDATE customers 
            SET firstName = ?, lastName = ?, address = ?, employed = ?, 
                employerName = ?, employerAddress = ?, nationalId = ?, registrationNumber = ?
            WHERE id = ?
        """;
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getAddress());
            pstmt.setInt(4, customer.isEmployed() ? 1 : 0);
            pstmt.setString(5, customer.getEmployerName());
            pstmt.setString(6, customer.getEmployerAddress());
            
            if (customer instanceof Individual) {
                pstmt.setString(7, ((Individual) customer).getNationalId());
                pstmt.setString(8, null);
            } else if (customer instanceof Company) {
                pstmt.setString(7, null);
                pstmt.setString(8, ((Company) customer).getRegistrationNumber());
            }
            
            pstmt.setInt(9, customer.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating customer", e);
        }
    }
    
    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM customers WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting customer", e);
        }
    }
    
    private Customer mapResultSetToCustomer(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("id");
        String firstName = rs.getString("firstName");
        String lastName = rs.getString("lastName");
        String address = rs.getString("address");
        boolean employed = rs.getInt("employed") == 1;
        String employerName = rs.getString("employerName");
        String employerAddress = rs.getString("employerAddress");
        String customerType = rs.getString("customerType");
        
        if ("INDIVIDUAL".equals(customerType)) {
            String nationalId = rs.getString("nationalId");
            return new Individual(id, firstName, lastName, address, nationalId, 
                                employed, employerName, employerAddress);
        } else if ("COMPANY".equals(customerType)) {
            String registrationNumber = rs.getString("registrationNumber");
            return new Company(id, firstName, address, registrationNumber,
                             employed, employerName, employerAddress);
        } else {
            throw new IllegalArgumentException("Unknown customer type: " + customerType);
        }
    }
}

