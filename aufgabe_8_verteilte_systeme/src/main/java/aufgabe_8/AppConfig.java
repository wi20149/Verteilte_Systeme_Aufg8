package aufgabe_8;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;

import javax.jms.ConnectionFactory;

@Configuration
@ComponentScan
public class AppConfig {
    public static final String QUEUE_NAME = "example.queue";

    //Bean: Eine JavaBean ist eine Java- Klasse, die einem Komponentenmodell entspricht, um automatisierten Zugriff auf ihre Eigenschaften ( Membervariablen) und Operationen ( Methoden) zu erlauben.
    @Bean
    public ConnectionFactory connectionFactory() {
    	//Apache ActiveMQ ist ein freier Message Broker, der vollständig das Java Message Service 1.1 (JMS) implementiert. Apache ActiveMQ verändert die Verbindungen eines Netzwerks zwischen bestehenden Anwendungen, indem die synchrone Kommunikation zwischen zu integrierenden Applikationen in eine asynchrone Kommunikation umgewandelt wird.
        //hier wird die Verbindung zum Localhost aufgebaut 
    	ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("vm://localhost");
        return connectionFactory;
    }

    @Bean
    public MessageListenerContainer listenerContainer() {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setDestinationName(QUEUE_NAME);
        container.setMessageListener(new JmsListener());
        return container;
    }

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        MessageSender ms = context.getBean(MessageSender.class);
        ms.sendMessage("test message 1");
        ms.sendMessage("test message 2");
        
        
        //beendet die Verbindung zum Server
        System.out.println("-- shutting down listener container --");
        DefaultMessageListenerContainer container = context.getBean(DefaultMessageListenerContainer.class);
        container.shutdown();
    }
}