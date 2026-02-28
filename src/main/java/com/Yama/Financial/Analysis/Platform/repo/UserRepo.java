package com.Yama.Financial.Analysis.Platform.repo;

import com.Yama.Financial.Analysis.Platform.entity.Portfolio;
import com.Yama.Financial.Analysis.Platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo  extends JpaRepository<User,Long> {

    // Özel sorgu: Email ile kullanıcı bulmak gerekirse Optional<User> yazdım. Bu, "kullanıcı veritabanında olmayabilir, null kontrolü yapmayı unutma" demektir. Modern Java dünyasında null dönmek yerine Optional dönmek çok daha güvenlidir.
  //  Optional ile: "Bu veri burada olabilir de, olmayabilir de" mesajını verirsin. Java seni bu veriyi kullanmadan önce kontrol etmeye zorlar.
//User findByEmail(...): "Kesin kullanıcı gelir" sanırsın ama gelmeyebilir.
//
//Optional<User> findByEmail(...): Metodu çağıran kişiye şu uyarıyı verir: "Dikkat! Bu email ile kayıtlı biri olmayabilir, kontrolünü yap!"
    Optional<User> findByEmail(String email);
}
