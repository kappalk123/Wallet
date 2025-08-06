package com.example.Wallet.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class WalletDTO {

    @NotNull
    private UUID walletId;

    @NotNull
    private OperationType operationType;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;

    public @NotNull UUID getWalletId() {
        return this.walletId;
    }

    public @NotNull OperationType getOperationType() {
        return this.operationType;
    }

    public @NotNull @DecimalMin(value = "0.01") BigDecimal getAmount() {
        return this.amount;
    }
}
