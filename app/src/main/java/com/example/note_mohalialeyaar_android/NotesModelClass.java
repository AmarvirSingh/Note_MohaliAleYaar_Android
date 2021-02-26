package com.example.note_mohalialeyaar_android;

public class NotesModelClass {

    private int noteID;
    private String noteTitle;
    private String noteDescription;
    private String noteDateTime;
    private String noteLocation;
    private String noteAddress;
    private byte[] noteImage;
    private  int folderID ;

public NotesModelClass(){

}

    public NotesModelClass(String noteTitle, String noteDescription, byte[] noteImage, String noteAddress) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.noteImage = noteImage;
        this.noteAddress = noteAddress;
    }

    public NotesModelClass(int noteID, String noteTitle, String noteDescription, String noteDateTime, int folderID) {
        this.noteID = noteID;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.noteDateTime = noteDateTime;
        this.folderID = folderID;
    }



    public NotesModelClass(int noteID, String noteTitle, String noteDescription, String noteDateTime, String noteLocation, String noteAddress, byte[] noteImage, int folderID) {
        this.noteID = noteID;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.noteDateTime = noteDateTime;
        this.noteLocation = noteLocation;
        this.noteAddress = noteAddress;
        this.noteImage = noteImage;
        this.folderID = folderID;
    }

    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public String getNoteDateTime() {
        return noteDateTime;
    }

    public void setNoteDateTime(String noteDateTime) {
        this.noteDateTime = noteDateTime;
    }

    public String getNoteLocation() {
        return noteLocation;
    }

    public void setNoteLocation(String noteLocation) {
        this.noteLocation = noteLocation;
    }

    public String getNoteAddress() {
        return noteAddress;
    }

    public void setNoteAddress(String noteAddress) {
        this.noteAddress = noteAddress;
    }

    public byte[] getNoteImage() {
        return noteImage;
    }

    public void setNoteImage(byte[] noteImage) {
        this.noteImage = noteImage;
    }
}
