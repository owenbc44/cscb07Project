package com.example.myapplication.store;

import java.util.ArrayList;
import java.util.List;

public class SalesLogImpl implements SalesLog {

  private List<SaleImpl> salesLog = new ArrayList<SaleImpl>();

  /**
   * Adds sale to the saleslog
   * 
   * @param sale the sale to be added
   */
  @Override
  public void addSale(SaleImpl sale) {
    this.salesLog.add(sale);
  }

  /**
   * return a list of sales.
   * 
   * @return list containing the sales.
   */
  @Override
  public List<SaleImpl> getSales() {
    List<SaleImpl> sales = new ArrayList<SaleImpl>();
    for (int i = 0; i < this.salesLog.size(); i++)
      sales.add(this.salesLog.get(i));
    return sales;
  }


}
