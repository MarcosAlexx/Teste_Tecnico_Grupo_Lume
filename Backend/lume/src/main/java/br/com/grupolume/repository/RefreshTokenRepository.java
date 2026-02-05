package br.com.grupolume.repository;

import br.com.grupolume.model.RefreshToken;
import br.com.grupolume.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken (String token);

    @Modifying
    @Transactional
    void deleteByUser(User user);
}