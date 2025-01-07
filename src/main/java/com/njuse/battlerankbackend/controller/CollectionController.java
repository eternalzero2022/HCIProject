package com.njuse.battlerankbackend.controller;

import com.njuse.battlerankbackend.aop.RoleAuthorization;
import com.njuse.battlerankbackend.aop.TakeCount;
import com.njuse.battlerankbackend.enums.RoleEnum;
import com.njuse.battlerankbackend.po.Item;
import com.njuse.battlerankbackend.service.CollectionService;
import com.njuse.battlerankbackend.service.UserProfileService;
import com.njuse.battlerankbackend.service.VoteRecordService;
import com.njuse.battlerankbackend.util.SecurityUtil;
import com.njuse.battlerankbackend.vo.CollectionVO;
import com.njuse.battlerankbackend.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private UserProfileService userProfileService;

    @PostMapping()
    public ResultVO<Integer> creatCollection(@RequestBody CollectionVO collectionVO){
        return ResultVO.buildSuccess(collectionService.creatCollection(collectionVO));
    }

    @TakeCount
    @GetMapping("/{collectionId}")
    public ResultVO<CollectionVO> getCollection(@PathVariable Integer collectionId){
        return ResultVO.buildSuccess(collectionService.getCollection(collectionId));
    }

    @GetMapping("/{collectionId}/items")
    public ResultVO<List<Item>> getCollectionItems(@RequestParam Integer collectionId){
        return ResultVO.buildSuccess(collectionService.getCollectionItems(collectionId));
    }

    @TakeCount
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

    @TakeCount
    @GetMapping("/recommend")
    public ResultVO<List<CollectionVO>> getCollectionRecommend(@RequestParam(required = false,defaultValue = "") List<Integer> excludeList,
                                                               @RequestParam Integer retNum){
        return ResultVO.buildSuccess(collectionService.getCollectionRecommend(excludeList, retNum));
    }

    @TakeCount
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

    @PostMapping("/{collectionId}/like")
    public ResultVO<Boolean> likeCollection(@PathVariable Integer collectionId){
        return ResultVO.buildSuccess(collectionService.likeCollection(securityUtil.getCurrentUser().getUserId(), collectionId));
    }

    @DeleteMapping("{collectionId}/like")
    public ResultVO<Boolean> unlikeCollection(@PathVariable Integer collectionId){
        return ResultVO.buildSuccess(collectionService.unlikeCollection(securityUtil.getCurrentUser().getUserId(), collectionId));
    }

    @PostMapping("{collectionId}/favorite")
    public ResultVO<Boolean> favoriteCollection(@PathVariable Integer collectionId){
        return ResultVO.buildSuccess(collectionService.favoriteCollection(securityUtil.getCurrentUser().getUserId(), collectionId));
    }

    @DeleteMapping("{collectionId}/favorite")
    public ResultVO<Boolean> unfavoriteCollection(@PathVariable Integer collectionId){
        return ResultVO.buildSuccess(collectionService.unfavoriteCollection(securityUtil.getCurrentUser().getUserId(), collectionId));
    }

    @GetMapping("{collectionId}/has-liked")
    public ResultVO<Boolean> hasLiked(@PathVariable Integer collectionId){
        return ResultVO.buildSuccess(userProfileService.hasLikedCollection(securityUtil.getCurrentUser().getUserId(), collectionId));
    }

    @GetMapping("{collectionId}/has-favorite")
    public ResultVO<Boolean> hasFavorite(@PathVariable Integer collectionId){
        return ResultVO.buildSuccess(userProfileService.hasFavouriteCollection(securityUtil.getCurrentUser().getUserId(), collectionId));
    }

    @DeleteMapping("/{collectionId}")
    @RoleAuthorization(RoleEnum.ADMIN)
    public ResultVO<Boolean> deleteCollection(@PathVariable Integer collectionId){
        return ResultVO.buildSuccess(collectionService.removeCollection(collectionId));
    }

}
