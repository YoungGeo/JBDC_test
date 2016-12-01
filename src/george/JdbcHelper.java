package george;

/* author: George Young
 * Date Created: September 16th 2016
 * Last Modified: September 30th 2016
 *
 */
import java.sql.*;
import java.util.*;

public class JdbcHelper {
//Decleared variables
    private Connection connection;
    private ResultSet result;
    private Statement statement;
    private String actsql;
    private PreparedStatement pre;
    private String errorMessage;

    // constructor
    public JdbcHelper() {
        connection = null;
        result = null;
        statement = null;
    }

    //connect method to set up a connection to the database
    public boolean connect(String url, String user, String pass) {
        boolean co = false;
        initJdbcDriver(url);

        try {
            connection = DriverManager.getConnection(url, user, pass);
            statement = connection.createStatement();
            co = true;
        } catch (SQLException ex) {
            System.err.println(ex.getSQLState() + ": " + ex.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return co;
    }
    
    
//Disconnects the connections
    public void disconnect() {

        try {
            result.close();
        } catch (Exception e) {
        }
        try {
            statement.close();
        } catch (Exception e) {
        }
        try {
            connection.close();
        } catch (Exception e) {
        }
    }

    //Will return number of times updated in database
    public int update(String sql, ArrayList<Object> params) {
        int result = -1;
        errorMessage = "";

        // default return value
        try {
// create new prepared statement only if sql was changed
            if (!actsql.equals(sql)) {
                pre = connection.prepareStatement(sql);
                actsql = sql;
            }
            // set all parameters for prepared statement
            if (params != null) {
                setParametersForPreparedStatement(params);
            }
// execute the prepared statement
            result = pre.executeUpdate();
        } catch (SQLException e) {
            errorMessage = e.getSQLState() + ": " + e.getMessage();
            System.err.println(errorMessage);
        } catch (Exception e) {
            errorMessage = e.getMessage();
            System.err.println(errorMessage);
        }
        return result;
    }

    //Automaticly selects which class to use
    private void initJdbcDriver(String url) {
        try {
            if (url.contains("jdbc:mysql")) {
                Class.forName("com.mysql.jdbc.Driver");
            } else if (url.contains("jdbc:oracle")) {
                Class.forName("oracle.jdbc.OracleDriver");
            } else if (url.contains("jdbc:derby")) {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
            } else if (url.contains("jdbc:db2")) {
                Class.forName("com.ibm.db2.jcc.DB2Driver");
            } else if (url.contains("jdbc:postgresql")) {
                Class.forName("org.postgresql.Driver");
            } else if (url.contains("jdbc:sqlite")) {
                Class.forName("org.sqlite.JDBC");
            } else if (url.contains("jdbc:sqlserver")) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            } else if (url.contains("jdbc:sybase")) {
                Class.forName("sybase.jdbc.sqlanywhere.IDriver");
            }

        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    //Excecutes the SQL prepared statements
    public ResultSet query(String sql, ArrayList<Object> params) {
        errorMessage = "";// reset
        result = null;
        // default return value
        try {
            // create new prepared statement only if sql was changed
            if (!sql.equals(actsql)) {
                pre = connection.prepareStatement(sql);
                actsql = sql;
            }//set all parameter values of prepared statement

            if (params != null) {
                setParametersForPreparedStatement(params);
            }
            // execute the prepared statement
            result = pre.executeQuery();
        } catch (SQLException e) {
            errorMessage = e.getSQLState() + ": " + e.getMessage();
            System.err.println(errorMessage);
        } catch (Exception e) {
            errorMessage = e.getMessage();
            System.err.println(errorMessage);
        }
        return result;
    }

    //used to set the params arraylist variables into prepared sql statement
    private void setParametersForPreparedStatement(ArrayList<Object> params) {
        errorMessage = "";
        Object param = null;

        try {

            for (int i = 0; i < params.size(); ++i) {
                param = params.get(i);

                if (param instanceof Integer) {
                    pre.setInt(i + 1, (int) param);
                } else if (param instanceof Double) {
                    pre.setDouble(i + 1, (double) param);
                } else if (param instanceof String) {
                    pre.setString(i + 1, (String) param);
                }
            }

        } catch (SQLException e) {
            errorMessage = e.getSQLState() + ": " + e.getMessage();
            System.err.println(errorMessage);
        } catch (Exception e) {
            errorMessage = e.getMessage();
            System.err.println(errorMessage);
        }
    }

}
