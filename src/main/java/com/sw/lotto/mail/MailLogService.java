package com.sw.lotto.mail;

import com.sw.lotto.account.domain.UserLottoEntity;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailLogService {

    private final JavaMailSender mailSender;
    private final MailLogRepository mailLogRepository; // 전송 이력 저장소

    public void sendEmail(MailLogEntity mailLogEntity) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(mailLogEntity.getRecipientEmail());
            helper.setSubject(mailLogEntity.getTitle());
            helper.setText(mailLogEntity.getContent(), true);

            mailSender.send(message);

            mailLogEntity.setSentSuccessfully(true);
        } catch (Exception e) {
            mailLogEntity.setSentSuccessfully(false);
            mailLogEntity.setErrorMessage(e.getMessage());
        } finally {
            mailLogRepository.save(mailLogEntity);
        }
    }

    public MailLogEntity preparePrizeNotificationEmail(UserLottoEntity userLotto, String prizeRank, long matchCount, String finalNumber) {
        String subject = "로또 당첨 결과 알림 - " + userLotto.getRound() + "회차";
        String content = buildEmailContent(userLotto, prizeRank, matchCount, finalNumber);
        return MailLogEntity.create(userLotto.getAccount().getEmail(), subject, content, "LottoNotification");
    }

    private String buildEmailContent(UserLottoEntity userLotto, String prizeRank, long matchCount, String finalNumber) {
        return "<html>" +
                "<body>" +
                "<h1>로또 당첨 결과</h1>" +
                "<p>안녕하세요, " + userLotto.getAccount().getUserName() + "님.</p>" +
                "<p>예측하신 번호: " + userLotto.getPredictedNumbers() + "</p>" +
                "<p>당첨 번호: " + finalNumber + "</p>" +
                "<p>맞춘 개수: " + matchCount + "개</p>" +
                "<p>당첨 등수: " + prizeRank + "</p>" +
                "<p>감사합니다. 다음 회차도 행운을 빕니다!</p>" +
                "</body>" +
                "</html>";
    }
}