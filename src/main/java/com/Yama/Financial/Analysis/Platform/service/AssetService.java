package com.Yama.Financial.Analysis.Platform.service;

import com.Yama.Financial.Analysis.Platform.config.WebClientConfig;
import com.Yama.Financial.Analysis.Platform.dto.BinanceTicker;
import com.Yama.Financial.Analysis.Platform.entity.Asset;
import com.Yama.Financial.Analysis.Platform.repo.AssetRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssetService {
    private final AssetRepo assetRepository;

    private final WebClient  webClient;



    @Scheduled(fixedRate = 60000) // 1 dakikada bir güncelle
    public void updateAssetPrices() {
        List<Asset> assets = assetRepository.findAll();

        for (Asset asset : assets) {
            // Örn: BTC -> BTCUSDT formatına çeviriyoruz Binance için
            String binanceSymbol = asset.getSymbol() + "USDT";

            BinanceTicker ticker = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/v3/ticker/price")
                            .queryParam("symbol", binanceSymbol)
                            .build())
                    .retrieve()
                    .bodyToMono(BinanceTicker.class)
                    .block(); // Şimdilik basitlik için blokluyoruz

            if (ticker != null) {
                asset.setCurrentPrice(new BigDecimal(ticker.price()));
                asset.setLastUpdated(LocalDateTime.now());
                assetRepository.save(asset);
                System.out.println(asset.getSymbol() + " güncel fiyatı Binance'ten alındı: " + ticker.price());
            }
        }
    }

    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    public Optional<Asset> getAssetBySymbol(String symbol) {
        // Repository üzerinden sembole göre varlığı buluyoruz
        // "BTC" gibi aramaları "btc" yazılsa bile bulması için
        // toUpperCase() kullanmak iyi bir pratiktir.
        return assetRepository.findBySymbol(symbol.toUpperCase());
    }
}
