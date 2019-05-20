package jobcatcher;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class EmployersAcc {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private int numActiveAdds;
    private int totalAdvertisments;
    private String email;

    public EmployersAcc() {
    }

    public EmployersAcc(String firstName, String lastName, String username, String password, int numActiveAdds, int totalAdvertisments) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.numActiveAdds = numActiveAdds;
        this.totalAdvertisments = totalAdvertisments;
    }

    private List<Advertisments> advertisments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "EmployersAccID")// ID of the other table
    public List<Advertisments> getAdvertisments() {
        return advertisments;
    }

    public void setAdvertisments(List<Advertisments> advertisments) {
        this.advertisments = advertisments;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "FirstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "LastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "numActiveAdds")
    public int getNumActiveAdds() {
        return numActiveAdds;
    }

    public void setNumActiveAdds(int numActiveAdds) {
        this.numActiveAdds = numActiveAdds;
    }

    @Basic
    @Column(name = "totalAdvertisments")
    public int getTotalAdvertisments() {
        return totalAdvertisments;
    }

    public void setTotalAdvertisments(int totalAdvertisments) {
        this.totalAdvertisments = totalAdvertisments;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployersAcc that = (EmployersAcc) o;
        return id == that.id &&
                numActiveAdds == that.numActiveAdds &&
                totalAdvertisments == that.totalAdvertisments &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, username, password, numActiveAdds, totalAdvertisments);
    }
}
