package verteilte_systeme_MessageListener;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer {

    public static void main(String[] args) {
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:8161"); // ActiveMQ-specific
        Connection con = null;
        try {
            con = factory.createConnection();
            Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE); // non-transacted session

            Queue queue = session.createQueue("test.queue"); // only specifies queue name

            MessageProducer producer = session.createProducer(queue);
            Message msg = session.createTextMessage("hello queue"); // text message
            producer.send(msg);

        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close(); // free all resources
                } catch (JMSException e) { /* Ignore */ }
            }
        }
    }
}