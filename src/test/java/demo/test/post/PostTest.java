package demo.test.post;

import demo.test.AbstractCleanTest;
import okhttp3.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class PostTest extends AbstractCleanTest {
    @Test
    public void formatPostTest() throws IOException {
        Response response = client.createNewRaw("{\"id\":1,\"text\":\"text\",\"completed\":false}");
        Assertions.assertThat(response.code())
                .isEqualTo(201);
    }

    @Test
    public void canBeEmptyText() throws IOException {
        Response response = client.createNewRaw("{\"id\":1,\"text\":\"\",\"completed\":false}");
        Assertions.assertThat(response.code())
                .isEqualTo(201);
    }

    @Test
    public void completedTrueAndFalse() throws IOException {
        Response response1 = client.createNewRaw("{\"id\":1,\"text\":\"\",\"completed\":false}");
        Response response2 = client.createNewRaw("{\"id\":2,\"text\":\"\",\"completed\":true}");
        Assertions.assertThat(response1.code())
                .isEqualTo(201);
        Assertions.assertThat(response2.code())
                .isEqualTo(201);
    }
}
