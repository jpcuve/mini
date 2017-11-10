package com.messio.mini;


import com.messio.mini.bean.model.BinderQueryModel;
import com.messio.mini.domain.Binder;
import com.messio.mini.domain.Court;
import com.messio.mini.domain.Decision;
import com.messio.mini.domain.Docket;
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
@CrossOrigin
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
        final List<Binder> binders = facade.findBinders(binderIds);
        final List<Docket> dockets = facade.findDocketsByBinderIds(binderIds);
        final List<Long> docketIds = dockets.stream().map(Docket::getId).collect(Collectors.toList());
        final List<Decision> decisions = facade.findDecisionsByDocketIds(docketIds);
        final List<Long> courtIds = dockets.stream().map(Docket::getCourtId).collect(Collectors.toList());
        final List<Court> courts = facade.findCourts(courtIds);
        map.put("binders", binders);
        map.put("dockets", dockets);
        map.put("decisions", decisions);
        map.put("courts", courts);
        return map;
    }

    @PostMapping("/binders/query")
    public List<Long> queryBinders(@RequestBody BinderQueryModel model){
        LOGGER.debug("model: {}", model);
        List<Long> binderIds = facade.queryBinders(model);
        LOGGER.debug("binder ids found: {}", binderIds);
        return binderIds;
    }
}
