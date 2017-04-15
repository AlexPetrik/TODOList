package ru.alexpetrik.todolist.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum TypeNote {
    NOTE_TYPE("Заметка"),
    TASKNOTE_TYPE("Задача");

    private String task;

    TypeNote(String task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return task;
    }

    public static ObservableList<Object> getArrayListTypeNote(){
        return FXCollections.observableArrayList(NOTE_TYPE, TASKNOTE_TYPE);
    }

}
