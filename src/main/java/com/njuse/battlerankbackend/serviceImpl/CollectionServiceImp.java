package com.njuse.battlerankbackend.serviceImpl;

import com.njuse.battlerankbackend.exception.SelfDefineException;
import com.njuse.battlerankbackend.po.CollectionPO;
import com.njuse.battlerankbackend.po.ItemPO;
import com.njuse.battlerankbackend.repository.CollectionRepository;
import com.njuse.battlerankbackend.service.CollectionService;
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
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Integer creatCollection(String collectionName,
                            Integer creatorId,
                            List<ItemVO> items){
        CollectionPO isAlreadyExist = collectionRepository.findByCollectionNameAndCreaterId(collectionName, creatorId);
        if(isAlreadyExist != null) throw SelfDefineException.creatCollectionFault1();

        CollectionPO newCollection = new CollectionPO();
        newCollection.setCollectionName(collectionName);
        newCollection.setCreaterId(creatorId);
        newCollection.setItems(new ArrayList<>());
        collectionRepository.save(newCollection);

        isAlreadyExist = collectionRepository.findByCollectionNameAndCreaterId(collectionName, creatorId);
        List<ItemPO> itemPOS = isAlreadyExist.getItems();
        if (itemPOS == null) itemPOS = new ArrayList<ItemPO>();
        for (int i = 0;i<items.size();i++){
            itemPOS.add(items.get(i).toPO());
        }
        isAlreadyExist.setItems(itemPOS);
        collectionRepository.save(isAlreadyExist);
        return isAlreadyExist.getCollectionId();
    }

    @Override
    public CollectionVO getCollection(Integer collectionId){
        CollectionPO collection = collectionRepository.findByCollectionId(collectionId);
        if (collection == null) throw SelfDefineException.getCollectionFault();

        return collection.toVO();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ItemPO> getCollectionItems(Integer collectionId){
        CollectionPO collection = collectionRepository.findByCollectionId(collectionId);
        return collection.getItems();
    }
    @Override
    public List<CollectionVO> getCollectionList(String category){
        List<CollectionPO> collectionVOS = collectionRepository.findAll();
        List<CollectionVO> result = new ArrayList<>();
        Boolean condition = false;
        if(category == null)  condition = true;
        for (int i = 0;i <collectionVOS.size();i++){
            if(condition){ // 条件判断
                result.add(collectionVOS.get(i).toVO());
            }
        }

        return result;
    }
}
