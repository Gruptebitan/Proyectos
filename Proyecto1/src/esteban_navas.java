/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Carolina
 */
import esteban_navas.controlador.contrlpersona;
import esteban_navas.modelo.daopersona;
import esteban_navas.vista.frmpersona;
        

public class esteban_navas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        daopersona persona = new daopersona();
        frmpersona frmper = new frmpersona();
        try {
           
            new contrlpersona(frmper,persona).go();
            
        } catch (Exception ex) 
        {}
        
    }
    
}
