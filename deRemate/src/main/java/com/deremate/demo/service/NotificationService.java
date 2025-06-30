package com.deremate.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deremate.demo.entity.Notification;
import com.deremate.demo.repository.NotificationRepository;

@Service
public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    public void createNotification(String title, String subtitle){
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setSubtitle(subtitle);
        notificationRepository.save(notification);
    }

public List<Notification> getUnreadNotifications() {
    List<Notification> lista = notificationRepository.getAllByRead(false);
    int nuevasOrders = 0;
    List<Notification> resultado = new ArrayList<>();

    for (Notification noti : lista) {
        if ("Nueva orden disponible".equals(noti.getTitle())) {
            nuevasOrders++;
        } else {
            resultado.add(noti);
        }

        noti.setRead(true);
        notificationRepository.save(noti);
    }

    if (nuevasOrders > 0) {
        Notification resumen = new Notification();
        resumen.setTitle("Resumen de nuevas órdenes");
        resumen.setSubtitle("Se ha(n) creado " + nuevasOrders + " órden(es) nueva(s).");
        resultado.add(resumen);
    };

    return resultado;
}

    
}
