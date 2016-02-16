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

import com.ccreanga.planning.domain.enumeration.TaskStatus;

import com.ccreanga.planning.domain.enumeration.Criticalness;

/**
 * A Task.
 */
@Entity
@Table(name = "task")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 128)
    @Column(name = "name", length = 128, nullable = false)
    private String name;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "criticalness")
    private Criticalness criticalness;
    
    @Column(name = "start_date")
    private LocalDate startDate;
    
    @Column(name = "end_date")
    private LocalDate endDate;
    
    @ManyToOne
    @JoinColumn(name = "security_engineer_id")
    private User securityEngineer;

    @ManyToOne
    @JoinColumn(name = "initial_security_review_id")
    private SecurityReview initialSecurityReview;

    @ManyToOne
    @JoinColumn(name = "tc_id")
    private Tc tc;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "task_notes",
               joinColumns = @JoinColumn(name="tasks_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="notess_id", referencedColumnName="ID"))
    private Set<Note> notess = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "task_links",
               joinColumns = @JoinColumn(name="tasks_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="linkss_id", referencedColumnName="ID"))
    private Set<Link> linkss = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

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

    public TaskStatus getStatus() {
        return status;
    }
    
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Criticalness getCriticalness() {
        return criticalness;
    }
    
    public void setCriticalness(Criticalness criticalness) {
        this.criticalness = criticalness;
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

    public User getSecurityEngineer() {
        return securityEngineer;
    }

    public void setSecurityEngineer(User user) {
        this.securityEngineer = user;
    }

    public SecurityReview getInitialSecurityReview() {
        return initialSecurityReview;
    }

    public void setInitialSecurityReview(SecurityReview securityReview) {
        this.initialSecurityReview = securityReview;
    }

    public Tc getTc() {
        return tc;
    }

    public void setTc(Tc tc) {
        this.tc = tc;
    }

    public Set<Note> getNotess() {
        return notess;
    }

    public void setNotess(Set<Note> notes) {
        this.notess = notes;
    }

    public Set<Link> getLinkss() {
        return linkss;
    }

    public void setLinkss(Set<Link> links) {
        this.linkss = links;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        if(task.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Task{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", status='" + status + "'" +
            ", criticalness='" + criticalness + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            '}';
    }
}
