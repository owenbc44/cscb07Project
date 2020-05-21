package com.b07.store;

import java.util.List;

public interface SalesLog {

  public void addSale(Sale sale);

  public List<Sale> getSales();


}
