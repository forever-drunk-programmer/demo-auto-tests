package demo.test.delete;

import demo.test.AbstractCleanTest;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class DeleteNegativeTest extends AbstractCleanTest {
    @Test
    public void deleteOnEmpty() throws IOException {
        Response response = client.deleteTodosRaw(1L);
        assertNotFound(response);
    }

    @Test
    public void deleteNotExisted() throws IOException {
        client.createNew(client.getCache().prepareNewTodo());
        Response response = client.deleteTodosRaw(10L);
        assertNotFound(response);
    }

    @Test
    public void deleteWhenIdIsNull() throws IOException {
        Long id = null;
        Response response = client.deleteTodosRaw(id);
        assertNotFound(response);
    }

    @Test
    public void deleteWhenIsIsString() throws IOException {
        Response response = client.deleteTodosRaw("test");
        assertNotFound(response);
    }
}
