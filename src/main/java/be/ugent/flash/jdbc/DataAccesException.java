package be.ugent.flash.jdbc;


public class DataAccesException extends Exception{
    public DataAccesException(String s, Throwable ex) {
        super(s,ex);
    }
}
