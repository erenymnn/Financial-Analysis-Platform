package com.Yama.Financial.Analysis.Platform.service;

import com.Yama.Financial.Analysis.Platform.entity.Asset;
import com.Yama.Financial.Analysis.Platform.entity.User;
import com.Yama.Financial.Analysis.Platform.repo.AssetRepo;
import com.Yama.Financial.Analysis.Platform.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepo userRepository;
    private final AssetRepo assetRepository;
    @Override
    public void run(String... args) {
        // Kullanıcıyı oluşturmaya devam etsin (Test için lazım)
        if (userRepository.count() == 0) {
            User user = new User();
            user.setUsername("eren_yaman");
            user.setEmail("eren@test.com");
            user.setBalance(new BigDecimal("10000.00"));
            userRepository.save(user);
        }

        // Sadece sembolleri ekle, fiyatları 0 kalsın (AssetService birazdan güncelleyecek)
        if (assetRepository.count() == 0) {
            saveAsset("BTC");
            saveAsset("ETH");
            saveAsset("SOL"); // Bir tane de Solana ekleyelim istersen
            System.out.println(">>> Test varlıkları sembol olarak oluşturuldu. Fiyatlar Binance'ten bekleniyor...");
        }
    }

    // Yardımcı metod (kod tekrarını önler)
    private void saveAsset(String symbol) {
        Asset asset = new Asset();
        asset.setSymbol(symbol);
        asset.setCurrentPrice(BigDecimal.ZERO); // Başlangıçta 0
        asset.setLastUpdated(LocalDateTime.now());
        assetRepository.save(asset);
    }
}
