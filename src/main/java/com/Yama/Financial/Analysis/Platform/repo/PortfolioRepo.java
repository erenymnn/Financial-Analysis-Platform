package com.Yama.Financial.Analysis.Platform.repo;

import com.Yama.Financial.Analysis.Platform.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepo extends JpaRepository<Portfolio,Long> {

    // Bir kullanıcıya ait tüm portföyü listelemek için
    List<Portfolio> findByUserId(Long userId);

    // Kullanıcının elinde o hisseden zaten var mı? (Alım yaparken kontrol etmek için)
    Optional<Portfolio> findByUserIdAndAssetId(Long userId,Long assetId);
}
