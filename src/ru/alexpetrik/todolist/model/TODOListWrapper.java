package ru.alexpetrik.todolist.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "todoLists")
public class TODOListWrapper {

    private List<TODOList> todoLists;

    @XmlElement(name = "todoList")
    public List<TODOList> getTodoLists() {
        return todoLists;
    }

    public void setTodoLists(List<TODOList> todoLists) {
        this.todoLists = todoLists;
    }
}
