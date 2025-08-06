package com.example.Wallet.controller;

import com.example.Wallet.dto.WalletDTO;
import com.example.Wallet.exception.NotEnoughMoneyException;
import com.example.Wallet.exception.WalletNotFoundException;
import com.example.Wallet.model.Wallet;
import com.example.Wallet.repository.WalletRepository;
import com.example.Wallet.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wallet")
public class WalletController {

    private final WalletRepository walletRepository;
    private WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService, WalletRepository walletRepository) {
        this.walletService = walletService;
        this.walletRepository = walletRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void changeAmount(@RequestBody @Valid WalletDTO request) throws WalletNotFoundException, NotEnoughMoneyException {
        switch (request.getOperationType()) {
            case DEPOSIT -> walletService.deposit(request.getWalletId(), request.getAmount());
            case WITHDRAW -> walletService.withdraw(request.getWalletId(), request.getAmount());
        }
    }

    @GetMapping("/{walletId}")
    public BigDecimal getAmount(@PathVariable UUID walletId) throws WalletNotFoundException {
        return walletService.getAmount(walletId);
    }

    @GetMapping("/ids")
    public List<Wallet> getWallets() {
        return walletRepository.findAll();
    }

}

