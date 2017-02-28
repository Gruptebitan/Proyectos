/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esteban_navas.modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
/**
 *
 * @author Esteban Navas
 */
public class conesqlserver {

    /*datos para la conexion*/
    private String user = "user123";
    private String pass = "123456789";
    private String bd = "prueba";
    private String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
   String url="jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=prueba;";
    //String url="jdbc:sqlserver://localhost:1433;databaseName=prueba;";
    Connection con = null;

    //----------------------------------------------------------------
    /*CONSTRUCTOR */
    public conesqlserver() {
    }

    //---------------------------------------------------------------------
    /*PARA ABRIR LA CONEXION */
    public Connection OpenConnection() {
       try {
            //obtenemos el driver para SQLSERVER
            Class.forName(driver);
            //obtenemos la conexion
            con = DriverManager.getConnection(url, user, pass);
            if (con != null) {
                System.out.println("OK base de datos " + bd + " listo");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
       return con;
    }

    //---------------------------------------------------------------------
    /*CERRAR LA CONEXION */
    public void Desconectar() {
        con = null;
        System.out.println("La conexion a la  base de datos " + bd + " a terminado");
    }

    //---------------------------------------------------------------------
    /*NUESTRO METODO Execomando SE PODRA INSERT,UPDATE,DELETE
     
     INPUT:
     SQLSTRING
     RETURN string*/
    public String Execomando(String Comsql) {
        String Mensaje = "";
        /* sino retorna nada es porque todo esta bien */
        try {
            PreparedStatement pstm = con.prepareStatement(Comsql);
            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            Mensaje = e.toString();
        }
        return Mensaje;
    }

    //---------------------------------------------------------------------
    /*NUESTRO METODO Consulta SE PODRA SELECT
     
     INPUT:
     SQLSTRING
     RETURN Object[][] */
    public Object[][] Consulta(String Comsql) {
        Object Datos[][] = null;
        try {
            PreparedStatement pstm = con.prepareStatement(Comsql);
            /* declarando ResulSet que va a contener el resultado de la ejecucion del Query */
            ResultSet rs = pstm.executeQuery();
            /*se ubica en el ultimo registro, para saber cuantos ahi */
            rs.last();
            /*para saber el numero de filas y columnas  del ResulSet */
            ResultSetMetaData rsmd = rs.getMetaData();
            /*aqui muestra la cantidad de filas y colmnas ahi */
            int numCols = rsmd.getColumnCount();
            int numFils = rs.getRow();
            /*cojemos nuestro objeto datos y le damos formato, eso es igual a numero de filas y numero de columnas */
            Datos = new Object[numFils][numCols];
            /* nos ubicamos antes de la primera fila */
            rs.beforeFirst();

            /*j= filas
              i= columnas
             */
            int j = 0;
            /*recorremos los datos del RS
             */
            while (rs.next()) {
                /*i siempre se inicializa en cero; la condicion va hasta que i sea menor que e numcol; y aumenta de 1 en 1  */
                for (int i = 0; i < numCols; i++) {
                    /*aqui le asignamos valor a nuestro arreglo */
                    Datos[j][i] = rs.getObject(i + 1);
                }
                j++;
            }
            pstm.close();
            rs.close();
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
        return Datos;
    }

    //----------------------------------------------------------------
    /* ** Manejo de procesidimientos almacenados  ** */
    //---------------------------------------------------------------------
    /*NUESTRO METODO Cerrarproce SE PODRA -> Cerrar el procedimiento almacenado
     
     INPUT:
     Connection , CallableStatement 
     */
    public void Cerrarproce(Connection cone, CallableStatement cstm) {
        try {
            if (cone != null) {
                cone.close();
            }
            if (cstm != null) {
                cstm.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //---------------------------------------------------------------------
    /*NUESTRO METODO Cerrarproceresul SE PODRA -> Cerrar el procedimiento almacenado y el ResultSet
     INPUT:
     Connection , ResultSet
     */
    public void Cerrarproceresul(CallableStatement cstm, ResultSet resul) {
        try {
            if (resul != null) {
                resul.close();
            }
            if (cstm != null) {
                cstm.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
