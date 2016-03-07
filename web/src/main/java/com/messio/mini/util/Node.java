package com.messio.mini.util;

/**
 * Created by jpc on 1/20/15.
 */
public interface Node<K, E> {
    K getId();
    E getParent();
}
