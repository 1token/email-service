package org.acme.emailservice.model;

import java.util.Date;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
// import javax.persistence.Temporal;
// import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "msg_tag")
@NamedQuery(name = "Tags.getAll", query = "SELECT t FROM Tag t ORDER BY t.value")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @JsonbTransient
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String key;

    private String value;

    @SequenceGenerator(name = "timelineSeq", sequenceName = "timeline_seq", allocationSize = 1, initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="timelineSeq")
    @Column(columnDefinition="serial", unique = true, nullable = false)
    private Long timelineId;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    // @Temporal(TemporalType.TIMESTAMP)
    // @Column(nullable = false, insertable = false, updatable = false, columnDefinition = "timestamp without time zone NOT NULL DEFAULT now()")
	private Date timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getTimelineId() {
        return timelineId;
    }

    public void setTimelineId(Long timelineId) {
        this.timelineId = timelineId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
