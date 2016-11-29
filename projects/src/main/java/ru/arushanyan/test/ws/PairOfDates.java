package ru.arushanyan.test.ws;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Created by Garushanyan on 24.11.2016.
 */
public class PairOfDates {
    public XMLGregorianCalendar dateFrom, dateTo;
        public PairOfDates(XMLGregorianCalendar date1, XMLGregorianCalendar date2) {
            this.dateFrom=date1;
            this.dateTo=date2;
        }
        public PairOfDates(){

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
