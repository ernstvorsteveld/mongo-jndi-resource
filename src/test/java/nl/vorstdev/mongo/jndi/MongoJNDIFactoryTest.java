package nl.vorstdev.mongo.jndi;

import com.mongodb.Mongo;
import org.junit.Before;
import org.junit.Test;

import javax.naming.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class MongoJNDIFactoryTest {

    @Before
    public void initialize() {
    }

    @Test
    public void should_load_factory() throws Exception {
        MongoJNDIFactory factor = new MongoJNDIFactory();

        Reference reference = expectReference();
        Name name = null;
        Context context = null;
        Hashtable table = null;
        Object objectInstance = factor.getObjectInstance(reference, name, context, table);
        assertThat(objectInstance, instanceOf(Mongo.class));
    }

    private Reference expectReference() {
        Reference reference = new Reference("");
        for (RefAddr current : expectRefAddr()) {
            reference.add(current);
        }
        return reference;
    }

    private List<RefAddr> expectRefAddr() {
        List<RefAddr> refAddrList = new ArrayList<RefAddr>();
        RefAddr refAddr = new StringRefAddr("uri", "mongodb://localhost");
        refAddrList.add(refAddr);
        return refAddrList;
    }
}
