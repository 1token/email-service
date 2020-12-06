package org.acme.emailservice.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
// import java.util.Optional;
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
import javax.persistence.PostLoad;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.acme.emailservice.exception.UpdateNotAllowedException;
import org.hibernate.annotations.ColumnDefault;
import org.jboss.logging.Logger;

@Entity
@Table(name = "message")
@NamedQuery(name = "Message.getAll", query = "SELECT m FROM Message m ORDER BY m.timelineId DESC")
public class Message {

    private static Logger logger = Logger.getLogger(Message.class);

    @Transient
    // private Message prevMessage;
    private LocalDateTime prevSentAt;
    private int prevUpdateNotAllowedHashCode;
    private int prevUpdateOnSentNotAllowedHashCode;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JsonbTransient
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private  Account account;

    @Column(nullable = false)
    private String messageId;

    @Column(nullable = true)
    private String threadId;

    @JsonbTransient
    @Column(columnDefinition = "jsonb", nullable = true)
    private String InReplyTo;

    @JsonbTransient
    @Column(name = "\"references\"", columnDefinition = "jsonb", nullable = true)
    private String references;

    @JsonbTransient
    @Column(nullable = true)
    private Boolean fwd;

    @Column(nullable = true)
    private String subject;

    @Column(nullable = true)
    private String snippet;

    @Column(nullable = true)
    private String resourceUrl;

    @Column(nullable = true)
    private String mimetype;
    
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

    @Column(nullable = true)
    private LocalDateTime sentAt;

    @Column(nullable = true)
    private LocalDateTime receivedAt;

    @Column(nullable = true)
    private LocalDateTime snoozedAt;

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
    
    @PostLoad
    private void onPostLoad() {
        prevSentAt = this.sentAt;
        prevUpdateNotAllowedHashCode = updateNotAllowedHashCode();
        prevUpdateOnSentNotAllowedHashCode = updateOnSentNotAllowedHashCode();
    }

    @PreUpdate
    private void onPreUpdate() throws UpdateNotAllowedException {
        if (prevUpdateNotAllowedHashCode != updateNotAllowedHashCode()) {
            throw new UpdateNotAllowedException("Update not allowed");
        }
        if (Objects.nonNull(prevSentAt)) {
            if (prevUpdateOnSentNotAllowedHashCode != updateOnSentNotAllowedHashCode()) {
                throw new UpdateNotAllowedException("Update on sent messages not allowed");
            }    
        }
    }

