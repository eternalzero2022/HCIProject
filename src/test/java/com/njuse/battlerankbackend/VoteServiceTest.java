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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VoteServiceTest extends BaseUtilTest {

    @Test
    public void collectionTest() {

        register("1888888", "123456");
        login("1888888", "123456");

        List<ItemVO> itemList = new ArrayList<>();
        itemList.add(new ItemVO(0, "高坂穗乃果", "", "", 0, 0f, 0, 0, true));
        itemList.add(new ItemVO(9, "高海千歌", "", "", 1, 0f, 0, 0, true));
        itemList.add(new ItemVO(18, "上原步梦", "", "", 2, 0f, 0, 0, true));
        itemList.add(new ItemVO(27, "涩谷香音", "", "", 3, 0f, 0, 0, true));
        itemList.add(new ItemVO(32, "日野下花帆", "", "", 4, 0f, 0, 0, true));
        Integer collectionId = createCollection("Your favoriate Lovelive leader", itemList);

        Integer sessionId = startVoteSession(collectionId);
        Random random = new Random();
        while (true) {
            VoteRound voteRound = getNextRound(sessionId);
            if (voteRound.getParticipants() == null) {
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

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        CollectionVO collectionVO = getCollectionById(collectionId);

        for (ItemVO itemVO : collectionVO.getItems()) {
            System.out.println(itemVO);
        }
    }

    @Test
    public void terminateAtHalf() {


        register("18888888", "123456");
        login("18888888", "123456");

        List<ItemVO> itemList = new ArrayList<>();
        itemList.add(new ItemVO(0, "高坂穗乃果", "", "", 0, 0f, 0, 0, true));
        itemList.add(new ItemVO(9, "高海千歌", "", "", 1, 0f, 0, 0, true));
        itemList.add(new ItemVO(18, "上原步梦", "", "", 2, 0f, 0, 0, true));
        itemList.add(new ItemVO(27, "涩谷香音", "", "", 3, 0f, 0, 0, true));
        itemList.add(new ItemVO(32, "日野下花帆", "", "", 4, 0f, 0, 0, true));
        Integer collectionId = createCollection("Your favoriate Lovelive leader", itemList);

        Integer sessionId = startVoteSession(collectionId);
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            VoteRound voteRound = getNextRound(sessionId);
            if (voteRound.getParticipants() == null) {
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

        endVoteSession(sessionId);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        CollectionVO collectionVO = getCollectionById(collectionId);

        for (ItemVO itemVO : collectionVO.getItems()) {
            System.out.println(itemVO);
        }
    }
}
