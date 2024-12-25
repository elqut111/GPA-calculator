
package project;
import java.sql.*;

public class connectionprovider {
    public static Connection getcon()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","0380");
            return con;
        }
        catch(Exception e)
        {
            System.out.println(e);
            return null;
        }
                
    }

    public static Connection getCon() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
