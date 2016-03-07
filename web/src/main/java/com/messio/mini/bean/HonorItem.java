package com.messio.mini.bean;

import com.messio.mini.entity.Honor;

/**
 * Created by jpc on 30/01/15.
 */
public class HonorItem {
    private Honor honor;

    public HonorItem() {
    }

    public HonorItem(Honor honor) {
        this.honor = honor;
    }

    public Honor getHonor() {
        return honor;
    }

    public void setHonor(Honor honor) {
        this.honor = honor;
    }
}
