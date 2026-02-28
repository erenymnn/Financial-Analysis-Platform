package com.Yama.Financial.Analysis.Platform.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "app_users") // 'user' yerine 'app_users' kullanarak PostgreSQL hatalarını önleriz
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    @Column(precision = 19, scale = 4)
    private BigDecimal balance;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference // "Ben ebeveynim, portfolyoları göster"
    private List<Portfolio> portfolios; // 'porfolios' yazım hatasını düzelttim
}