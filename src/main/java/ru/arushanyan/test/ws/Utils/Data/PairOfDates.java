package ru.arushanyan.test.ws.Utils.Data;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Created by Garushanyan on 24.11.2016.
 **/
public class PairOfDates {

    private XMLGregorianCalendar dateFrom, dateTo;

    public PairOfDates(XMLGregorianCalendar dateOne, XMLGregorianCalendar dateTwo) {
        this.dateFrom = dateOne;
        this.dateTo = dateTwo;
    }

    public PairOfDates() {

    }

    public XMLGregorianCalendar getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(XMLGregorianCalendar dateFrom) {
        this.dateFrom = dateFrom;
    }

    public XMLGregorianCalendar getDateTo() {
        return dateTo;
    }

    public void setDateTo(XMLGregorianCalendar dateTo) {
        this.dateTo = dateTo;
    }
}
