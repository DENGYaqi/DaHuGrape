package com.example.dahugrape.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dahugrape.database.model.Client;

import java.util.List;

@Dao
public interface ClientDao {

    @Insert
    void insertClient(Client client);

    @Query("SELECT * FROM client_table")
    List<Client> getAllClients();

    @Query("SELECT * FROM client_table WHERE client_id LIKE :clientId")
    Client findClientById(int clientId);

    @Delete
    void deleteClient(Client client);

    @Update
    void updateClient(Client client);

    @Insert
    void insertMultipleClients(List<Client> clientList);
}
