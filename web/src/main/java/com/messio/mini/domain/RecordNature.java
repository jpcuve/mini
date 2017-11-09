package com.messio.mini.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.messio.mini.util.Node;

/**
 * Created by jpc on 1/20/15.
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RecordNature implements Node<String, RecordNature> {
    DECISIONS_AND_ORDERS,
    PRELUDE,
    COMPLAINT,
    HEARING(PRELUDE),
    VERDICT(PRELUDE),
    EVIDENCE,
    PROCESS_PROCEDURE_INFORMATION,
    MOTIONS,
    BRIEFS_AND_MEMORANDUMS,
    SETTLEMENT_INFORMATION,
    PRELIMINARY_PROCEEDINGS(DECISIONS_AND_ORDERS),
    ON_THE_MERITS(DECISIONS_AND_ORDERS),
    OTHER_DECISIONS(DECISIONS_AND_ORDERS);

    private final RecordNature parent;

    RecordNature() {
        this.parent = null;
    }

    RecordNature(RecordNature parent) {
        this.parent = parent;
    }

    @Override
    public RecordNature getParent() {
        return parent;
    }

    public String getId() {
        return name();
    }
}
