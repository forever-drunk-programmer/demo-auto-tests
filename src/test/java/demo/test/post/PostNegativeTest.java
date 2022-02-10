package demo.test.post;

import demo.model.TodoDTO;
import demo.test.AbstractCleanTest;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class PostNegativeTest extends AbstractCleanTest {
    @Test
    public void cantCreateEntityWithSameID() {
        TodoDTO candidate = client.getCache().prepareNewTodo();
        client.createNew(candidate);
        Response response = client.createNewRaw(candidate);

        assertBadRequest(response);
    }

    @Test
    public void idIsNullInRequest() {
        Long id = null;
        TodoDTO candidate = new TodoDTO(id, "Test", false);
        Response response = client.createNewRaw(candidate);

        assertBadRequest(response);
    }

    @Test
    public void noIdInModelInRequest() throws IOException {

        Response response = client.createNewRaw("{\"text\":\"bambaleyla\",\"completed\":false}");

        assertBadRequest(response);
    }

    @Test
    public void textIsNullInRequest() {
        TodoDTO candidate = new TodoDTO(1, null, false);
        Response response = client.createNewRaw(candidate);

        assertBadRequest(response);
    }

    @Test
    public void noTextInRequest() throws IOException {

        Response response = client.createNewRaw("{\"id\":1,\"completed\":false}");

        assertBadRequest(response);
    }

    @Test
    public void completedIsNullInRequest() {
        TodoDTO candidate = new TodoDTO(1, "Test", null);
        Response response = client.createNewRaw(candidate);

        assertBadRequest(response);
    }

    @Test
    public void noCompletedInRequest() throws IOException {

        Response response = client.createNewRaw("{\"id\":1,\"text\":\"bambaleyla\"}");

        assertBadRequest(response);
    }

    @Test
    public void idIsOnlyNumExpected() throws IOException {
        Response response = client.createNewRaw("{\"id\":\"1\",\"text\":\"text\",\"completed\":false}");
        assertBadRequest(response);
    }

    @Test
    public void completedIsOnlyBool() throws IOException {
        Response response = client.createNewRaw("{\"id\":1,\"text\":\"text\",\"completed\":\"false\"}");
        assertBadRequest(response);
    }
}
