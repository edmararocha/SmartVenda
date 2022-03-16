package com.example.smartvenda.helpers;

import com.example.smartvenda.model.Sale;

import java.util.List;

public interface ISaleDAO {
    public boolean save (Sale sale);
    public boolean update (Sale sale);
    public boolean delete (Sale sale);
    public List<Sale> list();
}
