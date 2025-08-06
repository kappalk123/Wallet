package com.example.Wallet.service;

import com.example.Wallet.exception.NotEnoughMoneyException;
import com.example.Wallet.exception.WalletNotFoundException;
import com.example.Wallet.model.Wallet;
import com.example.Wallet.repository.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Transactional
    public void deposit(UUID walletId, BigDecimal amount) throws WalletNotFoundException {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new WalletNotFoundException("Кошелек не найден"));

        wallet.setAmount(wallet.getAmount().add(amount));
        walletRepository.save(wallet);
    }

    @Transactional
    public void withdraw(UUID walletId, BigDecimal amount) throws WalletNotFoundException, NotEnoughMoneyException {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new WalletNotFoundException("Кошелек не найден"));

        if (wallet.getAmount().compareTo(amount) < 0) {
            throw new NotEnoughMoneyException("Недостаточно средств");
        }

        wallet.setAmount(wallet.getAmount().subtract(amount));
        walletRepository.save(wallet);
    }

    public BigDecimal getAmount(UUID walletId) throws WalletNotFoundException {
        return walletRepository.findById(walletId)
                .map(Wallet::getAmount)
                .orElseThrow(() -> new WalletNotFoundException("Кошелёк не найден"));
    }

}
