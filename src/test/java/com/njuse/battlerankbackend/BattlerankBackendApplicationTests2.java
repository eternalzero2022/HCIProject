package com.njuse.battlerankbackend;

import com.njuse.battlerankbackend.controller.CollectionController;
import com.njuse.battlerankbackend.po.Item;
import com.njuse.battlerankbackend.service.CollectionService;
import com.njuse.battlerankbackend.vo.CollectionVO;
import com.njuse.battlerankbackend.vo.ItemVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class BattlerankBackendApplicationTests2 {

    @Autowired
    CollectionController controller;
    @Autowired
    CollectionService collectionService;

    @Test
    void creatTest() {
        int itemNum = 10;
        String name = "cwh";
        Integer creatorId = 1;
        List<ItemVO> list = new ArrayList<>();
        for (int i = 0; i < itemNum;i++){
            ItemVO itemVO = new ItemVO();
            itemVO.setItemName(Integer.toString(i));
            list.add(itemVO);
        }
        String name2 = "hyf";
        Integer creatorId2 = 2;
        List<ItemVO> list2 = new ArrayList<>();
        for (int i = 0; i < itemNum;i++){
            ItemVO itemVO = new ItemVO();
            itemVO.setItemName(Integer.toString(i+10));
            list2.add(itemVO);
        }
        CollectionVO collectionVO1 = CollectionVO.builder()
                .collectionName(name)
                .items(list)
                .build();
        CollectionVO collectionVO2 = CollectionVO.builder()
                .collectionName(name2)
                .items(list2)
                .build();
        Integer id = controller.creatCollection(collectionVO1).getResult();
        Integer id2 = controller.creatCollection(collectionVO2).getResult();
        CollectionVO newCollection = controller.getCollection(id).getResult();
        List<CollectionVO> voList = controller.getCollectionList(null,null, 10).getResult();
        List<Item> finaltest = controller.getCollectionItems(id).getResult();

        System.out.println(1);
        System.out.println(2);

    }

}
