package org.acme.emailservice.model;

import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "account")
@NamedQuery(name = "Account.getAll", query = "SELECT a FROM Account a ORDER BY a.username")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JsonbTransient
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(unique = false, nullable = true)
    private String displayName;

    @Column(updatable = false, unique = true, nullable = false)
    private String username;

    @Column(unique = false, nullable = true)
    private String imapAddr;
    @Column(unique = false, nullable = true)
    private int imapPort;
    @Column(unique = false, nullable = true)
    private int imapType;
    @Column(unique = false, nullable = true)
    private String imapSecret;

    @Column(unique = false, nullable = true)
    private String smtpAddr;
    @Column(unique = false, nullable = true)
    private int smtpPort;
    @Column(unique = false, nullable = true)
    private int smtpType;
    @Column(unique = false, nullable = true)
    private String smtpSecret;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Message> messages = new HashSet<Message>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()")
	private Date timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImapAddr() {
        return imapAddr;
    }

    public void setImapAddr(String imapAddr) {
        this.imapAddr = imapAddr;
    }

    public int getImapPort() {
        return imapPort;
    }

    public void setImapPort(int imapPort) {
        this.imapPort = imapPort;
    }

    public int getImapType() {
        return imapType;
    }

    public void setImapType(int imapType) {
        this.imapType = imapType;
    }

    /* public String getImapSecret() {
        return imapSecret;
    } */

    public void setImapSecret(String imapSecret) {
        this.imapSecret = imapSecret;
    }

    public String getSmtpAddr() {
        return smtpAddr;
    }

    public void setSmtpAddr(String smtpAddr) {
        this.smtpAddr = smtpAddr;
    }

    public int getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

    public int getSmtpType() {
        return smtpType;
    }

    public void setSmtpType(int smtpType) {
        this.smtpType = smtpType;
    }

    /* public String getSmtpSecret() {
        return smtpSecret;
    } */

    public void setSmtpSecret(String smtpSecret) {
        this.smtpSecret = smtpSecret;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }
}
