package demo.test;

import demo.utils.ApplicationClient;
import okhttp3.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.util.LinkedHashSet;
import java.util.Set;

public class AbstractCleanTest {

    protected ApplicationClient client = new ApplicationClient("http://localhost:8080/todos");

    @BeforeEach
    public void prepare() {
        deleteAllTodos();
    }

    protected void deleteAllTodos() {
        Set<Long> allIds = new LinkedHashSet<>(client.getCache().getAllIds());

        allIds.forEach(id -> client.deleteTodos(id));
    }

    protected void assertBadRequest(Response response) {
        Assertions.assertThat(response.code())
                .isEqualTo(400);
        Assertions.assertThat(response.message())
                .isEqualTo("Bad Request");
    }

    protected void assertNotFound(Response response) {
        Assertions.assertThat(response.code())
                .isEqualTo(404);
        Assertions.assertThat(response.message())
                .isEqualTo("Not Found");
    }
}
