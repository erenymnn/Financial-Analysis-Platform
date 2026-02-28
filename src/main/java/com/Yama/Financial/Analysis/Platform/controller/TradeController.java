package com.Yama.Financial.Analysis.Platform.controller;

import com.Yama.Financial.Analysis.Platform.dto.TradeRequest;
import com.Yama.Financial.Analysis.Platform.entity.Transaction;
import com.Yama.Financial.Analysis.Platform.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/trade")
@RequiredArgsConstructor
public class TradeController {

    private final TransactionService transactionService;

    @PostMapping("/buy")
    public ResponseEntity<?> buy(@RequestBody TradeRequest request) { // @RequestBody ve DTO kullandık
        try {
            Transaction transaction = transactionService.buyAsset(
                    request.userId(),
                    request.symbol(),
                   request.quantity()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
        } catch (RuntimeException e) {
            // Servisten gelen "Bakiye yetersiz" gibi hataları burada yakalayıp kullanıcıya mesaj atıyoruz
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/sell") // Satış işlemi için POST
    public ResponseEntity<?> sell(@RequestBody TradeRequest request) {
        try {
            transactionService.sellAsset(request.userId(), request.symbol(), request.quantity());
            return ResponseEntity.ok("Satış işlemi başarıyla gerçekleşti.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // Kullanıcının tüm geçmiş işlemlerini getirir
    @GetMapping("/{userId}")
    public List<Transaction> getTransactionHistory(@PathVariable Long userId) {
        // Not: Repo'da findByUserId metodunu tanımlamayı unutma!
        return transactionService.findByUserIdOrderByTimestampDesc(userId);
    }


}

