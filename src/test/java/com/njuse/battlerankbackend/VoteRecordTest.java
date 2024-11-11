package com.njuse.battlerankbackend;

import com.njuse.battlerankbackend.vo.CollectionVO;
import com.njuse.battlerankbackend.vo.ItemVO;
import com.njuse.battlerankbackend.vo.VoteRound;
import com.njuse.battlerankbackend.vo.VoteRoundResult;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VoteRecordTest extends BaseUtilTest {

    private void voteProcess(Integer collectionId) {

        Integer sessionId = startVoteSession(collectionId);
        Random random = new Random();
        while (true) {
            VoteRound voteRound = getNextRound(sessionId);
            if (voteRound.getParticipants().size() == 0) {
                break;
            }
            System.out.println("Round " + voteRound.getRoundId() + ": ");
            List<ItemVO> participants = voteRound.getParticipants();
            System.out.println("(0)" + participants.get(0).getItemName() + " vs (1)" + participants.get(1).getItemName());
            int voteFor = random.nextInt(2);
            System.out.println("You choose " + voteFor);
            VoteRoundResult result = new VoteRoundResult();
            result.setRoundId(voteRound.getRoundId());
            result.setSessionId(sessionId);
            result.setWinnerId(participants.get(voteFor).getItemId());
            submitVoteRound(result);
        }

    }

    @Test
    public void userRankAndCountTest() {
        register("1888888", "123456");
        login("1888888", "123456");

        List<ItemVO> itemList = new ArrayList<>();
        itemList.add(new ItemVO(0, "高坂穗乃果", "", "", 0, 0f, 0, 0, true));
        itemList.add(new ItemVO(9, "高海千歌", "", "", 1, 0f, 0, 0, true));
        itemList.add(new ItemVO(18, "上原步梦", "", "", 2, 0f, 0, 0, true));
        itemList.add(new ItemVO(27, "涩谷香音", "", "", 3, 0f, 0, 0, true));
        itemList.add(new ItemVO(32, "日野下花帆", "", "", 4, 0f, 0, 0, true));
        CollectionVO collectionVO = CollectionVO.builder()
                .collectionName("Your favoriate Lovelive leader")
                .items(itemList).build();
        Integer collectionId = createCollection(collectionVO);
        voteProcess(collectionId);

        register("123123", "123");
        login("123123", "123");
        voteProcess(collectionId);
        voteProcess(collectionId);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(20, getUserVoteCount(collectionId));

        CollectionVO rankCollection = getUserRankCollection(collectionId);

        System.out.println("UserRankCollection:");
        for (ItemVO itemVO : rankCollection.getItems()) {
            System.out.println(itemVO);
        }

        CollectionVO newCollectionVO = getCollectionById(collectionId);
        assertEquals(30, newCollectionVO.getVoteCount());
        System.out.println("New Collection:");
        for (ItemVO itemVO : newCollectionVO.getItems()) {
            System.out.println(itemVO);
        }
    }
}
