package com.Yama.Financial.Analysis.Platform.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    //işlem geçmişi

    public enum TransactionType {
        BUY, SELL
    }
    @Enumerated(EnumType.STRING) // Veritabanına 0-1 değil "BUY" veya "SELL" yazar.
    private TransactionType type;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(precision = 19, scale = 8)
    private BigDecimal amount; // kaç adet satın alındı
    @Column(precision = 19, scale = 4)
    private BigDecimal priceAtTransaction; // işlem anındaki fiyat yarın fiyat degişse bile kullanıcının kaçtan aldıgını bilmemiz gerekir
    private LocalDateTime timestamp; // (İşlemin yapıldığı tarih ve saat).

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"portfolios", "transactions", "balance", "email"})//// User içindeki gereksiz ve döngü yaratacak alanları görmezden geliyoruz
    private User user;

    @ManyToOne
    @JoinColumn(name = "asset_id")
    @JsonIgnoreProperties({"portfolios"})
    private Asset asset;

}
