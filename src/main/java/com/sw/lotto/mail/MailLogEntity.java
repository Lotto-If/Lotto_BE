package com.sw.lotto.mail;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Entity(name = "mailLog")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mailLog", indexes = {
        @Index(name = "idx_sent_successfully", columnList = "sentSuccessfully")
})
public class MailLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String recipientEmail;
    private String title;
    private String content;

    @Column(nullable = false)
    private LocalDateTime sentTime;

    @Column(nullable = false)
    private boolean sentSuccessfully;
    private String errorMessage;
    private String emailType;

    public static MailLogEntity create(String recipientEmail, String title, String content, String emailType) {
        MailLogEntity mailLogEntity = new MailLogEntity();
        mailLogEntity.setRecipientEmail(recipientEmail);
        mailLogEntity.setTitle(title);
        mailLogEntity.setContent(content);
        mailLogEntity.setEmailType(emailType);

        return mailLogEntity;
    }
}

