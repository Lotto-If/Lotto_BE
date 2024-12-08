package com.sw.lotto.account.domain;

import com.sw.lotto.auth.model.SignUpDto;
import com.sw.lotto.global.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * AccountEntity
 * 인덱스: IDX_ACCOUNT_SIGN_IN_ID
 */
@Getter
@Setter
@Entity(name = "account")
@Table(
        indexes = {
                @Index(name = "IDX_ACCOUNT_SIGN_IN_ID", columnList = "SIGN_IN_ID")
        }
)
public class AccountEntity extends BaseEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "account_sequence"
    )
    @SequenceGenerator(
            name = "account_sequence",
            sequenceName = "account_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @EqualsAndHashCode.Include
    @Column(name = "OID", nullable = false, unique = true)
    protected Long objectId;
    @Column(nullable = false, unique = true)
    private String signInId;
    private String password;
    private String email;
    private String userName;
    @Column(columnDefinition = "TEXT")
    private String profileImage;
    private Boolean emailNotification;  // 이메일 알림 허용 여부
    private LocalDateTime lastSignInAt;

    public static AccountEntity of(SignUpDto signUpDto) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.signInId = signUpDto.getSignInId();
        accountEntity.password = signUpDto.getPassword();
        accountEntity.email = signUpDto.getEmail();
        accountEntity.userName = signUpDto.getUsername();
        accountEntity.profileImage = signUpDto.getProfileImage();
        accountEntity.emailNotification = signUpDto.getEmailNotification();
        return accountEntity;
    }

}
