package company.gonggam.member.service;

import company.gonggam._core.error.ApplicationException;
import company.gonggam._core.error.ErrorCode;
import company.gonggam._core.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class EmailService {

    private final RedisUtils redisUtils;
    private final JavaMailSender javaMailSender;

    private final String EMAIL_PREFIX = "email:";
    private final long EMAIL_CODE_EXPIRE_TIME = 10L;

    // 만약 애초에 없는 이메일이라면 ?
    @Async
    public void sendEmailCode(String email) {

        String emailCode = generateEmailCode();

        // redis에 <email, 유형, 인증코드> 저장
        redisUtils.setEmailKey(EMAIL_PREFIX + email, "code", emailCode, EMAIL_CODE_EXPIRE_TIME, TimeUnit.MINUTES);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("*** 인증 번호 메일입니다.");
        simpleMailMessage.setText("인증 번호는 \n" + emailCode + "\n 입니다.");

        log.info("sendEmailCode started in thread: {}", Thread.currentThread().getName());

        javaMailSender.send(simpleMailMessage);

        log.info("sendEmailCode completed in thread: {}", Thread.currentThread().getName());
    }

    public String generateEmailCode() {

        int codeLength = 6;

        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < codeLength; i++) {
                builder.append(random.nextInt(10));
            }

            return builder.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new ApplicationException(ErrorCode.NO_SUCH_ALGORITHM);
        }
    }
}
