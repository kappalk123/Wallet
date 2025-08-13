package com.example.Wallet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wallet")
public class Wallet {
    @Id
    private UUID id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Version
    private Long version;

    public UUID getId() {
        return this.id;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public Long getVersion() {
        return this.version;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
