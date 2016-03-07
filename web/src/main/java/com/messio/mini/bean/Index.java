package com.messio.mini.bean;

import com.messio.mini.entity.*;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jpc on 11/10/14.
 */
@Named
@ViewScoped
public class Index implements Serializable {
/*
    @EJB
    private BinderDao binderDao;
    @EJB
    private DocketDao docketDao;
    @EJB
    private DecisionDao decisionDao;
    @EJB
    private DocumentDao documentDao;
    @EJB
    private AnalysisDao analysisDao;
    @EJB
    private HonorDao honorDao;
    @EJB
    private RightDao rightDao;
    @EJB
    private BinderMemberDao binderMemberDao;
    private final List<Long> binderIds = new ArrayList<>();
    private final LazyDataModel<BinderItem> binderItems = new LazyDataModel<BinderItem>() {
        @Override
        public List<BinderItem> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            final List<BinderItem> list = new ArrayList<>();
            int last = Math.min(first + pageSize, binderIds.size());
            for (final Binder binder: binderDao.findMany(binderIds.subList(first, last))){
                final BinderItem binderItem = new BinderItem(binder);
                final List<BinderMember> binderMembers = new ArrayList<>();
                for (final BinderMember binderMember: binderMemberDao.findByBinder(binder.getId())) binderMembers.add(binderMember);
                binderItem.setBinderMembers(binderMembers);
                final List<RightItem> rightItems = new ArrayList<>();
                for (final Right right: rightDao.findByBinder(binder.getId())){
                    RightItem rightItem = null;
                    switch (right.getDomain()){
                        case TRADEMARK:
                            rightItem = new TrademarkItem((Trademark) right);
                            break;
                        case PATENT:
                            rightItem = new PatentItem((Patent) right);
                            break;
                    }
                    if (rightItem != null) rightItems.add(rightItem);
                }
                binderItem.setRightItems(rightItems);
                final List<DecisionItem> decisionItems = new ArrayList<>();
                for (final Docket docket: docketDao.findByBinder(binder.getId())){
                    for (final Decision decision: decisionDao.findByDocket(docket.getId())){
                        final DecisionItem decisionItem = new DecisionItem(decision);
                        decisionItems.add(decisionItem);
                        final List<DocumentItem> documentItems = new ArrayList<>();
                        for (final Document document: documentDao.findByDecision(decision.getId())){
                            documentItems.add(new DocumentItem(document));
                        }
                        decisionItem.setDocumentItems(documentItems);
                        final List<AnalysisItem> analysisItems = new ArrayList<>();
                        for (final Analysis analysis: analysisDao.findByDecision(decision.getId())){
                            analysisItems.add(new AnalysisItem(analysis));
                        }
                        decisionItem.setAnalysisItems(analysisItems);
                        final List<HonorItem> honorItems = new ArrayList<>();
                        for (final Honor honor: honorDao.findByDecision(decision.getId())){
                            honorItems.add(new HonorItem(honor));
                        }
                        decisionItem.setHonorItems(honorItems);
                    }
                }
                binderItem.setDecisionItems(decisionItems);
                list.add(binderItem);
            }
            return list;
        }

        @Override
        public int getRowCount() {
            return binderIds.size();
        }
    };
    private BinderQueryModel queryModel = new BinderQueryModel();

    @PostConstruct
    public void init(){
    }

    public String reset(){
        this.queryModel.reset();
        return null;
    }

    public String query(){
        binderIds.clear();
        for (final Long binderId: binderDao.findByModel(queryModel)) binderIds.add(binderId);
        return null;
    }

    public BinderQueryModel getQueryModel() {
        return queryModel;
    }

    public LazyDataModel<BinderItem> getBinderItems() {
        return binderItems;
    }

    public List<BinderItem> getAllBinderItems(){
        return binderItems.load(0, binderIds.size(), null, null, null);
    }
*/
}
