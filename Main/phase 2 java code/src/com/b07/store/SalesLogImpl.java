package com.b07.store;

import java.util.ArrayList;
import java.util.List;

public class SalesLogImpl implements SalesLog {

  private List<Sale> salesLog = new ArrayList<Sale>();

  /**
   * Adds sale to the saleslog
   * 
   * @param sale the sale to be added
   */
  @Override
  public void addSale(Sale sale) {
    this.salesLog.add(sale);
  }

  /**
   * return a list of sales.
   * 
   * @return list containing the sales.
   */
  @Override
  public List<Sale> getSales() {
    List<Sale> sales = new ArrayList<Sale>();
    for(int i = 0; i < this.salesLog.size(); i++)
      sales.add(this.salesLog.get(i));
    return sales;
  }
  
  
}
