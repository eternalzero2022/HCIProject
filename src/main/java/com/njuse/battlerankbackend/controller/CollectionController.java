package com.njuse.battlerankbackend.controller;

import com.njuse.battlerankbackend.po.Item;
import com.njuse.battlerankbackend.service.CollectionService;
import com.njuse.battlerankbackend.vo.CollectionVO;
import com.njuse.battlerankbackend.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collections")
public class CollectionController {

    @Autowired
    CollectionService collectionService;

    @PostMapping()
    public ResultVO<Integer> creatCollection(@RequestBody CollectionVO collectionVO){
        return ResultVO.buildSuccess(collectionService.creatCollection(collectionVO));
    }

    @GetMapping("/{collectionId}")
    public ResultVO<CollectionVO> getCollection(@PathVariable Integer collectionId){
        return ResultVO.buildSuccess(collectionService.getCollection(collectionId));
    }

    @GetMapping("/{collectionId}/items")
    public ResultVO<List<Item>> getCollectionItems(@RequestParam Integer collectionId){
        return ResultVO.buildSuccess(collectionService.getCollectionItems(collectionId));
    }

    @GetMapping()
    public ResultVO<List<CollectionVO>> getCollectionList(@RequestParam(required = false, defaultValue = "") String category,
                                                          @RequestBody List<CollectionVO> excludeList,
                                                          @RequestParam Integer retNum){
        return ResultVO.buildSuccess(collectionService.getCollectionList(category, excludeList, retNum));
    }

    @GetMapping("/{userId}")
    public ResultVO<List<CollectionVO>> getCollectionListPrivate(@RequestParam(required = false, defaultValue = "") String category){
        return ResultVO.buildSuccess(collectionService.getCollectionListPrivate(category));
    }

    @GetMapping("/recommend")
    public ResultVO<List<CollectionVO>> getCollectionRecommend(@RequestBody List<CollectionVO> excludeList,
                                                               @RequestParam Integer retNum){
        return ResultVO.buildSuccess(collectionService.getCollectionRecommend(excludeList, retNum));
    }

    @GetMapping("/hot")
    public ResultVO<List<CollectionVO>> getCollectionHot(@RequestParam Integer retNum){
        return ResultVO.buildSuccess(collectionService.getCollectionHot(retNum));
    }

}
