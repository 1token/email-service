package org.acme.emailservice.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;

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
    private List<RecipientTo> recipientsTo;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RecipientCc> recipientsCc;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RecipientBcc> recipientsBcc;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Attachment> attachments;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tag> tags;

    @ManyToMany()
    @JoinTable(name = "labels_messages",
            joinColumns = @JoinColumn(name = "message_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "label_id", referencedColumnName = "id"))
    private Set<Label> labels = new HashSet<>();
    
    @SequenceGenerator(name="messageTimelineId", sequenceName="message_timeline_id")
    @GeneratedValue(generator="messageTimelineId", strategy = GenerationType.SEQUENCE)
    private Long timelineId;

    @SequenceGenerator(name="messageHistoryId", sequenceName="message_history_id")
    @GeneratedValue(generator="messageHistoryId", strategy = GenerationType.SEQUENCE)
    private Long historyId;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Byte lastStmt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()")
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

    public List<RecipientTo> getRecipientsTo() {
        return recipientsTo;
    }

    public void setRecipientsTo(List<RecipientTo> recipientsTo) {
        this.recipientsTo = recipientsTo;
    }

    public List<RecipientCc> getRecipientsCc() {
        return recipientsCc;
    }

    public void setRecipientsCc(List<RecipientCc> recipientsCc) {
        this.recipientsCc = recipientsCc;
    }

    public List<RecipientBcc> getRecipientsBcc() {
        return recipientsBcc;
    }

    public void setRecipientsBcc(List<RecipientBcc> recipientsBcc) {
        this.recipientsBcc = recipientsBcc;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Set<Label> getLabels() {
        return labels;
    }

    public void setLabels(Set<Label> labels) {
        this.labels = labels;
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
