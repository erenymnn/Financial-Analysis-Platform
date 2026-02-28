package com.Yama.Financial.Analysis.Platform.controller;

import com.Yama.Financial.Analysis.Platform.dto.PortfolioResponse;
import com.Yama.Financial.Analysis.Platform.entity.Portfolio;
import com.Yama.Financial.Analysis.Platform.repo.PortfolioRepo;
import com.Yama.Financial.Analysis.Platform.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<PortfolioResponse>> getUserPortfolio(@PathVariable Long userId) {
        List<PortfolioResponse> response = portfolioService.getUserPortfolioDetails(userId);

        if (response.isEmpty()) {
            return ResponseEntity.noContent().build(); // Kullanıcının hiç varlığı yoksa 204 döner
        }

        return ResponseEntity.ok(response);
    }



}