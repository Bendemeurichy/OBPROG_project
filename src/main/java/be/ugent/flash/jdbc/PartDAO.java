package be.ugent.flash.jdbc;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface PartDAO {
    //vraag parts op van specifieke vraag
    ArrayList<Parts> specificPart(int id) throws DataAccesException;

    //vraag imageparts op van specifieke vraag
    ArrayList<ImageParts> specificImagepart(int id) throws DataAccesException;

    //remove alle parts van een vraag
    void removeParts(int questionID) throws DataAccesException;
}
