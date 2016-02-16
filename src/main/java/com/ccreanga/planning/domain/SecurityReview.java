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

/**
 * A SecurityReview.
 */
@Entity
@Table(name = "security_review")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SecurityReview implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 64)
    @Column(name = "name", length = 64, nullable = false)
    private String name;
    
    @Column(name = "review_date")
    private LocalDate reviewDate;
    
    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "security_review_notes",
               joinColumns = @JoinColumn(name="security_reviews_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="notess_id", referencedColumnName="ID"))
    private Set<Note> notess = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "security_review_reviewers",
               joinColumns = @JoinColumn(name="security_reviews_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="reviewerss_id", referencedColumnName="ID"))
    private Set<User> reviewerss = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "security_review_links",
               joinColumns = @JoinColumn(name="security_reviews_id", referencedColumnName="ID"),
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

    public LocalDate getReviewDate() {
        return reviewDate;
    }
    
    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
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
        SecurityReview securityReview = (SecurityReview) o;
        if(securityReview.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, securityReview.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SecurityReview{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", reviewDate='" + reviewDate + "'" +
            '}';
    }
}
