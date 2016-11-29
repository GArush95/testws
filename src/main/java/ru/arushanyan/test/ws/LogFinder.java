package ru.arushanyan.test.ws;

import ru.arushanyan.test.ws.Core.Search;
import ru.arushanyan.test.ws.Utils.Data.PairOfDates;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.ArrayList;

/**
 * Created by Garushanyan on 15.11.2016.
 */

@WebService(name = "LogFinder")
@Stateless
public class LogFinder
{

    @WebMethod(operationName = "findString")
    public ArrayList<String> findString(@WebParam(name = "Word") String word, @WebParam(name = "PairOfDates") ArrayList<PairOfDates> pairOfDates, @WebParam(name="Location") String location)
    {
        return Search.findWord(word, pairOfDates, location);
    }

}
