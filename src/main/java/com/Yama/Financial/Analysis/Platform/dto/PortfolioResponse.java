package com.Yama.Financial.Analysis.Platform.dto;

import java.math.BigDecimal;

public record PortfolioResponse(
        String symbol,
        BigDecimal quantity,
        BigDecimal currentPrice,
        BigDecimal totalValue
) {
}