    public Message(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getInReplyTo() {
        return InReplyTo;
    }

    public void setInReplyTo(String InReplyTo) {
        this.InReplyTo = InReplyTo;
    }

    public String getReferences() {
        return references;
    }

    public void setReferences(String references) {
        this.references = references;
    }

    public Boolean getFwd() {
        return fwd;
    }

    public void setFwd(Boolean fwd) {
        this.fwd = fwd;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
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

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(LocalDateTime receivedAt) {
        this.receivedAt = receivedAt;
    }

    public LocalDateTime getSnoozedAt() {
        return snoozedAt;
    }

    public void setSnoozedAt(LocalDateTime snoozedAt) {
        this.snoozedAt = snoozedAt;
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

    public Message(Message original){
        this.subject = original.getSubject();
        this.snippet = original.getSnippet();
        this.attachments = new ArrayList<Attachment>();
        for (Attachment a : original.getAttachments()) {
            this.attachments.add(new Attachment(a));
        }
    }

    public int updateNotAllowedHashCode() {
        final int prime = 31;
        int result = 1;
        // result = prime * result + ((InReplyTo == null) ? 0 : InReplyTo.hashCode());
        // result = prime * result + ((account == null) ? 0 : account.hashCode());
        // result = prime * result + ((attachments == null) ? 0 : attachments.hashCode());
        // result = prime * result + ((fwd == null) ? 0 : fwd.hashCode());
        // result = prime * result + ((historyId == null) ? 0 : historyId.hashCode());
        // result = prime * result + ((id == null) ? 0 : id.hashCode());
        // result = prime * result + ((labels == null) ? 0 : labels.hashCode());
        // result = prime * result + ((lastStmt == null) ? 0 : lastStmt.hashCode());
        result = prime * result + ((messageId == null) ? 0 : messageId.hashCode());
        // result = prime * result + ((mimetype == null) ? 0 : mimetype.hashCode());
        // result = prime * result + ((prevSentAt == null) ? 0 : prevSentAt.hashCode());
        // result = prime * result + ((receivedAt == null) ? 0 : receivedAt.hashCode());
        // result = prime * result + ((recipientsBcc == null) ? 0 : recipientsBcc.hashCode());
        // result = prime * result + ((recipientsCc == null) ? 0 : recipientsCc.hashCode());
        // result = prime * result + ((recipientsTo == null) ? 0 : recipientsTo.hashCode());
        // result = prime * result + ((references == null) ? 0 : references.hashCode());
        // result = prime * result + ((resourceUrl == null) ? 0 : resourceUrl.hashCode());
        // result = prime * result + ((sentAt == null) ? 0 : sentAt.hashCode());
        // result = prime * result + ((snippet == null) ? 0 : snippet.hashCode());
        // result = prime * result + ((snoozedAt == null) ? 0 : snoozedAt.hashCode());
        // result = prime * result + ((subject == null) ? 0 : subject.hashCode());
        // result = prime * result + ((tags == null) ? 0 : tags.hashCode());
        // result = prime * result + ((threadId == null) ? 0 : threadId.hashCode());
        // result = prime * result + ((timelineId == null) ? 0 : timelineId.hashCode());
        // result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
        return result;
    }

    public int updateOnSentNotAllowedHashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((InReplyTo == null) ? 0 : InReplyTo.hashCode());
        result = prime * result + ((account == null) ? 0 : account.hashCode());
        result = prime * result + ((attachments == null) ? 0 : attachments.hashCode());
        result = prime * result + ((fwd == null) ? 0 : fwd.hashCode());
        result = prime * result + ((historyId == null) ? 0 : historyId.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        // result = prime * result + ((labels == null) ? 0 : labels.hashCode());
        result = prime * result + ((lastStmt == null) ? 0 : lastStmt.hashCode());
        result = prime * result + ((messageId == null) ? 0 : messageId.hashCode());
        result = prime * result + ((mimetype == null) ? 0 : mimetype.hashCode());
        result = prime * result + ((prevSentAt == null) ? 0 : prevSentAt.hashCode());
        result = prime * result + ((receivedAt == null) ? 0 : receivedAt.hashCode());
        result = prime * result + ((recipientsBcc == null) ? 0 : recipientsBcc.hashCode());
        result = prime * result + ((recipientsCc == null) ? 0 : recipientsCc.hashCode());
        result = prime * result + ((recipientsTo == null) ? 0 : recipientsTo.hashCode());
        result = prime * result + ((references == null) ? 0 : references.hashCode());
        result = prime * result + ((resourceUrl == null) ? 0 : resourceUrl.hashCode());
        result = prime * result + ((sentAt == null) ? 0 : sentAt.hashCode());
        result = prime * result + ((snippet == null) ? 0 : snippet.hashCode());
        result = prime * result + ((snoozedAt == null) ? 0 : snoozedAt.hashCode());
        result = prime * result + ((subject == null) ? 0 : subject.hashCode());
        result = prime * result + ((tags == null) ? 0 : tags.hashCode());
        result = prime * result + ((threadId == null) ? 0 : threadId.hashCode());
        result = prime * result + ((timelineId == null) ? 0 : timelineId.hashCode());
        result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
        return result;
    }

    /* @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((InReplyTo == null) ? 0 : InReplyTo.hashCode());
        result = prime * result + ((account == null) ? 0 : account.hashCode());
        result = prime * result + ((attachments == null) ? 0 : attachments.hashCode());
        result = prime * result + ((fwd == null) ? 0 : fwd.hashCode());
        result = prime * result + ((historyId == null) ? 0 : historyId.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((labels == null) ? 0 : labels.hashCode());
        result = prime * result + ((lastStmt == null) ? 0 : lastStmt.hashCode());
        result = prime * result + ((messageId == null) ? 0 : messageId.hashCode());
        result = prime * result + ((mimetype == null) ? 0 : mimetype.hashCode());
        result = prime * result + ((prevSentAt == null) ? 0 : prevSentAt.hashCode());
        result = prime * result + ((receivedAt == null) ? 0 : receivedAt.hashCode());
        result = prime * result + ((recipientsBcc == null) ? 0 : recipientsBcc.hashCode());
        result = prime * result + ((recipientsCc == null) ? 0 : recipientsCc.hashCode());
        result = prime * result + ((recipientsTo == null) ? 0 : recipientsTo.hashCode());
        result = prime * result + ((references == null) ? 0 : references.hashCode());
        result = prime * result + ((resourceUrl == null) ? 0 : resourceUrl.hashCode());
        result = prime * result + ((sentAt == null) ? 0 : sentAt.hashCode());
        result = prime * result + ((snippet == null) ? 0 : snippet.hashCode());
        result = prime * result + ((snoozedAt == null) ? 0 : snoozedAt.hashCode());
        result = prime * result + ((subject == null) ? 0 : subject.hashCode());
        result = prime * result + ((tags == null) ? 0 : tags.hashCode());
        result = prime * result + ((threadId == null) ? 0 : threadId.hashCode());
        result = prime * result + ((timelineId == null) ? 0 : timelineId.hashCode());
        result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
        return result;
    } */
}
