package io.tapkey.developers.data;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

    public static final PasswordEncoder PASSWORD_ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private String id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    private String[] roles;

    private String tapkeyUserId;

    private String tapkeyContactId;

    private String firstName;

    private String lastName;

    protected User() {
    }

    public User(String username, String password, String firstName, String lastName, String[] roles) {
        this.username = username;
        this.setPassword(password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s']",
                getId(), getFirstName(), getLastName());
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = PASSWORD_ENCODER.encode(password);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getTapkeyUserId() {
        return tapkeyUserId;
    }

    public void setTapkeyUserId(String tapkeyUserId) {
        this.tapkeyUserId = tapkeyUserId;
    }

    public String getTapkeyContactId() {
        return tapkeyContactId;
    }

    public void setTapkeyContactId(String tapkeyContactId) {
        this.tapkeyContactId = tapkeyContactId;
    }
}
