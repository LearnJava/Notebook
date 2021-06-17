package ru.konstantin.notebook.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Note implements Parcelable {

//    public Note() {}

    public Note(long id, String description, String note, long noteDate) {
        this.id = id;
        this.description = description;
        this.note = note;
        this.noteDate = noteDate;
    }

    private long id;
    private String description;
    private String note;
    private long noteDate;

    protected Note(Parcel in) {
        id = in.readLong();
        description = in.readString();
        note = in.readString();
        noteDate = in.readLong();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(long noteDate) {
        this.noteDate = noteDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(description);
        dest.writeString(note);
        dest.writeLong(noteDate);
    }
}
