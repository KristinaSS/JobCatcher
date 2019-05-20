package jobcatcher;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Catagory {
    private int id;
    private String name;

    private List<Advertisments> advertisments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "CatagoryID")// ID of the other table
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
    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Catagory catagory = (Catagory) o;
        return id == catagory.id &&
                Objects.equals(name, catagory.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
