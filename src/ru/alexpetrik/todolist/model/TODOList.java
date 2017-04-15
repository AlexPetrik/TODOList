package ru.alexpetrik.todolist.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import ru.alexpetrik.todolist.util.DateUtil;
import ru.alexpetrik.todolist.util.LocalDateAdapter;
import ru.alexpetrik.todolist.util.NoteTypeAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

public class TODOList {

    private TypeNote typeNote;
    private StringProperty captionList;
    private StringProperty descriptionList;
    private ObjectProperty<LocalDate> dateList;
    private ObjectProperty<LocalDate> dateDeadline;
//    private ObjectProperty<Image> typeNoteImg;

    public TODOList() {
        this( null);
    }

    public TODOList(String captionList) {
        this.captionList = new SimpleStringProperty(captionList);
        this.dateList = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 12, 31));
        this.descriptionList = new SimpleStringProperty("");
        this.typeNote = TypeNote.NOTE_TYPE;
        this.dateDeadline = new SimpleObjectProperty<LocalDate>(DateUtil.parse("0"));
//        this.typeNoteImg = new SimpleObjectProperty<Image>(typeNoteProperty());
    }

    public String getCaptionList() {
        return captionList.get();
    }

    public void setCaptionList(String captionList) {
        this.captionList.set(captionList);
    }

    public StringProperty captionListProperty() {
        return captionList;
    }

    public String getDescriptionList() {
        return descriptionList.get();
    }

    public void setDescriptionList(String descriptionList) {
        this.descriptionList.set(descriptionList);
    }

    public StringProperty descriptionListProperty() {
        return descriptionList;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getDateList() {
        return dateList.get();
    }

    public void setDateList(LocalDate dataList) {
        this.dateList.set(dataList);
    }

    public ObjectProperty<LocalDate> dateListProperty() {
        return dateList;
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getDateDeadline() {
        return dateDeadline.get();
    }

    public ObjectProperty<LocalDate> dateDeadlineProperty() {
        return dateDeadline;
    }

    public void setDateDeadline(LocalDate dateDeadline) {
        this.dateDeadline.set(dateDeadline);
    }

    @XmlJavaTypeAdapter(NoteTypeAdapter.class)
    public TypeNote getTypeNote() {
        return typeNote;
    }

    public void setTypeNote(TypeNote typeNote) {
        this.typeNote = typeNote;
    }

    public Image typeNoteProperty(){

        Image img = null;
        TypeNote typeNote = this.typeNote;
        switch (typeNote){
            case NOTE_TYPE:
                img = new Image("file:res/images/notes_type.png");
                break;

            case TASKNOTE_TYPE:
                img = new Image("file:res/images/tasknote_type.png");
                break;
        }

        return img;
    }

//    public ObjectProperty<Image> getTypeNoteImgProperty() {
//        return typeNoteImg;
//    }

}
