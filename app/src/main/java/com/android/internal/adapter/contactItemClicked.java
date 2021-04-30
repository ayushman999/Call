package com.android.internal.adapter;

import com.android.internal.Model.model;

public interface contactItemClicked {
    public void onItemClicked(String item);
    public void plus(model m,int position);
    public void minus(model m, int position);
}
