package com.njuse.battlerankbackend.serviceImpl;

import com.njuse.battlerankbackend.exception.SelfDefineException;
import com.njuse.battlerankbackend.po.CollectionPO;
import com.njuse.battlerankbackend.po.Item;
import com.njuse.battlerankbackend.po.User;
import com.njuse.battlerankbackend.repository.CollectionRepository;
import com.njuse.battlerankbackend.service.CollectionService;
import com.njuse.battlerankbackend.service.ItemService;
import com.njuse.battlerankbackend.util.SecurityUtil;
import com.njuse.battlerankbackend.vo.CollectionVO;
import com.njuse.battlerankbackend.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CollectionServiceImp implements CollectionService {

    @Autowired
    CollectionRepository collectionRepository;

    @Autowired
    ItemService itemService;

    @Autowired
    private SecurityUtil securityUtil;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Integer creatCollection(CollectionVO collectionVO){
        Integer creatorId = securityUtil.getCurrentUser().getUserId();
        CollectionPO isAlreadyExist = collectionRepository.findByCollectionNameAndCreatorId(collectionVO.getCollectionName(), creatorId);
        if(isAlreadyExist != null) throw SelfDefineException.creatCollectionFault1();

        List<ItemVO> items = collectionVO.getItems();
        CollectionPO newCollection = collectionVO.toPO();
        newCollection.setCreatorId(creatorId);
        newCollection.setVoteCount(0);
        newCollection.setItems(new ArrayList<>());
        newCollection = collectionRepository.save(newCollection);

//        isAlreadyExist = collectionRepository.findByCollectionNameAndCreaterId(collectionName, creatorId);
        List<Item> itemPOS = newCollection.getItems();
        if (itemPOS == null) itemPOS = new ArrayList<>();
        for (int i = 0;i<items.size();i++){
            Item item = items.get(i).toPO();
            item.setCollectionId(newCollection.getCollectionId());

            item.setItemId(0);
            item.setVoteCount(0);
            item.setWinCount(0);
            item.setWinRate(0f);
            item = itemService.saveItem(item);
            itemPOS.add(item);
        }
        newCollection.setItems(itemPOS);
        collectionRepository.save(newCollection);
        return newCollection.getCollectionId();
    }

    @Override
    public CollectionVO getCollection(Integer collectionId){
        CollectionPO collection = collectionRepository.findByCollectionId(collectionId);
        if (collection == null) throw SelfDefineException.getCollectionFault();

        return collection.toVO();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Item> getCollectionItems(Integer collectionId){
        CollectionPO collection = collectionRepository.findByCollectionId(collectionId);
        return collection.getItems();
    }
    @Override
    public List<CollectionVO> getCollectionList(String category){
        List<CollectionPO> collectionVOS = collectionRepository.findAll();
        List<CollectionVO> result = new ArrayList<>();
        Boolean condition = true;

        if(category == null || category == "")  condition = true;
        for (int i = 0;i <collectionVOS.size();i++){
            if(condition && collectionVOS.get(i).getIsPublic()){ // 条件判断
                result.add(collectionVOS.get(i).toVO());
            }
        }
        return result;
    }

    public List<CollectionVO> getCollectionListPrivate(String category){
        Integer userId = securityUtil.getCurrentUser().getUserId();
        List<CollectionPO> collectionVOS = collectionRepository.findAll();
        List<CollectionVO> result = new ArrayList<>();
        Boolean condition = true;

        if(category == null || category == "")  condition = true;
        for (int i = 0;i <collectionVOS.size();i++){
            if(condition && collectionVOS.get(i).getCreatorId().intValue() == userId.intValue()){ // 条件判断,如果是同一个用户才能返回对应集合
                result.add(collectionVOS.get(i).toVO());
            }
        }
        return result;
    }

    @Override
    public void increaseVoteCount(Integer collectionId, Integer voteCount) {
        CollectionPO collection = collectionRepository.findByCollectionId(collectionId);
        collection.setVoteCount(voteCount + collection.getVoteCount());
        collectionRepository.save(collection);
    }
}
