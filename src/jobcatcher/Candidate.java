package jobcatcher;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Candidate {
    private int id;
    private String firstName;
    private String lastName;
    private int addId;
    private String email;

    private List<Advertisments> advertisments = new ArrayList<>();

    public Candidate() {
    }

    public Candidate(String firstName, String lastName, int addId, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.addId = addId;
        this.email = email;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "Advertisments_Candidate",
            joinColumns = {
                    @JoinColumn(name = "CandidateID", nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "AdvertismentID", nullable = false, updatable = false)
            })
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
    @Column(name = "AddID")
    public int getAddId() {
        return addId;
    }

    public void setAddId(int addId) {
        this.addId = addId;
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
        Candidate candidate = (Candidate) o;
        return id == candidate.id &&
                addId == candidate.addId &&
                Objects.equals(firstName, candidate.firstName) &&
                Objects.equals(lastName, candidate.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, addId);
    }
}
