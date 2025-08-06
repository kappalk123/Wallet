package com.example.Wallet;

import com.example.Wallet.model.Wallet;
import com.example.Wallet.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WalletApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WalletRepository walletRepository;

    private final UUID TEST_UUID = UUID.randomUUID();
    private final BigDecimal TEST_AMOUNT = BigDecimal.valueOf(1000);

    @BeforeEach
    void setUp() {
        walletRepository.deleteAll();
        Wallet wallet = new Wallet();
        wallet.setId(TEST_UUID);
        wallet.setAmount(TEST_AMOUNT);
        walletRepository.save(wallet);
    }

    @Test
    void depositTest() throws Exception {
        String json = """
                {
                    "walletId": "%s",
                    "operationType": "DEPOSIT",
                    "amount": 500
                }
                """.formatted(TEST_UUID);

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        BigDecimal testAmount = walletRepository.findById(TEST_UUID).get().getAmount();
        assertThat(testAmount).isEqualByComparingTo("1500");
    }

    @Test
    void withdrawTest() throws Exception {
        String json = """
                {
                    "walletId": "%s",
                    "operationType": "WITHDRAW",
                    "amount": 300
                }
                """.formatted(TEST_UUID);

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        BigDecimal testAmount = walletRepository.findById(TEST_UUID).get().getAmount();
        assertThat(testAmount).isEqualByComparingTo("700");
    }

    @Test
    void getAmountTest() throws Exception {
        mockMvc.perform(get("/api/v1/wallet/" + TEST_UUID))
                .andExpect(status().isOk())
                .andExpect(content().string("1000.00"));
    }
}