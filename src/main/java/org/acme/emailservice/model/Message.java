package org.acme.emailservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "message")
@NamedQuery(name = "Messages.getAll", query = "SELECT m FROM Message m ORDER BY m.timelineId")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // @SequenceGenerator(name = "timelineSeq", sequenceName = "timeline_seq", allocationSize = 1, initialValue = 100)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="timelineSeq")
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(columnDefinition="serial", unique = true, nullable = false)
    private Long timelineId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTimelineId() {
        return timelineId;
    }

    public void setTimelineId(Long timelineId) {
        this.timelineId = timelineId;
    }
}
