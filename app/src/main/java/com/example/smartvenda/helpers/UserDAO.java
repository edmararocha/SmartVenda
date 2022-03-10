package com.example.smartvenda.helpers;

import com.example.smartvenda.model.User;

import java.util.List;

public class UserDAO implements IUserDAO{
    @Override
    public boolean save(User user) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean delete(User user) {
        return false;
    }

    @Override
    public List<User> list() {
        return null;
    }
}
