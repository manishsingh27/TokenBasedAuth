package com.adms.authz.self.user.repository;

import java.util.Date;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.adms.authz.self.user.model.Users;
import com.adms.authz.self.user.model.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

	VerificationToken findByToken(String token);

	VerificationToken findByUser(Users user);

	Stream<VerificationToken> findAllByExpiryDateLessThan(Date now);

	void deleteByExpiryDateLessThan(Date now);

	@Modifying
	@Query("delete from VerificationToken t where t.expiryDate <= ?1")
	void deleteAllExpiredSince(Date now);

}
