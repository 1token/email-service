package org.acme.emailservice.model;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "message")
@NamedQuery(name = "Message.getAll", query = "SELECT m FROM Message m ORDER BY m.timelineId DESC")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JsonbTransient
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private  Account account;

    private String subject;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tag> tags;
    
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(columnDefinition="serial", unique = true, nullable = false)
    private Long timelineId;

    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(columnDefinition="serial", nullable = false)
    private Long historyId;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Byte lastStmt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT timezone('UTC'::text, now())")
	private Date timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    /* public Optional<Tag> getTag(final String key) {
        return tags.stream()
                .filter(tag -> tag.getKey().equals(key))
                .findFirst();
    }

    public void addTag(final Tag tag) {
        tags.add(tag);
    } */

    public Long getTimelineId() {
        return timelineId;
    }

    public void setTimelineId(Long timelineId) {
        this.timelineId = timelineId;
    }

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public Byte getLastStmt() {
        return lastStmt;
    }

    public void setLastStmt(Byte lastStmt) {
        this.lastStmt = lastStmt;
    }
}
