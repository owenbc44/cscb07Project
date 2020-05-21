package com.example.myapplication.store;

import java.util.List;

public interface SalesLog {

  public void addSale(SaleImpl sale);

  public List<SaleImpl> getSales();


}
