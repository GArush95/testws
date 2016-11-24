package ru.arushanyan.test.ws;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;

/**
 * Created by Garushanyan on 15.11.2016.
 */

@WebService(name = "TestWS")
@Stateless
public class TestWS
{

    @WebMethod(operationName = "findString")
    public ArrayList<String> findString(@WebParam(name = "Word") String word, @WebParam(name = "PairOfDates") ArrayList<PairOfDates> pairOfDates)
    {
        return Search.findWord(word, pairOfDates);
    }

}
