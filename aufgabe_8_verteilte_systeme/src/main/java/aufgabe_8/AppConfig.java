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

    @Bean
    public ConnectionFactory connectionFactory() {
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

        System.out.println("-- shutting down listener container --");
        DefaultMessageListenerContainer container = context.getBean(DefaultMessageListenerContainer.class);
        container.shutdown();
    }
}