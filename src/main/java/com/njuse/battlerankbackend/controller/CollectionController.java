package com.njuse.battlerankbackend.controller;

import com.njuse.battlerankbackend.po.Item;
import com.njuse.battlerankbackend.service.CollectionService;
import com.njuse.battlerankbackend.service.VoteRecordService;
import com.njuse.battlerankbackend.util.SecurityUtil;
import com.njuse.battlerankbackend.vo.CollectionVO;
import com.njuse.battlerankbackend.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/collections")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private VoteRecordService voteRecordService;

    @Autowired
    private SecurityUtil securityUtil;

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
                                                          @RequestParam(required = false, defaultValue = "") List<Integer> excludeList,
                                                          @RequestParam Integer retNum){
        return ResultVO.buildSuccess(collectionService.getCollectionList(category, excludeList, retNum));
    }

    @GetMapping("/user")
    public ResultVO<List<CollectionVO>> getCollectionListPrivate(@RequestParam(required = false, defaultValue = "") String category){
        return ResultVO.buildSuccess(collectionService.getCollectionListPrivate(category));
    }

    @GetMapping("/recommend")
    public ResultVO<List<CollectionVO>> getCollectionRecommend(@RequestParam(required = false,defaultValue = "") List<Integer> excludeList,
                                                               @RequestParam Integer retNum){
        return ResultVO.buildSuccess(collectionService.getCollectionRecommend(excludeList, retNum));
    }

    @GetMapping("/hot")
    public ResultVO<List<CollectionVO>> getCollectionHot(@RequestParam Integer retNum){
        return ResultVO.buildSuccess(collectionService.getCollectionHot(retNum));
    }

    @GetMapping("/{collectionId}/userVoteCount")
    public ResultVO<Integer> getUserVoteCount(@PathVariable Integer collectionId){
        Integer count = voteRecordService.getVoteCount(securityUtil.getCurrentUser().getUserId(), collectionId);
        return ResultVO.buildSuccess(count);
    }

    @GetMapping("/{collectionId}/userRank")
    public ResultVO<CollectionVO> getUserRankCollection(@PathVariable Integer collectionId){
        return ResultVO.buildSuccess(collectionService.getUserRankCollection(securityUtil.getCurrentUser().getUserId(), collectionId));
    }

    @GetMapping("/search")
    public ResultVO<List<CollectionVO>> searchCollections(@RequestParam String content) {
        return ResultVO.buildSuccess(collectionService.searchCollections(content));
    }

}
