package org.acme.emailservice.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.ForeignKey;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "message")
@NamedQuery(name = "Messages.getAll", query = "SELECT m FROM Message m ORDER BY m.timelineId")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private String owner;

    private String subject;

    /* @OneToMany(
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
        )
    @JoinColumn(name = "message_id", nullable = false, foreignKey = @ForeignKey(name = "fk_msg_tag_id")) */
    // @ElementCollection
    // @CollectionTable(name = "msg_tag", joinColumns = @JoinColumn(name = "message_id"))
    // @ElementCollection(fetch = FetchType.EAGER, targetClass=Tag.class)
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public Optional<Tag> getTag(final String key) {
        return tags.stream()
                .filter(tag -> tag.getKey().equals(key))
                .findFirst();
    }

    public void addSkill(final Tag tag) {
        tags.add(tag);
    }

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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
