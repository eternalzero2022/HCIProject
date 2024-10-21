package com.njuse.battlerankbackend.controller;

import com.njuse.battlerankbackend.po.Item;
import com.njuse.battlerankbackend.service.CollectionService;
import com.njuse.battlerankbackend.vo.CollectionVO;
import com.njuse.battlerankbackend.vo.ItemVO;
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
    public ResponseEntity<Integer> creatCollection(
            @RequestParam String collectionName,
            @RequestBody List<ItemVO> items){
        return ResponseEntity.ok(collectionService.creatCollection(
                collectionName,items));
    }

    @GetMapping("/{collectionId}")
    public ResponseEntity<CollectionVO> getCollection(@PathVariable Integer collectionId){
        return ResponseEntity.ok(collectionService.getCollection(collectionId));
    }

    @GetMapping("/{collectionId}/items")
    public ResponseEntity<List<Item>> getCollectionItems(@RequestParam Integer collectionId){
        return ResponseEntity.ok(collectionService.getCollectionItems(collectionId));
    }

    @GetMapping()
    public ResponseEntity<List<CollectionVO>> getCollectionList(@RequestParam(required = false, defaultValue = "") String category){
        return ResponseEntity.ok(collectionService.getCollectionList(category));
    }

}
