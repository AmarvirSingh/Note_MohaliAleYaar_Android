package com.example.note_mohalialeyaar_android;

public class FolderModelClass
{
    private int folderId;
    private String folderName;


    public FolderModelClass(int folderId, String folderName) {
        this.folderId = folderId;
        this.folderName = folderName;
    }



    public int getFolderId() {
        return folderId;
    }

    public void setFolderId(int folderId) {
        this.folderId = folderId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
}
