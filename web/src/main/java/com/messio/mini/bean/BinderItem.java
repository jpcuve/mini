package com.messio.mini.bean;

import com.messio.mini.entity.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jpc on 10/15/14.
 */
public class BinderItem {
    private Binder binder;
    private List<RightItem> rightItems;
    private List<DecisionItem> decisionItems;
    private List<Domain> domains;
    private List<Party> parties;

    public BinderItem() {
    }

    public BinderItem(Binder binder) {
        this.binder = binder;
    }

    public List<Member> getOppMembers(){
        final List<Member> list = new ArrayList<>();
        for (final Party party : parties) if (party.isOpponent()) list.add(party.getMember());
        return list;
    }
    
    public List<Member> getAppMembers(){
        final List<Member> list = new ArrayList<>();
        for (final Party party : parties) if (!party.isOpponent()) list.add(party.getMember());
        return list;
    }

    public List<TrademarkItem> getOppTrademarkItems(){
        final List<TrademarkItem> list = new ArrayList<>();
        for (final RightItem rightItem: rightItems) if (rightItem instanceof TrademarkItem && rightItem.getRight().isOpponent()) list.add((TrademarkItem) rightItem);
        return list;
    }

    public List<TrademarkItem> getAppTrademarkItems(){
        final List<TrademarkItem> list = new ArrayList<>();
        for (final RightItem rightItem: rightItems) if (rightItem instanceof TrademarkItem && !rightItem.getRight().isOpponent()) list.add((TrademarkItem) rightItem);
        return list;
    }

    public List<PatentItem> getOppPatentItems(){
        final List<PatentItem> list = new ArrayList<>();
        for (final RightItem rightItem: rightItems) if (rightItem instanceof PatentItem && rightItem.getRight().isOpponent()) list.add((PatentItem) rightItem);
        return list;
    }

    public List<PatentItem> getAppPatentItems(){
        final List<PatentItem> list = new ArrayList<>();
        for (final RightItem rightItem: rightItems) if (rightItem instanceof PatentItem && !rightItem.getRight().isOpponent()) list.add((PatentItem) rightItem);
        return list;
    }

    public Binder getBinder() {
        return binder;
    }

    public void setBinder(Binder binder) {
        this.binder = binder;
    }

    public List<RightItem> getRightItems() {
        return rightItems;
    }

    public void setRightItems(List<RightItem> rightItems) {
        this.rightItems = rightItems;
    }

    public List<DecisionItem> getDecisionItems() {
        return decisionItems;
    }

    public void setDecisionItems(List<DecisionItem> decisionItems) {
        this.decisionItems = decisionItems;
    }

    public List<Party> getParties() {
        return parties;
    }

    public void setParties(List<Party> parties) {
        this.parties = parties;
    }

    public List<Domain> getDomains() {
        if (domains == null){
            domains = new ArrayList<>();
            if (binder != null) domains.addAll(binder.getDomains());
        }
        return domains;
    }
}
