package com.njuse.battlerankbackend.controller;

import com.njuse.battlerankbackend.service.CollectionService;
import com.njuse.battlerankbackend.vo.CollectionVO;
import com.njuse.battlerankbackend.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/collections")
public class CollectionController {

    @Autowired
    CollectionService collectionService;

    @PostMapping()
    public ResponseEntity<Integer> creatCollection(
            @RequestParam Integer collectionId,
            @RequestParam String collectionName,
            @RequestParam Integer creatorId,
            @RequestParam List<ItemVO> items){
        return ResponseEntity.ok(collectionService.creatCollection(
                collectionId,collectionName,creatorId,items));
    }

    @GetMapping("/{collectionId}")
    public ResponseEntity<CollectionVO> getCollection(@RequestParam Integer collectionId){
        return ResponseEntity.ok(collectionService.getCollection(collectionId));
    }

    @GetMapping()
    public ResponseEntity<List<CollectionVO>> getCollectionList(@RequestBody String category){
        return ResponseEntity.ok(collectionService.getCollectionList(category));
    }

}
