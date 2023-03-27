package io.ylab.intensive.lesson04.filesort_nobatch;

import io.ylab.intensive.lesson04.DbUtil;

import javax.sql.DataSource;
import java.io.File;
import java.sql.SQLException;

public class FileSorterTest {
  public static void main(String[] args) throws SQLException {
    DataSource dataSource = initDb();
    File data = new File("data.txt");
    FileSorter fileSorter = new FileSortImpl(dataSource);
    long startTime = System.currentTimeMillis();
    File res = fileSorter.sort(data);
    long endTime = System.currentTimeMillis();
    System.out.println("Продолжительность:  " + (endTime - startTime));
  }
  
  public static DataSource initDb() throws SQLException {
    String createSortTable = "" 
                                 + "drop table if exists numbers;" 
                                 + "CREATE TABLE if not exists numbers (\n"
                                 + "\tval bigint\n"
                                 + ");";
    DataSource dataSource = DbUtil.buildDataSource();
    DbUtil.applyDdl(createSortTable, dataSource);
    return dataSource;
  }
}
