package com.deremate.demo.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.deremate.demo.entity.PushToken;
import com.deremate.demo.repository.PushTokenRepository;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class NotificationServiceImpl {

    private final PushTokenRepository pushTokenRepository;
    private final RestTemplate restTemplate = new RestTemplate(); // para enviar a Expo

    private static final String EXPO_API_URL = "https://exp.host/--/api/v2/push/send";

    public void enviarNotificacion(String title, String body) {
        List<PushToken> tokens = pushTokenRepository.findAll();
        for (PushToken pushToken : tokens) {
            Map<String, Object> notification = new HashMap<>();
            notification.put("to", pushToken.getToken());
            notification.put("title", title);
            notification.put("body", body);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(notification, headers);

            try {
                restTemplate.postForEntity(EXPO_API_URL, entity, String.class);
            } catch (Exception e) {
                System.err.println("Error enviando notificación: " + e.getMessage());
            }
        }
    }
}
