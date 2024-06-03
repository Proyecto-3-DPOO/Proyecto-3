package main.java.open.dpoo.model.galeria.piece;

import java.util.Date;

public class TemporalLocation {
    private Date startDate;
    private Date limitDate;

    public TemporalLocation(Date startDate, Date limitDate) {
        this.startDate = startDate;
        this.limitDate = limitDate;
    }

    @Override public String toString() {
        return "TemporalLocation{" + "startDate=" + startDate + ", limitDate=" + limitDate + '}';
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(Date limitDate) {
        this.limitDate = limitDate;
    }
}
