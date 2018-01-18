package com.messio.mini;


import com.messio.mini.bean.model.BinderQueryModel;
import com.messio.mini.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jpc on 7/04/2016.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class ApiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);
    @Autowired
    private Facade facade;

    private static List<Long> computeIds(final String idsAsCommaSeparatedList){
        return Arrays.stream(idsAsCommaSeparatedList.split(",")).map(Long::parseLong).collect(Collectors.toList());
    }

    @GetMapping("/")
    public Map<String, Object> test(){
        final Map<String, Object> map = new HashMap<>();
        map.put("response", new Date().toString());
        return map;
    }

    @GetMapping("/binders/{ids}")
    public Map<String, Object> binders(@PathVariable("ids") String idsAsCommaSeparatedList){
        final Map<String, Object> map = new HashMap<>();
        final List<Long> binderIds = computeIds(idsAsCommaSeparatedList);
        final Map<Long, Binder> binders = facade.findBinders(binderIds);
        final Map<Long, Docket> dockets = facade.findDocketsByBinderIds(binderIds);
        final Map<Long, Decision> decisions = facade.findDecisionsByDocketIds(dockets.keySet());
        final Set<Long> courtIds = dockets.values().stream().map(Docket::getCourtId).collect(Collectors.toSet());
        final Map<Long, Court> courts = facade.findCourts(courtIds);
        final Map<Long, Party> parties = facade.findPartiesByBinderIds(binderIds);
        final Set<Long> actorIds = parties.values().stream().map(Party::getActorId).collect(Collectors.toSet());
        final Map<Long, Actor> actors = facade.findActors(actorIds);
        final Map<Long, Right> rights = facade.findRightsByBinderIds(binderIds);

        map.put("binders", binders);
        map.put("dockets", dockets);
        map.put("decisions", decisions);
        map.put("courts", courts);
        map.put("parties", parties);
        map.put("actors", actors);
        map.put("rights", rights);
        return map;
    }

    @PostMapping("/binders/query")
    public List<Long> queryBinders(@RequestBody BinderQueryModel model){
        LOGGER.debug("model: {}", model);
        List<Long> binderIds = facade.queryBinders(model);
        LOGGER.debug("binder ids found: {}", binderIds);
        return binderIds;
    }

    @GetMapping("/courts")
    public List<Court> getCourts(){
        return facade.findAllCourts();
    }

    @GetMapping("/pols")
    public List<Pol> getAllPols(){
        return facade.findAllPols();
    }

    @GetMapping("/decision/{id}")
    public Decision getDecision(@PathVariable("id") long id){
        return facade.findOne(Decision.class, id);
    }
}
