package com.deremate.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.deremate.demo.entity.Notification;
import com.deremate.demo.entity.User;
import com.deremate.demo.repository.NotificationRepository;
import com.deremate.demo.repository.UserRepository;

@Service
public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    UserRepository userRepository;

    public void createNotification(String title, String subtitle){
        List<User> allUsers = userRepository.findAll();

        for (User user : allUsers) {
            Notification notification = Notification.builder()
                    .title(title)
                    .subtitle(subtitle)
                    .user(user)
                    .build();

            notificationRepository.save(notification);
        }
    }

public List<Notification> getUnreadNotifications() {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    List<Notification> lista = notificationRepository.findByUser(user);

    int nuevasOrders = 0;
    List<Notification> resultado = new ArrayList<>();

    for (Notification noti : lista) {
        if ("Nueva orden disponible".equals(noti.getTitle())) {
            nuevasOrders++;
        } else {
            resultado.add(noti);
        }
        notificationRepository.delete(noti);
    }

    if (nuevasOrders > 0) {
        Notification resumen = new Notification();
        resumen.setTitle("Resumen de nuevas órdenes");
        resumen.setSubtitle("Se ha(n) creado " + nuevasOrders + " órden(es) nueva(s).");
        resultado.add(resumen);
    }

    return resultado;
}

    
}
