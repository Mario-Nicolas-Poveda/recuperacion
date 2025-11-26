package com.chathumal.smapp.dto;

/**
 * Data Transfer Object for User data.
 * Modeled after DatabaseConfig in configDb package.
 * 
 * Follows:
 * - SRP: Single responsibility of transferring user data
 * - Immutable design (like DatabaseConfig) for thread safety
 * - Clean separation from Entity layer
 */
public class UserDTO {
    private final Integer id;
    private final String name;
    private final String address;
    private final String contact;
    private final String email;
    private final String password;
    private final boolean fulacs;

    /**
     * Constructor for creating new users (no ID)
     */
    public UserDTO(String name, String address, String contact, String email, String password, boolean fulacs) {
        this(null, name, address, contact, email, password, fulacs);
    }

    /**
     * Constructor for existing users (with ID)
     */
    public UserDTO(Integer id, String name, String address, String contact, String email, String password,
            boolean fulacs) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.email = email;
        this.password = password;
        this.fulacs = fulacs;
    }

    // Getters only (immutable like DatabaseConfig)
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isFulacs() {
        return fulacs;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", fulacs=" + fulacs +
                '}';
    }
}
