package com.ccreanga.planning.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.ccreanga.planning.domain.enumeration.TcStatus;

/**
 * A Tc.
 */
@Entity
@Table(name = "tc")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tc implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 128)
    @Column(name = "name", length = 128, nullable = false)
    private String name;
    
    @Column(name = "planned_date")
    private LocalDate plannedDate;
    
    @Column(name = "start_date")
    private LocalDate startDate;
    
    @Column(name = "end_date")
    private LocalDate endDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TcStatus status;
    
    @Column(name = "dummy")
    private String dummy;
    
    @ManyToOne
    @JoinColumn(name = "review_id")
    private SecurityReview review;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "tc_notes",
               joinColumns = @JoinColumn(name="tcs_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="notess_id", referencedColumnName="ID"))
    private Set<Note> notess = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "tc_reviewers",
               joinColumns = @JoinColumn(name="tcs_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="reviewerss_id", referencedColumnName="ID"))
    private Set<User> reviewerss = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "tc_links",
               joinColumns = @JoinColumn(name="tcs_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="linkss_id", referencedColumnName="ID"))
    private Set<Link> linkss = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getPlannedDate() {
        return plannedDate;
    }
    
    public void setPlannedDate(LocalDate plannedDate) {
        this.plannedDate = plannedDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public TcStatus getStatus() {
        return status;
    }
    
    public void setStatus(TcStatus status) {
        this.status = status;
    }

    public String getDummy() {
        return dummy;
    }
    
    public void setDummy(String dummy) {
        this.dummy = dummy;
    }

    public SecurityReview getReview() {
        return review;
    }

    public void setReview(SecurityReview securityReview) {
        this.review = securityReview;
    }

    public Set<Note> getNotess() {
        return notess;
    }

    public void setNotess(Set<Note> notes) {
        this.notess = notes;
    }

    public Set<User> getReviewerss() {
        return reviewerss;
    }

    public void setReviewerss(Set<User> users) {
        this.reviewerss = users;
    }

    public Set<Link> getLinkss() {
        return linkss;
    }

    public void setLinkss(Set<Link> links) {
        this.linkss = links;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tc tc = (Tc) o;
        if(tc.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tc.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tc{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", plannedDate='" + plannedDate + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", status='" + status + "'" +
            ", dummy='" + dummy + "'" +
            '}';
    }
}
