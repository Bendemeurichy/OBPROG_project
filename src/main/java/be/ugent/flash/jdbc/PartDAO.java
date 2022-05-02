package be.ugent.flash.jdbc;

import java.util.ArrayList;

public interface PartDAO {
    //vraag answers op van specifieke vraag
    ArrayList<Parts> specificPart(int id) throws DataAccesException;

    //vraag imageparts op van specifieke vraag
    ArrayList<ImageParts> specificImagepart(int id) throws DataAccesException;

    void removeParts(int questionId) throws DataAccesException;

    void addParts(ArrayList<Parts> parts) throws DataAccesException;
}
