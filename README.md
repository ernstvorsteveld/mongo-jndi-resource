You need a running MongoDB instance.


Use this Resource definition in tomact Context.xml:

    <Resource name="bean/MyMongoInstance" auth="Container"
        type="com.mongodb.Mongo"
        factory="nl.vorstdev.mongo.jndi.MongoJNDIFactory"
        uri="mongodb://username:password@host:port" />

A Configuration example:

    @Configuration
    public class MongoConfiguration  {

        @Bean
        public Mongo mongo() throws Exception {
            Context initialContext = new InitialContext();
            Context environmentContext = (Context) initialContext.lookup("java:comp/env");
            return (Mongo) environmentContext.lookup("bean/MyMongoInstance");
        }
    }

Web.xml snippet:

    <resource-env-ref>
        <description>Mongo JNDI configuration</description>
        <resource-env-ref-name>comp/env/bean/MyMongoInstance</resource-env-ref-name>
        <resource-env-ref-type>com.mongodb.Mongo</resource-env-ref-type>
    </resource-env-ref>