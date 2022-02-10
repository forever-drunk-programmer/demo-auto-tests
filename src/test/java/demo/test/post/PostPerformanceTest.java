package demo.test.post;

import demo.model.TodoDTO;
import demo.test.AbstractCleanTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PostPerformanceTest extends AbstractCleanTest {
    @Test
    public void test() {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        long start = System.nanoTime();
        executor.execute(() -> {
            for (int i = 1; i <= 1000; i++) {
                try {
                    client.createNewRaw("{\"id\":" + i + ",\"text\":\"text\",\"completed\":false}");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        executor.execute(() -> {
            for (int i = 1001; i <= 2000; i++) {
                try {
                    client.createNewRaw("{\"id\":" + i + ",\"text\":\"text\",\"completed\":false}");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        executor.execute(() -> {
            for (int i = 2001; i <= 3000; i++) {
                try {
                    client.createNewRaw("{\"id\":" + i + ",\"text\":\"text\",\"completed\":false}");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        executor.execute(() -> {
            for (int i = 3001; i <= 4000; i++) {
                try {
                    client.createNewRaw("{\"id\":" + i + ",\"text\":\"text\",\"completed\":false}");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        long end = System.nanoTime();
        System.out.println("Total time: " + (end - start));
        List<TodoDTO> todos = client.getTodos();
        Assertions.assertThat(todos)
                .hasSize(4000);
    }
}
