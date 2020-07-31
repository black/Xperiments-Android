package com.example.viewpagerwithdynamicviews;

public class PagerModel {
    int pageNumber;
    boolean toPage;

  public PagerModel(int fromPage, boolean toPage) {
    this.pageNumber = fromPage;
    this.toPage = toPage;
  }

  public int getPageNumber() {
    return pageNumber;
  }

  public boolean isToPage() {
    return toPage;
  }


}
