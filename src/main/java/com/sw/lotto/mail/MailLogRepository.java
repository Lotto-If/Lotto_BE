package com.sw.lotto.mail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailLogRepository extends JpaRepository<MailLogEntity, Long> {
    List<MailLogEntity> findByRecipientEmail(String recipientEmail);
}
