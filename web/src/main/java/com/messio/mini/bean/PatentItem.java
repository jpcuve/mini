package com.messio.mini.bean;

import com.messio.mini.entity.Patent;

/**
 * Created by jpc on 12/5/14.
 */
public class PatentItem extends RightItem {
    public PatentItem() {
    }

    public PatentItem(Patent right) {
        super(right);
    }

    @Override
    public Patent getRight() {
        return (Patent) super.getRight();
    }

    public void setRight(Patent right) {
        super.setRight(right);
    }
}
