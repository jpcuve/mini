package com.messio.mini.session;


import com.messio.mini.entity.Binder;
import com.messio.mini.entity.Court;
import com.messio.mini.entity.Decision;
import com.messio.mini.entity.Docket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jpc on 7/04/2016.
 */
@Singleton(name = "mini/rest-service")
@Lock(LockType.READ)
@Path("/rest")
@Produces({"application/json"})
public class RESTService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RESTService.class);
    @Inject
    private Facade facade;

    private static List<Long> computeIds(final String idsAsCommaSeparatedList){
        return Arrays.stream(idsAsCommaSeparatedList.split(",")).map(Long::parseLong).collect(Collectors.toList());
    }

    @GET
    public Map<String, Object> test(){
        final Map<String, Object> map = new HashMap<>();
        map.put("response", new Date().toString());
        return map;
    }

    @GET
    @Path("/dockets/{ids}")
    public List<Docket> dockets(@PathParam("ids") String idsAsCommaSeparatedList){
        List<Docket> dockets = facade.results(DAO.builder(Docket.class).named(Docket.DOCKET_BY_IDS).param("ids", computeIds(idsAsCommaSeparatedList)));
        return dockets;
    }

    @GET
    @Path("/binders/{ids}")
    public Map<String, Object> binders(@PathParam("ids") String idsAsCommaSeparatedList){
        final Map<String, Object> map = new HashMap<>();
        final List<Long> binderIds = computeIds(idsAsCommaSeparatedList);
        final List<Binder> binders = facade.results(DAO.builder(Binder.class).named(Binder.BINDER_BY_IDS).param("ids", binderIds));
        final List<Docket> dockets = facade.results(DAO.builder(Docket.class).named(Docket.DOCKET_BY_BINDER_IDS).param("ids", binderIds));
        final List<Long> docketIds = dockets.stream().map(Docket::getId).collect(Collectors.toList());
        final List<Decision> decisions = facade.results(DAO.builder(Decision.class).named(Decision.DECISION_BY_DOCKET_IDS).param("ids", docketIds));
        final List<Long> courtIds = dockets.stream().map(Docket::getCourtId).collect(Collectors.toList());
        final List<Court> courts = facade.results(DAO.builder(Court.class).named(Court.COURT_BY_IDS).param("ids", courtIds));
        map.put("binders", binders);
        map.put("dockets", dockets);
        map.put("decisions", decisions);
        map.put("courts", courts);
        return map;
    }

}
