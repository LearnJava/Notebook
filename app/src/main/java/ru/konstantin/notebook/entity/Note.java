package ru.konstantin.notebook.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {

    public Note() {}

    public Note(String id, String description, String note, long noteDate) {
        this.id = id;
        this.description = description;
        this.noteText = note;
        this.noteDate = noteDate;
    }

    private String id;
    private String description;
    private String noteText;
    private long noteDate;

    protected Note(Parcel in) {
        id = in.readString();
        description = in.readString();
        noteText = in.readString();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
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
        dest.writeString(id);
        dest.writeString(description);
        dest.writeString(noteText);
        dest.writeLong(noteDate);
    }
}
