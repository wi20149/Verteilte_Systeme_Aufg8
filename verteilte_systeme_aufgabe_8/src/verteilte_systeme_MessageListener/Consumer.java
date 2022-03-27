package verteilte_systeme_MessageListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer {

    public static void main(String[] args) throws NamingException {
    	
    	
    	File f = new File("/Users/kiara/Documents/DHBW_4_SEM/jndi.properties");
    	  
        // Create new file
        // Check if it does not exist
        try {
			if (f.createNewFile())
			    System.out.println("File created");
			else
			    System.out.println("File already exists");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        Properties props = new Properties(); 
        try {
			props.load(new FileInputStream("/Users/kiara/Documents/DHBW_4_SEM/jndi.properties"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        props.setProperty("Name", "Anna");
        System.out.println("Name eingefügt");
        System.out.println(props.getProperty("Name"));
        
        
    	props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory"); 
    	props.setProperty(Context.PROVIDER_URL, "tcp://hostname:61616"); 
    	javax.naming.Context ctx = new InitialContext(props);
    }
}