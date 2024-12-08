package com.sw.lotto.auth.repository;

import com.sw.lotto.auth.domain.SignOutAccessToken;
import org.springframework.data.repository.CrudRepository;

public interface SignOutAccessTokenRepository extends CrudRepository<SignOutAccessToken, String> {
}
