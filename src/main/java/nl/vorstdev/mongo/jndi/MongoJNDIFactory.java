package nl.vorstdev.mongo.jndi;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.naming.*;
import javax.naming.spi.ObjectFactory;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Hashtable;

public class MongoJNDIFactory implements ObjectFactory {

    @Override
    public Object getObjectInstance(Object object, Name name, Context context, Hashtable<?, ?> table) throws Exception {
        validateProperty(object, "Invalid JNDI object reference");
        return createMongoClient((Reference) object);
    }

    private Object createMongoClient(Reference reference) throws NamingException, UnknownHostException {
        MongoClientURI mongoClientURI = getUri(reference);
        return new MongoClient(mongoClientURI);
    }

    private MongoClientURI getUri(Reference reference) throws NamingException {
        String uri = null;

        Enumeration<RefAddr> properties = reference.getAll();
        while (properties.hasMoreElements()) {
            RefAddr refAddr = properties.nextElement();
            String propName = refAddr.getType();
            String propValue = (String) refAddr.getContent();
            if (propName.equals("uri")) {
                uri = propValue;
            }
        }
        validateProperty(uri, "Invalid uri value.");
        return new MongoClientURI(uri);
    }


    private void validateProperty(String property, String message) throws NamingException {
        if (property == null || "".equals(property.trim())) {
            throw new NamingException(message);
        }
    }

    private void validateProperty(Object property, String message) throws NamingException {
        if (property == null) {
            throw new NamingException(message);
        }
    }

}
