package com.njuse.battlerankbackend.serviceImpl;

import com.njuse.battlerankbackend.repository.CollectionRepository;
import com.njuse.battlerankbackend.repository.ItemRepository;
import com.njuse.battlerankbackend.service.CollectionService;
import com.njuse.battlerankbackend.vo.CollectionVO;
import com.njuse.battlerankbackend.vo.ItemVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CollectionServiceImp implements CollectionService {
    ItemRepository itemRepository;
    CollectionRepository collectionRepository;
    @Override
    public Integer creatCollection(Integer collectionId,
                            String collectionName,
                            Integer creatorId,
                            List<ItemVO> items){
        return -1;
    }

    @Override
    public CollectionVO getCollection(Integer collectionId){
        return new CollectionVO();
    }

    @Override
    public List<CollectionVO> getCollectionList(String category){
        return new ArrayList<>();
    }
}
