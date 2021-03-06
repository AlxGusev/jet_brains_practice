package Paging;

import java.util.ArrayList;
import java.util.List;

public interface PagingInterface {

    default List<List<List<String>>> splitRecordsToPages(List<List<String>> list, int number) {
        return splitToPages(list, number);
    }

    default List<List<List<String>>> splitToPages(List<List<String>> list, int number) {
        List<List<List<String>>> pages = new ArrayList<>();
        int n = list.size();
        for (int i = 0; i < n; i += number) {
            pages.add(new ArrayList<>(
                    list.subList(i, Math.min(n, i + number))
            ));
        }
        return pages;
    }

}
