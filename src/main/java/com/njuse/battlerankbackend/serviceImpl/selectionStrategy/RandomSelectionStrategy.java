package com.njuse.battlerankbackend.serviceImpl.selectionStrategy;


import com.njuse.battlerankbackend.vo.ItemVO;
import com.njuse.battlerankbackend.vo.VoteSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Implements the {@link SelectionStrategy} for selecting items randomly from a given
 * collection in a voting session. This strategy shuffles the items and selects pairs
 * based on a specific mapping approach, ensuring that no item is selected more than
 * once in the current voting round.
 */
public class RandomSelectionStrategy implements SelectionStrategy {

    private final VoteSession voteSession;
    private final List<ItemVO> shuffledItems;
    int round = 0;
    private HashSet<Integer> excludedItems = new HashSet<>();

    public RandomSelectionStrategy(VoteSession voteSession) {
        this.voteSession = voteSession;
        shuffledItems = new ArrayList<>(voteSession.getCollectionVO().getItems());
        Collections.shuffle(shuffledItems);
    }

    /**
     * Maps a linear index to a 2D coordinate (row and column) based on the
     * pairing logic.
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
        return new int[]{row, col};
    }

    private boolean isUnsuitablePair(int i, int j) {
        return i >= j ||
                excludedItems.contains(this.shuffledItems.get(i).getItemId()) ||
                excludedItems.contains(this.shuffledItems.get(j).getItemId());
    }

    /**
     * Selects the next two items for voting from the shuffled list. If the selection
     * process is finished, returns null.
     *
     * @return a list containing the next two {@link ItemVO} objects for voting,
     * or null if all items have been exhausted
     */
    @Override
    public List<ItemVO> selectNextTwoItems() {
        int n = shuffledItems.size();
        int[] indexes;
        do {
            if (isFinished()) return new ArrayList<>();
            indexes = mapTo2D(this.round++, n);
        } while (isUnsuitablePair(indexes[0], indexes[1]));

        List<ItemVO> items = new ArrayList<>();
        items.add(this.shuffledItems.get(indexes[0]));
        items.add(this.shuffledItems.get(indexes[1]));
        return items;
    }

    /**
     * Checks if the selection process has finished, which occurs when all pairs
     * have been selected.
     *
     * @return true if the selection process is finished; false otherwise
     */
    @Override
    public Boolean isFinished() {
        int n = shuffledItems.size();
        return this.round == n * n;
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
