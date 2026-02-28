package com.Yama.Financial.Analysis.Platform.repo;

import com.Yama.Financial.Analysis.Platform.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,Long> {
    // Bir kullanıcının işlem geçmişini tarihe göre sıralı getirmek için
    List<Transaction> findByUserIdOrderByTimestampDesc(Long userId);
}
