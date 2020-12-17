package dev.jooz.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint(value = "/chat")
public class Socket {
    private Session session;
    public static Set<Socket> listeners = new CopyOnWriteArraySet<>();
    private static int onlineCount = 0;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @OnOpen
    public void onOpen(Session session) {
        onlineCount++;
        this.session = session;
        listeners.add(this);

        logger.info("onOpen called. userCount: " + onlineCount);
    }

    @OnClose
    public void onClose(Session session) {
        onlineCount--;
        listeners.remove(this);

        logger.info("onClose called, userCount: " + onlineCount);
    }

    @OnMessage
    public void onMessage(String message){
        logger.info("onMessage called, message: "+message);

        broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable){
        logger.info("onError called, error: "+throwable.getMessage());
        listeners.remove(this);
        onlineCount--;
    }

    public static void broadcast(String message){
        for(Socket listener: listeners){
            listener.sendMessage(message);
        }
    }

    private void sendMessage(String message){
        try{
            this.session.getBasicRemote().sendText(message);
        }catch (IOException e){
            logger.warn("Caught exception while sending message to session "+this.session.getId()+", error: "+e.getMessage());
        }
    }

}
