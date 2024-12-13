package com.njuse.battlerankbackend.serviceImpl.selectionStrategy;

import com.njuse.battlerankbackend.po.Item;
import com.njuse.battlerankbackend.vo.ItemVO;
import com.njuse.battlerankbackend.vo.VoteSession;
import org.antlr.v4.runtime.misc.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
/**
 * Implements the {@link SelectionStrategy} for selecting items more randomly from a given
 * collection in a voting session. Compared with {@link RandomSelectionStrategy}, This strategy 
 * doesn't shuffle the items but use a shuffledIndexMap to map the ordered index into a random one
 * and selects pairs based on the mapping approach that is modified from {@link RandomSelectionStrategy}, 
 * making the selection more randomly and also ensuring that no item is selected more than once 
 * in the current voting round.
 */
public class RandomOptimizedSelectionStrategy implements SelectionStrategy{
    
    private final VoteSession voteSession;
    
    private final List<ItemVO> items;
    
    private final List<Integer> excludedItems;

    int[] shuffldIndexMap;


    int round = 0;
    
    int size = 0;
    
    int totalElement = 0;


    /**
     * shuffle the array using Fisher-Yates algorithm
     * @param array the array to be shuffled
     */
    public static void shuffle(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    public RandomOptimizedSelectionStrategy(VoteSession voteSession) {
        this.voteSession = voteSession;
        items = new ArrayList<>(voteSession.getCollectionVO().getItems());
        size = items.size();
        totalElement = (size * (size-1))/2;
        
        shuffldIndexMap = new int[totalElement];
        for(int i = 0;i < totalElement;i++){
            shuffldIndexMap[i] = i;
        }
        shuffle(shuffldIndexMap);
        
        excludedItems = new ArrayList<Integer>();
    }

    /**
     * Maps a linear index to a 2D coordinate (row and column) based on the
     * pairing logic. The result row is smaller than column.
     *
     * @param k the linear index to map
     * @param n the number of items in the collection
     * @return an array containing the row and column corresponding to the given index
     */
    private static int[] mapTo2D(int k, int n) {
        int i = (int) Math.floor((1 + Math.sqrt(1 + 8 * k)) / 2);
        int sum = i * (i - 1) / 2;
        int offset = k - sum;
        int row, col;

        if (i > n) {
            k = n * n - 1 - k;
            int[] ret = mapTo2D(k, n);
            ret[0] = n - 1 - ret[0];
            ret[1] = n - 1 - ret[1];
            return ret;
        }
        row = i - 1 - offset;
        col = offset;
        return new int[]{row, n - 1 - col};
    }
    private boolean isUnsuitablePair(int i, int j) {
        return  excludedItems.contains(this.items.get(i).getItemId()) ||
                excludedItems.contains(this.items.get(j).getItemId());
    }

    /**
     * Selects the next two items for voting from the shuffled list. If the selection
     * process is finished, returns null.
     *
     * @return a list containing the next two {@link ItemVO} objects for voting,
     * or an empty list if all items have been exhausted
     */
    @Override
    public List<ItemVO> selectNextTwoItems() {
        int[] indexes;
        do {
            if (isFinished()) return new ArrayList<>();
            indexes = mapTo2D(shuffldIndexMap[this.round++], size);
        } while (isUnsuitablePair(indexes[0], indexes[1]));
        
        List<ItemVO> res = new ArrayList<ItemVO>();
        res.add(items.get(indexes[0]));
        res.add(items.get(indexes[1]));
        return res;
    }

    /**
     * Checks if the selection process has finished, which occurs when all pairs
     * have been selected.
     *
     * @return true if the selection process is finished; false otherwise
     */
    @Override
    public Boolean isFinished() {
        return round >= totalElement;
    }

    /**
     * Excludes an item from the selection process.
     *
     * @param itemId the ID of the item to exclude
     */
    @Override
    public void excludeItem(Integer itemId) {
        excludedItems.add(itemId);
    }
}
