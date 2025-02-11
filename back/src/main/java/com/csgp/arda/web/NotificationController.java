package com.csgp.arda.web;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.csgp.arda.domain.Notification;
import com.csgp.arda.domain.User;
import com.csgp.arda.domain.UserRepository;

@RepositoryRestController
public class NotificationController {

    // Mapa para asociar cada usuario con su lista de conexiones SSE
    private static final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final UserRepository userRepository;

    public NotificationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/notifications")
    public SseEmitter startConnection() {
        // Obtengo el username desde el contexto de seguridad (JWT)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Obtengo el id del usuario
        User user = userRepository.findByUsername(username);

        Long userId = user.getId();

        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.put(userId, emitter);

        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(() -> emitters.remove(userId));

        return emitter;
    }

    public void sendNotification(Long recipientId, String message) {
        SseEmitter emitter = emitters.get(recipientId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().data(message));
            } catch (Exception e) {
                emitters.remove(recipientId);
            }
        }
    }

    public void sendNotification(Notification notification) {
        Long userId = notification.getReceivedBy().getId(); // Obtener el destinatario de la notificación

        SseEmitter emitter = emitters.get(userId); // Obtener su conexión SSE
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().data(notification)); // enviar la notifiación
            } catch (IOException e) {
                emitters.remove(userId); // Remover la conexión si hay error
            }
        }
    }

}
