package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConfezioneDAO {

    public Confezione doRetrieveById(int id) {
        try (Connection connection = ConPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from confezione where id_confezione = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Confezione confezione = new Confezione();
                confezione.setIdConfezione(resultSet.getInt("id_confezione"));
                confezione.setPeso(resultSet.getInt("peso"));
                return confezione;
            }else{
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Confezione> doRetrieveAll() {
        List<Confezione> confezioni = new ArrayList<>();
        try (Connection connection = ConPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from confezione");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Confezione confezione = new Confezione();
                confezione.setIdConfezione(resultSet.getInt("id_confezione"));
                confezione.setPeso(resultSet.getInt("peso"));
                confezioni.add(confezione);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return confezioni;
    }

    public void doSaveConfezione(Confezione confezione) {
        StringBuilder sql = new StringBuilder("INSERT INTO confezione(");
        boolean first = true;
        List<Object> params = new ArrayList<>();

        if (confezione.getIdConfezione() > 0) {
            sql.append("id_confezione");
            params.add(confezione.getIdConfezione());
            first = false;
        }

        if (confezione.getPeso() > 0) {
            if (!first) {
                sql.append(", ");
            }
            sql.append("peso");
            params.add(confezione.getPeso());
        }


        sql.append(") VALUES (");

        for (int i = 0; i < params.size(); i++) {
            if (i > 0) {
                sql.append(", ");
            }
            sql.append("?");
        }
        sql.append(")");

        try (Connection connection = ConPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());

            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }

            int rows = preparedStatement.executeUpdate();

            if (rows > 0) {
                System.out.println("Inserimento riuscito.");
            } else {
                System.out.println("Nessuna riga inserita.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doUpdateConfezione(Confezione confezione, int idConfezione){
        try (Connection connection = ConPool.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("update confezione set id_confezione = ?, peso = ? where id_confezione = ?");
            preparedStatement.setInt(1, confezione.getIdConfezione());
            preparedStatement.setInt(2, confezione.getPeso());
            preparedStatement.setInt(3, idConfezione);


            int rows = preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void doRemoveConfezione(int idConfezione){
        try (Connection connection = ConPool.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("delete from confezione where id_confezione = ?");
            preparedStatement.setInt(1, idConfezione);


            int rows = preparedStatement.executeUpdate();


        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}

