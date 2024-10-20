package com.njuse.battlerankbackend.serviceImpl.selectionStrategy;

import com.njuse.battlerankbackend.vo.ItemVO;

import java.util.List;

public interface SelectionStrategy {
    List<ItemVO> selectNextTwoItems();

    Boolean isFinished();
}
