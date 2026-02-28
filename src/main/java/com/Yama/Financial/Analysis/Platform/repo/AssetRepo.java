package com.Yama.Financial.Analysis.Platform.repo;

import com.Yama.Financial.Analysis.Platform.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssetRepo extends JpaRepository<Asset,Long>
{
    //Diyelim ki bir alım işlemi yapacaksın (buyAsset metodu):
    //
    //assetRepository.findBySymbol("THYAO") metodunu çağırdın.
    //
    //Eğer kullanıcı yanlışlıkla "YAMA-COIN" diye sistemde olmayan bir şey yazdıysa, Optional sana boş döner.
    //
    //Sen de orElseThrow() kullanarak: "Böyle bir hisse sistemde kayıtlı değil!" diye hata fırlatabilirsin.

    Optional<Asset> findBySymbol(String symbol);
}
