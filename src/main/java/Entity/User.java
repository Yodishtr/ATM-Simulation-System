package Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class User {

    public enum ROLE{
        ADMIN,
        USER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ROLE role;

    @OneToMany(mappedBy = "registeredUser", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Account> accounts;

    public User(){}

    // getters
    public Long getUserId(){
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ROLE getRole() {
        return role;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setUserId(Long userId){
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setRole(ROLE role){
        this.role = role;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void setEmail(String email){
        this.email = email;
    }

}

