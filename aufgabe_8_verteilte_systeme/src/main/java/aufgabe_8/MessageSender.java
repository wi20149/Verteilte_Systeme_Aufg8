package aufgabe_8;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@Component
public class MessageSender {

	//Die Annotation `Autowired` dient bei Spring also der Dependency Injection. Java Annotations bilden seit Java 1.5 und seit Spring 2.5 eine weitere Möglichkeit, die Dependency Injection Konfiguration per Annotations im Code vorzunehmen und nicht per Spring XML Dateien
    @Autowired
    //ConnectionFactory: Client dieses um eine Verbindung zum JMS provider herstellen zu können
    private ConnectionFactory connectionFactory;
    //JMSTemplate - zentrale Klasse vom Spring Package
    private JmsTemplate jmsTemplate;

    //Initialisierungsmethode -- Objektinitialisierungsteil --Erläutern Sie die Methode, die separat ausgeführt werden soll, um Initialisierungsarbeiten nach der Objekterstellung durchzuführen.
    @PostConstruct
    public void init() {
        this.jmsTemplate = new JmsTemplate(connectionFactory);
    }

    public void sendMessage(String message) {
    	
    	//versendet die eigentliche Nachricht über die Warteschlange
        System.out.println("sending: " + message);
        jmsTemplate.send(AppConfig.QUEUE_NAME, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    }
}