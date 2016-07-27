package com.messio.mini.session;

import com.messio.mini.bean.model.BinderQueryModel;
import com.messio.mini.entity.Binder;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by jpc on 13-07-16.
 */
@Stateless(name = "mini/facade")
public class Facade extends DAO {
    public List<Long> queryBinders(BinderQueryModel model){
        // build query
        final String ref = String.format("%%%s%%", model.getReference());
        return results(builder(Long.class).named(Binder.BINDER_IDS_BY_ANY_REFERENCE).param("reference", ref));
    }

}
