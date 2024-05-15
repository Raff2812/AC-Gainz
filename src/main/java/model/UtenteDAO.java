package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAO {

    public Utente doRetrieveByEmail(String email)
    {
        try(Connection con= ConPool.getConnection())
        {
            PreparedStatement preparedStatement=con.prepareStatement("SELECT email,password,nome,cognome,codice_fiscale,data_di_nascita,indirizzo,numero_di_cellulare,poteri FROM utente WHERE email=?");
            preparedStatement.setString(1,email);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
            {
                Utente u=new Utente(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getDate(6),resultSet.getString(7),resultSet.getString(8),resultSet.getInt(9));
                return u;
            }
            return null;

        }
        catch (SQLException sqlException)
        {
            throw new RuntimeException(sqlException);
        }
    }

    public void doSave(Utente utente) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO utente (email,password,nome,cognome,codice_fiscale,data_di_nascita,indirizzo,numero_di_cellulare,poteri) VALUES(?,?,?,?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, utente.getEmail());
            ps.setString(2, utente.getPassword());
            ps.setString(3, utente.getNome());
            ps.setString(4, utente.getCognome());
            ps.setString(5, utente.getCodiceFiscale());
            ps.setDate(6, utente.getDataNascita());
            ps.setString(7, utente.getIndirizzo());
            ps.setString(8, utente.getTelefono());
            ps.setInt(9, utente.getPoteri());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Utente> doRetrieveAll(){

        ArrayList<Utente> utenti = new ArrayList<>();

        Statement st;

        ResultSet rs;

        Utente p;

        try (Connection con = ConPool.getConnection()) {

            st = con.createStatement();

            rs = st.executeQuery("SELECT * FROM utente");

            while(rs.next()) {

                p = new Utente();

                p.setEmail(rs.getString(1));

                p.setPassword(rs.getString(2));

                p.setNome(rs.getString(3));

                p.setCognome(rs.getString(4));
                p.setCodiceFiscale(rs.getString(5));

                p.setDataNascita(rs.getDate(6));

                p.setIndirizzo(rs.getString(7));

                p.setTelefono(rs.getString(8));
                p.setPoteri(rs.getInt(9));

                utenti.add(p);
            }

            con.close();

            return utenti;
        }

        catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    public void doUpdateCustomer(Utente u){

        try (Connection con = ConPool.getConnection()) {
            Statement st = con.createStatement();
            String query = "update utente set email='" + u.getEmail() +
                    "', password='" + u.getPassword() +
                    "', nome='" + u.getNome() +
                    "', cognome='"+ u.getCognome() +
                    "', codice_fiscale='"+ u.getCodiceFiscale() +
                    "', data_di_nascita='"+ u.getDataNascita() +
                    "', indirizzo='"+ u.getIndirizzo() +
                    "', numero_di_cellulare='"+ u.getTelefono() +
                    "', poteri="+ u.getPoteri()
                    + " where email=" + u.getEmail() + ";";
            st.executeUpdate(query);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
