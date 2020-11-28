package org.acme.emailservice.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
@Table(name = "label")
@NamedQuery(name = "Label.getAll", query = "SELECT l FROM Label l ORDER BY l.name")
public class Label {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id", referencedColumnName = "id", nullable = true)
    private Label parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Label> child = new HashSet<>();

    @ManyToOne()
    @JsonbTransient
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private  User user;

    @ManyToMany(mappedBy = "labels")
    @JsonbTransient
    private Set<Message> message;

    private String name;

    private int color;

    @SequenceGenerator(name="labelHistoryId", sequenceName="label_history_id")
    @GeneratedValue(generator="labelHistoryId", strategy = GenerationType.SEQUENCE)
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

    public Set<Label> getChild() {
        return child;
    }

    public void setChild(Set<Label> child) {
        this.child = child;
    }

    public Label getParent() {
        return parent;
    }

    public void setParent(Label parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Set<Message> getMessage() {
        return message;
    }

    public void setMessage(Set<Message> message) {
        this.message = message;
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
