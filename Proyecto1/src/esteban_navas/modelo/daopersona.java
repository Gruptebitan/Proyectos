/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esteban_navas.modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Esteban Navas
 */
public class daopersona {

    /*Atributos */
    
    private int perid;
    private String pernom;
    private String perpriape;
    private String persegape;
    private boolean perest;
    
    /*Constructor */
    public daopersona() {
    }
    
    /* Propiedades de los atributos Set y Get */
     
    /**
     * @return the perid
     */
    public int getPerid() {
        return perid;
    }
    
    /**
     * @param perid the perid to set
     */
    public void setPerid(int perid) {
        this.perid = perid;
    }

    /**
     * @return the pernom
     */
    public String getPernom() {
        return pernom;
    }

    /**
     * @param pernom the pernom to set
     */
    public void setPernom(String pernom) {
        this.pernom = pernom;
    }

    /**
     * @return the perpriape
     */
    public String getPerpriape() {
        return perpriape;
    }

    /**
     * @param perpriape the perpriape to set
     */
    public void setPerpriape(String perpriape) {
        this.perpriape = perpriape;
    }

    /**
     * @return the persegape
     */
    public String getPersegape() {
        return persegape;
    }

    /**
     * @param persegape the persegape to set
     */
    public void setPersegape(String persegape) {
        this.persegape = persegape;
    }

    /**
     * @return the perest
     */
    public boolean isPerest() {
        return perest;
    }

    /**
     * @param perest the perest to set
     */
    public void setPerest(boolean perest) {
        this.perest = perest;
    }
    
    /* ** Ejecutar procedimientos almacenados**  */
    conesqlserver conexion = new conesqlserver();

    //---------------------------------------------------------------------
    /*NUESTRO METODO AdicionarPersona() -> Se ejecuta el procedimiento almacenado "Addper"
     
     INPUT:
     
     RETURN boolean */
    public boolean AdicionarPersona() {
        Connection cone = null;
        CallableStatement cstm = null;
        boolean Bandera=true;
        try {
            //capturamos la Conexion
            cone = conexion.OpenConnection();
            //Inicio de Transacion 
            cone.setAutoCommit(false);
            //Llamamos el procedimiento almacenado
            cstm = cone.prepareCall("{Call Addper(?,?,?)}");
            //Asignamos los valores de el procedimiento almacenado
            cstm.setString(1, getPernom());
            cstm.setString(2, getPerpriape());
            cstm.setString(3, getPersegape());
            Bandera=cstm.execute();
            cone.commit();
        } catch (Exception e) {
            System.out.println("- Error \n" + e.getMessage());
        } finally {
            conexion.Cerrarproce(cone, cstm);
            conexion.Desconectar();
        }
        return Bandera;
    }

    //---------------------------------------------------------------------
    /*NUESTRO METODO ModificarPersona() -> Se ejecuta el procedimiento almacenado "Updper"
     
     INPUT:
     
     RETURN String */
    public boolean ModificarPersona() {
        Connection cone = null;
        CallableStatement cstm = null;
        boolean Bandera=true;
        try {
            //capturamos la Conexion
            cone = conexion.OpenConnection();
            //Inicio de Transacion 
            cone.setAutoCommit(false);
            //Llamamos el procedimiento almacenado
            cstm = cone.prepareCall("{Call Updper(?,?,?,?)}");
            //Asignamos los valores de el procedimiento almacenado
            cstm.setString(1, getPernom());
            cstm.setString(2, getPerpriape());
            cstm.setString(3, getPersegape());
            cstm.setInt(4, getPerid());
            Bandera=cstm.execute();
            cone.commit();
        } catch (Exception e) {
            System.out.println("- Error \n" + e.getMessage());
        } finally {
            conexion.Cerrarproce(cone, cstm);
            conexion.Desconectar();
        }
        return Bandera;
    }

    //---------------------------------------------------------------------
    /*NUESTRO METODO DeshabilitarPersona() -> Se ejecuta el procedimiento almacenado "Inaper"
     
     INPUT:
     
     RETURN String */
    public boolean DeshabilitarPersona() {
        Connection cone = null;
        CallableStatement cstm = null;
        boolean Bandera=true;
        try {
            //capturamos la Conexion
            cone = conexion.OpenConnection();
            //Inicio de Transacion 
            cone.setAutoCommit(false);
            //Llamamos el procedimiento almacenado
            cstm = cone.prepareCall("{Call Inaper(?)}");
            //Asignamos los valores de el procedimiento almacenado
            cstm.setInt(1, getPerid());
            Bandera=cstm.execute();
            cone.commit();
        } catch (Exception e) {
            System.out.println("- Error \n" + e.getMessage());
        } finally {
            conexion.Cerrarproce(cone, cstm);
            conexion.Desconectar();
        }
        return Bandera;
    }
    
    //---------------------------------------------------------------------
    /*NUESTRO METODO ListadoPersona() -> Se ejecuta el procedimiento almacenado "Lisper"
     
     INPUT:
     
     RETURN ArrayList<> */
    
     public List<Object> ListadoPersona() {
        Connection cone = null;
        CallableStatement cstm = null;
        ResultSet res = null;
        List<Object> lista = null;
        try {
            lista = new ArrayList<>();
            //capturamos la Conexion
            cone = conexion.OpenConnection();
            cstm = cone.prepareCall("{Call Lisper}");
            res = cstm.executeQuery();
            while (res.next()) {
                lista.add(res.getInt("perid")+","+res.getString("pernom")+","+res.getString("perpriape")+","+res.getString("persegape")); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            conexion.Cerrarproceresul(cstm, res);
            conexion.Desconectar();
        }
        return lista;
    }
     
    public String Reportepersona() {
        Connection cone = null;
        String Mensaje = "";
        try {
            //capturamos la Conexion
            cone = conexion.OpenConnection();
            String dir = "C:\\Users\\Carolina\\Documents\\NetBeansProjects\\esteban_navas\\src\\esteban_navas\\reportes\\rptpersona.jrxml";
            JasperReport reporteJasper = JasperCompileManager.compileReport(dir);
            JasperPrint mostrarReporte = JasperFillManager.fillReport(reporteJasper, null, cone);
            JasperViewer.viewReport(mostrarReporte);
        } catch (JRException ex ) {
            Mensaje += ex.getMessage();
        }
        finally
        {
            conexion.Desconectar();
        }
        return Mensaje;
    }
     
    
}
