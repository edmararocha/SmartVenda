package com.example.smartvenda.helpers;

import com.example.smartvenda.model.User;

import java.util.List;

public interface IUserDAO {
    public boolean save (User user);
    public boolean update (User user);
    public boolean delete (User user);
    public List<User> list();
}
