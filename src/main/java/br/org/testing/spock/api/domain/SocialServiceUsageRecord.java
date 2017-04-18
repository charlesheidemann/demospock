package br.org.testing.spock.api.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by c.heidemann on 05/04/2017.
 */
@Entity
@Table(name = "SERVICE_USAGE_AUDIT")
public class SocialServiceUsageRecord implements Serializable {

    @Id
    @SequenceGenerator(name = "SERVICE_USAGE_AUDIT_ID_GENERATOR", sequenceName = "SE_SERVICE_USAGE_AUDIT")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SERVICE_USAGE_AUDIT_ID_GENERATOR")
    @Column(name = "ID_SERVICE_USAGE_AUDIT")
    private Long id;

    @NotNull
    @Column
    public LocalDateTime createdAt;

    @NotNull
    private String result;

    public SocialServiceUsageRecord() {
    }

    public SocialServiceUsageRecord(String result) {
        this.result = result;
    }

    @PrePersist
    void createdAt() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
