package com.csgp.arda.web;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.csgp.arda.domain.Notification;
import com.csgp.arda.domain.User;
import com.csgp.arda.domain.UserRepository;

import jakarta.servlet.http.HttpServletResponse;

//@RepositoryRestController
@RestController
@RequestMapping("/notifications")
@CrossOrigin
public class NotificationController {

    // Mapa para asociar cada usuario con su lista de conexiones SSE
    private static final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final UserRepository userRepository;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public NotificationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public SseEmitter startConnection(HttpServletResponse response) {
        // configuro manualmente los headers CORS para SSE
        response.setHeader("Access-Control-Allow-Origin", "https://congenial-barnacle-qg7qqr744qxf64gv-5173.app.github.dev");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Cache-Control", "no-store");

        System.out.println("*********************************************************************************");
        System.out.println("\n \n \n \n \n \n \n ");
        System.out.println("Entre al controlador start connection 1");
        System.out.println("\n \n \n \n \n \n \n ");
        System.out.println("*********************************************************************************");

        // Obtengo el username desde el contexto de seguridad (JWT)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        System.out.println("*********************************************************************************");
        System.out.println("\n \n \n \n \n \n \n ");
        System.out.println("Entre al controlador start connection 2");
        System.out.println(username);
        System.out.println("\n \n \n \n \n \n \n ");
        System.out.println("*********************************************************************************");


        // Obtengo el id del usuario
        User user = userRepository.findByUsername(username);

        Long userId = user.getId();

        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.put(userId, emitter);

        System.out.println("*********************************************************************************");
        System.out.println("\n \n \n \n \n \n \n ");
        System.out.println("Entre al controlador start connection 3");
        System.out.println(username);
        System.out.println("\n \n \n \n \n \n \n ");
        System.out.println("*********************************************************************************");

        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(() -> emitters.remove(userId));

        // 🔥 Enviar evento de prueba cuando el cliente se conecta
        try {
            emitter.send(SseEmitter.event().name("connected").data("Conexión establecida"));
            System.out.println("MENSAJE INICIAL ENVIADO");
        } catch (IOException e) {
            emitter.complete();
        }

            // 🔥 Mantener viva la conexión con un "ping"
        scheduler.scheduleAtFixedRate(() -> {
            try {
                emitter.send(SseEmitter.event().name("ping").data("keep-alive"));
            } catch (IOException e) {
                emitter.complete();
            }
        }, 10, 30, TimeUnit.SECONDS); // Cada 30 segundos

        System.out.println("*********************************************************************************");
        System.out.println("\n \n \n \n \n \n \n ");
        System.out.println("Entre al controlador start connection 4");
        System.out.println(username);
        System.out.println("\n \n \n \n \n \n \n ");
        System.out.println("*********************************************************************************");

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

