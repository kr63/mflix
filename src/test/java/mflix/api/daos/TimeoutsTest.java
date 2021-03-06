package mflix.api.daos;

import com.mongodb.ConnectionString;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import mflix.config.MongoDBConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = {CommentDao.class, MongoDBConfiguration.class})
@EnableConfigurationProperties
@EnableAutoConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class TimeoutsTest extends TicketTest {

    @Autowired
    MongoClient mongoClient;
    @Value("${spring.mongodb.database}")
    String databaseName;
    private String mongoUri;
    private MovieDao movieDao;

    @Before
    public void setUp() throws IOException {
        this.movieDao = new MovieDao(mongoClient, databaseName);
        mongoUri = getProperty("spring.mongodb.uri");
        mongoClient = MongoClients.create(mongoUri);
    }

    @Test
    public void testConfiguredWtimeout() {
        WriteConcern wc = this.movieDao.mongoClient.getDatabase("mflix").getWriteConcern();

        Assert.assertNotNull(wc);
        Integer expected = 2500;
        Optional<Integer> actual = Optional.ofNullable(wc.getWTimeout(TimeUnit.MILLISECONDS));
        Assert.assertEquals("Configured `wtimeout` not set has expected",
                expected, actual.orElse(null));
    }

    @Test
    public void testConfiguredConnectionTimeoutMs() {
        ConnectionString connectionString = new ConnectionString(mongoUri);
        Integer expected = 2000;
        Optional<Integer> actual = Optional.ofNullable(connectionString.getConnectTimeout());
        Assert.assertEquals("Configured `connectionTimeoutMS` does not match expected",
                expected, actual.orElse(null));
    }
}
