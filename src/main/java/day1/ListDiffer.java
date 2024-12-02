package day1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListDiffer {

    public static long listDifference(final List<Long> list1, final List<Long> list2) {
        assert list1.size() == list2.size()
                : "Incompatible number of location lists. All elf teams must have an equal number of locations on their list";
        final List<Long> list1Copy = new ArrayList<>(list1);
        final List<Long> list2Copy = new ArrayList<>(list2);
        Collections.sort(list1Copy);
        Collections.sort(list2Copy);

        long difference = 0;
        for (int i = 0; i < list1Copy.size(); i++) {
            difference += Math.abs(list1Copy.get(i) - list2Copy.get(i));
        }
        return difference;
    }
}

