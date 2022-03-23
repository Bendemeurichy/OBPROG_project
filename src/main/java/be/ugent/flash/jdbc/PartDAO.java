package be.ugent.flash.jdbc;

public interface PartDAO {
    Iterable<Parts> specificPart(int id) throws DataAccesException;
}
