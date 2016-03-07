package com.messio.mini.bean;

import com.messio.mini.entity.Trademark;

/**
 * Created by jpc on 12/5/14.
 */
public class TrademarkItem extends RightItem {
    public TrademarkItem() {
    }

    public TrademarkItem(Trademark right) {
        super(right);
    }

    @Override
    public Trademark getRight() {
        return (Trademark) super.getRight();
    }

    public void setRight(Trademark right) {
        super.setRight(right);
    }
}
