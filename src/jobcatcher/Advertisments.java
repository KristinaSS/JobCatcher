package jobcatcher;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Advertisments {
    private int id;
    private String heading;
    private String description;
    private boolean isActive;
    private int timesApplied;
    private List<Candidate> candidates = new ArrayList<>();

    public Advertisments() {
    }

    public Advertisments(boolean isActive) {
        this.isActive = isActive;
    }


    @ManyToMany(mappedBy = "advertisments")
    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
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
    @Column(name = "heading")
    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "isActive")
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Basic
    @Column(name = "timesApplied")
    public int getTimesApplied() {
        return timesApplied;
    }

    public void setTimesApplied(int timesApplied) {
        this.timesApplied = timesApplied;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Advertisments that = (Advertisments) o;
        return id == that.id &&
                isActive == that.isActive &&
                Objects.equals(heading, that.heading) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, heading, description, isActive);
    }
}
