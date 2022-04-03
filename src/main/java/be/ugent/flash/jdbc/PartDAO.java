package be.ugent.flash.jdbc;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface PartDAO {
    ArrayList<Parts> specificPart(int id) throws DataAccesException;

    ArrayList<ImageParts> specificImagepart(int id) throws DataAccesException;
}
