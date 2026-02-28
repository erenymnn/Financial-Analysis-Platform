package com.Yama.Financial.Analysis.Platform.dto;

import java.math.BigDecimal;

public record TradeRequest(Long userId, String symbol, BigDecimal quantity) {
}
