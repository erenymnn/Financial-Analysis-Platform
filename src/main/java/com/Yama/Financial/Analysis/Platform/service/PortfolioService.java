package com.Yama.Financial.Analysis.Platform.service;

import com.Yama.Financial.Analysis.Platform.dto.PortfolioResponse;
import com.Yama.Financial.Analysis.Platform.entity.Asset;
import com.Yama.Financial.Analysis.Platform.entity.Portfolio;
import com.Yama.Financial.Analysis.Platform.entity.User;
import com.Yama.Financial.Analysis.Platform.repo.PortfolioRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepo portfolioRepository;

    @Transactional
    public void addAssetToPortfolio(User user, Asset asset, BigDecimal quantity) {
        // Kullanıcının bu coinden daha önce alıp almadığına bakıyoruz
        Portfolio portfolio = portfolioRepository.findByUserIdAndAssetId(user.getId(), asset.getId())
                .orElse(new Portfolio());

        if (portfolio.getId() == null) {
            // İlk kez alıyorsa yeni kayıt
            portfolio.setUser(user);
            portfolio.setAsset(asset);
            portfolio.setQuantity(quantity);
        } else {
            // Zaten varsa üzerine ekle
            portfolio.setQuantity(portfolio.getQuantity().add(quantity));
        }

        portfolioRepository.save(portfolio);
    }

    public List<Portfolio> getUserPortfolio(Long userId) {
        return portfolioRepository.findByUserId(userId);
    }

    public List<PortfolioResponse> getUserPortfolioDetails(Long userId) {
        List<Portfolio> portfolios = portfolioRepository.findByUserId(userId);

        return portfolios.stream()
                .map(p -> new PortfolioResponse(
                        p.getAsset().getSymbol(),
                        p.getQuantity(),
                        p.getAsset().getCurrentPrice(),
                        p.getQuantity().multiply(p.getAsset().getCurrentPrice())
                ))
                .collect(Collectors.toList());
    }

}
