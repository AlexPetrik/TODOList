package ru.alexpetrik.todolist.util;

import ru.alexpetrik.todolist.model.TypeNote;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class NoteTypeAdapter extends XmlAdapter<String, TypeNote>{
    @Override
    public TypeNote unmarshal(String v) throws Exception {
        return TypeNote.valueOf(v);
    }

    @Override
    public String marshal(TypeNote v) throws Exception {
        return v.toString();
    }
}
