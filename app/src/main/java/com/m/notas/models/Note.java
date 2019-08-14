package com.m.notas.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.m.notas.utils.Text;
import com.m.notas.utils.TimestampConverter;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "Notes")
public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "created_date")
    @TypeConverters({TimestampConverter.class})
    private Date createDate;

    public Note(){
        this.createDate = new Date();
    }

    @Ignore
    public Note(String name, String description){
        this.name = name;
        this.description = description;
        this.createDate = new Date();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Text.normalize(name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = Text.normalize(description);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateDate() {
        if( createDate == null) {
            return new Date();
        }
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
