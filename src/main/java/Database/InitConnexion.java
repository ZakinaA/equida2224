/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 *
 * @author Zakina
 */
public class InitConnexion implements ServletContextListener {
  //parametres de connexion
    Connection connection=null;
    String pilotejdbc=null;
    String urlconnexionjdbc=null;
    String utilisateurjdbc=null;
    String motdepassejdbc=null;

    //action déclenchée lors du chargement du context
    public void contextInitialized(ServletContextEvent event)
    {
        System.out.println("----------- Contexte initialisé -----------");

        //lire le contexte
        ServletContext servletContext=event.getServletContext();
        /*pilotejdbc=(String)servletContext.getInitParameter("pilotejdbc");
        urlconnexionjdbc=(String)servletContext.getInitParameter("urlconnexionjdbc");
        utilisateurjdbc=(String)servletContext.getInitParameter("utilisateurjdbc");
        motdepassejdbc=(String)servletContext.getInitParameter("motdepassejdbc");
        */
        try
        {
            //chargement du driver
            //Class.forName("com.mysql.cj.jdbc.Driver");
             Class.forName("org.mariadb.jdbc.Driver");
            System.out.println("Pilote Mariadb JDBC chargé ");
        }
        catch (ClassNotFoundException e) 
        {
            e.printStackTrace();
            System.out.println("Erreur lors du chargemement du pilote" + e.getMessage());
        }

        try
        {
            //obtention de la connexion
             connection = DriverManager.getConnection("jdbc:mariadb://localhost:3307/equida", "root", "");
            //sauvegarder la connexion dans le context
            servletContext.setAttribute("connection",connection);
            System.out.println("Connexion opérationnelle projet Equida " + "jdbc:mariadb//localhost:3306/equimaria");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("Erreur lors de l’établissementde la connexion " + e.getMessage());
        }
    }

    //action qui permet de détruire le filtre
    public void contextDestroyed(ServletContextEvent event)
    {
        System.out.println("----------- Contexte détruit -----------");
        try
        {
            //fermeture
            System.out.println("Connexion fermée ");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            Utilitaire.fermerConnexion(connection);
        }
    }
  
}
