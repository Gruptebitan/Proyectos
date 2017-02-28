/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esteban_navas.controlador;

/*se importan las clases y los formularios que se vayan a utilizar */
import esteban_navas.vista.frmpersona;
import esteban_navas.modelo.daopersona;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Esteban Navas
 */
/* ActionListener= es para escuchar un event, en la vista*/
public class contrlpersona implements ActionListener {

    private frmpersona view;
    private daopersona per;

    /*en el controlador iniciamos nuestros objetos */
    public contrlpersona(frmpersona vista, daopersona perso) {
        this.view = vista;
        this.per = perso;
        //Ejecutamos el metodo Iniciar
        iniciar();
    }

    public void iniciar() {
        view.setTitle("INGENIERO INFORMATICO ESTEBAN NAVAS");
        view.setLocationRelativeTo(null);
        view.setExtendedState(frmpersona.MAXIMIZED_BOTH);/* estado maximizado*/
 /*Se añade las acciones del formulario al controlador*/
        this.view.Btn_adicionar.setActionCommand("Addpersona");
        this.view.Btn_Actualizar.setActionCommand("Modpersona");
        this.view.Btn_Deshabilitar.setActionCommand("Despersona");
        this.view.Btn_Report.setActionCommand("Reppersona");
        /*Escucha las acciones del Usuario*/
        view.Btn_adicionar.addActionListener(this);
        view.Btn_Actualizar.addActionListener(this);
         view.Btn_Deshabilitar.addActionListener(this);
        view.Btn_Report.addActionListener(this);
       
    }

    /*Muestra la vista al Usuario*/
    public void go() {
        this.view.setVisible(true);
        this.view.toFront();
        Listar_Personas();
    }

    /*Atento a las acciones del Usuario y las interpreta*/
    public void actionPerformed(ActionEvent e) {
        //Captura en String el comando accionado por el Usuario
        String comando = e.getActionCommand();
        switch (comando) {
            case "Addpersona":
                Adicionar_Persona();
                break;
            case "Modpersona":
                Modificar_Persona();
                break;
            case "Despersona":
                Deshabilitar_Persona();
                break;
            case "Reppersona":
                Reporte_Persona();
                break;
                

        }
    }

    public void Adicionar_Persona() {

        per.setPernom(view.Txtnom.getText().toUpperCase());
        per.setPerpriape(view.Txtpriape.getText().toUpperCase());
        per.setPersegape(view.Txtsegape.getText().toUpperCase());

        if (per.AdicionarPersona() == true) {
            JOptionPane.showMessageDialog(this.view, "Error \n" + "Al insertar la persona");
        } else {
            JOptionPane.showMessageDialog(this.view, "Exito al Insertar ");
            Listar_Personas();
            limpiar_frmper();
        }
    }

    public void Modificar_Persona() {
        per.setPernom(view.Txtnom.getText().toUpperCase());
        per.setPerpriape(view.Txtpriape.getText().toUpperCase());
        per.setPersegape(view.Txtsegape.getText().toUpperCase());
        per.setPerid(Integer.parseInt(view.Jtb_persona.getValueAt(view.Jtb_persona.getSelectedRow(), 0).toString()));
        if (per.ModificarPersona() == true) {
            JOptionPane.showMessageDialog(this.view, "Error \n" + "Actualizar la persona con Id : " + per.getPerid());
        } else {
            Listar_Personas();
            limpiar_frmper();
            JOptionPane.showMessageDialog(this.view, "Exito al Modificar ");
        }
    }

    public void Deshabilitar_Persona() {
        per.setPerid(Integer.parseInt(view.Jtb_persona.getValueAt(view.Jtb_persona.getSelectedRow(), 0).toString()));
        if (per.DeshabilitarPersona() == true) {
            JOptionPane.showMessageDialog(this.view, "Error \n" + "Al desabilitar la persona con Id : " + per.getPerid());
        } else {
            Listar_Personas();
            limpiar_frmper();
            JOptionPane.showMessageDialog(this.view, "Exito al  Deshabilitar ");
        }
    }

    public void Listar_Personas() {
        view.Jtb_persona.removeAll();// removeAll:limpia toda la grilla
        List<Object> lista = per.ListadoPersona();

        if (lista.size() == 0) {
            JOptionPane.showMessageDialog(this.view, "No se encontraron personas Activas");
        } else {
            DefaultTableModel tableModel = new DefaultTableModel();
            Object Colum[] = {"ID", "NOMBRE", "1ª APELLIDO", "2ª APELLIDO"};
            tableModel.setColumnIdentifiers(Colum);
            Object[] fila = new Object[tableModel.getColumnCount()];
            for (int i = 0; i < lista.size(); i++) {
                fila = lista.get(i).toString().split(",");
                tableModel.addRow(fila);
            }
            view.Jtb_persona.setModel(tableModel);
        }
    }
    
    public void Reporte_Persona() {
        String Mensaje = "";
        Mensaje = per.Reportepersona();
        if (Mensaje.length() > 0) {
            JOptionPane.showMessageDialog(this.view, "Error \n" + Mensaje);
        }
    }

    public void limpiar_frmper() {
        view.Txtnom.setText("");
        view.Txtpriape.setText("");
        view.Txtsegape.setText("");
    }

}
 