package demo.utils;

import demo.model.TodoDTO;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.UUID;

//For controlling app memory information
public class TodosRepository {
    private final LinkedHashMap<Long, TodoDTO> cache = new LinkedHashMap<>();

    //Get MAX_ID + 1, because it's simple
    private Long getNewId() {
        Long aLong = cache.keySet().stream()
                .max(Long::compareTo)
                .orElse(0L);
        return aLong + 1;
    }

    public TodoDTO prepareNewTodo() {
        return prepareNewTodo(UUID.randomUUID().toString());
    }

    public TodoDTO prepareNewTodo(String desc) {
        return prepareNewTodo(getNewId(), desc, false);
    }

    public TodoDTO prepareNewTodo(Boolean isCompleted) {
        return prepareNewTodo(getNewId(), UUID.randomUUID().toString(), isCompleted);
    }

    public TodoDTO prepareNewTodo(String desc, Boolean isCompleted) {
        return prepareNewTodo(getNewId(), desc, isCompleted);
    }

    public TodoDTO prepareNewTodo(Long id, String desc, Boolean isCompleted) {
        if (cache.containsKey(id)) {
            throw new RuntimeException("Id already exists");
        }

        TodoDTO todoDTO = new TodoDTO(id, desc, isCompleted);
        cache.put(id, todoDTO);
        return todoDTO;
    }

    public void addNewToCache(TodoDTO todo) {
        cache.put(todo.getId(), todo);
    }

    public void deleteTodo(Long id) {
        cache.remove(id);
    }

    public TodoDTO get(Long id) {
        return cache.get(id);
    }

    public Set<Long> getAllIds() {
        return cache.keySet();
    }

    public Collection<TodoDTO> getAllTodos() {
        return cache.values();
    }

    public boolean isEmpty() {
        return cache.isEmpty();
    }
}
