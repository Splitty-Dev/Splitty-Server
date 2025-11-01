package com.chaegangjo.firebase;

import static com.chaegangjo.exception.errorcode.FirebaseErrorCode.FIREBASE_INITIALIZATION_ERROR;
import static com.chaegangjo.firebase.FirebaseProperties.FIREBASE_SERVICE_ACCOUNT_KEY_PATH;

import com.chaegangjo.exception.FirebaseException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Slf4j
@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initializeFirebase() throws FirebaseException {
        try {
            InputStream serviceAccount = new ClassPathResource(FIREBASE_SERVICE_ACCOUNT_KEY_PATH).getInputStream();
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            //FirebaseApp 초기화 여부 확인
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            throw new FirebaseException(FIREBASE_INITIALIZATION_ERROR);
        }
    }
}
