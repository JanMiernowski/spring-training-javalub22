package pl.sda.springtrainingjavalub22.external.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaActivationTokenRepository extends JpaRepository<ActivationTokenEntity, Long> {

    Optional<ActivationTokenEntity> findByToken(String token);
}
