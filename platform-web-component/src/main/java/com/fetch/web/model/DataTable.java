package com.fetch.web.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataTable implements Serializable {

    private static final long serialVersionUID = 1L;

    private int draw;

    private int recordsTotal;

    private int recordsFiltered;

    private List<String[]> data = new ArrayList<>();

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<String[]> getData() {
        return data;
    }

    public void setData(List<String[]> data) {
        this.data = data;
    }

    public void addData(String[] record) {
        this.data.add(record);
    }
}