package com.blackspider.rxdemo;
/*
 *  ****************************************************************************
 *  * Created by : Arhan Ashik on 7/10/2018 at 3:24 PM.
 *  * Email : ashik.pstu.cse@gmail.com
 *  *
 *  * Last edited by : Arhan Ashik on 7/10/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

public class Note {
    int id;
    String note;

    Note(int id, String note) {
        this.id = id;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
