package com.Yama.Financial.Analysis.Platform.service;

import com.Yama.Financial.Analysis.Platform.entity.Asset;
import com.Yama.Financial.Analysis.Platform.entity.Portfolio;
import com.Yama.Financial.Analysis.Platform.entity.Transaction;
import com.Yama.Financial.Analysis.Platform.entity.User;
import com.Yama.Financial.Analysis.Platform.repo.AssetRepo;
import com.Yama.Financial.Analysis.Platform.repo.PortfolioRepo;
import com.Yama.Financial.Analysis.Platform.repo.TransactionRepo;
import com.Yama.Financial.Analysis.Platform.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    //Servis Adı	Sorumluluk Alanı
    //UserService	Kullanıcı bakiyesi güncelleme, kullanıcı oluşturma.
    //AssetService	Dış API'den fiyat çekme, yeni hisse ekleme.
    //TradeService	Ana Orkestra Şefi. Alım-satım mantığını yönetir; UserService ve AssetService'i çağırarak işleri birleştirir.

    private final UserRepo userRepo;
    private final AssetRepo assetRepo;
    private final PortfolioRepo portfolioRepo;
    private final TransactionRepo transactionRepo;

    @Transactional // Bu metod içindeki her şey ya hep gerçekleşir ya hiç!
    public Transaction buyAsset(Long userId, String symbol, BigDecimal quantity) {
        // 1. Kullanıcıyı bul (Yoksa hata fırlat)
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Asset asset = assetRepo.findBySymbol(symbol) //2. Varlığı (Hisse/Kripto) bul
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        //3. Toplam maliyeti hesapla (Fiyat * Miktar) * yerine multiply
        BigDecimal totalCost = asset.getCurrentPrice().multiply(quantity);

        if (user.getBalance().compareTo(totalCost) < 0) {

            throw new RuntimeException("Insufficient funds: " + totalCost);

        }

        user.setBalance(user.getBalance().subtract(totalCost)); // - yerine subtract kullandım
        userRepo.save(user);

        updatePortfolio(user, asset, quantity);

        Transaction transaction = saveTransaction(user, asset, quantity, asset.getCurrentPrice(), Transaction.TransactionType.BUY);

        return transaction;

    }



    @Transactional
    public void sellAsset(Long userId, String symbol, BigDecimal quantity) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Asset asset = assetRepo.findBySymbol(symbol)
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        // findByUserIdAndAssetId metodunun repo'da tanımlı olduğundan emin ol
        Portfolio portfolio = portfolioRepo.findByUserIdAndAssetId(userId, asset.getId())
                .orElseThrow(() -> new RuntimeException("Portfolio not found"));

        if (portfolio.getQuantity().compareTo(quantity) < 0) {
            throw new RuntimeException("Insufficient quantity in portfolio: " + quantity);
        }

        BigDecimal totalEarnings = asset.getCurrentPrice().multiply(quantity);

        // Güncelleme: Bakiyeye PARA ekle
        user.setBalance(user.getBalance().add(totalEarnings));
        userRepo.save(user);

        // Güncelleme: Portföyden ADET çıkar
        portfolio.setQuantity(portfolio.getQuantity().subtract(quantity));

        if (portfolio.getQuantity().compareTo(BigDecimal.ZERO) == 0) {
            portfolioRepo.delete(portfolio);
        } else {
            portfolioRepo.save(portfolio);
        }

        // İşlemi kaydet
        saveTransaction(user, asset, quantity, asset.getCurrentPrice(), Transaction.TransactionType.SELL);
    }

    private Transaction saveTransaction(User user, Asset asset, BigDecimal quantity, BigDecimal currentPrice, Transaction.TransactionType transactionType) {
        Transaction transaction = new Transaction();
        transaction.setAsset(asset);
        transaction.setUser(user);
        transaction.setAmount(quantity);// Kaç adet alındı
        transaction.setPriceAtTransaction(currentPrice);// O anki fiyat neydi
        transaction.setType(transactionType);// BUY veya SELL
        transaction.setTimestamp(LocalDateTime.now());// Tam şu anki zaman

        return transactionRepo.save(transaction);
    }

    private Portfolio updatePortfolio(User user, Asset asset, BigDecimal quantity) {
// Kullanıcının elinde bu varlıktan zaten var mı kontrol et
        Portfolio portfolio = portfolioRepo.findByUserIdAndAssetId(user.getId(), asset.getId())
                .orElse(new Portfolio());
// Eğer yeni oluşturulduysa (id null ise) bağlantıları kur
        if (portfolio.getUser() == null) {
            portfolio.setUser(user);
            portfolio.setAsset(asset);
            portfolio.setQuantity(BigDecimal.ZERO);// Başlangıç miktarını 0 yap
        }

        //Mevcut miktarın üzerine yeni miktarı ekle (add metodu)
        portfolio.setQuantity(portfolio.getQuantity().add(quantity));

        return portfolioRepo.save(portfolio);
    }


    public List<Transaction> findByUserIdOrderByTimestampDesc(Long userId) {
        // Repository'deki aynı isimli metodu çağırıyoruz
        return transactionRepo.findByUserIdOrderByTimestampDesc(userId);
    }
}




