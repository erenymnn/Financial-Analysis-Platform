package com.Yama.Financial.Analysis.Platform.controller;


import com.Yama.Financial.Analysis.Platform.entity.Asset;
import com.Yama.Financial.Analysis.Platform.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    // 1. Tüm varlıkları listeleme (Frontend'deki market sayfası için)
    @GetMapping
    public ResponseEntity<List<Asset>> getAllAssets() {
        List<Asset> assets = assetService.getAllAssets();
        return ResponseEntity.ok(assets);
    }

    // 2. Belirli bir varlığın detayını getirme
    @GetMapping("/{symbol}")
    public ResponseEntity<Asset> getAssetBySymbol(@PathVariable String symbol) {
        return assetService.getAssetBySymbol(symbol) // Service'e bu metodu ekleyebilirsin
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Manuel fiyat güncelleme tetikleyicisi (Admin paneli için gibi düşün)
    // Bunu @PostMapping yapmak daha doğrudur çünkü bir veri değişikliği tetikler
    @PostMapping("/sync-prices")
    public ResponseEntity<String> syncPrices() {
        try {
            assetService.updateAssetPrices();
            return ResponseEntity.ok("Fiyatlar Binance üzerinden başarıyla güncellendi.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Fiyat senkronizasyonu başarısız: " + e.getMessage());
        }
    }
}